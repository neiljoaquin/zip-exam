package co.zip.candidate.userapi.services;

import co.zip.candidate.userapi.dtos.AccountDto;
import co.zip.candidate.userapi.entities.Account;
import co.zip.candidate.userapi.errors.CreditLimitException;
import co.zip.candidate.userapi.errors.NotUniqueEmailException;
import co.zip.candidate.userapi.repositories.AccountRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {
    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountService accountService;
    private final String email = "sample@gmail.com";
    private final AccountDto accountDto = new AccountDto("sample", email, 2000L, 1L);
    private final AccountDto accountDtoInvalidCredit = new AccountDto("sample", email, 2000L, 2000L);
    private final Account account = new Account("sample", email, 2000L, 1L);

    @Test
    void testAddAccountSuccess() {
        accountService.addAccount(accountDto);
    }

    @Test
    void testAddAccountNotUniqueEmail() {
        when(accountRepository.findAccountByEmailAddress(email)).thenReturn(Optional.of(account));
        assertThrows(NotUniqueEmailException.class, () -> accountService.addAccount(accountDto));
    }

    @Test
    void testAddAccountInvalidCredit() {
        assertThrows(CreditLimitException.class, () -> accountService.addAccount(accountDtoInvalidCredit));
    }
}
