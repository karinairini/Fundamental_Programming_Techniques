package ro.tuc.tp.logic;

import ro.tuc.tp.dataAccess.BillDAO;
import ro.tuc.tp.model.Bill;

import java.util.ArrayList;

/**
 * Aceasta clasa implementeaza logica pentru clasa Bill.
 */

public class BillBLL {
    private BillDAO billDAO;
    public BillBLL() {
        billDAO = new BillDAO();
    }
    public void insertBill(Bill bill) {
        billDAO.insertBill(bill);
    }
    public ArrayList<Bill> getAllBills() {
        return (ArrayList<Bill>) billDAO.findAll();
    }
}