package tr.org.linux.kamp.agarioclone;

import java.awt.Color;

import tr.org.linux.kamp.agarioclone.logic.GameLogic;
import tr.org.linux.kamp.agarioclone.model.Difficulty30temmuz;

public class AgarioCloneApp {
	
	public static void main(String[] args) {
		
		GameLogic gameLogic = new GameLogic("Player", Color.BLACK, Difficulty30temmuz.EASY);
		gameLogic.startApplication();
		/**
		 * start the game
		 */
		
	}

}
/*Thread serisini açmadan oyunumuz başlamaz thread oluşmadan başlatınca boş sayfa geliyor
 * thread oluşturduktan sonra açınca hepsi basılcak ekrana*/
