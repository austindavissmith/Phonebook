package project.pkg5.cmpsci.pkg182;

import javafx.application.Application;
import javafx.event.*;
import javafx.scene.*;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.*;
import java.util.NoSuchElementException;
import java.util.Scanner;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.stage.Modality;

/**
 * @author Austin Smith
 * Student at California State University Northridge(CSUN)
 * Date: 7/9/2019
 */
public class gui extends Application {
        String names;
        long numbers;
        static Phonebook phoneBook = new Phonebook(); //Creating a phonebook variable to access across classes
        static ListViews list = new ListViews(); //Creating a Listview gui to access in the gui class
        static Stage secondaryStage = new Stage(); //Creating a static secondary stage to start the ListView gui
    public static void main(String[] args) {
        launch(args); //Launches the gui and start method
    }
    
        @Override
    public void start(Stage primaryStage) throws Exception {
        String filePath = "D:\\phonebook.txt"; //Is there a smarter location to save file (Maybe some people don't have a D:\\)
        File file = new File(filePath); 
        try{ // Create new file is one does not exist
            if (file.createNewFile()) {
                System.out.println("File created");
            }
            else
                System.out.println("File already exists");
        Scanner sc = new Scanner(file); //Create a scanner to read the contents of the file
        String name;
        String number;
        while (sc.hasNextLine()) { //File is formatted in Name, then number, then empty line
            name = sc.nextLine();
            number = sc.nextLine();
            String skip = sc.nextLine(); // Must skip the empty line to read file correctly
            long num = Long.parseLong(number); //Must parse long from scanned string
            add(name, num); //Add them to the phonebook
            }
        }
        catch (IOException e) {
            System.out.println("File does not exist");
        }
        catch (NoSuchElementException f) {
            System.out.println("File is empty, nothing to import");
        }
        primaryStage.setTitle("Phonebook"); //Sets the title of the gui
        Label label1 = new Label("Name"); //Label for textfield1
        Label label2 = new Label("Phone Number"); //Label for textfield2
        TextField textField1 = new TextField(""); //Create new empty textfield for name
        TextField textField2 = new TextField(""); //Create new empty textfield for number
        Button insertBtn = new Button("Insert Person");
        Button findBtn = new Button("Search");
        Button deleteBtn = new Button("Delete Person");
        Button exitBtn = new Button("Save & Exit");
        Button chgBtn = new Button("Change Existing Contact Number");
        
        exitBtn.setOnAction((ActionEvent event) -> { //Write the phonebook to textfile upon exit
            try {
                phoneBook.Quit();
            } catch (FileNotFoundException | NullPointerException ex) {
                System.out.println("Error" + ex.toString());
            }
        });
        
        DropShadow shadow = new DropShadow(); //Addsdrop shadow effect to all buttons on gui
        insertBtn.setEffect(shadow);
        insertBtn.setEffect(shadow);
        findBtn.setEffect(shadow);
        deleteBtn.setEffect(shadow);
        chgBtn.setEffect(shadow);
        exitBtn.setEffect(shadow);
        
        insertBtn.setOnAction((ActionEvent event) -> {
            try { //Makes sure the name field is not empty
            names = textField1.getText();
            if (names.isEmpty()) {
                throw new IllegalArgumentException("Enter a name");
            }
            numbers = Long.parseLong(textField2.getText()); //Parses the number string to a long type
            if (find(names) == true) { //Checks to see if name is already in the phonebook
                alreadyExists(names, numbers); //Throws message that number was not added bc Person already in phonebook
                return;
            }
                add(names, numbers); //Adds person to phonebook
                ListViews.addUpdateList(textField1, textField2); //Update the ListView gui to reflect new Person
                textField1.clear(); //Clear the textfields
                textField2.clear();
                added(names); //Shows successful add message
            }
            catch (NumberFormatException f) { //No number error
                Stage popupwindow = new Stage();
                popupwindow.initModality(Modality.APPLICATION_MODAL);
                Label label3 = new Label("Please enter a number");
                Button close = new Button("Close");
                close.setOnAction(e -> popupwindow.close());
                VBox layout = new VBox(10);
                layout.getChildren().addAll(label3, close);
                layout.setAlignment(Pos.BASELINE_CENTER);
                Scene scene1 = new Scene(layout, 300, 80);
                popupwindow.setScene(scene1);
                popupwindow.showAndWait();
            }
            catch (IllegalArgumentException exception) { //No name error
                if (names.isEmpty()) {
                    Stage popupwindow = new Stage();
                    popupwindow.initModality(Modality.APPLICATION_MODAL);
                    Label label3 = new Label("Please enter a Person to add");
                    Button close = new Button("Close");
                    close.setOnAction(e -> popupwindow.close());
                    VBox layout = new VBox(10);
                    layout.getChildren().addAll(label3, close);
                    layout.setAlignment(Pos.BASELINE_CENTER);
                    Scene scene1 = new Scene(layout, 300, 80);
                    popupwindow.setScene(scene1);
                    popupwindow.showAndWait();
                }
            }
        });
        
        findBtn.setOnAction((ActionEvent event) -> {
            try{ 
            names = textField1.getText();
            long initTime = System.nanoTime(); //Checks the time taken for the search
            find(names); //Find the Person in the phonebook
            long elapsedTime = System.nanoTime() - initTime;
            System.out.println("Time taken for search in nanoseconds: " + elapsedTime);
            if (names.isEmpty()) { //Checks to see if textfield is empty
                throw new IllegalArgumentException("Please enter a person to search for");
            }
            else if (find(names) != true) { //Person not found error
                Stage popupwindow = new Stage();
                popupwindow.initModality(Modality.APPLICATION_MODAL);
                Label label3 = new Label(names + " not found");
                Button close = new Button("Close");
                close.setOnAction(e -> popupwindow.close());
                VBox layout = new VBox(10);
                layout.getChildren().addAll(label3, close);
                layout.setAlignment(Pos.BASELINE_CENTER);
                Scene scene1 = new Scene(layout, 300, 125);
                popupwindow.setScene(scene1);
                popupwindow.showAndWait();
            }  
            else { //Person found, displays their name and number
                Stage popupwindow = new Stage();
                popupwindow.initModality(Modality.APPLICATION_MODAL);
                Label label3 = new Label(names);
                Label label4 = new Label("" + phoneBook.Find(names));
                Button close = new Button("Close");
                close.setOnAction(e -> popupwindow.close());
                VBox layout = new VBox(10);
                layout.getChildren().addAll(label3, label4, close);
                layout.setAlignment(Pos.BASELINE_CENTER);
                Scene scene1 = new Scene(layout, 300, 125);
                popupwindow.setScene(scene1);
                popupwindow.showAndWait();
            }
            textField2.clear();
            }
            catch (IllegalArgumentException ex){ //No name error
                Stage popupwindow = new Stage();
                popupwindow.initModality(Modality.APPLICATION_MODAL);
                Label label3 = new Label("Please enter a Person to search for");
                Button close = new Button("Close");
                close.setOnAction(e -> popupwindow.close());
                VBox layout = new VBox(10);
                layout.getChildren().addAll(label3, close);
                layout.setAlignment(Pos.BASELINE_CENTER);
                Scene scene1 = new Scene(layout, 300, 80);
                popupwindow.setScene(scene1);
                popupwindow.showAndWait();
            }
        });
        
        deleteBtn.setOnAction((ActionEvent event) -> {
            try{ 
            names = textField1.getText();
            if (names.isEmpty()) {
                throw new IllegalArgumentException("Enter a name");
            }
            else if (find(names) == false) { //Person not found error
                    Stage popupwindow = new Stage();
                    popupwindow.initModality(Modality.APPLICATION_MODAL);
                    Label label3 = new Label("Person not in phonebook");
                    Button close = new Button("Close");
                    close.setOnAction(e -> popupwindow.close());
                    VBox layout = new VBox(10);
                    layout.getChildren().addAll(label3, close);
                    layout.setAlignment(Pos.BASELINE_CENTER);
                    Scene scene1 = new Scene(layout, 300, 80);
                    popupwindow.setScene(scene1);
                    popupwindow.showAndWait();
            }
            else{
            delete(names); //Delete Person from phonebook
            ListViews.removeFromGUI(names); //Deletes Person from ListView
            deleted(names); //Displays message of successful deletion
            textField1.clear();
            textField2.clear();
            }
            }
            catch (IllegalArgumentException exception) { //No name entered error
                if (names.isEmpty()) {
                    Stage popupwindow = new Stage();
                    popupwindow.initModality(Modality.APPLICATION_MODAL);
                    Label label3 = new Label("Please enter a name to delete");
                    Button close = new Button("Close");
                    close.setOnAction(e -> popupwindow.close());
                    VBox layout = new VBox(10);
                    layout.getChildren().addAll(label3, close);
                    layout.setAlignment(Pos.BASELINE_CENTER);
                    Scene scene1 = new Scene(layout, 300, 80);
                    popupwindow.setScene(scene1);
                    popupwindow.showAndWait();
                }
            }
        });
        
        chgBtn.setOnAction((ActionEvent event) -> {
            try{
            names = textField1.getText();
            if (names.isEmpty()) {
                throw new IllegalArgumentException("Enter a name");
            }
            numbers = Long.parseLong(textField2.getText());
            change(names, numbers);
            ListViews.changeInGUI(names, numbers);
            changed(names);
            textField1.clear();
            textField2.clear();
            }
            catch (NullPointerException e){ //Person not found in phonebook
                Stage popupwindow = new Stage();
                popupwindow.initModality(Modality.APPLICATION_MODAL);
                Label label3 = new Label("Person does not exist in phonebook");
                Button close = new Button("Close");
                close.setOnAction(ef -> popupwindow.close());
                VBox layout = new VBox(10);
                layout.getChildren().addAll(label3, close);
                layout.setAlignment(Pos.BASELINE_CENTER);
                Scene scene1 = new Scene(layout, 300, 80);
                popupwindow.setScene(scene1);
                popupwindow.showAndWait();
            }
            catch (NumberFormatException f) { //Number not entered error
                Stage popupwindow = new Stage();
                popupwindow.initModality(Modality.APPLICATION_MODAL);
                Label label3 = new Label("Please enter a number");
                Button close = new Button("Close");
                close.setOnAction(e -> popupwindow.close());
                VBox layout = new VBox(10);
                layout.getChildren().addAll(label3, close);
                layout.setAlignment(Pos.BASELINE_CENTER);
                Scene scene1 = new Scene(layout, 300, 80);
                popupwindow.setScene(scene1);
                popupwindow.showAndWait();
            }
            catch (IllegalArgumentException g) { //Name not entered error
                Stage popupwindow = new Stage();
                popupwindow.initModality(Modality.APPLICATION_MODAL);
                Label label3 = new Label("Please enter a name");
                Button close = new Button("Close");
                close.setOnAction(e -> popupwindow.close());
                VBox layout = new VBox(10);
                layout.getChildren().addAll(label3, close);
                layout.setAlignment(Pos.BASELINE_CENTER);
                Scene scene1 = new Scene(layout, 300, 80);
                popupwindow.setScene(scene1);
                popupwindow.showAndWait();
            }
    });
        exitBtn.setOnAction((ActionEvent event) -> {
            exitApplication(event);
        });
        
        BorderPane pane = new BorderPane();
        pane.setPadding(new Insets(70));
        VBox paneCenter = new VBox();
        paneCenter.setSpacing(8);
        pane.setCenter(paneCenter);
        paneCenter.getChildren().add(label1);
        paneCenter.getChildren().add(textField1);
        paneCenter.getChildren().add(label2);
        paneCenter.getChildren().add(textField2);
        paneCenter.getChildren().add(insertBtn);
        paneCenter.getChildren().add(chgBtn);
        paneCenter.getChildren().add(findBtn);
        paneCenter.getChildren().add(deleteBtn);
        paneCenter.getChildren().add(exitBtn);
        Scene scene = new Scene(pane, 550, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
        
        list.start(secondaryStage);
    }
    
    public void exitApplication(ActionEvent event) {
    Platform.exit();
    ListViews.exitApplication(event);
    }
    @Override
    public void stop(){
        try {
            phoneBook.Quit();
        }
        catch(FileNotFoundException e) {
            System.out.println("Phonebook Out.txt cannot be found. " + e.toString());
        }
    }
    
    static private void add(String name, long number)  {
        try{
            phoneBook.Add(name, number);
        }
        catch (Exception e) {
            System.out.println("poop");
        }
    }
    static private boolean find(String name) {
        boolean exists = true;
        try {
            phoneBook.Find(name);
        }
        catch(NullPointerException e){
            exists = false;
        }
        return exists;
    }
    static private void delete(String name) {
        phoneBook.Delete(name);
        
    }
    static private void change(String name, long number) {
        if (name == null) {
            System.out.println("Enter a name into the text field");
        }
        else
            phoneBook.Change(name, number);
    }
    public void Exception() {
        System.out.println("Please Enter a name");
    }
    public void IllegalArgumentException() {
        System.out.println("Enter a name");
    }
    public static void alreadyExists(String name, long number) {
        Stage popupwindow = new Stage();
        popupwindow.initModality(Modality.APPLICATION_MODAL);
        popupwindow.setTitle("This is a pop up window");
        Label label1= new Label("Person already exists. Would you like to edit their number?");
        Button button1= new Button("Yes");
        Button button2 = new Button("No");
        button2.setOnAction(e -> popupwindow.close());
        button1.setOnAction((ActionEvent event) -> {
                change(name, number);
                popupwindow.close();
        });
        VBox layout= new VBox(10);
        layout.getChildren().addAll(label1, button1, button2);
        layout.setAlignment(Pos.CENTER);
        Scene scene1= new Scene(layout, 400, 250);
        popupwindow.setScene(scene1);
        popupwindow.showAndWait();   
    }
    public static void added(String name) {
        Stage popupwindow = new Stage();
        popupwindow.initModality(Modality.APPLICATION_MODAL);
        Label label1 = new Label(name + " was added!");
        Button close = new Button("Close");
        close.setOnAction(e -> popupwindow.close());
        VBox layout = new VBox(10);
        layout.getChildren().addAll(label1, close);
        layout.setAlignment(Pos.BASELINE_CENTER);
        Scene scene1 = new Scene(layout, 300, 100);
        popupwindow.setScene(scene1);
        popupwindow.showAndWait();
    }
    public static void deleted(String name) {
        Stage popupwindow = new Stage();
        popupwindow.initModality(Modality.APPLICATION_MODAL);
        Label label1 = new Label(name + " was deleted");
        Button close = new Button("Close");
        close.setOnAction(e -> popupwindow.close());
        VBox layout = new VBox(10);
        layout.getChildren().addAll(label1, close);
        layout.setAlignment(Pos.BASELINE_CENTER);
        Scene scene1 = new Scene(layout, 300, 100);
        popupwindow.setScene(scene1);
        popupwindow.showAndWait();
    }
    public static void changed(String name) {
        Stage popupwindow = new Stage();
        popupwindow.initModality(Modality.APPLICATION_MODAL);
        Label label1 = new Label(name + "'s number was changed!");
        Button close = new Button("Close");
        close.setOnAction(e -> popupwindow.close());
        VBox layout = new VBox(10);
        layout.getChildren().addAll(label1, close);
        layout.setAlignment(Pos.BASELINE_CENTER);
        Scene scene1 = new Scene(layout, 300, 100);
        popupwindow.setScene(scene1);
        popupwindow.showAndWait();
    }
}
