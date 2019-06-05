package fr.mrcraftcod.outofdate.jfx.table.consumed;

import fr.mrcraftcod.outofdate.jfx.MainController;
import fr.mrcraftcod.outofdate.jfx.utils.LangUtils;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.stage.Stage;

/**
 * Created by mrcraftcod (MrCraftCod - zerderr@gmail.com) on 2019-04-20.
 *
 * @author Thomas Couchoud
 * @since 2019-04-20
 */
public class ConsumedProductsTab extends Tab{
	private final MainController controller;
	
	public MainController getController(){
		return controller;
	}
	
	public ConsumedProductsTab(final Stage parentStage, final MainController controller){
		super(LangUtils.getString("consumed_products_tab_name"));
		this.controller = controller;
		this.setContent(createContent(parentStage));
		this.setClosable(false);
	}
	
	private Node createContent(final Stage parentStage){
		final var productsTable = new ConsumedProductTableView(parentStage, this.getController());
		productsTable.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		return productsTable;
	}
}
