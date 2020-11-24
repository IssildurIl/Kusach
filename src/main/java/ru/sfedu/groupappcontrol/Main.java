package ru.sfedu.groupappcontrol;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.groupappcontrol.api.DataProviderCsv;
import ru.sfedu.groupappcontrol.api.DataProviderXML;
import ru.sfedu.groupappcontrol.models.Employee;
import ru.sfedu.groupappcontrol.models.enums.TypeOfEmployee;

import java.util.ArrayList;
import java.util.List;

public class Main  extends TestEmployee{
    private static Logger log = LogManager.getLogger(Main.class);
    public static void main(String[] args) throws Exception{
        log.info("Info");
        log.error("Error");
        log.debug("Debug");

        Employee employee1 = createUser(1,"Employee1","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
        Employee employee2 = createUser(2,"Employee2","Employee_sec_name","Employee_Login","admin","employee@sfedu.ru","Employee_personal_token","FullStack", TypeOfEmployee.Developer);
        List<Employee> employeeList=new ArrayList<>();
        employeeList.add(employee1);
        employeeList.add(employee2);
        DataProviderCsv dataProviderCsv = new DataProviderCsv();
        dataProviderCsv.insert(Employee.class,employeeList,false);
        dataProviderCsv.delete(Employee.class,1);
        DataProviderXML dataProviderXML = new DataProviderXML();
        dataProviderXML.insertEmployee(employeeList,false);
    }
}
