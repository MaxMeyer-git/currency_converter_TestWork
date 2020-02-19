package currencyconverter.core.repository;

import currencyconverter.core.entity.сurrency.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, UUID> {

    Optional<Currency> findByNumcodeAndDate(Integer numcode , LocalDate date);

    Optional<List<Currency>> findByDate(LocalDate date);

    int countByDate (LocalDate date);

}
