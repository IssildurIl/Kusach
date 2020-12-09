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
    public <T extends Task> Result<T> writeComment(Class cl, long id, String comment);
//    ScrumMaster.taskAnalysis

    public Result getUserInfoList(long userId);
    public Result getBaseTaskList(long taskId);
    public Result getTaskInfo(Class cl,long taskId);
    public Result getTask(long userId, long taskId);
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
    public Result deleteProject(Project project);
    public Result updateProject(Project project);
    public Result createProject(long id,String title, String takeIntoDevelopment, List<Task> tasks);
    public Result getProjectById(Employee employee, long projectId);
    public Result getProjectListById(Employee employee);
//    ScrumMaster.EmployeeControl
    public Result correctEmployeeParameters(Employee editedEmployee);
    public Result addEmployeeToTask(Task task, Employee employee);
    public Result deleteEmployeeFromTask(Task task, Employee employee);
    public Result createEmployee(long id,String firstName, String lastName, String login, String password, String email,String token, String department,TypeOfEmployee typeOfEmployee);
    public <T> Result<T> deleteRecord(Class<T> cl);
}
