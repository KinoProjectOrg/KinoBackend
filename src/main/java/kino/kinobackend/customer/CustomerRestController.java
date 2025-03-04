package kino.kinobackend.customer;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerRestController {

    private final CustomerService customerService;

    public CustomerRestController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/get")
    public List<CustomerModel> getAllCustomers(){
        return customerService.getAllCustomers();
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<CustomerModel> getCustomer(@PathVariable long id){
        CustomerModel foundCustomer = customerService.getCustomerById(id);
        return ResponseEntity.status(HttpStatus.OK).body(foundCustomer);
    }

    @PostMapping("/create")
    public ResponseEntity<CustomerModel> createCustomer(@RequestBody CustomerModel customer){
        CustomerModel createdCustomer = customerService.createCustomer(customer);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCustomer);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<CustomerModel> updateCustomer(@PathVariable long id, @RequestBody CustomerModel customer){
        customer.setCustomerId(id);
        CustomerModel updatedCustomer = customerService.updateCustomer(customer);
        return ResponseEntity.status(HttpStatus.OK).body(updatedCustomer);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<CustomerModel> deleteCustomer(@PathVariable long id){
        try {
            customerService.deleteCustomer(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
