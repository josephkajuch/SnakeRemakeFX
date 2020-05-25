////////////////////////////////
// CREATED BY JOSEPH KAJUCH
// NOT FOR REUSE
////////////////////////////////
package application;

/*
 * Class that represents each individual part of the snake. Movement and 
 * collision detection with frame and other snakes are dealt with in this class
 */
public class Snake {
	private int positionX;
	private int positionY;
	private boolean upMove;
	private boolean downMove;
	private boolean leftMove;
	private boolean rightMove;
	private int velocityX;
	private int velocityY;

	public int getVelocityX() {
		return velocityX;
	}

	public int getVelocityY() {
		return velocityY;
	}

	public void setVelocityX(int v) {
		velocityX = v;
	}

	public void setVelocityY(int v) {
		velocityY = v;
	}

	public void move() {

		if (velocityY == 1 || upMove) {
			moveUp();
		} else if (velocityY == -1 || downMove) {
			moveDown();
		} else if (velocityX == -1 || leftMove) {
			moveLeft();
		} else if (velocityX == 1 || rightMove) {
			moveRight();
		}
	}

	private void moveUp() {
		int tempPositionX = positionX;
		int tempPositionY = positionY;

		// Wall Collision, can use this to end game
		if (!(positionY == 125)) { // collision with the top wall
			if (collisionWithOtherSnake(0, -50) == false) {
				if (Main.fruit.collisionWithFruit(0, -50) == false) {
					tempPositionY = positionY;
					positionY -= 50;
					tempPositionX = positionX;
					drawSnakes(tempPositionX, tempPositionY);

				} else {
					// add to snake array in Main, create new fruit object
					Snake newSnake = new Snake();
					newSnake.setPositionX(
							Main.snake.get(Main.snake.size() - 1).positionX);
					newSnake.setPositionY(
							Main.snake.get(Main.snake.size() - 1).positionY);
					Main.snake.add(newSnake);
					positionY -= 50;

					Main.fpsMod -= 2;
					Main.fps = 1;
					drawSnakes(tempPositionX, tempPositionY);
					Main.fruit = new Fruit();
					Main.fruit.color = Main.fruit.randomColor();
					Main.fruitPlayer.play();
					Main.fruitPlayer.stop();
				}
			} else {
				Main.gameOver = true;
			}
		} else {
			Main.gameOver = true;
		}
	}

	private void moveDown() {
		int tempPositionX = positionX;
		int tempPositionY = positionY;

		// Wall Collision
		if (!(positionY == 625)) {
			if (collisionWithOtherSnake(0, 50) == false) {
				if (Main.fruit.collisionWithFruit(0, 50) == false) {
					tempPositionY = positionY;
					positionY += 50;
					tempPositionX = positionX;
					drawSnakes(tempPositionX, tempPositionY);
				} else {
					Snake newSnake = new Snake();
					newSnake.setPositionX(
							Main.snake.get(Main.snake.size() - 1).positionX);
					newSnake.setPositionY(
							Main.snake.get(Main.snake.size() - 1).positionY);
					Main.snake.add(newSnake);
					positionY += 50;

					Main.fpsMod -= 2;
					Main.fps = 1;
					drawSnakes(tempPositionX, tempPositionY);
					Main.fruit = new Fruit();
					Main.fruit.color = Main.fruit.randomColor();
					Main.fruitPlayer.play();
					Main.fruitPlayer.stop();
				}
			} else {
				Main.gameOver = true;
			}
		} else {
			Main.gameOver = true;
		}
	}

	private void moveLeft() {
		int tempPositionX = positionX;
		int tempPositionY = positionY;

		// Wall Collision
		if (!(positionX == 365)) {
			if (collisionWithOtherSnake(-50, 0) == false) {
				if (Main.fruit.collisionWithFruit(-50, 0) == false) {
					tempPositionX = positionX;
					positionX -= 50;
					tempPositionY = positionY;
					drawSnakes(tempPositionX, tempPositionY);
				} else {
					Snake newSnake = new Snake();
					newSnake.setPositionX(
							Main.snake.get(Main.snake.size() - 1).positionX);
					newSnake.setPositionY(
							Main.snake.get(Main.snake.size() - 1).positionY);
					Main.snake.add(newSnake);
					positionX -= 50;

					Main.fpsMod -= 2;
					Main.fps = 1;
					drawSnakes(tempPositionX, tempPositionY);
					Main.fruit = new Fruit();
					Main.fruit.color = Main.fruit.randomColor();
					Main.fruitPlayer.play();
					Main.fruitPlayer.stop();
				}
			} else {
				Main.gameOver = true;
			}
		} else {
			Main.gameOver = true;
		}
	}

	private void moveRight() {
		int tempPositionX = positionX;
		int tempPositionY = positionY;

		// Wall Collision
		if (!(positionX == 865)) {
			if (collisionWithOtherSnake(50, 0) == false) {
				if (Main.fruit.collisionWithFruit(50, 0) == false) {
					tempPositionX = positionX;
					positionX += 50;
					tempPositionY = positionY;
					drawSnakes(tempPositionX, tempPositionY);
				} else {

					Snake newSnake = new Snake();
					newSnake.setPositionX(
							Main.snake.get(Main.snake.size() - 1).positionX);
					newSnake.setPositionY(
							Main.snake.get(Main.snake.size() - 1).positionY);
					Main.snake.add(newSnake);

					positionX += 50;

					Main.fpsMod -= 2;
					Main.fps = 1;
					drawSnakes(tempPositionX, tempPositionY);
					Main.fruit = new Fruit();
					Main.fruit.color = Main.fruit.randomColor();
					Main.fruitPlayer.play();
					Main.fruitPlayer.stop();
				}
			} else {
				Main.gameOver = true;
			}
		} else {
			Main.gameOver = true;
		}
	}

	private void drawSnakes(int tempPositionX, int tempPositionY) {

		int temporaryX = tempPositionX;
		int temporaryY = tempPositionY;
		for (int i = 1; i < Main.snake.size(); i++) {
			int secondTempX = 0;
			int secondTempY = 0;

			secondTempX = Main.snake.get(i).getPositionX();
			secondTempY = Main.snake.get(i).getPositionY();

			Main.snake.get(i).setPositionX(temporaryX); // then set new temp
														// position of last
														// snake i
			Main.snake.get(i).setPositionY(temporaryY);

			temporaryX = secondTempX; // Main.snake[i].getPositionX();
			temporaryY = secondTempY; // Main.snake[i].getPositionY();
		}

	}

	private boolean collisionWithOtherSnake(int xChange, int yChange) {
		// see if first snake hits itself, can use this to end the game when hit
		// yourself
		for (int i = 0; i < Main.snake.size(); i++) {
			if ((Main.snake.get(0).getPositionX() + xChange == Main.snake.get(i)
					.getPositionX())
					&& (Main.snake.get(0).getPositionY() + yChange == Main.snake
							.get(i).getPositionY())) {
				return true;
			}
		}
		return false;
	}

	public int getPositionX() {
		return positionX;
	}

	public int getPositionY() {
		return positionY;
	}

	public void setPositionX(int posX) {
		positionX = posX;
	}

	public void setPositionY(int posY) {
		positionY = posY;
	}

	public boolean getUpMove() {
		return upMove;
	}

	public boolean getDownMove() {
		return downMove;
	}

	public boolean getLeftMove() {
		return leftMove;
	}

	public boolean getRightMove() {
		return rightMove;
	}

	public void setUpMove(boolean move) {
		upMove = move;
	}

	public void setDownMove(boolean move) {
		downMove = move;
	}

	public void setLeftMove(boolean move) {
		leftMove = move;
	}

	public void setRightMove(boolean move) {
		rightMove = move;
	}

}
