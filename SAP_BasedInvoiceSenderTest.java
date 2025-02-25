package org.example.Assignment;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class SAP_BasedInvoiceSenderTest {

    @Test
    public void testWhenLowInvoicesSent() {
        FilterInvoice mockFilter = mock(FilterInvoice.class); // Mock invoice filter
        SAP mockSAP = mock(SAP.class); // Mock SAP
        Invoice invoice1 = mock(Invoice.class);
        Invoice invoice2 = mock(Invoice.class);
        List<Invoice> lowValueInvoices = Arrays.asList(invoice1, invoice2);
        when(mockFilter.lowValueInvoices()).thenReturn(lowValueInvoices); // Return mock invoices

        SAP_BasedInvoiceSender sender = new SAP_BasedInvoiceSender(mockFilter, mockSAP);
        List<Invoice> failedInvoices = sender.sendLowValuedInvoices(); // Call method

        verify(mockSAP).send(invoice1); // Ensure invoice1 was sent
        verify(mockSAP).send(invoice2); // Ensure invoice2 was sent
        assertEquals(0, failedInvoices.size()); // No failures expected
    }

    @Test
    public void testThrowExceptionWhenBadInvoice() {
        FilterInvoice mockFilter = mock(FilterInvoice.class); // Mock invoice filter
        SAP mockSAP = mock(SAP.class); // Mock SAP
        Invoice goodInvoice = mock(Invoice.class);
        Invoice badInvoice = mock(Invoice.class);

        List<Invoice> lowValueInvoices = Arrays.asList(goodInvoice, badInvoice);
        when(mockFilter.lowValueInvoices()).thenReturn(lowValueInvoices); // Return mock invoices
        doThrow(new RuntimeException("SAP error")).when(mockSAP).send(badInvoice); // Simulate failure

        SAP_BasedInvoiceSender sender = new SAP_BasedInvoiceSender(mockFilter, mockSAP);
        List<Invoice> failedInvoices = sender.sendLowValuedInvoices(); // Call method

        verify(mockSAP).send(goodInvoice); // Ensure good invoice was sent
        verify(mockSAP).send(badInvoice); // Ensure bad invoice was attempted
        assertEquals(1, failedInvoices.size()); // One invoice should have failed
        assertEquals(badInvoice, failedInvoices.get(0)); // Ensure correct invoice failed
    }
}
