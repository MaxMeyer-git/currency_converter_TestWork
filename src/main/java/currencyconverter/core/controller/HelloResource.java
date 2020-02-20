package currencyconverter.core.controller;


import currencyconverter.core.entity.сurrency.*;
import currencyconverter.core.service.SomeConversionService;
import currencyconverter.core.service.CurrencyService;
import currencyconverter.core.service.RequestLogUnitService;
import io.swagger.annotations.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@Api(tags = {"Currency"}, consumes = "application/json; utf-8", produces = "application/json; utf-8")
@SwaggerDefinition(tags = {@Tag(name = "Currency", description = "Currency conversion API")})
public class HelloResource  {

    private final SomeConversionService someConversionService;
    private final RequestLogUnitService requestLogUnitService;
    private final CurrencyService currencyService;


    public HelloResource(SomeConversionService someConversionService, RequestLogUnitService requestLogUnitService, CurrencyService currencyService) {
        this.someConversionService = someConversionService;
        this.requestLogUnitService = requestLogUnitService;
        this.currencyService = currencyService;
    }

    @GetMapping("/info")
    public String info() {
        return CurrencyENUM.RUR.getAllAvailable();
    }

    @GetMapping("/converter_reference")
    public ConversionRequest referenceForConvert() {
        return new ConversionRequest(CurrencyENUM.USD, CurrencyENUM.AMD, 100., "10.02.2019");
    }

    @ApiOperation(value = "Convert currency from one to another")
//    @GetMapping("/user/converter")
    @GetMapping("/converter")
    public ResultDTO convert(@Validated @RequestBody ConversionRequest request) {
        return someConversionService.calculate(request);
    }

    @GetMapping("/log_reference")
    public LogUnitRequest referenceForGetRequestLog() {
        return new LogUnitRequest(CurrencyENUM.USD, CurrencyENUM.AMD, "09.02.2019", true);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "ok", response = RequestLogUnit.class)
    })
//    @GetMapping("/admin/log")
    @GetMapping("/log")
    public List<RequestLogDTO> getRequestLog(@Validated @RequestBody LogUnitRequest request) {
        return requestLogUnitService.getLogsDTO(request);
    }

    @GetMapping("/convert/x")
    public String secureTestConvert() {
        return "secure convert test";
    }

    @GetMapping("/update/x")
    public String secureTestUpdate() {
        return "secure update test";
    }
}
