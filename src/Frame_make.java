import javax.swing.*;
import java.awt.*;

public class Frame_make {
	
	public static void main(String[] ar){

	Frame_make_sub fms = new Frame_make_sub();
	
	}
}

class Frame_make_sub extends JFrame{ // �������� ����� ���� Ŭ����

	int f_width = 800; //������ �������� ���� ��
	int f_height = 600; //������ �������� ���� ��
	
	Frame_make_sub(){ //������
	
		init(); //������ ���� �����ӿ� �� ������Ʈ ���� �޼ҵ�
		start(); //������ ���� �⺻���� ���� ���
	
		setTitle("������ �׽�Ʈ"); //������ �̸�
		setSize(f_width, f_height); //������ ũ�� ����
		
		//�������� �����쿡 ǥ�õɶ� ��ġ�� �����ϱ� ���� ���� ������� �ػ� ���� �޾ƿ�
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		
		//�������� ����� ȭ�� ���߾ӿ� ��ġ��Ű�� ���� ��ǥ ���� ���
		int f_xpos = (int)(screen.getWidth() / 2 - f_width / 2);
		int f_ypos = (int)(screen.getHeight() / 2 - f_height / 2);
		

		setLocation(f_xpos, f_ypos);//�������� ȭ�鿡 ��ġ
		setResizable(false); // �������� ũ�⸦ ���Ƿ� ������ϰ� ����
		setVisible(true); // �������� ���� ���̰� ���ϴ�.
	}

	public void init(){ //������ ���� �����ӿ� �� ������Ʈ ���� �޼ҵ�
	}
	
	public void start(){ //������ ���� �⺻���� ���� ���
	}
}
