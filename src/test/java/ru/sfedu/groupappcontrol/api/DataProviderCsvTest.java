package ru.sfedu.groupappcontrol.api;

import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.sfedu.groupappcontrol.models.Employee;
import ru.sfedu.groupappcontrol.models.Task;
import ru.sfedu.groupappcontrol.models.TestersTask;
import ru.sfedu.groupappcontrol.models.enums.TaskTypes;
import ru.sfedu.groupappcontrol.models.enums.TypeOfCompletion;
import ru.sfedu.groupappcontrol.models.enums.TypeOfEmployee;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DataProviderCsvTest extends TestEmployee {
    private Logger log = LogManager.getLogger(DataProviderCsvTest.class);
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
    @Test
    public void changeProfileInfoTest() throws IOException {
        List<Employee> employeeList=new ArrayList<>();
        DataProviderCsv instance = new DataProviderCsv();
        Employee employee1= createUser(1,"Employee1","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
        Employee employee2= createUser(1,"Employee2","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
        employeeList.add(employee1);
        instance.insert(Employee.class,employeeList,true);
        instance.changeProfileInfo(employee2);
        assertEquals(employee2, instance.getByID(Employee.class,1).getData());
    }
    @Test
    public void changeTaskStatusTest() throws IOException {
        DataProviderCsv instance = new DataProviderCsv();
        List<Task> taskList=new ArrayList<>();
        List<Employee> employeeList=new ArrayList<>();
        Employee employee1= createUser(1,"Employee1","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
        Employee employee2= createUser(2,"Employee2","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
        //Employee employee3= createUser(3,"Employee2","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
        employeeList.add(employee1);
        employeeList.add(employee2);
        //employeeList.add(employee3);
        Task task1 = createTask(1, "Desc",1000.0, employee1, TypeOfCompletion.DEVELOPING, employeeList,"30-11-20","5-12-20","30-11-20", TaskTypes.BASE_TASK);
        Task task2 = createTask(2, "Desc_1",2000.0, employee1, TypeOfCompletion.DEVELOPING, employeeList,"30-11-20","5-12-20","30-11-20", TaskTypes.BASE_TASK);
        taskList.add(task1);
        log.error(task1.getId());
        taskList.add(task2);
        log.error(task2.getId());
        instance.insert(Task.class,taskList,true);
        instance.changeTaskStatus(1,"TESTING");
        //assertEquals(task2, instance.getByID(Employee.class,1).getData());
    }
    @Test
    public void writeComment() throws IOException {
        DataProviderCsv instance = new DataProviderCsv();
        List<Task> taskList=new ArrayList<>();
        List<Employee> employeeList=new ArrayList<>();
        Employee employee1= createUser(1,"Employee1","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
        Employee employee2= createUser(2,"Employee2","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
        //Employee employee3= createUser(3,"Employee2","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
        employeeList.add(employee1);
        employeeList.add(employee2);
        //employeeList.add(employee3);
        Task task1 = createTask(1, "Desc",1000.0, employee1, TypeOfCompletion.DEVELOPING, employeeList,"30-11-20","5-12-20","30-11-20", TaskTypes.BASE_TASK);
        Task task2 = createTask(2, "Desc_1",2000.0, employee1, TypeOfCompletion.DEVELOPING, employeeList,"30-11-20","5-12-20","30-11-20", TaskTypes.BASE_TASK);
        taskList.add(task1);
        taskList.add(task2);
        instance.insert(Task.class,taskList,true);
        instance.writeComment(1,"i am custom comment");
        //assertEquals(task2, instance.getByID(Employee.class,1).getData());

    }

}