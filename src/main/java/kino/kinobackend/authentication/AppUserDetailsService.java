package kino.kinobackend.authentication;

import kino.kinobackend.customer.CustomerModel;
import kino.kinobackend.customer.CustomerRepository;
import kino.kinobackend.employee.EmployeeModel;
import kino.kinobackend.employee.EmployeeRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;



/*
    denne her klasse gør brug af Spring Security's UserDetailsService
    og bruges til at hente en bruger (enten medarbejder eller en kunde)
    fra db baseret på brugernavn (medarbejder=navn, kunde=email).

    Spring Security kan så bruge oplysnignerne til autentificering
 */

@Service
public class AppUserDetailsService implements UserDetailsService {

    private final EmployeeRepository employeeRepository;
    private final CustomerRepository customerRepository;

    public AppUserDetailsService(EmployeeRepository employeeRepository, CustomerRepository customerRepository) {
        this.employeeRepository = employeeRepository;
        this.customerRepository = customerRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Først tjekker vi om brugeren findes som medarbejder
        Optional<EmployeeModel> employee = employeeRepository.findByUsername(username);
        if (employee.isPresent()) {
            // Hvis medarbejderen findes, opretter vi en User med rollen fra medarbejderen
            return User.builder()
                    .username(employee.get().getUsername())
                    .password(employee.get().getPassword())
                    .roles(employee.get().getRole()) // Bruger rollen fra medarbejderen (ADMIN eller FILM_OPERATOR)
                    .build();
        }

        // Hvis medarbejderen ikke findes, tjekker vi om brugeren er en kunde
        CustomerModel customer = customerRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // Hvis det er en kunde, opretter vi en User med rollen 'USER'
        return User.builder()
                .username(customer.getUsername())
                .password(customer.getPassword())
                .roles("USER") // Alle kunder får rollen USER
                .build();
    } // Så har vi oprettet en User-instans som SPring Security kan bruge til autentificering :-)



}
