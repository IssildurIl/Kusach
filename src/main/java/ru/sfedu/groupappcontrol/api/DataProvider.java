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
    public Result writeComment(long id, String comment);
//    ScrumMaster.taskAnalysis

    public Result getUserInfoList(long userId);
    public Result getTaskList(long userId);
    public Result getTaskInfo(long taskId);
    public Result getTask(long userId, long taskId);
    public Result calculateTaskCost(Task task);

//    ScrumMaster.projectAnalysis
    public Result getProjectStatistic(long userId);
    public Result getProject(long userId, long projectId);
    public Result calculateProjectCost(Project project);
    public Result calculateProjectTime(Project project);
//    ScrumMaster.TaskControl
    public Result createTask(long userId,String taskDescription, Double money, String deadline);
    public Result createTask(long userId, String taskDescription, Double money, String deadline, DeveloperTaskType taskType);
    public Result deleteTask(Task task);
    public Result getTask(Employee employee, long taskId);
    public Result getTaskById(Employee employee, long taskId);
    public Result getTaskListById(Employee employee);
//    ScrumMaster.ProjectControl
    public Result deleteProject(Project project);
    public Result updateProject(Project project);
    public Result createProject(String title, String takeIntoDevelopment);
    public Result getProject(Employee employee);
    public Result getProjectById(Employee employee, long projectId);
    public Result getProjecListById(Employee employee);
//    ScrumMaster.EmployeeControl
    public Result correctEmployeeParameters(Employee editedEmployee);
    public Result addEmployeeToTask(Task task, Employee employee);
    public Result deleteEmployeeFromTask(Task task, Employee employee);
    public Result createEmployee(String firstName, String lastName, String login, String password, String email,String token, String department);
    public Result createEmployee(String firstName, String lastName, String login, String password, String email,String token, String department, TypeOfDevelopers status, ProgrammingLanguage language);
    public Result createEmployee(String firstName, String lastName, String login, String password, String email,String token, String department, TypeOfDevelopers status, ProgrammingLanguage language,TypeOfTester typeOfTester);
}
