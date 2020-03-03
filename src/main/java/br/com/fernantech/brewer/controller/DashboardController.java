package br.com.fernantech.brewer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.fernantech.brewer.repository.Cervejas;
import br.com.fernantech.brewer.repository.Clientes;
import br.com.fernantech.brewer.repository.Vendas;

@Controller
public class DashboardController {
	
	@Autowired
	private Vendas vendas;
	@Autowired
	private Clientes clientes;
	@Autowired
	private Cervejas cervejas;

	@GetMapping("/")
	public ModelAndView dashboard() {
		ModelAndView mv = new ModelAndView("Dashboard");
		
		mv.addObject("vendasNoAno", this.vendas.valorTotalNoAno());
		mv.addObject("vendasNoMes", this.vendas.valorTotalNoMes());
		mv.addObject("ticketMedio", this.vendas.valorTicketMedioNoAno());
		mv.addObject("totalClientes",this.clientes.count());
		mv.addObject("valorItensEstoque", this.cervejas.valorItensEstoque());
		
		return mv;

	}
}
