package org.example.Assignment;

import org.junit.Test;

import java.util.List;
import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertTrue;


public class FilterInvoice {
    QueryInvoicesDAO dao;
    Database db;

    // We want to stub the dao to avoid interacting with database, however it is hard to do so, since dao is initialized internally
    // we need some way to inject dependency which is a stub, so we don't interact with database explicitly
    // we want it to depend on concretion, but only an abstraction.
    public FilterInvoice() {
        // this class doesn't need db, only dao needs it... there is a tight coupling
        // this is called dependency instantiation not injection
        this.db = new Database();
        this.dao = new QueryInvoicesDAO(db);
    }

    public List<Invoice> lowValueInvoices() {
        List<Invoice> all = dao.all();

        return all.stream()
                .filter(invoice -> invoice.getValue() < 100)
                .collect(toList());
    }
    @Test
    public void filterInvoiceTest(){
        Database db = new Database();
        QueryInvoicesDAO dao = new QueryInvoicesDAO(db);
        FilterInvoice f = new FilterInvoice();
        List<Invoice> result = f.lowValueInvoices();
        assertTrue(result.stream().allMatch(invoice -> invoice.getValue()<100));
    }
}