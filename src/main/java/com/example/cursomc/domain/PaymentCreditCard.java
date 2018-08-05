package com.example.cursomc.domain;

import javax.persistence.Entity;

import com.example.cursomc.domain.enums.StatusPayment;
import com.fasterxml.jackson.annotation.JsonTypeName;

@Entity
@JsonTypeName("paymentCreditCard")
public class PaymentCreditCard extends Payment {
	private static final long serialVersionUID = 1L;

	private Integer numberOfInstallments;

	public PaymentCreditCard() {

	}

	public PaymentCreditCard(Integer id, StatusPayment status, Order order, Integer numberOfInstallments) {
		super(id, status, order);
		this.numberOfInstallments = numberOfInstallments;
	}

	public Integer getNumberOfInstallments() {
		return numberOfInstallments;
	}

	public void setNumberOfInstallments(Integer numberOfInstallments) {
		this.numberOfInstallments = numberOfInstallments;
	}

}
