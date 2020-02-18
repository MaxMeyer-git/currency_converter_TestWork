package currencyconverter.core.entity;

import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.*;

public class VALCODEENUMTest {

    @Test
    public void doSonthing() {
        Double val_1_Cu = 65.25;
        Double val_2_Cu = 13.7;
        Double baseCur = 1.;

        int val_1_amount = 1;
        int val_2_amount = 78;

        int val_1_nominal = 1;
        int val_2_nominal = 1;

        Double res = val_1_amount * val_1_nominal * val_2_Cu / val_2_nominal * val_1_Cu ;
        System.out.println(res);

//        String dateIn = "05.02.2019";
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");


//        LocalDateTime dateTime = LocalDateTime.parse(dateIn, formatter);
//        LocalDate ld = LocalDate.parse(dateIn, formatter);


//        System.out.println(VALCODEENUM.AUD.getName());


    }

//    private Double count ()

}