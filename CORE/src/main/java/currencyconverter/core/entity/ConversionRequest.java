package currencyconverter.core.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ConversionRequest {

    VALCODEENUM currencyFrom;
    VALCODEENUM currencyTo;
    Double amount;
    String date;

}
