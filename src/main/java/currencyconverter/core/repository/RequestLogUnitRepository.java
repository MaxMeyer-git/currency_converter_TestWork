package currencyconverter.core.repository;

import currencyconverter.core.entity.сurrency.RequestLogUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RequestLogUnitRepository extends JpaRepository<RequestLogUnit, UUID> {

    Optional<List<RequestLogUnit>> findByNumCodeFromAndNumCodeTo(int numCodeFrom, int numCodeTo );

    Optional<List<RequestLogUnit>> findByNumCodeFromAndNumCodeToAndDateOfCourse(int numCodeFrom, int numCodeTo, LocalDate dateOfCourse);

    Optional<List<RequestLogUnit>> findByNumCodeFromAndNumCodeToAndDateOfRequest(int numCodeFrom, int numCodeTo, LocalDate dateOfRequest);
}
