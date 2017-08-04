package tr.org.linux.kamp.agarioclone.logic;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Random;

import tr.org.linux.kamp.agarioclone.model.Chip;
import tr.org.linux.kamp.agarioclone.model.Difficulty30temmuz;
import tr.org.linux.kamp.agarioclone.model.Enemy;
import tr.org.linux.kamp.agarioclone.model.GameObject;
import tr.org.linux.kamp.agarioclone.model.Mine;
import tr.org.linux.kamp.agarioclone.model.Player;
import tr.org.linux.kamp.agarioclone.view.GameFrame;
import tr.org.linux.kamp.agarioclone.view.GamePanel;
/**
 * 
 * @author ozgur
 * @version 1.0
 * 
 *
 *
 */
public class GameLogic {
	
	//1 playerı oluşturduk hareket tanımladık mouse haraketi ile
	//2 yemleri tanımladık büyüklüklerini ayarladık
	//3 player yemleri yiyince büyüyeceğini ayarladık yenilen yemleri yok ettik ve yeni yemler atadık
	//4 mayınları oluşturduk boyutlarını tanımladık
	//5 player mayın yiyince küçülceğini ayarladık yok olan mayınlar yerine yeni mayınlar atadık
	//6 enemy tanmımladık boyutları oluşturduk playera göre hareket tanımladık
	//7 enemy yemi yiyince büyüme yaptık
	//8 enemy mayın yiyince küçülme ayarladık
	//9 enemy ile player arasında ilişkiyi kurduk enemy küçükse playerdan kaçıyo büyükse kovalıyo
	//10 player enemy yiyince yeni bi enemy attık ve yediği enemyi yok edip playerın boyutunu büyüttük
	//11 enemy playerı yiyince oyunu durdurduk
	//12 ince ayarları yaptık
	
	
	private boolean isGameRunning = true;
	private int xTarget;//mouse için
	private int yTarget;

	private Player player;// önce playerı oluşturuyoruz sonra oyuncu listesini oluşturuyoruz ki
	// oyuncuyu görmemezlik yapmasın direk oyuncuyu listeye alsın oyuncuyu listeden
	// sonra yazarsak oyun hata verir ve başlamaz.
	private ArrayList<GameObject> gameObjects;
	ArrayList<GameObject> chipsToRemove;// yemler ekrandan silinip bu listeye atılcak
	private ArrayList<GameObject> minesToRemove;// mayınlar ekrandan silinip bu listeye atılcak
	private ArrayList<GameObject> enemiesToRemove;// ekrandan silincek düşmanlar listesi

	private GameFrame gameFrame;//çerçeve
	private GamePanel gamePanel;//penel

	private Random random = new Random();// random tanımladık
	
	/**
	 * 
	 * @param playerName defined here as player name
	 * @param selectedColor defined here colors as player choose them
	 * @param difficulty defined here to choose one of easy normal hard properties
	 */
	
	

	public GameLogic(String playerName, Color selectedColor, Difficulty30temmuz difficulty) {
		
		player = new Player(20, 20, 20, selectedColor, 1, playerName);// playerın özelliklerini tanımladık

		gameObjects = new ArrayList<GameObject>();// oyun nesnelerini tutucağımız liste oyuncu düşmanlar yemler..
		chipsToRemove = new ArrayList<GameObject>();// yenilen yemleri tutucağımız liste
		minesToRemove = new ArrayList<GameObject>();// yenilen mayınları tutalan liste
		enemiesToRemove = new ArrayList<GameObject>();// yenilen düşmanların eklenceği liste

		gameObjects.add(player);// player nesnesini oyuna ekledik

		gameFrame = new GameFrame();
		gamePanel = new GamePanel(gameObjects);
		/**
		 * add frame and panel to game
		 */
		
		switch (difficulty) {
		case EASY:
			
			fillChips(100);// yemleri oyuna ekledik
			fillMines(30);// mayınları oyuna ekledik
			fillEnemies(5);//düşmanları oyuna ekledik

			break;
		
		case NORMAL :
			
			fillChips(100);// yemleri oyuna ekledik
			fillMines(40);// mayınları oyuna ekledik
			fillEnemies(10);//düşmanları oyuna ekledik

			break;
			
		case HARD:
			
			fillChips(100);// yemleri oyuna ekledik
			fillMines(50);// mayınları oyuna ekledik
			fillEnemies(20);//düşmanları oyuna ekledik

			break;
			/**
			 * defined difficulty
			 */
		}
		

		addMouseListener();

	}
	/**
	 * checks collisions
	 */
	

	private synchronized void checkCollisions() {// çarpışmaları kontrol ediyoruz yediklerimizi sildiriyoruz

		for (GameObject gameObject : gameObjects) {// for each kullanımı

			if (player.getRectangle().intersects(gameObject.getRectangle())) {//player küçük bile olsa mayın etkiliyor
//			if (player.getRectangle().contains(gameObject.getRectangle())) { //player mayınlardan küçükse mayın playerı etkilemiyor
				
				if (gameObject instanceof Chip) {// yem için olan çarpışmayı kotrol ediyoruz
					player.setRadius(player.getRadius() + gameObject.getRadius() / 3);
					// gameObjects.remove(gameObject);
					chipsToRemove.add(gameObject);
				}

				if (gameObject instanceof Mine) {// mayınla olan çarpışmaları kontrol ediyoruz
					player.setRadius(player.getRadius() / 2);// çarpışma sonucunda oyuncunun büyüklüğü yarıya
																// düşürüyoruz
					minesToRemove.add(gameObject);
				}
				
				
				
				if(gameObject instanceof Enemy) {//enemynin playerla çarpışmasını ayarlıyoruz
					if(player.getRadius() > gameObject.getRadius()) {//enemy küçükse yem olcak
						player.setRadius((int)player.getRadius()+gameObject.getRadius());
						enemiesToRemove.add(gameObject);
					
						
					}else if(player.getRadius() < gameObject.getRadius()) {//enemy büyükse bizi yicek ve oyun durucak
						gameObject.setRadius(gameObject.getRadius() + player.getRadius());
						
						isGameRunning = false;//oyun durucak
					}
				}
			}
			
			
			if(gameObject instanceof Enemy) {//enemy nin yemle mayınla ve oyuncuyla çarpışmalarını ayarlicaz
				Enemy enemy = (Enemy) gameObject;
				
				for(GameObject gameObject2 : gameObjects) {//gameobjectsi game object sınıfından çektik 
					if(enemy.getRectangle().intersects(gameObject2.getRectangle())) {//enemy i gameobject2ye eşitledik 
						//böylece enemyinin özelliklerini çekebiliyor olucaz ve hata almicaz
						
						if(gameObject2 instanceof Chip) {// enemynin yemle ilişkisi
							enemy.setRadius(enemy.getRadius()+ gameObject2.getRadius());
							chipsToRemove.add(gameObject2);
						}
						
						if(gameObject2 instanceof Mine) {//enemy nin mayınla ilişkisi
							enemy.setRadius((int)enemy.getRadius()/2);
							minesToRemove.add(gameObject2);
						}
						
					}
					
				}
				
			}
			

		}
		
		
		
		//oyuncuyla çarpışan nesnelerin silme listesine taşınması
		gameObjects.removeAll(chipsToRemove);
		gameObjects.removeAll(minesToRemove);
		gameObjects.removeAll(enemiesToRemove);

	}

	/**
	 * add new objects when they destroy at check collision
	 */
	
	
	private synchronized void addNewObjects() {// yediklerimizi yeniden oluşturuyoruz ki yem bitip oyun bitmesin
		fillChips(chipsToRemove.size());// sildiğimiz yem kadar yem ekliyoruz
		// sarı uyarıyı eclipse genelde oluşturduğumuz metodu biyerde kullanmayınca
		// verir
		fillMines(minesToRemove.size());// sildiğimiz mayın kadar mayın oluşturuyoruz
		
		fillEnemies(enemiesToRemove.size());//sildiğimiz enemyler yerine enemy oluşuyor

		chipsToRemove.clear();// silme listesine attıklarımızı temizliyoruz
		minesToRemove.clear();// silme listesine attıklarımızı temizliyoruz
		enemiesToRemove.clear();// silme listesine attıklarımızı temizliyoruz

	}

	
	
	private synchronized void movePlayer() {// oyuncunun hareketleri mouse hareketine göre tanımlandı
		// x ve y koordinatlarında hareketler tanımladık
/**
 * defined move
 */
		if (xTarget > player.getX()) {
			player.setX(player.getX() + player.getSpeed());
		} else if (xTarget < player.getX()) {
			player.setX(player.getX() - player.getSpeed());
		}

		if (yTarget > player.getY()) {
			player.setY(player.getY() + player.getSpeed());

		} else if (yTarget < player.getY()) {
			player.setY(player.getY() - player.getSpeed());
		}

	}
	
	
	
	private synchronized void moveEnemies() {
		/**
		 * defined move
		 */
		
		for(GameObject gameObject : gameObjects) {//game objectin içinde speed yok bu yüzden diğerleri gibi yazamadık
			if (gameObject instanceof Enemy) {//enemy olarak kullanmak istediğimiz için casting sağlaması yaptık
				Enemy enemy =(Enemy) gameObject;//önce foreach gameObject yapıyoruz sonra gameo yu enemy yazdırıyoruz
				//ve enemy özelliklerine böylece ulaşabilmiş oluyoruz
				
				if(enemy.getRadius() < player.getRadius()) {//düşman bizden küçükse kaçıcak
					
					int distance =(int)Point.distance(player.getX(), player.getY(), enemy.getX(), enemy.getY());
					//playerla enemy arasındaki uzaklığı ayarladık doğduklarında dibinde doğmasın diye
					
					int newX = enemy.getX()+ enemy.getSpeed();
					//newxy: enemy playerdan küçük kaçması lazım hangi yöne kaçması gerektiğini buldurcaz 
					int newY = enemy.getY()+ enemy.getSpeed();
					if(calculateNewDistanceToEnemy(enemy, distance, newX, newY)) {
						continue;
				}	
					newX = enemy.getX()+ enemy.getSpeed();
					newY = enemy.getY()- enemy.getSpeed();
					if(calculateNewDistanceToEnemy(enemy, distance, newX, newY)) {
						continue;
				}
					newX = enemy.getX()- enemy.getSpeed();
					newY = enemy.getY()+ enemy.getSpeed();
					if(calculateNewDistanceToEnemy(enemy, distance, newX, newY)) {
						continue;
				}
					newX = enemy.getX()- enemy.getSpeed();
					newY = enemy.getY()- enemy.getSpeed();
					if(calculateNewDistanceToEnemy(enemy, distance, newX, newY)) {
						continue;
				}

					

				}else {//büyükse kovalicak
					//playerın hareket mekanizmasını kopyaladık sonuçta büyük düşmanlar playerı kovalicak
					//player yazan yerleri enemy diye değiştirdik ve xytarget yerine playergetxy atadık 
					//böylece büyük düşmanların da hareket kodları tamamlanmış oldu
					if (player.getX() > enemy.getX()) {
						enemy.setX(enemy.getX() + enemy.getSpeed());
					} else if (player.getX() < enemy.getX()) {
						enemy.setX(enemy.getX() - enemy.getSpeed());
					}

					if (player.getY() > enemy.getY()) {
						enemy.setY(enemy.getY() + enemy.getSpeed());

					} else if (player.getY() < enemy.getY()) {
						enemy.setY(enemy.getY() - enemy.getSpeed());
					}
					
					
					
					
			}
		}
	}
}
	
	
	
	
	private boolean calculateNewDistanceToEnemy(Enemy enemy, int distance, int x, int y) {

		/**
		 * defined distance enemy from player 
		 */
		int newDistance =(int)Point.distance(player.getX(), player.getY(), x, y);
		if(newDistance > distance) {
			enemy.setX(x);
			enemy.setY(y);
			return true;
		}
		return false;
	}
	
	
	
	private void fillChips(int n) {// yem oluşma metodunu oluşturduk özelliklerini yazdık

		/**
		 * defined creating chips method
		 */
		// yemleri oluşturduk yemlerle doldur metodu oluşturduk ve yemlerin
		// özelliklerini bastırdık
		for (int i = 0; i < n; i++) {
			/*int x = random.nextInt(gamePanel.getWidth());
			int y = random.nextInt(gamePanel.getHeight());
			
			if(x>= gamePanel.getWidth()) {
				x -= 15;
			}
			
			if(y >= gamePanel.getHeight()) {
				y -= 15;
			}*/
			gameObjects.add(new Chip(random.nextInt(1000), random.nextInt(1000), 10, Color.MAGENTA));
			// yemlerin yerlerini random atadık ve özelliklerini tanımladık

		}
	}

	
	
	
	private void fillMines(int n) {// mayının oluşma merodunu yaptık
/**
 *  defined creating mines method
 */
		for (int i = 0; i < n; i++) {
			Mine mine = new Mine(random.nextInt(1000), random.nextInt(1000), 20, Color.YELLOW);

			while (player.getRectangle().intersects(mine.getRectangle())) {// İntersect mayınla
														// oyuncunun kesişip kesişmediğini kontrol ediyor
				mine.setX(random.nextInt(1000));
				mine.setY(random.nextInt(1000));
			}

			gameObjects.add(mine);

		}

	}
	
	
	

	private void fillEnemies(int n) {
		/**
		 *  defined creating enemies method
		 */
		for (int i = 0; i < n; i++) {
			Enemy enemy = new Enemy(random.nextInt(1000), random.nextInt(1000), (random.nextInt(10) + 10), Color.RED, 1,"enemy");
			while (player.getRectangle().intersects(enemy.getRectangle())) {
				enemy.setX(random.nextInt(1000));
				enemy.setY(random.nextInt(1000));
			}
			gameObjects.add(enemy);
		}
	}

	
	
	
	private void startGame() {
		new Thread(new Runnable() {
			// thread oluşturduk oyuncuları ve yemleri ekrana bastırabilmek için
			// thread :javada programımızda bi döngü oluşturmamız lazım ama java o döngüden
			// asla çıkamicak ve program
			// çalışmicak böyle bi durumda thread açıyoruz ve thread java içinde başka bir
			// iş parçacığı tanımlıyor
			// ve biz döngümüzü threadde tanımlıyoruz böylece hem döngü threadde sürekli
			// dönüyor hem de java da
			// programımızın devamını yazabiliyoruz.

			@Override
			public void run() {
				// TODO Auto-generated method stub
				gamePanel.repaint();

				while (isGameRunning) {
					movePlayer();
					moveEnemies();
					checkCollisions();
					addNewObjects();
					gamePanel.repaint();
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						// TODO: handle exception
						e.printStackTrace();
					}

				}
			}
		}).start();// start demeden thread çalışmaya başlamaz hata verir
	}

	
	/**
	 * starts the game
	 */
	
	public void startApplication() {
		gameFrame.setContentPane(gamePanel);
		gameFrame.setVisible(true);
		startGame();

	}

	/**
	 * mouse movements
	 */
	
	
	private void addMouseListener() {// mouse hareketlerine göre hareket kabiliyetini tanımlicaz
		gameFrame.addMouseListener(new MouseListener() {// Mouse hareketlerini çağırıyoruz

			@Override
			public void mouseReleased(MouseEvent e) {// fare bırakıldığında
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e) {// fareye basıldığında
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e) {// mouse ekrandan çıktığında
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent e) {// mouse ekrana girdiğinde
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseClicked(MouseEvent e) {// mouse a tıklatıldığında
				// TODO Auto-generated method stub

			}
		});

		
		
		
		gameFrame.addMouseMotionListener(new MouseMotionListener() {// mouse hareket dinleyicisi

			@Override
			public void mouseMoved(MouseEvent e) {// mouse hareketi
				// TODO Auto-generated method stub

				System.out.println("Mouse moved" + e);
				xTarget = e.getX();
				yTarget = e.getY();

			}

			@Override
			public void mouseDragged(MouseEvent e) {// mouseu sürüklemek
				// TODO Auto-generated method stub

			}
		});
	}

}
