package mochi.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/**
 * Displays one user or Mochi message in the chat window.
 */
public class DialogBox extends HBox {
    private DialogBox(String message, boolean isMochi) {
        Label text = new Label(message);
        text.setWrapText(true);
        text.setMaxWidth(310);
        text.setPadding(new Insets(10));
        text.setStyle(isMochi
                ? "-fx-background-color: #f3e8ff; -fx-background-radius: 12;"
                : "-fx-background-color: #e0f2fe; -fx-background-radius: 12;");
        setPadding(new Insets(6, 10, 6, 10));
        setAlignment(isMochi ? Pos.TOP_LEFT : Pos.TOP_RIGHT);
        getChildren().add(text);
    }

    /**
     * Creates a right-aligned user message.
     *
     * @param message message text
     * @return user dialog box
     */
    public static DialogBox user(String message) {
        return new DialogBox(message, false);
    }

    /**
     * Creates a left-aligned Mochi response.
     *
     * @param message response text
     * @return Mochi dialog box
     */
    public static DialogBox mochi(String message) {
        return new DialogBox(message, true);
    }
}
