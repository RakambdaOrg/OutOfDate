package fr.mrcraftcod.outofdate.model;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javax.persistence.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

/**
 * Created by mrcraftcod (MrCraftCod - zerderr@gmail.com) on 2019-04-20.
 *
 * @author Thomas Couchoud
 * @since 2019-04-20
 */
@Entity
@Table(name = "OwnedProducts")
@Access(AccessType.FIELD)
public class OwnedProduct implements Comparable<OwnedProduct>{
	@Transient
	private final SimpleObjectProperty<Product> product;
	@Transient
	private final SimpleIntegerProperty id;
	@Transient
	private final SimpleBooleanProperty isOpen;
	@Transient
	private final SimpleObjectProperty<LocalDate> spoilDate;
	@Transient
	private final SimpleLongProperty daysLeft;
	@Transient
	private final SimpleIntegerProperty subCount;
	@Transient
	private final SimpleBooleanProperty isConsumed;
	@Transient
	private final SimpleObjectProperty<LocalDate> addedOn;
	@Transient
	private final SimpleObjectProperty<LocalDate> consumedOn;
	
	public OwnedProduct(){
		this.product = new SimpleObjectProperty<>();
		this.id = new SimpleIntegerProperty();
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
	
	public OwnedProduct(final Product product){
		this.product = new SimpleObjectProperty<>(product);
		this.id = new SimpleIntegerProperty();
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
	
	@Id
	@Access(AccessType.PROPERTY)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getId(){
		return id.get();
	}
	
	public SimpleIntegerProperty idProperty(){
		return id;
	}
	
	public void setId(int id){
		this.id.set(id);
	}
	
	@Access(AccessType.PROPERTY)
	@Column(name = "consumedOn")
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
	
	@Access(AccessType.PROPERTY)
	@Column(name = "addedOn")
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
	
	@Access(AccessType.PROPERTY)
	@Column(name = "spoilOn")
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
			return Boolean.compare(o.getIsOpen(), this.getIsOpen());
		}
		return diff;
	}
	
	public long getDaysLeft(){
		return this.daysLeftProperty().get();
	}
	
	@Access(AccessType.PROPERTY)
	@Column(name = "open")
	public boolean getIsOpen(){
		return this.isOpenProperty().get();
	}
	
	public SimpleLongProperty daysLeftProperty(){
		return this.daysLeft;
	}
	
	public SimpleBooleanProperty isOpenProperty(){
		return this.isOpen;
	}
	
	@Access(AccessType.PROPERTY)
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "productId")
	public Product getProduct(){
		return productProperty().get();
	}
	
	public void setProduct(Product product){
		this.product.set(product);
	}
	
	private SimpleObjectProperty<Product> productProperty(){
		return this.product;
	}
	
	@Access(AccessType.PROPERTY)
	@Column(name = "subCount")
	public int getSubCount(){
		return this.subCountProperty().get();
	}
	
	public SimpleIntegerProperty subCountProperty(){
		return this.subCount;
	}
	
	public void setSubCount(final int subCount){
		this.subCountProperty().set(subCount);
	}
	
	@Access(AccessType.PROPERTY)
	@Column(name = "consumed")
	public boolean getIsConsumed(){
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
