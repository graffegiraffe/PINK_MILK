package by.rublevskaya.pinkmilk.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class CowTest {

    @Test
    public void getId() {
        // Arrange
        int expectedId = 1;
        double milkPerDay = 8.5;
        Cow cow = new Cow(expectedId, milkPerDay);

        // Act
        int actualId = cow.getId();

        // Assert
        assertEquals("ID коровы должен совпадать с переданным в конструктор", expectedId, actualId);
    }

    @Test
    public void getMilkPerDay() {
        // Arrange
        int id = 2;
        double expectedMilkPerDay = 10.0;
        Cow cow = new Cow(id, expectedMilkPerDay);

        // Act
        double actualMilkPerDay = cow.getMilkPerDay();

        // Assert
        assertEquals("Количество молока в день должно совпадать с переданным в конструктор", expectedMilkPerDay, actualMilkPerDay, 0.001);
    }

    @Test
    public void testToString() {
        // Arrange
        int id = 3;
        double milkPerDay = 12.3;
        Cow cow = new Cow(id, milkPerDay);

        // Act
        String actualToString = cow.toString();
        String expectedToString = "Корова{id=" + id + ", молоко=" + milkPerDay + " литров/день}";

        // Assert
        assertEquals("Метод toString должен возвращать строку с корректным форматом", expectedToString, actualToString);
    }
}