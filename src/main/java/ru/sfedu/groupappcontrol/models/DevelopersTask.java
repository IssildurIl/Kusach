package ru.sfedu.groupappcontrol.models;


import com.opencsv.bean.CsvBindByName;
import ru.sfedu.groupappcontrol.models.enums.DeveloperTaskType;

import java.util.Objects;

/**
 * Class DevelopersTask
 */
public class DevelopersTask extends Task{

  //
  // Fields
  //
  @CsvBindByName
  private String developerComments;
  @CsvBindByName
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

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    if (!super.equals(o)) return false;
    DevelopersTask that = (DevelopersTask) o;
    return Objects.equals(developerComments, that.developerComments) &&
            developerTaskType == that.developerTaskType;
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), developerComments, developerTaskType);
  }

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

  @Override
  public String toString() {
    return "DevelopersTask{" +
            "developerComments='" + developerComments + '\'' +
            ", developerTaskType=" + developerTaskType +
            '}';
  }

}
