package com.sangbango.project.services;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.sangbango.project.shared.dto.AgendaDto;
import com.sangbango.project.shared.dto.InvoiceDto;
import com.sangbango.project.shared.dto.PaymentNotifQrenContainerDto;
import com.sangbango.project.shared.dto.ProductDto;
import com.sangbango.project.shared.dto.RoleDto;
import com.sangbango.project.shared.dto.TransactionDto;
import com.sangbango.project.shared.dto.UserDto;

public interface UserService extends UserDetailsService {
	UserDto getUser(String email);
	UserDto getUserByUserId(String userId);
	RoleDto createRole(RoleDto role);
	RoleDto getRole(String roleId);
	UserDto createUser(UserDto user, String roleId);
	List<UserDto> getUsers();
	AgendaDto createAgenda(AgendaDto agendaDto);
	AgendaDto getByAgendaId(Long agendaId);
	List<AgendaDto> getAgenda();
	AgendaDto updateAgenda(Long agendaId, AgendaDto agendaDto);
	ProductDto createProduct(ProductDto product);
	List<ProductDto> getProducts();
	ProductDto getByProductId(String productId);
	ProductDto updateProduct(String productId, ProductDto productDto);
	List<InvoiceDto> getByInvoiceDate(String invoiceDate);
	InvoiceDto createInvoice(String productId);
	PaymentNotifQrenContainerDto createNotif(PaymentNotifQrenContainerDto notif);
	TransactionDto getTransactionInfo(String invoiceId);
	InvoiceDto getByInvoiceNumber(String invoiceNumber);
	List<TransactionDto> getTransactions();
	List<ProductDto> getSomeProducts(int page, int limit);
	List<TransactionDto> getInvoicesByCreatedBy(String createdBy, int page, int limit);
	TransactionDto putConfirmation(String transactionId);
	ProductDto disactivated(String productId);
	PaymentNotifQrenContainerDto findTransaction(String invoiceNumber);
	TransactionDto addInvoice(String invoiceNumber);
}
