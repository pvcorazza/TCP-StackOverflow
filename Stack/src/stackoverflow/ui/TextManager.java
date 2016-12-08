package stackoverflow.ui;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;



public class TextManager {
	
	public static final Locale LANGUAGE[] = { new Locale("en", "US"),
			new Locale("pt", "BR") };

	public static final int LANGUAGE_ENGLISH = 0;
	public static final int LANGUAGE_PORTUGUESE = 1;
	
	private String baseName;
	private ResourceBundle bundle;
	private ResourceBundle customBundle;
	private String customName;
	private Locale locale;
	
	public TextManager(String baseName) {
		this(baseName, Locale.getDefault());
	}

	public TextManager(String baseName, Integer language) {
		this(baseName, LANGUAGE[language]);
	}

	public TextManager(String baseName, Locale locale) {
		this.locale = locale;
		setBaseName(baseName);
		setCustomName(null);
	}
	
	/**
	 * @return the baseName
	 */
	public String getBaseName() {
		return baseName;
	}
	
	/**
	 * @param baseName
	 *            the baseName to set
	 */
	public void setBaseName(String baseName) {
		this.baseName = baseName;
		bundle = ResourceBundle.getBundle(baseName, locale);
	}
	
	/**
	 * @param customName
	 *            the customName to set
	 */
	public void setCustomName(String customName) {
		this.customName = customName;
		if (customName == null) {
			customBundle = null;
		} else {
			customBundle = ResourceBundle.getBundle(customName, locale);
		}
	}
	
	public String getText(String key) {
		String text = null;
		if (customBundle != null) {
			try {
				text = customBundle.getString(key);
			} catch (MissingResourceException exc) {
				text = null;
			}
		}
		if (text == null) {
			try {
				text = bundle.getString(key);
			} catch (MissingResourceException exc) {
				text = key;
			}
		}
		return text;
	}

}
