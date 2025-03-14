package kino.kinobackend.employee;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {


    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public EmployeeModel createNewEmployee(EmployeeModel employee){
        return employeeRepository.save(employee);
    }

    @Override
    public List<EmployeeModel> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public Optional<EmployeeModel> getEmployeeById(int id) {
        return employeeRepository.findById(id);
    }

    @Override
    public EmployeeModel updateEmployee(int employeeID, EmployeeModel updatedEmployee) {
        if (employeeRepository.existsById(employeeID)) {
            updatedEmployee.setEmployeeId(employeeID);
            return employeeRepository.save(updatedEmployee);
        }
        throw new RuntimeException("Error.. Could not find employee with ID: " + employeeID);
    }

    @Override
    public void deleteEmployee(int employeeID) {
        employeeRepository.deleteById(employeeID);
    }

    @Override
    public Optional<EmployeeModel> getEmployeeByName(String username) {
        return employeeRepository.findByUsername(username);
    }
}
