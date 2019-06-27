/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.pkg5.cmpsci.pkg182;

/**
 *
 * @author Textbook
 */
class Person<T, L> {
  long number;
  String name;
  Person<T, L> leftChild;
  Person<T, L> rightChild;

  public Person(String newName, long newNumber) {
  // Initializes tree node with item and no children.
    number = newNumber;
    name = newName;
    leftChild  = null;
    rightChild = null;
  }  // end constructor

  public Person(String newName, long newNumber,
                  Person<T, L> left, Person<T, L> right) {
  // Initializes tree node with item and
  // the left and right children references.
    number = newNumber;
    name = newName;
    leftChild  = left;
    rightChild = right;
  }  // end constructor

}  // end TreeNode
