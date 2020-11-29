package ru.sfedu.groupappcontrol.api;


import ru.sfedu.groupappcontrol.Result;
import ru.sfedu.groupappcontrol.models.*;
import ru.sfedu.groupappcontrol.models.enums.DeveloperTaskType;
import ru.sfedu.groupappcontrol.models.enums.ProgrammingLanguage;
import ru.sfedu.groupappcontrol.models.enums.TypeOfDevelopers;
import ru.sfedu.groupappcontrol.models.enums.TypeOfTester;

import java.io.IOException;
import java.util.List;

public interface DataProvider {

//    Generalized method

//    Employee
    public Result changeProfileInfo(Employee employee);

//    Developer, Tester

    public Result changeTaskStatus(Task task, long id);
    public Result writeComment(Task task,String comment);
    public Result writeComment(DevelopersTask developersTask,String comment);
    public Result writeComment(TestersTask testersTask, String comment);

//    ScrumMaster.taskAnalysis

    public Result getTaskInfoList(long userId);
    public Result getTaskList(long userId);
    public Result getTaskInfo(long userId, long taskId);
    public Result getTask(long userId, long taskId);
    public Result calculateTaskCost(Task task);
    public Result calculateDeveloperTaskCost(Task task);
    public Result calculateTesterTaskCost(Task task);

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
    public Result createEmployee(String firstName, String lastName, String login, String password, String email, String department);
    public Result createEmployee(String firstName, String lastName, String login, String password, String email, String department, TypeOfDevelopers status, ProgrammingLanguage language);
    public Result createEmployee(String firstName, String lastName, String login, String password, String email, String department, TypeOfTester typeOfTester, TypeOfDevelopers status, ProgrammingLanguage language);
}
