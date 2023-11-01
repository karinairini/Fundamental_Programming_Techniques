package ro.tuc.tp.dataAccess;

import ro.tuc.tp.model.Bill;

import java.util.List;

/**
 * Aceasta clasa extinde clasa AbstractDAO pe tipul de obiect Bill si implementeaza actiunea de a
 * vedea toate inregistrarile de acest tip.
 */

public class BillDAO extends AbstractDAO<Bill> {
    public List<Bill> findAll() {
        return super.findAll();
    }
    public void insertBill(Bill bill) {
        super.insert(bill);
    }
}
