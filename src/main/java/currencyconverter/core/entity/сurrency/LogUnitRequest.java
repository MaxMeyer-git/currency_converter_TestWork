package currencyconverter.core.entity.сurrency;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@ApiModel
@Getter
public class LogUnitRequest {

    @ApiModelProperty(example = "USD", value = "Source currency", required = true)
    private CurrencyENUM currencyFrom;

    @ApiModelProperty(example = "AMD", value = "Target currency", required = true, position = 1)
    CurrencyENUM currencyTo;

    @ApiModelProperty(example = "09.02.2019", value = "date, unnecessary, " +
            "if null/empty return all accordingly chosen currencies", position = 2)
    String date;

    @ApiModelProperty(example = "true", value = "unnecessary can be null/empty, " +
            "if true date = date of course, if false Date Of Request", position = 3)
    Boolean checkBox;

    public LogUnitRequest(
            @JsonProperty("currencyFrom") @NotBlank @NotNull CurrencyENUM currencyFrom,
            @JsonProperty("currencyTo") @NotBlank @NotNull CurrencyENUM currencyTo,
            @JsonProperty("date") String date,
            @JsonProperty("checkBox") Boolean checkBox
    ) {
        this.currencyFrom = currencyFrom;
        this.currencyTo = currencyTo;
        this.date = date;
        this.checkBox = checkBox;
    }
}
