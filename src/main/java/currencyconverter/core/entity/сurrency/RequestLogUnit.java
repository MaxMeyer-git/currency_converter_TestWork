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
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RequestLogUnit {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @Column(name = "dateofrequest")
    private LocalDate dateOfRequest;

    @Column(name = "numcodefrom")
    private int numCodeFrom;

    @Column(name = "cursefrom")
    private Double curseFrom;

    @Column(name = "numcodeto")
    private int numCodeTo;

    @Column(name = "curseto")
    private Double curseTo;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "result")
    private Double result;

    @Column(name = "dateofcruse")
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
        return
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
