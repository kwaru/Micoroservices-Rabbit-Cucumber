package microservices.book.socialmultiplications.Repository;

import microservices.book.socialmultiplications.domain.MultiplicationResultAttempt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MultiplicationResultAttemptRepository extends JpaRepository<MultiplicationResultAttempt, Long> {
    /**
     * @return the latest 5 attempts for a given user,
    identified by their alias.
     */
    List<MultiplicationResultAttempt> findTop5ByUserAliasOrderByIdDesc(String userAlias);
}