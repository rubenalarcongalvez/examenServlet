package org.iesvegademijas.dao;

import org.iesvegademijas.model.Departamento;

public class DepartamentoDTO extends Departamento {

	private int numEmpleados;

	public DepartamentoDTO() {}

	public Integer getNumEmpleados() {
		return numEmpleados;
	}

	public void setNumEmpleados(int numEmpleados) {
		this.numEmpleados = numEmpleados;
	}

	@Override
	public String toString() {
		return super.toString() + ", número de empleados=" + numEmpleados;
	}

}