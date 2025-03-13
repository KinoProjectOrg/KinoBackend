package kino.kinobackend.customer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@TestPropertySource(locations = "classpath:application-test.properties")
@Transactional
public class CustomerRestControlerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private CustomerModel customer1;
    private CustomerModel customer2;

    @BeforeEach
    public void setup() {
        // Clear any existing data
        customerRepository.deleteAll();

        // Create test customers
        customer1 = new CustomerModel();
        customer1.setUsername("mail@mail.com");
        customer1.setPassword("123");
        customer1 = customerRepository.save(customer1);

        customer2 = new CustomerModel();
        customer2.setUsername("mail2@mail.com");
        customer2.setPassword("321");
        customer2 = customerRepository.save(customer2);
    }

    @Test
    public void getAllCustomersIntegrationTest() throws Exception {
        mockMvc.perform(get("/customer/get")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].username").value(customer1.getUsername()))
                .andExpect(jsonPath("$[1].username").value(customer2.getUsername()));
    }

    @Test
    public void getCustomerIntegrationTest() throws Exception {
        mockMvc.perform(get("/customer/get/" + customer1.getCustomerId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value(customer1.getUsername()));
    }

    @Test
    public void createCustomerIntegrationTest() throws Exception {
        CustomerModel newCustomer = new CustomerModel();
        newCustomer.setUsername("new@mail.com");
        newCustomer.setPassword("newpass");

        mockMvc.perform(post("/customer/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newCustomer)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value(newCustomer.getUsername()));

        assertEquals(3, customerRepository.findAll().size());
    }

    @Test
    public void updateCustomerIntegrationTest() throws Exception {
        CustomerModel updatedCustomer = new CustomerModel();
        updatedCustomer.setCustomerId(customer2.getCustomerId());
        updatedCustomer.setUsername("updated@mail.com");
        updatedCustomer.setPassword("updatedpass");

        mockMvc.perform(put("/customer/update/" + customer2.getCustomerId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedCustomer)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("updated@mail.com"));
    }

    @Test
    public void deleteCustomerIntegrationTest() throws Exception {
        mockMvc.perform(delete("/customer/delete/" + customer1.getCustomerId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        assertEquals(1, customerRepository.findAll().size());
        assertFalse(customerRepository.existsById(customer1.getCustomerId()));
    }

    @Test
    public void getNonExistentCustomerIntegrationTest() throws Exception {
        mockMvc.perform(get("/customer/get/999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
