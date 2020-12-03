package ru.sfedu.groupappcontrol.utils;

import com.opencsv.bean.AbstractBeanField;
import com.opencsv.exceptions.CsvConstraintViolationException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.groupappcontrol.models.Employee;

import java.util.ArrayList;
import java.util.List;

public class EmployeeConverter extends AbstractBeanField<Employee, Integer> {
    private static final Logger log = LogManager.getLogger(EmployeeConverter.class);

    protected Object convert(String s) throws CsvDataTypeMismatchException, CsvConstraintViolationException {
        Employee employee = new Employee();
        employee.setId(Long.parseLong(s));
        return employee;
    }

    public String convertToWrite(Object value){
        String res="";
        Employee employee = (Employee) value;
        res += employee.getId();
        return res;
    }
}
