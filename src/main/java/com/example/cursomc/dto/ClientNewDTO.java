package com.example.cursomc.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.example.cursomc.services.validation.ClientInsert;

@ClientInsert
public class ClientNewDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@NotEmpty(message="Required Field")
	@Length(min=5, max = 120, message="The size must to be between 5 and 120 characters")
	private String name;
	
	@NotEmpty(message="Required Field")
	@Email(message="Invalid email")
	
	@NotEmpty(message="Required Field")
	private String email;
	
	@NotEmpty(message="Required Field")
	private String tid;
	private Integer type;
	
	@NotEmpty(message="Required Field")
	private String street;
	
	@NotEmpty(message="Required Field")
	private String number;
	private String complement;
	private String district;
	
	@NotEmpty(message="Required Field")
	private String zipCode;
	
	@NotEmpty(message="Required Field")
	private String phone1;
	private String phone2;
	private String phone3;
	
	private Integer cityId;

	public ClientNewDTO() {
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}


	public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getComplement() {
		return complement;
	}

	public void setComplement(String complement) {
		this.complement = complement;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getPhone1() {
		return phone1;
	}

	public void setPhone1(String phone1) {
		this.phone1 = phone1;
	}

	public String getPhone2() {
		return phone2;
	}

	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}

	public String getPhone3() {
		return phone3;
	}

	public void setPhone3(String phone3) {
		this.phone3 = phone3;
	}

	public Integer getCityId() {
		return cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}
	
	
}
