package co.zip.candidate.userapi.services;

import co.zip.candidate.userapi.dtos.AccountDto;
import co.zip.candidate.userapi.entities.Account;
import co.zip.candidate.userapi.errors.CreditLimitException;
import co.zip.candidate.userapi.errors.InvalidInputException;
import co.zip.candidate.userapi.errors.NotUniqueEmailException;
import co.zip.candidate.userapi.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service class to contain logic on Account operations
 * @author Neil Joaquin
 */
@Service
public class AccountService {
    private static Long CREDIT_LIMIT = 1000L;
    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    /**
     * Adds the account to the db if
     * 1. Email address is unique
     * 2. Monthly salary - monthly expenses < 1000
     * @param accountDto the account to be added
     * @throws InvalidInputException is thrown whenever the
     * above properties are not met
     */
    public void addAccount(AccountDto accountDto) throws InvalidInputException {
        if (accountDto.getMonthlySalary() - accountDto.getMonthlyExpenses() < CREDIT_LIMIT) {
            throw new CreditLimitException();
        }
        if (accountRepository.findAccountByEmailAddress(accountDto.getEmailAddress()).isPresent()) {
            throw new NotUniqueEmailException();
        }
        accountRepository.save(convertDtoToEntity(accountDto));
    }

    private Account convertDtoToEntity(AccountDto accountDto) {
        return new Account(accountDto.getName(),
                accountDto.getEmailAddress(),
                accountDto.getMonthlySalary(),
                accountDto.getMonthlyExpenses());
    }
}
