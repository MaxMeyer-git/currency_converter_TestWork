package currencyconverter.core.entity.сurrency;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;


@Entity
@Table (name = "currency")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Currency {

    @Id
    @GeneratedValue
    @Column (name = "id")
    private UUID id;

    @Column (name = "num_code")
    private Integer numcode;

    @Column (name = "value")
    private Double value;

    @Column (name = "date")
    private  LocalDate date;

    public Currency(ValuteDTO valuteDTO, LocalDate date) {
        this.numcode = valuteDTO.getNumcode();
        this.value = valuteDTO.getValue();
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Currency)) return false;
        Currency currency = (Currency) o;
        return Objects.equals(numcode, currency.numcode) &&
                Objects.equals(value, currency.value) &&
                Objects.equals(date, currency.date);
    }

    @Override
    public String toString() {
        return "Currency{" +
                ", numcode=" + numcode +
                ", value=" + value +
                ", date=" + date +
                '}';
    }
}
