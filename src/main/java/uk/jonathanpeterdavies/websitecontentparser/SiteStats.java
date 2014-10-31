package uk.jonathanpeterdavies.websitecontentparser;

public class SiteStats {

	private String practiceName;
	private String baseUrl;
	private int complexity;
	private int referencesRIBA;
	private int referencesARB;
	private int referencesArchitects;
	private int referencesPracticeName;
	private int referencesCost;
	private boolean googleScripted;
	private int numberMainPages;
	private int numberSocialMediaLinks;

	public SiteStats() {

	}

	public String getPracticeName() {
		return practiceName;
	}

	public void setPracticeName(String practiceName) {
		this.practiceName = practiceName;
	}

	public String getBaseUrl() {
		return baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	public int getComplexity() {
		return complexity;
	}

	public void setComplexity(int complexity) {
		this.complexity = complexity;
	}

	public int getReferencesRIBA() {
		return referencesRIBA;
	}

	public void setReferencesRIBA(int referencesRIBA) {
		this.referencesRIBA = referencesRIBA;
	}

	public int getReferencesARB() {
		return referencesARB;
	}

	public void setReferencesARB(int referencesARB) {
		this.referencesARB = referencesARB;
	}

	public int getReferencesArchitects() {
		return referencesArchitects;
	}

	public void setReferencesArchitects(int referencesArchitects) {
		this.referencesArchitects = referencesArchitects;
	}

	public int getReferencesPracticeName() {
		return referencesPracticeName;
	}

	public void setReferencesPracticeName(int referencesPracticeName) {
		this.referencesPracticeName = referencesPracticeName;
	}

	public int getReferencesCost() {
		return referencesCost;
	}

	public void setReferencesCost(int referencesCost) {
		this.referencesCost = referencesCost;
	}

	public boolean isGoogleScripted() {
		return googleScripted;
	}

	public void setGoogleScripted(boolean googleScripted) {
		this.googleScripted = googleScripted;
	}

	public int getNumberMainPages() {
		return numberMainPages;
	}

	public void setNumberMainPages(int numberMainPages) {
		this.numberMainPages = numberMainPages;
	}

	public int getNumberSocialMediaLinks() {
		return numberSocialMediaLinks;
	}

	public void setNumberSocialMediaLinks(int numberSocialMediaLinks) {
		this.numberSocialMediaLinks = numberSocialMediaLinks;
	}

	
}
