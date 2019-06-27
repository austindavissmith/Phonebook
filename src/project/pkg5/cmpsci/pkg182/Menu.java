package project.pkg5.cmpsci.pkg182;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/**
 * Project 5
 * Programmer: Austin Smith
 * Source Code: Menu
 * Due Date: 05/08/18
 * Description: Creates a menu for a user to add, delete, and retrieve phone numbers 
 */
public class Menu {
    
    public static void main(String[] agrs) throws FileNotFoundException {
        Phonebook phoneBook = new Phonebook();
        // Open and Read File
        try{
            File file = new File("phonebook.txt");
            if(!file.exists()) {
                file.createNewFile();
            }
        }catch(IOException e) {
            System.out.println("File Error");
        }
        try {
            FileReader reader = new FileReader("phonebook.txt");
            BufferedReader bufferedReader = new BufferedReader(reader);
            String name = bufferedReader.readLine();
            Scanner numbers = new Scanner(bufferedReader);
            long number = numbers.nextLong();
            phoneBook.root = new Person(name, number);
        }catch(FileNotFoundException f) {
            System.out.println("Could not Read File");
        }catch(IOException r) {
            System.out.println("File Error");
        }
        boolean quit = false;
        do {
        
        Scanner input = new Scanner(System.in);
        System.out.println("Menu");
        System.out.println("---------------------");
        System.out.println("Select an Option: ");
        System.out.println("---------------------");
        System.out.println("1.) Add");
        System.out.println("2.) Delete");
        System.out.println("3.) Find");
        System.out.println("4.) Change");
        System.out.println("5.) Quit");
        int selection = input.nextInt();
        
        switch (selection) {
            case 1: //Add
                System.out.println("Enter a name you want to add: ");
                Scanner newName = new Scanner(System.in);
                String name = newName.nextLine();
                System.out.println("What is their phone number? ");
                Scanner newNumber = new Scanner(System.in);
                long pn = newNumber.nextLong();
                phoneBook.Add(name, pn);
                System.out.println(" ");
            break;
            case 2: //Delete
                System.out.println("Type the first and last name of the contact you wish to delete: ");
                Scanner deleteName = new Scanner(System.in);
                String name2 = deleteName.nextLine();
                phoneBook.Delete(name2);
            break;
            case 3: //Find
                System.out.println("Type the full name of the ocntact you wish to find ");
                System.out.println("(Seperate first and last name by a space): ");
                Scanner findName = new Scanner(System.in);
                System.out.println(" ");
                String name3 = findName.nextLine();
                phoneBook.Find(name3); 
                System.out.println(" ");
            break;
            case 4: //Change
                System.out.println("Who's phone number do you wish to change?: ");
                Scanner changeName = new Scanner(System.in);
                String name4 = changeName.nextLine();
                System.out.println("What is the new phone number you wish to assign to " + name4 + " ?");
                Scanner newPhone = new Scanner(System.in);
                long changeNumber = newPhone.nextLong();
                phoneBook.Find(name4);
                phoneBook.Change(name4, changeNumber);
            break;
            case 5: // Quit
                phoneBook.Quit();
                quit = true;
            break;
            default: 
                System.out.println("Not a valid option, please select an option from #1-5 ");
            }
        } while (!quit);
        //Use PrintWriter to output    
    }
}
