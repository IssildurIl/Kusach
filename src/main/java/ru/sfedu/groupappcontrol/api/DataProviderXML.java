package ru.sfedu.groupappcontrol.api;


import lombok.NonNull;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import ru.sfedu.groupappcontrol.Constants;
import ru.sfedu.groupappcontrol.Result;
import ru.sfedu.groupappcontrol.models.*;
import ru.sfedu.groupappcontrol.models.enums.*;
import ru.sfedu.groupappcontrol.utils.ConfigurationUtil;
import ru.sfedu.groupappcontrol.utils.CustomLogger;
import ru.sfedu.groupappcontrol.utils.WrapperXML;

import java.io.*;
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

/**
 * The type Data api xml.
 */
@SuppressWarnings("JavaDoc")
public class DataProviderXML implements DataProvider {

    private static final Logger log = LogManager.getLogger(DataProviderXML.class);

    @Override
    public void initDataSource() {

    }

    @Override
    public Result<Task> getTaskById(long id){
        log.info(CustomLogger.startFunc());
        return getTaskByID(Task.class,id);
    }

    @Override
    public Result<DevelopersTask> getDevelopersTaskById(long id){
        log.info(CustomLogger.startFunc());
        return getTaskByID(DevelopersTask.class,id);
    }

    @Override
    public Result<TestersTask> getTestersTaskById(long id){
        log.info(CustomLogger.startFunc());
        return getTaskByID(TestersTask.class,id);
    }

    @Override
    public Result<Employee> getEmployeeById(long id){
        log.info(CustomLogger.startFunc());
        return getEmployeeByID(Employee.class,id);
    }

    @Override
    public Result<Developer> getDeveloperById(long id){
        log.info(CustomLogger.startFunc());
        return getEmployeeByID(Developer.class,id);
    }

    @Override
    public Result<Tester> getTesterById(long id){
        log.info(CustomLogger.startFunc());
        return getEmployeeByID(Tester.class,id);
    }

    @Override
    public Result<Project> getProjectByID( long id){
        try{
            log.info(CustomLogger.startFunc());
            List<Project> listRes = select(Project.class);
            Optional<Project> optional = listRes.stream().filter(el -> el.getId() == id).findFirst();
            log.debug(optional);
            log.info(CustomLogger.startFunc(optional));
            return new Result<>(Complete, optional.orElseThrow());
        } catch (NoSuchElementException e) {
            log.error(e);
            log.info(CustomLogger.endFunc(e));
            return new Result<>(Fail);
        }
    }

    @Override
    public Result<Void> insertTask(Task task){
        log.info(CustomLogger.startFunc());
        if(!isValidTask(task)){
            log.info(CustomLogger.endFunc());
            return new Result<>(Fail);
        }
        return insertGenericTask(Task.class,task);
    }

    @Override
    public Result<Void> insertDevelopersTask(DevelopersTask task){
        log.info(CustomLogger.startFunc());
        if(!isValidTask(task)){
            log.info(CustomLogger.endFunc());
            return new Result<>(Fail);
        }
        return insertGenericTask(DevelopersTask.class,task);
    }

    @Override
    public Result<Void> insertTestersTask(TestersTask task){
        log.info(CustomLogger.startFunc());
        if(!isValidTask(task)){
            log.info(CustomLogger.endFunc());
            return new Result<>(Fail);
        }
        return insertGenericTask(TestersTask.class,task);
    }

    @Override
    public Result<Void> insertEmployee(Employee employee){
        log.info(CustomLogger.startFunc());
        if(!isValidEmployee(employee)){
            log.info(CustomLogger.endFunc());
            return new Result<>(Fail);
        }
        return insertGenericEmployee(Employee.class,employee);
    }

    @Override
    public Result<Void> insertDeveloper(Developer developer){
        log.info(CustomLogger.startFunc());
        if(!isValidEmployee(developer)){
            log.info(CustomLogger.endFunc());
            return new Result<>(Fail);
        }
        return insertGenericEmployee(Developer.class,developer);
    }

    @Override
    public Result<Void> insertTester(Tester tester){
        log.info(CustomLogger.startFunc());
        if(!isValidEmployee(tester)){
            log.info(CustomLogger.endFunc());
            return new Result<>(Fail);
        }
        return insertGenericEmployee(Tester.class,tester);
    }

    @Override
    public Result<Project> insertProject(Project project) {
        try{
            log.info(CustomLogger.startFunc());
            if(!isValidProject(project)){
                log.info(CustomLogger.endFunc());
                return new Result<>(Fail);
            }
            String path = getPath(Project.class);
            log.debug(path);
            createFile(path);
            List<Project> oldList = this.select(Project.class);
            if (oldList != null) {
                long id = project.getId();
                log.debug(id);
                if (oldList.stream().anyMatch(el -> el.getId() == id)) {
                    log.info(CustomLogger.endFunc());
                    return new Result<>(Fail);
                }
                oldList.add(project);
            }
            writer(path,oldList);
            return new Result<>(Complete);
        } catch (IndexOutOfBoundsException e) {
            log.error(e);
            log.info(CustomLogger.endFunc(e));
            return new Result<>(Fail);
        }

    }

    @Override
    public Result<Void> deleteTask(long id){
        log.info(CustomLogger.startFunc());
        return deleteGenericTask(Task.class,id);
    }

    @Override
    public Result<Void> deleteDevelopersTask(long id){
        log.info(CustomLogger.startFunc());
        return deleteGenericTask(DevelopersTask.class,id);
    }

    @Override
    public Result<Void> deleteTestersTask(long id){
        log.info(CustomLogger.startFunc());
        return deleteGenericTask(TestersTask.class,id);
    }

    @Override
    public Result<Void> deleteEmployee(long id){
        log.info(CustomLogger.startFunc());
        return deleteGenericEmployee(Employee.class,id);
    }

    @Override
    public Result<Void> deleteDeveloper(long id){
        log.info(CustomLogger.startFunc());
        return deleteGenericEmployee(Developer.class,id);
    }

    @Override
    public Result<Void> deleteTester(long id){
        log.info(CustomLogger.startFunc());
        return deleteGenericEmployee(Tester.class,id);
    }

    @Override
    public Result<Void> deleteProject(long id){
        log.info(CustomLogger.startFunc());
        List<Project> listData = select(Project.class);
        listData = listData.stream().filter(el -> el.getId() != id).collect(Collectors.toList());
        log.debug(listData);
        insertProjectForDelete(listData, false);
        return new Result<>(Complete);
    }

    @Override
    public Result<Void> updateTask(Task task){
        log.info(CustomLogger.startFunc());
        if(!isValidTask(task)){
            log.info(CustomLogger.endFunc());
            return new Result<>(Fail);
        }
        return updateGenericTask(Task.class,task);
    }

    @Override
    public Result<Void> updateDevelopersTask(DevelopersTask developersTask){
        log.info(CustomLogger.startFunc());
        if(!isValidTask(developersTask)){
            log.info(CustomLogger.endFunc());
            return new Result<>(Fail);
        }
        return updateGenericTask(DevelopersTask.class, developersTask);
    }

    @Override
    public Result<Void> updateTestersTask(TestersTask testersTask){
        log.info(CustomLogger.startFunc());
        if(!isValidTask(testersTask)){
            log.info(CustomLogger.endFunc());
            return new Result<>(Fail);
        }
        return updateGenericTask(TestersTask.class,testersTask);
    }

    @Override
    public Result<Void> updateEmployee(Employee employee){
        log.info(CustomLogger.startFunc());
        if(!isValidEmployee(employee)){
            log.info(CustomLogger.endFunc());
            return new Result<>(Fail);
        }
        return updateGenericEmployee(Employee.class,employee);
    }

    @Override
    public Result<Void> updateDeveloper(Developer developer){
        log.info(CustomLogger.startFunc());
        if(!isValidEmployee(developer)){
            log.info(CustomLogger.endFunc());
            return new Result<>(Fail);
        }
        return updateGenericEmployee(Developer.class, developer);
    }

    @Override
    public Result<Void> updateTester(Tester tester){
        log.info(CustomLogger.startFunc());
        if(!isValidEmployee(tester)){
            log.info(CustomLogger.endFunc());
            return new Result<>(Fail);
        }
        return updateGenericEmployee(Tester.class,tester);
    }

    @Override
    public Result<Project> updateProject(Project project) {
        try {
            log.info(CustomLogger.startFunc());
            if(!isValidProject(project)){
                log.info(CustomLogger.endFunc());
                return new Result<>(Fail);
            }
            List<Project> userList = select(Project.class);
            Optional<Project> optionalUser = searchProject(userList,project.getId());
            log.debug(optionalUser);
            if(optionalIsValid(optionalUser)){
                log.info(CustomLogger.endFunc());
                return new Result<>(Fail);
            }
            log.debug(optionalUser);
            userList.remove(optionalUser.get());
            log.debug(optionalUser);
            userList.add(project);
            insertProjectForDelete(userList, false);
            log.info(CustomLogger.endFunc());
            return new Result<>(Complete);
        } catch (Exception e) {
            log.error(e);
            log.info(CustomLogger.endFunc(e));
            return new Result<>(Fail);
        }

    }

    @Override
    public Result<Task> createTask(@NonNull long id, @NonNull String taskDescription,
                                   @NonNull Double money,@NonNull Employee scrumMaster,
                                   @NonNull TypeOfCompletion status,
                                   @NonNull List<Employee> team,@NonNull String createdDate,
                                   @NonNull String deadline,@NonNull String lastUpdate,
                                   @NonNull TaskTypes taskType) {
        log.info(CustomLogger.startFunc());
        switch (taskType) {
            case BASE_TASK:
                Task baseTask = createBaseTask(id, taskDescription, money, scrumMaster, status, team, createdDate, deadline, lastUpdate).getData();
                log.debug(baseTask);
                log.info(CustomLogger.endFunc(baseTask));
                return isValidTask(baseTask) ? new Result<>(Complete, baseTask): new Result<>(Fail);
            case DEVELOPERS_TASK:
                DevelopersTask developersTask = createDevelopersTask(id, taskDescription, money, scrumMaster, status, team, createdDate, deadline, lastUpdate).getData();
                log.debug(developersTask);
                log.info(CustomLogger.endFunc(developersTask));
                return isValidTask(developersTask) ? new Result<>(Complete, developersTask): new Result<>(Fail);
            case TESTERS_TASK:
                TestersTask testersTask = createTestersTask(id, taskDescription, money, scrumMaster, status, team, createdDate, deadline, lastUpdate).getData();
                log.debug(testersTask);
                log.info(CustomLogger.endFunc(testersTask));
                return isValidTask(testersTask) ? new Result<>(Complete, testersTask): new Result<>(Fail);
            default:
                log.info(CustomLogger.endFunc());
                return new Result<>(Fail);
        }
    }

    @Override
    public Result<Task> getTasks(long id){
        log.info(CustomLogger.startFunc());
        List<Task> taskList = new ArrayList<>();
        taskList.addAll(select(Task.class));
        taskList.addAll(select(TestersTask.class));
        taskList.addAll(select(DevelopersTask.class));
        log.debug(taskList);
        Optional<Task> optTask = taskList.stream().filter(el -> el.getId() == id).findAny();
        log.info(CustomLogger.endFunc(optTask));
        return optTask.map(task -> new Result<>(Outcomes.Complete, task)).orElseGet(() ->
                new Result<>(Outcomes.Fail));
    }

    @Override
    public List<Task> getAllTask(){
        log.info(CustomLogger.startFunc());
        List<Task> taskList = new ArrayList<>();
        taskList.addAll(select(Task.class));
        taskList.addAll(select(TestersTask.class));
        taskList.addAll(select(DevelopersTask.class));
        log.debug(taskList);
        log.info(CustomLogger.endFunc(taskList));
        return taskList;
    }

    @Override
    public Result<Task> getTasksByUser(long userId, long taskId) {
        try {
            log.info(CustomLogger.startFunc());
            List<Task> list = select(Task.class);
            Task task = searchTask(list,taskId).get();
            log.debug(task);
            if(listIsValid(task.getTeam())){
                log.info(CustomLogger.endFunc());
                return new Result<>(Fail);
            }
            log.debug(task);
            log.info(CustomLogger.endFunc(task));
            return task.getTeam().stream().anyMatch(employee -> employee.getId() == userId)
                    ? new Result<>(Complete, task) : new Result<>(Fail);
        } catch (Exception e) {
            log.error(e);
            log.info(CustomLogger.endFunc(e));
            return new Result<>(Fail);
        }
    }

    @Override
    public Result<Void> changeTaskStatus(long id, String status) {
        try {
            log.info(CustomLogger.startFunc());
            if(stringIsValid(status)){
                log.info(CustomLogger.endFunc());
                return new Result<>(Fail);
            }
            List<Task> listRes = select(Task.class);
            Optional<Task> optionalTask = searchTask(listRes, id);
            if(optionalIsValid(optionalTask)){
                log.info(CustomLogger.endFunc());
                return new Result<>(Fail);
            }
            log.debug(optionalTask);
            Task editedTask = optionalTask.get();
            listRes.remove(optionalTask.get());
            editedTask.setStatus(TypeOfCompletion.valueOf(status));
            log.debug(editedTask);
            listRes.add(editedTask);
            log.info(CustomLogger.endFunc(listRes));
            insertGenericTaskForDelete(Task.class, listRes, false);
            return new Result<>(Complete);
        } catch (Exception e) {
            log.error(e);
            log.info(CustomLogger.endFunc(e));
            return new Result<>(Fail);
        }

    }

    @Override
    public Result<Double> calculateTaskCost(long id) {
        try {
            log.info(CustomLogger.startFunc());
            Task task = getTaskById(id).getData();
            SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT, Locale.ENGLISH);
            Date firstDate = sdf.parse(String.valueOf(task.getCreatedDate()));
            log.debug(firstDate);
            Date secondDate = sdf.parse(String.valueOf(task.getDeadline()));
            log.debug(secondDate);
            long diffInMillies = Math.abs(secondDate.getTime() - firstDate.getTime());
            log.debug(diffInMillies);
            long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
            log.debug(diff);
            double res=(double) diff * task.getMoney();
            log.debug(res);
            log.info(CustomLogger.endFunc(res));
            return new Result<>(Complete, res);
        } catch (ParseException e) {
            log.error(e);
            log.info(CustomLogger.endFunc(e));
            return new Result<>(Fail);
        }
    }

    @Override
    public Result<Void> writeBaseTaskComment(long id,String comment){
        try {
            log.info(CustomLogger.startFunc());
            if (stringIsValid(comment)) {
                log.info(CustomLogger.endFunc());
                return new Result<>(Fail);
            }
            return writeComment(Task.class, id, comment);
        } catch (Exception e) {
            log.error(e);
            log.info(CustomLogger.endFunc(e));
            return new Result<>(Fail);
        }
    }

    @Override
    public Result<Void> writeDevelopersTaskComment(long id,String comment){
        try {
            log.info(CustomLogger.startFunc());
            if (stringIsValid(comment)) {
                log.info(CustomLogger.endFunc());
                return new Result<>(Fail);
            }
            return writeComment(DevelopersTask.class, id, comment);
        }catch (Exception e) {
            log.error(e);
            log.info(CustomLogger.endFunc(e));
            return new Result<>(Fail);
        }
    }

    @Override
    public Result<Void> writeTestersTaskComment(long id,String comment){
        try {
            log.info(CustomLogger.startFunc());
            if (stringIsValid(comment)) {
                log.info(CustomLogger.endFunc());
                return new Result<>(Fail);
            }
            return writeComment(TestersTask.class,id, comment);
        }catch (Exception e) {
            log.error(e);
            log.info(CustomLogger.endFunc(e));
            return new Result<>(Fail);
        }
    }

    @Override
    public Result<List<Task>> getTaskListByScrumMaster(long id){
        log.info(CustomLogger.startFunc());
        return getTaskListByScrumMaster(Task.class,id);
    }

    @Override
    public Result<List<DevelopersTask>> getDevelopersTaskListByScrumMaster(long id){
        log.info(CustomLogger.startFunc());
        return getTaskListByScrumMaster(DevelopersTask.class,id);
    }

    @Override
    public Result<List<TestersTask>> getTestersTaskListByScrumMaster(long id){
        log.info(CustomLogger.startFunc());
        return getTaskListByScrumMaster(TestersTask.class,id);
    }

    @Override
    public Result<Project> createProject(long id,String title,String takeIntoDevelopment,
                                         List<Task> tasks) {
        try {
            log.info(CustomLogger.startFunc());
            if (stringIsValid(title) || stringIsValid(takeIntoDevelopment)) {
                log.info(CustomLogger.endFunc());
                return new Result<>(Fail);
            }
            log.info(CustomLogger.startFunc());
            Project project=new Project();
            project.setId(id);
            project.setTitle(title);
            project.setTakeIntoDevelopment(takeIntoDevelopment);
            project.setTask(tasks);
            if(listIsValid(tasks)){
                log.info(CustomLogger.endFunc());
                return new Result<>(Fail);
            }
            log.debug(project);
            log.info(CustomLogger.endFunc(project));
            return new Result<>(Complete,project);
        } catch (Exception e) {
            log.error(e);
            log.info(CustomLogger.endFunc(e));
            return new Result<>(Fail);
        }
    }

    @Override
    public Result<List<Project>> getProjectByScrumMasterId(long empId, long projectId) {
        log.info(CustomLogger.startFunc());
        List<Task> listRes = select(Task.class);
        List<Task> findedTaskList = listRes.stream()
                .filter(task -> task.getTeam().stream().anyMatch(employee1 ->
                        employee1.getId() == empId))
                .collect(Collectors.toList());
        log.debug(findedTaskList);
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
        log.debug(optionalProject);
        log.info(CustomLogger.endFunc(optionalProject));
        return optionalProject.isEmpty() ? new Result<>(Outcomes.Empty) :
                new Result<>(Complete,optionalProject);
    }

    @Override
    public Result<Long> calculateProjectCost(long id) {
        try {
            log.info(CustomLogger.startFunc());
            Project project = getProjectByID(id).getData();
            List<Task> taskList = project.getTask();
            double projectCost = 0.0;
            for (Task task: taskList) {
                projectCost = projectCost + (double) calculateTaskCost(task.getId()).getData();
            }
            log.info(projectCost);
            log.info(CustomLogger.endFunc(projectCost));
            return new Result<>(Complete,(long) projectCost);
        } catch (Exception e) {
            log.error(e);
            log.info(CustomLogger.endFunc(e));
            return new Result<>(Fail);
        }

    }

    @Override
    public Result<Long> calculateProjectTime(long id) {
        log.info(CustomLogger.startFunc());
        Project project = getProjectByID(id).getData();
        List<Task> taskList = project.getTask();
        long resulttime = 0;
        for (Task task: taskList) {
            resulttime = resulttime + (long)calculatePrice(task).getData();
        }
        log.debug(resulttime);
        log.info(CustomLogger.endFunc(resulttime));
        return new Result<>(Complete,resulttime);
    }

    @Override
    public Result<Employee> createEmployee(long id,@NonNull String firstName,@NonNull String lastName,
                                           @NonNull String login,@NonNull String password,
                                           @NonNull String email,@NonNull String token,
                                           @NonNull String department,
                                           @NonNull TypeOfEmployee typeOfEmployee){
        log.info(CustomLogger.startFunc());
        switch (typeOfEmployee){
            case EMPLOYEE:
                Employee baseEmployee = (Employee) createBaseEmployee(id,firstName, lastName, login, password, email, token, department).getData();
                log.debug(baseEmployee);
                log.info(CustomLogger.endFunc(baseEmployee));
                return isValidEmployee(baseEmployee) ? new Result<>(Complete,baseEmployee): new Result<>(Fail);
            case Developer:
                Developer developerEmployee = (Developer) createDeveloperEmployee(id,firstName, lastName, login, password, email, token, department).getData();
                log.debug(developerEmployee);
                log.info(CustomLogger.endFunc(developerEmployee));
                return isValidEmployee(developerEmployee) ? new Result<>(Complete,developerEmployee): new Result<>(Fail);
            case Tester:
                Tester testerEmployee = (Tester) createTesterEmployee(id,firstName, lastName, login, password, email, token, department).getData();
                log.debug(testerEmployee);
                log.info(CustomLogger.endFunc(testerEmployee));
                return isValidEmployee(testerEmployee) ? new Result<>(Complete,testerEmployee): new Result<>(Fail);
            default:
                log.info(CustomLogger.endFunc());
                return new Result<>(Fail);
        }
    }

    @Override
    public List<Employee> getAllEmployee(){
        log.info(CustomLogger.startFunc());
        List<Employee> employees = new ArrayList<>();
        employees.addAll(select(Employee.class));
        employees.addAll(select(Tester.class));
        employees.addAll(select(Developer.class));
        log.debug(employees);
        log.info(CustomLogger.endFunc(employees));
        return employees;
    }

    @Override
    public Result<List<Task>> getScrumMasterTaskList(long userId, TaskTypes taskTypes) {
        log.info(CustomLogger.startFunc());
        switch (taskTypes){
            case BASE_TASK:
                log.info(CustomLogger.endFunc());
                return new Result<>(Complete, getTaskListByScrumMaster(Task.class, userId).getData());
            case DEVELOPERS_TASK:
                log.info(CustomLogger.endFunc());
                return new Result<>(Complete,new ArrayList<>(getTaskListByScrumMaster(DevelopersTask.class,
                        userId).getData()));
            case TESTERS_TASK:
                log.info(CustomLogger.endFunc());
                return new Result<>(Complete,new ArrayList<>(getTaskListByScrumMaster(TestersTask.class,
                        userId).getData()));
            default:
                log.info(CustomLogger.endFunc());
                return new Result<>(Fail);
        }
    }

    @Override
    public Result<List<Project>> getProjectListByScrummasterId(long id) {
        try {
            log.info(CustomLogger.startFunc());
            List<Task> listRes = select(Task.class);
            List<Task> findedTaskList = listRes.stream()
                    .filter(el -> el.getScrumMaster().getId()==id)
                    .collect(Collectors.toList());
            log.debug(findedTaskList);
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
            if(listIsValid(optionalProject)){
                log.info(CustomLogger.endFunc());
                return new Result<>(Fail);
            }
            log.debug(optionalProject);
            log.info(CustomLogger.endFunc(optionalProject));
            return new Result<>(Complete,optionalProject);
        } catch (Exception e) {
            log.error(e);
            log.info(CustomLogger.endFunc(e));
            return new Result<>(Fail);
        }
    }

    @Override
    public <T> Result<T> deleteRecord(Class<T> cl) {
        log.info(CustomLogger.startFunc());
        String path = getPath(cl);
        log.debug(path);
        File file = new File(path);
        log.debug(file);
        file.delete();
        log.info(CustomLogger.endFunc());
        return new Result<>(Complete);

    }

    @Override
    public void deleteAllRecord(){
        log.info(CustomLogger.startFunc());
        deleteRecord(Employee.class);
        deleteRecord(Developer.class);
        deleteRecord(Tester.class);
        deleteRecord(Task.class);
        deleteRecord(DevelopersTask.class);
        deleteRecord(TestersTask.class);
        deleteRecord(Project.class);
        log.info(CustomLogger.endFunc());
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
        log.info(CustomLogger.startFunc());
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
                TypeOfEmployee.EMPLOYEE);
        log.debug(employee);
        log.info(CustomLogger.endFunc(employee));
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
        log.info(CustomLogger.startFunc());
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
        log.debug(developer);
        log.info(CustomLogger.endFunc(developer));
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
        log.info(CustomLogger.startFunc());
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
        log.debug(tester);
        log.info(CustomLogger.endFunc(tester));
        return new Result<>(Complete,tester);
    }

    //Optional

    /**
     * @param cl
     * @param <T>
     * @return
     */
    public <T> List<T> select(Class<T> cl) {
        try {
            String path = getPath(cl);
            createFile(path);
            FileReader file = new FileReader(path);
            Serializer serializer = new Persister();
            WrapperXML<T> xml = serializer.read(WrapperXML.class, file);
            List<T> list = xml.getList();
            if (list.size()>0){
                return list;
            }
            file.close();
            return Collections.emptyList();
        } catch (Exception e) {
            log.error(e);
            return Collections.emptyList();
        }
    }

    /**
     * @param cl
     * @return
     */
    private String getPath(Class<?> cl) {
        try {
            String PATH = ConfigurationUtil.getConfigurationEntry(Constants.XML_PATH);
            return PATH + cl.getSimpleName().toLowerCase() + ConfigurationUtil.
                    getConfigurationEntry(Constants.FILE_EXTENSION_XML);
        } catch (IOException e) {
            log.error(e);
            return null;
        }
    }

    /**
     * @param path
     */
    private void createFile(String path)  {
        try {
            String PATH = ConfigurationUtil.getConfigurationEntry(Constants.XML_PATH);
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

    /**
     * @param path
     * @param list
     * @param <T>
     * @return
     */
    public <T> Result<T> writer(String path,List<T> list) {
        try {
            Writer writer = new FileWriter(path);
            Serializer serializer = new Persister();
            WrapperXML<T> xml = new WrapperXML<>(list);
            serializer.write(xml, writer);
            return new Result<>(Complete);
        } catch (Exception e){
            log.error(e);
            return new Result<>(Fail);
        }
    }
    //CRUD

    /**
     * @param cl
     * @param id
     * @param <T>
     * @return
     */
    private <T extends Task> Result<T> getTaskByID(Class<T> cl, long id) {
        try{
            log.info(CustomLogger.startFunc());
            List<T> listRes = select(cl);
            Optional<T> optional = listRes.stream().filter(el -> el.getId() == id).findFirst();
            log.debug(optional);
            log.info(CustomLogger.endFunc(optional));
            return new Result<>(Complete, optional.orElseThrow());
        } catch (NoSuchElementException e) {
            log.error(e);
            log.info(CustomLogger.endFunc(e));
            return new Result<>(Fail);
        }
    }

    /**
     * @param cl
     * @param id
     * @param <T>
     * @return
     */
    private <T extends Employee> Result<T> getEmployeeByID(Class<T> cl, long id) {
        try{
            log.info(CustomLogger.startFunc());
            List<T> listRes = select(cl);
            Optional<T> optional = listRes.stream().filter(el -> el.getId() == id).findFirst();
            log.debug(optional);
            log.info(CustomLogger.endFunc(optional));
            return new Result<>(Complete, optional.orElseThrow());
        } catch (NoSuchElementException e) {
            log.error(e);
            log.info(CustomLogger.endFunc(e));
            return new Result<>(Fail);
        }
    }

    /**
     * @param cl
     * @param element
     * @param <T>
     * @return
     */
    private <T extends Task> Result<Void> insertGenericTask(Class<T> cl,@NonNull T element) {
        try{
            log.info(CustomLogger.startFunc());
            String path = getPath(cl);
            log.debug(path);
            createFile(path);
            List<T> oldList = this.select(cl);
            if (oldList != null) {
                long id = element.getId();
                if (oldList.stream().anyMatch(el -> el.getId() == id)) {
                    log.info(CustomLogger.endFunc());
                    return new Result<>(Fail);
                }
                oldList.add(element);
            }
            log.debug(oldList);
            writer(path,oldList);
            log.info(CustomLogger.endFunc());
            return new Result(Complete);
        } catch (IndexOutOfBoundsException e) {
            log.error(e);
            log.info(CustomLogger.endFunc(e));
            return new Result<>(Fail);
        }

    }

    /**
     * @param cl
     * @param <T>
     * @return
     */
    private <T extends Employee> Result<Void> insertGenericEmployee(Class<T> cl,
                                                                    @NonNull T element) {
        try{
            log.info(CustomLogger.startFunc());
            String path = getPath(cl);
            log.debug(path);
            createFile(path);
            List<T> oldList = this.select(cl);
            if (oldList != null) {
                long id = element.getId();
                if (oldList.stream().anyMatch(el -> el.getId() == id)) {
                    log.debug(Outcomes.Empty);
                    return new Result<>(Fail);
                }
                oldList.add(element);
            }
            log.debug(oldList);
            writer(path,oldList);
            log.info(CustomLogger.endFunc());
            return new Result(Complete);
        } catch (IndexOutOfBoundsException e) {
            log.error(e);
            log.info(CustomLogger.endFunc(e));
            return new Result<>(Fail);
        }

    }

    /**
     * @param cl
     * @param list
     * @param append
     * @param <T>
     * @return
     */
    public <T extends Employee> Result<Void> insertGenericEmployeeForDelete(Class<T> cl,
                                                                             @NonNull List<T> list,
                                                                             boolean append) {
        try{
            log.info(CustomLogger.startFunc());
            String path = getPath(cl);
            log.debug(path);
            createFile(path);
            List<T> oldList = this.select(cl);
            if (append){
                if (oldList != null && oldList.size() > 0) {
                    long id = list.get(0).getId();
                    if (oldList.stream().anyMatch(el -> el.getId() == id)) {
                        log.debug(Outcomes.Empty);
                        return new Result<>(Fail);
                    }
                    list = Stream
                            .concat(list.stream(), oldList.stream())
                            .collect(Collectors.toList());
                }
            }
            log.debug(list);
            writer(path,list);
            log.info(CustomLogger.endFunc());
            return new Result(Complete);
        } catch (IndexOutOfBoundsException e) {
            log.error(e);
            log.info(CustomLogger.endFunc(e));
            return new Result<>(Fail);
        }

    }

    /**
     * @param cl
     * @param list
     * @param append
     * @param <T>
     * @return
     */
    public <T extends Task> Result<Void> insertGenericTaskForDelete(Class<T> cl,
                                                                     @NonNull List<T> list,
                                                                     boolean append) {
        try{
            log.info(CustomLogger.startFunc());
            String path = getPath(cl);
            log.debug(path);
            createFile(path);
            List<T> oldList = this.select(cl);
            if (append) {
                if (oldList != null && oldList.size() > 0) {
                    long id = list.get(0).getId();
                    if (oldList.stream().anyMatch(el -> el.getId() == id)) {
                        log.debug(Empty);
                        return new Result<>(Fail);
                    }
                    list = Stream
                            .concat(list.stream(), oldList.stream())
                            .collect(Collectors.toList());
                }
            }
            log.debug(list);
            writer(path,list);
            log.info(CustomLogger.endFunc());
            return new Result(Complete);
        } catch (IndexOutOfBoundsException e) {
            log.error(e);
            log.info(CustomLogger.endFunc(e));
            return new Result<>(Fail);
        }

    }

    /**
     * @param cl
     * @param id
     * @param <T>
     * @return
     */
    private <T extends Task> Result<Void> deleteGenericTask(Class<T> cl, long id) {
        try {
            log.info(CustomLogger.startFunc());
            List<T> listData = select(cl);
            listData = listData.stream().filter(el -> el.getId() != id).collect(Collectors.toList());
            if(listIsValid(listData)){
                log.info(CustomLogger.endFunc());
                return new Result<>(Fail);
            }
            log.debug(listData);
            log.info(CustomLogger.endFunc(listData));
            insertGenericTaskForDelete(cl, listData, false);
            return new Result<>(Complete);
        } catch (Exception e) {
            log.error(e);
            log.info(CustomLogger.endFunc(e));
            return new Result<>(Fail);
        }

    }

    /**
     * @param cl
     * @param id
     * @param <T>
     * @return
     */
    private <T extends Employee> Result<Void> deleteGenericEmployee(Class<T> cl, long id) {
        log.info(CustomLogger.startFunc());
        List<T> listData = select(cl);
        listData = listData.stream().filter(el -> el.getId() != id).collect(Collectors.toList());
        log.debug(listData);
        insertGenericEmployeeForDelete(cl, listData, false);
        log.info(CustomLogger.endFunc());
        return new Result<>(Complete);
    }

    /**
     * @param cl
     * @param updElement
     * @param <T>
     * @return
     */
    private <T extends Task> Result<Void> updateGenericTask(Class<T> cl, T updElement){
        try{
            log.info(CustomLogger.startFunc());
            if(!isValidTask(updElement)){
                return new Result<>(Fail);
            }
            List<T> userList = select(cl);
            log.debug(userList);
            Optional<T> optionalUser = searchTask(userList,updElement.getId());
            log.debug(optionalUser);
            if(optionalIsValid(optionalUser)){
                log.info(CustomLogger.endFunc());
                return new Result<>(Fail);
            }
            log.debug(optionalUser);
            userList.remove(optionalUser.get());
            userList.add(updElement);
            insertGenericTaskForDelete(cl, userList, false);
            return new Result<>(Complete);
        } catch (Exception e) {
            log.error(e);
            return new Result<>(Fail);
        }
    }

    /**
     * @param cl
     * @param updElement
     * @param <T>
     * @return
     */
    private <T extends Employee> Result<Void> updateGenericEmployee(Class<T> cl, T updElement)  {
        try{
            log.info(CustomLogger.startFunc());
            if(!isValidEmployee(updElement)){
                log.info(CustomLogger.endFunc());
                return new Result<>(Fail);
            }
            List<T> userList = select(cl);
            Optional<T> optionalUser = searchEmployee(userList,updElement.getId());
            if(optionalIsValid(optionalUser)){
                log.info(CustomLogger.endFunc());
                return new Result<>(Fail);
            }
            userList.remove(optionalUser.get());
            userList.add(updElement);
            log.debug(userList);
            log.info(CustomLogger.endFunc(userList));
            insertGenericEmployeeForDelete(cl, userList, false);
            return new Result<>(Complete);
        } catch (Exception e) {
            log.error(e);
            log.info(CustomLogger.endFunc(e));
            return new Result<>(Fail);
        }
    }

    /**
     * @param listRes
     * @param id
     * @param <T>
     * @return
     */
    private <T extends Task> Optional<T> searchTask(List<T> listRes,long id) {
        log.info(CustomLogger.startFunc());
        return listRes.stream()
                .filter(el -> el.getId() == id)
                .findFirst();

    }

    /**
     * @param listRes
     * @param id
     * @param <T>
     * @return
     */
    private <T extends Employee> Optional<T> searchEmployee(List<T> listRes,long id) {
        log.info(CustomLogger.startFunc());
        return listRes.stream()
                .filter(el -> el.getId() == id)
                .findFirst();
    }

    /**
     * @param listRes
     * @param id
     * @return
     */
    private Optional<Project> searchProject(List<Project> listRes,long id) {
        log.info(CustomLogger.startFunc());
        return listRes.stream()
                .filter(el -> el.getId() == id)
                .findFirst();
    }

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
        log.info(CustomLogger.startFunc());
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
        if(!isValidTask(task)){
            log.info(CustomLogger.endFunc());
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
    private Result<Task> createBaseTask(long id,@NonNull String taskDescription,@NonNull Double money,
                                        @NonNull Employee scrumMaster,@NonNull TypeOfCompletion status,
                                        @NonNull List<Employee> team,@NonNull String createdDate,
                                        @NonNull String deadline,@NonNull String lastUpdate) {
        log.info(CustomLogger.startFunc());
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
        log.debug(task);
        log.info(CustomLogger.endFunc());
        return isValidTask(task)? new Result<>(Complete,task) : new Result<>(Fail);
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
    private Result<DevelopersTask> createDevelopersTask(long id,
                                                        @NonNull String taskDescription,
                                                        @NonNull Double money,
                                                        @NonNull Employee scrumMaster,
                                                        @NonNull TypeOfCompletion status,
                                                        @NonNull List<Employee> team,
                                                        @NonNull String createdDate,
                                                        @NonNull String deadline,
                                                        @NonNull String lastUpdate) {
        log.info(CustomLogger.startFunc());
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
        log.debug(developersTask);
        log.info(CustomLogger.endFunc());
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
    private Result<TestersTask> createTestersTask(long id,
                                                  @NonNull String taskDescription,
                                                  @NonNull Double money,
                                                  @NonNull Employee scrumMaster,
                                                  @NonNull TypeOfCompletion status,
                                                  @NonNull List<Employee> team,
                                                  @NonNull String createdDate,
                                                  @NonNull String deadline,
                                                  @NonNull String lastUpdate) {
        log.info(CustomLogger.startFunc());
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
        log.debug(testersTask);
        log.info(CustomLogger.endFunc());
        return new Result<>(Complete,testersTask);
    }

    /**
     * @param cl
     * @param id
     * @param comment
     * @param <T>
     * @return
     */
    private <T extends Task> Result<Void> writeComment(Class<T> cl, long id, String comment) {
        try {
            log.info(CustomLogger.startFunc());
            if (stringIsValid(comment)) {
                log.info(CustomLogger.endFunc());
                return new Result<>(Fail);
            }
            List<T> listRes = select(cl);
            Optional<T> optionalTask = searchTask(listRes, id);
            if(optionalIsValid(optionalTask)){
                log.info(CustomLogger.endFunc());
                return new Result<>(Fail);
            }
            T editedTask = (T) optionalTask.get();
            log.debug(editedTask);
            listRes.remove(optionalTask.get());
            editedTask.setTaskDescription(comment);
            listRes.add(editedTask);
            log.info(CustomLogger.endFunc(listRes));
            insertGenericTaskForDelete(cl, listRes, false);
            return new Result<>(Complete);
        } catch (Exception e) {
            log.error(e);
            log.info(CustomLogger.endFunc(e));
            return new Result<>(Fail);
        }
    }

    /**
     * @param cl
     * @param userId
     * @param <T>
     * @return
     */
    private <T extends Task> Result<List<T>> getTaskListByScrumMaster(Class<T> cl, long userId){
        try {
            log.info(CustomLogger.startFunc());
            List<T> listRes = select(cl);
            List<T> optionalRes = listRes.stream()
                    .filter(el -> el.getScrumMaster().getId() == userId)
                    .collect(Collectors.toList());
            log.debug(listRes);
            if(listIsValid(optionalRes)){
                log.info(CustomLogger.endFunc());
                return new Result<>(Fail);
            }
            log.info(CustomLogger.endFunc(optionalRes));
            return new Result<>(Complete, optionalRes);
        } catch (Exception e) {
            log.error(e);
            log.info(CustomLogger.endFunc(e));
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
        log.info(CustomLogger.startFunc());
        employee.setId(id);
        log.debug(employee);
        employee.setFirstName(firstName);
        log.debug(employee);
        employee.setLastName(lastName);
        log.debug(employee);
        employee.setLogin(login);
        log.debug(employee);
        employee.setPassword(password);
        log.debug(employee);
        employee.setEmail(email);
        log.debug(employee);
        employee.setToken(token);
        log.debug(employee);
        employee.setDepartment(department);
        log.debug(employee);
        employee.setTypeOfEmployee(typeOfEmployee);
        log.debug(employee);
        log.info(CustomLogger.endFunc(employee));
    }

    /**
     * @param task
     * @return
     */
    private Result<Long> calculatePrice(Task task) {
        log.info(CustomLogger.startFunc());
        try {
            if(!isValidTask(task)){
                return new Result<>(Fail);
            }
            long resulttime = 0;
            SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT, Locale.ENGLISH);
            Date firstDate = sdf.parse(String.valueOf(task.getCreatedDate()));
            log.debug(firstDate);
            Date secondDate = sdf.parse(String.valueOf(task.getDeadline()));
            log.debug(secondDate);
            long diffInMillies = Math.abs(secondDate.getTime() - firstDate.getTime());
            log.debug(diffInMillies);
            long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
            resulttime = resulttime + diff;
            log.debug(resulttime);
            log.info(CustomLogger.endFunc(resulttime));
            return new Result<>(Complete,resulttime);
        } catch (ParseException e) {
            log.error(e);
            log.info(CustomLogger.endFunc(e));
            return new Result<>(Fail);
        }
    }

    /**
     * @param list
     */
    private boolean listIsValid(List<?> list) {
        return list.isEmpty();
    }

    /**
     * @param optional
     */
    private boolean optionalIsValid(Optional<?> optional){
        return optional.isEmpty();
    }

    /**
     * @param str
     * @return
     */
    private boolean stringIsValid(String str){
        return str.isEmpty();
    }

    /**
     * @param task
     */
    public boolean isValidTask(Task task) {
        return task.getTaskDescription() != null
                && !task.getTaskDescription().isEmpty()
                && !task.getMoney().isNaN()
                && !task.getStatus().toString().isEmpty()
                && task.getStatus().toString()!=null
                && !task.getCreatedDate().isEmpty()
                && task.getCreatedDate() != null
                && !task.getDeadline().isEmpty()
                && task.getDeadline() != null
                && !task.getLastUpdate().isEmpty()
                && task.getLastUpdate() != null
                && !task.getTaskType().toString().isEmpty()
                && task.getTaskType().toString()!=null;
    }

    /**
     * @param project
     * @return
     */
    public boolean isValidProject(Project project){
        return  !project.getTitle().isEmpty()
                && project.getTitle() != null
                && !project.getTakeIntoDevelopment().isEmpty()
                && project.getTakeIntoDevelopment() != null;
    }

    /**
     * @param employee
     */
    private boolean isValidEmployee(Employee employee) {
        return employee.getFirstName() != null
                && !employee.getFirstName().isEmpty()
                && employee.getLastName() != null
                && !employee.getLastName().isEmpty()
                && employee.getLogin() != null
                && !employee.getLogin().isEmpty()
                && employee.getPassword() != null
                && !employee.getPassword().isEmpty()
                && employee.getEmail() != null
                && !employee.getEmail().isEmpty()
                && employee.getToken() != null
                && !employee.getToken().isEmpty()
                && employee.getDepartment() != null
                && !employee.getDepartment().isEmpty()
                && employee.getTypeOfEmployee() != null;
    }

    /**
     * @param list
     * @param append
     * @return
     */
    public Result<Project> insertProjectForDelete(List<Project> list, boolean append) {
        try{
            log.info(CustomLogger.startFunc());
            String path = getPath(Project.class);
            log.debug(path);
            createFile(path);
            List<Project> oldList = this.select(Project.class);
            if(append){
                if (oldList != null) {
                    long id = list.get(0).getId();
                    if (oldList.stream().anyMatch(el -> el.getId() == id)) {
                        log.debug(Outcomes.Empty);
                        return new Result<>(Fail);
                    }
                    list = Stream
                            .concat(list.stream(), oldList.stream())
                            .collect(Collectors.toList());
                }
            }
            log.debug(list);
            log.info(CustomLogger.endFunc(list));
            writer(path,list);
            return new Result<>(Complete);
        } catch (IndexOutOfBoundsException e) {
            log.error(e);
            log.info(CustomLogger.endFunc(e));
            return new Result<>(Fail);
        }

    }
}
