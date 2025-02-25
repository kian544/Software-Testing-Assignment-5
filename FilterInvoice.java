package org.example.Assignment;

import org.junit.Test;
import java.util.List;
import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertTrue;
import java.util.Arrays;

public class FilterInvoice {
    private QueryInvoicesDAO dao;
    public void setDao(QueryInvoicesDAO dao) { //Setter for dependency injection

        this.dao = dao;
    }
    public List<Invoice> lowValueInvoices() {
        List<Invoice> all = dao.all();
        return all.stream()
                .filter(invoice -> invoice.getValue() < 100)
                .collect(toList());
    }
    @Test
    public void filterInvoiceStubbedTest() {
        QueryInvoicesDAO stubDao = new QueryInvoicesDAO(null) { //Stubbed DAO to return test data
            @Override
            public List<Invoice> all() {
                return Arrays.asList(
                        new Invoice("INV-001", 50),
                        new Invoice("INV-002", 75),
                        new Invoice("INV-003", 125) // Should be filtered out due to OOB
                );
            }
        };
        FilterInvoice f = new FilterInvoice(); //Inject stubbed DAO
        f.setDao(stubDao);  // Injecting the stub

        List<Invoice> result = f.lowValueInvoices();


        assertTrue(result.stream().allMatch(invoice -> invoice.getValue() < 100));//Make sure invoices have a value less than 100
    }
}
