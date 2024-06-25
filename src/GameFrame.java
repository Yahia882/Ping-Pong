import java.awt.*;
import javax.swing.*;
public class GameFrame extends JFrame {

	GamePanel Panel;
	
	public GameFrame() {
		Panel = new GamePanel();
	    this.add(Panel);
	    this.setTitle("Ping Pong");
	    this.setResizable(false);
	    this.setBackground(Color.BLACK);
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    this.pack();
	    this.setVisible(true);
	    this.setLocationRelativeTo(null); 
}

}
