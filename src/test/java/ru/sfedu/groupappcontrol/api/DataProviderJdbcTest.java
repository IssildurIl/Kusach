package ru.sfedu.groupappcontrol.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.sfedu.groupappcontrol.Constants;
import ru.sfedu.groupappcontrol.models.*;
import ru.sfedu.groupappcontrol.models.enums.*;

import java.util.ArrayList;
import java.util.List;

import static ru.sfedu.groupappcontrol.api.Fill.*;
import static ru.sfedu.groupappcontrol.models.enums.Outcomes.*;

class DataProviderJdbcTest {
    private static final Logger log = LogManager.getLogger(DataProviderJdbcTest.class);
    public static DataProviderJdbc instance = new DataProviderJdbc();

    @BeforeAll
    static void setJDBCEnv() {
        instance.deleteAllRecord();
        instance.createTables();
        addRecord();
    }

//    @Test
//    public void insertEmployeeSuccess() {
//        System.out.println("Insert employee success");
//        Employee employee = new Employee();
//        db.setBasicEmployeeParams(employee,"Employee3","Employee_sec_name",
//                "Employee_Login","admin","employee@sfedu.ru","Employee_personal_token",
//                "FullStack", TypeOfEmployee.Employee);
//        Outcomes o = db.insertEmployee(employee).getStatus();
//        assertEquals(Outcomes.Complete,o);
//    }
//    @Test
//    public void insertProject(){
//        Project project = db.createProject(0,"TEST_PROJECT","18-12-2020",getListTask()).getData();
//        log.info(project);
//        db.insertProject(project);
//    }
//    @Test
//    public void insertEmployee(){
//        Employee employee = new Employee();
//        db.setBasicEmployeeParams(employee,"Employee3","Employee_sec_name",
//                "Employee_Login","admin","employee@sfedu.ru","Employee_personal_token",
//                "FullStack", TypeOfEmployee.Employee);
//        log.info(employee.getId());
//        List<Task> task = new ArrayList<>();
//        Task testTask = db.createTask(0,"Descript",14553.0,db.getEmployeeById(1).getData(), TypeOfCompletion.DEVELOPING,getListEmployee(),"04-12-2020","10-12-2020","05-12-2020", TaskTypes.BASE_TASK).getData();
//        db.insertTask(testTask);
//    }
//    @Test
//    public void getAll(){
//        log.info(db.getTasks(1).getData());
//    }
//    @Test
//    public void delEmployee(){
//        db.deleteEmployee(1);
//    }
//
//    @Test
//    public void updEmployee(){
//        Employee employee = db.createEmployee(2,"Test_Employee","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Employee).getData();
//        log.info(employee);
//        db.updateEmployee(employee);
//    }
//    @Test
//    public void getTasksByUserSuccess(){
//        log.info(db.getTasksByUser(1,1).getData());
//    }


    @Test
    public void getTaskByIdSuccess(){
        log.info(instance.getTaskById(1).getData());
        Assertions.assertEquals(1,instance.getTaskById(1).getData().getId());
    }

    @Test
    public void getTaskByIdFail() {
        Assertions.assertNotEquals(0,
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
        Task task = (DevelopersTask) instance.createTask(20,"insertDevelopersTaskSuccess",14553.0,getScrum(),TypeOfCompletion.DEVELOPING,getListEmployee(),"04-12-2020","10-12-2020","05-12-2020",TaskTypes.DEVELOPERS_TASK).getData();
        instance.insertTask(task);
        long id = instance.getTaskByParam(task).getData();
        log.info(id);
        Assertions.assertEquals(instance.getDevelopersTaskById(id).getData().getTaskDescription(),"insertDevelopersTaskSuccess");

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
        DevelopersTask developersTask = (DevelopersTask) instance.createTask(20,"insertDevelopersTaskSuccess",14553.0,getScrum(),TypeOfCompletion.DEVELOPING,getListEmployee(),"04-12-2020","10-12-2020","05-12-2020",TaskTypes.DEVELOPERS_TASK).getData();
        instance.insertDevelopersTask(developersTask);
        long id = instance.getDevelopersTaskByParam(developersTask).getData();
        log.info(id);
        Assertions.assertEquals(instance.getDevelopersTaskById(id).getData().getTaskDescription(),"insertDevelopersTaskSuccess");
    }

    @Test
    public void insertDevelopersTaskFail() {
        DevelopersTask developersTask = (DevelopersTask) instance.createTask(20,"insertDevelopersTaskSuccess",14553.0,getScrum(),TypeOfCompletion.DEVELOPING,getListEmployee(),"04-12-2020","10-12-2020","05-12-2020",TaskTypes.DEVELOPERS_TASK).getData();
        instance.insertDevelopersTask(developersTask);
        long id = instance.getDevelopersTaskByParam(developersTask).getData();
        log.info(id);
        Assertions.assertNotEquals(instance.getDevelopersTaskById(id-1).getData().getTaskDescription(),"insertDevelopersTaskSuccess");
    }

    @Test
    public void insertTestersTaskSuccess() {
        TestersTask testersTask = (TestersTask) instance.createTask(20,"insertTestersTaskSuccess",14553.0,getScrum(),TypeOfCompletion.DEVELOPING,getListEmployee(),"04-12-2020","10-12-2020","05-12-2020",TaskTypes.TESTERS_TASK).getData();
        instance.insertTestersTask(testersTask);
        long id = instance.getTestersTaskByParam(testersTask).getData();
        log.info(id);
        Assertions.assertEquals(instance.getTestersTaskById(id).getData().getTaskDescription(),"insertTestersTaskSuccess");
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
        Developer developer = (Developer) instance.createEmployee(20,"insertDeveloperSuccess","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer).getData();
        instance.insertDeveloper(developer);
        long id = instance.getDeveloperByParam(developer).getData();
        Assertions.assertEquals("insertDeveloperSuccess",instance.getEmployeeById(id).getData().getFirstName());
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
        List<Task> taskList = instance.getTaskRecords(Task.class);
        log.debug(taskList);
        instance.deleteTask(testTask.getId());
        List<Task> editedTaskList = instance.getTaskRecords(Task.class);
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
        List<DevelopersTask> taskList = instance.getTaskRecords(DevelopersTask.class);
        log.debug(taskList);
        instance.deleteDevelopersTask(developersTask.getId());
        List<DevelopersTask> editedDevelopersTaskList = instance.getTaskRecords(DevelopersTask.class);
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
        List<TestersTask> taskList = instance.getTaskRecords(TestersTask.class);
        log.debug(taskList);
        instance.deleteTestersTask(testersTask.getId());
        List<TestersTask> editedTestersTaskList = instance.getTaskRecords(TestersTask.class);
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
        List<Project> list = instance.getProjectRecords();
        log.debug(list);
        instance.deleteProject(8);
        List<Project> list1 = instance.getProjectRecords();
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
        TestersTask testersTask = (TestersTask) instance.createTask(2,"updateTestersTaskSuccess",14553.0,getScrum(),TypeOfCompletion.DEVELOPING,getListEmployee(),"04-12-2020","10-12-2020","05-12-2020",TaskTypes.TESTERS_TASK).getData();
        instance.updateTestersTask(testersTask);
        long id = instance.getTestersTaskByParam(testersTask).getData();
        Assertions.assertEquals("updateTestersTaskSuccess",instance.getTestersTaskById(id).getData().getTaskDescription());
    }

    @Test
    public void updateTestersTaskFail() {
        TestersTask testersTask = (TestersTask) instance.createTask(100,"Description_2",14553.0,getScrum(),TypeOfCompletion.DEVELOPING,getListEmployee(),"04-12-2020","10-12-2020","05-12-2020",TaskTypes.TESTERS_TASK).getData();
        instance.updateTestersTask(testersTask);
        Assertions.assertEquals(Fail,instance.updateTestersTask(testersTask).getStatus());
    }

    @Test
    public void updateEmployeeSuccess() {
        Employee employee = instance.createEmployee(2,"Test_Employee_2","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Employee).getData();
        instance.updateEmployee(employee);
        long id = instance.getEmployeeByParam(employee).getData();
        Assertions.assertEquals("Test_Employee_2",instance.getEmployeeById(id).getData().getFirstName());
    }

    @Test
    public void updateEmployeeFail() {
        Employee employee =  instance.createEmployee(100,"Test_Employee_2","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Employee).getData();
        instance.updateEmployee(employee);
        Assertions.assertEquals(Fail,instance.updateEmployee(employee).getStatus());
    }

    @Test
    public void updateDeveloperSuccess() {
        Developer developer = (Developer) instance.createEmployee(2,"updateDeveloperSuccess","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer).getData();
        instance.updateEmployee(developer);
        long id = instance.getDeveloperByParam(developer).getData();
        Assertions.assertEquals("updateDeveloperSuccess",instance.getDeveloperById(id).getData().getFirstName());
    }

    @Test
    public void updateDeveloperFail() {
        Developer developer = (Developer) instance.createEmployee(100,"Test_Employee_2","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer).getData();
        instance.updateDeveloper(developer);
        Assertions.assertEquals(Fail,instance.updateDeveloper(developer).getStatus());
    }

    @Test
    public void updateTesterSuccess() {
        Tester tester = (Tester) instance.createEmployee(2,"updateTesterSuccess","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Tester).getData();
        instance.updateTester(tester);
        long id = instance.getTesterByParam(tester).getData();
        Assertions.assertEquals("updateTesterSuccess",instance.getTesterById(id).getData().getFirstName());
    }

    @Test
    public void updateTesterFail() {
        Tester tester = (Tester) instance.createEmployee(100,"Test_Employee_2","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Tester).getData();
        instance.updateTester(tester);
        Assertions.assertEquals(Fail,instance.updateTester(tester).getStatus());
    }

    @Test
    public void updateProjectSuccess() {
        Project project = instance.createProject(5,"updateProjectSuccess","05-12-2020",getListTask()).getData();
        instance.updateProject(project);
        long id = instance.getProjectByParam(project).getData();
        Assertions.assertEquals("updateProjectSuccess",instance.getProjectByID(id).getData().getTitle());
    }

    @Test
    public void updateZProjectFail() {
        }

    @Test
    public void createTaskSuccess() {
        Task testTask = instance.createTask(12,"Descript",14553.0,getScrum(),TypeOfCompletion.DEVELOPING,getListEmployee(),"04-12-2020","10-12-2020","05-12-2020",TaskTypes.BASE_TASK).getData();
        instance.insertTask(testTask);
        long id = instance.getTaskByParam(testTask).getData();
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
        Assertions.assertEquals(30,size);
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
        List<Task> list = new ArrayList<>();
        List<Employee> list1 = getListEmployee();
        list1.add(employee);
        Task testTask =  instance.createTask(11,"Descript",14553.0,employee,TypeOfCompletion.DEVELOPING,list1,"04-12-2020","10-12-2020","05-12-2020",TaskTypes.BASE_TASK).getData();
        instance.insertTask(testTask);
        List<Task> taskList= instance.getTaskRecords(Task.class);
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
        instance.changeTaskStatus(10, String.valueOf(TypeOfCompletion.TESTING));
        Task task =  instance.getTaskById(10).getData();
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
        List<Project> list = instance.getProjectRecords();
        log.debug(list);
        Assertions.assertEquals(Complete,o);
    }

    @Test
    public void createProjectFail() {
        Outcomes o = instance.createProject(5,"","05-12-2020",getListTask()).getStatus();
        List<Project> list = instance.getProjectRecords();
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
        Assertions.assertNotNull(time);
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
        Assertions.assertNotEquals(0,instance.getProjectListByScrummasterId(1).getData());
    }

    @Test
    public void writeCommentSuccess() {
        instance.writeDevelopersTaskComment(1,"i wrote this comment now");
        DevelopersTask developersTask = instance.getDevelopersTaskById(1).getData();
        log.debug(developersTask);
        Assertions.assertEquals("i wrote this comment now",developersTask.getTaskDescription());
    }

    @Test
    public void writeCommentFail() {
        DevelopersTask developersTask = instance.getDevelopersTaskById(1).getData();
        log.debug(developersTask);
        Assertions.assertEquals(Fail,instance.writeDevelopersTaskComment(1,"").getStatus());
    }

    @Test
    public void getTaskInfoSuccess() {
        Outcomes o = instance. getDevelopersTaskById(1).getStatus();
        log.info(instance. getDevelopersTaskById(1).getData());
        Assertions.assertEquals(Complete,o);
    }

    @Test
    public void getTaskInfoFail() {
        Outcomes o = instance.getDevelopersTaskById(45).getStatus();
        log.info(instance. getDevelopersTaskById(45).getData());
        Assertions.assertEquals(Fail,o);
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
    public void getProjectListByIdSuccess() {
        Outcomes o = instance.getProjectListByScrummasterId(1).getStatus();
        log.debug(instance.getProjectListByScrummasterId(1).getStatus());
        Assertions.assertEquals(Complete,o);
    }

    @Test
    public void getProjectListByIdFail(){
        Assertions.assertNull(instance.getProjectListByScrummasterId(100).getData());
    }

    @Test
    public void getTaskInfoGenericSuccess(){
        log.error(instance.getTestersTaskById(1).getData());
        Assertions.assertEquals(1,instance.getTestersTaskById(1).getData().getId());
    }

    @Test
    public void getTaskInfoGenericFail(){
        log.error(instance.getTestersTaskById(1).getData());
        Assertions.assertNotEquals(2,instance.getTestersTaskById(1).getData().getId());
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
            project.setId(i-1);
            project.setTitle(title[i-1]);
            project.setTakeIntoDevelopment(createdDate[i-1]);
            project.setTask(getListTask());
            instance.insertProject(project);
        }
    }
    private static Employee getScrum(){
        List<Employee> listemployee = instance.getListEmployees(Employee.class);
        int max=9; int min=0;
        return listemployee.get((int) ((Math.random() * ((max - min) + 1)) + min));
    }
    private static List<Employee> getListEmployee(){
        List<Employee> listemployee = instance.getListEmployees(Employee.class);
        List<Developer> developers = instance.getListEmployees(Developer.class);
        int max=9; int min=0;
        for (int i=1;i<=3; i++) {
            Developer developer = developers.get((int) ((Math.random() * ((max - min) + 1)) + min));
            listemployee.add(developer);
        }
        List<Tester> testers = instance.getListEmployees(Tester.class);
        for (int i=1;i<=3; i++) {
            Tester tester = testers.get((int) ((Math.random() * ((max - min) + 1)) + min));
            listemployee.add(tester);
        }
        return listemployee;
    }
    private static List<Task> getListTask(){
        List<Task> fullList = new ArrayList<>();
        List<Task> listTask = instance.getListTasks(Task.class);
        int max=9; int min=0;
        for (int i=1;i<=4; i++) {
            Task task = listTask.get((int)((Math.random() * ((max - min) + 1)) + min));
            fullList.add(task);
            log.info(task);
        }
        List<DevelopersTask> developers = instance.getListTasks(DevelopersTask.class);
        for (int i=1;i<=4; i++) {
            DevelopersTask developersTask = developers.get((int)((Math.random() * ((max - min) + 1)) + min));
            fullList.add(developersTask);
            log.info(developersTask);
        }
        List<TestersTask> testers = instance.getListTasks(TestersTask.class);
        for (int i=1;i<=4; i++) {
            TestersTask testersTask = testers.get((int) ((Math.random() * ((max - min) + 1)) + min));
            fullList.add(testersTask);
            log.info(testersTask);
        }
        return fullList;
    }
}