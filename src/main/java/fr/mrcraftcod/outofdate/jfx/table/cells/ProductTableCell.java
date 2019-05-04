package fr.mrcraftcod.outofdate.jfx.table.cells;

import fr.mrcraftcod.outofdate.model.OwnedProduct;
import javafx.geometry.Pos;
import javafx.scene.control.TableCell;
import javafx.scene.input.MouseButton;
import javafx.scene.text.TextAlignment;
import java.util.function.Consumer;

/**
 * Created by mrcraftcod (MrCraftCod - zerderr@gmail.com) on 2019-04-20.
 *
 * @author Thomas Couchoud
 * @since 2019-04-20
 */
public class ProductTableCell<T> extends TableCell<OwnedProduct, T>{
	public ProductTableCell(final Consumer<OwnedProduct> onEditProduct){
		this.setAlignment(Pos.CENTER);
		this.setTextAlignment(TextAlignment.CENTER);
		this.setWrapText(true);
		this.setOnMouseClicked(evt -> {
			if(evt.getClickCount() == 2 && evt.getButton() == MouseButton.PRIMARY){
				onEditProduct.accept(getOwnedProduct());
			}
		});
	}
	
	@SuppressWarnings("Duplicates")
	public OwnedProduct getOwnedProduct(){
		if(this.getTableView().getItems().size() > this.getTableRow().getIndex() && this.getTableRow().getIndex() >= 0){
			return this.getTableView().getItems().get(this.getTableRow().getIndex());
		}
		return null;
	}
	
	@Override
	protected void updateItem(final T item, final boolean empty){
		super.updateItem(item, empty);
		if(item == null || empty){
			setText(null);
		}
		else{
			setText(item.toString());
		}
	}
}
