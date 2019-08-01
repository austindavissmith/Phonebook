/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.pkg5.cmpsci.pkg182;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;

/**
 *
 * @author Textbook
 */
class Person {
  long number;
  String name;
  Person leftChild;
  Person rightChild;
  final SimpleStringProperty nListView;
  final SimpleStringProperty pnListView;

  Person(String newName, long newNumber) {
  // Initializes tree node with item and no children.
    number = newNumber;
    name = newName;
    leftChild  = null;
    rightChild = null;
    this.nListView = new SimpleStringProperty(newName);
    this.pnListView = new SimpleStringProperty(Long.toString(newNumber));
    
  }  // end constructor

  Person(String newName, long newNumber,
                  Person left, Person right) {
  // Initializes tree node with item and
  // the left and right children references.
    number = newNumber;
    name = newName;
    leftChild  = left;
    rightChild = right;
    this.nListView = new SimpleStringProperty(newName);
    this.pnListView = new SimpleStringProperty(Long.toString(newNumber));
  }  // end constructor
    
  String getName() {
    return nListView.get();
  }
 
  void setName(String fName) {
    nListView.set(fName);
    }
 
  String getNumber() {
    return pnListView.get();
    }
 
  void setNumber(long number) {
    pnListView.set(Long.toString(number));
    }
}  // end TreeNode
