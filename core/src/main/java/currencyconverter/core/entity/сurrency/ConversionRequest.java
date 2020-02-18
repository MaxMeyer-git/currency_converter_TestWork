package currencyconverter.core.entity.сurrency;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ConversionRequest {

    CurrencyENUM currencyFrom;
    CurrencyENUM currencyTo;
    Double amount;
    String date;



}
