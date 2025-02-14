package by.rublevskaya.pinkmilk.client;

import org.junit.Test;

import static org.junit.Assert.*;

public class ConsumerTest {

    @Test
    public void getName() {
        String testName = "Иван";
        Consumer consumer = new Consumer(testName);
        String resultName = consumer.getName();
        assertEquals("Имя потребителя должно совпадать с заданным", testName, resultName);
    }

    @Test
    public void testToString() {
        String testName = "Иван";
        Consumer consumer = new Consumer(testName);
        String resultString = consumer.toString();
        String expectedString = "Потребитель{имя='Иван'}";
        assertEquals("Строковое представление объекта Consumer некорректно", expectedString, resultString);
    }
}