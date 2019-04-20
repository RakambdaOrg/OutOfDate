package fr.mrcraftcod.outofdate;

import fr.mrcraftcod.outofdate.jfx.MainApplication;
import javafx.application.Application;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Main class.
 * <p>
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 19/12/2016.
 *
 * @author Thomas Couchoud
 * @since 2016-12-19
 */
public class Main{
	private final static Logger LOGGER = LoggerFactory.getLogger(Main.class);
	
	/**
	 * Main method.
	 *
	 * @param args The arguments of the program:
	 */
	public static void main(final String[] args){
		Application.launch(MainApplication.class, args);
	}
}
