package fr.mrcraftcod.outofdate.jfx;

import fr.mrcraftcod.outofdate.model.OwnedProduct;
import fr.mrcraftcod.outofdate.model.Product;
import fr.mrcraftcod.outofdate.utils.HBDatabase;
import fr.mrcraftcod.outofdate.utils.OpenFoodFacts;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.extern.slf4j.Slf4j;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by mrcraftcod (MrCraftCod - zerderr@gmail.com) on 2019-04-20.
 *
 * @author Thomas Couchoud
 * @since 2019-04-20
 */
@Slf4j
public class MainController implements AutoCloseable{
	private static final DateTimeFormatter df = DateTimeFormatter.ISO_DATE;
	private final HashSet<Product> products;
	private final ObservableList<OwnedProduct> ownedProducts;
	private final static String productHintSeparator = "~";
	private final HBDatabase db;
	
	public MainController(){
		this.products = new HashSet<>();
		this.db = new HBDatabase();
		
		this.ownedProducts = FXCollections.observableArrayList(p -> new Observable[]{
				p.isOpenProperty(),
				p.spoilDateProperty(),
				p.subCountProperty(),
				p.isConsumedProperty(),
				p.getProduct().nameProperty(),
				p.getProduct().imageProperty(),
				p.getProduct().nutriscoreProperty()
		});
		
		this.ownedProducts.addAll(db.getOwnedProducts());
	}
	
	public void addProduct(final Product product){
		this.products.add(product);
	}
	
	@Override
	public void close() throws Exception{
		this.db.close();
	}
	
	public List<String> getProductsHints(){
		return this.getProducts().stream().map(p -> String.format("%s%s%s", p.getId(), this.getProductHintSeparator(), p.getName())).distinct().collect(Collectors.toList());
	}
	
	public String getProductHintSeparator(){
		return productHintSeparator;
	}
	
	public Optional<OwnedProduct> addNewOwnedProduct(final String id){
		return this.getProductOrCreate(id).map(p -> this.addOwnedProduct(new OwnedProduct(p)));
	}
	
	private Optional<Product> getProductOrCreate(final String id){
		return this.getProduct(id).or(() -> OpenFoodFacts.getProduct(id));
	}
	
	private Optional<Product> getProduct(final String id){
		return this.getProducts().stream().filter(p -> Objects.equals(p.getId(), id)).findFirst();
	}
	
	public Set<Product> getProducts(){
		return this.products;
	}
	
	public OwnedProduct addOwnedProduct(final OwnedProduct owned){
		this.products.add(owned.getProduct());
		this.ownedProducts.add(owned);
		return owned;
	}
	
	public ObservableList<OwnedProduct> getOwnedProducts(){
		return this.ownedProducts;
	}
	
	public void refreshProductInfos(){
		this.getProducts().forEach(p -> OpenFoodFacts.getProduct(p.getId()).ifPresent(newProduct -> {
			p.setName(newProduct.getName());
			p.setImage(newProduct.getImage());
			p.setNutriscore(newProduct.getNutriscore());
		}));
	}
}
