package by.rublevskaya.pinkmilk.client;

import java.io.Serializable;

public class Consumer implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String name;

    public Consumer(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Потребитель{" +
                "имя='" + name + '\'' +
                '}';
    }
}