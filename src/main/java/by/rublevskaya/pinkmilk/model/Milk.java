package by.rublevskaya.pinkmilk.model;

import java.io.Serializable;

public class Milk implements Serializable {
    private static final long serialVersionUID = 1L;
    private double volume;

    public Milk(double volume) {
        this.volume = volume;
    }

    public double getVolume() {
        return volume;
    }

}

