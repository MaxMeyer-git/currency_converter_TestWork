package currencyconverter.core.repository;

import currencyconverter.core.entity.user.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UsersRepository extends JpaRepository<AppUser, UUID> {

    Optional<AppUser> findByName(String name);

}
