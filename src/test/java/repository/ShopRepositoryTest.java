package repository;

import domain.Product;
import exceptions.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.lang.reflect.Method;
import static org.junit.jupiter.api.Assertions.*;

class ShopRepositoryTest {
    private ShopRepository repository;
    private Product product1;
    private Product product2;
    private Product product3;

    @BeforeEach
    void setUp() {
        repository = new ShopRepository();
        product1 = new Product(1, "Хлеб", 50);
        product2 = new Product(2, "Молоко", 80);
        product3 = new Product(3, "Сыр", 120);
    }

    // ===== Тест приватного метода addToArray через рефлексию =====
    @Test
    void addToArray_ShouldHandleEmptyArray() throws Exception {
        Method method = ShopRepository.class.getDeclaredMethod("addToArray", Product[].class, Product.class);
        method.setAccessible(true);

        Product[] result = (Product[]) method.invoke(repository, new Product[0], product1);

        assertEquals(1, result.length);
        assertEquals(product1, result[0]);
    }

    @Test
    void addToArray_ShouldExpandNonEmptyArray() throws Exception {
        Method method = ShopRepository.class.getDeclaredMethod("addToArray", Product[].class, Product.class);
        method.setAccessible(true);

        Product[] input = {product1, product2};
        Product[] result = (Product[]) method.invoke(repository, input, product3);

        assertEquals(3, result.length);
        assertArrayEquals(new Product[]{product1, product2, product3}, result);
    }

    // ===== Дополнительные тесты для remove() =====
    @Test
    void remove_ShouldHandleEmptyRepository() {
        assertThrows(NotFoundException.class, () -> repository.remove(1));
    }

    @Test
    void remove_ShouldMaintainOrderAfterDeletion() {
        repository.add(product1);
        repository.add(product2);
        repository.add(product3);
        repository.remove(2);

        Product[] result = repository.findAll();
        assertEquals(2, result.length);
        assertEquals(product1, result[0]);
        assertEquals(product3, result[1]);
    }

    // ===== Тесты для полного покрытия findById() =====
    @Test
    void findById_ShouldReturnNullForEmptyRepository() {
        assertNull(repository.findById(1));
    }

    @Test
    void findById_ShouldCheckAllElements() {
        repository.add(product1);
        repository.add(product2);
        repository.add(product3);

        assertEquals(product1, repository.findById(1));
        assertEquals(product2, repository.findById(2));
        assertEquals(product3, repository.findById(3));
        assertNull(repository.findById(4));
    }

    // В ShopRepositoryTest.java
    @Test
    void remove_ShouldHandleConsecutiveRemovals() {
        repository.add(product1);
        repository.add(product2);
        repository.add(product3);

        repository.remove(1);
        repository.remove(3);

        Product[] result = repository.findAll();
        assertEquals(1, result.length);
        assertEquals(product2, result[0]);
    }

    @Test
    void findById_ShouldCheckAllBranches() {
        // Проверка пустого массива
        assertNull(repository.findById(1));

        // Проверка одного элемента
        repository.add(product1);
        assertEquals(product1, repository.findById(1));
        assertNull(repository.findById(2));

        // Проверка нескольких элементов
        repository.add(product2);
        assertEquals(product2, repository.findById(2));
    }

    // В ProductTest.java
    @Test
    void equals_ShouldVerifyAllConditions() {
        Product same = new Product(1, "Хлеб", 50);
        Product different = new Product(2, "Молоко", 80);

        // Проверка всех условий в equals()
        assertTrue(product1.equals(same));
        assertFalse(product1.equals(null));
        assertFalse(product1.equals("string"));
        assertFalse(product1.equals(different));
    }

}