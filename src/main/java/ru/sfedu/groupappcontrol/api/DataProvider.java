package ru.sfedu.groupappcontrol.api;


import ru.sfedu.groupappcontrol.Result;
import ru.sfedu.groupappcontrol.models.*;
import ru.sfedu.groupappcontrol.models.enums.*;

import java.io.IOException;
import java.util.List;

public interface DataProvider {

//    Generalized method

//    Employee
     Result<Employee> changeProfileInfo(Employee employee);

//    Developer, Tester
     Result<Void> changeTaskStatus(long id, String status);
     <T extends Task> Result<T> writeComment(Class<T> cl, long id, String comment);
//    ScrumMaster.taskAnalysis

     <T extends Employee> Result<List<T>> getUserInfoList(Class<T> cl,long userId);
     Result<List<Task>> getScrumMasterTaskList(long userId, TaskTypes taskTypes);
     <T extends Task> Result<Task> getTaskInfo(Class<T> cl,long taskId);
     Result<List<Task>> getTasksByUser(long userId, long taskId);
     Result<Double> calculateTaskCost(Task task);

//    ScrumMaster.projectAnalysis
     Result<Project> getProjectStatistic(long userId);
     Result<Project> getProject(long projectId);
     Result<Long> calculateProjectCost(Project project);
     Result<Long> calculateProjectTime(Project project);
//    ScrumMaster.TaskControl
     Result<Task> createTask(long id,String taskDescription, Double money, Employee scrumMaster,TypeOfCompletion status, List<Employee> team, String createdDate,String deadline,String lastUpdate,TaskTypes taskType);
     Result<Void> deleteTask(Task task);
     Result<List<Task>> getTaskWorker(Employee employee, long taskId);
     <T extends Task> Result<T> getTaskById(Class cl, long taskId);
     Result<List<Task>> getTaskListById(long id);
//    ScrumMaster.ProjectControl
     Result<Void> deleteProject(Project project);
     Result<Void> updateProject(Project project);
     Result<Project> createProject(long id,String title, String takeIntoDevelopment, List<Task> tasks);
     Result<Project> getProjectById(Employee employee, long projectId);
     Result<List<Project>> getProjectListById(Employee employee);
//    ScrumMaster.EmployeeControl
     Result<Employee> correctEmployeeParameters(Employee editedEmployee);
     Result<Employee> addEmployeeToTask(Task task, Employee employee);
     Result<Void> deleteEmployeeFromTask(Task task, Employee employee);
     Result<Employee> createEmployee(long id,String firstName, String lastName, String login, String password, String email,String token, String department,TypeOfEmployee typeOfEmployee);
     <T> Result<T> deleteRecord(Class<T> cl);
     Result<Task> getTasks(long id);
}
