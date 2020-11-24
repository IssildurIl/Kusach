package ru.sfedu.groupappcontrol.models;


import ru.sfedu.groupappcontrol.models.enums.DeveloperTaskType;

/**
 * Class DevelopersTask
 */
public class DevelopersTask extends Task{

  //
  // Fields
  //

  private String developerComments;
  private DeveloperTaskType developerTaskType;

  //
  // Constructors
  //
  public DevelopersTask () { };

  //
  // Methods
  //


  //
  // Accessor methods
  //

  /**
   * Set the value of developerComments
   * @param newVar the new value of developerComments
   */
  public void setDeveloperComments (String newVar) {
    developerComments = newVar;
  }

  /**
   * Get the value of developerComments
   * @return the value of developerComments
   */
  public String getDeveloperComments () {
    return developerComments;
  }

  /**
   * Set the value of developerTaskType
   * @param newVar the new value of developerTaskType
   */
  public void setDeveloperTaskType (DeveloperTaskType newVar) {
    developerTaskType = newVar;
  }

  /**
   * Get the value of developerTaskType
   * @return the value of developerTaskType
   */
  public DeveloperTaskType getDeveloperTaskType () {
    return developerTaskType;
  }

  //
  // Other methods
  //

}
