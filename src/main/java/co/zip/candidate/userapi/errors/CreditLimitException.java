package co.zip.candidate.userapi.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author Neil Joaquin
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Credit rating is unsatisfactory")
public class CreditLimitException extends InvalidInputException {
}
