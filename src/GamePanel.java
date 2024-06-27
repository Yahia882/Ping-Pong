import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

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
	boolean running = false;
	JButton restarButton;
	JButton starButton;

	// Game begins with the start screen/window
	public GamePanel() {
		start_window();

	}
	// start window method 1. set Running = false to prevent starting the game
	// (paint method and run method)
	// 2. create a start button and its action is to start the game

	public void start_window() {
		running = false;
		this.setBackground(Color.BLACK);
		this.setPreferredSize(SCREEN_SIZE);
		starButton = new JButton("Start");
		starButton.setVisible(true);
		starButton.setBackground(Color.white);
		starButton.setForeground(Color.BLACK);
		this.setLayout(null);
		starButton.setLocation((Game_WIDTH / 2) - 50, 0);
		starButton.setSize(100, 50);
		starButton.setBorder(new EmptyBorder(5, 15, 5, 15));
		starButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				starButton.setVisible(false);
				start_game();

			}
		});
		this.add(starButton);
		repaint();
	}
	// initialize all game components, adding a restart button (to let u get back to
	// the start window) and then executing the run() method

	public void start_game() {
		running = true;
		newPaddles();
		newBall();
		this.setBackground(Color.BLACK);
		score = new Score(Game_WIDTH, Game_HEIGHT);
		this.setFocusable(true);
		this.requestFocus();
		this.addKeyListener(new al());
		this.setLayout(null);
		restarButton = new JButton("Restart");
		restarButton.setVisible(true);
		restarButton.setBackground(Color.white);
		restarButton.setForeground(Color.BLACK);
		restarButton.setLocation((Game_WIDTH / 2) - 50, Game_HEIGHT - 50);
		restarButton.setSize(100, 50);
		restarButton.setBorder(new EmptyBorder(5, 15, 5, 15));
		restarButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				restarButton.setVisible(false);
				Restart();
			}
		});
		this.add(restarButton);
		this.revalidate();
		this.repaint();
		thread = new Thread(this);
		thread.start();
	}

	public void Restart() {
		start_window();
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

		if (running) {

			image = createImage(Game_WIDTH, Game_HEIGHT);
			restarButton.repaint();
			graphics = image.getGraphics();
			draw(graphics);
			g.drawImage(image, 0, 0, this);

		} else {
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, Game_WIDTH, Game_HEIGHT);
			starButton.repaint();
		}

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
		// Main game loop
		while (true) {
			// Check if the game is running; if not, exit the loop
			if (!running) {
				break;
			}
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
