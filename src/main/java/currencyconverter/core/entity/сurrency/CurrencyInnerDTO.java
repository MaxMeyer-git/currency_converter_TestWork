package currencyconverter.core.entity.сurrency;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
public class CurrencyInnerDTO {

    private Double valFrom;
    private int nominalFrom;

    private Double valTo;
    private int nominalTo;

    private LocalDate date;
}
