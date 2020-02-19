package currencyconverter.core.entity.сurrency;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
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

    @Column (name = "numcode")
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


 }
