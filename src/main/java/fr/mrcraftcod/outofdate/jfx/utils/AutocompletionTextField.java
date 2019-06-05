package fr.mrcraftcod.outofdate.jfx.utils;

import javafx.geometry.Side;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import lombok.Getter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * From https://stackoverflow.com/questions/31556373/javafx-dialog-with-2-input-fields
 */
public class AutocompletionTextField extends TextField{
	@Getter
	private final SortedSet<String> entries;
	private final ContextMenu entriesPopup;
	
	public AutocompletionTextField(final Collection<String> suggestions){
		this();
		this.getEntries().addAll(suggestions);
	}
	
	public AutocompletionTextField(){
		super();
		this.entries = new TreeSet<>();
		this.entriesPopup = new ContextMenu();
		setListener();
	}
	
	private void setListener(){
		textProperty().addListener((observable, oldValue, newValue) -> {
			final var enteredText = getText();
			if(enteredText == null || enteredText.isBlank()){
				entriesPopup.hide();
			}
			else{
				final var filteredEntries = entries.stream().filter(e -> e.toLowerCase().contains(enteredText.toLowerCase())).collect(Collectors.toList());
				if(!filteredEntries.isEmpty()){
					populatePopup(filteredEntries, enteredText);
					if(!entriesPopup.isShowing()){
						entriesPopup.show(AutocompletionTextField.this, Side.BOTTOM, 0, 0);
					}
				}
				else{
					entriesPopup.hide();
				}
			}
		});
		//Hide always by focus-in (optional) and out
		focusedProperty().addListener((observableValue, oldValue, newValue) -> entriesPopup.hide());
	}
	
	private void populatePopup(final List<String> searchResult, final String searchResultStr){
		final List<CustomMenuItem> menuItems = new LinkedList<>();
		final var maxEntries = 10;
		final var count = Math.min(searchResult.size(), maxEntries);
		for(var i = 0; i < count; i++){
			final var result = searchResult.get(i);
			final var entryLabel = new Label();
			entryLabel.setGraphic(buildTextFlow(result, searchResultStr));
			entryLabel.setPrefHeight(10);  //don't sure why it's changed with "graphic"
			final var item = new CustomMenuItem(entryLabel, true);
			menuItems.add(item);
			item.setOnAction(actionEvent -> {
				setText(result);
				positionCaret(result.length());
				entriesPopup.hide();
			});
		}
		entriesPopup.getItems().clear();
		entriesPopup.getItems().addAll(menuItems);
	}
	
	private static TextFlow buildTextFlow(final String text, final String filter){
		final var filterIndex = text.toLowerCase().indexOf(filter.toLowerCase());
		final var textBefore = new Text(text.substring(0, filterIndex));
		final var textAfter = new Text(text.substring(filterIndex + filter.length()));
		final var textFilter = new Text(text.substring(filterIndex, filterIndex + filter.length()));
		textFilter.setFill(Color.ORANGE);
		textFilter.setFont(Font.font("Helvetica", FontWeight.BOLD, 12));
		return new TextFlow(textBefore, textFilter, textAfter);
	}
}
