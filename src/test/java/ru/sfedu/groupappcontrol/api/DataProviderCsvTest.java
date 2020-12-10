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


class DataProviderCsvTest {
    public static DataProviderCsv instance = new DataProviderCsv();


    private static final Logger log = LogManager.getLogger(DataProviderCsvTest.class);


    DataProviderCsvTest() {
    }

    @BeforeAll
    static void setCsvEnv() throws IOException {
        deleteAll();
        addRecord();
    }

    @Test
    public void changeProfileInfoSuccess() throws IOException {
        Employee testEmployee = (Employee) instance.createEmployee(1,"Employee6","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Employee).getData();
        log.debug(testEmployee);
        instance.changeProfileInfo(testEmployee);
        assertEquals(instance.getEmployeeByID(Employee.class,1).getData().getId(),testEmployee.getId());
    }
    @Test
    public void changeProfileInfoFail() throws IOException {
        Employee testEmployee = (Employee) instance.createEmployee(1,"Employee6","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer).getData();
        log.debug(testEmployee);
        instance.changeProfileInfo(testEmployee);
        assertNotEquals(instance.getEmployeeByID(Employee.class,2).getData().getId(), testEmployee.getId());
    }

    @Test
    public void changeTaskStatusSuccess() throws IOException {
        instance.changeTaskStatus(1, TypeOfCompletion.TESTING.toString());
        Task task =  instance.getTaskByID(Task.class,1).getData();
        log.debug(task);
        assertEquals(TESTING, task.getStatus());
    }

    @Test
    public void changeTaskStatusFail() throws IOException {
        Task task = instance.getTaskById(Task.class,1).getData();
        log.debug(task);
        assertEquals(Fail,instance.changeTaskStatus(1,"").getStatus());
    }

    @Test
    public void writeCommentSuccess() throws IOException {
        instance.writeComment(DevelopersTask.class,1,"i wrote this comment now");
        DevelopersTask developersTask = (DevelopersTask) instance.getTaskById(DevelopersTask.class,1).getData();
        assertEquals("i wrote this comment now",developersTask.getTaskDescription());
    }

    @Test
    public void writeCommentFail() throws IOException {
        DevelopersTask developersTask = (DevelopersTask) instance.getTaskById(DevelopersTask.class,1).getData();
        log.debug(developersTask);
        assertEquals(Fail,instance.writeComment(DevelopersTask.class,1,"").getStatus());
    }

    @Test
    public void getUserInfoListSuccess() throws IOException {
        Employee testEmp = (Employee) instance.getUserInfoList(Employee.class,2).getData();
        log.debug(testEmp);
        //assertEquals(testEmp.getId(),2);
        assertEquals(2,  testEmp.getId());
    }

    @Test
    public void UpdateUserInfo() throws IOException {
        Employee testEmployee = (Employee) instance.createEmployee(3,"Emp","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer).getData();
        log.debug(testEmployee);
        instance.updateGenericEmployee(Employee.class,testEmployee);
        //assertNotEquals(instance.getUserInfoList(2).getStatus().toString(),outcomes.toString());
    }



    @Test
    public void getUserInfoListFail() throws IOException {
        Outcomes outcomes = instance.getUserInfoList(Employee.class,200).getStatus();
        log.debug(outcomes);
        assertNotEquals(instance.getUserInfoList(Employee.class,2).getStatus().toString(),outcomes.toString());
    }

    @Test
    public void getTaskListSuccess() throws IOException {
        List<Task> developersTask= (List<Task>) instance.getTaskListByScrumMaster(Task.class,8).getData();
        Outcomes o = instance.getTaskListByScrumMaster(Task.class,8).getStatus();
        log.debug(developersTask);
        assertEquals(o.toString(),Complete.toString());
    }

    @Test
    public void getTaskListFail() throws IOException {
        List<Task> developersTask= (List<Task>) instance.getTaskListByScrumMaster(Task.class,100).getData();
        Outcomes o = instance.getTaskListByScrumMaster(Task.class,100).getStatus();
        log.debug(developersTask);
        assertNotEquals(o.toString(),Outcomes.NotFound.toString());
    }

    @Test
    public void getTaskInfoSuccess() throws IOException {
        Outcomes o = instance.getTaskInfo(DevelopersTask.class,1).getStatus();
        log.info(instance.getTaskInfo(DevelopersTask.class,1).getData());
        assertEquals(Complete,o);
    }

    @Test
    public void getTaskSuccess() throws IOException {
        log.info(instance.getTasksByUser(1,1).getData());
    }

    @Test
    public void getProjectStatistic() throws IOException {
        log.info(instance.getProjectStatistic(1).getData());
    }

    @Test
    public void calculateTaskCostSuccess() throws IOException {
        Task testTask = (Task) instance.createTask(1,"Descript",14553.0,getScrum(),TypeOfCompletion.DEVELOPING,getListEmployee(),"04-12-2020","10-12-2020","05-12-2020",TaskTypes.BASE_TASK).getData();
        log.debug(instance.calculateTaskCost(testTask).getData());
        Assertions.assertEquals(instance.calculateTaskCost(testTask).getData().longValue(), 87318.0);
    }

    @Test
    public void calculateTaskCostFail() throws IOException {
        Task testTask = (Task) instance.createTask(1,"Descript",14553.0,getScrum(),TypeOfCompletion.DEVELOPING,getListEmployee(),"04-12-2020","10-12-2020","05-12-2020",TaskTypes.BASE_TASK).getData();
        log.debug(instance.calculateTaskCost(testTask).getData());
        assertNotEquals(instance.calculateTaskCost(testTask).getData(),1.0);
    }

    @Test
    public void calculateProjectCostSuccess() throws IOException {
        Project testProject=(Project) instance.getProject(1).getData();
        double cost = (double) instance.calculateProjectCost(testProject).getData();
        log.debug(cost);
    }

//    @log.info
//    public void calculateProjectCostFail() throws IOException {
//
//    }

    @Test
    public void calculateProjectTimeSuccess() throws IOException {
        Project testProject = (Project) instance.getProject(1).getData();
        Outcomes o = instance.calculateProjectTime(testProject).getStatus();
        assertEquals(Complete,o);
    }
    @Test
    public void calculateProjectTimeFail() throws IOException {
        Project testProject = (Project) instance.getProject(1).getData();
        long time = (long)instance.calculateProjectTime(testProject).getData();
        assertNotEquals(1000,time);
    }

    @Test
    public void createTaskSuccess() throws IOException {
        List<Task> task = new ArrayList<>();
        Task testTask = (Task) instance.createTask(12,"Descript",14553.0,getScrum(),TypeOfCompletion.DEVELOPING,getListEmployee(),"04-12-2020","10-12-2020","05-12-2020",TaskTypes.BASE_TASK).getData();
        task.add(testTask);
        instance.insertGenericTask(Task.class,task,true);
        assertEquals(12,instance.getTaskById(Task.class,12).getData().getId());
    }
    @Test
    public void createTaskFail() throws IOException {
        List<Task> task = new ArrayList<>();
        Task testTask = (Task) instance.createTask(12,"Descript",14553.0,getScrum(),TypeOfCompletion.DEVELOPING,getListEmployee(),"04-12-2020","10-12-2020","05-12-2020",TaskTypes.BASE_TASK).getData();
        task.add(testTask);
        instance.insertGenericTask(Task.class,task,true);
        assertNotEquals(14551.0,instance.getTaskById(Task.class,12).getData().getMoney());
    }

    @Test
    public void deleteTaskSuccess() throws IOException {
        List<Task> task = new ArrayList<>();
        Task testTask = (Task) instance.createTask(12,"Descript",14553.0,getScrum(),TypeOfCompletion.DEVELOPING,getListEmployee(),"04-12-2020","10-12-2020","05-12-2020",TaskTypes.BASE_TASK).getData();
        task.add(testTask);
        instance.insertGenericTask(Task.class,task,true);
        instance.deleteGenericTask(Task.class,12);
        assertEquals(Fail,instance.getTaskById(Task.class,12).getStatus());
    }
    @Test
    public void deleteTaskFail() throws IOException {
        List<Task> task = new ArrayList<>();
        instance.deleteGenericTask(Task.class,12);
        assertNotEquals(Complete,instance.getTaskById(Task.class,12).getStatus());
    }

    @Test
    public void getTaskWorkerSuccess() throws IOException {
        Employee testdeveloper = (Employee) instance.getEmployeeByID(Employee.class,1).getData();
        Outcomes o = instance.getTaskWorker(testdeveloper,1).getStatus();
        assertNotEquals(Complete,o);
    }
    @Test
    public void getTaskWorkerFail() throws IOException {
        Employee testdeveloper = (Employee) instance.getEmployeeByID(Employee.class,1).getData();
        Outcomes o = instance.getTaskWorker(testdeveloper,1).getStatus();
        assertEquals(Fail,o);
    }

    @Test
    public void getTaskByIdSuccess() throws IOException {
        Task task = instance.getTaskById(Task.class,1).getData();
        assertEquals(1,task.getId());
    }
    @Test
    public void getTaskByIdFail() throws IOException {
        Task task = instance.getTaskById(Task.class,1).getData();
        assertNotEquals(2,task.getId());
    }

    @Test
    public void getTaskListByIdSuccess() throws IOException {
        Outcomes o = instance.getTaskListById(1).getStatus();
        log.debug(instance.getTaskListById(1).getData());
        assertNotEquals(Fail,o);
    }
    @Test
    public void getTaskListByIdFail() throws IOException {
        Outcomes o = instance.getTaskListById(100).getStatus();
        assertNotEquals(Complete,o);
    }

    @Test
    public void deleteProjectSuccess() throws IOException {
        List<Project> projectList = new ArrayList<>();
        Project project = (Project) instance.createProject(5,"TestProject","05-12-2020",getListTask()).getData();
        projectList.add(project);
        instance.insertGenericProject(Project.class,projectList,true);
        instance.deleteProject(project);
        Outcomes o = instance.getProject(5).getStatus();
        assertEquals(Fail,o);
    }
    @Test
    public void deleteProjectFail() throws IOException {
        List<Project> projectList = new ArrayList<>();
        Project project = (Project) instance.createProject(5,"TestProject","05-12-2020",getListTask()).getData();
        projectList.add(project);
        instance.insertGenericProject(Project.class,projectList,true);
        instance.deleteProject(project);
        Outcomes o = instance.getProject(5).getStatus();
        assertNotEquals(Complete,o);
    }

    @Test
    public void createProjectSuccess() throws IOException {
        Outcomes o = instance.createProject(5,"TestProject","05-12-2020",getListTask()).getStatus();
        assertEquals(Complete,o);
    }


    @Test
    public void getProjectByIdFail() throws IOException {
        Employee testdeveloper = (Employee) instance.getEmployeeByID(Employee.class,1).getData();
        Outcomes o = instance.getProjectById(testdeveloper,100).getStatus();
        assertEquals(Empty,o);
    }

    @Test
    public void getProjectListByIdSuccess() throws IOException {
        Employee testdeveloper = (Employee) instance.getEmployeeByID(Employee.class,1).getData();
        Outcomes o = instance.getProjectListById(testdeveloper).getStatus();
        assertEquals(Fail,o);
    }
    @Test
    public void getProjectListByIdFail() throws IOException {
        Employee testdeveloper = (Employee) instance.getEmployeeByID(Employee.class,1).getData();
        Outcomes o = instance.getProjectListById(testdeveloper).getStatus();
        assertNotEquals(Complete,o);
    }

    @Test
    public void correctEmployeeParametersSuccess() throws IOException {
        Employee test = (Employee) instance.createEmployee(4,"Employee4","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Employee).getData();
        instance.correctEmployeeParameters(test);
        Employee testemployee = (Employee) instance.getEmployeeByID(Employee.class,4).getData();
        assertEquals("Employee4",testemployee.getFirstName());
    }
    @Test
    public void correctEmployeeParametersFail() throws IOException {
        Employee test = (Employee) instance.createEmployee(4,"Employee4","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Employee).getData();
        instance.correctEmployeeParameters(test);
        Employee testemployee = (Employee) instance.getEmployeeByID(Employee.class,4).getData();
        assertNotEquals("Ilya",testemployee.getFirstName());
    }

    @Test
    public void addEmployeeToTaskSuccess() throws IOException {
        Employee testdeveloper= (Employee) instance.getEmployeeByID(Employee.class,1).getData();
        Task testTask = (Task) instance.createTask(1,"Descript",14553.0,getScrum(),TypeOfCompletion.DEVELOPING,getListEmployee(),"04-12-2020","10-12-2020","05-12-2020",TaskTypes.BASE_TASK).getData();
        Outcomes outcomes= instance.addEmployeeToTask(testTask,testdeveloper).getStatus();
        assertEquals(outcomes, Complete);
    }
    @Test
    public void addEmployeeToTaskFail() throws IOException {
        Employee testdeveloper = (Employee) instance.getEmployeeByID(Employee.class,1).getData();
        Task testTask = (Task) instance.createTask(1,"Descript",14553.0,getScrum(),TypeOfCompletion.DEVELOPING,getListEmployee(),"04-12-2020","10-12-2020","05-12-2020",TaskTypes.BASE_TASK).getData();
        Outcomes outcomes = instance.addEmployeeToTask(testTask,testdeveloper).getStatus();
        assertNotEquals(outcomes, Fail);
    }

    @Test
    public void deleteEmployeeFromTaskSuccess() throws IOException {
        Employee testdeveloper = (Employee) instance.getEmployeeByID(Employee.class,1).getData();
        Task testTask = (Task) instance.createTask(1,"Descript",14553.0,getScrum(),TypeOfCompletion.DEVELOPING,getListEmployee(),"04-12-2020","10-12-2020","05-12-2020",TaskTypes.BASE_TASK).getData();
        Outcomes outcomes = instance.deleteEmployeeFromTask(testTask,testdeveloper).getStatus();
        assertEquals(outcomes, Complete);
    }
    @Test
    public void deleteEmployeeFromTaskFail() throws IOException {
        Employee testdeveloper= (Employee) instance.getEmployeeByID(Employee.class,1).getData();
        Task testTask = (Task) instance.createTask(1,"Descript",14553.0,getScrum(),TypeOfCompletion.DEVELOPING,getListEmployee(),"04-12-2020","10-12-2020","05-12-2020",TaskTypes.BASE_TASK).getData();
        Outcomes outcomes = instance.deleteEmployeeFromTask(testTask,testdeveloper).getStatus();
        assertNotEquals(outcomes, Fail);
    }

    @Test
    public void createEmployeeSuccess() throws IOException {
        List<Tester> testerList= new ArrayList<>();
        Tester tester = (Tester) instance.createEmployee(11,"Vasily","Vasilyev","vas1ly","vasyan","VasLy@","9bba8047-f0aa-473d-aef9-6905edcd3f99","Team13",TypeOfEmployee.Tester).getData();
        testerList.add(tester);
        instance.insertGenericEmployee(Tester.class,testerList,true);
        assertEquals("Vasily",instance.getEmployeeByID(Tester.class,11).getData().getFirstName().toString());
    }

    public static void addRecord() throws IOException {
    List<Employee> employees = new ArrayList<>();
    List<Developer> developers = new ArrayList<>();
    List<Task> tasks = new ArrayList<>();
    List<Tester> testers = new ArrayList<>();
    List<DevelopersTask> developersTasks = new ArrayList<DevelopersTask>();
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
        instance.insertGenericEmployee(Employee.class,employees,false);
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
        instance.insertGenericEmployee(Developer.class,developers,false);
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
        instance.insertGenericEmployee(Tester.class,testers,false);
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
        instance.insertGenericTask(Task.class,tasks,false);
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
        developersTasks.add(developerTask);
        instance.insertGenericTask(DevelopersTask.class,developersTasks,false);
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
        instance.insertGenericTask(TestersTask.class,testersTasks,false);
    }
    for (int i=1; i<=4; i++) {
        Project project=new Project();
        project.setId(i);
        project.setTitle(title[i-1]);
        project.setTakeIntoDevelopment(createdDate[i-1]);
        project.setTask(getListTask());
        projects.add(project);
        instance.insertGenericProject(Project.class,projects,false);
    }
}
    private static Employee getScrum(){
        List<Employee> listemployee = instance.select(Employee.class);
        int max=9; int min=0;
        Employee employee =listemployee.get((int) ((Math.random() * ((max - min) + 1)) + min));
        return employee;
    }
    private static List<Employee> getListEmployee(){
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
    private static List<Task> getListTask(){
        List<Task> listTask = instance.select(Task.class);
        List<DevelopersTask> developers = instance.select(DevelopersTask.class);
        int max=9; int min=0;
        for (int i=1;i<=3; i++) {
            DevelopersTask developersTask =developers.get((int)((Math.random() * ((max - min) + 1)) + min));
            listTask.add(developersTask);
        }
        List<TestersTask> testers = instance.select(TestersTask.class);
        for (int i=1;i<=3; i++) {
            TestersTask testersTask = testers.get((int) ((Math.random() * ((max - min) + 1)) + min));
            listTask.add(testersTask);
        }
        return listTask;
    }
    public static void deleteAll(){
        instance.deleteRecord(Employee.class);
        instance.deleteRecord(Developer.class);
        instance.deleteRecord(Tester.class);
        instance.deleteRecord(Task.class);
        instance.deleteRecord(DevelopersTask.class);
        instance.deleteRecord(TestersTask.class);
        instance.deleteRecord(Project.class);
    }
}