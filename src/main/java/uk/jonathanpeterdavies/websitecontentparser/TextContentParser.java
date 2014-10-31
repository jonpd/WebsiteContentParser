package uk.jonathanpeterdavies.websitecontentparser;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.jsoup.Connection;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class TextContentParser implements ITextContentParser {

	private static final String TAG_P = "p";
	private static final String TAG_A = "a";
	private static final String TAG_H = "h";
	private static final String TAG_SCRIPT = "script";
	private static final String TWITTER = "twitter";
	private static final String FACEBOOK = "facebook";
	private static final String LINKEDIN = "linkedin";
	private Document document;
	private String baseUrl;
	private String contextUrl;
	private Elements allLinks;

	public TextContentParser(String baseUrl, String contextUrl) {

		this.baseUrl = baseUrl;
		this.contextUrl = contextUrl;

		try {

			this.document = getDocument();
		}
		catch (IOException ex) {

			System.out.println(ex.getMessage());
			System.out.println("Base url: " + baseUrl + ", context url: " + contextUrl);
		}
	}

	private Document getDocument() throws IOException {

		Connection connection = Jsoup.connect(contextUrl);
		Response response = connection.execute();
		Map<String, String> allCookies = response.cookies();
		connection.cookies(allCookies);

		return connection.get();
	}

	private Elements getAllLinks() {

		if (allLinks == null) {

			allLinks = document.getElementsByTag(TAG_A);
		}

		return allLinks;
	}

	@Override
	public Elements getInternalSiteLinks() {

		Elements validLinks = new Elements();
		String link;

		for (Element element : getAllLinks()) {

			link = element.attr("abs:href");

			if (link.startsWith(baseUrl)) {

				validLinks.add(element);
			}
		}

		return validLinks;
	}

	@Override
	public Elements getHeadings() {

		Elements allElements = new Elements();
		String headingTag;

		for (int i = 1; i < 7; i++) {

			headingTag = TAG_H + i;
			allElements.addAll(document.getElementsByTag(headingTag));
		}

		return allElements;
	}

	@Override
	public Elements getParagraphs() {

		Elements allParagraphs = document.getElementsByTag(TAG_P);

		Elements cleanedParagraphs = new Elements();

		String cleanedText;

		for (Element eachParagraph : allParagraphs) {

			if (eachParagraph.text().contains("\u00A0")) {

				cleanedText = removeHTMLEntities(eachParagraph.text());

				if (!cleanedText.trim().isEmpty()) {

					cleanedParagraphs.add(eachParagraph);
				}
			}

		}

		return cleanedParagraphs;
	}

	@Override
	public Elements getScripts() {

		Elements scripts = document.getElementsByTag(TAG_SCRIPT);

		return scripts;
	}

	private String removeHTMLEntities(String text) {

		return text.trim().replaceAll("\u00A0", "");
	}

	public Map<String, Element> getSocialMediaLinksMap() {

		Map<String, Element> socialMediaLinksMap = new HashMap<String, Element>();
		String link;

		for (Element element : getAllLinks()) {

			link = element.attr("abs:href");

			if (link.contains(TWITTER)) {

				socialMediaLinksMap.put(TWITTER, element);
			}
			if (link.contains(FACEBOOK)) {

				socialMediaLinksMap.put(FACEBOOK, element);
			}
			if (link.contains(LINKEDIN)) {

				socialMediaLinksMap.put(LINKEDIN, element);
			}
		}

		return socialMediaLinksMap;
	}
}
