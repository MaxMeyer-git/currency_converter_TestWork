package currencyconverter.core.controller;


import currencyconverter.core.entity.сurrency.*;
import currencyconverter.core.service.MainConversionService;
import currencyconverter.core.service.RequestLogUnitService;
import io.swagger.annotations.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(tags = {"Currency"}, consumes = "application/json; utf-8", produces = "application/json; utf-8")
@SwaggerDefinition(tags = {@Tag(name = "Currency", description = "Currency conversion API")})
public class HelloResource {

    private final MainConversionService mainConversionService;
    private final RequestLogUnitService requestLogUnitService;

    public HelloResource(MainConversionService mainConversionService, RequestLogUnitService requestLogUnitService) {
        this.mainConversionService = mainConversionService;
        this.requestLogUnitService = requestLogUnitService;
    }

    @GetMapping("/info")
    public List<CurrencyENUM> info() {
        return CurrencyENUM.getAllAvailable();
    }

    @GetMapping("/converter_reference")
    public ConversionRequest referenceForConvert() {
        return new ConversionRequest(CurrencyENUM.USD, CurrencyENUM.AMD, 100., "10.02.2019");
    }

    //    @GetMapping("/user/converter")
    @GetMapping("/converter")
    public ResultDTO convert(@Validated @RequestBody ConversionRequest request) {
        return mainConversionService.calculate(request);
    }

    @GetMapping("/log_reference")
    public LogUnitRequest referenceForGetRequestLog() {
        return new LogUnitRequest(CurrencyENUM.USD, CurrencyENUM.AMD, "09.02.2019", true);
    }

    //    @GetMapping("/admin/log")
    @GetMapping("/log")
    public List<RequestLogDTO> getRequestLog(@Validated @RequestBody LogUnitRequest request) {
        return requestLogUnitService.getLogsDTO(request);
    }
}
