package com.edu.fersko.smartcalc.models.dataType;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class GraphData {
    private final List<Double> xValues;
    private final List<Double> yValues;
}
