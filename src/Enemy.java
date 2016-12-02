
public class Enemy 
{
	int x,y,speed;
	int type;
	
	Enemy(int x, int y, int speed,int type)
	{
		this.x = x;
		this.y = y;
		this.speed = speed;
		this.type = type;
	}
	
	public void move()
	{
		if(this.type < 4)
			x -= speed;
		else
			if(x>900)	x -= speed;
	}
}
