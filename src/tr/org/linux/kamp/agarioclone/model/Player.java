package tr.org.linux.kamp.agarioclone.model;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

public class Player extends GameObject {
	/*player objesini oluşturduk farklı olarak speed fieldını tanımladık getter ve setterlarını 
	 * tanımladık player objesini diğer sınıflarda çağırabilmek adına constructer yarattık  */

	private int speed;
	private String playerName;
	
	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}
	
	@Override
	public void draw(Graphics2D g2d) {
		// TODO Auto-generated method stub
		super.draw(g2d);
		
		
		FontMetrics fontMetrics = g2d.getFontMetrics(g2d.getFont());  //başlangıç ekranında girilen ismi oyuncunun üstüne taşımak için yapıyoruz bu kısmı
		int width = fontMetrics.stringWidth(playerName);
		int nameX = getX()+ (getRadius() - width)/2;  //ismin uzunluğunu oyuncunun büyüklüğünün x koordinatına göre ayarlıyoruz
		int nameY = getY() - fontMetrics.getHeight();
		g2d.drawString(playerName, nameX, nameY);
		/**
		 * made draw player to screen and count player name's lengt to the circle
		 */
	}


	public Player(int x, int y, int radius, Color color, int speed, String playerName) {
		super(x, y, radius, color);
		this.speed = speed;
		this.playerName = playerName;
	}
	
	@Override
	public void setRadius(int radius) {
		// TODO Auto-generated method stub
		super.setRadius(radius);
		
		if(getRadius() < 8) {
			setRadius(8);
		}else if(getRadius() > 250) {
			setRadius(250);
			
			/**
			 * blocked the radius to get so bigger as cover the screen or get so little as cant see
			 */
		}
		
	}
	

	
	

}
