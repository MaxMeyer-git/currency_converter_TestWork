package currencyconverter.core.repository;

import currencyconverter.core.entity.user.AppUsers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UsersRepository extends JpaRepository<AppUsers, UUID> {

    Optional<AppUsers> findByName(String name);

    List<AppUsers> findAll ();
}
