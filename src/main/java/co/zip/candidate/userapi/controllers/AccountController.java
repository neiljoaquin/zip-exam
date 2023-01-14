package co.zip.candidate.userapi.controllers;

import co.zip.candidate.userapi.dtos.AccountDto;
import co.zip.candidate.userapi.entities.Account;
import co.zip.candidate.userapi.errors.EntryNotFoundException;
import co.zip.candidate.userapi.errors.InvalidInputException;
import co.zip.candidate.userapi.repositories.AccountRepository;
import co.zip.candidate.userapi.services.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Exposes REST API endpoints that allows Account entity related operations on
 * the db
 * @author Neil Joaquin
 */
@RestController
public class AccountController {
    public static final String BASE_URL = "/api/v1/accounts";
    private final AccountRepository accountRepository;
    private final AccountService accountService;

    public AccountController(AccountRepository accountRepository, AccountService accountService) {
        this.accountRepository = accountRepository;
        this.accountService = accountService;
    }

    @PostMapping(BASE_URL)
    @ResponseStatus(HttpStatus.OK)
    public void addAccount(@RequestBody AccountDto accountDto) throws InvalidInputException {
        accountService.addAccount(accountDto);
    }

    @GetMapping(BASE_URL)
    public ResponseEntity<Account> fetchAccount(@RequestParam Long id) {
        Optional<Account> account = accountRepository.findById(id);
        return ResponseEntity.ok(account.orElseThrow(EntryNotFoundException::new));
    }

    @GetMapping(BASE_URL + "/list")
    public ResponseEntity<List<Account>> fetchAccountList() {
        return ResponseEntity.ok(accountRepository.findAll());
    }
}
