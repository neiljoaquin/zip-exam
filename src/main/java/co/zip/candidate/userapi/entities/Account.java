package co.zip.candidate.userapi.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Entity used for Account object in db
 * @author Neil Joaquin
 */
@Entity
@Data
@RequiredArgsConstructor
@NoArgsConstructor
public class Account implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column(nullable = false)
    @NonNull
    private String name;

    @Column(nullable = false, unique = true)
    @NonNull
    private String emailAddress;

    @Column(nullable = false)
    @NonNull
    private Long monthlySalary;

    @Column(nullable = false)
    @NonNull
    private Long monthlyExpenses;
}
