package currencyconverter.core.service;

import currencyconverter.core.entity.ConversionRequest;
import currencyconverter.core.entity.VALCODEENUM;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HuinaTest {

    @Autowired
    private Huina huina;

    @Test
    public void calc() {
        ConversionRequest conversionRequest = new ConversionRequest
                (VALCODEENUM.USD, VALCODEENUM.RUR, 100. ,
                        "21.02.2019");
        var x = huina.calc(conversionRequest);
        System.out.println(x.toString());

    }
}