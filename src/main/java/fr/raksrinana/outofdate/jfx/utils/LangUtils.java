package fr.raksrinana.outofdate.jfx.utils;

import java.util.Locale;
import java.util.ResourceBundle;

public class LangUtils{
	private static ResourceBundle bundle;
	
	public static String getString(final String key){
		return getBundle().getString(key);
	}
	
	private static ResourceBundle getBundle(){
		if(bundle == null){
			bundle = ResourceBundle.getBundle("jfx/lang/strings", new Locale("en", "EN"));
		}
		return bundle;
	}
}
