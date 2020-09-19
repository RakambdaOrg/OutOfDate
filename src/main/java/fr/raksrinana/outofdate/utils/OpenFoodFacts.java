package fr.raksrinana.outofdate.utils;

import fr.raksrinana.outofdate.model.Product;
import fr.raksrinana.utils.http.requestssenders.get.JSONGetRequestSender;
import kong.unirest.Unirest;
import kong.unirest.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;

public class OpenFoodFacts{
	private static final Logger log = LoggerFactory.getLogger(OpenFoodFacts.class);
	
	public static Optional<Product> getProduct(final String productID){
		log.info("Getting information on product {}", productID);
		final var requestJSON = new JSONGetRequestSender(Unirest.get("https://world.openfoodfacts.org/api/v0/product/{productId}.json").queryString("productId", productID)).getRequestHandler();
		if(requestJSON.getResult().isSuccess()){
			final var jsonResponse = requestJSON.getRequestResult().getObject();
			if(jsonResponse.has("status") && jsonResponse.optInt("status", 0) == 1){
				return Optional.of(parseJsonProduct(jsonResponse.getJSONObject("product")));
			}
			else{
				log.warn("JSON has status {} : {}", jsonResponse.optInt("status", 0), jsonResponse.optString("status_verbose", "<empty>"));
			}
		}
		else{
			log.warn("API replied with code {}", requestJSON.getStatus());
		}
		return Optional.empty();
	}
	
	private static Product parseJsonProduct(final JSONObject json){
		final var product = new Product(json.getString("code"), json.getString("product_name"), Optional.ofNullable(json.optString("image_url", null)).map(urlStr -> {
			try{
				return new URL(urlStr);
			}
			catch(MalformedURLException e){
				log.warn("Image URl for product not recognized: {}", urlStr);
			}
			return null;
		}).orElse(null));
		if(json.has("nutrition_grade_fr")){
			product.setNutriscore(json.getString("nutrition_grade_fr").toUpperCase());
		}
		return product;
	}
}
