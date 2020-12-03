//package ru.sfedu.groupappcontrol.api;
//
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import ru.sfedu.groupappcontrol.Result;
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
////
////    private final String DB_DRIVER = ConfigurationUtil.getConfigurationEntry("db_driver");
////    private final String DB_USER = ConfigurationUtil.getConfigurationEntry("db_user");
////    private final String DB_PASS = ConfigurationUtil.getConfigurationEntry("db_pass");
////    private final String DB_URL = ConfigurationUtil.getConfigurationEntry("db_url");
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
//    public void execute(String sql) throws SQLException, IOException, ClassNotFoundException {
//        log.info(sql);
//        PreparedStatement statement = getConnection().prepareStatement(sql);
//        statement.executeUpdate();
//        statement.close();
//    }
//
//    public ResultSet select (String sql) {
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
//    public Result writeComment(long id, String comment) {
//        return null;
//    }
//
//
//    @Override
//    public Result getUserInfoList(long userId) {
//        return null;
//    }
//
//    @Override
//    public Result getTaskList(long userId) {
//        return null;
//    }
//
//    @Override
//    public Result getTaskInfo(long taskId) {
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
//
//    @Override
//    public Result getProjectStatistic(long userId) {
//        return null;
//    }
//
//    @Override
//    public Result getProject(long userId, long projectId) {
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
//    public Result createTask(String taskDescription, Double money, Employee scrumMaster, TypeOfCompletion status, List<Employee> team, String createdDate, String deadline, String lastUpdate, TaskTypes taskType) {
//        return null;
//    }
//
//    @Override
//    public Result createTask( String taskDescription, Double money, String deadline,Employee scrumMaster,TypeOfCompletion status, List<Employee> team, String createdDate,String lastUpdate,TaskTypes taskType, DeveloperTaskType devTaskType,String developerComments) {
//        return null;
//    }
//
//    @Override
//    public Result createTask(String taskDescription, Double money, String deadline, Employee scrumMaster, TypeOfCompletion status, List<Employee> team, String createdDate, String lastUpdate, TaskTypes taskType, BugStatus bugStatus, String bugDescription) {
//        return null;
//    }
//
//    @Override
//    public Result deleteTask(Task task) {
//        return null;
//    }
//
//    @Override
//    public Result getTask(Employee employee, long taskId) {
//        return null;
//    }
//
//    @Override
//    public Result getTaskById(Employee employee, long taskId) {
//        return null;
//    }
//
//    @Override
//    public Result getTaskListById(Employee employee) {
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
//    public Result createProject(String title, String takeIntoDevelopment, List<Task> tasks) {
//        return null;
//    }
//
//    @Override
//    public Result getProject(Employee employee) {
//        return null;
//    }
//
//    @Override
//    public Result getProjectById(Employee employee, long projectId) {
//        return null;
//    }
//
//    @Override
//    public Result getProjecListById(Employee employee) {
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
//    public Result createEmployee(String firstName, String lastName, String login, String password, String email,String token, String department){
//        return null;
//    }
//
//    @Override
//    public Result createEmployee(String firstName, String lastName, String login, String password, String email,String token, String department, TypeOfDevelopers status, ProgrammingLanguage language) {
//        return null;
//    }
//
//    @Override
//    public Result createEmployee(String firstName, String lastName, String login, String password, String email,String token, String department, TypeOfDevelopers status, ProgrammingLanguage language,TypeOfTester typeOfTester) {
//        return null;
//    }
//}
