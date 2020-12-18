package ru.sfedu.groupappcontrol.api;

import lombok.NonNull;
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

    @Override
    public Result<Task> getTaskById(long id) {
        try {
            ResultSet set = setResById(Task.class,id);
            if(set!=null && set.next()){
                Task task = new Task();
                setBasicTask(set,task);
                List<Task> taskList = new ArrayList<>();
                taskList.add(task);
                return new Result(Outcomes.Complete,taskList);
            }else{
                return new Result(Outcomes.Fail);
            }
        } catch (SQLException e) {
            log.error(e);
            return new Result(Outcomes.Fail);
        }
    }

    @Override
    public Result<DevelopersTask> getDevelopersTaskById(long id) {
        try {
            ResultSet set = setResById(DevelopersTask.class,id);
            if(set!=null && set.next()){
                DevelopersTask developersTask = new DevelopersTask();
                setBasicTask(set,developersTask);
                developersTask.setDeveloperComments(set.getString(Constants.DEVELOPERS_TASK_COMMENTS));
                developersTask.setDeveloperTaskType(DeveloperTaskType.valueOf(set.getString(Constants.DEVELOPERS_TASK_TYPE)));
                List<DevelopersTask> developersTasks = new ArrayList<>();
                developersTasks.add(developersTask);
                return new Result(Outcomes.Complete,developersTasks);
            }else{
                return new Result<>(Outcomes.Fail);
            }
        } catch (SQLException e) {
            log.error(e);
            return new Result<>(Outcomes.Fail);
        }
    }

    @Override
    public Result<TestersTask> getTestersTaskById(long id) {
        try {
            ResultSet set = setResById(DevelopersTask.class,id);
            if(set!=null && set.next()){
                TestersTask testersTask = new TestersTask();
                setBasicTask(set,testersTask);
                testersTask.setBugStatus(BugStatus.valueOf(set.getString(Constants.TESTERS_BUG_STATUS)));
                testersTask.setBugDescription(set.getString(Constants.TESTERS_BUG_DESCRIPTION));
                List<TestersTask> testersTasks = new ArrayList<>();
                testersTasks.add(testersTask);
                return new Result(Outcomes.Complete,testersTasks);
            }else{
                return new Result(Outcomes.Fail);
            }
        } catch (SQLException e) {
            log.error(e);
            return new Result(Outcomes.Fail);
        }
    }

    @Override
    public Result<Employee> getEmployeeById(long id) {
        try {
            ResultSet set = setResById(Employee.class, id);
            if(set!=null && set.next()){
                Employee employee = new Employee();
                setBasicEmployee(set,employee);
                List<Employee> employees = new ArrayList<>();
                employees.add(employee);
                return new Result(Outcomes.Complete,employees);
            }else{
                return new Result(Outcomes.Fail);
            }
        } catch (SQLException e) {
            log.error(e);
            return new Result(Outcomes.Fail);
        }
    }

    @Override
    public Result<Developer> getDeveloperById(long id) {
        try {
            ResultSet set = setResById(Developer.class,id);
            if(set!=null && set.next()){
                Developer developer = new Developer();
                setBasicEmployee(set,developer);
                developer.setStatus(TypeOfDevelopers.valueOf(set.getString(Constants.DEVELOPER_TYPE_OF_DEVELOPER)));
                developer.setProgrammingLanguage(ProgrammingLanguage.valueOf(set.getString(Constants.DEVELOPER_TYPE_OF_PROGRAMMING_LANGUAGE)));
                List<Developer> developers = new ArrayList<>();
                developers.add(developer);
                return new Result(Outcomes.Complete,developers);
            }else{
                return new Result(Outcomes.Fail);
            }
        } catch (SQLException e) {
            log.error(e);
            return new Result(Outcomes.Fail);
        }
    }

    @Override
    public Result<Tester> getTesterById(long id) {
        try {
            ResultSet set = setResById(Tester.class,id);
            if(set!=null && set.next()){
                Tester tester = new Tester();
                setBasicEmployee(set,tester);
                tester.setStatus(TypeOfDevelopers.valueOf(set.getString(Constants.TESTER_TYPE_OF_DEVELOPER)));
                tester.setProgrammingLanguage(ProgrammingLanguage.valueOf(set.getString(Constants.TESTER_TYPE_OF_PROGRAMMING_LANGUAGE)));
                tester.setTypeOfTester(TypeOfTester.valueOf(set.getString(Constants.TESTER_TYPE_OF_TESTER)));
                List<Tester> testers = new ArrayList<>();
                testers.add(tester);
                return new Result(Outcomes.Complete,testers);
            }else{
                return new Result(Outcomes.Fail);
            }
        } catch (SQLException e) {
            log.error(e);
            return new Result(Outcomes.Fail);
        }
    }

    @Override
    public Result<Project> getProjectByID(long id) {
        try {
            ResultSet set = setResById(Project.class,id);
            if(set!=null && set.next()){
                Project project = new Project();
                setProject(set,project);
                List<Project> projects = new ArrayList<>();
                projects.add(project);
                return new Result(Outcomes.Complete,projects);
            }else{
                return new Result(Outcomes.Fail);
            }
        } catch (SQLException e) {
            log.error(e);
            return new Result(Outcomes.Fail);
        }
    }

    @Override
    public Result<Void> insertTask(List<Task> list, boolean append) {
        return null;
    }

    @Override
    public Result<Void> insertDevelopersTask(List<DevelopersTask> list, boolean append) {
        return null;
    }

    @Override
    public Result<Void> insertTestersTask(List<TestersTask> list, boolean append) {
        return null;
    }

    @Override
    public Result<Void> insertEmployee(List<Employee> list, boolean append) {
        try {
            boolean exist = list.stream().anyMatch(employee -> (execute(String.format(
                    Constants.INSERT_EMPLOYEE,employee.getFirstName(),employee.getLastName(),
                    employee.getLogin(), employee.getPassword(),employee.getEmail(),employee.getToken(),
                    employee.getDepartment(),employee.getTypeOfEmployee().toString()))
                    .getStatus()==Outcomes.Complete));
            insertToMappingEmployee(list);
            return (exist) ? new Result<>(Outcomes.Complete) : new Result<>(Outcomes.Fail);
        } catch (Exception e) {
            log.error(e);
            return new Result<>(Outcomes.Fail);
        }
    }
    //!!!!!!!!
    public Result<Void> insertToMappingEmployee(List<Employee> list) {
        try {
            boolean exist = list.stream().anyMatch(employee -> (execute(String.format(
                    Constants.INSERT_EMPLOYEE_TO_MAPPING,getEmployeeByParam(employee).getData().getId(),
                    employee.getTypeOfEmployee().toString()))
                    .getStatus()==Outcomes.Complete));
            return (exist) ? new Result<>(Outcomes.Complete) : new Result<>(Outcomes.Fail);
        } catch (Exception e) {
            log.error(e);
            return new Result<>(Outcomes.Fail);
        }
    }
    @Override
    public Result<Void> insertDeveloper(List<Developer> list, boolean append) {
        try {
            boolean exist = list.stream().anyMatch(developer -> execute(String.format
                    (Constants.INSERT_DEVELOPER,
                            developer.getFirstName(),developer.getLastName(),
                            developer.getLogin(),developer.getPassword(),developer.getEmail(),
                            developer.getToken(),developer.getDepartment(),
                            developer.getTypeOfEmployee().toString(),developer.getStatus().toString(),
                            developer.getProgrammingLanguage().toString())).getStatus()==Outcomes.Complete);
            return (exist) ? new Result<>(Outcomes.Complete) : new Result<>(Outcomes.Fail);
        } catch (Exception e) {
            log.error(e);
            return new Result<>(Outcomes.Fail);
        }
    }

    @Override
    public Result<Void> insertTester(List<Tester> list, boolean append) {
        try {
            boolean exist = list.stream().anyMatch(tester -> execute(String.format(
                    Constants.INSERT_TESTER,
                    tester.getFirstName(),tester.getLastName(),tester.getLogin(),tester.getPassword(),
                    tester.getEmail(),tester.getToken(),tester.getDepartment(),
                    tester.getTypeOfEmployee().toString(),tester.getTypeOfTester().toString())).
                    getStatus()==Outcomes.Complete);
            return (exist) ? new Result<>(Outcomes.Complete) : new Result<>(Outcomes.Fail);
        } catch (Exception e) {
            log.error(e);
            return new Result<>(Outcomes.Fail);
        }
    }

    @Override
    public Result<Project> insertProject(List<Project> list, boolean append) {
        return null;
    }

    @Override
    public Result<Void> deleteTask(long id) {
        try {
            return (execute(String.format(Constants.DELETE_TASK, id)).getStatus() == Outcomes.Complete) ?
                    new Result<>(Outcomes.Complete) : new Result<>(Outcomes.Fail);
        } catch (Exception e) {
            log.error(e);
            return new Result<>(Outcomes.Fail);
        }
    }

    @Override
    public Result<Void> deleteDevelopersTask(long id) {
        try {
            return (execute(String.format(Constants.DELETE_DEVELOPERS_TASK, id)).getStatus() == Outcomes.Complete) ?
                    new Result<>(Outcomes.Complete) : new Result<>(Outcomes.Fail);
        } catch (Exception e) {
            log.error(e);
            return new Result<>(Outcomes.Fail);
        }
    }
    //i
    @Override
    public Result<Void> deleteTestersTask(long id) {
        try {
            boolean a = execute(String.format(Constants.DELETE_TESTERS_TASK, id)).getStatus() == Outcomes.Complete;
            if (a) return new Result(Outcomes.Complete);
        } catch (Exception e) {
            log.error(e);
        }
        return new Result(Outcomes.Fail);
    }

    @Override
    public Result<Void> deleteEmployee(long id) {
        try {
            boolean a = execute(String.format(Constants.DELETE_EMPLOYEE, id)).getStatus() == Outcomes.Complete;
            if (a) return new Result(Outcomes.Complete);
        } catch (Exception e) {
            log.error(e);
        }
        return new Result(Outcomes.Fail);
    }

    @Override
    public Result<Void> deleteDeveloper(long id) {
        try {
            boolean a = execute(String.format(Constants.DELETE_DEVELOPER, id)).getStatus() == Outcomes.Complete;
            if (a) return new Result(Outcomes.Complete);
        } catch (Exception e) {
            log.error(e);
        }
        return new Result(Outcomes.Fail);
    }

    @Override
    public Result<Void> deleteTester(long id) {
        try {
            boolean a = execute(String.format(Constants.DELETE_TESTER, id)).getStatus() == Outcomes.Complete;
            if (a) return new Result(Outcomes.Complete);
        } catch (Exception e) {
            log.error(e);
        }
        return new Result(Outcomes.Fail);
    }

    @Override
    public Result<Void> deleteProject(long id) {
        try {
            boolean a = execute(String.format(Constants.DELETE_PROJECT, id)).getStatus() == Outcomes.Complete;
            if (a) return new Result(Outcomes.Complete);
        } catch (Exception e) {
            log.error(e);
        }
        return new Result(Outcomes.Fail);
    }

    @Override
    public Result<Void> updateTask(Task task) {
     return null;
    }

    @Override
    public Result<Void> updateDevelopersTask(DevelopersTask developersTask) {
        return null;
    }

    @Override
    public Result<Void> updateTestersTask(TestersTask testersTask) {
        return null;
    }

    @Override
    public Result<Void> updateEmployee(Employee employee) {
        return null;
    }

    @Override
    public Result<Void> updateDeveloper(Developer developer) {
        return null;
    }

    @Override
    public Result<Void> updateTester(Tester tester) {
        return null;
    }

    @Override
    public Result<Project> updateProject(Project project) {
        return null;
    }

    @Override
    public Result<Task> createTask(long id, @NonNull String taskDescription, @NonNull Double money, @NonNull Employee scrumMaster, @NonNull TypeOfCompletion status, @NonNull List<Employee> team, @NonNull String createdDate, @NonNull String deadline, @NonNull String lastUpdate, @NonNull TaskTypes taskType) {
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
    public Result<Task> getTasksByUser(long userId, long taskId) {
        return null;
    }

    @Override
    public Result<List<Task>> getTaskWorker(Employee employee, long taskId) {
        return null;
    }

    @Override
    public Result<Void> changeTaskStatus(long id, String status) {
        return null;
    }

    @Override
    public Result<Double> calculateTaskCost(Task task) {
        return null;
    }

    @Override
    public Result<Void> writeBaseTaskComment(long id, String comment) {
        return null;
    }

    @Override
    public Result<Void> writeDevelopersTaskComment(long id, String comment) {
        return null;
    }

    @Override
    public Result<Void> writeTestersTaskComment(long id, String comment) {
        return null;
    }

    @Override
    public Result<List<Task>> getTaskListByScrumMaster(long id) {
        return null;
    }

    @Override
    public Result<List<DevelopersTask>> getDevelopersTaskListByScrumMaster(long id) {
        return null;
    }

    @Override
    public Result<List<TestersTask>> getTestersTaskListByScrumMaster(long id) {
        return null;
    }

    @Override
    public Result<Project> createProject(long id, @NonNull String title, @NonNull String takeIntoDevelopment, @NonNull List<Task> tasks) {
        return null;
    }

    @Override
    public Result<Project> getProjectByProjectID(long projectId) {
        return null;
    }

    @Override
    public Result<List<Project>> getProjectById(Employee employee, long projectId) {
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
    public Result<Employee> createEmployee(long id, @NonNull String firstName, @NonNull String lastName, @NonNull String login, @NonNull String password, @NonNull String email, @NonNull String token, @NonNull String department, @NonNull TypeOfEmployee typeOfEmployee) {
        return null;
    }

    @Override
    public List<Employee> getAllEmployee() {
        return null;
    }

    @Override
    public Result<List<Task>> getScrumMasterTaskList(long userId, TaskTypes taskTypes) {
        return null;
    }

    @Override
    public Result<List<Project>> getProjectListByScrummasterId(long id) {
        return null;
    }

    @Override
    public Result<Void> deleteEmployeeFromTask(Task task, Employee employee) {
        return null;
    }

    @Override
    public Result<Employee> changeProfileInfo(Employee editedEmployee) {
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
    public <T> Result<T> deleteRecord(Class<T> cl) {
        return null;
    }

    @Override
    public void deleteAllRecord() {

    }

    @Override
    public <T> List<T> select(Class<T> cl) {
        return null;
    }

    private Connection getConnection() throws ClassNotFoundException, SQLException, IOException {
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
            statement.execute();
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

    public ResultSet setResById(Class cl, long id){
        ResultSet set = select(String.format(Constants.SELECT_ALL,
                cl.getSimpleName().toLowerCase(),id));
        return set;
    }
    private void setBasicTask(ResultSet set,Task task){
        try {
            task.setId(set.getLong(Constants.TASK_ID));
            task.setTaskDescription(set.getString(Constants.TASK_DESCRIPTION));
            task.setMoney(set.getDouble(Constants.TASK_MONEY));
            //task.setScrumMaster();
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
    public void setBasicEmployee(ResultSet set,Employee employee){
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

    public void setBasicEmployeeParams(Employee employee,
                                 String firstName,
                                 String lastName,
                                 String login,
                                 String email,
                                 String password,
                                 String token,
                                 String department,
                                 TypeOfEmployee typeOfEmployee){
        employee.setFirstName(firstName);
        employee.setLastName(lastName);
        employee.setLogin(login);
        employee.setPassword(password);
        employee.setEmail(email);
        employee.setToken(token);
        employee.setDepartment(department);
        employee.setTypeOfEmployee(typeOfEmployee);
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

    public Result<Long> selectEmployee(Employee employee){
        try {
            ResultSet set = select(String.format(
                    Constants.SELECT_EMPLOYEE,employee.getFirstName(),
                    employee.getLastName(),employee.getLogin(),
                    employee.getPassword(),employee.getEmail(),
                    employee.getToken(),employee.getDepartment(),
                    employee.getTypeOfEmployee()));
            if(set!=null && set.next()){
                long id = set.getLong(Constants.EMPLOYEE_ID);
//                Employee newEmployee = new Employee();
//                setBasicEmployee(set,newEmployee);
                return new Result(Outcomes.Complete,id);
            }else{
                return new Result(Outcomes.Fail);
            }
        } catch (SQLException e) {
            log.error(e);
            return new Result(Outcomes.Fail);
        }
    }

    public Result<Employee> getEmployeeByParam(Employee employee) {
        try {
            ResultSet res = select(String.format(Constants.SELECT_EMPLOYEE, employee.getFirstName(),employee.getLastName(),
                    employee.getLogin(),employee.getPassword(),employee.getEmail(),employee.getToken(),
                    employee.getDepartment(),employee.getTypeOfEmployee().toString()));

            if (res != null && res.next()) {
                Employee getEmployee = new Employee();

                getEmployee.setId(res.getLong(Constants.EMPLOYEE_ID));
                return new Result<Employee>(Outcomes.Complete, getEmployee);
            }
        } catch (SQLException e) {
            log.error(e);
        }
        return new Result(Outcomes.Fail);
    }
}
