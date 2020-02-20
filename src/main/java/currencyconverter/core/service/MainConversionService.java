package currencyconverter.core.service;

import currencyconverter.core.entity.сurrency.ConversionRequest;
import currencyconverter.core.entity.сurrency.CurrencyInnerDTO;
import currencyconverter.core.entity.сurrency.ResultDTO;
import currencyconverter.core.util.DataConversionUtility;
import org.springframework.stereotype.Service;

@Service
public class MainConversionService {

    private final RequestLogUnitService requestLogUnitService;
    private final CurrencyService currencyService;
    private final DataConversionUtility dcu;

    public MainConversionService(RequestLogUnitService requestLogUnitService, CurrencyService currencyService, DataConversionUtility dcu) {
        this.requestLogUnitService = requestLogUnitService;
        this.currencyService = currencyService;
        this.dcu = dcu;
    }

    public ResultDTO calculate(ConversionRequest request) {
        CurrencyInnerDTO currency = currencyService.getCurrencyValues(request);

        double result = (currency.getValFrom() / currency.getNominalFrom() * request.getAmount())
                / (currency.getValTo() / currency.getNominalTo());

        result = dcu.roundDouble(result);
        requestLogUnitService.logSave(currency, request, result);

        return new ResultDTO(request.getCurrencyFrom().getName(),
                request.getCurrencyTo().getName(),
                result,
                dcu.DateToString(currency.getDate()));
    }

}
