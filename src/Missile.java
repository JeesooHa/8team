
public class Missile 
{
	
		 int x;
		 int y;
		 int angle;
		 int speed;

		 Missile(int x, int y, int angle, int speed) 
		 {
			 this.x = x;
			 this.y = y;

			 this.angle = angle;

			 this.speed = speed;

		 }

		 public void move() 
		 {
			 x += Math.cos(Math.toRadians(angle))*speed;
			 y += Math.sin(Math.toRadians(angle))*speed;
		 }
		
}
