package com.example.limphodee;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

public class LesothoTriviaGame extends Application {
    private static final String[] questions = {
            "What is the capital of Lesotho?",
            "Which mountain range covers much of Lesotho?",
            "What is the traditional Basostho hat called?",
            "What is the currency of Lesotho?",
            "What is the national animal of Lesotho?",
            "What is the traditional attire worn by Basotho men called?",
            "Who is the current king of Lesotho?"
    };

    private static final String[][] options = {
            {"Maseru", "Morija", "Mohale's Hoek", "Quthing"},
            {"Andes", "Himalayas", "Drakensberg", "Alps"},
            {"Igloo", "Tukul", "Rondavel", "Yurt"},
            {"Rand", "Euro", "Loti", "Dollar"},
            {"Elephant", "Springbok", "Basotho Hat", "Eland"},
            {"Kufuor", "Agbada", "Mantsho", "Sesotho Blanket"},
            {"Letsie III", "Moshoeshoe II", "Metsi", "Moshoshoe I"}
    };

    private static final String[] correctAnswers = {"Maseru", "Drakensberg", "Rondavel", "Loti", "Eland", "Basotho Hat", "Letsie III"};

    private static final String[] imagePaths = {
            "/images/maseru.jpg",
            "/images/drakensberg.jpg",
            "/images/basotho_hat.jpg",
            "/images/lesotho_currency.jpg",
            "/images/eland.jpg",
            "/images/basotho_attire.jpg",
            "/images/letsie_iii.jpg"
    };

    private static final String[] videoPaths = {
            "/videos/Maseru.mp4",
            "/videos/highest mountain.mp4",
            "/videos/sesotho hat.mp4",
            "/videos/Lesotho Currency.mp4",
            "/videos/elaba.mp4",
            "/videos/Lesotho attire.mp4",
            "/videos/Letsie iii.mp4"
    };

    private int currentQuestionIndex = 0;
    private int score = 0;
    private MediaPlayer mediaPlayer;

    @Override
    public void start(Stage stage) {
        stage.setTitle("Lesotho Trivia Game");

        Label questionLabel = new Label();
        ImageView imageView = new ImageView();
        imageView.setFitWidth(300);
        imageView.setFitHeight(200);
        RadioButton[] optionButtons = new RadioButton[options[currentQuestionIndex].length];
        ToggleGroup toggleGroup = new ToggleGroup();

        for (int i = 0; i < options[currentQuestionIndex].length; i++) {
            optionButtons[i] = new RadioButton(options[currentQuestionIndex][i]);
            optionButtons[i].setToggleGroup(toggleGroup);
        }

        Button submitButton = new Button("Submit");
        submitButton.setOnAction(event -> {
            RadioButton selectedRadioButton = (RadioButton) toggleGroup.getSelectedToggle();
            if (selectedRadioButton != null) {
                String selectedAnswer = selectedRadioButton.getText();
                if (selectedAnswer.equals(correctAnswers[currentQuestionIndex])) {
                    score++;
                }
                currentQuestionIndex++;
                if (currentQuestionIndex < questions.length) {
                    displayNextQuestion(questionLabel, imageView, optionButtons, toggleGroup);
                } else {
                    displayScore();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("No answer selected");
                alert.setHeaderText(null);
                alert.setContentText("Please select an answer before submitting.");
                alert.showAndWait();
            }
        });

        VBox root = new VBox(10);
        root.getChildren().addAll(questionLabel, imageView, submitButton);
        for (RadioButton optionButton : optionButtons) {
            root.getChildren().add(optionButton);
        }

        MediaView mediaView = new MediaView();
        VBox.setVgrow(mediaView, Priority.ALWAYS);

        Button playVideoButton = new Button("Play Video");
        playVideoButton.setOnAction(event -> {
            if (mediaPlayer != null) {
                mediaPlayer.stop();
            }
            Media video = new Media(getClass().getResource(videoPaths[currentQuestionIndex]).toString());
            mediaPlayer = new MediaPlayer(video);
            mediaPlayer.setAutoPlay(true);
            mediaView.setMediaPlayer(mediaPlayer);
        });

        root.getChildren().addAll(mediaView, playVideoButton);

        Scene scene = new Scene(root, 600, 600);
        stage.setScene(scene);
        stage.show();

        displayNextQuestion(questionLabel, imageView, optionButtons, toggleGroup);
    }

    private void displayNextQuestion(Label questionLabel, ImageView imageView, RadioButton[] optionButtons, ToggleGroup toggleGroup) {
        if (currentQuestionIndex < questions.length) {
            questionLabel.setText(questions[currentQuestionIndex]);
            Image image = new Image(getClass().getResourceAsStream(imagePaths[currentQuestionIndex]));
            imageView.setImage(image);
            for (int i = 0; i < optionButtons.length; i++) {
                optionButtons[i].setText(options[currentQuestionIndex][i]);
                optionButtons[i].setSelected(false);
                optionButtons[i].setToggleGroup(toggleGroup);
            }
        } else {
            displayScore();
        }
    }

    private void displayScore() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Quiz completed");
        alert.setHeaderText(null);
        alert.setContentText("Your score: " + score + " out of " + questions.length);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
