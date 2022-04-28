package es.ams.api.rest.backend.controller;

import es.ams.api.rest.backend.dto.ProductDTO;
import es.ams.api.rest.backend.common.exception.NotFoundException;
import es.ams.api.rest.backend.service.ProductService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class ProductController {
    @Autowired
    private ProductService service;

    /**
     * get similar products by product id
     * @param id product id to find
     * @return list of similar products
     */
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Products does not exists"),
    })
    @GetMapping("/product/{id}/similar")
    public ResponseEntity<List<ProductDTO>> getSimilarids(@PathVariable(name = "id") String id) {
        try {
            List<ProductDTO> similarProducts = service.getSimilarProducts(id);
            return ResponseEntity.ok(similarProducts);
        } catch (NotFoundException e) {
            log.error("No product found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
