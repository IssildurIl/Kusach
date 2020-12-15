package ru.sfedu.groupappcontrol.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.groupappcontrol.Constants;
import ru.sfedu.groupappcontrol.Result;
import ru.sfedu.groupappcontrol.models.*;
import ru.sfedu.groupappcontrol.models.enums.*;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static ru.sfedu.groupappcontrol.utils.ConfigurationUtil.getConfigurationEntry;

public class DataProviderJdbc implements DataProvider {

    private Connection connection;
    private Logger log = LogManager.getLogger(DataProviderJdbc.class);

    @Override
    public void initDataSource() {

    }

    private Connection getConnection() throws ClassNotFoundException, SQLException, IOException {
        Class.forName(getConfigurationEntry(Constants.JDBC_DRIVER));
        connection = DriverManager.getConnection(
                getConfigurationEntry(Constants.DB_CONNECT),
                getConfigurationEntry(Constants.DB_USER),
                getConfigurationEntry(Constants.DB_PASS)
        );
        return connection;
    }

    public Result execute(String sql){
        try {
            PreparedStatement statement = getConnection().prepareStatement(sql);
            statement.executeUpdate();
            getConnection().close();
            return new Result(Outcomes.Complete);
        } catch (SQLException | IOException | ClassNotFoundException e) {
            log.error(e);
            return new Result(Outcomes.Fail);
        }
    }

    public ResultSet select(String sql) {
        log.info(sql);
        try {
            PreparedStatement statement = getConnection().prepareStatement(sql);
            getConnection().close();
            return statement.executeQuery();
        } catch (SQLException | ClassNotFoundException | IOException e) {
            log.info(e);
        }
        return null;
    }

    public ResultSet setRes(Class cl,long id){
        ResultSet set = select(String.format(Constants.SELECT_ALL,
                cl.getSimpleName().toLowerCase(),id));
        return set;

    }

    @Override
    public <T> List<T> select(Class<T> cl) {
        return null;
    }

    @Override
    public <T extends Task> Result<T> getTaskByID(Class<T> cl, long id) {
        switch (cl.getSimpleName().toLowerCase()){
            case Constants.Task:
                getBaseTaskByID(id);
            case Constants.DevelopersTask:
                getDevelopersTaskByID(id);
            case Constants.TestersTask:
                getTestersTaskByID(id);
            default:
                return new Result<>(Outcomes.Fail);
        }
    }

    public Result getBaseTaskByID(long id){
        try {
            ResultSet set = setRes(Task.class,id);
            if(set!=null && set.next()){
                Task task = new Task();
                setBasicTask(set,task);
                List<Task> taskList = new ArrayList<>();
                taskList.add(task);
                return new Result(Outcomes.Complete);
            }else{
                return new Result(Outcomes.Fail);
            }
        } catch (SQLException e) {
            log.error(e);
            return new Result(Outcomes.Fail);
        }
    }

    public Result getDevelopersTaskByID(long id){
        try {
            ResultSet set = setRes(DevelopersTask.class,id);
            if(set!=null && set.next()){
                DevelopersTask developersTask = new DevelopersTask();
                setBasicTask(set,developersTask);
                developersTask.setDeveloperComments(set.getString(Constants.DEVELOPERS_TASK_COMMENTS));
                developersTask.setDeveloperTaskType(DeveloperTaskType.valueOf(set.getString(Constants.DEVELOPERS_TASK_TYPE)));
                List<DevelopersTask> developersTasks = new ArrayList<>();
                developersTasks.add(developersTask);
                return new Result(Outcomes.Complete);
            }else{
                return new Result(Outcomes.Fail);
            }
        } catch (SQLException e) {
            log.error(e);
            return new Result(Outcomes.Fail);
        }
    }

    public Result getTestersTaskByID(long id){
        try {
            ResultSet set = setRes(DevelopersTask.class,id);
            if(set!=null && set.next()){
                TestersTask testersTask = new TestersTask();
                setBasicTask(set,testersTask);
                testersTask.setBugStatus(BugStatus.valueOf(set.getString(Constants.TESTERS_BUG_STATUS)));
                testersTask.setBugDescription(set.getString(Constants.TESTERS_BUG_DESCRIPTION));
                List<TestersTask> testersTasks = new ArrayList<>();
                testersTasks.add(testersTask);
                return new Result(Outcomes.Complete);
            }else{
                return new Result(Outcomes.Fail);
            }
        } catch (SQLException e) {
            log.error(e);
            return new Result(Outcomes.Fail);
        }
    }

    @Override
    public Result getProjectByID(long id) {
        try {
            ResultSet set = setRes(Project.class,id);
            if(set!=null && set.next()){
                Project project = new Project();
                setProject(set,project);
                List<Project> projects = new ArrayList<>();
                projects.add(project);
                return new Result(Outcomes.Complete);
            }else{
                return new Result(Outcomes.Fail);
            }
        } catch (SQLException e) {
            log.error(e);
            return new Result(Outcomes.Fail);
        }
    }

    @Override
    public <T extends Employee> Result<T> getEmployeeByID(Class<T> cl, long id) {
        switch (cl.getSimpleName().toLowerCase()){
            case Constants.Employee:
                getBaseEmployeeByID(id);
            case Constants.Developer:
                getDeveloperByID(id);
            case Constants.Tester:
                getTesterByID(id);
            default:
                return new Result<>(Outcomes.Fail);
        }
    }

    public Result getBaseEmployeeByID(long id){
        try {
            ResultSet set = setRes(Employee.class, id);
            if(set!=null && set.next()){
                Employee employee = new Employee();
                setBasicEmployee(set,employee);
                List<Employee> employees = new ArrayList<>();
                employees.add(employee);
                return new Result(Outcomes.Complete);
            }else{
                return new Result(Outcomes.Fail);
            }
        } catch (SQLException e) {
            log.error(e);
            return new Result(Outcomes.Fail);
        }
    }

    public Result getDeveloperByID(long id){
        try {
            ResultSet set = setRes(Developer.class,id);
            if(set!=null && set.next()){
                Developer developer = new Developer();
                setBasicEmployee(set,developer);
                developer.setStatus(TypeOfDevelopers.valueOf(set.getString(Constants.DEVELOPER_TYPE_OF_DEVELOPER)));
                developer.setProgrammingLanguage(ProgrammingLanguage.valueOf(set.getString(Constants.DEVELOPER_TYPE_OF_PROGRAMMING_LANGUAGE)));
                List<Developer> developers = new ArrayList<>();
                developers.add(developer);
                return new Result(Outcomes.Complete);
            }else{
                return new Result(Outcomes.Fail);
            }
        } catch (SQLException e) {
            log.error(e);
            return new Result(Outcomes.Fail);
        }
    }

    public Result getTesterByID(long id){
        try {
            ResultSet set = setRes(Tester.class,id);
            if(set!=null && set.next()){
                Tester tester = new Tester();
                setBasicEmployee(set,tester);
                tester.setStatus(TypeOfDevelopers.valueOf(set.getString(Constants.TESTER_TYPE_OF_DEVELOPER)));
                tester.setProgrammingLanguage(ProgrammingLanguage.valueOf(set.getString(Constants.TESTER_TYPE_OF_PROGRAMMING_LANGUAGE)));
                tester.setTypeOfTester(TypeOfTester.valueOf(set.getString(Constants.TESTER_TYPE_OF_TESTER)));
                List<Tester> testers = new ArrayList<>();
                testers.add(tester);
                return new Result(Outcomes.Complete);
            }else{
                return new Result(Outcomes.Fail);
            }
        } catch (SQLException e) {
            log.error(e);
            return new Result(Outcomes.Fail);
        }
    }



    public void insertEmployee(Employee employee) {
        execute(String.format(Constants.INSERT_EMPLOYEE,employee.getClass().getSimpleName().toLowerCase(),employee.getId(),employee.getFirstName(),employee.getLastName(),employee.getLogin(),employee.getPassword(),employee.getEmail(),employee.getToken(),employee.getDepartment(),employee.getTypeOfEmployee().toString()));

    }

    @Override
    public <T extends Task> Result<Void> insertGenericTask(Class<T> cl, List<T> list, boolean append) {
        return null;
    }

    @Override
    public <T extends Employee> Result<Void> insertGenericEmployee(Class<T> cl, List<T> list, boolean append) {
        return null;
    }

    @Override
    public Result<Project> insertProject(List<Project> list, boolean append) {
        return null;
    }


    @Override
    public <T extends Task> Result<T> deleteGenericTask(Class<T> cl, long id) {
        return null;
    }

    @Override
    public <T extends Employee> Result<T> deleteGenericEmployee(Class<T> cl, long id) {
        return null;
    }

    @Override
    public Result<Project> deleteGenericProject(long id) {
        return null;
    }


    @Override
    public <T extends Task> Result<T> updateGenericTask(Class<T> cl, T updElement) {
        return null;
    }

    @Override
    public <T extends Employee> Result<T> updateGenericEmployee(Class<T> cl, T updElement) {
        return null;
    }

    @Override
    public Result<Project> updateGenericProject(Project project) {
        return null;
    }


    @Override
    public Result<Employee> changeProfileInfo(Employee employee) {
        return null;
    }

    @Override
    public Result<Void> changeTaskStatus(long id, String status) {
        return null;
    }

    @Override
    public <T extends Task> Result<T> writeComment(Class<T> cl, long id, String comment) {
        return null;
    }

    @Override
    public <T extends Employee> Result<List<T>> getUserInfoList(Class<T> cl, long userId) {
        return null;
    }

    @Override
    public Result<List<Task>> getScrumMasterTaskList(long userId, TaskTypes taskTypes) {
        return null;
    }

    @Override
    public <T extends Task> Result<List<T>> getTaskListByScrumMaster(Class<T> cl, long userId) {
        return null;
    }

    @Override
    public Result<List<Task>> getTasksByUser(long userId, long taskId) {
        return null;
    }

    @Override
    public Result<Double> calculateTaskCost(Task task) {
        return null;
    }

    @Override
    public Result<Project> getProjectByProjectID(long projectId) {
        return null;
    }

    @Override
    public Result<Long> calculateProjectCost(Project project) {
        return null;
    }

    @Override
    public Result<Long> calculateProjectTime(Project project) {
        return null;
    }

    @Override
    public Result<Task> createTask(long id, String taskDescription, Double money, Employee scrumMaster, TypeOfCompletion status, List<Employee> team, String createdDate, String deadline, String lastUpdate, TaskTypes taskType) {
        return null;
    }

    @Override
    public Result<Void> deleteTask(Task task) {
        return null;
    }

    @Override
    public Result<List<Task>> getTaskWorker(Employee employee, long taskId) {
        return null;
    }

    @Override
    public <T extends Task> Result<T> getAnyTaskByTaskId(Class<T> cl, long taskId) {
        return null;
    }

    @Override
    public Result<Void> deleteProject(Project project) {
        return null;
    }

    @Override
    public Result<Void> updateProject(Project project) {
        return null;
    }

    @Override
    public Result<Project> createProject(long id, String title, String takeIntoDevelopment, List<Task> tasks) {
        return null;
    }

    @Override
    public Result<Project> getProjectById(Employee employee, long projectId) {
        return null;
    }

    @Override
    public Result<List<Project>> getProjectListByScrummasterId(long id) {
        return null;
    }

    @Override
    public Result<Employee> correctEmployeeParameters(Employee editedEmployee) {
        return null;
    }

    @Override
    public Result<Employee> addEmployeeToTask(Task task, Employee employee) {
        return null;
    }

    @Override
    public Result<Void> deleteEmployeeFromTask(Task task, Employee employee) {
        return null;
    }

    @Override
    public Result<Employee> createEmployee(long id, String firstName, String lastName, String login, String password, String email, String token, String department, TypeOfEmployee typeOfEmployee) {
        return null;
    }

    @Override
    public <T extends Task> Result<T> getTaskInfoGeneric(Class<T> cl, long taskId) {
        return null;
    }

    @Override
    public List<Employee> getAllEmployee() {
        return null;
    }

    @Override
    public <T> Result<T> deleteRecord(Class<T> cl) {
        return null;
    }

    @Override
    public Result<Task> getTasks(long id) {
        return null;
    }

    @Override
    public List<Task> getAllTask() {
        return null;
    }

    @Override
    public void deleteAllRecord() {

    }


    private void setBasicEmployee(ResultSet set,Employee employee){
        try {
            employee.setId(set.getLong(Constants.EMPLOYEE_ID));
            employee.setFirstName(set.getString(Constants.EMPLOYEE_FIRSTNAME));
            employee.setLastName(set.getString(Constants.EMPLOYEE_LASTNAME));
            employee.setLogin(set.getString(Constants.EMPLOYEE_LOGIN));
            employee.setPassword(set.getString(Constants.EMPLOYEE_PASSWORD));
            employee.setEmail(set.getString(Constants.EMPLOYEE_EMAIL));
            employee.setToken(set.getString(Constants.EMPLOYEE_TOKEN));
            employee.setDepartment(set.getString(Constants.EMPLOYEE_DEPARTMENT));
            employee.setTypeOfEmployee(TypeOfEmployee.valueOf(set.getString(Constants.EMPLOYEE_TYPE_OF_EMLPOYEE)));
        } catch (SQLException e) {
            log.error(e);
        }
    }

    private void setBasicTask(ResultSet set,Task task){
        try {
            task.setId(set.getLong(Constants.TASK_ID));
            task.setTaskDescription(set.getString(Constants.TASK_DESCRIPTION));
            task.setMoney(set.getDouble(Constants.TASK_MONEY));
            task.setScrumMaster(getEmployeeByID(Employee.class, set.getLong(Constants.EMPLOYEE_ID)).getData());
            task.setStatus(TypeOfCompletion.valueOf(set.getString(Constants.TASK_TYPE_OF_COMPLETION)));
            //task.setTeam();
            task.setCreatedDate(set.getString(Constants.TASK_CREATED_DATE));
            task.setDeadline(set.getString(Constants.TASK_DEADLINE));
            task.setLastUpdate(set.getString(Constants.TASK_LAST_UPDATE));
            task.setTaskType(TaskTypes.valueOf(set.getString(Constants.TASK_TASK_TYPES)));
        } catch (SQLException e) {
            log.error(e);
        }
    }

    private void setProject(ResultSet set,Project project){
        try {
            project.setId(set.getLong(Constants.PROJECT_ID));
            project.setTitle(set.getString(Constants.PROJECT_TITLE));
            project.setTakeIntoDevelopment(set.getString(Constants.PROJECT_TAKE_INTO_DEVELOPMENT));
            //project.setTask();
        } catch (SQLException e) {
            log.error(e);
        }
    }
}
