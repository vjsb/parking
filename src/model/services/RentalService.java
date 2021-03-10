package model.services;

import model.entities.CarRental;
import model.entities.Invoice;

public class RentalService {

	private Double pricePerDay;
	private Double pricePerHour;
	
	//tera uma taxa de serviço generica implementada pelo TaxService
	private TaxService taxServices;

	public RentalService(Double pricePerDay, Double pricePerHour, TaxService taxServices) {
		super();
		this.pricePerDay = pricePerDay;
		this.pricePerHour = pricePerHour;
		this.taxServices = taxServices;
	}
	
	//gera a nota de pagamento, recibo
	public void processInvoice(CarRental carRental) {
		long t1 = carRental.getStart().getTime();
		long t2 = carRental.getFinish().getTime();
		double hours = (double)(t2 - t1) / 1000 / 60 / 60; //pega a diferença e transforma em horas ao invés de milisegundos
	
		double basicPayment;
		if (hours <= 12.0) {
			//Math.ceil serve para arredondar
			basicPayment = Math.ceil(hours) * pricePerHour;
		}
		else {
			//divido por 24 porque quero arredondar os dias
			basicPayment = Math.ceil(hours / 24) * pricePerDay;
		}
		
		//calcula o valor do imposto a partir da quantidade basicPayment
		double tax = taxServices.tax(basicPayment);
		
		//objeto de nota d pagamento associado com o objeto de aluguel
		carRental.setInvoice(new Invoice(basicPayment, tax));
	
	}
	
	
}
