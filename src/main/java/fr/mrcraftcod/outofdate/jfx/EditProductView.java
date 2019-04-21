package fr.mrcraftcod.outofdate.jfx;

import fr.mrcraftcod.outofdate.jfx.utils.LangUtils;
import fr.mrcraftcod.outofdate.jfx.utils.NumberTextField;
import fr.mrcraftcod.outofdate.model.Product;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Created by mrcraftcod (MrCraftCod - zerderr@gmail.com) on 2019-04-20.
 *
 * @author Thomas Couchoud
 * @since 2019-04-20
 */
public class EditProductView{
	private final Stage dialog;
	
	public EditProductView(final Stage parentStage, final Product product){
		dialog = new Stage();
		
		final var scene = buildScene(dialog, product);
		dialog.setTitle(LangUtils.getString("edit_product_title"));
		dialog.setScene(scene);
		dialog.sizeToScene();
		
		dialog.initOwner(parentStage);
		dialog.initModality(Modality.APPLICATION_MODAL);
		dialog.showAndWait();
	}
	
	private Scene buildScene(final Stage stage, final Product product){
		return new Scene(buildContent(stage, product));
	}
	
	private Parent buildContent(final Stage stage, final Product product){
		final var root = new VBox(3);
		
		final var datePicker = new DatePicker();
		datePicker.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		datePicker.setOnAction(event -> product.setSpoilDate(datePicker.getValue()));
		datePicker.valueProperty().bindBidirectional(product.spoilDateProperty());
		
		final var isOpenCheck = new CheckBox(LangUtils.getString("edit_product_open_label"));
		isOpenCheck.selectedProperty().bindBidirectional(product.isOpenProperty());
		
		final var subCountLabel = new Text(LangUtils.getString("edit_product_sub_count_label"));
		final var subCountField = new NumberTextField(product.getSubCount());
		subCountField.numberProperty().bindBidirectional(product.subCountProperty());
		
		final var subCountBox = new HBox();
		subCountBox.getChildren().addAll(subCountLabel, subCountField);
		HBox.setHgrow(subCountField, Priority.ALWAYS);
		
		final var isConsumedCheck = new CheckBox(LangUtils.getString("edit_product_consumed_label"));
		isConsumedCheck.selectedProperty().bindBidirectional(product.isConsumedProperty());
		
		root.getChildren().addAll(datePicker, isOpenCheck, subCountBox, isConsumedCheck);
		return root;
	}
}
