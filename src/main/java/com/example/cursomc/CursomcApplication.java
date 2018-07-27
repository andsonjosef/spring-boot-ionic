package com.example.cursomc;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.cursomc.domain.Address;
import com.example.cursomc.domain.Category;
import com.example.cursomc.domain.City;
import com.example.cursomc.domain.Client;
import com.example.cursomc.domain.Order;
import com.example.cursomc.domain.Payment;
import com.example.cursomc.domain.PaymentBankTransfer;
import com.example.cursomc.domain.PaymentCreditCard;
import com.example.cursomc.domain.Product;
import com.example.cursomc.domain.State;
import com.example.cursomc.domain.enums.StatusPayment;
import com.example.cursomc.domain.enums.TypeClient;
import com.example.cursomc.repositories.AddressRepository;
import com.example.cursomc.repositories.CategoryRepository;
import com.example.cursomc.repositories.CityRepository;
import com.example.cursomc.repositories.ClientRepository;
import com.example.cursomc.repositories.OrderRepository;
import com.example.cursomc.repositories.PaymentRepository;
import com.example.cursomc.repositories.ProductRepository;
import com.example.cursomc.repositories.StateRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {

	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private StateRepository stateRepository;
	@Autowired
	private CityRepository cityRepository;
	@Autowired
	private ClientRepository clientRepository;
	@Autowired
	private AddressRepository addressRepository;
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private PaymentRepository paymentRepository;

	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		Category cat1 = new Category(null, "Computing");
		Category cat2 = new Category(null, "Office");

		Product p1 = new Product(null, "Computer", 2000.00);
		Product p2 = new Product(null, "Print", 800.00);
		Product p3 = new Product(null, "Mouse", 80.00);

		cat1.getProducts().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProducts().addAll(Arrays.asList(p2));

		p1.getCategories().addAll(Arrays.asList(cat1));
		p2.getCategories().addAll(Arrays.asList(cat1, cat2));
		p3.getCategories().addAll(Arrays.asList(cat1));
		

		categoryRepository.saveAll(Arrays.asList(cat1, cat2));
		productRepository.saveAll(Arrays.asList(p1, p2, p3));
		
		
		State state1 = new State(null, "Minas Gerais");
		State state2 = new State(null, "São Paulo");
		
		City c1 = new City(null, "Uberlândia", state1);
		City c2 = new City(null, "São Paulo", state2);
		City c3 = new City(null, "Campinas", state2);
		
		state1.getCities().addAll(Arrays.asList(c1));
		state2.getCities().addAll(Arrays.asList(c2, c3));
		
		stateRepository.saveAll(Arrays.asList(state1, state2));
		cityRepository.saveAll(Arrays.asList(c1,c2,c3));
		
		Client cli1 = new Client(null, "Maria Silva", "maria@gmail.com", "36378912377", TypeClient.INDIVIDUAL);
		cli1.getPhones().addAll(Arrays.asList("27736323", "93838393"));
		
		Address a1 = new Address(null, "Rua Flores", "300", "Apto 303", "Jardin", "38220834", cli1, c1);
		Address a2 = new Address(null, "Avenida Matos", "105", "Sala 800", "Centro", "38777012", cli1, c2);
		
		cli1.getAdresses().addAll(Arrays.asList(a1,a2));
		
		clientRepository.saveAll(Arrays.asList(cli1));
		addressRepository.saveAll(Arrays.asList(a1,a2));
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		Order ord1 = new Order(null, sdf.parse("30/09/2017 10:32"), cli1, a1);
		Order ord2 = new Order(null, sdf.parse("10/10/2017 19:35"), cli1, a2);
		
		Payment pay1 = new PaymentCreditCard(null, StatusPayment.PAYED, ord1, 6);
		ord1.setPayment(pay1);
		
		Payment pay2 = new PaymentBankTransfer(null, StatusPayment.PENDING, ord2,sdf.parse("20/10/2017 00:00"), null);
		ord2.setPayment(pay2);
		
		cli1.getOrders().addAll(Arrays.asList(ord1,ord2));
		
		orderRepository.saveAll(Arrays.asList(ord1,ord2));
		paymentRepository.saveAll(Arrays.asList(pay1, pay2));


	}
}
