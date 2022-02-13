package microservices.book.socialmultiplications.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import microservices.book.socialmultiplications.Service.MultiplicationService;
import microservices.book.socialmultiplications.domain.Multiplication;
import microservices.book.socialmultiplications.domain.MultiplicationResultAttempt;
import microservices.book.socialmultiplications.domain.User;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.assertj.core.api.Assertions.assertThat;
@RunWith(SpringRunner.class)
@WebMvcTest(MultiplicationResultAttemptController.class)
class MultiplicationResultAttemptControllerTest {

    @MockBean
    private MultiplicationService multiplicationService;
    @Autowired
    private MockMvc mvc;
    // This object will be magically initialized by theinitFields method below.
    private JacksonTester<MultiplicationResultAttempt> jsonResult;
    //private JacksonTester<ResultResponse> jsonResponse;

    @Before
    public void setup() {
        JacksonTester.initFields(this, new ObjectMapper());
    }
    @Test
    public void postResultReturnCorrect() throws Exception {
        genericParameterizedTest(true);
    }
    @Test
    public void postResultReturnNotCorrect() throws Exception {
        genericParameterizedTest(false);
    }
    void genericParameterizedTest(final boolean correct) throws
            Exception {
        // given (remember we're not testing here the serviceitself)
        given(multiplicationService.checkAttempt(any(MultiplicationResultAttempt.class))).willReturn(correct);
        User user = new User("john");
        Multiplication multiplication = new Multiplication(50, 70);
        MultiplicationResultAttempt attempt = new MultiplicationResultAttempt(user, multiplication, 3500,true);
        // when
        MockHttpServletResponse response = mvc.perform(post("/results").contentType(MediaType.APPLICATION_JSON).content(jsonResult.write(attempt).getJson())).andReturn().getResponse();
        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        //assertThat(response.getContentAsString()).isEqualTo(jsonResponse.write(new ResultResponse(correct)).getJson());
    }
}