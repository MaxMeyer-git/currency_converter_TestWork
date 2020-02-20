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
public class ResultDTO {

    @ApiModelProperty(value = "Source currency", required = true)
    @JsonProperty("currencyFrom")
    private String currencyFrom;

    @ApiModelProperty(value = "Target currency", required = true)
    @JsonProperty("currencyTo")
    private String currencyTo;

    @ApiModelProperty(value = "Result", required = true)
    @JsonProperty("value")
    private Double value;

    @ApiModelProperty(value = "Conversion curse date", required = true)
    @JsonProperty("date")
    private String date;
}
