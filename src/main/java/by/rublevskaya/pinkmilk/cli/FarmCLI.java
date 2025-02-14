package by.rublevskaya.pinkmilk.cli;

import by.rublevskaya.pinkmilk.client.Consumer;
import by.rublevskaya.pinkmilk.log.CustomLogger;
import by.rublevskaya.pinkmilk.model.Cow;
import by.rublevskaya.pinkmilk.model.Milk;
import by.rublevskaya.pinkmilk.production.ProductionUnit;
import by.rublevskaya.pinkmilk.service.FarmService;
import by.rublevskaya.pinkmilk.service.PropsHandler;

import java.util.Scanner;

public class FarmCLI {
    private static final String SAVE_FILE = PropsHandler.getPropertyFromConfig("SAVE_FILE");
    private final FarmService farmService;

    public FarmCLI(FarmService farmService) {
        this.farmService = farmService;
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        ProductionUnit productionUnit = new ProductionUnit(farmService.getWarehouse());
        boolean running = true;

        while (running) {
            System.out.println("\nВыберите действие:");
            System.out.println("1. Добавить корову");
            System.out.println("2. Показать всех коров");
            System.out.println("3. Доить коров");
            System.out.println("4. Производство продукции");
            System.out.println("5. Распределить продукцию");
            System.out.println("6. Показать содержимое склада");
            System.out.println("7. Почухать корову");
            System.out.println("8. Удалить продукт из склада");
            System.out.println("9. Выйти");

            try {
                System.out.print("Ваш выбор: ");
                int choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {
                    case 1 -> addCow(scanner);
                    case 2 -> showCows();
                    case 3 -> milkCows(scanner);
                    case 4 ->   produceProduct(scanner, new ProductionUnit(farmService.getWarehouse()));
                    case 5 -> distributeProduct(scanner);
                    case 6 -> showWarehouse();
                    case 7 -> petCow(scanner);
                    case 8 -> removeProduct(scanner);
                    case 9 -> {
                        saveState();
                        running = false;
                        exit();
                    }
                    default -> System.out.println("Неверный выбор. Попробуйте снова.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Неверный ввод. Пожалуйста, введите число.");
            } catch (Exception e) {
                System.out.println("Ошибка: " + e.getMessage());
            }
        }
    }

    private void removeProduct(Scanner scanner) {
        System.out.print("Введите название продукта для удаления: ");
        String productName = scanner.nextLine().trim();

        try {
            farmService.getWarehouse().removeProductByName(productName);
            System.out.println("Продукт успешно удален из склада: " + productName);
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Произошла неопознанная ошибка при удалении продукта. Пожалуйста, повторите попытку.");
            CustomLogger.warning("Ошибка при удалении продукта с названием: " + productName);
        }
    }

    private void addCow(Scanner scanner) {
        try {

            System.out.print("Введите идентификатор коровы: ");
            int id = Integer.parseInt(scanner.nextLine());

            if (id <= 0) {
                System.out.println("Ошибка: Идентификатор коровы должен быть положительным числом");
                return;
            }

            System.out.print("Введите количество молока (литры/в день): ");
            double milkPerDay = Double.parseDouble(scanner.nextLine());

            if (milkPerDay < 0) {
                System.out.println("Ошибка: Количество молока не может быть отрицательным");
                return;
            }

            Cow cow = new Cow(id, milkPerDay);
            farmService.addCow(cow);
            System.out.println("Корова успешно добавлена: " + cow);

        } catch (NumberFormatException e) {
            System.out.println("Ошибка: введены некорректные данные. Используйте числа.");
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    private void showCows() {
        System.out.println("Список коров:");
        for (Cow cow : farmService.getCows()) {
            System.out.println(cow);
        }
    }

    private void milkCows(Scanner scanner) {
        System.out.println("Выберите действие:");
        System.out.println("1. Доить одну корову");
        System.out.println("2. Доить всех коров");
        System.out.print("Ваш выбор: ");
        int choice = Integer.parseInt(scanner.nextLine());

        switch (choice) {
            case 1 -> {
                // Дойка одной коровы
                System.out.print("Введите идентификатор коровы: ");
                int cowId = Integer.parseInt(scanner.nextLine());
                Cow cow = farmService.getCows().stream()
                        .filter(c -> c.getId() == cowId)
                        .findFirst()
                        .orElse(null);

                if (cow == null) {
                    System.out.println("Корова с идентификатором " + cowId + " не найдена.");
                } else {
                    Milk milk = farmService.milkCow(cow); // Доим конкретную корову
                    farmService.addMilkToWarehouse(milk); // Отправляем молоко на склад
                    System.out.println("Сдоено " + milk.getVolume() + " литров молока с коровы идентификатором " + cowId);
                }
            }
            case 2 -> {
                // Дойка всех коров
                double totalMilk = farmService.milkAllCows(); // Доим всех коров
                farmService.addMilkToWarehouse(new Milk(totalMilk)); // Отправляем общее молоко на склад
                System.out.println("Общий объем надоенного молока: " + totalMilk + " литров.");
            }
            default -> System.out.println("Неверный выбор.");
        }
    }

    private void produceProduct(Scanner scanner, ProductionUnit productionUnit) {
        System.out.print("Введите название продукта: ");
        String productName = scanner.nextLine();
        System.out.print("Введите объем молока для производства продукта (литры): ");
        double milkVolume = Double.parseDouble(scanner.nextLine());
        System.out.print("Введите срок хранения продукта (дни): ");
        int shelfLife = Integer.parseInt(scanner.nextLine());

        try {
            productionUnit.processMilk(new by.rublevskaya.pinkmilk.model.Milk(milkVolume), productName, shelfLife);
            System.out.println("Продукт успешно создан");
        } catch (Exception e) {
            System.out.println("Ошибка при создании продукта: " + e.getMessage());
        }
    }

    private void distributeProduct(Scanner scanner) {
        System.out.print("Введите имя потребителя: ");
        String consumerName = scanner.nextLine();
        Consumer consumer = new Consumer(consumerName);
        farmService.addConsumer(consumer);

        System.out.print("Введите название продукта для распределения: ");
        String productName = scanner.nextLine();

        farmService.getWarehouse().getStoredProducts()
                .stream()
                .filter(product -> product.getName().equals(productName))
                .findFirst()
                .ifPresentOrElse(
                        product -> {
                            try {
                                farmService.distributeProduct(product, consumer);
                                System.out.println("Продукт успешно распределен");
                            } catch (Exception e) {
                                System.out.println("Ошибка распределения продукта: " + e.getMessage());
                            }
                        },
                        () -> System.out.println("Продукт с названием " + productName + " не найден.")
                );
    }

    private void showWarehouse() {
        farmService.showWarehouse();
    }

    private void saveState() {
        farmService.saveState(SAVE_FILE);
        System.out.println("Состояние фермы успешно сохранено");
    }

    private void petCow(Scanner scanner) {
        System.out.print("Введите идентификатор коровы, которую хотите почухать: ");
        int cowId = Integer.parseInt(scanner.nextLine());
        Cow cow = farmService.getCows().stream()
                .filter(c -> c.getId() == cowId)
                .findFirst()
                .orElse(null);
        if (cow == null) {
            System.out.println("Корова с идентификатором " + cowId + " не найдена");
        } else {
            System.out.println("Ой пасибки!!!!!!)))  Корова с идентификатором " + cowId + " очень рада вашему вниманию!");
        }
    }

    private void exit() {
        System.out.println("Выход из программы...ПОКА");
    }
}