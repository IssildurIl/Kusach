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
    @Override
    protected Object convert(String s) throws CsvDataTypeMismatchException, CsvConstraintViolationException {
        String indexString = s.substring(1, s.length() - 1);
        //List<Employee> indexEmployeeList = new ArrayList<>();
        Employee employee = new Employee();
            if (!indexString.isEmpty()) {
                employee.setId(Long.parseLong(indexString));
                //indexEmployeeList.add(employee);
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
        log.debug(builder.toString());
        return builder.toString();
    }
}
