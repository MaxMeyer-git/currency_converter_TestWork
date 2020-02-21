package currencyconverter.core.entity.сurrency;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "exchange_log_unit")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ExchangeLogUnit {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @Column(name = "date_of_request")
    private LocalDate dateOfRequest;

    @Column(name = "num_code_from")
    private int numCodeFrom;

    @Column(name = "curse_from")
    private Double curseFrom;

    @Column(name = "num_code_to")
    private int numCodeTo;

    @Column(name = "curse_to")
    private Double curseTo;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "result")
    private Double result;

    @Column(name = "date_of_course")
    private LocalDate dateOfCourse;

    public ExchangeLogUnit(int numCodeFrom,
                           Double curseFrom,
                           int numCodeTo,
                           Double curseTo,
                           Double amount,
                           Double result,
                           LocalDate dateOfCruse) {

        this.dateOfRequest = LocalDate.now();
        this.numCodeFrom = numCodeFrom;
        this.curseFrom = curseFrom;
        this.numCodeTo = numCodeTo;
        this.curseTo = curseTo;
        this.amount = amount;
        this.result = result;
        this.dateOfCourse = dateOfCruse;
    }

    @Override
    public String toString() {
        return
                ", dateOfRequest=" + dateOfRequest +
                ", numCodeFrom=" + numCodeFrom +
                ", curseFrom=" + curseFrom +
                ", numCodeTo=" + numCodeTo +
                ", curseTo=" + curseTo +
                ", amount=" + amount +
                ", result=" + result +
                ", dateOfCruse=" + dateOfCourse +
                '}';
    }
}
