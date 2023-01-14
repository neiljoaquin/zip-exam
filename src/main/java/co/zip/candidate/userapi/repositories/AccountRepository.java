package co.zip.candidate.userapi.repositories;

import co.zip.candidate.userapi.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository class to do Account entity related operations on db
 * @author Neil Joaquin
 */
public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findAccountByEmailAddress(String emailAddress);
}
