package net.staniscia.odynodatabus.tests;

import java.io.Serializable;

public class QuestoMessaggioDaMandare implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 8425257141244412701L;

	private String nome;
	private int numero;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

}
