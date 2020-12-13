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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.groupappcontrol.Constants;
import ru.sfedu.groupappcontrol.Result;
import ru.sfedu.groupappcontrol.models.*;
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

import static ru.sfedu.groupappcontrol.models.enums.Outcomes.*;

public class DataProviderCsv implements DataProvider {

    private static final Logger log = LogManager.getLogger(DataProviderCsv.class);
    @Override
    public void initDataSource() {
    }

    /**
     * @param cl
     * @return
     * @throws IOException
     */
    public String getPath(Class<?> cl) {
        try {
            String PATH = ConfigurationUtil.getConfigurationEntry(Constants.CSV_PATH);
            return PATH + cl.getSimpleName().toLowerCase() + ConfigurationUtil.
                    getConfigurationEntry(Constants.FILE_EXTENSION_CSV);
        } catch (IOException e) {
            log.error(e);
            return null;
        }
    }

    /**
     * @param path
     * @throws IOException
     */
    public void createFile(String path)  {
        try {
            String PATH = ConfigurationUtil.getConfigurationEntry(Constants.CSV_PATH);
            File file = new File(path);
            if (!file.exists()) {
                Path dirPath = Paths.get(PATH);
                Files.createDirectories(dirPath);
                if(!file.createNewFile()){
                    log.error(Empty);
                }
            }
        } catch (IOException e) {
            log.error(e);
        }
    }

    @Override
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

    /**
     * @param path
     * @param list
     * @param <T>
     * @return
     */
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
    //CRUD


    @Override
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

    @Override
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

    @Override
    public <T extends Project> Result<T> getProjectByID(Class<T> cl, long id) {
        try{
            List<T> listRes = select(cl);
            Optional<T> optional = listRes.stream().filter(el -> el.getId() == id).findFirst();
            return new Result<>(Complete, optional.orElseThrow());
        } catch (NoSuchElementException e) {
            log.error(e);
            return new Result<>(Fail);
        }
    }

    @Override
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
        } catch (IndexOutOfBoundsException e) {
            log.error(e);
            return new Result<>(Fail);
        }

    }

    @Override
    public <T extends Employee> Result<Void> insertGenericEmployee(Class<T> cl,
                                                                   List<T> list, boolean append) {
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
        } catch (IndexOutOfBoundsException e) {
            log.error(e);
            return new Result<>(Fail);
        }

    }

    @Override
    public <T extends Project> Result<Void> insertGenericProject(Class<T> cl,
                                                                 List<T> list, boolean append) {
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
        } catch (IndexOutOfBoundsException e) {
            log.error(e);
            return new Result<>(Fail);
        }

    }

    @Override
    public <T extends Task> Result<T> deleteGenericTask(Class<T> cl, long id) {
        List<T> listData = select(cl);
        listData = listData.stream().filter(el -> el.getId() != id).collect(Collectors.toList());
        insertGenericTask(cl, listData, false);
        return new Result<>(Complete);
    }

    @Override
    public <T extends Employee> Result<T> deleteGenericEmployee(Class<T> cl, long id) {
        List<T> listData = select(cl);
        listData = listData.stream().filter(el -> el.getId() != id).collect(Collectors.toList());
        insertGenericEmployee(cl, listData, false);
        return new Result<>(Complete);
    }

    @Override
    public <T extends Project> Result<T> deleteGenericProject(Class<T> cl, long id){
        List<T> listData = select(cl);
        listData = listData.stream().filter(el -> el.getId() != id).collect(Collectors.toList());
        insertGenericProject(cl, listData, false);
        return new Result<>(Complete);
    }

    @Override
    public <T extends Task> Result<T> updateGenericTask(Class<T> cl, T updElement){
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

    @Override
    public <T extends Employee> Result<T> updateGenericEmployee(Class<T> cl, T updElement)  {
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

    @Override
    public <T extends Project> Result<T> updateGenericProject(Class<T> cl, T updElement) {
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

    /**
     * @param listRes
     * @param id
     * @param <T>
     * @return
     * @throws IOException
     */
    private <T extends Task> Optional<T> searchTask(List<T> listRes,long id) {
        return listRes.stream()
                .filter(el -> el.getId() == id)
                .findFirst();
    }

    /**
     * @param listRes
     * @param id
     * @param <T>
     * @return
     * @throws IOException
     */
    private <T extends Employee> Optional<T> searchEmployee(List<T> listRes,long id) {
        return listRes.stream()
                .filter(el -> el.getId() == id)
                .findFirst();
    }

    /**
     * @param listRes
     * @param id
     * @param <T>
     * @return
     * @throws IOException
     */
    private <T extends Project> Optional<T> searchProject(List<T> listRes,long id) {
        return listRes.stream()
                .filter(el -> el.getId() == id)
                .findFirst();
    }

//Task
    /**
     * @param task
     * @param id
     * @param taskDescription
     * @param money
     * @param scrumMaster
     * @param status
     * @param team
     * @param createdDate
     * @param deadline
     * @param lastUpdate
     * @param taskType
     */
    private void setBasicTask(Task task, long id, String taskDescription, double money,
                              Employee scrumMaster, TypeOfCompletion status, List<Employee> team,
                              String createdDate, String deadline,String lastUpdate,TaskTypes taskType ){
    task.setId(id);
    task.setTaskDescription(taskDescription);
    task.setMoney(money);
    task.setScrumMaster(scrumMaster);
    task.setStatus(status);
    task.setTeam(team);
    task.setCreatedDate(createdDate);
    task.setDeadline(deadline);
    task.setLastUpdate(lastUpdate);
    task.setTaskType(taskType);
}

    @Override
    public <T extends Task> Result <T> getTaskInfoGeneric(Class<T> cl,long taskId){
        try {
            List<T> list = select(cl);
            Optional<T> task = searchTask(list,taskId);
            if(task.isEmpty()){
                return new Result<>(Fail);
            }
            T res = task.get();
            if (list.isEmpty()) {
                return new Result<>(Fail);
            } else {
                return new Result<>(Complete, res);
            }
        } catch (Exception e) {
            log.error(e);
            return new Result<>(Fail);
        }
    }

    @Override
    public Result<Task> createTask(long id, @NonNull String taskDescription,@NonNull Double money,
                                   @NonNull Employee scrumMaster,@NonNull TypeOfCompletion status,
                                   @NonNull List<Employee> team,@NonNull String createdDate,
                                   @NonNull String deadline,@NonNull String lastUpdate,
                                   @NonNull TaskTypes taskType) {
        switch(taskType){
            case BASE_TASK:
                Task baseTask = createBaseTask(id, taskDescription, money, scrumMaster, status, team, createdDate, deadline, lastUpdate).getData();
                return new Result<>(Complete, baseTask);
            case DEVELOPERS_TASK:
                DevelopersTask developersTask = createDevelopersTask(id, taskDescription, money, scrumMaster, status, team, createdDate, deadline, lastUpdate).getData();
                return new Result<>(Complete, developersTask);
            case TESTERS_TASK:
                TestersTask testersTask = createTestersTask(id, taskDescription, money, scrumMaster, status, team, createdDate, deadline, lastUpdate).getData();
                return new Result<>(Complete, testersTask);
            default:
                return new Result<>(Fail);
        }
    }

    /**
     * @param id
     * @param taskDescription
     * @param money
     * @param scrumMaster
     * @param status
     * @param team
     * @param createdDate
     * @param deadline
     * @param lastUpdate
     * @return
     */
    public Result<Task> createBaseTask(long id,@NonNull String taskDescription,@NonNull Double money,
                                       @NonNull Employee scrumMaster,@NonNull TypeOfCompletion status,
                                       @NonNull List<Employee> team,@NonNull String createdDate,
                                       @NonNull String deadline,@NonNull String lastUpdate) {
        Task task = new Task();
        setBasicTask(task,
                id,
                taskDescription,
                money,
                scrumMaster,
                status,
                team,
                createdDate,
                deadline,
                lastUpdate,
                TaskTypes.BASE_TASK);
        return new Result<>(Complete,task);
    }

    /**
     * @param id
     * @param taskDescription
     * @param money
     * @param scrumMaster
     * @param status
     * @param team
     * @param createdDate
     * @param deadline
     * @param lastUpdate
     * @return
     */
    public Result<DevelopersTask> createDevelopersTask(long id,@NonNull String taskDescription,@NonNull Double money,@NonNull Employee scrumMaster,@NonNull TypeOfCompletion status,@NonNull List<Employee> team,@NonNull String createdDate,@NonNull String deadline,@NonNull String lastUpdate) {
        DevelopersTask developersTask= new DevelopersTask();
        setBasicTask(developersTask,
                id,
                taskDescription,
                money,
                scrumMaster,
                status,
                team,
                createdDate,
                deadline,
                lastUpdate,
                TaskTypes.DEVELOPERS_TASK);
        developersTask.setDeveloperTaskType(DeveloperTaskType.DEVELOPMENT);
        developersTask.setDeveloperComments(Constants.BaseComment);
        return new Result<>(Complete,developersTask);
    }

    /**
     * @param id
     * @param taskDescription
     * @param money
     * @param scrumMaster
     * @param status
     * @param team
     * @param createdDate
     * @param deadline
     * @param lastUpdate
     * @return
     */
    public Result<TestersTask> createTestersTask(long id,@NonNull String taskDescription,@NonNull Double money,
                                                 @NonNull Employee scrumMaster,@NonNull TypeOfCompletion status,
                                                 @NonNull List<Employee> team,@NonNull String createdDate,
                                                 @NonNull String deadline,@NonNull String lastUpdate) {
        TestersTask testersTask= new TestersTask();
        setBasicTask(testersTask,
                id,
                taskDescription,
                money,
                scrumMaster,
                status,
                team,
                createdDate,
                deadline,
                lastUpdate,
                TaskTypes.TESTERS_TASK);
        testersTask.setBugStatus(BugStatus.IN_WORK);
        testersTask.setBugDescription(Constants.BaseComment);
        return new Result<>(Complete,testersTask);
    }

    @Override
    public Result<Task> getTasks(long id){
        List<Task> taskList = new ArrayList<>();
        taskList.addAll(select(Task.class));
        taskList.addAll(select(TestersTask.class));
        taskList.addAll(select(DevelopersTask.class));
        Optional<Task> optTask = taskList.stream().filter(el -> el.getId() == id).findAny();
        return optTask.map(task -> new Result<>(Outcomes.Complete, task)).orElseGet(() ->
                new Result<>(Outcomes.Fail));
    }

    @Override
    public List<Task> getAllTask(){
        List<Task> taskList = new ArrayList<>();
        taskList.addAll(select(Task.class));
        taskList.addAll(select(TestersTask.class));
        taskList.addAll(select(DevelopersTask.class));
        return taskList;
    }

    @Override
    public Result getTasksByUser(long userId, long taskId) {
        List<Task> list = select(Task.class);
        Task task = searchTask(list, taskId).get();
        if(task.getTeam().contains(null)){
            return new Result(Fail);
        }
        if (task.getTeam().stream().anyMatch(employee -> employee.getId() == userId)) {
            return new Result(Complete, task);
        } else {
            return new Result(Fail);
        }
    }

    @Override
    public <T extends Task> Result<T> getAnyTaskByTaskId(Class cl, long taskId) {
        List<T> listTaskRes = select(cl);
        Optional<T> optionalTask= searchTask(listTaskRes,taskId);
        if(optionalTask.isEmpty()){
            return new Result<>(Fail);
        }
        T task=optionalTask.get();
        return new Result<>(Complete, task);
    }

    @Override
    public Result<List<Task>> getTaskWorker(Employee employee, long taskId) {
        //Task
        List<Task> listRes = select(Task.class);
        List<Task> taskList = listRes.stream()
                .filter(task -> task.getId() == taskId)
                .filter(task -> task.getTeam().stream().anyMatch(employee1 ->
                        employee1.getId() == employee.getId()))
                .collect(Collectors.toList());
        if(!taskList.isEmpty()) {
            return new Result<>(Complete, taskList);
        }else{
            return new Result<>(Fail);
        }
    }

    @Override
    public Result<Void> deleteTask(Task task) {
        deleteGenericTask(Task.class, task.getId());
        return new Result<>(Complete);
    }

    @Override
    public Result<Void> changeTaskStatus(long id, String status) {
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
    public <T extends Task> Result<T> writeComment(Class<T> cl, long id, String comment) {
        if(comment.isEmpty()){
            return new Result<>(Fail);
        }
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
    }
    @Override
    public <T extends Task> Result<List<T>> getTaskListByScrumMaster(Class<T> cl, long userId){
        List<T> listRes = select(cl);
        List<T> optionalRes = listRes.stream()
                .filter(el -> el.getScrumMaster().getId() == userId)
                .collect(Collectors.toList());
        if(optionalRes.contains(null)){
            return new Result<>(Outcomes.NotFound);
        }
        return new Result<>(Complete, optionalRes);
    }

    //Project
    @Override
    public Result<Project> createProject(long id,@NonNull String title,@NonNull String takeIntoDevelopment,
                                         @NonNull List<Task> tasks) {
        Project project=new Project();
        project.setId(id);
        project.setTitle(title);
        project.setTakeIntoDevelopment(takeIntoDevelopment);
        project.setTask(tasks);
        return new Result<>(Complete,project);
    }

    /**
     * @param userId
     * @return
     */
    public Result<Project> getProjectStatistic(long userId) {
        //Task
        List<Task> listRes = select(Task.class);
        List<Task> findedTaskList = listRes.stream()
                .filter(el -> el.getScrumMaster().getId() == userId)
                .collect(Collectors.toList());
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
        return new Result(Complete, optionalProject);

    }

    @Override
    public Result<Project> getProjectByProjectID(long projectId) {
        List<Project> listPrjRes = select(Project.class);
        Optional<Project> optionalProject = searchProject(listPrjRes, projectId);
        if(optionalProject.isEmpty()){
            return new Result<>(Fail);
        }
        Project findedProject = optionalProject.get();
        return new Result<>(Complete, findedProject);
    }

    @Override
    public Result getProjectById(Employee employee, long projectId) {
        List<Task> listRes = select(Task.class);
        List<Task> findedTaskList = listRes.stream()
                .filter(task -> task.getTeam().stream().anyMatch(employee1 ->
                        employee1.getId() == employee.getId()))
                .collect(Collectors.toList());
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
        return optionalProject.isEmpty() ? new Result<>(Outcomes.Empty) :
                new Result<>(Complete,optionalProject);
        }

    @Override
    public Result<Void> deleteProject(Project project) {
        deleteGenericProject(Project.class,project.getId());
        return new Result<>(Complete);
    }

    @Override
    public Result<Void> updateProject(Project project) {
        updateGenericProject(Project.class,project);
        return new Result<>(Complete);
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

    //Employee
    @Override
    public Result<Employee> createEmployee(long id,@NonNull String firstName,@NonNull String lastName,
                                           @NonNull String login,@NonNull String password,
                                           @NonNull String email,@NonNull String token,
                                           @NonNull String department,@NonNull TypeOfEmployee typeOfEmployee){
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

    /**
     * @param employee
     * @param id
     * @param firstName
     * @param lastName
     * @param login
     * @param email
     * @param password
     * @param token
     * @param department
     * @param typeOfEmployee
     */
    private void setBasicEmployee(Employee employee,
                                  long id,
                                  String firstName,
                                  String lastName,
                                  String login,
                                  String email,
                                  String password,
                                  String token,
                                  String department,
                                  TypeOfEmployee typeOfEmployee){
        employee.setId(id);
        employee.setFirstName(firstName);
        employee.setLastName(lastName);
        employee.setLogin(login);
        employee.setPassword(password);
        employee.setEmail(email);
        employee.setToken(token);
        employee.setDepartment(department);
        employee.setTypeOfEmployee(typeOfEmployee);
    }


    /**
     * @param id
     * @param firstName
     * @param lastName
     * @param login
     * @param password
     * @param email
     * @param token
     * @param department
     * @return
     */
    public Result<Employee> createBaseEmployee(long id,@NonNull String firstName,
                                               @NonNull String lastName,@NonNull String login,
                                               @NonNull String password,@NonNull String email,
                                               @NonNull String token,@NonNull String department) {
        Employee employee = new Employee();
        setBasicEmployee(employee,
                id,
                firstName,
                lastName,
                login,
                password,
                email,
                token,
                department,
                TypeOfEmployee.Employee);
        return new Result<>(Complete,employee);
    }

    /**
     * @param id
     * @param firstName
     * @param lastName
     * @param login
     * @param password
     * @param email
     * @param token
     * @param department
     * @return
     */
    public Result<Developer> createDeveloperEmployee(long id,@NonNull String firstName,
                                                     @NonNull String lastName,@NonNull String login,
                                                     @NonNull String password,@NonNull String email,
                                                     @NonNull String token,@NonNull String department) {
        Developer developer= new Developer();
        setBasicEmployee(developer,
                id,
                firstName,
                lastName,
                login,
                password,
                email,
                token,
                department,
                TypeOfEmployee.Developer);
        developer.setStatus(TypeOfDevelopers.CUSTOM);
        developer.setProgrammingLanguage(ProgrammingLanguage.Custom);
        return new Result<>(Complete,developer);
    }

    /**
     * @param id
     * @param firstName
     * @param lastName
     * @param login
     * @param password
     * @param email
     * @param token
     * @param department
     * @return
     */
    public Result<Tester> createTesterEmployee(long id,@NonNull String firstName,@NonNull String lastName,
                                               @NonNull String login,@NonNull String password,
                                               @NonNull String email,@NonNull String token,
                                               @NonNull String department) {
        Tester tester= new Tester();
        setBasicEmployee(tester,
                id,
                firstName,
                lastName,
                login,
                password,
                email,
                token,
                department,
                TypeOfEmployee.Tester);
        tester.setStatus(TypeOfDevelopers.CUSTOM);
        tester.setProgrammingLanguage(ProgrammingLanguage.Custom);
        tester.setTypeOfTester(TypeOfTester.Custom);
        return new Result<>(Complete,tester);
    }

    @Override
    public List<Employee> getAllEmployee(){
        List<Employee> employees = new ArrayList<>();
        employees.addAll(select(Employee.class));
        employees.addAll(select(Tester.class));
        employees.addAll(select(Developer.class));
        return employees;
    }

    @Override
    public Result<List<Task>> getScrumMasterTaskList(long userId, TaskTypes taskTypes) {
        switch (taskTypes){
            case BASE_TASK:
                return new Result<>(Complete, getTaskListByScrumMaster(Task.class, userId).getData());
            case DEVELOPERS_TASK:
                return new Result<>(Complete,new ArrayList<>(getTaskListByScrumMaster(DevelopersTask.class,
                        userId).getData()));
            case TESTERS_TASK:
                return new Result<>(Complete,new ArrayList<>(getTaskListByScrumMaster(TestersTask.class,
                        userId).getData()));
            default:
                return new Result<>(Fail);
        }
    }

    @Override
    public <T extends Employee> Result getUserInfoList(Class<T> cl,long userId) {
        return new Result(getEmployeeByID(cl,userId).getStatus(),getEmployeeByID(cl,userId).getData());
    }

    @Override
    public Result getProjectListByScrummasterId(long id) {
        List<Task> listRes = select(Task.class);
        List<Task> findedTaskList = listRes.stream()
                .filter(el -> el.getTeam().contains(id))
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
    public Result<Void> deleteEmployeeFromTask(Task task, Employee employee) {
        List<Employee> employeeList = task.getTeam();
        employeeList.removeIf(employee1 -> employee1.getId() == employee.getId());
        task.setTeam(employeeList);
        return new Result<>(Complete);
    }

    @Override
    public Result<Employee> changeProfileInfo(Employee editedEmployee) {
        List<Employee> listRes = select(Employee.class);
        Optional<Employee> optionalUser = searchEmployee(listRes, editedEmployee.getId());
        if(optionalUser.isEmpty()){
            return new Result<>(Fail);
        }
        listRes.remove(optionalUser.get());
        listRes.add(editedEmployee);
        insertGenericEmployee(Employee.class, listRes, false);
        return new Result<>(Complete);

    }

    @Override
    public Result<Employee> correctEmployeeParameters(Employee editedEmployee) {
        updateGenericEmployee(Employee.class, editedEmployee);
        return new Result<>(Complete);
    }

    @Override
    public Result<Employee> addEmployeeToTask(Task task, Employee employee) {
        List<Employee> employeeList = task.getTeam();
        employeeList.addAll(Collections.singleton(employee));
        task.setTeam(employeeList);
        return new Result<>(Complete);
    }

    //Optional

    @Override
    public <T> Result<T> deleteRecord(Class<T> cl) {
        String path = getPath(cl);
        File file = new File(path);
        file.delete();
        return new Result<>(Complete);

    }

    @Override
    public void deleteAllRecord(){
        deleteRecord(Employee.class);
        deleteRecord(Developer.class);
        deleteRecord(Tester.class);
        deleteRecord(Task.class);
        deleteRecord(DevelopersTask.class);
        deleteRecord(TestersTask.class);
        deleteRecord(Project.class);
    }

    /**
     * @param task
     * @return
     */
    private Result<Long> calculatePrice(Task task) {
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
    }