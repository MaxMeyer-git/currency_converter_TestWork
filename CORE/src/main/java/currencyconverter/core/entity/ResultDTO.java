package currencyconverter.core.entity;

import jdk.jfr.StackTrace;
import lombok.*;

import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ResultDTO {

    Double value;
    LocalDate date;



}
