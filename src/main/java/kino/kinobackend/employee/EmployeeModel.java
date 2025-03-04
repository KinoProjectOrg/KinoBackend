package kino.kinobackend.employee;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="employee")
public class EmployeeModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="employee_id")
    private int employeeID;

    private String name;

    @Enumerated(EnumType.STRING) // gemmer enum-værdien som en streng.
    @Column(name="role")
    private Role role; // admin eller Filmoperatør?

    public enum Role {
        ADMIN,
        FILM_OPERATOR
    }



}
