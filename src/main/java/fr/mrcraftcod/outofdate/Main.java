package fr.mrcraftcod.outofdate;

import fr.mrcraftcod.outofdate.jfx.MainApplication;
import javafx.application.Application;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.util.Properties;

/**
 * Main class.
 * <p>
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 19/12/2016.
 *
 * @author Thomas Couchoud
 * @since 2016-12-19
 */
public class Main{
	private static final Logger log = LoggerFactory.getLogger(Main.class);
	
	/**
	 * Main method.
	 *
	 * @param args The arguments of the program:
	 */
	public static void main(final String[] args){
		Application.launch(MainApplication.class, args);
	}
	
	/**
	 * Get the program version from maven.
	 *
	 * @return The program version.
	 */
	public static String getMavenVersion(){
		final Properties properties = new Properties();
		try{
			properties.load(Main.class.getResource("/version.properties").openStream());
		}
		catch(final IOException e){
			log.warn("Error reading version from maven", e);
		}
		return properties.getProperty("version", "Unknown");
	}
}
