package ru.sfedu.groupappcontrol.utils;

import ru.sfedu.groupappcontrol.Constants;
import ru.sfedu.groupappcontrol.api.DataProviderXML;
import ru.sfedu.groupappcontrol.models.*;
import ru.sfedu.groupappcontrol.models.enums.*;

import java.util.ArrayList;
import java.util.List;

public class XmlGenerator {
    public static DataProviderXML instance = new DataProviderXML();

    public static void addRecord() {
        List<Employee> employeeList = new ArrayList<>();
        List<Developer> developerList = new ArrayList<>();
        List<Tester> testersList = new ArrayList<>();
        List<Task> taskList = new ArrayList<>();
        List<DevelopersTask> developersTasks = new ArrayList<>();
        List<TestersTask> testersTasks = new ArrayList<>();
        List<Project> projectList = new ArrayList<>();
        for (int i=1; i<=10; i++) {
            Employee employee = new Employee();
            employee.setId(i);
            employee.setFirstName(Fill.firstName[i-1]);
            employee.setLastName(Fill.lastName[i-1]);
            employee.setLogin(Fill.login[i-1]);
            employee.setPassword(Fill.password[i-1]);
            employee.setEmail(Fill.email[i-1]);
            employee.setToken(Fill.token[i-1]);
            employee.setDepartment(Fill.department[i-1]);
            employee.setTypeOfEmployee(TypeOfEmployee.EMPLOYEE);
            employeeList.add(employee);
            instance.insertGenericEmployeeForDelete(Employee.class,employeeList,false);
        }
        for (int i=1; i<=10; i++) {
            Developer developer = new Developer();
            developer.setId(i);
            developer.setFirstName(Fill.firstName[i-1]);
            developer.setLastName(Fill.lastName[i-1]);
            developer.setLogin(Fill.login[i-1]);
            developer.setPassword(Fill.password[i-1]);
            developer.setEmail(Fill.email[i-1]);
            developer.setToken(Fill.token[i-1]);
            developer.setDepartment(Fill.department[i-1]);
            developer.setTypeOfEmployee(TypeOfEmployee.Developer);
            developer.setStatus(TypeOfDevelopers.CUSTOM);
            developer.setProgrammingLanguage(ProgrammingLanguage.Custom);
            developerList.add(developer);
            instance.insertGenericEmployeeForDelete(Developer.class,developerList,false);
        }
        for (int i=1; i<=10; i++) {
            Tester tester = new Tester();
            tester.setId(i);
            tester.setFirstName(Fill.firstName[i-1]);
            tester.setLastName(Fill.lastName[i-1]);
            tester.setLogin(Fill.login[i-1]);
            tester.setPassword(Fill.password[i-1]);
            tester.setEmail(Fill.email[i-1]);
            tester.setToken(Fill.token[i-1]);
            tester.setDepartment(Fill.department[i-1]);
            tester.setTypeOfEmployee(TypeOfEmployee.Tester);
            tester.setStatus(TypeOfDevelopers.CUSTOM);
            tester.setProgrammingLanguage(ProgrammingLanguage.Custom);
            tester.setTypeOfTester(TypeOfTester.Custom);
            testersList.add(tester);
            instance.insertGenericEmployeeForDelete(Tester.class,testersList,false);
        }
        for (int i=1; i<=10; i++) {
            Task task = new Task();
            task.setId(i);
            task.setTaskDescription(Fill.taskDescription[i-1]);
            task.setMoney(Fill.money[i-1]);
            task.setScrumMaster(getScrum());
            task.setStatus(TypeOfCompletion.CUSTOM);
            task.setTeam(getListEmployee());
            task.setCreatedDate(Fill.createdDate[i-1]);
            task.setDeadline(Fill.deadline[i-1]);
            task.setLastUpdate(Fill.lastUpdate[i-1]);
            task.setTaskType(TaskTypes.BASE_TASK);
            taskList.add(task);
            instance.insertGenericTaskForDelete(Task.class,taskList,false);
        }
        for (int i=1; i<=10; i++) {
            DevelopersTask developerTask = new DevelopersTask();
            developerTask.setId(i);
            developerTask.setTaskDescription(Fill.taskDescription[i-1]);
            developerTask.setMoney(Fill.money[i-1]);
            developerTask.setScrumMaster(getScrum());
            developerTask.setStatus(TypeOfCompletion.CUSTOM);
            developerTask.setTeam(getListEmployee());
            developerTask.setCreatedDate(Fill.createdDate[i-1]);
            developerTask.setDeadline(Fill.deadline[i-1]);
            developerTask.setLastUpdate(Fill.lastUpdate[i-1]);
            developerTask.setTaskType(TaskTypes.DEVELOPERS_TASK);
            developerTask.setDeveloperTaskType(DeveloperTaskType.DEVELOPMENT);
            developerTask.setDeveloperComments(Constants.BaseComment);
            developersTasks.add(developerTask);
            instance.insertGenericTaskForDelete(DevelopersTask.class,developersTasks,false);
        }
        for (int i=1; i<=10; i++) {
            TestersTask testersTask = new TestersTask();
            testersTask.setId(i);
            testersTask.setTaskDescription(Fill.taskDescription[i-1]);
            testersTask.setMoney(Fill.money[i-1]);
            testersTask.setScrumMaster(getScrum());
            testersTask.setStatus(TypeOfCompletion.CUSTOM);
            testersTask.setTeam(getListEmployee());
            testersTask.setCreatedDate(Fill.createdDate[i-1]);
            testersTask.setDeadline(Fill.deadline[i-1]);
            testersTask.setLastUpdate(Fill.lastUpdate[i-1]);
            testersTask.setTaskType(TaskTypes.TESTERS_TASK);
            testersTask.setBugStatus(BugStatus.IN_WORK);
            testersTask.setBugDescription(Constants.BaseComment);
            testersTasks.add(testersTask);
            instance.insertGenericTaskForDelete(TestersTask.class,testersTasks,false);
        }
        for (int i=1; i<=4; i++) {
            Project project=new Project();
            project.setId(i);
            project.setTitle(Fill.title[i-1]);
            project.setTakeIntoDevelopment(Fill.createdDate[i-1]);
            project.setTask(getListTask());
            projectList.add(project);
            instance.insertProjectForDelete(projectList,false);
        }
    }
    public static Employee getScrum(){
        List<Employee> listemployee = instance.select(Employee.class);
        int max=9; int min=0;
        return listemployee.get((int) ((Math.random() * ((max - min) + 1)) + min));
    }
    public static List<Employee> getListEmployee(){
        List<Employee> listemployee = instance.select(Employee.class);
        List<Developer> developers = instance.select(Developer.class);
        int max=9; int min=0;
        for (int i=1;i<=3; i++) {
            Developer developer = developers.get((int) ((Math.random() * ((max - min) + 1)) + min));
            listemployee.add(developer);
        }
        List<Tester> testers = instance.select(Tester.class);
        for (int i=1;i<=3; i++) {
            Tester tester = testers.get((int) ((Math.random() * ((max - min) + 1)) + min));
            listemployee.add(tester);
        }
        return listemployee;
    }
    public static List<Task> getListTask(){
        List<Task> listTask = instance.select(Task.class);
        List<DevelopersTask> developers = instance.select(DevelopersTask.class);
        int max=9; int min=0;
        for (int i=1;i<=3; i++) {
            DevelopersTask developersTask = developers.get((int)((Math.random() * ((max - min) + 1)) + min));
            listTask.add(developersTask);
        }
        List<TestersTask> testers = instance.select(TestersTask.class);
        for (int i=1;i<=3; i++) {
            TestersTask testersTask = testers.get((int) ((Math.random() * ((max - min) + 1)) + min));
            listTask.add(testersTask);
        }
        return listTask;
    }
}
