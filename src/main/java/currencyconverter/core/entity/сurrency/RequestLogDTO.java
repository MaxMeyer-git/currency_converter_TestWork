package currencyconverter.core.entity.сurrency;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RequestLogDTO {

    @ApiModelProperty(value = "Source currency")
    @JsonProperty("currFrom")
    private String currFrom;

    @ApiModelProperty(value = "Target currency")
    @JsonProperty("currTo")
    private String currTo;

    @ApiModelProperty(value = "Amount")
    @JsonProperty("amount")
    private Double amount;

    @ApiModelProperty(value = "Result")
    @JsonProperty("result")
    private Double result;

    @ApiModelProperty(value = "Date of this exchange")
    @JsonProperty("dateOfCourse")
    private String dateOfCourse;

}
