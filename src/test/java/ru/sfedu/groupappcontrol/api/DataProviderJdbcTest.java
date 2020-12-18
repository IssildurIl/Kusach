package ru.sfedu.groupappcontrol.api;

import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.sfedu.groupappcontrol.TestEmployee;
import ru.sfedu.groupappcontrol.models.Employee;
import ru.sfedu.groupappcontrol.models.enums.Outcomes;
import ru.sfedu.groupappcontrol.models.enums.TypeOfEmployee;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class DataProviderJdbcTest extends TestEmployee {
    private static final Logger log = LogManager.getLogger(DataProviderJdbcTest.class);
    DataProviderJdbc db = new DataProviderJdbc();
    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    public void insertEmployeeSuccess() throws IOException, SQLException, ClassNotFoundException {
        System.out.println("Insert employee success");
        Employee employee = new Employee();
//        Employee employee1 = new Employee();
//        Employee employee2 = new Employee();
        db.setBasicEmployeeParams(employee,"Employee3","Employee_sec_name",
                "Employee_Login","admin","employee@sfedu.ru","Employee_personal_token",
                "FullStack", TypeOfEmployee.Employee);
//        db.setBasicEmployeeParams(employee1,"Employee2","Employee_sec_name",
//                "Employee_Login","admin","employee@sfedu.ru","Employee_personal_token",
//                "FullStack", TypeOfEmployee.Developer);
//        db.setBasicEmployeeParams(employee2,"Employee3","Employee_sec_name",
//                "Employee_Login","admin","employee@sfedu.ru","Employee_personal_token",
//                "FullStack", TypeOfEmployee.Developer);
        List<Employee> list= new ArrayList<>();
        list.add(employee);
//        list.add(employee1);
//        list.add(employee2);
        Outcomes o = db.insertEmployee(list,true).getStatus();

        assertEquals(Outcomes.Complete,o);
    }

    @Test
    public void insertEmployee(){
        Employee employee = new Employee();
        db.setBasicEmployeeParams(employee,"Employee3","Employee_sec_name",
                "Employee_Login","admin","employee@sfedu.ru","Employee_personal_token",
                "FullStack", TypeOfEmployee.Employee);
        db.getEmployeeByParam(employee);
        log.info(employee.getId());
        //insertToMappingEmployee()
    }

}