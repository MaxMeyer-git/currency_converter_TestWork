package currencyconverter.core.entity.сurrency;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
public class RequestLogDTO  {

    private String currFrom;

    private String currTo;

    private Double amount;

    private Double result;

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
