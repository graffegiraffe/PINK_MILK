package by.rublevskaya.pinkmilk.storage;

import by.rublevskaya.pinkmilk.production.Product;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class WarehouseTest {

    @Test
    public void addProduct() {
        // Arrange
        Warehouse warehouse = new Warehouse();
        Product product = new Product("Milk", 10.0, 5);

        // Act
        warehouse.addProduct(product);
        List<Product> storedProducts = warehouse.getStoredProducts();

        // Assert
        assertEquals("Склад должен содержать ровно 1 продукт", 1, storedProducts.size());
        assertEquals("Добавленный продукт должен быть на складе", product, storedProducts.get(0));
    }

    @Test(expected = IllegalArgumentException.class)
    public void addNullProduct_throwsException() {
        // Arrange
        Warehouse warehouse = new Warehouse();

        // Act
        warehouse.addProduct(null);

        // Assert - ожидается исключение IllegalArgumentException
    }

    @Test
    public void getStoredProducts() {
        // Arrange
        Warehouse warehouse = new Warehouse();
        Product product1 = new Product("Milk", 10.0, 5);
        Product product2 = new Product("Bread", 5.0, 10);
        warehouse.addProduct(product1);
        warehouse.addProduct(product2);

        // Act
        List<Product> storedProducts = warehouse.getStoredProducts();

        // Assert
        assertEquals("Склад должен содержать 2 продукта", 2, storedProducts.size());
        assertTrue("Продукт Milk должен быть на складе", storedProducts.contains(product1));
        assertTrue("Продукт Bread должен быть на складе", storedProducts.contains(product2));
    }

    @Test
    public void removeProductByName() {
        // Arrange
        Warehouse warehouse = new Warehouse();
        Product product = new Product("Juice", 8.0, 2);
        warehouse.addProduct(product);

        // Act
        Product removedProduct = warehouse.removeProductByName("Juice");
        List<Product> storedProducts = warehouse.getStoredProducts();

        // Assert
        assertEquals("Удаленный продукт должен быть Juice", product, removedProduct);
        assertTrue("Склад должен быть пуст", storedProducts.isEmpty());
    }

    @Test(expected = IllegalArgumentException.class)
    public void removeNonExistingProduct_throwsException() {
        // Arrange
        Warehouse warehouse = new Warehouse();

        // Act
        warehouse.removeProductByName("NonExistentProduct");

        // Assert - ожидается исключение IllegalArgumentException
    }

    @Test
    public void testToString() {
        // Arrange
        Warehouse warehouse = new Warehouse();
        Product product1 = new Product("Milk", 10.0, 5);
        Product product2 = new Product("Bread", 5.0, 10);
        warehouse.addProduct(product1);
        warehouse.addProduct(product2);

        // Act
        String warehouseString = warehouse.toString();

        // Assert
        assertTrue("Строковое представление должно содержать информацию о продукте Milk",
                warehouseString.contains("Milk"));
        assertTrue("Строковое представление должно содержать информацию о продукте Bread",
                warehouseString.contains("Bread"));
    }
}