package com.sangbango.project.ui.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
//import java.util.Arrays;
import java.util.Date;
import java.util.List;
//import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.sangbango.project.exceptions.UserServiceException;
import com.sangbango.project.services.UserService;
import com.sangbango.project.services.impl.UserServiceImpl;
import com.sangbango.project.shared.dto.AgendaDto;
import com.sangbango.project.shared.dto.InvoiceDto;
import com.sangbango.project.shared.dto.PaymentNotifQrenContainerDto;
import com.sangbango.project.shared.dto.ProductDto;
import com.sangbango.project.shared.dto.RoleDto;
import com.sangbango.project.shared.dto.TransactionDto;
import com.sangbango.project.shared.dto.UserDto;
import com.sangbango.project.ui.entitymodel.FileEntity;
import com.sangbango.project.ui.entitymodel.request.AgendaRequest;
import com.sangbango.project.ui.entitymodel.request.PaymentNotifQrenContainerRequest;
import com.sangbango.project.ui.entitymodel.request.ProductRequest;
import com.sangbango.project.ui.entitymodel.request.RoleRequest;
import com.sangbango.project.ui.entitymodel.request.UserRequest;
import com.sangbango.project.ui.entitymodel.response.AgendaResponse;
import com.sangbango.project.ui.entitymodel.response.ContentAgenda;
import com.sangbango.project.ui.entitymodel.response.ContentInvoice;
import com.sangbango.project.ui.entitymodel.response.ContentInvoices;
import com.sangbango.project.ui.entitymodel.response.ContentListAgenda;
import com.sangbango.project.ui.entitymodel.response.ContentProduct;
import com.sangbango.project.ui.entitymodel.response.ContentProducts;
import com.sangbango.project.ui.entitymodel.response.ContentTransaction;
import com.sangbango.project.ui.entitymodel.response.ContentTransactions;
import com.sangbango.project.ui.entitymodel.response.ContentUser;
import com.sangbango.project.ui.entitymodel.response.ContentUsers;
import com.sangbango.project.ui.entitymodel.response.FileResponse;
import com.sangbango.project.ui.entitymodel.response.InvoiceResponse;
import com.sangbango.project.ui.entitymodel.response.PaymentNotifQrenContainerResponse;
import com.sangbango.project.ui.entitymodel.response.ProductResponse;
import com.sangbango.project.ui.entitymodel.response.RoleResponse;
import com.sangbango.project.ui.entitymodel.response.TransactionResponse;
import com.sangbango.project.ui.entitymodel.response.UserResponse;

@RestController
public class UserController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	UserServiceImpl userServiceImpl;
	
	@GetMapping
	public String hello() {
		return "Welcome to my channel!";
	}
	
	@PostMapping(
			path = "/registration",
			consumes = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
			produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}
			)
	public UserResponse register(@RequestBody UserRequest user, @RequestParam(value = "roleId") String roleId) throws Exception {
		UserResponse returnValue = new UserResponse();
		
//		validation
		if (user.getEmail().isEmpty()) throw new UserServiceException("Email may not be null");
		if (user.getPassword().isEmpty()) throw new UserServiceException("Password may not be null");
		if (user.getPhoneNumber().isEmpty()) throw new UserServiceException("Phone may not be null");
		if (user.getName().isEmpty()) throw new UserServiceException("Name may not be null");
		
		ModelMapper modelMapper = new ModelMapper();
		UserDto userDto = modelMapper.map(user, UserDto.class);
		
		UserDto createdUser = userService.createUser(userDto, roleId);
		returnValue = modelMapper.map(createdUser, UserResponse.class);
		return returnValue;
	}
	
	@PostMapping(
			path = "/setrole",
			consumes = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
			produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}
			)
	public RoleResponse setRole(@RequestBody RoleRequest role) {
		RoleResponse returnValue = new RoleResponse();
		
//		validation
		if (role.getRoleName().isEmpty()) throw new UserServiceException("role name may not be null");
		
		ModelMapper modelMapper = new ModelMapper();
		RoleDto roleDto = modelMapper.map(role, RoleDto.class);
		
		RoleDto createdRole = userService.createRole(roleDto);
		returnValue = modelMapper.map(createdRole, RoleResponse.class);
		return returnValue;
	}
	
	@GetMapping(
			path = "/users/all", 
			produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
	public ContentUsers getUsers() {
		List<UserResponse> returnValue = new ArrayList<>();
		
		ContentUsers result = new ContentUsers();
		
		List<UserDto> users = userService.getUsers();
		
		if (users != null && !users.isEmpty()) {
			java.lang.reflect.Type listType = new TypeToken<List<UserResponse>>() {
			}.getType();
			returnValue = new ModelMapper().map(users, listType);
		}
		
		result.setContent(returnValue);
		result.setErrorCode("0");
		result.setErrorDesc("success");
		Date date = new Date();
		result.setTimestamp(new Timestamp(date.getTime()).toString());
		return result;
	}
	
	@GetMapping(path = "/users/userdetail", 
			produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
	public ContentUser getUserDetail(@RequestParam(value = "userId") String userId) {
		UserResponse returnValue = new UserResponse();
		
		ContentUser result = new ContentUser();
		
		UserDto userDto = userService.getUserByUserId(userId);
		
		if (userId == null) throw new UserServiceException("user id may not be null");
		
		if (userDto != null) {
			java.lang.reflect.Type listType = new TypeToken<UserResponse>() {
			}.getType();
			returnValue = new ModelMapper().map(userDto, listType);
		}

		result.setContent(returnValue);
		result.setErrorCode("0");
		result.setErrorDesc("success");
		Date date = new Date();
		result.setTimestamp(new Timestamp(date.getTime()).toString());
		return result;
	}
	
	@PostMapping(
			path = "/agenda",
			consumes = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
			produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}
			)
	public ContentAgenda createAgenda(@RequestBody AgendaRequest agenda) throws Exception {
		AgendaResponse returnValue = new AgendaResponse();
		
		ContentAgenda result = new ContentAgenda();
		
		ModelMapper modelMapper = new ModelMapper();
		AgendaDto agendaDto = modelMapper.map(agenda, AgendaDto.class);
		
		AgendaDto createdAgenda = userService.createAgenda(agendaDto);
		returnValue = modelMapper.map(createdAgenda, AgendaResponse.class);
		
		result.setContent(returnValue);
		result.setErrorCode("0");
		result.setErrorDesc("success");
		Date date = new Date();
		result.setTimestamp(new Timestamp(date.getTime()).toString());
		return result;
	}
	
	@GetMapping(
			path = "/agenda/all", 
			produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
	public ContentListAgenda getAgenda() {
		List<AgendaResponse> returnValue = new ArrayList<>();
		ContentListAgenda result = new ContentListAgenda();
		
		List<AgendaDto> agenda = userService.getAgenda();
		
		if (agenda != null && !agenda.isEmpty()) {
			java.lang.reflect.Type listType = new TypeToken<List<AgendaResponse>>() {
			}.getType();
			returnValue = new ModelMapper().map(agenda, listType);
		}
		
		result.setContent(returnValue);
		result.setErrorCode("0");
		result.setErrorDesc("success");
		Date date = new Date();
		result.setTimestamp(new Timestamp(date.getTime()).toString());
		return result;
	}
	
	@GetMapping(path = "/agenda/detail", 
			produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
	public ContentAgenda getAgendaDetail(@RequestParam(value = "agendaId") Long agendaId) {
		AgendaResponse returnValue = new AgendaResponse();
		
		ContentAgenda result = new ContentAgenda();
		
		if (agendaId == null) throw new UserServiceException("agenda id may not be null");
		
		AgendaDto agenda = userService.getByAgendaId(agendaId);
		if (agenda != null) {
			java.lang.reflect.Type listType = new TypeToken<AgendaResponse>() {
			}.getType();
			returnValue = new ModelMapper().map(agenda, listType);
		}
		
		result.setContent(returnValue);
		result.setErrorCode("0");
		result.setErrorDesc("success");
		Date date = new Date();
		result.setTimestamp(new Timestamp(date.getTime()).toString());
		return result;
	}
	
	@PutMapping(path = "/agenda/updatedetail",
	consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
	produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
	public ContentAgenda updateAgenda(@RequestParam(value = "agendaId") Long agendaId,
			@RequestBody AgendaRequest agenda) {
		AgendaResponse returnValue = new AgendaResponse();
		
		ContentAgenda result = new ContentAgenda();
		
		if (agendaId == null) throw new UserServiceException("agenda id may not be null");
		
		AgendaDto agendaDto = new AgendaDto();
		BeanUtils.copyProperties(agenda, agendaDto);
		
		AgendaDto updatedAgenda = userService.updateAgenda(agendaId, agendaDto);
		BeanUtils.copyProperties(updatedAgenda, returnValue);
		
		result.setContent(returnValue);
		result.setErrorCode("0");
		result.setErrorDesc("success");
		Date date = new Date();
		result.setTimestamp(new Timestamp(date.getTime()).toString());
		return result;
	}
	
	@PostMapping(
			path = "/product",
			consumes = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
			produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}
			)
	public ContentProduct createProduct(@RequestBody ProductRequest product) throws Exception {
		ProductResponse returnValue = new ProductResponse();
		
		ContentProduct result = new ContentProduct();
		
		ModelMapper modelMapper = new ModelMapper();
		ProductDto productDto = modelMapper.map(product, ProductDto.class);
		
		ProductDto createdProduct = userService.createProduct(productDto);
		returnValue = modelMapper.map(createdProduct, ProductResponse.class);
		
		result.setContent(returnValue);
		result.setErrorCode("0");
		result.setErrorDesc("success");
		Date date = new Date();
		result.setTimestamp(new Timestamp(date.getTime()).toString());
		return result;
	}
	
	@GetMapping(
			path = "/product/all",
			produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}
			)
	public ContentProducts getProducts() {
		List<ProductResponse> returnValue = new ArrayList<>();
		
		ContentProducts result = new ContentProducts();
		
		List<ProductDto> products = userService.getProducts();
		
		if (products != null && !products.isEmpty()) {
			java.lang.reflect.Type listType = new TypeToken<List<ProductResponse>>() {
			}.getType();
			returnValue = new ModelMapper().map(products, listType);
		}
		
		result.setContent(returnValue);
		result.setErrorCode("0");
		result.setErrorDesc("success");
		Date date = new Date();
		result.setTimestamp(new Timestamp(date.getTime()).toString());
		return result;
	}
	
	@GetMapping(
			path = "/product/someproducts",
			produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}
			)
	public ContentProducts getSomeProducts(
			@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "limit", defaultValue = "3") int limit) {
		List<ProductResponse> returnValue = new ArrayList<>();
		
		ContentProducts result = new ContentProducts();
		
		List<ProductDto> products = userService.getSomeProducts(page, limit);
		
		if (products != null && !products.isEmpty()) {
			java.lang.reflect.Type listType = new TypeToken<List<ProductResponse>>() {
			}.getType();
			returnValue = new ModelMapper().map(products, listType);
		}
		
		result.setContent(returnValue);
		result.setErrorCode("0");
		result.setErrorDesc("success");
		Date date = new Date();
		result.setTimestamp(new Timestamp(date.getTime()).toString());
		return result;
	}
	
	@GetMapping(path = "/product/detail", 
			produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
	public ContentProduct getProductDetail(@RequestParam(value = "productId") String productId) {
		ProductResponse returnValue = new ProductResponse();
		
		ContentProduct result = new ContentProduct();
		
		ProductDto productDto = userService.getByProductId(productId);
		
		if (productId == null) throw new UserServiceException("product id may not be null");
		
		if (productDto != null) {
			java.lang.reflect.Type listType = new TypeToken<ProductResponse>() {
			}.getType();
			returnValue = new ModelMapper().map(productDto, listType);
		}
		
		result.setContent(returnValue);
		result.setErrorCode("0");
		result.setErrorDesc("success");
		Date date = new Date();
		result.setTimestamp(new Timestamp(date.getTime()).toString());
		return result;
	}
	
	@PutMapping(path = "/product/updatedetail",
		consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
		produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
	public ContentProduct updateProduct(@RequestParam(value = "productId") String productId,
			@RequestBody ProductRequest product) {
		ProductResponse returnValue = new ProductResponse();
		
		ContentProduct result = new ContentProduct();
		
		if (productId == null) throw new UserServiceException("product id may not be null");
		
		ProductDto productDto = new ProductDto();
		
		BeanUtils.copyProperties(product, productDto);
		
		ProductDto updatedProduct = userService.updateProduct(productId, productDto);
		BeanUtils.copyProperties(updatedProduct, returnValue);
		
		result.setContent(returnValue);
		result.setErrorCode("0");
		result.setErrorDesc("success");
		Date date = new Date();
		result.setTimestamp(new Timestamp(date.getTime()).toString());
		return result;
	}
	
	@PostMapping(
			path = "/invoice",
			produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}
			)
	public ContentInvoice createInvoice(@RequestParam("productId") String productId) throws Exception {
//		Setting response 
		InvoiceResponse returnValue = new InvoiceResponse();
		ContentInvoice result = new ContentInvoice();
		
		if (productId == null) throw new UserServiceException("product id may not be null");
		
		ModelMapper modelMapper = new ModelMapper();
		
		InvoiceDto createdInvoice = userService.createInvoice(productId);
		returnValue = modelMapper.map(createdInvoice, InvoiceResponse.class);
		
		result.setContent(returnValue);
		result.setErrorCode("0");
		result.setErrorDesc("success");
		Date date = new Date();
		result.setTimestamp(new Timestamp(date.getTime()).toString());
		return result;
	}
	
	@GetMapping(
			path = "/invoice/getbyinvoiceNumber",
			produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
	public ContentInvoice getInvoiceByInvoiceNumber(@RequestParam("invoiceNumber") String invoiceNumber) throws Exception {
		
		InvoiceResponse returnValue = new InvoiceResponse();
		ContentInvoice result = new ContentInvoice();
		
		InvoiceDto invoice = userService.getByInvoiceNumber(invoiceNumber);
		if (invoiceNumber == null) throw new UserServiceException("invoice number may not be null");
		
		if (invoice != null) {
			java.lang.reflect.Type listType = new TypeToken<InvoiceResponse>() {
			}.getType();
			returnValue = new ModelMapper().map(invoice, listType);
		}
		
		result.setContent(returnValue);
		result.setErrorCode("0");
		result.setErrorDesc("success");
		Date date = new Date();
		result.setTimestamp(new Timestamp(date.getTime()).toString());
		return result;
	}
	
	@GetMapping(
			path = "/invoice/getbydate",
			produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
	public ContentInvoices getInvoiceByCreatedDate(@RequestParam("invoiceDate") String invoiceDate) throws Exception {
		
		List<InvoiceResponse> returnValue = new ArrayList<>();
		ContentInvoices result = new ContentInvoices();
		
		List<InvoiceDto> invoices = userService.getByInvoiceDate(invoiceDate);
		if (invoiceDate == null) throw new UserServiceException("invoice date may not be null");
		
		if (invoices != null && !invoices.isEmpty()) {
			java.lang.reflect.Type listType = new TypeToken<List<InvoiceResponse>>() {
			}.getType();
			returnValue = new ModelMapper().map(invoices, listType);
		}
		
		result.setContent(returnValue);
		result.setErrorCode("0");
		result.setErrorDesc("success");
		Date date = new Date();
		result.setTimestamp(new Timestamp(date.getTime()).toString());
		return result;
	}
	
	@GetMapping(
			path = "/invoice/getbycreatedby",
			produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
	public ContentTransactions getInvoiceByCreatedBy(
			@RequestParam("createdBy") String createdBy, 
			@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "limit", defaultValue = "5") int limit) throws Exception {
		
		List<TransactionResponse> returnValue = new ArrayList<>();
		ContentTransactions result = new ContentTransactions();
		
		List<TransactionDto> transactions = userService.getInvoicesByCreatedBy(createdBy, page, limit);
		if (createdBy == null) throw new UserServiceException("this parameter is require");
		
		if (transactions != null && !transactions.isEmpty()) {
			java.lang.reflect.Type listType = new TypeToken<List<TransactionResponse>>() {
			}.getType();
			returnValue = new ModelMapper().map(transactions, listType);
		}
		
		result.setContent(returnValue);
		result.setErrorCode("0");
		result.setErrorDesc("success");
		Date date = new Date();
		result.setTimestamp(new Timestamp(date.getTime()).toString());
		return result;
	}
	
	@PostMapping(
			path = "/qris/paymentnotifqren",
			consumes = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
			produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}
			)
	public PaymentNotifQrenContainerResponse createPaymentNotif(@RequestBody PaymentNotifQrenContainerRequest notif) throws Exception {
		PaymentNotifQrenContainerResponse returnValue = new PaymentNotifQrenContainerResponse();
		
		ModelMapper modelMapper = new ModelMapper();
		System.out.println("disini");
		PaymentNotifQrenContainerDto notifDto = modelMapper.map(notif, PaymentNotifQrenContainerDto.class);
		
		PaymentNotifQrenContainerDto createNotif = userService.createNotif(notifDto);
		returnValue = modelMapper.map(createNotif, PaymentNotifQrenContainerResponse.class);
		
		Date date = new Date();
		returnValue.setTimeStamp(new Timestamp(date.getTime()).toString());
		
		return returnValue;
	}
	
	@GetMapping(
			path = "/transaction",
			produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
	public ContentTransaction getTransaction(@RequestParam("invoiceNumber") String invoiceNumber) throws Exception {
		TransactionResponse returnValue = new TransactionResponse();
		ContentTransaction result = new ContentTransaction();
		
		TransactionDto transactionDto = userService.getTransactionInfo(invoiceNumber);
		if (invoiceNumber == null) throw new UserServiceException("invoice number may not be null");
		
		if (transactionDto != null) {
			java.lang.reflect.Type listType = new TypeToken<TransactionResponse>() {
			}.getType();
			returnValue = new ModelMapper().map(transactionDto, listType);
		}
		
		result.setContent(returnValue);
		result.setErrorCode("0");
		result.setErrorDesc("success");
		Date date = new Date();
		result.setTimestamp(new Timestamp(date.getTime()).toString());
		return result;
	}
	
	@GetMapping(
			path = "/transaction/all",
			produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
	public ContentTransactions getTransaction() {
		
		List<TransactionResponse> returnValue = new ArrayList<>();
		ContentTransactions result = new ContentTransactions();
		
		List<TransactionDto> transactions = userService.getTransactions();
		
		if (transactions != null && !transactions.isEmpty()) {
			java.lang.reflect.Type listType = new TypeToken<List<TransactionResponse>>() {
			}.getType();
			returnValue = new ModelMapper().map(transactions, listType);
		}
		
		result.setContent(returnValue);
		result.setErrorCode("0");
		result.setErrorDesc("success");
		Date date = new Date();
		result.setTimestamp(new Timestamp(date.getTime()).toString());
		return result;
	}
	
	@PutMapping(
			path = "/transaction/confirmation"
			)
	public ContentTransaction confirmTransaction(@RequestParam("transactionId") String transactionId) {
		TransactionResponse returnValue = new TransactionResponse();
		ContentTransaction result = new ContentTransaction();
		
		ModelMapper modelMapper = new ModelMapper();
		
		TransactionDto putConfirmation = userService.putConfirmation(transactionId);
		if (transactionId == null) throw new UserServiceException("transaction id may not be null");
		
		returnValue = modelMapper.map(putConfirmation, TransactionResponse.class);
		
		result.setContent(returnValue);
		result.setErrorCode("0");
		result.setErrorDesc("success");
		Date date = new Date();
		result.setTimestamp(new Timestamp(date.getTime()).toString());
		
		return result;
		
	}
	
	@PutMapping(
			path = "/product/disactivated"
			)
	public ContentProduct disactivated(@RequestParam("productId") String productId) {
		ProductResponse returnValue = new ProductResponse();
		ContentProduct result = new ContentProduct();
		
		ModelMapper modelMapper = new ModelMapper();
		
		ProductDto disactivated = userService.disactivated(productId);
		if (productId == null) throw new UserServiceException("product id may not be null");
		
		returnValue = modelMapper.map(disactivated, ProductResponse.class);
		
		result.setContent(returnValue);
		result.setErrorCode("0");
		result.setErrorDesc("disactivated");
		Date date = new Date();
		result.setTimestamp(new Timestamp(date.getTime()).toString());
		
		return result;
		
	}
	
	@RequestMapping(value = "/upload", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Object> upload (@RequestParam("file") MultipartFile file) throws IOException {
		File convertFile = new File("D:\\sts-bundle\\school-apps\\image\\"+file.getOriginalFilename());
		convertFile.createNewFile();
		FileOutputStream fout = new FileOutputStream(convertFile);
		fout.write(file.getBytes());
		fout.close();
		
		return new ResponseEntity<>("file is uploaded successfully!", HttpStatus.OK);
		
	}
	
	@PostMapping("/uploadFile")
    public FileResponse uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("productId") String productId) {
//		save image to local
		try {
			File convertFile = new File("D:\\sts-bundle\\school-apps\\image\\"+file.getOriginalFilename());
			convertFile.createNewFile();
			FileOutputStream fout = new FileOutputStream(convertFile);
			fout.write(file.getBytes());
			fout.close();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}
		
//		save image to database
    	FileEntity fileName = userServiceImpl.storeFile(file, productId);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName.getFileName())
                .toUriString();

        return new FileResponse(fileName.getFileId(), fileName.getFileName(), fileDownloadUri,
                file.getContentType(), file.getSize());
    }
	

//    @PostMapping("/uploadMultipleFiles")
//    public List<FileResponse> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
//        return Arrays.asList(files)
//                .stream()
//                .map(file -> uploadFile(file))
//                .collect(Collectors.toList());
//    }
    
    @GetMapping("/downloadFile/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        // Load file as Resource
        FileEntity databaseFile = userServiceImpl.getFile("bebek.jpg");

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(databaseFile.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + databaseFile.getFileName() + "\"")
                .body(new ByteArrayResource(databaseFile.getData()));
    }
}
