package org.iesvegademijas.model;

public class Departamento {

	private int id;
	private String nombre;
	private double presupuesto;
	private double gastos;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public double getPresupuesto() {
		return this.presupuesto;
	}

	public void setPresupuesto(double presupuesto) {
		this.presupuesto = presupuesto;
	}
	
	public double getGastos() {
		return this.gastos;
	}

	public void setGastos(double gastos) {
		this.gastos = gastos;
	}

	@Override
	public String toString() {
		return "Departamento:\n codigo=" + id + ", nombre=" + nombre + ", presupuesto=" + presupuesto + ", gastos=" + gastos;
	}
	
}
