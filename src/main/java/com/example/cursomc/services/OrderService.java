package com.example.cursomc.services;

import java.util.Date;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.example.cursomc.domain.Client;
import com.example.cursomc.domain.ItemOrder;
import com.example.cursomc.domain.Order;
import com.example.cursomc.domain.PaymentBankTransfer;
import com.example.cursomc.domain.enums.StatusPayment;
import com.example.cursomc.repositories.ItemOrderRepository;
import com.example.cursomc.repositories.OrderRepository;
import com.example.cursomc.repositories.PaymentRepository;
import com.example.cursomc.security.UserSS;
import com.example.cursomc.services.exceptions.AuthorizationException;
import com.example.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class OrderService {

	@Autowired
	private BankTransferService bankTransferService;

	@Autowired
	private EmailService emailService;

	@Autowired
	private ItemOrderRepository itemOrderRepository;

	@Autowired
	private OrderRepository repo;

	@Autowired
	private PaymentRepository paymentRepository;

	@Autowired
	private ProductService productService;

	@Autowired
	ClientService clientService;

	public Order find(Integer id) {
		Optional<Order> obj = repo.findById(id);
		return obj.orElseThrow(
				() -> new ObjectNotFoundException("Obect not found! Id: " + id + ", Type: " + Order.class.getName()));
	}

	@Transactional
	public Order insert(Order obj) {
		obj.setId(null);
		obj.setMoment(new Date());
		obj.setClient(clientService.find(obj.getClient().getId()));
		obj.getPayment().setStatus(StatusPayment.PENDING);
		obj.getPayment().setOrder(obj);
		if (obj.getPayment() instanceof PaymentBankTransfer) {
			PaymentBankTransfer pay = (PaymentBankTransfer) obj.getPayment();
			bankTransferService.fillPaymentBankTransfer(pay, obj.getMoment());
		}
		obj = repo.save(obj);
		paymentRepository.save(obj.getPayment());
		for (ItemOrder io : obj.getItems()) {
			io.setDiscount(0.0);
			io.setProduct(productService.find(io.getProduct().getId()));
			io.setPrice(io.getProduct().getPrice());
			io.setOrder(obj);
		}
		itemOrderRepository.saveAll(obj.getItems());
		emailService.sendOrderConfirmationHtmlEmail(obj);

		return obj;
	}

	public Page<Order> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		UserSS user = UserService.authenticated();
		if (user == null) {
			throw new AuthorizationException("Acess denied");
		}
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		Client client = clientService.find(user.getId());
		return repo.findByClient(client, pageRequest);
	}
}
