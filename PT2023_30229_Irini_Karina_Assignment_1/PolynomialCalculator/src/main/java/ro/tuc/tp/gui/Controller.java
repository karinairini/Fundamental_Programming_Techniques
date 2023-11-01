package ro.tuc.tp.gui;

import ro.tuc.tp.logic.Operations;
import ro.tuc.tp.model.Polynomial;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller implements ActionListener {
    private final View view;
    private final Operations operations = new Operations();
    public Controller(View v){
        this.view = v;
    }
    public static String resultOperation(Polynomial polynomial)
    {
        String result;
        if (polynomial.fromPolynomialToString().startsWith("+"))
            result = polynomial.fromPolynomialToString().substring(1);
        else
            result = polynomial.fromPolynomialToString();
        return result;
    }

    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if(command.equals("COMPUTE")) {
            if(view.getOperationRadioButton() != null && (view.getFirstPolynomialTextField() != null || view.getSecondPolynomialTextField() != null)){
                Polynomial polynomial1 = Polynomial.fromStringToPolynomial(view.getFirstPolynomialTextField().getText());
                Polynomial polynomial2 = Polynomial.fromStringToPolynomial(view.getSecondPolynomialTextField().getText());
                if (polynomial1 == null || polynomial2 == null) {
                    view.getResultPolynomialLabel().setText("Invalid polynomial input!");
                    return;
                }
                String operation = view.getOperationRadioButton();
                switch (operation) {
                    case "ADD" -> view.getResultPolynomialLabel().setText(Controller.resultOperation(operations.addPolynomials(polynomial1, polynomial2)));
                    case "SUBTRACT" -> view.getResultPolynomialLabel().setText(Controller.resultOperation(operations.subtractPolynomials(polynomial1, polynomial2)));
                    case "MULTIPLY" -> view.getResultPolynomialLabel().setText(Controller.resultOperation(operations.multiplyPolynomials(polynomial1, polynomial2)));
                    case "DIVIDE" -> {
                        String quotient="", remainder="";
                        if(operations.dividePolynomials(polynomial1,polynomial2) != null) {
                            quotient = Controller.resultOperation(operations.dividePolynomials(polynomial1, polynomial2).get(0));
                            remainder = Controller.resultOperation(operations.dividePolynomials(polynomial1, polynomial2).get(1));
                        }
                        view.getResultPolynomialLabel().setText(operations.dividePolynomials(polynomial1, polynomial2) == null ? "Cannot do this operation!" : "Q:" + quotient + " R:" + remainder);
                    }
                    case "DERIVE" -> view.getResultPolynomialLabel().setText(Controller.resultOperation(operations.derivePolynomials(polynomial1)));
                    case "INTEGRATE" -> view.getResultPolynomialLabel().setText(Controller.resultOperation(operations.integratePolynomial(polynomial1)) + "+C");
                }
            }
        }
    }
}
