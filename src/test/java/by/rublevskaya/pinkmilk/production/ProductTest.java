package by.rublevskaya.pinkmilk.production;

import org.junit.Test;

import static org.junit.Assert.*;

public class ProductTest {

    @Test
    public void getName() {
        // Arrange
        String expectedName = "Молоко";
        Product product = new Product(expectedName, 1.0, 7);

        // Act
        String actualName = product.getName();

        // Assert
        assertEquals("Название продукта должно совпадать с переданным в конструктор", expectedName, actualName);
    }

    @Test
    public void getVolume() {
        // Arrange
        double expectedVolume = 2.5;
        Product product = new Product("Йогурт", expectedVolume, 10);

        // Act
        double actualVolume = product.getVolume();

        // Assert
        assertEquals("Объем продукта должен совпадать с переданным в конструктор", expectedVolume, actualVolume, 0.001);
    }

    @Test
    public void getShelfLife() {
        // Arrange
        int expectedShelfLife = 15;
        Product product = new Product("Сыр", 1.5, expectedShelfLife);

        // Act
        int actualShelfLife = product.getShelfLife();

        // Assert
        assertEquals("Срок хранения продукта должен совпадать с переданным в конструктор", expectedShelfLife, actualShelfLife);
    }

    @Test
    public void testToString() {
        // Arrange
        String name = "Творог";
        double volume = 0.5;
        int shelfLife = 5;
        Product product = new Product(name, volume, shelfLife);

        // Act
        String actualToString = product.toString();
        String expectedToString = "Продукт{название='" + name + "', объем=" + volume + " л, срок хранения=" + shelfLife + " дней}";

        // Assert
        assertEquals("Строковое представление продукта должно быть корректным", expectedToString, actualToString);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorThrowsExceptionIfVolumeIsZeroOrNegative() {
        // Arrange & Act
        new Product("Молоко", 0, 7);

        // Assert
        // Исключение должно быть выброшено, иначе тест упадет
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorThrowsExceptionIfShelfLifeIsZeroOrNegative() {
        // Arrange & Act
        new Product("Сметана", 1.0, -5);

        // Assert
        // Исключение должно быть выброшено, иначе тест упадет
    }
}