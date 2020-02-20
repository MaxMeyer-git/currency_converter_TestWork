package currencyconverter.core.service;

import currencyconverter.core.entity.сurrency.*;
import currencyconverter.core.util.SomeDataConversionUtility;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SomeConversionServiceTest {

    @Autowired
    private SomeConversionService someConversionService;
    @Autowired
    private RequestLogUnitService requestLogUnitService;
    @Autowired
    private SomeDataConversionUtility dcu;

    private CurrencyENUM currFrom = CurrencyENUM.USD;
    private CurrencyENUM currTo = CurrencyENUM.AMD;
    private Double amount = 100.;
    private String dateStr1 = "10.02.2019";
    private String dateStr2 = "09.02.2019";
    private LocalDate localDate1;
    private LocalDate localDate2;
    private LocalDate localDateNow;
    private String stringDateNow;

    @Before
    public void aBefore() {
        localDate1 = dcu.StringToDate(dateStr1);
        localDate2 = dcu.StringToDate(dateStr2);
        localDateNow = LocalDate.now();
        stringDateNow = dcu.DateToString(localDateNow);
    }

    @Test
    public void aCalculate() {
        ConversionRequest conversionRequest = new ConversionRequest
                (currFrom, currTo, amount, dateStr1);

        var x = someConversionService.calculate(conversionRequest);
        System.out.println(x.toString());
    }

//    @Test
//    public void bFindLog() {
//        var x = requestLogUnitService.findByCurrencyCouple(currFrom, currTo);
//        var y = requestLogUnitService.findByCurrencyCoupleAndDateOfCourse(currFrom, currTo, localDate2);
//        var z = requestLogUnitService.findByCurrencyCoupleAndDateOfRequest(currFrom, currTo, localDateNow);
//        testSout(x, "findByCurrencyCouple");
//        testSout(y, "findByCurrencyCoupleAndDateOfCourse");
//        testSout(z, "findByCurrencyCoupleAndDateOfRequest");
//    }

//    @Test
//    public void cWebReq() {
//        var x = new LogUnitRequest(currFrom, currTo, null, null);
//        var y = new LogUnitRequest(currFrom, currTo, dateStr2, true);
//        var z = new LogUnitRequest(currFrom, currTo, stringDateNow, false);
//
//        testSout(requestLogUnitService.getLog(x), "Null / Null");
//        testSout(requestLogUnitService.getLog(y), dateStr2 + " / true");
//        testSout(requestLogUnitService.getLog(z), "string Date Now / false");
//    }


    @Test
    public void dWebReq() {
        var x = new LogUnitRequest(currFrom, currTo, null, null);
        var y = new LogUnitRequest(currFrom, currTo, dateStr2, true);
        var z = new LogUnitRequest(currFrom, currTo, stringDateNow, false);

        testSout(requestLogUnitService.getLogsDTO(x), "getLogsDTO Null / Null");
        testSout(requestLogUnitService.getLogsDTO(y), "getLogsDTO " + dateStr2 + " / true");
        testSout(requestLogUnitService.getLogsDTO(z), "getLogsDTO string Date Now / false");
    }


    private void testSout(List<RequestLogDTO> logUnitList, String name) {
        System.out.println();
        System.out.println("--------------------- " + name + " ---------------------");
        for (RequestLogDTO lu : logUnitList) {
            System.out.println(lu.toString());
        }
        System.out.println("-----------------------------------------------");
    }


}