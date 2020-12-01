package ru.sfedu.groupappcontrol.models;


import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;
import com.opencsv.bean.CsvRecurse;
import org.simpleframework.xml.Attribute;
import ru.sfedu.groupappcontrol.models.enums.TaskTypes;
import ru.sfedu.groupappcontrol.models.enums.TypeOfCompletion;
import ru.sfedu.groupappcontrol.utils.EmployeeListConverter;

import java.util.List;

/**
 * Class Task
 */
public class Task extends BaseClass{

  //
  // Fields
  //
  @CsvBindByName
  private String taskDescription;
  @CsvBindByName
  private Double money;
  @CsvRecurse
  private Employee scrumMaster;
  @CsvBindByName
  private TypeOfCompletion status;
  @Attribute
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
