package fr.mrcraftcod.outofdate.jfx.table.products;

import fr.mrcraftcod.outofdate.jfx.EditProductView;
import fr.mrcraftcod.outofdate.jfx.MainController;
import fr.mrcraftcod.outofdate.jfx.table.cells.ImageProductTableCell;
import fr.mrcraftcod.outofdate.jfx.table.cells.ProductTableCell;
import fr.mrcraftcod.outofdate.jfx.utils.LangUtils;
import fr.mrcraftcod.outofdate.model.Product;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.net.URL;
import java.util.function.Consumer;

/**
 * Created by mrcraftcod (MrCraftCod - zerderr@gmail.com) on 2019-04-20.
 *
 * @author Thomas Couchoud
 * @since 2019-04-20
 */
public class ProductTableView extends TableView<Product>{
	private static final Logger LOGGER = LoggerFactory.getLogger(ProductTableView.class);
	private final MainController controller;
	
	public ProductTableView(final Stage parentStage, final MainController controller){
		super();
		this.controller = controller;
		this.setSortPolicy(cell -> false);
		
		final Consumer<Product> onProductEdit = product -> new EditProductView(parentStage, product);
		
		final var columnID = new TableColumn<Product, String>(LangUtils.getString("product_table_column_id"));
		columnID.setCellValueFactory(obj -> new SimpleStringProperty(obj.getValue().getID()));
		columnID.setCellFactory(cb -> new ProductTableCell<>(onProductEdit));
		
		final var columnName = new TableColumn<Product, String>(LangUtils.getString("product_table_column_name"));
		columnName.setCellValueFactory(obj -> new SimpleStringProperty(obj.getValue().getName()));
		columnName.setCellFactory(cb -> new ProductTableCell<>(onProductEdit));
		
		final var columnPicture = new TableColumn<Product, URL>(LangUtils.getString("product_table_column_picture"));
		columnPicture.setCellValueFactory(obj -> new SimpleObjectProperty<>(obj.getValue().getImage()));
		columnPicture.setCellFactory(cb -> new ImageProductTableCell(onProductEdit));
		
		final var columnDaysLeft = new TableColumn<Product, Number>(LangUtils.getString("product_table_column_days_left"));
		columnDaysLeft.setCellValueFactory(obj -> obj.getValue().daysLeftProperty());
		columnDaysLeft.setCellFactory(cb -> new ProductTableCell<>(onProductEdit));
		
		final var columnOpen = new TableColumn<Product, Boolean>(LangUtils.getString("product_table_column_open"));
		columnOpen.setCellValueFactory(obj -> obj.getValue().isOpenProperty());
		columnOpen.setCellFactory(cb -> new ProductTableCell<>(onProductEdit));
		
		final var columnSubCount = new TableColumn<Product, Number>(LangUtils.getString("product_table_column_sub_count"));
		columnSubCount.setCellValueFactory(obj -> obj.getValue().subCountProperty());
		columnSubCount.setCellFactory(cb -> new ProductTableCell<>(onProductEdit));
		
		this.getColumns().addAll(columnID, columnName, columnPicture, columnDaysLeft, columnOpen, columnSubCount);
		
		this.setItems(createList());
	}
	
	private ObservableList<Product> createList(){
		final var filteredList = controller.getProducts().filtered(p -> !p.isConsumed());
		final var sortedList = filteredList.sorted();
		//TODO
		return sortedList;
	}
}
