package com.augustoakuma.cursomc.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.augustoakuma.cursomc.domain.Produto;
import com.augustoakuma.cursomc.dto.ProdutoDTO;
import com.augustoakuma.cursomc.resources.utils.URL;
import com.augustoakuma.cursomc.services.ProdutoService;

@RestController
@RequestMapping(value="/produtos")
public class ProdutoResource {	
	
	@Autowired
	private ProdutoService service;
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Produto> find( @PathVariable Integer id ) {		
		Produto obj = service.find(id);		
		return ResponseEntity.ok().body(obj);	
	}
	
	
	@RequestMapping(method=RequestMethod.GET)
	public List<Produto> list() {		
		List<Produto> lista =	service.listAll();		
		return lista;
	}
	
	@RequestMapping(value="/page", method=RequestMethod.GET)
	public ResponseEntity<Page<ProdutoDTO>> listarPagina(
			@RequestParam(value="nome", defaultValue="") String nome, 
			@RequestParam(value="categorias", defaultValue="") String categorias, 
			@RequestParam(value="page", defaultValue="0") Integer pagina, 
			@RequestParam(value="qtdPorPagina", defaultValue="24") Integer qtdPorPagina, 
			@RequestParam(value="orderBy", defaultValue="nome") String orderBy, 
			@RequestParam(value="direction", defaultValue="ASC") String direction) {	
		List<Integer> ids = URL.decodeIntList(categorias);
		String nomeDecoded = URL.decodeParam(nome);
		Page<Produto> lista =	service.search(nomeDecoded, ids, pagina, qtdPorPagina, orderBy, direction);		
		Page<ProdutoDTO> listaDTO = lista.map(obj -> new ProdutoDTO(obj));
		return ResponseEntity.ok().body(listaDTO) ;
	}
	
}
