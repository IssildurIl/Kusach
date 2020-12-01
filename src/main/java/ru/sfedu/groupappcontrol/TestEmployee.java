package ru.sfedu.groupappcontrol;

import ru.sfedu.groupappcontrol.models.Employee;
import ru.sfedu.groupappcontrol.models.Task;
import ru.sfedu.groupappcontrol.models.enums.TaskTypes;
import ru.sfedu.groupappcontrol.models.enums.TypeOfCompletion;
import ru.sfedu.groupappcontrol.models.enums.TypeOfEmployee;

import java.util.List;

public class TestEmployee {
    public static Employee createUser(long id, String firstName, String lastName, String login, String password, String email, String token, String department, TypeOfEmployee typeOfEmployee){
        Employee employee = new Employee();
        employee.setId(id);
        employee.setFirstName(firstName);
        employee.setLastName(lastName);
        employee.setLogin(login);
        employee.setPassword(password);
        employee.setEmail(email);
        employee.setToken(token);
        employee.setDepartment(department);
        employee.setTypeOfEmployee(typeOfEmployee);
        return employee;
    }
    public static Task createTask(long id, String taskDescription, Double money, Employee scrumMaster, TypeOfCompletion status, List<Employee> team, String createdDate, String deadline, String lastUpdate, TaskTypes taskType){
        Task task = new Task();
        task.setId(id);
        task.setTaskDescription(taskDescription);
        task.setMoney(money);
        task.setScrumMaster(scrumMaster);
        task.setStatus(status);
        task.setTeam(team);
        task.setCreatedDate(createdDate);
        task.setDeadline(deadline);
        task.setLastUpdate(lastUpdate);
        task.setTaskType(taskType);
        return task;
    }
}
