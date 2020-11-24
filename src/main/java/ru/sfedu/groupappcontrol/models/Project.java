package ru.sfedu.groupappcontrol.models;


import java.util.List;

/**
 * Class Project
 */
public class Project extends BaseClass{

  //
  // Fields
  //
  private String title;
  private String takeIntoDevelopment;
  private List<Task> task;

  //
  // Constructors
  //
  public Project () { };

  //
  // Methods
  //


  //
  // Accessor methods
  //

  /**
   * Set the value of title
   * @param newVar the new value of title
   */
  public void setTitle (String newVar) {
    title = newVar;
  }

  /**
   * Get the value of title
   * @return the value of title
   */
  public String getTitle () {
    return title;
  }

  /**
   * Set the value of takeIntoDevelopment
   * @param newVar the new value of takeIntoDevelopment
   */
  public void setTakeIntoDevelopment (String newVar) {
    takeIntoDevelopment = newVar;
  }

  /**
   * Get the value of takeIntoDevelopment
   * @return the value of takeIntoDevelopment
   */
  public String getTakeIntoDevelopment () {
    return takeIntoDevelopment;
  }

  public List<Task> getTask() {
    return task;
  }

  public void setTask(List<Task> task) {
    this.task = task;
  }

//
  // Other methods
  //

}
