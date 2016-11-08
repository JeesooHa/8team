import javax.swing.*;
import java.awt.*;

public class Frame_make {
	
	public static void main(String[] ar){

	Frame_make_sub fms = new Frame_make_sub();
	
	}
}

class Frame_make_sub extends JFrame{ // 프레임을 만들기 위한 클래스

	int f_width = 800; //생성할 프레임의 넓이 값
	int f_height = 600; //생성할 프레임의 높이 값
	
	Frame_make_sub(){ //생성자
	
		init(); //나중을 위한 프레임에 들어갈 컴포넌트 세팅 메소드
		start(); //나중을 위한 기본적인 시작 명령
	
		setTitle("프레임 테스트"); //프레임 이름
		setSize(f_width, f_height); //프레임 크기 설정
		
		//프레임이 윈도우에 표시될때 위치를 세팅하기 위해 현재 모니터의 해상도 값을 받아옴
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		
		//프레임을 모니터 화면 정중앙에 배치시키기 위해 좌표 값을 계산
		int f_xpos = (int)(screen.getWidth() / 2 - f_width / 2);
		int f_ypos = (int)(screen.getHeight() / 2 - f_height / 2);
		

		setLocation(f_xpos, f_ypos);//프레임을 화면에 배치
		setResizable(false); // 프레임의 크기를 임의로 변경못하게 설정
		setVisible(true); // 프레임을 눈에 보이게 띄웁니다.
	}

	public void init(){ //나중을 위한 프레임에 들어갈 컴포넌트 세팅 메소드
	}
	
	public void start(){ //나중을 위한 기본적인 시작 명령
	}
}
