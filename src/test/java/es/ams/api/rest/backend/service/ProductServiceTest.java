package es.ams.api.rest.backend.service;

import es.ams.api.rest.backend.common.exception.NotFoundException;
import es.ams.api.rest.backend.dto.ProductDTO;
import jdk.nashorn.internal.runtime.regexp.joni.exception.InternalException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceTest {
    @InjectMocks
    ProductService service;

    @Mock
    RestTemplate restTemplate;

    @Test
    public void shouldGetSimilarProductsOK() throws NotFoundException {
        // Given
        ProductDTO product1 = new ProductDTO("1", "", 0.0, true);
        ResponseEntity<ProductDTO> responseProduct1 = ResponseEntity.ok(product1);
        ProductDTO product2 = new ProductDTO("2", "", 0.0, true);
        ResponseEntity<ProductDTO> responseProduct2 = ResponseEntity.ok(product2);
        List<String> similar = Arrays.asList("1", "2");
        ResponseEntity<List<String>> response = ResponseEntity.ok(similar);
        String id = "6";
        Mockito.when(restTemplate.exchange(ArgumentMatchers.anyString(),
                ArgumentMatchers.eq(HttpMethod.GET), ArgumentMatchers.eq(null), ArgumentMatchers.any(ParameterizedTypeReference.class))).thenReturn(response);

        Mockito.when(restTemplate.getForEntity(service.getUriProduct(product1.getId()), ProductDTO.class)).thenReturn(responseProduct1);
        Mockito.when(restTemplate.getForEntity(service.getUriProduct(product2.getId()), ProductDTO.class)).thenReturn(responseProduct2);

        // When
        List<ProductDTO> result = service.getSimilarProducts(id);

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(product1, result.get(0));
        assertEquals(product2, result.get(1));
    }

    @Test(expected = NotFoundException.class)
    public void shouldGetSimilarProductsErrorInExternalSimilarIds() throws NotFoundException {
        // Given
        ResponseEntity<List<String>> response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        String id = "6";
        Mockito.when(restTemplate.exchange(ArgumentMatchers.anyString(),
                ArgumentMatchers.eq(HttpMethod.GET), ArgumentMatchers.eq(null), ArgumentMatchers.any(ParameterizedTypeReference.class))).thenReturn(response);

        // When
        List<ProductDTO> result = service.getSimilarProducts(id);

        // Then
    }

    @Test
    public void shouldGetSimilarProductsReturnEmpty() throws NotFoundException {
        // Given
        ResponseEntity<List<String>> response = ResponseEntity.ok(null);
        String id = "6";
        Mockito.when(restTemplate.exchange(ArgumentMatchers.anyString(),
                ArgumentMatchers.eq(HttpMethod.GET), ArgumentMatchers.eq(null), ArgumentMatchers.any(ParameterizedTypeReference.class))).thenReturn(response);

        // When
        List<ProductDTO> result = service.getSimilarProducts(id);

        // Then
        assertNotNull(result);
        assertEquals(0, result.size());
    }

    @Test(expected = NotFoundException.class)
    public void shouldGetSimilarProductsErrorInExternalProductDetails() throws NotFoundException {
        // Given
        ProductDTO product1 = new ProductDTO("1", "", 0.0, true);
        ResponseEntity<ProductDTO> responseProduct1 = new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        List<String> similar = Arrays.asList("1", "2");
        ResponseEntity<List<String>> response = ResponseEntity.ok(similar);
        String id = "6";
        Mockito.when(restTemplate.exchange(ArgumentMatchers.anyString(),
                ArgumentMatchers.eq(HttpMethod.GET), ArgumentMatchers.eq(null), ArgumentMatchers.any(ParameterizedTypeReference.class))).thenReturn(response);

        Mockito.when(restTemplate.getForEntity(service.getUriProduct(product1.getId()), ProductDTO.class)).thenReturn(responseProduct1);

        // When
        List<ProductDTO> result = service.getSimilarProducts(id);

        // Then
    }

    @Test(expected = NotFoundException.class)
    public void shouldGetSimilarProductsErrorInExternalProductDetailsWithEmptyBody() throws NotFoundException {
        // Given
        ProductDTO product1 = new ProductDTO("1", "", 0.0, true);
        ResponseEntity<ProductDTO> responseProduct1 = new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        List<String> similar = Arrays.asList("1", "2");
        ResponseEntity<List<String>> response = ResponseEntity.ok(similar);
        String id = "6";
        Mockito.when(restTemplate.exchange(ArgumentMatchers.anyString(),
                ArgumentMatchers.eq(HttpMethod.GET), ArgumentMatchers.eq(null), ArgumentMatchers.any(ParameterizedTypeReference.class))).thenReturn(response);

        Mockito.when(restTemplate.getForEntity(service.getUriProduct(product1.getId()), ProductDTO.class)).thenReturn(responseProduct1);

        // When
        List<ProductDTO> result = service.getSimilarProducts(id);

        // Then
    }

    @Test(expected = NotFoundException.class)
    public void shouldGetSimilarProductsErrorInExternalProductDetailsException() throws NotFoundException {
        // Given
        ProductDTO product1 = new ProductDTO("1", "", 0.0, true);
        List<String> similar = Arrays.asList("1", "2");
        ResponseEntity<List<String>> response = ResponseEntity.ok(similar);
        String id = "6";
        Mockito.when(restTemplate.exchange(ArgumentMatchers.anyString(),
                ArgumentMatchers.eq(HttpMethod.GET), ArgumentMatchers.eq(null), ArgumentMatchers.any(ParameterizedTypeReference.class))).thenReturn(response);

        Mockito.when(restTemplate.getForEntity(service.getUriProduct(product1.getId()), ProductDTO.class)).thenThrow(new InternalException(""));

        // When
        List<ProductDTO> result = service.getSimilarProducts(id);

        // Then
    }
}