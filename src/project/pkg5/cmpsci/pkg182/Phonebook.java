package project.pkg5.cmpsci.pkg182;

import java.io.FileNotFoundException;
import java.io.FileWriter;  

/**
 * Project 5
 * Programmer: Austin Smith
 * Source Code: Phonebook
 * Due Date: 05/08/18
 * Description: An ADT (BST) that contains methods for the Menu class to use
 */
public class Phonebook {
    Person root = null;
    private boolean exists;
    public void Add(String name, long number) {
        try{
        root = AddRecursive(root, name, number);
        }
        catch (Exception ex) {
            System.out.println("Could not add to phonebook");
        }
    } 
    private Person AddRecursive(Person curr, String name, long number) {
        if(curr == null) {
            return new Person(name, number);
        }
        if(root.name.compareToIgnoreCase(name) > 0) {
            curr.leftChild = AddRecursive(curr.leftChild, name, number);
        }
        else if(root.name.compareToIgnoreCase(name) < 0) {
            curr.rightChild = AddRecursive(curr.rightChild, name, number);
        }
        else if(root.name.compareToIgnoreCase(name) == 0 || root.name.equalsIgnoreCase(name) == true) {
            System.out.println("Name is already in phonebook");
        }
        return curr;
    }
    public void Delete(String name) {
        if (name == null) {
            System.out.println("Enter a name to delete");
        }
        else
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
    public long Find(String name) {
            long num = FindRecursive(root, name).number;
            return num;
    }
    public String Find_name(String name) {
        String names = FindRecursive(root, name).name;
        return names;
    }
   // public Person Find_Person(String name) {
          //  return FindRecursive(root, name);
    //}
    private Person FindRecursive(Person curr, String name) throws NumberNotFoundException {
        exists = false;
        if (curr.name.compareTo(name) > 0) {
            return FindRecursive(curr.leftChild, name);
            }
        else if (curr.name.compareTo(name) < 0) {
            return FindRecursive(curr.rightChild, name);
        }
        else if (curr.name.equalsIgnoreCase(name)) {
            exists = true;
            return curr;
        }
        else{ 
            throw new NumberNotFoundException("Number does not exist in phonebook");
            }
    }
    public void Change(String name, long newPhone) {
        FindRecursive(root, name).number = newPhone;
    }
    private void TraverseInOrder(Person root, FileWriter fw) { //Now this is traversing in order entered, not alphabetic
        try{    
            if (root == null) {
                return;
            }
        TraverseInOrder(root.leftChild,fw);
        fw.write(root.name);
        fw.write("\n");
        fw.write(Long.toString(root.number));
        fw.write("\n");
        fw.write("\n");
        TraverseInOrder(root.rightChild,fw);
            
        }
        catch(Exception e){System.out.println(e);}    
    }
    
    public void Quit() throws FileNotFoundException {
        try{    
            try (FileWriter fw = new FileWriter("D:\\phonebook.txt")) { 
                if (root != null) {
                    TraverseInOrder(root,fw);
                }
            }    
          }catch(Exception e){System.out.println(e);}    
          System.out.println("Success...");    
     }    
}
