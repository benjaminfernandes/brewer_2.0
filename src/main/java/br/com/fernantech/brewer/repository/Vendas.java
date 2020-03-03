package br.com.fernantech.brewer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fernantech.brewer.model.Venda;
import br.com.fernantech.brewer.repository.helper.VendasQueries;

public interface Vendas extends JpaRepository<Venda, Long>, VendasQueries{

		
}
