package currencyconverter.core.util;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class SomeDataConversionUtility {

//    @Getter
    private  DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public String DateToString(LocalDate date) {
        return date.format(formatter);
    }

    public LocalDate StringToDate(String date) {
        return LocalDate.parse(date, formatter);
    }
}
