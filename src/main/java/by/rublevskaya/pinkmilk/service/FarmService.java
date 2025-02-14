package by.rublevskaya.pinkmilk.service;

import by.rublevskaya.pinkmilk.log.CustomLogger;
import by.rublevskaya.pinkmilk.client.Consumer;
import by.rublevskaya.pinkmilk.model.Cow;
import by.rublevskaya.pinkmilk.model.Milk;
import by.rublevskaya.pinkmilk.production.Product;
import by.rublevskaya.pinkmilk.storage.Warehouse;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FarmService {
    private final List<Cow> cows = new ArrayList<>();
    private final Warehouse warehouse = new Warehouse();
    private final List<Consumer> consumers = new ArrayList<>();
    private final ObjectMapper objectMapper = new ObjectMapper();//для джейсон

    public FarmService() {
        CustomLogger.info("Инициализация FarmService");
    }

    public void addCow(Cow cow) {
        if (cow == null) {
            CustomLogger.warning("Попытка добавить null-корову");
            throw new IllegalArgumentException("Корова не может быть null");

        }
        if (cows.stream().anyMatch(existingCow -> existingCow.getId() == cow.getId())) {
            CustomLogger.warning("Попытка добавить дублирующую корову: " + cow);
            throw new IllegalArgumentException("Корова с идентификатором " + cow.getId() + " уже существует");
        }
        if (cow.getId() <= 0) {
            CustomLogger.warning("Отрицательный ID коровы: " + cow);
            throw new IllegalArgumentException("Идентификатор коровы должен быть положительным числом");
        }
        if (cow.getMilkPerDay() < 0) {
            CustomLogger.warning("Отрицательное кол-во молока для коровы: " + cow);
            throw new IllegalArgumentException("Кол-во молока не может быть отрицательным");
        }
        cows.add(cow);
        CustomLogger.info("Добавлена корова: " + cow);
    }

    public List<Cow> getCows() {
        CustomLogger.info("Получен список коров: " + cows);
        return new ArrayList<>(cows);
    }

    public Milk milkCow(Cow cow) {
        if (cow == null) {
            CustomLogger.warning("Попытка подоить null-корову");
            throw new IllegalArgumentException("Корова не может быть null");
        }
        Milk milk = new Milk(cow.getMilkPerDay());
        CustomLogger.info("Подоена корова " + cow + ". Получено молока: " + milk);
        return milk;
    }

    public double milkAllCows() {
        if (cows.isEmpty()) {
            CustomLogger.warning("Попытка поить молоко со стада, но коровы отсутствуют");
            return 0;
        }
        double totalMilk = cows.stream().mapToDouble(Cow::getMilkPerDay).sum();
        CustomLogger.info("Всё стадо подоено! Итоговый объем молока: " + totalMilk + " л");
        return totalMilk;
    }

    public void addMilkToWarehouse(Milk milk) {
        if (milk == null || milk.getVolume() <= 0) {
            CustomLogger.warning("Попытка добавить молоко на склад с некорректными параметрами: " + milk);
            throw new IllegalArgumentException("Объем молока должен быть положительным");
        }
        Product milkProduct = new Product("Молоко", milk.getVolume(), 3);
        warehouse.addProduct(milkProduct);
        CustomLogger.info("Молоко добавлено на склад: " + milkProduct);
    }

    public Warehouse getWarehouse() {
        CustomLogger.info("Получен объект склада");
        return warehouse;
    }

    public void addConsumer(Consumer consumer) {
        if (consumer == null) {
            CustomLogger.warning("Попытка добавить null-потребителя");
            throw new IllegalArgumentException("Потребитель не может быть null");
        }
        consumers.add(consumer);
        CustomLogger.info("Добавлен потребитель: " + consumer);
    }

    public void distributeProduct(Product product, Consumer consumer) {
        if (product == null || consumer == null) {
            CustomLogger.warning("Попытка распределить null-продукт или null-потребителя");
            throw new IllegalArgumentException("Продукт и потребитель не могут быть null");
        }
        if (!warehouse.getStoredProducts().remove(product)) {
            CustomLogger.warning("Попытка распределить продукт, которого нет на складе: " + product);
            throw new IllegalArgumentException("Продукт отсутствует на складе(((");
        }
        CustomLogger.info("Продукт " + product + " распределен потребителю: " + consumer);
    }

    public void saveState(String filePath) {
        if (filePath == null || filePath.isEmpty()) {
            CustomLogger.warning("Попытка сохранить состояние с некорректным путем: " + filePath);
            throw new IllegalArgumentException("Путь сохранения не может быть пустым");
        }
        try {
            objectMapper.writeValue(new File(filePath), cows); //коровы в джейсон
            CustomLogger.info("Состояние успешно сохранено в файл: " + filePath);
        } catch (IOException e) {
            CustomLogger.warning("Ошибка при сохранении состояния: " + e.getMessage());
            throw new RuntimeException("Ошибка при сохранении состояния: " + e.getMessage(), e);
        }
    }

    public void showWarehouse() {
        List<Product> products = warehouse.getStoredProducts();
        if (products.isEmpty()) {
            CustomLogger.info("Склад пуст");
            System.out.println("Склад пустой");
            return;
        }

        CustomLogger.info("Содержимое склада: " + products);
        System.out.println("Содержимое склада:");
        for (Product product : products) {
            System.out.println("- " + product.getName()
                    + " | Объем: " + product.getVolume() + " л"
                    + " | Срок хранения: " + product.getShelfLife() + " дней");
        }
    }

    public List<Consumer> getConsumers() {
        return new ArrayList<>(consumers);
    }
}