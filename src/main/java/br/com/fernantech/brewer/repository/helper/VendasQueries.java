package br.com.fernantech.brewer.repository.helper;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.fernantech.brewer.dto.VendaMes;
import br.com.fernantech.brewer.dto.VendaOrigem;
import br.com.fernantech.brewer.model.Venda;
import br.com.fernantech.brewer.repository.filter.VendaFilter;

public interface VendasQueries {

	public Page<Venda> filtrar(VendaFilter vendaFilter, Pageable pageable);
	
	public Venda buscarComItens(Long codigo);
	
	public BigDecimal valorTotalNoAno();
	
	public BigDecimal valorTotalNoMes();
	
	public BigDecimal valorTicketMedioNoAno();
	
	public List<VendaMes> totalPorMes();
	
	public List<VendaOrigem> totalPorOrigem();
}
