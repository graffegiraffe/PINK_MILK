package by.rublevskaya.pinkmilk.model;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Cow implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private double milkPerDay; // Литры в день

    public Cow(int id, double milkPerDay) {
        this.id = id;
        this.milkPerDay = milkPerDay;
    }

    public int getId() {
        return id;
    }

    public double getMilkPerDay() {
        return milkPerDay;
    }

    @Override
    public String toString() {
        return "Корова{id=" + id + ", молоко=" + milkPerDay + " литров/день}";
    }
}
