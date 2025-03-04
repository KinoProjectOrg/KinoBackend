package kino.kinobackend.customer;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public List<CustomerModel> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public CustomerModel getCustomerById(long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Customer with id " + id + " not found"));
    }

    @Override
    public CustomerModel createCustomer(CustomerModel customer) {
        return customerRepository.save(customer);
    }

    @Override
    public CustomerModel updateCustomer(CustomerModel customer) {
        return customerRepository.save(customer);
    }

    @Override
    public void deleteCustomer(long id) {
        customerRepository.deleteById(id);
    }
}
