package currencyconverter.core.service;

import currencyconverter.core.entity.сurrency.CurrencyENUM;
import currencyconverter.core.entity.сurrency.LogUnitRequest;
import currencyconverter.core.entity.сurrency.RequestLogUnit;
import currencyconverter.core.repository.RequestLogUnitRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class RequestLogUnitService {

    private final RequestLogUnitRepository requestLogUnitRepository;

    public RequestLogUnitService(RequestLogUnitRepository requestLogUnitRepository) {
        this.requestLogUnitRepository = requestLogUnitRepository;
    }

    public void save(RequestLogUnit rlu) {
        requestLogUnitRepository.save(rlu);
    }

    public List<RequestLogUnit> getLog(LogUnitRequest request) {
        if (request.getDate() == null) {
            return findByCurrencyCouple(request.getCurrencyFrom(), request.getCurrencyTo());
        }
        if (request.getCheckBox() != null) {
            if (request.getCheckBox()) {
                return findByCurrencyCoupleAndDateOfCourse
                        (request.getCurrencyFrom(), request.getCurrencyTo(), parseDate(request.getDate()));
            } else {
                return findByCurrencyCoupleAndDateOfRequest
                        (request.getCurrencyFrom(), request.getCurrencyTo(), parseDate(request.getDate()));
            }
        }
        return null;
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

    private LocalDate parseDate(String date) {
        return LocalDate.parse(date, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }
}
