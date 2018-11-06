package ekindergarten.repositories;

import ekindergarten.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    @Transactional
    @Modifying
    @Query ("Update Payment SET balance = (balance - 10 * (?2)) WHERE child.id = ?1")
    void updateBalanceOnAbsenceChange(long childId, int absenceDaysMultiplayer);

    Payment getPaymentByChildIdAndPaymentMonth(long childId, LocalDate paymentMonth);

    @Transactional
    @Modifying
    @Query ("UPDATE Payment SET balance = (-300 + balance), paymentMonth = ?1 WHERE paymentMonth != ?1 ")
    void updateBalanceOnMonthChange(LocalDate localDate);

}
