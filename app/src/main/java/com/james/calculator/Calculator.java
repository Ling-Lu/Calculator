package com.james.calculator;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.Arrays;

public class Calculator {
    private static final ArrayList<Character> dividers = new ArrayList<Character>(Arrays.asList('*', '/', '-', '+'));
    private static final ArrayList<String> variables = new ArrayList<String>(Arrays.asList(")", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "pi", "e"));
    private static final ArrayList<String> nonvariables = new ArrayList<String>(Arrays.asList("(", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "pi", "e", "sqrt", "asin", "acos", "atan", "sinh", "cosh", "tanh", "sin", "cos", "tan", "abs", "exp"));
    private static final int right = 1;
    private static final int left = -1;

    public static String contextCalc(String expression, Context context) {
        expression = expression.toLowerCase();
        expression = expression.replace(" ", "");

        for (String variable : variables) {
            for (String variable2 : nonvariables) {
                expression = expression.replace(variable + variable2, variable + "*" + variable2);
            }
        }

        expression = expression.replace("π", "pi").replace("√", "sqrt").replace(" ", "");
        expression = expression.replace("[", "(").replace("]", ")").replace("{", "(").replace("}", ")");
        expression = expression.replace("pi", Double.toString(Math.PI)).replace("e", Double.toString(Math.E));

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);

        if (prefs.getBoolean("automem", false)) {
            if (expression.contains("=")) {
                int pos = expression.indexOf("=");
                String first = calc(expression.substring(0, pos + left));
                String second = calc(expression.substring(pos + right, expression.length()-1));

                boolean invalid1 = false;
                boolean invalid2 = false;
                try {
                    Integer.parseInt(first);
                } catch (Exception e) {

                    invalid1 = true;
                    try {
                        Integer.parseInt(second);
                    } catch(Exception o) {
                        invalid2 = true;
                    }
                }

                if (invalid1 && !invalid2) {
                    expression = second + "=" + first;
                } else if (invalid2) {
                    expression = first + "=" + second;
                }
            } else {
                expression = calc(expression);
            }
        } else return calc(expression);

        return expression;
    }

    public static String calc(String expression) {
        int opencount = 0, closedcount = 0;
        for (int i = 0; i < expression.length(); i++) {
            switch (expression.charAt(i)) {
                case '(' :
                    opencount++;
                    break;
                case ')' :
                    closedcount++;
                    break;
            }
        }
        if (opencount != closedcount) {
            throw new NumberFormatException();
        }

        int pos = 0;
        if (-1 != (pos = expression.indexOf("("))) {
            String sube = unBrace(expression, pos);
            expression = expression.replace("(" + sube + ")", calc(sube));

            return calc(expression);

        } else if (-1 != (pos = expression.indexOf("asin"))) {
            pos += 3;
            String number = getNum(expression, pos, right);
            expression = expression.replace("asin" + number, Double.toString(Math.asin(Double.parseDouble(number))));

            return calc(expression);

        } else if (-1 != (pos = expression.indexOf("acos"))) {
            pos += 3;
            String number = getNum(expression, pos, right);
            expression = expression.replace("acos" + number, Double.toString(Math.acos(Double.parseDouble(number))));

            return calc(expression);

        } else if (-1 != (pos = expression.indexOf("atan"))) {
            pos += 3;
            String number = getNum(expression, pos, right);
            expression = expression.replace("atan" + number, Double.toString(Math.atan(Double.parseDouble(number))));

            return calc(expression);

        } else if (-1 != (pos = expression.indexOf("sinh"))) {
            pos += 3;
            String number = getNum(expression, pos, right);
            expression = expression.replace("sinh" + number, Double.toString(Math.sinh(Double.parseDouble(number))));

            return calc(expression);

        } else if (-1 != (pos = expression.indexOf("cosh"))) {
            pos += 3;
            String number = getNum(expression, pos, right);
            expression = expression.replace("cosh" + number, Double.toString(Math.cosh(Double.parseDouble(number))));

            return calc(expression);

        } else if (-1 != (pos = expression.indexOf("tanh"))) {
            pos += 3;
            String number = getNum(expression, pos, right);
            expression = expression.replace("tanh" + number, Double.toString(Math.tanh(Double.parseDouble(number))));

            return calc(expression);

        } else if (-1 != (pos = expression.indexOf("sin"))) {
            //sine
            pos += 2;
            String number = getNum(expression, pos, right);
            expression = expression.replace("sin"+number, Double.toString(Math.sin(Double.parseDouble(number))));
            return calc(expression);
        } else if (-1 != (pos = expression.indexOf("cos"))) {
            //cosine
            pos += 2;
            String number = getNum(expression, pos, right);
            expression = expression.replace("cos" + number, Double.toString(Math.cos(Double.parseDouble(number))));

            return calc(expression);
        } else if (-1 != (pos = expression.indexOf("tan"))) {
            //tangent
            pos += 2;
            String number = getNum(expression, pos, right);
            expression = expression.replace("cos" + number, Double.toString(Math.tan(Double.parseDouble(number))));

            return calc(expression);
        } else if (-1 != (pos = expression.indexOf("sqrt"))) {
            //square root
            pos += 3;
            String number = getNum(expression, pos, right);
            expression = expression.replace("sqrt" + number, Double.toString(Math.sqrt(Double.parseDouble(number))));


            return calc(expression);
        } else if (-1 != (pos = expression.indexOf("abs"))) {
            //absolute value
            pos += 2;
            String number = getNum(expression, pos, right);
            expression = expression.replace("abs" + number, Double.toString(Math.abs(Double.parseDouble(number))));

            return calc(expression);
        } else if (-1 != (pos = expression.indexOf("exp"))) {

            pos += 2;
            String number = getNum(expression, pos, right);
            expression = expression.replace("exp" + number, Double.toString(Math.exp(Double.parseDouble(number))));

            return calc(expression);

        } else if (expression.indexOf("^") > 0) {
            int powPos = expression.indexOf("^");

            String leftNum = getNum(expression, powPos, left);
            String rightNum = getNum(expression, powPos, right);

            expression = expression.replace(leftNum + "^" + rightNum, Double.toString(Math.pow(Double.parseDouble(leftNum), Double.parseDouble(rightNum))));

            return calc(expression);

        } else if (expression.indexOf("*") > 0 | expression.indexOf("/") > 0) {

            int multPos = expression.indexOf("*");
            int divPos = expression.indexOf("/");

            pos = Math.min(multPos, divPos);
            if (multPos < 0) pos = divPos; else if (divPos < 0) pos = multPos;

            char divider = expression.charAt(pos);

            String leftNum = getNum(expression, pos, left);
            String rightNum = getNum(expression, pos, right);

            expression = expression.replace(leftNum + divider + rightNum, calcShort(leftNum, rightNum, divider));

            return calc(expression);
        } else if (expression.indexOf("+") > 0 | expression.indexOf("-") > 0) {

            int summPos = expression.indexOf("+");
            int minusPos = expression.indexOf("-");

            pos = Math.min(summPos, minusPos);

            if (summPos < 0) pos = minusPos; else if (minusPos < 0) pos = summPos;

            char divider = expression.charAt(pos);

            String leftNum = getNum(expression, pos, left);
            String rightNum = getNum(expression, pos, right);

            expression = expression.replace(leftNum + divider + rightNum, calcShort(leftNum, rightNum, divider));

            return calc(expression);
        } else return expression;
    }

    private static String unBrace(String expression, int pos) {
        int braceDepth = 1;
        String subexp="";

        for (int i = pos + 1; i < expression.length(); i++) {
            switch (expression.charAt(i)) {
                case '(':
                    braceDepth++;
                    subexp += "(";
                    break;
                case ')':
                    braceDepth--;
                    if (braceDepth != 0) subexp += ")";
                    break;
                default:
                    if (braceDepth > 0) subexp += expression.charAt(i);

            }
            if (braceDepth == 0 && !subexp.equals("")) return subexp;
        }
        throw new NumberFormatException();
    }

    private static String getNum(String expression, int pos, int dir) {

        String resultNumber = "";
        int currant = pos + dir;

        if (expression.charAt(currant) == '-') {
            resultNumber+=expression.charAt(currant);
            currant += dir;
        }

        for (; currant >= 0 && currant < expression.length() && !dividers.contains(expression.charAt(currant)); currant += dir) {
            resultNumber += expression.charAt(currant);
        }

        if (dir == left) resultNumber = new StringBuilder(resultNumber).reverse().toString();

        return resultNumber;
    }

    private static String calcShort(String leftNum, String rightNum, char divider) {
        switch (divider) {
            case '*':
                return Double.toString(Double.parseDouble(leftNum) * Double.parseDouble(rightNum));
            case '/':
                return Double.toString(Double.parseDouble(leftNum) / Double.parseDouble(rightNum));
            case '+':
                return Double.toString(Double.parseDouble(leftNum) + Double.parseDouble(rightNum));
            case '-':
                return Double.toString(Double.parseDouble(leftNum) - Double.parseDouble(rightNum));
            default:
                return "0";
        }

    }
}
