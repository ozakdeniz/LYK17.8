package tr.org.linux.kamp.agarioclone.model;

import java.awt.Color;

public class Player extends GameObject {
	/*player objesini oluşturduk farklı olarak speed fieldını tanımladık getter ve setterlarını 
	 * tanımladık player objesini diğer sınıflarda çağırabilmek adına constructer yarattık  */

	private int speed;
	
	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}


	public Player(int x, int y, int radius, Color color, int speed) {
		super(x, y, radius, color);
		this.speed = speed;
	}

	
	

}
