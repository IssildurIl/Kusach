package ru.sfedu.groupappcontrol.api;


import lombok.NonNull;
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

     void initDataSource();

     Result<Task> getTaskById(long id);
     Result<DevelopersTask> getDevelopersTaskById(long id);
     Result<TestersTask> getTestersTaskById(long id);
     Result<Employee> getEmployeeById(long id);
     Result<Developer> getDeveloperById(long id);
     Result<Tester> getTesterById(long id);
     Result<Project> getProjectByID( long id);
     Result<Void> insertTask(List<Task> list,boolean append);
     Result<Void> insertDevelopersTask(List<DevelopersTask> list,boolean append);
     Result<Void> insertTestersTask(List<TestersTask> list,boolean append);
     Result<Void> insertEmployee(List<Employee> list,boolean append);
     Result<Void> insertDeveloper(List<Developer> list,boolean append);
     Result<Void> insertTester(List<Tester> list,boolean append);
     Result<Project> insertProject(List<Project> list, boolean append);
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
     Result<List<Task>> getTaskWorker(Employee employee, long taskId);
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
     Result<Project> getProjectByProjectID(long projectId);
     Result<List<Project>> getProjectById(Employee employee, long projectId);
     Result<Long> calculateProjectCost(Project project);
     Result<Long> calculateProjectTime(Project project);
     Result<Employee> createEmployee(long id,@NonNull String firstName,@NonNull String lastName,
                                            @NonNull String login,@NonNull String password,
                                            @NonNull String email,@NonNull String token,
                                            @NonNull String department,@NonNull TypeOfEmployee typeOfEmployee);
     List<Employee> getAllEmployee();
     Result<List<Task>> getScrumMasterTaskList(long userId, TaskTypes taskTypes);
     Result<List<Project>> getProjectListByScrummasterId(long id);
     Result<Void> deleteEmployeeFromTask(Task task, Employee employee);
     Result<Employee> changeProfileInfo(Employee editedEmployee);
     Result<Employee> correctEmployeeParameters(Employee editedEmployee);
     Result<Employee> addEmployeeToTask(Task task, Employee employee);
     <T> Result<T> deleteRecord(Class<T> cl);
     void deleteAllRecord();
     <T> List<T> select(Class<T> cl);
}
