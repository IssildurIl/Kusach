package ru.sfedu.groupappcontrol.models;


import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;
import ru.sfedu.groupappcontrol.models.enums.TaskTypes;
import ru.sfedu.groupappcontrol.models.enums.TypeOfCompletion;
import ru.sfedu.groupappcontrol.utils.EmployeeConverter;
import ru.sfedu.groupappcontrol.utils.EmployeeListConverter;

import java.util.List;
import java.util.Objects;

/**
 * Class Task
 */
public class Task{

  @CsvBindByName
  private long id;
  @CsvBindByName
  private String taskDescription;
  @CsvBindByName
  private double money;
  //@CsvBindByName
  //@CsvRecurse
  @CsvCustomBindByName(converter = EmployeeConverter.class)
  private Employee scrumMaster;
  @CsvBindByName
  private TypeOfCompletion status;
  @CsvCustomBindByName(converter = EmployeeListConverter.class)
  private List<Employee> team;
  @CsvBindByName
  private String createdDate;
  @CsvBindByName
  private String deadline;
  @CsvBindByName
  private String lastUpdate;
  @CsvBindByName
  private TaskTypes taskType;

  //
  // Constructors
  //
  public Task () { };

  //
  // Methods
  //


  //
  // Accessor methods
  //
  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  /**
   * Set the value of taskDescription
   * @param newVar the new value of taskDescription
   */
  public void setTaskDescription (String newVar) {
    taskDescription = newVar;
  }

  /**
   * Get the value of taskDescription
   * @return the value of taskDescription
   */
  public String getTaskDescription () {
    return taskDescription;
  }

  /**
   * Set the value of money
   * @param newVar the new value of money
   */
  public void setMoney (Double newVar) {
    money = newVar;
  }

  /**
   * Get the value of money
   * @return the value of money
   */
  public Double getMoney () {
    return money;
  }

  /**
   * Set the value of scrumMaster
   * @param newVar the new value of scrumMaster
   */
  public void setScrumMaster (Employee newVar) {
    scrumMaster = newVar;
  }

  /**
   * Get the value of scrumMaster
   * @return the value of scrumMaster
   */
  public Employee getScrumMaster () {
    return scrumMaster;
  }

  /**
   * Set the value of status
   * @param newVar the new value of status
   */
  public void setStatus (TypeOfCompletion newVar) {
    status = newVar;
  }

  /**
   * Get the value of status
   * @return the value of status
   */
  public TypeOfCompletion getStatus () {
    return status;
  }

  public List<Employee> getTeam() {
    return team;
  }

  public void setTeam(List<Employee> team) {
    this.team = team;
  }

  /**
   * Set the value of createdDate
   * @param newVar the new value of createdDate
   */
  public void setCreatedDate (String newVar) {
    createdDate = newVar;
  }

  /**
   * Get the value of createdDate
   * @return the value of createdDate
   */
  public String getCreatedDate () {
    return createdDate;
  }

  /**
   * Set the value of deadline
   * @param newVar the new value of deadline
   */
  public void setDeadline (String newVar) {
    deadline = newVar;
  }

  /**
   * Get the value of deadline
   * @return the value of deadline
   */
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
