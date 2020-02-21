package currencyconverter.core.service;

import currencyconverter.core.entity.сurrency.*;
import currencyconverter.core.util.DataConversionUtility;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest
public class mainConversionServiceTest {

    @Autowired
    private MainConversionService mainConversionService;
    @Autowired
    private CurrencyService currencyService;
    @Autowired
    private RequestLogUnitService requestLogUnitService;
    @Autowired
    private DataConversionUtility dcu;

    private CurrencyENUM currFrom = CurrencyENUM.USD;
    private CurrencyENUM currTo = CurrencyENUM.AMD;
    private Double amount = 100.;
    private String dateStr1 = "16.05.2000";
    private String dateStr2 = "09.02.2019";
    private LocalDate localDateNow;
    private String stringDateNow;

    @Before
    public void aBefore() {
        localDateNow = LocalDate.now();
        stringDateNow = dcu.DateToString(localDateNow);
    }

    @Test
    public void aCalculate() {
        ConversionRequest conversionRequest = new ConversionRequest
                (currFrom, currTo, amount, dateStr1);

        var x = mainConversionService.calculate(conversionRequest);
        System.out.println(x.toString());
    }

    @Test
    public void bWebReq() {
        var x = new LogUnitRequest(currFrom, currTo, null, null);
        var y = new LogUnitRequest(currFrom, currTo, dateStr2, true);
        var z = new LogUnitRequest(currFrom, currTo, stringDateNow, false);

        soutRequestLogUnit(requestLogUnitService.getLog(x), "Null / Null");
        soutRequestLogUnit(requestLogUnitService.getLog(y), dateStr2 + " / true");
        soutRequestLogUnit(requestLogUnitService.getLog(z), "string Date Now / false");
    }

    @Test
    public void cWebReq() {
        var x = new LogUnitRequest(currFrom, currTo, null, null);
        var y = new LogUnitRequest(currFrom, currTo, dateStr2, true);
        var z = new LogUnitRequest(currFrom, currTo, stringDateNow, false);

        soutRequestLogDTO(requestLogUnitService.getLogsDTO(x), "getLogsDTO Null / Null");
        soutRequestLogDTO(requestLogUnitService.getLogsDTO(y), "getLogsDTO " + dateStr2 + " / true");
        soutRequestLogDTO(requestLogUnitService.getLogsDTO(z), "getLogsDTO string Date Now / false");
    }

    private void soutRequestLogUnit(List<RequestLogUnit> logUnitList, String name) {
        universalTestSout(logUnitList.stream().map(RequestLogUnit::toString).collect(Collectors.toList()), name);
    }

    private void soutRequestLogDTO(List<RequestLogDTO> requestLogDTOS, String name) {
        universalTestSout(requestLogDTOS.stream().map(RequestLogDTO::toString).collect(Collectors.toList()), name);
    }

    private void universalTestSout(List<String> stringList, String name) {
        System.out.println();
        System.out.println("--------------------- " + name + " ---------------------");
        for (String s : stringList) {
            System.out.println(s);
        }
        System.out.println("-----------------------------------------------");
    }
}