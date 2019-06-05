package fr.mrcraftcod.outofdate.jfx.table.consumed;

import fr.mrcraftcod.outofdate.jfx.MainController;
import fr.mrcraftcod.outofdate.jfx.table.cells.ProductTableCell;
import fr.mrcraftcod.outofdate.jfx.table.products.ProductTableView;
import fr.mrcraftcod.outofdate.jfx.utils.LangUtils;
import fr.mrcraftcod.outofdate.model.OwnedProduct;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.stage.Stage;
import java.time.LocalDate;

/**
 * Created by mrcraftcod (MrCraftCod - zerderr@gmail.com) on 2019-04-20.
 *
 * @author Thomas Couchoud
 * @since 2019-04-20
 */
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
