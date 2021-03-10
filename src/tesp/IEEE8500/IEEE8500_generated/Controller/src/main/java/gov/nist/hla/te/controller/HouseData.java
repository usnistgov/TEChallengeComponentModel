package gov.nist.hla.te.controller;

import java.util.LinkedList;
import java.util.Queue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

class HouseData {
    private final static Logger log = LogManager.getLogger();
    
    private String name;

    private Queue<Double> setPointSchedule = new LinkedList<Double>();

    public HouseData(String name) {
        this.name = name;
    }
    
    public boolean add(String value) {
        double valueAsDouble;
        
        if (value == null) {
            throw new NullPointerException();
        }
        try {
            valueAsDouble = Double.parseDouble(value);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException();
        }
        
        return setPointSchedule.add(valueAsDouble);
    }
    
    public void add(String[] values) {
        for(String value : values) {
            add(value);
        }
    }
    
    public Double remove() {
        return setPointSchedule.remove();
    }
    
    public Double peek() {
        return setPointSchedule.peek();
    }
    
    public int size() {
        return setPointSchedule.size();
    }
}
