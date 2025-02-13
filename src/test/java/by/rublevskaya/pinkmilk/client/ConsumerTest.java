package by.rublevskaya.pinkmilk.client;

import org.junit.Test;

import static org.junit.Assert.*;

public class ConsumerTest {

    @Test
    public void getName() {
        // Arrange: создаем объект Consumer
        String testName = "Иван";
        Consumer consumer = new Consumer(testName);

        // Act: вызываем метод getName
        String resultName = consumer.getName();

        // Assert: проверяем, что возвращаемое имя совпадает с переданным в конструктор
        assertEquals("Имя потребителя должно совпадать с заданным", testName, resultName);
    }

    @Test
    public void testToString() {
        // Arrange: создаем объект Consumer
        String testName = "Иван";
        Consumer consumer = new Consumer(testName);

        // Act: вызываем метод toString
        String resultString = consumer.toString();

        // Assert: проверяем, что строка соответствует ожидаемому формату
        String expectedString = "Потребитель{имя='Иван'}";
        assertEquals("Строковое представление объекта Consumer некорректно", expectedString, resultString);
    }
}