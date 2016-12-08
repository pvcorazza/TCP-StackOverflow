package ui.text;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;

import stackoverflow.ui.TextManager;



public class UIUtils {

	public static final UIUtils INSTANCE = new UIUtils();
	public static final String PROPERTY_RESOURCE_BUNDLE = "resources.globalMessages";
	
	private final TextManager textManager;
	

	
	private UIUtils() {
		
		this.textManager = new TextManager(PROPERTY_RESOURCE_BUNDLE);

	}
	
	/**
	 * @return the textManager
	 */
	public TextManager getTextManager() {
		return textManager;
	}
}
