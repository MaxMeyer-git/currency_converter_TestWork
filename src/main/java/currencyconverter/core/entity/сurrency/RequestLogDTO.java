package currencyconverter.core.entity.сurrency;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequestLogDTO {

    @JsonProperty("currFrom")
    private String currFrom;

    @JsonProperty("currTo")
    private String currTo;

    @JsonProperty("amount")
    private Double amount;

    @JsonProperty("result")
    private Double result;

    @JsonProperty("dateOfCourse")
    private String dateOfCourse;

    @Override
    public String toString() {
        return "RequestLogDTO{" +
                "currFrom='" + currFrom + '\'' +
                ", currTo='" + currTo + '\'' +
                ", amount=" + amount +
                ", result=" + result +
                ", dateOfCourse='" + dateOfCourse + '\'' +
                '}';
    }
}
