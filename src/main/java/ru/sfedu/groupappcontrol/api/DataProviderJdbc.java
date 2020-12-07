//package ru.sfedu.groupappcontrol.api;
//
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import ru.sfedu.groupappcontrol.models.Result;
//import ru.sfedu.groupappcontrol.models.*;
//import ru.sfedu.groupappcontrol.models.enums.*;
//import ru.sfedu.groupappcontrol.utils.ConfigurationUtil;
//
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.sql.*;
//import java.util.List;
//
//import static ru.sfedu.groupappcontrol.models.constants.Constants.*;
//import static ru.sfedu.groupappcontrol.utils.ConfigurationUtil.getConfigurationEntry;
//
//public class DataProviderJdbc implements DataProvider {
//    private final String PATH = getConfigurationEntry("JDBC_PATH");
//    private final String FILE_EXTENSION_JDBC = "FILE_EXTENSION_JDBC";
//    private final String DB_INSERT ="INSERT INTO %s VALUES(%d,'%s','%s','%s','%s','%s','%s','%s');";
//    private final String DB_SELECT ="SELECT * FROM %s WHERE id=%d";
//    private Connection connection;
//    private Logger log = LogManager.getLogger(DataProviderJdbc.class);
//
//    public DataProviderJdbc() throws IOException {
//    }
//
//
//    public void insertEmployee(Employee employee) throws SQLException, IOException, ClassNotFoundException {
//        System.out.println(String.format(DB_INSERT,employee.getClass().getSimpleName().toLowerCase(),employee.getId(),employee.getFirstName(), employee.getLastName(), employee.getLogin(), employee.getPassword(),employee.getEmail(),employee.getToken(),employee.getDepartment(),employee.getTypeOfEmployee()));
//        this.execute(String.format(DB_INSERT,employee.getClass().getSimpleName().toLowerCase(),employee.getId(),employee.getFirstName(),employee.getLastName(),employee.getLogin(),employee.getPassword(),employee.getEmail(),employee.getToken(),employee.getDepartment(),employee.getTypeOfEmployee().toString()));
//
//    }
//    public Employee getUserById(long id) throws SQLException, IOException, ClassNotFoundException {
//        ResultSet set = getResultSet(String.format(DB_SELECT,Employee.class.getSimpleName().toLowerCase(),id));
//        if(set!=null && set.next()){
//            Employee employee = new Employee();
//            employee.setId(set.getLong(ID));
//            employee.setFirstName(set.getString(USER_NAME));
//            employee.setLastName(set.getString(USER_LAST_NAME));
//            employee.setLogin(set.getString(USER_LOGIN));
//            employee.setPassword(set.getString(USER_PASSWORD));
//            employee.setEmail(set.getString(USER_EMAIL));
//            employee.setToken(set.getString(USER_TOKEN));
//            employee.setDepartment(set.getString(USER_DEPARTMENT));
//            employee.setTypeOfEmployee(set.getString(USER_TYPE_OF_EMPLOYEE));
//            return employee;
//        }else
//            {
//                log.error("ERROR");
//            }
//        return null;
//    }
//    private Connection getConnection() throws ClassNotFoundException, SQLException, IOException {
//        Class.forName(getConfigurationEntry("db_driver"));
//        connection = DriverManager.getConnection(
//                getConfigurationEntry("db_url"),
//                getConfigurationEntry("db_user"),
//                getConfigurationEntry("db_pass")
//        );
//        return connection;
//    }
//
//    public Result execute(String sql) {
//        try {
//            log.info(sql);
//            PreparedStatement statement = getConnection().prepareStatement(sql);
//            statement.executeUpdate();
//            statement.close();
//            return new Result(Outcomes.Complete);
//        } catch (SQLException | IOException | ClassNotFoundException e) {
//            log.error(e);
//            return new Result(Outcomes.Fail);
//        }
//
//    }
//    public ResultSet select(String sql) {
//        log.info(sql);
//        try {
//            PreparedStatement statement = getConnection().prepareStatement(sql);
//            getConnection().close();
//            return statement.executeQuery();
//        } catch (SQLException | ClassNotFoundException | IOException e) {
//            log.info(e);
//        }
//        return null;
//    }
//    private ResultSet getResultSet(String sql) throws SQLException, IOException, ClassNotFoundException {
//        PreparedStatement statement = getConnection().prepareStatement(sql);
//        ResultSet set = statement.executeQuery();
//        statement.close();
//        return set;
//    }
//
//
//    @Override
//    public Result changeProfileInfo(Employee employee) {
//        return null;
//    }
//
//    @Override
//    public Result changeTaskStatus(long id, String status) {
//        return null;
//    }
//
//    @Override
//    public <T extends Task> Result<T> writeComment(Class cl, long id, String comment) {
//        return null;
//    }
//
//    @Override
//    public Result getUserInfoList(long userId) {
//        return null;
//    }
//
//    @Override
//    public Result getBaseTaskList(long taskId) {
//        return null;
//    }
//
//    @Override
//    public Result getTaskInfo(Class cl, long taskId) {
//        return null;
//    }
//
//    @Override
//    public Result getTask(long userId, long taskId) {
//        return null;
//    }
//
//    @Override
//    public Result calculateTaskCost(Task task) {
//        return null;
//    }
//
//    @Override
//    public Result getProjectStatistic(long userId) {
//        return null;
//    }
//
//    @Override
//    public Result getProject(long projectId) {
//        return null;
//    }
//
//    @Override
//    public Result calculateProjectCost(Project project) {
//        return null;
//    }
//
//    @Override
//    public Result calculateProjectTime(Project project) {
//        return null;
//    }
//
//    @Override
//    public Result createTask(long id, String taskDescription, Double money, Employee scrumMaster, TypeOfCompletion status, List<Employee> team, String createdDate, String deadline, String lastUpdate, TaskTypes taskType) {
//        return null;
//    }
//
//    @Override
//    public Result deleteTask(Task task) {
//        return null;
//    }
//
//    @Override
//    public Result getTaskWorker(Employee employee, long taskId) {
//        return null;
//    }
//
//    @Override
//    public <T extends Task> Result<T> getTaskById(Class cl, long taskId) {
//        return null;
//    }
//
//    @Override
//    public Result getTaskListById(long id) {
//        return null;
//    }
//
//    @Override
//    public Result deleteProject(Project project) {
//        return null;
//    }
//
//    @Override
//    public Result updateProject(Project project) {
//        return null;
//    }
//
//    @Override
//    public Result createProject(long id, String title, String takeIntoDevelopment, List<Task> tasks) {
//        return null;
//    }
//
//    @Override
//    public Result getProjectById(Employee employee, long projectId) {
//        return null;
//    }
//
//    @Override
//    public Result getProjectListById(Employee employee) {
//        return null;
//    }
//
//    @Override
//    public Result correctEmployeeParameters(Employee editedEmployee) {
//        return null;
//    }
//
//    @Override
//    public Result addEmployeeToTask(Task task, Employee employee) {
//        return null;
//    }
//
//    @Override
//    public Result deleteEmployeeFromTask(Task task, Employee employee) {
//        return null;
//    }
//
//    @Override
//    public Result createEmployee(long id, String firstName, String lastName, String login, String password, String email, String token, String department, TypeOfEmployee typeOfEmployee) {
//        return null;
//    }
//
//    @Override
//    public <T> Result<T> deleteRecord(Class<T> cl) {
//        return null;
//    }
//}
