package fr.raksrinana.outofdate.model;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javax.persistence.*;
import java.net.URL;

/**
 * Created by mrcraftcod (MrCraftCod - zerderr@gmail.com) on 2019-04-20.
 *
 * @author Thomas Couchoud
 * @since 2019-04-20
 */
@Entity
@Table(name = "Products")
@Access(AccessType.FIELD)
public class Product{
	@Transient
	private final SimpleStringProperty id;
	@Transient
	private final SimpleStringProperty name;
	@Transient
	private final SimpleObjectProperty<URL> image;
	@Transient
	private final SimpleStringProperty nutriscore;
	
	public Product(){
		this.id = new SimpleStringProperty();
		this.name = new SimpleStringProperty();
		this.image = new SimpleObjectProperty<>();
		this.nutriscore = new SimpleStringProperty();
	}
	
	public Product(final String id, final String name, final URL image){
		this.id = new SimpleStringProperty(id);
		this.name = new SimpleStringProperty(name);
		this.image = new SimpleObjectProperty<>(image);
		this.nutriscore = new SimpleStringProperty();
	}
	
	@Id
	@Access(AccessType.PROPERTY)
	public String getId(){
		return this.idProperty().get();
	}
	
	private SimpleStringProperty idProperty(){
		return this.id;
	}
	
	@Access(AccessType.PROPERTY)
	@Column(name = "name")
	public String getName(){
		return this.nameProperty().get();
	}
	
	@Access(AccessType.PROPERTY)
	@Column(name = "imageURL")
	public URL getImage(){
		return this.imageProperty().get();
	}
	
	public void setImage(final URL image){
		this.imageProperty().set(image);
	}
	
	public SimpleObjectProperty<URL> imageProperty(){
		return this.image;
	}
	
	public SimpleStringProperty nameProperty(){
		return this.name;
	}
	
	public void setName(final String name){
		this.nameProperty().set(name);
	}
	
	@Access(AccessType.PROPERTY)
	@Column(name = "nutriscore")
	public String getNutriscore(){
		return this.nutriscoreProperty().get();
	}
	
	public void setNutriscore(final String nutriscore){
		this.nutriscoreProperty().set(nutriscore);
	}
	
	public SimpleStringProperty nutriscoreProperty(){
		return this.nutriscore;
	}
	
	public void setId(String id){
		this.id.set(id);
	}
	
	@Override
	public String toString(){
		return "Product{" + "id=" + id + ", name=" + name + '}';
	}
}
