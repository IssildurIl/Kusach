package ru.sfedu.groupappcontrol.api;

import ru.sfedu.groupappcontrol.models.Employee;
import ru.sfedu.groupappcontrol.models.enums.TypeOfEmployee;

public class TestEmployee {
    public Employee createUser(long id, String firstName, String lastName, String login, String password, String email, String token, String department, TypeOfEmployee typeOfEmployee){
        Employee employee = new Employee();
        employee.setId(id);
        employee.setFirstName(firstName);
        employee.setLastName(lastName);
        employee.setLogin(login);
        employee.setPassword(password);
        employee.setEmail(email);
        employee.setToken(token);
        employee.setDepartment(department);
        employee.setTypeOfEmployee(TypeOfEmployee.Developer);
        return employee;
    }

}
