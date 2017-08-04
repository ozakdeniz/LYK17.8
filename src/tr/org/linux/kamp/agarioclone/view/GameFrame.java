package tr.org.linux.kamp.agarioclone.view;

import javax.swing.JFrame;

public class GameFrame extends JFrame {
	
	public GameFrame() {//çerçeve oluşturduk 
		setTitle("Agario Clone");//çerçeveye başlık atadık
		setResizable(true);//çerçevenin boyutunu değiştirilebilire atadık.
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//Pencere kapatıldığında programın arka planda çalışmasını engelledik memorye yüklenmesini engelledik
		setSize(640,480);//çözünürlüğü ve boyutları ayarladık
		//setLocationRelativeTo(null);//Çerçevenin ortada açılmasını sağlıyor
	/*
	 * defined game frame
	 */
	}
	

}
