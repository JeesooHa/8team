/*
=====================
:mod:'Explosion' ���
===================== 
  
.. moduleauthor:: ������ <jsha2913@gmail.com>
  
  
����
====
 
 �÷��̾�� ������ �̻��� ���� �̺�Ʈ�� ���� Ŭ���� ����


����
====

 ���ø�ũ:
 * http://blog.naver.com/dosem321/40171029559
 
 
���� �۾���
======== 

 * ������ (Jeesoo Ha)
 * Ȳ���� (Soonguen Hwang)

�۾�����
--------

 * 2016.09.12 Jeesoo : ���� Ŭ���� ���� 

 */




public class Explosion 
{
	int x,y;
	int ex_cnt;
	int damage; 

	Explosion(int x, int y, int damage)
	{
		this.x = x;
		this.y = y;
		this.damage = damage;
	}
	
	public void effect()
	{
		ex_cnt ++;
	}
}
