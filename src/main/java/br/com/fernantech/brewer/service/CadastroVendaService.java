package br.com.fernantech.brewer.service;

import java.time.LocalDateTime;
import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.fernantech.brewer.model.StatusVenda;
import br.com.fernantech.brewer.model.Venda;
import br.com.fernantech.brewer.repository.Vendas;
import br.com.fernantech.brewer.service.event.venda.VendaEvent;

@Service
public class CadastroVendaService {

	@Autowired
	private Vendas vendas;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Transactional
	public Venda salvar(Venda venda) {
		
		if(venda.isSalvarProibido()) {
			throw new RuntimeException("Usuário tentando salvar uma venda proibida!");
		}
		
		if(venda.isNova()) {
			venda.setDataCriacao(LocalDateTime.now());
		}else {
			Venda vendaExistente = this.vendas.getOne(venda.getCodigo());
			venda.setDataCriacao(vendaExistente.getDataCriacao());
		}
			
		if(venda.getDataEntrega() != null) {
			venda.setDataHoraEntrega(LocalDateTime.of(venda.getDataEntrega(), venda.getHorarioEntrega() != null ? venda.getHorarioEntrega() : 
				LocalTime.NOON));
		}
		
		return vendas.saveAndFlush(venda);
	}

	@Transactional
	public void emitir(Venda venda) {
		venda.setStatusVenda(StatusVenda.EMITIDA);		
		salvar(venda);
		
		publisher.publishEvent(new VendaEvent(venda));
	}

	@PreAuthorize("#venda.usuario == principal.usuario or hasRole('CANCELAR_VENDA')") //principal é o usuário do sistema, principal é o spring que coloca. Somente adm ou usuario que criou a venda podem cancelar
	@Transactional
	public void cancelar(Venda venda) {
		Venda vendaExistente = this.vendas.getOne(venda.getCodigo());
				
		vendaExistente.setStatusVenda(StatusVenda.CANCELADA);
		vendas.save(vendaExistente);
	}
}
