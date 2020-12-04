package ru.sfedu.groupappcontrol.utils;

import com.opencsv.bean.AbstractBeanField;
import com.opencsv.bean.AbstractCsvConverter;
import com.opencsv.exceptions.CsvConstraintViolationException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.groupappcontrol.models.Employee;
import ru.sfedu.groupappcontrol.models.Task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeListConverter extends AbstractBeanField<Employee, Integer> {
    private static final Logger log = LogManager.getLogger(EmployeeListConverter.class);

    @Override
    protected Object convert(String s) throws CsvDataTypeMismatchException, CsvConstraintViolationException {
        String indexString;
        indexString = s.substring(1, s.length() - 1);
        String[] unparsedIndexList = indexString.split(",");
        List<Employee> indexEmployeeList = new ArrayList<>();
        for (String strIndex : unparsedIndexList) {
            if (!strIndex.isEmpty()) {
                Employee employee = new Employee();
                employee.setId(Long.parseLong(strIndex));
                indexEmployeeList.add(employee);
            }
        }
        return indexEmployeeList;
    }

    public String convertToWrite(Object value) {
        List<Employee> employeeList = (List<Employee>) value;
        StringBuilder builder = new StringBuilder("[");
        if (employeeList.size() > 0) {
            for (Employee employee : employeeList) {
                builder.append(employee.getId());
                builder.append(",");
            }

            builder.delete(builder.length() - 1, builder.length());
        }
        builder.append("]");
        //log.debug(builder.toString());
        return builder.toString();
    }
}
