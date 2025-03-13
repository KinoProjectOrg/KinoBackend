package kino.kinobackend.employee;

import com.fasterxml.jackson.databind.ObjectMapper;
import kino.kinobackend.KinoBackendApplication;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = KinoBackendApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
@Transactional
class EmployeeRestControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EmployeeRepository employeeRepository;

    private EmployeeModel employee1;
    private EmployeeModel employee2;

    @BeforeEach
    void setUp() {
        employeeRepository.deleteAll();

        employee1 = new EmployeeModel();
        employee1.setUsername("Hans");
        employee1.setPassword("Hans123");
        employeeRepository.save(employee1);

        employee2 = new EmployeeModel();
        employee2.setUsername("Ole");
        employee2.setPassword("ole1234");
        employeeRepository.save(employee2);

    }

    @Test
    void createNewEmployeeIntegration() throws Exception{

        EmployeeModel employee = new EmployeeModel();
        employee.setUsername("Jakob");
        employee.setPassword("Jakob123");

        // converts to a json string
        ObjectMapper objectMapper = new ObjectMapper();
        String newEmployee = objectMapper.writeValueAsString(employee);

        mockMvc.perform(post("/employee/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newEmployee))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value("Jakonb"));

    }

    @Test
    void getAllEmployeesIntegration() throws Exception{
        mockMvc.perform(get("/employee/get")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$.[0].username").value(employee1.getUsername()))
                .andExpect(jsonPath("$.[1].password").value(employee2.getPassword()));
    }

    @Test
    void getEmployeeByIdIntegration() throws Exception{
        mockMvc.perform(get("/employee/get/" + employee1.getEmployeeId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$.username").value(employee1.getUsername()));
    }

    @Test
    void getEmployeeByNameIntegration() throws Exception{
        mockMvc.perform(get("/employee/get/name/" + employee1.getUsername())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$.username").value(employee1.getUsername()));
    }

    @Test
    void updateEmployeeIntegration() throws Exception {
        EmployeeModel employeeUpdated = new EmployeeModel();
        employeeUpdated.setEmployeeId(employee1.getEmployeeId());
        employeeUpdated.setUsername("Toby");
        employeeUpdated.setPassword("LangeLand");

        ObjectMapper objectMapper = new ObjectMapper();
        String newEmployee = objectMapper.writeValueAsString(employeeUpdated);

        mockMvc.perform(put("/employee/update/" + employeeUpdated.getEmployeeId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(newEmployee))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("Toby"));

    }

    @Test
    void deleteEmployeeIntegration() throws Exception{
        mockMvc.perform(delete("/employee/delete/" + employee2.getEmployeeId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

                assertEquals(1, employeeRepository.findAll().size());
                assertFalse(employeeRepository.existsById(employee2.getEmployeeId()));

    }
}