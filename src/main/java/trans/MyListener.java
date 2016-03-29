package trans;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import org.apache.commons.io.IOUtils;

public class MyListener extends MouseAdapter {
	
	private final static int NUMBER_OF_TRANSLATIONS = 5;
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getClickCount() != 2) {
			return;
		}

		try {

			String selectedWord = ((JTextArea) e.getComponent()).getSelectedText();
			String wordsToShow = selectedWord + ":\n\n";

			URL url = new URL("http://www.arabdict.com/en/english-arabic/" + selectedWord);
			HttpURLConnection httpcon = (HttpURLConnection) url.openConnection();
			httpcon.addRequestProperty("User-Agent", "Mozilla/4.0");
			InputStream is = httpcon.getInputStream();
			String answer = IOUtils.toString(is, "UTF8");
			is.close();
			
			List<String> arabicTranslations = extractUsingRegex("arabic-term\">(.*?)<", answer.replaceAll("\\n", " "));
			for (int i = 0; i < NUMBER_OF_TRANSLATIONS; i++) {
				if (i < arabicTranslations.size()) {
					wordsToShow += arabicTranslations.get(i) + "\n";
				}
			}

			JOptionPane.showMessageDialog(e.getComponent().getParent(), wordsToShow, selectedWord, JOptionPane.PLAIN_MESSAGE);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	private List<String> extractUsingRegex(String regex, String s) {
		List<String> results = new ArrayList<String>();
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(s);
		while (matcher.find()) {
			results.add(matcher.group(1));
		}

		if (results.isEmpty()) {
			results.add("");
		}
		return results;
	}

}
