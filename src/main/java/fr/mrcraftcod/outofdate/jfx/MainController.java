package fr.mrcraftcod.outofdate.jfx;

import fr.mrcraftcod.outofdate.model.Product;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by mrcraftcod (MrCraftCod - zerderr@gmail.com) on 2019-04-20.
 *
 * @author Thomas Couchoud
 * @since 2019-04-20
 */
public class MainController{
	private static final Logger LOGGER = LoggerFactory.getLogger(MainController.class);
	private static final DateTimeFormatter df = DateTimeFormatter.ISO_DATE;
	private final ObservableList<Product> productsList;
	private final static String productHintSeparator = "~";
	
	public MainController(){
		this.productsList = FXCollections.observableArrayList(p -> new Observable[]{
				p.spoilDateProperty(),
				p.isOpenProperty(),
				p.isConsumedProperty()
		});
		this.loadPreviousProducts(getProductsJsonPath());
	}
	
	private void loadPreviousProducts(final Path path){
		if(path.toFile().exists()){
			try{
				final var json = new JSONObject(String.join("\n", Files.readAllLines(path)));
				if(json.has("products")){
					final var products = json.getJSONArray("products");
					for(var i = 0; i < products.length(); i++){
						try{
							this.productsList.add(parseProduct(products.getJSONObject(i)));
						}
						catch(final Exception e){
							LOGGER.error("Failed to parse json product", e);
						}
					}
				}
			}
			catch(final IOException e){
				LOGGER.error("Failed to read json file {}", path, e);
			}
		}
	}
	
	public List<String> getProductsHints(){
		return this.getProducts().stream().map(p -> String.format("%s%s%s", p.getID(), this.getProductHintSeparator(), p.getName())).distinct().collect(Collectors.toList());
	}
	
	public String getProductHintSeparator(){
		return productHintSeparator;
	}
	
	private Path getProductsJsonPath(){
		return Paths.get(".", "products.json");
	}
	
	private Product parseProduct(final JSONObject json){
		final var product = new Product(json.getString("id"), json.getString("name"), Optional.ofNullable(json.optString("image", null)).map(s -> {
			try{
				return new URL(s);
			}
			catch(MalformedURLException e){
				LOGGER.warn("Bad url from json", e);
			}
			return null;
		}).orElse(null));
		Optional.ofNullable(json.optString("spoilDate", null)).map(s -> LocalDate.parse(s, df)).ifPresent(product::setSpoilDate);
		if(json.has("isOpen")){
			product.setIsOpen(json.getBoolean("isOpen"));
		}
		if(json.has("subCount")){
			product.setSubCount(json.getInt("subCount"));
		}
		if(json.has("isConsumed")){
			product.setIsConsumed(json.getBoolean("isConsumed"));
		}
		if(json.has("nutriscore")){
			product.setNutriscore(json.getString("nutriscore"));
		}
		return product;
	}
	
	public void addProduct(final Product product){
		productsList.add(product);
	}
	
	public void saveProducts(){
		final var products = new JSONArray();
		this.getProducts().forEach(p -> {
			final var obj = new JSONObject();
			obj.put("id", p.getID());
			obj.put("name", p.getName());
			if(Objects.nonNull(p.getImage())){
				obj.put("image", p.getImage().toString());
			}
			if(Objects.nonNull(p.getSpoilDate())){
				obj.put("spoilDate", df.format(p.getSpoilDate()));
			}
			obj.put("isOpen", p.isOpen());
			obj.put("subCount", p.getSubCount());
			obj.put("isConsumed", p.isConsumed());
			obj.put("nutriscore", p.getNutriscore());
			products.put(obj);
		});
		final var json = new JSONObject();
		json.put("products", products);
		try(final var pw = new PrintWriter(new FileOutputStream(getProductsJsonPath().toFile()))){
			json.write(pw, 4, 0);
		}
		catch(final FileNotFoundException e){
			LOGGER.error("Error writing products", e);
		}
	}
	
	public ObservableList<Product> getProducts(){
		return this.productsList;
	}
}
