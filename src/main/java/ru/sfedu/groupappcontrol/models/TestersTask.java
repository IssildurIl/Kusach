package ru.sfedu.groupappcontrol.models;


import ru.sfedu.groupappcontrol.models.enums.BugStatus;

/**
 * Class TestersTask
 */
public class TestersTask extends Task{

  //
  // Fields
  //

  private BugStatus bugStatus;
  private String bugDescription;

  //
  // Constructors
  //
  public TestersTask () { };

  //
  // Methods
  //


  //
  // Accessor methods
  //

  /**
   * Set the value of bugStatus
   * @param newVar the new value of bugStatus
   */
  public void setBugStatus (BugStatus newVar) {
    bugStatus = newVar;
  }

  /**
   * Get the value of bugStatus
   * @return the value of bugStatus
   */
  public BugStatus getBugStatus () {
    return bugStatus;
  }

  /**
   * Set the value of bugDescription
   * @param newVar the new value of bugDescription
   */
  public void setBugDescription (String newVar) {
    bugDescription = newVar;
  }

  /**
   * Get the value of bugDescription
   * @return the value of bugDescription
   */
  public String getBugDescription () {
    return bugDescription;
  }


  //
  // Other methods
  //

}
