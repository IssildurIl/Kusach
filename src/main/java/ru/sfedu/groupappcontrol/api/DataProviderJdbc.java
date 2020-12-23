package ru.sfedu.groupappcontrol.api;

import lombok.NonNull;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.groupappcontrol.Constants;
import ru.sfedu.groupappcontrol.Result;
import ru.sfedu.groupappcontrol.models.*;
import ru.sfedu.groupappcontrol.models.enums.*;

import java.io.IOException;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static ru.sfedu.groupappcontrol.models.enums.Outcomes.Complete;
import static ru.sfedu.groupappcontrol.models.enums.Outcomes.Fail;
import static ru.sfedu.groupappcontrol.utils.ConfigurationUtil.getConfigurationEntry;

public class DataProviderJdbc implements DataProvider {

    private Connection connection;
    public static final Logger log = LogManager.getLogger(DataProviderJdbc.class);

    public void initDataSource() {

    }

    @Override
    public Result<Task> getTaskById(long id) {
        log.info(Thread.currentThread().getStackTrace()[1].getMethodName());
        try {
            ResultSet set = setResById(Task.class, id);
            if (set != null && set.next()) {
                Task task = new Task();
                setBasicTask(set, task);
                return new Result(Outcomes.Complete, task);
            } else {
                return new Result<>(Outcomes.Fail);
            }
        } catch (SQLException e) {
            log.error(e);
            return new Result<>(Outcomes.Fail);
        }
    }

    @Override
    public Result<DevelopersTask> getDevelopersTaskById(long id) {
        try {
            ResultSet set = setResById(DevelopersTask.class, id);
            if (set != null && set.next()) {
                DevelopersTask developersTask = new DevelopersTask();
                setBasicTask(set, developersTask);
                developersTask.setDeveloperComments(set.getString(Constants.DEVELOPERS_TASK_COMMENTS));
                developersTask.setDeveloperTaskType(DeveloperTaskType.valueOf(set.getString(Constants.DEVELOPERS_TASK_TYPE)));
                return new Result<>(Outcomes.Complete, developersTask);
            } else {
                return new Result<>(Outcomes.Fail);
            }
        } catch (SQLException e) {
            log.error(e);
            return new Result<>(Outcomes.Fail);
        }
    }

    @Override
    public Result<TestersTask> getTestersTaskById(long id) {
        try {
            ResultSet set = setResById(TestersTask.class, id);
            if (set != null && set.next()) {
                TestersTask testersTask = new TestersTask();
                setBasicTask(set, testersTask);
                testersTask.setBugStatus(BugStatus.valueOf(set.getString(Constants.TESTERSTASK_BUGSTATUS)));
                testersTask.setBugDescription(set.getString(Constants.TESTERSTASK_DESCRIPTION));
                return new Result(Outcomes.Complete, testersTask);
            } else {
                return new Result(Outcomes.Fail);
            }
        } catch (SQLException e) {
            log.error(e);
            return new Result(Outcomes.Fail);
        }
    }

    @Override
    public Result<Employee> getEmployeeById(long id) {
        try {
            ResultSet set = setResById(Employee.class, id);
            if (set != null && set.next()) {
                Employee employee = new Employee();
                setBasicEmployee(set, employee);
                return new Result(Outcomes.Complete, employee);
            } else {
                return new Result(Outcomes.Fail);
            }
        } catch (SQLException e) {
            log.error(e);
            return new Result(Outcomes.Fail);
        }
    }

    @Override
    public Result<Developer> getDeveloperById(long id) {
        try {
            ResultSet set = setResById(Developer.class, id);
            if (set != null && set.next()) {
                Developer developer = new Developer();
                setBasicEmployee(set, developer);
                developer.setStatus(TypeOfDevelopers.valueOf(set.getString(Constants.DEVELOPER_TYPE_OF_DEVELOPER)));
                developer.setProgrammingLanguage(ProgrammingLanguage.valueOf(set.getString(Constants.DEVELOPER_TYPE_OF_PROGRAMMING_LANGUAGE)));
                return new Result(Outcomes.Complete, developer);
            } else {
                return new Result(Outcomes.Fail);
            }
        } catch (SQLException e) {
            log.error(e);
            return new Result(Outcomes.Fail);
        }
    }

    @Override
    public Result<Tester> getTesterById(long id) {
        try {
            ResultSet set = setResById(Tester.class, id);
            if (set != null && set.next()) {
                Tester tester = new Tester();
                setBasicEmployee(set, tester);
                tester.setStatus(TypeOfDevelopers.valueOf(set.getString(Constants.TESTER_TYPE_OF_DEVELOPER)));
                tester.setProgrammingLanguage(ProgrammingLanguage.valueOf(set.getString(Constants.TESTER_TYPE_OF_PROGRAMMING_LANGUAGE)));
                tester.setTypeOfTester(TypeOfTester.valueOf(set.getString(Constants.TESTER_TYPE_OF_TESTER)));
                return new Result(Outcomes.Complete, tester);
            } else {
                return new Result(Outcomes.Fail);
            }
        } catch (SQLException e) {
            log.error(e);
            return new Result(Outcomes.Fail);
        }
    }

    @Override
    public Result<Project> getProjectByID(long id) {
        try {
            ResultSet set = setResById(Project.class, id);
            if (set != null && set.next()) {
                Project project = new Project();
                setProject(set, project);
                return new Result(Outcomes.Complete, project);
            } else {
                return new Result(Outcomes.Fail);
            }
        } catch (SQLException e) {
            log.error(e);
            return new Result(Outcomes.Fail);
        }
    }

    @Override
    public Result<Void> insertTask(Task task) {
        try {
            boolean exist = execute(String.format(
                    Constants.INSERT_TASK, task.getTaskDescription(),
                    task.getMoney(), task.getScrumMaster().getId(), task.getStatus(), task.getCreatedDate(), task.getDeadline(),
                    task.getLastUpdate(), task.getTaskType().toString())).getStatus() == Outcomes.Complete;
            ResultSet set = getTaskRecords(String.format(Constants.SELECT_TASK, task.getTaskDescription(),
                    task.getMoney(), task.getScrumMaster().getId(), task.getStatus(), task.getCreatedDate(), task.getDeadline(),
                    task.getLastUpdate(), task.getTaskType().toString()));
            set.next();
            Task task1 = new Task();
            task1.setId(set.getLong(Constants.TASK_ID));
            log.info(task1.getId());
            task1.setTaskDescription(task.getTaskDescription());
            task1.setMoney(task.getMoney());
            task1.setScrumMaster(task.getScrumMaster());
            task1.setStatus(task.getStatus());
            task1.setCreatedDate(task.getCreatedDate());
            task1.setDeadline(task.getDeadline());
            task1.setLastUpdate(task.getLastUpdate());
            task1.setTaskType(task.getTaskType());
            task1.setTeam(task.getTeam());
            log.info(task);
            taskToEmployeeMapping(task1);
            return (exist) ? new Result<>(Outcomes.Complete) : new Result<>(Outcomes.Fail);
        } catch (Exception e) {
            log.error(e);
            return new Result<>(Outcomes.Fail);
        }
    }

    @Override
    public Result<Void> insertDevelopersTask(DevelopersTask task) {
        try {
            boolean exist = execute(String.format(
                    Constants.INSERT_DEVELOPERS_TASK, task.getTaskDescription(),
                    task.getMoney(), task.getScrumMaster().getId(), task.getStatus(), task.getCreatedDate(), task.getDeadline(),
                    task.getLastUpdate(), task.getTaskType().toString(), task.getDeveloperComments(), task.getDeveloperTaskType().toString()))
                    .getStatus() == Outcomes.Complete;
            ResultSet set = getTaskRecords(String.format(Constants.SELECT_DEVELOPERSTASK, task.getTaskDescription(),
                    task.getMoney(), task.getScrumMaster().getId(), task.getStatus(), task.getCreatedDate(), task.getDeadline(),
                    task.getLastUpdate(), task.getTaskType().toString(), task.getDeveloperComments(), task.getDeveloperTaskType().toString()));
            set.next();
            DevelopersTask developersTask = new DevelopersTask();
            developersTask.setId(set.getLong(Constants.TASK_ID));
            developersTask.setTaskDescription(task.getTaskDescription());
            developersTask.setMoney(task.getMoney());
            developersTask.setScrumMaster(task.getScrumMaster());
            developersTask.setStatus(task.getStatus());
            developersTask.setCreatedDate(task.getCreatedDate());
            developersTask.setDeadline(task.getDeadline());
            developersTask.setLastUpdate(task.getLastUpdate());
            developersTask.setTaskType(task.getTaskType());
            developersTask.setTeam(task.getTeam());
            developersTask.setDeveloperComments(task.getDeveloperComments());
            developersTask.setDeveloperTaskType(task.getDeveloperTaskType());
            log.info(task);
            taskToEmployeeMapping(developersTask);
            return (exist) ? new Result<>(Outcomes.Complete) : new Result<>(Outcomes.Fail);
        } catch (Exception e) {
            log.error(e);
            return new Result<>(Outcomes.Fail);
        }
    }

    @Override
    public Result<Void> insertTestersTask(TestersTask task) {
        try {
            boolean exist = execute(String.format(
                    Constants.INSERT_TESTERS_TASK,
                    task.getTaskDescription(),
                    task.getMoney(),
                    task.getScrumMaster().getId(),
                    task.getStatus(),
                    task.getCreatedDate(),
                    task.getDeadline(),
                    task.getLastUpdate(),
                    task.getTaskType().toString(),
                    task.getBugStatus().toString(),
                    task.getBugDescription()))
                    .getStatus() == Outcomes.Complete;
            ResultSet set = getTaskRecords(String.format(Constants.SELECT_TESTERSTASK, task.getTaskDescription(),
                    task.getMoney(), task.getScrumMaster().getId(), task.getStatus(), task.getCreatedDate(), task.getDeadline(),
                    task.getLastUpdate(), task.getTaskType().toString(), task.getBugStatus().toString(), task.getBugDescription()));
            set.next();
            TestersTask testersTask = new TestersTask();
            testersTask.setId(set.getLong(Constants.TASK_ID));
            testersTask.setTaskDescription(task.getTaskDescription());
            testersTask.setMoney(task.getMoney());
            testersTask.setScrumMaster(task.getScrumMaster());
            testersTask.setStatus(task.getStatus());
            testersTask.setCreatedDate(task.getCreatedDate());
            testersTask.setDeadline(task.getDeadline());
            testersTask.setLastUpdate(task.getLastUpdate());
            testersTask.setTaskType(task.getTaskType());
            testersTask.setTeam(task.getTeam());
            testersTask.setBugStatus(task.getBugStatus());
            testersTask.setBugDescription(task.getBugDescription());
            log.info(task);
            taskToEmployeeMapping(testersTask);
            return (exist) ? new Result<>(Outcomes.Complete) : new Result<>(Outcomes.Fail);
        } catch (Exception e) {
            log.error(e);
            return new Result<>(Outcomes.Fail);
        }
    }

    @Override
    public Result<Void> insertEmployee(Employee employee) {
        try {
            boolean exist = execute(String.format(
                    Constants.INSERT_EMPLOYEE,
                    employee.getFirstName(),
                    employee.getLastName(),
                    employee.getLogin(),
                    employee.getPassword(),
                    employee.getEmail(),
                    employee.getToken(),
                    employee.getDepartment(),
                    employee.getTypeOfEmployee().toString()))
                    .getStatus() == Outcomes.Complete;
            return (exist) ? new Result<>(Outcomes.Complete) : new Result<>(Outcomes.Fail);
        } catch (Exception e) {
            log.error(e);
            return new Result<>(Outcomes.Fail);
        }
    }

    @Override
    public Result<Void> insertDeveloper(Developer developer) {
        try {
            boolean exist = execute(String.format(
                    Constants.INSERT_DEVELOPER,
                    developer.getFirstName(),
                    developer.getLastName(),
                    developer.getLogin(),
                    developer.getPassword(),
                    developer.getEmail(),
                    developer.getToken(),
                    developer.getDepartment(),
                    developer.getTypeOfEmployee().toString(),
                    developer.getStatus().toString(),
                    developer.getProgrammingLanguage().toString()))
                    .getStatus() == Outcomes.Complete;
            return (exist) ? new Result<>(Outcomes.Complete) : new Result<>(Outcomes.Fail);
        } catch (Exception e) {
            log.error(e);
            return new Result<>(Outcomes.Fail);
        }
    }

    @Override
    public Result<Void> insertTester(Tester tester) {
        try {
            boolean exist = execute(String.format(
                    Constants.INSERT_TESTER,
                    tester.getFirstName(),
                    tester.getLastName(),
                    tester.getLogin(),
                    tester.getPassword(),
                    tester.getEmail(),
                    tester.getToken(),
                    tester.getDepartment(),
                    tester.getTypeOfEmployee().toString(),
                    tester.getStatus().toString(),
                    tester.getProgrammingLanguage().toString(),
                    tester.getTypeOfTester().toString())).
                    getStatus() == Outcomes.Complete;
            return (exist) ? new Result<>(Outcomes.Complete) : new Result<>(Outcomes.Fail);
        } catch (Exception e) {
            log.error(e);
            return new Result<>(Outcomes.Fail);
        }
    }

    @Override
    public Result<Project> insertProject(Project project) {
        try {
            boolean exist = execute(String.format(
                    Constants.INSERT_PROJECT,
                    project.getTitle(),
                    project.getTakeIntoDevelopment())).
                    getStatus() == Outcomes.Complete;
            log.info(project);
            ResultSet set = getTaskRecords(String.format(Constants.SELECT_PROJECT, project.getTitle(), project.getTakeIntoDevelopment()));
            set.next();
            Project project1 = new Project();
            project1.setId(set.getLong(Constants.PROJECT_ID));
            project1.setTitle(project.getTitle());
            project1.setTakeIntoDevelopment(project.getTakeIntoDevelopment());
            project1.setTask(project.getTask());
            updTask(project1);
            return (exist) ? new Result<>(Outcomes.Complete) : new Result<>(Outcomes.Fail);
        } catch (Exception e) {
            log.error(e);
            return new Result<>(Outcomes.Fail);
        }
    }

    @Override
    public Result<Void> deleteTask(long id) {
        try {
            return (execute(String.format(Constants.DELETE_TASK, id)).getStatus() == Outcomes.Complete) ?
                    new Result<>(Outcomes.Complete) : new Result<>(Outcomes.Fail);
        } catch (Exception e) {
            log.error(e);
            return new Result<>(Outcomes.Fail);
        }
    }

    @Override
    public Result<Void> deleteDevelopersTask(long id) {
        try {
            return (execute(String.format(Constants.DELETE_DEVELOPERS_TASK, id)).getStatus() == Outcomes.Complete) ?
                    new Result<>(Outcomes.Complete) : new Result<>(Outcomes.Fail);
        } catch (Exception e) {
            log.error(e);
            return new Result<>(Outcomes.Fail);
        }
    }

    @Override
    public Result<Void> deleteTestersTask(long id) {
        try {
            boolean a = execute(String.format(Constants.DELETE_TESTERS_TASK, id)).getStatus() == Outcomes.Complete;
            if (a) return new Result(Outcomes.Complete);
        } catch (Exception e) {
            log.error(e);
        }
        return new Result(Outcomes.Fail);
    }

    @Override
    public Result<Void> deleteEmployee(long id) {
        try {
            boolean a = execute(String.format(Constants.DELETE_EMPLOYEE, id)).getStatus() == Outcomes.Complete;
            if (a) return new Result(Outcomes.Complete);
        } catch (Exception e) {
            log.error(e);
        }
        return new Result(Outcomes.Fail);
    }

    @Override
    public Result<Void> deleteDeveloper(long id) {
        try {
            boolean a = execute(String.format(Constants.DELETE_DEVELOPER, id)).getStatus() == Outcomes.Complete;
            if (a) return new Result(Outcomes.Complete);
        } catch (Exception e) {
            log.error(e);
        }
        return new Result(Outcomes.Fail);
    }

    @Override
    public Result<Void> deleteTester(long id) {
        try {
            boolean a = execute(String.format(Constants.DELETE_TESTER, id)).getStatus() == Outcomes.Complete;
            if (a) return new Result(Outcomes.Complete);
        } catch (Exception e) {
            log.error(e);
        }
        return new Result(Outcomes.Fail);
    }

    @Override
    public Result<Void> deleteProject(long id) {
        try {
            boolean a = execute(String.format(Constants.DELETE_PROJECT, id)).getStatus() == Outcomes.Complete;
            if (a) return new Result(Outcomes.Complete);
        } catch (Exception e) {
            log.error(e);
        }
        return new Result(Outcomes.Fail);
    }

    @Override
    public Result<Void> updateTask(Task task) {
        try {
            boolean a = execute(String.format(Constants.UPDATE_TASK,
                    task.getTaskDescription(),
                    task.getMoney(),
                    task.getScrumMaster().getId(),
                    task.getStatus().toString(),
                    task.getCreatedDate(),
                    task.getDeadline(),
                    task.getLastUpdate(),
                    task.getTaskType().toString(),
                    task.getId()))
                    .getStatus() == Outcomes.Complete;
            if (a) return new Result(Outcomes.Complete);
        } catch (Exception e) {
            log.error(e);
        }
        return new Result(Outcomes.Fail);
    }

    @Override
    public Result<Void> updateDevelopersTask(DevelopersTask developersTask) {
        try {
            boolean a = execute(String.format(Constants.UPDATE_DEVELOPERS_TASK,
                    developersTask.getTaskDescription(),
                    developersTask.getMoney(),
                    developersTask.getScrumMaster().getId(),
                    developersTask.getStatus().toString(),
                    developersTask.getCreatedDate(),
                    developersTask.getDeadline(),
                    developersTask.getLastUpdate(),
                    developersTask.getTaskType().toString(),
                    developersTask.getDeveloperComments(),
                    developersTask.getDeveloperTaskType(),
                    developersTask.getId()))
                    .getStatus() == Outcomes.Complete;
            if (a) return new Result(Outcomes.Complete);
        } catch (Exception e) {
            log.error(e);
        }
        return new Result(Outcomes.Fail);
    }

    @Override
    public Result<Void> updateTestersTask(TestersTask testersTask) {
        try {
            boolean a = execute(String.format(Constants.UPDATE_TESTERS_TASK,
                    testersTask.getTaskDescription(),
                    testersTask.getMoney(),
                    testersTask.getScrumMaster().getId(),
                    testersTask.getStatus().toString(),
                    testersTask.getCreatedDate(),
                    testersTask.getDeadline(),
                    testersTask.getLastUpdate(),
                    testersTask.getTaskType().toString(),
                    testersTask.getBugStatus().toString(),
                    testersTask.getBugDescription(),
                    testersTask.getId()))
                    .getStatus() == Outcomes.Complete;
            if (a) return new Result(Outcomes.Complete);
        } catch (Exception e) {
            log.error(e);
        }
        return new Result(Outcomes.Fail);
    }

    @Override
    public Result<Void> updateEmployee(Employee employee) {
        try {
            boolean a = execute(String.format(Constants.UPDATE_EMPLOYEE,
                    employee.getFirstName(),
                    employee.getLastName(),
                    employee.getLogin(),
                    employee.getPassword(),
                    employee.getEmail(),
                    employee.getToken(),
                    employee.getDepartment(),
                    employee.getTypeOfEmployee().toString(),
                    employee.getId()))
                    .getStatus() == Outcomes.Complete;
            if (a) return new Result(Outcomes.Complete);
        } catch (Exception e) {
            log.error(e);
        }
        return new Result(Outcomes.Fail);
    }

    @Override
    public Result<Void> updateDeveloper(Developer developer) {
        try {
            boolean a = execute(String.format(Constants.UPDATE_DEVELOPER,
                    developer.getFirstName(),
                    developer.getLastName(),
                    developer.getLogin(),
                    developer.getPassword(),
                    developer.getEmail(),
                    developer.getToken(),
                    developer.getDepartment(),
                    developer.getTypeOfEmployee().toString(),
                    developer.getStatus(),
                    developer.getProgrammingLanguage(),
                    developer.getId()))
                    .getStatus() == Outcomes.Complete;
            if (a) return new Result(Outcomes.Complete);
        } catch (Exception e) {
            log.error(e);
        }
        return new Result(Outcomes.Fail);
    }

    @Override
    public Result<Void> updateTester(Tester tester) {
        try {
            boolean a = execute(String.format(Constants.UPDATE_TESTER,
                    tester.getFirstName(),
                    tester.getLastName(),
                    tester.getLogin(),
                    tester.getPassword(),
                    tester.getEmail(),
                    tester.getToken(),
                    tester.getDepartment(),
                    tester.getTypeOfEmployee().toString(),
                    tester.getStatus(),
                    tester.getProgrammingLanguage(),
                    tester.getTypeOfTester(),
                    tester.getId()))
                    .getStatus() == Outcomes.Complete;
            if (a) return new Result(Outcomes.Complete);
        } catch (Exception e) {
            log.error(e);
        }
        return new Result(Outcomes.Fail);
    }

    @Override
    public Result<Project> updateProject(Project project) {
        try {
            boolean a = execute(String.format(Constants.UPDATE_PROJECT,
                    project.getTitle(),
                    project.getTakeIntoDevelopment()))
                    .getStatus() == Outcomes.Complete;
            if (a) return new Result(Outcomes.Complete);
        } catch (Exception e) {
            log.error(e);
        }
        return new Result(Outcomes.Fail);
    }

    @Override
    public Result<Task> createTask(@NonNull long id,
                                   @NonNull String taskDescription,
                                   @NonNull Double money,
                                   @NonNull Employee scrumMaster,
                                   @NonNull TypeOfCompletion status,
                                   @NonNull List<Employee> team,
                                   @NonNull String createdDate,
                                   @NonNull String deadline,
                                   @NonNull String lastUpdate,
                                   @NonNull TaskTypes taskType) {
        switch (taskType) {
            case BASE_TASK:
                Task baseTask = createBaseTask(id, taskDescription, money, scrumMaster, status, team, createdDate, deadline, lastUpdate).getData();
                return new Result<>(Complete, baseTask);
            case DEVELOPERS_TASK:
                DevelopersTask developersTask = createDevelopersTask(id, taskDescription, money, scrumMaster, status, team, createdDate, deadline, lastUpdate).getData();
                developersTask.setDeveloperComments(Constants.BaseComment);
                developersTask.setDeveloperTaskType(DeveloperTaskType.DEVELOPMENT);
                return new Result<>(Complete, developersTask);
            case TESTERS_TASK:
                TestersTask testersTask = createTestersTask(id, taskDescription, money, scrumMaster, status, team, createdDate, deadline, lastUpdate).getData();
                testersTask.setBugStatus(BugStatus.FIND_BUGS);
                testersTask.setBugDescription(Constants.BaseComment);
                return new Result<>(Complete, testersTask);
            default:
                return new Result<>(Fail);
        }
    }

    @Override
    public Result<Task> getTasks(long id) {
        List<Task> taskList = new ArrayList<>();
        taskList.addAll(getTaskRecords(Task.class));
        taskList.addAll(getTaskRecords(DevelopersTask.class));
        taskList.addAll(getTaskRecords(TestersTask.class));
        log.debug(taskList);
        Optional<Task> optTask = taskList.stream().filter(el -> el.getId() == id).findAny();
        log.info(optTask);
        return optTask.map(task -> new Result<>(Outcomes.Complete, task)).orElseGet(() ->
                new Result<>(Outcomes.Fail));
    }

    @Override
    public List<Task> getAllTask() {
        List<Task> taskList = new ArrayList<>();
        taskList.addAll(getTaskRecords(Task.class));
        taskList.addAll(getTaskRecords(DevelopersTask.class));
        taskList.addAll(getTaskRecords(TestersTask.class));
        log.debug(taskList);
        return taskList;
    }

    @Override
    public Result<Task> getTasksByUser(long userId, long taskId) {
        try {
            ResultSet res = getTaskRecords(String.format(Constants.SELECT_TASK_EMPLOYEE_MAPPING,
                    userId, taskId));
            Task task = new Task();
            while (res.next()) {
                setBasicTask(res, task);
            }
            return new Result<>(Complete, task);
        } catch (SQLException e) {
            log.error(e);
            return new Result<>(Fail);
        }
    }

    @Override
    public Result<Void> changeTaskStatus(long id, String status) {
        boolean exist = execute(String.format(Constants.UPDATE_TASK_STATUS,
                status, id)).getStatus() == Complete;
        return (exist) ? new Result<>(Outcomes.Complete) : new Result<>(Outcomes.Fail);
    }

    @Override
    public Result<Double> calculateTaskCost(Task task) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT, Locale.ENGLISH);
            java.util.Date firstDate = sdf.parse(String.valueOf(task.getCreatedDate()));
            Date secondDate = sdf.parse(String.valueOf(task.getDeadline()));
            long diffInMillies = Math.abs(secondDate.getTime() - firstDate.getTime());
            log.debug(diffInMillies);
            long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
            log.debug(diff);
            double res = (double) diff * task.getMoney();
            log.debug(res);
            log.info(res);
            return new Result<>(Complete, res);
        } catch (ParseException e) {
            log.error(e);
            return new Result<>(Fail);
        }
    }

    @Override
    public Result<Void> writeBaseTaskComment(long id, String comment) {
        boolean exist = execute(String.format(Constants.UPDATE_TASK_COMMENT,
                comment, id)).getStatus() == Complete;
        return (exist) ? new Result<>(Outcomes.Complete) : new Result<>(Outcomes.Fail);
    }

    @Override
    public Result<Void> writeDevelopersTaskComment(long id, String comment) {
        boolean exist = execute(String.format(Constants.UPDATE_DEVELOPERSTASK_COMMENT,
                comment, id)).getStatus() == Complete;
        return (exist) ? new Result<>(Outcomes.Complete) : new Result<>(Outcomes.Fail);

    }

    @Override
    public Result<Void> writeTestersTaskComment(long id, String comment) {
        boolean exist = execute(String.format(Constants.UPDATE_TESTERSTASK_COMMENT,
                comment, id)).getStatus() == Complete;
        return (exist) ? new Result<>(Outcomes.Complete) : new Result<>(Outcomes.Fail);

    }

    @Override
    public Result<List<Task>> getTaskListByScrumMaster(long id) {
        try {
            ResultSet set = getTaskRecords(String.format(Constants.GetTaskByScrummaster,
                    id));
            if (set != null && set.next()) {
                Task task = new Task();
                setBasicTask(set, task);
                List<Task> taskList = new ArrayList<>();
                taskList.add(task);
                return new Result<>(Outcomes.Complete, taskList);
            } else {
                return new Result<>(Outcomes.Fail);
            }
        } catch (SQLException e) {
            log.error(e);
            return new Result<>(Fail);
        }
    }

    @Override
    public Result<List<DevelopersTask>> getDevelopersTaskListByScrumMaster(long id) {
        try {
            ResultSet set = getTaskRecords(String.format(Constants.GetDevelopersTaskByScrummaster,
                    id));
            if (set != null && set.next()) {
                DevelopersTask developersTask = new DevelopersTask();
                setBasicTask(set, developersTask);
                developersTask.setDeveloperComments(set.getString(Constants.DEVELOPERS_TASK_COMMENTS));
                developersTask.setDeveloperTaskType(DeveloperTaskType.valueOf(set.getString(Constants.DEVELOPERS_TASK_TYPE)));
                List<DevelopersTask> developersTasks = new ArrayList<>();
                developersTasks.add(developersTask);
                return new Result<>(Outcomes.Complete, developersTasks);
            } else {
                return new Result<>(Outcomes.Fail);
            }
        } catch (SQLException e) {
            log.error(e);
            return new Result<>(Fail);
        }
    }

    @Override
    public Result<List<TestersTask>> getTestersTaskListByScrumMaster(long id) {
        try {
            ResultSet set = getTaskRecords(String.format(Constants.GetTestersTaskByScrummaster,
                    id));
            if (set != null && set.next()) {
                TestersTask testersTask = new TestersTask();
                setBasicTask(set, testersTask);
                testersTask.setBugStatus(BugStatus.valueOf(set.getString(Constants.TESTERSTASK_BUGSTATUS)));
                testersTask.setBugDescription(set.getString(Constants.TESTERSTASK_DESCRIPTION));
                List<TestersTask> testersTasks = new ArrayList<>();
                testersTasks.add(testersTask);
                return new Result<>(Outcomes.Complete, testersTasks);
            } else {
                return new Result<>(Outcomes.Fail);
            }
        } catch (SQLException e) {
            log.error(e);
            return new Result<>(Fail);
        }
    }

    @Override
    public Result<Project> createProject(long id, @NonNull String title,
                                         @NonNull String takeIntoDevelopment,
                                         @NonNull List<Task> tasks) {
        Project project = new Project();
        project.setTitle(title);
        project.setTakeIntoDevelopment(takeIntoDevelopment);
        project.setTask(tasks);
        return new Result<>(Complete, project);
    }

    @Override
    public Result<List<Project>> getProjectById(long employeeId, long projectId) {
        try {
            ResultSet set = getTaskRecords(String.format(Constants.GETPROJECTLISTBYWORKERID,
                    employeeId, projectId));
            List<Project> projects = new ArrayList<>();
            if (set != null && set.next()) {
                Project project = new Project();
                setProject(set, project);
                projects.add(project);
            }
            return new Result<>(Complete, projects);
        } catch (SQLException e) {
            log.error(e);
            return new Result<>(Fail);
        }
    }

    @Override
    public Result<Long> calculateProjectCost(Project project) {
        try {
            stringIsValid(project.getTakeIntoDevelopment());
            List<Task> taskList = project.getTask();
            double projectCost = 0.0;
            for (Task task : taskList) {
                projectCost = projectCost + (double) calculateTaskCost(task).getData();
            }
            log.info(projectCost);
            return new Result<>(Complete, (long) projectCost);
        } catch (Exception e) {
            log.error(e);
            return new Result<>(Fail);
        }
    }

    @Override
    public Result<Long> calculateProjectTime(Project project) {
        List<Task> taskList = project.getTask();
        long resulttime = 0;
        for (Task task : taskList) {
            resulttime = resulttime + (long) calculatePrice(task).getData();
        }
        log.info(resulttime);
        return new Result<>(Complete, resulttime);
    }


    @Override
    public Result<Employee> createEmployee(long id,
                                           @NonNull String firstName,
                                           @NonNull String lastName,
                                           @NonNull String login,
                                           @NonNull String password,
                                           @NonNull String email,
                                           @NonNull String token,
                                           @NonNull String department,
                                           @NonNull TypeOfEmployee typeOfEmployee) {
        switch (typeOfEmployee) {
            case Employee:
                Employee employee = new Employee();
                setBasicEmployeeParams(employee, firstName, lastName, login,
                        password, email, token, department, typeOfEmployee);
                return new Result<>(Complete, employee);
            case Developer:
                Developer developer = new Developer();
                setBasicEmployeeParams(developer, firstName, lastName, login,
                        password, email, token, department, typeOfEmployee);
                developer.setStatus(TypeOfDevelopers.CUSTOM);
                developer.setProgrammingLanguage(ProgrammingLanguage.JAVA);
                return new Result<>(Complete, developer);
            case Tester:
                Tester tester = new Tester();
                setBasicEmployeeParams(tester, firstName, lastName, login,
                        password, email, token, department, typeOfEmployee);
                tester.setStatus(TypeOfDevelopers.CUSTOM);
                tester.setProgrammingLanguage(ProgrammingLanguage.JAVA);
                tester.setTypeOfTester(TypeOfTester.Custom);
                return new Result<>(Complete, tester);
            default:
                return new Result<>(Fail);
        }

    }

    @Override
    public List<Employee> getAllEmployee() {
        List<Employee> emplList = new ArrayList<>();
        emplList.addAll(getBaseEmployee(Employee.class));
        emplList.addAll(getBaseEmployee(Developer.class));
        emplList.addAll(getBaseEmployee(Tester.class));
        log.debug(emplList);
        return emplList;
    }

    @Override
    public Result getScrumMasterTaskList(long userId,
                                                     TaskTypes taskTypes) {
        List<Task> taskList = new ArrayList<>();
        switch (taskTypes){
            case BASE_TASK:
                List<Task> tasks = getTaskListByScrumMaster(userId).getData();
                return new Result<>(Complete, tasks);
            case DEVELOPERS_TASK:
                List<DevelopersTask> developersTasks =
                        getDevelopersTaskListByScrumMaster(userId).getData();
                return new Result<>(Complete , developersTasks);
            case TESTERS_TASK:
                List<TestersTask> testersTasks =
                        getTestersTaskListByScrumMaster(userId).getData();
                return new Result(Complete , testersTasks);
            default:
                return new Result(Fail);
        }

    }

    @Override
    public Result<List<Project>> getProjectListByScrummasterId(long id) {
      try {
          ResultSet set = getTaskRecords(String.format(
                  Constants.GETPROJECTLISTBYSCRUMMASTERID, id));
          List<Project> projects = new ArrayList<>();
          if (set != null && set.next()) {
              Project project = new Project();
              setProject(set, project);
              projects.add(project);
          }
          return new Result<>(Complete,projects);
      } catch (SQLException e) {
          log.error(e);
          return new Result<>(Fail);
      }
    }

    @Override
    public <T> Result<T> deleteRecord(Class<T> cl) {
        execute(String.format(Constants.DROP,
                cl.getSimpleName().toUpperCase()));
        return new Result<>(Outcomes.Complete);
    }

    @Override
    public void deleteAllRecord() {
        deleteRecord(Employee.class);
        deleteRecord(Developer.class);
        deleteRecord(Tester.class);
        deleteRecord(Task.class);
        deleteRecord(DevelopersTask.class);
        deleteRecord(TestersTask.class);
        deleteRecord(Project.class);
        execute(String.format(Constants.DROPTASKTOEMPLOYEE));
        //createTables();
    }

    /**
     * @param set
     * @param task
     */
    private void setBasicTask(ResultSet set,Task task){
        try {
            task.setId(set.getLong(Constants.TASK_ID));
            task.setTaskDescription(set.getString(Constants.TASK_DESCRIPTION));
            task.setMoney(set.getDouble(Constants.TASK_MONEY));
            task.setScrumMaster(getEmployeeById(set.getLong(Constants.TASK_SCRUMMASTER)).getData());
            task.setStatus(TypeOfCompletion.valueOf(set.getString(Constants.TASK_TYPE_OF_COMPLETION)));
            task.setTaskType(TaskTypes.valueOf(set.getString(Constants.TASK_TASK_TYPES)));
            task.setTeam(getEmployeeListFromTask(task));
            task.setCreatedDate(set.getString(Constants.TASK_CREATED_DATE));
            task.setDeadline(set.getString(Constants.TASK_DEADLINE));
            task.setLastUpdate(set.getString(Constants.TASK_LAST_UPDATE));
        } catch (SQLException e) {
            log.error(e);
        }
    }

    /**
     * @param task
     * @return
     */
    private List<Employee> getEmployeeListFromTask(Task task){
        try {
            ResultSet res = getTaskRecords(String.format(Constants.SELECT_TASK_EMPLOYEES, task.getId(),
                    task.getTaskType().toString()));
            List<Employee> list = new ArrayList<>();
            while(res.next()) {
                switch (TypeOfEmployee.valueOf(res.getString(Constants.EMPLOYEE_TYPE_OF_EMLPOYEE))){
                    case Employee:
                        list.add(getEmployeeById(res.getLong(Constants.MAP_EMPLOYEE_ID)).getData());
                        break;
                    case Developer:
                        list.add(getDeveloperById(res.getLong(Constants.MAP_EMPLOYEE_ID)).getData());
                        break;
                    case Tester:
                        list.add(getTesterById(res.getLong(Constants.MAP_EMPLOYEE_ID)).getData());
                }
            }
            return list;
        } catch (SQLException e) {
            log.error(e);
            return new ArrayList<>();
        }
    }

    /**
     * @param set
     * @param employee
     */
    private void setBasicEmployee(ResultSet set,Employee employee){
        try {
            employee.setId(set.getLong(Constants.EMPLOYEE_ID));
            employee.setFirstName(set.getString(Constants.EMPLOYEE_FIRSTNAME));
            employee.setLastName(set.getString(Constants.EMPLOYEE_LASTNAME));
            employee.setLogin(set.getString(Constants.EMPLOYEE_LOGIN));
            employee.setPassword(set.getString(Constants.EMPLOYEE_PASSWORD));
            employee.setEmail(set.getString(Constants.EMPLOYEE_EMAIL));
            employee.setToken(set.getString(Constants.EMPLOYEE_TOKEN));
            employee.setDepartment(set.getString(Constants.EMPLOYEE_DEPARTMENT));
            employee.setTypeOfEmployee(TypeOfEmployee.valueOf(set.getString(Constants.EMPLOYEE_TYPE_OF_EMLPOYEE)));
        } catch (SQLException e) {
            log.error(e);
        }
    }

    /**
     * @param employee
     * @param firstName
     * @param lastName
     * @param login
     * @param email
     * @param password
     * @param token
     * @param department
     * @param typeOfEmployee
     */
    public void setBasicEmployeeParams(Employee employee,
                                 String firstName,
                                 String lastName,
                                 String login,
                                 String email,
                                 String password,
                                 String token,
                                 String department,
                                 TypeOfEmployee typeOfEmployee){
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
     *
     */
    public void createTables(){
        execute(String.format(Constants.CREATE_EMPLOYEE));
        execute(String.format(Constants.CREATE_DEVELOPER));
        execute(String.format(Constants.CREATE_TESTER));
        execute(String.format(Constants.CREATE_TASK));
        execute(String.format(Constants.CREATE_DEVELOPERSTASK));
        execute(String.format(Constants.CREATE_TESTERSTASK));
        execute(String.format(Constants.CREATE_PROJECT));
        execute(String.format(Constants.TASKTOEMPLOYEEMAPING));
    }

    /**
     * @param cl
     * @param <T>
     * @return
     */
    public <T extends Employee> List<T> getListEmployees(Class<T> cl){
        try {
            ResultSet set = getTaskRecords(String.format(Constants.SELECT_ALL_USERS, cl.getSimpleName().toUpperCase()));
            List<Employee> list = new ArrayList<>();
            while (set.next()) {
                switch (TypeOfEmployee.valueOf(set.getString(Constants.EMPLOYEE_TYPE_OF_EMLPOYEE))) {
                    case Employee:
                        Employee employee =new Employee();
                        setBasicEmployee(set,employee);
                        list.add(employee);
                        break;
                    case Developer:
                        Developer developer =new Developer();
                        setBasicEmployee(set,developer);
                        developer.setStatus(TypeOfDevelopers.valueOf(set.getString(Constants.DEVELOPER_TYPE_OF_DEVELOPER)));
                        developer.setProgrammingLanguage(ProgrammingLanguage.valueOf(set.getString(Constants.DEVELOPER_TYPE_OF_PROGRAMMING_LANGUAGE)));
                        list.add(developer);
                        break;
                    case Tester:
                        Tester tester = new Tester();
                        setBasicEmployee(set,tester);
                        tester.setStatus(TypeOfDevelopers.valueOf(set.getString(Constants.TESTER_TYPE_OF_DEVELOPER)));
                        tester.setProgrammingLanguage(ProgrammingLanguage.valueOf(set.getString(Constants.TESTER_TYPE_OF_PROGRAMMING_LANGUAGE)));
                        tester.setTypeOfTester(TypeOfTester.valueOf(set.getString(Constants.TESTER_TYPE_OF_TESTER)));
                        list.add(tester);
                }
            }
            return (List<T>) list;
        } catch (SQLException e) {
            log.error(e);
            return new ArrayList<>();
        }
    }


    /**
     * @param set
     * @param project
     */
    private void setProject(ResultSet set,Project project){
        try {
            project.setId(set.getLong(Constants.PROJECT_ID));
            project.setTitle(set.getString(Constants.PROJECT_TITLE));
            project.setTakeIntoDevelopment(set.getString(Constants.PROJECT_TAKE_INTO_DEVELOPMENT));
        } catch (SQLException e) {
            log.error(e);
        }
    }

    /**
     * @param set
     * @param task
     */
    private void setTask(ResultSet set,Task task){
        try {
            task.setId(set.getLong(Constants.TASK_ID));
            task.setTaskDescription(set.getString(Constants.TASK_DESCRIPTION));
            task.setMoney(set.getDouble(Constants.TASK_MONEY));
            task.setScrumMaster(getEmployeeById(set.getLong(Constants.TASK_SCRUMMASTER)).getData());
            task.setStatus(TypeOfCompletion.valueOf(set.getString(Constants.TASK_TYPE_OF_COMPLETION)));
            task.setTaskType(TaskTypes.valueOf(set.getString(Constants.TASK_TASK_TYPES)));
            task.setTeam(getEmployeeListFromTask(task));
            task.setCreatedDate(set.getString(Constants.TASK_CREATED_DATE));
            task.setDeadline(set.getString(Constants.TASK_DEADLINE));
            task.setLastUpdate(set.getString(Constants.TASK_LAST_UPDATE));
        } catch (SQLException e) {
            log.error(e);
        }
    }

    /**
     * @param set
     * @param task
     */
    private void setDevelopersTask(ResultSet set,DevelopersTask task){
        try {
            task.setId(set.getLong(Constants.TASK_ID));
            task.setTaskDescription(set.getString(Constants.TASK_DESCRIPTION));
            task.setMoney(set.getDouble(Constants.TASK_MONEY));
            task.setScrumMaster(getEmployeeById(set.getLong(Constants.TASK_SCRUMMASTER)).getData());
            task.setStatus(TypeOfCompletion.valueOf(set.getString(Constants.TASK_TYPE_OF_COMPLETION)));
            task.setTaskType(TaskTypes.valueOf(set.getString(Constants.TASK_TASK_TYPES)));
            task.setTeam(getEmployeeListFromTask(task));
            task.setCreatedDate(set.getString(Constants.TASK_CREATED_DATE));
            task.setDeadline(set.getString(Constants.TASK_DEADLINE));
            task.setLastUpdate(set.getString(Constants.TASK_LAST_UPDATE));
            task.setDeveloperComments(set.getString(Constants.DEVELOPERS_TASK_COMMENTS));
            task.setDeveloperTaskType(DeveloperTaskType.valueOf(set.getString(Constants.DEVELOPERS_TASK_TYPE)));
        } catch (SQLException e) {
            log.error(e);
        }
    }

    /**
     * @param set
     * @param task
     */
    private void setTestersTask(ResultSet set,TestersTask task){
        try {
            task.setId(set.getLong(Constants.TASK_ID));
            task.setTaskDescription(set.getString(Constants.TASK_DESCRIPTION));
            task.setMoney(set.getDouble(Constants.TASK_MONEY));
            task.setScrumMaster(getEmployeeById(set.getLong(Constants.TASK_SCRUMMASTER)).getData());
            task.setStatus(TypeOfCompletion.valueOf(set.getString(Constants.TASK_TYPE_OF_COMPLETION)));
            task.setTaskType(TaskTypes.valueOf(set.getString(Constants.TASK_TASK_TYPES)));
            task.setTeam(getEmployeeListFromTask(task));
            task.setCreatedDate(set.getString(Constants.TASK_CREATED_DATE));
            task.setDeadline(set.getString(Constants.TASK_DEADLINE));
            task.setLastUpdate(set.getString(Constants.TASK_LAST_UPDATE));
            task.setBugStatus(BugStatus.valueOf(set.getString(Constants.TESTERSTASK_BUGSTATUS)));
            task.setBugDescription(set.getString(Constants.TESTERSTASK_DESCRIPTION));
        } catch (SQLException e) {
            log.error(e);
        }
    }

    /**
     * @param task
     * @param taskDescription
     * @param money
     * @param scrumMaster
     * @param status
     * @param createdDate
     * @param deadline
     * @param lastUpdate
     * @param taskType
     */
    private void setBasicTask(Task task,long id, String taskDescription, double money,
                               Employee scrumMaster, TypeOfCompletion status,List<Employee> team,
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
     * @param task
     */
    private void isValidTask(Task task) {
        if (task.getTaskDescription() != null
                && !task.getTaskDescription().isEmpty()
                && !task.getMoney().isNaN()) {
            task.getMoney();
        }
    }

    /**
     * @param cl
     * @param <T>
     * @return
     */
    public <T extends Task> List<T> getListTasks(Class<T> cl){
        try {
            ResultSet set = getTaskRecords(String.format(Constants.SELECT_ALL_USERS, cl.getSimpleName().toUpperCase()));
            List<Task> list = new ArrayList<>();
            while (set.next()) {
                switch (TaskTypes.valueOf(set.getString(Constants.TASK_TASK_TYPES))) {
                    case BASE_TASK:
                        Task task =new Task();
                        setBasicTask(set,task);
                        ResultSet set1 = getTaskRecords(String.format(Constants.SELECT_TASK, task.getTaskDescription(),
                                task.getMoney(), task.getScrumMaster().getId(), task.getStatus(), task.getCreatedDate(), task.getDeadline(),
                                task.getLastUpdate(), task.getTaskType().toString()));
                        set1.next();
                        task.setId(set1.getLong(Constants.TASK_ID));
                        list.add(task);
                        break;
                    case DEVELOPERS_TASK:
                        DevelopersTask developersTask =new DevelopersTask();
                        setBasicTask(set,developersTask);
                        developersTask.setDeveloperComments(set.getString(Constants.DEVELOPERS_TASK_COMMENTS));
                        developersTask.setDeveloperTaskType(DeveloperTaskType.valueOf(set.getString(Constants.DEVELOPERS_TASK_TYPE)));
                        ResultSet set2 = getTaskRecords(String.format(Constants.SELECT_DEVELOPERSTASK,
                                developersTask.getTaskDescription(),
                                developersTask.getMoney(),
                                developersTask.getScrumMaster().getId(),
                                developersTask.getStatus(),
                                developersTask.getCreatedDate(),
                                developersTask.getDeadline(),
                                developersTask.getLastUpdate(),
                                developersTask.getTaskType().toString(),
                                developersTask.getDeveloperComments(),
                                developersTask.getDeveloperTaskType()));
                        set2.next();
                        developersTask.setId(set2.getLong(Constants.DEVELOPERSTASK_ID));
                        list.add(developersTask);
                        break;
                    case TESTERS_TASK:
                        TestersTask testersTask = new TestersTask();
                        setBasicTask(set,testersTask);
                        testersTask.setBugStatus(BugStatus.valueOf(set.getString(Constants.TESTERSTASK_BUGSTATUS)));
                        testersTask.setBugDescription(set.getString(Constants.TESTERSTASK_DESCRIPTION));
                        ResultSet set3 = getTaskRecords(String.format(Constants.SELECT_TESTERSTASK,
                                testersTask.getTaskDescription(),
                                testersTask.getMoney(),
                                testersTask.getScrumMaster().getId(),
                                testersTask.getStatus(),
                                testersTask.getCreatedDate(),
                                testersTask.getDeadline(),
                                testersTask.getLastUpdate(),
                                testersTask.getTaskType().toString(),
                                testersTask.getBugStatus(),
                                testersTask.getBugDescription()));
                        set3.next();
                        testersTask.setId(set3.getLong(Constants.TESTERSTASK_ID));
                        list.add(testersTask);
                }
            }
            return (List<T>) list;
        } catch (SQLException e) {
            log.error(e);
            return new ArrayList<>();
        }
    }

    /**
     * @param str
     * @throws Exception
     */
    private void stringIsValid(String str) throws Exception {
        if (str.isEmpty()) throw new Exception(String.valueOf(Fail));
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
     * @param cl
     * @param <T>
     * @return
     */
    private <T extends Employee> List<T> getBaseEmployee(Class<T> cl){
        try {
            List<T> empList = new ArrayList<>();
            ResultSet setTask = getTaskRecords(String.format(Constants.SELECT_FOR_ALL,
                    cl.getSimpleName().toUpperCase()));
            while (setTask.next()) {
                T employee = (T) new Employee();
                setBasicEmployee(setTask, employee);
                empList.add(employee);
            }
            return empList;
        } catch (SQLException e) {
            log.error(e);
            return new ArrayList<>();
        }
    }


    /**
     * @param task
     * @param <T>
     */
    private <T extends Task> void taskToEmployeeMapping(T task){
        task.getTeam().forEach(employee->{
            log.debug(String.format(Constants.INSERT_TO_MAPPING,
                    task.getId(),
                    task.getTaskType(),
                    employee.getId(),
                    employee.getTypeOfEmployee()));
            execute(String.format(Constants.INSERT_TO_MAPPING,
                    task.getId(),
                    task.getTaskType(),
                    employee.getId(),
                    employee.getTypeOfEmployee()));
        });
    }

    /**
     * @param project
     */
    private void updTask(Project project){
        log.info(project.getTask().toString());
        project.getTask().forEach(task->{
            execute(String.format(Constants.UPDATE_PrID_TASK,
                    checkType(task).getData().toString().toUpperCase(),
                    project.getId(),
                    task.getId()
            ));
            log.info(String.format(Constants.UPDATE_PrID_TASK,
                    checkType(task).getData().toString().toUpperCase(),
                    project.getId(),
                    task.getId()
            ));
        });
    }

    /**
     * @param element
     * @param <T>
     * @return
     */
    private <T extends Task> Result<?> checkType(T element){
        switch(element.getTaskType()){
            case BASE_TASK:
                return new Result<>(Complete, Constants.Task);
            case DEVELOPERS_TASK:
                return new Result<>(Complete, Constants.DevelopersTask);
            case TESTERS_TASK:
                return new Result<>(Complete, Constants.TestersTask);
            default:
                return new Result<>(Fail);
        }

    }


    /**
     * @param taskDescription
     * @param money
     * @param scrumMaster
     * @param status
     * @param createdDate
     * @param deadline
     * @param lastUpdate
     * @return
     */
    private Result<Task> createBaseTask(@NonNull long id,
                                        @NonNull String taskDescription,
                                        @NonNull Double money,
                                        Employee scrumMaster,
                                        TypeOfCompletion status,
                                        List<Employee> team,
                                        @NonNull String createdDate,
                                        @NonNull String deadline,
                                        @NonNull String lastUpdate) {
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
     * @param taskDescription
     * @param money
     * @param scrumMaster
     * @param status
     * @param createdDate
     * @param deadline
     * @param lastUpdate
     * @return
     */
    private Result<DevelopersTask> createDevelopersTask(@NonNull long id,
                                                        @NonNull String taskDescription,
                                                        @NonNull Double money,
                                                        @NonNull Employee scrumMaster,
                                                        @NonNull TypeOfCompletion status,
                                                        List<Employee> team,
                                                        @NonNull String createdDate,
                                                        @NonNull String deadline,
                                                        @NonNull String lastUpdate) {
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
     * @param taskDescription
     * @param money
     * @param scrumMaster
     * @param status
     * @param createdDate
     * @param deadline
     * @param lastUpdate
     * @return
     */
    private Result<TestersTask> createTestersTask(@NonNull long id,
                                                  @NonNull String taskDescription,
                                                  @NonNull Double money,
                                                  @NonNull Employee scrumMaster,
                                                  @NonNull TypeOfCompletion status,
                                                  List<Employee> team,
                                                  @NonNull String createdDate,
                                                  @NonNull String deadline,
                                                  @NonNull String lastUpdate) {
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
     * @param <T>
     * @return
     */
    public <T extends Task> List<T> getTaskRecords(Class<T> cl){
        try {
            List<T> taskList = new ArrayList<>();
            ResultSet setTask = getTaskRecords(String.format(Constants.SELECT_FOR_ALL,
                    cl.getSimpleName().toUpperCase()));
            List<T> tasks = new ArrayList<>();
            while (setTask.next()) {
                switch(TaskTypes.valueOf(setTask.getString(Constants.TASK_TASK_TYPES))){
                    case BASE_TASK:
                        Task task = new Task();
                        setTask(setTask,task);
                        tasks.add((T) task);
                        break;
                    case DEVELOPERS_TASK:
                        DevelopersTask developersTask = new DevelopersTask();
                        setDevelopersTask(setTask,developersTask);
                        tasks.add((T) developersTask);
                        break;
                    case TESTERS_TASK:
                        TestersTask testersTask = new TestersTask();
                        setTestersTask(setTask,testersTask);
                        tasks.add((T) testersTask);
                        break;
                    default:
                        return new ArrayList<>();
                }
            }
            return tasks;
        } catch (SQLException e) {
            log.error(e);
            return new ArrayList<>();
        }
    }
    public <T extends Employee> List<T> getEmployeeRecords(Class<T> cl){
        try {
            List<T> employeeList = new ArrayList<>();
            ResultSet setEmployee = getTaskRecords(String.format(Constants.SELECT_FOR_ALL,
                    cl.getSimpleName().toUpperCase()));
            while (setEmployee.next()) {
                T employee = (T) new Employee();
                setBasicEmployee(setEmployee, employee);
                employeeList.add(employee);
            }
            return employeeList;
        } catch (SQLException e) {
            log.error(e);
            return new ArrayList<>();
        }
    }
    public List<Project> getProjectRecords(){
        try {
            List<Project> taskList = new ArrayList<>();
            ResultSet setProject = getTaskRecords(String.format(Constants.SELECT_FOR_ALL,
                    Project.class.getSimpleName().toUpperCase()));
            while (setProject.next()) {
                Project project =  new Project();
                setProject(setProject, project);
                taskList.add(project);
            }
            return taskList;
        } catch (SQLException e) {
            log.error(e);
            return new ArrayList<>();
        }
    }

    /**
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws IOException
     */
    private Connection getConnection() throws ClassNotFoundException, SQLException, IOException {
        connection = DriverManager.getConnection(
                getConfigurationEntry(Constants.DB_CONNECT),
                getConfigurationEntry(Constants.DB_USER),
                getConfigurationEntry(Constants.DB_PASS)
        );
        return connection;
    }

    /**
     * @param sql
     * @return
     */
    private Result execute(String sql){
        try {
            PreparedStatement statement = getConnection().prepareStatement(sql);
            statement.executeUpdate();
            getConnection().close();
            return new Result(Outcomes.Complete);
        } catch (SQLException | IOException | ClassNotFoundException e) {
            log.error(e);
            return new Result(Outcomes.Fail);
        }
    }

    /**
     * @param sql
     * @return
     */
    private ResultSet getTaskRecords(String sql) {
        log.info(sql);
        try {
            PreparedStatement statement = getConnection().prepareStatement(sql);
            getConnection().close();
            return statement.executeQuery();
        } catch (SQLException | ClassNotFoundException | IOException e) {
            log.info(e);
        }
        return null;
    }

    /**
     * @param cl
     * @param id
     * @return
     */
    private ResultSet setResById(Class cl, long id){
        ResultSet set = getTaskRecords(String.format(Constants.SELECT_ALL,
                cl.getSimpleName().toLowerCase(),id));
        return set;
    }

    public Result<Long> getEmployeeByParam(Employee employee) {
        try {
            ResultSet set = getTaskRecords(String.format(Constants.SELECT_EMPLOYEE,
                    employee.getFirstName(),
                    employee.getLastName(),
                    employee.getLogin(),
                    employee.getPassword(),
                    employee.getEmail(),
                    employee.getToken(),
                    employee.getDepartment(),
                    employee.getTypeOfEmployee().toString()));
            long id = 0;
            if (set != null) {
                set.next();
                id = set.getLong(Constants.EMPLOYEE_ID);
            }
            return new Result<>(Complete, id);
        } catch (SQLException e) {
            log.error(e);
            return new Result<>(Fail);
        }
    }

    public Result<Long> getDeveloperByParam(Developer developer) {
        try {
            ResultSet set = getTaskRecords(String.format(Constants.SELECT_DEVELOPER,
                    developer.getFirstName(),
                    developer.getLastName(),
                    developer.getLogin(),
                    developer.getPassword(),
                    developer.getEmail(),
                    developer.getToken(),
                    developer.getDepartment(),
                    developer.getTypeOfEmployee().toString(),
                    developer.getStatus(),
                    developer.getProgrammingLanguage()));
            long id = 0;
            if (set != null) {
                set.next();
                id = set.getLong(Constants.DEVELOPER_ID);
            }
            return new Result<>(Complete, id);
        } catch (SQLException e) {
            log.error(e);
            return new Result<>(Fail);
        }
    }

    public Result<Long> getTesterByParam(Tester tester) {
        try {
            ResultSet set = getTaskRecords(String.format(Constants.SELECT_TESTER,
                    tester.getFirstName(),
                    tester.getLastName(),
                    tester.getLogin(),
                    tester.getPassword(),
                    tester.getEmail(),
                    tester.getToken(),
                    tester.getDepartment(),
                    tester.getTypeOfEmployee().toString(),
                    tester.getStatus().toString(),
                    tester.getProgrammingLanguage().toString(),
                    tester.getTypeOfTester().toString()));
            long id = 0;
            if (set != null) {
                set.next();
                id = set.getLong(Constants.TESTER_ID);
            }
            return new Result<>(Complete, id);
        } catch (SQLException e) {
            log.error(e);
            return new Result<>(Fail);
        }
    }

    public Result<Long> getProjectByParam(Project project) {
        try {
            ResultSet set = getTaskRecords(String.format(Constants.SELECT_PROJECT,
                    project.getTitle(),
                    project.getTakeIntoDevelopment()));
            long id = 0;
            if (set != null) {
                set.next();
                id = set.getLong(Constants.PROJECT_ID);
            }
            return new Result<>(Complete, id);
        } catch (SQLException e) {
            log.error(e);
            return new Result<>(Fail);
        }
    }





    public Result<Long> getTaskByParam(Task task) {
        try {
            ResultSet set = getTaskRecords(String.format(Constants.SELECT_TASK, task.getTaskDescription(),
                    task.getMoney(), task.getScrumMaster().getId(), task.getStatus(), task.getCreatedDate(), task.getDeadline(),
                    task.getLastUpdate(), task.getTaskType().toString()));
            long id = 0;
            if (set != null) {
                set.next();
                id = set.getLong(Constants.TASK_ID);
            }
            return new Result<>(Complete, id);
        } catch (SQLException e) {
            log.error(e);
            return new Result<>(Fail);
        }
    }


    public Result<Long> getDevelopersTaskByParam(DevelopersTask developersTask) {
        try {
            ResultSet set = getTaskRecords(String.format(Constants.SELECT_DEVELOPERSTASK, developersTask.getTaskDescription(),
                    developersTask.getMoney(), developersTask.getScrumMaster().getId(), developersTask.getStatus(), developersTask.getCreatedDate(), developersTask.getDeadline(),
                    developersTask.getLastUpdate(), developersTask.getTaskType().toString(), developersTask.getDeveloperComments(), developersTask.getDeveloperTaskType().toString()));
            long id = 0;
            if (set != null) {
                set.next();
                id = set.getLong(Constants.TASK_ID);
            }
            return new Result<>(Complete, id);
        } catch (SQLException e) {
            log.error(e);
            return new Result<>(Fail);
        }
    }

    public Result<Long> getTestersTaskByParam(TestersTask task) {
        try {
            ResultSet set = getTaskRecords(String.format(Constants.SELECT_TESTERSTASK, task.getTaskDescription(),
                    task.getMoney(), task.getScrumMaster().getId(), task.getStatus(), task.getCreatedDate(), task.getDeadline(),
                    task.getLastUpdate(), task.getTaskType().toString(),task.getBugStatus().toString(),task.getBugDescription()));
            long id = 0;
            if (set != null) {
                set.next();
                id = set.getLong(Constants.TASK_ID);
            }
            return new Result<>(Complete, id);
        } catch (SQLException e) {
            log.error(e);
            return new Result<>(Fail);
        }
    }
}
