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

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
    private final Account accountSample2 = new Account("Sample2", "sample2@gmail.com", 2000L, 1L);

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

    @Test
    void testFetchAccountSuccess() throws Exception {
        mockMvc.perform(post(AccountController.BASE_URL)
                        .content(OBJECT_MAPPER.writeValueAsString(accountSample1))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        mockMvc.perform(get(AccountController.BASE_URL)
                        .param("id", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is(accountSample1.getName())))
                .andExpect(jsonPath("$.emailAddress", is(accountSample1.getEmailAddress())))
                .andExpect(jsonPath("$.monthlySalary", is(accountSample1.getMonthlySalary().intValue())))
                .andExpect(jsonPath("$.monthlyExpenses", is(accountSample1.getMonthlyExpenses().intValue())))
                .andReturn();
    }

    @Test
    void testFetchAccountNotFound() throws Exception {
        mockMvc.perform(post(AccountController.BASE_URL)
                        .content(OBJECT_MAPPER.writeValueAsString(accountSample1))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        mockMvc.perform(get(AccountController.BASE_URL)
                        .param("id", "2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andReturn();
    }

    @Test
    void testFetchAccountList() throws Exception {
        mockMvc.perform(post(AccountController.BASE_URL)
                        .content(OBJECT_MAPPER.writeValueAsString(accountSample1))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        mockMvc.perform(post(AccountController.BASE_URL)
                        .content(OBJECT_MAPPER.writeValueAsString(accountSample2))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        mockMvc.perform(get(AccountController.BASE_URL + "/list")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("[0].id", is(1)))
                .andExpect(jsonPath("[0].name", is(accountSample1.getName())))
                .andExpect(jsonPath("[0].emailAddress", is(accountSample1.getEmailAddress())))
                .andExpect(jsonPath("[0].monthlySalary", is(accountSample1.getMonthlySalary().intValue())))
                .andExpect(jsonPath("[0].monthlyExpenses", is(accountSample1.getMonthlyExpenses().intValue())))
                .andExpect(jsonPath("[1].id", is(2)))
                .andExpect(jsonPath("[1].name", is(accountSample2.getName())))
                .andExpect(jsonPath("[1].emailAddress", is(accountSample2.getEmailAddress())))
                .andExpect(jsonPath("[1].monthlySalary", is(accountSample2.getMonthlySalary().intValue())))
                .andExpect(jsonPath("[1].monthlyExpenses", is(accountSample2.getMonthlyExpenses().intValue())))
                .andReturn();
    }
}
