package microservices.book.socialmultiplications.Controller;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import microservices.book.socialmultiplications.Service.MultiplicationService;
import microservices.book.socialmultiplications.domain.MultiplicationResultAttempt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/results")
@CrossOrigin(origins = "http://localhost:63342/") // allow cross-origin for the client application
@Slf4j
public class MultiplicationResultAttemptController {

    private final MultiplicationService multiplicationService;
    @Autowired
    MultiplicationResultAttemptController(final MultiplicationService multiplicationService) {
        this.multiplicationService = multiplicationService;
    }
    // Here we'll implement our POST later
    @RequiredArgsConstructor
    @NoArgsConstructor(force = true)
    @Getter

    private static final class ResultResponse {
        private final boolean correct;
    }

    @GetMapping
    ResponseEntity<List<MultiplicationResultAttempt>>
    getStatistics(@RequestParam("alias") String alias) {
        log.info("Alis name "+alias);
        return ResponseEntity.ok(multiplicationService.getStatsForUser(alias));
    }

    @PostMapping
    ResponseEntity<MultiplicationResultAttempt>
    postResult(@RequestBody MultiplicationResultAttempt multiplicationResultAttempt) {
        boolean isCorrect = multiplicationService.checkAttempt(multiplicationResultAttempt);
        MultiplicationResultAttempt attemptCopy = new MultiplicationResultAttempt(
                multiplicationResultAttempt.getUser(), multiplicationResultAttempt.getMultiplication(),
                multiplicationResultAttempt.getResultAttempt(), isCorrect);
        return ResponseEntity.ok(attemptCopy);
    }
}
