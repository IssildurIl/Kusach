package ru.sfedu.groupappcontrol.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.groupappcontrol.Result;
import ru.sfedu.groupappcontrol.models.Employee;
import ru.sfedu.groupappcontrol.utils.ConfigurationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;

import static ru.sfedu.groupappcontrol.models.constants.Constants.*;
import static ru.sfedu.groupappcontrol.utils.ConfigurationUtil.getConfigurationEntry;

public class DataProviderJdbc implements DataProvider {
    private final String PATH = getConfigurationEntry("JDBC_PATH");
    private final String FILE_EXTENSION_JDBC = "FILE_EXTENSION_JDBC";
//
//    private final String DB_DRIVER = ConfigurationUtil.getConfigurationEntry("db_driver");
//    private final String DB_USER = ConfigurationUtil.getConfigurationEntry("db_user");
//    private final String DB_PASS = ConfigurationUtil.getConfigurationEntry("db_pass");
//    private final String DB_URL = ConfigurationUtil.getConfigurationEntry("db_url");
    private final String DB_INSERT ="INSERT INTO %s VALUES(%d,'%s','%s','%s','%s','%s','%s','%s');";
    private final String DB_SELECT ="SELECT * FROM %s WHERE id=%d";
    private Connection connection;
    private Logger log = LogManager.getLogger(DataProviderJdbc.class);

    public DataProviderJdbc() throws IOException {
    }


    public void insertEmployee(Employee employee) throws SQLException, IOException, ClassNotFoundException {
        System.out.println(String.format(DB_INSERT,employee.getClass().getSimpleName().toLowerCase(),employee.getId(),employee.getFirstName(), employee.getLastName(), employee.getLogin(), employee.getPassword(),employee.getEmail(),employee.getToken(),employee.getDepartment(),employee.getTypeOfEmployee()));
        this.execute(String.format(DB_INSERT,employee.getClass().getSimpleName().toLowerCase(),employee.getId(),employee.getFirstName(),employee.getLastName(),employee.getLogin(),employee.getPassword(),employee.getEmail(),employee.getToken(),employee.getDepartment(),employee.getTypeOfEmployee()));
    }
    public Employee getUserById(long id) throws SQLException, IOException, ClassNotFoundException {
        ResultSet set = getResultSet(String.format(DB_SELECT,Employee.class.getSimpleName().toLowerCase(),id));
        if(set!=null && set.next()){
            Employee employee = new Employee();
            employee.setId(set.getLong(ID));
            employee.setFirstName(set.getString(USER_NAME));
            employee.setLastName(set.getString(USER_LAST_NAME));
            employee.setLogin(set.getString(USER_LOGIN));
            employee.setPassword(set.getString(USER_PASSWORD));
            employee.setEmail(set.getString(USER_EMAIL));
            employee.setToken(set.getString(USER_TOKEN));
            employee.setDepartment(set.getString(USER_DEPARTMENT));
            employee.setTypeOfEmployee(set.getString(USER_TYPE_OF_EMPLOYEE));
            return employee;
        }else
            {
                log.error("ERROR");
            }
        return null;
    }
    private Connection getConnection() throws ClassNotFoundException, SQLException, IOException {
        Class.forName(getConfigurationEntry("db_driver"));
        connection = DriverManager.getConnection(
                getConfigurationEntry("db_url"),
                getConfigurationEntry("db_user"),
                getConfigurationEntry("db_pass")
        );
        return connection;
    }

    public void execute(String sql) throws SQLException, IOException, ClassNotFoundException {
        PreparedStatement statement = getConnection().prepareStatement(sql);
        statement.executeUpdate();
        statement.close();
    }

    public ResultSet select (String sql) {
        try {
            PreparedStatement statement = getConnection().prepareStatement(sql);
            getConnection().close();
            return statement.executeQuery();
        } catch (SQLException | ClassNotFoundException | IOException e) {
            log.info(e);
        }
        return null;
    }
    private ResultSet getResultSet(String sql) throws SQLException, IOException, ClassNotFoundException {
        PreparedStatement statement = getConnection().prepareStatement(sql);
        ResultSet set = statement.executeQuery();
        statement.close();
        return set;
    }
}
