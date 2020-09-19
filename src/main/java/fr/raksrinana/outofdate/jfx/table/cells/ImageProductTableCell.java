package fr.raksrinana.outofdate.jfx.table.cells;

import fr.raksrinana.outofdate.jfx.utils.ImageCache;
import fr.raksrinana.outofdate.model.OwnedProduct;
import javafx.scene.image.ImageView;
import java.net.URL;
import java.util.Objects;
import java.util.function.Consumer;

public class ImageProductTableCell extends ProductTableCell<URL>{
	private final ImageView imageView;
	private URL lastUrl;
	
	public ImageProductTableCell(final Consumer<OwnedProduct> onProductEdit){
		super(onProductEdit);
		this.imageView = new ImageView();
		this.imageView.setFitHeight(50);
		this.imageView.setFitWidth(50);
		this.imageView.setCache(true);
		this.setGraphic(this.imageView);
	}
	
	@Override
	protected void updateItem(final URL item, final boolean empty){
		//super.updateItem(item, empty);
		if(item == null || empty){
			this.setGraphic(null);
			this.setText(null);
		}
		else{
			this.setText(null);
			this.setGraphic(this.imageView);
			if(!Objects.equals(this.lastUrl, item)){
				this.lastUrl = item;
				this.imageView.setImage(ImageCache.fetchImage(item));
			}
		}
	}
}
