////////////////////////////////
// CREATED BY JOSEPH KAJUCH
// NOT FOR REUSE
////////////////////////////////
package application;

import java.util.Random;

import javafx.scene.paint.Color;

/*
 * Class to represent the fruits the snake eats to grow 
 */
public class Fruit {

	public Random randGen = new Random();
	private int positionX;
	private int positionY;
	public Color color;

	public Fruit() {
		positionX = 365 + (50 * randGen.nextInt(11)); // 365 is first space
		positionY = 125 + (50 * randGen.nextInt(11)); // 125 is first space

		for (int i = 0; i < Main.snake.size(); i++) {
			if (positionX == Main.snake.get(i).getPositionX()
					&& positionY == Main.snake.get(i).getPositionY()) {
				positionX = 365 + (50 * randGen.nextInt(11));
				positionY = 125 + (50 * randGen.nextInt(11)); 
			}
		}
	}

	public boolean collisionWithFruit(int xChange, int yChange) {
		// See if first snake hits itself, can use this to end the game when hit
		// yourself
		for (int i = 0; i < Main.snake.size(); i++) {
			if ((Main.snake.get(0).getPositionX() + xChange == getPositionX())
					&& (Main.snake.get(0).getPositionY()
							+ yChange == getPositionY())) {
				return true;
			}
		}
		return false;
	}

	public Color randomColor() {

		int random = randGen.nextInt(7);
		if (random == 0) {
			return Color.BLUE;
		} else if (random == 1) {
			return Color.CYAN;
		} else if (random == 2) {
			return Color.MAGENTA;
		} else if (random == 3) {
			return Color.ORANGE;
		} else if (random == 4) {
			return Color.LIGHTGREEN;
		} else if (random == 5) {
			return Color.YELLOW;
		} else {
			return Color.PURPLE;
		}

	}

	public int getPositionY() {
		return positionY;
	}

	public void setPositionY(int positionY) {
		this.positionY = positionY;
	}

	public int getPositionX() {
		return positionX;
	}

	public void setPositionX(int positionX) {
		this.positionX = positionX;
	}
}
