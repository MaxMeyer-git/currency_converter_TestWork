package currencyconverter.core.service;

import currencyconverter.core.entity.сurrency.CurrencyENUM;
import currencyconverter.core.entity.сurrency.RequestLogUnit;
import currencyconverter.core.repository.RequestLogUnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class RequestLogUnitService {

    private final RequestLogUnitRepository requestLogUnitRepository;

    public RequestLogUnitService(RequestLogUnitRepository requestLogUnitRepository) {
        this.requestLogUnitRepository = requestLogUnitRepository;
    }

    public void save (RequestLogUnit rlu){
        requestLogUnitRepository.save(rlu);

    }

    public List<RequestLogUnit> findByCurrencyCouple (CurrencyENUM curFrom, CurrencyENUM curTo){
        var optional = requestLogUnitRepository.findByNumCodeFromAndNumCodeTo(curFrom.getNumcode(), curTo.getNumcode());
        return optional.orElseThrow(NoSuchElementException::new);
    }

    public List<RequestLogUnit> findByCurrencyCoupleAndDateOfCruse (CurrencyENUM curFrom, CurrencyENUM curTo, LocalDate date){
        var optional = requestLogUnitRepository.findByNumCodeFromAndNumCodeToaAndDateOfCruse(curFrom.getNumcode(), curTo.getNumcode(),date);
        return optional.orElseThrow(NoSuchElementException::new);
    }

    public List<RequestLogUnit> findByCurrencyCoupleAndDateOfRequest (CurrencyENUM curFrom, CurrencyENUM curTo, LocalDate date){
        var optional = requestLogUnitRepository.findByNumCodeFromAndNumCodeToAndDateOfRequest(curFrom.getNumcode(), curTo.getNumcode(),date);
        return optional.orElseThrow(NoSuchElementException::new);
    }




}
