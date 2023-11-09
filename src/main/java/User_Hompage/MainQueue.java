package User_Hompage;

import com.example.dsa_final.Main;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import javax.swing.*;
import java.io.*;
import java.util.*;

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

    @FXML
    private Button refreshBtn;
    @FXML
    private Label reservationFive;

    @FXML
    private Label reservationFour;

    @FXML
    private Label reservationOne;

    @FXML
    private Label reservationThree;

    @FXML
    private Label reservationTwo;
    @FXML
    private Label reservationSix;

    @FXML
    private Label currentReservation;
    @FXML
    private TextField age;
    @FXML
    Label currentTime;


    @FXML
    private void updateCurrentTime() {
        currentTime.setText(date); // Set the text of the "currentTime" label to the selected time
    }



    private final String[] choices ={"General check up","Tooth Extraction","Teeth whitening","X-Ray", "Consultation", "Braces"};

    private PriorityQueue originalQueue;
    @FXML
    private void initialize() {
        ToggleGroup dateToggleGroup = new ToggleGroup();
        originalQueue = new PriorityQueue<>();
        nineAm.setToggleGroup(dateToggleGroup);
        tenAm.setToggleGroup(dateToggleGroup);
        elevenAm.setToggleGroup(dateToggleGroup);
        onePM.setToggleGroup(dateToggleGroup);
        twoPm.setToggleGroup(dateToggleGroup);
        threePm.setToggleGroup(dateToggleGroup);
        disableRadioButtons(false);


        dateToggleGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (dateToggleGroup.getSelectedToggle() != null) {
                RadioButton selectedRadio = (RadioButton) dateToggleGroup.getSelectedToggle();
                date = selectedRadio.getUserData().toString();
                updateCurrentReservation(); // Call the method to update the "currentReservation" label
                updateCurrentTime(); // Call the method to update the "currentTime" label
                updateReservationLabels(); // Call the method to update reservation labels
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
        updateReservationLabels(); // Call the method to initially update the reservation labels
    }

    @FXML
    public void handleAddButtonAction() {

        // Extract input data from your UI components
        String first = firstName_Input.getText();
        String last = lastName_Input.getText();
        String contact = number_Input.getText();
        String ages = age.getText();
        String reason = choiceBox.getValue();

        // Check if any of the input fields are empty
        if (first.isEmpty()|| !isValidInput(first))  {
            error_message("Invalid input first name");
        }else if(last.isEmpty() || !isValidInput(last)){
            error_message("Invalid input last name");
        }else if(contact.isEmpty()|| !isValidContactNumber(contact)){
            error_message("Invalid input contact number");
        }else if(reason == null ){
            error_message("Invalid reason");
        }else if(date == null ){
            error_message("Please choose time");
        }else if(age == null || !isValidInputAge(ages)){
            error_message("Invalid input age");
        } else {
            // Use regular expressions to check for special characters in first name, last name, and contact
                try {
                    long contactNumber = Long.parseLong(contact);
                    int priority;

                    // Set priority based on the date
                    switch (date) {
                        case "9:00 AM":
                            priority = 11;
                            break;
                        case "10:00 AM":
                            priority = 9;
                            break;
                        case "11:00 AM":
                            priority = 7;
                            break;
                        case "1:00 PM":
                            priority = 5;
                            break;
                        case "2:00 PM":
                            priority = 3;
                            break;
                        case "3:00 PM":
                            priority = 1;
                            break;
                        default:
                            priority = 1; // Default priority
                            break;
                    }

                    // Create a formatted input string
                    String input = first + " , " + last + " , " + contact + " , " + date + " , " + ages + " , " + reason;

                    // Add the item to the priority queue with the determined priority
                    priorityQueue.add(input, priority);
                    saveInputToFile(input);
                    // Refresh the output in your UI
                    refreshOutput();

                    // Show a success message
                    JOptionPane.showMessageDialog(null, "Successful booking!", "Success", JOptionPane.INFORMATION_MESSAGE);
                } catch (NumberFormatException e) {
                    error_message("Invalid contact number. Please enter a non-negative number.");
                }

            if (!priorityQueue.isEmpty()) {
                disableRadioButtons(true);
                refreshOutput();
                // If priority queue is empty, disable the radio buttons

            } else {
                refreshOutput();
                // If priority queue has items, enable the radio buttons
                disableRadioButtons(false);
            }
            }
        }

    private boolean isValidInput(String input) {
        return input.matches("^[a-zA-Z0-9]*$");
    }

    private boolean isValidInputAge(String input) {
        return input.matches("^[0-9]*$");
    }

    private boolean isValidContactNumber(String contact) {
        return contact.matches("^\\d{11}$");
    }
    private void disableRadioButtons(boolean disable) {
        nineAm.setDisable(disable);
        tenAm.setDisable(disable);
        elevenAm.setDisable(disable);
        onePM.setDisable(disable);
        twoPm.setDisable(disable);
        threePm.setDisable(disable);
    }


    @FXML
    public void addButton_Click(ActionEvent event) {

        // refreshOutput();
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
    public void age_Input(ActionEvent event) {

    }

    @FXML
    public void handleRemoveButtonAction() {
        String removedItem = "";
        try {
            if(removeItemFromFile(date)){
                removeItemFromFile(date);
            }else if(!removeItemFromFile(date)){
                priorityQueue.remove();
            }


            updateReservationLabels();
            updateLabelsFromFileData();
            //updateCurrentReservation();
            refreshOutput();
            JOptionPane.showMessageDialog(null, "Customer removed from Queue", "Item Removed", JOptionPane.INFORMATION_MESSAGE);
        } catch (NoSuchElementException e) {
            error_message("Priority Queue is empty.");
        }

        if (!priorityQueue.isEmpty()) {
            disableRadioButtons(true);
            refreshOutput();
            // If priority queue is empty, disable the radio buttons

        } else {
            refreshOutput();
            // If priority queue has items, enable the radio buttons
            disableRadioButtons(false);
        }
        }


    private boolean removeItemFromFile(String targetTime) {
        File inputFile = new File(filePath);
        File tempFile = new File("temp.txt");
        boolean itemRemovedFromFile = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    String itemTime = parts[3].trim();
                    if (itemTime.equals(targetTime)) {
                        // An item with the same time as the targetTime was found and removed from the file
                        itemRemovedFromFile = true;
                    } else {
                        writer.write(line + System.getProperty("line.separator"));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (inputFile.delete()) {
            if (!tempFile.renameTo(inputFile)) {
                System.err.println("Error renaming temp file.");
            }
        }

        return itemRemovedFromFile;
    }

    private void updateLabelsFromFileData() {
        // Update the current reservation label based on the selected time
        updateCurrentReservation();

        // Update all reservation labels
        updateReservationLabels();
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
        System.out.println(removeItemFromFile(date));
        refreshOutput();
        updateLabelsFromFileData();
        //handleLoadFileAction();

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

        // Create a set to store items already present in the PriorityQueue
        Set<String> existingItems = new HashSet<>(priorityQueue.getItems());

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

                    // Check if the item is new (not in the existingItems set)
                    if (!existingItems.contains(input)) {
                        int priority;

                        // Set priority based on time
                        switch (time) {
                            case "9:00 AM":
                                priority = 12;
                                break;
                            case "10:00 AM":
                                priority = 10;
                                break;
                            case "11:00 AM":
                                priority = 8;
                                break;
                            case "1:00 PM":
                                priority = 6;
                                break;
                            case "2:00 PM":
                                priority = 4;
                                break;
                            case "3:00 PM":
                                priority = 2;
                                break;
                            default:
                                priority = 5; // Default priority
                                break;
                        }

                        priorityQueue.add(input, priority);
                    }
                }
            }
            refreshOutput();
        } catch (IOException e) {
            error_message("Error reading the file: " + e.getMessage());
        }
    }




    void addData(String name, String lastName, String contactNumber, String date , String reason) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(name + ",");
            writer.write(lastName + ",");
            writer.write(contactNumber + ",");
            writer.write(date + ",");
            writer.write(reason + "\r\n");
        } catch (IOException ex) {
            ex.printStackTrace(); // Handle or log the exception properly
        }
    }

    @FXML
    public void updateCurrentReservation() {
        String selectedTime = date;

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            StringBuilder reservationText = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    String time = parts[3].trim();
                    if (time.equals(selectedTime)) {
                        // Display the reservation data in the label
                        reservationText.append(parts[0].trim()).append(" ")
                                .append(parts[1].trim()).append(", ")
                                .append(parts[4].trim()).append("\n");
                    }
                }
            }

            if (!reservationText.isEmpty()) {
                currentReservation.setText(reservationText.toString());
            } else {
                currentReservation.setText("No current reservations at this time.");
            }
        } catch (IOException e) {
            error_message("Error reading the file: " + e.getMessage());
        }
    }

    private void updateReservationLabels() {
        List<Label> reservationLabels = Arrays.asList(
                reservationOne, reservationTwo, reservationThree,
                reservationFour, reservationFive, reservationSix
        );

        for (int i = 0; i < reservationLabels.size(); i++) {
            reservationLabels.get(i).setText(getReservationsForTimeSlot(i));
        }
    }

    private String getReservationsForTimeSlot(int slotIndex) {
        String selectedTime = getTimeSlotByIndex(slotIndex);
        StringBuilder reservationText = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    String time = parts[3].trim();
                    if (time.equals(selectedTime)) {
                        // Display the reservation data in the label
                        reservationText.append(parts[0].trim()).append(" ")
                                .append(parts[1].trim()).append(", ")
                                .append(parts[4].trim()).append("\n");
                    }
                }
            }
        } catch (IOException e) {
            error_message("Error reading the file: " + e.getMessage());
        }

        if (reservationText.isEmpty()) {
            return "No reservations at this time";
        }

        return reservationText.toString();
    }

    private void removeItemsByTime(String time) {
        File inputFile = new File(filePath);
        File tempFile = new File("temp.txt");

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    String itemTime = parts[3].trim();
                    if (!itemTime.equals(time)) {
                        writer.write(line + System.getProperty("line.separator"));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (inputFile.delete()) {
            if (!tempFile.renameTo(inputFile)) {
                System.err.println("Error renaming temp file.");
            }
        }
    }



    private void refreshOutput(PriorityQueue originalQueue, String s) {
    }


    private String getTimeSlotByIndex(int slotIndex) {
        switch (slotIndex) {
            case 0: return "9:00 AM";
            case 1: return "10:00 AM";
            case 2: return "11:00 AM";
            case 3: return "1:00 PM";
            case 4: return "2:00 PM";
            case 5: return "3:00 PM";
            default: return "";
        }
    }


    @FXML
    void nineAm_Action(ActionEvent event) {
        if (!nineAm.isDisabled()) {
            refreshOutput();
        } else {
            JOptionPane.showMessageDialog(null, "Cannot change time while the queue is not empty.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @FXML
    void tenAm_Action(ActionEvent event) {
        if (!tenAm.isDisabled()) {
            refreshOutput();
        } else {
            JOptionPane.showMessageDialog(null, "Cannot change time while the queue is not empty.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    @FXML
    void elevenAm_Action(ActionEvent event) {

        if (!elevenAm.isDisabled()) {
            refreshOutput();
        } else {
            JOptionPane.showMessageDialog(null, "Cannot change time while the queue is not empty.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @FXML
    void onePM_Action(ActionEvent event) {
        if (!onePM.isDisabled()) {
            refreshOutput();
        } else {
            JOptionPane.showMessageDialog(null, "Cannot change time while the queue is not empty.", "Error", JOptionPane.ERROR_MESSAGE);
        }

    }

    @FXML
    void twoPm_Action(ActionEvent event) {
        if (!twoPm.isDisabled()) {
            refreshOutput();
        } else {
            JOptionPane.showMessageDialog(null, "Cannot change time while the queue is not empty.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @FXML
    void threePm_Action(ActionEvent event) {
        if (!threePm.isDisabled()) {
            refreshOutput();
        } else {
            JOptionPane.showMessageDialog(null, "Cannot change time while the queue is not empty.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @FXML
    void backButton_Click(ActionEvent event) throws IOException {
        Main m = new Main();
        m.changeScene("hello-view.fxml");
    }

    @FXML
    void refreshBtn_Click(ActionEvent event) {
    refreshOutput();
    updateLabelsFromFileData();
    updateReservationLabels();

    }

    private void saveInputToFile(String input) {
        String fileName = "queue_items.txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            writer.write(input);
            writer.newLine();
        } catch (IOException e) {
            // Handle the exception, e.g., display an error message or log the error
            e.printStackTrace();
        }
    }




//


}
