package kino.kinobackend.employee;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/employee")
public class EmployeeRestController {

    private final EmployeeService employeeService;
    EmployeeRepository employeeRepository;

    public EmployeeRestController(EmployeeRepository employeeRepository, EmployeeService employeeService) {
        this.employeeRepository = employeeRepository;
        this.employeeService = employeeService;
    }


    @PostMapping("/create")
    public ResponseEntity<EmployeeModel> createNewEmployee(@RequestBody EmployeeModel employee) {
        EmployeeModel createdEmployee = employeeService.createNewEmployee(employee);
        return ResponseEntity.ok(createdEmployee);
    }

    @GetMapping("/get")
    public ResponseEntity<List<EmployeeModel>> getAllEmployees(){
        List<EmployeeModel> employees = employeeService.getAllEmployees();
        if (employees.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<EmployeeModel> getEmployeeById(@PathVariable int id) {
        Optional<EmployeeModel> employee = employeeService.getEmployeeById(id);
        return employee.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<EmployeeModel> updateEmployee(@PathVariable int id,
                                                        @RequestBody EmployeeModel updatedEmployee) {
        try {
            EmployeeModel employee = employeeService.updateEmployee(id, updatedEmployee);
            return ResponseEntity.ok(employee);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<EmployeeModel> deleteEmployee(@PathVariable int id) {
        try {
            employeeService.deleteEmployee(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


}
