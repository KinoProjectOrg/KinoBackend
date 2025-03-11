package kino.kinobackend.customer;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<CustomerModel, Long> {


    Optional<CustomerModel> findByUsername(String username);

}
