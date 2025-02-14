package by.rublevskaya.pinkmilk.storage;

import by.rublevskaya.pinkmilk.production.Product;
import by.rublevskaya.pinkmilk.log.CustomLogger;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Warehouse implements Serializable {
    private static final long serialVersionUID = 1L;
    private final List<Product> storedProducts = new ArrayList<>();

    public Warehouse() {
        CustomLogger.info("Создан склад");
    }

    public void addProduct(Product product) {
        if (product == null) {
            CustomLogger.warning("Попытка добавить null-продукт на склад");
            throw new IllegalArgumentException("Продукт не может быть null");
        }
        storedProducts.add(product);
        CustomLogger.info("Добавлен продукт на склад: " + product);
    }

    public List<Product> getStoredProducts() {
        CustomLogger.info("Получен список продуктов на складе: " + storedProducts);
        return storedProducts;
    }

    public Product removeProductByName(String productName) {
        for (Product product : storedProducts) {
            if (product.getName().equals(productName)) {
                storedProducts.remove(product);
                CustomLogger.info("Удален продукт с названием: " + productName);
                return product;
            }
        }
        CustomLogger.warning("Продукт с названием " + productName + " не найден на складе");
        throw new IllegalArgumentException("Продукт с таким названием отсутствует на складе");
    }

    @Override
    public String toString() {
        return "Склад{" +
                "хранится=" + storedProducts +
                '}';
    }
}