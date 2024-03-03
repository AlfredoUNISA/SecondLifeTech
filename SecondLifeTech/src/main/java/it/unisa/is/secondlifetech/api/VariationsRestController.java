package it.unisa.is.secondlifetech.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import it.unisa.is.secondlifetech.entity.ProductVariation;
import it.unisa.is.secondlifetech.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
public class VariationsRestController {
	private final ProductService productService;

	@Autowired
	public VariationsRestController(ProductService productService) {
		this.productService = productService;
	}

	@GetMapping("/variations/{modelName}")
	public String getVariationsJson(@PathVariable String modelName) {
		List<ProductVariation> variations = productService.findModelByName(modelName).getVariations();

		ObjectMapper mapper = new ObjectMapper();
		ArrayNode root = mapper.createArrayNode();

		for (ProductVariation variation : variations) {
			ObjectNode variationNode = mapper.createObjectNode();

			variationNode.put("id", variation.getId().toString());
			variationNode.put("ram", String.valueOf(variation.getRam()));

			double displaySize = variation.getDisplaySize();
			String displaySizeString;

			if (displaySize % 1 == 0) {
				// If the double is an integer, convert only the integer part to a string
				displaySizeString = Integer.toString((int) displaySize);
			} else {
				// Otherwise, convert the entire double to a string
				displaySizeString = Double.toString(displaySize);
			}

			variationNode.put("display", displaySizeString);
			variationNode.put("storage", String.valueOf(variation.getStorageSize()));
			variationNode.put("color", String.valueOf(variation.getColor()));
			variationNode.put("state", String.valueOf(variation.getState()));
			variationNode.put("inStock", String.valueOf(variation.getQuantityInStock()));
			variationNode.put("price", String.valueOf(variation.getPrice()));

			root.add(variationNode);
		}

		return root.toPrettyString();

	}
}
