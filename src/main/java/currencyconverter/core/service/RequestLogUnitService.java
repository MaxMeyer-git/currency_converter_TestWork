package currencyconverter.core.service;

import currencyconverter.core.entity.сurrency.CurrencyENUM;
import currencyconverter.core.entity.сurrency.LogUnitRequest;
import currencyconverter.core.entity.сurrency.RequestLogDTO;
import currencyconverter.core.entity.сurrency.RequestLogUnit;
import currencyconverter.core.repository.RequestLogUnitRepository;
import currencyconverter.core.util.SomeDataConversionUtility;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class RequestLogUnitService {

    private final RequestLogUnitRepository requestLogUnitRepository;
    private final SomeDataConversionUtility dcu;


    public RequestLogUnitService(RequestLogUnitRepository requestLogUnitRepository, SomeDataConversionUtility dcu) {
        this.requestLogUnitRepository = requestLogUnitRepository;
        this.dcu = dcu;
    }

    public void save(RequestLogUnit rlu) {
        requestLogUnitRepository.save(rlu);
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
                (curFrom.getNumcode(), curTo.getNumcode());
        return optional.orElseThrow(NoSuchElementException::new);
    }

    public List<RequestLogUnit> findByCurrencyCoupleAndDateOfCourse
            (CurrencyENUM curFrom, CurrencyENUM curTo, LocalDate date) {

        var optional = requestLogUnitRepository.findByNumCodeFromAndNumCodeToAndDateOfCourse
                (curFrom.getNumcode(), curTo.getNumcode(), date);
        return optional.orElseThrow(NoSuchElementException::new);
    }

    public List<RequestLogUnit> findByCurrencyCoupleAndDateOfRequest
            (CurrencyENUM curFrom, CurrencyENUM curTo, LocalDate date) {

        var optional = requestLogUnitRepository.findByNumCodeFromAndNumCodeToAndDateOfRequest
                (curFrom.getNumcode(), curTo.getNumcode(), date);
        return optional.orElseThrow(NoSuchElementException::new);
    }

    private String findCurrencyEnum(int numCode) {
        return CurrencyENUM.findByNumCode(numCode).name();
    }
}
