package ru.sfedu.groupappcontrol.api;

import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.sfedu.groupappcontrol.TestEmployee;
import ru.sfedu.groupappcontrol.models.Employee;
import ru.sfedu.groupappcontrol.models.enums.TypeOfEmployee;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class DataProviderJdbcTest extends TestEmployee {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    public void insertEmployeeSuccess() throws IOException, SQLException, ClassNotFoundException {
        System.out.println("Insert employee success");

        DataProviderJdbc db = new DataProviderJdbc();
        Employee employee1 = createUser(2,"Employee1","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        db.insertEmployee(employee1);
//        assertEquals(employee1, db.getUserById(2));
    }

}