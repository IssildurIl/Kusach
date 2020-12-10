package ru.sfedu.groupappcontrol.api;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import lombok.NonNull;
import ru.sfedu.groupappcontrol.Result;
import ru.sfedu.groupappcontrol.models.*;
import ru.sfedu.groupappcontrol.Constants;
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

import static ru.sfedu.groupappcontrol.models.enums.Outcomes.*;

public class DataProviderCsv implements DataProvider {

    private String PATH;
    private static final Logger log = LogManager.getLogger(DataProviderCsv.class);
    
    public DataProviderCsv() {
        try {
            PATH = ConfigurationUtil.getConfigurationEntry(Constants.CSV_PATH);
        } catch (IOException e) {
            log.error(e);
        }
    }

    public String getPath(Class<?> cl) throws IOException {
        return PATH + cl.getSimpleName().toLowerCase() + ConfigurationUtil.getConfigurationEntry(Constants.FILE_EXTENSION_CSV);
    }

    public void createFile(String path) throws IOException {
        File file = new File(path);
        if (!file.exists()) {
            Path dirPath = Paths.get(PATH);
            Files.createDirectories(dirPath);
            if(!file.createNewFile()){
                log.error(Empty);
            }
        }
    }

    public <T extends Task> Result<T> getTaskByID(Class<T> cl, long id) {
        try{
            List<T> listRes = select(cl);
            Optional<T> optional = listRes.stream().filter(el -> el.getId() == id).findFirst();
            return new Result<>(Complete, optional.orElseThrow());
        } catch (NoSuchElementException e) {
            log.error(e);
            return new Result<>(Fail);
        }
    }
    public <T extends Employee> Result<T> getEmployeeByID(Class<T> cl, long id) {
        try{
            List<T> listRes = select(cl);
            Optional<T> optional = listRes.stream().filter(el -> el.getId() == id).findFirst();
            return new Result<>(Complete, optional.orElseThrow());
        } catch (NoSuchElementException e) {
            log.error(e);
            return new Result<>(Fail);
        }
    }

    public <T extends Task> Result<Void> insertGenericTask(Class<T> cl, List<T> list, boolean append) {
        try{
            String path = getPath(cl);
            createFile(path);
            List<T> oldList = this.select(cl);
            if (append) {
                if (oldList != null && oldList.size() > 0) {
                    long id = list.get(0).getId();
                    if (oldList.stream().anyMatch(el -> el.getId() == id)) {
                        return new Result<>(Fail);
                    }
                    list = Stream
                            .concat(list.stream(), oldList.stream())
                            .collect(Collectors.toList());
                }
            }
            writer(path,list);
            return new Result(Complete);
        } catch (IndexOutOfBoundsException | IOException e) {
            log.error(e);
            return new Result<>(Fail);
        }

    }
    public <T extends Employee> Result<Void> insertGenericEmployee(Class<T> cl, List<T> list, boolean append) {
        try {
            String path = getPath(cl);
            createFile(path);
            if(list.isEmpty()){
                return new Result<>(Fail);
            }
            List<T> oldList = (List<T>) this.select(cl);
            if (append){
                if (oldList != null && oldList.size() > 0) {
                    long id = list.get(0).getId();
                    if (oldList.stream().anyMatch(el -> el.getId() == id)) {
                        return new Result<>(Fail);
                    }
                    list = Stream
                            .concat(list.stream(), oldList.stream())
                            .collect(Collectors.toList());
                }
            }
            writer(path,list);
            return new Result(Complete);
        } catch (IndexOutOfBoundsException | IOException e) {
            log.error(e);
            return new Result<>(Fail);
        }

    }
    public <T extends Project> Result<Void> insertGenericProject(Class<T> cl, List<T> list, boolean append) {
        try {
            String path = getPath(cl);
            createFile(path);
            List<T> oldList = this.select(cl);
            if (append) {
                if (oldList != null && oldList.size() > 0) {
                    long id = list.get(0).getId();
                    if (oldList.stream().anyMatch(el -> el.getId() == id)) {
                        return new Result<>(Fail);
                    }
                    list = Stream
                            .concat(list.stream(), oldList.stream())
                            .collect(Collectors.toList());
                }
            }
            writer(path,list);
            return new Result<>(Complete);
        } catch (IndexOutOfBoundsException | IOException  e) {
            log.error(e);
            return new Result<>(Fail);
        }

    }

    public <T extends Task> Result<T> deleteGenericTask(Class<T> cl, long id) throws IOException {
        List<T> listData = select(cl);
        listData = listData.stream().filter(el -> el.getId() != id).collect(Collectors.toList());
        insertGenericTask(cl, listData, false);
        return new Result<>(Complete);
    }
    public <T extends Employee> Result<T> deleteGenericEmployee(Class<T> cl, long id) throws IOException {
        List<T> listData = select(cl);
        listData = listData.stream().filter(el -> el.getId() != id).collect(Collectors.toList());
        insertGenericEmployee(cl, listData, false);
        return new Result<>(Complete);
    }
    public <T extends Project> Result<T> deleteGenericProject(Class<T> cl, long id) throws IOException {
        List<T> listData = select(cl);
        listData = listData.stream().filter(el -> el.getId() != id).collect(Collectors.toList());
        insertGenericProject(cl, listData, false);
        return new Result<>(Complete);
    }

    public <T extends Task> Result<T> updateGenericTask(Class<T> cl, T updElement) throws IOException {
        List<T> userList = select(cl);
        Optional<T> optionalUser = searchTask(userList,updElement.getId());
        if (optionalUser.isEmpty()) {
            return new Result<T>(Fail);
        }
        userList.remove(optionalUser.get());
        userList.add(updElement);
        insertGenericTask(cl, userList, false);
        return new Result<T>(Complete);
    }
    public <T extends Employee> Result<T> updateGenericEmployee(Class<T> cl, T updElement) throws IOException {
        List<T> userList = select(cl);
        Optional<T> optionalUser = searchEmployee(userList,updElement.getId());
        if (optionalUser.isEmpty()) {
            return new Result<T>(Fail);
        }
        userList.remove(optionalUser.get());
        userList.add(updElement);
        insertGenericEmployee(cl, userList, false);
        return new Result<T>(Complete);
    }
    public <T extends Project> Result<T> updateGenericProject(Class<T> cl, T updElement) throws IOException {
        List<T> userList = select(cl);
        Optional<T> optionalUser = searchProject(userList,updElement.getId());
        if (optionalUser.isEmpty()) {
            return new Result<T>(Fail);
        }
        userList.remove(optionalUser.get());
        userList.add(updElement);
        insertGenericProject(cl, userList, false);
        return new Result<T>(Complete);
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
            return list;
        } catch (IOException e) {
            log.error(e);
            return Collections.emptyList();
        }

    }

    public <T extends Task> Optional<T> searchTask(List<T> listRes,long id) throws IOException {
        return listRes.stream()
                .filter(el -> el.getId() == id)
                .findFirst();
    }
    public <T extends Employee> Optional<T> searchEmployee(List<T> listRes,long id) throws IOException {
        return listRes.stream()
                .filter(el -> el.getId() == id)
                .findFirst();
    }
    public <T extends Project> Optional<T> searchProject(List<T> listRes,long id) throws IOException {
        return listRes.stream()
                .filter(el -> el.getId() == id)
                .findFirst();
    }

    public <T> Result<T> writer(String path,List<T> list) {
        try {
            FileWriter file = new FileWriter(path);
            CSVWriter writer = new CSVWriter(file);
            StatefulBeanToCsv<T> beanToCsv = new StatefulBeanToCsvBuilder<T>(writer)
                    .withApplyQuotesToAll(false)
                    .build();
            beanToCsv.write(list);
            writer.close();
            return new Result<>(Complete);
        } catch (CsvRequiredFieldEmptyException | IOException | CsvDataTypeMismatchException e) {
            log.error(e);
            return new Result<>(Fail);
        }
    }

    public Result calculatePrice(Task task) {
        try {
            long resulttime = 0;
            SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT, Locale.ENGLISH);
            Date firstDate = sdf.parse(String.valueOf(task.getCreatedDate()));
            Date secondDate = sdf.parse(String.valueOf(task.getDeadline()));
            long diffInMillies = Math.abs(secondDate.getTime() - firstDate.getTime());
            long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
            resulttime = resulttime + diff;
            return new Result<>(Complete,resulttime);
        } catch (ParseException e) {
            log.error(e);
            return new Result<>(Fail);
        }
    }

    public <T extends Task> Result <T> getTaskInfoGeneric(Class cl,long taskId){
        try {
            List<T> list = select(cl);
            Optional task = searchTask(list,taskId);
            if(task.isEmpty()){
                return new Result<>(Fail);
            }
            T res= (T) task.get();
            if (list.isEmpty()) {
                return new Result(Fail);
            } else {
                return new Result<T>(Complete, res);
            }
        } catch (Exception e) {
            log.error(e);
            return new Result(Fail);
        }
    }

    @Override
    public Result<Task> createTask(long id, @NonNull String taskDescription,@NonNull Double money,@NonNull Employee scrumMaster,@NonNull TypeOfCompletion status,@NonNull List<Employee> team,@NonNull String createdDate,@NonNull String deadline,@NonNull String lastUpdate,@NonNull TaskTypes taskType){
        switch (taskType){
        case BASE_TASK:
            Task baseTask = createBaseTask(id,taskDescription,money,scrumMaster,status,team,createdDate,deadline,lastUpdate).getData();
            return new Result<>(Complete,baseTask);
        case DEVELOPERS_TASK:
            DevelopersTask developersTask = createDevelopersTask(id,taskDescription,money,scrumMaster,status,team,createdDate,deadline,lastUpdate).getData();
            return new Result<>(Complete,developersTask);
        case TESTERS_TASK:
            TestersTask testersTask = createTestersTask(id,taskDescription,money,scrumMaster,status,team,createdDate,deadline,lastUpdate).getData();
            return new Result<>(Complete,testersTask);
        default:
            return new Result<>(Fail);
    }
}
    public Result<Task> createBaseTask(long id,@NonNull String taskDescription,@NonNull Double money,@NonNull Employee scrumMaster,@NonNull TypeOfCompletion status,@NonNull List<Employee> team,@NonNull String createdDate,@NonNull String deadline,@NonNull String lastUpdate) {
        Task task = new Task();
        task.setId(id);
        task.setTaskDescription(taskDescription);
        task.setMoney(money);
        task.setScrumMaster(scrumMaster);
        task.setStatus(status);
        task.setTeam(team);
        task.setCreatedDate(createdDate);
        task.setDeadline(deadline);
        task.setLastUpdate(lastUpdate);
        task.setTaskType(TaskTypes.BASE_TASK);
        return new Result<Task>(Complete,task);
    }
    public Result<DevelopersTask> createDevelopersTask(long id,@NonNull String taskDescription,@NonNull Double money,@NonNull Employee scrumMaster,@NonNull TypeOfCompletion status,@NonNull List<Employee> team,@NonNull String createdDate,@NonNull String deadline,@NonNull String lastUpdate) {
        DevelopersTask developersTask= new DevelopersTask();
        developersTask.setId(id);
        developersTask.setTaskDescription(taskDescription);
        developersTask.setMoney(money);
        developersTask.setScrumMaster(scrumMaster);
        developersTask.setStatus(status);
        developersTask.setTeam(team);
        developersTask.setCreatedDate(createdDate);
        developersTask.setDeadline(deadline);
        developersTask.setLastUpdate(lastUpdate);
        developersTask.setTaskType(TaskTypes.DEVELOPERS_TASK);
        developersTask.setDeveloperTaskType(DeveloperTaskType.DEVELOPMENT);
        developersTask.setDeveloperComments(Constants.BaseComment);
        return new Result(Complete,developersTask);
    }
    public Result<TestersTask> createTestersTask(long id,@NonNull String taskDescription,@NonNull Double money,@NonNull Employee scrumMaster,@NonNull TypeOfCompletion status,@NonNull List<Employee> team,@NonNull String createdDate,@NonNull String deadline,@NonNull String lastUpdate) {
        TestersTask testersTask= new TestersTask();
        testersTask.setId(id);
        testersTask.setTaskDescription(taskDescription);
        testersTask.setMoney(money);
        testersTask.setScrumMaster(scrumMaster);
        testersTask.setStatus(status);
        testersTask.setTeam(team);
        testersTask.setCreatedDate(createdDate);
        testersTask.setDeadline(deadline);
        testersTask.setLastUpdate(lastUpdate);
        testersTask.setTaskType(TaskTypes.TESTERS_TASK);
        testersTask.setBugStatus(BugStatus.IN_WORK);
        testersTask.setBugDescription(Constants.BaseComment);
        return new Result(Complete,testersTask);
    }

    @Override
    public Result<Project> createProject(long id,@NonNull String title,@NonNull String takeIntoDevelopment,@NonNull List<Task> tasks) {
        Project project=new Project();
        project.setId(id);
        project.setTitle(title);
        project.setTakeIntoDevelopment(takeIntoDevelopment);
        project.setTask(tasks);
        return new Result<>(Complete,project);
    }

    @Override
    public Result<Employee> createEmployee(long id,@NonNull String firstName,@NonNull String lastName,@NonNull String login,@NonNull String password,@NonNull String email,@NonNull String token,@NonNull String department,@NonNull TypeOfEmployee typeOfEmployee){
        switch (typeOfEmployee){
            case Employee:
                Employee baseEmployee = (Employee) createBaseEmployee(id,firstName, lastName, login, password, email, token, department).getData();
                return new Result<>(Complete,baseEmployee);
            case Developer:
                Developer developerEmployee = (Developer) createDeveloperEmployee(id,firstName, lastName, login, password, email, token, department).getData();
                return new Result<>(Complete,developerEmployee);
            case Tester:
                Tester testerEmployee = (Tester) createTesterEmployee(id,firstName, lastName, login, password, email, token, department).getData();
                return new Result<>(Complete,testerEmployee);
            default:
                return new Result<>(Fail);
        }
    }
    public Result<Employee> createBaseEmployee(long id,@NonNull String firstName,@NonNull String lastName,@NonNull String login,@NonNull String password,@NonNull String email,@NonNull String token,@NonNull String department) {
        Employee employee = new Employee();
        employee.setId(id);
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
    public Result<Developer> createDeveloperEmployee(long id,@NonNull String firstName,@NonNull String lastName,@NonNull String login,@NonNull String password,@NonNull String email,@NonNull String token,@NonNull String department) {
        Developer developer= new Developer();
        developer.setId(id);
        developer.setFirstName(firstName);
        developer.setLastName(lastName);
        developer.setLogin(login);
        developer.setPassword(password);
        developer.setEmail(email);
        developer.setToken(token);
        developer.setDepartment(department);
        developer.setTypeOfEmployee(TypeOfEmployee.Developer);
        developer.setStatus(TypeOfDevelopers.CUSTOM);
        developer.setProgrammingLanguage(ProgrammingLanguage.Custom);
        return new Result<>(Complete,developer);
    }
    public Result<Tester> createTesterEmployee(long id,@NonNull String firstName,@NonNull String lastName,@NonNull String login,@NonNull String password,@NonNull String email,@NonNull String token,@NonNull String department) {
        Tester tester= new Tester();
        tester.setId(id);
        tester.setFirstName(firstName);
        tester.setLastName(lastName);
        tester.setLogin(login);
        tester.setPassword(password);
        tester.setEmail(email);
        tester.setToken(token);
        tester.setDepartment(department);tester.setTypeOfEmployee(TypeOfEmployee.Tester);
        tester.setStatus(TypeOfDevelopers.CUSTOM);
        tester.setProgrammingLanguage(ProgrammingLanguage.Custom);
        tester.setTypeOfTester(TypeOfTester.Custom);
        return new Result<>(Complete,tester);
    }


    public <T extends Task> Result<T> getTaskListByScrumMaster(Class<T> cl, long userId){
        List<T> listRes = select(cl);
        List<T> optionalRes = listRes.stream()
                .filter(el -> el.getScrumMaster().getId() == userId)
                .collect(Collectors.toList());
        if(optionalRes.contains(null)){
            return new Result<>(Outcomes.NotFound);
        }
        return new Result(Complete, optionalRes);
    }

    public Result<Task> getTasks(long id){
        List<Task> taskList = new ArrayList<>();
        taskList.addAll(select(Task.class));
        taskList.addAll(select(TestersTask.class));
        taskList.addAll(select(DevelopersTask.class));
        Optional<Task> optTask = taskList.stream().filter(el -> el.getId() == id).findAny();
        return optTask.map(task -> new Result<>(Outcomes.Complete, task)).orElseGet(() -> new Result<>(Outcomes.Fail));
    }
    public List<Task> getAllTask(){
        List<Task> taskList = new ArrayList<>();
        taskList.addAll(select(Task.class));
        taskList.addAll(select(TestersTask.class));
        taskList.addAll(select(DevelopersTask.class));
        return taskList;
    }
    public List<Employee> getAllEmployee(){
        List<Employee> employees = new ArrayList<>();
        employees.addAll(select(Employee.class));
        employees.addAll(select(Tester.class));
        employees.addAll(select(Developer.class));
        return employees;
    }
    @Override
    public Result getScrumMasterTaskList(long userId, TaskTypes taskTypes) {
        switch (taskTypes){
            case BASE_TASK:
                List<Task> taskList = (List<Task>) getTaskListByScrumMaster(Task.class, userId).getData();
                return new Result<>(Complete, taskList);
            case DEVELOPERS_TASK:
                List<DevelopersTask> developersTask = (List<DevelopersTask>) getTaskListByScrumMaster(DevelopersTask.class, userId).getData();
                return new Result<>(Complete, developersTask);
            case TESTERS_TASK:
                List<TestersTask> testersTasks = (List<TestersTask>) getTaskListByScrumMaster(TestersTask.class,userId);
                return new Result<>(Complete, testersTasks);
            default:
                return new Result<>(Fail);
        }
    }

    @Override
    public Result getTaskInfo(Class cl,long taskId) {
        Object task = getTaskInfoGeneric(cl,taskId).getData();
        return new Result(Complete,task);
    }

    @Override
    public <T> Result<T> deleteRecord(Class<T> cl) {
        try {
            String path = getPath(cl);
            File file = new File(path);
            file.delete();
            return new Result<>(Complete);
        } catch (IOException e) {
            log.error(e);
            return new Result<>(Fail);
        }

    }
////////////////////////////////////////////
    @Override
    public Result getTasksByUser(int userId, long taskId) {
        List<Task> taskRes= new ArrayList<>();
        List<Task> listRes = getAllTask();
            return new Result<>(Fail);
    }

    @Override
    public <T extends Employee> Result getUserInfoList(Class cl,long userId) {
        try {
            List<T> list = select(cl);
            if (list.isEmpty()) {
                return new Result(Fail);
            }
            Optional<T> optionalEmployee=searchEmployee(list,userId);
            return optionalEmployee.isEmpty() ? new Result<>(Fail) : new Result(Complete, optionalEmployee.get());
        } catch (IOException e) {
            log.error(e);
            return new Result(Fail);
        }

    }

    @Override
    public Result getProjectStatistic(long userId) {
        try {
            //Emp
            List<Employee> listEmpRes = select(Employee.class);
            Optional<Employee> optionalUser = searchEmployee(listEmpRes, userId);
            if(optionalUser.isEmpty()){
                return new Result<>(Fail);
            }
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
            Optional<Project> optionalProject = searchProject(listPrjRes, projectId);
            if(optionalProject.isEmpty()){
                return new Result<>(Fail);
            }
            Project findedProject = optionalProject.get();
            return new Result<>(Complete, findedProject);
        } catch (IOException e) {
            log.error(e);
            return new Result<>(Fail);
        }
    }

    @Override
    public <T extends Task> Result<T> getTaskById(Class cl, long taskId) {
        try {
            List<T> listTaskRes = select(cl);
            Optional<T> optionalTask= searchTask(listTaskRes,taskId);
            if(optionalTask.isEmpty()){
                return new Result<>(Fail);
            }
            T task=optionalTask.get();
            return new Result<>(Complete, task);
        } catch (IOException e) {
            log.error(e);
            return new Result<>(Fail);
        }
    }

    @Override
    public Result getTaskListById(long id) {
        try {
            List<Employee> listEmpRes = select(Employee.class);
            Optional<Employee> optionalUser = searchEmployee(listEmpRes, id);
            if(optionalUser.isEmpty()){
                return new Result<>(Fail);
            }
            Employee findedEmployee = optionalUser.get();
            List<Task> listRes = select(Task.class);
            List<Task> listTask = listRes.stream()
                    .filter(el -> el.getScrumMaster().getId()==id)
                    .collect(Collectors.toList());
            if(listTask.isEmpty()){
                return new Result(Outcomes.Empty);
            }
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
        if(optionalProject.isEmpty()){
            return new Result<>(Outcomes.Empty);
        }else{
            return new Result<>(Complete,optionalProject);
        }

    }

    @Override
    public Result getProjectListById(Employee employee) {
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
        if(optionalProject.isEmpty()){
            return new Result<>(Fail);
        }else{
            return new Result<>(Complete,optionalProject);
        }
    }

    @Override
    public Result getTaskWorker(Employee employee, long taskId) {
        //Task
        List<Task> listRes = select(Task.class);
        List<Task> findedTaskList = listRes.stream()
                .filter(el -> el.getTeam().contains(employee.getId()))
                .collect(Collectors.toList());
        List<Task> findedTask = findedTaskList;
        if(!findedTask.isEmpty()) {
            return new Result<>(Complete, findedTask);
        }else{
            return new Result<>(Fail);
        }
    }

    @Override
    public Result deleteTask(Task task) {
    try {
        deleteGenericTask(Task.class, task.getId());
        return new Result(Complete);
    } catch (IOException e) {
        log.error(e);
        return new Result(Fail);
    }
}

    @Override
    public Result deleteProject(Project project) {
        try {
            deleteGenericProject(Project.class,project.getId());
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

    @Override
    public Result updateProject(Project project) {
    try {
        updateGenericProject(Project.class,project);
        return new Result(Complete);
    } catch (IOException e) {
        log.error(e);
        return new Result(Fail);
    }
}

    @Override
    public Result<Employee> changeProfileInfo(Employee editedEmployee) {
        try {
            List<Employee> listRes = select(Employee.class);
            Optional<Employee> optionalUser = searchEmployee(listRes, editedEmployee.getId());
            if(optionalUser.isEmpty()){
                return new Result<>(Fail);
            }
            listRes.remove(optionalUser.get());
            listRes.add(editedEmployee);
            insertGenericEmployee(Employee.class, listRes, false);
            return new Result<>(Complete);
        } catch (IOException e) {
            log.error(e);
            return new Result<>(Fail);
        }

    }

    @Override
    public Result changeTaskStatus(long id, String status) {
        try {
            if(status.isEmpty()){
                return new Result<>(Fail);
            }
            List<Task> listRes = select(Task.class);
            Optional<Task> optionalTask = searchTask(listRes, id);
            if (optionalTask.isEmpty()) {
                return new Result<>(Fail);
            }
            Task editedTask = optionalTask.get();
            listRes.remove(optionalTask.get());
            editedTask.setStatus(TypeOfCompletion.valueOf(status));
            listRes.add(editedTask);
            insertGenericTask(Task.class, listRes, false);
            return new Result<>(Complete);
        } catch (IOException e) {
            log.error(e);
            return new Result<>(Fail);
        }
    }

    @Override
    public Result<Double> calculateTaskCost(Task task) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT, Locale.ENGLISH);
            Date firstDate = sdf.parse(String.valueOf(task.getCreatedDate()));
            Date secondDate = sdf.parse(String.valueOf(task.getDeadline()));
            long diffInMillies = Math.abs(secondDate.getTime() - firstDate.getTime());
            long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
            double res=(double) diff * task.getMoney();
            return new Result<>(Complete, res);
        } catch (ParseException e) {
            log.error(e);
            return new Result<>(Fail);
        }
    }

    @Override
    public Result<Long> calculateProjectCost(Project project) {
        List<Task> taskList = project.getTask();
        double projectCost = 0.0;
        for (Task task: taskList) {
            projectCost = projectCost + (double) calculateTaskCost(task).getData();
        }
        return new Result<Long>(Complete,(long) projectCost);
    }

    @Override
    public Result<Long> calculateProjectTime(Project project) {
        List<Task> taskList = project.getTask();
        long resulttime = 0;
        for (Task task: taskList) {
                resulttime = resulttime + (long)calculatePrice(task).getData();
        }
        return new Result<>(Complete,resulttime);
    }

    @Override
    public <T extends Task> Result<T> writeComment(Class<T> cl, long id, String comment) {
        if(comment.isEmpty()){
            return new Result<>(Fail);
        }
        try {
            List<T> listRes = select(cl);
            Optional<T> optionalTask = searchTask(listRes, id);
            if (optionalTask.isEmpty()) {
                return new Result<>(Fail);
            }
            T editedTask = (T) optionalTask.get();
            listRes.remove(optionalTask.get());
            editedTask.setTaskDescription(comment);
            listRes.add(editedTask);
            insertGenericTask(cl, listRes, false);
            return new Result<>(Complete);
        } catch (IOException e) {
            log.error(e);
            return new Result<>(Fail);
        }
    }

    @Override
    public Result<Employee> correctEmployeeParameters(Employee editedEmployee) {
        try {
            updateGenericEmployee(Employee.class, editedEmployee);
            return new Result<>(Complete);
        } catch (IOException e) {
            log.error(e);
            return new Result<>(Fail);
        }
    }

    @Override
    public Result<Employee> addEmployeeToTask(Task task, Employee employee) {
        List<Employee> employeeList = task.getTeam();
        employeeList.addAll(Collections.singleton(employee));
        task.setTeam(employeeList);
        return new Result<>(Complete);
    }

}