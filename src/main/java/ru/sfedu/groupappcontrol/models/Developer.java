package ru.sfedu.groupappcontrol.models;


import ru.sfedu.groupappcontrol.models.enums.ProgrammingLanguage;
import ru.sfedu.groupappcontrol.models.enums.TypeOfDevelopers;

/**
 * Class Developer
 */
public class Developer extends Employee {

  private TypeOfDevelopers status;
  private ProgrammingLanguage programmingLanguage;

  public Developer () { };

  public ProgrammingLanguage getProgrammingLanguage() {
    return programmingLanguage;
  }

  public void setProgrammingLanguage(ProgrammingLanguage programmingLanguage) {
    this.programmingLanguage = programmingLanguage;
  }

  public void setStatus (TypeOfDevelopers newVar) {
    status = newVar;
  }

  public TypeOfDevelopers getStatus () {
    return status;
  }


  public void setProgramming_language (ProgrammingLanguage newVar) {
    programmingLanguage = newVar;
  }


  public ProgrammingLanguage getProgramming_language () {
    return programmingLanguage;
  }

}
