package uk.jonathanpeterdavies.websitecontentparser;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class App {

	public static void main(String[] args) {

		ISiteReporter siteReporter = new SiteReporter(getPracticeNameUrlMap());

		siteReporter.analyseSites();
		siteReporter.reportSiteStatistics();
	}

	private static Map<String, String> getPracticeNameUrlMap() {

		Map<String, String> practiceNameUrlMap = new HashMap<String, String>();

		practiceNameUrlMap.put("AHMM", "http://www.ahmm.co.uk/");
		practiceNameUrlMap.put("Alison Brooks Architects", "http://www.alisonbrooksarchitects.com/");
//		practiceNameUrlMap.put("BDP", "http://www.bdp.com/");
		practiceNameUrlMap.put("Hodder", "http://www.hodderandpartners.com/");
		practiceNameUrlMap.put("SWA", "http://www.swarch.co.uk/");

		return practiceNameUrlMap;
	}
}
