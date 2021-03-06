package com.example.cursomc.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.cursomc.domain.Address;
import com.example.cursomc.domain.City;
import com.example.cursomc.domain.Client;
import com.example.cursomc.domain.enums.Profile;
import com.example.cursomc.domain.enums.TypeClient;
import com.example.cursomc.dto.ClientDTO;
import com.example.cursomc.dto.ClientNewDTO;
import com.example.cursomc.repositories.AddressRepository;
import com.example.cursomc.repositories.ClientRepository;
import com.example.cursomc.security.UserSS;
import com.example.cursomc.services.exceptions.AuthorizationException;
import com.example.cursomc.services.exceptions.DataIntegrityException;
import com.example.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClientService {

	@Autowired
	private ClientRepository repo;

	@Autowired
	private BCryptPasswordEncoder pe;

	@Autowired
	private AddressRepository addressRepository;

	public Client find(Integer id) {
		UserSS user = UserService.authenticated();

		if (user == null || !user.hasRole(Profile.ADMIN) && !id.equals(user.getId())) {
			throw new AuthorizationException("Acess denied");
		}

		Optional<Client> obj = repo.findById(id);
		return obj.orElseThrow(
				() -> new ObjectNotFoundException("Obect not found! Id: " + id + ", Type: " + Client.class.getName()));
	}

	@Transactional
	public Client insert(Client obj) {
		obj.setId(null);
		obj = repo.save(obj);
		addressRepository.saveAll(obj.getAdresses());
		return obj;
	}

	public Client update(Client obj) {
		Client newObj = find(obj.getId());
		updateData(newObj, obj);
		return repo.save(newObj);
	}

	public void delete(Integer id) {
		find(id);
		try {
			repo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Can not delete because there are related orders");
		}
	}

	public List<Client> findAll() {
		return repo.findAll();
	}

	public Client findByEmail(String email) {

		UserSS user = UserService.authenticated();
		if (user == null || !user.hasRole(Profile.ADMIN) && !email.equals(user.getUsername())) {
			throw new AuthorizationException("Acess denied");
		}

		Client obj = repo.findByEmail(email);
		if (obj == null) {
			throw new ObjectNotFoundException(
					"Object not found! Id: " + user.getId() + ", Type: " + Client.class.getName());
		}
		return obj;

	}

	public Page<Client> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);

		return repo.findAll(pageRequest);
	}

	public Client fromDTO(ClientDTO objDto) {
		return new Client(objDto.getId(), objDto.getName(), objDto.getEmail(), null, null, null);

	}

	public Client fromDTO(ClientNewDTO objDto) {
		Client cli = new Client(null, objDto.getName(), objDto.getEmail(), objDto.getTid(),
				TypeClient.toEnum(objDto.getType()), pe.encode(objDto.getPassword()));
		City city = new City(objDto.getCityId(), null, null);
		Address address = new Address(null, objDto.getStreet(), objDto.getNumber(), objDto.getComplement(),
				objDto.getDistrict(), objDto.getZipCode(), cli, city);
		cli.getAdresses().add(address);
		cli.getPhones().add(objDto.getPhone1());

		if (objDto.getPhone2() != null) {
			cli.getPhones().add(objDto.getPhone2());
		}
		if (objDto.getPhone3() != null) {
			cli.getPhones().add(objDto.getPhone3());
		}
		return cli;

	}

	private void updateData(Client newObj, Client obj) {
		newObj.setName(obj.getName());
		newObj.setEmail(obj.getEmail());

	}

}
