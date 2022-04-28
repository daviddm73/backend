package es.ams.api.rest.backend.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * This class is the same as ProductDTO but without lombok.
 */
public class ProductWithoutLombokDTO implements Serializable {
    private String id;
    private String name;
    private Number price;
    private Boolean availability;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Number getPrice() {
        return price;
    }

    public void setPrice(Number price) {
        this.price = price;
    }

    public Boolean getAvailability() {
        return availability;
    }

    public void setAvailability(Boolean availability) {
        this.availability = availability;
    }

    @Override
    public String toString() {
        return "ProductWithoutLombokDTO{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", availability=" + availability +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductWithoutLombokDTO that = (ProductWithoutLombokDTO) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(price, that.price) &&
                Objects.equals(availability, that.availability);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price, availability);
    }
}
