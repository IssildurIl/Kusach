package ru.sfedu.groupappcontrol.utils;

import ru.sfedu.groupappcontrol.Constants;
import ru.sfedu.groupappcontrol.api.DataProviderJdbc;
import ru.sfedu.groupappcontrol.models.*;
import ru.sfedu.groupappcontrol.models.enums.*;

import java.util.ArrayList;
import java.util.List;

public class JdbcGenerator {
    public static DataProviderJdbc instance = new DataProviderJdbc();
    public static void addRecord() {
        for (int i=1; i<=10; i++) {
            Employee employee = new Employee();
            employee.setFirstName(Fill.firstName[i-1]);
            employee.setLastName(Fill.lastName[i-1]);
            employee.setLogin(Fill.login[i-1]);
            employee.setPassword(Fill.password[i-1]);
            employee.setEmail(Fill.email[i-1]);
            employee.setToken(Fill.token[i-1]);
            employee.setDepartment(Fill.department[i-1]);
            employee.setTypeOfEmployee(TypeOfEmployee.EMPLOYEE);
            instance.insertEmployee(employee);
        }
        for (int i=1; i<=10; i++) {
            Developer developer = new Developer();
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
            instance.insertDeveloper(developer);
        }
        for (int i=1; i<=10; i++) {
            Tester tester = new Tester();
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
            instance.insertTester(tester);
        }
        for (int i=1; i<=10; i++) {
            Task task = new Task();
            task.setTaskDescription(Fill.taskDescription[i-1]);
            task.setMoney(Fill.money[i-1]);
            task.setScrumMaster(getScrum());
            task.setStatus(TypeOfCompletion.CUSTOM);
            task.setTeam(getListEmployee());
            task.setCreatedDate(Fill.createdDate[i-1]);
            task.setDeadline(Fill.deadline[i-1]);
            task.setLastUpdate(Fill.lastUpdate[i-1]);
            task.setTaskType(TaskTypes.BASE_TASK);
            instance.insertTask(task);
        }
        for (int i=1; i<=10; i++) {
            DevelopersTask developerTask = new DevelopersTask();
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
            instance.insertDevelopersTask(developerTask);
        }
        for (int i=1; i<=10; i++) {
            TestersTask testersTask = new TestersTask();
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
            instance.insertTestersTask(testersTask);
        }
        for (int i=1; i<=4; i++) {
            Project project=new Project();
            project.setId(i-1);
            project.setTitle(Fill.title[i-1]);
            project.setTakeIntoDevelopment(Fill.createdDate[i-1]);
            project.setTask(getListTask());
            instance.insertProject(project);
        }
    }
    public static Employee getScrum(){
        List<Employee> listemployee = ((DataProviderJdbc) instance).getListEmployees(Employee.class);
        int max=9; int min=0;
        return listemployee.get((int) ((Math.random() * ((max - min) + 1)) + min));
    }
    public static List<Employee> getListEmployee(){
        List<Employee> fullList = new ArrayList<>();
        int max=9; int min=0;
        List<Employee> listemployee = ((DataProviderJdbc) instance).getListEmployees(Employee.class);
        for (int i=1;i<=3; i++) {
            Employee employee = listemployee.get((int) ((Math.random() * ((max - min) + 1)) + min));
            fullList.add(employee);
        }
        List<Developer> developers = ((DataProviderJdbc) instance).getListEmployees(Developer.class);
        for (int i=1;i<=3; i++) {
            Developer developer = developers.get((int) ((Math.random() * ((max - min) + 1)) + min));
            fullList.add(developer);
        }
        List<Tester> testers = ((DataProviderJdbc) instance).getListEmployees(Tester.class);
        for (int i=1;i<=3; i++) {
            Tester tester = testers.get((int) ((Math.random() * ((max - min) + 1)) + min));
            fullList.add(tester);
        }
        return listemployee;
    }
    public static List<Task> getListTask(){
        List<Task> fullList = new ArrayList<>();
        List<Task> listTask = ((DataProviderJdbc) instance).getListTasks(Task.class);
        int max=9; int min=0;
        for (int i=0;i<=4; i++) {
            Task task = listTask.get((int)((Math.random() * ((max - min) + 1)) + min));
            fullList.add(task);
        }
        List<DevelopersTask> developers = ((DataProviderJdbc) instance).getListTasks(DevelopersTask.class);
        for (int i=0;i<=4; i++) {
            DevelopersTask developersTask = developers.get((int)((Math.random() * ((max - min) + 1)) + min));
            fullList.add(developersTask);
        }
        List<TestersTask> testers = ((DataProviderJdbc) instance).getListTasks(TestersTask.class);
        for (int i=0;i<=4; i++) {
            TestersTask testersTask = testers.get((int) ((Math.random() * ((max - min) + 1)) + min));
            fullList.add(testersTask);
        }
        return fullList;
    }
}
