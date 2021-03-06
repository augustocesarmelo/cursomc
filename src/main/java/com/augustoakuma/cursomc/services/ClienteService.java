package com.augustoakuma.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.augustoakuma.cursomc.domain.Cidade;
import com.augustoakuma.cursomc.domain.Cliente;
import com.augustoakuma.cursomc.domain.Endereco;
import com.augustoakuma.cursomc.domain.enums.TipoCliente;
import com.augustoakuma.cursomc.dto.ClienteDTO;
import com.augustoakuma.cursomc.dto.ClienteNewDTO;
import com.augustoakuma.cursomc.repositories.ClienteRepository;
import com.augustoakuma.cursomc.repositories.EnderecoRepository;
import com.augustoakuma.cursomc.services.exceptions.DataIntegrityException;
import com.augustoakuma.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository repo;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
		
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
	
	public Cliente fromDTO(ClienteNewDTO objDTO) {
		Cliente cli =  new Cliente(null, objDTO.getNome(), objDTO.getEmail(), objDTO.getCpfOuCnpj(), TipoCliente.toEnum( objDTO.getTipoCliente() ) );
		Cidade cidade = new Cidade(objDTO.getCidadeId(), null, null );
		Endereco end = new Endereco(null, objDTO.getLogradouro(), objDTO.getNumero(), objDTO.getComplento(), objDTO.getBairro(), objDTO.getCep(), cli, cidade );
		cli.getEnderecos().add(end);
		cli.getTelefones().add(objDTO.getTelefone1());
		if(objDTO.getTelefone2() != null) {
			cli.getTelefones().add(objDTO.getTelefone2());
		}
		if(objDTO.getTelefone3() != null) {
			cli.getTelefones().add(objDTO.getTelefone3());
		}
		return cli;
	}
	
	private void updateData(Cliente banco, Cliente argumento) {
		banco.setNome(argumento.getNome());
		banco.setEmail(argumento.getEmail());
	}
	
	@Transactional
	public Cliente insert(Cliente obj) {
		obj.setId(null);
		obj = repo.save(obj);
		
		enderecoRepository.saveAll( obj.getEnderecos() );
		return obj;		
	}
}
