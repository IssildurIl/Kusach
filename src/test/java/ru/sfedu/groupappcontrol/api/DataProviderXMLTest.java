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
    public static DataProvider instance = new DataProviderXML();


    private static final Logger log = LogManager.getLogger(DataProviderXMLTest.class);


    DataProviderXMLTest() {
    }
    @BeforeAll
    static void setCSVEnv() throws IOException {
        instance.deleteAllRecord();
        addRecord();
    }

    @Test
    public void changeProfileInfoSuccess() {
        Employee testEmployee = (Employee) instance.createEmployee(1,"Employee6","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Employee).getData();
        log.debug(testEmployee);
        instance.changeProfileInfo(testEmployee);
        Assertions.assertEquals(instance.getEmployeeById(1).getData().getId(),testEmployee.getId());
    }
    @Test
    public void changeProfileInfoFail() {
        Employee testEmployee = (Employee) instance.createEmployee(1,"Employee6","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer).getData();
        log.debug(testEmployee);
        instance.changeProfileInfo(testEmployee);
        Assertions.assertNotEquals(instance.getEmployeeById(2).getData().getId(), testEmployee.getId());
    }

    @Test
    public void changeTaskStatusSuccess() {
        instance.changeTaskStatus(1, TypeOfCompletion.TESTING.toString());
        Task task =  instance.getTaskById(1).getData();
        log.debug(task);
        Assertions.assertEquals(TESTING, task.getStatus());
    }

    @Test
    public void changeTaskStatusFail() {
        Task task = instance.getTaskById(1).getData();
        log.debug(task);
        Assertions.assertEquals(Fail,instance.changeTaskStatus(1,"").getStatus());
    }

    @Test
    public void writeCommentSuccess() {
        instance.writeDevelopersTaskComment(1,"i wrote this comment now");
        DevelopersTask developersTask = (DevelopersTask) instance.getDevelopersTaskById(1).getData();
        log.debug(developersTask);
        Assertions.assertEquals("i wrote this comment now",developersTask.getTaskDescription());
    }

    @Test
    public void writeCommentFail() {
        DevelopersTask developersTask = (DevelopersTask) instance.getDevelopersTaskById(1).getData();
        log.debug(developersTask);
        Assertions.assertEquals(Fail,instance.writeDevelopersTaskComment(1,"").getStatus());
    }


    @Test
    public void getScrumMasterTaskListSuccess(){
        Employee employee = (Employee) instance.getEmployeeById(10).getData();
        List<Task> list = new ArrayList<>();
        Task testTask = (Task) instance.createTask(15,"Descript",14553.0,employee,TypeOfCompletion.DEVELOPING,getListEmployee(),"04-12-2020","10-12-2020","05-12-2020",TaskTypes.BASE_TASK).getData();
        list.add(testTask);
        instance.insertTask(list,true);
        List<Task> taskList= instance.select(Task.class);
        log.info(taskList);
        List<Task> testlist = instance.getScrumMasterTaskList(10,TaskTypes.BASE_TASK).getData();
        log.debug(testlist);
        Assertions.assertEquals(15,testlist.get(0).getId());
    }

    @Test
    public void getScrumMasterTaskListFail(){
        Employee employee = (Employee) instance.getEmployeeById(10).getData();
        List<Task> list = new ArrayList<>();
        Task testTask = (Task) instance.createTask(11,"Descript",14553.0,employee,TypeOfCompletion.DEVELOPING,getListEmployee(),"04-12-2020","10-12-2020","05-12-2020",TaskTypes.BASE_TASK).getData();
        list.add(testTask);
        instance.insertTask(list,true);
        List<Task> taskList= instance.select(Task.class);
        log.info(taskList);
        List<Task> testlist = instance.getScrumMasterTaskList(10,TaskTypes.CUSTOM).getData();
        log.debug(testlist);
        Outcomes outcomes = instance.getScrumMasterTaskList(10,TaskTypes.CUSTOM).getStatus();
        Assertions.assertEquals(outcomes,Fail);
    }

    @Test
    public void getTaskInfoSuccess() {
        Outcomes o = instance. getDevelopersTaskById(1).getStatus();
        log.info(instance. getDevelopersTaskById(1).getData());
        Assertions.assertEquals(Complete,o);
    }

    @Test
    public void getTaskInfoFail() {
        Outcomes o = instance. getDevelopersTaskById(15).getStatus();
        log.info(instance. getDevelopersTaskById(15).getData());
        Assertions.assertEquals(Fail,o);
    }

    @Test
    public void getTaskListSuccess() {
        List<Task> developersTask= (List<Task>) instance.getTaskListByScrumMaster(8).getData();
        Outcomes o = instance.getTaskListByScrumMaster(8).getStatus();
        log.debug(developersTask);
        Assertions.assertEquals(Complete,o);
    }

    @Test
    public void getTaskListFail() {
        List<Task> developersTask= (List<Task>) instance.getTaskListByScrumMaster(100).getData();
        Outcomes o = instance.getTaskListByScrumMaster(100).getStatus();
        log.debug(developersTask);
        Assertions.assertNotEquals(o.toString(),Outcomes.NotFound.toString());
    }

    @Test
    public void getTasksByUserSuccess() {
        Employee employee = (Employee) instance.getEmployeeById(1).getData();
        List<Task> list = new ArrayList<>();
        List<Employee> list1 = getListEmployee();
        list1.add(employee);
        Task testTask = (Task) instance.createTask(11,"Descript",14553.0,employee,TypeOfCompletion.DEVELOPING,list1,"04-12-2020","10-12-2020","05-12-2020",TaskTypes.BASE_TASK).getData();
        list.add(testTask);
        instance.insertTask(list,true);
        List<Task> taskList= instance.select(Task.class);
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
    public void calculateTaskCostSuccess() {
        Task testTask = (Task) instance.createTask(1,"Descript",14553.0,getScrum(),TypeOfCompletion.DEVELOPING,getListEmployee(),"04-12-2020","10-12-2020","05-12-2020",TaskTypes.BASE_TASK).getData();
        log.debug(instance.calculateTaskCost(testTask).getData());
        Assertions.assertEquals(instance.calculateTaskCost(testTask).getData().longValue(), 87318.0);
    }

    @Test
    public void calculateTaskCostFail() {
        Task testTask = (Task) instance.createTask(1,"Descript",14553.0,getScrum(),TypeOfCompletion.DEVELOPING,getListEmployee(),"04-12-2020","10-12-2020","05-12-2020",TaskTypes.BASE_TASK).getData();
        log.debug(instance.calculateTaskCost(testTask).getData());
        assertNotEquals(instance.calculateTaskCost(testTask).getData(),1.0);
    }

    @Test
    public void calculateProjectCostSuccess() {
        Project testProject=(Project) instance.getProjectByProjectID(1).getData();
        double cost = (double) instance.calculateProjectCost(testProject).getData();
        log.debug(cost);
        assertNotEquals(cost,5.0);
    }


    @Test
    public void calculateProjectTimeSuccess() {
        Project testProject = (Project) instance.getProjectByProjectID(1).getData();
        Outcomes o = instance.calculateProjectTime(testProject).getStatus();
        assertEquals(Complete,o);
    }
    @Test
    public void calculateProjectTimeFail() {
        Project testProject = (Project) instance.getProjectByProjectID(1).getData();
        long time = (long)instance.calculateProjectTime(testProject).getData();
        assertNotEquals(1000,time);
    }

    @Test
    public void createTaskSuccess() {
        List<Task> task = new ArrayList<>();
        Task testTask = (Task) instance.createTask(12,"Descript",14553.0,getScrum(),TypeOfCompletion.DEVELOPING,getListEmployee(),"04-12-2020","10-12-2020","05-12-2020",TaskTypes.BASE_TASK).getData();
        task.add(testTask);
        instance.insertTask(task,true);
        assertEquals(12,instance.getTaskById(12).getData().getId());
    }
    @Test
    public void createTaskFail() {
        List<Task> task = new ArrayList<>();
        Task testTask = (Task) instance.createTask(12,"Descript",14553.0,getScrum(),TypeOfCompletion.DEVELOPING,getListEmployee(),"04-12-2020","10-12-2020","05-12-2020",TaskTypes.BASE_TASK).getData();
        task.add(testTask);
        instance.insertTask(task,true);
        assertNotEquals(14551.0,instance.getTaskById(12).getData().getMoney());
    }

    @Test
    public void deleteTaskSuccess() {
        List<Task> task = new ArrayList<>();
        Task testTask = (Task) instance.createTask(12,"Descript",14553.0,getScrum(),TypeOfCompletion.DEVELOPING,getListEmployee(),"04-12-2020","10-12-2020","05-12-2020",TaskTypes.BASE_TASK).getData();
        task.add(testTask);
        instance.insertTask(task,true);
        List<Task> taskList = instance.select(Task.class);
        log.debug(taskList);
        instance.deleteTask(testTask.getId());
        List<Task> editedTaskList = instance.select(Task.class);
        log.debug(editedTaskList);
        Assertions.assertEquals(Fail,instance.getTaskById(12).getStatus());
    }
    @Test
    public void deleteTaskFail() {
        instance.deleteTask(12);
        Assertions.assertNotEquals(Complete,instance.getTaskById(12).getStatus());
    }

    @Test
    public void getTaskWorkerSuccess() {
        Employee employee = (Employee) instance.getEmployeeById(1).getData();
        Outcomes o = instance.getTaskWorker(employee,1).getStatus();
        log.debug(instance.getTaskWorker(employee,1).getData());
        Assertions.assertEquals(Complete,o);
    }
    @Test
    public void getTaskWorkerFail() {
        Employee employee = (Employee) instance.getEmployeeById(1).getData();
        Outcomes o = instance.getTaskWorker(employee,15).getStatus();
        log.debug(instance.getTaskWorker(employee,15).getData());
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
    public void deleteProjectSuccess() {
        List<Project> projectList = new ArrayList<>();
        Project project = (Project) instance.createProject(5,"TestProject","05-12-2020",getListTask()).getData();
        projectList.add(project);
        instance.insertProject(projectList,true);
        List<Project> list = instance.select(Project.class);
        log.debug(list);
        instance.deleteProject(project.getId());
        List<Project> list1 = instance.select(Project.class);
        log.debug(list1);
        Outcomes o = instance.getProjectByProjectID(5).getStatus();
        Assertions.assertEquals(Fail,o);
    }

    @Test
    public void deleteProjectFail() {
        List<Project> projectList = new ArrayList<>();
        Project project = (Project) instance.createProject(5,"TestProject","05-12-2020",getListTask()).getData();
        projectList.add(project);
        instance.insertProject(projectList,true);
        instance.deleteProject(project.getId());
        log.debug(project);
        Outcomes o = instance.getProjectByProjectID(5).getStatus();
        Assertions.assertNotEquals(Complete,o);
    }

    @Test
    public void updateProjectSuccess() {
        Project project = (Project) instance.createProject(4,"PROJECT FOR TESTING","05-12-2020",getListTask()).getData();
        instance.updateProject(project);
        log.debug(instance.getProjectByProjectID(4).getData());
        Assertions.assertEquals("PROJECT FOR TESTING",instance.getProjectByProjectID(4).getData().getTitle());
    }

    @Test
    public void updateProjectFail() {
        Project project = (Project) instance.createProject(4,"PROJECT FOR TESTING","05-12-2020",getListTask()).getData();
        instance.updateProject(project);
        log.debug(instance.getProjectByProjectID(4).getData());
        Assertions.assertNotEquals("", instance.getProjectByProjectID(4).getData().getId());
    }

    @Test
    public void createProjectSuccess() {
        Outcomes o = instance.createProject(5,"TestProject","05-12-2020",getListTask()).getStatus();
        List<Project> list = instance.select(Project.class);
        log.debug(list);
        Assertions.assertEquals(Complete,o);
    }

    @Test
    public void getProjectByIdSuccess(){
        Employee testdeveloper = (Employee) instance.getEmployeeById(1).getData();
        Outcomes o = instance.getProjectById(testdeveloper,1).getStatus();
        log.debug(testdeveloper);
        Assertions.assertEquals(Complete,o);
    }
    @Test
    public void getProjectByIdFail() {
        Employee testdeveloper = (Employee) instance.getEmployeeById(1).getData();
        Outcomes o = instance.getProjectById(testdeveloper,100).getStatus();
        log.debug(o);
        Assertions.assertEquals(Empty,o);
    }

    @Test
    public void getProjectListByIdSuccess() {
        Outcomes o = instance.getProjectListByScrummasterId(1).getStatus();
        log.debug(instance.getProjectListByScrummasterId(1).getStatus());
        Assertions.assertEquals(Fail,o);
    }
    @Test
    public void getProjectListByIdFail(){
        Outcomes o = instance.getProjectListByScrummasterId(1).getStatus();
        log.debug(instance.getProjectListByScrummasterId(1).getStatus());
        Assertions.assertNotEquals(Complete,o);
    }

    @Test
    public void correctEmployeeParametersSuccess() {
        Employee test = (Employee) instance.createEmployee(4,"Employee4","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Employee).getData();
        instance.correctEmployeeParameters(test);
        Employee testemployee = (Employee) instance.getEmployeeById(4).getData();
        log.debug(testemployee);
        Assertions.assertEquals("Employee4",testemployee.getFirstName());
    }
    @Test
    public void correctEmployeeParametersFail() {
        Employee test = (Employee) instance.createEmployee(4,"Employee4","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Employee).getData();
        instance.correctEmployeeParameters(test);
        Employee testemployee = (Employee) instance.getEmployeeById(4).getData();
        log.debug(testemployee);
        Assertions.assertNotEquals("Ilya",testemployee.getFirstName());
    }

    @Test
    public void addEmployeeToTaskSuccess() {
        Employee testdeveloper= (Employee) instance.getEmployeeById(1).getData();
        Task testTask = (Task) instance.createTask(1,"Descript",14553.0,getScrum(),TypeOfCompletion.DEVELOPING,getListEmployee(),"04-12-2020","10-12-2020","05-12-2020",TaskTypes.BASE_TASK).getData();
        log.debug(testTask);
        Outcomes outcomes= instance.addEmployeeToTask(testTask,testdeveloper).getStatus();
        Assertions.assertEquals(outcomes, Complete);
    }
    @Test
    public void addEmployeeToTaskFail(){
        Employee testdeveloper = (Employee) instance.getEmployeeById(1).getData();
        Task testTask = (Task) instance.createTask(1,"Descript",14553.0,getScrum(),TypeOfCompletion.DEVELOPING,getListEmployee(),"04-12-2020","10-12-2020","05-12-2020",TaskTypes.BASE_TASK).getData();
        log.debug(testTask);
        Outcomes outcomes = instance.addEmployeeToTask(testTask,testdeveloper).getStatus();
        Assertions.assertNotEquals(outcomes, Fail);
    }

    @Test
    public void deleteEmployeeFromTaskSuccess() {
        Employee testdeveloper = (Employee) instance.getEmployeeById(1).getData();
        Task testTask = (Task) instance.createTask(1,"Descript",14553.0,getScrum(),TypeOfCompletion.DEVELOPING,getListEmployee(),"04-12-2020","10-12-2020","05-12-2020",TaskTypes.BASE_TASK).getData();
        log.debug(testTask);
        Outcomes outcomes = instance.deleteEmployeeFromTask(testTask,testdeveloper).getStatus();
        Assertions.assertEquals(outcomes, Complete);
    }
    @Test
    public void deleteEmployeeFromTaskFail() {
        Employee testdeveloper= (Employee) instance.getEmployeeById(1).getData();
        Task testTask = (Task) instance.createTask(1,"Descript",14553.0,getScrum(),TypeOfCompletion.DEVELOPING,getListEmployee(),"04-12-2020","10-12-2020","05-12-2020",TaskTypes.BASE_TASK).getData();
        log.debug(testTask);
        Outcomes outcomes = instance.deleteEmployeeFromTask(testTask,testdeveloper).getStatus();
        Assertions.assertNotEquals(outcomes, Fail);
    }

    @Test
    public void createEmployeeSuccess() throws IOException {
        List<Tester> testerList= new ArrayList<>();
        Tester tester = (Tester) instance.createEmployee(11,"Vasily","Vasilyev","vas1ly","vasyan","VasLy@","9bba8047-f0aa-473d-aef9-6905edcd3f99","Team13",TypeOfEmployee.Tester).getData();
        testerList.add(tester);
        instance.insertTester(testerList,true);
        log.debug(tester);
        Assertions.assertEquals("Vasily",instance.getTesterById(11).getData().getFirstName());
    }
    @Test
    public void UpdateUserInfo() throws IOException {
        Employee testEmployee = (Employee) instance.createEmployee(3,"Emp","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer).getData();
        log.debug(testEmployee);
        instance.updateEmployee(testEmployee);
        Employee employee = instance.getEmployeeById(3).getData();
        log.debug(employee);
        Assertions.assertNotEquals(instance.getEmployeeById(20).getStatus().toString(), Complete);
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
            instance.insertEmployee(employees,false);
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
            instance.insertDeveloper(developers,false);
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
            instance.insertTester(testers,false);
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
            instance.insertTask(tasks,false);
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
            instance.insertDevelopersTask(developersTasks,false);
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
            instance.insertTestersTask(testersTasks,false);
        }
        for (int i=1; i<=4; i++) {
            Project project=new Project();
            project.setId(i);
            project.setTitle(title[i-1]);
            project.setTakeIntoDevelopment(createdDate[i-1]);
            project.setTask(getListTask());
            projects.add(project);
            instance.insertProject(projects,false);
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
            DevelopersTask developersTask = developers.get((int)((Math.random() * ((max - min) + 1)) + min));
            listTask.add(developersTask);
        }
        List<TestersTask> testers = instance.select(TestersTask.class);
        for (int i=1;i<=3; i++) {
            TestersTask testersTask = testers.get((int) ((Math.random() * ((max - min) + 1)) + min));
            listTask.add(testersTask);
        }
        return listTask;
    }
}