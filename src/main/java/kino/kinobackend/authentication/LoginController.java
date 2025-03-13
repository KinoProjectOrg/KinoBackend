package kino.kinobackend.authentication;

import kino.kinobackend.customer.CustomerModel;
import kino.kinobackend.customer.CustomerRepository;
import kino.kinobackend.employee.EmployeeModel;
import kino.kinobackend.employee.EmployeeRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


/*
    Denne her klasse håndterer login og registrering af brugere (både kunder og medarbejdere).

    Spring Security's "AuthenticationManager" bruges til at validere loginforsøg.
 */

@CrossOrigin("*")
@RestController
public class LoginController {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final CustomerRepository customerRepository;
    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;


    public LoginController(AuthenticationManager authenticationManager, UserDetailsService userDetailsService, CustomerRepository customerRepository, EmployeeRepository employeeRepository, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.customerRepository = customerRepository;
        this.employeeRepository = employeeRepository;
        this.passwordEncoder = passwordEncoder;
    }


    // Opret dig som kunde
    @PostMapping("/registerCustomer")
    public ResponseEntity<?> registerCustomer(@RequestBody RegisterRequest registerRequest) {
        if (customerRepository.findByUsername(registerRequest.getUsername()).isPresent()) {
            return ResponseEntity.status(409).body("Bruger findes allerede");
        }

        CustomerModel newCustomer = new CustomerModel();
        newCustomer.setUsername(registerRequest.getUsername());
        newCustomer.setPassword(passwordEncoder.encode(registerRequest.getPassword())); // her bliver koden hashet så man ikke kan lure den i db.....
        customerRepository.save(newCustomer);

        return ResponseEntity.status(201).body(newCustomer);
    }

    // opret en ny medarbejder ---- der skal laves dropdown med role
    @PostMapping("/registerEmployee")
    public ResponseEntity<?> registerEmployee(@RequestBody RegisterRequest registerRequest) {
        if (employeeRepository.findByUsername(registerRequest.getUsername()).isPresent()) {
            return ResponseEntity.status(409).body("Medarbejderen findes allerede");
        }

        EmployeeModel employee = new EmployeeModel();
        employee.setUsername(registerRequest.getUsername());
        employee.setPassword(passwordEncoder.encode(registerRequest.getPassword())); // her bliver koden hashet så man ikke kan lure den i db.....
        employee.setRole(registerRequest.getRole());
        employeeRepository.save(employee);

        return ResponseEntity.status(201).body(employee);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            // findes der en kunde med den email?
            var customer = customerRepository.findByUsername(loginRequest.getUsername());
            if (customer.isPresent()) {
                var userDetails = userDetailsService.loadUserByUsername(loginRequest.getUsername());
                authenticateUser(loginRequest, userDetails);
                return ResponseEntity.status(HttpStatus.OK).body(customer);
            }

            // hvis ikke kunden findes, så leder den efter en medarbejder
            var employee = employeeRepository.findByUsername(loginRequest.getUsername());
            if (employee.isPresent()) {
                var userDetails = userDetailsService.loadUserByUsername(loginRequest.getUsername());
                authenticateUser(loginRequest, userDetails);
                return ResponseEntity.status(HttpStatus.OK).body(employee);
            }

            // hvis ingen af dem findes, returneres en 401 Unauthorized........
            return ResponseEntity.status(401).body("Forkert brugernavn eller kodeord");

    } catch (Exception e) {
        return ResponseEntity.status(401).body("Authentication failed");
    }
    }

    // en hjælper-metode så vi undgår redundans ovenfor
    private ResponseEntity<?> authenticateUser(LoginRequest loginRequest, UserDetails userDetails) {
        // Hvis brugeren findes, prøv at autentificere
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userDetails.getUsername(), loginRequest.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return ResponseEntity.ok().build();
    }



    @Setter
    @Getter
    public static class RegisterRequest {
        private String username; // kunderne bruger email...
        private String password;
        private String role; // kun til medarbejdere
    }

    @Setter
    @Getter
    public static class LoginRequest {
        private String username; // kunderne bruger email...
        private String password;
    }
}