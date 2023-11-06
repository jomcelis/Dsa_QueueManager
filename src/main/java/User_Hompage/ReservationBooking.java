package User_Hompage;

import com.example.dsa_final.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class ReservationBooking {

    @FXML
    public Button BookBtn;

    @FXML
    public TableColumn<?, ?> Date_Tbl;

    @FXML
    public ChoiceBox<?> DropDownBtn;

    @FXML
    public Button ElevenAmBtn;

    @FXML
    public TableColumn<?, ?> LastName_Tbl;

    @FXML
    public TableColumn<?, ?> Name_Tbl;

    @FXML
    public Button NineAmBTN;

    @FXML
    public Button OnePmBtn;

    @FXML
    public Button TenAmBtn;

    @FXML
    public Button ThreePmBtn;

    @FXML
    public TableColumn<?, ?> Time_Tbl;

    @FXML
    public Button TwoPmBtn;

    @FXML
    public DatePicker User_DatePicker;

    @FXML
    public TableColumn<?, ?> reservationID_Tbl;

    @FXML
    public TableView<?> reservationTable;

    @FXML
    private Button backBtn;

    @FXML
    void BookBtn_Click(ActionEvent event) {

    }

    @FXML
    void DropDownBtn_Click(MouseEvent event) {

    }

    @FXML
    void backBtn_Click(ActionEvent event) throws IOException {
        Main m = new Main();
        m.changeScene("UserHomepage.fxml");
    }

    @FXML
    void ElevenAmBtn_Click(ActionEvent event) throws IOException {
        Parent root;
        String elevenAM = "11:00 AM";
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/dsa_final/UserHomepage.fxml")); // Update the path to match the actual location
        root = loader.load();

        MainQueue mainQueue = loader.getController();
        mainQueue.outputArea.setText("Wawa");
    }


    @FXML
    void NineAmBTN_Click(ActionEvent event) {
        String nineAM = "9:00 AM";
    }

    @FXML
    void OnePmBtn_Click(ActionEvent event) {
        String onePM = "1:00 PM";
    }

    @FXML
    void TenAmBtn_Click(ActionEvent event) {
        String tenAM = "10:00 AM";
    }

    @FXML
    void ThreePmBtn_Click(ActionEvent event) {
        String threePM = "3:00 PM";
    }

    @FXML
    void TwoPmBtn_Click(ActionEvent event) {
        String twoPM = "2:00 PM";

    }

    @FXML
    void User_DatePicker_Action(ActionEvent event) {

    }

}
