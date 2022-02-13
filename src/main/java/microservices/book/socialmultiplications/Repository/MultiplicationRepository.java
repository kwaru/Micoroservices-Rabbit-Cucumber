package microservices.book.socialmultiplications.Repository;

import microservices.book.socialmultiplications.domain.Multiplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * This interface allows us to save and retrieve
 Multiplications
 */
@Repository
public interface MultiplicationRepository extends JpaRepository<Multiplication, Long> {
    Multiplication findByAlias(String AliasName);
}