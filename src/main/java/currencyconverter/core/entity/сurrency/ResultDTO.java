package currencyconverter.core.entity.сurrency;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ResultDTO {

    @ApiModelProperty(value = "Source currency", required = true)
    private String currencyFrom;

    @ApiModelProperty(value = "Target currency", required = true)
    private String currencyTo;

    @ApiModelProperty(value = "Result", required = true)
    private Double value;

    @ApiModelProperty(value = "Conversion curse date", required = true)
    private String date;


}
