package ro.tuc.tp.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.TreeMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Polynomial {
    private final TreeMap<Integer,Monomial> monomials = new TreeMap<>(Collections.reverseOrder());
    public static Monomial getMonomial(Matcher matcher)
    {
        int degreeRead = 0;
        double coefficientRead;
        if (matcher.group(2).contains("x^")) {
            if (!matcher.group(3).isEmpty()) {
                coefficientRead = matcher.group(1).isEmpty() ? 1 : matcher.group(1).equals("-") ? -1 : matcher.group(1).equals("+") ? 1 : Double.parseDouble(matcher.group(1));
                degreeRead = Integer.parseInt(matcher.group(3));
            } else
                return null;
        } else if (matcher.group(2).contains("x")) {
            coefficientRead = matcher.group(1).isEmpty() ? 1 : matcher.group(1).equals("-") ? -1 : matcher.group(1).equals("+") ? 1 : Double.parseDouble(matcher.group(1));
            degreeRead = matcher.group(3).isEmpty() ? 1 : Integer.parseInt(matcher.group(3));
        } else if (matcher.group(2).isEmpty() && Pattern.compile("([-+]?\\d)").matcher(matcher.group(1)).find())
            coefficientRead = Double.parseDouble(matcher.group(1));
        else
            return null;
        return new Monomial(degreeRead, coefficientRead);
    }
    public static Polynomial fromStringToPolynomial(String polynomialString) {
        Polynomial polynomial = new Polynomial();
        Pattern pattern = Pattern.compile("([-+]?\\d*)(x?\\^?)(\\d*)");
        Matcher matcher = pattern.matcher(polynomialString);
        int matchesFound = 0;
        while (matcher.find()) {
            if (!matcher.group(0).isEmpty()) {
                matchesFound = matchesFound + 1;
                Monomial monomial = Polynomial.getMonomial(matcher);
                if (monomial != null) {
                    if (matchesFound == 1)
                        polynomial.addMonomial(monomial);
                    else if (matchesFound > 1 && (matcher.group(0).startsWith("+") || matcher.group(0).startsWith("-")))
                        polynomial.addMonomial(monomial);
                    else
                        return null;
                } else
                    return null;
            }
        }
        if(matchesFound==0)
            return null;
        return polynomial;
    }
    public TreeMap<Integer,Monomial> getMonomials() {return monomials;}
    public void addMonomial(Monomial monomial)
    {
        for(Map.Entry<Integer,Monomial> monomialEntry: monomials.entrySet())
        {
            if(monomialEntry.getKey() == monomial.getDegree()) {
                Double coefficient = monomialEntry.getValue().getCoefficient() + monomial.getCoefficient();
                monomial.setCoefficient(coefficient);
            }
        }
        monomials.put(monomial.getDegree(), monomial);
    }
    public static String firstHelperToString(Monomial monomial)
    {
        String result = "";
        int degree = monomial.getDegree();
        Double coefficient = monomial.getCoefficient();
        String[] coefficientInt = coefficient.toString().split(Pattern.quote("."));
        BigDecimal bd = new BigDecimal(coefficient).setScale(2, RoundingMode.HALF_UP);
        double newInput = bd.doubleValue();
        if(degree == 1 && coefficient == 1.0)
            result += "+x";
        if(degree != 1 && coefficient == 1.0)
            result += "+x^" + degree;
        if(degree == 1 && coefficient != 1.0)
            result += (coefficientInt[1].equals("0")) ? ("+" + coefficient.intValue() + "x") : ("+" + newInput + "x");
        if(degree !=1 && coefficient != 1.0)
            result += (coefficientInt[1].equals("0")) ? ("+" + coefficient.intValue() + "x^" + degree) : ("+" + newInput + "x^" + degree);
        return result;
    }
    public static String secondHelperToString(Monomial monomial)
    {
        String result = "";
        int degree = monomial.getDegree();
        Double coefficient = monomial.getCoefficient();
        String[] coefficientInt = coefficient.toString().split(Pattern.quote("."));
        BigDecimal bd = new BigDecimal(coefficient).setScale(2, RoundingMode.HALF_UP);
        double newInput = bd.doubleValue();
        if(degree == 1 && coefficient == -1.0)
            result += "-x";
        if(degree != 1 && coefficient == -1.0)
            result += "-x^" + degree;
        if(degree == 1 && coefficient != -1.0)
            result += (coefficientInt[1].equals("0")) ? (coefficient.intValue() + "x") : (newInput + "x");
        if(degree !=1 && coefficient != -1.0)
            result += (coefficientInt[1].equals("0")) ? (coefficient.intValue() + "x^" + degree) : (newInput + "x^" + degree);
        return result;
    }
    public static String constantToString(Monomial monomial)
    {
        String result = "";
        Double coefficient = monomial.getCoefficient();
        String[] coefficientInt = coefficient.toString().split(Pattern.quote("."));
        BigDecimal bd = new BigDecimal(coefficient).setScale(2, RoundingMode.HALF_UP);
        double newInput = bd.doubleValue();
        if(coefficient > 0.0) {
            if(coefficientInt[1].equals("0"))
                result += "+" + coefficient.intValue();
            else
                result += "+" + newInput;
        }
        else if (coefficient < 0.0) {
            if(coefficientInt[1].equals("0"))
                result += coefficient.intValue();
            else
                result += newInput;
        }
        return result;
    }
    public String fromPolynomialToString()
    {
        StringBuilder result = new StringBuilder();
        if(monomials.firstEntry() == null || (monomials.firstEntry().getKey() == 0 & monomials.firstEntry().getValue().getCoefficient() == 0.0))
            return "0";
        boolean test1 = false, test2 = false;
        for(Map.Entry<Integer, Monomial> monomialEntry: monomials.entrySet())
        {
            int degree = monomialEntry.getKey();
            Double coefficient = monomialEntry.getValue().getCoefficient();
            if(degree == 0)
                result.append(Polynomial.constantToString(monomialEntry.getValue()));
            else if(coefficient > 0.0) {
                test2 = true;
                result.append(Polynomial.firstHelperToString(monomialEntry.getValue()));
            }
            else if(coefficient < 0.0) {
                test2 = true;
                result.append(Polynomial.secondHelperToString(monomialEntry.getValue()));
            }
            else
                test1 = true;
        }
        if(test1 && !test2)
            return "0";
        return result.toString();
    }
}
