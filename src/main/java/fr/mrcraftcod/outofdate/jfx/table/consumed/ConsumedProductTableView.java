package fr.mrcraftcod.outofdate.jfx.table.consumed;

import fr.mrcraftcod.outofdate.jfx.MainController;
import fr.mrcraftcod.outofdate.jfx.table.products.ProductTableView;
import fr.mrcraftcod.outofdate.model.Product;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by mrcraftcod (MrCraftCod - zerderr@gmail.com) on 2019-04-20.
 *
 * @author Thomas Couchoud
 * @since 2019-04-20
 */
public class ConsumedProductTableView extends ProductTableView{
	private static final Logger LOGGER = LoggerFactory.getLogger(ConsumedProductTableView.class);
	
	public ConsumedProductTableView(final Stage parentStage, final MainController controller){
		super(parentStage, controller);
	}
	
	@Override
	protected ObservableList<Product> createList(){
		final var list = getController().getProducts().filtered(Product::isConsumed).sorted();
		//TODO
		return list;
	}
}
