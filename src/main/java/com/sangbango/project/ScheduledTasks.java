package com.sangbango.project;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.sangbango.project.shared.dto.InvoiceDto;
import com.sangbango.project.ui.entitymodel.InvoiceEntity;
import com.sangbango.project.ui.repositories.InvoiceRepository;

@Component
public class ScheduledTasks {
	
	@Autowired
	InvoiceRepository invoiceRepository;
	
	@Scheduled(cron = "0 0 1 * * *")
	public InvoiceDto changeExpiredInvoice() {
		InvoiceDto returnValue = new InvoiceDto();
		
//		setting time
		final String expiredTime = "yyyy-MM-dd";
		SimpleDateFormat timeFormat = new SimpleDateFormat(expiredTime);
		timeFormat.setTimeZone(TimeZone.getTimeZone("GMT+7"));
		
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		String dueTime = timeFormat.format(cal.getTime());
		
//		get list of invoice
		List<InvoiceEntity> invoiceEntity = invoiceRepository.findByDueTime(dueTime);
		
//		looping invoices
		for (InvoiceEntity invoices : invoiceEntity) {
			if (invoices.getStatus().equals("pending")) {
				invoices.setStatus("expired");
				invoices.setDescription("invoice expired");
				
//				setting modified date
				final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
				SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
				formatter.setTimeZone(TimeZone.getTimeZone("GMT+7"));
				 
				Calendar currentTime = Calendar.getInstance();
				
				String modifiedDate = formatter.format(currentTime.getTime());
				invoices.setModifiedDate(modifiedDate);
				
				InvoiceEntity invoiceUpdate = invoiceRepository.save(invoices);
				BeanUtils.copyProperties(invoiceUpdate, returnValue);
			}
		}
		
		return returnValue;
	}
}
