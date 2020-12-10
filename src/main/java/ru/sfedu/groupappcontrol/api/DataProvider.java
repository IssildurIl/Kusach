package ru.sfedu.groupappcontrol.api;


import ru.sfedu.groupappcontrol.Result;
import ru.sfedu.groupappcontrol.models.*;
import ru.sfedu.groupappcontrol.models.enums.*;

import java.io.IOException;
import java.util.List;

public interface DataProvider {

//    Generalized method
     <T extends Task> Result<T> getTaskByID(Class<T> cl, long id);
     <T extends Employee> Result<T> getEmployeeByID(Class<T> cl, long id);
     <T extends Project> Result<T> getProjectByID(Class<T> cl, long id);

     <T extends Task> Result<Void> insertGenericTask(Class<T> cl, List<T> list, boolean append);
     <T extends Employee> Result<Void> insertGenericEmployee(Class<T> cl, List<T> list, boolean append);
     <T extends Project> Result<Void> insertGenericProject(Class<T> cl, List<T> list, boolean append);

     <T extends Task> Result<T> deleteGenericTask(Class<T> cl, long id);
     <T extends Employee> Result<T> deleteGenericEmployee(Class<T> cl, long id);
     <T extends Project> Result<T> deleteGenericProject(Class<T> cl, long id);

     <T extends Task> Result<T> updateGenericTask(Class<T> cl, T updElement);
     <T extends Employee> Result<T> updateGenericEmployee(Class<T> cl, T updElement);
     <T extends Project> Result<T> updateGenericProject(Class<T> cl, T updElement);

//    Employee
     Result<Employee> changeProfileInfo(Employee employee);

//    Developer, Tester
     Result<Void> changeTaskStatus(long id, String status);
     <T extends Task> Result<T> writeComment(Class<T> cl, long id, String comment);
//    ScrumMaster.taskAnalysis

     <T extends Employee> Result<List<T>> getUserInfoList(Class<T> cl,long userId);
     Result<List<Task>> getScrumMasterTaskList(long userId, TaskTypes taskTypes);
     <T extends Task> Result<List<T>> getTaskListByScrumMaster(Class<T> cl, long userId);
     Result<List<Task>> getTasksByUser(long userId, long taskId);
     Result<Double> calculateTaskCost(Task task);

//    ScrumMaster.projectAnalysis
     Result<Project> getProjectByProjectID(long projectId);
     Result<Long> calculateProjectCost(Project project);
     Result<Long> calculateProjectTime(Project project);
//    ScrumMaster.TaskControl
     Result<Task> createTask(long id,String taskDescription, Double money, Employee scrumMaster,TypeOfCompletion status, List<Employee> team, String createdDate,String deadline,String lastUpdate,TaskTypes taskType);
     Result<Void> deleteTask(Task task);
     Result<List<Task>> getTaskWorker(Employee employee, long taskId);
     <T extends Task> Result<T> getAnyTaskByTaskId(Class cl, long taskId);
//    ScrumMaster.ProjectControl
     Result<Void> deleteProject(Project project);
     Result<Void> updateProject(Project project);
     Result<Project> createProject(long id,String title, String takeIntoDevelopment, List<Task> tasks);
     Result<Project> getProjectById(Employee employee, long projectId);
     Result<List<Project>> getProjectListByScrummasterId(long id);
//    ScrumMaster.EmployeeControl
     Result<Employee> correctEmployeeParameters(Employee editedEmployee);
     Result<Employee> addEmployeeToTask(Task task, Employee employee);
     Result<Void> deleteEmployeeFromTask(Task task, Employee employee);
     Result<Employee> createEmployee(long id,String firstName, String lastName, String login, String password, String email,String token, String department,TypeOfEmployee typeOfEmployee);
     <T extends Task> Result <T> getTaskInfoGeneric(Class<T> cl,long taskId);

     List<Employee> getAllEmployee();
     <T> Result<T> deleteRecord(Class<T> cl);
     Result<Task> getTasks(long id);
     List<Task> getAllTask();
}
