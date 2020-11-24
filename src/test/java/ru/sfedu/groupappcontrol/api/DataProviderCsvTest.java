package ru.sfedu.groupappcontrol.api;

import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.sfedu.groupappcontrol.models.Employee;
import ru.sfedu.groupappcontrol.models.enums.TypeOfEmployee;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DataProviderCsvTest extends TestEmployee {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    public void insertEmployeeSuccess() throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        System.out.println("Insert employee success");
        List<Employee> employeeList = new ArrayList<>();
        DataProviderCsv instance = new DataProviderCsv();
        Employee employee1 = createUser(1,"Employee1","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
        Employee employee2 = createUser(2,"Employee2","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
        Employee employee3 = createUser(3,"Employee3","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
        Employee employee4 = createUser(4,"Employee4","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
        Employee employee5 = createUser(5,"Employee5","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
        Employee employee6 = createUser(6,"Employee6","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
        employeeList.add(employee1);
        employeeList.add(employee2);
        employeeList.add(employee3);
        employeeList.add(employee4);
        employeeList.add(employee5);
        employeeList.add(employee6);
        instance.insert(Employee.class,employeeList,false);
        System.out.println(instance.select(Employee.class));
        assertEquals(employee1, instance.getByID(Employee.class,1).getData());
    }
    @Test
    public void insertEmployeeFail() throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        System.out.println("Insert employee fail");
        List<Employee> employeeList=new ArrayList<>();
        DataProviderCsv instance = new DataProviderCsv();
        Employee employee1= createUser(1,"Employee1","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
        Employee employee2= createUser(2,"Employee2","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
        Employee employee3= createUser(3,"Employee3","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
        Employee employee4= createUser(4,"Employee4","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
        Employee employee5= createUser(5,"Employee5","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
        Employee employee6= createUser(6,"Employee6","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
        employeeList.add(employee1);
        employeeList.add(employee2);
        employeeList.add(employee3);
        employeeList.add(employee4);
        employeeList.add(employee5);
        employeeList.add(employee6);
        instance.insert(Employee.class,employeeList,true);
        assertNotEquals(null, instance.getByID(Employee.class,1).getData());
    }
}