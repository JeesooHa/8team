/*
=====================
:mod:'Enemy' ���
===================== 
  
.. moduleauthor:: ������ <jsha2913@gmail.com>
  
  
����
====
 
 ���� �����ϴ� Ŭ����
 
 type ������ ���� 1~3�� ��� �� ������ �Ϲ� ���� �����ǰ�,
 ������ ���� 4~6�� ��쿡�� �� stage�� ������ �����ȴ�.


����
====

 ���ø�ũ:
 * http://blog.naver.com/dosem321/40171017707
 
 
���� �۾���
======== 

 * ������ (Jeesoo Ha)
 * Ȳ���� (Soonguen Hwang)

�۾�����
--------

 * 2016.09.09 Jeesoo : �� Ŭ���� ���� 

*/
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
