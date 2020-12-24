package ru.sfedu.groupappcontrol.api;

import lombok.NonNull;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.groupappcontrol.Constants;
import ru.sfedu.groupappcontrol.Result;
import ru.sfedu.groupappcontrol.models.*;
import ru.sfedu.groupappcontrol.models.enums.*;
import ru.sfedu.groupappcontrol.utils.CustomLogger;

import java.io.IOException;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static ru.sfedu.groupappcontrol.models.enums.Outcomes.Complete;
import static ru.sfedu.groupappcontrol.models.enums.Outcomes.Fail;
import static ru.sfedu.groupappcontrol.utils.ConfigurationUtil.getConfigurationEntry;

/**
 * The type Data api jdbc.
 */
@SuppressWarnings("JavaDoc")
public class DataProviderJdbc implements DataProvider {

    public Connection connection;
    public static final Logger log = LogManager.getLogger(DataProviderJdbc.class);

    public void initDataSource() {

    }
    @Override
    public Result<Task> getTaskById(long id) {
        log.info(CustomLogger.startFunc());
        try {
            ResultSet set = setResById(Task.class, id);
            log.debug(set);
            if (set != null && set.next()) {
                Task task = new Task();
                setBasicTask(set, task);
                log.info(CustomLogger.endFunc(task));
                return new Result<>(Outcomes.Complete, task);
            } else {
                return new Result<>(Outcomes.Fail);
            }

        } catch (SQLException e) {
            log.error(e);
            log.info(CustomLogger.endFunc(e));
            return new Result<>(Outcomes.Fail);
        }
    }

    @Override
    public Result<DevelopersTask> getDevelopersTaskById(long id) {
        try {
            log.info(CustomLogger.startFunc());
            ResultSet set = setResById(DevelopersTask.class, id);
            log.debug(set);
            if (set != null && set.next()) {
                DevelopersTask developersTask = new DevelopersTask();
                setBasicTask(set, developersTask);
                log.debug(developersTask);
                developersTask.setDeveloperComments(set.getString(Constants.DEVELOPERS_TASK_COMMENTS));
                log.debug(developersTask);
                developersTask.setDeveloperTaskType(DeveloperTaskType.valueOf(set.getString(Constants.DEVELOPERS_TASK_TYPE)));
                log.debug(developersTask);
                log.info(CustomLogger.endFunc(developersTask));
                return new Result<>(Outcomes.Complete, developersTask);
            } else {
                log.info(CustomLogger.endFunc());
                return new Result<>(Outcomes.Fail);
            }
        } catch (SQLException e) {
            log.info(CustomLogger.endFunc(e));
            log.error(e);
            return new Result<>(Outcomes.Fail);
        }
    }

    @Override
    public Result<TestersTask> getTestersTaskById(long id) {
        try {
            log.info(CustomLogger.startFunc());
            ResultSet set = setResById(TestersTask.class, id);
            log.debug(set);
            if (set != null && set.next()) {
                TestersTask testersTask = new TestersTask();
                setBasicTask(set, testersTask);
                log.debug(testersTask);
                testersTask.setBugStatus(BugStatus.valueOf(set.getString(Constants.TESTERSTASK_BUGSTATUS)));
                log.debug(testersTask);
                testersTask.setBugDescription(set.getString(Constants.TESTERSTASK_DESCRIPTION));
                log.debug(testersTask);
                log.info(CustomLogger.endFunc(testersTask));
                return new Result<>(Outcomes.Complete, testersTask);
            } else {
                log.info(CustomLogger.endFunc());
                return new Result<>(Outcomes.Fail);
            }
        } catch (SQLException e) {
            log.error(e);
            log.info(CustomLogger.endFunc(e));
            return new Result<>(Outcomes.Fail);
        }
    }

    @Override
    public Result<Employee> getEmployeeById(long id) {
        try {
            log.info(CustomLogger.startFunc());
            ResultSet set = setResById(Employee.class, id);
            log.debug(set);
            if (set != null && set.next()) {
                Employee employee = new Employee();
                setBasicEmployee(set, employee);
                log.debug(employee);
                log.info(CustomLogger.endFunc(employee));
                return new Result<>(Outcomes.Complete, employee);
            } else {
                log.info(CustomLogger.endFunc());
                return new Result<>(Outcomes.Fail);
            }
        } catch (SQLException e) {
            log.error(e);
            log.info(CustomLogger.endFunc(e));
            return new Result<>(Outcomes.Fail);
        }
    }

    @Override
    public Result<Developer> getDeveloperById(long id) {
        try {
            log.info(CustomLogger.startFunc());
            ResultSet set = setResById(Developer.class, id);
            log.debug(set);
            if (set != null && set.next()) {
                Developer developer = new Developer();
                setBasicEmployee(set, developer);
                log.debug(developer);
                developer.setStatus(TypeOfDevelopers.valueOf(set.getString(Constants.DEVELOPER_TYPE_OF_DEVELOPER)));
                log.debug(developer);
                developer.setProgrammingLanguage(ProgrammingLanguage.valueOf(set.getString(Constants.DEVELOPER_TYPE_OF_PROGRAMMING_LANGUAGE)));
                log.debug(developer);
                log.info(CustomLogger.endFunc(developer));
                return new Result<>(Outcomes.Complete, developer);
            } else {
                log.info(CustomLogger.endFunc());
                return new Result<>(Outcomes.Fail);
            }
        } catch (SQLException e) {
            log.error(e);
            log.info(CustomLogger.endFunc(e));
            return new Result<>(Outcomes.Fail);
        }
    }

    @Override
    public Result<Tester> getTesterById(long id) {
        try {
            log.info(CustomLogger.startFunc());
            ResultSet set = setResById(Tester.class, id);
            log.debug(set);
            if (set != null && set.next()) {
                Tester tester = new Tester();
                setBasicEmployee(set, tester);
                log.debug(tester);
                tester.setStatus(TypeOfDevelopers.valueOf(set.getString(Constants.TESTER_TYPE_OF_DEVELOPER)));
                log.debug(tester);
                tester.setProgrammingLanguage(ProgrammingLanguage.valueOf(set.getString(Constants.TESTER_TYPE_OF_PROGRAMMING_LANGUAGE)));
                log.debug(tester);
                tester.setTypeOfTester(TypeOfTester.valueOf(set.getString(Constants.TESTER_TYPE_OF_TESTER)));
                log.info(CustomLogger.endFunc(tester));
                return new Result<>(Outcomes.Complete, tester);
            } else {
                log.info(CustomLogger.endFunc());
                return new Result<>(Outcomes.Fail);
            }
        } catch (SQLException e) {
            log.error(e);
            log.info(CustomLogger.endFunc(e));
            return new Result<>(Outcomes.Fail);
        }
    }

    @Override
    public Result<Project> getProjectByID(long id) {
        try {
            log.info(CustomLogger.startFunc());
            ResultSet set = setResById(Project.class, id);
            log.debug(set);
            if (set != null && set.next()) {
                Project project = new Project();
                setProject(set, project);
                log.debug(project);
                log.info(CustomLogger.endFunc(project));
                return new Result<>(Outcomes.Complete, project);
            } else {
                log.info(CustomLogger.endFunc());
                return new Result<>(Outcomes.Fail);
            }
        } catch (SQLException e) {
            log.error(e);
            log.info(CustomLogger.endFunc(e));
            return new Result<>(Outcomes.Fail);
        }
    }

    @Override
    public Result<Void> insertTask(Task task) {
        try {
            log.info(CustomLogger.startFunc());
            if(!isValidTask(task)){
                log.info(CustomLogger.endFunc());
                return new Result<>(Fail);
            }
            boolean exist = execute(String.format(
                    Constants.INSERT_TASK, task.getTaskDescription(),
                    task.getMoney(), task.getScrumMaster().getId(), task.getStatus(), task.getCreatedDate(), task.getDeadline(),
                    task.getLastUpdate(), task.getTaskType().toString())).getStatus() == Outcomes.Complete;
            log.debug(exist);
            ResultSet set = getTaskRecords(String.format(Constants.SELECT_TASK, task.getTaskDescription(),
                    task.getMoney(), task.getScrumMaster().getId(), task.getStatus(), task.getCreatedDate(), task.getDeadline(),
                    task.getLastUpdate(), task.getTaskType().toString()));
            log.debug(exist);
            set.next();
            Task task1 = new Task();
            task1.setId(set.getLong(Constants.TASK_ID));
            log.info(task1.getId());
            task1.setTaskDescription(task.getTaskDescription());
            log.debug(task1);
            task1.setMoney(task.getMoney());
            log.debug(task1);
            task1.setScrumMaster(task.getScrumMaster());
            log.debug(task1);
            task1.setStatus(task.getStatus());
            log.debug(task1);
            task1.setCreatedDate(task.getCreatedDate());
            log.debug(task1);
            task1.setDeadline(task.getDeadline());
            log.debug(task1);
            task1.setLastUpdate(task.getLastUpdate());
            log.debug(task1);
            task1.setTaskType(task.getTaskType());
            log.debug(task1);
            task1.setTeam(task.getTeam());
            log.debug(task1);
            log.info(task);
            taskToEmployeeMapping(task1);
            log.info(CustomLogger.endFunc(task, task1));
            return (exist) ? new Result<>(Outcomes.Complete) : new Result<>(Outcomes.Fail);
        } catch (Exception e) {
            log.error(e);
            log.info(CustomLogger.endFunc(e));
            return new Result<>(Outcomes.Fail);
        }
    }

    @Override
    public Result<Void> insertDevelopersTask(DevelopersTask task) {
        try {
            log.info(CustomLogger.startFunc());
            if(!isValidTask(task)){
                log.info(CustomLogger.endFunc());
                return new Result<>(Fail);
            }
            boolean exist = execute(String.format(
                    Constants.INSERT_DEVELOPERS_TASK, task.getTaskDescription(),
                    task.getMoney(), task.getScrumMaster().getId(), task.getStatus(), task.getCreatedDate(), task.getDeadline(),
                    task.getLastUpdate(), task.getTaskType().toString(), task.getDeveloperComments(), task.getDeveloperTaskType().toString()))
                    .getStatus() == Outcomes.Complete;
            log.debug(exist);
            ResultSet set = getTaskRecords(String.format(Constants.SELECT_DEVELOPERSTASK, task.getTaskDescription(),
                    task.getMoney(), task.getScrumMaster().getId(), task.getStatus(), task.getCreatedDate(), task.getDeadline(),
                    task.getLastUpdate(), task.getTaskType().toString(), task.getDeveloperComments(), task.getDeveloperTaskType().toString()));
            log.debug(task);
            set.next();
            DevelopersTask developersTask = new DevelopersTask();
            developersTask.setId(set.getLong(Constants.TASK_ID));
            log.debug(developersTask);
            developersTask.setTaskDescription(task.getTaskDescription());
            log.debug(developersTask);
            developersTask.setMoney(task.getMoney());
            log.debug(developersTask);
            developersTask.setScrumMaster(task.getScrumMaster());
            log.debug(developersTask);
            developersTask.setStatus(task.getStatus());
            log.debug(developersTask);
            developersTask.setCreatedDate(task.getCreatedDate());
            log.debug(developersTask);
            developersTask.setDeadline(task.getDeadline());
            log.debug(developersTask);
            developersTask.setLastUpdate(task.getLastUpdate());
            log.debug(developersTask);
            developersTask.setTaskType(task.getTaskType());
            log.debug(developersTask);
            developersTask.setTeam(task.getTeam());
            log.debug(developersTask);
            developersTask.setDeveloperComments(task.getDeveloperComments());
            log.debug(developersTask);
            developersTask.setDeveloperTaskType(task.getDeveloperTaskType());
            log.debug(developersTask);
            log.info(task);
            taskToEmployeeMapping(developersTask);
            log.info(CustomLogger.endFunc(developersTask));
            return (exist) ? new Result<>(Outcomes.Complete) : new Result<>(Outcomes.Fail);
        } catch (Exception e) {
            log.error(e);
            log.info(CustomLogger.endFunc(e));
            return new Result<>(Outcomes.Fail);
        }
    }

    @Override
    public Result<Void> insertTestersTask(TestersTask task) {
        try {
            log.info(CustomLogger.startFunc());
            if(!isValidTask(task)){
                log.info(CustomLogger.endFunc());
                return new Result<>(Fail);
            }
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
            log.debug(exist);
            ResultSet set = getTaskRecords(String.format(Constants.SELECT_TESTERSTASK, task.getTaskDescription(),
                    task.getMoney(), task.getScrumMaster().getId(), task.getStatus(), task.getCreatedDate(), task.getDeadline(),
                    task.getLastUpdate(), task.getTaskType().toString(), task.getBugStatus().toString(), task.getBugDescription()));
            log.debug(set);
            set.next();
            TestersTask testersTask = new TestersTask();
            testersTask.setId(set.getLong(Constants.TASK_ID));
            log.debug(testersTask);
            testersTask.setTaskDescription(task.getTaskDescription());
            log.debug(testersTask);
            testersTask.setMoney(task.getMoney());
            log.debug(testersTask);
            testersTask.setScrumMaster(task.getScrumMaster());
            log.debug(testersTask);
            testersTask.setStatus(task.getStatus());
            log.debug(testersTask);
            testersTask.setCreatedDate(task.getCreatedDate());
            log.debug(testersTask);
            testersTask.setDeadline(task.getDeadline());
            log.debug(testersTask);
            testersTask.setLastUpdate(task.getLastUpdate());
            log.debug(testersTask);
            testersTask.setTaskType(task.getTaskType());
            log.debug(testersTask);
            testersTask.setTeam(task.getTeam());
            log.debug(testersTask);
            testersTask.setBugStatus(task.getBugStatus());
            log.debug(testersTask);
            testersTask.setBugDescription(task.getBugDescription());
            log.debug(testersTask);
            log.info(task);
            taskToEmployeeMapping(testersTask);
            log.info(CustomLogger.endFunc(testersTask));
            return (exist) ? new Result<>(Outcomes.Complete) : new Result<>(Outcomes.Fail);
        } catch (Exception e) {
            log.error(e);
            log.info(CustomLogger.endFunc(e));
            return new Result<>(Outcomes.Fail);
        }
    }

    @Override
    public Result<Void> insertEmployee(Employee employee) {
        try {
            log.info(CustomLogger.startFunc());
            if(!isValidEmployee(employee)){
                log.info(CustomLogger.endFunc());
                return new Result<>(Fail);
            }
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
            log.debug(employee);
            log.info(CustomLogger.endFunc(employee));
            log.debug(exist);
            return (exist) ? new Result<>(Outcomes.Complete) : new Result<>(Outcomes.Fail);
        } catch (Exception e) {
            log.error(e);
            log.info(CustomLogger.endFunc(e));
            return new Result<>(Outcomes.Fail);
        }
    }

    @Override
    public Result<Void> insertDeveloper(Developer developer) {
        try {
            log.info(CustomLogger.startFunc());
            if(!isValidEmployee(developer)){
                log.info(CustomLogger.endFunc());
                return new Result<>(Fail);
            }
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
            log.debug(developer);
            log.info(CustomLogger.endFunc(developer));
            log.debug(exist);
            return (exist) ? new Result<>(Outcomes.Complete) : new Result<>(Outcomes.Fail);
        } catch (Exception e) {
            log.error(e);
            log.info(CustomLogger.endFunc(e));
            return new Result<>(Outcomes.Fail);
        }
    }

    @Override
    public Result<Void> insertTester(Tester tester) {
        try {
            log.info(CustomLogger.startFunc());
            if(!isValidEmployee(tester)){
                log.info(CustomLogger.endFunc());
                return new Result<>(Fail);
            }
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
            log.debug(tester);
            log.info(CustomLogger.endFunc(tester));
            log.debug(exist);
            return (exist) ? new Result<>(Outcomes.Complete) : new Result<>(Outcomes.Fail);
        } catch (Exception e) {
            log.error(e);
            log.info(CustomLogger.endFunc(e));
            return new Result<>(Outcomes.Fail);
        }
    }

    @Override
    public Result<Project> insertProject(Project project) {
        try {
            log.info(CustomLogger.startFunc());
            if(!isValidProject(project)){
                log.info(CustomLogger.endFunc());
                return new Result<>(Fail);
            }
            boolean exist = execute(String.format(
                    Constants.INSERT_PROJECT,
                    project.getTitle(),
                    project.getTakeIntoDevelopment())).
                    getStatus() == Outcomes.Complete;
            log.info(project);
            log.debug(exist);
            ResultSet set = getTaskRecords(String.format(Constants.SELECT_PROJECT, project.getTitle(),
                    project.getTakeIntoDevelopment()));
            log.debug(set);
            set.next();
            Project project1 = new Project();
            log.debug(project1);
            project1.setId(set.getLong(Constants.PROJECT_ID));
            log.debug(project1);
            project1.setTitle(project.getTitle());
            log.debug(project1);
            project1.setTakeIntoDevelopment(project.getTakeIntoDevelopment());
            log.debug(project1);
            project1.setTask(project.getTask());
            log.debug(project1);
            updTask(project1);
            log.debug(project1);
            log.info(CustomLogger.endFunc(project1));
            return (exist) ? new Result<>(Outcomes.Complete) : new Result<>(Outcomes.Fail);
        } catch (Exception e) {
            log.error(e);
            log.info(CustomLogger.endFunc(e));
            return new Result<>(Outcomes.Fail);
        }
    }

    @Override
    public Result<Void> deleteTask(long id) {
        try {
            log.info(CustomLogger.startFunc());
            return (execute(String.format(Constants.DELETE_TASK, id)).getStatus() == Outcomes.Complete) ?
                    new Result<>(Outcomes.Complete) : new Result<>(Outcomes.Fail);
        } catch (Exception e) {
            log.error(e);
            log.info(CustomLogger.endFunc(e));
            return new Result<>(Outcomes.Fail);
        }
    }

    @Override
    public Result<Void> deleteDevelopersTask(long id) {
        try {
            log.info(CustomLogger.startFunc());
            return (execute(String.format(Constants.DELETE_DEVELOPERS_TASK, id)).getStatus() == Outcomes.Complete) ?
                    new Result<>(Outcomes.Complete) : new Result<>(Outcomes.Fail);
        } catch (Exception e) {
            log.error(e);
            log.info(CustomLogger.endFunc(e));
            return new Result<>(Outcomes.Fail);
        }
    }

    @Override
    public Result<Void> deleteTestersTask(long id) {
        try {
            log.info(CustomLogger.startFunc());
            boolean a = execute(String.format(Constants.DELETE_TESTERS_TASK, id)).getStatus() == Outcomes.Complete;
            log.debug(a);
            log.info(CustomLogger.endFunc(a));
            if (a) return new Result<>(Outcomes.Complete);
        } catch (Exception e) {
            log.error(e);
            log.info(CustomLogger.endFunc(e));
        }
        return new Result<>(Outcomes.Fail);
    }

    @Override
    public Result<Void> deleteEmployee(long id) {
        try {
            log.info(CustomLogger.startFunc());
            boolean a = execute(String.format(Constants.DELETE_EMPLOYEE, id)).getStatus() == Outcomes.Complete;
            log.debug(a);
            log.info(CustomLogger.endFunc(a));
            if (a) return new Result<>(Outcomes.Complete);
        } catch (Exception e) {
            log.info(CustomLogger.endFunc(e));
            log.error(e);
        }
        return new Result<>(Outcomes.Fail);
    }

    @Override
    public Result<Void> deleteDeveloper(long id) {
        try {
            log.info(CustomLogger.startFunc());
            boolean a = execute(String.format(Constants.DELETE_DEVELOPER, id)).getStatus() == Outcomes.Complete;
            log.debug(a);
            log.info(CustomLogger.endFunc(a));
            if (a) return new Result<>(Outcomes.Complete);
        } catch (Exception e) {
            log.info(CustomLogger.endFunc(e));
            log.error(e);
        }
        return new Result<>(Outcomes.Fail);
    }

    @Override
    public Result<Void> deleteTester(long id) {
        try {
            log.info(CustomLogger.startFunc());
            boolean a = execute(String.format(Constants.DELETE_TESTER, id)).getStatus() == Outcomes.Complete;
            log.debug(a);
            log.info(CustomLogger.endFunc(a));
            if (a) return new Result<>(Outcomes.Complete);
        } catch (Exception e) {
            log.info(CustomLogger.endFunc(e));
            log.error(e);
        }
        return new Result<>(Outcomes.Fail);
    }

    @Override
    public Result<Void> deleteProject(long id) {
        try {
            log.info(CustomLogger.startFunc());
            boolean a = execute(String.format(Constants.DELETE_PROJECT, id)).getStatus() == Outcomes.Complete;
            log.debug(a);
            log.info(CustomLogger.endFunc(a));
            if (a) return new Result<>(Outcomes.Complete);
        } catch (Exception e) {
            log.info(CustomLogger.endFunc(e));
            log.error(e);
        }
        return new Result<>(Outcomes.Fail);
    }

    @Override
    public Result<Void> updateTask(Task task) {
        try {
            log.info(CustomLogger.startFunc());
            if(!isValidTask(task)){
                return new Result<>(Fail);
            }
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
            log.debug(a);
            log.debug(task);
            log.info(CustomLogger.endFunc(a));
            if (a) return new Result<>(Outcomes.Complete);
        } catch (Exception e) {
            log.info(CustomLogger.endFunc(e));
            log.error(e);
        }
        return new Result<>(Outcomes.Fail);
    }

    @Override
    public Result<Void> updateDevelopersTask(DevelopersTask developersTask) {
        try {
            log.info(CustomLogger.startFunc());
            if(!isValidTask(developersTask)){
                return new Result<>(Fail);
            }
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
            log.debug(a);
            log.debug(developersTask);
            log.info(CustomLogger.endFunc(a));
            if (a) return new Result<>(Outcomes.Complete);
        } catch (Exception e) {
            log.info(CustomLogger.endFunc(e));
            log.error(e);
        }
        return new Result<>(Outcomes.Fail);
    }

    @Override
    public Result<Void> updateTestersTask(TestersTask testersTask) {
        try {
            log.info(CustomLogger.startFunc());
            if(!isValidTask(testersTask)){
                return new Result<>(Fail);
            }
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
            log.debug(a);
            log.debug(testersTask);
            log.info(CustomLogger.endFunc(a));
            if (a) return new Result<>(Outcomes.Complete);
        } catch (Exception e) {
            log.info(CustomLogger.endFunc(e));
            log.error(e);
        }
        return new Result<>(Outcomes.Fail);
    }

    @Override
    public Result<Void> updateEmployee(Employee employee) {
        try {
            log.info(CustomLogger.startFunc());
            if(!isValidEmployee(employee)){
                return new Result<>(Fail);
            }
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
            log.debug(a);
            log.debug(employee);
            log.info(CustomLogger.endFunc(a));
            if (a) return new Result<>(Outcomes.Complete);
        } catch (Exception e) {
            log.info(CustomLogger.endFunc(e));
            log.error(e);
        }
        return new Result<>(Outcomes.Fail);
    }

    @Override
    public Result<Void> updateDeveloper(Developer developer) {
        try {
            log.info(CustomLogger.startFunc());
            if(!isValidEmployee(developer)){
                return new Result<>(Fail);
            }
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
            log.debug(a);
            log.debug(developer);
            log.info(CustomLogger.endFunc(a));
            if (a) return new Result<>(Outcomes.Complete);
        } catch (Exception e) {
            log.info(CustomLogger.endFunc(e));
            log.error(e);
        }
        return new Result<>(Outcomes.Fail);
    }

    @Override
    public Result<Void> updateTester(Tester tester) {
        try {
            log.info(CustomLogger.startFunc());
            if(!isValidEmployee(tester)){
                return new Result<>(Fail);
            }
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
            log.debug(a);
            log.debug(tester);
            log.info(CustomLogger.endFunc(a));
            if (a) return new Result<>(Outcomes.Complete);
        } catch (Exception e) {
            log.info(CustomLogger.endFunc(e));
            log.error(e);
        }
        return new Result<>(Outcomes.Fail);
    }

    @Override
    public Result<Project> updateProject(Project project) {
        try {
            log.info(CustomLogger.startFunc());
            if(!isValidProject(project)){
                return new Result<>(Fail);
            }
            boolean a = execute(String.format(Constants.UPDATE_PROJECT,
                    project.getTitle(),
                    project.getTakeIntoDevelopment(),
                    project.getId()))
                    .getStatus() == Outcomes.Complete;
            log.debug(a);
            log.debug(project);
            log.info(CustomLogger.endFunc(a));
            if (a) return new Result<>(Outcomes.Complete);
        } catch (Exception e) {
            log.info(CustomLogger.endFunc(e));
            log.error(e);
        }
        return new Result<>(Outcomes.Fail);
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
        log.info(CustomLogger.startFunc());
        switch (taskType) {
            case BASE_TASK:
                log.debug(TaskTypes.BASE_TASK);
                Task baseTask = createBaseTask(id, taskDescription, money, scrumMaster, status, team, createdDate, deadline, lastUpdate).getData();
                log.debug(baseTask);
                log.info(CustomLogger.endFunc(baseTask));
                return isValidTask(baseTask) ? new Result<>(Complete, baseTask): new Result<>(Fail);
            case DEVELOPERS_TASK:
                log.debug(TaskTypes.DEVELOPERS_TASK);
                DevelopersTask developersTask = createDevelopersTask(id, taskDescription, money, scrumMaster, status, team, createdDate, deadline, lastUpdate).getData();
                developersTask.setDeveloperComments(Constants.BaseComment);
                developersTask.setDeveloperTaskType(DeveloperTaskType.DEVELOPMENT);
                log.debug(developersTask);
                log.info(CustomLogger.endFunc(developersTask));
                return isValidTask(developersTask) ? new Result<>(Complete, developersTask): new Result<>(Fail);
            case TESTERS_TASK:
                log.debug(TaskTypes.TESTERS_TASK);
                TestersTask testersTask = createTestersTask(id, taskDescription, money, scrumMaster, status, team, createdDate, deadline, lastUpdate).getData();
                testersTask.setBugStatus(BugStatus.FIND_BUGS);
                testersTask.setBugDescription(Constants.BaseComment);
                log.debug(testersTask);
                log.info(CustomLogger.endFunc(testersTask));
                return isValidTask(testersTask) ? new Result<>(Complete, testersTask): new Result<>(Fail);
            default:
                log.info(CustomLogger.endFunc());
                return new Result<>(Fail);
        }
    }

    @Override
    public Result<Task> getTasks(long id) {
        log.info(CustomLogger.startFunc());
        List<Task> taskList = new ArrayList<>();
        taskList.addAll(getTaskRecords(Task.class));
        log.debug(taskList);
        taskList.addAll(getTaskRecords(DevelopersTask.class));
        log.debug(taskList);
        taskList.addAll(getTaskRecords(TestersTask.class));
        log.debug(taskList);
        Optional<Task> optTask = taskList.stream().filter(el -> el.getId() == id).findAny();
        log.debug(optTask);
        log.info(CustomLogger.endFunc(optTask));
        return optTask.map(task -> new Result<>(Outcomes.Complete, task)).orElseGet(() ->
                new Result<>(Outcomes.Fail));
    }

    @Override
    public List<Task> getAllTask() {
        log.info(CustomLogger.startFunc());
        List<Task> taskList = new ArrayList<>();
        taskList.addAll(getTaskRecords(Task.class));
        log.debug(taskList);
        taskList.addAll(getTaskRecords(DevelopersTask.class));
        log.debug(taskList);
        taskList.addAll(getTaskRecords(TestersTask.class));
        log.debug(taskList);
        log.info(CustomLogger.endFunc(taskList));
        return taskList;
    }

    @Override
    public Result<Task> getTasksByUser(long userId, long taskId) {
        try {
            log.info(CustomLogger.startFunc());
            ResultSet res = getTaskRecords(String.format(Constants.SELECT_TASK_EMPLOYEE_MAPPING,
                    userId, taskId));
            log.debug(res);
            Task task = new Task();
            while (res.next()) {
                setBasicTask(res, task);
                log.debug(task);
            }
            log.info(CustomLogger.endFunc(task));
            return new Result<>(Complete, task);
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
            log.debug(status);
            if(stringIsValid(status)){
                return new Result<>(Fail);
            }
            boolean exist = execute(String.format(Constants.UPDATE_TASK_STATUS,
                    status, id)).getStatus() == Complete;
            log.debug(exist);
            log.info(CustomLogger.endFunc(exist));
            return (exist) ? new Result<>(Outcomes.Complete) : new Result<>(Outcomes.Fail);
        } catch (Exception e) {
            log.error(e);
            log.info(CustomLogger.endFunc(e));
            return new Result<>(Fail);
        }
    }

    @Override
    public Result<Double> calculateTaskCost(Task task) {
        try {
            log.info(CustomLogger.startFunc());
            if(!isValidTask(task)){
                return new Result<>(Fail);
            }
            SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT, Locale.ENGLISH);
            java.util.Date firstDate = sdf.parse(String.valueOf(task.getCreatedDate()));
            log.debug(firstDate);
            Date secondDate = sdf.parse(String.valueOf(task.getDeadline()));
            log.debug(secondDate);
            long diffInMillies = Math.abs(secondDate.getTime() - firstDate.getTime());
            log.debug(diffInMillies);
            long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
            log.debug(diff);
            double res = (double) diff * task.getMoney();
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
    public Result<Void> writeBaseTaskComment(long id, String comment) {
        log.info(CustomLogger.startFunc());
        log.debug(comment);
        if(stringIsValid(comment)){
            log.info(CustomLogger.endFunc());
            return new Result<>(Outcomes.Fail);
        }
        boolean exist = execute(String.format(Constants.UPDATE_TASK_COMMENT,
                comment, id)).getStatus() == Complete;
        log.debug(exist);
        log.info(CustomLogger.endFunc(exist));
        return (exist) ? new Result<>(Outcomes.Complete) : new Result<>(Outcomes.Fail);
    }

    @Override
    public Result<Void> writeDevelopersTaskComment(long id, String comment) {
        log.info(CustomLogger.startFunc());
        log.debug(comment);
        try {
            if(stringIsValid(comment)){
                log.info(CustomLogger.endFunc());
                return new Result<>(Fail);
            }
            boolean exist = execute(String.format(Constants.UPDATE_DEVELOPERSTASK_COMMENT,
                    comment, id)).getStatus() == Complete;
            log.debug(exist);
            log.info(CustomLogger.endFunc(exist));
            return (exist) ? new Result<>(Outcomes.Complete) : new Result<>(Outcomes.Fail);
        } catch (Exception e) {
            log.error(e);
            log.info(CustomLogger.endFunc(e));
            return new Result<>(Fail);
        }
    }

    @Override
    public Result<Void> writeTestersTaskComment(long id, String comment) {
        log.info(CustomLogger.startFunc());
        try {
            log.debug(comment);
            if(stringIsValid(comment)){
                log.info(CustomLogger.endFunc());
                return new Result<>(Fail);
            }
            boolean exist = execute(String.format(Constants.UPDATE_TESTERSTASK_COMMENT,
                    comment, id)).getStatus() == Complete;
            log.debug(exist);
            log.info(CustomLogger.endFunc(exist));
            return (exist) ? new Result<>(Outcomes.Complete) : new Result<>(Outcomes.Fail);
        } catch (Exception e) {
            log.error(e);
            log.info(CustomLogger.endFunc(e));
            return new Result<>(Outcomes.Fail);
        }


    }

    @Override
    public Result<List<Task>> getTaskListByScrumMaster(long id) {
        log.info(CustomLogger.startFunc());
        try {
            ResultSet set = getTaskRecords(String.format(Constants.GetTaskByScrummaster,
                    id));
            log.debug(set);
            if (set != null && set.next()) {
                Task task = new Task();
                setBasicTask(set, task);
                log.debug(task);
                List<Task> taskList = new ArrayList<>();
                taskList.add(task);
                log.debug(taskList);
                log.info(CustomLogger.endFunc(task));
                return new Result<>(Outcomes.Complete, taskList);
            } else {
                log.info(CustomLogger.endFunc());
                return new Result<>(Outcomes.Fail);
            }
        } catch (SQLException e) {
            log.error(e);
            log.info(CustomLogger.endFunc(e));
            return new Result<>(Fail);
        }
    }

    @Override
    public Result<List<DevelopersTask>> getDevelopersTaskListByScrumMaster(long id) {
        log.info(CustomLogger.startFunc());
        try {
            ResultSet set = getTaskRecords(String.format(Constants.GetDevelopersTaskByScrummaster,
                    id));
            log.debug(set);
            if (set != null && set.next()) {
                DevelopersTask developersTask = new DevelopersTask();
                setBasicTask(set, developersTask);
                log.debug(developersTask);
                developersTask.setDeveloperComments(set.getString(Constants.DEVELOPERS_TASK_COMMENTS));
                log.debug(developersTask);
                developersTask.setDeveloperTaskType(DeveloperTaskType.valueOf(set.getString(Constants.DEVELOPERS_TASK_TYPE)));
                log.debug(developersTask);
                List<DevelopersTask> developersTasks = new ArrayList<>();
                developersTasks.add(developersTask);
                log.debug(developersTasks);
                log.info(CustomLogger.endFunc(developersTask));
                return new Result<>(Outcomes.Complete, developersTasks);
            } else {
                log.info(CustomLogger.endFunc());
                return new Result<>(Outcomes.Fail);
            }
        } catch (SQLException e) {
            log.error(e);
            log.info(CustomLogger.endFunc(e));
            return new Result<>(Fail);
        }
    }

    @Override
    public Result<List<TestersTask>> getTestersTaskListByScrumMaster(long id) {
        log.info(CustomLogger.startFunc());
        try {
            ResultSet set = getTaskRecords(String.format(Constants.GetTestersTaskByScrummaster,
                    id));
            log.debug(set);
            if (set != null && set.next()) {
                TestersTask testersTask = new TestersTask();
                setBasicTask(set, testersTask);
                log.debug(testersTask);
                testersTask.setBugStatus(BugStatus.valueOf(set.getString(Constants.TESTERSTASK_BUGSTATUS)));
                log.debug(testersTask);
                testersTask.setBugDescription(set.getString(Constants.TESTERSTASK_DESCRIPTION));
                log.debug(testersTask);
                List<TestersTask> testersTasks = new ArrayList<>();
                testersTasks.add(testersTask);
                log.debug(testersTasks);
                log.info(CustomLogger.endFunc(testersTask));
                return new Result<>(Outcomes.Complete, testersTasks);
            } else {
                log.info(CustomLogger.endFunc());
                return new Result<>(Outcomes.Fail);
            }
        } catch (SQLException e) {
            log.info(CustomLogger.endFunc(e));
            log.error(e);
            return new Result<>(Fail);
        }
    }

    @Override
    public Result<Project> createProject(long id, @NonNull String title,
                                         @NonNull String takeIntoDevelopment,
                                         @NonNull List<Task> tasks) {
        log.info(CustomLogger.startFunc());
        Project project = new Project();
        project.setId(id);
        log.debug(project);
        project.setTitle(title);
        log.debug(project);
        project.setTakeIntoDevelopment(takeIntoDevelopment);
        log.debug(project);
        project.setTask(tasks);
        log.debug(project);
        log.info(CustomLogger.endFunc(project));
        return !isValidProject(project) ? new Result<>(Fail) : new Result<>(Complete, project);
    }

    @Override
    public Result<List<Project>> getProjectById(long employeeId, long projectId) {
        log.info(CustomLogger.startFunc());
        try {
            ResultSet set = getTaskRecords(String.format(Constants.GETPROJECTLISTBYWORKERID,
                    employeeId, projectId));
            log.debug(set);
            List<Project> projects = new ArrayList<>();
            if (set != null && set.next()) {
                Project project = new Project();
                setProject(set, project);
                log.debug(project);
                projects.add(project);
            }
            log.info(CustomLogger.endFunc(projects));
            return new Result<>(Complete, projects);
        } catch (SQLException e) {
            log.error(e);
            log.info(CustomLogger.endFunc(e));
            return new Result<>(Fail);
        }
    }

    @Override
    public Result<Long> calculateProjectCost(Project project) {
        log.info(CustomLogger.startFunc());
        try {
            if(!isValidProject(project)){
                log.info(CustomLogger.endFunc());
                return new Result<>(Fail);
            }
            ResultSet set = getTaskRecords(String.format(Constants.GetTaskList,
                    project.getId()));
            log.debug(set);
            List<Task> tasks = new ArrayList<>();
            if (set != null && set.next()) {
                Task task = new Task();
                setTask(set, task);
                log.debug(task);
                tasks.add(task);
            }
            double projectCost = 0.0;
            for (Task task : tasks) {
                projectCost = projectCost + (double) calculateTaskCost(task).getData();
            }
            log.info(CustomLogger.endFunc(projectCost));
            return new Result<>(Complete, (long) projectCost);
        } catch (Exception e) {
            log.error(e);
            log.info(CustomLogger.endFunc(e));
            return new Result<>(Fail);
        }
    }

    @Override
    public Result<Long> calculateProjectTime(Project project) {
        log.info(CustomLogger.startFunc());
        try {
            if(!isValidProject(project)){
                return new Result<>(Fail);
            }
            ResultSet set = getTaskRecords(String.format(Constants.GetTaskList,
                    project.getId()));
            log.debug(set);
            List<Task> tasks = new ArrayList<>();
            if (set != null && set.next()) {
                Task task = new Task();
                setTask(set, task);
                log.debug(task);
                tasks.add(task);
            }
            long resulttime = 0;
            for (Task task : tasks) {
                resulttime = resulttime + (long) calculatePrice(task).getData();
            }
            log.debug(resulttime);
            log.info(CustomLogger.endFunc(resulttime));
            return new Result<>(Complete, resulttime);
        } catch (SQLException e) {
            log.error(e);
            log.info(CustomLogger.endFunc(e));
            return new Result<>(Fail);
        }
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
        log.info(CustomLogger.startFunc());
        switch (typeOfEmployee) {
            case Employee:
                Employee employee = new Employee();
                setBasicEmployeeParams(employee,id, firstName, lastName, login,
                        password, email, token, department, typeOfEmployee);
                log.debug(employee);
                log.info(CustomLogger.endFunc(employee));
                return isValidEmployee(employee) ? new Result<>(Complete, employee): new Result<>(Fail);
            case Developer:
                Developer developer = new Developer();
                setBasicEmployeeParams(developer,id, firstName, lastName, login,
                        password, email, token, department, typeOfEmployee);
                log.debug(developer);
                developer.setStatus(TypeOfDevelopers.CUSTOM);
                log.debug(developer);
                developer.setProgrammingLanguage(ProgrammingLanguage.JAVA);
                log.debug(developer);
                log.info(CustomLogger.endFunc(developer));
                return isValidEmployee(developer) ? new Result<>(Complete, developer): new Result<>(Fail);
            case Tester:
                Tester tester = new Tester();
                setBasicEmployeeParams(tester,id, firstName, lastName, login,
                        password, email, token, department, typeOfEmployee);
                log.debug(tester);
                tester.setStatus(TypeOfDevelopers.CUSTOM);
                log.debug(tester);
                tester.setProgrammingLanguage(ProgrammingLanguage.JAVA);
                log.debug(tester);
                tester.setTypeOfTester(TypeOfTester.Custom);
                log.debug(tester);
                log.info(CustomLogger.endFunc(tester));
                return isValidEmployee(tester) ? new Result<>(Complete, tester): new Result<>(Fail);
            default:
                log.info(CustomLogger.endFunc());
                return new Result<>(Fail);
        }

    }

    @Override
    public List<Employee> getAllEmployee() {
        log.info(CustomLogger.startFunc());
        List<Employee> emplList = new ArrayList<>();
        emplList.addAll(getBaseEmployee(Employee.class));
        log.debug(emplList);
        emplList.addAll(getBaseEmployee(Developer.class));
        log.debug(emplList);
        emplList.addAll(getBaseEmployee(Tester.class));
        log.debug(emplList);
        log.info(CustomLogger.endFunc(emplList));
        return emplList;
    }

    @Override
    public Result getScrumMasterTaskList(long userId,
                                                     TaskTypes taskTypes) {
        log.info(CustomLogger.startFunc());
        switch (taskTypes){
            case BASE_TASK:
                List<Task> tasks = getTaskListByScrumMaster(userId).getData();
                log.debug(tasks);
                log.info(CustomLogger.endFunc(tasks));
                return new Result<>(Complete, tasks);
            case DEVELOPERS_TASK:
                List<DevelopersTask> developersTasks =
                        getDevelopersTaskListByScrumMaster(userId).getData();
                log.debug(developersTasks);
                log.info(CustomLogger.endFunc(developersTasks));
                return new Result<>(Complete , developersTasks);
            case TESTERS_TASK:
                List<TestersTask> testersTasks =
                        getTestersTaskListByScrumMaster(userId).getData();
                log.debug(testersTasks);
                log.info(CustomLogger.endFunc(testersTasks));
                return new Result(Complete , testersTasks);
            default:
                log.info(CustomLogger.endFunc());
                return new Result(Fail);
        }

    }

    @Override
    public Result<List<Project>> getProjectListByScrummasterId(long id) {
        log.info(CustomLogger.startFunc());
      try {
          ResultSet set = getTaskRecords(String.format(
                  Constants.GETPROJECTLISTBYSCRUMMASTERID, id));
          log.debug(set);
          List<Project> projects = new ArrayList<>();
          if (set != null && set.next()) {
              Project project = new Project();
              setProject(set, project);
              log.debug(project);
              projects.add(project);
              log.info(CustomLogger.endFunc(projects));
              return new Result<>(Complete,projects);
          }
          log.info(CustomLogger.endFunc());
          return new Result<>(Fail,new ArrayList<>());
      } catch (SQLException e) {
          log.error(e);
          log.info(CustomLogger.endFunc(e));
          return new Result<>(Fail);
      }
    }

    @Override
    public <T> Result<T> deleteRecord(Class<T> cl) {
        log.info(CustomLogger.startFunc());
        execute(String.format(Constants.DROP,
                cl.getSimpleName().toUpperCase()));
        log.info(CustomLogger.endFunc());
        return new Result<>(Outcomes.Complete);
    }

    @Override
    public void deleteAllRecord() {
        log.info(CustomLogger.startFunc());
        deleteRecord(Employee.class);
        deleteRecord(Developer.class);
        deleteRecord(Tester.class);
        deleteRecord(Task.class);
        deleteRecord(DevelopersTask.class);
        deleteRecord(TestersTask.class);
        deleteRecord(Project.class);
        execute(String.format(Constants.DROPTASKTOEMPLOYEE));
        log.info(CustomLogger.endFunc());
        //createTables();
    }

    /**
     * @param set
     * @param task
     */
    private void setBasicTask(ResultSet set,Task task){
        log.info(CustomLogger.startFunc());
        try {
            task.setId(set.getLong(Constants.TASK_ID));
            log.debug(task);
            task.setTaskDescription(set.getString(Constants.TASK_DESCRIPTION));
            log.debug(task);
            task.setMoney(set.getDouble(Constants.TASK_MONEY));
            log.debug(task);
            task.setScrumMaster(getEmployeeById(set.getLong(Constants.TASK_SCRUMMASTER)).getData());
            log.debug(task);
            task.setStatus(TypeOfCompletion.valueOf(set.getString(Constants.TASK_TYPE_OF_COMPLETION)));
            log.debug(task);
            task.setTaskType(TaskTypes.valueOf(set.getString(Constants.TASK_TASK_TYPES)));
            log.debug(task);
            task.setTeam(getEmployeeListFromTask(task));
            log.debug(task);
            task.setCreatedDate(set.getString(Constants.TASK_CREATED_DATE));
            log.debug(task);
            task.setDeadline(set.getString(Constants.TASK_DEADLINE));
            log.debug(task);
            task.setLastUpdate(set.getString(Constants.TASK_LAST_UPDATE));
            log.debug(task);
            log.info(CustomLogger.endFunc(task));
        } catch (SQLException e) {
            log.error(e);
            log.info(CustomLogger.endFunc(e));
        }
    }

    /**
     * @param task
     * @return
     */
    private List<Employee> getEmployeeListFromTask(Task task){
        log.info(CustomLogger.startFunc());
        try {
            ResultSet res = getTaskRecords(String.format(Constants.SELECT_TASK_EMPLOYEES, task.getId(),
                    task.getTaskType().toString()));
            log.debug(res);
            List<Employee> list = new ArrayList<>();
            while(res.next()) {
                switch (TypeOfEmployee.valueOf(res.getString(Constants.EMPLOYEE_TYPE_OF_EMLPOYEE))){
                    case Employee:
                        list.add(getEmployeeById(res.getLong(Constants.MAP_EMPLOYEE_ID)).getData());
                        log.debug(list);
                        log.info(CustomLogger.endFunc(list));
                        break;
                    case Developer:
                        list.add(getDeveloperById(res.getLong(Constants.MAP_EMPLOYEE_ID)).getData());
                        log.debug(list);
                        log.info(CustomLogger.endFunc(list));
                        break;
                    case Tester:
                        list.add(getTesterById(res.getLong(Constants.MAP_EMPLOYEE_ID)).getData());
                        log.debug(list);
                        log.info(CustomLogger.endFunc(list));
                }
            }
            return list;
        } catch (SQLException e) {
            log.error(e);
            log.info(CustomLogger.endFunc(e));
            return new ArrayList<>();
        }
    }

    /**
     * @param set
     * @param employee
     */
    private void setBasicEmployee(ResultSet set,Employee employee){
        log.info(CustomLogger.startFunc());
        try {
            employee.setId(set.getLong(Constants.EMPLOYEE_ID));
            log.debug(employee);
            employee.setFirstName(set.getString(Constants.EMPLOYEE_FIRSTNAME));
            log.debug(employee);
            employee.setLastName(set.getString(Constants.EMPLOYEE_LASTNAME));
            log.debug(employee);
            employee.setLogin(set.getString(Constants.EMPLOYEE_LOGIN));
            log.debug(employee);
            employee.setPassword(set.getString(Constants.EMPLOYEE_PASSWORD));
            log.debug(employee);
            employee.setEmail(set.getString(Constants.EMPLOYEE_EMAIL));
            log.debug(employee);
            employee.setToken(set.getString(Constants.EMPLOYEE_TOKEN));
            log.debug(employee);
            employee.setDepartment(set.getString(Constants.EMPLOYEE_DEPARTMENT));
            log.debug(employee);
            employee.setTypeOfEmployee(TypeOfEmployee.valueOf(set.getString(Constants.EMPLOYEE_TYPE_OF_EMLPOYEE)));
            log.debug(employee);
            log.info(CustomLogger.endFunc(employee));
        } catch (SQLException e) {
            log.info(CustomLogger.endFunc(e));
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
     *
     */
    public void createTables(){
        log.info(CustomLogger.startFunc());
        execute(String.format(Constants.CREATE_EMPLOYEE));
        execute(String.format(Constants.CREATE_DEVELOPER));
        execute(String.format(Constants.CREATE_TESTER));
        execute(String.format(Constants.CREATE_TASK));
        execute(String.format(Constants.CREATE_DEVELOPERSTASK));
        execute(String.format(Constants.CREATE_TESTERSTASK));
        execute(String.format(Constants.CREATE_PROJECT));
        execute(String.format(Constants.TASKTOEMPLOYEEMAPING));
        log.info(CustomLogger.endFunc());
    }

    /**
     * @param cl
     * @param <T>
     * @return
     */
    public <T extends Employee> List<T> getListEmployees(Class<T> cl){
        log.info(CustomLogger.startFunc());
        try {
            ResultSet set = getTaskRecords(String.format(Constants.SELECT_ALL_USERS, cl.getSimpleName().toUpperCase()));
            log.debug(set);
            List<Employee> list = new ArrayList<>();
            while (set.next()) {
                switch (TypeOfEmployee.valueOf(set.getString(Constants.EMPLOYEE_TYPE_OF_EMLPOYEE))) {
                    case Employee:
                        Employee employee =new Employee();
                        setBasicEmployee(set,employee);
                        log.debug(employee);
                        list.add(employee);
                        log.info(CustomLogger.endFunc(list));
                        break;
                    case Developer:
                        Developer developer =new Developer();
                        setBasicEmployee(set,developer);
                        developer.setStatus(TypeOfDevelopers.valueOf(set.getString(Constants.DEVELOPER_TYPE_OF_DEVELOPER)));
                        developer.setProgrammingLanguage(ProgrammingLanguage.valueOf(set.getString(Constants.DEVELOPER_TYPE_OF_PROGRAMMING_LANGUAGE)));
                        log.debug(developer);
                        list.add(developer);
                        log.info(CustomLogger.endFunc(list));
                        break;
                    case Tester:
                        Tester tester = new Tester();
                        setBasicEmployee(set,tester);
                        tester.setStatus(TypeOfDevelopers.valueOf(set.getString(Constants.TESTER_TYPE_OF_DEVELOPER)));
                        tester.setProgrammingLanguage(ProgrammingLanguage.valueOf(set.getString(Constants.TESTER_TYPE_OF_PROGRAMMING_LANGUAGE)));
                        tester.setTypeOfTester(TypeOfTester.valueOf(set.getString(Constants.TESTER_TYPE_OF_TESTER)));
                        log.debug(tester);
                        list.add(tester);
                        log.info(CustomLogger.endFunc(list));
                }
            }
            return (List<T>) list;
        } catch (SQLException e) {
            log.error(e);
            log.info(CustomLogger.endFunc(e));
            return new ArrayList<>();
        }
    }


    /**
     * @param set
     * @param project
     */
    private void setProject(ResultSet set,Project project){
        log.info(CustomLogger.startFunc());
        try {
            project.setId(set.getLong(Constants.PROJECT_ID));
            log.debug(project);
            project.setTitle(set.getString(Constants.PROJECT_TITLE));
            log.debug(project);
            project.setTakeIntoDevelopment(set.getString(Constants.PROJECT_TAKE_INTO_DEVELOPMENT));
            log.debug(project);
            log.info(CustomLogger.endFunc(project));
        } catch (SQLException e) {
            log.info(CustomLogger.endFunc(e));
            log.error(e);
        }
    }

    /**
     * @param set
     * @param task
     */
    private void setTask(ResultSet set,Task task){
        log.info(CustomLogger.startFunc());
        try {
            task.setId(set.getLong(Constants.TASK_ID));
            log.debug(task);
            task.setTaskDescription(set.getString(Constants.TASK_DESCRIPTION));
            log.debug(task);
            task.setMoney(set.getDouble(Constants.TASK_MONEY));
            log.debug(task);
            task.setScrumMaster(getEmployeeById(set.getLong(Constants.TASK_SCRUMMASTER)).getData());
            log.debug(task);
            task.setStatus(TypeOfCompletion.valueOf(set.getString(Constants.TASK_TYPE_OF_COMPLETION)));
            log.debug(task);
            task.setTaskType(TaskTypes.valueOf(set.getString(Constants.TASK_TASK_TYPES)));
            log.debug(task);
            task.setTeam(getEmployeeListFromTask(task));
            log.debug(task);
            task.setCreatedDate(set.getString(Constants.TASK_CREATED_DATE));
            log.debug(task);
            task.setDeadline(set.getString(Constants.TASK_DEADLINE));
            log.debug(task);
            task.setLastUpdate(set.getString(Constants.TASK_LAST_UPDATE));
            log.debug(task);
            log.info(CustomLogger.endFunc(task));
        } catch (SQLException e) {
            log.error(e);
            log.info(CustomLogger.endFunc(e));
        }
    }

    /**
     * @param set
     * @param task
     */
    private void setDevelopersTask(ResultSet set,DevelopersTask task){
        log.info(CustomLogger.startFunc());
        try {
            task.setId(set.getLong(Constants.TASK_ID));
            log.debug(task);
            task.setTaskDescription(set.getString(Constants.TASK_DESCRIPTION));
            log.debug(task);
            task.setMoney(set.getDouble(Constants.TASK_MONEY));
            log.debug(task);
            task.setScrumMaster(getEmployeeById(set.getLong(Constants.TASK_SCRUMMASTER)).getData());
            log.debug(task);
            task.setStatus(TypeOfCompletion.valueOf(set.getString(Constants.TASK_TYPE_OF_COMPLETION)));
            log.debug(task);
            task.setTaskType(TaskTypes.valueOf(set.getString(Constants.TASK_TASK_TYPES)));
            log.debug(task);
            task.setTeam(getEmployeeListFromTask(task));
            log.debug(task);
            task.setCreatedDate(set.getString(Constants.TASK_CREATED_DATE));
            log.debug(task);
            task.setDeadline(set.getString(Constants.TASK_DEADLINE));
            log.debug(task);
            task.setLastUpdate(set.getString(Constants.TASK_LAST_UPDATE));
            log.debug(task);
            task.setDeveloperComments(set.getString(Constants.DEVELOPERS_TASK_COMMENTS));
            log.debug(task);
            task.setDeveloperTaskType(DeveloperTaskType.valueOf(set.getString(Constants.DEVELOPERS_TASK_TYPE)));
            log.debug(task);
            log.info(CustomLogger.endFunc(task));
        } catch (SQLException e) {
            log.info(CustomLogger.endFunc(e));
            log.error(e);
        }
    }

    /**
     * @param set
     * @param task
     */
    private void setTestersTask(ResultSet set,TestersTask task){
        log.info(CustomLogger.startFunc());
        try {
            task.setId(set.getLong(Constants.TASK_ID));
            log.debug(task);
            task.setTaskDescription(set.getString(Constants.TASK_DESCRIPTION));
            log.debug(task);
            task.setMoney(set.getDouble(Constants.TASK_MONEY));
            log.debug(task);
            task.setScrumMaster(getEmployeeById(set.getLong(Constants.TASK_SCRUMMASTER)).getData());
            log.debug(task);
            task.setStatus(TypeOfCompletion.valueOf(set.getString(Constants.TASK_TYPE_OF_COMPLETION)));
            log.debug(task);
            task.setTaskType(TaskTypes.valueOf(set.getString(Constants.TASK_TASK_TYPES)));
            log.debug(task);
            task.setTeam(getEmployeeListFromTask(task));
            log.debug(task);
            task.setCreatedDate(set.getString(Constants.TASK_CREATED_DATE));
            log.debug(task);
            task.setDeadline(set.getString(Constants.TASK_DEADLINE));
            log.debug(task);
            task.setLastUpdate(set.getString(Constants.TASK_LAST_UPDATE));
            log.debug(task);
            task.setBugStatus(BugStatus.valueOf(set.getString(Constants.TESTERSTASK_BUGSTATUS)));
            log.debug(task);
            task.setBugDescription(set.getString(Constants.TESTERSTASK_DESCRIPTION));
            log.debug(task);
            log.info(CustomLogger.endFunc(task));
        } catch (SQLException e) {
            log.info(CustomLogger.endFunc(e));
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
        log.info(CustomLogger.startFunc());
        task.setId(id);
        log.debug(task);
        task.setTaskDescription(taskDescription);
        log.debug(task);
        task.setMoney(money);
        log.debug(task);
        task.setScrumMaster(scrumMaster);
        log.debug(task);
        task.setStatus(status);
        log.debug(task);
        task.setTeam(team);
        log.debug(task);
        task.setCreatedDate(createdDate);
        log.debug(task);
        task.setDeadline(deadline);
        log.debug(task);
        task.setLastUpdate(lastUpdate);
        log.debug(task);
        task.setTaskType(taskType);
        log.debug(task);
        log.info(CustomLogger.endFunc(task));
        if(!isValidTask(task)){
            log.info(CustomLogger.endFunc());
        }
    }


    /**
     * @param task
     */
    public boolean isValidTask(Task task) {
        log.info(CustomLogger.startFunc());
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
        log.info(CustomLogger.startFunc());
        return  !project.getTitle().isEmpty()
                && project.getTitle() != null
                && !project.getTakeIntoDevelopment().isEmpty()
                && project.getTakeIntoDevelopment() != null;
    }

    /**
     * @param employee
     */
    private boolean isValidEmployee(Employee employee) {
        log.info(CustomLogger.startFunc());
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
     * @param cl
     * @param <T>
     * @return
     */
    public <T extends Task> List<T> getListTasks(Class<T> cl){
        log.info(CustomLogger.startFunc());
        try {
            ResultSet set = getTaskRecords(String.format(Constants.SELECT_ALL_USERS, cl.getSimpleName().toUpperCase()));
            log.debug(set);
            List<Task> list = new ArrayList<>();
            while (set.next()) {
                switch (TaskTypes.valueOf(set.getString(Constants.TASK_TASK_TYPES))) {
                    case BASE_TASK:
                        Task task =new Task();
                        setBasicTask(set,task);
                        log.debug(task);
                        ResultSet set1 = getTaskRecords(String.format(Constants.SELECT_TASK, task.getTaskDescription(),
                                task.getMoney(), task.getScrumMaster().getId(), task.getStatus(), task.getCreatedDate(), task.getDeadline(),
                                task.getLastUpdate(), task.getTaskType().toString()));
                        log.debug(set1);
                        set1.next();
                        task.setId(set1.getLong(Constants.TASK_ID));
                        log.debug(task);
                        list.add(task);
                        log.info(CustomLogger.endFunc(task));
                        break;
                    case DEVELOPERS_TASK:
                        DevelopersTask developersTask =new DevelopersTask();
                        setBasicTask(set,developersTask);
                        log.debug(developersTask);
                        developersTask.setDeveloperComments(set.getString(Constants.DEVELOPERS_TASK_COMMENTS));
                        log.debug(developersTask);
                        developersTask.setDeveloperTaskType(DeveloperTaskType.valueOf(set.getString(Constants.DEVELOPERS_TASK_TYPE)));
                        log.debug(developersTask);
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
                        log.debug(set2);
                        set2.next();
                        developersTask.setId(set2.getLong(Constants.DEVELOPERSTASK_ID));
                        log.debug(developersTask);
                        list.add(developersTask);
                        log.info(CustomLogger.endFunc(developersTask));
                        break;
                    case TESTERS_TASK:
                        TestersTask testersTask = new TestersTask();
                        setBasicTask(set,testersTask);
                        log.debug(testersTask);
                        testersTask.setBugStatus(BugStatus.valueOf(set.getString(Constants.TESTERSTASK_BUGSTATUS)));
                        log.debug(testersTask);
                        testersTask.setBugDescription(set.getString(Constants.TESTERSTASK_DESCRIPTION));
                        log.debug(testersTask);
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
                        log.debug(set3);
                        set3.next();
                        testersTask.setId(set3.getLong(Constants.TESTERSTASK_ID));
                        log.debug(testersTask);
                        list.add(testersTask);
                        log.info(CustomLogger.endFunc(testersTask));
                }
            }
            return (List<T>) list;
        } catch (SQLException e) {
            log.error(e);
            log.info(CustomLogger.endFunc(e));
            return new ArrayList<>();
        }
    }

    /**
     * @param str
     * @throws Exception
     */
    private boolean stringIsValid(String str) {
        log.info(CustomLogger.startFunc());
        return str.isEmpty();
    }

    /**
     * @param task
     * @return
     */
    private Result<Long> calculatePrice(Task task) {
        log.info(CustomLogger.startFunc());
        try {
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
     * @param cl
     * @param <T>
     * @return
     */
    private <T extends Employee> List<T> getBaseEmployee(Class<T> cl){
        log.info(CustomLogger.startFunc());
        try {
            List<T> empList = new ArrayList<>();
            ResultSet setTask = getTaskRecords(String.format(Constants.SELECT_FOR_ALL,
                    cl.getSimpleName().toUpperCase()));
            log.debug(setTask);
            while (setTask.next()) {
                T employee = (T) new Employee();
                setBasicEmployee(setTask, employee);
                log.debug(employee);
                empList.add(employee);
            }
            log.info(CustomLogger.endFunc(empList));
            return empList;
        } catch (SQLException e) {
            log.info(CustomLogger.endFunc(e));
            log.error(e);
            return new ArrayList<>();
        }
    }


    /**
     * @param task
     * @param <T>
     */
    private <T extends Task> void taskToEmployeeMapping(T task){
        log.info(CustomLogger.startFunc());
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
        log.info(CustomLogger.endFunc());
    }

    /**
     * @param project
     */
    private void updTask(Project project){
        log.info(CustomLogger.startFunc());
        project.getTask().forEach(task->{
            execute(String.format(Constants.UPDATE_PrID_TASK,
                    checkType(task).getData().toString().toUpperCase(),
                    project.getId(),
                    task.getId()
            ));
            log.debug(String.format(Constants.UPDATE_PrID_TASK,
                    checkType(task).getData().toString().toUpperCase(),
                    project.getId(),
                    task.getId()
            ));
        });
        log.info(CustomLogger.endFunc());
    }

    /**
     * @param element
     * @param <T>
     * @return
     */
    private <T extends Task> Result<?> checkType(T element){
        log.info(CustomLogger.startFunc());
        switch(element.getTaskType()){
            case BASE_TASK:
                log.info(CustomLogger.endFunc(Constants.Task));
                return new Result<>(Complete, Constants.Task);
            case DEVELOPERS_TASK:
                log.info(CustomLogger.endFunc(Constants.DevelopersTask));
                return new Result<>(Complete, Constants.DevelopersTask);
            case TESTERS_TASK:
                log.info(CustomLogger.endFunc(Constants.TestersTask));
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
        log.info(CustomLogger.endFunc(task));
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
        log.debug(developersTask);
        developersTask.setDeveloperComments(Constants.BaseComment);
        log.debug(developersTask);
        log.info(CustomLogger.endFunc(developersTask));
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
        log.debug(testersTask);
        testersTask.setBugDescription(Constants.BaseComment);
        log.debug(testersTask);
        log.info(CustomLogger.endFunc(testersTask));
        return new Result<>(Complete,testersTask);
    }

    /**
     * @param cl
     * @param <T>
     * @return
     */
    public <T extends Task> List<T> getTaskRecords(Class<T> cl){
        log.info(CustomLogger.startFunc());
        try {
            List<T> taskList = new ArrayList<>();
            ResultSet setTask = getTaskRecords(String.format(Constants.SELECT_FOR_ALL,
                    cl.getSimpleName().toUpperCase()));
            log.debug(setTask);
            List<T> tasks = new ArrayList<>();
            while (setTask.next()) {
                switch(TaskTypes.valueOf(setTask.getString(Constants.TASK_TASK_TYPES))){
                    case BASE_TASK:
                        Task task = new Task();
                        setTask(setTask,task);
                        log.debug(task);
                        tasks.add((T) task);
                        break;
                    case DEVELOPERS_TASK:
                        DevelopersTask developersTask = new DevelopersTask();
                        setDevelopersTask(setTask,developersTask);
                        log.debug(developersTask);
                        tasks.add((T) developersTask);
                        break;
                    case TESTERS_TASK:
                        TestersTask testersTask = new TestersTask();
                        setTestersTask(setTask,testersTask);
                        log.debug(testersTask);
                        tasks.add((T) testersTask);
                        break;
                    default:
                        return new ArrayList<>();
                }
            }
            log.info(CustomLogger.endFunc(tasks));
            return tasks;
        } catch (SQLException e) {
            log.error(e);
            log.info(CustomLogger.endFunc(e));
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
        log.info(CustomLogger.startFunc());
        ResultSet set = getTaskRecords(String.format(Constants.SELECT_ALL,
                cl.getSimpleName().toLowerCase(),id));
        log.info(CustomLogger.endFunc());
        return set;
    }

    /**
     * @param employee
     * @return
     */
    public Result<Long> getEmployeeByParam(Employee employee) {
        log.info(CustomLogger.startFunc());
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
            log.debug(set);
            long id = 0;
            if (set != null) {
                set.next();
                id = set.getLong(Constants.EMPLOYEE_ID);
            }
            log.debug(id);
            log.info(CustomLogger.endFunc(id));
            return new Result<>(Complete, id);
        } catch (SQLException e) {
            log.error(e);
            log.info(CustomLogger.endFunc(e));
            return new Result<>(Fail);
        }
    }

    /**
     * @param developer
     * @return
     */
    public Result<Long> getDeveloperByParam(Developer developer) {
        log.info(CustomLogger.startFunc());
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
            log.debug(set);
            long id = 0;
            if (set != null) {
                set.next();
                id = set.getLong(Constants.DEVELOPER_ID);
            }
            log.debug(id);
            log.info(CustomLogger.endFunc(id));
            return new Result<>(Complete, id);
        } catch (SQLException e) {
            log.error(e);
            log.info(CustomLogger.endFunc(e));
            return new Result<>(Fail);
        }
    }

    /**
     * @param tester
     * @return
     */
    public Result<Long> getTesterByParam(Tester tester) {
        log.info(CustomLogger.startFunc());
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
            log.debug(set);
            long id = 0;
            if (set != null) {
                set.next();
                id = set.getLong(Constants.TESTER_ID);
            }
            log.debug(id);
            log.info(CustomLogger.endFunc(id));
            return new Result<>(Complete, id);
        } catch (SQLException e) {
            log.error(e);
            log.info(CustomLogger.endFunc(e));
            return new Result<>(Fail);
        }
    }

    /**
     * @param project
     * @return
     */
    public Result<Long> getProjectByParam(Project project) {
        log.info(CustomLogger.startFunc());
        try {
            ResultSet set = getTaskRecords(String.format(Constants.SELECT_PROJECT,
                    project.getTitle(),
                    project.getTakeIntoDevelopment()));
            log.debug(set);
            long id = 0;
            if (set != null) {
                set.next();
                id = set.getLong(Constants.PROJECT_ID);
            }
            log.debug(id);
            log.info(CustomLogger.endFunc(id));
            return new Result<>(Complete, id);
        } catch (SQLException e) {
            log.error(e);
            log.info(CustomLogger.endFunc(e));
            return new Result<>(Fail);
        }
    }

    /**
     * @param task
     * @return
     */
    public Result<Long> getTaskByParam(Task task) {
        log.info(CustomLogger.startFunc());
        try {
            ResultSet set = getTaskRecords(String.format(Constants.SELECT_TASK, task.getTaskDescription(),
                    task.getMoney(), task.getScrumMaster().getId(), task.getStatus(), task.getCreatedDate(), task.getDeadline(),
                    task.getLastUpdate(), task.getTaskType().toString()));
            log.debug(set);
            long id = 0;
            if (set != null) {
                set.next();
                id = set.getLong(Constants.TASK_ID);
            }
            log.debug(id);
            log.info(CustomLogger.endFunc(id));
            return new Result<>(Complete, id);
        } catch (SQLException e) {
            log.error(e);
            log.info(CustomLogger.endFunc(e));
            return new Result<>(Fail);
        }
    }

    /**
     * @param developersTask
     * @return
     */
    public Result<Long> getDevelopersTaskByParam(DevelopersTask developersTask) {
        log.info(CustomLogger.startFunc());
        try {
            ResultSet set = getTaskRecords(String.format(Constants.SELECT_DEVELOPERSTASK, developersTask.getTaskDescription(),
                    developersTask.getMoney(), developersTask.getScrumMaster().getId(), developersTask.getStatus(), developersTask.getCreatedDate(), developersTask.getDeadline(),
                    developersTask.getLastUpdate(), developersTask.getTaskType().toString(), developersTask.getDeveloperComments(), developersTask.getDeveloperTaskType().toString()));
            log.debug(set);
            long id = 0;
            if (set != null) {
                set.next();
                id = set.getLong(Constants.TASK_ID);
            }
            log.debug(id);
            log.info(CustomLogger.endFunc(id));
            return new Result<>(Complete, id);
        } catch (SQLException e) {
            log.error(e);
            log.info(CustomLogger.endFunc(e));
            return new Result<>(Fail);
        }
    }

    /**
     * @param task
     * @return
     */
    public Result<Long> getTestersTaskByParam(TestersTask task) {
        log.info(CustomLogger.startFunc());
        try {
            ResultSet set = getTaskRecords(String.format(Constants.SELECT_TESTERSTASK, task.getTaskDescription(),
                    task.getMoney(), task.getScrumMaster().getId(), task.getStatus(), task.getCreatedDate(), task.getDeadline(),
                    task.getLastUpdate(), task.getTaskType().toString(),task.getBugStatus().toString(),task.getBugDescription()));
            log.debug(set);
            long id = 0;
            if (set != null) {
                set.next();
                id = set.getLong(Constants.TASK_ID);
            }
            log.debug(id);
            log.info(CustomLogger.endFunc(id));
            return new Result<>(Complete, id);
        } catch (SQLException e) {
            log.error(e);
            log.info(CustomLogger.endFunc(e));
            return new Result<>(Fail);
        }
    }
}
