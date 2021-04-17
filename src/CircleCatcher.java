import javafx.scene.control.*;

import javafx.scene.text.*;
import javafx.scene.media.*;
import java.io.File;
import java.sql.Time;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javax.swing.JOptionPane;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.application.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.*;
import java.util.Random;
import javafx.scene.media.MediaPlayer;
import java.io.File;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.media.MediaPlayer;
import javafx.scene.control.Button;
import java.io.File;

public class CircleCatcher extends Application {
	public static void main(String args[]) {
		launch(args);
	}

	private int circlesClicked = 0;
	private RandomCircle[] circleArray;
	private int windowHeight = 400;
	private int windowWidth = 600;

	private int circleCount;
	private int maxSpeed;
	private String fileName;

	@Override
	public void start(Stage stage) throws Exception {

			// Start up stage that asks for user input on difficulty.
			Stage askStage = new Stage();

			// Gets audio file to play menu intro music for difficulty selection.
			File soundFile = new File("intro.mp3");
			Media tempMed = new Media(soundFile.toURI().toString());
			MediaPlayer player = new MediaPlayer(tempMed);
			player.setVolume(.3);

			player.setCycleCount(MediaPlayer.INDEFINITE);

			player.setAutoPlay(true);

			GridPane gridMaster = new GridPane();
			gridMaster.setPrefSize(400, 300);

			RadioButton easyButton = new RadioButton("Easy");
			RadioButton averageButton = new RadioButton("Average");
			RadioButton hardButton = new RadioButton("Hard");
			RadioButton insaneButton = new RadioButton("Insanity\n!!!Warning!!!");
			Button startButton = new Button("Enter Game");
			Button closeButton = new Button("Exit Game");

			startButton.setOnAction(event -> {
				stage.show();
				askStage.close();
				player.stop();

				if (easyButton.isSelected()) {
					circleCount = 5;
					maxSpeed = 2500;
					//fileName = "questioningModerato.mp3";
				} else if (averageButton.isSelected()) {
					circleCount = 8;
					maxSpeed = 2000;
					//fileName = "examinationAllegro.mp3";
				} else if (hardButton.isSelected()) {
					circleCount = 13;
					maxSpeed = 1500;
					//fileName = "questioningAllegro.mp3";
				} else {
					circleCount = 5;
					maxSpeed = 0;
					//fileName = "speedySpeedBoy.wav";
				}

				setUpGame(stage, circleCount, maxSpeed);
			});

			ToggleGroup rg = new ToggleGroup();

			averageButton.setSelected(true);

			easyButton.setToggleGroup(rg);
			averageButton.setToggleGroup(rg);
			hardButton.setToggleGroup(rg);
			insaneButton.setToggleGroup(rg);

			VBox radioButtons = new VBox(easyButton, averageButton, hardButton, insaneButton, startButton);

			gridMaster.add(radioButtons, 0, 1);
//		gridMaster.add(tempButton, 0, 2);

			Scene scenePrep = new Scene(gridMaster);

			scenePrep.getStylesheets().add("file:intro.css");

			askStage.setScene(scenePrep);
			askStage.setTitle("Difficulty Select");
			askStage.show();
			// End of start up stage.

	}

	// Sets up the circleArray, the number of circles, speed of circles, and music
	// selection.
	// Also sets the main stage of the game.
	public void setUpGame(Stage stage, int circleCount, int maxSpeed) {

		//File gameSoundFile = new File(fileName);
		// Media mediaObject = new Media(gameSoundFile.toURI().toString());
		// MediaPlayer gamePlayer = new MediaPlayer(mediaObject);
		// gamePlayer.setVolume(.2);

		// gamePlayer.setCycleCount(MediaPlayer.INDEFINITE);

		// gamePlayer.setAutoPlay(true);

		BorderPane bp = new BorderPane();

		bp.setPrefHeight(windowHeight);
		bp.setPrefWidth(windowWidth);

		circleArray = new RandomCircle[circleCount];

		for (int i = 0; i < circleCount; i++) {

//		circleArray[i].setCenterX(0);
//		circleArray[i].setCenterY(0);

			circleArray[i] = new RandomCircle(windowHeight, windowWidth);
			Random randy = new Random();
			TranslateTransition tt = new TranslateTransition(new Duration(randy.nextInt(500) + maxSpeed),
					circleArray[i]);

			// Gets a random starting point for the circle object.
			tt.setFromX(getCordinate(true, i));
			tt.setFromY(getCordinate(false, i));

			// Gets a random ending point for object.
			tt.setToX(getCordinate(true, i));
			tt.setToY(getCordinate(false, i));

			int indexOfCircle = i;

			// Checks to see if the clicked circle is in the center.
			// If so, it stays there.
			// Else if finds a new random point to move towards.
			tt.setOnFinished(event -> {
				if (tt.getToX() == (windowWidth / 2) && tt.getToY() == (windowHeight / 2)) {
					tt.stop();
				} else {
					tt.setFromX(tt.getToX());
					tt.setFromY(tt.getToY());
					tt.setToX(getCordinate(true, indexOfCircle));
					tt.setToY(getCordinate(false, indexOfCircle));
					tt.setCycleCount(1);
					tt.play();
				}
			});

			circleArray[i].setOnMouseClicked(event -> {
				if (!(tt.getToX() == (windowWidth / 2) && tt.getToY() == (windowHeight / 2))) {
					circlesClicked++;
				}
				tt.stop();
				tt.setAutoReverse(false);
				tt.setInterpolator(Interpolator.EASE_BOTH);
				tt.setFromX(event.getSceneX());
				tt.setFromY(event.getSceneY());
				tt.setToX((windowWidth / 2));
				tt.setToY(windowHeight / 2);
				tt.setDuration(Duration.millis(550));
				tt.setCycleCount(1);
				tt.play();

//			System.out.println(circlesClicked);

//			tt.getNode().getId();
//			Need a way to access the data fields of the clicked shape.

				if (circlesClicked == circleCount) {
					// gamePlayer.stop();
					Text winnerText = new Text(windowWidth / 2 - 55, windowHeight / 2 - 20, "You've won!");
					winnerText.setFont(new Font("TimeNewRoman", 30));
					winnerText.setStroke(Color.CORNFLOWERBLUE);
					winnerText.setFill(Color.CORNFLOWERBLUE);
					bp.getChildren().add(winnerText);
				}

			});
			tt.play();
		}

		bp.getChildren().addAll(circleArray);

		MenuBar menuBar = new MenuBar();

		Menu controlMenu = new Menu("Control");

		MenuItem exit = new MenuItem("Exit");

		exit.setOnAction(event -> {
			System.exit(0);
		});

		menuBar.getMenus().addAll(controlMenu);
		controlMenu.getItems().add(exit);

		bp.setTop(menuBar);

		Scene primaryScene = new Scene(bp);
		stage.setScene(primaryScene);
		stage.setTitle("Circle Catcher");
	}

	// Boolean is true if x coordinate, false if y coordinate.
	public int getCordinate(boolean xy, int index) {
		if (xy == true) {
			return (int) (Math.random()
					* ((windowWidth - circleArray[index].getRad()) - circleArray[index].getRad() * 2)
					+ circleArray[index].getRad());
		} else {
			return (int) (Math.random()
					* ((windowHeight - circleArray[index].getRad()) - circleArray[index].getRad() * 2)
					+ circleArray[index].getRad());
		}
	}
}
