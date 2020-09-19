package fr.raksrinana.outofdate.jfx;

import fr.raksrinana.outofdate.Main;
import fr.raksrinana.outofdate.jfx.table.consumed.ConsumedProductsTab;
import fr.raksrinana.outofdate.jfx.table.products.ProductsTab;
import fr.raksrinana.outofdate.jfx.utils.LangUtils;
import fr.raksrinana.outofdate.utils.CLIParameters;
import fr.raksrinana.utils.javafx.ApplicationBase;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import picocli.CommandLine;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.function.Consumer;

public class MainApplication extends ApplicationBase{
	private static final Logger log = LoggerFactory.getLogger(MainApplication.class);
	private static CLIParameters cliParameters;
	private MainController controller;
	private TabPane tabPane;
	private ProductsTab productsTab;
	private ConsumedProductsTab consumedProductsTab;
	
	public static void main(String[] args){
		log.info("Starting OutOfFood version {}", Main.getVersion());
		cliParameters = new CLIParameters();
		
		var cli = new CommandLine(cliParameters);
		cli.registerConverter(Path.class, Paths::get);
		cli.setUnmatchedArgumentsAllowed(true);
		try{
			cli.parseArgs(args);
		}
		catch(final CommandLine.ParameterException e){
			log.error("Failed to parse arguments", e);
			cli.usage(System.out);
			return;
		}
		
		Application.launch(args);
	}
	
	@Override
	public void preInit() throws Exception{
		super.preInit();
		this.controller = new MainController(cliParameters);
	}
	
	@Override
	public Image getIcon(){
		return new Image(this.getClass().getResourceAsStream("/jfx/icon.png"));
	}
	
	@Override
	public String getFrameTitle(){
		return "OutOfFood";
	}
	
	private MenuBar constructMenuBar(){
		final var menuBar = new MenuBar();
		final var os = System.getProperty("os.name");
		if(os != null && os.startsWith("Mac")){
			menuBar.useSystemMenuBarProperty().set(true);
		}
		final var menuProductAdd = new MenuItem(LangUtils.getString("menu_bar_product_add"));
		menuProductAdd.setAccelerator(new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN));
		menuProductAdd.setOnAction(evt -> {
			if(Objects.nonNull(this.productsTab)){
				new Thread(() -> this.productsTab.addProduct()).start();
			}
		});
		final var menuProductRefresh = new MenuItem(LangUtils.getString("menu_bar_product_refresh"));
		menuProductRefresh.setAccelerator(new KeyCodeCombination(KeyCode.R, KeyCombination.CONTROL_DOWN));
		menuProductRefresh.setOnAction(evt -> this.controller.refreshProductInfos());
		final var menuProduct = new Menu(LangUtils.getString("menu_bar_product"));
		menuProduct.getItems().addAll(menuProductAdd, menuProductRefresh);
		menuBar.getMenus().addAll(menuProduct);
		return menuBar;
	}
	
	@Override
	public Consumer<Stage> getOnStageDisplayed() throws Exception{
		return null;
	}
	
	@Override
	public Parent createContent(final Stage stage){
		final var borderPane = new BorderPane();
		borderPane.getStylesheets().add("/jfx/style.css");
		this.productsTab = new ProductsTab(stage, this.controller);
		this.consumedProductsTab = new ConsumedProductsTab(stage, this.controller);
		this.tabPane = new TabPane();
		this.tabPane.getTabs().addAll(this.productsTab, this.consumedProductsTab);
		borderPane.setTop(constructMenuBar());
		borderPane.setCenter(this.tabPane);
		return borderPane;
	}
	
	@Override
	public Consumer<Stage> getStageHandler(){
		return stage -> stage.setOnCloseRequest(cl -> {
			try{
				this.controller.close();
			}
			catch(Exception e){
				log.error("Failed to close controller", e);
			}
		});
	}
}
