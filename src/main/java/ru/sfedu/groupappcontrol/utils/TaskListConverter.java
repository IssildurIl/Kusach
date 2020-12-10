package ru.sfedu.groupappcontrol.utils;

import com.opencsv.bean.AbstractBeanField;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.groupappcontrol.api.DataProvider;
import ru.sfedu.groupappcontrol.api.DataProviderCsv;
import ru.sfedu.groupappcontrol.models.Task;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TaskListConverter extends AbstractBeanField<Task, Integer> {
    private static final Logger log = LogManager.getLogger(TaskListConverter.class);
    private static final DataProvider DATA_PROVIDER = new DataProviderCsv();


    @Override
    protected Object convert(String s) throws CsvDataTypeMismatchException, CsvConstraintViolationException {

        String indexString = s.substring(1, s.length() - 1);
        String[] unparsedList = indexString.split(",");
        List<String> indexTaskList = new ArrayList<>(Arrays.asList(unparsedList));
        List<Task> taskList = new ArrayList<>();
        indexTaskList.forEach(indexStr -> taskList.add(DATA_PROVIDER.getTasks(Long.parseLong(indexStr)).getData()));
        return taskList;
    }

    public String convertToWrite(Object value) {
        List<Task> taskList = (List<Task>) value;
        StringBuilder builder = new StringBuilder("[");
        if (taskList.size() > 0) {
            taskList.forEach(task -> builder
                    .append(task.getId())
                    .append(","));
            builder.delete(builder.length() - 1, builder.length());
        }
        builder.append("]");
        log.debug(builder.toString());
        return builder.toString();
    }
}
