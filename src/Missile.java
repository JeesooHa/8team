import java.awt.Image;

/*
=====================
:mod:'Missile' ���
===================== 
  
.. moduleauthor:: ������ <jsha2913@gmail.com>
  
  
����
====
 
 �÷��̾�� ���� �̻����� �����ϴ� Ŭ����
 
 who ������ ���� 0�� ��쿡�� �÷��̾��� �̻����� �����ǰ�,
 1�� ��쿡�� ���� �̻����� �����ȴ�.


����
====

 ���ø�ũ:
 * http://blog.naver.com/dosem321/40171473252
 
 
���� �۾���
======== 

 * ������ (Jeesoo Ha)
 * Ȳ���� (Soonguen Hwang)

�۾�����
--------

 * 2016.09.15 Jeesoo : �̻��� Ŭ���� ���� 

*/

public class Missile{
	Image type;
	int x;
	int y;
	int angle;// 0 is direct.
	int speed;
	int who;// 0: plane, 1: enemy
	Missile(Image img, int x, int y, int speed, int who, int angle) {
		this.type = img;
		this.x = x;
		this.y = y;

		this.angle = angle;

		this.speed = speed;
		this.who = who;
	}

	public void move() {
		if(this.who == 0){
			x += Math.cos(Math.toRadians(angle))*speed;
			y += Math.sin(Math.toRadians(angle))*speed;
		}
		else{
			x -= Math.cos(Math.toRadians(angle))*speed;
			y -= Math.sin(Math.toRadians(angle))*speed;
		}
	}
		
}
