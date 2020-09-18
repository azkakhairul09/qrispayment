package com.sangbango.project.ui.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sangbango.project.ui.entitymodel.PaymentNotifQrenContainer;

@Repository
public interface PaymentNotifQrenContainerRepository extends JpaRepository<PaymentNotifQrenContainer, Long> {
	PaymentNotifQrenContainer findByInvoice(String invoiceId);
}
