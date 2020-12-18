package ru.sfedu.groupappcontrol.api;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.*;
import ru.sfedu.groupappcontrol.models.*;
import ru.sfedu.groupappcontrol.Constants;
import ru.sfedu.groupappcontrol.models.enums.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static ru.sfedu.groupappcontrol.api.Fill.*;
import static ru.sfedu.groupappcontrol.models.enums.Outcomes.*;
import static ru.sfedu.groupappcontrol.models.enums.TypeOfCompletion.TESTING;

import java.io.IOException;
import java.util.*;


class DataProviderXMLTest {
    public static DataProviderXML instance = new DataProviderXML();


    private static final Logger log = LogManager.getLogger(DataProviderXMLTest.class);


    DataProviderXMLTest() {
    }

    @BeforeAll
    static void setXMLEnv() {
        instance.deleteAllRecord();
        addRecord();
    }

    @Test
    public void getTaskByIdSuccess() {
        Assertions.assertEquals(1, instance.getTaskById(1).getData().getId());
    }

    @Test
    public void getTaskByIdFail() {
        Assertions.assertNotEquals(2, instance.getTaskById(1).getData().getId());
    }

    @Test
    public void getDevelopersTaskByIdSuccess() {
        Assertions.assertEquals(1, instance.getDevelopersTaskById(1).getData().getId());
    }

    @Test
    public void getDevelopersTaskByIdFail() {
        Assertions.assertNotEquals(2, instance.getDevelopersTaskById(1).getData().getId());
    }

    @Test
    public void getTestersTaskByIdSuccess() {
        Assertions.assertEquals(1, instance.getTestersTaskById(1).getData().getId());
    }

    @Test
    public void getTestersTaskByIdFail() {
        Assertions.assertNotEquals(2, instance.getTestersTaskById(1).getData().getId());
    }

    @Test
    public void getEmployeeByIdSuccess() {
        Assertions.assertEquals(1, instance.getEmployeeById(1).getData().getId());
    }

    @Test
    public void getEmployeeByIdFail() {
        Assertions.assertNotEquals(2, instance.getEmployeeById(1).getData().getId());
    }

    @Test
    public void getDeveloperByIdSuccess() {
        Assertions.assertEquals(1, instance.getDeveloperById(1).getData().getId());
    }

    @Test
    public void getDeveloperByIdFail() {
        Assertions.assertNotEquals(2, instance.getDeveloperById(1).getData().getId());
    }

    @Test
    public void getTesterByIdSuccess() {
        Assertions.assertEquals(1, instance.getTesterById(1).getData().getId());
    }

    @Test
    public void getTesterByIdFail() {
        Assertions.assertNotEquals(2, instance.getTesterById(1).getData().getId());
    }

    @Test
    public void getProjectByIDSuccess() {
        Assertions.assertEquals(1, instance.getProjectByID(1).getData().getId());
    }

    @Test
    public void getProjectByIDFail() {
        Assertions.assertNotEquals(2, instance.getProjectByID(1).getData().getId());
    }

    @Test
    public void insertTaskSuccess() {
        List<Task> task = new ArrayList<>();
        Task testTask = instance.createTask(20, "Descript", 14553.0, getScrum(), TypeOfCompletion.DEVELOPING, getListEmployee(), "04-12-2020", "10-12-2020", "05-12-2020", TaskTypes.BASE_TASK).getData();
        task.add(testTask);
        instance.insertTask(task, true);
        log.info(instance.getTaskById(20).getData().getId());
        Assertions.assertEquals(20, instance.getTaskById(20).getData().getId());
    }

    @Test
    public void insertTaskFail() {
        List<Task> task = new ArrayList<>();
        Task testTask = instance.createTask(1, "Descript", 14553.0, getScrum(), TypeOfCompletion.DEVELOPING, getListEmployee(), "04-12-2020", "10-12-2020", "05-12-2020", TaskTypes.BASE_TASK).getData();
        task.add(testTask);
        instance.insertTask(task, true);
        log.info(instance.getTaskById(1).getData().getTaskDescription());
        Assertions.assertNotEquals("Descript", instance.getTaskById(1).getData().getTaskDescription());
    }

    @Test
    public void insertDevelopersTaskSuccess() {
        List<DevelopersTask> developersTasks = new ArrayList<>();
        DevelopersTask developersTask = (DevelopersTask) instance.createTask(20, "Descript", 14553.0, getScrum(), TypeOfCompletion.DEVELOPING, getListEmployee(), "04-12-2020", "10-12-2020", "05-12-2020", TaskTypes.DEVELOPERS_TASK).getData();
        developersTasks.add(developersTask);
        instance.insertDevelopersTask(developersTasks, true);
        log.info(instance.getDevelopersTaskById(20).getData().getId());
        Assertions.assertEquals(20, instance.getDevelopersTaskById(20).getData().getId());
    }

    @Test
    public void insertDevelopersTaskFail() {
        List<DevelopersTask> developersTasks = new ArrayList<>();
        DevelopersTask developersTask = (DevelopersTask) instance.createTask(1, "Descript", 14553.0, getScrum(), TypeOfCompletion.DEVELOPING, getListEmployee(), "04-12-2020", "10-12-2020", "05-12-2020", TaskTypes.DEVELOPERS_TASK).getData();
        developersTasks.add(developersTask);
        instance.insertDevelopersTask(developersTasks, true);
        log.info(instance.getDevelopersTaskById(1).getData().getTaskDescription());
        Assertions.assertNotEquals("Descript", instance.getDevelopersTaskById(1).getData().getTaskDescription());
    }

    @Test
    public void insertTestersTaskSuccess() {
        List<TestersTask> testersTasks = new ArrayList<>();
        TestersTask testersTask = (TestersTask) instance.createTask(20, "Descript", 14553.0, getScrum(), TypeOfCompletion.DEVELOPING, getListEmployee(), "04-12-2020", "10-12-2020", "05-12-2020", TaskTypes.TESTERS_TASK).getData();
        testersTasks.add(testersTask);
        instance.insertTestersTask(testersTasks, true);
        log.info(instance.getTestersTaskById(20).getData().getId());
        Assertions.assertEquals(20, instance.getTestersTaskById(20).getData().getId());
    }

    @Test
    public void insertTestersTaskFail() {
        List<TestersTask> testersTasks = new ArrayList<>();
        TestersTask testersTask = (TestersTask) instance.createTask(1, "Descript", 14553.0, getScrum(), TypeOfCompletion.DEVELOPING, getListEmployee(), "04-12-2020", "10-12-2020", "05-12-2020", TaskTypes.TESTERS_TASK).getData();
        testersTasks.add(testersTask);
        instance.insertTestersTask(testersTasks, true);
        log.info(instance.getTestersTaskById(1).getData().getTaskDescription());
        Assertions.assertNotEquals("Descript", instance.getTestersTaskById(1).getData().getTaskDescription());

    }

    @Test
    public void insertEmployeeSuccess() {
        List<Employee> employees = new ArrayList<>();
        Employee employee = instance.createEmployee(20, "Test_Employee", "Employee_sec_name", "Employee_Login", "admin", "employee@sfedu.ru", "Employee_personal_token", "FullStack", TypeOfEmployee.Employee).getData();
        employees.add(employee);
        instance.insertEmployee(employees, true);
        log.info(instance.getEmployeeById(20).getData().getId());
        Assertions.assertEquals(20, instance.getEmployeeById(20).getData().getId());
    }

    @Test
    public void insertEmployeeFail() {
        List<Employee> employees = new ArrayList<>();
        Employee employee = instance.createEmployee(1, "Test_Employee", "Employee_sec_name", "Employee_Login", "admin", "employee@sfedu.ru", "Employee_personal_token", "FullStack", TypeOfEmployee.Employee).getData();
        employees.add(employee);
        instance.insertEmployee(employees, true);
        log.info(instance.getEmployeeById(1).getData().getFirstName());
        Assertions.assertNotEquals("Test_Employee", instance.getEmployeeById(1).getData().getFirstName());
    }

    @Test
    public void insertDeveloperSuccess() {
        List<Developer> developers = new ArrayList<>();
        Developer developer = (Developer) instance.createEmployee(20, "Test_Employee", "Employee_sec_name", "Employee_Login", "admin", "employee@sfedu.ru", "Employee_personal_token", "FullStack", TypeOfEmployee.Developer).getData();
        developers.add(developer);
        instance.insertDeveloper(developers, true);
        log.info(instance.getDeveloperById(20).getData().getId());
        Assertions.assertEquals(20, instance.getDeveloperById(20).getData().getId());
    }

    @Test
    public void insertDeveloperFail() {
        List<Developer> developers = new ArrayList<>();
        Developer developer = (Developer) instance.createEmployee(1, "Test_Employee", "Employee_sec_name", "Employee_Login", "admin", "employee@sfedu.ru", "Employee_personal_token", "FullStack", TypeOfEmployee.Developer).getData();
        developers.add(developer);
        instance.insertDeveloper(developers, true);
        log.info(instance.getDeveloperById(1).getData().getFirstName());
        Assertions.assertNotEquals("Test_Employee", instance.getDeveloperById(1).getData().getFirstName());
    }

    @Test
    public void insertTesterSuccess() {
        List<Tester> testers = new ArrayList<>();
        Tester tester = (Tester) instance.createEmployee(20, "Test_Employee", "Employee_sec_name", "Employee_Login", "admin", "employee@sfedu.ru", "Employee_personal_token", "FullStack", TypeOfEmployee.Tester).getData();
        testers.add(tester);
        instance.insertTester(testers, true);
        log.info(instance.getTesterById(20).getData().getId());
        Assertions.assertEquals(20, instance.getTesterById(20).getData().getId());
    }

    @Test
    public void insertTesterFail() {
        List<Tester> testers = new ArrayList<>();
        Tester tester = (Tester) instance.createEmployee(1, "Test_Employee", "Employee_sec_name", "Employee_Login", "admin", "employee@sfedu.ru", "Employee_personal_token", "FullStack", TypeOfEmployee.Tester).getData();
        testers.add(tester);
        instance.insertTester(testers, true);
        log.info(instance.getTesterById(1).getData().getFirstName());
        Assertions.assertNotEquals("Test_Employee", instance.getTesterById(1).getData().getFirstName());
    }

    @Test
    public void insertFProjectSuccess() {
        List<Project> list = new ArrayList<>();
        Project project = instance.createProject(6, "TEST_PROJECT", "18-12-2020", getListTask()).getData();
        list.add(project);
        instance.insertProject(list, true);
        Assertions.assertEquals("TEST_PROJECT", instance.getProjectByID(6).getData().getTitle());
    }

    @Test
    public void insertGProjectFail() {
        List<Project> list = new ArrayList<>();
        Project project = instance.createProject(6, "NEW_TEST_PROJECT", "18-12-2020", getListTask()).getData();
        list.add(project);
        instance.insertProject(list, true);
        Assertions.assertNotEquals("NEW_TEST_PROJECT", instance.getProjectByID(6).getData().getTitle());
    }

    @Test
    public void deleteTaskSuccess() {
        List<Task> task = new ArrayList<>();
        Task testTask = instance.createTask(12, "Descript", 14553.0, getScrum(), TypeOfCompletion.DEVELOPING, getListEmployee(), "04-12-2020", "10-12-2020", "05-12-2020", TaskTypes.BASE_TASK).getData();
        task.add(testTask);
        instance.insertTask(task, true);
        List<Task> taskList = instance.select(Task.class);
        log.debug(taskList);
        instance.deleteTask(testTask.getId());
        List<Task> editedTaskList = instance.select(Task.class);
        log.debug(editedTaskList);
        Assertions.assertEquals(Fail, instance.getTaskById(12).getStatus());
    }

    @Test
    public void deleteTaskFail() {
        instance.deleteTask(12);
        Assertions.assertEquals(Complete, instance.deleteTask(12).getStatus());
    }

    @Test
    public void deleteDevelopersTaskSuccess() {
        List<DevelopersTask> developersTasks = new ArrayList<>();
        DevelopersTask developersTask = (DevelopersTask) instance.createTask(12, "Descript", 14553.0, getScrum(), TypeOfCompletion.DEVELOPING, getListEmployee(), "04-12-2020", "10-12-2020", "05-12-2020", TaskTypes.DEVELOPERS_TASK).getData();
        developersTasks.add(developersTask);
        instance.insertDevelopersTask(developersTasks, true);
        List<DevelopersTask> taskList = instance.select(DevelopersTask.class);
        log.debug(taskList);
        instance.deleteDevelopersTask(developersTask.getId());
        List<DevelopersTask> editedDevelopersTaskList = instance.select(DevelopersTask.class);
        log.debug(editedDevelopersTaskList);
        Assertions.assertEquals(Fail, instance.getDevelopersTaskById(12).getStatus());
    }

    @Test
    public void deleteDevelopersTaskFail() {
        instance.deleteDevelopersTask(12);
        Assertions.assertEquals(Complete, instance.deleteDevelopersTask(12).getStatus());
    }

    @Test
    public void deleteTestersTaskSuccess() {
        List<TestersTask> testersTasks = new ArrayList<>();
        TestersTask testersTask = (TestersTask) instance.createTask(12, "Descript", 14553.0, getScrum(), TypeOfCompletion.DEVELOPING, getListEmployee(), "04-12-2020", "10-12-2020", "05-12-2020", TaskTypes.TESTERS_TASK).getData();
        testersTasks.add(testersTask);
        instance.insertTestersTask(testersTasks, true);
        List<TestersTask> taskList = instance.select(TestersTask.class);
        log.debug(taskList);
        instance.deleteTestersTask(testersTask.getId());
        List<TestersTask> editedTestersTaskList = instance.select(TestersTask.class);
        log.debug(editedTestersTaskList);
        Assertions.assertEquals(Fail, instance.getTestersTaskById(12).getStatus());
    }

    @Test
    public void deleteTestersTaskFail() {
        instance.deleteTestersTask(12);
        Assertions.assertEquals(Complete, instance.deleteTestersTask(12).getStatus());
    }

    @Test
    public void deleteEmployeeSuccess() {
        List<Employee> employees = new ArrayList<>();
        Employee employee = instance.createEmployee(21, "Test_Employee_21", "Employee_sec_name", "Employee_Login", "admin", "employee@sfedu.ru", "Employee_personal_token", "FullStack", TypeOfEmployee.Employee).getData();
        employees.add(employee);
        instance.insertEmployee(employees, true);
        log.info(instance.getEmployeeById(21).getData());
        Assertions.assertEquals(Complete, instance.deleteEmployee(21).getStatus());
    }

    @Test
    public void deleteEmployeeFail() {
        instance.deleteEmployee(12);
        Assertions.assertEquals(Complete, instance.deleteEmployee(12).getStatus());
    }

    @Test
    public void deleteDeveloperSuccess() {
        List<Developer> developers = new ArrayList<>();
        Developer developer = (Developer) instance.createEmployee(21, "Test_Employee_21", "Employee_sec_name", "Employee_Login", "admin", "employee@sfedu.ru", "Employee_personal_token", "FullStack", TypeOfEmployee.Developer).getData();
        developers.add(developer);
        instance.insertDeveloper(developers, true);
        log.info(instance.getEmployeeById(21).getData());
        Assertions.assertEquals(Complete, instance.deleteEmployee(21).getStatus());
    }

    @Test
    public void deleteDeveloperFail() {
        instance.deleteDeveloper(12);
        Assertions.assertEquals(Complete, instance.deleteDeveloper(12).getStatus());
    }

    @Test
    public void deleteTesterSuccess() {
        List<Tester> testers = new ArrayList<>();
        Tester tester = (Tester) instance.createEmployee(21, "Test_Employee_21", "Employee_sec_name", "Employee_Login", "admin", "employee@sfedu.ru", "Employee_personal_token", "FullStack", TypeOfEmployee.Tester).getData();
        testers.add(tester);
        instance.insertTester(testers, true);
        log.info(instance.getEmployeeById(21).getData());
        Assertions.assertEquals(Complete, instance.deleteEmployee(21).getStatus());
    }

    @Test
    public void deleteTesterFail() {
        instance.deleteTester(12);
        Assertions.assertEquals(Complete, instance.deleteTester(12).getStatus());
    }

    @Test
    public void deleteProjectSuccess() {
        List<Project> projectList = new ArrayList<>();
        Project project = instance.createProject(8, "TestProject7", "05-12-2020", getListTask()).getData();
        projectList.add(project);
        instance.insertProject(projectList, true);
        List<Project> list = instance.select(Project.class);
        log.debug(list);
        instance.deleteProject(8);
        List<Project> list1 = instance.select(Project.class);
        log.debug(list1);
        Outcomes o = instance.getProjectByProjectID(8).getStatus();
        Assertions.assertEquals(Fail, o);
    }

    @Test
    public void deleteProjectFail() {
        instance.deleteProject(12);
        Assertions.assertEquals(Complete, instance.deleteProject(12).getStatus());
    }

    @Test
    public void updateTaskSuccess() {
        Task task = instance.createTask(2, "Description_2", 14553.0, getScrum(), TypeOfCompletion.DEVELOPING, getListEmployee(), "04-12-2020", "10-12-2020", "05-12-2020", TaskTypes.BASE_TASK).getData();
        instance.updateTask(task);
        Assertions.assertEquals("Description_2", instance.getTaskById(2).getData().getTaskDescription());
    }

    @Test
    public void updateTaskTaskFail() {
        Task task = instance.createTask(100, "Description_2", 14553.0, getScrum(), TypeOfCompletion.DEVELOPING, getListEmployee(), "04-12-2020", "10-12-2020", "05-12-2020", TaskTypes.BASE_TASK).getData();
        instance.updateTask(task);
        Assertions.assertEquals(Fail, instance.updateTask(task).getStatus());
    }

    @Test
    public void updateDevelopersTaskSuccess() {
        DevelopersTask developersTask = (DevelopersTask) instance.createTask(2, "Description_2", 14553.0, getScrum(), TypeOfCompletion.DEVELOPING, getListEmployee(), "04-12-2020", "10-12-2020", "05-12-2020", TaskTypes.DEVELOPERS_TASK).getData();
        instance.updateDevelopersTask(developersTask);
        Assertions.assertEquals("Description_2", instance.getDevelopersTaskById(2).getData().getTaskDescription());
    }

    @Test
    public void updateDevelopersTaskFail() {
        DevelopersTask developersTask = (DevelopersTask) instance.createTask(100, "Description_2", 14553.0, getScrum(), TypeOfCompletion.DEVELOPING, getListEmployee(), "04-12-2020", "10-12-2020", "05-12-2020", TaskTypes.DEVELOPERS_TASK).getData();
        instance.updateDevelopersTask(developersTask);
        Assertions.assertEquals(Fail, instance.updateDevelopersTask(developersTask).getStatus());
    }

    @Test
    public void updateTestersTaskSuccess() {
        TestersTask testersTask = (TestersTask) instance.createTask(2, "Description_2", 14553.0, getScrum(), TypeOfCompletion.DEVELOPING, getListEmployee(), "04-12-2020", "10-12-2020", "05-12-2020", TaskTypes.TESTERS_TASK).getData();
        instance.updateTestersTask(testersTask);
        Assertions.assertEquals("Description_2", instance.getTestersTaskById(2).getData().getTaskDescription());
    }

    @Test
    public void updateTestersTaskFail() {
        TestersTask testersTask = (TestersTask) instance.createTask(100, "Description_2", 14553.0, getScrum(), TypeOfCompletion.DEVELOPING, getListEmployee(), "04-12-2020", "10-12-2020", "05-12-2020", TaskTypes.TESTERS_TASK).getData();
        instance.updateTestersTask(testersTask);
        Assertions.assertEquals(Fail, instance.updateTestersTask(testersTask).getStatus());
    }

    @Test
    public void updateEmployeeSuccess() {
        Employee employee = instance.createEmployee(2, "Test_Employee_2", "Employee_sec_name", "Employee_Login", "admin", "employee@sfedu.ru", "Employee_personal_token", "FullStack", TypeOfEmployee.Employee).getData();
        instance.updateEmployee(employee);
        Assertions.assertEquals("Test_Employee_2", instance.getEmployeeById(2).getData().getFirstName());
    }

    @Test
    public void updateEmployeeFail() {
        Employee employee = instance.createEmployee(100, "Test_Employee_2", "Employee_sec_name", "Employee_Login", "admin", "employee@sfedu.ru", "Employee_personal_token", "FullStack", TypeOfEmployee.Employee).getData();
        instance.updateEmployee(employee);
        Assertions.assertEquals(Fail, instance.updateEmployee(employee).getStatus());
    }

    @Test
    public void updateDeveloperSuccess() {
        Developer developer = (Developer) instance.createEmployee(2, "Test_Employee_2", "Employee_sec_name", "Employee_Login", "admin", "employee@sfedu.ru", "Employee_personal_token", "FullStack", TypeOfEmployee.Developer).getData();
        instance.updateDeveloper(developer);
        Assertions.assertEquals("Test_Employee_2", instance.getDeveloperById(2).getData().getFirstName());
    }

    @Test
    public void updateDeveloperFail() {
        Developer developer = (Developer) instance.createEmployee(100, "Test_Employee_2", "Employee_sec_name", "Employee_Login", "admin", "employee@sfedu.ru", "Employee_personal_token", "FullStack", TypeOfEmployee.Developer).getData();
        instance.updateDeveloper(developer);
        Assertions.assertEquals(Fail, instance.updateDeveloper(developer).getStatus());
    }

    @Test
    public void updateTesterSuccess() {
        Tester tester = (Tester) instance.createEmployee(2, "Test_Employee_2", "Employee_sec_name", "Employee_Login", "admin", "employee@sfedu.ru", "Employee_personal_token", "FullStack", TypeOfEmployee.Tester).getData();
        instance.updateTester(tester);
        Assertions.assertEquals("Test_Employee_2", instance.getTesterById(2).getData().getFirstName());
    }

    @Test
    public void updateTesterFail() {
        Tester tester = (Tester) instance.createEmployee(100, "Test_Employee_2", "Employee_sec_name", "Employee_Login", "admin", "employee@sfedu.ru", "Employee_personal_token", "FullStack", TypeOfEmployee.Tester).getData();
        instance.updateTester(tester);
        Assertions.assertEquals(Fail, instance.updateTester(tester).getStatus());
    }

    @Test
    public void updateZProjectSuccess() {
        Project project = instance.createProject(4, "PROJECT FOR TESTING", "05-12-2020", getListTask()).getData();
        instance.updateProject(project);
        log.debug(instance.getProjectByProjectID(4).getData());
        Assertions.assertEquals("PROJECT FOR TESTING", instance.getProjectByProjectID(4).getData().getTitle());
    }

    @Test
    public void updateZProjectFail() {
        Project project = instance.createProject(4, "PROJECT FOR TESTING", "05-12-2020", getListTask()).getData();
        instance.updateProject(project);
        log.debug(instance.getProjectByProjectID(4).getData());
        Assertions.assertNotEquals(100, instance.getProjectByProjectID(4).getData().getId());
    }

    @Test
    public void createTaskSuccess() {
        List<Task> task = new ArrayList<>();
        Task testTask = instance.createTask(12, "Descript", 14553.0, getScrum(), TypeOfCompletion.DEVELOPING, getListEmployee(), "04-12-2020", "10-12-2020", "05-12-2020", TaskTypes.BASE_TASK).getData();
        task.add(testTask);
        instance.insertTask(task, true);
        Assertions.assertEquals(12, instance.getTaskById(12).getData().getId());
    }

    @Test
    public void createTaskFail() {
        List<Task> task = new ArrayList<>();
        Task testTask = instance.createTask(12, "Descript", 14553.0, getScrum(), TypeOfCompletion.DEVELOPING, getListEmployee(), "04-12-2020", "10-12-2020", "05-12-2020", TaskTypes.BASE_TASK).getData();
        task.add(testTask);
        instance.insertTask(task, true);
        Assertions.assertNotEquals(14551.0, instance.getTaskById(12).getData().getMoney());
    }

    @Test
    public void getTasksSuccess() {
        Assertions.assertEquals(10, instance.getTasks(10).getData().getId());
    }

    @Test
    public void getTasksFail() {
        Assertions.assertEquals(Fail, instance.getTasks(30).getStatus());
    }

    @Test
    public void getAllTaskSuccess() {
        List<Task> taskList = instance.getAllTask();
        int size = taskList.size();
        Assertions.assertEquals(35, size);
    }

    @Test
    public void getAllTaskFail() {
        List<Task> taskList = instance.getAllTask();
        int size = taskList.size();
        Assertions.assertNotEquals(30, size);
    }

    @Test
    public void getTasksByUserSuccess() {
        Employee employee = instance.getEmployeeById(1).getData();
        List<Task> list = new ArrayList<>();
        List<Employee> list1 = getListEmployee();
        list1.add(employee);
        Task testTask = instance.createTask(11, "Descript", 14553.0, employee, TypeOfCompletion.DEVELOPING, list1, "04-12-2020", "10-12-2020", "05-12-2020", TaskTypes.BASE_TASK).getData();
        list.add(testTask);
        instance.insertTask(list, true);
        List<Task> taskList = instance.select(Task.class);
        log.info(taskList);
        log.info(instance.getTasksByUser(1, 11).getData());
        Assertions.assertEquals(Complete, instance.getTasksByUser(1, 11).getStatus());
    }

    @Test
    public void getTasksByUserFail() {
        log.info(instance.getTasksByUser(15, 10).getData());
        Assertions.assertEquals(Fail, instance.getTasksByUser(15, 10).getStatus());
    }

    @Test
    public void getTaskWorkerSuccess() {
        Employee employee = instance.getEmployeeById(1).getData();
        Outcomes o = instance.getTaskWorker(employee, 1).getStatus();
        log.debug(instance.getTaskWorker(employee, 1).getData());
        Assertions.assertEquals(Complete, o);
    }

    @Test
    public void getTaskWorkerFail() {
        Employee employee = instance.getEmployeeById(1).getData();
        Outcomes o = instance.getTaskWorker(employee, 15).getStatus();
        log.debug(instance.getTaskWorker(employee, 15).getData());
        Assertions.assertEquals(Fail, o);
    }

    @Test
    public void changeTaskStatusSuccess() {
        instance.changeTaskStatus(1, TypeOfCompletion.TESTING.toString());
        Task task = instance.getTaskById(1).getData();
        log.debug(task);
        Assertions.assertEquals(TypeOfCompletion.TESTING, task.getStatus());
    }

    @Test
    public void changeTaskStatusFail() {
        Task task = instance.getTaskById(1).getData();
        log.debug(task);
        Assertions.assertEquals(Fail, instance.changeTaskStatus(1, "").getStatus());
    }

    @Test
    public void calculateTaskCostSuccess() {
        Task testTask = instance.createTask(1, "Descript", 14553.0, getScrum(), TypeOfCompletion.DEVELOPING, getListEmployee(), "04-12-2020", "10-12-2020", "05-12-2020", TaskTypes.BASE_TASK).getData();
        log.debug(instance.calculateTaskCost(testTask).getData());
        Assertions.assertEquals(instance.calculateTaskCost(testTask).getData().longValue(), 87318.0);
    }

    @Test
    public void calculateTaskCostFail() {
        Task testTask = instance.createTask(1, "Descript", 14553.0, getScrum(), TypeOfCompletion.DEVELOPING, getListEmployee(), "04-12-2020", "10-12-2020", "05-12-2020", TaskTypes.BASE_TASK).getData();
        log.debug(instance.calculateTaskCost(testTask).getData());
        Assertions.assertNotEquals(instance.calculateTaskCost(testTask).getData(), 1.0);
    }

    @Test
    public void writeBaseTaskCommentSuccess() {
        List<Task> taskList = new ArrayList<>();
        Task task = instance.createTask(13, "Descript", 14553.0, getScrum(), TypeOfCompletion.DEVELOPING, getListEmployee(), "04-12-2020", "10-12-2020", "05-12-2020", TaskTypes.BASE_TASK).getData();
        taskList.add(task);
        instance.insertTask(taskList, true);
        instance.writeBaseTaskComment(13, "I am a custom task description");
        log.info(instance.getTaskById(13).getData().getTaskDescription());
        Assertions.assertEquals("I am a custom task description", instance.getTaskById(13).getData().getTaskDescription());
    }

    @Test
    public void writeBaseTaskCommentFail() {
        List<Task> taskList = new ArrayList<>();
        Task task = instance.createTask(14, "Descript", 14553.0, getScrum(), TypeOfCompletion.DEVELOPING, getListEmployee(), "04-12-2020", "10-12-2020", "05-12-2020", TaskTypes.BASE_TASK).getData();
        taskList.add(task);
        instance.insertTask(taskList, true);
        Assertions.assertEquals(Fail, instance.writeBaseTaskComment(14, "").getStatus());
    }

    @Test
    public void writeDevelopersTaskCommentSuccess() {
        List<DevelopersTask> taskList = new ArrayList<>();
        DevelopersTask developersTask = (DevelopersTask) instance.createTask(13, "Descript", 14553.0, getScrum(), TypeOfCompletion.DEVELOPING, getListEmployee(), "04-12-2020", "10-12-2020", "05-12-2020", TaskTypes.DEVELOPERS_TASK).getData();
        taskList.add(developersTask);
        instance.insertDevelopersTask(taskList, true);
        instance.writeDevelopersTaskComment(13, "I am a custom task description");
        log.info(instance.getDevelopersTaskById(13).getData().getTaskDescription());
        Assertions.assertEquals("I am a custom task description", instance.getDevelopersTaskById(13).getData().getTaskDescription());
    }

    @Test
    public void writeDevelopersTaskCommentFail() {
        List<DevelopersTask> taskList = new ArrayList<>();
        DevelopersTask developersTask = (DevelopersTask) instance.createTask(14, "Descript", 14553.0, getScrum(), TypeOfCompletion.DEVELOPING, getListEmployee(), "04-12-2020", "10-12-2020", "05-12-2020", TaskTypes.DEVELOPERS_TASK).getData();
        taskList.add(developersTask);
        instance.insertDevelopersTask(taskList, true);
        Assertions.assertEquals(Fail, instance.writeDevelopersTaskComment(14, "").getStatus());
    }

    @Test
    public void writeTestersTaskCommentSuccess() {
        List<TestersTask> taskList = new ArrayList<>();
        TestersTask testersTask = (TestersTask) instance.createTask(13, "Descript", 14553.0, getScrum(), TypeOfCompletion.DEVELOPING, getListEmployee(), "04-12-2020", "10-12-2020", "05-12-2020", TaskTypes.TESTERS_TASK).getData();
        taskList.add(testersTask);
        instance.insertTestersTask(taskList, true);
        instance.writeTestersTaskComment(13, "I am a custom task description");
        log.info(instance.getTestersTaskById(13).getData().getTaskDescription());
        Assertions.assertEquals("I am a custom task description", instance.getTestersTaskById(13).getData().getTaskDescription());
    }

    @Test
    public void writeTestersTaskCommentFail() {
        List<TestersTask> taskList = new ArrayList<>();
        TestersTask testersTask = (TestersTask) instance.createTask(14, "Descript", 14553.0, getScrum(), TypeOfCompletion.DEVELOPING, getListEmployee(), "04-12-2020", "10-12-2020", "05-12-2020", TaskTypes.TESTERS_TASK).getData();
        taskList.add(testersTask);
        instance.insertTestersTask(taskList, true);
        Assertions.assertEquals(Fail, instance.writeTestersTaskComment(14, "").getStatus());
    }

    @Test
    public void getTaskListByScrumMasterSuccess() {
        List<Task> taskList = new ArrayList<>();
        Task task = instance.createTask(15, "Descript", 14553.0, instance.getEmployeeById(1).getData(), TypeOfCompletion.DEVELOPING, getListEmployee(), "04-12-2020", "10-12-2020", "05-12-2020", TaskTypes.BASE_TASK).getData();
        taskList.add(task);
        instance.insertTask(taskList, true);
        log.info(instance.getTaskListByScrumMaster(1));
        Assertions.assertNotNull(instance.getTaskListByScrumMaster(1).getData());
    }

    @Test
    public void getTaskListByScrumMasterFail() {
        List<Task> taskList = new ArrayList<>();
        Task task = instance.createTask(15, "Descript", 14553.0, instance.getEmployeeById(1).getData(), TypeOfCompletion.DEVELOPING, getListEmployee(), "04-12-2020", "10-12-2020", "05-12-2020", TaskTypes.BASE_TASK).getData();
        taskList.add(task);
        instance.insertTask(taskList, true);
        log.info(instance.getTaskListByScrumMaster(1));
        Assertions.assertNotEquals(0, instance.getTaskListByScrumMaster(1).getData().get(0).getId());
    }

    @Test
    public void getDevelopersTaskListByScrumMasterSuccess() {
        List<DevelopersTask> taskList = new ArrayList<>();
        DevelopersTask task = (DevelopersTask) instance.createTask(15, "Descript", 14553.0, instance.getEmployeeById(1).getData(), TypeOfCompletion.DEVELOPING, getListEmployee(), "04-12-2020", "10-12-2020", "05-12-2020", TaskTypes.DEVELOPERS_TASK).getData();
        taskList.add(task);
        instance.insertDevelopersTask(taskList, true);
        log.info(instance.getDevelopersTaskListByScrumMaster(1));
        Assertions.assertNotNull(instance.getDevelopersTaskListByScrumMaster(1).getData());
    }

    @Test
    public void getDevelopersTaskListByScrumMasterFail() {
        List<DevelopersTask> taskList = new ArrayList<>();
        DevelopersTask task = (DevelopersTask) instance.createTask(15, "Descript", 14553.0, instance.getEmployeeById(1).getData(), TypeOfCompletion.DEVELOPING, getListEmployee(), "04-12-2020", "10-12-2020", "05-12-2020", TaskTypes.DEVELOPERS_TASK).getData();
        taskList.add(task);
        instance.insertDevelopersTask(taskList, true);
        log.info(instance.getDevelopersTaskListByScrumMaster(1));
        Assertions.assertNotEquals(0, instance.getDevelopersTaskListByScrumMaster(1).getData().get(0).getId());
    }

    @Test
    public void getTestersTaskListByScrumMasterSuccess() {
        List<TestersTask> taskList = new ArrayList<>();
        TestersTask task = (TestersTask) instance.createTask(15, "Descript", 14553.0, instance.getEmployeeById(1).getData(), TypeOfCompletion.DEVELOPING, getListEmployee(), "04-12-2020", "10-12-2020", "05-12-2020", TaskTypes.TESTERS_TASK).getData();
        taskList.add(task);
        instance.insertTestersTask(taskList, true);
        log.info(instance.getTestersTaskListByScrumMaster(1));
        Assertions.assertNotNull(instance.getTestersTaskListByScrumMaster(1).getData());
    }

    @Test
    public void getTestersTaskListByScrumMasterFail() {
        List<TestersTask> taskList = new ArrayList<>();
        TestersTask task = (TestersTask) instance.createTask(15, "Descript", 14553.0, instance.getEmployeeById(1).getData(), TypeOfCompletion.DEVELOPING, getListEmployee(), "04-12-2020", "10-12-2020", "05-12-2020", TaskTypes.TESTERS_TASK).getData();
        taskList.add(task);
        instance.insertTestersTask(taskList, true);
        log.info(instance.getTestersTaskListByScrumMaster(1));
        Assertions.assertNotEquals(0, instance.getTestersTaskListByScrumMaster(1).getData().get(0).getId());

    }

    @Test
    public void createProjectSuccess() {
        Outcomes o = instance.createProject(5, "TestProject", "05-12-2020", getListTask()).getStatus();
        List<Project> list = instance.select(Project.class);
        log.debug(list);
        Assertions.assertEquals(Complete, o);
    }

    @Test
    public void createProjectFail() {
        Outcomes o = instance.createProject(5, "", "05-12-2020", getListTask()).getStatus();
        List<Project> list = instance.select(Project.class);
        log.debug(list);
        Assertions.assertEquals(Fail, o);
    }


    @Test
    public void getProjectByIdSuccess() {
        Employee testdeveloper = instance.getEmployeeById(1).getData();
        Outcomes o = instance.getProjectById(testdeveloper, 1).getStatus();
        log.debug(testdeveloper);
        Assertions.assertEquals(Complete, o);
    }

    @Test
    public void getProjectByIdFail() {
        Employee employee = instance.getEmployeeById(1).getData();
        Outcomes o = instance.getProjectById(employee, 100).getStatus();
        log.debug(o);
        Assertions.assertEquals(Empty, o);
    }

    @Test
    public void calculateProjectCostSuccess() {
        Project testProject = instance.getProjectByProjectID(1).getData();
        double cost = (double) instance.calculateProjectCost(testProject).getData();
        log.debug(cost);
        Assertions.assertNotEquals(cost, 5.0);
    }

    @Test
    public void calculateProjectCostFail() {
        Project testProject = instance.getProjectByProjectID(1).getData();
        double cost = (double) instance.calculateProjectCost(testProject).getData();
        log.debug(cost);
        Assertions.assertNotEquals(cost, 5.0);
    }

    @Test
    public void calculateProjectTimeSuccess() {
        Project testProject = instance.getProjectByProjectID(1).getData();
        Outcomes o = instance.calculateProjectTime(testProject).getStatus();
        Assertions.assertEquals(Complete, o);
    }

    @Test
    public void calculateProjectTimeFail() {
        Project testProject = instance.createProject(6, "TEST_PROJECT", "18-12-2020", getListTask()).getData();
        long time = instance.calculateProjectTime(testProject).getData();
        log.info(time);
        Assertions.assertNotNull(time);
    }

    @Test
    public void createEmployeeSuccess() {
        List<Tester> testerList = new ArrayList<>();
        Tester tester = (Tester) instance.createEmployee(11, "Vasily", "Vasilyev", "vas1ly", "vasyan", "VasLy@", "9bba8047-f0aa-473d-aef9-6905edcd3f99", "Team13", TypeOfEmployee.Tester).getData();
        testerList.add(tester);
        instance.insertTester(testerList, true);
        log.debug(tester);
        Assertions.assertEquals("Vasily", instance.getTesterById(11).getData().getFirstName());
    }

    @Test
    public void createEmployeeFail() {
        Outcomes outcomes = instance.createEmployee(11, "", "", "vas1ly", "vasyan", "VasLy@", "9bba8047-f0aa-473d-aef9-6905edcd3f99", "Team13", TypeOfEmployee.Tester).getStatus();
        Assertions.assertEquals(Fail, outcomes);
    }

    @Test
    public void getAllEmployeeSuccess() {
        List<Employee> list = instance.getAllEmployee();
        long size = list.size();
        Assertions.assertEquals(33, size);
    }

    @Test
    public void getAllEmployeeFail() {
        List<Employee> list = instance.getAllEmployee();
        long size = list.size();
        Assertions.assertNotEquals(15, size);
    }

    @Test
    public void getProjectListByScrummasterIdSuccess() {
        List<Task> task = new ArrayList<>();
        Task testTask = instance.createTask(17, "Descript", 14553.0, instance.getEmployeeById(1).getData(), TypeOfCompletion.DEVELOPING, getListEmployee(), "04-12-2020", "10-12-2020", "05-12-2020", TaskTypes.BASE_TASK).getData();
        Task testTask2 = instance.createTask(18, "Descript", 14553.0, instance.getEmployeeById(1).getData(), TypeOfCompletion.DEVELOPING, getListEmployee(), "04-12-2020", "10-12-2020", "05-12-2020", TaskTypes.BASE_TASK).getData();
        task.add(testTask);
        task.add(testTask2);
        List<Project> projectList = new ArrayList<>();
        Project project = instance.createProject(7, "TestProject8", "05-12-2020", task).getData();
        projectList.add(project);
        log.info(projectList);
        instance.insertProject(projectList, true);
        Assertions.assertNotNull(instance.getProjectListByScrummasterId(7).getData());
    }

    @Test
    public void getProjectListByScrummasterIdFail() {
        Assertions.assertNotEquals(0, instance.getProjectListByScrummasterId(1).getData());
    }

    @Test
    public void deleteEmployeeFromTaskSuccess() {
        Employee testdeveloper = instance.getEmployeeById(1).getData();
        Task testTask = instance.createTask(1, "Descript", 14553.0, getScrum(), TypeOfCompletion.DEVELOPING, getListEmployee(), "04-12-2020", "10-12-2020", "05-12-2020", TaskTypes.BASE_TASK).getData();
        log.debug(testTask);
        Outcomes outcomes = instance.deleteEmployeeFromTask(testTask, testdeveloper).getStatus();
        Assertions.assertEquals(outcomes, Complete);
    }

    @Test
    public void deleteEmployeeFromTaskFail() {
        Employee testdeveloper = instance.getEmployeeById(1).getData();
        Task testTask = instance.createTask(1, "Descript", 14553.0, getScrum(), TypeOfCompletion.DEVELOPING, getListEmployee(), "04-12-2020", "10-12-2020", "05-12-2020", TaskTypes.BASE_TASK).getData();
        log.debug(testTask);
        Outcomes outcomes = instance.deleteEmployeeFromTask(testTask, testdeveloper).getStatus();
        Assertions.assertNotEquals(outcomes, Fail);
    }

    @Test
    public void changeProfileInfoSuccess() {
        Employee testEmployee = instance.createEmployee(1, "Employee6", "Employee_sec_name", "Employee_Login", "admin", "employee@sfedu.ru", "Employee_personal_token", "FullStack", TypeOfEmployee.Employee).getData();
        log.debug(testEmployee);
        instance.changeProfileInfo(testEmployee);
        Assertions.assertEquals(instance.getEmployeeById(1).getData().getId(), testEmployee.getId());
    }

    @Test
    public void changeProfileInfoFail() {
        Employee testEmployee = instance.createEmployee(1, "Employee6", "Employee_sec_name", "Employee_Login", "admin", "employee@sfedu.ru", "Employee_personal_token", "FullStack", TypeOfEmployee.Developer).getData();
        log.debug(testEmployee);
        instance.changeProfileInfo(testEmployee);
        Assertions.assertNotEquals(instance.getEmployeeById(2).getData().getId(), testEmployee.getId());
    }

    @Test
    public void correctEmployeeParametersSuccess() {
        Employee test = instance.createEmployee(4, "Employee4", "Employee_sec_name", "Employee_Login", "admin", "employee@sfedu.ru", "Employee_personal_token", "FullStack", TypeOfEmployee.Employee).getData();
        instance.correctEmployeeParameters(test);
        Employee employee = instance.getEmployeeById(4).getData();
        log.debug(employee);
        Assertions.assertEquals("Employee4", employee.getFirstName());
    }

    @Test
    public void correctEmployeeParametersFail() {
        Employee test = instance.createEmployee(4, "Employee4", "Employee_sec_name", "Employee_Login", "admin", "employee@sfedu.ru", "Employee_personal_token", "FullStack", TypeOfEmployee.Employee).getData();
        instance.correctEmployeeParameters(test);
        Employee testemployee = instance.getEmployeeById(4).getData();
        log.debug(testemployee);
        Assertions.assertNotEquals("Ilya", testemployee.getFirstName());
    }

    @Test
    public void addEmployeeToTaskSuccess() {
        Employee testdeveloper = instance.getEmployeeById(1).getData();
        Task testTask = instance.createTask(1, "Descript", 14553.0, getScrum(), TypeOfCompletion.DEVELOPING, getListEmployee(), "04-12-2020", "10-12-2020", "05-12-2020", TaskTypes.BASE_TASK).getData();
        log.debug(testTask);
        Outcomes outcomes = instance.addEmployeeToTask(testTask, testdeveloper).getStatus();
        Assertions.assertEquals(outcomes, Complete);
    }

    @Test
    public void addEmployeeToTaskFail() {
        Employee testdeveloper = instance.getEmployeeById(1).getData();
        Task testTask = instance.createTask(1, "Descript", 14553.0, getScrum(), TypeOfCompletion.DEVELOPING, getListEmployee(), "04-12-2020", "10-12-2020", "05-12-2020", TaskTypes.BASE_TASK).getData();
        log.debug(testTask);
        Outcomes outcomes = instance.addEmployeeToTask(testTask, testdeveloper).getStatus();
        Assertions.assertNotEquals(outcomes, Fail);
    }

    @Test
    public void writeCommentSuccess() {
        instance.writeDevelopersTaskComment(1, "i wrote this comment now");
        DevelopersTask developersTask = instance.getDevelopersTaskById(1).getData();
        log.debug(developersTask);
        Assertions.assertEquals("i wrote this comment now", developersTask.getTaskDescription());
    }

    @Test
    public void writeCommentFail() {
        DevelopersTask developersTask = instance.getDevelopersTaskById(1).getData();
        log.debug(developersTask);
        Assertions.assertEquals(Fail, instance.writeDevelopersTaskComment(1, "").getStatus());
    }

    @Test
    public void getTaskInfoSuccess() {
        Outcomes o = instance.getDevelopersTaskById(1).getStatus();
        log.info(instance.getDevelopersTaskById(1).getData());
        Assertions.assertEquals(Complete, o);
    }

    @Test
    public void getTaskInfoFail() {
        Outcomes o = instance.getDevelopersTaskById(45).getStatus();
        log.info(instance.getDevelopersTaskById(45).getData());
        Assertions.assertEquals(Fail, o);
    }

    @Test
    public void getTaskListSuccess() {

    }

    @Test
    public void getTaskListFail() {
        List<Task> developersTask = instance.getTaskListByScrumMaster(100).getData();
        Outcomes o = instance.getTaskListByScrumMaster(100).getStatus();
        log.debug(developersTask);
        Assertions.assertNotEquals(o.toString(), Outcomes.NotFound.toString());
    }

    @Test
    public void getAnyTaskByTaskIdSuccess() {
        Task task = instance.getTaskById(1).getData();
        log.debug(task);
        Assertions.assertEquals(1, task.getId());
    }

    @Test
    public void getAnyTaskByTaskIdFail() {
        Task task = instance.getTaskById(1).getData();
        log.debug(task);
        Assertions.assertNotEquals(2, task.getId());
    }

    @Test
    public void getProjectListByIdSuccess() {
        Outcomes o = instance.getProjectListByScrummasterId(1).getStatus();
        log.debug(instance.getProjectListByScrummasterId(1).getStatus());
        Assertions.assertEquals(Complete, o);
    }

    @Test
    public void getProjectListByIdFail() {
        Assertions.assertNull(instance.getProjectListByScrummasterId(100).getData());
    }

    @Test
    public void getTaskInfoGenericSuccess() {
        log.error(instance.getTestersTaskById(1).getData());
        Assertions.assertEquals(1, instance.getTestersTaskById(1).getData().getId());
    }

    @Test
    public void getTaskInfoGenericFail() {
        log.error(instance.getTestersTaskById(1).getData());
        Assertions.assertNotEquals(2, instance.getTestersTaskById(1).getData().getId());
    }


    public static void addRecord() {
        List<Employee> employees = new ArrayList<>();
        List<Developer> developers = new ArrayList<>();
        List<Task> tasks = new ArrayList<>();
        List<Tester> testers = new ArrayList<>();
        List<DevelopersTask> developersTasks = new ArrayList<>();
        List<TestersTask> testersTasks = new ArrayList<>();
        List<Project> projects = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            Employee employee = new Employee();
            employee.setId(i);
            employee.setFirstName(firstName[i - 1]);
            employee.setLastName(lastName[i - 1]);
            employee.setLogin(login[i - 1]);
            employee.setPassword(password[i - 1]);
            employee.setEmail(email[i - 1]);
            employee.setToken(token[i - 1]);
            employee.setDepartment(department[i - 1]);
            employee.setTypeOfEmployee(TypeOfEmployee.Employee);
            employees.add(employee);
            instance.insertEmployee(employees, false);
        }
        for (int i = 1; i <= 10; i++) {
            Developer developer = new Developer();
            developer.setId(i);
            developer.setFirstName(firstName[i - 1]);
            developer.setLastName(lastName[i - 1]);
            developer.setLogin(login[i - 1]);
            developer.setPassword(password[i - 1]);
            developer.setEmail(email[i - 1]);
            developer.setToken(token[i - 1]);
            developer.setDepartment(department[i - 1]);
            developer.setTypeOfEmployee(TypeOfEmployee.Developer);
            developer.setStatus(TypeOfDevelopers.CUSTOM);
            developer.setProgrammingLanguage(ProgrammingLanguage.Custom);
            developers.add(developer);
            instance.insertDeveloper(developers, false);
        }
        for (int i = 1; i <= 10; i++) {
            Tester tester = new Tester();
            tester.setId(i);
            tester.setFirstName(firstName[i - 1]);
            tester.setLastName(lastName[i - 1]);
            tester.setLogin(login[i - 1]);
            tester.setPassword(password[i - 1]);
            tester.setEmail(email[i - 1]);
            tester.setToken(token[i - 1]);
            tester.setDepartment(department[i - 1]);
            tester.setTypeOfEmployee(TypeOfEmployee.Tester);
            tester.setStatus(TypeOfDevelopers.CUSTOM);
            tester.setProgrammingLanguage(ProgrammingLanguage.Custom);
            tester.setTypeOfTester(TypeOfTester.Custom);
            testers.add(tester);
            instance.insertTester(testers, false);
        }
        for (int i = 1; i <= 10; i++) {
            Task task = new Task();
            task.setId(i);
            task.setTaskDescription(taskDescription[i - 1]);
            task.setMoney(money[i - 1]);
            task.setScrumMaster(getScrum());
            task.setStatus(TypeOfCompletion.CUSTOM);
            task.setTeam(getListEmployee());
            task.setCreatedDate(createdDate[i - 1]);
            task.setDeadline(deadline[i - 1]);
            task.setLastUpdate(lastUpdate[i - 1]);
            task.setTaskType(TaskTypes.BASE_TASK);
            tasks.add(task);
            instance.insertTask(tasks, false);
        }
        for (int i = 1; i <= 10; i++) {
            DevelopersTask developerTask = new DevelopersTask();
            developerTask.setId(i);
            developerTask.setTaskDescription(taskDescription[i - 1]);
            developerTask.setMoney(money[i - 1]);
            developerTask.setScrumMaster(getScrum());
            developerTask.setStatus(TypeOfCompletion.CUSTOM);
            developerTask.setTeam(getListEmployee());
            developerTask.setCreatedDate(createdDate[i - 1]);
            developerTask.setDeadline(deadline[i - 1]);
            developerTask.setLastUpdate(lastUpdate[i - 1]);
            developerTask.setTaskType(TaskTypes.DEVELOPERS_TASK);
            developerTask.setDeveloperTaskType(DeveloperTaskType.DEVELOPMENT);
            developerTask.setDeveloperComments(Constants.BaseComment);
            developersTasks.add(developerTask);
            instance.insertDevelopersTask(developersTasks, false);
        }
        for (int i = 1; i <= 10; i++) {
            TestersTask testersTask = new TestersTask();
            testersTask.setId(i);
            testersTask.setTaskDescription(taskDescription[i - 1]);
            testersTask.setMoney(money[i - 1]);
            testersTask.setScrumMaster(getScrum());
            testersTask.setStatus(TypeOfCompletion.CUSTOM);
            testersTask.setTeam(getListEmployee());
            testersTask.setCreatedDate(createdDate[i - 1]);
            testersTask.setDeadline(deadline[i - 1]);
            testersTask.setLastUpdate(lastUpdate[i - 1]);
            testersTask.setTaskType(TaskTypes.TESTERS_TASK);
            testersTask.setBugStatus(BugStatus.IN_WORK);
            testersTask.setBugDescription(Constants.BaseComment);
            testersTasks.add(testersTask);
            instance.insertTestersTask(testersTasks, false);
        }
        for (int i = 1; i <= 4; i++) {
            Project project = new Project();
            project.setId(i);
            project.setTitle(title[i - 1]);
            project.setTakeIntoDevelopment(createdDate[i - 1]);
            project.setTask(getListTask());
            projects.add(project);
            instance.insertProject(projects, false);
        }
    }

    private static Employee getScrum() {
        List<Employee> listemployee = instance.select(Employee.class);
        int max = 9;
        int min = 0;
        return listemployee.get((int) ((Math.random() * ((max - min) + 1)) + min));
    }

    private static List<Employee> getListEmployee() {
        List<Employee> listemployee = instance.select(Employee.class);
        List<Developer> developers = instance.select(Developer.class);
        int max = 9;
        int min = 0;
        for (int i = 1; i <= 3; i++) {
            Developer developer = developers.get((int) ((Math.random() * ((max - min) + 1)) + min));
            listemployee.add(developer);
        }
        List<Tester> testers = instance.select(Tester.class);
        for (int i = 1; i <= 3; i++) {
            Tester tester = testers.get((int) ((Math.random() * ((max - min) + 1)) + min));
            listemployee.add(tester);
        }
        return listemployee;
    }

    private static List<Task> getListTask() {
        List<Task> listTask = instance.select(Task.class);
        List<DevelopersTask> developers = instance.select(DevelopersTask.class);
        int max = 9;
        int min = 0;
        for (int i = 1; i <= 3; i++) {
            DevelopersTask developersTask = developers.get((int) ((Math.random() * ((max - min) + 1)) + min));
            listTask.add(developersTask);
        }
        List<TestersTask> testers = instance.select(TestersTask.class);
        for (int i = 1; i <= 3; i++) {
            TestersTask testersTask = testers.get((int) ((Math.random() * ((max - min) + 1)) + min));
            listTask.add(testersTask);
        }
        return listTask;
    }
}