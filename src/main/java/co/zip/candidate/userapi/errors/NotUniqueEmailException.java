package co.zip.candidate.userapi.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author Neil Joaquin
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Email is already used")
public class NotUniqueEmailException extends InvalidInputException {
}
