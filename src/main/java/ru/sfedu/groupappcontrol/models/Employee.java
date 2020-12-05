package ru.sfedu.groupappcontrol.models;


import com.opencsv.bean.CsvBindByName;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import ru.sfedu.groupappcontrol.models.enums.TypeOfEmployee;

import java.io.Serializable;
import java.util.Objects;

public class Employee implements Serializable {
  @CsvBindByName
  private long id;
  @Element
  @CsvBindByName
  private String firstName;
  @Element
  @CsvBindByName
  private String lastName;
  @Element
  @CsvBindByName
  private String login;
  @Element
  @CsvBindByName
  private String password;
  @Element
  @CsvBindByName
  private String email;
  @Element
  @CsvBindByName
  private String token;
  @Element
  @CsvBindByName
  private String department;
  @Element
  @CsvBindByName
  private TypeOfEmployee typeOfEmployee;
  

  public Employee () { };

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public void setFirstName (String newVar) {
    firstName = newVar;
  }

  public String getFirstName () {
    return firstName;
  }

  public void setLastName (String newVar) {
    lastName = newVar;
  }

  public String getLastName () {
    return lastName;
  }

  public void setLogin (String newVar) {
    login = newVar;
  }

  public String getLogin () {
    return login;
  }

  public void setPassword (String newVar) {
    password = newVar;
  }

  public String getPassword () {
    return password;
  }

  public void setEmail (String newVar) {
    email = newVar;
  }

  public String getEmail () {
    return email;
  }

  public void setToken (String newVar) {
    token = newVar;
  }

  public String getToken () {
    return token;
  }

  public void setDepartment (String newVar) {
    department = newVar;
  }

  public String getDepartment () {
    return department;
  }

  public void setTypeOfEmployee (TypeOfEmployee newVar) {
    typeOfEmployee = newVar;
  }

  public void setTypeOfEmployee (String newVar) {
    this.typeOfEmployee = TypeOfEmployee.valueOf(newVar);
  }

  public TypeOfEmployee getTypeOfEmployee () {
    return typeOfEmployee;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Employee employee = (Employee) o;
    return id == employee.id &&
            Objects.equals(firstName, employee.firstName) &&
            Objects.equals(lastName, employee.lastName) &&
            Objects.equals(login, employee.login) &&
            Objects.equals(password, employee.password) &&
            Objects.equals(email, employee.email) &&
            Objects.equals(token, employee.token) &&
            Objects.equals(department, employee.department) &&
            typeOfEmployee == employee.typeOfEmployee;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, firstName, lastName, login, password, email, token, department, typeOfEmployee);
  }

  @Override
  public String toString() {
    return "Employee{" +
            "id=" + id +
            ", firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", login='" + login + '\'' +
            ", password='" + password + '\'' +
            ", email='" + email + '\'' +
            ", token='" + token + '\'' +
            ", department='" + department + '\'' +
            ", typeOfEmployee=" + typeOfEmployee +
            '}';
  }
}
