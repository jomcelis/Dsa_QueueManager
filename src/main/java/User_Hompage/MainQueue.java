    package User_Hompage;
    
    import com.example.dsa_final.Main;
    import javafx.collections.FXCollections;
    import javafx.event.ActionEvent;
    import javafx.fxml.FXML;
    import javafx.scene.control.*;
    
    import javax.swing.*;
    import java.io.*;
    import java.time.LocalDate;
    import java.util.*;
    
    public class MainQueue {
        private final PriorityQueue<String> priorityQueue = new PriorityQueue<>();
        private static final String filePath = "C:\\Users\\Lenovo\\Desktop\\BST\\Dsa_final\\src\\Customers\\customer_data.txt";
        private String date;
        @FXML
        private Button print;
    
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
        private Label emergencyLabel;
        @FXML
        private Button emergency_Click;
        @FXML
        Label currentTime;
        @FXML
        private Label warning;
        private boolean CheckEmergency;
    
        @FXML
        private void updateCurrentTime() {
            currentTime.setText(date); // Set the text of the "currentTime" label to the selected time
        }
    
    
    
        private final String[] choices ={"General check up","Tooth Extraction","Teeth whitening","X-Ray", "Consultation", "Braces", "Severe gum bleeding", "PWD", "Senior Citizen"};
    
        private PriorityQueue originalQueue;
        @FXML
        private void initialize() {
            ToggleGroup dateToggleGroup = new ToggleGroup();
            FileCreation();
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
        public void handleAddButtonAction() throws IOException {
            // Extract input data from your UI components
            String first = firstName_Input.getText();
            String last = lastName_Input.getText();
            String contact = number_Input.getText();
            String ages = age.getText();
            String reason = choiceBox.getValue();
    
            // Check if any of the input fields are empty
            if (first.isEmpty() || !isValidInput(first)) {
                error_message("Invalid input first name");
            } else if (last.isEmpty() || !isValidInput(last)) {
                error_message("Invalid input last name");
            } else if (contact.isEmpty() || !isValidContactNumber(contact)) {
                error_message("Invalid input contact number");
            } else if (reason == null) {
                error_message("Invalid reason");
            } else if (date == null) {
                error_message("Please choose time");
            } else if (age == null || !isValidInputAge(ages)) {
                error_message("Invalid input age");
            } else {
                try {
                    long contactNumber = Long.parseLong(contact);
                    int priority;
    
                    // Set priority based on the reason
                    switch (reason) {
                        case "General check up":
                        case "Teeth whitening":
                        case "X-Ray":
                        case "Consultation":
                        case "Braces":
                            priority = 1;
                            break;
                        case "PWD":
                        case "Senior citizen":
                            priority = 2;
                            break;
                        case "Severe gum bleeding":
                            priority = 3;
                            break;
                        default:
                            priority = 1; // Default priority
                            break;
                    }
    
                    // Create a formatted input string
                    String input = first + " , " + last + " , " + contact + " , " + date + " , " + ages + " , " + reason + " , "+ priority;
    
                    // Add the item to the priority queue with the determined priority
                    addData(first, last, contact, date, reason);
                    //addData2(first, last, contact, date, reason);
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
                    warning.setText("You can't change time if queue isn't empty.");
                    // If priority queue is not empty, disable the radio buttons
                } else {

                    refreshOutput();
                    // If priority queue has items, enable the radio buttons
                    disableRadioButtons(false);
                }
            }
            emergency_Click();
            updateEmergencyLabel();
            //printTemporaryQueue();
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
            updateEmergencyLabel();
            String removedItem = "";
            try {
                PrintWriter writer = new PrintWriter("C:\\Users\\Lenovo\\Desktop\\BST\\Dsa_final\\src\\Customers\\TempQueue.txt");
                writer.print("");
                writer.close();

                if(CheckEmergency) {
                        priorityQueue.remove();
                        CheckEmergency=false;
                        }else{
                            if (removeItemFromFile(date)) {
                                removeItemFromFile(date);
                            } else if (!removeItemFromFile(date)) {
                                priorityQueue.remove();
                            }
                        }


                updateReservationLabels();
                updateLabelsFromFileData();
                updateEmergencyLabel();
                refreshOutput();
                JOptionPane.showMessageDialog(null, "Client removed from Queue", "Dequeue", JOptionPane.INFORMATION_MESSAGE);
            } catch (NoSuchElementException | FileNotFoundException e) {
                error_message("Priority Queue is empty.");
                e.printStackTrace();
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
        private void emergency_Click() {
            try {
                if (!priorityQueue.isEmpty()) {
                    String emergencyItem = priorityQueue.peek();
                    String[] parts = emergencyItem.split(",");
                    if (parts.length >= 2) {
                        String firstName = parts[0].trim();
                        String lastName = parts[1].trim();
                        String priority = parts[6].trim(); // Assuming the priority is at index 4 in the 'parts' array
                        String reason = parts[5].trim();
                        if (priority.equals("3")) {
                            CheckEmergency = true;
                            emergencyLabel.setText(firstName + " " + lastName + " " + reason);

                            // You might want to remove the item from the priority queue if it is sent to the emergency label
                            // priorityQueue.remove();
                        } else {
                            CheckEmergency = false;
                            emergencyLabel.setText("No emergencies queued");

                        }
                    } else {
                        emergencyLabel.setText(emergencyItem);
                    }
                } else {
                    error_message("Priority Queue is empty.");
                }
            } catch (NoSuchElementException e) {
                error_message("Priority Queue is empty.");
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
            StringBuilder filteredOutput = new StringBuilder();
            for (String item : priorityQueue.getItems()) {
                String[] parts = item.split(",");
                if (parts.length >= 6) {
                    int priority = Integer.parseInt(parts[6].trim());
                    if (priority == 1 || priority == 2) {
                        filteredOutput.append(item).append("\n");
                    }
                }
            }
            outputArea.setText(filteredOutput.toString());
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
    
    
    
    
        void addData(String name, String lastName, String contactNumber, String date, String reason) {
            String filePath = "C:\\Users\\Lenovo\\Desktop\\BST\\Dsa_final\\src\\Customers\\PriorityQueue.txt";
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
                writer.write(name + ",");
                writer.write(lastName + ",");
                writer.write(contactNumber + ",");
                writer.write(date + ",");
                writer.write(reason + System.getProperty("line.separator"));
            } catch (IOException ex) {
                ex.printStackTrace(); // Handle or log the exception properly
            }
        }

        void addData2(String name, String lastName, String contactNumber, String date, String reason) {
            String filePath2 = "C:\\Users\\Lenovo\\Desktop\\BST\\Dsa_final\\src\\Customers\\TempQueue.txt";
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath2, true))) {
                writer.write(name + ",");
                writer.write(lastName + ",");
                writer.write(contactNumber + ",");
                writer.write(date + ",");
                writer.write(reason + System.getProperty("line.separator"));
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
        private void updateEmergencyLabel() {
            try {
                if (!priorityQueue.isEmpty()) {
                    String emergencyItem = priorityQueue.peek();
                    String[] parts = emergencyItem.split(",");
                    if (parts.length >= 2) {
                        String firstName = parts[0].trim();
                        String lastName = parts[1].trim();
                        String priority = parts[6].trim(); // Assuming the priority is at index 6 in the 'parts' array
                        String reason = parts[5].trim();
                        if (priority.equals("3")) {
                            emergencyLabel.setText(firstName + " " + lastName + " " + reason);

                            // You might want to remove the item from the priority queue if it is sent to the emergency label
                            // priorityQueue.remove();
                        } else {
                            emergencyLabel.setText("No emergencies queued");
                        }
                    } else {
                        emergencyLabel.setText(emergencyItem);
                    }
                } else {
                    emergencyLabel.setText("No emergencies queued");
                }
            } catch (NoSuchElementException e) {
                error_message("Priority Queue is empty.");
            }
        }
        @FXML
        private void print_Click() {
            printToFile();
        }
    
        private void printToFile() {
            String reservationFilePath = "C:\\Users\\Lenovo\\Desktop\\BST\\Dsa_final\\src\\Customers\\Reservations.txt";
            String priorityQueueFilePath = "C:\\Users\\Lenovo\\Desktop\\BST\\Dsa_final\\src\\Customers\\PriorityQueue.txt";
            String outputFilePath = "C:\\Users\\Lenovo\\Desktop\\BST\\Dsa_final\\src\\Customers\\OutputFile.txt";

    
            try {
                File reservationFile = new File(reservationFilePath);
                File priorityQueueFile = new File(priorityQueueFilePath);

                if (!reservationFile.exists()) {
                    reservationFile.createNewFile();
                }
                if (!priorityQueueFile.exists()) {
                    priorityQueueFile.createNewFile();
                }

                BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath));
                writer.write("Reservations:\n");
                writeDataFromFile(reservationFilePath, writer);
                writer.write("\nPriority Queue:\n");
                writeDataFromFile(priorityQueueFilePath, writer);
                writer.write("\nDate today: " + LocalDate.now() + "\n");

                writer.close();
            } catch (IOException ex) {
                ex.printStackTrace(); // Handle or log the exception properly
            }
        }
/*
        private void printTemporaryQueue() throws IOException {
            String temporaryQueueFilePath = "C:\\Users\\Lenovo\\Desktop\\BST\\Dsa_final\\src\\Customers\\TempQueue.txt";
            File tempQueueFile = new File(temporaryQueueFilePath);
            if(!tempQueueFile.exists()){
                tempQueueFile.createNewFile();
            }
            if(!priorityQueue.isEmpty()){
                BufferedWriter writer2 = new BufferedWriter(new FileWriter(tempQueueFile));
                writeDataFromFile(temporaryQueueFilePath,writer2);
            }else{
                tempQueueFile.delete();
            }
        }
        */

    
        private void writeDataFromFile(String filePath, BufferedWriter writer) {
            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    writer.write(line);
                    writer.newLine();
                }
            } catch (IOException ex) {
                ex.printStackTrace(); // Handle or log the exception properly
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



        private String getTimeSlotByIndex(int slotIndex) {
            return switch (slotIndex) {
                case 0 -> "9:00 AM";
                case 1 -> "10:00 AM";
                case 2 -> "11:00 AM";
                case 3 -> "1:00 PM";
                case 4 -> "2:00 PM";
                case 5 -> "3:00 PM";
                default -> "";
            };
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
        updateEmergencyLabel();
    
        }

        private void saveInputToFile(String input) throws IOException {
            String fileName = "C:\\Users\\Lenovo\\Desktop\\BST\\Dsa_final\\src\\Customers\\TempQueue.txt";

            File file = new File(fileName);

            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fileWriter = new FileWriter(fileName, false);
            BufferedWriter writer = new BufferedWriter(fileWriter);

            try {
                if (!priorityQueue.isEmpty()) {
                    writer.write(input);
                    writer.newLine();
                    writer.flush();
                }
            } finally {
                writer.close();
                fileWriter.close();
            }
        }


        public void FileCreation(){
                String filePath = "C:\\Users\\Lenovo\\Desktop\\BST\\Dsa_final\\src\\Customers\\customer_data.txt";
                File file = new File(filePath);

                try {
                    if (file.createNewFile()) {
                        System.out.println("File created: " + file.getName());
                    } else {
                        System.out.println("File already exists.");
                    }
                } catch (IOException e) {
                    System.out.println("An error occurred while creating the file.");
                    e.printStackTrace();
                }
            }


        }
