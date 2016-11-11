import javax.swing.*;
import java.awt.*;

public class Frame_make {
	
	public static void main(String[] ar){

	Frame_make_sub fms = new Frame_make_sub();
	
	}
}

class Frame_make_sub extends JFrame{ 

	int f_width = 800; 
	int f_height = 600;
	
	Frame_make_sub(){ 
	
		init();
		start(); 
	
		setTitle("Frame Test");
		setSize(f_width, f_height); 
		
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		
		int f_xpos = (int)(screen.getWidth() / 2 - f_width / 2);
		int f_ypos = (int)(screen.getHeight() / 2 - f_height / 2);
		
		setLocation(f_xpos, f_ypos);	//set frame location 
		
		setResizable(false);
		setVisible(true); 
	}

	public void init(){ 
	}
	
	public void start(){
	}
}
