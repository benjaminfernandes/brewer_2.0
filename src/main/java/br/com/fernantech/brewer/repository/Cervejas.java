package br.com.fernantech.brewer.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.fernantech.brewer.model.Cerveja;
import br.com.fernantech.brewer.repository.helper.CervejasQueries;

@Repository
public interface Cervejas extends JpaRepository<Cerveja, Long>, CervejasQueries{

	public Optional<Cerveja> findBySkuIgnoreCase(String sku);
}
