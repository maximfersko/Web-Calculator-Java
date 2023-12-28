package com.edu.fersko.smartcalc.models.dataType;

import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class GraphData {
    private final List<Double> xValues;
    private final List<Double> yValues;

    public GraphData(List<Double> xValues, List<Double> yValues) {
        this.xValues = xValues;
        this.yValues = yValues;
    }

}
