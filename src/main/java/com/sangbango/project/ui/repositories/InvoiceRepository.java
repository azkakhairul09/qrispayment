package com.sangbango.project.ui.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.sangbango.project.ui.entitymodel.InvoiceEntity;

@Repository
public interface InvoiceRepository extends PagingAndSortingRepository<InvoiceEntity, Long> {
	InvoiceEntity findByInvoiceId(String invoiceId);
	List<InvoiceEntity> findByInvoiceDate(String invoiceDate);
	List<InvoiceEntity> findByCreatedBy(String createdBy);
	InvoiceEntity findByInvoiceNumber(String invoiceNumber);
	InvoiceEntity findInvoiceIdByInvoiceNumber(String invoiceNumber);
	List<InvoiceEntity> findByCreatedDate(String createdDate);
	List<InvoiceEntity> findByDueTime(String dueTime);
	Page<InvoiceEntity> findByCreatedByAndStatusOrderByIdDesc(String createdBy, String status, Pageable pageableRequest);
}
