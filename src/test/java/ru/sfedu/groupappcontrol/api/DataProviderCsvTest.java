package ru.sfedu.groupappcontrol.api;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.*;
import ru.sfedu.groupappcontrol.models.*;
import ru.sfedu.groupappcontrol.Constants;
import ru.sfedu.groupappcontrol.models.enums.*;

import static ru.sfedu.groupappcontrol.api.Fill.*;
import static ru.sfedu.groupappcontrol.models.enums.Outcomes.*;

import java.util.*;


class DataProviderCsvTest {
    public static DataProvider instance = new DataProviderCsv();

    private static final Logger log = LogManager.getLogger(DataProviderCsvTest.class);


    DataProviderCsvTest() {
    }

    @BeforeAll
    static void setCSVEnv() {
        instance.deleteAllRecord();
        addRecord();
    }

    @Test
    public void getTaskByIdSuccess(){
        Assertions.assertEquals(1,instance.getTaskById(1).getData().getId());
    }

    @Test
    public void getTaskByIdFail() {
        Assertions.assertNotEquals(2,
                instance.getTaskById(1).getData().getId());
    }

    @Test
    public void getDevelopersTaskByIdSuccess() {
        Assertions.assertEquals(1,instance.getDevelopersTaskById(1).getData().getId());
    }

    @Test
    public void getDevelopersTaskByIdFail() {
        Assertions.assertNotEquals(2,instance.getDevelopersTaskById(1).getData().getId());
    }

    @Test
    public void getTestersTaskByIdSuccess() {
        Assertions.assertEquals(1,instance.getTestersTaskById(1).getData().getId());
    }

    @Test
    public void getTestersTaskByIdFail() {
        Assertions.assertNotEquals(2,instance.getTestersTaskById(1).getData().getId());
    }

    @Test
    public void getEmployeeByIdSuccess() {
        Assertions.assertEquals(1,
                instance.getEmployeeById(1).getData().getId());
    }

    @Test
    public void getEmployeeByIdFail() {
        Assertions.assertNotEquals(2,
                instance.getEmployeeById(1).getData().getId());
    }

    @Test
    public void getDeveloperByIdSuccess() {
        Assertions.assertEquals(1,
                instance.getDeveloperById(1).getData().getId());
    }

    @Test
    public void getDeveloperByIdFail() {
        Assertions.assertNotEquals(2,
                instance.getDeveloperById(1).getData().getId());
    }

    @Test
    public void getTesterByIdSuccess() {
        Assertions.assertEquals(1,
                instance.getTesterById(1).getData().getId());
    }

    @Test
    public void getTesterByIdFail() {
        Assertions.assertNotEquals(2,
                instance.getTesterById(1).getData().getId());
    }

    @Test
    public void getProjectByIDSuccess() {
        Assertions.assertEquals(1,
                instance.getProjectByID(1).getData().getId());
    }

    @Test
    public void getProjectByIDFail() {
        Assertions.assertNotEquals(2,
                 instance.getProjectByID(1).getData().getId());
    }

    @Test
    public void insertTaskSuccess() {
        Task testTask = instance.createTask(20,"Descript",14553.0,getScrum(),TypeOfCompletion.DEVELOPING,getListEmployee(),"04-12-2020","10-12-2020","05-12-2020",TaskTypes.BASE_TASK).getData();
        instance.insertTask(testTask);
        log.info(instance.getTaskById(20).getData().getId());
        Assertions.assertEquals(20,instance.getTaskById(20).getData().getId());
    }

    @Test
    public void insertTaskFail() {
        Task testTask = instance.createTask(1,"Descript",14553.0,getScrum(),TypeOfCompletion.DEVELOPING,getListEmployee(),"04-12-2020","10-12-2020","05-12-2020",TaskTypes.BASE_TASK).getData();
        instance.insertTask(testTask);
        log.info(instance.getTaskById(1).getData().getTaskDescription());
        Assertions.assertNotEquals("Descript",instance.getTaskById(1).getData().getTaskDescription());
    }

    @Test
    public void insertDevelopersTaskSuccess() {
        DevelopersTask developersTask = (DevelopersTask) instance.createTask(20,"Descript",14553.0,getScrum(),TypeOfCompletion.DEVELOPING,getListEmployee(),"04-12-2020","10-12-2020","05-12-2020",TaskTypes.DEVELOPERS_TASK).getData();
        instance.insertDevelopersTask(developersTask);
        log.info(instance.getDevelopersTaskById(20).getData().getId());
        Assertions.assertEquals(20,instance.getDevelopersTaskById(20).getData().getId());
    }

    @Test
    public void insertDevelopersTaskFail() {
        DevelopersTask developersTask = (DevelopersTask) instance.createTask(1,"Descript",14553.0,getScrum(),TypeOfCompletion.DEVELOPING,getListEmployee(),"04-12-2020","10-12-2020","05-12-2020",TaskTypes.DEVELOPERS_TASK).getData();
        instance.insertDevelopersTask(developersTask);
        log.info(instance.getDevelopersTaskById(1).getData().getTaskDescription());
        Assertions.assertNotEquals("Descript",instance.getDevelopersTaskById(1).getData().getTaskDescription());
    }

    @Test
    public void insertTestersTaskSuccess() {
        TestersTask testersTask = (TestersTask) instance.createTask(20,"Descript",14553.0,getScrum(),TypeOfCompletion.DEVELOPING,getListEmployee(),"04-12-2020","10-12-2020","05-12-2020",TaskTypes.TESTERS_TASK).getData();
        instance.insertTestersTask(testersTask);
        log.info(instance.getTestersTaskById(20).getData().getId());
        Assertions.assertEquals(20,instance.getTestersTaskById(20).getData().getId());
    }

    @Test
    public void insertTestersTaskFail() {
        TestersTask testersTask = (TestersTask) instance.createTask(1,"Descript",14553.0,getScrum(),TypeOfCompletion.DEVELOPING,getListEmployee(),"04-12-2020","10-12-2020","05-12-2020",TaskTypes.TESTERS_TASK).getData();
        instance.insertTestersTask(testersTask);
        log.info(instance.getTestersTaskById(1).getData().getTaskDescription());
        Assertions.assertNotEquals("Descript",instance.getTestersTaskById(1).getData().getTaskDescription());

    }

    @Test
    public void insertEmployeeSuccess() {
        Employee employee =  instance.createEmployee(20,"Test_Employee","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Employee).getData();
        instance.insertEmployee(employee);
        log.info(instance.getEmployeeById(20).getData().getId());
        Assertions.assertEquals(20,instance.getEmployeeById(20).getData().getId());
    }

    @Test
    public void insertEmployeeFail() {
        Employee employee =  instance.createEmployee(1,"Test_Employee","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Employee).getData();
        instance.insertEmployee(employee);
        log.info(instance.getEmployeeById(1).getData().getFirstName());
        Assertions.assertNotEquals("Test_Employee",instance.getEmployeeById(1).getData().getFirstName());
    }

    @Test
    public void insertDeveloperSuccess() {
        Developer developer = (Developer) instance.createEmployee(20,"Test_Employee","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer).getData();
        instance.insertDeveloper(developer);
        log.info(instance.getDeveloperById(20).getData().getId());
        Assertions.assertEquals(20,instance.getDeveloperById(20).getData().getId());
    }

    @Test
    public void insertDeveloperFail() {
        Developer developer = (Developer) instance.createEmployee(1,"Test_Employee","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer).getData();
        instance.insertDeveloper(developer);
        log.info(instance.getDeveloperById(1).getData().getFirstName());
        Assertions.assertNotEquals("Test_Employee",instance.getDeveloperById(1).getData().getFirstName());
    }

    @Test
    public void insertTesterSuccess() {
        Tester tester = (Tester) instance.createEmployee(20,"Test_Employee","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Tester).getData();
        instance.insertTester(tester);
        log.info(instance.getTesterById(20).getData().getId());
        Assertions.assertEquals(20,instance.getTesterById(20).getData().getId());
    }

    @Test
    public void insertTesterFail() {
        Tester tester = (Tester) instance.createEmployee(1,"Test_Employee","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Tester).getData();
        instance.insertTester(tester);
        log.info(instance.getTesterById(1).getData().getFirstName());
        Assertions.assertNotEquals("Test_Employee",instance.getTesterById(1).getData().getFirstName());
    }

    @Test
    public void insertFProjectSuccess(){
        Project project = instance.createProject(6,"TEST_PROJECT","18-12-2020",getListTask()).getData();
        instance.insertProject(project);
        Assertions.assertEquals("TEST_PROJECT",instance.getProjectByID(6).getData().getTitle());
    }

    @Test
    public void insertGProjectFail(){
        Project project = instance.createProject(6,"NEW_TEST_PROJECT","18-12-2020",getListTask()).getData();
        instance.insertProject(project);
        Assertions.assertNotEquals("NEW_TEST_PROJECT",instance.getProjectByID(6).getData().getTitle());
    }

    @Test
    public void deleteTaskSuccess() {
        Task testTask = instance.createTask(12,"Descript",14553.0,getScrum(),TypeOfCompletion.DEVELOPING,getListEmployee(),"04-12-2020","10-12-2020","05-12-2020",TaskTypes.BASE_TASK).getData();
        instance.insertTask(testTask);
        List<Task> taskList = ((DataProviderCsv) instance).select(Task.class);
        log.debug(taskList);
        instance.deleteTask(testTask.getId());
        List<Task> editedTaskList = ((DataProviderCsv) instance).select(Task.class);
        log.debug(editedTaskList);
        Assertions.assertEquals(Fail,instance.getTaskById(12).getStatus());
    }

    @Test
    public void deleteTaskFail() {
        instance.deleteTask(12);
        Assertions.assertEquals(Complete,instance.deleteTask(12).getStatus());
    }

    @Test
    public void deleteDevelopersTaskSuccess() {
        DevelopersTask developersTask = (DevelopersTask) instance.createTask(12,"Descript",14553.0,getScrum(),TypeOfCompletion.DEVELOPING,getListEmployee(),"04-12-2020","10-12-2020","05-12-2020",TaskTypes.DEVELOPERS_TASK).getData();
        instance.insertDevelopersTask(developersTask);
        List<DevelopersTask> taskList = ((DataProviderCsv) instance).select(DevelopersTask.class);
        log.debug(taskList);
        instance.deleteDevelopersTask(developersTask.getId());
        List<DevelopersTask> editedDevelopersTaskList = ((DataProviderCsv) instance).select(DevelopersTask.class);
        log.debug(editedDevelopersTaskList);
        Assertions.assertEquals(Fail,instance.getDevelopersTaskById(12).getStatus());
    }

    @Test
    public void deleteDevelopersTaskFail() {
        instance.deleteDevelopersTask(12);
        Assertions.assertEquals(Complete,instance.deleteDevelopersTask(12).getStatus());
    }

    @Test
    public void deleteTestersTaskSuccess() {
        TestersTask testersTask = (TestersTask) instance.createTask(12,"Descript",14553.0,getScrum(),TypeOfCompletion.DEVELOPING,getListEmployee(),"04-12-2020","10-12-2020","05-12-2020",TaskTypes.TESTERS_TASK).getData();
        instance.insertTestersTask(testersTask);
        List<TestersTask> taskList = ((DataProviderCsv) instance).select(TestersTask.class);
        log.debug(taskList);
        instance.deleteTestersTask(testersTask.getId());
        List<TestersTask> editedTestersTaskList = ((DataProviderCsv) instance).select(TestersTask.class);
        log.debug(editedTestersTaskList);
        Assertions.assertEquals(Fail,instance.getTestersTaskById(12).getStatus());
    }

    @Test
    public void deleteTestersTaskFail() {
        instance.deleteTestersTask(12);
        Assertions.assertEquals(Complete,instance.deleteTestersTask(12).getStatus());
    }

    @Test
    public void deleteEmployeeSuccess() {
        Employee employee =  instance.createEmployee(21,"Test_Employee_21","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Employee).getData();
        instance.insertEmployee(employee);
        log.info(instance.getEmployeeById(21).getData());
        Assertions.assertEquals(Complete,instance.deleteEmployee(21).getStatus());
    }

    @Test
    public void deleteEmployeeFail() {
        instance.deleteEmployee(12);
        Assertions.assertEquals(Complete,instance.deleteEmployee(12).getStatus());
    }

    @Test
    public void deleteDeveloperSuccess() {
        Developer developer =  (Developer) instance.createEmployee(21,"Test_Employee_21","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer).getData();
        instance.insertDeveloper(developer);
        log.info(instance.getEmployeeById(21).getData());
        Assertions.assertEquals(Complete,instance.deleteEmployee(21).getStatus());
    }

    @Test
    public void deleteDeveloperFail() {
        instance.deleteDeveloper(12);
        Assertions.assertEquals(Complete,instance.deleteDeveloper(12).getStatus());
    }

    @Test
    public void deleteTesterSuccess() {
        Tester tester =  (Tester) instance.createEmployee(21,"Test_Employee_21","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Tester).getData();
        instance.insertTester(tester);
        log.info(instance.getEmployeeById(21).getData());
        Assertions.assertEquals(Complete,instance.deleteEmployee(21).getStatus());
    }

    @Test
    public void deleteTesterFail() {
        instance.deleteTester(12);
        Assertions.assertEquals(Complete,instance.deleteTester(12).getStatus());
    }

    @Test
    public void deleteProjectSuccess() {
        Project project = instance.createProject(8,"TestProject7","05-12-2020",getListTask()).getData();
        instance.insertProject(project);
        List<Project> list = ((DataProviderCsv) instance).select(Project.class);
        log.debug(list);
        instance.deleteProject(8);
        List<Project> list1 = ((DataProviderCsv) instance).select(Project.class);
        log.debug(list1);
        Outcomes o = instance.getProjectByID(8).getStatus();
        Assertions.assertEquals(Fail,o);
    }

    @Test
    public void deleteProjectFail() {
        instance.deleteProject(12);
        Assertions.assertEquals(Complete,instance.deleteProject(12).getStatus());
    }

    @Test
    public void updateTaskSuccess(){
        Task task = instance.createTask(2,"Description_2",14553.0,getScrum(),TypeOfCompletion.DEVELOPING,getListEmployee(),"04-12-2020","10-12-2020","05-12-2020",TaskTypes.BASE_TASK).getData();
        instance.updateTask(task);
        Assertions.assertEquals("Description_2",instance.getTaskById(2).getData().getTaskDescription());
    }

    @Test
    public void updateTaskTaskFail() {
        Task task = instance.createTask(100,"Description_2",14553.0,getScrum(),TypeOfCompletion.DEVELOPING,getListEmployee(),"04-12-2020","10-12-2020","05-12-2020",TaskTypes.BASE_TASK).getData();
        instance.updateTask(task);
        Assertions.assertEquals(Fail,instance.updateTask(task).getStatus());
    }

    @Test
    public void updateDevelopersTaskSuccess() {
        DevelopersTask developersTask = (DevelopersTask) instance.createTask(2,"Description_2",14553.0,getScrum(),TypeOfCompletion.DEVELOPING,getListEmployee(),"04-12-2020","10-12-2020","05-12-2020",TaskTypes.DEVELOPERS_TASK).getData();
        instance.updateDevelopersTask(developersTask);
        Assertions.assertEquals("Description_2",instance.getDevelopersTaskById(2).getData().getTaskDescription());
    }

    @Test
    public void updateDevelopersTaskFail() {
        DevelopersTask developersTask = (DevelopersTask) instance.createTask(100,"Description_2",14553.0,getScrum(),TypeOfCompletion.DEVELOPING,getListEmployee(),"04-12-2020","10-12-2020","05-12-2020",TaskTypes.DEVELOPERS_TASK).getData();
        instance.updateDevelopersTask(developersTask);
        Assertions.assertEquals(Fail,instance.updateDevelopersTask(developersTask).getStatus());
    }

    @Test
    public void updateTestersTaskSuccess() {
        TestersTask testersTask = (TestersTask) instance.createTask(2,"Description_2",14553.0,getScrum(),TypeOfCompletion.DEVELOPING,getListEmployee(),"04-12-2020","10-12-2020","05-12-2020",TaskTypes.TESTERS_TASK).getData();
        instance.updateTestersTask(testersTask);
        Assertions.assertEquals("Description_2",instance.getTestersTaskById(2).getData().getTaskDescription());
    }

    @Test
    public void updateTestersTaskFail() {
        TestersTask testersTask = (TestersTask) instance.createTask(100,"Description_2",14553.0,getScrum(),TypeOfCompletion.DEVELOPING,getListEmployee(),"04-12-2020","10-12-2020","05-12-2020",TaskTypes.TESTERS_TASK).getData();
        instance.updateTestersTask(testersTask);
        Assertions.assertEquals(Fail,instance.updateTestersTask(testersTask).getStatus());
    }

    @Test
    public void updateEmployeeSuccess() {
        Employee employee =  instance.createEmployee(2,"Test_Employee_2","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Employee).getData();
        instance.updateEmployee(employee);
        Assertions.assertEquals("Test_Employee_2",instance.getEmployeeById(2).getData().getFirstName());
    }

    @Test
    public void updateEmployeeFail() {
        Employee employee =  instance.createEmployee(100,"Test_Employee_2","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Employee).getData();
        instance.updateEmployee(employee);
        Assertions.assertEquals(Fail,instance.updateEmployee(employee).getStatus());
    }

    @Test
    public void updateDeveloperSuccess() {
        Developer developer = (Developer) instance.createEmployee(2,"Test_Employee_2","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer).getData();
        instance.updateDeveloper(developer);
        Assertions.assertEquals("Test_Employee_2",instance.getDeveloperById(2).getData().getFirstName());
    }

    @Test
    public void updateDeveloperFail() {
        Developer developer = (Developer) instance.createEmployee(100,"Test_Employee_2","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer).getData();
        instance.updateDeveloper(developer);
        Assertions.assertEquals(Fail,instance.updateDeveloper(developer).getStatus());
    }

    @Test
    public void updateTesterSuccess() {
        Tester tester = (Tester) instance.createEmployee(2,"Test_Employee_2","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Tester).getData();
        instance.updateTester(tester);
        Assertions.assertEquals("Test_Employee_2",instance.getTesterById(2).getData().getFirstName());
    }

    @Test
    public void updateTesterFail() {
        Tester tester = (Tester) instance.createEmployee(100,"Test_Employee_2","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Tester).getData();
        instance.updateTester(tester);
        Assertions.assertEquals(Fail,instance.updateTester(tester).getStatus());
    }

    @Test
    public void updateProjectSuccess() {
        Project project = instance.createProject(5,"PROJECT FOR TESTING","05-12-2020",getListTask()).getData();
        instance.updateProject(project);
        log.debug(instance.getProjectByID(5).getData());
        Assertions.assertEquals("PROJECT FOR TESTING",instance.getProjectByID(4).getData().getTitle());
    }

    @Test
    public void updateZProjectFail() {
        Project project = instance.createProject(4,"PROJECT FOR TESTING","05-12-2020",getListTask()).getData();
        instance.updateProject(project);
        log.debug(instance.getProjectByID(4).getData());
        Assertions.assertNotEquals(100, instance.getProjectByID(4).getData().getId());
    }

    @Test
    public void createTaskSuccess() {
        Task testTask = instance.createTask(12,"Descript",14553.0,getScrum(),TypeOfCompletion.DEVELOPING,getListEmployee(),"04-12-2020","10-12-2020","05-12-2020",TaskTypes.BASE_TASK).getData();
        instance.insertTask(testTask);
        Assertions.assertEquals(12,instance.getTaskById(12).getData().getId());
    }

    @Test
    public void createTaskFail() {
        Task testTask = instance.createTask(12,"Descript",14553.0,getScrum(),TypeOfCompletion.DEVELOPING,getListEmployee(),"04-12-2020","10-12-2020","05-12-2020",TaskTypes.BASE_TASK).getData();
        instance.insertTask(testTask);
        Assertions.assertNotEquals(14551.0,instance.getTaskById(12).getData().getMoney());
    }

    @Test
    public void getTasksSuccess() {
        Assertions.assertEquals(10,
                instance.getTasks(10).getData().getId());
    }

    @Test
    public void getTasksFail() {
        Assertions.assertEquals(Fail,
                instance.getTasks(30).getStatus());
    }

    @Test
    public void getAllTaskSuccess() {
        List<Task> taskList = instance.getAllTask();
        int size=taskList.size();
        Assertions.assertEquals(35,size);
    }

    @Test
    public void getAllTaskFail() {
        List<Task> taskList = instance.getAllTask();
        int size=taskList.size();
        Assertions.assertNotEquals(30,size);
    }

    @Test
    public void getTasksByUserSuccess() {
        Employee employee = instance.getEmployeeById(1).getData();
        List<Employee> list1 = getListEmployee();
        list1.add(employee);
        Task testTask =  instance.createTask(11,"Descript",14553.0,employee,TypeOfCompletion.DEVELOPING,list1,"04-12-2020","10-12-2020","05-12-2020",TaskTypes.BASE_TASK).getData();
        instance.insertTask(testTask);
        List<Task> taskList= ((DataProviderCsv) instance).select(Task.class);
        log.info(taskList);
        log.info(instance.getTasksByUser(1,11).getData());
        Assertions.assertEquals(Complete,instance.getTasksByUser(1,11).getStatus());
    }

    @Test
    public void getTasksByUserFail() {
        log.info(instance.getTasksByUser(15,10).getData());
        Assertions.assertEquals(Fail,instance.getTasksByUser(15,10).getStatus());
    }

    @Test
    public void changeTaskStatusSuccess() {
        instance.changeTaskStatus(1, TypeOfCompletion.TESTING.toString());
        Task task =  instance.getTaskById(1).getData();
        log.debug(task);
        Assertions.assertEquals(TypeOfCompletion.TESTING, task.getStatus());
    }

    @Test
    public void changeTaskStatusFail() {
        Task task = instance.getTaskById(1).getData();
        log.debug(task);
        Assertions.assertEquals(Fail,instance.changeTaskStatus(1,"").getStatus());
    }

    @Test
    public void calculateTaskCostSuccess() {
        Task testTask = instance.createTask(1,"Descript",14553.0,getScrum(),TypeOfCompletion.DEVELOPING,getListEmployee(),"04-12-2020","10-12-2020","05-12-2020",TaskTypes.BASE_TASK).getData();
        log.debug(instance.calculateTaskCost(testTask).getData());
        Assertions.assertEquals(instance.calculateTaskCost(testTask).getData().longValue(), 87318.0);
    }

    @Test
    public void calculateTaskCostFail() {
        Task testTask = instance.createTask(1,"Descript",14553.0,getScrum(),TypeOfCompletion.DEVELOPING,getListEmployee(),"04-12-2020","10-12-2020","05-12-2020",TaskTypes.BASE_TASK).getData();
        log.debug(instance.calculateTaskCost(testTask).getData());
        Assertions.assertNotEquals(instance.calculateTaskCost(testTask).getData(),1.0);
    }

    @Test
    public void writeBaseTaskCommentSuccess() {
        Task task = instance.createTask(13,"Descript",14553.0,getScrum(),TypeOfCompletion.DEVELOPING,getListEmployee(),"04-12-2020","10-12-2020","05-12-2020",TaskTypes.BASE_TASK).getData();
        instance.insertTask(task);
        instance.writeBaseTaskComment(13,"I am a custom task description");
        log.info(instance.getTaskById(13).getData().getTaskDescription());
        Assertions.assertEquals("I am a custom task description",instance.getTaskById(13).getData().getTaskDescription());
    }

    @Test
    public void writeBaseTaskCommentFail() {
        Task task = instance.createTask(14,"Descript",14553.0,getScrum(),TypeOfCompletion.DEVELOPING,getListEmployee(),"04-12-2020","10-12-2020","05-12-2020",TaskTypes.BASE_TASK).getData();
        instance.insertTask(task);
        Assertions.assertEquals(Fail,instance.writeBaseTaskComment(14,"").getStatus());
    }

    @Test
    public void writeDevelopersTaskCommentSuccess() {
        DevelopersTask developersTask = (DevelopersTask) instance.createTask(13,"Descript",14553.0,getScrum(),TypeOfCompletion.DEVELOPING,getListEmployee(),"04-12-2020","10-12-2020","05-12-2020",TaskTypes.DEVELOPERS_TASK).getData();
        instance.insertDevelopersTask(developersTask);
        instance.writeDevelopersTaskComment(13,"I am a custom task description");
        log.info(instance.getDevelopersTaskById(13).getData().getTaskDescription());
        Assertions.assertEquals("I am a custom task description",instance.getDevelopersTaskById(13).getData().getTaskDescription());
    }

    @Test
    public void writeDevelopersTaskCommentFail() {
        DevelopersTask developersTask = (DevelopersTask) instance.createTask(14,"Descript",14553.0,getScrum(),TypeOfCompletion.DEVELOPING,getListEmployee(),"04-12-2020","10-12-2020","05-12-2020",TaskTypes.DEVELOPERS_TASK).getData();
        instance.insertDevelopersTask(developersTask);
        Assertions.assertEquals(Fail,instance.writeDevelopersTaskComment(14,"").getStatus());
    }

    @Test
    public void writeTestersTaskCommentSuccess() {
        TestersTask testersTask = (TestersTask) instance.createTask(13,"Descript",14553.0,getScrum(),TypeOfCompletion.DEVELOPING,getListEmployee(),"04-12-2020","10-12-2020","05-12-2020",TaskTypes.TESTERS_TASK).getData();
        instance.insertTestersTask(testersTask);
        instance.writeTestersTaskComment(13,"I am a custom task description");
        log.info(instance.getTestersTaskById(13).getData().getTaskDescription());
        Assertions.assertEquals("I am a custom task description",instance.getTestersTaskById(13).getData().getTaskDescription());
    }

    @Test
    public void writeTestersTaskCommentFail() {
        TestersTask testersTask = (TestersTask) instance.createTask(14,"Descript",14553.0,getScrum(),TypeOfCompletion.DEVELOPING,getListEmployee(),"04-12-2020","10-12-2020","05-12-2020",TaskTypes.TESTERS_TASK).getData();
        instance.insertTestersTask(testersTask);
        Assertions.assertEquals(Fail,instance.writeTestersTaskComment(14,"").getStatus());
    }

    @Test
    public void getTaskListByScrumMasterSuccess() {
        Task task = instance.createTask(15,"Descript",14553.0,instance.getEmployeeById(1).getData(),TypeOfCompletion.DEVELOPING,getListEmployee(),"04-12-2020","10-12-2020","05-12-2020",TaskTypes.BASE_TASK).getData();
        instance.insertTask(task);
        log.info(instance.getTaskListByScrumMaster(1));
        Assertions.assertNotNull(instance.getTaskListByScrumMaster(1).getData());
    }

    @Test
    public void getTaskListByScrumMasterFail() {
        Task task = instance.createTask(15,"Descript",14553.0,instance.getEmployeeById(1).getData(),TypeOfCompletion.DEVELOPING,getListEmployee(),"04-12-2020","10-12-2020","05-12-2020",TaskTypes.BASE_TASK).getData();
        instance.insertTask(task);
        log.info(instance.getTaskListByScrumMaster(1));
        Assertions.assertNotEquals(0,instance.getTaskListByScrumMaster(1).getData().get(0).getId());
    }

    @Test
    public void getDevelopersTaskListByScrumMasterSuccess() {
        DevelopersTask task = (DevelopersTask) instance.createTask(15,"Descript",14553.0,instance.getEmployeeById(1).getData(),TypeOfCompletion.DEVELOPING,getListEmployee(),"04-12-2020","10-12-2020","05-12-2020",TaskTypes.DEVELOPERS_TASK).getData();
        instance.insertDevelopersTask(task);
        log.info(instance.getDevelopersTaskListByScrumMaster(1));
        Assertions.assertNotNull(instance.getDevelopersTaskListByScrumMaster(1).getData());
    }

    @Test
    public void getDevelopersTaskListByScrumMasterFail() {
        DevelopersTask task = (DevelopersTask)  instance.createTask(15,"Descript",14553.0,instance.getEmployeeById(1).getData(),TypeOfCompletion.DEVELOPING,getListEmployee(),"04-12-2020","10-12-2020","05-12-2020",TaskTypes.DEVELOPERS_TASK).getData();
        instance.insertDevelopersTask(task);
        log.info(instance.getDevelopersTaskListByScrumMaster(1));
        Assertions.assertNotEquals(0,instance.getDevelopersTaskListByScrumMaster(1).getData().get(0).getId());
    }

    @Test
    public void getTestersTaskListByScrumMasterSuccess() {
        TestersTask task = (TestersTask) instance.createTask(15,"Descript",14553.0,instance.getEmployeeById(1).getData(),TypeOfCompletion.DEVELOPING,getListEmployee(),"04-12-2020","10-12-2020","05-12-2020",TaskTypes.TESTERS_TASK).getData();
        instance.insertTestersTask(task);
        log.info(instance.getTestersTaskListByScrumMaster(1));
        Assertions.assertNotNull(instance.getTestersTaskListByScrumMaster(1).getData());
    }

    @Test
    public void getTestersTaskListByScrumMasterFail() {
        TestersTask task = (TestersTask)  instance.createTask(15,"Descript",14553.0,instance.getEmployeeById(1).getData(),TypeOfCompletion.DEVELOPING,getListEmployee(),"04-12-2020","10-12-2020","05-12-2020",TaskTypes.TESTERS_TASK).getData();
        instance.insertTestersTask(task);
        log.info(instance.getTestersTaskListByScrumMaster(1));
        Assertions.assertNotEquals(0,instance.getTestersTaskListByScrumMaster(1).getData().get(0).getId());

    }

    @Test
    public void createProjectSuccess() {
        Outcomes o = instance.createProject(5,"TestProject","05-12-2020",getListTask()).getStatus();
        List<Project> list = ((DataProviderCsv) instance).select(Project.class);
        log.debug(list);
        Assertions.assertEquals(Complete,o);
    }

    @Test
    public void createProjectFail() {
        Outcomes o = instance.createProject(5,"","05-12-2020",getListTask()).getStatus();
        List<Project> list = ((DataProviderCsv) instance).select(Project.class);
        log.debug(list);
        Assertions.assertEquals(Fail,o);
    }

    @Test
    public void getProjectByIdSuccess(){
        Employee testdeveloper = instance.getEmployeeById(1).getData();
        Outcomes o = instance.getProjectById(testdeveloper.getId(),1).getStatus();
        log.debug(testdeveloper);
        Assertions.assertEquals(Complete,o);
    }

    @Test
    public void getProjectByIdFail() {
        Employee employee =  instance.getEmployeeById(1).getData();
        Outcomes o = instance.getProjectById(employee.getId(),100).getStatus();
        log.debug(o);
        Assertions.assertEquals(Empty,o);
    }

    @Test
    public void calculateProjectCostSuccess() {
        Project testProject= instance.getProjectByID(1).getData();
        double cost = (double) instance.calculateProjectCost(testProject).getData();
        log.debug(cost);
        Assertions.assertNotEquals(cost,5.0);
    }

    @Test
    public void calculateProjectCostFail() {
        Project testProject= instance.getProjectByID(1).getData();
        double cost = (double) instance.calculateProjectCost(testProject).getData();
        log.debug(cost);
        Assertions.assertNotEquals(cost,5.0);
    }

    @Test
    public void calculateProjectTimeSuccess() {
        Project testProject = instance.getProjectByID(1).getData();
        Outcomes o = instance.calculateProjectTime(testProject).getStatus();
        Assertions.assertEquals(Complete,o);
    }

    @Test
    public void calculateProjectTimeFail() {
        Project testProject =  instance.createProject(6,"TEST_PROJECT","18-12-2020",getListTask()).getData();
        long time = instance.calculateProjectTime(testProject).getData();
        log.info(time);
        Assertions.assertNotEquals(0,time);
    }

    @Test
    public void createEmployeeSuccess() {
        Tester tester = (Tester) instance.createEmployee(11,"Vasily","Vasilyev","vas1ly","vasyan","VasLy@","9bba8047-f0aa-473d-aef9-6905edcd3f99","Team13",TypeOfEmployee.Tester).getData();
        instance.insertTester(tester);
        log.debug(tester);
        Assertions.assertEquals("Vasily",instance.getTesterById(11).getData().getFirstName());
    }

    @Test
    public void createEmployeeFail() {
        Outcomes outcomes= instance.createEmployee(11,"","","vas1ly","vasyan","VasLy@","9bba8047-f0aa-473d-aef9-6905edcd3f99","Team13",TypeOfEmployee.Tester).getStatus();
        Assertions.assertEquals(Fail,outcomes);
    }

    @Test
    public void getAllEmployeeSuccess() {
        List<Employee> list = instance.getAllEmployee();
        long size = list.size();
        Assertions.assertEquals(33,size);
    }

    @Test
    public void getAllEmployeeFail() {
        List<Employee> list = instance.getAllEmployee();
        long size = list.size();
        Assertions.assertNotEquals(15,size);
    }

    @Test
    public void getProjectListByScrummasterIdSuccess() {
        List<Task> task = new ArrayList<>();
        Task testTask = instance.createTask(17,"Descript",14553.0,instance.getEmployeeById(1).getData(),TypeOfCompletion.DEVELOPING,getListEmployee(),"04-12-2020","10-12-2020","05-12-2020",TaskTypes.BASE_TASK).getData();
        Task testTask2 = instance.createTask(18,"Descript",14553.0,instance.getEmployeeById(1).getData(),TypeOfCompletion.DEVELOPING,getListEmployee(),"04-12-2020","10-12-2020","05-12-2020",TaskTypes.BASE_TASK).getData();
        task.add(testTask);
        task.add(testTask2);
        Project project = instance.createProject(7,"TestProject8","05-12-2020",task).getData();
        log.info(project);
        instance.insertProject(project);
        Assertions.assertNotNull(instance.getProjectListByScrummasterId(1).getData());
    }

    @Test
    public void getProjectListByScrummasterIdFail() {
        Assertions.assertNotEquals(0,instance.getProjectListByScrummasterId(1).getData().get(0));
    }

    @Test
    public void getAnyTaskByTaskIdSuccess() {
        Task task = instance.getTaskById(1).getData();
        log.debug(task);
        Assertions.assertEquals(1,task.getId());
    }

    @Test
    public void getAnyTaskByTaskIdFail() {
        Task task = instance.getTaskById(1).getData();
        log.debug(task);
        Assertions.assertNotEquals(2,task.getId());
    }

    @Test
    public void getScrumMasterTaskListSuccess(){
        Assertions.assertNotNull(instance.getScrumMasterTaskList(1,TaskTypes.BASE_TASK));
    }

    @Test
    public void getScrumMasterTaskListFail(){
        Assertions.assertEquals(Outcomes.Fail,instance.getScrumMasterTaskList(1,TaskTypes.CUSTOM).getStatus());
    }

    public static void addRecord() {
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
            instance.insertEmployee(employee);
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
            instance.insertDeveloper(developer);
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
            instance.insertTester(tester);
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
            instance.insertTask(task);
        }
        for (int i=1; i<=10; i++) {
            DevelopersTask developerTask = new DevelopersTask();
            developerTask.setId(i);
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
            instance.insertDevelopersTask(developerTask);
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
            instance.insertTestersTask(testersTask);
        }
        for (int i=1; i<=4; i++) {
            Project project=new Project();
            project.setId(i);
            project.setTitle(title[i-1]);
            project.setTakeIntoDevelopment(createdDate[i-1]);
            project.setTask(getListTask());
            instance.insertProject(project);
        }
    }
    private static Employee getScrum(){
        List<Employee> listemployee = ((DataProviderCsv) instance).select(Employee.class);
        int max=9; int min=0;
        return listemployee.get((int) ((Math.random() * ((max - min) + 1)) + min));
    }
    private static List<Employee> getListEmployee(){
        List<Employee> listemployee = ((DataProviderCsv) instance).select(Employee.class);
        List<Developer> developers = ((DataProviderCsv) instance).select(Developer.class);
        int max=9; int min=0;
        for (int i=1;i<=3; i++) {
            Developer developer = developers.get((int) ((Math.random() * ((max - min) + 1)) + min));
            listemployee.add(developer);
        }
        List<Tester> testers = ((DataProviderCsv) instance).select(Tester.class);
        for (int i=1;i<=3; i++) {
            Tester tester = testers.get((int) ((Math.random() * ((max - min) + 1)) + min));
            listemployee.add(tester);
        }
        return listemployee;
    }
    private static List<Task> getListTask(){
        List<Task> listTask = ((DataProviderCsv) instance).select(Task.class);
        List<DevelopersTask> developers = ((DataProviderCsv) instance).select(DevelopersTask.class);
        int max=9; int min=0;
        for (int i=1;i<=3; i++) {
            DevelopersTask developersTask = developers.get((int)((Math.random() * ((max - min) + 1)) + min));
            listTask.add(developersTask);
        }
        List<TestersTask> testers = ((DataProviderCsv) instance).select(TestersTask.class);
        for (int i=1;i<=3; i++) {
            TestersTask testersTask = testers.get((int) ((Math.random() * ((max - min) + 1)) + min));
            listTask.add(testersTask);
        }
        return listTask;
    }
}