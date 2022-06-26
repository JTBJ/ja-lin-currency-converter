package com.ja.currency_converter.converter.model;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class User {

	private String name;
	private Map<String, Double> wallet = new HashMap<>();
	
	@JsonCreator
	public User(@JsonProperty(value = "name")String name, 
			@JsonProperty(value = "wallet")Map<String, Double> wallet) {
		this.name = name;
		this.wallet = wallet;
	}

	public User withName(String name) {
		this.setName(name);
		return this;
	}
	
	public User withWallet(Map<String, Double> wallet) {
		this.setWallet(wallet);
		return this;
	}

}
