package ru.sfedu.groupappcontrol.models;


import com.opencsv.bean.CsvBindByName;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;
import ru.sfedu.groupappcontrol.models.enums.BugStatus;

import java.util.Objects;

/**
 * Class TestersTask
 */
@Root(name = "TestersTask")
public class TestersTask extends Task{
  @Element
  @CsvBindByName
  private BugStatus bugStatus;
  @Element
  @CsvBindByName
  private String bugDescription;

  public TestersTask () { };

  public void setBugStatus (BugStatus newVar) {
    bugStatus = newVar;
  }

  public BugStatus getBugStatus () {
    return bugStatus;
  }

  public void setBugDescription (String newVar) {
    bugDescription = newVar;
  }

  public String getBugDescription () {
    return bugDescription;
  }


  @Override
  public String toString() {
    return super.toString()+"TestersTask{" +
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
