package kino.kinobackend.configuration;

import kino.kinobackend.employee.EmployeeModel;
import kino.kinobackend.employee.EmployeeRepository;
import kino.kinobackend.customer.CustomerModel;
import kino.kinobackend.customer.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInit {

    @Bean
    CommandLineRunner initDatabase(
            EmployeeRepository employeeRepository,
            CustomerRepository customerRepository,
            PasswordEncoder passwordEncoder) {

        return args -> {
            // Only add if not already exists
            if (employeeRepository.findByUsername("admin").isEmpty()) {
                EmployeeModel admin = new EmployeeModel();
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("123"));
                admin.setRole("ADMIN");
                employeeRepository.save(admin);
                System.out.println("Added admin user");
            }

            if (employeeRepository.findByUsername("operator").isEmpty()) {
                EmployeeModel operator = new EmployeeModel();
                operator.setUsername("operator");
                operator.setPassword(passwordEncoder.encode("123"));
                operator.setRole("FILM_OPERATOR");
                employeeRepository.save(operator);
                System.out.println("Added film operator user");
            }

        };

    }
}


