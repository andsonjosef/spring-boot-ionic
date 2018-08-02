package com.example.cursomc.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.cursomc.domain.Client;
import com.example.cursomc.domain.enums.TypeClient;
import com.example.cursomc.dto.ClientNewDTO;
import com.example.cursomc.repositories.ClientRepository;
import com.example.cursomc.resources.exception.FieldMessage;
import com.example.cursomc.services.validation.utils.BR;

public class ClientInsertValidator implements ConstraintValidator<ClientInsert, ClientNewDTO> {

	@Autowired
	private ClientRepository repo;

	@Override
	public void initialize(ClientInsert ann) {
	}

	@Override
	public boolean isValid(ClientNewDTO objDto, ConstraintValidatorContext context) {
		List<FieldMessage> list = new ArrayList<>();

		if (objDto.getType().equals(TypeClient.INDIVIDUAL.getCod()) && !BR.isValidCPF(objDto.getTid())) {
			list.add(new FieldMessage("tid", "Invalid individual tid(CPF)"));
		}

		if (objDto.getType().equals(TypeClient.CORPORATION.getCod()) && !BR.isValidCNPJ(objDto.getTid())) {
			list.add(new FieldMessage("tid", "Invalid corporation tid(CNPJ)"));
		}

		Client aux = repo.findByEmail(objDto.getEmail());
		if(aux !=null) {
			list.add(new FieldMessage("email", "existing email"));
		}
		

		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}