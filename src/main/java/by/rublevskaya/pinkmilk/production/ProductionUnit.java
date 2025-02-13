package by.rublevskaya.pinkmilk.production;

import by.rublevskaya.pinkmilk.exception.NotEnoughMilkException;
import by.rublevskaya.pinkmilk.log.CustomLogger;
import by.rublevskaya.pinkmilk.model.Milk;
import by.rublevskaya.pinkmilk.storage.Warehouse;

import java.util.ArrayList;
import java.util.List;

public class ProductionUnit {
    private final Warehouse warehouse;

    public ProductionUnit(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    public Product processMilk(Milk milk, String productName, int shelfLife) {
        double availableMilkVolume = warehouse.getStoredProducts().stream()
                .filter(product -> product.getName().equals("Молоко"))
                .mapToDouble(Product::getVolume)
                .sum();

        if (milk.getVolume() > availableMilkVolume) {
            throw new NotEnoughMilkException("Недостаточно молока для производства продукта: " + productName);
        }

        double requiredVolume = milk.getVolume();
        double remainingMilkVolume = availableMilkVolume - requiredVolume;

        List<Product> milkProducts = new ArrayList<>();
        for (Product product : warehouse.getStoredProducts()) {
            if (product.getName().equals("Молоко")) {
                milkProducts.add(product);
                if ((requiredVolume -= product.getVolume()) <= 0) {
                    break;
                }
            }
        }
        warehouse.getStoredProducts().removeAll(milkProducts);

        if (remainingMilkVolume > 0) {
            Product remainingMilk = new Product("Молоко", remainingMilkVolume, 3); // Срок хранения 3 дня
            warehouse.addProduct(remainingMilk);
        }

        Product product = new Product(productName, milk.getVolume(), shelfLife);
        warehouse.addProduct(product);
        CustomLogger.info("Продукт создан и добавлен на склад: " + product);
        return product;
    }
}
