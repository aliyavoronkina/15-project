package domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ProductTest {
    private Product product1;
    private Product product2;
    private Product differentProduct;

    @BeforeEach
    void setUp() {
        product1 = new Product(1, "Хлеб", 50);
        product2 = new Product(1, "Хлеб", 50);
        differentProduct = new Product(2, "Молоко", 80);
    }

    // ===== Тесты для полного покрытия equals() =====
    @Test
    void equals_ShouldReturnFalseForNull() {
        assertNotEquals(null, product1);
    }

    @Test
    void equals_ShouldReturnFalseForDifferentClass() {
        assertNotEquals(product1, "Not a product");
    }

    @Test
    void equals_ShouldCheckAllFields() {
        Product sameFields = new Product(1, "Хлеб", 50);
        Product diffId = new Product(2, "Хлеб", 50);
        Product diffTitle = new Product(1, "Батон", 50);
        Product diffPrice = new Product(1, "Хлеб", 60);

        assertEquals(product1, sameFields);
        assertNotEquals(product1, diffId);
        assertNotEquals(product1, diffTitle);
        assertNotEquals(product1, diffPrice);
    }

    // ===== Тесты для hashCode() =====
    @Test
    void hashCode_ShouldBeConsistent() {
        int hash1 = product1.hashCode();
        assertEquals(hash1, product1.hashCode());
    }

    @Test
    void hashCode_ShouldBeEqualForEqualObjects() {
        assertEquals(product1.hashCode(), product2.hashCode());
    }
}