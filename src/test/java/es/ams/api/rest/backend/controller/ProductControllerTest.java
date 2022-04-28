package es.ams.api.rest.backend.controller;

import es.ams.api.rest.backend.common.exception.NotFoundException;
import es.ams.api.rest.backend.dto.ProductDTO;
import es.ams.api.rest.backend.service.ProductService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(MockitoJUnitRunner.class)
public class ProductControllerTest {
    @InjectMocks
    ProductController controller;

    @Mock
    ProductService service;

    @Before
    public void setUp() {
    }

    @Test
    public void shouldGetSimilaridsOk() throws NotFoundException {
        // Given
        ProductDTO product1 = new ProductDTO();
        ProductDTO product2 = new ProductDTO();
        List<ProductDTO> expected = Arrays.asList(product1, product2);
        String id = "6";
        Mockito.when(service.getSimilarProducts(id)).thenReturn(expected);

        // When
        ResponseEntity<List<ProductDTO>> result = controller.getSimilarids(id);

        // Then
        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(expected, result.getBody());
    }

    @Test(expected=NotFoundException.class)
    public void shouldGetSimilaridsException() throws NotFoundException {
        // Given
        ProductDTO product1 = new ProductDTO();
        ProductDTO product2 = new ProductDTO();
        List<ProductDTO> expected = Arrays.asList(product1, product2);
        String id = "6";
        Mockito.when(service.getSimilarProducts(id)).thenThrow(new NotFoundException(""));

        // When
        ResponseEntity<List<ProductDTO>> result = controller.getSimilarids(id);

        // Then
    }
}