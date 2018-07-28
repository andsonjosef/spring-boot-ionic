package com.example.cursomc.domain;

import java.util.Date;

import javax.persistence.Entity;

import com.example.cursomc.domain.enums.StatusPayment;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class PaymentBankTransfer extends Payment {
	private static final long serialVersionUID = 1L;

	@JsonFormat(pattern="dd/MM/yyyy")
	private Date expiryDate;
	@JsonFormat(pattern="dd/MM/yyyy")
	private Date payDate;

	public PaymentBankTransfer() {

	}

	public PaymentBankTransfer(Integer id, StatusPayment status, Order order, Date expiryDate, Date payDate) {
		super(id, status, order);
		this.expiryDate = expiryDate;
		this.payDate = payDate;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public Date getPayDate() {
		return payDate;
	}

	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}

}
