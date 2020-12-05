package ru.sfedu.groupappcontrol.models;


import com.opencsv.bean.CsvBindByName;
import ru.sfedu.groupappcontrol.models.enums.TypeOfTester;

import java.util.Objects;

/**
 * Class Tester
 */
public class Tester extends Developer{

  //
  // Fields
  //
  @CsvBindByName
  private TypeOfTester typeOfTester;

  //
  // Constructors
  //
  public Tester () { };

  //
  // Methods
  //


  //
  // Accessor methods
  //

  /**
   * Set the value of typeOfTester
   * @param newVar the new value of typeOfTester
   */
  public void setTypeOfTester (TypeOfTester newVar) {
    typeOfTester = newVar;
  }

  /**
   * Get the value of typeOfTester
   * @return the value of typeOfTester
   */
  public TypeOfTester getTypeOfTester () {
    return typeOfTester;
  }

  //
  // Other methods
  //

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
