package ru.sfedu.groupappcontrol.models;


import ru.sfedu.groupappcontrol.models.enums.TaskTypes;
import ru.sfedu.groupappcontrol.models.enums.TypeOfCompletion;

import java.util.List;

/**
 * Class Task
 */
public class Task extends BaseClass{

  //
  // Fields
  //
  private String taskDescription;
  private Double money;
  private Employee scrumMaster;
  private TypeOfCompletion status;
  private List<Employee> team;
  private String createdDate;
  private String deadline;
  private String lastUpdate;
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

  /**
   * Set the value of lastUpdate
   * @param newVar the new value of lastUpdate
   */
  public void setLastUpdate (String newVar) {
    lastUpdate = newVar;
  }

  /**
   * Get the value of lastUpdate
   * @return the value of lastUpdate
   */
  public String getLastUpdate () {
    return lastUpdate;
  }

  /**
   * Set the value of taskType
   * @param newVar the new value of taskType
   */
  public void setTaskType (TaskTypes newVar) {
    taskType = newVar;
  }

  /**
   * Get the value of taskType
   * @return the value of taskType
   */
  public TaskTypes getTaskType () {
    return taskType;
  }

  //
  // Other methods
  //

}
