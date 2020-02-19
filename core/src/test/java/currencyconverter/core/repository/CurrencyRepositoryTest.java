package currencyconverter.core.repository;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CurrencyRepositoryTest {

    @Autowired
    private CurrencyRepository currencyRepository;

    @Ignore
    @Test
    public void test (){

        String dateIn = "17.02.2020";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate date = LocalDate.parse(dateIn, formatter);

        var x = currencyRepository.findByNumcodeAndDate(840, date);
        System.out.println(x.orElseThrow().getValue());

//        {
//            "amount": 10,
//                "currencyFrom": "USD",
//                "currencyTo": "RUR",
//                "date": "17.02.2020"
//        }
    }

}