package es.ams.api.rest.backend.dto;

import lombok.*;

import java.io.Serializable;

/**
 * This class uses the lombok library, this library must be installed before using it.
 * The lombok library implements the access methods to the class.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO implements Serializable {
    private String id;
    private String name;
    private Number price;
    private Boolean availability;
}
