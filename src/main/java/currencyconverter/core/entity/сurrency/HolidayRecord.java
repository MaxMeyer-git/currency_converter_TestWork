package currencyconverter.core.entity.сurrency;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "holiday_record")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class HolidayRecord {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @Column(name = "request_date")
    LocalDate requestDate;

    @Column(name = "last_working_day")
    LocalDate  lastWorkingDay;

    public HolidayRecord(LocalDate requestDate, LocalDate lastWorkingDay) {
        this.requestDate = requestDate;
        this.lastWorkingDay = lastWorkingDay;
    }
}
