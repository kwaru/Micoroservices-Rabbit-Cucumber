package microservices.book.socialmultiplications.Service;

import static org.junit.jupiter.api.Assertions.*;

import microservices.book.socialmultiplications.domain.Multiplication;
import microservices.book.socialmultiplications.domain.MultiplicationResultAttempt;
import microservices.book.socialmultiplications.domain.User;
import org.junit.Before;
import org.junit.Test;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import static org.assertj.core.api.Assertions.assertThat;

class RandomGeneratorServiceImpTest {

    private RandomGeneratorServiceImp randomGeneratorServiceImpl;
    private MultiplicationServiceImpl multiplicationServiceImpl;
    @Before
    public void setUp() {
        randomGeneratorServiceImpl = new
                RandomGeneratorServiceImp();
    }
    @Test
    public void generateRandomFactorIsBetweenExpectedLimits()
            throws Exception {
        // when a good sample of randomly generated factors is generated
        List<Integer> randomFactors = IntStream.range(0, 1000)
                .map(i -> randomGeneratorServiceImpl.
                        generateRandomFactor())
                .boxed().collect(Collectors.toList());
        // then all of them should be between 11 and 100
        // because we want a middle-complexity calculation
        assertThat(randomFactors).containsOnlyElementsOf(IntStream.range(11, 100)
                        .boxed().collect(Collectors.toList()));
    }


    @Test
    public void checkCorrectAttemptTest() {
        // given
        Multiplication multiplication = new Multiplication(50, 60);
        User user = new User("john_doe");
        MultiplicationResultAttempt attempt = new MultiplicationResultAttempt(user, multiplication, 3000,false);
        // when
        boolean attemptResult = multiplicationServiceImpl.checkAttempt(attempt);
        // assert
        assertThat(attemptResult).isTrue();
    }
    @Test
    public void checkWrongAttemptTest() {
        // given
        Multiplication multiplication = new Multiplication(50, 60);
        User user = new User("john_doe");
        MultiplicationResultAttempt attempt = new MultiplicationResultAttempt(user, multiplication, 3010,false);
        // when
        boolean attemptResult = multiplicationServiceImpl.checkAttempt(attempt);
        // assert
        assertThat(attemptResult).isFalse();
    }
}