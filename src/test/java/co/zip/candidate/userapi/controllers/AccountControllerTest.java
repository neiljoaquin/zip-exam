package co.zip.candidate.userapi.controllers;

import co.zip.candidate.userapi.entities.Account;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@SpringBootTest
@TestPropertySource(properties = { "spring.config.location=classpath:application.properties" })
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AccountControllerTest {
    private final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;

    private final Account accountSample1 = new Account("Sample", "sample@gmail.com", 2000L, 1L);
    private final Account accountSameEmail = new Account("Sample2", "sample@gmail.com", 3000L, 2L);
    private final Account accountNoCredit = new Account("Sample", "sample@gmail.com", 2000L, 2000L);

    @BeforeEach
    void setup() {
        DefaultMockMvcBuilder builder = webAppContextSetup(this.wac);
        this.mockMvc = builder.build();
    }

    @Test
    void testAddAccountSuccess() throws Exception {
        mockMvc.perform(post(AccountController.BASE_URL)
                        .content(OBJECT_MAPPER.writeValueAsString(accountSample1))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void testAddAccountNotUniqueEmail() throws Exception {
        mockMvc.perform(post(AccountController.BASE_URL)
                        .content(OBJECT_MAPPER.writeValueAsString(accountSample1))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        mockMvc.perform(post(AccountController.BASE_URL)
                        .content(OBJECT_MAPPER.writeValueAsString(accountSameEmail))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andReturn();
    }

    @Test
    void testAddAccountInvalidCredit() throws Exception {
        mockMvc.perform(post(AccountController.BASE_URL)
                        .content(OBJECT_MAPPER.writeValueAsString(accountSample1))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        mockMvc.perform(post(AccountController.BASE_URL)
                        .content(OBJECT_MAPPER.writeValueAsString(accountNoCredit))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andReturn();
    }
}
