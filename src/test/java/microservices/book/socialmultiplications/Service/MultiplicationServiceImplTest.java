package microservices.book.socialmultiplications.Service;

import microservices.book.socialmultiplications.Repository.MultiplicationRepository;
import microservices.book.socialmultiplications.Repository.MultiplicationResultAttemptRepository;
import microservices.book.socialmultiplications.Repository.UserRepository;
import microservices.book.socialmultiplications.domain.Multiplication;
import microservices.book.socialmultiplications.domain.MultiplicationResultAttempt;
import microservices.book.socialmultiplications.domain.User;
import org.junit.Before;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.BDDMockito.given;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

class MultiplicationServiceImplTest {

    private MultiplicationServiceImpl
            multiplicationServiceImpl;
    @Mock
    private RandomGeneratorService randomGeneratorService;
    @Mock
    private MultiplicationResultAttemptRepository
            attemptRepository;
    @Mock
    private UserRepository userRepository;

    @Mock
    private MultiplicationRepository multiplicationRepository;
    @Before
    public void setUp() {
        // With this call to initMocks we tell Mockito toprocess the annotations
        MockitoAnnotations.initMocks(this);
        multiplicationServiceImpl = new MultiplicationServiceImpl(randomGeneratorService,attemptRepository,userRepository,multiplicationRepository);
    }
    @Test
    public void createRandomMultiplicationTest() {
        // [...] no changes here, keep it as it was before
    }


    @Test
    public void checkCorrectAttemptTest() {
        // given
        Multiplication multiplication = new Multiplication
                (50, 60);
        User user = new User("john_doe");
        MultiplicationResultAttempt attempt = new
                MultiplicationResultAttempt(
                user, multiplication, 3000, false);
        MultiplicationResultAttempt verifiedAttempt = new
                MultiplicationResultAttempt(
                user, multiplication, 3000, true);given(userRepository.findByAlias("john_doe")).
                willReturn(Optional.empty());
        // when
        boolean attemptResult = multiplicationServiceImpl.
                checkAttempt(attempt);
        // then
        assertThat(attemptResult).isTrue();
        verify(attemptRepository).save(verifiedAttempt);
    }
    @Test
    public void checkWrongAttemptTest() {
        // given
        Multiplication multiplication = new Multiplication
                (50, 60);
        User user = new User("john_doe");
        MultiplicationResultAttempt attempt = new
                MultiplicationResultAttempt(
                user, multiplication, 3010, false);
        //given(userRepository.findByAlias("john_doe")).willReturn(Optional.empty());

        // when
        boolean attemptResult = multiplicationServiceImpl.checkAttempt(attempt);
        // then
        assertThat(attemptResult).isFalse();
        verify(attemptRepository).save(attempt);
    }
}