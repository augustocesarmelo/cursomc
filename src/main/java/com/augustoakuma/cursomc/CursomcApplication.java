package com.augustoakuma.cursomc;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.augustoakuma.cursomc.domain.Categoria;
import com.augustoakuma.cursomc.domain.Cidade;
import com.augustoakuma.cursomc.domain.Cliente;
import com.augustoakuma.cursomc.domain.Endereco;
import com.augustoakuma.cursomc.domain.Estado;
import com.augustoakuma.cursomc.domain.Produto;
import com.augustoakuma.cursomc.domain.enums.TipoCliente;
import com.augustoakuma.cursomc.repositories.CategoriaRepository;
import com.augustoakuma.cursomc.repositories.CidadeRepository;
import com.augustoakuma.cursomc.repositories.ClienteRepository;
import com.augustoakuma.cursomc.repositories.EnderecoRepository;
import com.augustoakuma.cursomc.repositories.EstadoRepository;
import com.augustoakuma.cursomc.repositories.ProdutoRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {

	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	
	
	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");		
		
		Produto p1 = new Produto(null, "Computador", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 800.00);
		Produto p3 = new Produto(null, "Mouse", 200.00);
		
		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3) );
		cat2.getProdutos().addAll(Arrays.asList( p2) );
		
		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));
		
		categoriaRepository.saveAll(Arrays.asList(cat1, cat2));
		produtoRepository.saveAll(Arrays.asList(p1, p2, p3));
		
		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "São Paulo");
		
		Cidade c1 = new Cidade(null, "Uberlâncida", est1);
		Cidade c2 = new Cidade(null, "São Paulo", est2 );
		Cidade c3 = new Cidade(null, "Campinas", est2);
		
		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2, c3));
		
		estadoRepository.saveAll(Arrays.asList(est1, est2));
		cidadeRepository.saveAll(Arrays.asList(c1, c2, c3));
		
		
		Cliente cli1 = new Cliente(null, "Maria Silva", "maria@gmail.com", "111.111.111-11", TipoCliente.PESSOAFISICA);
		cli1.getTelefones().addAll(Arrays.asList("81344444444", "8198888888")); 
		
		Endereco e1 = new Endereco(null, "Rua flores", "300", "apt 5500", "Jardim", "50800-000", cli1, c1);
		Endereco e2 = new Endereco(null, "Avenida Matos", "105", "sala 800", "Centro", "50700-000", cli1, c2);
		
		cli1.getEnderecos().addAll(Arrays.asList(e1,e2));
		
		clienteRepository.save(cli1);
		enderecoRepository.saveAll( Arrays.asList(e1,e2)  );
		
		
		
	}
}

