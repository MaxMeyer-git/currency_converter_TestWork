package currencyconverter.core.entity.сurrency;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@ApiModel
@Getter
public class ConversionRequest {

    @ApiModelProperty(example = "USD", value = "Source currency", required = true)
    CurrencyENUM currencyFrom;

    @ApiModelProperty(example = "AMD", value = "Target currency", required = true, position = 1)
    CurrencyENUM currencyTo;

    @ApiModelProperty(example = "100", value = "Amount", required = true, position = 2)
    Double amount;

    @ApiModelProperty(example = "09.02.2019", value = "Conversion curse date Format: dd.MM.yyyy ", required = true, position = 3)
    String date;

    public ConversionRequest(
            @JsonProperty("currencyFrom") @NotBlank @NotNull CurrencyENUM currencyFrom,
            @JsonProperty("currencyTo") @NotBlank @NotNull CurrencyENUM currencyTo,
            @JsonProperty("amount") @NotBlank @NotNull Double amount,
            @JsonProperty("date") @NotBlank @NotNull String date) {
        this.currencyFrom = currencyFrom;
        this.currencyTo = currencyTo;
        this.amount = amount;
        this.date = date;
    }
}
