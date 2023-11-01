package ro.tuc.tp.logic;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ro.tuc.tp.gui.Controller;
import ro.tuc.tp.model.Polynomial;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class OperationsTest {
    private static Operations operations;
    private static Polynomial polynomial1, polynomial2;
    @BeforeAll
    static void initialize()
    {
        operations = new Operations();
        polynomial1 = Polynomial.fromStringToPolynomial("3x^2-x");
        polynomial2 = Polynomial.fromStringToPolynomial("x-2");
    }
    @Test
    void convertToPolynomial1(){
        assertEquals(Objects.requireNonNull(Polynomial.fromStringToPolynomial("3x^2-x+1")).fromPolynomialToString(), "+3x^2-x+1");
    }
    @Test
    void convertToPolynomial2(){
        assertNull(Polynomial.fromStringToPolynomial("3x^^2-x+1"));
    }
    @Test
    void convertToPolynomial3(){
        assertNull(Polynomial.fromStringToPolynomial("3x^2-xxx+1"));
    }
    @Test
    void convertToPolynomial4(){
        assertNull(Polynomial.fromStringToPolynomial("NotAPolynomial"));
    }
    @Test
    void convertToPolynomial5(){
        assertNull(Polynomial.fromStringToPolynomial("------x"));
    }
    @Test
    void addPolynomials(){
        assertEquals(Controller.resultOperation(operations.addPolynomials(polynomial1, polynomial2)), "3x^2-2");}
    @Test
    void subtractPolynomials() {
        assertEquals(Controller.resultOperation(operations.subtractPolynomials(polynomial1, polynomial2)), "3x^2-2x+2");}
    @Test
    void multiplyPolynomials() {
        assertEquals(Controller.resultOperation(operations.multiplyPolynomials(polynomial1, polynomial2)), "3x^3-7x^2+2x");
    }
    @Test
    void derivePolynomials() {
        assertEquals(Controller.resultOperation(operations.derivePolynomials(polynomial1)), "6x-1");
    }
    @Test
    void integratePolynomial() {
        assertEquals(Controller.resultOperation(operations.integratePolynomial(polynomial1)), "x^3-0.5x^2");
    }
    @Test
    void dividePolynomialsQuotient() {
        assertEquals(Controller.resultOperation(operations.dividePolynomials(polynomial1, polynomial2).get(0)), "3x+5");
    }
    @Test
    void dividePolynomialsRemainder() {
        assertEquals(Controller.resultOperation(operations.dividePolynomials(polynomial1, polynomial2).get(1)), "10");
    }
    @Test
    void addPolynomialsFalse() {
        assertEquals(Controller.resultOperation(operations.addPolynomials(polynomial1, polynomial2)), "21x^4-7x^2+3x-2");
    }
    @Test
    void subtractPolynomialsFalse() {
        assertEquals(Controller.resultOperation(operations.subtractPolynomials(polynomial1, polynomial2)), "-3x^2-2x-3");
    }
    @Test
    void multiplyPolynomialsFalse() {
        assertEquals(Controller.resultOperation(operations.multiplyPolynomials(polynomial1, polynomial2)), "3x^3+7x^2+3x+2");
    }
}