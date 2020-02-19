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
@Table(name = "requestlogunit")
@Getter
public class RequestLogUnit {

    @Id
    @GeneratedValue
    @Column(name = "ID")
    private UUID id;

    private LocalDate dateOfRequest;

    private int numCodeFrom;

    private Double curseFrom;

    private int numCodeTo;

    private Double curseTo;

    private Double amount;

    private Double result;

    private LocalDate dateOfCruse;

    public RequestLogUnit( int numCodeFrom,
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
        this.dateOfCruse = dateOfCruse;
    }

    @Override
    public String toString() {
        return "RequestLogUnit{" +
                ", dateOfRequest=" + dateOfRequest +
                ", numCodeFrom=" + numCodeFrom +
                ", curseFrom=" + curseFrom +
                ", numCodeTo=" + numCodeTo +
                ", curseTo=" + curseTo +
                ", amount=" + amount +
                ", result=" + result +
                ", dateOfCruse=" + dateOfCruse +
                '}';
    }
}
