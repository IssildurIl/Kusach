package ru.sfedu.groupappcontrol.api;


import ru.sfedu.groupappcontrol.Result;
import ru.sfedu.groupappcontrol.models.Employee;
import ru.sfedu.groupappcontrol.models.Project;
import ru.sfedu.groupappcontrol.models.Task;
import ru.sfedu.groupappcontrol.models.enums.TaskTypes;
import ru.sfedu.groupappcontrol.models.enums.TypeOfCompletion;
import ru.sfedu.groupappcontrol.models.enums.TypeOfEmployee;

import java.util.List;

/**
 *
 */
public interface DataProvider {

     void initDataSource();

//    Generalized method
     /**
      * @param cl
      * @param <T>
      * @return
      */
     <T> List<T> select(Class<T> cl);

     /**
      * @param cl
      * @param id
      * @param <T>
      * @return
      */
     <T extends Task> Result<T> getTaskByID(Class<T> cl, long id);

     /**
      * @param cl
      * @param id
      * @param <T>
      * @return
      */
     <T extends Employee> Result<T> getEmployeeByID(Class<T> cl, long id);

     /**
      * @param id
      * @return
      */
     Result getProjectByID(long id);

     /**
      * @param cl
      * @param list
      * @param append
      * @param <T>
      * @return
      */
     <T extends Task> Result<Void> insertGenericTask(Class<T> cl, List<T> list, boolean append);

     /**
      * @param cl
      * @param list
      * @param append
      * @param <T>
      * @return
      */
     <T extends Employee> Result<Void> insertGenericEmployee(Class<T> cl, List<T> list, boolean append);

     /**
      * @param list
      * @param append
      * @return
      */
     Result<Project> insertProject(List<Project> list, boolean append);

     /**
      * @param cl
      * @param id
      * @param <T>
      * @return
      */
     <T extends Task> Result<T> deleteGenericTask(Class<T> cl, long id);

     /**
      * @param cl
      * @param id
      * @param <T>
      * @return
      */
     <T extends Employee> Result<T> deleteGenericEmployee(Class<T> cl, long id);

     /**
      * @param id
      * @return
      */
     Result<Project> deleteGenericProject(long id);

     /**
      * @param cl
      * @param updElement
      * @param <T>
      * @return
      */
     <T extends Task> Result<T> updateGenericTask(Class<T> cl, T updElement);

     /**
      * @param cl
      * @param updElement
      * @param <T>
      * @return
      */
     <T extends Employee> Result<T> updateGenericEmployee(Class<T> cl, T updElement);

     /**
      * @param project
      * @return
      */
     Result<Project> updateGenericProject(Project project);

     /**
      * @param employee
      * @return
      */
//    Employee
     Result<Employee> changeProfileInfo(Employee employee);

     /**
      * @param id
      * @param status
      * @return
      */
//    Developer, Tester
     Result<Void> changeTaskStatus(long id, String status);

     /**
      * @param cl
      * @param id
      * @param comment
      * @param <T>
      * @return
      */
     <T extends Task> Result<T> writeComment(Class<T> cl, long id, String comment);
//    ScrumMaster.taskAnalysis

     /**
      * @param cl
      * @param userId
      * @param <T>
      * @return
      */
     <T extends Employee> Result<List<T>> getUserInfoList(Class<T> cl,long userId);

     /**
      * @param userId
      * @param taskTypes
      * @return
      */
     Result<List<Task>> getScrumMasterTaskList(long userId, TaskTypes taskTypes);

     /**
      * @param cl
      * @param userId
      * @param <T>
      * @return
      */
     <T extends Task> Result<List<T>> getTaskListByScrumMaster(Class<T> cl, long userId);

     /**
      * @param userId
      * @param taskId
      * @return
      */
     Result<List<Task>> getTasksByUser(long userId, long taskId);

     /**
      * @param task
      * @return
      */
     Result<Double> calculateTaskCost(Task task);

     /**
      * @param projectId
      * @return
      */
//    ScrumMaster.projectAnalysis
     Result<Project> getProjectByProjectID(long projectId);

     /**
      * @param project
      * @return
      */
     Result<Long> calculateProjectCost(Project project);

     /**
      * @param project
      * @return
      */
     Result<Long> calculateProjectTime(Project project);

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
     Result<Task> createTask(long id,String taskDescription, Double money, Employee scrumMaster,TypeOfCompletion status, List<Employee> team, String createdDate,String deadline,String lastUpdate,TaskTypes taskType);

     /**
      * @param task
      * @return
      */
     Result<Void> deleteTask(Task task);

     /**
      * @param employee
      * @param taskId
      * @return
      */
     Result<List<Task>> getTaskWorker(Employee employee, long taskId);

     /**
      * @param cl
      * @param taskId
      * @param <T>
      * @return
      */
     <T extends Task> Result<T> getAnyTaskByTaskId(Class<T> cl, long taskId);

     /**
      * @param project
      * @return
      */
     Result<Void> deleteProject(Project project);

     /**
      * @param project
      * @return
      */
     Result<Void> updateProject(Project project);

     /**
      * @param id
      * @param title
      * @param takeIntoDevelopment
      * @param tasks
      * @return
      */
     Result<Project> createProject(long id,String title, String takeIntoDevelopment, List<Task> tasks);

     /**
      * @param employee
      * @param projectId
      * @return
      */
     Result<Project> getProjectById(Employee employee, long projectId);

     /**
      * @param id
      * @return
      */
     Result<List<Project>> getProjectListByScrummasterId(long id);

     /**
      * @param editedEmployee
      * @return
      */
     Result<Employee> correctEmployeeParameters(Employee editedEmployee);

     /**
      * @param task
      * @param employee
      * @return
      */
     Result<Employee> addEmployeeToTask(Task task, Employee employee);

     /**
      * @param task
      * @param employee
      * @return
      */
     Result<Void> deleteEmployeeFromTask(Task task, Employee employee);

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
     Result<Employee> createEmployee(long id,String firstName, String lastName, String login, String password, String email,String token, String department,TypeOfEmployee typeOfEmployee);

     /**
      * @param cl
      * @param taskId
      * @param <T>
      * @return
      */
     <T extends Task> Result <T> getTaskInfoGeneric(Class<T> cl,long taskId);

     /**
      * @return
      */
     List<Employee> getAllEmployee();

     /**
      * @param cl
      * @param <T>
      * @return
      */
     <T> Result<T> deleteRecord(Class<T> cl);

     /**
      * @param id
      * @return
      */
     Result<Task> getTasks(long id);

     /**
      * @return
      */
     List<Task> getAllTask();

     /**
      *
      */
     void deleteAllRecord();
}
