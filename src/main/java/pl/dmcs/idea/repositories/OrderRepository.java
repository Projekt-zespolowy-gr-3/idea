package pl.dmcs.idea.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.dmcs.idea.entities.Order;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    Optional<Order> findByBusinessKey(String businessKey);
    List<Order> findAllByClientAccountLogin(String login);
}
