package currencyconverter.core.repository;

import currencyconverter.core.entity.сurrency.RequestLogUnit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RequestLogUnitRepository extends JpaRepository<RequestLogUnit, UUID> {

    Optional<List<RequestLogUnit>> findByNumCodeFromAndNumCodeTo(int numCodeFrom, int numCodeTo );

    Optional<List<RequestLogUnit>> findByNumCodeFromAndNumCodeToaAndDateOfCruse(int numCodeFrom, int numCodeTo, LocalDate dateOfCruse);

    Optional<List<RequestLogUnit>> findByNumCodeFromAndNumCodeToAndDateOfRequest(int numCodeFrom, int numCodeTo, LocalDate dateOfCruse);
}