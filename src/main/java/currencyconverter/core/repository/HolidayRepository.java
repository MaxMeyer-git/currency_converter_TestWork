package currencyconverter.core.repository;

import currencyconverter.core.entity.сurrency.HolidayRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface HolidayRepository extends JpaRepository<HolidayRecord , UUID> {

   Optional<HolidayRecord> findByRequestDate (LocalDate requestDate);


}
