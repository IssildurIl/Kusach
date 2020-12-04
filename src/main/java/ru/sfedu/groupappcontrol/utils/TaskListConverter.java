package ru.sfedu.groupappcontrol.utils;

import com.opencsv.bean.AbstractBeanField;
import com.opencsv.exceptions.CsvConstraintViolationException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.groupappcontrol.models.Employee;
import ru.sfedu.groupappcontrol.models.Task;

import java.util.ArrayList;
import java.util.List;

public class TaskListConverter extends AbstractBeanField<Task, Integer> {
    private static final Logger log = LogManager.getLogger(TaskListConverter.class);

    @Override
    protected Object convert(String s) throws CsvDataTypeMismatchException, CsvConstraintViolationException {
        String indexString;
        indexString = s.substring(1, s.length() - 1);
        String[] unparsedIndexList = indexString.split(",");
        List<Task> indexTaskList = new ArrayList<>();
        for (String strIndex : unparsedIndexList) {
            if (!strIndex.isEmpty()) {
                Task task = new Task();
                task.setId(Long.parseLong(strIndex));
                indexTaskList.add(task);
            }
        }
        return indexTaskList;
    }

    public String convertToWrite(Object value) {
        List<Task> taskList = (List<Task>) value;
        StringBuilder builder = new StringBuilder("[");
        if (taskList.size() > 0) {
            for (Task task : taskList) {
                builder.append(task.getId());
                builder.append(",");
            }

            builder.delete(builder.length() - 1, builder.length());
        }
        builder.append("]");
        log.debug(builder.toString());
        return builder.toString();
    }
}
