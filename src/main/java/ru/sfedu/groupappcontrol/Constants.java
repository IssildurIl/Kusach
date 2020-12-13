package ru.sfedu.groupappcontrol;

import ru.sfedu.groupappcontrol.utils.ConfigurationUtil;

public class Constants {
    public static final String TEST_NAME="test.name";
    public static final String DATE_FORMAT = "dd-mm-yyyy";
    public static final String FILE_EXTENSION_CSV = "FILE_EXTENSION_CSV";
    public static final String CSV_PATH ="CSV_PATH";
    public static final String BaseComment = "BASE Comment";
    public static final String FILE_EXTENSION_XML = "FILE_EXTENSION_CSV";
    public static final String XML_PATH ="CSV_PATH";

    public static final String SELECT_ALL_EMPLOYEE = "SELECT * FROM Employee WHERE Id=%d";
    public static final String SELECT_ALL_DEVELOPER = "SELECT * FROM Developer WHERE Id=%d;";
    public static final String SELECT_ALL_TESTER = "SELECT * FROM Tester WHERE Id=%d;";
    public static final String SELECT_ALL_TASK = "SELECT * FROM Task WHERE Id=%d;";
    public static final String SELECT_ALL_DEVELOPERS_TASK = "SELECT * FROM DevelopersTask WHERE Id=%d;";
    public static final String SELECT_ALL_TESTERS_TASK = "SELECT * FROM TestersTask WHERE Id=%d;";
    public static final String SELECT_ALL_PROJECT = "SELECT * FROM Project WHERE Id=%d;";

    public static final String INSERT_EMPLOYEE = "INSERT INTO Employee VALUES (%d, '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s');";
    public static final String INSERT_DEVELOPER = "INSERT INTO Developer VALUES (%d, '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s','%s', '%s');";
    public static final String INSERT_TESTER = "INSERT INTO Tester VALUES (%d, '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s','%s', '%s','%s');";
    public static final String INSERT_TASK = "INSERT INTO Task VALUES (%d, '%s', %f, '%s', '%s', '%s', '%s', '%s', '%s','%s');";
    public static final String INSERT_DEVELOPERS_TASK = "INSERT INTO DevelopersTask VALUES (%d, '%s', %f, '%s', '%s', '%s', '%s', '%s', '%s','%s','%s','%s');";
    public static final String INSERT_TESTERS_TASK = "INSERT INTO TestersTask VALUES (%d, '%s', %f, '%s', '%s', '%s', '%s', '%s', '%s','%s','%s','%s');";
    public static final String INSERT_PROJECT = "INSERT INTO Project VALUES (%d, '%s', '%s', '%s');";

    public static final String UPDATE_EMPLOYEE = "UPDATE Employee SET firstName='%s', lastName='%s', login='%s', password='%s', email='%s',token='%s',department='%s',typeOfEmployee ='%s' WHERE Id=%d";
    public static final String UPDATE_DEVELOPER = "UPDATE Developer SET firstName='%s', lastName='%s', login='%s', password='%s', email='%s',token='%s',department='%s',typeOfEmployee ='%s',status='%s',programmingLanguage='%s' WHERE Id=%d";
    public static final String UPDATE_TESTER = "UPDATE Tester SET firstName='%s', lastName='%s', login='%s', password='%s', email='%s',token='%s',department='%s',typeOfEmployee ='%s',status='%s',programmingLanguage='%s',typeOfTester='%s' WHERE Id=%d";
    public static final String UPDATE_TASK = "UPDATE Task SET taskDescription='%s', money=%f, scrumMaster=%d, status='%s', team='%s',createdDate='%s', deadline='%s', lastUpdate='%s', taskType='%s' WHERE id=%d;";
    public static final String UPDATE_DEVELOPERS_TASK = "UPDATE DevelopersTask SET  taskDescription='%s', money=%f, scrumMaster=%d, status='%s', team='%s',createdDate='%s', deadline='%s', lastUpdate='%s', taskType='%s', developerComments='%s' ,developerTaskType='%s' WHERE id=%d;";
    public static final String UPDATE_TESTERS_TASK = "UPDATE TestersTask SET taskDescription='%s', money=%f, scrumMaster=%d, status='%s', team='%s',createdDate='%s', deadline='%s', lastUpdate='%s', taskType='%s',bugStatus='%s',bugDescription='%s' WHERE id=%d;";
    public static final String UPDATE_PROJECT = "UPDATE Project SET title='%s' , takeIntoDevelopment='%s', task='%s' WHERE id=%d;";

    public static final String DELETE_EMPLOYEE = "DELETE FROM Employee WHERE Id=%d;";
    public static final String DELETE_DEVELOPER = "DELETE FROM Developer WHERE Id=%d;";
    public static final String DELETE_TESTER = "DELETE FROM Tester WHERE Id=%d";
    public static final String DELETE_TASK = "DELETE FROM Task WHERE Id=%d;";
    public static final String DELETE_DEVELOPERS_TASK = "DELETE FROM DevelopersTask WHERE Id=%d;";
    public static final String DELETE_TESTERS_TASK = "DELETE FROM TestersTask WHERE Id=%d;";
    public static final String DELETE_PROJECT = "DELETE FROM Project WHERE Id=%d;";

    public static final String SPLIT = "\\W+";
    public static final String GET_ID = "\\d+";

}
