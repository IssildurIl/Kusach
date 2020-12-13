package ru.sfedu.groupappcontrol.models;


import com.opencsv.bean.CsvBindByName;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;
import ru.sfedu.groupappcontrol.models.enums.DeveloperTaskType;

import java.util.Objects;

/**
 * Class DevelopersTask
 */
@Root(name = "DevelopersTask")
public class DevelopersTask extends Task{
  @Element
  @CsvBindByName
  private String developerComments;
  @Element
  @CsvBindByName
  private DeveloperTaskType developerTaskType;

  public DevelopersTask () { };

  public void setDeveloperComments (String newVar) {
    developerComments = newVar;
  }

  public String getDeveloperComments () {
    return developerComments;
  }

  public void setDeveloperTaskType (DeveloperTaskType newVar) {
    developerTaskType = newVar;
  }

  public DeveloperTaskType getDeveloperTaskType () {
    return developerTaskType;
  }


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

  @Override
  public String toString() {
    return super.toString()+"DevelopersTask{" +
            "developerComments='" + developerComments + '\'' +
            ", developerTaskType=" + developerTaskType +
            '}';
  }
}
