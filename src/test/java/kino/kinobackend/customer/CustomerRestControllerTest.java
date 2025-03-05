package kino.kinobackend.customer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(CustomerRestController.class)
class CustomerRestControllerTest {

    @MockitoBean
    private CustomerService customerService;

    @Autowired
    private MockMvc mockMvc;

    private CustomerModel customer;
    @BeforeEach
    void setUp() {
        customer = new CustomerModel();
        customer.setCustomerId(1);
    }

    @Test
    void getAllCustomers() throws Exception {
        List<CustomerModel> customers = new ArrayList<>();
        customers.add(customer);

        Mockito.when(customerService.getAllCustomers()).thenReturn(customers);

        mockMvc.perform(get("/customer/get")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void getCustomer() throws Exception {

        Mockito.when(customerService.getCustomerById(Mockito.anyLong())).thenReturn(customer);

        mockMvc.perform(get("/customer/get/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerId").value(1));
    }

    @Test
    void createCustomer() throws Exception {

        Mockito.when(customerService.createCustomer(Mockito.any(CustomerModel.class))).thenReturn(customer);

        ObjectMapper mapper = new ObjectMapper();
        String createdCustomer = mapper.writeValueAsString(customer);

        mockMvc.perform(post("/customer/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createdCustomer))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.customerId").value(1));
    }

    @Test
    void updateCustomer() throws Exception {

        Mockito.when(customerService.updateCustomer(Mockito.any(CustomerModel.class))).thenReturn(customer);

        ObjectMapper mapper = new ObjectMapper();
        String updatedCustomer = mapper.writeValueAsString(customer);

        mockMvc.perform(put("/customer/update/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(updatedCustomer))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerId").value(1));
    }

    @Test
    void deleteCustomer() throws Exception {

        Mockito.doNothing().when(customerService).deleteCustomer(Mockito.anyLong());

        mockMvc.perform(delete("/customer/delete/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}