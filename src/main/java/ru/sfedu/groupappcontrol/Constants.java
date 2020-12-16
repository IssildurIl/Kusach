package ru.sfedu.groupappcontrol;


public class Constants {
    public static final String TEST_NAME="test.name";
    public static final String DATE_FORMAT = "dd-mm-yyyy";
    public static final String FILE_EXTENSION_CSV = "FILE_EXTENSION_CSV";
    public static final String CSV_PATH ="CSV_PATH";
    public static final String BaseComment = "Base Comment";
    public static final String FILE_EXTENSION_XML = "FILE_EXTENSION_XML";
    public static final String XML_PATH ="XML_PATH";

    public static final String JDBC_DRIVER = "db_driver";
    public static final String DB_CONNECT = "db_url";
    public static final String DB_USER = "db_user";
    public static final String DB_PASS = "db_pass";

    public static final String EMPLOYEE_ID = "EmployeeId";
    public static final String EMPLOYEE_FIRSTNAME = "firstName";
    public static final String EMPLOYEE_LASTNAME = "lastName";
    public static final String EMPLOYEE_LOGIN = "login";
    public static final String EMPLOYEE_PASSWORD = "password";
    public static final String EMPLOYEE_EMAIL = "email";
    public static final String EMPLOYEE_TOKEN = "token";
    public static final String EMPLOYEE_DEPARTMENT = "department";
    public static final String EMPLOYEE_TYPE_OF_EMLPOYEE = "typeOfEmployee";

    public static final String DEVELOPER_ID = "Id";
    public static final String DEVELOPER_FIRSTNAME = "firstName";
    public static final String DEVELOPER_LASTNAME = "lastName";
    public static final String DEVELOPER_LOGIN = "login";
    public static final String DEVELOPER_PASSWORD = "password";
    public static final String DEVELOPER_EMAIL = "email";
    public static final String DEVELOPER_TOKEN = "token";
    public static final String DEVELOPER_DEPARTMENT = "department";
    public static final String DEVELOPER_TYPE_OF_EMLPOYEE = "typeOfEmployee";
    public static final String DEVELOPER_TYPE_OF_DEVELOPER = "status";
    public static final String DEVELOPER_TYPE_OF_PROGRAMMING_LANGUAGE = "programmingLanguage";

    public static final String TESTER_ID = "Id";
    public static final String TESTER_FIRSTNAME = "firstName";
    public static final String TESTER_LASTNAME = "lastName";
    public static final String TESTER_LOGIN = "login";
    public static final String TESTER_PASSWORD = "password";
    public static final String TESTER_EMAIL = "email";
    public static final String TESTER_TOKEN = "token";
    public static final String TESTER_DEPARTMENT = "department";
    public static final String TESTER_TYPE_OF_EMLPOYEE = "typeOfEmployee";
    public static final String TESTER_TYPE_OF_DEVELOPER = "status";
    public static final String TESTER_TYPE_OF_PROGRAMMING_LANGUAGE = "programmingLanguage";
    public static final String TESTER_TYPE_OF_TESTER = "typeOfTester";

    public static final String DEVELOPERSTASK_ID="Id";
    public static final String TESTERSTASK_ID="Id";

    public static final String TASK_ID = "TaskId";
    public static final String TASK_DESCRIPTION= "taskDescription";
    public static final String TASK_MONEY = "money";
    public static final String TASK_SCRUMMASTER = "scrumMaster";
    public static final String TASK_TYPE_OF_COMPLETION = "status";
    public static final String TASK_TEAM = "team";
    public static final String TASK_CREATED_DATE = "createdDate";
    public static final String TASK_DEADLINE = "deadline";
    public static final String TASK_LAST_UPDATE = "lastUpdate";
    public static final String TASK_TASK_TYPES = "taskType";

    public static final String DEVELOPERS_TASK_COMMENTS = "developerComments";
    public static final String DEVELOPERS_TASK_TYPE = "developerTaskType";

    public static final String TESTERS_BUG_STATUS = "bugStatus";
    public static final String TESTERS_BUG_DESCRIPTION = "bugDescription";

    public static final String PROJECT_ID="ProjectId";
    public static final String PROJECT_TITLE = "title";
    public static final String PROJECT_TAKE_INTO_DEVELOPMENT= "takeIntoDevelopment";
    public static final String PROJECT_TASK= "task";

    public static final String SELECT_ALL = "SELECT * FROM '%s' WHERE Id=%d";
//    public static final String SELECT_ALL_EMPLOYEE = "SELECT * FROM '%s' WHERE Id=%d";
//    public static final String SELECT_ALL_DEVELOPER = "SELECT * FROM Developer WHERE Id=%d;";
//    public static final String SELECT_ALL_TESTER = "SELECT * FROM Tester WHERE Id=%d;";
//    public static final String SELECT_ALL_TASK = "SELECT * FROM Task WHERE Id=%d;";
//    public static final String SELECT_ALL_DEVELOPERS_TASK = "SELECT * FROM DevelopersTask WHERE Id=%d;";
//    public static final String SELECT_ALL_TESTERS_TASK = "SELECT * FROM TestersTask WHERE Id=%d;";
//    public static final String SELECT_ALL_PROJECT = "SELECT * FROM Project WHERE Id=%d;";

    public static final String INSERT_EMPLOYEE = "INSERT INTO Employee VALUES (%d, '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s');";
    public static final String INSERT_DEVELOPER = "INSERT INTO Developer VALUES (%d, '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s','%s', '%s');";
    public static final String INSERT_TESTER = "INSERT INTO Tester VALUES (%d, '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s','%s', '%s','%s');";
    public static final String INSERT_TASK = "INSERT INTO Task VALUES (%d, '%s', %f, '%s', '%s', '%s', '%s', '%s', '%s','%s');";
    public static final String INSERT_DEVELOPERS_TASK = "INSERT INTO DevelopersTask VALUES (%d, '%s', %f, '%s', '%s', '%s', '%s', '%s', '%s','%s','%s','%s');";
    public static final String INSERT_TESTERS_TASK = "INSERT INTO TestersTask VALUES (%d, '%s', %f, '%s', '%s', '%s', '%s', '%s', '%s','%s','%s','%s');";
    public static final String INSERT_PROJECT = "INSERT INTO Project VALUES (%d, '%s', '%s', '%s');";

    public static final String UPDATE_EMPLOYEE = "UPDATE Employee SET firstName='%s', lastName='%s', login='%s', password='%s', email='%s',token='%s',department='%s',typeOfEmployee ='%s' WHERE EmployeeId=%d";
    public static final String UPDATE_DEVELOPER = "UPDATE Developer SET firstName='%s', lastName='%s', login='%s', password='%s', email='%s',token='%s',department='%s',typeOfEmployee ='%s',status='%s',programmingLanguage='%s' WHERE Id=%d";
    public static final String UPDATE_TESTER = "UPDATE Tester SET firstName='%s', lastName='%s', login='%s', password='%s', email='%s',token='%s',department='%s',typeOfEmployee ='%s',status='%s',programmingLanguage='%s',typeOfTester='%s' WHERE Id=%d";
    public static final String UPDATE_TASK = "UPDATE Task SET taskDescription='%s', money=%f, scrumMaster=%d, status='%s', team='%s',createdDate='%s', deadline='%s', lastUpdate='%s', taskType='%s' WHERE TaskId=%d;";
    public static final String UPDATE_DEVELOPERS_TASK = "UPDATE DevelopersTask SET  taskDescription='%s', money=%f, scrumMaster=%d, status='%s', team='%s',createdDate='%s', deadline='%s', lastUpdate='%s', taskType='%s', developerComments='%s' ,developerTaskType='%s' WHERE Id=%d;";
    public static final String UPDATE_TESTERS_TASK = "UPDATE TestersTask SET taskDescription='%s', money=%f, scrumMaster=%d, status='%s', team='%s',createdDate='%s', deadline='%s', lastUpdate='%s', taskType='%s',bugStatus='%s',bugDescription='%s' WHERE ProjectId=%d;";
    public static final String UPDATE_PROJECT = "UPDATE Project SET title='%s' , takeIntoDevelopment='%s', task='%s' WHERE id=%d;";

    public static final String DELETE_EMPLOYEE = "DELETE FROM Employee WHERE EmployeeId=%d;";
    public static final String DELETE_DEVELOPER = "DELETE FROM Developer WHERE Id=%d;";
    public static final String DELETE_TESTER = "DELETE FROM Tester WHERE Id=%d";
    public static final String DELETE_TASK = "DELETE FROM Task WHERE TaskId=%d;";
    public static final String DELETE_DEVELOPERS_TASK = "DELETE FROM DevelopersTask WHERE Id=%d;";
    public static final String DELETE_TESTERS_TASK = "DELETE FROM TestersTask WHERE Id=%d;";
    public static final String DELETE_PROJECT = "DELETE FROM Project WHERE ProjectId=%d;";

    public static final String SPLIT = "\\W+";
    public static final String GET_ID = "\\d+";

    public static final String Employee = "employee";
    public static final String Developer = "developer";
    public static final String Tester = "tester";
    public static final String Task = "task";
    public static final String DevelopersTask = "developersTask";
    public static final String TestersTask = "testersTask";

}
