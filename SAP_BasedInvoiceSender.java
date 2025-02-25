package org.example.Assignment;

import java.util.ArrayList;
import java.util.List;

public class SAP_BasedInvoiceSender {

    private final FilterInvoice filter;
    private final SAP sap;

    public SAP_BasedInvoiceSender(FilterInvoice filter, SAP sap) {
        this.filter = filter;
        this.sap = sap;
    }

    public List<Invoice> sendLowValuedInvoices() { // Now returns a list of failed invoices
        List<Invoice> lowValuedInvoices = filter.lowValueInvoices(); // Get low-valued invoices
        List<Invoice> failedInvoices = new ArrayList<>(); // Store failed invoices

        for (Invoice invoice : lowValuedInvoices) {
            try {
                sap.send(invoice); // Try sending invoice
            } catch (Exception e) {
                failedInvoices.add(invoice); // If failed, store it
            }
        }
        return failedInvoices; // Return failed invoices
    }
}
