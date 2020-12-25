package ru.sfedu.groupappcontrol.api;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.sfedu.groupappcontrol.models.*;
import ru.sfedu.groupappcontrol.models.enums.Outcomes;
import ru.sfedu.groupappcontrol.models.enums.TaskTypes;
import ru.sfedu.groupappcontrol.models.enums.TypeOfCompletion;
import ru.sfedu.groupappcontrol.models.enums.TypeOfEmployee;
import ru.sfedu.groupappcontrol.utils.JdbcGenerator;

import java.util.ArrayList;
import java.util.List;

import static ru.sfedu.groupappcontrol.models.enums.Outcomes.*;

class DataProviderJdbcTest{
    public static DataProvider instance = new DataProviderJdbc();

    @BeforeAll
    static void setJDBCEnv() {
        instance.deleteAllRecord();
        ((DataProviderJdbc)instance).createTables();
        JdbcGenerator.addRecord();
    }


    @Test
    public void getTaskByIdSuccess(){
        Assertions.assertEquals(1,
                instance.getTaskById(1).getData().getId());
    }

    @Test
    public void getTaskByIdFail() {
        Assertions.assertNotEquals(0,
                instance.getTaskById(1).getData().getId());
    }

    @Test
    public void getDevelopersTaskByIdSuccess() {
        Assertions.assertEquals(1,
                instance.getDevelopersTaskById(1).getData().getId());
    }

    @Test
    public void getDevelopersTaskByIdFail() {
        Assertions.assertNotEquals(0,
                instance.getDevelopersTaskById(1).getData().getId());
    }

    @Test
    public void getTestersTaskByIdSuccess() {
        Assertions.assertEquals(1,
                instance.getTestersTaskById(1).getData().getId());
    }

    @Test
    public void getTestersTaskByIdFail() {
        Assertions.assertNotEquals(0,
                instance.getTestersTaskById(1).getData().getId());
    }

    @Test
    public void getEmployeeByIdSuccess() {
        Assertions.assertEquals(1,
                instance.getEmployeeById(1).getData().getId());
    }

    @Test
    public void getEmployeeByIdFail() {
        Assertions.assertNotEquals(0,
                instance.getEmployeeById(1).getData().getId());
    }

    @Test
    public void getDeveloperByIdSuccess() {
        Assertions.assertEquals(1,
                instance.getDeveloperById(1).getData().getId());
    }

    @Test
    public void getDeveloperByIdFail() {
        Assertions.assertNotEquals(0,
                instance.getDeveloperById(1).getData().getId());
    }

    @Test
    public void getTesterByIdSuccess() {
        Assertions.assertEquals(1,
                instance.getTesterById(1).getData().getId());
    }

    @Test
    public void getTesterByIdFail() {
        Assertions.assertNotEquals(0,
                instance.getTesterById(1).getData().getId());
    }

    @Test
    public void getProjectByIDSuccess() {
        Assertions.assertEquals(1,
                instance.getProjectByID(1).getData().getId());
    }

    @Test
    public void getProjectByIDFail() {
        Assertions.assertNotEquals(0,
                instance.getProjectByID(1).getData().getId());
    }

    @Test
    public void insertTaskSuccess() {
        Task task =  instance.createTask(20,
                "insertDevelopersTaskSuccess",14553.0,JdbcGenerator.getScrum(),
                TypeOfCompletion.DEVELOPING,JdbcGenerator.getListEmployee(),
                "04-12-2020","10-12-2020","05-12-2020",
                TaskTypes.BASE_TASK).getData();
        instance.insertTask(task);
        long id =  ((DataProviderJdbc) instance).getTaskByParam(task).getData();
        Assertions.assertEquals(instance.getDevelopersTaskById(id).getData().getTaskDescription(),"insertDevelopersTaskSuccess");
    }

    @Test
    public void insertTaskFail() {
        Task task =  instance.createTask(20,
                "",14553.0,JdbcGenerator.getScrum(),
                TypeOfCompletion.DEVELOPING,JdbcGenerator.getListEmployee(),
                "","10-12-2020","05-12-2020",
                TaskTypes.BASE_TASK).getData();
        Outcomes o = instance.insertTask(task).getStatus();
        Assertions.assertEquals(Fail,o);
    }

    @Test
    public void insertDevelopersTaskSuccess() {
        DevelopersTask developersTask = (DevelopersTask) instance.createTask(20,"insertDevelopersTaskSuccess",14553.0,JdbcGenerator.getScrum(),TypeOfCompletion.DEVELOPING,JdbcGenerator.getListEmployee(),"04-12-2020","10-12-2020","05-12-2020",TaskTypes.DEVELOPERS_TASK).getData();
        instance.insertDevelopersTask(developersTask);
        long id = ((DataProviderJdbc) instance).getDevelopersTaskByParam(developersTask).getData();
        Assertions.assertEquals(instance.getDevelopersTaskById(id).getData().getTaskDescription(),"insertDevelopersTaskSuccess");
    }

    @Test
    public void insertDevelopersTaskFail() {
        DevelopersTask developersTask = (DevelopersTask) instance.createTask(20,"",14553.0,JdbcGenerator.getScrum(),TypeOfCompletion.DEVELOPING,JdbcGenerator.getListEmployee(),"04-12-2020","10-12-2020","05-12-2020",TaskTypes.DEVELOPERS_TASK).getData();
        Outcomes o = instance.insertDevelopersTask(developersTask).getStatus();
        Assertions.assertEquals(Fail,o);
    }

    @Test
    public void insertTestersTaskSuccess() {
        TestersTask testersTask = (TestersTask) instance.createTask(20,"insertTestersTaskSuccess",14553.0,JdbcGenerator.getScrum(),TypeOfCompletion.DEVELOPING,JdbcGenerator.getListEmployee(),"04-12-2020","10-12-2020","05-12-2020",TaskTypes.TESTERS_TASK).getData();
        instance.insertTestersTask(testersTask);
        long id = ((DataProviderJdbc) instance).getTestersTaskByParam(testersTask).getData();
        Assertions.assertEquals(instance.getTestersTaskById(id).getData().getTaskDescription(),"insertTestersTaskSuccess");
    }

    @Test
    public void insertTestersTaskFail() {
        TestersTask testersTask = (TestersTask) instance.createTask(1,"",14553.0,JdbcGenerator.getScrum(),TypeOfCompletion.DEVELOPING,JdbcGenerator.getListEmployee(),"04-12-2020","10-12-2020","05-12-2020",TaskTypes.TESTERS_TASK).getData();
        Outcomes o = instance.insertTestersTask(testersTask).getStatus();
        Assertions.assertEquals(Fail,o);
    }

    @Test
    public void insertEmployeeSuccess() {
        Employee employee =  instance.createEmployee(20,"insertEmployeeSuccess","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.EMPLOYEE).getData();
        instance.insertEmployee(employee);
        long id = ((DataProviderJdbc) instance).getEmployeeByParam(employee).getData();
        Assertions.assertEquals("insertEmployeeSuccess",instance.getEmployeeById(id).getData().getFirstName());
    }

    @Test
    public void insertEmployeeFail() {
        Employee employee =  instance.createEmployee(1,"","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.EMPLOYEE).getData();
        Outcomes o = instance.insertEmployee(employee).getStatus();
        Assertions.assertEquals(Fail,o);
    }

    @Test
    public void insertDeveloperSuccess() {
        Developer developer = (Developer) instance.createEmployee(11,"insertDeveloperSuccess","Vasilyev","vas1ly","vasyan","VasLy@","9bba8047-f0aa-473d-aef9-6905edcd3f99","Team13",TypeOfEmployee.Developer).getData();
        instance.insertDeveloper(developer);
        long id = ((DataProviderJdbc) instance).getDeveloperByParam(developer).getData();
        Assertions.assertEquals("insertDeveloperSuccess",instance.getDeveloperById(id).getData().getFirstName());
    }

    @Test
    public void insertDeveloperFail() {
        Developer developer = (Developer) instance.createEmployee(1,"","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer).getData();
        Outcomes o = instance.insertEmployee(developer).getStatus();
        Assertions.assertEquals(Fail,o);
    }

    @Test
    public void insertTesterSuccess() {
        Tester tester = (Tester) instance.createEmployee(20,"insertTesterSuccess","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Tester).getData();
        instance.insertTester(tester);
        long id = ((DataProviderJdbc) instance).getTesterByParam(tester).getData();
        Assertions.assertEquals("insertTesterSuccess",instance.getTesterById(id).getData().getFirstName());
    }

    @Test
    public void insertTesterFail() {
        Tester tester = (Tester) instance.createEmployee(1,"","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Tester).getData();
        Outcomes o = instance.insertEmployee(tester).getStatus();
        Assertions.assertEquals(Fail,o);
    }

    @Test
    public void insertFProjectSuccess(){
        Project project = instance.createProject(6,"TEST_PROJECT","18-12-2020",JdbcGenerator.getListTask()).getData();
        instance.insertProject(project);
        Assertions.assertEquals("TEST_PROJECT",instance.getProjectByID(6).getData().getTitle());
    }

    @Test
    public void insertGProjectFail(){
        Project project = instance.createProject(6,"","18-12-2020",JdbcGenerator.getListTask()).getData();
        Outcomes o = instance.insertProject(project).getStatus();
        Assertions.assertEquals(Fail,o);
    }

    @Test
    public void deleteTaskSuccess() {
        Task testTask = instance.createTask(12,"deleteTaskSuccess",14553.0,JdbcGenerator.getScrum(),TypeOfCompletion.DEVELOPING,JdbcGenerator.getListEmployee(),"04-12-2020","10-12-2020","05-12-2020",TaskTypes.BASE_TASK).getData();
        instance.insertTask(testTask);
        long id = ((DataProviderJdbc) instance).getTaskByParam(testTask).getData();
        instance.deleteTask(id);
        Assertions.assertNull(((DataProviderJdbc) instance).getTaskByParam(testTask).getData());
    }

    @Test
    public void deleteTaskFail() {
        Assertions.assertEquals(Complete,instance.deleteTask(100).getStatus());
    }

    @Test
    public void deleteDevelopersTaskSuccess() {
        DevelopersTask testTask = (DevelopersTask) instance.createTask(12,"deleteDevelopersTaskSuccess",14553.0,JdbcGenerator.getScrum(),TypeOfCompletion.DEVELOPING,JdbcGenerator.getListEmployee(),"04-12-2020","10-12-2020","05-12-2020",TaskTypes.DEVELOPERS_TASK).getData();
        instance.insertDevelopersTask(testTask);
        long id = ((DataProviderJdbc) instance).getDevelopersTaskByParam(testTask).getData();
        instance.deleteDevelopersTask(id);
        Assertions.assertNull(((DataProviderJdbc) instance).getDevelopersTaskByParam(testTask).getData());
    }

    @Test
    public void deleteDevelopersTaskFail() {
        Assertions.assertEquals(Complete,instance.deleteDevelopersTask(100).getStatus());
    }

    @Test
    public void deleteTestersTaskSuccess() {
        TestersTask testTask = (TestersTask) instance.createTask(12,"deleteDevelopersTaskSuccess",14553.0,JdbcGenerator.getScrum(),TypeOfCompletion.DEVELOPING,JdbcGenerator.getListEmployee(),"04-12-2020","10-12-2020","05-12-2020",TaskTypes.TESTERS_TASK).getData();
        instance.insertTestersTask(testTask);
        long id = ((DataProviderJdbc) instance).getTestersTaskByParam(testTask).getData();
        instance.deleteTestersTask(id);
        Assertions.assertNull(((DataProviderJdbc) instance).getTestersTaskByParam(testTask).getData());
    }

    @Test
    public void deleteTestersTaskFail() {
        instance.deleteTestersTask(100);
        Assertions.assertEquals(Complete,instance.deleteTestersTask(100).getStatus());
    }

    @Test
    public void deleteEmployeeSuccess() {
        Employee employee =  instance.createEmployee(1,"deleteEmployeeSuccess","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.EMPLOYEE).getData();
        instance.insertEmployee(employee);
        long id = ((DataProviderJdbc) instance).getEmployeeByParam(employee).getData();
        instance.deleteEmployee(id);
        Assertions.assertNull(((DataProviderJdbc) instance).getEmployeeByParam(employee).getData());
    }

    @Test
    public void deleteEmployeeFail() {
        instance.deleteEmployee(100);
        Assertions.assertEquals(Complete,instance.deleteEmployee(100).getStatus());
    }

    @Test
    public void deleteDeveloperSuccess() {
        Developer developer =  (Developer) instance.createEmployee(21,"Test_Employee_21","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.EMPLOYEE).getData();
        instance.insertDeveloper(developer);
        long id = ((DataProviderJdbc) instance).getDeveloperByParam(developer).getData();
        instance.deleteDeveloper(id);
        Assertions.assertNull(((DataProviderJdbc) instance).getDeveloperByParam(developer).getData());
    }

    @Test
    public void deleteDeveloperFail() {
        instance.deleteDeveloper(100);
        Assertions.assertEquals(Complete,instance.deleteDeveloper(100).getStatus());
    }

    @Test
    public void deleteTesterSuccess() {
        Tester tester =  (Tester) instance.createEmployee(21,"Test_Employee_21","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Tester).getData();
        instance.insertTester(tester);
        long id = ((DataProviderJdbc) instance).getTesterByParam(tester).getData();
        instance.deleteTester(id);
        Assertions.assertNull(((DataProviderJdbc) instance).getTesterByParam(tester).getData());
    }

    @Test
    public void deleteTesterFail() {
        instance.deleteTester(100);
        Assertions.assertEquals(Complete,instance.deleteTester(100).getStatus());
    }

    @Test
    public void deleteProjectSuccess() {
        Project project = instance.createProject(8,"TestProject7","05-12-2020",JdbcGenerator.getListTask()).getData();
        instance.insertProject(project);
        long id = ((DataProviderJdbc) instance).getProjectByParam(project).getData();
        instance.deleteProject(id);
        Assertions.assertNull(((DataProviderJdbc) instance).getProjectByParam(project).getData());
    }

    @Test
    public void deleteProjectFail() {
        instance.deleteProject(100);
        Assertions.assertEquals(Complete,instance.deleteProject(100).getStatus());
    }

    @Test
    public void updateTaskSuccess(){
        Task task = instance.createTask(2,"updateTaskSuccess",14553.0,JdbcGenerator.getScrum(),TypeOfCompletion.DEVELOPING,JdbcGenerator.getListEmployee(),"04-12-2020","10-12-2020","05-12-2020",TaskTypes.BASE_TASK).getData();
        instance.updateTask(task);
        Assertions.assertEquals("updateTaskSuccess",instance.getTaskById(2).getData().getTaskDescription());
    }

    @Test
    public void updateTaskFail() {
        Task task = instance.createTask(100,"updateTaskFail",14553.0,JdbcGenerator.getScrum(),TypeOfCompletion.DEVELOPING,JdbcGenerator.getListEmployee(),"04-12-2020","10-12-2020","05-12-2020",TaskTypes.BASE_TASK).getData();
        task.setTaskDescription("");
        instance.updateTask(task);
        Assertions.assertEquals(Fail,instance.updateTask(task).getStatus());
    }

    @Test
    public void updateDevelopersTaskSuccess() {
        DevelopersTask developersTask = (DevelopersTask) instance.createTask(2,"Description_2",14553.0,JdbcGenerator.getScrum(),TypeOfCompletion.DEVELOPING,JdbcGenerator.getListEmployee(),"04-12-2020","10-12-2020","05-12-2020",TaskTypes.DEVELOPERS_TASK).getData();
        instance.updateDevelopersTask(developersTask);
        Assertions.assertEquals("Description_2",instance.getDevelopersTaskById(2).getData().getTaskDescription());
    }

    @Test
    public void updateDevelopersTaskFail() {
        DevelopersTask developersTask = (DevelopersTask) instance.createTask(100,"updateDevelopersTaskFail",14553.0,JdbcGenerator.getScrum(),TypeOfCompletion.DEVELOPING,JdbcGenerator.getListEmployee(),"04-12-2020","10-12-2020","05-12-2020",TaskTypes.DEVELOPERS_TASK).getData();
        developersTask.setTaskDescription("");
        instance.updateDevelopersTask(developersTask);
        Assertions.assertEquals(Fail,instance.updateDevelopersTask(developersTask).getStatus());
    }

    @Test
    public void updateTestersTaskSuccess() {
        TestersTask testersTask = (TestersTask) instance.createTask(2,"updateTestersTaskSuccess",14553.0,JdbcGenerator.getScrum(),TypeOfCompletion.DEVELOPING,JdbcGenerator.getListEmployee(),"04-12-2020","10-12-2020","05-12-2020",TaskTypes.TESTERS_TASK).getData();
        instance.updateTestersTask(testersTask);
        Assertions.assertEquals("updateTestersTaskSuccess",instance.getTestersTaskById(2).getData().getTaskDescription());
    }

    @Test
    public void updateTestersTaskFail() {
        TestersTask testersTask = (TestersTask) instance.createTask(100,"Description_2",14553.0,JdbcGenerator.getScrum(),TypeOfCompletion.DEVELOPING,JdbcGenerator.getListEmployee(),"04-12-2020","10-12-2020","05-12-2020",TaskTypes.TESTERS_TASK).getData();
        testersTask.setTaskDescription("");
        instance.updateTestersTask(testersTask);
        Assertions.assertEquals(Fail,instance.updateTestersTask(testersTask).getStatus());
    }

    @Test
    public void updateEmployeeSuccess() {
        Employee employee = instance.createEmployee(2,"updateEmployeeSuccess","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.EMPLOYEE).getData();
        instance.updateEmployee(employee);
        Assertions.assertEquals("updateEmployeeSuccess",instance.getEmployeeById(2).getData().getFirstName());
    }

    @Test
    public void updateEmployeeFail() {
        Employee employee =  instance.createEmployee(100,"Test_Employee_2","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.EMPLOYEE).getData();
        employee.setFirstName("");
        instance.updateEmployee(employee);
        Assertions.assertEquals(Fail,instance.updateEmployee(employee).getStatus());
    }

    @Test
    public void updateDeveloperSuccess() {
        Developer developer = (Developer) instance.createEmployee(2,"updateDeveloperSuccess","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer).getData();
        instance.updateDeveloper(developer);
        Assertions.assertEquals("updateDeveloperSuccess",instance.getDeveloperById(2).getData().getFirstName());
    }

    @Test
    public void updateDeveloperFail() {
        Developer developer = (Developer) instance.createEmployee(100,"Test_Employee_2","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer).getData();
        developer.setFirstName("");
        instance.updateDeveloper(developer);
        Assertions.assertEquals(Fail,instance.updateDeveloper(developer).getStatus());
    }

    @Test
    public void updateTesterSuccess() {
        Tester tester = (Tester) instance.createEmployee(2,"updateTesterSuccess","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Tester).getData();
        instance.updateTester(tester);
        Assertions.assertEquals("updateTesterSuccess",instance.getTesterById(2).getData().getFirstName());
    }

    @Test
    public void updateTesterFail() {
        Tester tester = (Tester) instance.createEmployee(100,"Test_Employee_2","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Tester).getData();
        tester.setFirstName("");
        instance.updateTester(tester);
        Assertions.assertEquals(Fail,instance.updateTester(tester).getStatus());
    }

    @Test
    public void updateProjectSuccess() {
        Project project = instance.createProject(4,"updateProjectSuccess","05-12-2020",JdbcGenerator.getListTask()).getData();
        instance.updateProject(project);
        Assertions.assertEquals("updateProjectSuccess",instance.getProjectByID(4).getData().getTitle());
    }

    @Test
    public void updateProjectFail() {
        Project project = instance.createProject(4,"updateProjectSuccess","05-12-2020",JdbcGenerator.getListTask()).getData();
        project.setTitle("");
        instance.updateProject(project);
        Assertions.assertEquals(Fail,instance.updateProject(project).getStatus());
    }

    @Test
    public void createTaskSuccess() {
        Outcomes o = instance.createTask(12,"Descript",14553.0,JdbcGenerator.getScrum(),TypeOfCompletion.DEVELOPING,JdbcGenerator.getListEmployee(),"04-12-2020","10-12-2020","05-12-2020",TaskTypes.BASE_TASK).getStatus();
        Assertions.assertEquals(Complete,o);
    }

    @Test
    public void createTaskFail() {
        Outcomes o = instance.createTask(12,"",14553.0,JdbcGenerator.getScrum(),TypeOfCompletion.DEVELOPING,JdbcGenerator.getListEmployee(),"04-12-2020","10-12-2020","05-12-2020",TaskTypes.BASE_TASK).getStatus();
        Assertions.assertEquals(Fail,o);
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
        Assertions.assertNotEquals(0,size);
    }

    @Test
    public void getTasksByUserSuccess() {
        Employee employee = instance.getEmployeeById(1).getData();
        List<Employee> list1 = JdbcGenerator.getListEmployee();
        list1.add(employee);
        Task testTask =  instance.createTask(11,"getTasksByUserSuccess",14553.0,employee,TypeOfCompletion.DEVELOPING,list1,"04-12-2020","10-12-2020","05-12-2020",TaskTypes.BASE_TASK).getData();
        instance.insertTask(testTask);
        long id = ((DataProviderJdbc) instance).getTaskByParam(testTask).getData();
        Assertions.assertEquals(id,instance.getTasksByUser(1,id).getData().getId());
    }

    @Test
    public void getTasksByUserFail() {
        Assertions.assertEquals(0,instance.getTasksByUser(20,20).getData().getId());
    }

    @Test
    public void changeTaskStatusSuccess() {
        instance.changeTaskStatus(10, String.valueOf(TypeOfCompletion.TESTING));
        Task task =  instance.getTaskById(10).getData();
        Assertions.assertEquals(TypeOfCompletion.TESTING, task.getStatus());
    }

    @Test
    public void changeTaskStatusFail() {
        Assertions.assertEquals(Fail,instance.changeTaskStatus(1,"").getStatus());
    }

    @Test
    public void calculateTaskCostSuccess() {
        Assertions.assertEquals(instance.calculateTaskCost(1).getData().longValue(), 731000.0);
    }

    @Test
    public void calculateTaskCostFail() {
        Assertions.assertNotEquals(instance.calculateTaskCost(1).getData(),1.0);
    }

    @Test
    public void writeBaseTaskCommentSuccess() {
        Task task = instance.createTask(13,"Descript",14553.0,JdbcGenerator.getScrum(),TypeOfCompletion.DEVELOPING,JdbcGenerator.getListEmployee(),"04-12-2020","10-12-2020","05-12-2020",TaskTypes.BASE_TASK).getData();
        instance.insertTask(task);
        long id = ((DataProviderJdbc) instance).getTaskByParam(task).getData();
        instance.writeBaseTaskComment(id,"writeBaseTaskCommentSuccess");
        Assertions.assertEquals("writeBaseTaskCommentSuccess",instance.getTaskById(id).getData().getTaskDescription());
    }

    @Test
    public void writeBaseTaskCommentFail() {
        Task task = instance.createTask(14,"Descript",14553.0,JdbcGenerator.getScrum(),TypeOfCompletion.DEVELOPING,JdbcGenerator.getListEmployee(),"04-12-2020","10-12-2020","05-12-2020",TaskTypes.BASE_TASK).getData();
        instance.insertTask(task);
        long id = ((DataProviderJdbc) instance).getTaskByParam(task).getData();
        Assertions.assertEquals(Fail,instance.writeBaseTaskComment(id,"").getStatus());
    }

    @Test
    public void writeDevelopersTaskCommentSuccess() {
        DevelopersTask developersTask = (DevelopersTask) instance.createTask(13,"Descript",14553.0,JdbcGenerator.getScrum(),TypeOfCompletion.DEVELOPING,JdbcGenerator.getListEmployee(),"04-12-2020","10-12-2020","05-12-2020",TaskTypes.DEVELOPERS_TASK).getData();
        instance.insertDevelopersTask(developersTask);
        long id = ((DataProviderJdbc) instance).getDevelopersTaskByParam(developersTask).getData();
        instance.writeDevelopersTaskComment(id,"writeDevelopersTaskCommentSuccess");
        Assertions.assertEquals("writeDevelopersTaskCommentSuccess",instance.getDevelopersTaskById(id).getData().getTaskDescription());
    }

    @Test
    public void writeDevelopersTaskCommentFail() {
        DevelopersTask developersTask = (DevelopersTask) instance.createTask(13,"Descript",14553.0,JdbcGenerator.getScrum(),TypeOfCompletion.DEVELOPING,JdbcGenerator.getListEmployee(),"04-12-2020","10-12-2020","05-12-2020",TaskTypes.DEVELOPERS_TASK).getData();
        instance.insertDevelopersTask(developersTask);
        long id = ((DataProviderJdbc) instance).getDevelopersTaskByParam(developersTask).getData();
        Assertions.assertEquals(Fail,instance.writeDevelopersTaskComment(id,"").getStatus());
    }

    @Test
    public void writeTestersTaskCommentSuccess() {
        TestersTask testersTask = (TestersTask) instance.createTask(13,"Descript",14553.0,JdbcGenerator.getScrum(),TypeOfCompletion.DEVELOPING,JdbcGenerator.getListEmployee(),"04-12-2020","10-12-2020","05-12-2020",TaskTypes.TESTERS_TASK).getData();
        instance.insertTestersTask(testersTask);
        long id = ((DataProviderJdbc) instance).getTestersTaskByParam(testersTask).getData();
        instance.writeTestersTaskComment(id,"writeTestersTaskCommentSuccess");
        Assertions.assertEquals("writeTestersTaskCommentSuccess",instance.getTestersTaskById(id).getData().getTaskDescription());
    }

    @Test
    public void writeTestersTaskCommentFail() {
        TestersTask testersTask = (TestersTask) instance.createTask(14,"writeTestersTaskCommentFail",14553.0,JdbcGenerator.getScrum(),TypeOfCompletion.DEVELOPING,JdbcGenerator.getListEmployee(),"04-12-2020","10-12-2020","05-12-2020",TaskTypes.TESTERS_TASK).getData();
        instance.insertTestersTask(testersTask);
        long id = ((DataProviderJdbc) instance).getTestersTaskByParam(testersTask).getData();
        Assertions.assertEquals(Fail,instance.writeTestersTaskComment(id,"").getStatus());
    }

    @Test
    public void getTaskListByScrumMasterSuccess() {
        Task task = instance.createTask(15,"Descript",14553.0,instance.getEmployeeById(1).getData(),TypeOfCompletion.DEVELOPING,JdbcGenerator.getListEmployee(),"04-12-2020","10-12-2020","05-12-2020",TaskTypes.BASE_TASK).getData();
        instance.insertTask(task);
        Assertions.assertNotNull(instance.getTaskListByScrumMaster(1).getData());
    }

    @Test
    public void getTaskListByScrumMasterFail() {
        Task task = instance.createTask(15,"Descript",14553.0,instance.getEmployeeById(6).getData(),TypeOfCompletion.DEVELOPING,JdbcGenerator.getListEmployee(),"04-12-2020","10-12-2020","05-12-2020",TaskTypes.BASE_TASK).getData();
        instance.insertTask(task);
        Assertions.assertNotEquals(0,instance.getTaskListByScrumMaster(6).getData().get(0).getId());
    }

    @Test
    public void getDevelopersTaskListByScrumMasterSuccess() {
        DevelopersTask developersTask = (DevelopersTask) instance.createTask(13,"Descript",14553.0,instance.getEmployeeById(7).getData(),TypeOfCompletion.DEVELOPING,JdbcGenerator.getListEmployee(),"04-12-2020","10-12-2020","05-12-2020",TaskTypes.DEVELOPERS_TASK).getData();
        instance.insertDevelopersTask(developersTask);
        Assertions.assertNotNull(instance.getDevelopersTaskListByScrumMaster(7).getData());
    }

    @Test
    public void getDevelopersTaskListByScrumMasterFail() {
        Task task = instance.createTask(15,"Descript",14553.0,instance.getEmployeeById(6).getData(),TypeOfCompletion.DEVELOPING,JdbcGenerator.getListEmployee(),"04-12-2020","10-12-2020","05-12-2020",TaskTypes.BASE_TASK).getData();
        instance.insertTask(task);
        Assertions.assertNotEquals(0,instance.getTaskListByScrumMaster(6).getData().get(0).getId());
    }

    @Test
    public void getTestersTaskListByScrumMasterSuccess() {
        TestersTask task = (TestersTask) instance.createTask(15,"Descript",14553.0,instance.getEmployeeById(8).getData(),TypeOfCompletion.DEVELOPING,JdbcGenerator.getListEmployee(),"04-12-2020","10-12-2020","05-12-2020",TaskTypes.TESTERS_TASK).getData();
        instance.insertTestersTask(task);
        Assertions.assertNotNull(instance.getTestersTaskListByScrumMaster(8).getData());
    }

    @Test
    public void getTestersTaskListByScrumMasterFail() {
        TestersTask task = (TestersTask)  instance.createTask(15,"Descript",14553.0,instance.getEmployeeById(8).getData(),TypeOfCompletion.DEVELOPING,JdbcGenerator.getListEmployee(),"04-12-2020","10-12-2020","05-12-2020",TaskTypes.TESTERS_TASK).getData();
        instance.insertTestersTask(task);
        Assertions.assertNotEquals(0,instance.getTestersTaskListByScrumMaster(8).getData().get(0).getId());

    }

    @Test
    public void createProjectSuccess() {
        Outcomes o = instance.createProject(5,"TestProject","05-12-2020",JdbcGenerator.getListTask()).getStatus();
        Assertions.assertEquals(Complete,o);
    }

    @Test
    public void createProjectFail() {
        Outcomes o = instance.createProject(5,"","05-12-2020",JdbcGenerator.getListTask()).getStatus();
        Assertions.assertEquals(Fail,o);
    }

    @Test
    public void getProjectByIdSuccess(){
        Employee testdeveloper = instance.getEmployeeById(1).getData();
        Outcomes o = instance.getProjectByScrumMasterId(testdeveloper.getId(),1).getStatus();
        Assertions.assertEquals(Complete,o);
    }

    @Test
    public void getProjectByIdFail() {
        Employee employee =  instance.getEmployeeById(1).getData();
        Outcomes o = instance.getProjectByScrumMasterId(employee.getId(),100).getStatus();
        Assertions.assertNotEquals(Fail,o);
    }

    @Test
    public void calculateProjectCostSuccess() {
        double cost = (double) instance.calculateProjectCost(1).getData();
        Assertions.assertNotEquals(cost,5.0);
    }

    @Test
    public void calculateProjectCostFail() {
        Outcomes o = instance.calculateProjectCost(100).getStatus();
        Assertions.assertEquals(Fail,o);
    }

    @Test
    public void calculateProjectTimeSuccess() {
        Outcomes o = instance.calculateProjectTime(1).getStatus();
        Assertions.assertEquals(Complete,o);
    }

    @Test
    public void calculateProjectTimeFail() {
        Outcomes o = instance.calculateProjectCost(100).getStatus();
        Assertions.assertEquals(Fail,o);
    }

    @Test
    public void createEmployeeSuccess() {
        Outcomes o =instance.createEmployee(11,"createEmployeeSuccess","Vasilyev","vas1ly","vasyan","VasLy@","9bba8047-f0aa-473d-aef9-6905edcd3f99","Team13",TypeOfEmployee.Tester).getStatus();
        Assertions.assertEquals(Complete, o);
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
        Assertions.assertNotEquals(0,size);
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
        Task testTask = instance.createTask(17,"Descript",14553.0,instance.getEmployeeById(1).getData(),TypeOfCompletion.DEVELOPING,JdbcGenerator.getListEmployee(),"04-12-2020","10-12-2020","05-12-2020",TaskTypes.BASE_TASK).getData();
        Task testTask2 = instance.createTask(18,"Descript",14553.0,instance.getEmployeeById(1).getData(),TypeOfCompletion.DEVELOPING,JdbcGenerator.getListEmployee(),"04-12-2020","10-12-2020","05-12-2020",TaskTypes.BASE_TASK).getData();
        task.add(testTask);
        task.add(testTask2);
        Project project = instance.createProject(7,"TestProject8","05-12-2020",task).getData();
        instance.insertProject(project);
        Assertions.assertNotNull(instance.getProjectListByScrummasterId(1).getData());
    }

    @Test
    public void getProjectListByScrummasterIdFail() {
        Assertions.assertNotEquals(0,instance.getProjectListByScrummasterId(1).getData());
    }

    @Test
    public void getProjectListByIdSuccess() {
        Outcomes o = instance.getProjectListByScrummasterId(1).getStatus();
        Assertions.assertNotEquals(Empty,o);
    }

    @Test
    public void getProjectListByIdFail(){
        Assertions.assertNotNull(instance.getProjectListByScrummasterId(100).getData());
    }

    @Test
    public void getScrumMasterTaskListSuccess(){
        Assertions.assertNotNull(instance.getScrumMasterTaskList(1,TaskTypes.BASE_TASK));
    }

    @Test
    public void getScrumMasterTaskListFail(){
        Assertions.assertEquals(Outcomes.Fail,instance.getScrumMasterTaskList(1,TaskTypes.CUSTOM).getStatus());
    }



}