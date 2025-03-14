package kino.kinobackend.employee;

import com.fasterxml.jackson.databind.ObjectMapper;
import kino.kinobackend.showing.ShowingRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(EmployeeRestController.class)
@AutoConfigureMockMvc(addFilters = false)
class EmployeeRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private EmployeeRepository employeeRepository;

    @MockitoBean
    private EmployeeService employeeService;

    @MockitoBean
    private ShowingRepository showingRepository;

    private EmployeeModel employee;
    @BeforeEach
    void setUp() {
        employee = new EmployeeModel();
        employee.setEmployeeId(1);
        employee.setUsername("og@gmail.com");
        employee.setPassword("detHerErETPassword");
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void createNewEmployee() throws  Exception{

        // simulates the creation of an employee with our employee mock up
        Mockito.when(employeeService.createNewEmployee(Mockito.any(EmployeeModel.class))).thenReturn(employee);

        ObjectMapper objectMapper = new ObjectMapper();
        String newEmployee = objectMapper.writeValueAsString(employee);

        mockMvc.perform(post("/employee/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newEmployee))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value("og@gmail.com"));
                 // andExpect(jsonPath("$.employeeId").value(1));
    }

    @Test
    void getAllEmployees() throws Exception {
        List<EmployeeModel> employees = new ArrayList<>();
        employees.add(employee);

        // mocking the service layer with getAllEmployees()
        Mockito.when(employeeService.getAllEmployees()).thenReturn(employees);

        mockMvc.perform(get("/employee/get")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void getEmployeeById() throws Exception{

        int employeeId = employee.getEmployeeId();

        Mockito.when(employeeService.getEmployeeById(Mockito.anyInt())).thenReturn(Optional.ofNullable(employee));

        mockMvc.perform(get("/employee/get/"+employeeId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.employeeId").value(1));
    }

    @Test
    void getEmployeeByName() throws Exception{

        String employeeName = employee.getUsername();

        Mockito.when(employeeService.getEmployeeByName(employeeName)).thenReturn(Optional.ofNullable(employee));

        mockMvc.perform(get("/employee/get/name/" + employeeName)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.employeeId").value(1));

    }

    @Test
    void updateEmployee() throws Exception{

        int employeeId = employee.getEmployeeId();

        Mockito.when(employeeService.updateEmployee(Mockito.anyInt(), Mockito.any(EmployeeModel.class))).thenReturn(employee);

        // convert the Java object to JSON
        ObjectMapper mapper = new ObjectMapper();
        String updatedEmployee= mapper.writeValueAsString(employee);

        mockMvc.perform(put("/employee/update/" + employeeId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(updatedEmployee))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.employeeId").value(1));
    }

    @Test
    void deleteEmployee() throws Exception {

        int employeeNumber = employee.getEmployeeId();

        Mockito.doNothing().when(employeeService).deleteEmployee(Mockito.anyInt());

        mockMvc.perform(delete("/employee/delete/" + employeeNumber)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

    }
}