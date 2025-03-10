package kino.kinobackend.customer;


import java.util.List;
import java.util.Optional;

public interface CustomerService {
    List<CustomerModel> getAllCustomers();
    CustomerModel getCustomerById(long id);
    CustomerModel createCustomer(CustomerModel customer);
    CustomerModel updateCustomer(CustomerModel customer);
    void deleteCustomer(long id);

    Optional<CustomerModel> findByEmail(String email);
}
