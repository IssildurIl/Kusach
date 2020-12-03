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

    public <T extends BaseClass> Result<T> update(Class<T> cl, T updElement) throws IOException {
        delete(cl, updElement.getId());
        List<T> list = new ArrayList<T>();
        list.addAll(list);
        list.add(updElement);
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
            log.error(e);
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
            return new Result(Complete);
        } catch (IndexOutOfBoundsException | IOException | CsvDataTypeMismatchException | CsvRequiredFieldEmptyException ex) {
            log.error(ex);
            return new Result<>(Fail, Constants.IS_EMPTY, null);
        }

    }

    public <T extends BaseClass> Optional<T> search(List<T> listRes,long id) throws IOException {
        Optional<T> optional = listRes.stream()
                .filter(el -> el.getId() == id)
                .findFirst();
        return optional;
    }

//  Create

    @Override
    public Result createTask( String taskDescription, Double money, Employee scrumMaster,TypeOfCompletion status, List<Employee> team, String createdDate,String deadline,String lastUpdate,TaskTypes taskType){
        if(taskDescription.isEmpty() || money.isNaN() || scrumMaster.toString().isEmpty()||status.toString().isEmpty()||team.isEmpty()||createdDate.isEmpty()||deadline.isEmpty()||lastUpdate.isEmpty()||taskType.toString().isEmpty()){
            return new Result(Fail);
        }
        switch (taskType){
        case BASE_TASK:
            Task baseTask = (Task) createBaseTask(taskDescription,money,scrumMaster,status,team,createdDate,deadline,lastUpdate).getData();
            return new Result<>(Complete,baseTask);
        case DEVELOPERS_TASK:
            DevelopersTask developersTask = (DevelopersTask) createDevelopersTask(taskDescription,money,scrumMaster,status,team,createdDate,deadline,lastUpdate).getData();
            return new Result<>(Complete,developersTask);
        case TESTERS_TASK:
            TestersTask testersTask = (TestersTask) createTestersTask(taskDescription,money,scrumMaster,status,team,createdDate,deadline,lastUpdate).getData();
            return new Result<>(Complete,testersTask);
        default:
            return new Result(Fail);
    }
}
    public Result createBaseTask( String taskDescription, Double money, Employee scrumMaster,TypeOfCompletion status, List<Employee> team, String createdDate,String deadline,String lastUpdate) {
        Task task = new Task();
        task.setTaskDescription(taskDescription);
        task.setMoney(money);
        task.setScrumMaster(scrumMaster);
        task.setStatus(status);
        task.setTeam(team);
        task.setCreatedDate(createdDate);
        task.setDeadline(deadline);
        task.setLastUpdate(lastUpdate);
        task.setTaskType(TaskTypes.BASE_TASK);
        return new Result(Complete,task);
    }
    public Result createDevelopersTask( String taskDescription, Double money,Employee scrumMaster,TypeOfCompletion status, List<Employee> team, String createdDate, String deadline, String lastUpdate) {
        DevelopersTask developersTask= (DevelopersTask) createBaseTask(taskDescription,money,scrumMaster,status,team,createdDate,deadline,lastUpdate).getData();
        developersTask.setTaskType(TaskTypes.DEVELOPERS_TASK);
        developersTask.setDeveloperTaskType(DeveloperTaskType.DEVELOPMENT);
        developersTask.setDeveloperComments("developerComments");
        return new Result(Complete,developersTask);
    }
    public Result createTestersTask( String taskDescription, Double money,Employee scrumMaster,TypeOfCompletion status, List<Employee> team, String createdDate, String deadline,String lastUpdate) {
        TestersTask testersTask= (TestersTask) createBaseTask(taskDescription,money,scrumMaster,status,team,createdDate,deadline,lastUpdate).getData();
        testersTask.setTaskType(TaskTypes.TESTERS_TASK);
        testersTask.setBugStatus(BugStatus.IN_WORK);
        testersTask.setBugDescription("bugDescription");
        return new Result(Complete,testersTask);
    }

    @Override
    public Result createProject(String title, String takeIntoDevelopment, List<Task> tasks) {
        Project project=new Project();
        project.setTitle(title);
        project.setTakeIntoDevelopment(takeIntoDevelopment);
        project.setTask(tasks);
        return new Result(Complete,project);
    }

    @Override
    public Result createEmployee(String firstName, String lastName, String login, String password, String email,String token, String department,TypeOfEmployee typeOfEmployee){
        if(firstName.isEmpty() || lastName.isEmpty() || login.isEmpty()||password.isEmpty()||email.isEmpty()||department.isEmpty()||typeOfEmployee.toString().isEmpty()){
            return new Result(Fail);
        }
        switch (typeOfEmployee){
            case Employee:
                Task baseEmployee = (Task) createBaseEmployee(firstName, lastName, login, password, email, token, department).getData();
                return new Result<>(Complete,baseEmployee);
            case Developer:
                DevelopersTask developersEmployee = (DevelopersTask) createDeveloperEmployee(firstName, lastName, login, password, email, token, department).getData();
                return new Result<>(Complete,developersEmployee);
            case Tester:
                TestersTask testersEmployee = (TestersTask) createTesterEmployee(firstName, lastName, login, password, email, token, department).getData();
                return new Result<>(Complete,testersEmployee);
            default:
                return new Result(Fail);
        }
    }
    public Result createBaseEmployee(String firstName, String lastName, String login, String password, String email,String token, String department) {
        Employee employee = new Employee();
        employee.setFirstName(firstName);
        employee.setLastName(lastName);
        employee.setLogin(login);
        employee.setPassword(password);
        employee.setEmail(email);
        employee.setToken(token);
        employee.setDepartment(department);
        employee.setTypeOfEmployee(TypeOfEmployee.Employee);
        return new Result<>(Complete,employee);
    }
    public Result createDeveloperEmployee(String firstName, String lastName, String login, String password, String email,String token, String department) {
        Developer developer= (Developer)createBaseEmployee(firstName,lastName,login,password,email,token,department).getData();
        developer.setTypeOfEmployee(TypeOfEmployee.Developer);
        developer.setStatus(TypeOfDevelopers.CUSTOM);
        developer.setProgrammingLanguage(ProgrammingLanguage.Custom);
        return new Result<>(Complete,developer);
    }
    public Result createTesterEmployee(String firstName, String lastName, String login, String password, String email,String token, String department) {
        Tester tester= (Tester)createBaseEmployee(firstName,lastName,login,password,email,token,department).getData();
        tester.setTypeOfEmployee(TypeOfEmployee.Tester);
        tester.setStatus(TypeOfDevelopers.CUSTOM);
        tester.setProgrammingLanguage(ProgrammingLanguage.Custom);
        tester.setTypeOfTester(TypeOfTester.Custom);
        return new Result<>(Complete,tester);
    }
//  Get
    @Override
    public Result getTaskList(long taskId) {
    List<Task> listRes = select(Task.class);
    return new Result(Complete, listRes);
}

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
            return new Result(Fail);
        }
    }

    @Override
    public Result getTask(long userId, long taskId) {
        try {
            List<Task> listRes = select(Task.class);
            Optional<Task> optionalTask = search(listRes, taskId);
            Task findedTask = optionalTask.get();
            findedTask.getScrumMaster().getId();
            List<Employee> listEmpRes = select(Employee.class);
            Optional<Employee> optionalUser = search(listEmpRes, userId);
            Employee findedEmployee = optionalUser.get();
            if (findedTask.getScrumMaster().getId() == findedEmployee.getId()) {
                return new Result(Complete, findedTask);
            } else {
                return new Result<>(Fail);
            }
        } catch (IOException e) {
            log.error(e);
            return new Result<>(Fail);
        }
    }

    @Override
    public Result getUserInfoList(long userId) {
        List<Employee> list = select(Employee.class);
        if (list.isEmpty()) {
            return new Result(Fail);
        }
        list.removeIf(el -> el.getId() != userId);
        return new Result(Complete, String.valueOf(list));
    }

    @Override
    public Result getProjectStatistic(long userId) {
        try {
            //Emp
            List<Employee> listEmpRes = select(Employee.class);
            Optional<Employee> optionalUser = search(listEmpRes, userId);
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
                        boolean isContains = false;
                        for (Task task : findedTaskList) {
                            if (project.getTask().contains(task)) {
                                isContains = true;
                                break;
                            }
                        }
                        return isContains;
                    })
                    .collect(Collectors.toList());
            return new Result<>(Complete, optionalProject);
        } catch (IOException e) {
            log.error(e);
            return new Result<>(Fail);
        }

    }

    @Override
    public Result getProject(long projectId) {
        try {
            List<Project> listPrjRes = select(Project.class);
            Optional<Project> optionalProject = search(listPrjRes, projectId);
            Project findedProject = optionalProject.get();
            return new Result<>(Complete, findedProject);
        } catch (IOException e) {
            log.error(e);
            return new Result<>(Fail);
        }
    }

    @Override
    public Result getTaskById( long taskId) {
        try {
            List<Task> listTaskRes = select(Task.class);
            Optional<Task> task= search(listTaskRes,taskId);
            return new Result<>(Complete,task);
        } catch (IOException e) {
            log.error(e);
            return new Result<>(Fail);
        }
    }

    @Override
    public Result getTaskListById(Employee employee) {
        try {
            List<Employee> listEmpRes = select(Employee.class);
            Optional<Employee> optionalUser = search(listEmpRes, employee.getId());
            Employee findedEmployee = optionalUser.get();
            List<Task> listRes = select(Task.class);
            List<Task> listTask = listRes.stream()
                    .filter(el -> el.getTeam().contains(employee.getId()))
                    .collect(Collectors.toList());
            return new Result(Complete, listTask);
        } catch (IOException e) {
            log.error(e);
            return new Result<>(Fail);
        }
    }

    @Override
    public Result getProjectById(Employee employee, long projectId) {
        List<Task> listRes = select(Task.class);
        List<Task> findedTaskList = listRes.stream()
                .filter(el -> el.getTeam().contains(employee.getId()))
                .collect(Collectors.toList());
        List<Task> findedTask = findedTaskList;
        List<Project> listProject = select(Project.class);
        List<Project> optionalProject = listProject.stream()
                .filter(project -> {
                    boolean isContains=false;
                    for(Task task:findedTaskList){
                        if(project.getTask().contains(task)&&project.getId()==projectId){
                            isContains=true;
                            break;
                        }
                    }
                    return isContains;
                })
                .collect(Collectors.toList());
        return new Result<>(Complete,optionalProject);
    }

    @Override
    public Result getProjecListById(Employee employee) {
        List<Task> listRes = select(Task.class);
        List<Task> findedTaskList = listRes.stream()
                .filter(el -> el.getTeam().contains(employee.getId()))
                .collect(Collectors.toList());
        List<Task> findedTask = findedTaskList;
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
    }

    @Override
    public Result getTask(Employee employee, long taskId) {
        //Task
        List<Task> listRes = select(Task.class);
        List<Task> findedTaskList = listRes.stream()
                .filter(el -> el.getTeam().contains(employee.getId()))
                .collect(Collectors.toList());
        List<Task> findedTask = findedTaskList;
        return new Result<>(Complete,findedTask);

    }

//  Delete
    @Override
    public Result deleteTask(Task task) {
    try {
        delete(Task.class,task.getId());
        return new Result(Complete);
    } catch (IOException e) {
        log.error(e);
        return new Result(Fail);
    }
}

    @Override
    public Result deleteProject(Project project) {
        try {
            delete(Project.class,project.getId());
            return new Result(Complete);
        } catch (IOException e) {
            log.error(e);
            return new Result(Fail);
        }
    }

    @Override
    public Result deleteEmployeeFromTask(Task task, Employee employee) {
        List<Employee> employeeList = task.getTeam();
        employeeList.remove(employee.getId());
        task.setTeam(employeeList);
        return new Result<>(Complete);
    }
//  Update
    @Override
    public Result updateProject(Project project) {
    try {
        update(Project.class,project);
        return new Result(Complete);
    } catch (IOException e) {
        log.error(e);
        return new Result(Fail);
    }
}

//  Change
    @Override
    public Result<Employee> changeProfileInfo(Employee editedEmployee) {
        try {
            List<Employee> listRes = select(Employee.class);
            Optional<Employee> optionalUser = search(listRes, editedEmployee.getId());
            listRes.remove(optionalUser.get());
            listRes.add(editedEmployee);
            insert(Employee.class, listRes, false);
            return new Result<>(Complete);
        } catch (IOException e) {
            log.error(e);
            return new Result<>(Fail);
        }

    }

    @Override
    public Result changeTaskStatus(long id, String status) {
        try {
            List<Task> listRes = select(Task.class);
            Optional<Task> optionalTask = search(listRes, id);
            if (optionalTask.isEmpty()) {
                return new Result<>(Fail);
            }
            Task editedTask = optionalTask.get();
            listRes.remove(optionalTask.get());
            editedTask.setStatus(TypeOfCompletion.valueOf(status));
            listRes.add(editedTask);
            insert(Task.class, listRes, false);
            return new Result<>(Complete);
        } catch (IOException e) {
            log.error(e);
            return new Result<>(Fail);
        }
    }

//  Calculate
    @Override
    public Result calculateTaskCost(Task task) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-mm-yyyy", Locale.ENGLISH);
            Date firstDate = sdf.parse(String.valueOf(task.getCreatedDate()));
            Date secondDate = sdf.parse(String.valueOf(task.getDeadline()));
            long diffInMillies = Math.abs(secondDate.getTime() - firstDate.getTime());
            long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
            return new Result<>(Complete, diff * task.getMoney());
        } catch (ParseException e) {
            log.error(e);
            return new Result<>(Fail);
        }
    }

    @Override
    public Result calculateProjectCost(Project project) {
        try {
            List<Task> taskList = project.getTask();
            double projectCost = 0;
            for (Task task: taskList) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd-mm-yyyy", Locale.ENGLISH);
                Date firstDate = sdf.parse(String.valueOf(task.getCreatedDate()));
                Date secondDate = sdf.parse(String.valueOf(task.getDeadline()));
                long diffInMillies = Math.abs(secondDate.getTime() - firstDate.getTime());
                long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
                projectCost=projectCost+ diff*task.getMoney();
            }
            return new Result<>(Complete,projectCost);
        } catch (ParseException e) {
            log.error(e);
            return new Result<>(Fail);
        }
    }

    @Override
    public Result calculateProjectTime(Project project) {
        try {
            List<Task> taskList = project.getTask();
            long resulttime = 0;
            for (Task task: taskList) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd-mm-yyyy", Locale.ENGLISH);
                Date firstDate = sdf.parse(String.valueOf(task.getCreatedDate()));
                Date secondDate = sdf.parse(String.valueOf(task.getDeadline()));
                long diffInMillies = Math.abs(secondDate.getTime() - firstDate.getTime());
                long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
                resulttime = resulttime+diff;
            }
            return new Result<>(Complete,resulttime);
        } catch (ParseException e) {
            log.error(e);
            return new Result<>(Fail);
        }
    }

//  Correct
    @Override
    public Result writeComment(long id, String comment) {
        try {
            List<Task> listRes = select(Task.class);
            Optional<Task> optionalTask = search(listRes, id);
            if (optionalTask.isEmpty()) {
                return new Result<>(Fail);
            }
            Task editedTask = optionalTask.get();
            listRes.remove(optionalTask.get());
            editedTask.setTaskDescription(comment);
            listRes.add(editedTask);
            insert(Task.class, listRes, false);
            return new Result<>(Complete);
        } catch (IOException e) {
            log.error(e);
            return new Result<>(Fail);
        }
    }

    @Override
    public Result correctEmployeeParameters(Employee editedEmployee) {
        try {
            update(Employee.class, editedEmployee);
            return new Result<>(Complete);
        } catch (IOException e) {
            log.error(e);
            return new Result<>(Fail);
        }
    }

    @Override
    public Result addEmployeeToTask(Task task, Employee employee) {
        List<Employee> employeeList = task.getTeam();
        employeeList.addAll(Collections.singleton(employee));
        task.setTeam(employeeList);
        return new Result<>(Complete);
    }





}