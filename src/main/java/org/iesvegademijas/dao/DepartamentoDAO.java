package org.iesvegademijas.dao;

import java.util.List;
import java.util.Optional;

import org.iesvegademijas.model.Departamento;

public interface DepartamentoDAO {
		
	public void create(Departamento departamento);
	
	public List<Departamento> getAll();
	
	public Optional<Departamento>  find(int id);
	
	public void update(Departamento departamento);
	
	public void delete(int id);
	
	public Optional<Integer> getCountEmpleados(int codigo);
	
	public List<DepartamentoDTO> getAllDTOPlusCountEmpleados();
	
	public List<DepartamentoDTO> getAllRango(Double desde, Double hasta);

}
