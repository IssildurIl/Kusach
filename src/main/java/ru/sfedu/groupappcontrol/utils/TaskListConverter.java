package ru.sfedu.groupappcontrol.utils;

import com.opencsv.bean.AbstractBeanField;
import com.opencsv.exceptions.CsvConstraintViolationException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.groupappcontrol.models.Task;

import java.util.ArrayList;
import java.util.List;

public class TaskListConverter extends AbstractBeanField<Task, Integer> {
    private static final Logger log = LogManager.getLogger(TaskListConverter.class);

    @Override
    protected Object convert(String s) throws CsvDataTypeMismatchException, CsvConstraintViolationException {

        String indexString = s.substring(1, s.length() - 1);
        String[] unparsedList = indexString.split(",");
        ArrayList<String> elements=new ArrayList();
        for (String stringToList:unparsedList){
            elements.add(stringToList);
        }
        List<Task> indexTaskList = new ArrayList<>();
        for(int i=0;i<elements.size();i+=3){
            Task task = new Task();
            task.setId(Long.parseLong(elements.get(i)));
            task.setCreatedDate(elements.get(i+1));
            task.setDeadline(elements.get(i+2));
            indexTaskList.add(task);
            log.debug(task);
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
                builder.append(task.getCreatedDate());
                builder.append(",");
                builder.append(task.getDeadline());
                builder.append(",");
            }

            builder.delete(builder.length() - 1, builder.length());
        }
        builder.append("]");
        log.debug(builder.toString());
        return builder.toString();
    }
}
