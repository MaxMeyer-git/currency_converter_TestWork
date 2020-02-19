package currencyconverter.core.entity.сurrency;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@ApiModel
@Getter
public class ConversionRequest {

    @ApiModelProperty(value = "Source currency", required = true)
    CurrencyENUM currencyFrom;

    @ApiModelProperty(value = "Target currency", required = true)
    CurrencyENUM currencyTo;

    @ApiModelProperty(value = "Amount", required = true)
    Double amount;

    @ApiModelProperty(value = "Conversion curse date", required = true)
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
