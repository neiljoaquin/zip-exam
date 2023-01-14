package co.zip.candidate.userapi.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author Neil Joaquin
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Item is not found")
public class EntryNotFoundException extends RuntimeException {
}
