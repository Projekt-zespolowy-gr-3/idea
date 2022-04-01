package pl.dmcs.idea.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.dmcs.idea.entities.AccessLevel;

import java.util.Optional;

public interface AccessLevelRepository extends JpaRepository<AccessLevel, Long> {

    Optional<AccessLevel> findFirstByAccountLoginAndAccessLevel(String login, String accessLevel);

}
