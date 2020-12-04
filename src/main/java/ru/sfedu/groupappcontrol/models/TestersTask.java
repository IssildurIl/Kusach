package ru.sfedu.groupappcontrol.models;


import com.opencsv.bean.CsvBindByName;
import ru.sfedu.groupappcontrol.models.enums.BugStatus;

import java.util.Objects;

/**
 * Class TestersTask
 */
public class TestersTask extends Task{

  //
  // Fields
  //
  @CsvBindByName
  private BugStatus bugStatus;
  @CsvBindByName
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

  @Override
  public String toString() {
    return "TestersTask{" +
            "bugStatus=" + bugStatus +
            ", bugDescription='" + bugDescription + '\'' +
            '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    if (!super.equals(o)) return false;
    TestersTask that = (TestersTask) o;
    return bugStatus == that.bugStatus &&
            Objects.equals(bugDescription, that.bugDescription);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), bugStatus, bugDescription);
  }
}
