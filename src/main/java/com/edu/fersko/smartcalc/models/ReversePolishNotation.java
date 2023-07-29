package com.edu.fersko.smartcalc.models;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

@Component
public class ReversePolishNotation {
    private Stack<Double> numbers;
    private String expression;
    private double x;

    private enum Type {
        PLUS,
        MINUS,
        DIV,
        MULT,
        POW,
        MOD,
        COS,
        SIN,
        TAN,
        ATAN,
        ACOS,
        ASIN,
        SQRT,
        UPLUS,
        UMINUS,
        LOG,
        LN,
        X,
        DOT,
        CLOSE_BRACKET,
        OPEN_BRACKET,
        NUMBER
    }

    private enum Priority {
        LOW,
        MID,
        HIGH,
        VHIGH,
        VVHIGH
    }

    public ReversePolishNotation() {

    }

    public ReversePolishNotation(String expression, double x) {
        this.expression = expression;
        this.x = x;
    }

    public ReversePolishNotation(String expression) {
        this.expression = expression;
        this.x = 0;
    }

    public List<Point> calculateGraph(String expression, double xStart, double xEnd, double step) {
        List<Point> points = new ArrayList<>();
        double x = xStart;

        while (x <= xEnd) {
            double y = getResult(expression, x);
            points.add(new Point(x, y));
            x += step;
        }

        return points;
    }


    public double getResult() {
        return calculateRPN(getReversePolishNotation());
    }

    public double getResult(String expression) {
        this.expression = expression;
        return calculateRPN(getReversePolishNotation());
    }

    public double getResult(String expression, double x) {
        this.expression = expression;
        this.x = x;
        return calculateRPN(getReversePolishNotation());
    }

    private String getReversePolishNotation() {
        String[] tokens = expression.split("(?=[-+*/^()])|(?<=[-+*/^()])");
        tokens = removeEmptyTokens(tokens);
        numbers = new Stack<>();
        StringBuilder rpnExpression = new StringBuilder();
        Stack<String> stack = new Stack<>();
        boolean unaryOperatorExpected = true;

        for (String token : tokens) {
            if (token.isEmpty()) {
                continue;
            }

            if (token.equals("(")) {
                stack.push(token);
                unaryOperatorExpected = true;
            } else if (token.equals(")")) {
                while (!stack.isEmpty() && !stack.peek().equals("(")) {
                    rpnExpression.append(stack.pop()).append(" ");
                }
                if (!stack.isEmpty() && stack.peek().equals("(")) {
                    stack.pop();
                } else {
                    throw new IllegalArgumentException("Mismatched parentheses in expression");
                }
                unaryOperatorExpected = false;
            } else {
                Type typeLexeme = getType(token);
                if (typeLexeme.equals(Type.NUMBER) || typeLexeme.equals(Type.X)) {
                    rpnExpression.append(token).append(" ");
                    unaryOperatorExpected = false;
                } else if (typeLexeme.equals(Type.POW)) {
                    while (!stack.isEmpty() && getType(stack.peek()).compareTo(Type.POW) >= 0) {
                        rpnExpression.append(stack.pop()).append(" ");
                    }
                    stack.push(token);
                    unaryOperatorExpected = true;
                } else if (typeLexeme.compareTo(Type.PLUS) >= 0 && typeLexeme.compareTo(Type.LN) <= 0) {
                    while (!stack.isEmpty() && !stack.peek().equals("(") &&
                            getType(stack.peek()).compareTo(Type.PLUS) >= 0 &&
                            getPriority(typeLexeme).compareTo(getPriority(getType(stack.peek()))) <= 0) {
                        rpnExpression.append(stack.pop()).append(" ");
                    }
                    stack.push(token);
                    unaryOperatorExpected = true;
                } else if (typeLexeme.equals(Type.UMINUS) || typeLexeme.equals(Type.UPLUS)) {
                    if (unaryOperatorExpected) {
                        stack.push(token);
                    } else {
                        throw new IllegalArgumentException("Invalid use of unary operator: " + token);
                    }
                    unaryOperatorExpected = true;
                }
            }
        }

        while (!stack.isEmpty()) {
            if (stack.peek().equals("(") || stack.peek().equals(")")) {
                throw new IllegalArgumentException("Mismatched parentheses in expression");
            }
            rpnExpression.append(stack.pop()).append(" ");
        }

        return rpnExpression.toString();
    }

    private double calculateRPN(String rpnExpression) {
        String[] tokens = rpnExpression.trim().split(" ");
        numbers = new Stack<>();

        for (String token : tokens) {
            if (Character.isDigit(token.charAt(0))) {
                numbers.push(Double.parseDouble(token));
            } else if (token.equals("x")) {
                numbers.push(x);
            } else if (isUnaryOperator(token)) {
                if (numbers.isEmpty()) {
                    throw new IllegalArgumentException("Not enough operands for unary operator: " + token);
                }
                double operand = numbers.pop();
                numbers.push(calculateUnary(token, operand));
            } else {
                if (numbers.size() < 2) {
                    throw new IllegalArgumentException("Not enough operands for operator: " + token);
                }
                double operand2 = numbers.pop();
                double operand1 = numbers.pop();
                numbers.push(calculateBinary(token, operand1, operand2));
            }
        }

        if (numbers.size() != 1) {
            throw new IllegalArgumentException("Invalid expression");
        }

        return numbers.pop();
    }


    private boolean isUnaryOperator(String operator) {
        return operator.equals("u+") || operator.equals("u-") || isMathFunction(operator);
    }

    private boolean isMathFunction(String operator) {
        return operator.equals("cos") || operator.equals("sin") || operator.equals("tan")
                || operator.equals("atan") || operator.equals("acos") || operator.equals("asin")
                || operator.equals("sqrt") || operator.equals("log") || operator.equals("ln");
    }


    private double calculateBinary(String operator, double operand1, double operand2) {
        switch (operator) {
            case "+":
                return operand1 + operand2;
            case "-":
                return operand1 - operand2;
            case "*":
                return operand1 * operand2;
            case "/":
                return operand1 / operand2;
            case "^":
                return Math.pow(operand1, operand2);
            case "mod":
                return operand1 % operand2;
            default:
                throw new IllegalArgumentException("Invalid operator: " + operator);
        }
    }

    private double calculateUnary(String operator, double operand) {
        switch (operator) {
            case "u+":
                return operand;
            case "u-":
                return -operand;
            case "cos":
                return Math.cos(operand);
            case "sin":
                return Math.sin(operand);
            case "tan":
                return Math.tan(operand);
            case "atan":
                return Math.atan(operand);
            case "acos":
                return Math.acos(operand);
            case "asin":
                return Math.asin(operand);
            case "sqrt":
                return Math.sqrt(operand);
            case "log":
                return Math.log10(operand);
            case "ln":
                return Math.log(operand);
            default:
                throw new IllegalArgumentException("Invalid operator: " + operator);
        }
    }


    private Type getType(String token) {
        switch (token) {
            case "+":
                return Type.PLUS;
            case "-":
                return Type.MINUS;
            case "*":
                return Type.MULT;
            case "/":
                return Type.DIV;
            case "^":
                return Type.POW;
            case "mod":
                return Type.MOD;
            case "cos":
                return Type.COS;
            case "sin":
                return Type.SIN;
            case "tan":
                return Type.TAN;
            case "atan":
                return Type.ATAN;
            case "acos":
                return Type.ACOS;
            case "asin":
                return Type.ASIN;
            case "sqrt":
                return Type.SQRT;
            case "u+":
                return Type.UPLUS;
            case "u-":
                return Type.UMINUS;
            case "log":
                return Type.LOG;
            case "ln":
                return Type.LN;
            case "x":
                return Type.X;
            case ".":
                return Type.DOT;
            default:
                return Type.NUMBER;
        }
    }

    private Priority getPriority(Type typeToken) {
        Priority priority = null;
        if (typeToken.equals(Type.PLUS) || typeToken.equals(Type.MINUS)) {
            priority = Priority.LOW;
        } else if (typeToken.equals(Type.DIV) || typeToken.equals(Type.MULT) || typeToken.equals(Type.MOD)) {
            priority = Priority.MID;
        } else if (typeToken.equals(Type.POW)) {
            priority = Priority.HIGH;
        } else if (typeToken.equals(Type.UMINUS) || typeToken.equals(Type.UPLUS)) {
            priority = Priority.VHIGH;
        } else if (typeToken.compareTo(Type.COS) >= 0 && typeToken.compareTo(Type.LN) <= 0
                   && (typeToken != Type.UPLUS && typeToken != Type.UMINUS)) {
            priority = Priority.VVHIGH;
        }
        return priority != null ? priority : Priority.LOW;
    }

    private String[] removeEmptyTokens(String[] tokens) {
        return Arrays.stream(tokens)
                .map(String::trim)
                .filter(token -> !token.isEmpty())
                .toArray(String[]::new);
    }
}
