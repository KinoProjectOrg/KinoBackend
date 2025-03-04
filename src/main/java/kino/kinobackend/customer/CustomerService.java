package kino.kinobackend.customer;


import java.util.List;

public interface CustomerService {
    List<CustomerModel> getAllCustomers();
    CustomerModel getCustomerById(long id);
    CustomerModel createCustomer(CustomerModel customer);
    CustomerModel updateCustomer(CustomerModel customer);
    void deleteCustomer(long id);
}
