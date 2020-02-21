package currencyconverter.core.controller;


import currencyconverter.core.entity.сurrency.*;
import currencyconverter.core.service.MainConversionService;
import currencyconverter.core.service.RequestLogUnitService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class HelloResource {

    private final MainConversionService mainConversionService;
    private final RequestLogUnitService requestLogUnitService;
//    @Value("${priority.available.currency}")
//    private  String lol ;
    public HelloResource(MainConversionService mainConversionService, RequestLogUnitService requestLogUnitService) {
        this.mainConversionService = mainConversionService;
        this.requestLogUnitService = requestLogUnitService;
    }

    //    @PostMapping(path = "/user/converter", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "Convert currency ", response = ResultDTO.class, tags = "REAL END POINTS")
    @PostMapping(path = "/converter", consumes = "application/json", produces = "application/json")
    public ResultDTO convert(@Validated @RequestBody ConversionRequest request) {
        return mainConversionService.calculate(request);
    }

    //    @PostMapping(path = "/admin/log", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "Return a List of Users exchange logs, accordingly the requested property", tags = "REAL END POINTS")
    @PostMapping(path = "/log", consumes = "application/json", produces = "application/json")
    public List<RequestLogDTO> agetRequestLog(@Validated @RequestBody LogUnitRequest request) {
        return requestLogUnitService.getLogsDTO(request);
    }

    @ApiOperation(value = "Get a List of all Available currencies", tags = "REAL END POINTS")
    @GetMapping("/info")
    public List<CurrencyENUM> info() {
        return CurrencyENUM.getAllAvailable();
    }

    @ApiOperation(value = "Fake end point, to easily get reference for the Postman.", tags = "FAKE")
    @GetMapping("/converter_reference")
    public ConversionRequest referenceForConvert() {
        return new ConversionRequest(CurrencyENUM.USD, CurrencyENUM.AMD, 100., "10.02.2019");
    }

    @ApiOperation(value = "Fake end point, to easily get reference for the Postman.", tags = "FAKE")
    @GetMapping("/log_reference")
    public LogUnitRequest referenceForGetRequestLog() {
        return new LogUnitRequest(CurrencyENUM.USD, CurrencyENUM.AMD, "09.02.2019", true);
    }


}
