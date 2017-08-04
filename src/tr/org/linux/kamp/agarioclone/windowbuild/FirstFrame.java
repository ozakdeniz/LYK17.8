package tr.org.linux.kamp.agarioclone.windowbuild;

//new/other /window builder/swing /jframe diyoruz ve bu kodlar direk geliyor



import javax.swing.JFrame;
import javax.swing.border.EmptyBorder;

public class FirstFrame extends JFrame {

	private FirstPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		
		FirstFrame frame = new FirstFrame();
		frame.pack();//çerçevenin ana öğeleri içeren şeklide minimum büyüklüğü alması
		frame.setVisible(true);
		
		
		
		//EventQueue.invokeLater(new Runnable() {
			//public void run() {
				//try {
					//FirstFrame frame = new FirstFrame();
					//frame.setVisible(true);
				//} catch (Exception e) {
					//e.printStackTrace();
				//}
			//}
//		});
	}

	/**
	 * Create the frame.
	 */
	public FirstFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new FirstPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
//		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		setTitle("LYK 2017 java");
		/**
		 * defined frame properties for instance close, bounds, border
		 */
	}

}
