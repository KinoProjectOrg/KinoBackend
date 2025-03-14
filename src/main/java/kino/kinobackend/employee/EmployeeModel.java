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
    private int employeeId;


    @Column(name="name")
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(name="role")
    private String role; // ændret fra enum til en String så Spring Security kan give dem forskellige rettigehder

//    @Enumerated(EnumType.STRING) // gemmer enum-værdien som en streng.
//    @Column(name="role")
//    private Role role; // admin eller Filmoperatør?
//
//    public enum Role {
//        ADMIN,
//        FILM_OPERATOR
//    }



}
