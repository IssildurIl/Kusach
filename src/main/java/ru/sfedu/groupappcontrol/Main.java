package ru.sfedu.groupappcontrol;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.groupappcontrol.api.DataProvider;
import ru.sfedu.groupappcontrol.api.DataProviderCsv;
import ru.sfedu.groupappcontrol.api.DataProviderJdbc;
import ru.sfedu.groupappcontrol.api.DataProviderXML;
import ru.sfedu.groupappcontrol.models.*;
import ru.sfedu.groupappcontrol.models.enums.*;
import ru.sfedu.groupappcontrol.utils.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Main.
 */
public class Main {
    public static final Logger log = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
//        System.out.println(System.getProperty(Constants.CONFIG_PATH));
//        System.setProperty(Constants.CONFIG_PATH,"D:\\projects\\groupappcontrol\\src\\main\\resources\\environment.properties");
        try {
            ConfigurationUtil.getConfigurationEntry(Constants.CONFIG_PATH);
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < args.length; i++) {
            System.out.println(args[i]);
        }

        DataProvider dataProvider;
        if (args[0].equals("DataProviderCsv")) {
            dataProvider = new DataProviderCsv();
            CsvGenerator.addRecord();
        } else if (args[0].equals("DataProviderXml")) {
            dataProvider = new DataProviderXML();
            XmlGenerator.addRecord();
        } else if (args[0].equals("DataProviderJdbc")) {
            dataProvider = new DataProviderJdbc();
            dataProvider.deleteAllRecord();
            ((DataProviderJdbc)dataProvider).createTables();
            JdbcGenerator.addRecord();
        } else dataProvider = null;

        System.out.println(getAnswer(dataProvider, args));
    }

    public static String getAnswer(DataProvider dataProvider, String[] args) {
        switch (CliCommand.valueOf(args[1].toUpperCase())) {
            case GETTASKBYID:
                return String.valueOf(dataProvider.getTaskById(Long.parseLong(args[2])).getData());
            case GETDEVELOPERSTASKBYID:
                return String.valueOf(dataProvider.getDevelopersTaskById(Long.parseLong(args[2])).getData());
            case GETTESTERSTASKBYID:
                return String.valueOf(dataProvider.getTestersTaskById(Long.parseLong(args[2])).getData());
            case GETEMPLOYEEBYID:
                return String.valueOf(dataProvider.getEmployeeById(Long.parseLong(args[2])).getData());
            case GETDEVELOPERBYID:
                return String.valueOf(dataProvider.getDeveloperById(Long.parseLong(args[2])).getData());
            case GETTESTERBYID:
                return String.valueOf(dataProvider.getTesterById(Long.parseLong(args[2])).getData());
            case GETPROJECTBYID:
                return String.valueOf(dataProvider.getProjectByID(Long.parseLong(args[2])).getData());
            case DELETETASK:
                return String.valueOf(dataProvider.deleteTask(Long.parseLong(args[2])).getData());
            case DELETEDEVELOPERSTASK:
                return String.valueOf(dataProvider.deleteDevelopersTask(Long.parseLong(args[2])).getData());
            case DELETETESTERSTASK:
                return String.valueOf(dataProvider.deleteTestersTask(Long.parseLong(args[2])).getData());
            case DELETEEMPLOYEE:
                return String.valueOf(dataProvider.deleteEmployee(Long.parseLong(args[2])).getData());
            case DELETEDEVELOPER:
                return String.valueOf(dataProvider.deleteDeveloper(Long.parseLong(args[2])).getData());
            case DELETETESTER:
                return String.valueOf(dataProvider.deleteTester(Long.parseLong(args[2])).getData());
            case DELETEPROJECT:
                return String.valueOf(dataProvider.deleteProject(Long.parseLong(args[2])).getData());
            case CREATETASK:
                dataProvider.insertTask(dataProvider.createTask(Long.parseLong(args[2]), args[3],
                        Double.parseDouble(args[4]), dataProvider.getEmployeeById(Long.parseLong(args[5])).getData(),
                        TypeOfCompletion.valueOf(args[5]), new ArrayList<>(), args[6], args[7], args[8], TaskTypes.valueOf(args[8])).getData());
                return String.valueOf(dataProvider.createTask(Long.parseLong(args[2]), args[3],
                        Double.parseDouble(args[4]), dataProvider.getEmployeeById(Long.parseLong(args[5])).getData(),
                        TypeOfCompletion.valueOf(args[5]), new ArrayList<>(), args[6], args[7], args[8], TaskTypes.valueOf(args[8])).getData());
            case GETTASKS:
                return String.valueOf(dataProvider.getTasks(Long.parseLong(args[2])).getData());
            case GETALLTASK:
                return String.valueOf(dataProvider.getAllTask());
            case GETTASKSBYUSER:
                return String.valueOf(dataProvider.getTasksByUser(Long.parseLong(args[2]), Long.parseLong(args[3])).getData());
            case CHANGETASKSTATUS:
                return String.valueOf(dataProvider.changeTaskStatus(Long.parseLong(args[2]), args[3]).getData());
            case CALCULATETASKCOST:
                return String.valueOf(dataProvider.calculateTaskCost(Long.parseLong(args[2])).getData());
            case WRITEBASETASKCOMMENT:
                return String.valueOf(dataProvider.writeBaseTaskComment(Long.parseLong(args[2]), args[3]).getData());
            case WRITEDEVELOPERSTASKCOMMENT:
                return String.valueOf(dataProvider.writeDevelopersTaskComment(Long.parseLong(args[2]), args[3]).getData());
            case WRITETESTERSTASKCOMMENT:
                return String.valueOf(dataProvider.writeTestersTaskComment(Long.parseLong(args[2]), args[3]).getData());
            case GETTASKLISTBYSCRUMMASTER:
                return String.valueOf(dataProvider.getTaskListByScrumMaster(Long.parseLong(args[2])).getData());
            case GETDEVELOPERSTASKLISTBYSCRUMMASTER:
                return String.valueOf(dataProvider.getDevelopersTaskListByScrumMaster(Long.parseLong(args[2])).getData());
            case GETTESTERSTASKLISTBYSCRUMMASTER:
                return String.valueOf(dataProvider.getTestersTaskListByScrumMaster(Long.parseLong(args[2])).getData());
            case GETPROJECTBYSCRUMMASTERID:
                return String.valueOf(dataProvider.getProjectByScrumMasterId(Long.parseLong(args[2]), Long.parseLong(args[3])).getData());
            case CALCULATEPROJECTCOST:
                return String.valueOf(dataProvider.calculateProjectCost(Long.parseLong(args[2])).getData());
            case CALCULATEPROJECTTIME:
                return String.valueOf(dataProvider.calculateProjectTime(Long.parseLong(args[2])).getData());
            case CREATEEMPLOYEE:
                dataProvider.insertEmployee(dataProvider.createEmployee(Long.parseLong(args[2]), args[3],
                        args[4], args[5], args[6], args[7], args[8], args[9], TypeOfEmployee.valueOf(args[10])).getData());
                return String.valueOf(dataProvider.insertEmployee(dataProvider.createEmployee(Long.parseLong(args[2]), args[3],
                        args[4], args[5], args[6], args[7], args[8], args[9], TypeOfEmployee.valueOf(args[10])).getData()));
            case GETALLEMPLOYEE:
                return String.valueOf(dataProvider.getAllEmployee());
            case GETSCRUMMASTERTASKLIST:
                return String.valueOf(dataProvider.getScrumMasterTaskList(Long.parseLong(args[2]),
                        TaskTypes.valueOf(args[3])).getData());
            case GETPROJECTLISTBYSCRUMMASTERID:
                return String.valueOf(dataProvider.getProjectListByScrummasterId(Long.parseLong(args[2])).getData());
            default:
                System.out.println("finish");
        }
        return "";
    }


}
