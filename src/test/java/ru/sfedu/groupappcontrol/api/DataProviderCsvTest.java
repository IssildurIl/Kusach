package ru.sfedu.groupappcontrol.api;

import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.sfedu.groupappcontrol.models.*;
import ru.sfedu.groupappcontrol.models.constants.Constants;
import ru.sfedu.groupappcontrol.models.enums.*;

import static ru.sfedu.groupappcontrol.api.Fill.*;
import java.io.IOException;
import java.util.*;


class DataProviderCsvTest {
    DataProviderCsv instance = new DataProviderCsv();
    private Logger log = LogManager.getLogger(DataProviderCsvTest.class);

    DataProviderCsvTest() throws IOException {
    }

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }
    public void addRecord() throws IOException {
        List<Employee> employees = new ArrayList<>();
        List<Developer> developers = new ArrayList<>();
        List<Task> tasks = new ArrayList<>();
        List<Tester> testers = new ArrayList<>();
        List<DevelopersTask> developersTasks = new ArrayList<>();
        List<TestersTask> testersTasks = new ArrayList<>();
        List<Project> projects = new ArrayList<>();
        for (int i=1; i<=10; i++) {
            Employee employee = new Employee();
            employee.setId(i);
            employee.setFirstName(firstName[i-1]);
            employee.setLastName(lastName[i-1]);
            employee.setLogin(login[i-1]);
            employee.setPassword(password[i-1]);
            employee.setEmail(email[i-1]);
            employee.setToken(token[i-1]);
            employee.setDepartment(department[i-1]);
            employee.setTypeOfEmployee(TypeOfEmployee.Employee);
            employees.add(employee);
            instance.insert(Employee.class,employees,false);
        }
        for (int i=1; i<=10; i++) {
            Developer developer = new Developer();
            developer.setId(i);
            developer.setFirstName(firstName[i-1]);
            developer.setLastName(lastName[i-1]);
            developer.setLogin(login[i-1]);
            developer.setPassword(password[i-1]);
            developer.setEmail(email[i-1]);
            developer.setToken(token[i-1]);
            developer.setDepartment(department[i-1]);
            developer.setTypeOfEmployee(TypeOfEmployee.Developer);
            developer.setStatus(TypeOfDevelopers.CUSTOM);
            developer.setProgrammingLanguage(ProgrammingLanguage.Custom);
            developers.add(developer);
            instance.insert(Developer.class,developers,false);
        }
        for (int i=1; i<=10; i++) {
            Tester tester = new Tester();
            tester.setId(i);
            tester.setFirstName(firstName[i-1]);
            tester.setLastName(lastName[i-1]);
            tester.setLogin(login[i-1]);
            tester.setPassword(password[i-1]);
            tester.setEmail(email[i-1]);
            tester.setToken(token[i-1]);
            tester.setDepartment(department[i-1]);
            tester.setTypeOfEmployee(TypeOfEmployee.Tester);
            tester.setStatus(TypeOfDevelopers.CUSTOM);
            tester.setProgrammingLanguage(ProgrammingLanguage.Custom);
            tester.setTypeOfTester(TypeOfTester.Custom);
            testers.add(tester);
            instance.insert(Tester.class,testers,false);
        }
        for (int i=1; i<=10; i++) {
            Task task = new Task();
            task.setId(i);
            task.setTaskDescription(taskDescription[i-1]);
            task.setMoney(money[i-1]);
            task.setScrumMaster(getScrum());
            task.setStatus(TypeOfCompletion.CUSTOM);
            task.setTeam(getListEmployee());
            task.setCreatedDate(createdDate[i-1]);
            task.setDeadline(deadline[i-1]);
            task.setLastUpdate(lastUpdate[i-1]);
            task.setTaskType(TaskTypes.BASE_TASK);
            tasks.add(task);
            instance.insert(Task.class,tasks,false);
            }
        for (int i=1; i<=10; i++) {
            DevelopersTask developersTask = new DevelopersTask();
            developersTask.setId(i);
            developersTask.setTaskDescription(taskDescription[i-1]);
            developersTask.setMoney(money[i-1]);
            developersTask.setScrumMaster(getScrum());
            developersTask.setStatus(TypeOfCompletion.CUSTOM);
            developersTask.setTeam(getListEmployee());
            developersTask.setCreatedDate(createdDate[i-1]);
            developersTask.setDeadline(deadline[i-1]);
            developersTask.setLastUpdate(lastUpdate[i-1]);
            developersTask.setTaskType(TaskTypes.DEVELOPERS_TASK);
            developersTask.setDeveloperTaskType(DeveloperTaskType.DEVELOPMENT);
            developersTask.setDeveloperComments(Constants.BaseComment);
            developersTasks.add(developersTask);
            instance.insert(DevelopersTask.class,developersTasks,false);
        }
        for (int i=1; i<=10; i++) {
            TestersTask testersTask = new TestersTask();
            testersTask.setId(i);
            testersTask.setTaskDescription(taskDescription[i-1]);
            testersTask.setMoney(money[i-1]);
            testersTask.setScrumMaster(getScrum());
            testersTask.setStatus(TypeOfCompletion.CUSTOM);
            testersTask.setTeam(getListEmployee());
            testersTask.setCreatedDate(createdDate[i-1]);
            testersTask.setDeadline(deadline[i-1]);
            testersTask.setLastUpdate(lastUpdate[i-1]);
            testersTask.setTaskType(TaskTypes.TESTERS_TASK);
            testersTask.setBugStatus(BugStatus.IN_WORK);
            testersTask.setBugDescription(Constants.BaseComment);
            testersTasks.add(testersTask);
            instance.insert(TestersTask.class,testersTasks,false);
            //Assert.assertEquals(csv.addRecord(freights, Freight.class).getStatus(), COMPLETE);
        }
        for (int i=1; i<=4; i++) {
            Project project=new Project();
            project.setId(i);
            project.setTitle(title[i-1]);
            project.setTakeIntoDevelopment(createdDate[i-1]);
            project.setTask(getListTask());
            projects.add(project);
            instance.insert(Project.class,projects,false);
            //Assert.assertEquals(csv.addRecord(freights, Passenger.class).getStatus(), COMPLETE);
        }
    }
    private Employee getScrum(){
        List<Employee> listemployee = instance.select(Employee.class);
        int max=9; int min=0;
        Employee employee =listemployee.get((int) ((Math.random() * ((max - min) + 1)) + min));
        return employee;
    }
    private List<Employee> getListEmployee(){
        List<Employee> listemployee = instance.select(Employee.class);
        List<Developer> developers = instance.select(Developer.class);
        int max=9; int min=0;
        for (int i=1;i<=3; i++) {
            Developer developer = developers.get((int) ((Math.random() * ((max - min) + 1)) + min));
            listemployee.add(developer);
        }
        List<Tester> testers = instance.select(Tester.class);
        for (int i=1;i<=3; i++) {
            Tester tester = testers.get((int) ((Math.random() * ((max - min) + 1)) + min));
            listemployee.add(tester);
        }
        return listemployee;
    }
    private List<Task> getListTask(){
        List<Task> listTask = instance.select(Task.class);
        List<DevelopersTask> developers = instance.select(DevelopersTask.class);
        int max=9; int min=0;
        for (int i=1;i<=3; i++) {
            DevelopersTask developersTask = developers.get((int) ((Math.random() * ((max - min) + 1)) + min));
            listTask.add(developersTask);
        }
        List<TestersTask> testers = instance.select(TestersTask.class);
        for (int i=1;i<=3; i++) {
            TestersTask testersTask = testers.get((int) ((Math.random() * ((max - min) + 1)) + min));
            listTask.add(testersTask);
        }
        return listTask;
    }

    @Test
    public void changeProfileInfoSuccess() throws IOException {
        addRecord();
        List<Employee> employeeList = new ArrayList<>();
        DataProviderCsv instance = new DataProviderCsv();
        Employee employee6 = (Employee) instance.createEmployee(1,"Employee6","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer).getData();
        //instance.correctEmployeeParameters(employee6);
        //System.out.println(instance.select(Employee.class));
        //assertEquals(employee1, instance.getByID(Employee.class,1).getData());
    }
//    @Test
//    public void changeProfileInfoFail() throws IOException {
//        List<Employee> employeeList = new ArrayList<>();
//        DataProviderCsv instance = new DataProviderCsv();
//        Employee employee1 = createUser(1,"Employee1","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee2 = createUser(2,"Employee2","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee3 = createUser(3,"Employee3","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee4 = createUser(4,"Employee4","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee5 = createUser(5,"Employee5","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee6 = createUser(1,"Employee6","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        employeeList.add(employee1);
//        employeeList.add(employee2);
//        employeeList.add(employee3);
//        employeeList.add(employee4);
//        employeeList.add(employee5);
//        //employeeList.add(employee6);
//        instance.insert(Employee.class,employeeList,false);
//        instance.correctEmployeeParameters(employee6);
//        //System.out.println(instance.select(Employee.class));
//        //assertEquals(employee1, instance.getByID(Employee.class,1).getData());
//    }
//
//    @Test
//    public void changeTaskStatusSuccess() throws IOException {
//        List<Employee> employeeList = new ArrayList<>();
//        DataProviderCsv instance = new DataProviderCsv();
//        Employee employee1 = createUser(1,"Employee1","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee2 = createUser(2,"Employee2","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee3 = createUser(3,"Employee3","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee4 = createUser(4,"Employee4","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee5 = createUser(5,"Employee5","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee6 = createUser(1,"Employee6","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        employeeList.add(employee1);
//        employeeList.add(employee2);
//        employeeList.add(employee3);
//        employeeList.add(employee4);
//        employeeList.add(employee5);
//        //employeeList.add(employee6);
//        instance.insert(Employee.class,employeeList,false);
//        instance.correctEmployeeParameters(employee6);
//        //System.out.println(instance.select(Employee.class));
//        //assertEquals(employee1, instance.getByID(Employee.class,1).getData());
//    }
//    @Test
//    public void changeTaskStatusFail() throws IOException {
//        List<Employee> employeeList = new ArrayList<>();
//        DataProviderCsv instance = new DataProviderCsv();
//        Employee employee1 = createUser(1,"Employee1","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee2 = createUser(2,"Employee2","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee3 = createUser(3,"Employee3","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee4 = createUser(4,"Employee4","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee5 = createUser(5,"Employee5","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee6 = createUser(1,"Employee6","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        employeeList.add(employee1);
//        employeeList.add(employee2);
//        employeeList.add(employee3);
//        employeeList.add(employee4);
//        employeeList.add(employee5);
//        //employeeList.add(employee6);
//        instance.insert(Employee.class,employeeList,false);
//        instance.correctEmployeeParameters(employee6);
//        //System.out.println(instance.select(Employee.class));
//        //assertEquals(employee1, instance.getByID(Employee.class,1).getData());
//    }
//
//
//    @Test
//    public void writeCommentSuccess() throws IOException {
//        List<Employee> employeeList = new ArrayList<>();
//        DataProviderCsv instance = new DataProviderCsv();
//        Employee employee1 = createUser(1,"Employee1","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee2 = createUser(2,"Employee2","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee3 = createUser(3,"Employee3","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee4 = createUser(4,"Employee4","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee5 = createUser(5,"Employee5","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee6 = createUser(1,"Employee6","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        employeeList.add(employee1);
//        employeeList.add(employee2);
//        employeeList.add(employee3);
//        employeeList.add(employee4);
//        employeeList.add(employee5);
//        //employeeList.add(employee6);
//        instance.insert(Employee.class,employeeList,false);
//        instance.correctEmployeeParameters(employee6);
//        //System.out.println(instance.select(Employee.class));
//        //assertEquals(employee1, instance.getByID(Employee.class,1).getData());
//    }
//    @Test
//    public void writeCommentFail() throws IOException {
//        List<Employee> employeeList = new ArrayList<>();
//        DataProviderCsv instance = new DataProviderCsv();
//        Employee employee1 = createUser(1,"Employee1","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee2 = createUser(2,"Employee2","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee3 = createUser(3,"Employee3","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee4 = createUser(4,"Employee4","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee5 = createUser(5,"Employee5","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee6 = createUser(1,"Employee6","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        employeeList.add(employee1);
//        employeeList.add(employee2);
//        employeeList.add(employee3);
//        employeeList.add(employee4);
//        employeeList.add(employee5);
//        //employeeList.add(employee6);
//        instance.insert(Employee.class,employeeList,false);
//        instance.correctEmployeeParameters(employee6);
//        //System.out.println(instance.select(Employee.class));
//        //assertEquals(employee1, instance.getByID(Employee.class,1).getData());
//    }
//
//
//    @Test
//    public void getUserInfoListSuccess() throws IOException {
//        List<Employee> employeeList = new ArrayList<>();
//        DataProviderCsv instance = new DataProviderCsv();
//        Employee employee1 = createUser(1,"Employee1","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee2 = createUser(2,"Employee2","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee3 = createUser(3,"Employee3","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee4 = createUser(4,"Employee4","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee5 = createUser(5,"Employee5","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee6 = createUser(1,"Employee6","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        employeeList.add(employee1);
//        employeeList.add(employee2);
//        employeeList.add(employee3);
//        employeeList.add(employee4);
//        employeeList.add(employee5);
//        //employeeList.add(employee6);
//        instance.insert(Employee.class,employeeList,false);
//        instance.correctEmployeeParameters(employee6);
//        //System.out.println(instance.select(Employee.class));
//        //assertEquals(employee1, instance.getByID(Employee.class,1).getData());
//    }
//    @Test
//    public void getUserInfoListFail() throws IOException {
//        List<Employee> employeeList = new ArrayList<>();
//        DataProviderCsv instance = new DataProviderCsv();
//        Employee employee1 = createUser(1,"Employee1","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee2 = createUser(2,"Employee2","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee3 = createUser(3,"Employee3","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee4 = createUser(4,"Employee4","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee5 = createUser(5,"Employee5","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee6 = createUser(1,"Employee6","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        employeeList.add(employee1);
//        employeeList.add(employee2);
//        employeeList.add(employee3);
//        employeeList.add(employee4);
//        employeeList.add(employee5);
//        //employeeList.add(employee6);
//        instance.insert(Employee.class,employeeList,false);
//        instance.correctEmployeeParameters(employee6);
//        //System.out.println(instance.select(Employee.class));
//        //assertEquals(employee1, instance.getByID(Employee.class,1).getData());
//    }
//
//    @Test
//    public void getDevelopersListSuccess() throws IOException {
//        List<Employee> employeeList = new ArrayList<>();
//        DataProviderCsv instance = new DataProviderCsv();
//        Employee employee1 = createUser(1,"Employee1","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee2 = createUser(2,"Employee2","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee3 = createUser(3,"Employee3","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee4 = createUser(4,"Employee4","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee5 = createUser(5,"Employee5","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee6 = createUser(1,"Employee6","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        employeeList.add(employee1);
//        employeeList.add(employee2);
//        employeeList.add(employee3);
//        employeeList.add(employee4);
//        employeeList.add(employee5);
//        //employeeList.add(employee6);
//        instance.insert(Employee.class,employeeList,false);
//        instance.correctEmployeeParameters(employee6);
//        //System.out.println(instance.select(Employee.class));
//        //assertEquals(employee1, instance.getByID(Employee.class,1).getData());
//    }
//    @Test
//    public void getDevelopersListFail() throws IOException {
//        List<Employee> employeeList = new ArrayList<>();
//        DataProviderCsv instance = new DataProviderCsv();
//        Employee employee1 = createUser(1,"Employee1","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee2 = createUser(2,"Employee2","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee3 = createUser(3,"Employee3","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee4 = createUser(4,"Employee4","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee5 = createUser(5,"Employee5","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee6 = createUser(1,"Employee6","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        employeeList.add(employee1);
//        employeeList.add(employee2);
//        employeeList.add(employee3);
//        employeeList.add(employee4);
//        employeeList.add(employee5);
//        //employeeList.add(employee6);
//        instance.insert(Employee.class,employeeList,false);
//        instance.correctEmployeeParameters(employee6);
//        //System.out.println(instance.select(Employee.class));
//        //assertEquals(employee1, instance.getByID(Employee.class,1).getData());
//    }
//
//    @Test
//    public void getTestersListSuccess() throws IOException {
//        List<Employee> employeeList = new ArrayList<>();
//        DataProviderCsv instance = new DataProviderCsv();
//        Employee employee1 = createUser(1,"Employee1","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee2 = createUser(2,"Employee2","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee3 = createUser(3,"Employee3","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee4 = createUser(4,"Employee4","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee5 = createUser(5,"Employee5","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee6 = createUser(1,"Employee6","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        employeeList.add(employee1);
//        employeeList.add(employee2);
//        employeeList.add(employee3);
//        employeeList.add(employee4);
//        employeeList.add(employee5);
//        //employeeList.add(employee6);
//        instance.insert(Employee.class,employeeList,false);
//        instance.correctEmployeeParameters(employee6);
//        //System.out.println(instance.select(Employee.class));
//        //assertEquals(employee1, instance.getByID(Employee.class,1).getData());
//    }
//    @Test
//    public void getTestersListFail() throws IOException {
//        List<Employee> employeeList = new ArrayList<>();
//        DataProviderCsv instance = new DataProviderCsv();
//        Employee employee1 = createUser(1,"Employee1","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee2 = createUser(2,"Employee2","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee3 = createUser(3,"Employee3","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee4 = createUser(4,"Employee4","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee5 = createUser(5,"Employee5","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee6 = createUser(1,"Employee6","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        employeeList.add(employee1);
//        employeeList.add(employee2);
//        employeeList.add(employee3);
//        employeeList.add(employee4);
//        employeeList.add(employee5);
//        //employeeList.add(employee6);
//        instance.insert(Employee.class,employeeList,false);
//        instance.correctEmployeeParameters(employee6);
//        //System.out.println(instance.select(Employee.class));
//        //assertEquals(employee1, instance.getByID(Employee.class,1).getData());
//    }
//
//    @Test
//    public void getBaseTaskListSuccess() throws IOException {
//        List<Employee> employeeList = new ArrayList<>();
//        DataProviderCsv instance = new DataProviderCsv();
//        Employee employee1 = createUser(1,"Employee1","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee2 = createUser(2,"Employee2","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee3 = createUser(3,"Employee3","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee4 = createUser(4,"Employee4","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee5 = createUser(5,"Employee5","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee6 = createUser(1,"Employee6","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        employeeList.add(employee1);
//        employeeList.add(employee2);
//        employeeList.add(employee3);
//        employeeList.add(employee4);
//        employeeList.add(employee5);
//        //employeeList.add(employee6);
//        instance.insert(Employee.class,employeeList,false);
//        instance.correctEmployeeParameters(employee6);
//        //System.out.println(instance.select(Employee.class));
//        //assertEquals(employee1, instance.getByID(Employee.class,1).getData());
//    }
//    @Test
//    public void getBaseTaskListFail() throws IOException {
//        List<Employee> employeeList = new ArrayList<>();
//        DataProviderCsv instance = new DataProviderCsv();
//        Employee employee1 = createUser(1,"Employee1","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee2 = createUser(2,"Employee2","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee3 = createUser(3,"Employee3","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee4 = createUser(4,"Employee4","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee5 = createUser(5,"Employee5","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee6 = createUser(1,"Employee6","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        employeeList.add(employee1);
//        employeeList.add(employee2);
//        employeeList.add(employee3);
//        employeeList.add(employee4);
//        employeeList.add(employee5);
//        //employeeList.add(employee6);
//        instance.insert(Employee.class,employeeList,false);
//        instance.correctEmployeeParameters(employee6);
//        //System.out.println(instance.select(Employee.class));
//        //assertEquals(employee1, instance.getByID(Employee.class,1).getData());
//    }
//
//    @Test
//    public void getTaskInfoSuccess() throws IOException {
//        List<Employee> employeeList = new ArrayList<>();
//        DataProviderCsv instance = new DataProviderCsv();
//        Employee employee1 = createUser(1,"Employee1","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee2 = createUser(2,"Employee2","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee3 = createUser(3,"Employee3","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee4 = createUser(4,"Employee4","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee5 = createUser(5,"Employee5","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee6 = createUser(1,"Employee6","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        employeeList.add(employee1);
//        employeeList.add(employee2);
//        employeeList.add(employee3);
//        employeeList.add(employee4);
//        employeeList.add(employee5);
//        //employeeList.add(employee6);
//        instance.insert(Employee.class,employeeList,false);
//        instance.correctEmployeeParameters(employee6);
//        //System.out.println(instance.select(Employee.class));
//        //assertEquals(employee1, instance.getByID(Employee.class,1).getData());
//    }
//    @Test
//    public void getTaskInfoFail() throws IOException {
//        List<Employee> employeeList = new ArrayList<>();
//        DataProviderCsv instance = new DataProviderCsv();
//        Employee employee1 = createUser(1,"Employee1","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee2 = createUser(2,"Employee2","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee3 = createUser(3,"Employee3","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee4 = createUser(4,"Employee4","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee5 = createUser(5,"Employee5","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee6 = createUser(1,"Employee6","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        employeeList.add(employee1);
//        employeeList.add(employee2);
//        employeeList.add(employee3);
//        employeeList.add(employee4);
//        employeeList.add(employee5);
//        //employeeList.add(employee6);
//        instance.insert(Employee.class,employeeList,false);
//        instance.correctEmployeeParameters(employee6);
//        //System.out.println(instance.select(Employee.class));
//        //assertEquals(employee1, instance.getByID(Employee.class,1).getData());
//    }
//
//    @Test
//    public void getTaskSuccess() throws IOException {
//        List<Employee> employeeList = new ArrayList<>();
//        DataProviderCsv instance = new DataProviderCsv();
//        Employee employee1 = createUser(1,"Employee1","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee2 = createUser(2,"Employee2","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee3 = createUser(3,"Employee3","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee4 = createUser(4,"Employee4","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee5 = createUser(5,"Employee5","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee6 = createUser(1,"Employee6","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        employeeList.add(employee1);
//        employeeList.add(employee2);
//        employeeList.add(employee3);
//        employeeList.add(employee4);
//        employeeList.add(employee5);
//        //employeeList.add(employee6);
//        instance.insert(Employee.class,employeeList,false);
//        instance.correctEmployeeParameters(employee6);
//        //System.out.println(instance.select(Employee.class));
//        //assertEquals(employee1, instance.getByID(Employee.class,1).getData());
//    }
//    @Test
//    public void getTaskFail() throws IOException {
//        List<Employee> employeeList = new ArrayList<>();
//        DataProviderCsv instance = new DataProviderCsv();
//        Employee employee1 = createUser(1,"Employee1","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee2 = createUser(2,"Employee2","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee3 = createUser(3,"Employee3","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee4 = createUser(4,"Employee4","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee5 = createUser(5,"Employee5","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee6 = createUser(1,"Employee6","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        employeeList.add(employee1);
//        employeeList.add(employee2);
//        employeeList.add(employee3);
//        employeeList.add(employee4);
//        employeeList.add(employee5);
//        //employeeList.add(employee6);
//        instance.insert(Employee.class,employeeList,false);
//        instance.correctEmployeeParameters(employee6);
//        //System.out.println(instance.select(Employee.class));
//        //assertEquals(employee1, instance.getByID(Employee.class,1).getData());
//    }
//
//    @Test
//    public void calculateTaskCostSuccess() throws IOException {
//        List<Employee> employeeList = new ArrayList<>();
//        DataProviderCsv instance = new DataProviderCsv();
//        Employee employee1 = createUser(1,"Employee1","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee2 = createUser(2,"Employee2","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee3 = createUser(3,"Employee3","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee4 = createUser(4,"Employee4","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee5 = createUser(5,"Employee5","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee6 = createUser(1,"Employee6","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        employeeList.add(employee1);
//        employeeList.add(employee2);
//        employeeList.add(employee3);
//        employeeList.add(employee4);
//        employeeList.add(employee5);
//        //employeeList.add(employee6);
//        instance.insert(Employee.class,employeeList,false);
//        instance.correctEmployeeParameters(employee6);
//        //System.out.println(instance.select(Employee.class));
//        //assertEquals(employee1, instance.getByID(Employee.class,1).getData());
//    }
//    @Test
//    public void calculateTaskCostFail() throws IOException {
//        List<Employee> employeeList = new ArrayList<>();
//        DataProviderCsv instance = new DataProviderCsv();
//        Employee employee1 = createUser(1,"Employee1","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee2 = createUser(2,"Employee2","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee3 = createUser(3,"Employee3","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee4 = createUser(4,"Employee4","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee5 = createUser(5,"Employee5","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee6 = createUser(1,"Employee6","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        employeeList.add(employee1);
//        employeeList.add(employee2);
//        employeeList.add(employee3);
//        employeeList.add(employee4);
//        employeeList.add(employee5);
//        //employeeList.add(employee6);
//        instance.insert(Employee.class,employeeList,false);
//        instance.correctEmployeeParameters(employee6);
//        //System.out.println(instance.select(Employee.class));
//        //assertEquals(employee1, instance.getByID(Employee.class,1).getData());
//    }
//
//    @Test
//    public void getProjectSuccess() throws IOException {
//        List<Employee> employeeList = new ArrayList<>();
//        DataProviderCsv instance = new DataProviderCsv();
//        Employee employee1 = createUser(1,"Employee1","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee2 = createUser(2,"Employee2","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee3 = createUser(3,"Employee3","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee4 = createUser(4,"Employee4","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee5 = createUser(5,"Employee5","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee6 = createUser(1,"Employee6","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        employeeList.add(employee1);
//        employeeList.add(employee2);
//        employeeList.add(employee3);
//        employeeList.add(employee4);
//        employeeList.add(employee5);
//        //employeeList.add(employee6);
//        instance.insert(Employee.class,employeeList,false);
//        instance.correctEmployeeParameters(employee6);
//        //System.out.println(instance.select(Employee.class));
//        //assertEquals(employee1, instance.getByID(Employee.class,1).getData());
//    }
//    @Test
//    public void getProjectFail() throws IOException {
//        List<Employee> employeeList = new ArrayList<>();
//        DataProviderCsv instance = new DataProviderCsv();
//        Employee employee1 = createUser(1,"Employee1","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee2 = createUser(2,"Employee2","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee3 = createUser(3,"Employee3","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee4 = createUser(4,"Employee4","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee5 = createUser(5,"Employee5","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee6 = createUser(1,"Employee6","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        employeeList.add(employee1);
//        employeeList.add(employee2);
//        employeeList.add(employee3);
//        employeeList.add(employee4);
//        employeeList.add(employee5);
//        //employeeList.add(employee6);
//        instance.insert(Employee.class,employeeList,false);
//        instance.correctEmployeeParameters(employee6);
//        //System.out.println(instance.select(Employee.class));
//        //assertEquals(employee1, instance.getByID(Employee.class,1).getData());
//    }
//
//    @Test
//    public void calculateProjectCostSuccess() throws IOException {
//        List<Employee> employeeList = new ArrayList<>();
//        DataProviderCsv instance = new DataProviderCsv();
//        Employee employee1 = createUser(1,"Employee1","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee2 = createUser(2,"Employee2","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee3 = createUser(3,"Employee3","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee4 = createUser(4,"Employee4","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee5 = createUser(5,"Employee5","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee6 = createUser(1,"Employee6","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        employeeList.add(employee1);
//        employeeList.add(employee2);
//        employeeList.add(employee3);
//        employeeList.add(employee4);
//        employeeList.add(employee5);
//        //employeeList.add(employee6);
//        instance.insert(Employee.class,employeeList,false);
//        instance.correctEmployeeParameters(employee6);
//        //System.out.println(instance.select(Employee.class));
//        //assertEquals(employee1, instance.getByID(Employee.class,1).getData());
//    }
//    @Test
//    public void calculateProjectCostFail() throws IOException {
//        List<Employee> employeeList = new ArrayList<>();
//        DataProviderCsv instance = new DataProviderCsv();
//        Employee employee1 = createUser(1,"Employee1","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee2 = createUser(2,"Employee2","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee3 = createUser(3,"Employee3","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee4 = createUser(4,"Employee4","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee5 = createUser(5,"Employee5","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee6 = createUser(1,"Employee6","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        employeeList.add(employee1);
//        employeeList.add(employee2);
//        employeeList.add(employee3);
//        employeeList.add(employee4);
//        employeeList.add(employee5);
//        //employeeList.add(employee6);
//        instance.insert(Employee.class,employeeList,false);
//        instance.correctEmployeeParameters(employee6);
//        //System.out.println(instance.select(Employee.class));
//        //assertEquals(employee1, instance.getByID(Employee.class,1).getData());
//    }
//
//    @Test
//    public void calculateProjectTimeSuccess() throws IOException {
//        List<Employee> employeeList = new ArrayList<>();
//        DataProviderCsv instance = new DataProviderCsv();
//        Employee employee1 = createUser(1,"Employee1","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee2 = createUser(2,"Employee2","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee3 = createUser(3,"Employee3","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee4 = createUser(4,"Employee4","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee5 = createUser(5,"Employee5","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee6 = createUser(1,"Employee6","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        employeeList.add(employee1);
//        employeeList.add(employee2);
//        employeeList.add(employee3);
//        employeeList.add(employee4);
//        employeeList.add(employee5);
//        //employeeList.add(employee6);
//        instance.insert(Employee.class,employeeList,false);
//        instance.correctEmployeeParameters(employee6);
//        //System.out.println(instance.select(Employee.class));
//        //assertEquals(employee1, instance.getByID(Employee.class,1).getData());
//    }
//    @Test
//    public void calculateProjectTimeFail() throws IOException {
//        List<Employee> employeeList = new ArrayList<>();
//        DataProviderCsv instance = new DataProviderCsv();
//        Employee employee1 = createUser(1,"Employee1","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee2 = createUser(2,"Employee2","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee3 = createUser(3,"Employee3","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee4 = createUser(4,"Employee4","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee5 = createUser(5,"Employee5","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee6 = createUser(1,"Employee6","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        employeeList.add(employee1);
//        employeeList.add(employee2);
//        employeeList.add(employee3);
//        employeeList.add(employee4);
//        employeeList.add(employee5);
//        //employeeList.add(employee6);
//        instance.insert(Employee.class,employeeList,false);
//        instance.correctEmployeeParameters(employee6);
//        //System.out.println(instance.select(Employee.class));
//        //assertEquals(employee1, instance.getByID(Employee.class,1).getData());
//    }
//
//    @Test
//    public void createTaskSuccess() throws IOException {
//        List<Employee> employeeList = new ArrayList<>();
//        DataProviderCsv instance = new DataProviderCsv();
//        Employee employee1 = createUser(1,"Employee1","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee2 = createUser(2,"Employee2","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee3 = createUser(3,"Employee3","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee4 = createUser(4,"Employee4","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee5 = createUser(5,"Employee5","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee6 = createUser(1,"Employee6","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        employeeList.add(employee1);
//        employeeList.add(employee2);
//        employeeList.add(employee3);
//        employeeList.add(employee4);
//        employeeList.add(employee5);
//        //employeeList.add(employee6);
//        instance.insert(Employee.class,employeeList,false);
//        instance.correctEmployeeParameters(employee6);
//        //System.out.println(instance.select(Employee.class));
//        //assertEquals(employee1, instance.getByID(Employee.class,1).getData());
//    }
//    @Test
//    public void createTaskFail() throws IOException {
//        List<Employee> employeeList = new ArrayList<>();
//        DataProviderCsv instance = new DataProviderCsv();
//        Employee employee1 = createUser(1,"Employee1","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee2 = createUser(2,"Employee2","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee3 = createUser(3,"Employee3","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee4 = createUser(4,"Employee4","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee5 = createUser(5,"Employee5","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee6 = createUser(1,"Employee6","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        employeeList.add(employee1);
//        employeeList.add(employee2);
//        employeeList.add(employee3);
//        employeeList.add(employee4);
//        employeeList.add(employee5);
//        //employeeList.add(employee6);
//        instance.insert(Employee.class,employeeList,false);
//        instance.correctEmployeeParameters(employee6);
//        //System.out.println(instance.select(Employee.class));
//        //assertEquals(employee1, instance.getByID(Employee.class,1).getData());
//    }
//
//    @Test
//    public void deleteTaskSuccess() throws IOException {
//        List<Employee> employeeList = new ArrayList<>();
//        DataProviderCsv instance = new DataProviderCsv();
//        Employee employee1 = createUser(1,"Employee1","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee2 = createUser(2,"Employee2","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee3 = createUser(3,"Employee3","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee4 = createUser(4,"Employee4","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee5 = createUser(5,"Employee5","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee6 = createUser(1,"Employee6","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        employeeList.add(employee1);
//        employeeList.add(employee2);
//        employeeList.add(employee3);
//        employeeList.add(employee4);
//        employeeList.add(employee5);
//        //employeeList.add(employee6);
//        instance.insert(Employee.class,employeeList,false);
//        instance.correctEmployeeParameters(employee6);
//        //System.out.println(instance.select(Employee.class));
//        //assertEquals(employee1, instance.getByID(Employee.class,1).getData());
//    }
//    @Test
//    public void deleteTaskFail() throws IOException {
//        List<Employee> employeeList = new ArrayList<>();
//        DataProviderCsv instance = new DataProviderCsv();
//        Employee employee1 = createUser(1,"Employee1","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee2 = createUser(2,"Employee2","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee3 = createUser(3,"Employee3","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee4 = createUser(4,"Employee4","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee5 = createUser(5,"Employee5","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee6 = createUser(1,"Employee6","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        employeeList.add(employee1);
//        employeeList.add(employee2);
//        employeeList.add(employee3);
//        employeeList.add(employee4);
//        employeeList.add(employee5);
//        //employeeList.add(employee6);
//        instance.insert(Employee.class,employeeList,false);
//        instance.correctEmployeeParameters(employee6);
//        //System.out.println(instance.select(Employee.class));
//        //assertEquals(employee1, instance.getByID(Employee.class,1).getData());
//    }
//
//    @Test
//    public void getTaskWorkerSuccess() throws IOException {
//        List<Employee> employeeList = new ArrayList<>();
//        DataProviderCsv instance = new DataProviderCsv();
//        Employee employee1 = createUser(1,"Employee1","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee2 = createUser(2,"Employee2","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee3 = createUser(3,"Employee3","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee4 = createUser(4,"Employee4","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee5 = createUser(5,"Employee5","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee6 = createUser(1,"Employee6","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        employeeList.add(employee1);
//        employeeList.add(employee2);
//        employeeList.add(employee3);
//        employeeList.add(employee4);
//        employeeList.add(employee5);
//        //employeeList.add(employee6);
//        instance.insert(Employee.class,employeeList,false);
//        instance.correctEmployeeParameters(employee6);
//        //System.out.println(instance.select(Employee.class));
//        //assertEquals(employee1, instance.getByID(Employee.class,1).getData());
//    }
//    @Test
//    public void getTaskWorkerFail() throws IOException {
//        List<Employee> employeeList = new ArrayList<>();
//        DataProviderCsv instance = new DataProviderCsv();
//        Employee employee1 = createUser(1,"Employee1","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee2 = createUser(2,"Employee2","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee3 = createUser(3,"Employee3","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee4 = createUser(4,"Employee4","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee5 = createUser(5,"Employee5","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee6 = createUser(1,"Employee6","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        employeeList.add(employee1);
//        employeeList.add(employee2);
//        employeeList.add(employee3);
//        employeeList.add(employee4);
//        employeeList.add(employee5);
//        //employeeList.add(employee6);
//        instance.insert(Employee.class,employeeList,false);
//        instance.correctEmployeeParameters(employee6);
//        //System.out.println(instance.select(Employee.class));
//        //assertEquals(employee1, instance.getByID(Employee.class,1).getData());
//    }
//
//    @Test
//    public void getTaskByIdSuccess() throws IOException {
//        List<Employee> employeeList = new ArrayList<>();
//        DataProviderCsv instance = new DataProviderCsv();
//        Employee employee1 = createUser(1,"Employee1","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee2 = createUser(2,"Employee2","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee3 = createUser(3,"Employee3","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee4 = createUser(4,"Employee4","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee5 = createUser(5,"Employee5","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee6 = createUser(1,"Employee6","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        employeeList.add(employee1);
//        employeeList.add(employee2);
//        employeeList.add(employee3);
//        employeeList.add(employee4);
//        employeeList.add(employee5);
//        //employeeList.add(employee6);
//        instance.insert(Employee.class,employeeList,false);
//        instance.correctEmployeeParameters(employee6);
//        //System.out.println(instance.select(Employee.class));
//        //assertEquals(employee1, instance.getByID(Employee.class,1).getData());
//    }
//    @Test
//    public void getTaskByIdFail() throws IOException {
//        List<Employee> employeeList = new ArrayList<>();
//        DataProviderCsv instance = new DataProviderCsv();
//        Employee employee1 = createUser(1,"Employee1","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee2 = createUser(2,"Employee2","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee3 = createUser(3,"Employee3","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee4 = createUser(4,"Employee4","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee5 = createUser(5,"Employee5","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee6 = createUser(1,"Employee6","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        employeeList.add(employee1);
//        employeeList.add(employee2);
//        employeeList.add(employee3);
//        employeeList.add(employee4);
//        employeeList.add(employee5);
//        //employeeList.add(employee6);
//        instance.insert(Employee.class,employeeList,false);
//        instance.correctEmployeeParameters(employee6);
//        //System.out.println(instance.select(Employee.class));
//        //assertEquals(employee1, instance.getByID(Employee.class,1).getData());
//    }
//
//    @Test
//    public void getTaskListByIdSuccess() throws IOException {
//        List<Employee> employeeList = new ArrayList<>();
//        DataProviderCsv instance = new DataProviderCsv();
//        Employee employee1 = createUser(1,"Employee1","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee2 = createUser(2,"Employee2","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee3 = createUser(3,"Employee3","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee4 = createUser(4,"Employee4","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee5 = createUser(5,"Employee5","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee6 = createUser(1,"Employee6","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        employeeList.add(employee1);
//        employeeList.add(employee2);
//        employeeList.add(employee3);
//        employeeList.add(employee4);
//        employeeList.add(employee5);
//        //employeeList.add(employee6);
//        instance.insert(Employee.class,employeeList,false);
//        instance.correctEmployeeParameters(employee6);
//        //System.out.println(instance.select(Employee.class));
//        //assertEquals(employee1, instance.getByID(Employee.class,1).getData());
//    }
//    @Test
//    public void getTaskListByIdFail() throws IOException {
//        List<Employee> employeeList = new ArrayList<>();
//        DataProviderCsv instance = new DataProviderCsv();
//        Employee employee1 = createUser(1,"Employee1","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee2 = createUser(2,"Employee2","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee3 = createUser(3,"Employee3","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee4 = createUser(4,"Employee4","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee5 = createUser(5,"Employee5","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee6 = createUser(1,"Employee6","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        employeeList.add(employee1);
//        employeeList.add(employee2);
//        employeeList.add(employee3);
//        employeeList.add(employee4);
//        employeeList.add(employee5);
//        //employeeList.add(employee6);
//        instance.insert(Employee.class,employeeList,false);
//        instance.correctEmployeeParameters(employee6);
//        //System.out.println(instance.select(Employee.class));
//        //assertEquals(employee1, instance.getByID(Employee.class,1).getData());
//    }
//
//    @Test
//    public void deleteProjectSuccess() throws IOException {
//        List<Employee> employeeList = new ArrayList<>();
//        DataProviderCsv instance = new DataProviderCsv();
//        Employee employee1 = createUser(1,"Employee1","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee2 = createUser(2,"Employee2","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee3 = createUser(3,"Employee3","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee4 = createUser(4,"Employee4","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee5 = createUser(5,"Employee5","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee6 = createUser(1,"Employee6","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        employeeList.add(employee1);
//        employeeList.add(employee2);
//        employeeList.add(employee3);
//        employeeList.add(employee4);
//        employeeList.add(employee5);
//        //employeeList.add(employee6);
//        instance.insert(Employee.class,employeeList,false);
//        instance.correctEmployeeParameters(employee6);
//        //System.out.println(instance.select(Employee.class));
//        //assertEquals(employee1, instance.getByID(Employee.class,1).getData());
//    }
//    @Test
//    public void deleteProjectFail() throws IOException {
//        List<Employee> employeeList = new ArrayList<>();
//        DataProviderCsv instance = new DataProviderCsv();
//        Employee employee1 = createUser(1,"Employee1","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee2 = createUser(2,"Employee2","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee3 = createUser(3,"Employee3","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee4 = createUser(4,"Employee4","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee5 = createUser(5,"Employee5","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee6 = createUser(1,"Employee6","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        employeeList.add(employee1);
//        employeeList.add(employee2);
//        employeeList.add(employee3);
//        employeeList.add(employee4);
//        employeeList.add(employee5);
//        //employeeList.add(employee6);
//        instance.insert(Employee.class,employeeList,false);
//        instance.correctEmployeeParameters(employee6);
//        //System.out.println(instance.select(Employee.class));
//        //assertEquals(employee1, instance.getByID(Employee.class,1).getData());
//    }
//
//    @Test
//    public void createProjectSuccess() throws IOException {
//        List<Employee> employeeList = new ArrayList<>();
//        DataProviderCsv instance = new DataProviderCsv();
//        Employee employee1 = createUser(1,"Employee1","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee2 = createUser(2,"Employee2","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee3 = createUser(3,"Employee3","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee4 = createUser(4,"Employee4","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee5 = createUser(5,"Employee5","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee6 = createUser(1,"Employee6","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        employeeList.add(employee1);
//        employeeList.add(employee2);
//        employeeList.add(employee3);
//        employeeList.add(employee4);
//        employeeList.add(employee5);
//        //employeeList.add(employee6);
//        instance.insert(Employee.class,employeeList,false);
//        instance.correctEmployeeParameters(employee6);
//        //System.out.println(instance.select(Employee.class));
//        //assertEquals(employee1, instance.getByID(Employee.class,1).getData());
//    }
//    @Test
//    public void createProjectFail() throws IOException {
//        List<Employee> employeeList = new ArrayList<>();
//        DataProviderCsv instance = new DataProviderCsv();
//        Employee employee1 = createUser(1,"Employee1","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee2 = createUser(2,"Employee2","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee3 = createUser(3,"Employee3","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee4 = createUser(4,"Employee4","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee5 = createUser(5,"Employee5","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee6 = createUser(1,"Employee6","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        employeeList.add(employee1);
//        employeeList.add(employee2);
//        employeeList.add(employee3);
//        employeeList.add(employee4);
//        employeeList.add(employee5);
//        //employeeList.add(employee6);
//        instance.insert(Employee.class,employeeList,false);
//        instance.correctEmployeeParameters(employee6);
//        //System.out.println(instance.select(Employee.class));
//        //assertEquals(employee1, instance.getByID(Employee.class,1).getData());
//    }
//
//    @Test
//    public void getProjectByIdSuccess() throws IOException {
//        List<Employee> employeeList = new ArrayList<>();
//        DataProviderCsv instance = new DataProviderCsv();
//        Employee employee1 = createUser(1,"Employee1","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee2 = createUser(2,"Employee2","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee3 = createUser(3,"Employee3","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee4 = createUser(4,"Employee4","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee5 = createUser(5,"Employee5","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee6 = createUser(1,"Employee6","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        employeeList.add(employee1);
//        employeeList.add(employee2);
//        employeeList.add(employee3);
//        employeeList.add(employee4);
//        employeeList.add(employee5);
//        //employeeList.add(employee6);
//        instance.insert(Employee.class,employeeList,false);
//        instance.correctEmployeeParameters(employee6);
//        //System.out.println(instance.select(Employee.class));
//        //assertEquals(employee1, instance.getByID(Employee.class,1).getData());
//    }
//    @Test
//    public void getProjectByIdFail() throws IOException {
//        List<Employee> employeeList = new ArrayList<>();
//        DataProviderCsv instance = new DataProviderCsv();
//        Employee employee1 = createUser(1,"Employee1","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee2 = createUser(2,"Employee2","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee3 = createUser(3,"Employee3","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee4 = createUser(4,"Employee4","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee5 = createUser(5,"Employee5","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee6 = createUser(1,"Employee6","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        employeeList.add(employee1);
//        employeeList.add(employee2);
//        employeeList.add(employee3);
//        employeeList.add(employee4);
//        employeeList.add(employee5);
//        //employeeList.add(employee6);
//        instance.insert(Employee.class,employeeList,false);
//        instance.correctEmployeeParameters(employee6);
//        //System.out.println(instance.select(Employee.class));
//        //assertEquals(employee1, instance.getByID(Employee.class,1).getData());
//    }
//
//    @Test
//    public void getProjecListByIdSuccess() throws IOException {
//        List<Employee> employeeList = new ArrayList<>();
//        DataProviderCsv instance = new DataProviderCsv();
//        Employee employee1 = createUser(1,"Employee1","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee2 = createUser(2,"Employee2","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee3 = createUser(3,"Employee3","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee4 = createUser(4,"Employee4","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee5 = createUser(5,"Employee5","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee6 = createUser(1,"Employee6","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        employeeList.add(employee1);
//        employeeList.add(employee2);
//        employeeList.add(employee3);
//        employeeList.add(employee4);
//        employeeList.add(employee5);
//        //employeeList.add(employee6);
//        instance.insert(Employee.class,employeeList,false);
//        instance.correctEmployeeParameters(employee6);
//        //System.out.println(instance.select(Employee.class));
//        //assertEquals(employee1, instance.getByID(Employee.class,1).getData());
//    }
//    @Test
//    public void getProjecListByIdFail() throws IOException {
//        List<Employee> employeeList = new ArrayList<>();
//        DataProviderCsv instance = new DataProviderCsv();
//        Employee employee1 = createUser(1,"Employee1","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee2 = createUser(2,"Employee2","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee3 = createUser(3,"Employee3","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee4 = createUser(4,"Employee4","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee5 = createUser(5,"Employee5","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee6 = createUser(1,"Employee6","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        employeeList.add(employee1);
//        employeeList.add(employee2);
//        employeeList.add(employee3);
//        employeeList.add(employee4);
//        employeeList.add(employee5);
//        //employeeList.add(employee6);
//        instance.insert(Employee.class,employeeList,false);
//        instance.correctEmployeeParameters(employee6);
//        //System.out.println(instance.select(Employee.class));
//        //assertEquals(employee1, instance.getByID(Employee.class,1).getData());
//    }
//
//    @Test
//    public void correctEmployeeParametersSuccess() throws IOException {
//        List<Employee> employeeList = new ArrayList<>();
//        DataProviderCsv instance = new DataProviderCsv();
//        Employee employee1 = createUser(1,"Employee1","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee2 = createUser(2,"Employee2","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee3 = createUser(3,"Employee3","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee4 = createUser(4,"Employee4","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee5 = createUser(5,"Employee5","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee6 = createUser(1,"Employee6","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        employeeList.add(employee1);
//        employeeList.add(employee2);
//        employeeList.add(employee3);
//        employeeList.add(employee4);
//        employeeList.add(employee5);
//        //employeeList.add(employee6);
//        instance.insert(Employee.class,employeeList,false);
//        instance.correctEmployeeParameters(employee6);
//        //System.out.println(instance.select(Employee.class));
//        //assertEquals(employee1, instance.getByID(Employee.class,1).getData());
//    }
//    @Test
//    public void correctEmployeeParametersFail() throws IOException {
//        List<Employee> employeeList = new ArrayList<>();
//        DataProviderCsv instance = new DataProviderCsv();
//        Employee employee1 = createUser(1,"Employee1","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee2 = createUser(2,"Employee2","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee3 = createUser(3,"Employee3","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee4 = createUser(4,"Employee4","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee5 = createUser(5,"Employee5","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee6 = createUser(1,"Employee6","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        employeeList.add(employee1);
//        employeeList.add(employee2);
//        employeeList.add(employee3);
//        employeeList.add(employee4);
//        employeeList.add(employee5);
//        //employeeList.add(employee6);
//        instance.insert(Employee.class,employeeList,false);
//        instance.correctEmployeeParameters(employee6);
//        //System.out.println(instance.select(Employee.class));
//        //assertEquals(employee1, instance.getByID(Employee.class,1).getData());
//    }
//
//    @Test
//    public void addEmployeeToTaskSuccess() throws IOException {
//        List<Employee> employeeList = new ArrayList<>();
//        DataProviderCsv instance = new DataProviderCsv();
//        Employee employee1 = createUser(1,"Employee1","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee2 = createUser(2,"Employee2","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee3 = createUser(3,"Employee3","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee4 = createUser(4,"Employee4","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee5 = createUser(5,"Employee5","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee6 = createUser(1,"Employee6","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        employeeList.add(employee1);
//        employeeList.add(employee2);
//        employeeList.add(employee3);
//        employeeList.add(employee4);
//        employeeList.add(employee5);
//        //employeeList.add(employee6);
//        instance.insert(Employee.class,employeeList,false);
//        instance.correctEmployeeParameters(employee6);
//        //System.out.println(instance.select(Employee.class));
//        //assertEquals(employee1, instance.getByID(Employee.class,1).getData());
//    }
//    @Test
//    public void addEmployeeToTaskFail() throws IOException {
//        List<Employee> employeeList = new ArrayList<>();
//        DataProviderCsv instance = new DataProviderCsv();
//        Employee employee1 = createUser(1,"Employee1","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee2 = createUser(2,"Employee2","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee3 = createUser(3,"Employee3","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee4 = createUser(4,"Employee4","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee5 = createUser(5,"Employee5","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee6 = createUser(1,"Employee6","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        employeeList.add(employee1);
//        employeeList.add(employee2);
//        employeeList.add(employee3);
//        employeeList.add(employee4);
//        employeeList.add(employee5);
//        //employeeList.add(employee6);
//        instance.insert(Employee.class,employeeList,false);
//        instance.correctEmployeeParameters(employee6);
//        //System.out.println(instance.select(Employee.class));
//        //assertEquals(employee1, instance.getByID(Employee.class,1).getData());
//    }
//
//    @Test
//    public void deleteEmployeeFromTaskSuccess() throws IOException {
//        List<Employee> employeeList = new ArrayList<>();
//        DataProviderCsv instance = new DataProviderCsv();
//        Employee employee1 = createUser(1,"Employee1","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee2 = createUser(2,"Employee2","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee3 = createUser(3,"Employee3","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee4 = createUser(4,"Employee4","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee5 = createUser(5,"Employee5","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee6 = createUser(1,"Employee6","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        employeeList.add(employee1);
//        employeeList.add(employee2);
//        employeeList.add(employee3);
//        employeeList.add(employee4);
//        employeeList.add(employee5);
//        //employeeList.add(employee6);
//        instance.insert(Employee.class,employeeList,false);
//        instance.correctEmployeeParameters(employee6);
//        //System.out.println(instance.select(Employee.class));
//        //assertEquals(employee1, instance.getByID(Employee.class,1).getData());
//    }
//    @Test
//    public void deleteEmployeeFromTaskFail() throws IOException {
//        List<Employee> employeeList = new ArrayList<>();
//        DataProviderCsv instance = new DataProviderCsv();
//        Employee employee1 = createUser(1,"Employee1","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee2 = createUser(2,"Employee2","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee3 = createUser(3,"Employee3","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee4 = createUser(4,"Employee4","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee5 = createUser(5,"Employee5","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee6 = createUser(1,"Employee6","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        employeeList.add(employee1);
//        employeeList.add(employee2);
//        employeeList.add(employee3);
//        employeeList.add(employee4);
//        employeeList.add(employee5);
//        //employeeList.add(employee6);
//        instance.insert(Employee.class,employeeList,false);
//        instance.correctEmployeeParameters(employee6);
//        //System.out.println(instance.select(Employee.class));
//        //assertEquals(employee1, instance.getByID(Employee.class,1).getData());
//    }
//
//    @Test
//    public void createEmployeeSuccess() throws IOException {
//        List<Employee> employeeList = new ArrayList<>();
//        DataProviderCsv instance = new DataProviderCsv();
//        Employee employee1 = createUser(1,"Employee1","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee2 = createUser(2,"Employee2","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee3 = createUser(3,"Employee3","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee4 = createUser(4,"Employee4","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee5 = createUser(5,"Employee5","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee6 = createUser(1,"Employee6","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        employeeList.add(employee1);
//        employeeList.add(employee2);
//        employeeList.add(employee3);
//        employeeList.add(employee4);
//        employeeList.add(employee5);
//        //employeeList.add(employee6);
//        instance.insert(Employee.class,employeeList,false);
//        instance.correctEmployeeParameters(employee6);
//        //System.out.println(instance.select(Employee.class));
//        //assertEquals(employee1, instance.getByID(Employee.class,1).getData());
//    }
//    @Test
//    public void createEmployeeFail() throws IOException {
//        List<Employee> employeeList = new ArrayList<>();
//        DataProviderCsv instance = new DataProviderCsv();
//        Employee employee1 = createUser(1,"Employee1","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee2 = createUser(2,"Employee2","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee3 = createUser(3,"Employee3","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee4 = createUser(4,"Employee4","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee5 = createUser(5,"Employee5","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        Employee employee6 = createUser(1,"Employee6","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
//        employeeList.add(employee1);
//        employeeList.add(employee2);
//        employeeList.add(employee3);
//        employeeList.add(employee4);
//        employeeList.add(employee5);
//        //employeeList.add(employee6);
//        instance.insert(Employee.class,employeeList,false);
//        instance.correctEmployeeParameters(employee6);
//        //System.out.println(instance.select(Employee.class));
//        //assertEquals(employee1, instance.getByID(Employee.class,1).getData());
//    }

}