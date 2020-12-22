package ru.sfedu.groupappcontrol.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.sfedu.groupappcontrol.Constants;
import ru.sfedu.groupappcontrol.models.*;
import ru.sfedu.groupappcontrol.models.enums.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.sfedu.groupappcontrol.api.Fill.*;

class DataProviderJdbcTest {
    private static final Logger log = LogManager.getLogger(DataProviderJdbcTest.class);
    public static DataProviderJdbc db = new DataProviderJdbc();

    @BeforeAll
    static void setCSVEnv() {
        db.deleteAllRecord();
        db.createTables();
        addRecord();
    }

    @Test
    public void insertEmployeeSuccess() {
        System.out.println("Insert employee success");
        Employee employee = new Employee();
        db.setBasicEmployeeParams(employee,"Employee3","Employee_sec_name",
                "Employee_Login","admin","employee@sfedu.ru","Employee_personal_token",
                "FullStack", TypeOfEmployee.Employee);
        Outcomes o = db.insertEmployee(employee).getStatus();
        assertEquals(Outcomes.Complete,o);
    }
    @Test
    public void insertProject(){
        Project project = db.createProject("TEST_PROJECT","18-12-2020",getListTask()).getData();
        log.info(project);
        db.insertProject(project);
    }
    @Test
    public void insertEmployee(){
        Employee employee = new Employee();
        db.setBasicEmployeeParams(employee,"Employee3","Employee_sec_name",
                "Employee_Login","admin","employee@sfedu.ru","Employee_personal_token",
                "FullStack", TypeOfEmployee.Employee);
        log.info(employee.getId());
        List<Task> task = new ArrayList<>();
        Task testTask = db.createTask("Descript",14553.0,db.getEmployeeById(1).getData(), TypeOfCompletion.DEVELOPING,getListEmployee(),"04-12-2020","10-12-2020","05-12-2020", TaskTypes.BASE_TASK).getData();
        db.insertTask(testTask);
    }

    @Test
    public void delEmployee(){
        db.deleteEmployee(1);
    }

    @Test
    public void updEmployee(){
        Employee employee = db.createEmployee(2,"Test_Employee","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Employee).getData();
        log.info(employee);
        db.updateEmployee(employee);
    }

    public static void addRecord() {
        for (int i=1; i<=10; i++) {
            Employee employee = new Employee();
            employee.setFirstName(firstName[i-1]);
            employee.setLastName(lastName[i-1]);
            employee.setLogin(login[i-1]);
            employee.setPassword(password[i-1]);
            employee.setEmail(email[i-1]);
            employee.setToken(token[i-1]);
            employee.setDepartment(department[i-1]);
            employee.setTypeOfEmployee(TypeOfEmployee.Employee);
            db.insertEmployee(employee);
        }
        for (int i=1; i<=10; i++) {
            Developer developer = new Developer();
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
            db.insertDeveloper(developer);
        }
        for (int i=1; i<=10; i++) {
            Tester tester = new Tester();
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
            db.insertTester(tester);
        }
        for (int i=1; i<=10; i++) {
            Task task = new Task();
            task.setTaskDescription(taskDescription[i-1]);
            task.setMoney(money[i-1]);
            task.setScrumMaster(getScrum());
            task.setStatus(TypeOfCompletion.CUSTOM);
            task.setTeam(getListEmployee());
            task.setCreatedDate(createdDate[i-1]);
            task.setDeadline(deadline[i-1]);
            task.setLastUpdate(lastUpdate[i-1]);
            task.setTaskType(TaskTypes.BASE_TASK);
            db.insertTask(task);
        }
        for (int i=1; i<=10; i++) {
            DevelopersTask developerTask = new DevelopersTask();
            developerTask.setTaskDescription(taskDescription[i-1]);
            developerTask.setMoney(money[i-1]);
            developerTask.setScrumMaster(getScrum());
            developerTask.setStatus(TypeOfCompletion.CUSTOM);
            developerTask.setTeam(getListEmployee());
            developerTask.setCreatedDate(createdDate[i-1]);
            developerTask.setDeadline(deadline[i-1]);
            developerTask.setLastUpdate(lastUpdate[i-1]);
            developerTask.setTaskType(TaskTypes.DEVELOPERS_TASK);
            developerTask.setDeveloperTaskType(DeveloperTaskType.DEVELOPMENT);
            developerTask.setDeveloperComments(Constants.BaseComment);
            db.insertDevelopersTask(developerTask);
        }
        for (int i=1; i<=10; i++) {
            TestersTask testersTask = new TestersTask();
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
            db.insertTestersTask(testersTask);
        }
        for (int i=1; i<=4; i++) {
            Project project=new Project();
            project.setId(i-1);
            project.setTitle(title[i-1]);
            project.setTakeIntoDevelopment(createdDate[i-1]);
            project.setTask(getListTask());
            db.insertProject(project);
        }
    }
    private static Employee getScrum(){
        List<Employee> listemployee = db.getListEmployees(Employee.class);
        int max=9; int min=0;
        return listemployee.get((int) ((Math.random() * ((max - min) + 1)) + min));
    }
    private static List<Employee> getListEmployee(){
        List<Employee> listemployee = db.getListEmployees(Employee.class);
        List<Developer> developers = db.getListEmployees(Developer.class);
        int max=9; int min=0;
        for (int i=1;i<=3; i++) {
            Developer developer = developers.get((int) ((Math.random() * ((max - min) + 1)) + min));
            listemployee.add(developer);
        }
        List<Tester> testers = db.getListEmployees(Tester.class);
        for (int i=1;i<=3; i++) {
            Tester tester = testers.get((int) ((Math.random() * ((max - min) + 1)) + min));
            listemployee.add(tester);
        }
        return listemployee;
    }
    private static List<Task> getListTask(){
        List<Task> fullList = new ArrayList<>();
        List<Task> listTask = db.getListTasks(Task.class);
        int max=9; int min=0;
        for (int i=1;i<=4; i++) {
            Task task = listTask.get((int)((Math.random() * ((max - min) + 1)) + min));
            fullList.add(task);
            log.info(task);
        }
        List<DevelopersTask> developers = db.getListTasks(DevelopersTask.class);
        for (int i=1;i<=4; i++) {
            DevelopersTask developersTask = developers.get((int)((Math.random() * ((max - min) + 1)) + min));
            fullList.add(developersTask);
            log.info(developersTask);
        }
        List<TestersTask> testers = db.getListTasks(TestersTask.class);
        for (int i=1;i<=4; i++) {
            TestersTask testersTask = testers.get((int) ((Math.random() * ((max - min) + 1)) + min));
            fullList.add(testersTask);
            log.info(testersTask);
        }
        return fullList;
    }
}