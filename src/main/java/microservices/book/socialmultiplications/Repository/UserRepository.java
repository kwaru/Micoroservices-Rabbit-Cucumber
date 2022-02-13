package microservices.book.socialmultiplications.Repository;

import microservices.book.socialmultiplications.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * This interface allows us to save and retrieve Users
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByAlias(final String alias);
}
