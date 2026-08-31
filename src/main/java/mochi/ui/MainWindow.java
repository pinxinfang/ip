package mochi.ui;

import java.nio.file.Path;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import mochi.Mochi;

/**
 * Controls Mochi's main chat window.
 */
public class MainWindow extends BorderPane {
    private final VBox dialogContainer = new VBox();
    private final TextField userInput = new TextField();
    private final Mochi mochi = Mochi.forGui(Path.of("data", "mochi.txt"));

    /**
     * Creates and wires the chat controls.
     */
    public MainWindow() {
        ScrollPane scrollPane = new ScrollPane(dialogContainer);
        scrollPane.setFitToWidth(true);
        dialogContainer.setFillWidth(true);
        dialogContainer.heightProperty().addListener(observable -> scrollPane.setVvalue(1.0));

        Button sendButton = new Button("Send");
        sendButton.setOnAction(event -> handleUserInput());
        userInput.setOnAction(event -> handleUserInput());
        userInput.setPromptText("Enter a command...");

        HBox inputBar = new HBox(8, userInput, sendButton);
        inputBar.setPadding(new Insets(10));
        HBox.setHgrow(userInput, Priority.ALWAYS);
        setCenter(scrollPane);
        setBottom(inputBar);
        dialogContainer.getChildren().add(DialogBox.mochi("Hello! I'm Mochi. What can I do for you?"));
    }

    private void handleUserInput() {
        String input = userInput.getText().trim();
        if (input.isEmpty()) {
            return;
        }
        dialogContainer.getChildren().addAll(
                DialogBox.user(input),
                DialogBox.mochi(mochi.getResponse(input)));
        userInput.clear();
    }
}
