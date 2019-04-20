package fr.mrcraftcod.outofdate.jfx;

import fr.mrcraftcod.utils.javafx.ApplicationBase;
import javafx.scene.Parent;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import java.util.function.Consumer;

/**
 * Created by mrcraftcod (MrCraftCod - zerderr@gmail.com) on 2019-04-20.
 *
 * @author Thomas Couchoud
 * @since 2019-04-20
 */
public class MainApplication extends ApplicationBase{
	private MainController controller;
	private TabPane tabPane;
	private ProductsTab productsTab;
	
	@Override
	public void preInit() throws Exception{
		super.preInit();
		this.controller = new MainController();
	}
	
	@Override
	public Image getIcon(){
		return new Image(this.getClass().getResourceAsStream("/jfx/icon.png"));
	}
	
	@Override
	public String getFrameTitle(){
		return "OutOfFood";
	}
	
	@Override
	public Consumer<Stage> getStageHandler(){
		return stage -> stage.setOnCloseRequest(cl -> this.controller.saveProducts());
	}
	
	@Override
	public Consumer<Stage> getOnStageDisplayed() throws Exception{
		return null;
	}
	
	@Override
	public Parent createContent(final Stage stage){
		final var borderPane = new BorderPane();
		
		this.productsTab = new ProductsTab(stage, this.controller);
		
		this.tabPane = new TabPane();
		this.tabPane.getTabs().add(this.productsTab);
		
		borderPane.setTop(constructMenuBar());
		borderPane.setCenter(this.tabPane);
		return borderPane;
	}
	
	private MenuBar constructMenuBar(){
		final var menuBar = new MenuBar();
		final var os = System.getProperty("os.name");
		if(os != null && os.startsWith("Mac")){
			menuBar.useSystemMenuBarProperty().set(true);
		}
		
		return menuBar;
	}
}
