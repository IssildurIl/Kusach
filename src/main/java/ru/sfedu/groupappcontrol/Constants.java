package ru.sfedu.groupappcontrol;


public class Constants {
    public static final String CONFIG_PATH = "config.path";
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
    public static final String DEVELOPER_TYPE_OF_DEVELOPER = "status";
    public static final String DEVELOPER_TYPE_OF_PROGRAMMING_LANGUAGE = "programmingLanguage";
    public static final String TESTER_ID = "Id";
    public static final String TESTER_TYPE_OF_DEVELOPER = "status";
    public static final String TESTER_TYPE_OF_PROGRAMMING_LANGUAGE = "programmingLanguage";
    public static final String TESTER_TYPE_OF_TESTER = "TYPEOFTESTER";
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
    public static final String TESTERSTASK_BUGSTATUS = "bugStatus";
    public static final String TESTERSTASK_DESCRIPTION = "bugDescription";
    public static final String PROJECT_ID="Id";
    public static final String PROJECT_TITLE = "title";
    public static final String PROJECT_TAKE_INTO_DEVELOPMENT= "takeIntoDevelopment";
    public static final String SELECT_ALL = "SELECT * FROM %s WHERE Id=%d";
    public static final String SELECT_FOR_ALL = "SELECT * FROM %s";
    public static final String INSERT_TO_MAPPING = "INSERT INTO TASKTOEMPLOYEEMAPING(TASKID,TASKTYPE,EMPLOYEEID,EMPLOYEETYPE) VALUES (%d,'%s',%d,'%s');";
    public static final String SELECT_TASK_EMPLOYEES = "SELECT EMPLOYEEID, EMPLOYEETYPE FROM TASKTOEMPLOYEEMAPING WHERE TASKID=%d AND EMPLOYEETYPE='%s'";
    public static final String SELECT_TASK_EMPLOYEE_MAPPING = "SELECT * FROM TASK WHERE ID in (Select TaskID from TASKTOEMPLOYEEMAPING WHERE EMPLOYEEID=%d AND TASKID=%d)";
    public static final String SELECT_EMPLOYEE = "SELECT ID FROM EMPLOYEE WHERE firstName='%s' AND lastName='%s' AND login='%s' AND password='%s' AND email='%s' AND token='%s' AND department='%s' AND typeOfEmployee ='%s'";
    public static final String SELECT_DEVELOPER = "SELECT ID FROM DEVELOPER WHERE firstName='%s' AND lastName='%s' AND login='%s' AND password='%s' AND email='%s' AND token='%s' AND department='%s' AND typeOfEmployee ='%s'";
    public static final String SELECT_TESTER = "SELECT ID FROM TESTER WHERE firstName='%s' AND lastName='%s' AND login='%s' AND password='%s' AND email='%s' AND token='%s' AND department='%s' AND typeOfEmployee ='%s'";
    public static final String SELECT_ALL_USERS = "Select * FROM %s";
    public static final String SELECT_TASK = "SELECT ID FROM TASK WHERE  taskDescription='%s' AND money=%.0f AND scrumMaster=%d AND status='%s' AND createdDate='%s' AND deadline='%s' AND lastUpdate='%s' AND taskType='%s';";
    public static final String SELECT_DEVELOPERSTASK = "SELECT ID FROM DEVELOPERSTASK WHERE  taskDescription='%s' AND money=%.0f AND scrumMaster=%d AND status='%s' AND createdDate='%s' AND deadline='%s' AND lastUpdate='%s' AND taskType='%s' AND developerComments='%s' AND developerTaskType='%s';";
    public static final String SELECT_TESTERSTASK = "SELECT ID FROM TESTERSTASK WHERE  taskDescription='%s' AND money=%.0f AND scrumMaster=%d AND status='%s' AND createdDate='%s' AND deadline='%s' AND lastUpdate='%s' AND taskType='%s' AND BUGSTATUS='%s' AND BUGDESCRIPTION='%s';";
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
    public static final String UPDATE_TASK = "UPDATE Task SET taskDescription='%s', money=%.0f, scrumMaster=%d, status='%s',createdDate='%s', deadline='%s', lastUpdate='%s', taskType='%s' WHERE Id=%d;";
    public static final String UPDATE_DEVELOPERS_TASK = "UPDATE DevelopersTask SET taskDescription='%s', money=%.0f, scrumMaster=%d, status='%s', createdDate='%s', deadline='%s', lastUpdate='%s', taskType='%s', developerComments='%s' ,developerTaskType='%s' WHERE Id=%d;";
    public static final String UPDATE_TESTERS_TASK = "UPDATE TestersTask SET taskDescription='%s', money=%.0f, scrumMaster=%d, status='%s', createdDate='%s', deadline='%s', lastUpdate='%s', taskType='%s',bugStatus='%s',bugDescription='%s' WHERE Id=%d;";
    public static final String UPDATE_PROJECT = "UPDATE Project SET title='%s' , takeIntoDevelopment='%s' WHERE id=%d;";
    public static final String UPDATE_PrID_TASK ="UPDATE %s SET ProjectID=%d WHERE ID=%d;";
    public static final String UPDATE_TASK_STATUS ="UPDATE TASK SET Status='%s' WHERE ID=%d;";
    public static final String UPDATE_TASK_COMMENT = "UPDATE TASK SET TaskDescription='%s' WHERE ID=%d;";
    public static final String UPDATE_DEVELOPERSTASK_COMMENT = "UPDATE DEVELOPERSTASK SET TaskDescription='%s' WHERE ID=%d;";
    public static final String UPDATE_TESTERSTASK_COMMENT = "UPDATE TESTERSTASK SET TaskDescription='%s' WHERE ID=%d;";
    public static final String GetTaskByScrummaster = "Select * from TASK where Scrummaster=%d;";
    public static final String GetDevelopersTaskByScrummaster = "Select * from DEVELOPERSTASK where Scrummaster=%d;";
    public static final String GetTestersTaskByScrummaster = "Select * from TESTERSTASK where Scrummaster=%d;";
    public static final String GetTaskList="Select * from Task where projectId=%d;";
    public static final String GETPROJECTLISTBYSCRUMMASTERID = "Select * from Project Where ID in (Select ProjectId From TASK Where Scrummaster = %d);";
    public static final String GETPROJECTLISTBYWORKERID = "Select * from Project Where ID in (Select ID From TASK Where ID = (Select TaskID from TASKTOEMPLOYEEMAPING Where EmployeeID=%d) AND ID=%d;";
    public static final String DELETE_EMPLOYEE = "DELETE FROM Employee WHERE Id=%d;";
    public static final String DELETE_DEVELOPER = "DELETE FROM Developer WHERE Id=%d;";
    public static final String DELETE_TESTER = "DELETE FROM Tester WHERE Id=%d";
    public static final String DELETE_TASK = "DELETE FROM Task WHERE Id=%d;";
    public static final String DELETE_DEVELOPERS_TASK = "DELETE FROM DevelopersTask WHERE Id=%d;";
    public static final String DELETE_TESTERS_TASK = "DELETE FROM TestersTask WHERE Id=%d;";
    public static final String DELETE_PROJECT = "DELETE FROM Project WHERE Id=%d;";
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
            "\tID BIGINT auto_increment\n" +
            "\t\tprimary key,\n" +
            "\tTASKID BIGINT,\n" +
            "\tTASKTYPE VARCHAR(40),\n" +
            "\tEMPLOYEEID BIGINT,\n" +
            "\tEMPLOYEETYPE VARCHAR(40),\n" +
            "\tconstraint TASKTOEMPLOYEEMAPING_EMPLOYEE_ID_TYPEOFEMPLOYEE_FK\n" +
            "\t\tforeign key (EMPLOYEEID, EMPLOYEETYPE) references EMPLOYEE (ID, TYPEOFEMPLOYEE)\n" +
            "\t\t\ton update cascade on delete cascade\n" +
            ");\n" +
            "\n";
    public static final String Employee = "employee";
    public static final String Developer = "developer";
    public static final String Tester = "tester";
    public static final String Task = "task";
    public static final String DevelopersTask = "developersTask";
    public static final String TestersTask = "testersTask";
    public static final String logInfo = "method is %s";
    public static final String POINT_FOR_LOGGER = ",";
    public static final String Logger_Start="Start %s %s";
    public static final String Logger_END="End %s %s";
}
