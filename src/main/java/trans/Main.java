package trans;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

public class Main {

	public static void main(String[] args) {
		JTextArea text = new JTextArea();
		text.setLineWrap(true);
		text.setWrapStyleWord(true);
		
	    JScrollPane scroll = new JScrollPane ( text );
	    scroll.setVerticalScrollBarPolicy ( ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS );

		
	    JFrame f = new JFrame("Translator");
	    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    f.setSize(500, 300);
	    f.setExtendedState(JFrame.MAXIMIZED_BOTH);
		f.add(scroll);
		f.setVisible(true);

		
		MyListener listener = new MyListener();
		text.addMouseListener(listener);

	}

}
