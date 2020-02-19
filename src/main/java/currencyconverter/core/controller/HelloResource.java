package currencyconverter.core.controller;


import currencyconverter.core.entity.сurrency.ConversionRequest;
import currencyconverter.core.entity.сurrency.CurrencyENUM;
import currencyconverter.core.entity.сurrency.ResultDTO;
import currencyconverter.core.service.CurrencyService;
import currencyconverter.core.service.RequestLogUnitService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@Api(tags = {"Currency"}, consumes = "application/json; utf-8", produces = "application/json; utf-8")
@SwaggerDefinition(tags = {@Tag(name = "Currency", description = "Currency conversion API")})
public class HelloResource {

    private final CurrencyService currencyService;
    private final RequestLogUnitService requestLogUnitService;

    public HelloResource(CurrencyService currencyService, RequestLogUnitService requestLogUnitService) {
        this.currencyService = currencyService;
        this.requestLogUnitService = requestLogUnitService;
    }

    @GetMapping("/info")
    public String info() {
        return CurrencyENUM.RUR.getAllAvailable();
    }

    @ApiOperation(value = "Convert currency from one to another")
    @GetMapping("/convert")
    public ResultDTO convert(@Validated @RequestBody ConversionRequest request) {
        return currencyService.calculate(request);
    }

    @ApiOperation(value = "Update DB on certain date, return date of pulled data")
    @GetMapping("/update/{date}")
    public String update(@PathVariable("date") String date) {
        LocalDate ld = currencyService.pullAndSave(date);
        return currencyService.parseFromDateToString(ld);
    }

    @GetMapping("/test")
    public String noSecureTest() {
        return "test";
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
