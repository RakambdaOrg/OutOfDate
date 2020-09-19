package fr.raksrinana.outofdate.jfx.table.consumed;

import fr.raksrinana.outofdate.jfx.MainController;
import fr.raksrinana.outofdate.jfx.table.cells.ProductTableCell;
import fr.raksrinana.outofdate.jfx.table.products.ProductTableView;
import fr.raksrinana.outofdate.jfx.utils.LangUtils;
import fr.raksrinana.outofdate.model.OwnedProduct;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.stage.Stage;
import java.time.LocalDate;

public class ConsumedProductTableView extends ProductTableView{
	public ConsumedProductTableView(final Stage parentStage, final MainController controller){
		super(parentStage, controller);
		
		final var columnConsumedOn = new TableColumn<OwnedProduct, LocalDate>(LangUtils.getString("product_table_column_consumed_on"));
		columnConsumedOn.setCellValueFactory(obj -> obj.getValue().consumedOnProperty());
		columnConsumedOn.setCellFactory(cb -> new ProductTableCell<>(this.getOnProductEdit(parentStage)));
		
		this.getColumns().addAll(columnConsumedOn);
	}
	
	@Override
	protected ObservableList<OwnedProduct> createList(){
		final var list = getController().getOwnedProducts().filtered(OwnedProduct::getIsConsumed).sorted();
		//TODO
		return list;
	}
}
