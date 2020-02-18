package currencyconverter.core.repository;

import currencyconverter.core.entity.Currency;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, UUID> {


    Optional<Currency> findByNumcodeAndDate(Integer numcode , LocalDate date);


//    @Query(value = "select c from currency c where numcode = 840")
    Optional<Currency> findByNumcode(Integer numcode );


    Optional<Currency> findByDate(LocalDate date );

    Long countByDate (LocalDate date);
//    Optional<List<Currency>>


}
