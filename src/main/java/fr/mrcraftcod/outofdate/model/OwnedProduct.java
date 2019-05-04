package fr.mrcraftcod.outofdate.model;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

/**
 * Created by mrcraftcod (MrCraftCod - zerderr@gmail.com) on 2019-04-20.
 *
 * @author Thomas Couchoud
 * @since 2019-04-20
 */
public class OwnedProduct implements Comparable<OwnedProduct>{
	private final Product product;
	private final SimpleBooleanProperty isOpen;
	private final SimpleObjectProperty<LocalDate> spoilDate;
	private final SimpleLongProperty daysLeft;
	private final SimpleIntegerProperty subCount;
	private final SimpleBooleanProperty isConsumed;
	private final SimpleObjectProperty<LocalDate> addedOn;
	private final SimpleObjectProperty<LocalDate> consumedOn;
	
	public OwnedProduct(final Product product){
		this.product = product;
		this.spoilDate = new SimpleObjectProperty<>(null);
		this.spoilDate.addListener(evt -> this.updateRemainingDays());
		this.daysLeft = new SimpleLongProperty(-1);
		this.isOpen = new SimpleBooleanProperty(false);
		this.subCount = new SimpleIntegerProperty(0);
		this.isConsumed = new SimpleBooleanProperty(false);
		this.isConsumed.addListener((obj, oldV, newV) -> {
			if(newV && Objects.isNull(this.getConsumedOn())){
				this.setConsumedOn(LocalDate.now());
			}
		});
		this.addedOn = new SimpleObjectProperty<>();
		this.consumedOn = new SimpleObjectProperty<>();
	}
	
	public LocalDate getConsumedOn(){
		return consumedOn.get();
	}
	
	public void setConsumedOn(final LocalDate consumedOn){
		this.consumedOn.set(consumedOn);
	}
	
	public SimpleObjectProperty<LocalDate> addedOnProperty(){
		return addedOn;
	}
	
	public SimpleObjectProperty<LocalDate> consumedOnProperty(){
		return consumedOn;
	}
	
	public LocalDate getAddedOn(){
		return addedOn.get();
	}
	
	public void setAddedOn(final LocalDate addedOn){
		this.addedOn.set(addedOn);
	}
	
	public void updateRemainingDays(){
		if(Objects.isNull(getSpoilDate())){
			this.daysLeft.setValue(-9999999);
		}
		else{
			this.daysLeft.set(LocalDate.now().until(getSpoilDate(), ChronoUnit.DAYS));
		}
	}
	
	public LocalDate getSpoilDate(){
		return this.spoilDateProperty().get();
	}
	
	public void setSpoilDate(final LocalDate spoilDate){
		this.spoilDateProperty().set(spoilDate);
	}
	
	public SimpleObjectProperty<LocalDate> spoilDateProperty(){
		return this.spoilDate;
	}
	
	@Override
	public int compareTo(final OwnedProduct o){
		if(Objects.isNull(this.getSpoilDate()) && Objects.isNull(o.getSpoilDate())){
			return 0;
		}
		if(Objects.isNull(this.getSpoilDate())){
			return 1;
		}
		if(Objects.isNull(o.getSpoilDate())){
			return -1;
		}
		final var diff = Long.compare(this.getDaysLeft(), o.getDaysLeft());
		if(diff == 0){
			return Boolean.compare(o.isOpen(), this.isOpen());
		}
		return diff;
	}
	
	public long getDaysLeft(){
		return this.daysLeftProperty().get();
	}
	
	public boolean isOpen(){
		return this.isOpenProperty().get();
	}
	
	public SimpleLongProperty daysLeftProperty(){
		return this.daysLeft;
	}
	
	public SimpleBooleanProperty isOpenProperty(){
		return this.isOpen;
	}
	
	public Product getProduct(){
		return product;
	}
	
	public int getSubCount(){
		return this.subCountProperty().get();
	}
	
	public SimpleIntegerProperty subCountProperty(){
		return this.subCount;
	}
	
	public void setSubCount(final int subCount){
		this.subCountProperty().set(subCount);
	}
	
	public boolean isConsumed(){
		return this.isConsumedProperty().get();
	}
	
	public SimpleBooleanProperty isConsumedProperty(){
		return isConsumed;
	}
	
	public void setIsConsumed(final boolean isConsumed){
		this.isConsumedProperty().set(isConsumed);
	}
	
	public void setIsOpen(final boolean isOpen){
		this.isOpenProperty().set(isOpen);
	}
}
