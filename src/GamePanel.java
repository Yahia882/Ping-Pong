import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class GamePanel extends JPanel implements Runnable {
	static final int Game_WIDTH = 1000;
	static final int Game_HEIGHT = (int) (Game_WIDTH * .5555);
	static final Dimension SCREEN_SIZE = new Dimension(Game_WIDTH, Game_HEIGHT);
	static final int BALL_DIAMETER = 20;
	static final int PADDLE_WIDTH = 25;
	static final int PADDLE_HEIGHT = 100;
	Thread thread;
	Image image;
	Graphics graphics;
	Random random;
	Paddle paddle1;
	Paddle paddle2;
	Ball ball;
	Score score;
	public GamePanel() {
		newPaddles();
		newBall();
		this.setBackground(Color.BLACK);
		score = new Score(Game_WIDTH,Game_HEIGHT);
		this.setFocusable(true);
		this.addKeyListener(new al());
		this.setPreferredSize(SCREEN_SIZE);
		thread = new Thread(this);
		thread.start();

	}

	





	public void newBall() {
		random = new Random();
		ball = new Ball((Game_WIDTH / 2) - (BALL_DIAMETER / 2), random.nextInt(Game_HEIGHT - BALL_DIAMETER),
				BALL_DIAMETER, BALL_DIAMETER);

	}

	public void newPaddles() {
		paddle1 = new Paddle(0, (Game_HEIGHT / 2) - PADDLE_HEIGHT / 2, PADDLE_WIDTH, PADDLE_HEIGHT, 1);
		paddle2 = new Paddle(Game_WIDTH - PADDLE_WIDTH, (Game_HEIGHT / 2) - PADDLE_HEIGHT / 2, PADDLE_WIDTH,
				PADDLE_HEIGHT, 2);

	}

	public void paint(Graphics g) {

		

			image = createImage(Game_WIDTH, Game_HEIGHT);
			;
			graphics = image.getGraphics();
			draw(graphics);
			g.drawImage(image, 0, 0, this);

		

	}

	public void draw(Graphics g) {
		paddle1.draw(g);
		paddle2.draw(g);
		ball.draw(g);
		score.draw(g);

	}

	public void move() {
		paddle1.move();
		paddle2.move();
		ball.move();

	}

	public void checkCollision() {
		// collisions of the ball top and bottom
		if (ball.y <= 0) {
			ball.setYDirection(-ball.yVelocity);

		}
		if (ball.y >= Game_HEIGHT - BALL_DIAMETER) {
			ball.setYDirection(-ball.yVelocity);

		}
		// collision of the ball with the paddles
		if (ball.intersects(paddle1)) {
			ball.xVelocity = Math.abs(ball.xVelocity);
			// ball.xVelocity++; //optional for more difficulty
			if (ball.yVelocity > 0) {
				ball.yVelocity++;
			} else {
				ball.yVelocity--;
			}
			ball.setXDirection(ball.xVelocity);
			ball.setYDirection(ball.yVelocity);

		}
		if (ball.intersects(paddle2)) {
			ball.xVelocity = Math.abs(ball.xVelocity);
			ball.xVelocity++; // optional for more difficulty
			if (ball.yVelocity > 0) {
				ball.yVelocity++;
			} else {
				ball.yVelocity--;
			}
			ball.setXDirection(-ball.xVelocity);
			ball.setYDirection(ball.yVelocity);

		}

		// stops paddles at window edges
		if (paddle1.y <= 0) {
			paddle1.y = 0;

		}
		if (paddle1.y >= Game_HEIGHT - PADDLE_HEIGHT) {
			paddle1.y = Game_HEIGHT - PADDLE_HEIGHT;

		}
		if (paddle2.y <= 0) {
			paddle2.y = 0;

		}
		if (paddle2.y >= Game_HEIGHT - PADDLE_HEIGHT) {
			paddle2.y = Game_HEIGHT - PADDLE_HEIGHT;

		}
		// give player 1 point and create new paddles and ball
		if (ball.x <= 0) {
			score.player2++;
			newPaddles();
			newBall();
			System.out.println(score.player2);
		}
		if (ball.x >= Game_WIDTH - BALL_DIAMETER) {
			score.player1++;
			newPaddles();
			newBall();
			System.out.println(score.player1);

		}

	}

	public void run() {
		
			long lastTime = System.nanoTime();
			double amountOfTicks = 60.0;
			double ns = 1000000000 / amountOfTicks;
			double Delta = 0;
			while (true) {
				long now = System.nanoTime();
				Delta += (now - lastTime) / ns;
				lastTime = now;
				if (Delta >= 1) {
					move();
					checkCollision();
					repaint();
					Delta--;

				}

			}
		}

	

	public class al extends KeyAdapter {
		public void keyPressed(KeyEvent e) {
			paddle1.keyPressed(e);
			paddle2.keyPressed(e);

		}

		public void keyReleased(KeyEvent e) {
			paddle1.keyReleased(e);
			paddle2.keyReleased(e);

		}

	}

}
