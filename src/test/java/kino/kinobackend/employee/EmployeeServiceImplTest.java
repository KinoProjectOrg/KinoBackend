package kino.kinobackend.employee;

import kino.kinobackend.reservation.ReservationModel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {

    @Mock
    EmployeeRepository employeeRepository;

    @InjectMocks
    EmployeeServiceImpl employeeService;

    private EmployeeModel employee;
    @BeforeEach
    void setUp() {
        employee = new EmployeeModel();
        employee.setEmployeeId(1);
        employee.setUsername("Hanibal123@gmail.com");
        employee.setPassword("duKnækkerAldrigDenHer");
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void createNewEmployee() {

        Mockito.when(employeeRepository.save(Mockito.any(EmployeeModel.class))).thenReturn(employee);

        EmployeeModel employeeModel = employeeService.createNewEmployee(employee);

        assertEquals(employeeModel, employee);
        Mockito.verify(employeeRepository).save(Mockito.any(EmployeeModel.class));

    }

    @Test
    void getAllEmployees() {
        List<EmployeeModel> employees = new ArrayList<>();
        employees.add(employee);

        // mocks the database
        Mockito.when(employeeRepository.findAll()).thenReturn(employees);

        List<EmployeeModel> actualEmployees = employeeService.getAllEmployees();

        assertEquals(employees, actualEmployees);
        assertEquals(employee, actualEmployees.get(0));

    }

    @Test
    void getEmployeeById() {

        int employeeId = employee.getEmployeeId();

        Mockito.when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));

        Optional<EmployeeModel> employeeModel = employeeService.getEmployeeById(employeeId);

        assertTrue(employeeModel.isPresent()); // because getEmployeeById returns optional
        assertEquals(employee, employeeModel.get()); // get() udtrækker objektet fra Optional

    }

    @Test
    void updateEmployee() {


        Mockito.when(employeeRepository.existsById(1)).thenReturn(true);
        Mockito.when(employeeRepository.save(Mockito.any(EmployeeModel.class))).thenReturn(employee);

        EmployeeModel employeeModel = employeeService.updateEmployee(1, employee);

        assertEquals(employee, employeeModel);

    }

    @Test
    void deleteEmployee() {

        int employeeId = employee.getEmployeeId();

        Mockito.doNothing().when(employeeRepository).deleteById(employeeId);

        employeeService.deleteEmployee(employeeId);

        Mockito.verify(employeeRepository).deleteById(employeeId);
    }

    @Test
    void getEmployeeByName() {

        String employeeName = employee.getUsername();

        Mockito.when(employeeRepository.findByUsername(employeeName)).thenReturn(Optional.ofNullable(employee));

        Optional<EmployeeModel> employeeModel = employeeService.getEmployeeByName(employeeName);

        assertTrue(employeeModel.isPresent());
        assertEquals(employee, employeeModel.get());

    }
}