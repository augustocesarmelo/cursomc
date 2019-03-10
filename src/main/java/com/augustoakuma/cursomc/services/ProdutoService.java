package com.augustoakuma.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.augustoakuma.cursomc.domain.Categoria;
import com.augustoakuma.cursomc.domain.Produto;
import com.augustoakuma.cursomc.repositories.CategoriaRepository;
import com.augustoakuma.cursomc.repositories.ProdutoRepository;
import com.augustoakuma.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ProdutoService {
	
	@Autowired
	private ProdutoRepository repo;
	
	@Autowired
	private CategoriaRepository categoriaRepository;
		
	public Produto find(Integer id) {
		Optional<Produto> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Produto.class.getName()));
	}
	
	public List<Produto> listAll() {
		List<Produto> obj = repo.findAll();
		return obj;
	}
	
	public Page<Produto> search(String nome, List<Integer> ids, Integer pagina, Integer qtdPorPagina, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(pagina, qtdPorPagina, Direction.valueOf(direction),
				orderBy);
		List<Categoria> categorias = categoriaRepository.findAllById(ids);
		return repo.findDistinctByNomeContainingAndCategoriasIn(nome, categorias, pageRequest);
	}
}
