package currencyconverter.core.entity.сurrency;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CurrencyExchangeResponseDTO {

    @ApiModelProperty(value = "Source currency")
    @JsonProperty("currencyFrom")
    private String currencyFrom;

    @ApiModelProperty(value = "Target currency")
    @JsonProperty("currencyTo")
    private String currencyTo;

    @ApiModelProperty(value = "Result")
    @JsonProperty("value")
    private Double value;

    @ApiModelProperty(value = "Conversion curse date")
    @JsonProperty("date")
    private String date;
}
