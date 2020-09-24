package com.sangbango.project.services.impl;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext; 
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sangbango.project.exceptions.FileStorageException;
import com.sangbango.project.exceptions.UserServiceException;
import com.sangbango.project.services.UserService;
import com.sangbango.project.shared.Utils;
import com.sangbango.project.shared.dto.AddressDto;
import com.sangbango.project.shared.dto.AgendaDto;
import com.sangbango.project.shared.dto.InvoiceDto;
import com.sangbango.project.shared.dto.PaymentNotifQrenContainerDto;
import com.sangbango.project.shared.dto.ProductDto;
import com.sangbango.project.shared.dto.QrenInvoiceDto;
import com.sangbango.project.shared.dto.RoleDto;
import com.sangbango.project.shared.dto.TransactionDto;
import com.sangbango.project.shared.dto.UserDto;
import com.sangbango.project.ui.entitymodel.AddressEntity;
import com.sangbango.project.ui.entitymodel.AgendaEntity;
import com.sangbango.project.ui.entitymodel.FileEntity;
import com.sangbango.project.ui.entitymodel.InvoiceEntity;
import com.sangbango.project.ui.entitymodel.PaymentNotifQrenContainer;
import com.sangbango.project.ui.entitymodel.ProductEntity;
import com.sangbango.project.ui.entitymodel.RoleEntity;
import com.sangbango.project.ui.entitymodel.TransactionEntity;
import com.sangbango.project.ui.entitymodel.UserEntity;
import com.sangbango.project.ui.repositories.AddressRepository;
import com.sangbango.project.ui.repositories.AgendaRepository;
import com.sangbango.project.ui.repositories.FileRepository;
import com.sangbango.project.ui.repositories.InvoiceRepository;
import com.sangbango.project.ui.repositories.PaymentNotifQrenContainerRepository;
import com.sangbango.project.ui.repositories.ProductRepository;
import com.sangbango.project.ui.repositories.RoleRepository;
import com.sangbango.project.ui.repositories.TransactionRepository;
import com.sangbango.project.ui.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	AddressRepository addressRepository;
	
	@Autowired
	AgendaRepository agendaRepository;
	
	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	InvoiceRepository invoiceRepository;
	
	@Autowired
	PaymentNotifQrenContainerRepository paymentNotifQrenContainerRepository;
	
	@Autowired
	TransactionRepository transactionRepository;
	
	@Autowired
	FileRepository fileRepository;
	
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	Utils utils;

	private AgendaEntity agenda;
	
	@Override
	public UserDetails loadUserByUsername(String email){
		// TODO Auto-generated method stub
		UserEntity userEntity = userRepository.findByEmail(email);
		
		if (userEntity == null) throw new UsernameNotFoundException("user not found");
		
		return new User(userEntity.getEmail(), userEntity.getEncryptPassword(), 
				userEntity.getIsActive(),
				true, true,
				true, new ArrayList<>());
	}

	@Override
	public UserDto getUser(String email) {
		// TODO Auto-generated method stub
		
//		query search user
		UserEntity userEntity = userRepository.findByEmail(email);
		
		if (userEntity == null) throw new UsernameNotFoundException("user not found");
		
		UserDto returnValue = new UserDto();
		
//		get users data
		BeanUtils.copyProperties(userEntity, returnValue);
				
		return returnValue;
	}
	
	@Override
	public List<UserDto> getUsers() {
		// TODO Auto-generated method stub
		List<UserDto> returnValue = new ArrayList<UserDto>();
		
		ModelMapper modelMapper = new ModelMapper();
		
//		query search users
		List<UserEntity> users = userRepository.findUserByIsActiveOrderByCreatedDateDesc(true);
		if (users == null) throw new UsernameNotFoundException("no records");
		
//		Looping users data
		for (UserEntity userEntity : users) {
			returnValue.add(modelMapper.map(userEntity, UserDto.class) );
		}
		
		return returnValue;
	}

	@Override
	public UserDto createUser(UserDto user, String roleId) {
		// TODO Auto-generated method stub

//		user data
		UserEntity userEntity = userRepository.findByEmail(user.getEmail());
		
		if (userEntity != null) throw new UserServiceException("email is already exist");
		
		ModelMapper modelMapper = new ModelMapper();
		UserEntity userData = modelMapper.map(user, UserEntity.class);
		
//		address data
		AddressDto address = user.getAddress();
		address.setAddressId("ADR-"+utils.generateId(3));
		
		AddressEntity addressEntity = modelMapper.map(address, AddressEntity.class);
		userData.setAddress(addressEntity);
		addressRepository.save(addressEntity);
		
//		role data
		RoleEntity roleEntity = roleRepository.findByRoleId(roleId);
		if (roleEntity == null) throw new UserServiceException("role is empty");
		
		userData.setRole(roleEntity);
		
//		user data
		String id = utils.generateId(5);
		userData.setEncryptPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		
		userData.setUserId("USR-"+id);
		userData.setIsActive(true);
		userData.setLoginTime("0");
		if (roleEntity.getRoleName().equals("Administrator")) {
			userData.setMerchantId("195238151618");
		} else {
			userData.setMerchantId("");
		}
		
//		setting user image
		userData.setUserImage("https://hosting.photobucket.com/images/i/sangbango/pngtree_businessman_user_avatar_free_vector_png_image_1538405.jpg?width=1920&height=1080&fit=bounds");
		
//		setting create date
		final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
		formatter.setTimeZone(TimeZone.getTimeZone("GMT+7"));
		 
		Calendar currentTime = Calendar.getInstance();
		
		String createdDate = formatter.format(currentTime.getTime());
		userData.setCreatedDate(createdDate);
		
//		save user data
		UserEntity storeOfUser = userRepository.save(userData);
		
		UserDto returnValue = modelMapper.map(storeOfUser, UserDto.class);
		
		return returnValue;
	} 

	@Override
	public UserDto getUserByUserId(String userId) {
		// TODO Auto-generated method stub
		UserDto returnValue = new UserDto();
		
		ModelMapper modelMapper = new ModelMapper();
		
//		query search user
		UserEntity userEntity = userRepository.findUserByUserId(userId);
		
		if (userEntity == null) throw new UserServiceException(
				"user not found");
		
//		get user
		returnValue = modelMapper.map(userEntity, UserDto.class);
		
		return returnValue;
	}

	@Override
	public RoleDto createRole(RoleDto role) {
		// TODO Auto-generated method stub
		
		//Validation
		RoleEntity roleEntity = roleRepository.findByRoleName(role.getRoleName());
		
		if (roleEntity != null) throw new UserServiceException("role is already exist");
				
		ModelMapper modelMapper = new ModelMapper();
		RoleEntity dataRole = modelMapper.map(role, RoleEntity.class);
		
//		set role id
		String id = utils.generateId(2);
		dataRole.setRoleId("RL-"+id);
		
//		save role data
		RoleEntity storeOfRole = roleRepository.save(dataRole);
		RoleDto returnValue = modelMapper.map(storeOfRole, RoleDto.class);
		
		return returnValue;
	}

	@Override
	public RoleDto getRole(String roleId) {
		// TODO Auto-generated method stub
		RoleDto returnValue = new RoleDto();
		
//		query search role
		RoleEntity roleEntity = roleRepository.findByRoleId(roleId);
		
		if (roleEntity == null) throw new UserServiceException(
				"role not found");
		
//		get role data
		BeanUtils.copyProperties(roleEntity, returnValue);
		
		return returnValue;
	}

	@Override
	public AgendaDto createAgenda(AgendaDto agendaDto) {
		// TODO Auto-generated method stub
		ModelMapper modelMapper = new ModelMapper();
		agenda = modelMapper.map(agendaDto, AgendaEntity.class);
		
		if (agenda != null) throw new UserServiceException("agenda is already exist");
		
//      role checking
		SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        String email = authentication.getName();
        
        UserEntity user = userRepository.findByEmail(email);
        UserEntity userRole = userRepository.findRoleByUserId(user.getUserId());
        
        String role = userRole.getRole().getRoleName();
        if (!role.equals("Administrator")) throw new UserServiceException ("access denied!, " +role+ " not allowed");
        
//      save createdBy
        agenda.setCreatedBy(authentication.getName());      
        
//		setting createdDate
		final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
		formatter.setTimeZone(TimeZone.getTimeZone("GMT+7"));
		 
		Calendar currentTime = Calendar.getInstance();
		
		String createdDate = formatter.format(currentTime.getTime());
		agenda.setCreatedDate(createdDate);
		
//		setting image uploaded
		agenda.setImage("https://hosting.photobucket.com/images/i/sangbango/product_icon.jpg?width=1920&height=1080&fit=bounds");
		
//		save agenda
		AgendaEntity storeOfAgenda = agendaRepository.save(agenda);
		AgendaDto returnValue = modelMapper.map(storeOfAgenda, AgendaDto.class);
		
		return returnValue;
	}

	@Override
	public AgendaDto getByAgendaId(Long id) {
		// TODO Auto-generated method stub
		AgendaDto returnValue = new AgendaDto();
		
//		query search agenda
		AgendaEntity agendaEntity = agendaRepository.findAgendaById(id);
		
		if (agendaEntity == null) throw new UserServiceException(
				"agenda not found");
		
//		get agenda data
		BeanUtils.copyProperties(agendaEntity, returnValue);
		
		return returnValue;
	}

	@Override
	public List<AgendaDto> getAgenda() {
		// TODO Auto-generated method stub	
        
		List<AgendaDto> returnValue = new ArrayList<AgendaDto>();
		
		ModelMapper modelMapper = new ModelMapper();
		
//		query search agenda
		List<AgendaEntity> agenda = agendaRepository.findAllByOrderByCreatedDateDesc();
		if (agenda == null) throw new UserServiceException(
				"no records");
		
//		Looping users data
		for (AgendaEntity agendaEntity : agenda) {
			returnValue.add(modelMapper.map(agendaEntity, AgendaDto.class) );
		}
		
		return returnValue;
	}

	@Override
	public AgendaDto updateAgenda(Long agendaId, AgendaDto agendaDto) {
		// TODO Auto-generated method stub
		AgendaDto returnValue = new AgendaDto();
		
//      role checking
		SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        String email = authentication.getName();
        
        UserEntity user = userRepository.findByEmail(email);
        UserEntity userRole = userRepository.findRoleByUserId(user.getUserId());
        
        String role = userRole.getRole().getRoleName();
        if (!role.equals("Administrator")) throw new UserServiceException ("access denied!, " +role+ " not allowed");
		
		final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
		formatter.setTimeZone(TimeZone.getTimeZone("GMT+7"));
		 
		Calendar currentTime = Calendar.getInstance(); 
		String timeEdited = formatter.format(currentTime.getTime());
		
		AgendaEntity agenda = agendaRepository.findAgendaById(agendaId);
		if (agenda == null) throw new UserServiceException("No record found");
		
		agenda.setTitle(agendaDto.getTitle());
		agenda.setDescription(agenda.getDescription());
		agenda.setLocation(agendaDto.getLocation());
		agenda.setUpdatedDate(timeEdited);
		
		AgendaEntity updated = agendaRepository.save(agenda);
		BeanUtils.copyProperties(updated, returnValue);
		
		return returnValue;
	}

	@Override
	public ProductDto createProduct(ProductDto productDto) {
		// TODO Auto-generated method stub
		ModelMapper modelMapper = new ModelMapper();
		ProductEntity product = modelMapper.map(productDto, ProductEntity.class);
		
//      role checking
		SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        String email = authentication.getName();
        
        UserEntity user = userRepository.findByEmail(email);
        
        UserEntity userRole = userRepository.findRoleByUserId(user.getUserId());
        
        String role = userRole.getRole().getRoleName();
        if (!role.equals("Administrator")) throw new UserServiceException ("access denied!, " +role+ " not allowed");
        
        if (role.equals("Administrator")) 
        {
			product.setMerchantId(user.getMerchantId());
		} 
        
//      save createdBy
        product.setCreatedBy(authentication.getName());      
        
//		setting createdDate
		final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
		formatter.setTimeZone(TimeZone.getTimeZone("GMT+7"));
		 
		Calendar currentTime = Calendar.getInstance();
		
		String createdDate = formatter.format(currentTime.getTime());
		product.setCreatedDate(createdDate);
		
//		setting product image
		product.setProductImage("https://hosting.photobucket.com/images/i/sangbango/product_icon.jpg?width=1920&height=1080&fit=bounds");
		
//		save product
		String id = utils.generateId(3);
		product.setProductId("PD-"+id);
		double doubleNumber = product.getPrice() * 0.025;
		String doubleAsString = String.valueOf(doubleNumber);
		int indexOfDecimal = doubleAsString.indexOf(".");
		
		product.setAdminPrice(Double.valueOf(doubleAsString.substring(0, indexOfDecimal)));
		product.setTotalPrice(product.getPrice() + product.getAdminPrice());
		product.setIsActive(true);
		ProductEntity storeOfProduct = productRepository.save(product);
		ProductDto returnValue = modelMapper.map(storeOfProduct, ProductDto.class);
		
		return returnValue;
	}

	@Override
	public List<ProductDto> getProducts() {
		// TODO Auto-generated method stub
		List<ProductDto> returnValue = new ArrayList<ProductDto>();
		
		ModelMapper modelMapper = new ModelMapper();
		
//		query search products
		List<ProductEntity> products = productRepository.findByIsActiveTrueOrderByCreatedDateDesc();
		if (products == null) throw new UserServiceException("no records");
		
//		Looping products data
		for (ProductEntity productEntity : products) {
			returnValue.add(modelMapper.map(productEntity, ProductDto.class) );
		}
		
		return returnValue;
	}

	@Override
	public ProductDto getByProductId(String productId) {
		// TODO Auto-generated method stub
		ProductDto returnValue = new ProductDto();
			
		ModelMapper modelMapper = new ModelMapper();
			
//		query search product
		ProductEntity product = productRepository.findByProductId(productId);
			
		if (product == null) throw new UserServiceException(
			"product not found");
			
//		get product
		returnValue = modelMapper.map(product, ProductDto.class);
		return returnValue;
	}
	
	@Override
	public ProductDto updateProduct(String productId, ProductDto productDto) {
		// TODO Auto-generated method stub
		ModelMapper modelMapper = new ModelMapper();
		
//      role checking
		SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        String email = authentication.getName();
        
        UserEntity user = userRepository.findByEmail(email);
        UserEntity userRole = userRepository.findRoleByUserId(user.getUserId());
        
        String role = userRole.getRole().getRoleName();
        if (!role.equals("Administrator")) throw new UserServiceException ("access denied!, " +role+ " not allowed");
		
		final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
		formatter.setTimeZone(TimeZone.getTimeZone("GMT+7"));
		 
		Calendar currentTime = Calendar.getInstance(); 
		String timeEdited = formatter.format(currentTime.getTime());
		
		ProductEntity product = productRepository.findByProductId(productId);
		if (product == null) throw new UserServiceException("No record found");
		
//		save product data
		double doubleNumber = product.getPrice() * 0.025;
		String doubleAsString = String.valueOf(doubleNumber);
		int indexOfDecimal = doubleAsString.indexOf(".");

		product.setAdminPrice(Double.valueOf(doubleAsString.substring(0, indexOfDecimal)));
		product.setTotalPrice(product.getPrice() + product.getAdminPrice());
		product.setUpdatedDate(timeEdited);
		
		ProductEntity storeOfProduct = productRepository.save(product);
		ProductDto returnValue = modelMapper.map(storeOfProduct, ProductDto.class);
		
		return returnValue;
	}
	
	@Override
	public ProductDto disactivated(String productId) {
		// TODO Auto-generated method stub
		ModelMapper modelMapper = new ModelMapper();
		
//      role checking
		SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        String email = authentication.getName();
        
        UserEntity user = userRepository.findByEmail(email);
        UserEntity userRole = userRepository.findRoleByUserId(user.getUserId());
        
        String role = userRole.getRole().getRoleName();
        if (!role.equals("Administrator")) throw new UserServiceException ("access denied!, " +role+ " not allowed");
        
        ProductEntity product = productRepository.findByProductIdAndIsActiveTrue(productId);
        if (product == null) throw new UserServiceException("No record found");
        
        if (product.getIsActive() == true) {
        	product.setIsActive(false);
		}
        
        ProductEntity disactivated = productRepository.save(product);
        ProductDto returnValue = modelMapper.map(disactivated, ProductDto.class);
		
		return returnValue;
	}

	@Override
	public List<ProductDto> getSomeProducts(int page, int limit) {
		// TODO Auto-generated method stub
		List<ProductDto> returnValue = new ArrayList<ProductDto>();
		
//		paging
		if (page>0) page = page-1;
		Pageable pageableRequest = PageRequest.of(page, limit);
		
		Page<ProductEntity> productsPage = productRepository.findByIsActiveTrueOrderByIdDesc(pageableRequest);
		List<ProductEntity> products = productsPage.getContent();
		
		if (products == null) throw new UserServiceException(
				"no records");
		
		for (ProductEntity productEntity : products) {
			ProductDto productDto = new ProductDto();
			BeanUtils.copyProperties(productEntity, productDto);
			returnValue.add(productDto);
		}
		
		return returnValue;
	}	

	@Override
	public InvoiceDto createInvoice(String productId) {
		// TODO Auto-generated method stub
		ModelMapper modelMapper = new ModelMapper();
		InvoiceEntity invoiceEntity = new InvoiceEntity();
		
//      user checking
		SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        String email = authentication.getName();
        
//      save createdBy
        UserEntity user = userRepository.findByEmail(email);
        invoiceEntity.setCreatedBy(user.getName());  
        
//      get product by id
        ProductEntity product = productRepository.findByProductId(productId);
        if (product == null) throw new UserServiceException("product not found");
        invoiceEntity.setProduct(product);
        
        Date date = new Date();
		LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		int month = localDate.getMonthValue();
		String monthValue = "";
		int year = localDate.getYear();
		int day = localDate.getDayOfMonth();
		switch (month) {
		  case 1:
			  monthValue = "Januari";
			  break;
		  case 2:
			  monthValue = "Februari";
			  break;
		  case 3:
			  monthValue = "Maret";
			  break;
		  case 4:
			  monthValue = "April";
			  break;
		  case 5:
			  monthValue = "Mei";
			  break;
		  case 6:
			  monthValue = "Juni";
			  break;
		  case 7:
			  monthValue = "Juli";
			  break;
		  case 8:
			  monthValue = "Agustus";
			  break;
		  case 9:
			  monthValue = "September";
			  break;
		  case 10:
			  monthValue = "Oktober";
			  break;
		  case 11:
			  monthValue = "November";
			  break;
		  case 12:
			  monthValue = "Desember";
			  break;
		}
		
        String generateInvoiceId = utils.generateId(7);
		String random = utils.generateId(1);
		invoiceEntity.setInvoiceNumber("INV-"+month+day+"-"+generateInvoiceId+"-"+random);
		
//      send request to create QRIS from QREN
        try {		        	
//			setting parameter input
			QrenInvoiceDto qrenInvoiceDto = new QrenInvoiceDto();
			qrenInvoiceDto.setMerchantApiKey(product.getMerchantId());
			String nominal = String.format("%.0f", product.getTotalPrice());
			qrenInvoiceDto.setNominal(nominal);
			qrenInvoiceDto.setStaticQr("0");
			qrenInvoiceDto.setInvoiceName("Invoice pembayaran/pembelian "+product.getProductName());
			qrenInvoiceDto.setQrGaruda("1");
			qrenInvoiceDto.setInfo("Pembelian/Pembayaran "+product.getProductName()+" dengan metode QR Payment");
			String generateTrxId = utils.generateId(5);
			final String DATE_FORMAT = "yyyyMMdd";
			SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
			dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+7"));
			 
			Calendar currentDate = Calendar.getInstance();
			String curdate = dateFormat.format(currentDate.getTime());
			
			final String TIME_FORMAT = "HHmmss";
			SimpleDateFormat timeFormat = new SimpleDateFormat(TIME_FORMAT);
			timeFormat.setTimeZone(TimeZone.getTimeZone("GMT+7"));
			 
			Calendar currentTime = Calendar.getInstance();
			String time = timeFormat.format(currentTime.getTime());
			
			qrenInvoiceDto.setTrxId(curdate+time+generateTrxId);
			
//			configure api request
			ObjectMapper obj = new ObjectMapper();
			
			String url = "https://qren-api.tmoney.co.id/paybyqr/createinvoice/";
			String json = obj.writeValueAsString(qrenInvoiceDto);
			
			
			URL uri = new URL(url);
			HttpURLConnection connection = (HttpURLConnection) uri.openConnection();
			
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Authorization", 
					"Basic dG1vbmV5OmZmODY2ZjViNjE1NGJiYjdkOTc4ZTUyNDNiNDkzMjBiMGQxYWQ2N2M=");
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setDoInput(true);
			connection.setDoOutput(true);
			
			OutputStream os = connection.getOutputStream();
			os.write(json.getBytes("UTF-8"));
			os.close();
			
			InputStream in = new BufferedInputStream(connection.getInputStream());
			String result = IOUtils.toString(in, "UTF-8");
			
			System.out.println("request \n"+json);
			System.out.println("result \n"+result);
			
			JSONObject qrenResponse = new JSONObject(result);
			invoiceEntity.setContent(qrenResponse.getString("content"));
			invoiceEntity.setInvoiceId(qrenResponse.getString("invoiceId"));
			
//			close connection
			in.close();
			connection.disconnect();
        } catch (IOException e) {
			// TODO: handle exception
        	throw new UserServiceException("Could not get QRIS. Please try again!", e);
		}
		
//		setting dueTime
		final String TIME_FORMAT = "yyyy-MM-dd";
		SimpleDateFormat sdf = new SimpleDateFormat(TIME_FORMAT);
		sdf.setTimeZone(TimeZone.getTimeZone("GMT+7"));
		 
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, 1);
		
		String dueTime = sdf.format(cal.getTime());
		invoiceEntity.setDueTime(dueTime);
		
//		save invoice data
		
		invoiceEntity.setInvoiceName("Invoice pembayaran/pembelian "+product.getProductName());
		invoiceEntity.setInvoiceNominal(product.getTotalPrice());
		invoiceEntity.setAdminFee("2.5%");
		invoiceEntity.setInvoiceDate(day+" "+monthValue+" "+year);
		
//		setting create date
		final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
		formatter.setTimeZone(TimeZone.getTimeZone("GMT+7"));
		 
		Calendar currentTime = Calendar.getInstance();
		
		String createdDate = formatter.format(currentTime.getTime());
		invoiceEntity.setCreatedDate(createdDate);
		
		invoiceEntity.setStatus("pending");
		invoiceEntity.setQty(1);
		invoiceEntity.setCurrency("IDR");
		invoiceEntity.setDescription("waiting for payment");
		invoiceEntity.setIsVerified(false);
		InvoiceEntity storeOfInvoice = invoiceRepository.save(invoiceEntity);
		InvoiceDto returnValue = modelMapper.map(storeOfInvoice, InvoiceDto.class);
		return returnValue;
	}

	@Override
	public InvoiceDto getByInvoiceNumber(String invoiceNumber) {
		// TODO Auto-generated method stub
		InvoiceDto returnValue = new InvoiceDto();
		
		ModelMapper modelMapper = new ModelMapper();
			
//		query search invoice
		InvoiceEntity invoice = invoiceRepository.findByInvoiceNumber(invoiceNumber);
			
		if (invoice == null) throw new UserServiceException(
			"invoice not found");
			
//		get invoice
		returnValue = modelMapper.map(invoice, InvoiceDto.class);
		return returnValue;
	}

	@Override
	public List<InvoiceDto> getByInvoiceDate(String invoiceDate) {
		// TODO Auto-generated method stub
		List<InvoiceDto> returnValue = new ArrayList<InvoiceDto>();
		
		ModelMapper modelMapper = new ModelMapper();
		
//		query search invoices
		List<InvoiceEntity> invoices = invoiceRepository.findByInvoiceDate(invoiceDate);
		if (invoices == null) throw new UserServiceException(
				"no records");
		
//		Looping invoices data
		for (InvoiceEntity invoiceEntity : invoices) {
			returnValue.add(modelMapper.map(invoiceEntity, InvoiceDto.class) );
		}
		
		return returnValue;
	}

	@Override
	public List<TransactionDto> getInvoicesByCreatedBy(String createdBy, int page, int limit) {
		// TODO Auto-generated method stub
		List<TransactionDto> returnValue = new ArrayList<TransactionDto>();
		
		ModelMapper modelMapper = new ModelMapper();
		
//		paging
		if (page>0) page = page-1;
		Pageable pageableRequest = PageRequest.of(page, limit);
		
//		query search invoices
		Page<InvoiceEntity> invoicesPage = invoiceRepository.findByCreatedByAndStatusOrderByIdDesc(createdBy, "success", pageableRequest);
		List<InvoiceEntity> invoices = invoicesPage.getContent();
		
		if (invoices == null) throw new UserServiceException(
				"no records");
		
//		Looping invoices data
		for (InvoiceEntity invoiceEntity : invoices) {
			
//			get transactions data
			List<TransactionEntity> transactions = transactionRepository.findAllByTransactionId(invoiceEntity.getTransactionId());
			
			for (TransactionEntity transactionEntity : transactions) {
					
				returnValue.add(modelMapper.map(transactionEntity, TransactionDto.class) );
			}
		}
		
		return returnValue;
	}

	@Override
	public PaymentNotifQrenContainerDto createNotif(PaymentNotifQrenContainerDto notif) {
		// TODO Auto-generated method stub
		ModelMapper modelMapper = new ModelMapper();
		PaymentNotifQrenContainer paymentNotif = modelMapper.map(notif, PaymentNotifQrenContainer.class);

//		save payment notif data
		Date date = new Date();
		paymentNotif.setTimeStamp(new Timestamp(date.getTime()).toString());
		
//		setting response payment notif dto
		PaymentNotifQrenContainer storeOfPaymentNotif = paymentNotifQrenContainerRepository.save(paymentNotif);
		PaymentNotifQrenContainerDto returnValue = modelMapper.map(storeOfPaymentNotif, PaymentNotifQrenContainerDto.class);
		
//		get invoice data
		InvoiceEntity invoice = invoiceRepository.findByInvoiceId(paymentNotif.getInvoice());
		if (invoice == null) throw new UserServiceException(
				"invoice not found");
		
//		transaction configuration
		TransactionEntity transaction = new TransactionEntity();
		transaction.setInvoice(invoice);
		
		String generateTrxId = utils.generateId(5);
		String random = utils.generateId(1);
		
		final String DATE_FORMAT = "yyyymmdd";
		SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
		dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+7"));
		 
		Calendar currentDate = Calendar.getInstance();
		String trxdate = dateFormat.format(currentDate.getTime());
		
		transaction.setTransactionId("QR-"+trxdate+"-"+random+"-"+generateTrxId);
		
		invoice.setTransactionId(transaction.getTransactionId());
		invoice.setStatus(paymentNotif.getMessage());
		invoice.setDescription("payment success");
		
		final String FORMAT = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat formatter = new SimpleDateFormat(FORMAT);
		formatter.setTimeZone(TimeZone.getTimeZone("GMT+7"));
		 
		Calendar currentTime = Calendar.getInstance();
		
		String modifiedDate = formatter.format(currentTime.getTime());
		
		invoice.setModifiedDate(modifiedDate);
		
		invoiceRepository.save(invoice);
		
		transaction.setQrenTransactionId(paymentNotif.getQrentransid());
		transaction.setMerchantId(paymentNotif.getMerchantApiKey());
		transaction.setAmount(paymentNotif.getAmount());
		
		transaction.setStatus(paymentNotif.getMessage());
		transaction.setTransactionDate(paymentNotif.getTimeStamp());
		transaction.setDescription("menunggu konfirmasi");
		transaction.setIsConfirmed(false);
		
//		save transaction data
		transactionRepository.save(transaction);
		
		return returnValue;
	}

	@Override
	public TransactionDto getTransactionInfo(String invoiceNumber) {
		// TODO Auto-generated method stub
		TransactionDto returnValue = new TransactionDto();
		ModelMapper modelMapper = new ModelMapper();
		
		InvoiceEntity invoice = invoiceRepository.findByInvoiceNumber(invoiceNumber);
		String transactionId = invoice.getTransactionId();
		
//		query search transaction
		TransactionEntity transaction = transactionRepository.findByTransactionId(transactionId);
			
		if (transaction == null) throw new UserServiceException(
			"no transaction detail for this transaction id");
			
//		get product
		returnValue = modelMapper.map(transaction, TransactionDto.class);
		return returnValue;
	}

	@Override
	public List<TransactionDto> getTransactions() {
		// TODO Auto-generated method stub
		List<TransactionDto> returnValue = new ArrayList<TransactionDto>();
		
		ModelMapper modelMapper = new ModelMapper();
		
//		query search transactions
		List<TransactionEntity> transactions = transactionRepository.findAllByOrderByIdDesc();
		if (transactions == null) throw new UserServiceException(
				"no records");
		
//		Looping transactions data	
		for (TransactionEntity transactionEntity : transactions) {
			returnValue.add(modelMapper.map(transactionEntity, TransactionDto.class) );
		}
		
		return returnValue;
	}
	
	@Override
	public TransactionDto putConfirmation(String transactionId) {
		// TODO Auto-generated method stub
		ModelMapper modelMapper = new ModelMapper();
		
//      role checking
		SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        String email = authentication.getName();
        
        UserEntity user = userRepository.findByEmail(email);
        UserEntity userRole = userRepository.findRoleByUserId(user.getUserId());
        
        String role = userRole.getRole().getRoleName();
        if (!role.equals("Administrator")) throw new UserServiceException ("access denied!, " +role+ " not allowed");
        
        TransactionEntity transaction = transactionRepository.findByTransactionId(transactionId);
        if (transaction == null) throw new UserServiceException("No record found");
        
        if (transaction.getIsConfirmed() == false) {
			transaction.setIsConfirmed(true);
		}
        
        if (transaction.getDescription().equals("menunggu konfirmasi")) {
			transaction.setDescription("payment success");
		}
        
        InvoiceEntity invoice = transaction.getInvoice();
        invoice.setIsVerified(true);
        
        final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
		formatter.setTimeZone(TimeZone.getTimeZone("GMT+7"));
		 
		Calendar currentTime = Calendar.getInstance();
		
		String modifiedDate = formatter.format(currentTime.getTime());
        invoice.setModifiedDate(modifiedDate);
        invoiceRepository.save(invoice);
        
        TransactionEntity confirmation = transactionRepository.save(transaction);
		TransactionDto returnValue = modelMapper.map(confirmation, TransactionDto.class);
		
		return returnValue;
	}
	
	@Override
	public PaymentNotifQrenContainerDto findTransaction(String invoiceNumber) {
		// TODO Auto-generated method stub

//      role checking
		SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        String email = authentication.getName();
        
        UserEntity user = userRepository.findByEmail(email);
        UserEntity userRole = userRepository.findRoleByUserId(user.getUserId());
        
        String role = userRole.getRole().getRoleName();
        if (!role.equals("Administrator")) throw new UserServiceException ("access denied!, " +role+ " not allowed");
		
//      data execute
		PaymentNotifQrenContainerDto returnValue = new PaymentNotifQrenContainerDto();
		ModelMapper modelMapper = new ModelMapper();
		
		InvoiceEntity invoice = invoiceRepository.findByInvoiceNumber(invoiceNumber);
		if (invoice == null) throw new UserServiceException ("no data record");
		
		if (invoice.getStatus().equals("pending")) {
			try {
//				configure api request			
				String invoiceId = invoice.getInvoiceId();
				String charset = "UTF-8";
				
				String url = "https://qren-api.tmoney.co.id/paybyqr/checktransactionstatus/";
				String query = String.format("invoice=%s", 
		                   URLEncoder.encode(invoiceId, charset));
				
//				String json = obj.writeValueAsString(jsonInputString);
				
				URL uri = new URL(url + "?" +query);
				HttpURLConnection connection = (HttpURLConnection) uri.openConnection();
				
				connection.setRequestMethod("POST");
				connection.setRequestProperty("Authorization", 
						"Basic dG1vbmV5OmZmODY2ZjViNjE1NGJiYjdkOTc4ZTUyNDNiNDkzMjBiMGQxYWQ2N2M=");
				connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
				connection.setDoInput(true);
				connection.setDoOutput(true);
				
//				if ( connection instanceof HttpURLConnection)
//	            {
//	               HttpURLConnection httpConnection = (HttpURLConnection) connection;
//	               System.out.println(httpConnection.getResponseCode());
//	               System.out.println(httpConnection.getResponseMessage());
//	            }
//	            else
//	            {
//	               System.err.println ("error!");
//	            }
				
				InputStream in = new BufferedInputStream(connection.getInputStream());
				String result = IOUtils.toString(in, "UTF-8");
				
				JSONObject qrenResponse = new JSONObject(result);
				System.out.println(qrenResponse);
				
				if (qrenResponse.getString("resultCode").equals("0")) {
					
//					setting response
					returnValue.setStatus("0");
					returnValue.setMessage("success");
					returnValue = modelMapper.map(returnValue, PaymentNotifQrenContainerDto.class);
					
//					save the invoice in container qren
					PaymentNotifQrenContainer notif = new PaymentNotifQrenContainer();
					notif.setInvoice(qrenResponse.getString("invoiceKey"));
					notif.setStatus("0");
					notif.setAmount(qrenResponse.getString("amount"));
					notif.setMerchantApiKey(invoice.getProduct().getMerchantId());
					notif.setTrxId("0");
					notif.setQrentransid("0");
					notif.setMessage("success");
					notif.setTimeStamp(qrenResponse.getString("solvedDate"));
					
					paymentNotifQrenContainerRepository.save(notif);
					
//					save success invoice in invoice table
					invoice.setStatus("success");
					invoice.setDescription("payment success");
					
					final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
					SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
					formatter.setTimeZone(TimeZone.getTimeZone("GMT+7"));
					 
					Calendar currentTime = Calendar.getInstance();
					
					String modifiedDate = formatter.format(currentTime.getTime());
					
					invoice.setModifiedDate(modifiedDate);
					
					invoiceRepository.save(invoice);
				} else if (qrenResponse.getString("resultCode").equals("qr9929")) {
//					setting response
					returnValue.setStatus("1");
					returnValue.setMessage(qrenResponse.getString("message"));
					returnValue = modelMapper.map(returnValue, PaymentNotifQrenContainerDto.class);
				}
				
//				close connection
				in.close();
				connection.disconnect();
	        } catch (IOException e) {
				// TODO: handle exception
	        	throw new UserServiceException("Cannot get invoice", e);
			}
		} else {
			returnValue.setStatus("1");
			returnValue.setMessage("invoice sudah tersedia pada tabel");
		}
		
		return returnValue;
	}
	
	@Override
	public TransactionDto addInvoice(String invoiceNumber) {
		// TODO Auto-generated method stub
		
//      role checking
		SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        String email = authentication.getName();
        
        UserEntity user = userRepository.findByEmail(email);
        UserEntity userRole = userRepository.findRoleByUserId(user.getUserId());
        
        String role = userRole.getRole().getRoleName();
        if (!role.equals("Administrator")) throw new UserServiceException ("access denied!, " +role+ " not allowed");
        
        TransactionDto returnValue = new TransactionDto();
		ModelMapper modelMapper = new ModelMapper();
		
//		find the invoice id by invoice number
		InvoiceEntity invoice = invoiceRepository.findByInvoiceNumber(invoiceNumber);
		if (invoice == null) throw new UserServiceException ("no data record");
		
		String invoiceId = invoice.getInvoiceId();
//		find the invoice in qren container
		PaymentNotifQrenContainer paymentNotif = paymentNotifQrenContainerRepository.findByInvoice(invoiceId);
		if (paymentNotif == null) throw new UserServiceException ("no qren invoice created");
		
		System.out.println(invoiceId);
		if (invoice.getStatus().equals("success") && invoice.getIsVerified() == false) {
			System.out.println(invoice.getStatus());
//			transaction configuration
			TransactionEntity transaction = new TransactionEntity();
			transaction.setInvoice(invoice);
			
			System.out.println(transaction.getInvoice());
			
			String generateTrxId = utils.generateId(5);
			String random = utils.generateId(1);
			
			final String DATE_FORMAT = "yyyymmdd";
			SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
			dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+7"));
			 
			Calendar currentDate = Calendar.getInstance();
			String trxdate = dateFormat.format(currentDate.getTime());
			
			transaction.setTransactionId("QR-"+trxdate+"-"+random+"-"+generateTrxId);
			
			invoice.setTransactionId(transaction.getTransactionId());
			
			System.out.println(transaction.getTransactionId());
			
			final String FORMAT = "yyyy-MM-dd HH:mm:ss";
			SimpleDateFormat formatter = new SimpleDateFormat(FORMAT);
			formatter.setTimeZone(TimeZone.getTimeZone("GMT+7"));
			 
			Calendar currentTime = Calendar.getInstance();
			
			String modifiedDate = formatter.format(currentTime.getTime());
			
			invoice.setModifiedDate(modifiedDate);
			
			invoiceRepository.save(invoice);
			
			System.out.println(paymentNotif.getQrentransid());
//			save transaction data
			transaction.setQrenTransactionId(paymentNotif.getQrentransid());
			transaction.setMerchantId(paymentNotif.getMerchantApiKey());
			transaction.setAmount(paymentNotif.getAmount());
			
			transaction.setStatus(paymentNotif.getMessage());
			transaction.setTransactionDate(paymentNotif.getTimeStamp());
			transaction.setDescription("menunggu konfirmasi");
			transaction.setIsConfirmed(false);
			
			System.out.println(transaction);
			
			TransactionEntity transactionEntity = transactionRepository.save(transaction);
			returnValue = modelMapper.map(transactionEntity, TransactionDto.class);
		}

		return returnValue;
	}

	public FileEntity storeFile(MultipartFile file, String productId) {
		// TODO Auto-generated method stub
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }
            
            String fileId = utils.generateId(12);
            
            ProductEntity product = productRepository.findByProductId(productId);
    		product.setImageId(fileId);
    		
    		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/downloadFile/")
                    .path(fileName)
                    .toUriString();
    		
    		product.setProductImage(fileDownloadUri);
    		
    		productRepository.save(product);

            FileEntity dbFile = new FileEntity(fileId, fileName, file.getContentType(), file.getBytes());
    		
            return fileRepository.save(dbFile);
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
	}
	
	public FileEntity getFile(String fileName) {
        return fileRepository.findByFileName(fileName);
    }
	
}
