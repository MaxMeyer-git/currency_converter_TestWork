package currencyconverter.core.util;

import lombok.Getter;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class DataConversionUtility {

    private final static int DECIMAL_PLACES = 2;

    @Getter
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public String DateToString(LocalDate date) {
        return date.format(formatter);
    }

    public LocalDate StringToDate(String date) {
        return LocalDate.parse(date, formatter);
    }

    public double roundDouble(double value) {
        double scale = Math.pow(10, DECIMAL_PLACES);
        return Math.round(value * scale) / scale;
    }
}
