package fr.mrcraftcod.outofdate.jfx.table.products;

import fr.mrcraftcod.outofdate.jfx.MainController;
import fr.mrcraftcod.outofdate.jfx.utils.AutoCompleteTextInputDialog;
import fr.mrcraftcod.outofdate.jfx.utils.LangUtils;
import fr.mrcraftcod.outofdate.utils.OpenFoodFacts;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Created by mrcraftcod (MrCraftCod - zerderr@gmail.com) on 2019-04-20.
 *
 * @author Thomas Couchoud
 * @since 2019-04-20
 */
public class ProductsTab extends Tab{
	private final MainController controller;
	
	public ProductsTab(final Stage parentStage, final MainController controller){
		super(LangUtils.getString("products_tab_name"));
		this.controller = controller;
		this.setContent(createContent(parentStage));
		this.setClosable(false);
	}
	
	private Node createContent(final Stage parentStage){
		final var root = new VBox();
		
		final var productsTable = new ProductTableView(parentStage, this.controller);
		productsTable.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		
		final var addProductButton = new Button(LangUtils.getString("products_tab_add_product_button"));
		addProductButton.setMaxWidth(Double.MAX_VALUE);
		addProductButton.setOnAction(evt -> this.addProduct());
		
		final var bottomButtons = new HBox();
		bottomButtons.getChildren().addAll(addProductButton);
		HBox.setHgrow(addProductButton, Priority.ALWAYS);
		
		VBox.setVgrow(productsTable, Priority.ALWAYS);
		root.getChildren().addAll(productsTable, bottomButtons);
		return root;
	}
	
	public void addProduct(){
		final var dialog = new AutoCompleteTextInputDialog(controller.getProductsHints(), "");
		dialog.setTitle(LangUtils.getString("products_tab_add_product_dialog_title"));
		dialog.setHeaderText(LangUtils.getString("products_tab_add_product_dialog_header"));
		dialog.setContentText(LangUtils.getString("products_tab_add_product_dialog_content"));
		
		final var result = dialog.showAndWait();
		result.ifPresent(s -> {
			var id = result.get();
			final var toldIndex = id.indexOf(controller.getProductHintSeparator());
			if(toldIndex > 0){
				id = id.substring(0, toldIndex);
			}
			OpenFoodFacts.getProduct(id).ifPresent(controller::addProduct);
		});
	}
}
