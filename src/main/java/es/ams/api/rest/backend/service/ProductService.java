package es.ams.api.rest.backend.service;

import es.ams.api.rest.backend.dto.ProductDTO;
import es.ams.api.rest.backend.common.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class ProductService {
    private static final HttpEntity NOT_REQUEST_ENTITY = null;

    private final RestTemplate restTemplate;

    @Autowired
    public ProductService() {
        this.restTemplate = new RestTemplate();
    }

    protected ProductService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    /**
     * Get a similar products from Web Service
     * @param productId product to find
     * @return list of similar products
     * @throws NotFoundException not found similar products
     */
    public List<ProductDTO> getSimilarProducts(String productId) throws NotFoundException {
        log.debug("get similar products to product id={}", productId);
        ResponseEntity<List<String>> responseSimilarProducts = restTemplate.exchange(getUriSimilar(productId),
                HttpMethod.GET, NOT_REQUEST_ENTITY, new ParameterizedTypeReference<List<String>>(){});

        if (HttpStatus.OK.equals(responseSimilarProducts.getStatusCode())) {
            List<String> productsId = responseSimilarProducts.getBody();
            if (!CollectionUtils.isEmpty(productsId)) {
                log.debug("Product id={}, found similar products={}", productId, productsId);
                List<ProductDTO> products = new ArrayList<>();
                for (String similarId : productsId) {
                    products.add(getProduct(similarId));
                }
                return products;
            } else {
                return new ArrayList<>();
            }
        } else {
            log.error("Error when call simillar products to product id={}, response status={}", productId, responseSimilarProducts.getStatusCode());
            throw new NotFoundException("Error when call simillar products to product id=" + productId + ", response status={}"+responseSimilarProducts.getStatusCode());
        }
    }

    /**
     * get a product from web service
     * @param id product id to find
     * @return product found
     * @throws NotFoundException not found products from web service
     */
    private ProductDTO getProduct(String id) throws NotFoundException {
        log.debug("get product id={}", id);
        try {
            ResponseEntity<ProductDTO> product = restTemplate.getForEntity(getUriProduct(id), ProductDTO.class);
            if (HttpStatus.OK.equals(product.getStatusCode())) {
                if (product.getBody() != null) {
                    log.debug("Product id={} found product={}", id, product.getBody());
                    return product.getBody();
                }
            }
        } catch (Exception e) {
            log.error("Error when get product id={}", id, e);
            throw new NotFoundException("Error when get product id=" + id);
        }
        log.error("Not found product id={}", id);
        throw new NotFoundException("Not found product id=" + id);
    }

    /**
     * get url to find product
     * @param id product id to find
     * @return url to find
     */
    protected String getUriProduct(String id) {
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
