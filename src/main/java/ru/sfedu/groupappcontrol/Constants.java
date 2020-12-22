package ru.sfedu.groupappcontrol;


public class Constants {
    public static final String TEST_NAME="test.name";
    public static final String DATE_FORMAT = "dd-mm-yyyy";
    public static final String FILE_EXTENSION_CSV = "FILE_EXTENSION_CSV";
    public static final String CSV_PATH ="CSV_PATH";
    public static final String BaseComment = "Base Comment";
    public static final String FILE_EXTENSION_XML = "FILE_EXTENSION_XML";
    public static final String XML_PATH ="XML_PATH";

    public static final String DB_CONNECT = "db_url";
    public static final String DB_USER = "db_user";
    public static final String DB_PASS = "db_pass";

    public static final String MAP_EMPLOYEE_ID = "EMPLOYEEID";

    public static final String EMPLOYEE_ID = "Id";
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

    public static final String TASK_ID = "Id";
    public static final String TASK_DESCRIPTION= "taskDescription";
    public static final String TASK_MONEY = "money";
    public static final String TASK_SCRUMMASTER = "scrumMaster";
    public static final String TASK_TYPE_OF_COMPLETION = "status";
    public static final String TASK_CREATED_DATE = "createdDate";
    public static final String TASK_DEADLINE = "deadline";
    public static final String TASK_LAST_UPDATE = "lastUpdate";
    public static final String TASK_TASK_TYPES = "taskType";

    public static final String DEVELOPERS_TASK_COMMENTS = "developerComments";
    public static final String DEVELOPERS_TASK_TYPE = "developerTaskType";

    public static final String TESTERS_BUG_STATUS = "bugStatus";
    public static final String TESTERS_BUG_DESCRIPTION = "bugDescription";

    public static final String PROJECT_ID="Id";
    public static final String PROJECT_TITLE = "title";
    public static final String PROJECT_TAKE_INTO_DEVELOPMENT= "takeIntoDevelopment";

    public static final String SELECT_ALL = "SELECT * FROM %s WHERE Id=%d";

    //Mapping
    public static final String SELECT_EMPLOYEE_FROM_MAPPING = "SELECT * FROM TASKTOEMPLOYEEMAPING WHERE TASKID=%d AND TASKTYPE='%s'";
    public static final String SELECT_TASK_FROM_MAPPING = "SELECT * FROM TASKTOEMPLOYEEMAPING WHERE EMPLOYEEID=%d AND EMPLOYEETYPE='%s'";
    public static final String INSERT_TO_MAPPING = "INSERT INTO TASKTOEMPLOYEEMAPING(TASKID,TASKTYPE,EMPLOYEEID,EMPLOYEETYPE) VALUES (%d,'%s',%d,'%s');";
    public static final String INSERT_TASK_TO_MAPPING = "INSERT INTO TASKTOEMPLOYEEMAPING(TASKID, TASKTYPE) VALUES(%d, '%s')";
    public static final String UPDATE_EMPLOYEE_MAPPING = "UPDATE TASKTOEMPLOYEEMAPING SET TASKID=%d, TASKTYPE='%s' EMPLOYEEID=%d AND EMPLOYEETYPE='%s'";
    public static final String UPDATE_TASK_MAPPING = "UPDATE TASKTOEMPLOYEEMAPING SET EMPLOYEEID=%d, EMPLOYEETYPE='%s' WHERE TASKID=%d AND TASKTYPE='%s'";
    public static final String SELECT_TASK_EMPLOYEES = "SELECT EMPLOYEEID, EMPLOYEETYPE FROM TASKTOEMPLOYEEMAPING WHERE TASKID=%d AND EMPLOYEETYPE='%s'";
//    public static final String DELETE_TASK_FROM_MAPPING = "SELECT * FROM TASKTOEMPLOYEEMAPING WHERE EMPLOYEEID=%d AND EMPLOYEETYPE='%s'";

    public static final String SELECT_EMPLOYEE = "SELECT ID FROM '%s' WHERE firstName='%s' AND lastName='%s' AND login='%s' AND password='%s' AND email='%s' AND token='%s' AND department='%s' AND typeOfEmployee ='%s'";
    public static final String SELECT_DEVELOPER = "SELECT ID FROM DEVELOPER WHERE firstName='%s' AND lastName='%s' AND login='%s' AND password='%s' AND email='%s' AND token='%s' AND department='%s' AND typeOfEmployee ='%s'";

    public static final String SELECT_TESTER = "SELECT ID FROM TESTER WHERE firstName='%s' AND lastName='%s' AND login='%s' AND password='%s' AND email='%s' AND token='%s' AND department='%s' AND typeOfEmployee ='%s'";
    public static final String SELECT_ALL_USERS = "Select * FROM %s";
    public static final String SELECT_ALL_Employee = "SELECT * FROM EMPLOYEE WHERE ID=%d;";
//    public static final String SELECT_ALL_TESTER = "SELECT * FROM Tester WHERE Id=%d;";
    public static final String SELECT_TASK = "SELECT ID FROM TASK WHERE  taskDescription='%s' AND money=%.0f AND scrumMaster=%d AND status='%s' AND createdDate='%s' AND deadline='%s' AND lastUpdate='%s' AND taskType='%s';";
    public static final String SELECT_DEVELOPERSTASK = "SELECT ID FROM DEVELOPERSTASK WHERE  taskDescription='%s' AND money=%.0f AND scrumMaster=%d AND status='%s' AND createdDate='%s' AND deadline='%s' AND lastUpdate='%s' AND taskType='%s' AND developerComments='%s' AND developerTaskType='%s';";
    public static final String SELECT_TESTERSTASK = "SELECT ID FROM TESTERSTASK WHERE  taskDescription='%s' AND money=%.0f AND scrumMaster=%d AND status='%s' AND createdDate='%s' AND deadline='%s' AND lastUpdate='%s' AND taskType='%s' AND BUGSTATUS='%s' AND BUGDESCRIPTION='%s';";


    //    public static final String SELECT_ALL_DEVELOPERS_TASK = "SELECT * FROM DevelopersTask WHERE Id=%d;";
//    public static final String SELECT_ALL_TESTERS_TASK = "SELECT * FROM TestersTask WHERE Id=%d;";
//    public static final String SELECT_ALL_PROJECT = "SELECT * FROM Project WHERE Id=%d;";
public static final String SELECT_PROJECT = "SELECT ID FROM PROJECT WHERE  TITLE='%s' AND TakeIntoDevelopment='%s';";




    public static final String INSERT_EMPLOYEE = "INSERT INTO EMPLOYEE(firstName,lastName,login,password,email,token,department,typeOfEmployee) VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s');";
    public static final String INSERT_DEVELOPER = "INSERT INTO DEVELOPER(firstName,lastName,login,password,email,token,department,typeOfEmployee,status,programmingLanguage) VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s','%s','%s');";
    public static final String INSERT_TESTER = "INSERT INTO TESTER(firstName,lastName,login,password,email,token,department,typeOfEmployee,status,programmingLanguage,typeOfTester) VALUES ('%s','%s','%s','%s','%s','%s', '%s', '%s','%s','%s','%s');";
    public static final String INSERT_TASK = "INSERT INTO Task(TASKDESCRIPTION,MONEY,SCRUMMASTER,STATUS,CREATEDDATE,DEADLINE,LASTUPDATE,TASKTYPE) VALUES ('%s', %.0f, %d, '%s', '%s', '%s','%s','%s');";
    public static final String INSERT_DEVELOPERS_TASK = "INSERT INTO DevelopersTask(TASKDESCRIPTION,MONEY,SCRUMMASTER,STATUS,CREATEDDATE,DEADLINE,LASTUPDATE,TASKTYPE,DEVELOPERCOMMENTS,DEVELOPERTASKTYPE) VALUES ('%s', %.0f, %d, '%s', '%s', '%s','%s','%s','%s','%s');";
    public static final String INSERT_TESTERS_TASK = "INSERT INTO TestersTask(TASKDESCRIPTION,MONEY,SCRUMMASTER,STATUS,CREATEDDATE,DEADLINE,LASTUPDATE,TASKTYPE,BUGSTATUS,BUGDESCRIPTION)  VALUES ('%s', %.0f, %d, '%s', '%s', '%s','%s','%s','%s','%s');";
    public static final String INSERT_PROJECT = "INSERT INTO Project(title,takeIntoDevelopment) VALUES ('%s', '%s');";

    public static final String UPDATE_EMPLOYEE = "UPDATE EMPLOYEE SET FIRSTNAME='%s', LASTNAME='%s', LOGIN='%s', PASSWORD='%s', email='%s', token='%s', department='%s',typeOfEmployee = '%s' WHERE ID=%d;";
    public static final String UPDATE_DEVELOPER = "UPDATE DEVELOPER SET firstName='%s', lastName='%s', login='%s', password='%s', email='%s',token='%s',department='%s',typeOfEmployee ='%s',status='%s',programmingLanguage='%s' WHERE Id=%d";
    public static final String UPDATE_TESTER = "UPDATE TESTER SET firstName='%s', lastName='%s', login='%s', password='%s', email='%s',token='%s',department='%s',typeOfEmployee ='%s',status='%s',programmingLanguage='%s',typeOfTester='%s' WHERE Id=%d";
    public static final String UPDATE_TASK = "UPDATE Task SET projectId=%d, taskDescription='%s', money=%f, scrumMaster=%d, status='%s', team='%s',createdDate='%s', deadline='%s', lastUpdate='%s', taskType='%s' WHERE TaskId=%d;";
    public static final String UPDATE_DEVELOPERS_TASK = "UPDATE DevelopersTask SET projectId=%d, taskDescription='%s', money=%f, scrumMaster=%d, status='%s', team='%s',createdDate='%s', deadline='%s', lastUpdate='%s', taskType='%s', developerComments='%s' ,developerTaskType='%s' WHERE Id=%d;";
    public static final String UPDATE_TESTERS_TASK = "UPDATE TestersTask SET projectId=%d, taskDescription='%s', money=%f, scrumMaster=%d, status='%s', team='%s',createdDate='%s', deadline='%s', lastUpdate='%s', taskType='%s',bugStatus='%s',bugDescription='%s' WHERE ProjectId=%d;";
    public static final String UPDATE_PROJECT = "UPDATE Project SET title='%s' , takeIntoDevelopment='%s' WHERE id=%d;";

    public static final String UPDATE_PrID_TASK ="UPDATE %s SET ProjectID=%d WHERE ID=%d;";



    public static final String DELETE_EMPLOYEE = "DELETE FROM Employee WHERE Id=%d;";
    public static final String DELETE_DEVELOPER = "DELETE FROM Developer WHERE Id=%d;";
    public static final String DELETE_TESTER = "DELETE FROM Tester WHERE Id=%d";
    public static final String DELETE_TASK = "DELETE FROM Task WHERE TaskId=%d;";
    public static final String DELETE_DEVELOPERS_TASK = "DELETE FROM DevelopersTask WHERE Id=%d;";
    public static final String DELETE_TESTERS_TASK = "DELETE FROM TestersTask WHERE Id=%d;";
    public static final String DELETE_PROJECT = "DELETE FROM Project WHERE ProjectId=%d;";

    public static final String DROP ="DROP TABLE %s";
    public static final String DROPTASKTOEMPLOYEE ="DROP TABLE TASKTOEMPLOYEEMAPING";

    public static final String CREATE_EMPLOYEE="create table EMPLOYEE\n" +
            "(\n" +
            "    ID BIGINT auto_increment primary key,\n" +
            "    FIRSTNAME      VARCHAR(255),\n" +
            "    LASTNAME       VARCHAR(255),\n" +
            "    LOGIN          VARCHAR(255),\n" +
            "    PASSWORD       VARCHAR(255),\n" +
            "    EMAIL          VARCHAR(255),\n" +
            "    TOKEN          VARCHAR(255),\n" +
            "    DEPARTMENT     VARCHAR(255),\n" +
            "    TYPEOFEMPLOYEE VARCHAR(255)\n" +
            ");";
    public static final String CREATE_DEVELOPER="create table DEVELOPER\n" +
            "(\n" +
            "    ID BIGINT auto_increment primary key,\n" +
            "    FIRSTNAME           VARCHAR(40),\n" +
            "    LASTNAME            VARCHAR(40),\n" +
            "    LOGIN               VARCHAR(40),\n" +
            "    PASSWORD            VARCHAR(40),\n" +
            "    EMAIL               VARCHAR(40),\n" +
            "    TOKEN               VARCHAR(40),\n" +
            "    DEPARTMENT          VARCHAR(40),\n" +
            "    TYPEOFEMPLOYEE      VARCHAR(40),\n" +
            "    STATUS              VARCHAR(40),\n" +
            "    PROGRAMMINGLANGUAGE VARCHAR(40)\n" +
            ");";
    public static final String CREATE_TESTER="create table TESTER\n" +
            "(\n" +
            "    ID BIGINT auto_increment primary key,\n" +
            "    FIRSTNAME           VARCHAR(40),\n" +
            "    LASTNAME            VARCHAR(40),\n" +
            "    LOGIN               VARCHAR(40),\n" +
            "    PASSWORD            VARCHAR(40),\n" +
            "    EMAIL               VARCHAR(40),\n" +
            "    TOKEN               VARCHAR(40),\n" +
            "    DEPARTMENT          VARCHAR(40),\n" +
            "    TYPEOFEMPLOYEE      VARCHAR(40),\n" +
            "    STATUS              VARCHAR(40),\n" +
            "    PROGRAMMINGLANGUAGE VARCHAR(40),\n" +
            "    TYPEOFTESTER        VARCHAR(40)\n" +
            ");";
    public static final String CREATE_TASK="create table TASK\n" +
            "(\n" +
            "    ID BIGINT auto_increment primary key,\n" +
            "    PROJECTID       BIGINT,\n" +
            "    TASKDESCRIPTION VARCHAR(40),\n" +
            "    MONEY           DOUBLE PRECISION,\n" +
            "    SCRUMMASTER     BIGINT,\n" +
            "    STATUS          VARCHAR(40),\n" +
            "    CREATEDDATE     VARCHAR(40),\n" +
            "    DEADLINE        VARCHAR(40),\n" +
            "    LASTUPDATE      VARCHAR(40),\n" +
            "    TASKTYPE        VARCHAR(40)\n" +
            ");";
    public static final String CREATE_DEVELOPERSTASK="create table DEVELOPERSTASK\n" +
            "(\n" +
            "    ID BIGINT auto_increment primary key,\n" +
            "    PROJECTID         BIGINT,\n" +
            "    TASKDESCRIPTION   VARCHAR(40),\n" +
            "    MONEY             DOUBLE PRECISION,\n" +
            "    SCRUMMASTER       BIGINT,\n" +
            "    STATUS            VARCHAR(40),\n" +
            "    CREATEDDATE       VARCHAR(40),\n" +
            "    DEADLINE          VARCHAR(40),\n" +
            "    LASTUPDATE        VARCHAR(40),\n" +
            "    TASKTYPE          VARCHAR(40),\n" +
            "    DEVELOPERCOMMENTS VARCHAR(40),\n" +
            "    DEVELOPERTASKTYPE VARCHAR(40)\n" +
            ");";
    public static final String CREATE_TESTERSTASK="create table TESTERSTASK\n" +
            "(\n" +
            "    ID BIGINT auto_increment primary key,\n" +
            "    PROJECTID       BIGINT,\n" +
            "    TASKDESCRIPTION VARCHAR(40),\n" +
            "    MONEY           DOUBLE PRECISION,\n" +
            "    SCRUMMASTER     BIGINT,\n" +
            "    STATUS          VARCHAR(40),\n" +
            "    CREATEDDATE     VARCHAR(40),\n" +
            "    DEADLINE        VARCHAR(40),\n" +
            "    LASTUPDATE      VARCHAR(40),\n" +
            "    TASKTYPE        VARCHAR(40),\n" +
            "    BUGSTATUS       VARCHAR(40),\n" +
            "    BUGDESCRIPTION  VARCHAR(40)\n" +
            ");";
    public static final String CREATE_PROJECT="create table PROJECT\n" +
            "(\n" +
            "    ID BIGINT auto_increment primary key,\n" +
            "    TITLE               VARCHAR(255),\n" +
            "    TAKEINTODEVELOPMENT VARCHAR(40)\n" +
            ");";
    public static final String TASKTOEMPLOYEEMAPING="create table TASKTOEMPLOYEEMAPING\n" +
            "(\n" +
            "    ID BIGINT auto_increment primary key,\n" +
            "    TASKID          BIGINT,\n" +
            "    TASKTYPE        VARCHAR(40),\n" +
            "    EMPLOYEEID      BIGINT,\n" +
            "    EMPLOYEETYPE        VARCHAR(40)\n" +
            ");";
    public static final String Employee = "employee";
    public static final String Developer = "developer";
    public static final String Tester = "tester";
    public static final String Task = "task";
    public static final String DevelopersTask = "developersTask";
    public static final String TestersTask = "testersTask";

    public static final String logInfo = "method is %s";
    public static final String SPLIT = "\n";

}
