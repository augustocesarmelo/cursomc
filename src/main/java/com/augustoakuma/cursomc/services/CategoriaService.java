package com.augustoakuma.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.augustoakuma.cursomc.domain.Categoria;
import com.augustoakuma.cursomc.repositories.CategoriaRepository;
import com.augustoakuma.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository repo;
		
	public Categoria buscar(Integer id) {
		Optional<Categoria> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName()));
	}
	
	public List<Categoria> listarTodos() {
		List<Categoria> obj = repo.findAll();
		return obj;
	}
	
}
