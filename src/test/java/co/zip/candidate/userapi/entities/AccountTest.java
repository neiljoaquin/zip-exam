package co.zip.candidate.userapi.entities;

import co.zip.candidate.userapi.repositories.AccountRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@TestPropertySource(properties = { "spring.config.location=classpath:application.properties" })
public class AccountTest {
    @Autowired
    private AccountRepository accountRepository;

    @AfterEach
    void destroy() {
        accountRepository.deleteAll();
    }

    @Test
    void testUniqueEmailAddress() {
        String email = "sample@gmail.com";
        assertThrows(DataIntegrityViolationException.class, () -> {
            saveAccount("Sample 1", email);
            saveAccount("Sample 2", email);
        });
    }

    private void saveAccount(String name, String email) {
        Account account2 = new Account(name, email, 1L, 1L);
        accountRepository.save(account2);
    }
}
