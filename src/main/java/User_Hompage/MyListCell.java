package User_Hompage;

import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.Pane;

public class MyListCell extends ListCell<String> {
    private final Pane pane = new Pane();

    @Override
    protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);

        if (empty || item == null) {
            setGraphic(null);
        } else {
            // Customize the appearance of the rectangle as per your requirements
            pane.setStyle("-fx-background-color: lightblue; -fx-border-color: black; -fx-border-width: 1px;");
            pane.setPrefSize(200, 50); // Set the size of the rectangle

            // Set the text of the rectangle to the item from the priority queue
            Label label = new Label(item);

            pane.getChildren().setAll(label);
            setGraphic(pane);
        }
    }
}
