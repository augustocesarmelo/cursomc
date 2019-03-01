package com.augustoakuma.cursomc.services;

import java.util.List;
import java.util.Optional;

import com.augustoakuma.cursomc.domain.Categoria;
import com.augustoakuma.cursomc.repositories.CategoriaRepository;
import com.augustoakuma.cursomc.services.exceptions.DataIntegrityException;
import com.augustoakuma.cursomc.services.exceptions.ObjectNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository repo;
		
	public Categoria find(Integer id) {
		Optional<Categoria> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName()));
	}
	
	public List<Categoria> listarTodos() {
		List<Categoria> obj = repo.findAll();
		return obj;
	}
	
	public Categoria insert(Categoria obj) {
		obj.setId(null);
		return repo.save(obj);
	}
	
	public Categoria update(Categoria obj) {	
		this.find(obj.getId());
		return repo.save(obj);
	}

	public void delete(Integer id){
		this.find(id);
		try {
			repo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir uma categoria que possui produtos.");
		}
		
	}
	
}
