package fr.mrcraftcod.outofdate.jfx.utils;

import javafx.scene.image.Image;
import java.lang.ref.WeakReference;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by mrcraftcod (MrCraftCod - zerderr@gmail.com) on 2019-04-27.
 *
 * @author Thomas Couchoud
 * @since 2019-04-27
 */
public class ImageCache{
	private static final Map<URL, WeakReference<Image>> cache = new ConcurrentHashMap<>();
	
	public static synchronized void remove(final URL identifier){
		cache.remove(identifier);
	}
	
	public static Image fetchImage(final URL identifier){
		var image = getCachedImage(identifier);
		if(image != null){
			return image;
		}
		image = new Image(identifier.toString(), true);
		addToCache(identifier, image);
		return image;
	}
	
	private static Image getCachedImage(final URL imageIdentifier){
		final var ref = cache.get(imageIdentifier);
		if(ref != null){
			return ref.get();
		}
		return null;
	}
	
	private static synchronized void addToCache(final URL identifier, final Image image){
		cache.put(identifier, new WeakReference<>(image));
	}
}