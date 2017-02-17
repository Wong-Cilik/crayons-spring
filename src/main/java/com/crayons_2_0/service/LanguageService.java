package com.crayons_2_0.service;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Class for multilingual service
 *
 */
public class LanguageService {

	private static final LanguageService languageControl = new LanguageService();

	private Locale currentLocale;

	private LanguageService() {

		this.setCurrentLocale(new Locale("de", "de"));
		// this.setCurrentLocale(new Locale("en", "en"));
	}

	/**
	 * Returns the Singleton LanguageControl
	 * 
	 * @return
	 */
	public static LanguageService getInstance() {
		return languageControl;
	}

	/**
	 * Returns the RessourceBundle for the current Locale
	 * 
	 * @return RessourceBundle for the current Locale
	 */
	public ResourceBundle getRes() {
		return ResourceBundle.getBundle("com.crayons_2_0.language.Language",
				currentLocale);
	}

	/**
	 * @return the currentLocale
	 */
	public Locale getCurrentLocale() {
		return currentLocale;
	}

	/**
	 * @param currentLocale
	 *            the currentLocale to set
	 */
	public void setCurrentLocale(Locale currentLocale) {
		this.currentLocale = currentLocale;
	}

	/**
	 * set the current local
	 * 
	 * @param newLanguage
	 *            new Language to set
	 */
	public void setCurrentLocale(Language newLanguage) {

		if (newLanguage.equals(Language.German)) {
			this.setCurrentLocale(new Locale("de", "de"));
		} else if (newLanguage.equals(Language.English)) {
			this.setCurrentLocale(new Locale("en", "en"));
		}

	}

	/**
	 * returns the LanguageEnum of the current language
	 * 
	 * @return the LanguageEnum of the current language, null if wrong
	 *         imlemented
	 */
	public Language getLanguage() {
		if (currentLocale.equals(new Locale("de", "de"))) {
			return Language.German;
		} else if (currentLocale.equals(new Locale("en", "en"))) {
			return Language.English;
		}

		return null;
	}

}
