package com.example.cursomc.services;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.example.cursomc.domain.PaymentBankTransfer;

@Service
public class BankTransferService {
	
	public void fillPaymentBankTransfer(PaymentBankTransfer pay, Date momentOfOrder) {
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(momentOfOrder);
		cal.add(Calendar.DAY_OF_MONTH, 7);
		pay.setExpiryDate(cal.getTime());
	}

}
