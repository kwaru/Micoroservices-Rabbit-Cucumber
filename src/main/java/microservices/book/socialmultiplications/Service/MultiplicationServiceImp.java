package microservices.book.socialmultiplications.Service;

import microservices.book.socialmultiplications.Repository.MultiplicationRepository;
import microservices.book.socialmultiplications.Repository.MultiplicationResultAttemptRepository;
import microservices.book.socialmultiplications.Repository.UserRepository;
import microservices.book.socialmultiplications.domain.Multiplication;
import microservices.book.socialmultiplications.domain.MultiplicationResultAttempt;
import microservices.book.socialmultiplications.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
class MultiplicationServiceImpl implements MultiplicationService {
    private RandomGeneratorService randomGeneratorService;

    private MultiplicationResultAttemptRepository attemptRepository;
    private UserRepository userRepository;
    private MultiplicationRepository multiplicationRepository;
    @Autowired
    public MultiplicationServiceImpl(final RandomGeneratorService randomGeneratorService,
                                     final MultiplicationResultAttemptRepository attemptRepository,
                                     final UserRepository userRepository, final MultiplicationRepository multiplicationRepository) {
        this.randomGeneratorService = randomGeneratorService;
        this.attemptRepository = attemptRepository;
        this.userRepository = userRepository;
        this.multiplicationRepository = multiplicationRepository;
    }

    @Override
    public List<MultiplicationResultAttempt>
    getStatsForUser(String userAlias) {
        return attemptRepository.findTop5ByUserAliasOrderByIdDesc(userAlias);
    }

    /**
     * Deletes a single multiplication record.
     * @param alias
     * @return
     */
    @Override
    public Multiplication deleteMultiplication(String alias) {
        Multiplication multiplicationfound = multiplicationRepository.findByAlias(alias);
        if (multiplicationfound!=null){
            // set in trash to true for this record.
            return  multiplicationfound;
        }

        return null;
    }


    // [
    @Override
    public Multiplication createRandomMultiplication() {
        int factorA = randomGeneratorService.
                generateRandomFactor();
        int factorB = randomGeneratorService.
                generateRandomFactor();
        return new Multiplication(factorA, factorB);
    }


    @Override
    @Transactional
    public boolean checkAttempt(final MultiplicationResultAttempt
                                        attempt) {
        // Check if the user already exists for that alias
        Optional<User> user = userRepository.findByAlias(attempt.getUser().getAlias());

        // Avoids 'hack' attempts
        Assert.isTrue(!attempt.isCorrect(), "You can't send an attempt marked as correct!!");
        // Check if the attempt is correct
        boolean isCorrect = attempt.getResultAttempt() ==
                attempt.getMultiplication().
                        getFactorA() *
                        attempt.getMultiplication().
                                getFactorB();
        MultiplicationResultAttempt checkedAttempt = new
                MultiplicationResultAttempt(
                user.orElse(attempt.getUser()),
                attempt.getMultiplication(),
                attempt.getResultAttempt(),
                isCorrect
        );
        // Stores the attempt
        attemptRepository.save(checkedAttempt);
        return isCorrect;
    }




}