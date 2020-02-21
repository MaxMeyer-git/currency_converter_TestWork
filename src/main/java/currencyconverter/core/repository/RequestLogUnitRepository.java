package currencyconverter.core.repository;

import currencyconverter.core.entity.сurrency.ExchangeLogUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RequestLogUnitRepository extends JpaRepository<ExchangeLogUnit, UUID> {

    Optional<List<ExchangeLogUnit>> findByNumCodeFromAndNumCodeTo(int numCodeFrom, int numCodeTo );

    Optional<List<ExchangeLogUnit>> findByNumCodeFromAndNumCodeToAndDateOfCourse(int numCodeFrom, int numCodeTo, LocalDate dateOfCourse);

    Optional<List<ExchangeLogUnit>> findByNumCodeFromAndNumCodeToAndDateOfRequest(int numCodeFrom, int numCodeTo, LocalDate dateOfRequest);
}
