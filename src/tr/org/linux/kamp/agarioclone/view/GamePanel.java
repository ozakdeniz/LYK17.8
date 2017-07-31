package tr.org.linux.kamp.agarioclone.view;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JPanel;

import tr.org.linux.kamp.agarioclone.model.GameObject;

public class GamePanel extends JPanel {
	
	private ArrayList<GameObject> gameObjects;
	
	public GamePanel(ArrayList<GameObject> gameObjects) {
		this.gameObjects=gameObjects;
		
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		
		Graphics2D g2d =(Graphics2D)g;
		
		for(GameObject gameObject: gameObjects) {//for each kullanımı GameObject objeleri için
			gameObject.draw(g2d);
		}
	}

}
