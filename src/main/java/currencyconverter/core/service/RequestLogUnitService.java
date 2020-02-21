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
    public void logSave(CurrencyInnerDTO currency, ConversionRequest request, Double result) {
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

    private List<RequestLogUnit> findByCurrencyCouple
            (CurrencyENUM curFrom, CurrencyENUM curTo) {
        return requestLogUnitRepository.findByNumCodeFromAndNumCodeTo
                (curFrom.getNumCode(), curTo.getNumCode()).orElseThrow(NoSuchElementException::new);
    }

    private List<RequestLogUnit> findByCurrencyCoupleAndDateOfCourse
            (CurrencyENUM curFrom, CurrencyENUM curTo, LocalDate date) {
        return requestLogUnitRepository.findByNumCodeFromAndNumCodeToAndDateOfCourse
                (curFrom.getNumCode(), curTo.getNumCode(), date).orElseThrow(NoSuchElementException::new);
    }

    private List<RequestLogUnit> findByCurrencyCoupleAndDateOfRequest
            (CurrencyENUM curFrom, CurrencyENUM curTo, LocalDate date) {
        return requestLogUnitRepository.findByNumCodeFromAndNumCodeToAndDateOfRequest
                (curFrom.getNumCode(), curTo.getNumCode(), date).orElseThrow(NoSuchElementException::new);
    }

    private String findCurrencyEnum(int numCode) {
        return CurrencyENUM.findByNumCode(numCode).name();
    }
}
