package by.rublevskaya.pinkmilk.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class MilkTest {

    @Test
    public void getVolume() {
        double expectedVolume = 5.0;
        Milk milk = new Milk(expectedVolume);
        double actualVolume = milk.getVolume();
        assertEquals("Объём молока должен совпадать с переданным в конструктор", expectedVolume, actualVolume, 0.001);
    }
}