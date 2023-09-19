package com.xyzcorp.structuredconcurrency;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;

/*
 * All or nothing with threads ShutdownOnFailure
 * If a thread fails while another is running it just stops execution of all threads
 *
 * ShutdownOnSuccess stops executing when a thread finishes
 * "We got what we wanted from one thread, we're not going to wait around for the other threads"
 */
public class AccountingServiceTest {

    @Test
    void testStructuredConcurrency() throws ExecutionException, InterruptedException {
        UserService userService = new UserService();
        InvoiceService invoiceService = new InvoiceService();
        AccountingService accountingService = new AccountingService(userService, invoiceService);
        UserInvoices userInvoice = accountingService.findAllInvoicesByUser(90L);
        Assertions.assertThat(userInvoice.invoices()).hasSize(3);
        Assertions.assertThat(userInvoice.user().firstName()).isEqualTo("Simon");
        Assertions.assertThat(userInvoice.user().lastName()).isEqualTo("Roberts");
    }

    @Test
    void testStructuredConcurrencyWithError() {
        UserService userService = new UserService();
        InvoiceService invoiceService = new InvoiceService();
        AccountingService accountingService = new AccountingService(userService, invoiceService);
        Assertions.assertThatThrownBy(() -> accountingService.findAllInvoicesByUserWithFailedUserService(90L))
            .isInstanceOf(ExecutionException.class);
    }

    @Test
    void testStructuredConcurrencyWithErrorAndLongerInvoiceService() {
        UserService userService = new UserService();
        InvoiceService invoiceService = new InvoiceService();
        AccountingService accountingService = new AccountingService(userService, invoiceService);
        Assertions.assertThatThrownBy(() -> accountingService.findAllInvoicesByUserWithFailedUserService(90L))
            .isInstanceOf(ExecutionException.class);
    }

    @Test
    void testStructuredConcurrencyWithOnSuccess() throws ExecutionException, InterruptedException {
        UserService userService = new UserService();
        InvoiceService invoiceService = new InvoiceService();
        AccountingService accountingService = new AccountingService(userService, invoiceService);
        String result = accountingService.findAllEitherUserOrInvoices(90L);
        Assertions.assertThat(result).isEqualTo("User: Simon Roberts");
    }

    @Test
    void testStructuredConcurrencyWithOnSuccessWithUserServiceLatency() throws ExecutionException, InterruptedException {
        UserService userService = new UserService();
        InvoiceService invoiceService = new InvoiceService();
        AccountingService accountingService = new AccountingService(userService, invoiceService);
        String result = accountingService.findAllEitherUserOrInvoicesFromUserServiceWithLatency(90L);
        String expected = "A list of [Invoice[number=402, amount=1120.0], Invoice[number=1402, amount=1220.0], Invoice[number=671, amount=1220.0]]";
        Assertions.assertThat(result).isEqualTo(expected);
    }
}
