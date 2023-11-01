package ro.tuc.tp.model;

public class Monomial {
    private int degree;
    private Double coefficient;
    public Monomial(int degree, Double coefficient)
    {
        this.degree = degree;
        this.coefficient = coefficient;
    }
    public void setCoefficient(Double coefficient) {
        this.coefficient = coefficient;
    }
    public Double getCoefficient() {
        return coefficient;
    }
    public int getDegree(){return degree;}
}
