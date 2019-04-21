package fr.mrcraftcod.outofdate.jfx.utils;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.TextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by mrcraftcod (MrCraftCod - zerderr@gmail.com) on 2019-04-21.
 *
 * @author Thomas Couchoud
 * @since 2019-04-21
 */
public class NumberTextField extends TextField{
	private static final Logger LOGGER = LoggerFactory.getLogger(NumberTextField.class);
	private final SimpleIntegerProperty number = new SimpleIntegerProperty(1);
	
	public NumberTextField(final int defaultValue){
		super(Integer.toString(defaultValue));
		number.set(defaultValue);
	}
	
	@Override
	public void replaceText(final int start, final int end, final String text){
		if(validate(text)){
			try{
				super.replaceText(start, end, text);
				number.set(getText().isBlank() ? 0 : Integer.parseInt(getText()));
			}
			catch(final Exception e){
				LOGGER.warn("{}", e.getMessage());
				this.setText(Integer.toString(number.get()));
			}
		}
	}
	
	@Override
	public void replaceSelection(final String text){
		if(validate(text)){
			try{
				super.replaceSelection(text);
				number.set(getText().isBlank() ? 0 : Integer.parseInt(getText()));
			}
			catch(final Exception e){
				LOGGER.warn("{}", e.getMessage());
				this.setText(Integer.toString(number.get()));
			}
		}
	}
	
	private boolean validate(final String text){
		return text.matches("[0-9]*");
	}
	
	public SimpleIntegerProperty numberProperty(){
		return number;
	}
}
