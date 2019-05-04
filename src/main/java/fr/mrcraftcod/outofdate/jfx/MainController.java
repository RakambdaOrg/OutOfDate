package fr.mrcraftcod.outofdate.jfx;

import fr.mrcraftcod.outofdate.model.OwnedProduct;
import fr.mrcraftcod.outofdate.model.Product;
import fr.mrcraftcod.outofdate.utils.OpenFoodFacts;
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
import java.util.*;
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
	private final HashSet<Product> products;
	private final ObservableList<OwnedProduct> ownedProducts;
	private final static String productHintSeparator = "~";
	
	public MainController(){
		this.products = new HashSet<>();
		this.ownedProducts = FXCollections.observableArrayList(p -> new Observable[]{
				p.isOpenProperty(),
				p.spoilDateProperty(),
				p.subCountProperty(),
				p.isConsumedProperty(),
				p.getProduct().nameProperty(),
				p.getProduct().imageProperty(),
				p.getProduct().nutriscoreProperty()
		});
		this.loadData(getProductsJsonPath());
	}
	
	private void loadData(final Path path){
		if(path.toFile().exists()){
			try{
				final var json = new JSONObject(String.join("\n", Files.readAllLines(path)));
				if(json.has("products")){
					final var products = json.getJSONArray("products");
					for(var i = 0; i < products.length(); i++){
						try{
							this.addProduct(parseProduct(products.getJSONObject(i)));
						}
						catch(final Exception e){
							LOGGER.error("Failed to parse json product", e);
						}
					}
				}
				if(json.has("owned")){
					final var owned = json.getJSONArray("owned");
					for(var i = 0; i < owned.length(); i++){
						try{
							this.addOwnedProduct(parseOwnedProduct(owned.getJSONObject(i)));
						}
						catch(final Exception e){
							LOGGER.error("Failed to parse json owned product", e);
						}
					}
				}
			}
			catch(final IOException e){
				LOGGER.error("Failed to read json file {}", path, e);
			}
		}
	}
	
	public void addProduct(final Product product){
		this.products.add(product);
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
		if(json.has("nutriscore")){
			product.setNutriscore(json.getString("nutriscore"));
		}
		return product;
	}
	
	private OwnedProduct parseOwnedProduct(final JSONObject json){
		final var owned = new OwnedProduct(this.getProductOrCreate(json.getString("id")).orElseThrow(() -> new IllegalStateException("Couldn't find product with id " + json.getString("id"))));
		parseJsonLocalDate(json, "spoilDate").ifPresent(owned::setSpoilDate);
		parseJsonLocalDate(json, "addedDate").ifPresent(owned::setAddedOn);
		parseJsonLocalDate(json, "consumedDate").ifPresent(owned::setConsumedOn);
		if(json.has("isOpen")){
			owned.setIsOpen(json.getBoolean("isOpen"));
		}
		if(json.has("subCount")){
			owned.setSubCount(json.getInt("subCount"));
		}
		if(json.has("isConsumed")){
			owned.setIsConsumed(json.getBoolean("isConsumed"));
		}
		return owned;
	}
	
	private Optional<LocalDate> parseJsonLocalDate(final JSONObject json, final String key){
		return Optional.ofNullable(json.optString(key, null)).map(s -> LocalDate.parse(s, df));
	}
	
	public Optional<OwnedProduct> addNewOwnedProduct(final String id){
		return this.getProductOrCreate(id).map(p -> this.addOwnedProduct(new OwnedProduct(p)));
	}
	
	private Optional<Product> getProductOrCreate(final String id){
		return this.getProduct(id).or(() -> OpenFoodFacts.getProduct(id));
	}
	
	private Optional<Product> getProduct(final String id){
		return this.getProducts().stream().filter(p -> Objects.equals(p.getID(), id)).findFirst();
	}
	
	public Set<Product> getProducts(){
		return this.products;
	}
	
	public OwnedProduct addOwnedProduct(final OwnedProduct owned){
		this.products.add(owned.getProduct());
		this.ownedProducts.add(owned);
		return owned;
	}
	
	public void saveData(){
		final var products = new JSONArray();
		this.getProducts().forEach(p -> {
			final var obj = new JSONObject();
			obj.put("id", p.getID());
			obj.put("name", p.getName());
			if(Objects.nonNull(p.getImage())){
				obj.put("image", p.getImage().toString());
			}
			obj.put("nutriscore", p.getNutriscore());
			products.put(obj);
		});
		final var owned = new JSONArray();
		this.getOwnedProducts().forEach(o -> {
			final var obj = new JSONObject();
			obj.put("id", o.getProduct().getID());
			this.saveJsonDate(obj, "spoilDate", o.getSpoilDate());
			this.saveJsonDate(obj, "addedDate", o.getAddedOn());
			this.saveJsonDate(obj, "consumedDate", o.getConsumedOn());
			obj.put("isOpen", o.isOpen());
			obj.put("subCount", o.getSubCount());
			obj.put("isConsumed", o.isConsumed());
			owned.put(obj);
		});
		final var json = new JSONObject();
		json.put("products", products);
		json.put("owned", owned);
		try(final var pw = new PrintWriter(new FileOutputStream(getProductsJsonPath().toFile()))){
			json.write(pw, 4, 0);
		}
		catch(final FileNotFoundException e){
			LOGGER.error("Error writing products", e);
		}
	}
	
	private void saveJsonDate(final JSONObject obj, final String key, final LocalDate date){
		if(Objects.nonNull(date)){
			obj.put(key, df.format(date));
		}
	}
	
	public ObservableList<OwnedProduct> getOwnedProducts(){
		return this.ownedProducts;
	}
	
	public void refreshProductInfos(){
		this.getProducts().forEach(p -> OpenFoodFacts.getProduct(p.getID()).ifPresent(newProduct -> {
			p.setName(newProduct.getName());
			p.setImage(newProduct.getImage());
			p.setNutriscore(newProduct.getNutriscore());
		}));
	}
}
