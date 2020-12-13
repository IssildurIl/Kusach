package ru.sfedu.groupappcontrol.models;


import com.opencsv.bean.CsvBindByName;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;
import ru.sfedu.groupappcontrol.models.enums.ProgrammingLanguage;
import ru.sfedu.groupappcontrol.models.enums.TypeOfDevelopers;

import java.util.Objects;

/**
 * Class Developer
 */
@Root(name = "Developer")
public class Developer extends Employee {
  @Element
  @CsvBindByName
  private TypeOfDevelopers status;
  @Element
  @CsvBindByName
  private ProgrammingLanguage programmingLanguage;

  public Developer () { };

  public TypeOfDevelopers getStatus() {
    return status;
  }

  public void setStatus(TypeOfDevelopers status) {
    this.status = status;
  }

  public ProgrammingLanguage getProgrammingLanguage() {
    return programmingLanguage;
  }

  public void setProgrammingLanguage(ProgrammingLanguage programmingLanguage) {
    this.programmingLanguage = programmingLanguage;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    if (!super.equals(o)) return false;
    Developer developer = (Developer) o;
    return status == developer.status &&
            programmingLanguage == developer.programmingLanguage;
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), status, programmingLanguage);
  }

  @Override
  public String toString() {
    return super.toString()+"Developer{" +
            "status=" + status +
            ", programmingLanguage=" + programmingLanguage +
            '}';
  }
}
