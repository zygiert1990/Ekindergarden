package ekindergarten.service;

import ekindergarten.repositories.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
public class PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;

    public void updateChildBalance(long id, int absenceDaysMultiplayer) {
        paymentRepository.updateBalanceOnAbsenceChange(
                id,
                absenceDaysMultiplayer);
    }

    public BigDecimal getPaymentByChildIdAndCurrentMonth(long childId) {
        return paymentRepository
                .getPaymentByChildIdAndPaymentMonth(childId, LocalDate.now().withDayOfMonth(1))
                .getBalance();
    }

    public void updateBalanceOnMonthChange() {
        paymentRepository.updateBalanceOnMonthChange(LocalDate.now().withDayOfMonth(1));
    }
}
