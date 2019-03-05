package com.augustoakuma.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.augustoakuma.cursomc.domain.Cliente;
import com.augustoakuma.cursomc.domain.Cliente;
import com.augustoakuma.cursomc.dto.ClienteDTO;
import com.augustoakuma.cursomc.repositories.ClienteRepository;
import com.augustoakuma.cursomc.services.exceptions.DataIntegrityException;
import com.augustoakuma.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository repo;
		
	public Cliente find(Integer id) {
		Optional<Cliente> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
	}
	
	public List<Cliente> listarTodos() {
		List<Cliente> obj = repo.findAll();
		return obj;
	}
	
	public Page<Cliente> listarPagina(Integer pagina, Integer qtdPorPagina, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(pagina, qtdPorPagina, Direction.valueOf(direction),
				orderBy);
		return repo.findAll(pageRequest);
	}
	

	public Cliente update(Cliente obj) {	
		Cliente newObj = this.find(obj.getId());
		updateData(newObj, obj);
		return repo.save(newObj);
	}

	public void delete(Integer id){
		this.find(id);
		try {
			repo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir um Cliente que tenha pedidos");
		}
		
	}
	
	public Cliente fromDTO(ClienteDTO objDTO) {
		return new Cliente(objDTO.getId(), objDTO.getNome(), objDTO.getEmail(), null, null);
	}
	
	private void updateData(Cliente banco, Cliente argumento) {
		banco.setNome(argumento.getNome());
		banco.setEmail(argumento.getEmail());
	}
}
