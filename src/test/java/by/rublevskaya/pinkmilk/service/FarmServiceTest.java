package by.rublevskaya.pinkmilk.service;

import by.rublevskaya.pinkmilk.client.Consumer;
import by.rublevskaya.pinkmilk.model.Cow;
import by.rublevskaya.pinkmilk.model.Milk;
import by.rublevskaya.pinkmilk.production.Product;
import by.rublevskaya.pinkmilk.storage.Warehouse;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.List;

import static org.junit.Assert.*;

public class FarmServiceTest {

    private FarmService farmService;

    @Before
    public void setUp() {
        farmService = new FarmService();
    }

    @Test
    public void testAddCow() {
        // Arrange
        Cow cow = new Cow(1, 10.0);

        // Act
        farmService.addCow(cow);
        List<Cow> cows = farmService.getCows();

        // Assert
        assertTrue("Корова должна быть добавлена", cows.contains(cow));
        assertEquals("Общее количество коров должно быть 1", 1, cows.size());
    }

    @Test
    public void testMilkCow() {
        // Arrange
        Cow cow = new Cow(1, 10.0);
        farmService.addCow(cow);

        // Act
        Milk milk = farmService.milkCow(cow);

        // Assert
        assertNotNull("Молоко должно быть получено", milk);
        assertEquals("Объем молока должен быть равен 10.0 литров", 10.0, milk.getVolume(), 0.001);
    }

    @Test
    public void testMilkAllCows() {
        // Arrange
        Cow cow1 = new Cow(1, 10.0);
        Cow cow2 = new Cow(2, 15.0);
        farmService.addCow(cow1);
        farmService.addCow(cow2);

        // Act
        double totalMilk = farmService.milkAllCows();

        // Assert
        assertEquals("Общий объем молока должен быть 25.0 литров", 25.0, totalMilk, 0.001);
    }

    @Test
    public void testAddMilkToWarehouse() {
        // Arrange
        Milk milk = new Milk(10.0);

        // Act
        farmService.addMilkToWarehouse(milk);
        Warehouse warehouse = farmService.getWarehouse();

        // Assert
        assertNotNull("На складе должен быть продукт", warehouse);
        assertEquals("На складе должно быть 10.0 литров молока", 10.0,
                warehouse.getStoredProducts().stream().mapToDouble(Product::getVolume).sum(), 0.001);
    }

    @Test
    public void testAddConsumer() {
        // Arrange
        Consumer consumer = new Consumer("Магазин А");

        // Act
        farmService.addConsumer(consumer);
        // Непосредственно проверяем списки потребителей
        List<Consumer> consumers = farmService.getConsumers(); // Метод getConsumers вы должны добавить, если его до этого не было.

        // Assert
        assertNotNull("Список потребителей не должен быть пустым", consumers);
        assertTrue("Потребитель должен быть добавлен", consumers.contains(consumer));
    }

    @Test
    public void testDistributeProduct() {
        // Arrange
        Milk milk = new Milk(20.0);
        farmService.addMilkToWarehouse(milk); // Добавляем молоко на склад

        Product yogurt = new Product("Йогурт", 5.0, 7);
        farmService.getWarehouse().addProduct(yogurt); // Добавляем йогурт на склад

        Consumer consumer = new Consumer("Магазин А");
        farmService.addConsumer(consumer); // Добавляем потребителя

        // Act
        farmService.distributeProduct(yogurt, consumer); // Распределяем продукт потребителю

        // Проверяем - склад должен остаться без йогурта
        Warehouse warehouse = farmService.getWarehouse();

        // Assert
        assertFalse("На складе не должно быть продукта Йогурт",
                warehouse.getStoredProducts().stream().anyMatch(product -> product.getName().equals("Йогурт")));
    }

    @Test
    public void testSaveStateToFile() {
        // Arrange
        Cow cow = new Cow(1, 10.0);
        farmService.addCow(cow);
        String filePath = "farm_state.json";

        // Act
        farmService.saveState(filePath);

        // Assert
        File file = new File(filePath);
        assertTrue("Файл сохранения состояния должен существовать", file.exists());

        // Удалить файл после теста
        if (file.exists()) {
            file.delete();
        }
    }
}