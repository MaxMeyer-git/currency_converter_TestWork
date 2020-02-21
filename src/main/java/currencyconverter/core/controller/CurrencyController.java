package currencyconverter.core.controller;


import currencyconverter.core.entity.сurrency.*;
import currencyconverter.core.service.MainConversionService;
import currencyconverter.core.service.RequestLogUnitService;
import io.swagger.annotations.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CurrencyController {

    private final MainConversionService mainConversionService;
    private final RequestLogUnitService requestLogUnitService;

    public CurrencyController(MainConversionService mainConversionService,
                              RequestLogUnitService requestLogUnitService) {
        this.mainConversionService = mainConversionService;
        this.requestLogUnitService = requestLogUnitService;
    }

    @ApiOperation(value = "Convert currency ", response = CurrencyExchangeResponseDTO.class, tags = "REAL END POINTS")
    @PostMapping(path = "/user/converter", consumes = "application/json", produces = "application/json")
    public CurrencyExchangeResponseDTO convert(@Validated @RequestBody CurrencyExchangeRequestDTO request) {
        return mainConversionService.calculate(request);
    }

    @ApiOperation(value = "Return a List of Users exchange logs, accordingly the requested property", tags = "REAL END POINTS")
    @PostMapping(path = "/admin/log", consumes = "application/json", produces = "application/json")
    public List<ExchangeLogUnitResponseDTO> getRequestLog(@Validated @RequestBody ExchangeLogUnitRequest request) {
        return requestLogUnitService.getLogsDTO(request);
    }

    @ApiOperation(value = "Get a List of all Available currencies", tags = "REAL END POINTS")
    @GetMapping("/info")
    public List<CurrencyENUM> info() {
        return CurrencyENUM.getAllAvailable();
    }

    @ApiOperation(value = "Fake end point, to easily get reference for the Postman.", tags = "FAKE")
    @GetMapping("/converter_reference")
    public CurrencyExchangeRequestDTO referenceForConvert() {
        return new CurrencyExchangeRequestDTO(CurrencyENUM.USD, CurrencyENUM.AMD, 100., "10.02.2019");
    }

    @ApiOperation(value = "Fake end point, to easily get reference for the Postman.", tags = "FAKE")
    @GetMapping("/log_reference")
    public ExchangeLogUnitRequest referenceForGetRequestLog() {
        return new ExchangeLogUnitRequest(CurrencyENUM.USD, CurrencyENUM.AMD, "09.02.2019", true);
    }


}
