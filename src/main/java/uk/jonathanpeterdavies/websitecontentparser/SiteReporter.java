package uk.jonathanpeterdavies.websitecontentparser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SiteReporter implements ISiteReporter {

	private Map<String, String> practiceNameUrlMap;
	private List<SiteStats> siteStatsList;

	public SiteReporter(Map<String, String> practiceNameUrlMap) {

		this.practiceNameUrlMap = practiceNameUrlMap;
	}

	public void analyseSites() {

		siteStatsList = new ArrayList<SiteStats>();

		Set<String> practiceNames = practiceNameUrlMap.keySet();

		SiteStats stats;
		IWebsiteAnalyser analyser;

		for (String practiceName : practiceNames) {

			analyser = new WebsiteAnalyser(practiceNameUrlMap.get(practiceName), practiceName);

			analyser.analyseSite();
			stats = analyser.getSiteStats();
			siteStatsList.add(stats);
			//displayStats(stats);
		}
	}

	private void displayStats(SiteStats stats) {

		System.out.println("Base url: " + stats.getBaseUrl());
		System.out.println("Practice name: " + stats.getPracticeName());
		System.out.println("Home page complexity: " + stats.getComplexity());
		System.out.println("Google script found: " + stats.isGoogleScripted());
		System.out.println("References ARB: " + stats.getReferencesARB());
		System.out.println("References architects: " + stats.getReferencesArchitects());
		System.out.println("References cost: " + stats.getReferencesCost());
		System.out.println("References practice name: " + stats.getReferencesPracticeName());
		System.out.println("References RIBA: " + stats.getReferencesRIBA());
		System.out.println("Number of main pages: " + stats.getNumberMainPages());
		System.out.println("Number of social media links: " + stats.getNumberSocialMediaLinks());
		System.out.println();
	}

	public void reportSiteStatistics() {

		AverageSiteStats averages = getAverageSiteStats();

		System.out.println("REPORT");
		System.out.println();
		System.out.println("Number of sites analysed: " + siteStatsList.size());
		System.out.println("Average home page complexity: " + averages.getAverageComplexity());
		System.out.println("Average number main pages: " + averages.getAverageNumberMainPages());
		System.out.println("Average number of social media links: " + averages.getAverageNumberSocialMediaLinks());
		System.out.println("Average number of references ARB: " + averages.getAverageReferencesARB());
		System.out.println("Average number of references Architects: " + averages.getAverageReferencesArchitects());
		System.out.println("Average number of references cost: " + averages.getAverageReferencesCost());
		System.out.println("Average number of references of the practice name: " + averages.getAverageReferencesPracticeName());
		System.out.println("Average number of references RIBA: " + averages.getAverageReferencesRIBA());
		System.out.println("Average google scripted: " + averages.isAverageGoogleScripted());
	}

	private AverageSiteStats getAverageSiteStats() {

		AverageSiteStats averages = new AverageSiteStats();

		int totalComplexity = 0;
		int totalMainPages = 0;
		int totalSocialMediaLinks = 0;
		int totalRefARB = 0;
		int totalRefArchitects = 0;
		int totalRefCost = 0;
		int totalRefPracticeName = 0;
		int totalRefRIBA = 0;
		int totalOccurencesIsGoogleScripted = 0;

		for (SiteStats stats : siteStatsList) {

			totalComplexity += stats.getComplexity();
			totalMainPages += stats.getNumberMainPages();
			totalSocialMediaLinks += stats.getNumberSocialMediaLinks();
			totalRefARB += stats.getReferencesARB();
			totalRefArchitects += stats.getReferencesArchitects();
			totalRefCost += stats.getReferencesCost();
			totalRefPracticeName += stats.getReferencesPracticeName();
			totalRefRIBA += stats.getReferencesRIBA();

			if (stats.isGoogleScripted()) {

				totalOccurencesIsGoogleScripted ++;
			}
		}

		int listSize = siteStatsList.size();

		boolean averageGoogleScripted = false;

		if (totalOccurencesIsGoogleScripted > listSize / 2){

			averageGoogleScripted = true;
		}

		averages.setAverageComplexity(totalComplexity / listSize);
		averages.setAverageGoogleScripted(averageGoogleScripted);
		averages.setAverageNumberMainPages(totalMainPages / listSize);
		averages.setAverageNumberSocialMediaLinks(totalSocialMediaLinks / listSize);
		averages.setAverageReferencesARB(totalRefARB / listSize);
		averages.setAverageReferencesArchitects(totalRefArchitects / listSize);
		averages.setAverageReferencesCost(totalRefCost / listSize);
		averages.setAverageReferencesPracticeName(totalRefPracticeName / listSize);
		averages.setAverageReferencesRIBA(totalRefRIBA / listSize);

		return averages;
	}
}
