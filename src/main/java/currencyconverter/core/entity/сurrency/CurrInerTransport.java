package currencyconverter.core.entity.сurrency;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@Setter
@Getter
@NoArgsConstructor
public class CurrInerTransport {

        Double valFrom;
        int nominalFrom;

        Double valTo;
        int nominalTo;

        LocalDate date;
}
