package ru.sfedu.groupappcontrol.api;


import lombok.NonNull;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.groupappcontrol.Result;
import ru.sfedu.groupappcontrol.models.*;
import ru.sfedu.groupappcontrol.models.enums.TaskTypes;
import ru.sfedu.groupappcontrol.models.enums.TypeOfCompletion;
import ru.sfedu.groupappcontrol.models.enums.TypeOfEmployee;

import java.util.List;

/**
 *
 */
public interface DataProvider {
     public static final Logger log = LogManager.getLogger(DataProvider.class);

     void initDataSource();

     Result<Task> getTaskById(long id);
     Result<DevelopersTask> getDevelopersTaskById(long id);
     Result<TestersTask> getTestersTaskById(long id);
     Result<Employee> getEmployeeById(long id);
     Result<Developer> getDeveloperById(long id);
     Result<Tester> getTesterById(long id);
     Result<Project> getProjectByID( long id);
     Result<Void> insertTask(Task task);
     Result<Void> insertDevelopersTask(DevelopersTask task);
     Result<Void> insertTestersTask(TestersTask task);
     Result<Void> insertEmployee(Employee employee);
     Result<Void> insertDeveloper(Developer developer);
     Result<Void> insertTester(Tester tester);
     Result<Project> insertProject(Project project);
     Result<Void> deleteTask(long id);
     Result<Void> deleteDevelopersTask(long id);
     Result<Void> deleteTestersTask(long id);
     Result<Void> deleteEmployee(long id);
     Result<Void> deleteDeveloper(long id);
     Result<Void> deleteTester(long id);
     Result<Void> deleteProject(long id);
     Result<Void> updateTask(Task task);
     Result<Void> updateDevelopersTask(DevelopersTask developersTask);
     Result<Void> updateTestersTask(TestersTask testersTask);
     Result<Void> updateEmployee(Employee employee);
     Result<Void> updateDeveloper(Developer developer);
     Result<Void> updateTester(Tester tester);
     Result<Project> updateProject(Project project);
     Result<Task> createTask(long id, @NonNull String taskDescription, @NonNull Double money,
                             @NonNull Employee scrumMaster, @NonNull TypeOfCompletion status,
                             @NonNull List<Employee> team, @NonNull String createdDate,
                             @NonNull String deadline, @NonNull String lastUpdate,
                             @NonNull TaskTypes taskType);
     Result<Task> getTasks(long id);
     List<Task> getAllTask();
     Result<Task> getTasksByUser(long userId, long taskId);
     Result<Void> changeTaskStatus(long id, String status);
     Result<Double> calculateTaskCost(Task task);
     Result<Void> writeBaseTaskComment(long id,String comment);
     Result<Void> writeDevelopersTaskComment(long id,String comment);
     Result<Void> writeTestersTaskComment(long id,String comment);
     Result<List<Task>> getTaskListByScrumMaster(long id);
     Result<List<DevelopersTask>> getDevelopersTaskListByScrumMaster(long id);
     Result<List<TestersTask>> getTestersTaskListByScrumMaster(long id);
     Result<Project> createProject(long id,@NonNull String title,@NonNull String takeIntoDevelopment,
                                   @NonNull List<Task> tasks);
     Result<List<Project>> getProjectById(long empId, long projectId);
     Result<Long> calculateProjectCost(Project project);
     Result<Long> calculateProjectTime(Project project);
     Result<Employee> createEmployee(long id,@NonNull String firstName,@NonNull String lastName,
                                            @NonNull String login,@NonNull String password,
                                            @NonNull String email,@NonNull String token,
                                            @NonNull String department,@NonNull TypeOfEmployee typeOfEmployee);
     List<Employee> getAllEmployee();
     Result<List<Task>> getScrumMasterTaskList(long userId, TaskTypes taskTypes);
     Result<List<Project>> getProjectListByScrummasterId(long id);
     <T> Result<T> deleteRecord(Class<T> cl);
     void deleteAllRecord();

}
