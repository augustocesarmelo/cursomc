package com.augustoakuma.cursomc.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.augustoakuma.cursomc.domain.Cliente;
import com.augustoakuma.cursomc.domain.enums.TipoCliente;
import com.augustoakuma.cursomc.dto.ClienteNewDTO;
import com.augustoakuma.cursomc.repositories.ClienteRepository;
import com.augustoakuma.cursomc.resources.exceptions.FieldMessage;
import com.augustoakuma.cursomc.services.validation.utils.BR;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {
	
	@Autowired
	private ClienteRepository repo;
	
	@Override
	public void initialize(ClienteInsert ann) {
	}

	@Override
	public boolean isValid(ClienteNewDTO objDto, ConstraintValidatorContext context) {
		List<FieldMessage> list = new ArrayList<>();
		
		if(objDto.getTipoCliente().equals(TipoCliente.PESSOAFISICA.getCodigo()) && !BR.isValidCPF( objDto.getCpfOuCnpj() ) ) {
			list.add(new FieldMessage("CpfOuCnpj", "CPF Inválido"	));
		}
		
		if(objDto.getTipoCliente().equals(TipoCliente.PESSOAJURIDICA.getCodigo()) && !BR.isValidCNPJ( objDto.getCpfOuCnpj() ) ) {
			list.add(new FieldMessage("CpfOuCnpj", "CNPJ Inválido"	));
		}
		
		Cliente aux = repo.findByEmail(objDto.getEmail());
		if(aux != null) {
			list.add(new FieldMessage("email", "E-mail já existente"	));
		}
			
		
		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}