package ru.sfedu.groupappcontrol.api;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import ru.sfedu.groupappcontrol.models.Result;
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

    private final String PATH = ConfigurationUtil.getConfigurationEntry(Constants.CSV_PATH);
    private static Logger log = LogManager.getLogger(DataProviderCsv.class);
    
    public DataProviderCsv() throws IOException {
    }

    /**
     * @param cl
     * @return
     * @throws IOException
     */
    public String getPath(Class cl) throws IOException {
        return PATH + cl.getSimpleName().toLowerCase() + ConfigurationUtil.getConfigurationEntry(Constants.FILE_EXTENSION_CSV);
    }

    /**
     * @param path
     * @throws IOException
     */
    public void createFile(String path) throws IOException {
        File file = new File(path);
        if (!file.exists()) {
            Path dirPath = Paths.get(PATH);
            Files.createDirectories(dirPath);
            file.createNewFile();
        }
    }

    /**
     * @param cl
     * @param id
     * @param <T>
     * @return
     * @throws IOException
     */
    public <T extends Task> Result<T> getTaskByID(Class cl, long id) throws IOException {
        try {
            List<T> listRes = select(cl);
            Optional<T> optional=searchTask(listRes,id);
            return new Result(Complete, optional.get());
        } catch (NoSuchElementException e) {
            log.error(e);
            return new Result(Fail);
        }
    }

    /**
     * @param cl
     * @param id
     * @param <T>
     * @return
     * @throws IOException
     */
    public <T extends Employee> Result<T> getEmployeeByID(Class cl, long id) throws IOException {
        try {
            List<T> listRes = select(cl);
            Optional<T> optional = searchEmployee(listRes,id);
            return new Result(Complete, optional.get());
        } catch (NoSuchElementException e) {
            log.error(e);
            return new Result(Fail);
        }
    }


    /**
     * @param cl
     * @param list
     * @param append
     * @param <T>
     * @return
     */
    public <T extends Task> Result<Void> insertJenericTask(Class<T> cl, List<T> list, boolean append) {
        try {
            String path = getPath(cl);
            createFile(path);
            if (append){
                List<T> corrList= (List<T>) isOldTask(cl,list).getData();
                searchTemplate(path,corrList);
            }
            else{
                searchTemplate(path,list);
            }
            return new Result(Complete);
        } catch (IndexOutOfBoundsException | IOException e) {
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
    public <T extends Employee> Result<Void> insertJenericEmployee(Class<T> cl, List<T> list, boolean append) {
        try {
            String path = getPath(cl);
            createFile(path);
            if (append){
                List<T> corrList= (List<T>) isOldEmployee(cl,list).getData();
                searchTemplate(path,corrList);
            }
            else{
                searchTemplate(path,list);
            }
            return new Result(Complete);
        } catch (IndexOutOfBoundsException | IOException e) {
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
    public <T extends Project> Result<Void> insertJenericProject(Class<T> cl, List<T> list, boolean append) {
        try {
            String path = getPath(cl);
            createFile(path);
            if (append){
                List<T> corrList= (List<T>) isOldProject(cl,list).getData();
                searchTemplate(path,corrList);
            }
            else{
                searchTemplate(path,list);
            }
            return new Result(Complete);
        } catch (IndexOutOfBoundsException | IOException e) {
            log.error(e);
            return new Result<>(Fail);
        }
    }

    /**
     * @param cl
     * @param id
     * @param <T>
     * @return
     * @throws IOException
     */
    public <T extends Task> Result<T> deleteJenericTask(Class<T> cl, long id) throws IOException {
        List<T> listData = select(cl);
        listData = listData.stream().filter(el -> el.getId() != id).collect(Collectors.toList());
        insertJenericTask(cl, listData, false);
        return new Result(Complete);
    }

    /**
     * @param cl
     * @param id
     * @param <T>
     * @return
     * @throws IOException
     */
    public <T extends Employee> Result<T> deleteJenericEmployee(Class<T> cl, long id) throws IOException {
        List<T> listData = select(cl);
        listData = listData.stream().filter(el -> el.getId() != id).collect(Collectors.toList());
        insertJenericEmployee(cl, listData, false);
        return new Result(Complete);
    }

    /**
     * @param cl
     * @param id
     * @param <T>
     * @return
     * @throws IOException
     */
    public <T extends Project> Result<T> deleteJenericProject(Class<T> cl, long id) throws IOException {
        List<T> listData = select(cl);
        listData = listData.stream().filter(el -> el.getId() != id).collect(Collectors.toList());
        insertJenericProject(cl, listData, false);
        return new Result(Complete);
    }

    /**
     * @param cl
     * @param updElement
     * @param <T>
     * @return
     * @throws IOException
     */
    public <T extends Task> Result<T> updateJenericTask(Class<T> cl, T updElement) throws IOException {
        deleteJenericTask(cl, updElement.getId());
        List<T> list = new ArrayList<T>();
        list.addAll(list);
        list.add(updElement);
        insertJenericTask(cl, list, true);
        return new Result(Complete);
    }

    /**
     * @param cl
     * @param updElement
     * @param <T>
     * @return
     * @throws IOException
     */
    public <T extends Employee> Result<T> updateJenericEmployee(Class<T> cl, T updElement) throws IOException {
        deleteJenericEmployee(cl, updElement.getId());
        List<T> list = new ArrayList<T>();
        list.addAll(list);
        list.add(updElement);
        insertJenericEmployee(cl, list, true);
        return new Result(Complete);
    }

    /**
     * @param cl
     * @param updElement
     * @param <T>
     * @return
     * @throws IOException
     */
    public <T extends Project> Result<T> updateJenericProject(Class<T> cl, T updElement) throws IOException {
        deleteJenericProject(cl, updElement.getId());
        List<T> list = new ArrayList<T>();
        list.addAll(list);
        list.add(updElement);
        insertJenericProject(cl, list, true);
        return new Result(Complete);
    }

    /**
     * @param cl
     * @param <T>
     * @return
     */
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

    /**
     * @param listRes
     * @param id
     * @param <T>
     * @return
     * @throws IOException
     */
    public <T extends Task> Optional<T> searchTask(List<T> listRes,long id) throws IOException {
        Optional<T> optional = listRes.stream()
                .filter(el -> el.getId() == id)
                .findFirst();
        return optional;
    }

    /**
     * @param listRes
     * @param id
     * @param <T>
     * @return
     * @throws IOException
     */
    public <T extends Employee> Optional<T> searchEmployee(List<T> listRes,long id) throws IOException {
        Optional<T> optional = listRes.stream()
                .filter(el -> el.getId() == id)
                .findFirst();
        return optional;
    }

    /**
     * @param listRes
     * @param id
     * @param <T>
     * @return
     * @throws IOException
     */
    public <T extends Project> Optional<T> searchProject(List<T> listRes,long id) throws IOException {
        Optional<T> optional = listRes.stream()
                .filter(el -> el.getId() == id)
                .findFirst();
        return optional;
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
     * @param taskType
     * @return
     */
    @Override
    public Result createTask( long id, String taskDescription, Double money, Employee scrumMaster,TypeOfCompletion status, List<Employee> team, String createdDate,String deadline,String lastUpdate,TaskTypes taskType){
        if(taskDescription.isEmpty() || money.isNaN() || scrumMaster.toString().isEmpty()||status.toString().isEmpty()||team.isEmpty()||createdDate.isEmpty()||deadline.isEmpty()||lastUpdate.isEmpty()||taskType.toString().isEmpty()){
            return new Result(Fail);
        }
        switch (taskType){
        case BASE_TASK:
            Task baseTask = (Task) createBaseTask(id,taskDescription,money,scrumMaster,status,team,createdDate,deadline,lastUpdate).getData();
            return new Result<>(Complete,baseTask);
        case DEVELOPERS_TASK:
            DevelopersTask developersTask = (DevelopersTask) createDevelopersTask(id,taskDescription,money,scrumMaster,status,team,createdDate,deadline,lastUpdate).getData();
            return new Result<>(Complete,developersTask);
        case TESTERS_TASK:
            TestersTask testersTask = (TestersTask) createTestersTask(id,taskDescription,money,scrumMaster,status,team,createdDate,deadline,lastUpdate).getData();
            return new Result<>(Complete,testersTask);
        default:
            return new Result(Fail);
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
    public Result createBaseTask(long id, String taskDescription, Double money, Employee scrumMaster,TypeOfCompletion status, List<Employee> team, String createdDate,String deadline,String lastUpdate) {
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
        return new Result(Complete,task);
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
    public Result createDevelopersTask(long id, String taskDescription, Double money,Employee scrumMaster,TypeOfCompletion status, List<Employee> team, String createdDate, String deadline, String lastUpdate) {
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
    public Result createTestersTask(long id, String taskDescription, Double money,Employee scrumMaster,TypeOfCompletion status, List<Employee> team, String createdDate, String deadline,String lastUpdate) {
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

    /**
     * @param id
     * @param title
     * @param takeIntoDevelopment
     * @param tasks
     * @return
     */
    @Override
    public Result createProject(long id,String title, String takeIntoDevelopment, List<Task> tasks) {
        if(title.isEmpty()||takeIntoDevelopment.isEmpty()){
            return new Result(Fail);
        }
        Project project=new Project();
        project.setId(id);
        project.setTitle(title);
        project.setTakeIntoDevelopment(takeIntoDevelopment);
        project.setTask(tasks);
        return new Result(Complete,project);
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
     * @param typeOfEmployee
     * @return
     */
    @Override
    public Result createEmployee(long id,String firstName, String lastName, String login, String password, String email,String token, String department,TypeOfEmployee typeOfEmployee){
        if(firstName.isEmpty() || lastName.isEmpty() || login.isEmpty()||password.isEmpty()||email.isEmpty()||department.isEmpty()||typeOfEmployee.toString().isEmpty()){
            return new Result(Fail);
        }
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
                return new Result(Fail);
        }
    }

    /**
     * @param cl
     * @param <T>
     * @return
     */
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
    public Result createBaseEmployee(long id,String firstName, String lastName, String login, String password, String email,String token, String department) {
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
    public Result createDeveloperEmployee(long id, String firstName, String lastName, String login, String password, String email,String token, String department) {
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
    public Result createTesterEmployee(long id, String firstName, String lastName, String login, String password, String email,String token, String department) {
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

    /**
     * @param cl
     * @param userId
     * @param <T>
     * @return
     */
    public <T extends Task> Result<T> getTaskList(Class<T> cl,long userId){
        List<T> listRes = select(cl);
        List<T> optionalRes = listRes.stream()
                .filter(el -> el.getScrumMaster().getId() == userId)
                .collect(Collectors.toList());
        if(optionalRes.contains(null)){
            return new Result<>(Outcomes.NotFound);
        }
        return new Result(Complete, optionalRes);
    }

    /**
     * @param userId
     * @return
     */
    @Override
    public Result getBaseTaskList(long userId) {
        List<Task> taskList= (List<Task>) getTaskList(Task.class,userId).getData();
        return new Result<>(Complete,taskList);
    }

    /**
     * @param cl
     * @param taskId
     * @return
     */
    @Override
    public Result getTaskInfo(Class cl,long taskId) {
        Object task = getTaskInfoGeneric(cl,taskId).getData();
        return new Result(Complete,task);
    }

    /**
     * @param cl
     * @param taskId
     * @param <T>
     * @return
     */
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

    /**
     * @param userId
     * @param taskId
     * @return
     */
    @Override
    public Result getTask(long userId, long taskId) {
        try {
            List<Task> res = new ArrayList<>();
            List<Task> listRes = select(Task.class);
            if(listRes.isEmpty()){
                return new Result<>(Fail);
            }
            List<Task> optionalRes = listRes.stream()
                    .filter(el -> el.getScrumMaster().getId() == userId)
                    .collect(Collectors.toList());
            if(optionalRes.isEmpty()){
                return new Result<>(Fail);
            }
            List<Employee> listEmpRes = select(Employee.class);
            Optional<Employee> optionalUser = searchEmployee(listEmpRes, userId);
            if(optionalUser.isEmpty()){
                return new Result<>(Fail);
            }
            Employee findedEmployee = optionalUser.get();
            for(Task task:optionalRes){
                if (task.getScrumMaster().getId() == findedEmployee.getId()) {
                    res.add(task);
                } else {
                    return new Result<>(Fail);
                }
            }
            return new Result(Complete,res);
        } catch (IOException e) {
            log.error(e);
            return new Result<>(Fail);
        }
    }

    /**
     * @param userId
     * @return
     */
    @Override
    public Result getUserInfoList(long userId) {
        try {
            List<Employee> list = select(Employee.class);
            if (list.isEmpty()) {
                return new Result(Fail);
            }
            Optional<Employee> optionalEmployee=searchEmployee(list,userId);
            if(optionalEmployee.isEmpty()){
                return new Result(Fail);
            }
            Employee employee= optionalEmployee.get();
            return new Result(Complete, employee);
        } catch (IOException e) {
            log.error(e);
            return new Result(Fail);
        }

    }


    /**
     * @param userId
     * @return
     */
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

    /**
     * @param projectId
     * @return
     */
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

    /**
     * @param cl
     * @param taskId
     * @param <T>
     * @return
     */
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

    /**
     * @param id
     * @return
     */
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

    /**
     * @param employee
     * @param projectId
     * @return
     */
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

    /**
     * @param employee
     * @return
     */
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

    /**
     * @param employee
     * @param taskId
     * @return
     */
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

    /**
     * @param task
     * @return
     */
    @Override
    public Result deleteTask(Task task) {
    try {
        deleteJenericTask(Task.class, task.getId());
        return new Result(Complete);
    } catch (IOException e) {
        log.error(e);
        return new Result(Fail);
    }
}

    /**
     * @param project
     * @return
     */
    @Override
    public Result deleteProject(Project project) {
        try {
            deleteJenericProject(Project.class,project.getId());
            return new Result(Complete);
        } catch (IOException e) {
            log.error(e);
            return new Result(Fail);
        }
    }

    /**
     * @param task
     * @param employee
     * @return
     */
    @Override
    public Result deleteEmployeeFromTask(Task task, Employee employee) {
        List<Employee> employeeList = task.getTeam();
        employeeList.remove(employee.getId());
        task.setTeam(employeeList);
        return new Result<>(Complete);
    }

    /**
     * @param project
     * @return
     */
    @Override
    public Result updateProject(Project project) {
    try {
        updateJenericProject(Project.class,project);
        return new Result(Complete);
    } catch (IOException e) {
        log.error(e);
        return new Result(Fail);
    }
}

    /**
     * @param editedEmployee
     * @return
     */
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
            insertJenericEmployee(Employee.class, listRes, false);
            return new Result<>(Complete);
        } catch (IOException e) {
            log.error(e);
            return new Result<>(Fail);
        }

    }

    /**
     * @param id
     * @param status
     * @return
     */
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
            insertJenericTask(Task.class, listRes, false);
            return new Result<>(Complete);
        } catch (IOException e) {
            log.error(e);
            return new Result<>(Fail);
        }
    }

    /**
     * @param task
     * @return
     */
    @Override
    public Result calculateTaskCost(Task task) {
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

    /**
     * @param project
     * @return
     */
    @Override
    public Result calculateProjectCost(Project project) {
        List<Task> taskList = project.getTask();
        double projectCost = 0.0;
        for (Task task: taskList) {
            System.out.println();
            projectCost = projectCost + (double) calculateTaskCost(task).getData();
        }
        return new Result<>(Complete,projectCost);
    }

    /**
     * @param project
     * @return
     */
    @Override
    public Result calculateProjectTime(Project project) {
        try {
            List<Task> taskList = project.getTask();
            long resulttime = 0;
            for (Task task: taskList) {
                SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT, Locale.ENGLISH);
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

    /**
     * @param cl
     * @param id
     * @param comment
     * @param <T>
     * @return
     */
    @Override
    public <T extends Task> Result<T> writeComment(Class cl, long id, String comment) {
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
            insertJenericTask(cl, listRes, false);
            return new Result<>(Complete);
        } catch (IOException e) {
            log.error(e);
            return new Result<>(Fail);
        }
    }

    /**
     * @param editedEmployee
     * @return
     */
    @Override
    public Result correctEmployeeParameters(Employee editedEmployee) {
        try {
            updateJenericEmployee(Employee.class, editedEmployee);
            return new Result<>(Complete);
        } catch (IOException e) {
            log.error(e);
            return new Result<>(Fail);
        }
    }

    /**
     * @param task
     * @param employee
     * @return
     */
    @Override
    public Result addEmployeeToTask(Task task, Employee employee) {
        List<Employee> employeeList = task.getTeam();
        employeeList.addAll(Collections.singleton(employee));
        task.setTeam(employeeList);
        return new Result<>(Complete);
    }


    public <T extends Employee> Result isOldEmployee(Class<T> cl,List<T> list){
        List<T> oldList = this.select(cl);
        if (oldList != null && oldList.size() > 0) {
            Long id = list.get(0).getId();
            if (oldList.stream().anyMatch(el -> el.getId() == id)) {
                return new Result<>(Fail);
            }
            list = Stream
                    .concat(list.stream(), oldList.stream())
                    .collect(Collectors.toList());
        }
        return new Result(Complete,list);
    }

    public <T extends Task> Result isOldTask(Class<T> cl,List<T> list){
        List<T> oldList = this.select(cl);
        if (oldList != null && oldList.size() > 0) {
            Long id = list.get(0).getId();
            if (oldList.stream().anyMatch(el -> el.getId() == id)) {
                return new Result<>(Fail);
            }
            list = Stream
                    .concat(list.stream(), oldList.stream())
                    .collect(Collectors.toList());
        }
        return new Result(Complete,list);
    }

    public <T extends Project> Result isOldProject(Class<T> cl,List<T> list){
        List<T> oldList = this.select(cl);
        if (oldList != null && oldList.size() > 0) {
            Long id = list.get(0).getId();
            if (oldList.stream().anyMatch(el -> el.getId() == id)) {
                return new Result<>(Fail);
            }
            list = Stream
                    .concat(list.stream(), oldList.stream())
                    .collect(Collectors.toList());
        }
        return new Result(Complete,list);
    }

    public <T> Result searchTemplate(String path, List<T> list){
        try {
            FileWriter file = new FileWriter(path);
            CSVWriter writer = new CSVWriter(file);
            StatefulBeanToCsv<T> beanToCsv = new StatefulBeanToCsvBuilder<T>(writer)
                    .withApplyQuotesToAll(false)
                    .build();
            beanToCsv.write(list);
            writer.close();
            return new Result(Complete);
        } catch (CsvDataTypeMismatchException | CsvRequiredFieldEmptyException | IOException e) {
            log.error(e);
            return new Result(Fail);
        }

    }

}