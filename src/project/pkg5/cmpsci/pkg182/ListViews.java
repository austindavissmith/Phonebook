/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.pkg5.cmpsci.pkg182;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ListViews extends Application {
    private final static TableView<Person> table = new TableView<>();
    private final static ObservableList<Person> data = 
            FXCollections.observableArrayList(new Person(null, 0));
    final HBox hb = new HBox();
    private static ObservableList<Person> personSelected, allPersons;
    
  public static void main(String[] args) {
    launch(args);
  }
  public static void addUpdateList(TextField txt, TextField txt1) {
      try{
        String name = txt.getText();
        if (name.isEmpty() || txt1.getText().isEmpty()) {
            throw new NumberFormatException("Enter a name and number");
        }
        long num = Long.parseLong(txt1.getText());
        data.add(new Person(name,num)); 
        txt.clear();
        txt1.clear();
        }    
        catch (NumberFormatException f) { //No number error
                Stage popupwindow = new Stage();
                popupwindow.initModality(Modality.APPLICATION_MODAL);
                Label label3 = new Label("Please enter a number");
                Button close = new Button("Close");
                close.setOnAction(g -> popupwindow.close());
                VBox layout = new VBox(10);
                layout.getChildren().addAll(label3, close);
                layout.setAlignment(Pos.BASELINE_CENTER);
                Scene scene1 = new Scene(layout, 300, 80);
                popupwindow.setScene(scene1);
                popupwindow.showAndWait();
            }
        catch (IllegalArgumentException exception) { //No name error
                    Stage popupwindow = new Stage();
                    popupwindow.initModality(Modality.APPLICATION_MODAL);
                    Label label3 = new Label("Please enter a Person to add");
                    Button close = new Button("Close");
                    close.setOnAction(h -> popupwindow.close());
                    VBox layout = new VBox(10);
                    layout.getChildren().addAll(label3, close);
                    layout.setAlignment(Pos.BASELINE_CENTER);
                    Scene scene1 = new Scene(layout, 300, 80);
                    popupwindow.setScene(scene1);
                    popupwindow.showAndWait();
                }
  }
    public static void addButtonClicked(TextField txt, TextField txt1) {
      try{
        String name = txt.getText();
        if (name.isEmpty() || txt1.getText().isEmpty()) {
            throw new NumberFormatException("Enter a name and number");
        }
        long num = Long.parseLong(txt1.getText());
        data.add(new Person(name,num)); 
        gui.phoneBook.Add(name, num); //Adding to the phonebook from list view works but is finnicky
        txt.clear();
        txt1.clear();
        }    
        catch (NumberFormatException f) { //No number error
                Stage popupwindow = new Stage();
                popupwindow.initModality(Modality.APPLICATION_MODAL);
                Label label3 = new Label("Please enter a number");
                Button close = new Button("Close");
                close.setOnAction(g -> popupwindow.close());
                VBox layout = new VBox(10);
                layout.getChildren().addAll(label3, close);
                layout.setAlignment(Pos.BASELINE_CENTER);
                Scene scene1 = new Scene(layout, 300, 80);
                popupwindow.setScene(scene1);
                popupwindow.showAndWait();
            }
        catch (IllegalArgumentException exception) { //No name error
                    Stage popupwindow = new Stage();
                    popupwindow.initModality(Modality.APPLICATION_MODAL);
                    Label label3 = new Label("Please enter a Person to add");
                    Button close = new Button("Close");
                    close.setOnAction(h -> popupwindow.close());
                    VBox layout = new VBox(10);
                    layout.getChildren().addAll(label3, close);
                    layout.setAlignment(Pos.BASELINE_CENTER);
                    Scene scene1 = new Scene(layout, 300, 80);
                    popupwindow.setScene(scene1);
                    popupwindow.showAndWait();
                }
  }
    public static void removeButtonClicked() {
        //ObservableList<Person> personSelected, allPersons;
        allPersons = table.getItems();
        String name = table.getSelectionModel().getSelectedItem().getName();
        personSelected = table.getSelectionModel().getSelectedItems();
        personSelected.forEach(allPersons::remove);
            if (name != null) {
                gui.phoneBook.Delete(name);
                gui.deleted(name);
            }
            else throw new NullPointerException("Null name");
    }
    public static void removeFromGUI(String name) {
        allPersons = table.getItems();
        personSelected = table.getItems().filtered(person -> person.nListView.get().equals(name));
        System.out.println(personSelected.get(0).nListView.get());
        try{
        personSelected.forEach(allPersons::remove);
        }
        catch (NoSuchElementException h) {
            System.out.println("OOF");
        }
    }
    public static void changeInGUI(String name, long number) {
        allPersons = table.getItems();
        personSelected = table.getItems().filtered(person -> person.nListView.get().equals(name));
        try{
        personSelected.forEach(allPersons::remove);
        }
        catch (NoSuchElementException h) {
            System.out.println("OOF");
        }
        data.add(new Person(name, number));
    }
    @Override
  public void start(Stage primaryStage) throws FileNotFoundException {
    imports();
    Scene scene = new Scene(new Group());
    primaryStage.setWidth(675);
    primaryStage.setHeight(550);
    primaryStage.setX(1230);
    primaryStage.setY(150);

    TableColumn firstNameCol = new TableColumn("Name");
    firstNameCol.setMinWidth(200);
    firstNameCol.setCellValueFactory(
            new PropertyValueFactory<>("Name"));
    
    TableColumn phoneCol = new TableColumn("Number");
    phoneCol.setMinWidth(200);
    phoneCol.setCellValueFactory(
            new PropertyValueFactory<>("Number"));
    
    table.setItems(data);
    table.getColumns().addAll(firstNameCol, phoneCol);
    
    TextField txt = new TextField("");
    Label label1 = new Label("Name");
    TextField txt1 = new TextField("");
    Label label2 = new Label("Number");
    
    final Button addButton = new Button("Add");
    addButton.setOnAction((ActionEvent e) -> addButtonClicked(txt, txt1));
    
    final Button removeButton = new Button("Remove");
    removeButton.setOnAction((ActionEvent r) -> removeButtonClicked());
 
    hb.getChildren().addAll(addButton, removeButton, label1, txt, label2, txt1);
    hb.setSpacing(6);
    
    final VBox vbox1 = new VBox();
    vbox1.setSpacing(5);
    vbox1.setPadding(new Insets(10, 0, 0, 10));
    vbox1.getChildren().addAll(table, hb);
    
    ((Group) scene.getRoot()).getChildren().addAll(vbox1);

    primaryStage.setTitle("Phonebook List View");
    primaryStage.setScene(scene);
    primaryStage.show();
  }
  public static class Person {
 
        private final SimpleStringProperty nListView;
        private final SimpleStringProperty pnListView;
 
        private Person(String name, long number) {
            this.nListView = new SimpleStringProperty(name);
            this.pnListView = new SimpleStringProperty(Long.toString(number));
        }
 
        public String getName() {
            return nListView.get();
        }
 
        public String getNumber() {
            return pnListView.get();
        }
        public Person removes(String name, long number) {
            return Person.this;
        }
    }
  public void imports() throws FileNotFoundException {
      String filePath = "D:\\phonebook.txt";
        File file = new File(filePath);
        try{
            if (file.createNewFile()) {
                System.out.println("File created");
            }
            else {
                System.out.println("File already exists");
            }
        Scanner sc = new Scanner(file); 
        String name;
        String number;
        while (sc.hasNextLine()) {
            name = sc.nextLine();
            number = sc.nextLine();
            String skip = sc.nextLine();
            long num = Long.parseLong(number);
            data.add(new Person(name, num));   
            }
        }
        catch (IOException e) {
        }
        catch (NoSuchElementException f) {
            System.out.println("File is empty, nothing to import");
        }
        data.remove(0);
  }
  public static void exitApplication(ActionEvent event) {
    Platform.exit();
    }
  public void NumberFormatException() {
      Stage popupwindow = new Stage();
      popupwindow.initModality(Modality.APPLICATION_MODAL);
      Label label3 = new Label("Please enter a number");
      Button close = new Button("Close");
      close.setOnAction(g -> popupwindow.close());
      VBox layout = new VBox(10);
      layout.getChildren().addAll(label3, close);
      layout.setAlignment(Pos.BASELINE_CENTER);
      Scene scene1 = new Scene(layout, 300, 80);
      popupwindow.setScene(scene1);
      popupwindow.showAndWait();
  }
}
