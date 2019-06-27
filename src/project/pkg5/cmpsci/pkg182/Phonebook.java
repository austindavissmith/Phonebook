package project.pkg5.cmpsci.pkg182;

import java.io.BufferedReader;
import java.io.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

/**
 * Project 5
 * Programmer: Austin Smith
 * Source Code: Phonebook
 * Due Date: 05/08/18
 * Description: An ADT that contains methods for the Menu class to use
 */
public class Phonebook {
    Person root;
    public void Add(String name, long number) {
        root = AddRecursive(root, name, number);
    } 
    public Person AddRecursive(Person curr, String name, long number) {
        if(curr == null) {
            return new Person(name, number);
        }
        if(name.charAt(0) < curr.name.charAt(0)) {
            curr.leftChild = AddRecursive(curr.leftChild, name, number);
        }
        else if(name.charAt(0) > curr.name.charAt(0)) {
            curr.rightChild = AddRecursive(curr.rightChild, name, number);
        }
        else if(name.charAt(0) == curr.name.charAt(0)) {
            if(name.charAt(1) < curr.name.charAt(1)) {
                curr.leftChild = AddRecursive(curr.leftChild, name, number);
            }
        }
        return curr;
    }
    public void Delete(String name) {
        root = DeleteRecursive(root, name);
    }
    Person DeleteRecursive(Person root, String name) {
        if (root == null) { 
            return root;
        }
        if (root.name.compareTo(name) > 0) {
            root.leftChild = DeleteRecursive(root.leftChild, name);
        }
        else if (root.name.compareTo(name) < 0) {
            root.rightChild = DeleteRecursive(root.rightChild, name);
        }
        else {
            if (root.leftChild == null) {
                return root.rightChild;
            }
            else if (root.rightChild == null) {
                return root.leftChild;
            }
            root.name = minValue(root.rightChild);
            root.rightChild = DeleteRecursive(root.rightChild, root.name);
        }
        return root;
    }
    String minValue(Person root) {
        String minv = root.name;
        while (root.leftChild != null) {
            minv = root.leftChild.name;
            root = root.leftChild;
        }
        return minv;
    }
    public void Find(String name) {
        System.out.println(FindRecursive(root, name).number);
        
    }
    public Person FindRecursive(Person curr, String name) throws NumberNotFoundException {
        if (curr.name.compareTo(name) > 0) {
            return FindRecursive(curr.leftChild, name);
            }
        else if (curr.name.compareTo(name) < 0) {
            return FindRecursive(curr.rightChild, name);
        }
        else if (curr.name.compareTo(name) == 0) {
            return curr;
        }
        else{ 
            throw new NumberNotFoundException("Number does not exist in phonebook");
            }
    }
    public void Change(String name, long newPhone) {
        FindRecursive(root, name).number = newPhone;
    }
    public void TraverseInOrder(Person root) {
    if (root != null) {
        TraverseInOrder(root.leftChild);
        System.out.print(" " + root.name);
        System.out.println(" " + root.number);
        TraverseInOrder(root.rightChild);
        }
    }
    
    public void Quit() throws FileNotFoundException {
        try {
        File outputFile = new File("Phonebook Out.txt");
        PrintWriter output = new PrintWriter(outputFile);
      
        output.println(root.leftChild.name);
        output.println(root.leftChild.number);
        output.println(root.name);
        output.println(root.number);
        output.println(root.rightChild.name);
        output.println(root.rightChild.number);
        
        output.close();
        }
        catch(FileNotFoundException e) {
            System.out.println("Phonebook Out.txt cannot be found. " + e.toString());
        }
    }
}
