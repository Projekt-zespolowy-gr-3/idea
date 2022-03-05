package pl.dmcs.idea.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.dmcs.idea.entities.Account;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByLogin(String login);
    Optional<Account> findByToken(String token);
}
