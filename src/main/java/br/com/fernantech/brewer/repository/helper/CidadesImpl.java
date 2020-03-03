package br.com.fernantech.brewer.repository.helper;

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

import br.com.fernantech.brewer.model.Cidade;
import br.com.fernantech.brewer.repository.filter.CidadeFilter;
import br.com.fernantech.brewer.repository.paginacao.PaginacaoUtil;

public class CidadesImpl implements CidadesQueries{

	@PersistenceContext
	private EntityManager manager;
	@Autowired
	private PaginacaoUtil paginacaoUtil;
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public Page<Cidade> filtrar(CidadeFilter filtro, Pageable pageable) {		
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Cidade.class);		
		this.paginacaoUtil.preparar(criteria, pageable);		
		adicionarFiltro(filtro, criteria);		
		criteria.createAlias("estado", "e", JoinType.INNER_JOIN);
		return new PageImpl<>(criteria.list(), pageable, total(filtro));	
	}
	
	private Long total(CidadeFilter filtro) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Cidade.class);
		adicionarFiltro(filtro, criteria);
		criteria.setProjection(Projections.rowCount());
		return (Long) criteria.uniqueResult();
	}

	private void adicionarFiltro(CidadeFilter filtro, Criteria criteria) {
		
		if(filtro != null) {
			if(!StringUtils.isEmpty(filtro.getNome())) {
				criteria.add(Restrictions.ilike("nome", filtro.getNome(), MatchMode.ANYWHERE));
			}
			if(filtro.getEstado() != null) {
				criteria.add(Restrictions.eq("estado", filtro.getEstado()));
			}
		}
	}
}
