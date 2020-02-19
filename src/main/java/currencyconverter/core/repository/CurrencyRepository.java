package currencyconverter.core.repository;

import currencyconverter.core.entity.сurrency.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, UUID> {

    Optional<Currency> findByNumcodeAndDate(Integer numcode , LocalDate date);

    Long countByDate (LocalDate date);

}
