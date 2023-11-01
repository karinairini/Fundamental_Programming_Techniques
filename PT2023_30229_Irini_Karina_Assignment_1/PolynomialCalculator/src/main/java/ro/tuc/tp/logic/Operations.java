package ro.tuc.tp.logic;

import ro.tuc.tp.model.Monomial;
import ro.tuc.tp.model.Polynomial;

import java.util.ArrayList;
import java.util.Map;
public class Operations {
    public Polynomial addPolynomials(Polynomial polynomial1, Polynomial polynomial2)
    {
        Polynomial resultAddition = new Polynomial();
        for(Map.Entry<Integer, Monomial> monomialEntryP1: polynomial1.getMonomials().entrySet())
        {
            boolean ok = false;
            for(Map.Entry<Integer,Monomial> monomialEntryP2: polynomial2.getMonomials().entrySet())
            {
                if(monomialEntryP1.getKey().equals(monomialEntryP2.getKey()))
                {
                    double coefficient = monomialEntryP1.getValue().getCoefficient() + monomialEntryP2.getValue().getCoefficient();
                    resultAddition.addMonomial(new Monomial(monomialEntryP1.getKey(), coefficient));
                    ok = true;
                }
                else if(!polynomial1.getMonomials().containsKey(monomialEntryP2.getKey()) && !resultAddition.getMonomials().containsKey(monomialEntryP2.getKey()))
                    resultAddition.addMonomial(new Monomial(monomialEntryP2.getKey(), monomialEntryP2.getValue().getCoefficient()));
            }
            if(!ok)
                resultAddition.addMonomial(new Monomial(monomialEntryP1.getKey(), monomialEntryP1.getValue().getCoefficient()));
        }
        return resultAddition;
    }
    public Polynomial subtractPolynomials(Polynomial polynomial1, Polynomial polynomial2)
    {
        Polynomial resultSubtraction = new Polynomial();
        for(Map.Entry<Integer,Monomial> monomialEntryP1: polynomial1.getMonomials().entrySet())
        {
            boolean ok = false;
            for(Map.Entry<Integer,Monomial> monomialEntryP2: polynomial2.getMonomials().entrySet())
            {
                if(monomialEntryP1.getKey().equals(monomialEntryP2.getKey()))
                {
                    double coefficient = monomialEntryP1.getValue().getCoefficient() - monomialEntryP2.getValue().getCoefficient();
                    resultSubtraction.addMonomial(new Monomial(monomialEntryP1.getKey(), coefficient));
                    ok = true;
                }
                else if(!polynomial1.getMonomials().containsKey(monomialEntryP2.getKey()) && !resultSubtraction.getMonomials().containsKey(monomialEntryP2.getKey()))
                    resultSubtraction.addMonomial(new Monomial(monomialEntryP2.getKey(), -monomialEntryP2.getValue().getCoefficient()));
            }
            if(!ok)
                resultSubtraction.addMonomial(new Monomial(monomialEntryP1.getKey(), monomialEntryP1.getValue().getCoefficient()));
        }
        return resultSubtraction;
    }
    public Polynomial multiplyPolynomials(Polynomial polynomial1, Polynomial polynomial2)
    {
        Polynomial resultMultiplication = new Polynomial();
        for(Map.Entry<Integer,Monomial> monomialEntryP1: polynomial1.getMonomials().entrySet())
        {
            for(Map.Entry<Integer,Monomial> monomialEntryP2: polynomial2.getMonomials().entrySet())
            {
                int degree = monomialEntryP1.getKey() + monomialEntryP2.getKey();
                double coefficient = monomialEntryP1.getValue().getCoefficient() * monomialEntryP2.getValue().getCoefficient();
                resultMultiplication.addMonomial(new Monomial(degree, coefficient));
            }
        }
        return resultMultiplication;
    }
    public Polynomial derivePolynomials(Polynomial polynomial)
    {
        Polynomial resultDerivative = new Polynomial();
        for(Map.Entry<Integer,Monomial> monomialEntry: polynomial.getMonomials().entrySet())
        {
            if(monomialEntry.getKey()>0) {
                double coefficient = monomialEntry.getValue().getCoefficient() * monomialEntry.getValue().getDegree();
                int degree = monomialEntry.getValue().getDegree() - 1;
                resultDerivative.addMonomial(new Monomial(degree, coefficient));
            }
        }
        return resultDerivative;
    }
    public Polynomial integratePolynomial(Polynomial polynomial)
    {
        Polynomial resultIntegral = new Polynomial();
        for(Map.Entry<Integer,Monomial> monomialEntry: polynomial.getMonomials().entrySet())
        {
            int degree = monomialEntry.getValue().getDegree() + 1;
            double coefficient = monomialEntry.getValue().getCoefficient() / degree;
            resultIntegral.addMonomial(new Monomial(degree, coefficient));
        }
        return resultIntegral;
    }
    public ArrayList<Polynomial> dividePolynomials(Polynomial polynomial1, Polynomial polynomial2)
    {
        ArrayList<Polynomial> polynomialsResult = new ArrayList<>();
        if((polynomial1.getMonomials().firstEntry().getKey() < polynomial2.getMonomials().firstEntry().getKey()) || (polynomial2.getMonomials().firstEntry().getValue().getCoefficient()==0.0))
            return null;
        else {
            Polynomial remainder = new Polynomial();
            Polynomial quotientFinal = new Polynomial();
            while(polynomial1.getMonomials().firstEntry().getKey() >= polynomial2.getMonomials().firstEntry().getKey()) {
                int degree = polynomial1.getMonomials().firstEntry().getValue().getDegree() - polynomial2.getMonomials().firstEntry().getValue().getDegree();
                double coefficient = polynomial1.getMonomials().firstEntry().getValue().getCoefficient() / polynomial2.getMonomials().firstEntry().getValue().getCoefficient();
                Monomial currentQuotientMonomial = new Monomial(degree, coefficient);
                Polynomial quotient = new Polynomial();
                quotient.addMonomial(currentQuotientMonomial);
                quotientFinal.addMonomial(currentQuotientMonomial);
                remainder = subtractPolynomials(polynomial1, multiplyPolynomials(quotient, polynomial2));
                if(remainder.getMonomials().firstEntry().getValue().getCoefficient() == 0.0)
                    remainder.getMonomials().pollFirstEntry();
                if(remainder.getMonomials().firstEntry()==null)
                    break;
                polynomial1=remainder;
            }
            polynomialsResult.add(quotientFinal);
            polynomialsResult.add(remainder);
        }
        return polynomialsResult;
    }
}
