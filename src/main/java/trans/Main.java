package trans;

import javax.swing.JFrame;
import javax.swing.JTextArea;

public class Main {

	public static void main(String[] args) {
		JFrame f = new JFrame("Uhrzeit");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(1000, 300);
		JTextArea text = new JTextArea("There is no spoon.");
		f.add(text);
		f.setVisible(true);

		
		MyListener listener = new MyListener();
		text.addMouseListener(listener);

	}

}
