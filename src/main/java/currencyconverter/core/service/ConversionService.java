package currencyconverter.core.service;

import currencyconverter.core.entity.сurrency.ConversionRequest;
import currencyconverter.core.entity.сurrency.CurrInerTransport;
import currencyconverter.core.entity.сurrency.RequestLogUnit;
import currencyconverter.core.entity.сurrency.ResultDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ConversionService {
    private final RequestLogUnitService requestLogUnitService;
    private final CurrencyService currencyService;

    public ConversionService(RequestLogUnitService requestLogUnitService, CurrencyService currencyService) {
        this.requestLogUnitService = requestLogUnitService;
        this.currencyService = currencyService;
    }

    public ResultDTO calculate(ConversionRequest request) {
        CurrInerTransport currency = currencyService.getCurrValue(request);
        Double res = (currency.getValFrom() / currency.getNominalFrom() * request.getAmount())
                / (currency.getValTo() / currency.getNominalTo());

        res = roundD(res, 2);

        logSave(currency, request, res);

        return new ResultDTO(request.getCurrencyFrom().getName(),
                request.getCurrencyTo().getName(),
                res,
                currencyService.parseFromDateToString(currency.getDate()));
    }

    @Transactional
    protected void logSave(CurrInerTransport currency, ConversionRequest request, Double result) {
        var requestLogUnit = new RequestLogUnit(
                request.getCurrencyFrom().getNumcode(),
                currency.getValFrom() / currency.getNominalFrom(),
                request.getCurrencyTo().getNumcode(),
                currency.getValTo() / currency.getNominalTo(),
                request.getAmount(),
                result,
                currency.getDate());
        requestLogUnitService.save(requestLogUnit);
    }

    private static double roundD(double value, int places) {
        double scale = Math.pow(10, places);
        return Math.round(value * scale) / scale;
    }
}