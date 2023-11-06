package User_Hompage;

import com.example.dsa_final.Main;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.NoSuchElementException;

public class MainQueue {
    private final PriorityQueue<String> priorityQueue = new PriorityQueue<>();
    private static final String filePath = "C:\\Users\\Lenovo\\Desktop\\BST\\Dsa_final\\src\\Customers\\customer_data.txt";
    private String date;

    @FXML
    public TextArea outputArea;

    @FXML
    private TextField inputField;

    @FXML
    private Button addButton;

    @FXML
    private Button removeButton;

    @FXML
    private Button visualizeButton;

    @FXML
    private Button peekButton;

    @FXML
    private Button editButton;

    @FXML
    private Button addButtonNew;

    @FXML
    private Button walkInButton;
    @FXML
    private Button backButton;

    @FXML
    private TextField firstName_Input;

    @FXML
    private TextField lastName_Input;

    @FXML
    private TextField number_Input;
    @FXML
    private ChoiceBox<String> choiceBox;

    @FXML
    private RadioButton nineAm;
    @FXML
    private RadioButton tenAm;
    @FXML
    private RadioButton elevenAm;

    @FXML
    private RadioButton onePM;

    @FXML
    private RadioButton twoPm;

    @FXML
    private RadioButton threePm;



    private String[] choices ={"General check up","Tooth Extraction","ramen","X-Ray", "Consultation"};

    @FXML
    private void initialize() {
        ToggleGroup dateToggleGroup = new ToggleGroup();
        nineAm.setToggleGroup(dateToggleGroup);
        tenAm.setToggleGroup(dateToggleGroup);
        elevenAm.setToggleGroup(dateToggleGroup);
        onePM.setToggleGroup(dateToggleGroup);
        twoPm.setToggleGroup(dateToggleGroup);
        threePm.setToggleGroup(dateToggleGroup);

        dateToggleGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (dateToggleGroup.getSelectedToggle() != null) {
                RadioButton selectedRadio = (RadioButton) dateToggleGroup.getSelectedToggle();
                date = selectedRadio.getUserData().toString();
            }
        });

        // Set user data for radio buttons to store the date.
        nineAm.setUserData("9:00 AM");
        tenAm.setUserData("10:00 AM");
        elevenAm.setUserData("11:00 AM");
        onePM.setUserData("1:00 PM");
        twoPm.setUserData("2:00 PM");
        threePm.setUserData("3:00 PM");

        choiceBox.setItems(FXCollections.observableArrayList(choices));

        refreshOutput();
        handleLoadFileAction();
    }

    @FXML
    public void handleAddButtonAction() {


        // Extract input data from your UI components
        String first = firstName_Input.getText();
        String last = lastName_Input.getText();
        String contact = number_Input.getText();
        String reason = choiceBox.getValue();

        // Create a formatted input string
        String input = first + " , " + last + " , " + contact + " , " + date + " , " + reason;

        // Add the item to the priority queue
        priorityQueue.add(input, 1);

        // Refresh the output in your UI
        refreshOutput();
    }

    @FXML
    public void addButton_Click(ActionEvent event) {

        // refreshOutput();
    }
    @FXML
    public void HandleWalkInButtonAction(ActionEvent event) {

        String first = firstName_Input.getText();
        String last = lastName_Input.getText();
        String contact = number_Input.getText();
        String reason = choiceBox.getValue();
        String input = first + " , " + last + " , " + contact + " , "+ reason;
        priorityQueue.add(input, 5); // Call the method to add the item to the priority queue
        System.out.println(priorityQueue);
        refreshOutput();
    }

    @FXML
    public void firstName_Input(ActionEvent event) {

    }

    @FXML
    public void lastName_Input(ActionEvent event) {

    }

    @FXML
    public void number_Input(ActionEvent event) {

    }

    @FXML
    public void handleRemoveButtonAction() {
        try {
            String removedItem = priorityQueue.remove();
            refreshOutput();
            JOptionPane.showMessageDialog(null, "Removed: " + removedItem, "Item Removed", JOptionPane.INFORMATION_MESSAGE);
        } catch (NoSuchElementException e) {
            error_message("Priority Queue is empty.");
        }
    }


    @FXML
    public void handlePeekButtonAction() {
        try {
            String peekedItem = priorityQueue.peek();
            JOptionPane.showMessageDialog(null, "Peek: " + peekedItem, "Peeked Item", JOptionPane.INFORMATION_MESSAGE);
        } catch (NoSuchElementException e) {
            error_message("Priority Queue is empty.");
        }
    }

    @FXML
    public void handleEditButtonAction() {
        handleLoadFileAction();

        /*
        String input = JOptionPane.showInputDialog(null, "Enter new item:");
        int priority = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter new priority:"));
        try {
            priorityQueue.remove();
            priorityQueue.add(input, priority);
            refreshOutput();
        } catch (NoSuchElementException e) {
            error_message("Priority Queue is empty.");
        }

         */
    }

    private void refreshOutput() {

        outputArea.setText(priorityQueue.toString());

    }

    public void error_message(String msg) {
        JOptionPane.showMessageDialog(null, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }

    @FXML
    public void handleLoadFileAction() {
        File file = new File("C:\\Users\\Lenovo\\Desktop\\BST\\Dsa_final\\src\\Customers\\customer_data.txt");
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    String name = parts[0].trim();
                    String lastName = parts[1].trim();
                    String age = parts[2].trim();
                    String time = parts[3].trim();
                    String reason = parts[4].trim();
                    String input = name + " , " + lastName + " , " + age + " , " + time + " , " + reason;
                    priorityQueue.add(input, 1);
                }
            }
            refreshOutput();
        } catch (IOException e) {
            error_message("Error reading the file: " + e.getMessage());
        }
    }

    @FXML
    void nineAm_Action(ActionEvent event) {

    }

    @FXML
    void tenAm_Action(ActionEvent event) {

    }
    @FXML
    void elevenAm_Action(ActionEvent event) {

    }

    @FXML
    void onePM_Action(ActionEvent event) {

    }

    @FXML
    void twoPm_Action(ActionEvent event) {

    }

    @FXML
    void threePm_Action(ActionEvent event) {

    }

    @FXML
    void backButton_Click(ActionEvent event) throws IOException {
        Main m = new Main();
        m.changeScene("UserHomepage.fxml");
    }




//


}
