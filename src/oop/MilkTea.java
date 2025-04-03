package oop;

import java.util.ArrayList;
import java.util.List;

public class MilkTea {
    private List<String> topping;
    private int sugarLevel, iceLevel;

    public MilkTea( int sugarLevel,int iceLevel) {
        this.topping = new ArrayList<String>();
        this.sugarLevel = sugarLevel;
        this.iceLevel = iceLevel;
    }
}
