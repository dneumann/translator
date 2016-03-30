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

	private final static int NUMBER_OF_TRANSLATIONS = 7;

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getClickCount() != 2) {
			return;
		}

		String selectedWord = ((JTextArea) e.getComponent()).getSelectedText();
		String wordsToShow = selectedWord + ":\n\n";

		List<String> arabicTranslations = getTranslations("http://www.arabdict.com/en/english-arabic/" + selectedWord,
				"arabic-term\">(.*?)<");
		for (int i = 0; i < NUMBER_OF_TRANSLATIONS; i++) {
			if (i < arabicTranslations.size()) {
				wordsToShow += arabicTranslations.get(i) + "\n";
			}
		}
		wordsToShow += "\n";
		
		List<String> germanTranslations = getTranslations("http://en-de.dict.cc/?s=" + selectedWord,
				"a href=\"/deutsch-englisch/[^>]+>(.*?)<");
		for (int i = 0; i < NUMBER_OF_TRANSLATIONS; i++) {
			if (i < germanTranslations.size() && "".equals(germanTranslations.get(i))) {
				germanTranslations.remove(i);
				i--;
				continue;
			}
			if (i < germanTranslations.size()) {
				wordsToShow += germanTranslations.get(i) + "\n";
			}
		}

		JOptionPane.showMessageDialog(e.getComponent().getParent(), wordsToShow, selectedWord,
				JOptionPane.PLAIN_MESSAGE);
	}

	private List<String> getTranslations(String urlString, String regex) {
		List<String> foundTranslations = new ArrayList<String>();
		try {
			URL url = new URL(urlString);
			HttpURLConnection httpcon = (HttpURLConnection) url.openConnection();
			httpcon.addRequestProperty("User-Agent", "Mozilla/4.0");
			InputStream is = httpcon.getInputStream();
			String answer = IOUtils.toString(is, "UTF8");
			is.close();
			foundTranslations = extractUsingRegex(regex, answer.replaceAll("\\n", " "));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return foundTranslations;

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
