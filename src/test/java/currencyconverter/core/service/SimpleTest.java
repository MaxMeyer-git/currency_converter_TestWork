package currencyconverter.core.service;

import currencyconverter.core.entity.сurrency.CurrencyENUM;
import org.junit.Test;


public class SimpleTest {

    @Test
    public void test() {

//        System.out.println(CurrencyENUM.RUR.getAllAvailable());
//        Object[] possibleValues = CurrencyENUM.RUR.getDeclaringClass().getEnumConstants();

//        Object[] possibleValues = CurrencyENUM.RUR.getDeclaringClass().getEnumConstants();
//        for (Object o : possibleValues) {
//            System.out.println(o.getClass());
//        }

        var x = CurrencyENUM.findByNumCode(392);
        System.out.println(x.name());
    }

}
