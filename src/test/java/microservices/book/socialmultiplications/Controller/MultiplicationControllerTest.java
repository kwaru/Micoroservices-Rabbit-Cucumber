package microservices.book.socialmultiplications.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import microservices.book.socialmultiplications.Service.MultiplicationService;
import microservices.book.socialmultiplications.domain.Multiplication;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
@RunWith(SpringRunner.class)
@WebMvcTest(MultiplicationController.class)
class MultiplicationControllerTest {

    @MockBean
    private MultiplicationService multiplicationService;
    @Autowired
    private MockMvc mvc;
    // This object will be magically initialized by theinitFields method below.
    private JacksonTester<Multiplication> json;
    @Before
    public void setup() {
        JacksonTester.initFields(this, new ObjectMapper());
    }
    @Test
    public void getRandomMultiplicationTest() throws Exception{
        // given
        given(multiplicationService.createRandomMultiplication()).willReturn(new Multiplication(70, 20));
        // when
        MockHttpServletResponse response = mvc.perform(MockMvcRequestBuilders.get("/multiplications/random").accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();
        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(json.write(new Multiplication(70, 20)).getJson());
    }



}