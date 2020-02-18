package currencyconverter.core.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CurrencyRepositoryTest {


    private CurrencyRepository currencyRepository;

    @Test
    public void test (){

        String dateIn = "21.02.2019";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate ld = LocalDate.parse(dateIn, formatter);

//        System.out.println(ld);
        var x = currencyRepository.findByNumcodeAndDate(840, ld);
//        var x = currencyRepository.findByNumcode(840);
//        var x = currencyRepository.countByDate(ld);
//        System.out.println(x.get().getValue());
        System.out.println(x);

    }

}