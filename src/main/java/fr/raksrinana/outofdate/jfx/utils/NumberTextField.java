package fr.raksrinana.outofdate.jfx.utils;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.TextField;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NumberTextField extends TextField{
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
				log.warn("{}", e.getMessage());
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
				log.warn("{}", e.getMessage());
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
