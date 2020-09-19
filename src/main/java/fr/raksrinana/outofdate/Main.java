package fr.raksrinana.outofdate;

import fr.raksrinana.outofdate.jfx.MainApplication;
import lombok.extern.slf4j.Slf4j;
import java.io.IOException;
import java.util.Properties;

@Slf4j
public class Main{
	/**
	 * Main method.
	 *
	 * @param args The arguments of the program:
	 */
	public static void main(final String[] args){
		MainApplication.main(args);
	}
	
	/**
	 * Get the program version from maven.
	 *
	 * @return The program version.
	 */
	public static String getVersion(){
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
