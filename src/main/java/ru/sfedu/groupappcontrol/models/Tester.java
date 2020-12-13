package ru.sfedu.groupappcontrol.models;


import com.opencsv.bean.CsvBindByName;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;
import ru.sfedu.groupappcontrol.models.enums.TypeOfTester;

import java.util.Objects;

/**
 * Class Tester
 */

@Root(name = "Tester")
public class Tester extends Developer{
  @Element
  @CsvBindByName
  private TypeOfTester typeOfTester;


  public Tester () { };

  public void setTypeOfTester (TypeOfTester newVar) {
    typeOfTester = newVar;
  }

  public TypeOfTester getTypeOfTester () {
    return typeOfTester;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    if (!super.equals(o)) return false;
    Tester tester = (Tester) o;
    return typeOfTester == tester.typeOfTester;
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), typeOfTester);
  }

  @Override
  public String toString() {
    return super.toString()+"Tester{" +
            "typeOfTester=" + typeOfTester +
            '}';
  }
}
