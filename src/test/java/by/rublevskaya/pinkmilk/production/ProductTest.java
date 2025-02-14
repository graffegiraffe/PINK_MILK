package by.rublevskaya.pinkmilk.production;

import org.junit.Test;

import static org.junit.Assert.*;

public class ProductTest {

    @Test
    public void getName() {
        String expectedName = "Молоко";
        Product product = new Product(expectedName, 1.0, 7);
        String actualName = product.getName();
        assertEquals("Название продукта должно совпадать с переданным в конструктор", expectedName, actualName);
    }

    @Test
    public void getVolume() {
        double expectedVolume = 2.5;
        Product product = new Product("Йогурт", expectedVolume, 10);
        double actualVolume = product.getVolume();
        assertEquals("Объем продукта должен совпадать с переданным в конструктор", expectedVolume, actualVolume, 0.001);
    }

    @Test
    public void getShelfLife() {
        int expectedShelfLife = 15;
        Product product = new Product("Сыр", 1.5, expectedShelfLife);
        int actualShelfLife = product.getShelfLife();
        assertEquals("Срок хранения продукта должен совпадать с переданным в конструктор", expectedShelfLife, actualShelfLife);
    }

    @Test
    public void testToString() {
        String name = "Творог";
        double volume = 0.5;
        int shelfLife = 5;
        Product product = new Product(name, volume, shelfLife);
        String actualToString = product.toString();
        String expectedToString = "Продукт{название='" + name + "', объем=" + volume + " л, срок хранения=" + shelfLife + " дней}";
        assertEquals("Строковое представление продукта должно быть корректным", expectedToString, actualToString);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorThrowsExceptionIfVolumeIsZeroOrNegative() {
        new Product("Молоко", 0, 7);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorThrowsExceptionIfShelfLifeIsZeroOrNegative() {
        new Product("Сметана", 1.0, -5);
    }
}