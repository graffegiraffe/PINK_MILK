package by.rublevskaya.pinkmilk;

import by.rublevskaya.pinkmilk.cli.FarmCLI;
import by.rublevskaya.pinkmilk.service.FarmService;

public class App {
    public static void main(String[] args) {
        FarmService farmService = new FarmService();
        FarmCLI cli = new FarmCLI(farmService);
        cli.run();
    }
}
