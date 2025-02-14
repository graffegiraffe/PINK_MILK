package by.rublevskaya.pinkmilk.production;

import by.rublevskaya.pinkmilk.exception.NotEnoughMilkException;
import by.rublevskaya.pinkmilk.model.Milk;
import by.rublevskaya.pinkmilk.storage.Warehouse;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ProductionUnitTest {

    private Warehouse warehouse;
    private ProductionUnit productionUnit;

    @Before
    public void setUp() {
        warehouse = new Warehouse();
        productionUnit = new ProductionUnit(warehouse);
    }

    @Test
    public void processMilkSuccessfullyCreatesProduct() {
        warehouse.addProduct(new Product("Молоко", 10.0, 3));
        Milk milk = new Milk(5.0);
        String productName = "Йогурт";
        int shelfLife = 7;
        Product createdProduct = productionUnit.processMilk(milk, productName, shelfLife);
        assertEquals("Название созданного продукта должно совпадать", productName, createdProduct.getName());
        assertEquals("Объем созданного продукта должен быть 5 литров", 5.0, createdProduct.getVolume(), 0.001);
        assertEquals("Срок хранения продукта должен быть 7 дней", shelfLife, createdProduct.getShelfLife());
        assertEquals("На складе должно остаться 5 литров молока", 5.0,
                warehouse.getStoredProducts().stream()
                        .filter(product -> product.getName().equals("Молоко"))
                        .mapToDouble(Product::getVolume)
                        .sum(), 0.001);
    }

    @Test(expected = NotEnoughMilkException.class)
    public void processMilkThrowsExceptionWhenNotEnoughMilk() {
        warehouse.addProduct(new Product("Молоко", 3.0, 3));
        Milk milk = new Milk(5.0);
        productionUnit.processMilk(milk, "Сыр", 10);
    }

    @Test
    public void processMilkHandlesExactAmountOfMilk() {
        warehouse.addProduct(new Product("Молоко", 5.0, 3));
        Milk milk = new Milk(5.0);
        String productName = "Кефир";
        int shelfLife = 5;
        Product createdProduct = productionUnit.processMilk(milk, productName, shelfLife);
        assertEquals("Продукт должен быть создан с правильным названием", productName, createdProduct.getName());
        assertEquals("Объем созданного продукта должен быть 5 литров", 5.0, createdProduct.getVolume(), 0.001);
        assertTrue("На складе не должно быть молока с названием 'Молоко'",
                warehouse.getStoredProducts().stream()
                        .noneMatch(product -> product.getName().equals("Молоко")));
    }
}