package es.ams.api.rest.backend.service;

import es.ams.api.rest.backend.dto.ProductDTO;
import es.ams.api.rest.backend.common.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class ProductService {
    private static final HttpEntity NOT_REQUEST_ENTITY = null;

    private RestTemplate restTemplate = new RestTemplate();

    /**
     * Get a similar products from Web Service
     * @param productId product to find
     * @return list of similar products
     * @throws NotFoundException not found similar products
     */
    public List<ProductDTO> getSimilarProducts(String productId) throws NotFoundException {
        log.info("find similar products to {}", productId);
        ResponseEntity<List<String>> responseSimilarProducts = restTemplate.exchange(getUriSimilar(productId),
                HttpMethod.GET, NOT_REQUEST_ENTITY, new ParameterizedTypeReference<List<String>>(){});

        if (HttpStatus.OK.equals(responseSimilarProducts.getStatusCode())) {
            List<String> productsId = responseSimilarProducts.getBody();
            if (productsId != null) {
                List<ProductDTO> products = new ArrayList<>();
                for (String similarId : productsId) {
                    products.add(getProduct(similarId));
                }
                return products;
            } else {
                return new ArrayList<>();
            }
        } else {
            log.error("Error when call simillar products to {}", productId);
            throw new NotFoundException("Error when call simillar products to " + productId);
        }
    }

    /**
     * get a product from web service
     * @param id product id to find
     * @return product found
     * @throws NotFoundException not found products from web service
     */
    private ProductDTO getProduct(String id) throws NotFoundException {
        log.info("find product {}", id);
        try {
            ResponseEntity<ProductDTO> product = restTemplate.getForEntity(getUriProduct(id), ProductDTO.class);
            if (HttpStatus.OK.equals(product.getStatusCode())) {
                return product.getBody();
            }
            log.error("Not found product {}", id);
            throw new NotFoundException("Not found product " + id);
        } catch (Exception e) {
            log.error("Error found product {}", id);
            throw new NotFoundException("Error found product " + id);
        }
    }

    /**
     * get url to find product
     * @param id product id to find
     * @return url to find
     */
    private String getUriProduct(String id) {
        return "http://localhost:3001/product/" +
                id;
    }

    /**
     * get url to find similar products
     * @param id product id to find similar products
     * @return url to find
     */
    private String getUriSimilar(String id) {
        return "http://localhost:3001/product/" +
                id +
                "/similarids";
    }
}
