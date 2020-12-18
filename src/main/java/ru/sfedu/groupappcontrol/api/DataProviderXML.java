package ru.sfedu.groupappcontrol.api;
import lombok.NonNull;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import ru.sfedu.groupappcontrol.Result;
import ru.sfedu.groupappcontrol.models.*;
import ru.sfedu.groupappcontrol.Constants;
import ru.sfedu.groupappcontrol.utils.WrapperXML;
import ru.sfedu.groupappcontrol.models.enums.*;
import ru.sfedu.groupappcontrol.utils.ConfigurationUtil;

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

public class DataProviderXML implements DataProvider {

    private static final Logger log = LogManager.getLogger(DataProviderXML.class);
    @Override
    public void initDataSource() {

    }

    @Override
    public Result<Task> getTaskById(long id){
        log.info(String.format(Constants.logInfo,getTaskByID(Task.class,id)
                .getStatus().toString()));
        return getTaskByID(Task.class,id);
    }
    @Override
    public Result<DevelopersTask> getDevelopersTaskById(long id){
        log.info(String.format(Constants.logInfo,getTaskByID(DevelopersTask.class,id)
                .getStatus().toString()));
        return getTaskByID(DevelopersTask.class,id);
    }
    @Override
    public Result<TestersTask> getTestersTaskById(long id){
        log.info(String.format(Constants.logInfo,getTaskByID(TestersTask.class,id)
                .getStatus().toString()));
        return getTaskByID(TestersTask.class,id);
    }
    @Override
    public Result<Employee> getEmployeeById(long id){
        log.info(String.format(Constants.logInfo,getEmployeeByID(Employee.class,id)
                .getStatus().toString()));
        return getEmployeeByID(Employee.class,id);
    }
    @Override
    public Result<Developer> getDeveloperById(long id){
        log.info(String.format(Constants.logInfo,getEmployeeByID(Developer.class,id)
                .getStatus().toString()));
        return getEmployeeByID(Developer.class,id);
    }
    @Override
    public Result<Tester> getTesterById(long id){
        log.info(String.format(Constants.logInfo,getEmployeeByID(Tester.class,id)
                .getStatus().toString()));
        return getEmployeeByID(Tester.class,id);
    }
    @Override
    public Result<Project> getProjectByID( long id){
        try{
            List<Project> listRes = select(Project.class);
            Optional<Project> optional = listRes.stream().filter(el -> el.getId() == id).findFirst();
            log.debug(optional);
            log.info(optional);
            return new Result<>(Complete, optional.orElseThrow());
        } catch (NoSuchElementException e) {
            log.error(e);
            return new Result<>(Fail);
        }
    }
    @Override
    public Result<Void> insertTask(List<Task> list,boolean append){
        log.info(String.format(Constants.logInfo,insertGenericTask(Task.class,list,append)
                .getStatus().toString()));
        return insertGenericTask(Task.class,list,append);
    }
    @Override
    public Result<Void> insertDevelopersTask(List<DevelopersTask> list,boolean append){
        log.info(String.format(Constants.logInfo,insertGenericTask(DevelopersTask.class,list,append)
                .getStatus().toString()));
        return insertGenericTask(DevelopersTask.class,list,append);
    }
    @Override
    public Result<Void> insertTestersTask(List<TestersTask> list,boolean append){
        log.info(String.format(Constants.logInfo,insertGenericTask(TestersTask.class,list,append)
                .getStatus().toString()));
        return insertGenericTask(TestersTask.class,list,append);
    }
    @Override
    public Result<Void> insertEmployee(List<Employee> list,boolean append){
        log.info(String.format(Constants.logInfo,insertGenericEmployee(Employee.class,list,append)
                .getStatus().toString()));
        return insertGenericEmployee(Employee.class,list,append);
    }
    @Override
    public Result<Void> insertDeveloper(List<Developer> list,boolean append){
        log.info(String.format(Constants.logInfo,insertGenericEmployee(Developer.class,list,append)
                .getStatus().toString()));
        return insertGenericEmployee(Developer.class,list,append);
    }
    @Override
    public Result<Void> insertTester(List<Tester> list,boolean append){
        log.info(String.format(Constants.logInfo,insertGenericEmployee(Tester.class,list,append)
                .getStatus().toString()));
        return insertGenericEmployee(Tester.class,list,append);
    }
    @Override
    public Result<Project> insertProject(List<Project> list, boolean append) {
        try{
            String path = getPath(Project.class);
            createFile(path);
            List<Project> oldList = this.select(Project.class);
            if(append){
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
            return new Result<>(Complete);
        } catch (IndexOutOfBoundsException e) {
            log.error(e);
            return new Result<>(Fail);
        }

    }
    @Override
    public Result<Void> deleteTask(long id){
        log.info(String.format(Constants.logInfo,deleteGenericTask(Task.class,id)
                .getStatus().toString()));
        return deleteGenericTask(Task.class,id);
    }
    @Override
    public Result<Void> deleteDevelopersTask(long id){
        log.info(String.format(Constants.logInfo,deleteGenericTask(DevelopersTask.class,id)
                .getStatus().toString()));
        return deleteGenericTask(DevelopersTask.class,id);
    }
    @Override
    public Result<Void> deleteTestersTask(long id){
        log.info(String.format(Constants.logInfo,deleteGenericTask(TestersTask.class,id)
                .getStatus().toString()));
        return deleteGenericTask(TestersTask.class,id);
    }
    @Override
    public Result<Void> deleteEmployee(long id){
        log.info(String.format(Constants.logInfo,deleteGenericEmployee(Employee.class,id)
                .getStatus().toString()));
        return deleteGenericEmployee(Employee.class,id);
    }
    @Override
    public Result<Void> deleteDeveloper(long id){
        log.info(String.format(Constants.logInfo,deleteGenericEmployee(Developer.class,id)
                .getStatus().toString()));
        return deleteGenericEmployee(Developer.class,id);
    }
    @Override
    public Result<Void> deleteTester(long id){
        log.info(String.format(Constants.logInfo,deleteGenericEmployee(Tester.class,id)
                .getStatus().toString()));
        return deleteGenericEmployee(Tester.class,id);
    }
    @Override
    public Result<Void> deleteProject(long id){
        List<Project> listData = select(Project.class);
        listData = listData.stream().filter(el -> el.getId() != id).collect(Collectors.toList());
        log.debug(listData);
        insertProject(listData, false);
        return new Result<>(Complete);
    }
    @Override
    public Result<Void> updateTask(Task task){
        log.info(String.format(Constants.logInfo,updateGenericTask(Task.class,task)
                .getStatus().toString()));
        return updateGenericTask(Task.class,task);
    }
    @Override
    public Result<Void> updateDevelopersTask(DevelopersTask developersTask){
        log.info(String.format(Constants.logInfo,updateGenericTask(DevelopersTask.class, developersTask)
                .getStatus().toString()));
        return updateGenericTask(DevelopersTask.class, developersTask);
    }
    @Override
    public Result<Void> updateTestersTask(TestersTask testersTask){
        log.info(String.format(Constants.logInfo,updateGenericTask(TestersTask.class,testersTask)
                .getStatus().toString()));
        return updateGenericTask(TestersTask.class,testersTask);
    }
    @Override
    public Result<Void> updateEmployee(Employee employee){
        log.info(String.format(Constants.logInfo,updateGenericEmployee(Employee.class,employee)
                .getStatus().toString()));
        return updateGenericEmployee(Employee.class,employee);
    }
    @Override
    public Result<Void> updateDeveloper(Developer developer){
        log.info(String.format(Constants.logInfo,updateGenericEmployee(Developer.class, developer)
                .getStatus().toString()));
        return updateGenericEmployee(Developer.class, developer);
    }
    @Override
    public Result<Void> updateTester(Tester tester){
        log.info(String.format(Constants.logInfo,updateGenericEmployee(Tester.class,tester)
                .getStatus().toString()));
        return updateGenericEmployee(Tester.class,tester);
    }
    @Override
    public Result<Project> updateProject(Project project) {
        try {
            List<Project> userList = select(Project.class);
            Optional<Project> optionalUser = searchProject(userList,project.getId());
            optionalIsValid(optionalUser);
            log.debug(optionalUser);
            userList.remove(optionalUser.get());
            userList.add(project);
            log.debug(userList);
            insertProject(userList, false);
            return new Result<>(Complete);
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
    @Override
    public Result<Task> getTasks(long id){
        List<Task> taskList = new ArrayList<>();
        taskList.addAll(select(Task.class));
        taskList.addAll(select(TestersTask.class));
        taskList.addAll(select(DevelopersTask.class));
        log.debug(taskList);
        Optional<Task> optTask = taskList.stream().filter(el -> el.getId() == id).findAny();
        log.info(optTask);
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
    public Result<Task> getTasksByUser(long userId, long taskId) {
        try {
            List<Task> list = select(Task.class);
            Task task = searchTask(list, taskId).get();
            log.debug(task);
            listIsValid(task.getTeam());
            log.info(task);
            return task.getTeam().stream().anyMatch(employee -> employee.getId() == userId)
                    ? new Result<>(Complete, task) : new Result<>(Fail);
        } catch (Exception e) {
            log.error(e);
            return new Result<>(Fail);
        }
    }
    @Override
    public Result<List<Task>> getTaskWorker(Employee employee, long taskId) {
        try {
            List<Task> listRes = select(Task.class);
            List<Task> taskList = listRes.stream()
                    .filter(task -> task.getId() == taskId)
                    .filter(task -> task.getTeam().stream().anyMatch(employee1 ->
                            employee1.getId() == employee.getId()))
                    .collect(Collectors.toList());
            listIsValid(taskList);
            log.debug(taskList);
            log.info(taskList);
            return new Result<>(Complete, taskList);
        } catch (Exception e) {
            log.error(e);
            return new Result<>(Fail);
        }
    }
    @Override
    public Result<Void> changeTaskStatus(long id, String status) {
        try {
            stringIsValid(status);
            List<Task> listRes = select(Task.class);
            Optional<Task> optionalTask = searchTask(listRes, id);
            optionalIsValid(optionalTask);
            log.debug(optionalTask);
            Task editedTask = optionalTask.get();
            listRes.remove(optionalTask.get());
            editedTask.setStatus(TypeOfCompletion.valueOf(status));
            log.debug(editedTask);
            listRes.add(editedTask);
            log.info(listRes);
            insertGenericTask(Task.class, listRes, false);
            return new Result<>(Complete);
        } catch (Exception e) {
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
            log.debug(diffInMillies);
            long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
            log.debug(diff);
            double res=(double) diff * task.getMoney();
            log.debug(res);
            log.info(res);
            return new Result<>(Complete, res);
        } catch (ParseException e) {
            log.error(e);
            return new Result<>(Fail);
        }
    }
    @Override
    public Result<Void> writeBaseTaskComment(long id,String comment){
        log.info(String.format(Constants.logInfo,writeComment(Task.class,id,comment)
                .getStatus().toString()));
        return writeComment(Task.class,id,comment);
    }
    @Override
    public Result<Void> writeDevelopersTaskComment(long id,String comment){
        log.info(String.format(Constants.logInfo,writeComment(DevelopersTask.class,id, comment)
                .getStatus().toString()));
        return writeComment(DevelopersTask.class,id, comment);
    }
    @Override
    public Result<Void> writeTestersTaskComment(long id,String comment){
        log.info(String.format(Constants.logInfo,writeComment(TestersTask.class,id, comment)
                .getStatus().toString()));
        return writeComment(TestersTask.class,id, comment);
    }
    @Override
    public Result<List<Task>> getTaskListByScrumMaster(long id){
        log.info(String.format(Constants.logInfo,getTaskListByScrumMaster(Task.class,id)
                .getStatus().toString()));
        return getTaskListByScrumMaster(Task.class,id);
    }
    @Override
    public Result<List<DevelopersTask>> getDevelopersTaskListByScrumMaster(long id){
        log.info(String.format(Constants.logInfo,getTaskListByScrumMaster(DevelopersTask.class,id)
                .getStatus().toString()));
        return getTaskListByScrumMaster(DevelopersTask.class,id);
    }
    @Override
    public Result<List<TestersTask>> getTestersTaskListByScrumMaster(long id){
        log.info(String.format(Constants.logInfo,getTaskListByScrumMaster(TestersTask.class,id)
                .getStatus().toString()));
        return getTaskListByScrumMaster(TestersTask.class,id);
    }
    @Override
    public Result<Project> createProject(long id,String title,String takeIntoDevelopment,
                                         List<Task> tasks) {
        try {
            Project project=new Project();
            project.setId(id);
            project.setTitle(title);
            project.setTakeIntoDevelopment(takeIntoDevelopment);
            project.setTask(tasks);
            stringIsValid(title);
            stringIsValid(takeIntoDevelopment);
            listIsValid(tasks);
            log.debug(project);
            log.info(project);
            return new Result<>(Complete,project);
        } catch (Exception e) {
            log.error(e);
            return new Result<>(Fail);
        }
    }
    @Override
    public Result<Project> getProjectByProjectID(long projectId) {
        try {
            List<Project> listPrjRes = select(Project.class);
            Optional<Project> optionalProject = searchProject(listPrjRes, projectId);
            optionalIsValid(optionalProject);
            log.debug(optionalProject);
            Project findedProject = optionalProject.get();
            log.info(findedProject);
            return new Result<>(Complete, findedProject);
        } catch (Exception e) {
            log.error(e);
            return new Result<>(Fail);
        }
    }
    @Override
    public Result<List<Project>> getProjectById(Employee employee, long projectId) {
        List<Task> listRes = select(Task.class);
        List<Task> findedTaskList = listRes.stream()
                .filter(task -> task.getTeam().stream().anyMatch(employee1 ->
                        employee1.getId() == employee.getId()))
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
        log.info(optionalProject);
        return optionalProject.isEmpty() ? new Result<>(Outcomes.Empty) :
                new Result<>(Complete,optionalProject);
    }
    @Override
    public Result<Long> calculateProjectCost(Project project) {
        try {
            stringIsValid(project.getTakeIntoDevelopment());
            List<Task> taskList = project.getTask();
            double projectCost = 0.0;
            for (Task task: taskList) {
                projectCost = projectCost + (double) calculateTaskCost(task).getData();
            }
            log.info(projectCost);
            return new Result<>(Complete,(long) projectCost);
        } catch (Exception e) {
            log.error(e);
            return new Result<>(Fail);
        }

    }
    @Override
    public Result<Long> calculateProjectTime(Project project) {
        List<Task> taskList = project.getTask();
        long resulttime = 0;
        for (Task task: taskList) {
            resulttime = resulttime + (long)calculatePrice(task).getData();
        }
        log.info(resulttime);
        return new Result<>(Complete,resulttime);
    }
    @Override
    public Result<Employee> createEmployee(long id,@NonNull String firstName,@NonNull String lastName,
                                           @NonNull String login,@NonNull String password,
                                           @NonNull String email,@NonNull String token,
                                           @NonNull String department,@NonNull TypeOfEmployee typeOfEmployee){
        switch (typeOfEmployee){
            case Employee:
                Employee baseEmployee = (Employee) createBaseEmployee(id,firstName, lastName, login, password, email, token, department).getData();
                return isValidEmployee(baseEmployee) ? new Result<>(Complete,baseEmployee): new Result<>(Fail);
            case Developer:
                Developer developerEmployee = (Developer) createDeveloperEmployee(id,firstName, lastName, login, password, email, token, department).getData();
                return isValidEmployee(developerEmployee) ? new Result<>(Complete,developerEmployee): new Result<>(Fail);
            case Tester:
                Tester testerEmployee = (Tester) createTesterEmployee(id,firstName, lastName, login, password, email, token, department).getData();
                return isValidEmployee(testerEmployee) ? new Result<>(Complete,testerEmployee): new Result<>(Fail);
            default:
                return new Result<>(Fail);
        }
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
        log.info(employee);
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
        log.info(developer);
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
        log.info(tester);
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
    public Result<List<Project>> getProjectListByScrummasterId(long id) {
        try {
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
            listIsValid(optionalProject);
            log.debug(optionalProject);
            log.info(optionalProject);
            return new Result<>(Complete,optionalProject);
        } catch (Exception e) {
            log.error(e);
            return new Result<>(Fail);
        }
    }

    @Override
    public Result<Void> deleteEmployeeFromTask(Task task, Employee employee) {
        List<Employee> employeeList = task.getTeam();
        log.debug(employeeList);
        employeeList.removeIf(employee1 -> employee1.getId() == employee.getId());
        task.setTeam(employeeList);
        log.info(task);
        return new Result<>(Complete);
    }

    @Override
    public Result<Employee> changeProfileInfo(Employee editedEmployee) {
        try {
            List<Employee> listRes = select(Employee.class);
            Optional<Employee> optionalUser = searchEmployee(listRes, editedEmployee.getId());
            optionalIsValid(optionalUser);
            listRes.remove(optionalUser.get());
            listRes.add(editedEmployee);
            log.debug(listRes);
            insertGenericEmployee(Employee.class, listRes, false);
            log.info(String.format(Constants.logInfo,insertGenericEmployee(Employee.class, listRes, false)
                    .getStatus().toString()));
            return new Result<>(Complete);
        } catch (Exception e) {
            log.error(e);
            return new Result<>(Fail);
        }
    }

    @Override
    public Result<Employee> correctEmployeeParameters(Employee editedEmployee) {
        updateGenericEmployee(Employee.class, editedEmployee);
        log.info(String.format(Constants.logInfo,updateGenericEmployee(Employee.class, editedEmployee)
                .getStatus().toString()));
        return new Result<>(Complete);
    }

    @Override
    public Result<Employee> addEmployeeToTask(Task task, Employee employee) {
        List<Employee> employeeList = task.getTeam();
        employeeList.addAll(Collections.singleton(employee));
        log.debug(employeeList);
        task.setTeam(employeeList);
        log.info(task);
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
    public <T> List<T> select(Class<T> cl) {
        try {
            String path = getPath(cl);
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
     * @param cl
     * @return
     * @throws IOException
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
     * @throws IOException
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
            List<T> listRes = select(cl);
            Optional<T> optional = listRes.stream().filter(el -> el.getId() == id).findFirst();
            log.debug(optional);
            return new Result<>(Complete, optional.orElseThrow());
        } catch (NoSuchElementException e) {
            log.error(e);
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
            List<T> listRes = select(cl);
            Optional<T> optional = listRes.stream().filter(el -> el.getId() == id).findFirst();
            log.debug(optional);
            return new Result<>(Complete, optional.orElseThrow());
        } catch (NoSuchElementException e) {
            log.error(e);
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
    private <T extends Task> Result<Void> insertGenericTask(Class<T> cl,@NonNull List<T> list, boolean append) {
        try{
            String path = getPath(cl);
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
            return new Result(Complete);
        } catch (IndexOutOfBoundsException e) {
            log.error(e);
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
    private <T extends Employee> Result<Void> insertGenericEmployee(Class<T> cl,
                                                                    @NonNull List<T> list, boolean append) {
        try{
            String path = getPath(cl);
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
            return new Result(Complete);
        } catch (IndexOutOfBoundsException e) {
            log.error(e);
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
            List<T> listData = select(cl);
            listData = listData.stream().filter(el -> el.getId() != id).collect(Collectors.toList());
            listIsValid(listData);
            log.debug(listData);
            insertGenericTask(cl, listData, false);
            return new Result<>(Complete);
        } catch (Exception e) {
            log.error(e);
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
        List<T> listData = select(cl);
        listData = listData.stream().filter(el -> el.getId() != id).collect(Collectors.toList());
        log.debug(listData);
        insertGenericEmployee(cl, listData, false);
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
            List<T> userList = select(cl);
            Optional<T> optionalUser = searchTask(userList,updElement.getId());
            optionalIsValid(optionalUser);
            log.debug(optionalUser);
            userList.remove(optionalUser.get());
            userList.add(updElement);
            insertGenericTask(cl, userList, false);
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
        try {
            List<T> userList = select(cl);
            Optional<T> optionalUser = searchEmployee(userList,updElement.getId());
            optionalIsValid(optionalUser);
            log.debug(optionalUser);
            userList.remove(optionalUser.get());
            userList.add(updElement);
            insertGenericEmployee(cl, userList, false);
            return new Result<>(Complete);
        } catch (Exception e) {
            log.error(e);
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
        isValidTask(task);
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
    private Result<DevelopersTask> createDevelopersTask(long id,@NonNull String taskDescription,@NonNull Double money,@NonNull Employee scrumMaster,@NonNull TypeOfCompletion status,@NonNull List<Employee> team,@NonNull String createdDate,@NonNull String deadline,@NonNull String lastUpdate) {
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
    private Result<TestersTask> createTestersTask(long id,@NonNull String taskDescription,@NonNull Double money,
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
        log.debug(testersTask);
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
            stringIsValid(comment);
            List<T> listRes = select(cl);
            Optional<T> optionalTask = searchTask(listRes, id);
            optionalIsValid(optionalTask);
            T editedTask = (T) optionalTask.get();
            log.debug(editedTask);
            listRes.remove(optionalTask.get());
            editedTask.setTaskDescription(comment);
            listRes.add(editedTask);
            insertGenericTask(cl, listRes, false);
            return new Result<>(Complete);
        } catch (Exception e) {
            log.error(e);
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
            List<T> listRes = select(cl);
            List<T> optionalRes = listRes.stream()
                    .filter(el -> el.getScrumMaster().getId() == userId)
                    .collect(Collectors.toList());
            listIsValid(optionalRes);
            log.debug(optionalRes);
            return new Result<>(Complete, optionalRes);
        } catch (Exception e) {
            log.error(e);
            return new Result<>(Fail);
        }


    }

    /**
     * @param userId
     * @return
     */
    private Result<Project> getProjectStatistic(long userId) {
        //Task
        List<Task> listRes = select(Task.class);
        List<Task> findedTaskList = listRes.stream()
                .filter(el -> el.getScrumMaster().getId() == userId)
                .collect(Collectors.toList());
        log.debug(findedTaskList);
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
        log.debug(optionalProject);
        return new Result(Complete, optionalProject);

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
            log.debug(resulttime);
            return new Result<>(Complete,resulttime);
        } catch (ParseException e) {
            log.error(e);
            return new Result<>(Fail);
        }
    }

    /**
     * @param list
     * @throws Exception
     */
    private void listIsValid(List<?> list) throws Exception {
        if (list.isEmpty()) throw new Exception(String.valueOf(Fail));
    }

    /**
     * @param optional
     * @throws Exception
     */
    private void optionalIsValid(Optional<?> optional) throws Exception {
        if (optional.isEmpty()) throw new Exception(String.valueOf(Fail));
    }

    /**
     * @param str
     * @throws Exception
     */
    private void stringIsValid(String str) throws Exception {
        if (str.isEmpty()) throw new Exception(String.valueOf(Fail));
    }


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
     * @param task
     */
    private void isValidTask(Task task) {
        if (task.getTaskDescription() != null
                && !task.getTaskDescription().isEmpty()
                && !task.getMoney().isNaN()) {
            task.getMoney();
        }
    }


    private void isValidProject(Project project){
    }
}
