package com.augustoakuma.cursomc.resources;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.augustoakuma.cursomc.domain.Categoria;
import com.augustoakuma.cursomc.services.CategoriaService;

@RestController
@RequestMapping(value="/categorias")
public class CategoriaResource {	
	
	@Autowired
	private CategoriaService service;
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<?> find( @PathVariable Integer id ) {		
		Categoria obj = service.buscar(id);		
		return ResponseEntity.ok().body(obj);	
	}
	
	
	@RequestMapping(method=RequestMethod.GET)
	public List<Categoria> listar() {		
		List<Categoria> lista =	service.listarTodos();		
		return lista;
	}
	
}
