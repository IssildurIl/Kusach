package ru.sfedu.groupappcontrol.models;


import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;
import ru.sfedu.groupappcontrol.models.enums.TaskTypes;
import ru.sfedu.groupappcontrol.models.enums.TypeOfCompletion;
import ru.sfedu.groupappcontrol.utils.EmployeeConverter;
import ru.sfedu.groupappcontrol.utils.EmployeeListConverter;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;


@Root(name = "Task")
public class Task implements Serializable {
  @Attribute
  @CsvBindByName
  private long id;
  @Element
  @CsvBindByName
  private String taskDescription;
  @Element
  @CsvBindByName
  private double money;
  @Element
  @CsvCustomBindByName(converter = EmployeeConverter.class)
  private Employee scrumMaster;
  @Element
  @CsvBindByName
  private TypeOfCompletion status;
  @ElementList
  @CsvCustomBindByName(converter = EmployeeListConverter.class)
  private List<Employee> team;
  @Element
  @CsvBindByName
  private String createdDate;
  @Element
  @CsvBindByName
  private String deadline;
  @Element
  @CsvBindByName
  private String lastUpdate;
  @Element
  @CsvBindByName
  private TaskTypes taskType;

  public Task () { };


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public void setTaskDescription (String newVar) {
    taskDescription = newVar;
  }

  public String getTaskDescription () {
    return taskDescription;
  }

  public void setMoney (Double newVar) {
    money = newVar;
  }

  public Double getMoney () {
    return money;
  }

  public void setScrumMaster (Employee newVar) {
    scrumMaster = newVar;
  }

  public Employee getScrumMaster () {
    return scrumMaster;
  }

  public void setStatus (TypeOfCompletion newVar) {
    status = newVar;
  }

  public TypeOfCompletion getStatus () {
    return status;
  }

  public List<Employee> getTeam() {
    return team;
  }

  public void setTeam(List<Employee> team) {
    this.team = team;
  }

  public void setCreatedDate (String newVar) {
    createdDate = newVar;
  }

  public String getCreatedDate () {
    return createdDate;
  }

  public void setDeadline (String newVar) {
    deadline = newVar;
  }

  public String getDeadline () {
    return deadline;
  }

  public void setLastUpdate (String newVar) {
    lastUpdate = newVar;
  }

  public String getLastUpdate () {
    return lastUpdate;
  }

  public void setTaskType (TaskTypes newVar) {
    taskType = newVar;
  }

  public TaskTypes getTaskType () {
    return taskType;
  }

  @Override
  public String toString() {
    return "Task{" +
            "id=" + id +
            ", taskDescription='" + taskDescription + '\'' +
            ", money=" + money +
            ", scrumMaster=" + scrumMaster +
            ", status=" + status +
            ", team=" + team +
            ", createdDate='" + createdDate + '\'' +
            ", deadline='" + deadline + '\'' +
            ", lastUpdate='" + lastUpdate + '\'' +
            ", taskType=" + taskType +
            '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Task task = (Task) o;
    return Objects.equals(id, task.id) &&
            Objects.equals(taskDescription, task.taskDescription) &&
            Objects.equals(money, task.money) &&
            Objects.equals(scrumMaster, task.scrumMaster) &&
            status == task.status &&
            Objects.equals(team, task.team) &&
            Objects.equals(createdDate, task.createdDate) &&
            Objects.equals(deadline, task.deadline) &&
            Objects.equals(lastUpdate, task.lastUpdate) &&
            taskType == task.taskType;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, taskDescription, money, scrumMaster, status, team, createdDate, deadline, lastUpdate, taskType);
  }

}
