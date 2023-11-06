package User_Hompage;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import javax.swing.*;
import java.util.NoSuchElementException;

public class MainQueue {
    private final PriorityQueue<String> priorityQueue = new PriorityQueue<>();

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
    private TextField firstName_Input;

    @FXML
    private TextField lastName_Input;

    @FXML
    private TextField number_Input;
    @FXML
    private ChoiceBox<String> choiceBox;
    private String[] choices ={"General check up","Tooth Extraction","ramen","X-Ray", "Consultation"};

    @FXML
    private void initialize() {
        // Initialize the controller
        choiceBox.setItems(FXCollections.observableArrayList(choices));
    }

    @FXML
    public void handleAddButtonAction() {

        String first = firstName_Input.getText();
        String last = lastName_Input.getText();
        String contact = number_Input.getText();
        String reason = choiceBox.getValue();
        String input = first + " , " + last + " , " + contact + " , "+ reason;
        priorityQueue.add(input, 1); // Call the method to add the item to the priority queue
        System.out.println(priorityQueue);
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
        priorityQueue.add(input, 1); // Call the method to add the item to the priority queue
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
    void choiceBox_Input(MouseEvent event) {
    choiceBox.setItems(FXCollections.observableArrayList("General Check Up", "Tooth Extraction", "X-Ray", "Consultation"));
    }

    @FXML
    public void handleVisualizeButtonAction() {
        outputArea.setText(priorityQueue.toString());
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
        String input = JOptionPane.showInputDialog(null, "Enter new item:");
        int priority = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter new priority:"));
        try {
            priorityQueue.remove();
            priorityQueue.add(input, priority);
            refreshOutput();
        } catch (NoSuchElementException e) {
            error_message("Priority Queue is empty.");
        }
    }

    private void refreshOutput() {
        outputArea.setText(priorityQueue.toString());
    }

    public void error_message(String msg) {
        JOptionPane.showMessageDialog(null, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public TextArea getOutputArea() {
        return outputArea;
    }




//


}
