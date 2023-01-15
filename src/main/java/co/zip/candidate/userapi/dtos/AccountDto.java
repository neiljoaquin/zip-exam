package co.zip.candidate.userapi.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * POJO Data Transfer Object for Account
 * @author Neil Joaquin
 */
@Data
@AllArgsConstructor
public class AccountDto {
    private String name;
    private String emailAddress;
    private Long monthlySalary;
    private Long monthlyExpenses;
}
