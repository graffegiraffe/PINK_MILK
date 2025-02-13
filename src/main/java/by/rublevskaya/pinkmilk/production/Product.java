package by.rublevskaya.pinkmilk.production;

import by.rublevskaya.pinkmilk.log.CustomLogger;

import java.io.Serializable;

public class Product implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String name;
    private final double volume; // в литрах
    private final int shelfLife; // срок хранения (в днях)

    public Product(String name, double volume, int shelfLife) {
        if (volume <= 0) {
            CustomLogger.warning("Попытка создания продукта с некорректным объемом: " + volume);
            throw new IllegalArgumentException("Объем продукта должен быть положительным.");
        }
        if (shelfLife <= 0) {
            CustomLogger.warning("Попытка создания продукта с некорректным сроком хранения: " + shelfLife);
            throw new IllegalArgumentException("Срок хранения продукта должен быть положительным.");
        }

        this.name = name;
        this.volume = volume;
        this.shelfLife = shelfLife;
        CustomLogger.info("Создан продукт: " + this);
    }

    public String getName() {
        CustomLogger.info("Получено название продукта: " + name);
        return name;
    }

    public double getVolume() {
        CustomLogger.info("Получен объём продукта " + name + ": " + volume + " л");
        return volume;
    }

    public int getShelfLife() {
        CustomLogger.info("Получен срок хранения продукта " + name + ": " + shelfLife + " дней");
        return shelfLife;
    }

    @Override
    public String toString() {
        return "Продукт{" +
                "название='" + name + '\'' +
                ", объем=" + volume + " л" +
                ", срок хранения=" + shelfLife + " дней" +
                '}';
    }
}