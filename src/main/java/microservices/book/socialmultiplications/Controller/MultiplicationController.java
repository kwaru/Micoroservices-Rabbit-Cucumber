package microservices.book.socialmultiplications.Controller;

import microservices.book.socialmultiplications.Service.MultiplicationService;
import microservices.book.socialmultiplications.domain.Multiplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:63342/")
@RequestMapping("/multiplications")
public class MultiplicationController {
    private final MultiplicationService multiplicationService;

    @Autowired
    public MultiplicationController(MultiplicationService multiplicationService){
        this.multiplicationService = multiplicationService;
    }

    @GetMapping("/randoms")
    public ResponseEntity<Multiplication> getRandomNumbers(){

        return  ResponseEntity.ok().body(multiplicationService.createRandomMultiplication());
    }
}
