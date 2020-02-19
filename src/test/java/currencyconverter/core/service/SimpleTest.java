package currencyconverter.core.service;

import currencyconverter.core.entity.сurrency.CurrencyENUM;
import org.junit.Test;


public class SimpleTest {

    @Test
    public void test() {

//        System.out.println(CurrencyENUM.RUR.getAllAvailable());

        int i = 0;
        Object[] possibleValues = CurrencyENUM.RUR.getDeclaringClass().getEnumConstants();
        for (Object o : possibleValues) {
            i++;
        }
        System.out.println(i);
    }

}
