package currencyconverter.core.service;

import currencyconverter.core.entity.сurrency.ConversionRequest;
import currencyconverter.core.entity.сurrency.CurrencyENUM;
import currencyconverter.core.entity.сurrency.RequestLogUnit;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CurrencyServiceTest {

    @Autowired
    private CurrencyService currencyService;

    @Autowired
    private RequestLogUnitService requestLogUnitService;

    private CurrencyENUM currFrom = CurrencyENUM.USD;
    private CurrencyENUM currTo = CurrencyENUM.AMD;
    private Double amount = 100.;
    private String dateStr = "10.02.2019";
    private LocalDate localDate = LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    private LocalDate localDateForLog = LocalDate.parse("09.02.2019", DateTimeFormatter.ofPattern("dd.MM.yyyy"));


    @Test
    public void aCalculate() {
        ConversionRequest conversionRequest = new ConversionRequest
                (currFrom, currTo, amount, dateStr);

        var x = currencyService.calculate(conversionRequest);
        System.out.println(x.toString());
    }

    @Test
    public void bFindLog() {
        var x = requestLogUnitService.findByCurrencyCouple(currFrom, currTo);
        var y = requestLogUnitService.findByCurrencyCoupleAndDateOfCruse(currFrom, currTo, localDateForLog);
        var z = requestLogUnitService.findByCurrencyCoupleAndDateOfRequest(currFrom, currTo, LocalDate.now());
        testSout(x, "findByCurrencyCouple");
        testSout(y, "findByCurrencyCoupleAndDateOfCruse");
        testSout(z, "findByCurrencyCoupleAndDateOfRequest");
    }

    private void testSout (List<RequestLogUnit> logUnitList, String name){
        System.out.println();
        System.out.println("--------------------- " + name + " ---------------------");
        System.out.println();
        for (RequestLogUnit lu: logUnitList) {
            System.out.println(lu.toString());
        }
    }
}