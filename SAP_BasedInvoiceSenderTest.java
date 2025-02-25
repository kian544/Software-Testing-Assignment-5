package org.example.Assignment;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;

public class SAP_BasedInvoiceSenderTest {

    @Test
    public void testWhenLowInvoicesSent() {
        FilterInvoice mockFilter = mock(FilterInvoice.class); // Mocked FilterInvoice
        SAP mockSAP = mock(SAP.class); // Mocked SAP
        Invoice invoice1 = mock(Invoice.class);
        Invoice invoice2 = mock(Invoice.class);
        List<Invoice> lowValueInvoices = Arrays.asList(invoice1, invoice2);
        when(mockFilter.lowValueInvoices()).thenReturn(lowValueInvoices); // Stubbed filter return
        SAP_BasedInvoiceSender sender = new SAP_BasedInvoiceSender(mockFilter, mockSAP); // Inject mocks
        sender.sendLowValuedInvoices(); // Call method
        verify(mockSAP).send(invoice1); // Verify SAP calls
        verify(mockSAP).send(invoice2);
    }

    @Test
    public void testWhenNoInvoices() {
        FilterInvoice mockFilter = mock(FilterInvoice.class); // Mocked FilterInvoice
        SAP mockSAP = mock(SAP.class); // Mocked SAP
        when(mockFilter.lowValueInvoices()).thenReturn(Collections.emptyList()); // Stubbed empty list
        SAP_BasedInvoiceSender sender = new SAP_BasedInvoiceSender(mockFilter, mockSAP); // Inject mocks
        sender.sendLowValuedInvoices(); // Call method
        verify(mockSAP, never()).send(any(Invoice.class)); //No SAP call
    }
}
