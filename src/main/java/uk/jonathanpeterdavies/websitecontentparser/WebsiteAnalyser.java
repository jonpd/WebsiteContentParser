package uk.jonathanpeterdavies.websitecontentparser;

import java.io.IOException;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebsiteAnalyser implements IWebsiteAnalyser {

	private String baseUrl;
	private SiteStats siteStats;
	private static final String ARB = "ARB";
	private static final String RIBA = "RIBA";
	private static final String RIBA_DOTTED = "R.I.B.A";
	private static final String RCHITECT = "rchitect";
	private static final String POUNDS = "£";

	public WebsiteAnalyser(String baseUrl, String practiceName) {

		this.baseUrl = baseUrl;
		siteStats = new SiteStats();
		siteStats.setBaseUrl(baseUrl);
		siteStats.setPracticeName(practiceName);
	}

	@Override
	public SiteStats getSiteStats() {

		return siteStats;
	}

	@Override
	public void analyseSite() {

		TextContentParser parser = new TextContentParser(baseUrl, baseUrl);
		Elements links = parser.getInternalSiteLinks();
		siteStats.setComplexity(links.size());
		siteStats.setGoogleScripted(isGoogleScripted(parser));

		// base url
		siteStats.setReferencesARB(getNumberOfReferences(parser, ARB));
		siteStats.setReferencesArchitects(getNumberOfReferences(parser, RCHITECT));
		siteStats.setReferencesCost(getNumberOfReferences(parser, POUNDS));
		siteStats.setReferencesPracticeName(getNumberOfReferences(parser, siteStats.getPracticeName()));
		siteStats.setReferencesRIBA(getNumberOfReferences(parser, RIBA) + getNumberOfReferences(parser, RIBA_DOTTED));
		siteStats.setNumberSocialMediaLinks(parser.getSocialMediaLinksMap().size());

		Elements secondLevelLinks = getOnlySecondLevelLinks(links);
		siteStats.setNumberMainPages(secondLevelLinks.size());

		findReferencesInOtherPages(secondLevelLinks);
	}

	private Elements getOnlySecondLevelLinks(Elements allSiteLinks) {

		Elements secondLevelLinks = new Elements();

		String link;

		for (Element eachLink : allSiteLinks) {

			link = eachLink.attr("abs:href");

			if (link.endsWith("/")) {

				link = link.substring(0, link.lastIndexOf("/"));
			}

			if (findNumberOfOccurences(link, "/") < 4) {

				secondLevelLinks.add(eachLink);
			}
		}

		return secondLevelLinks;
	}

	private void findReferencesInOtherPages(Elements links) {

		// visit each page to count references
		for (Element linkElement : links) {

			updateSiteStatsReferences(linkElement);
		}
	}

	private void updateSiteStatsReferences(Element linkElement) {

		String link = linkElement.attr("abs:href");
		TextContentParser parser = new TextContentParser(baseUrl, link);

		siteStats.setReferencesARB(siteStats.getReferencesARB() + getNumberOfReferences(parser, ARB));
		siteStats.setReferencesArchitects(siteStats.getReferencesArchitects() + getNumberOfReferences(parser, RCHITECT));
		siteStats.setReferencesCost(siteStats.getReferencesCost() + getNumberOfReferences(parser, POUNDS));
		siteStats.setReferencesPracticeName(siteStats.getReferencesPracticeName() + getNumberOfReferences(parser, siteStats.getPracticeName()));
		siteStats.setReferencesRIBA(siteStats.getReferencesRIBA() + getNumberOfReferences(parser, RIBA) + getNumberOfReferences(parser, RIBA_DOTTED));
		siteStats.setNumberSocialMediaLinks(parser.getSocialMediaLinksMap().size());
	}

	private int getNumberOfReferences(TextContentParser parser, String reference) {

		int referenceTotal = 0;
		String content;

		Elements allElements = new Elements();
		allElements.addAll(parser.getHeadings());
		allElements.addAll(parser.getInternalSiteLinks());
		allElements.addAll(parser.getParagraphs());

		for (Element element : allElements) {

			content = element.text();

			if (content.contains(reference)) {

				referenceTotal += findNumberOfOccurences(content, reference);
			}
		}

		return referenceTotal;
	}

	private int findNumberOfOccurences(String content, String reference) {

		int lastIndex = 0;
		int count = 0;

		while (lastIndex != -1) {

			lastIndex = content.indexOf(reference, lastIndex);

			if (lastIndex != -1) {
				count++;
				// increment last found index by length of reference
				lastIndex += reference.length();
			}
		}

		return count;
	}

	private boolean isGoogleScripted(TextContentParser baseUrlParser) {

		Elements scripts = baseUrlParser.getScripts();

		String innerHtml;

		for (Element script : scripts) {

			innerHtml = script.html();

			if (innerHtml.contains("google")) {

				return true;
			}
		}

		return false;
	}

}
