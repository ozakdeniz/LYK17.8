package tr.org.linux.kamp.agarioclone.model;

import java.awt.Color;

public class Enemy extends GameObject {
	
	/*enemy sınıfında farklı olan speed fieldını tanımladık getter ve setter atadık ve farklı sınıflarda 
	 * enemy objesi yaratabilmek için contructor tanımlayıp this. larla bu değişkenlere değer atadık*/
	private int speed;

	private String name;
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}


	public Enemy(int x, int y, int radius, Color color, int speed,String name) {
		super(x, y, radius, color);
		this.speed = speed;
		this.name = name;
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
