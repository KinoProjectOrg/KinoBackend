package kino.kinobackend.customer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {

    @Mock
    CustomerRepository customerRepository;

    @InjectMocks
    CustomerServiceImpl customerService;

    CustomerModel customerModel;

    @BeforeEach
    void setUp() {
        customerModel = new CustomerModel();
        customerModel.setCustomerId(1);
    }

    @Test
    void getAllCustomers() {
        List<CustomerModel> customers = customerService.getAllCustomers();
        customers.add(customerModel);

        Mockito.when(customerRepository.findAll())
                .thenReturn(customers);

        List<CustomerModel> result = customerService.getAllCustomers();

        assertEquals(customers, result);
        assertEquals(customerModel, result.get(0));
    }

    @Test
    void getCustomerById() {

        Mockito.when(customerRepository.findById(1L)).thenReturn(Optional.of(customerModel));

        CustomerModel result = customerService.getCustomerById(1);

        assertEquals(customerModel, result);
    }

    @Test
    void createCustomer() {

        Mockito.when(customerRepository.save(customerModel)).thenReturn(customerModel);

        CustomerModel result = customerService.createCustomer(customerModel);

        assertEquals(customerModel, result);
        assertEquals(customerModel.getCustomerId(), result.getCustomerId());
    }

    @Test
    void updateCustomer() {

        Mockito.when(customerRepository.save(customerModel)).thenReturn(customerModel);

        CustomerModel result = customerService.updateCustomer(customerModel);

        assertEquals(customerModel, result);
    }

    @Test
    void deleteCustomer() {
        Mockito.doNothing().when(customerRepository).deleteById(1L);

        customerService.deleteCustomer(1L);

        Mockito.verify(customerRepository, Mockito.times(1)).deleteById(1L);

    }
}