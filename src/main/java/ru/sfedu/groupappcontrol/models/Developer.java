package ru.sfedu.groupappcontrol.models;


import ru.sfedu.groupappcontrol.models.enums.ProgrammingLanguage;
import ru.sfedu.groupappcontrol.models.enums.TypeOfDevelopers;

import java.util.Objects;

/**
 * Class Developer
 */
public class Developer extends Employee {

  private TypeOfDevelopers status;
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
    return "Developer{" +
            "status=" + status +
            ", programmingLanguage=" + programmingLanguage +
            '}';
  }
}
