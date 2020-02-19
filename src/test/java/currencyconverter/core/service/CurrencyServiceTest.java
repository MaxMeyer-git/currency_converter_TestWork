package currencyconverter.core.service;

import currencyconverter.core.entity.сurrency.ConversionRequest;
import currencyconverter.core.entity.сurrency.CurrencyENUM;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CurrencyServiceTest {

    @Autowired
    private CurrencyService currencyService;

    @Test
    public void calc() {
        ConversionRequest conversionRequest = new ConversionRequest
                (CurrencyENUM.USD, CurrencyENUM.RUR, 100. ,
                        "17.02.2019");

        var x = currencyService.calculate(conversionRequest);
        System.out.println(x.toString());

    }
}