package currencyconverter.core.service;

import currencyconverter.core.entity.сurrency.HolidayRecord;
import currencyconverter.core.repository.HolidayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.NoSuchElementException;

@Service
public class HolidayService {

//  TODO cache
    private final HolidayRepository holidayRepository;

    public HolidayService(HolidayRepository holidayRepository) {
        this.holidayRepository = holidayRepository;
    }

    public LocalDate isItHoliday(LocalDate request) {
        var x = holidayRepository.findByRequestDate(request);
        if (x.isPresent()) {
            return x.orElseThrow(NoSuchElementException::new).getLastWorkingDay();
        } else return null;
    }

    public void saveNewHoliday(LocalDate reqDate, LocalDate lwd) {
        if (!reqDate.equals(lwd)) {
            holidayRepository.save(new HolidayRecord(reqDate, lwd));
        }
    }
}
