package fr.mrcraftcod.outofdate.model;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import java.net.URL;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

/**
 * Created by mrcraftcod (MrCraftCod - zerderr@gmail.com) on 2019-04-20.
 *
 * @author Thomas Couchoud
 * @since 2019-04-20
 */
public class Product{
	private final String ID;
	private final String name;
	private final SimpleBooleanProperty isOpen;
	private final URL image;
	private final SimpleObjectProperty<LocalDate> spoilDate;
	private final SimpleLongProperty daysLeft;
	
	public Product(final String id, final String name, final URL image){
		this.ID = id;
		this.name = name;
		this.image = image;
		this.spoilDate = new SimpleObjectProperty<>(null);
		this.spoilDate.addListener(evt -> this.updateRemainingDays());
		this.daysLeft = new SimpleLongProperty(-1);
		this.isOpen = new SimpleBooleanProperty(false);
	}
	
	public void updateRemainingDays(){
		if(Objects.isNull(getSpoilDate())){
			this.daysLeft.set(-1L);
		}
		else{
			this.daysLeft.set(LocalDate.now().until(getSpoilDate(), ChronoUnit.DAYS));
		}
	}
	
	public LocalDate getSpoilDate(){
		return spoilDate.get();
	}
	
	public void setSpoilDate(final LocalDate spoilDate){
		this.spoilDate.set(spoilDate);
	}
	
	public SimpleBooleanProperty isOpenProperty(){
		return isOpen;
	}
	
	public SimpleObjectProperty<LocalDate> spoilDateProperty(){
		return spoilDate;
	}
	
	public SimpleLongProperty daysLeftProperty(){
		return daysLeft;
	}
	
	@Override
	public String toString(){
		return new ToStringBuilder(this).append("ID", ID).append("name", name).append("image", image).append("spoilDate", spoilDate).toString();
	}
	
	public long getDaysLeft(){
		return daysLeft.get();
	}
	
	public String getID(){
		return ID;
	}
	
	public URL getImage(){
		return image;
	}
	
	public String getName(){
		return name;
	}
	
	public boolean isOpen(){
		return isOpen.get();
	}
	
	public void setIsOpen(final boolean isOpen){
		this.isOpen.set(isOpen);
	}
}
