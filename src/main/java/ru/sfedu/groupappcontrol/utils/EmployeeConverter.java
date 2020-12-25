package ru.sfedu.groupappcontrol.utils;

import com.opencsv.bean.AbstractBeanField;
import com.opencsv.exceptions.CsvConstraintViolationException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.groupappcontrol.models.Employee;
import ru.sfedu.groupappcontrol.models.enums.TypeOfEmployee;

import java.util.ArrayList;
import java.util.List;

public class EmployeeConverter extends AbstractBeanField<Employee, Integer> {
    @Override
    protected Object convert(String s) {
        String indexString = s.substring(1, s.length() - 1);
        Employee employee = new Employee();
            if (!indexString.isEmpty()) {
                employee.setId(Long.parseLong(indexString));
                employee.setTypeOfEmployee(TypeOfEmployee.EMPLOYEE);
            }
        return employee;
    }

    public String convertToWrite(Object value) {
        Employee employee = (Employee) value;
        StringBuilder builder = new StringBuilder("[");
            builder.append(employee.getId());
            builder.append(",");
            builder.delete(builder.length() - 1, builder.length());
            builder.append("]");
        //log.debug(builder.toString());
        return builder.toString();
    }
}
