package com.example.cursomc.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.cursomc.domain.Address;
import com.example.cursomc.domain.Category;
import com.example.cursomc.domain.City;
import com.example.cursomc.domain.Client;
import com.example.cursomc.domain.ItemOrder;
import com.example.cursomc.domain.Order;
import com.example.cursomc.domain.Payment;
import com.example.cursomc.domain.PaymentBankTransfer;
import com.example.cursomc.domain.PaymentCreditCard;
import com.example.cursomc.domain.Product;
import com.example.cursomc.domain.State;
import com.example.cursomc.domain.enums.Profile;
import com.example.cursomc.domain.enums.StatusPayment;
import com.example.cursomc.domain.enums.TypeClient;
import com.example.cursomc.repositories.AddressRepository;
import com.example.cursomc.repositories.CategoryRepository;
import com.example.cursomc.repositories.CityRepository;
import com.example.cursomc.repositories.ClientRepository;
import com.example.cursomc.repositories.ItemOrderRepository;
import com.example.cursomc.repositories.OrderRepository;
import com.example.cursomc.repositories.PaymentRepository;
import com.example.cursomc.repositories.ProductRepository;
import com.example.cursomc.repositories.StateRepository;

@Service
public class DBService {

	@Autowired
	private BCryptPasswordEncoder pe;
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
	@Autowired
	private ItemOrderRepository itemOrderRepository;

	public void IstantiateTestDataBase() throws ParseException {
		Category cat1 = new Category(null, "Computing");
		Category cat2 = new Category(null, "Office");
		Category cat3 = new Category(null, "Bed & Bath");
		Category cat4 = new Category(null, "Eletronics");
		Category cat5 = new Category(null, "Gardening");
		Category cat6 = new Category(null, "Decoration");
		Category cat7 = new Category(null, "Perfumary");


		Product p1 = new Product(null, "Computer", 2000.00);
		Product p2 = new Product(null, "Print", 800.00);
		Product p3 = new Product(null, "Mouse", 80.00);
		Product p4 = new Product(null, "Office desk", 300.00);
		Product p5 = new Product(null, "Towel", 80.00);
		Product p6 = new Product(null, "Quilt", 200.00);
		Product p7 = new Product(null, "TV true color", 1200.00);
		Product p8 = new Product(null, "Brush cutter", 800.00);
		Product p9 = new Product(null, "Beside lamp", 100.00);
		Product p10 = new Product(null, "Pending", 180.00);
		Product p11 = new Product(null, "Shampoo", 90.00);

		cat1.getProducts().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProducts().addAll(Arrays.asList(p2, p4));
		cat3.getProducts().addAll(Arrays.asList(p5, p6));
		cat4.getProducts().addAll(Arrays.asList(p1, p2, p3, p7));
		cat5.getProducts().addAll(Arrays.asList(p8));
		cat6.getProducts().addAll(Arrays.asList(p9, p10));
		cat7.getProducts().addAll(Arrays.asList(p11));
		

		p1.getCategories().addAll(Arrays.asList(cat1, cat4));
		p2.getCategories().addAll(Arrays.asList(cat1, cat2, cat4));
		p3.getCategories().addAll(Arrays.asList(cat1, cat4));
		p4.getCategories().addAll(Arrays.asList(cat2));
		p5.getCategories().addAll(Arrays.asList(cat3));
		p6.getCategories().addAll(Arrays.asList(cat3));
		p7.getCategories().addAll(Arrays.asList(cat4));
		p8.getCategories().addAll(Arrays.asList(cat5));
		p9.getCategories().addAll(Arrays.asList(cat6));
		p10.getCategories().addAll(Arrays.asList(cat6));
		p11.getCategories().addAll(Arrays.asList(cat7));

		categoryRepository.saveAll(Arrays.asList(cat1, cat2, cat3, cat4, cat5, cat6, cat7));
		productRepository.saveAll(Arrays.asList(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11));

		State state1 = new State(null, "Minas Gerais");
		State state2 = new State(null, "São Paulo");

		City c1 = new City(null, "Uberlândia", state1);
		City c2 = new City(null, "São Paulo", state2);
		City c3 = new City(null, "Campinas", state2);

		state1.getCities().addAll(Arrays.asList(c1));
		state2.getCities().addAll(Arrays.asList(c2, c3));

		stateRepository.saveAll(Arrays.asList(state1, state2));
		cityRepository.saveAll(Arrays.asList(c1, c2, c3));

		Client cli1 = new Client(null, "Maria Silva", "maria@gmail.com", "36378912377", TypeClient.INDIVIDUAL, pe.encode("123"));
		cli1.getPhones().addAll(Arrays.asList("27736323", "93838393"));
		
		Client cli2 = new Client(null, "Ana Costa", "Ana@gmail.com", "31628382740", TypeClient.INDIVIDUAL, pe.encode("123"));
		cli1.getPhones().addAll(Arrays.asList("99336323", "93838258"));
		cli2.addProfile(Profile.ADMIN);

		Address a1 = new Address(null, "Rua Flores", "300", "Apto 303", "Jardin", "38220834", cli1, c1);
		Address a2 = new Address(null, "Avenida Matos", "105", "Sala 800", "Centro", "38777012", cli1, c2);
		Address a3 = new Address(null, "Avenida Floriano", "2106", null, "Centro", "58455654", cli2, c2);


		cli1.getAdresses().addAll(Arrays.asList(a1, a2));
		cli2.getAdresses().addAll(Arrays.asList(a3));


		clientRepository.saveAll(Arrays.asList(cli1, cli2));
		addressRepository.saveAll(Arrays.asList(a1, a2, a3));

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		Order ord1 = new Order(null, sdf.parse("30/09/2017 10:32"), cli1, a1);
		Order ord2 = new Order(null, sdf.parse("10/10/2017 19:35"), cli1, a2);

		Payment pay1 = new PaymentCreditCard(null, StatusPayment.PAYED, ord1, 6);
		ord1.setPayment(pay1);

		Payment pay2 = new PaymentBankTransfer(null, StatusPayment.PENDING, ord2, sdf.parse("20/10/2017 00:00"), null);
		ord2.setPayment(pay2);

		cli1.getOrders().addAll(Arrays.asList(ord1, ord2));

		orderRepository.saveAll(Arrays.asList(ord1, ord2));
		paymentRepository.saveAll(Arrays.asList(pay1, pay2));

		ItemOrder io1 = new ItemOrder(ord1, p1, 0.00, 1, 2000.00);
		ItemOrder io2 = new ItemOrder(ord1, p3, 0.00, 2, 80.00);
		ItemOrder io3 = new ItemOrder(ord2, p2, 100.00, 1, 800.00);

		ord1.getItems().addAll(Arrays.asList(io1, io2));
		ord2.getItems().addAll(Arrays.asList(io3));

		p1.getItems().addAll(Arrays.asList(io1));
		p2.getItems().addAll(Arrays.asList(io3));
		p3.getItems().addAll(Arrays.asList(io2));

		itemOrderRepository.saveAll(Arrays.asList(io1, io2, io3));
	}

}
