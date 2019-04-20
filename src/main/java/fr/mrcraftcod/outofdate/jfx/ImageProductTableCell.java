package fr.mrcraftcod.outofdate.jfx;

import fr.mrcraftcod.outofdate.model.Product;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.net.URL;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * Created by mrcraftcod (MrCraftCod - zerderr@gmail.com) on 2019-04-20.
 *
 * @author Thomas Couchoud
 * @since 2019-04-20
 */
public class ImageProductTableCell extends ProductTableCell<URL>{
	private final ImageView imageView;
	private URL lastUrl;
	
	public ImageProductTableCell(final Consumer<Product> onProductEdit){
		super(onProductEdit);
		this.imageView = new ImageView();
		this.imageView.setFitHeight(50);
		this.imageView.setFitWidth(50);
		this.imageView.setCache(true);
	}
	
	@Override
	protected void updateItem(final URL item, final boolean empty){
		super.updateItem(item, empty);
		if(item == null || empty){
			setGraphic(null);
			setText(null);
		}
		else{
			setText(null);
			setGraphic(this.imageView);
			if(!Objects.equals(lastUrl, item)){
				lastUrl = item;
				this.imageView.setImage(new Image(item.toString()));
			}
		}
	}
}
