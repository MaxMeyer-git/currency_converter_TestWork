package currencyconverter.core.service;

import currencyconverter.core.entity.сurrency.CurrencyENUM;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;


public class SimpleTest {

    @Test
    public void test() {

//        System.out.println(CurrencyENUM.RUR.getAllAvailable());
//        Object[] possibleValues = CurrencyENUM.RUR.getDeclaringClass().getEnumConstants();

//        Object[] possibleValues = CurrencyENUM.RUR.getDeclaringClass().getEnumConstants();
//        for (Object o : possibleValues) {
//            System.out.println(o.getClass());
//        }

//        var x = CurrencyENUM.findByNumCode(392);
//        System.out.println(x.name());
//        lol("lol");
//lol(1);
//lol(CurrencyENUM.RUR);

        List<Object> x = new ArrayList<>();
        x.add("LOl");
        x.add(1);
        x.add(CurrencyENUM.RUR);

        lol(x);

        lol(List.of("lol", 1, CurrencyENUM.RUR));


    }

    private  void lol(List<Object> Object) {
        System.out.println(Object.toString());
    }
}
