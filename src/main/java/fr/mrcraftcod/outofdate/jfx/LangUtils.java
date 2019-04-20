package fr.mrcraftcod.outofdate.jfx;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by mrcraftcod (MrCraftCod - zerderr@gmail.com) on 2019-04-20.
 *
 * @author Thomas Couchoud
 * @since 2019-04-20
 */
public class LangUtils{
	
	private static ResourceBundle bundle;
	
	public static String getString(final String key){
		return getBundle().getString(key);
	}
	
	private static ResourceBundle getBundle(){
		if(bundle == null){
			bundle = ResourceBundle.getBundle("/jfx/lang/strings", new Locale("en", "EN"));
		}
		return bundle;
	}
}
