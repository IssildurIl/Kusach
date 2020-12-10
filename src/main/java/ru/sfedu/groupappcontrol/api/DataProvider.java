package ru.sfedu.groupappcontrol.api;


import ru.sfedu.groupappcontrol.Result;
import ru.sfedu.groupappcontrol.models.*;
import ru.sfedu.groupappcontrol.models.enums.*;

import java.io.IOException;
import java.util.List;

public interface DataProvider {

//    Generalized method

//    Employee
    public Result changeProfileInfo(Employee employee);

//    Developer, Tester
    public Result changeTaskStatus(long id, String status);
    public <T extends Task> Result<T> writeComment(Class<T> cl, long id, String comment);
//    ScrumMaster.taskAnalysis

    public <T extends Employee> Result getUserInfoList(Class cl,long userId);
    public Result getScrumMasterTaskList(long userId, TaskTypes taskTypes);
    public Result getTaskInfo(Class cl,long taskId);
    Result getTasksByUser(int userId, long taskId);
    public Result calculateTaskCost(Task task);

//    ScrumMaster.projectAnalysis
    public Result getProjectStatistic(long userId);
    public Result getProject(long projectId);
    public Result calculateProjectCost(Project project);
    public Result calculateProjectTime(Project project);
//    ScrumMaster.TaskControl
    public Result createTask(long id,String taskDescription, Double money, Employee scrumMaster,TypeOfCompletion status, List<Employee> team, String createdDate,String deadline,String lastUpdate,TaskTypes taskType);
    public Result deleteTask(Task task);
    public Result getTaskWorker(Employee employee, long taskId);
    public <T extends Task> Result<T> getTaskById(Class cl, long taskId);
    public Result getTaskListById(long id);
//    ScrumMaster.ProjectControl
     Result deleteProject(Project project);
     Result updateProject(Project project);
     Result createProject(long id,String title, String takeIntoDevelopment, List<Task> tasks);
     Result getProjectById(Employee employee, long projectId);
     Result getProjectListById(Employee employee);
//    ScrumMaster.EmployeeControl
     Result correctEmployeeParameters(Employee editedEmployee);
     Result addEmployeeToTask(Task task, Employee employee);
     Result deleteEmployeeFromTask(Task task, Employee employee);
     Result createEmployee(long id,String firstName, String lastName, String login, String password, String email,String token, String department,TypeOfEmployee typeOfEmployee);
     <T> Result<T> deleteRecord(Class<T> cl);
     Result<Task> getTasks(long id);
}
