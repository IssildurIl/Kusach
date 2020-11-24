package ru.sfedu.groupappcontrol.models;


import ru.sfedu.groupappcontrol.models.enums.TypeOfTester;

/**
 * Class Tester
 */
public class Tester extends Developer{

  //
  // Fields
  //

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

}
