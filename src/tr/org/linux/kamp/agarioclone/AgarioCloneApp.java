package tr.org.linux.kamp.agarioclone;

import tr.org.linux.kamp.agarioclone.logic.GameLogic;

public class AgarioCloneApp {
	
	public static void main(String[] args) {
		
		GameLogic gameLogic = new GameLogic();
		gameLogic.startApplication();
		
		
	}

}
/*Thread serisini açmadan oyunumuz başlamaz thread oluşmadan başlatınca boş sayfa geliyor
 * thread oluşturduktan sonra açınca hepsi basılcak ekrana*/
