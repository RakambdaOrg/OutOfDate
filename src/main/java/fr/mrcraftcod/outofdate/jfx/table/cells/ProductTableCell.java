package fr.mrcraftcod.outofdate.jfx.table.cells;

import fr.mrcraftcod.outofdate.model.OwnedProduct;
import fr.mrcraftcod.outofdate.model.ProductState;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Pos;
import javafx.scene.control.TableCell;
import javafx.scene.input.MouseButton;
import javafx.scene.text.TextAlignment;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * Created by mrcraftcod (MrCraftCod - zerderr@gmail.com) on 2019-04-20.
 *
 * @author Thomas Couchoud
 * @since 2019-04-20
 */
public class ProductTableCell<T> extends TableCell<OwnedProduct, T>{
	private OwnedProduct previousItem;
	private ChangeListener<ProductState> stateChangeListener;
	
	public ProductTableCell(final Consumer<OwnedProduct> onEditProduct){
		this.previousItem = null;
		this.setAlignment(Pos.CENTER);
		this.setTextAlignment(TextAlignment.CENTER);
		this.setWrapText(true);
		this.setOnMouseClicked(evt -> {
			if(evt.getClickCount() == 2 && evt.getButton() == MouseButton.PRIMARY){
				onEditProduct.accept(getOwnedProduct());
			}
		});
		this.stateChangeListener = (obs, oldState, newState) -> this.applyStyle(newState);
	}
	
	@SuppressWarnings("Duplicates")
	public OwnedProduct getOwnedProduct(){
		if(Objects.nonNull(this.getTableView()) && Objects.nonNull(this.getTableRow()) && this.getTableView().getItems().size() > this.getTableRow().getIndex() && this.getTableRow().getIndex() >= 0){
			return this.getTableView().getItems().get(this.getTableRow().getIndex());
		}
		return null;
	}
	
	private void applyStyle(ProductState state){
		ProductTableCell.this.getStyleClass().remove("product_expired");
		ProductTableCell.this.getStyleClass().remove("product_opened");
		if(state == ProductState.EXPIRED || state == ProductState.OPENED_EXPIRED){
			this.getStyleClass().add("product_expired");
		}
		else if(state == ProductState.OPENED){
			this.getStyleClass().add("product_opened");
		}
		else if(state == ProductState.EATEN){
			this.getStyleClass().add("product_eaten");
		}
	}
	
	@Override
	public void updateIndex(int i){
		super.updateIndex(i);
		ProductTableCell.this.getStyleClass().remove("product_expired");
		ProductTableCell.this.getStyleClass().remove("product_opened");
		if(Objects.nonNull(previousItem)){
			previousItem.stateProperty().removeListener(stateChangeListener);
		}
		Optional.ofNullable(getOwnedProduct()).ifPresent(product -> {
			product.stateProperty().addListener(stateChangeListener);
			applyStyle(product.getState());
		});
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
