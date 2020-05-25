////////////////////////////////
// CREATED BY JOSEPH KAJUCH
// NOT FOR REUSE
////////////////////////////////
package application;

import javafx.animation.AnimationTimer;
import java.util.ArrayList;
import java.util.Random;
import javafx.application.Application;
import javafx.event.Event;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import java.awt.event.KeyEvent;
import java.io.File;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

/*
 * Basic version of Snake programmed from scratch by Joseph Kajuch.
 * 
 * JVM ARGUMENTS: 
 * --module-path "YOUR JAVAFX 11.0.2 PATH" 
 * --add-modules javafx.controls,javafx.fxml,javafx.media
 * 
 */
public class Main extends Application {

	private static final String APP_TITLE = "SnakeFX";

	public Random randGen = new Random();
	private double APP_WIDTH = 1250;
	private double APP_HEIGHT = 800;
	final int START_XPOS = 765;// APP_WIDTH / 2 - 35; // -35 makes it on
								// grid!!!!
	final int START_YPOS = 425;// APP_HEIGHT / 2 - 25;
	public static boolean gameOver = false; // set to true if collision
	public static ArrayList<Snake> snake = new ArrayList<>();
	public static int index = 0; // fruit is obtained
	private int highscore = 0;
	public static Fruit fruit;
	public AnimationTimer timer;
	public static int fps = 1; // increments every cycle
	public static int fpsMod = 45; // handles "thread" speed in start() using
									// mod
									// operator
	private int state; // for screen changes

	// STARTS GAME IN UNPLAYED STATE (NEW GAME)
	private void initiate() {
		APP_WIDTH = 1250;
		APP_HEIGHT = 800;
		gameOver = false;
		snake = new ArrayList<>();
		snake.add(new Snake());
		snake.add(new Snake());
		snake.add(new Snake());

		fruit = new Fruit();
		fruit.color = fruit.randomColor();
		int xPosIncrement = START_XPOS;

		for (int i = 0; i < snake.size(); i++) {
			snake.set(i, new Snake());
			snake.get(i).setPositionX(xPosIncrement);
			snake.get(i).setPositionY(START_YPOS);

			xPosIncrement += 50;

		}
		fpsMod = 45;
	}

	// STATE 0 SCREEN (INTRO SCREEN)
	private void paints0(GraphicsContext graphic) {
		graphic.setFill(Color.BLACK);
		graphic.fillRect(0, 0, APP_WIDTH, APP_HEIGHT);

		// "SNAKE" shadow
		graphic.setTextAlign(TextAlignment.CENTER);
		graphic.setFill(Color.RED);
		graphic.setFont(Font.font("arial", FontWeight.NORMAL,
				FontPosture.REGULAR, 100));
		graphic.fillText("Snake", (APP_WIDTH / 2) + 4, (APP_HEIGHT / 4) + 4);

		// "SNAKE"
		graphic.setTextAlign(TextAlignment.CENTER);
		graphic.setFill(Color.WHITE);
		graphic.setFont(Font.font("arial", FontWeight.NORMAL,
				FontPosture.REGULAR, 100));
		graphic.fillText("Snake", APP_WIDTH / 2, APP_HEIGHT / 4);

		// "Enter"
		graphic.setTextAlign(TextAlignment.CENTER);
		graphic.setFill(Color.WHITE);
		graphic.setFont(
				Font.font("arial", FontWeight.NORMAL, FontPosture.REGULAR, 25));
		graphic.fillText("Press Enter To Start Game", APP_WIDTH / 2,
				APP_HEIGHT / 2);

		// NAME
		graphic.setTextAlign(TextAlignment.CENTER);
		graphic.setFill(Color.WHITE);
		graphic.setFont(
				Font.font("arial", FontWeight.NORMAL, FontPosture.REGULAR, 25));
		graphic.fillText("By: Joseph Kajuch", APP_WIDTH / 2, APP_HEIGHT / 3);
	}

	private void paints1(GraphicsContext graphic) {
		// background
		graphic.setTextAlign(TextAlignment.CENTER);
		if (gameOver) {
			// draw box
			graphic.setFill(Color.DARKGRAY);
			graphic.fillRect(APP_WIDTH / 2 - 200,
					APP_HEIGHT / 2 - 70 + (70 / 4), 400, 70);
			graphic.setStroke(Color.DARKGRAY);

			// Write message
			graphic.setFont(new Font(60));
			graphic.setFill(Color.RED);
			graphic.fillText("GAME OVER", APP_WIDTH / 2, APP_HEIGHT / 2);

			highscore = snake.size() - 3;
			losePlayer.play();
			beepPlayer.stop();

			return;
		} else {
			losePlayer.stop();
		}

		graphic.setFill(Color.BLACK);
		graphic.fillRect(0, 0, APP_WIDTH, APP_HEIGHT);

		// frame
		graphic.setFill(Color.WHITE);
		graphic.fillRoundRect(330, 90, 620, 620, 100, 100);

		// board
		graphic.setFill(Color.ROSYBROWN);
		graphic.fillRoundRect(340, 100, 600, 600, 100, 100);

		graphic.setFill(fruit.color);
		graphic.setFont(new Font(50));
		graphic.fillText("Snake", (APP_WIDTH / 8) + 2, (APP_HEIGHT / 8) + 2);

		// "SNAKE"
		graphic.setFill(Color.WHITE);
		graphic.setFont(new Font(50));
		graphic.fillText("Snake", (APP_WIDTH / 8), (APP_HEIGHT / 8));

		graphic.setFill(Color.WHITE);
		// SCORE
		graphic.setFont(new Font(35));
		graphic.fillText("Score: " + (snake.size() - 3), (APP_WIDTH / 8),
				(APP_HEIGHT / 10) * 3);
		//
		graphic.setFill(Color.WHITE);
		graphic.setFont(new Font(20));
		graphic.fillText("High Score: " + highscore, (APP_WIDTH / 8),
				(APP_HEIGHT / 10) * 4);

		graphic.setFill(Color.WHITE);
		// SPACE
		graphic.setFont(new Font(20));
		graphic.fillText("Hit Space to Restart", (APP_WIDTH / 8),
				(APP_HEIGHT / 10) * 2);

		// Created By
		graphic.setFill(Color.WHITE);
		graphic.setFont(new Font(20));
		graphic.fillText("Created By Joseph Kajuch", (APP_WIDTH / 8),
				(APP_HEIGHT / 10) * 8);

		// SNAKES AND FRUITS

		graphic.setFill(Color.BLACK);
		graphic.fillOval(fruit.getPositionX() - 2, fruit.getPositionY() - 2, 45,
				45);

		graphic.setFill(fruit.color);
		graphic.fillOval(fruit.getPositionX(), fruit.getPositionY(), 41, 41);

		// FIRST SNAKE
		graphic.setFill(Color.BLACK);
		graphic.fillOval(snake.get(0).getPositionX() - 2,
				snake.get(0).getPositionY() - 2, 45, 45);
		graphic.setFill(Color.RED);
		graphic.fillOval(snake.get(0).getPositionX(),
				snake.get(0).getPositionY(), 41, 41);

		// DRAW ALL OTHER SNAKES
		for (int i = 1; i < snake.size(); i++) {
			graphic.setFill(Color.BLACK);
			graphic.fillOval(snake.get(i).getPositionX() - 2,
					snake.get(i).getPositionY() - 2, 45, 45);
			graphic.setFill(Color.GREEN);
			graphic.fillOval(snake.get(i).getPositionX(),
					snake.get(i).getPositionY(), 40, 40);
			graphic.fillOval(snake.get(i).getPositionX(),
					snake.get(i).getPositionY(), 41, 41);
		}
	}

	// ALL HANDLE SOUNDS PLAYED AS GAME PROGRESSES
	
	private MediaPlayer beepPlayer;
	public static MediaPlayer fruitPlayer;
	private Media fruitget;
	private Media beep;
	private Media lose;
	private MediaPlayer losePlayer;

	@Override
	public void start(Stage primaryStage) throws Exception {

		/***************** SCENE AND UI *****************/

		BorderPane root = new BorderPane();
		Scene theScene = new Scene(root);
		primaryStage.setScene(theScene);
		primaryStage.setWidth(APP_WIDTH);
		primaryStage.setHeight(APP_HEIGHT);
		primaryStage.setResizable(true);

		// Creates a canvas that can draw shapes and text
		Canvas canvas = new Canvas(APP_WIDTH, APP_HEIGHT);
		GraphicsContext gc = canvas.getGraphicsContext2D();
		root.getChildren().add(canvas);

		fruitget = new Media(new File("fruitget.wav").toURI().toString());
		fruitPlayer = new MediaPlayer(fruitget);
		beep = new Media(new File("beep.wav").toURI().toString());
		beepPlayer = new MediaPlayer(beep);
		lose = new Media(new File("lose.wav").toURI().toString());
		losePlayer = new MediaPlayer(lose);

		initiate();
		state = 0;

		new AnimationTimer() {
			public void handle(long currentNanoTime) {
				APP_WIDTH = primaryStage.getWidth();
				APP_HEIGHT = primaryStage.getHeight();
				canvas.prefWidth(APP_WIDTH);
				canvas.prefHeight(APP_HEIGHT);
				if (state == 0) {
					theScene.setOnKeyPressed(e1 -> {
						if (e1.getCode().getCode() == KeyEvent.VK_ENTER)
							state = 1;
					});
					paints0(gc);
				} else if (state == 1) {
					theScene.setOnKeyPressed(e1 -> {
						handleS1Input(e1);
					});
					if (fps == fpsMod + 1) {
						fps = 1;
					} else {
						fps++;
					}

					if (fps % fpsMod == 0) {
						snake.get(0).move();
						beepPlayer.play();
						beepPlayer.stop();
					}
					paints1(gc);
				}
			}
		}.start();

		primaryStage.setTitle(APP_TITLE);
		primaryStage.show();
	}

	// HANDLES ALL GAME INPUT
	private void handleS1Input(Event e1) {
		javafx.scene.input.KeyEvent e = (javafx.scene.input.KeyEvent) e1;
		if (!gameOver) {
			if (snake.get(0).getUpMove() == true
					&& e.getCode().getCode() == KeyEvent.VK_DOWN) {
				// skip
			} else if (snake.get(0).getDownMove() == true
					&& e.getCode().getCode() == KeyEvent.VK_UP) {
				// skip
			} else if (snake.get(0).getLeftMove() == true
					&& e.getCode().getCode() == KeyEvent.VK_RIGHT) {
				// skip
			} else if (snake.get(0).getRightMove() == true
					&& e.getCode().getCode() == KeyEvent.VK_LEFT) {
				// skip
			} else if (e.getCode().getCode() == KeyEvent.VK_UP) {
				snake.get(0).setUpMove(true);
				snake.get(0).setDownMove(false);
				snake.get(0).setLeftMove(false);
				snake.get(0).setRightMove(false);
			} else if (e.getCode().getCode() == KeyEvent.VK_DOWN) {
				snake.get(0).setUpMove(false);
				snake.get(0).setDownMove(true);
				snake.get(0).setLeftMove(false);
				snake.get(0).setRightMove(false);
			} else if (e.getCode().getCode() == KeyEvent.VK_LEFT) {
				snake.get(0).setLeftMove(false);
				snake.get(0).setUpMove(false);
				snake.get(0).setDownMove(false);
				snake.get(0).setLeftMove(true);
				snake.get(0).setRightMove(false);
			} else if (e.getCode().getCode() == KeyEvent.VK_RIGHT) {
				snake.get(0).setUpMove(false);
				snake.get(0).setDownMove(false);
				snake.get(0).setLeftMove(false);
				snake.get(0).setRightMove(true);
			}

			// Cheat code sorts of inputs!
			if (e.getCode().getCode() == KeyEvent.VK_Q) {
				snake.add(new Snake());
			}
			if (e.getCode().getCode() == KeyEvent.VK_A) {
				if (fpsMod - 2 > 0)
					fpsMod -= 2;
			}
			if (e.getCode().getCode() == KeyEvent.VK_S) {
				fpsMod += 2;
			}
			if (e.getCode().getCode() == KeyEvent.VK_R) {
				snake.remove(snake.get(snake.size() - 1));
			}
		}
		if (e.getCode().getCode() == KeyEvent.VK_SPACE) {
			initiate();
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}
}