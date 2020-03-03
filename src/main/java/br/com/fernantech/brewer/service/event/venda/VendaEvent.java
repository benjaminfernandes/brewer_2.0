package br.com.fernantech.brewer.service.event.venda;

import br.com.fernantech.brewer.model.Venda;

public class VendaEvent {
	
	public VendaEvent(Venda venda) {
		this.venda = venda;
	}

	private Venda venda;

	public Venda getVenda() {
		return venda;
	}
	
	
}
