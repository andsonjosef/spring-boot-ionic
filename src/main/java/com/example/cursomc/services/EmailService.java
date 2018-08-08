package com.example.cursomc.services;

import org.springframework.mail.SimpleMailMessage;

import com.example.cursomc.domain.Order;

public interface EmailService {
	
	void sendOrderConfrimationEmail(Order obj);
	
	void sendEmail(SimpleMailMessage msg);

}
