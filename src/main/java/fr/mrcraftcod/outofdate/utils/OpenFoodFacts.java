package fr.mrcraftcod.outofdate.utils;

import fr.mrcraftcod.outofdate.model.Product;
import fr.mrcraftcod.utils.http.requestssenders.get.JSONGetRequestSender;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Optional;

/**
 * Created by mrcraftcod (MrCraftCod - zerderr@gmail.com) on 2019-04-20.
 *
 * @author Thomas Couchoud
 * @since 2019-04-20
 */
public class OpenFoodFacts{
	private static final Logger LOGGER = LoggerFactory.getLogger(OpenFoodFacts.class);
	
	public static Optional<Product> getProduct(final String productID){
		LOGGER.info("Getting information on product {}", productID);
		try{
			final var requestJSON = new JSONGetRequestSender(new URL(String.format("https://world.openfoodfacts.org/api/v0/product/%s.json", productID))).getRequestHandler();
			if(requestJSON.getStatus() == 200){
				final var jsonResponse = requestJSON.getRequestResult();
				if(jsonResponse.has("status") && jsonResponse.optInt("status", 0) == 1){
					return Optional.of(parseJsonProduct(jsonResponse.getJSONObject("product")));
				}
				else{
					LOGGER.warn("JSON has status {} : {}", jsonResponse.optInt("status", 0), jsonResponse.optString("status_verbose", "<empty>"));
				}
			}
			else{
				LOGGER.warn("API replied with code {}", requestJSON.getStatus());
			}
		}
		catch(final MalformedURLException | URISyntaxException e){
			LOGGER.warn("Wrong url for product '{}'", productID);
		}
		return Optional.empty();
	}
	
	private static Product parseJsonProduct(final JSONObject json){
		
		return new Product(json.getString("code"), json.getString("product_name"), Optional.ofNullable(json.optString("image_url", null)).map(urlStr -> {
			try{
				return new URL(urlStr);
			}
			catch(MalformedURLException e){
				LOGGER.warn("Image URl for product not recognized: {}", urlStr);
			}
			return null;
		}).orElse(null));
	}
}
