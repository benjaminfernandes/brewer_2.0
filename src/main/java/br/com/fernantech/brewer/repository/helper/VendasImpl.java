package br.com.fernantech.brewer.repository.helper;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.MonthDay;
import java.time.Year;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import br.com.fernantech.brewer.dto.VendaMes;
import br.com.fernantech.brewer.dto.VendaOrigem;
import br.com.fernantech.brewer.model.StatusVenda;
import br.com.fernantech.brewer.model.TipoPessoa;
import br.com.fernantech.brewer.model.Venda;
import br.com.fernantech.brewer.repository.filter.VendaFilter;
import br.com.fernantech.brewer.repository.paginacao.PaginacaoUtil;

public class VendasImpl implements VendasQueries{
	
	@PersistenceContext
	private EntityManager manager;
	@Autowired
	private PaginacaoUtil paginacaoUtil;

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public Page<Venda> filtrar(VendaFilter vendaFilter, Pageable pageable) {
		
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Venda.class);
		
		this.paginacaoUtil.preparar(criteria, pageable);
		
		adicionarFiltro(vendaFilter, criteria);
			
		return new PageImpl<>(criteria.list(), pageable, total(vendaFilter));
	}
	
	@Transactional(readOnly = true)
	@Override
	public Venda buscarComItens(Long codigo) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Venda.class);
		criteria.createAlias("itens", "i", JoinType.LEFT_OUTER_JOIN);	
		criteria.add(Restrictions.eq("codigo", codigo));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);	
		return (Venda) criteria.uniqueResult();
	}
	
	@Override
	public BigDecimal valorTotalNoAno() {
		Optional<BigDecimal> optional = Optional.ofNullable(
				manager.createQuery("select sum(valorTotal) from Venda where year(dataCriacao) = :ano and "
						+ "statusVenda = :status", BigDecimal.class)
				.setParameter("ano", Year.now().getValue())
				.setParameter("status", StatusVenda.EMITIDA).getSingleResult());
		return optional.orElse(BigDecimal.ZERO);
	}
	
	//usado para preencher o gráfico com zero quando não houver vendas no mes
	@SuppressWarnings("unchecked")
	@Override
	public List<VendaMes> totalPorMes() {
		
		List<VendaMes> vendasMes =  manager.createNamedQuery("Vendas.totalPorMes").getResultList();
		
		LocalDate hoje = LocalDate.now();
		for(int i = 1; i<= 6; i++) {
			
			String mesIdeal = String.format("%d/%02d", hoje.getYear(), hoje.getMonthValue());
			
			boolean possuiMes = vendasMes.stream().filter(v -> v.getMes().equals(mesIdeal)).findAny().isPresent();
			
			if(!possuiMes) {
				vendasMes.add(i - 1, new VendaMes(mesIdeal,0));
			}
			
			hoje = hoje.minusMonths(1);
			
		}
		
		return vendasMes;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<VendaOrigem> totalPorOrigem() {
		
		List<VendaOrigem> vendasOrigem =  manager.createNamedQuery("Vendas.porOrigem").getResultList();
		
		LocalDate hoje = LocalDate.now();
		for(int i = 1; i<= 6; i++) {
			
			String mesIdeal = String.format("%d/%02d", hoje.getYear(), hoje.getMonthValue());
			
			boolean possuiMes = vendasOrigem.stream().filter(v -> v.getMes().equals(mesIdeal)).findAny().isPresent();
			
			if(!possuiMes) {
				vendasOrigem.add(i - 1, new VendaOrigem(mesIdeal,0,0));				
			}
			
			hoje = hoje.minusMonths(1);
			
		}
		
		return vendasOrigem;
	}
	
	@Override
	public BigDecimal valorTotalNoMes() {
		Optional<BigDecimal> optional = Optional.ofNullable(
				manager.createQuery("select sum(valorTotal) from Venda where month(dataCriacao) = :mes and "
						+ "statusVenda = :status", BigDecimal.class)
				.setParameter("mes", MonthDay.now().getMonthValue())
				.setParameter("status", StatusVenda.EMITIDA).getSingleResult());
		return optional.orElse(BigDecimal.ZERO);
	}
	
	@Override
	public BigDecimal valorTicketMedioNoAno() {
		Optional<BigDecimal> optional = Optional.ofNullable(
				manager.createQuery("select sum(valorTotal)/count(*) from Venda where year(dataCriacao) = :mes and "
						+ "statusVenda = :status", BigDecimal.class)
				.setParameter("mes", Year.now().getValue())
				.setParameter("status", StatusVenda.EMITIDA).getSingleResult());
		return optional.orElse(BigDecimal.ZERO);
	}
	
	private Long total(VendaFilter filtro) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Venda.class);
		adicionarFiltro(filtro, criteria);
		criteria.setProjection(Projections.rowCount());
		return (Long) criteria.uniqueResult();
	}
	
	private void adicionarFiltro(VendaFilter filtro, Criteria criteria) {
		criteria.createAlias("cliente", "c", JoinType.LEFT_OUTER_JOIN);
		
		if(filtro != null) {
			
			if(!StringUtils.isEmpty(filtro.getCodigo())) {
				criteria.add(Restrictions.eq("codigo", filtro.getCodigo()));
			}
			if(!StringUtils.isEmpty(filtro.getCpfOuCnpjCliente())) {
				criteria.add(Restrictions.eq("c.cpfOuCnpj", TipoPessoa.removerFormatacao(filtro.getCpfOuCnpjCliente())));
			}
			if(!StringUtils.isEmpty(filtro.getNomeCliente())) {
				criteria.add(Restrictions.ilike("c.nome", filtro.getNomeCliente(), MatchMode.ANYWHERE));
			}
			if(filtro.getStatusVenda() != null) {
				criteria.add(Restrictions.eq("statusVenda", filtro.getStatusVenda()));
			}
			if (filtro.getDataComeco() != null) {
				LocalDateTime desde = LocalDateTime.of(filtro.getDataComeco(), LocalTime.of(0, 0));
				criteria.add(Restrictions.ge("dataCriacao", desde));
			}
			
			if (filtro.getDataFim() != null) {
				LocalDateTime ate = LocalDateTime.of(filtro.getDataFim(), LocalTime.of(23, 59));
				criteria.add(Restrictions.le("dataCriacao", ate));
			}
			
			if (filtro.getValorMinimo() != null) {
				criteria.add(Restrictions.ge("valorTotal", filtro.getValorMinimo()));
			}
			
			if (filtro.getValorMaximo() != null) {
				criteria.add(Restrictions.le("valorTotal", filtro.getValorMaximo()));
			}
		}
	}
	
}
