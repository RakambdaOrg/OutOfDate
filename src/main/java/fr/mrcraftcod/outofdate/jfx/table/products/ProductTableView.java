package fr.mrcraftcod.outofdate.jfx.table.products;

import fr.mrcraftcod.outofdate.jfx.EditProductView;
import fr.mrcraftcod.outofdate.jfx.MainController;
import fr.mrcraftcod.outofdate.jfx.table.cells.ImageProductTableCell;
import fr.mrcraftcod.outofdate.jfx.table.cells.ProductTableCell;
import fr.mrcraftcod.outofdate.jfx.utils.LangUtils;
import fr.mrcraftcod.outofdate.model.OwnedProduct;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.net.URL;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * Created by mrcraftcod (MrCraftCod - zerderr@gmail.com) on 2019-04-20.
 *
 * @author Thomas Couchoud
 * @since 2019-04-20
 */
public class ProductTableView extends TableView<OwnedProduct>{
	private final MainController controller;
	
	public MainController getController(){
		return controller;
	}
	
	public ProductTableView(final Stage parentStage, final MainController controller){
		super();
		this.controller = controller;
		this.setSortPolicy(cell -> true);
		final var columnID = new TableColumn<OwnedProduct, String>(LangUtils.getString("product_table_column_id"));
		columnID.setCellValueFactory(obj -> new SimpleStringProperty(obj.getValue().getProduct().getId()));
		columnID.setCellFactory(cb -> new ProductTableCell<>(this.getOnProductEdit(parentStage)));
		final var columnName = new TableColumn<OwnedProduct, String>(LangUtils.getString("product_table_column_name"));
		columnName.setCellValueFactory(obj -> obj.getValue().getProduct().nameProperty());
		columnName.setCellFactory(cb -> new ProductTableCell<>(this.getOnProductEdit(parentStage)));
		final var columnPicture = new TableColumn<OwnedProduct, URL>(LangUtils.getString("product_table_column_picture"));
		columnPicture.setCellValueFactory(obj -> obj.getValue().getProduct().imageProperty());
		columnPicture.setCellFactory(cb -> new ImageProductTableCell(this.getOnProductEdit(parentStage)));
		final var columnDaysLeft = new TableColumn<OwnedProduct, Number>(LangUtils.getString("product_table_column_days_left"));
		columnDaysLeft.setCellValueFactory(obj -> obj.getValue().daysLeftProperty());
		columnDaysLeft.setCellFactory(cb -> new ProductTableCell<>(this.getOnProductEdit(parentStage)));
		final var columnOpen = new TableColumn<OwnedProduct, Boolean>(LangUtils.getString("product_table_column_open"));
		columnOpen.setCellValueFactory(obj -> obj.getValue().isOpenProperty());
		columnOpen.setCellFactory(cb -> new ProductTableCell<>(this.getOnProductEdit(parentStage)));
		final var columnSubCount = new TableColumn<OwnedProduct, Number>(LangUtils.getString("product_table_column_sub_count"));
		columnSubCount.setCellValueFactory(obj -> obj.getValue().subCountProperty());
		columnSubCount.setCellFactory(cb -> new ProductTableCell<>(this.getOnProductEdit(parentStage)));
		final var columnNutriscore = new TableColumn<OwnedProduct, String>(LangUtils.getString("product_table_column_nutriscore"));
		columnNutriscore.setCellValueFactory(obj -> obj.getValue().getProduct().nutriscoreProperty());
		columnNutriscore.setCellFactory(cb -> new ProductTableCell<>(this.getOnProductEdit(parentStage)));
		this.getColumns().addAll(columnID, columnName, columnPicture, columnDaysLeft, columnOpen, columnSubCount, columnNutriscore);
		this.setItems(createList());
		this.addEventHandler(MouseEvent.MOUSE_CLICKED, t -> {
			final OwnedProduct selectedItem = ProductTableView.this.getSelectionModel().getSelectedItem();
			if(Objects.nonNull(selectedItem)){
				if(t.getButton() == MouseButton.SECONDARY){
					ContextMenu cm = new ContextMenu();
					MenuItem menuDuplicate = new MenuItem(LangUtils.getString("product_table_menu_duplicate"));
					menuDuplicate.setOnAction(evt -> controller.duplicateOwnedProduct(selectedItem));
					cm.getItems().add(menuDuplicate);
					MenuItem menuDelete = new MenuItem(LangUtils.getString("product_table_menu_delete"));
					menuDelete.setOnAction(evt -> controller.removeOwnedProduct(selectedItem));
					cm.getItems().add(menuDelete);
					cm.show(ProductTableView.this, t.getScreenX(), t.getScreenY());
				}
			}
		});
		this.setOnKeyPressed(keyEvent -> {
			final OwnedProduct selectedItem = ProductTableView.this.getSelectionModel().getSelectedItem();
			if(Objects.nonNull(selectedItem)){
				if(keyEvent.getCode().equals(KeyCode.DELETE)){
					controller.removeOwnedProduct(selectedItem);
				}
				else if(keyEvent.isControlDown() && keyEvent.getCode().equals(KeyCode.D)){
					controller.duplicateOwnedProduct(selectedItem);
				}
			}
		});
		this.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
	}
	
	protected Consumer<OwnedProduct> getOnProductEdit(final Stage parentStage){
		return product -> new EditProductView(parentStage, product);
	}
	
	protected ObservableList<OwnedProduct> createList(){
		final var filteredList = getController().getOwnedProducts().filtered(p -> !p.getIsConsumed());
		final var sortedList = filteredList.sorted();
		//TODO
		return sortedList;
	}
}
