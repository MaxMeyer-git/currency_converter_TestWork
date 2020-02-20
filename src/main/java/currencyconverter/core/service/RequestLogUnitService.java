package currencyconverter.core.service;

import currencyconverter.core.entity.сurrency.*;
import currencyconverter.core.repository.RequestLogUnitRepository;
import currencyconverter.core.util.DataConversionUtility;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class RequestLogUnitService {

    private final RequestLogUnitRepository requestLogUnitRepository;
    private final DataConversionUtility dcu;

    public RequestLogUnitService(RequestLogUnitRepository requestLogUnitRepository, DataConversionUtility dcu) {
        this.requestLogUnitRepository = requestLogUnitRepository;
        this.dcu = dcu;
    }

    @Transactional
    protected void logSave(CurrencyInnerDTO currency, ConversionRequest request, Double result) {
        var requestLogUnit = new RequestLogUnit(
                request.getCurrencyFrom().getNumCode(),
                currency.getValFrom() / currency.getNominalFrom(),
                request.getCurrencyTo().getNumCode(),
                currency.getValTo() / currency.getNominalTo(),
                request.getAmount(),
                result,
                currency.getDate());
        requestLogUnitRepository.save(requestLogUnit);
    }

    public List<RequestLogDTO> getLogsDTO(LogUnitRequest request) {
        List<RequestLogUnit> res = getLog(request);
        return res.stream().map(requestLogUnit -> new RequestLogDTO(
                findCurrencyEnum(requestLogUnit.getNumCodeFrom()),
                findCurrencyEnum(requestLogUnit.getNumCodeTo()),
                requestLogUnit.getAmount(),
                requestLogUnit.getResult(),
                dcu.DateToString(requestLogUnit.getDateOfCourse())
        )).collect(Collectors.toList());
    }

//    public for tests. Might be useful for future statistic services.
    public List<RequestLogUnit> getLog(LogUnitRequest request) {
        if (request.getDate() == null) {
            return findByCurrencyCouple(request.getCurrencyFrom(), request.getCurrencyTo());
        }
        if (request.getCheckBox() != null) {
            if (request.getCheckBox()) {
                return findByCurrencyCoupleAndDateOfCourse
                        (request.getCurrencyFrom(), request.getCurrencyTo(), dcu.StringToDate(request.getDate()));
            } else {
                return findByCurrencyCoupleAndDateOfRequest
                        (request.getCurrencyFrom(), request.getCurrencyTo(), dcu.StringToDate(request.getDate()));
            }
        }
        throw new NoSuchElementException("No such Request log");
    }

    public List<RequestLogUnit> findByCurrencyCouple
            (CurrencyENUM curFrom, CurrencyENUM curTo) {

        var optional = requestLogUnitRepository.findByNumCodeFromAndNumCodeTo
                (curFrom.getNumCode(), curTo.getNumCode());
        return optional.orElseThrow(NoSuchElementException::new);
    }

    public List<RequestLogUnit> findByCurrencyCoupleAndDateOfCourse
            (CurrencyENUM curFrom, CurrencyENUM curTo, LocalDate date) {

        var optional = requestLogUnitRepository.findByNumCodeFromAndNumCodeToAndDateOfCourse
                (curFrom.getNumCode(), curTo.getNumCode(), date);
        return optional.orElseThrow(NoSuchElementException::new);
    }

    public List<RequestLogUnit> findByCurrencyCoupleAndDateOfRequest
            (CurrencyENUM curFrom, CurrencyENUM curTo, LocalDate date) {

        var optional = requestLogUnitRepository.findByNumCodeFromAndNumCodeToAndDateOfRequest
                (curFrom.getNumCode(), curTo.getNumCode(), date);
        return optional.orElseThrow(NoSuchElementException::new);
    }

    private String findCurrencyEnum(int numCode) {
        return CurrencyENUM.findByNumCode(numCode).name();
    }
}
