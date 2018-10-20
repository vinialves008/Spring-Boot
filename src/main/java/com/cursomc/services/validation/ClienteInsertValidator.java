package com.cursomc.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.cursomc.domain.Cliente;
import com.cursomc.dto.ClienteNewDTO;
import com.cursomc.repositories.ClienteRepository;
import com.cursomc.resources.exceptions.FieldMessage;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {

	@Autowired
	private ClienteRepository repo;
	
	@Override
	public void initialize(ClienteInsert ann) {

	}
	@Override
	public boolean isValid(ClienteNewDTO value, ConstraintValidatorContext context) {
		List<FieldMessage> list = new ArrayList<>();
		
		//INSERIR VALIDAÇÃO DE CPF OU CNPJ
		
		//Validação de Email
		Cliente aux = repo.findByEmail(value.getEmail());
		if(aux != null) {
			list.add(new FieldMessage("email", "Email já existe!"));
		}
		
		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldname()).addConstraintViolation();
		}
		
		return list.isEmpty();
	}

}
