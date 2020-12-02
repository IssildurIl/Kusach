package ru.sfedu.groupappcontrol.api;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.apache.commons.io.input.TailerListener;
import ru.sfedu.groupappcontrol.Result;
import ru.sfedu.groupappcontrol.models.*;
import ru.sfedu.groupappcontrol.models.constants.Constants;
import ru.sfedu.groupappcontrol.models.enums.*;
import ru.sfedu.groupappcontrol.utils.ConfigurationUtil;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static ru.sfedu.groupappcontrol.models.enums.Outcomes.Complete;
import static ru.sfedu.groupappcontrol.models.enums.Outcomes.Fail;

public class DataProviderCsv implements DataProvider {

    private final String PATH = ConfigurationUtil.getConfigurationEntry("CSV_PATH");

    private final String FILE_EXTENSION_CSV = "FILE_EXTENSION_CSV";
    private Logger log = LogManager.getLogger(DataProviderCsv.class);

    public DataProviderCsv() throws IOException {
    }

    public void createDirectories(String pathString) throws IOException {
        Path path = Paths.get(pathString);
        Files.createDirectories(path);
    }

    public String getPath(Class cl) throws IOException {
        return PATH + cl.getSimpleName().toLowerCase() + ConfigurationUtil.getConfigurationEntry(FILE_EXTENSION_CSV);
    }

    public void createFile(String path) throws IOException {
        File file = new File(path);
        if (!file.exists()) {
            Path dirPath = Paths.get(PATH);
            Files.createDirectories(dirPath);
            file.createNewFile();
        }
    }

    public <T extends BaseClass> Result<T> getByID(Class cl, long id) throws IOException {
        List<T> listRes = select(cl);
        try {
            T element = listRes.stream().filter(el -> el.getId() == id).findFirst().get();
            return new Result(Complete, Constants.IS_INSERTED, element);
        } catch (NoSuchElementException e) {
            log.error("There is no element with this id");
            return new Result(Fail, Constants.IS_FAILED, null);
        }
    }

    public <T extends BaseClass> Result<T> delete(Class<T> cl, long id) throws IOException {
        List<T> listData = select(cl);
        listData = listData.stream().filter(el -> el.getId() != id).collect(Collectors.toList());
        insert(cl, listData, false);
        return new Result(Complete);
    }

    public <T extends BaseClass> Result<T> update(Class<T> cl, long id) throws IOException {
        delete(cl, id);
        List<T> list = new ArrayList<T>();
        list.addAll(list);
        insert(cl, list, true);
        return new Result(Complete);
    }

    public <T> List<T> select(Class<T> cl) {
        try {
            String path = getPath(cl);
            FileReader file = new FileReader(path);
            CSVReader reader = new CSVReader(file);
            CsvToBean<T> csvToBean = new CsvToBeanBuilder<T>(reader)
                    .withType(cl)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();
            List<T> list = csvToBean.parse();
            reader.close();
            return (List<T>) list;
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }

    }

    public <T extends BaseClass> Result<Void> insert(Class<T> cl, List<T> list, boolean append) {
        try {
            String path = getPath(cl);
            createFile(path);
            List<T> oldList = (List<T>) this.select(cl);
            if (append) {
                if (oldList != null && oldList.size() > 0) {
                    int id = (int) list.get(0).getId();
                    if (oldList.stream().anyMatch(el -> el.getId() == id)) {
                        return new Result<>(Fail);
                    }
                    list = Stream
                            .concat(list.stream(), oldList.stream())
                            .collect(Collectors.toList());
                }
            }
            FileWriter file = new FileWriter(path);
            CSVWriter writer = new CSVWriter(file);
            StatefulBeanToCsv<T> beanToCsv = new StatefulBeanToCsvBuilder<T>(writer)
                    .withApplyQuotesToAll(false)
                    .build();
            beanToCsv.write(list);
            writer.close();
        } catch (IndexOutOfBoundsException | IOException | CsvDataTypeMismatchException | CsvRequiredFieldEmptyException ex) {
            return new Result<>(Fail, Constants.IS_EMPTY, null);
        }
        return new Result(Complete);
    }


    @Override
    public Result<Employee> changeProfileInfo(Employee editedEmployee) {
        try {
            List<Employee> listRes = select(Employee.class);
            Optional<Employee> optionalUser = search(listRes,editedEmployee.getId());
            listRes.remove(optionalUser.get());
            listRes.add(editedEmployee);
            insert(Employee.class, listRes, false);
            return new Result<>(Complete);
        } catch (IOException e) {
            e.printStackTrace();
            return new Result<>(Fail);
        }

    }

    @Override
    public Result changeTaskStatus(long id, String status) {
        try {
            List<Task> listRes = select(Task.class);
            Optional<Task> optionalTask = search(listRes,id);
            if(optionalTask.isEmpty()){
                return new Result<>(Fail);
            }
            Task editedTask = optionalTask.get();
            listRes.remove(optionalTask.get());
            editedTask.setStatus(TypeOfCompletion.valueOf(status));
            listRes.add(editedTask);
            insert(Task.class, listRes, false);
            return new Result<>(Complete);
        } catch (IOException e) {
            e.printStackTrace();
            return new Result<>(Fail);
        }
    }

    @Override
    public Result writeComment(long id, String comment) {
        try {
            List<Task> listRes = select(Task.class);
            Optional<Task> optionalTask = search(listRes,id);
            if(optionalTask.isEmpty()){
                return new Result<>(Fail);
            }
            Task editedTask = optionalTask.get();
            listRes.remove(optionalTask.get());
            editedTask.setTaskDescription(comment);
            listRes.add(editedTask);
            insert(Task.class, listRes, false);
            return new Result<>(Complete);
        } catch (IOException e) {
            e.printStackTrace();
            return new Result<>(Fail);
        }
    }

    @Override
    public Result getUserInfoList(long userId) {
            List<Employee> list = select(Employee.class);
            if(list.isEmpty()){
                return new Result(Fail);
            }
            list.removeIf(el->el.getId()!=userId);
            return new Result(Complete,String.valueOf(list));
    }

    @Override
    public Result getTaskList(long taskId) {
            List<Task> listRes = select(Task.class);
        return new Result(Complete,listRes);
    }
//тесты
    @Override
    public Result getTaskInfo(long taskId) {
        try {
            List<Task> list = select(Task.class);
            list.removeIf(l -> l.getId() != taskId);
            if (list.isEmpty()) {
                return new Result(Fail);
            } else {
                return new Result<>(Complete, String.valueOf(list));
            }
        } catch (Exception e) {
            log.error(e);
        }
        return new Result(Fail);
    }

    @Override
    public Result getTask(long userId, long taskId) {
        try {
            List<Task> listRes = select(Task.class);
            Optional<Task> optionalTask=search(listRes,taskId);
            Task findedTask = optionalTask.get();
            findedTask.getScrumMaster().getId();
            List<Employee> listEmpRes = select(Employee.class);
            Optional<Employee> optionalUser = search(listEmpRes,userId);
            Employee findedEmployee = optionalUser.get();
            if(findedTask.getScrumMaster().getId()==findedEmployee.getId()){
                return new Result(Complete,findedTask);
            }
            else {
                return new Result<>(Fail);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new Result<>(Fail);
        }
    }

    @Override
    public Result calculateTaskCost(Task task) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-mm-yyyy", Locale.ENGLISH);
            Date firstDate = sdf.parse(String.valueOf(task.getCreatedDate()));
            Date secondDate = sdf.parse(String.valueOf(task.getDeadline()));
            long diffInMillies = Math.abs(secondDate.getTime() - firstDate.getTime());
            long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
            return new Result<>(Complete,diff*task.getMoney());
        } catch (ParseException e) {
            e.printStackTrace();
            return new Result<>(Fail);
        }
    }

    @Override
    public Result getProjectStatistic(long userId) {
        try {
            //Emp
            List<Employee> listEmpRes = select(Employee.class);
            Optional<Employee> optionalUser = search(listEmpRes,userId);
            Employee findedEmployee = optionalUser.get();
            //Task
            List<Task> listRes = select(Task.class);
            List<Task> findedTaskList = listRes.stream()
                    .filter(el -> el.getScrumMaster().getId() == findedEmployee.getId())
                    .collect(Collectors.toList());
            List<Task> findedTask = findedTaskList;
            //Project
            List<Project> listProject = select(Project.class);
            List<Project> optionalProject = listProject.stream()
                    .filter(project -> {
                        boolean isContains=false;
                        for(Task task:findedTaskList){
                            if(project.getTask().contains(task)){
                             isContains=true;
                             break;
                            }
                        }
                        return isContains;
                    })
                    .collect(Collectors.toList());
            return new Result<>(Complete,optionalProject);
        } catch (IOException e) {
            e.printStackTrace();
            return new Result<>(Fail);
        }
        
    }

    @Override
    public Result getProject(long userId, long projectId) {
        return null;
    }

    @Override
    public Result calculateProjectCost(Project project) {
        return null;
    }

    @Override
    public Result calculateProjectTime(Project project) {
        return null;
    }

    @Override
    public Result createTask(long userId, String taskDescription, Double money, String deadline) {
        return null;
    }

    @Override
    public Result createTask(long userId, String taskDescription, Double money, String deadline, DeveloperTaskType taskType) {
        return null;
    }

    @Override
    public Result deleteTask(Task task) {
        return null;
    }

    @Override
    public Result getTask(Employee employee, long taskId) {
        return null;
    }

    @Override
    public Result getTaskById(Employee employee, long taskId) {
        return null;
    }

    @Override
    public Result getTaskListById(Employee employee) {
        return null;
    }

    @Override
    public Result deleteProject(Project project) {
        return null;
    }

    @Override
    public Result updateProject(Project project) {
        return null;
    }

    @Override
    public Result createProject(String title, String takeIntoDevelopment) {
        return null;
    }

    @Override
    public Result getProject(Employee employee) {
        return null;
    }

    @Override
    public Result getProjectById(Employee employee, long projectId) {
        return null;
    }

    @Override
    public Result getProjecListById(Employee employee) {
        return null;
    }

    @Override
    public Result correctEmployeeParameters(Employee editedEmployee) {
        return null;
    }

    @Override
    public Result addEmployeeToTask(Task task, Employee employee) {
        return null;
    }

    @Override
    public Result deleteEmployeeFromTask(Task task, Employee employee) {
        return null;
    }

    @Override
    public Result createEmployee(String firstName, String lastName, String login, String password, String email,String token, String department, TypeOfEmployee typeOfEmployee) {
        Employee employee = new Employee();
        employee.setFirstName(firstName);
        employee.setLastName(lastName);
        employee.setLogin(login);
        employee.setPassword(password);
        employee.setEmail(email);
        employee.setToken(token);
        employee.setDepartment(department);
        employee.setTypeOfEmployee(typeOfEmployee);
        return new Result<>(Complete,employee);
    }

    @Override
    public Result createEmployee(String firstName, String lastName, String login, String password, String email,String token, String department, TypeOfEmployee typeOfEmployee, TypeOfDevelopers status, ProgrammingLanguage language) {
     return null;
    }

    @Override
    public Result createEmployee(String firstName, String lastName, String login, String password, String email,String token, String department, TypeOfEmployee typeOfEmployee, TypeOfDevelopers status, ProgrammingLanguage language,TypeOfTester typeOfTester) {
        Employee testerEmployee = new Employee();
        return null;
    }


    public <T extends BaseClass> Optional<T> search(List<T> listRes,long id) throws IOException {
        Optional<T> optional = listRes.stream()
                .filter(el -> el.getId() == id)
                .findFirst();
        return optional;
    }

}