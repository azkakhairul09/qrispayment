package com.sangbango.project.exceptions;

import java.util.Calendar;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.sangbango.project.ui.entitymodel.response.ErrorMessage;

@ControllerAdvice
public class AppExceptionHandler {
	HttpServletRequest request;
	
	
	@ExceptionHandler(value = {UserServiceException.class})
	public ResponseEntity<Object> handleUserServiceException(UserServiceException ex, HttpServletRequest request) {
		
		String queryString = request.getRequestURI();
		String url = "/sangbango-microservices/payment/v1";
		
		System.out.println(queryString);
		if (queryString.equals(url+"/registration")) 
		{
			String errorCode = "Err001";
			HttpStatus oke = HttpStatus.valueOf(200);
			Date date = Calendar.getInstance().getTime();
			
			ErrorMessage errorMessage = new ErrorMessage(errorCode, ex.getLocalizedMessage(), date);
			
			return new ResponseEntity<>(errorMessage, oke);	
		}
		else if (queryString.equals(url+"/setrole")) 
		{
			String errorCode = "Err002";
			HttpStatus oke = HttpStatus.valueOf(200);
			Date date = Calendar.getInstance().getTime();
			
			ErrorMessage errorMessage = new ErrorMessage(errorCode, ex.getLocalizedMessage(), date);
			
			return new ResponseEntity<>(errorMessage, oke);	
		}
		else if (queryString.equals(url+"/login")) 
		{
			String errorCode = "Err003";
			HttpStatus oke = HttpStatus.valueOf(200);
			Date date = Calendar.getInstance().getTime();
			
			ErrorMessage errorMessage = new ErrorMessage(errorCode, ex.getLocalizedMessage(), date);
			
			return new ResponseEntity<>(errorMessage, oke);	
		}
		else if (queryString.equals(url+"/users/all")) 
		{
			String errorCode = "Err004";
			HttpStatus oke = HttpStatus.valueOf(200);
			Date date = Calendar.getInstance().getTime();
			
			ErrorMessage errorMessage = new ErrorMessage(errorCode, ex.getLocalizedMessage(), date);
			
			return new ResponseEntity<>(errorMessage, oke);	
		}
		else if (queryString.equals(url+"/users/userdetail")) 
		{
			String errorCode = "Err005";
			HttpStatus oke = HttpStatus.valueOf(200);
			Date date = Calendar.getInstance().getTime();
			
			ErrorMessage errorMessage = new ErrorMessage(errorCode, ex.getLocalizedMessage(), date);
			
			return new ResponseEntity<>(errorMessage, oke);	
		}
		else if (queryString.equals(url+"/agenda")) 
		{
			String errorCode = "Err101";
			HttpStatus oke = HttpStatus.valueOf(200);
			Date date = Calendar.getInstance().getTime();
			
			ErrorMessage errorMessage = new ErrorMessage(errorCode, ex.getLocalizedMessage(), date);
			
			return new ResponseEntity<>(errorMessage, oke);	
		}
		else if (queryString.equals(url+"/agenda/detail")) 
		{
			String errorCode = "Err102";
			HttpStatus oke = HttpStatus.valueOf(200);
			Date date = Calendar.getInstance().getTime();
			
			ErrorMessage errorMessage = new ErrorMessage(errorCode, ex.getLocalizedMessage(), date);
			
			return new ResponseEntity<>(errorMessage, oke);	
		}
		else if (queryString.equals(url+"/agenda/all")) 
		{
			String errorCode = "Err103";
			HttpStatus oke = HttpStatus.valueOf(200);
			Date date = Calendar.getInstance().getTime();
			
			ErrorMessage errorMessage = new ErrorMessage(errorCode, ex.getLocalizedMessage(), date);
			
			return new ResponseEntity<>(errorMessage, oke);	
		}
		else if (queryString.equals(url+"/agenda/updatedetail")) 
		{
			String errorCode = "Err104";
			HttpStatus oke = HttpStatus.valueOf(200);
			Date date = Calendar.getInstance().getTime();
			
			ErrorMessage errorMessage = new ErrorMessage(errorCode, ex.getLocalizedMessage(), date);
			
			return new ResponseEntity<>(errorMessage, oke);	
		}
		else if (queryString.equals(url+"/product")) 
		{
			String errorCode = "Err201";
			HttpStatus oke = HttpStatus.valueOf(200);
			Date date = Calendar.getInstance().getTime();
			
			ErrorMessage errorMessage = new ErrorMessage(errorCode, ex.getLocalizedMessage(), date);
			
			return new ResponseEntity<>(errorMessage, oke);	
		}
		else if (queryString.equals(url+"/product/all")) 
		{
			String errorCode = "Err202";
			HttpStatus oke = HttpStatus.valueOf(200);
			Date date = Calendar.getInstance().getTime();
			
			ErrorMessage errorMessage = new ErrorMessage(errorCode, ex.getLocalizedMessage(), date);
			
			return new ResponseEntity<>(errorMessage, oke);	
		}
		else if (queryString.equals(url+"/product/someproducts")) 
		{
			String errorCode = "Err203";
			HttpStatus oke = HttpStatus.valueOf(200);
			Date date = Calendar.getInstance().getTime();
			
			ErrorMessage errorMessage = new ErrorMessage(errorCode, ex.getLocalizedMessage(), date);
			
			return new ResponseEntity<>(errorMessage, oke);	
		}
		else if (queryString.equals(url+"/product/detail")) 
		{
			String errorCode = "Err204";
			HttpStatus oke = HttpStatus.valueOf(200);
			Date date = Calendar.getInstance().getTime();
			
			ErrorMessage errorMessage = new ErrorMessage(errorCode, ex.getLocalizedMessage(), date);
			
			return new ResponseEntity<>(errorMessage, oke);	
		}
		else if (queryString.equals(url+"/product/updatedetail")) 
		{
			String errorCode = "Err205";
			HttpStatus oke = HttpStatus.valueOf(200);
			Date date = Calendar.getInstance().getTime();
			
			ErrorMessage errorMessage = new ErrorMessage(errorCode, ex.getLocalizedMessage(), date);
			
			return new ResponseEntity<>(errorMessage, oke);	
		}
		else if (queryString.equals(url+"/product/disactivated")) 
		{
			String errorCode = "Err206";
			HttpStatus oke = HttpStatus.valueOf(200);
			Date date = Calendar.getInstance().getTime();
			
			ErrorMessage errorMessage = new ErrorMessage(errorCode, ex.getLocalizedMessage(), date);
			
			return new ResponseEntity<>(errorMessage, oke);	
		}
		else if (queryString.equals(url+"/invoice")) 
		{
			String errorCode = "Err301";
			HttpStatus oke = HttpStatus.valueOf(200);
			Date date = Calendar.getInstance().getTime();
			
			ErrorMessage errorMessage = new ErrorMessage(errorCode, ex.getLocalizedMessage(), date);
			
			return new ResponseEntity<>(errorMessage, oke);	
		}
		else if (queryString.equals(url+"/invoice/getbyinvoicenumber")) 
		{
			String errorCode = "Err302";
			HttpStatus oke = HttpStatus.valueOf(200);
			Date date = Calendar.getInstance().getTime();
			
			ErrorMessage errorMessage = new ErrorMessage(errorCode, ex.getLocalizedMessage(), date);
			
			return new ResponseEntity<>(errorMessage, oke);	
		}
		else if (queryString.equals(url+"/invoice/getbydate")) 
		{
			String errorCode = "Err303";
			HttpStatus oke = HttpStatus.valueOf(200);
			Date date = Calendar.getInstance().getTime();
			
			ErrorMessage errorMessage = new ErrorMessage(errorCode, ex.getLocalizedMessage(), date);
			
			return new ResponseEntity<>(errorMessage, oke);	
		}
		else if (queryString.equals(url+"/invoice/getbycreatedby")) 
		{
			String errorCode = "Err304";
			HttpStatus oke = HttpStatus.valueOf(200);
			Date date = Calendar.getInstance().getTime();
			
			ErrorMessage errorMessage = new ErrorMessage(errorCode, ex.getLocalizedMessage(), date);
			
			return new ResponseEntity<>(errorMessage, oke);	
		}
		else if (queryString.equals(url+"/transaction")) 
		{
			String errorCode = "Err701";
			HttpStatus oke = HttpStatus.valueOf(200);
			Date date = Calendar.getInstance().getTime();
			
			ErrorMessage errorMessage = new ErrorMessage(errorCode, ex.getLocalizedMessage(), date);
			
			return new ResponseEntity<>(errorMessage, oke);	
		}
		else if (queryString.equals(url+"/transaction/all")) 
		{
			String errorCode = "Err702";
			HttpStatus oke = HttpStatus.valueOf(200);
			Date date = Calendar.getInstance().getTime();
			
			ErrorMessage errorMessage = new ErrorMessage(errorCode, ex.getLocalizedMessage(), date);
			
			return new ResponseEntity<>(errorMessage, oke);	
		}
		else if (queryString.equals(url+"/transaction/confirmation")) 
		{
			String errorCode = "Err703";
			HttpStatus oke = HttpStatus.valueOf(200);
			Date date = Calendar.getInstance().getTime();
			
			ErrorMessage errorMessage = new ErrorMessage(errorCode, ex.getLocalizedMessage(), date);
			
			return new ResponseEntity<>(errorMessage, oke);	
		} 
		else if (queryString.equals(url+"/transaction/findTransaction")) 
		{
			String errorCode = "Err704";
			HttpStatus oke = HttpStatus.valueOf(200);
			Date date = Calendar.getInstance().getTime();
			
			ErrorMessage errorMessage = new ErrorMessage(errorCode, ex.getLocalizedMessage(), date);
			
			return new ResponseEntity<>(errorMessage, oke);	
		}
		else if (queryString.equals(url+"/transaction/addInvoice")) 
		{
			String errorCode = "Err705";
			HttpStatus oke = HttpStatus.valueOf(200);
			Date date = Calendar.getInstance().getTime();
			
			ErrorMessage errorMessage = new ErrorMessage(errorCode, ex.getLocalizedMessage(), date);
			
			return new ResponseEntity<>(errorMessage, oke);	
		}
		return null;		
	}
	
	@ExceptionHandler(value = {Exception.class})
	public ResponseEntity<Object> handleOtherException(Exception ex, HttpServletRequest request) {
		
		String errorCode = "E099";
		
		HttpStatus oke = HttpStatus.valueOf(200);
		Date date = Calendar.getInstance().getTime();
		
		ErrorMessage errorMessage = new ErrorMessage(errorCode, ex.getLocalizedMessage(), date);
		
		return new ResponseEntity<>(errorMessage, oke);
	}
}
