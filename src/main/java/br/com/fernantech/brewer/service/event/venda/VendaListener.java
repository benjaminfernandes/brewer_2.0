package br.com.fernantech.brewer.service.event.venda;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import br.com.fernantech.brewer.model.Cerveja;
import br.com.fernantech.brewer.model.ItemVenda;
import br.com.fernantech.brewer.repository.Cervejas;

@Component
public class VendaListener {

	@Autowired
	private Cervejas cervejas;
	
	@EventListener
	public void VendaEmitida(VendaEvent vendaEvent) {
		for(ItemVenda item : vendaEvent.getVenda().getItens()){
			Cerveja cerveja = this.cervejas.getOne(item.getCerveja().getCodigo());
			cerveja.setQuantidadeEstoque(cerveja.getQuantidadeEstoque() - item.getQuantidade());
			this.cervejas.save(cerveja);
		}
	}
}
