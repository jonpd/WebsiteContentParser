package uk.jonathanpeterdavies.websitecontentparser;

import java.util.Map;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public interface ITextContentParser {

	Elements getHeadings();

	Elements getInternalSiteLinks();

	Elements getParagraphs();

	Elements getScripts();

	Map<String, Element> getSocialMediaLinksMap();
}
