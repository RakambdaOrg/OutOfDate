package fr.mrcraftcod.outofdate.model;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import java.net.URL;

/**
 * Created by mrcraftcod (MrCraftCod - zerderr@gmail.com) on 2019-04-20.
 *
 * @author Thomas Couchoud
 * @since 2019-04-20
 */
public class Product{
	private final String ID;
	private final SimpleStringProperty name;
	private final SimpleObjectProperty<URL> image;
	private final SimpleStringProperty nutriscore;
	
	public Product(final String id, final String name, final URL image){
		this.ID = id;
		this.name = new SimpleStringProperty(name);
		this.image = new SimpleObjectProperty<>(image);
		this.nutriscore = new SimpleStringProperty();
	}
	
	@Override
	public String toString(){
		return new ToStringBuilder(this).append("ID", this.getID()).append("name", this.getName()).append("image", this.getImage()).toString();
	}
	
	public String getID(){
		return this.ID;
	}
	
	public String getName(){
		return this.nameProperty().get();
	}
	
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
	
	public String getNutriscore(){
		return this.nutriscoreProperty().get();
	}
	
	public void setNutriscore(final String nutriscore){
		this.nutriscoreProperty().set(nutriscore);
	}
	
	public SimpleStringProperty nutriscoreProperty(){
		return this.nutriscore;
	}
}
