package ekindergarten.cron;

import ekindergarten.service.PaymentService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class PaymentChecker {
    private final PaymentService paymentService;

    public PaymentChecker(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @Scheduled (cron = "0 0 * * * *")
    public void updatePaymentStatus() {
        paymentService.updateBalanceOnMonthChange();
    }
}
