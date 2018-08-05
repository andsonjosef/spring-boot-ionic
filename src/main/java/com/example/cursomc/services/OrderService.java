package com.example.cursomc.services;

import java.util.Date;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.cursomc.domain.ItemOrder;
import com.example.cursomc.domain.Order;
import com.example.cursomc.domain.PaymentBankTransfer;
import com.example.cursomc.domain.enums.StatusPayment;
import com.example.cursomc.repositories.ItemOrderRepository;
import com.example.cursomc.repositories.OrderRepository;
import com.example.cursomc.repositories.PaymentRepository;
import com.example.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class OrderService {

	@Autowired
	private BankTransferService bankTransferService;

	@Autowired
	private ItemOrderRepository itemOrderRepository;

	@Autowired
	private OrderRepository repo;

	@Autowired
	private PaymentRepository paymentRepository;

	@Autowired
	private ProductService productService;

	public Order find(Integer id) {
		Optional<Order> obj = repo.findById(id);
		return obj.orElseThrow(
				() -> new ObjectNotFoundException("Obect not found! Id: " + id + ", Type: " + Order.class.getName()));
	}

	@Transactional
	public Order insert(Order obj) {
		obj.setId(null);
		obj.setMoment(new Date());
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
			io.setPrice(productService.find(io.getProduct().getId()).getPrice());
			io.setOrder(obj);
		}
		itemOrderRepository.saveAll(obj.getItems());
		return obj;
	}
}
