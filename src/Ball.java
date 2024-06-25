import java.awt.*;
import java.util.*;
public class Ball extends Rectangle{
	
	Random random;
	int xVelocity;
	int yVelocity;
	int intialSpeed=4;
	
	Ball(int x, int y,int width,int height){
		super(x,y,width,height);
		random= new Random();
		int randomXdirection = random.nextInt(2);
		if(randomXdirection==0) {
			randomXdirection--;
			setXDirection(randomXdirection*intialSpeed);
			
		}
		setXDirection(randomXdirection*intialSpeed);
		int randomYdirection = random.nextInt(2);
		if(randomYdirection==0) {
			randomYdirection--;
			
		}
		setYDirection(randomYdirection*intialSpeed);
		
		
	}
	public void setXDirection(int randomXdirection) {
		xVelocity = randomXdirection;
		
		
	}
    public void setYDirection(int randomYdirection) {
    	yVelocity = randomYdirection;
		
		
	}
    
    public void move() {
    	x+=xVelocity;
    	y+=yVelocity;
    	
    }
    
    public void draw(Graphics g) {
    	g.setColor(Color.white);
    	g.fillOval(x, y, height, width);
    	
    }

}
