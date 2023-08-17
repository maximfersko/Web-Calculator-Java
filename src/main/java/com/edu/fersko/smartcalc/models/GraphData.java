package com.edu.fersko.smartcalc.models;

import java.util.List;

public class GraphData {
    private final List<Double> xValues;
    private final List<Double> yValues;

    public GraphData(List<Double> xValues, List<Double> yValues) {
        this.xValues = xValues;
        this.yValues = yValues;
    }

    public List<Double> getxValues() {
        return xValues;
    }

    public List<Double> getyValues() {
        return yValues;
    }
}
