package kino.kinobackend.employee;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface EmployeeService {

    EmployeeModel createNewEmployee(EmployeeModel employee);

    List<EmployeeModel> getAllEmployees();

    Optional<EmployeeModel> getEmployeeById(int employeeID);

    EmployeeModel updateEmployee(int employeeID, EmployeeModel employee);

    void deleteEmployee(int employeeID);
}
