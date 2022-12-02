package org.iesvegademijas.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.iesvegademijas.model.Departamento;

public class DepartamentoDAOImpl extends AbstractDAOImpl implements DepartamentoDAO {

	/**
	 * Inserta en base de datos el nuevo departamento, actualizando el id en el bean
	 * departamento.
	 */
	@Override
	public synchronized void create(Departamento departamento) {

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = connectDB();

			ps = conn.prepareStatement("INSERT INTO departamento (nombre, presupuesto, gastos) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

			int idx = 1; //Asignamos un indice por si tuviéramos que recorrer más valores
			ps.setString(idx++, departamento.getNombre());
			ps.setDouble(idx++, departamento.getPresupuesto());
			ps.setDouble(idx++, departamento.getGastos());

			int rows = ps.executeUpdate(); //Aplicamos los cambios y ejecutamos la query
			if (rows == 0)
				System.out.println("INSERT de departamento con 0 filas insertadas.");

			rs = ps.getGeneratedKeys(); //Obtenemos las celdas que queremos modificar restantes usando los valores predeterminados
			if (rs.next())
				departamento.setId(rs.getInt(1)); //Asignamos el valor predeterminado

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			closeDb(conn, ps, rs); //Al final siempre cerramos conexión con la base de datos
		}

	}

	/**
	 * Devuelve lista con todos loa departamentos.
	 */
	@Override
	public List<Departamento> getAll() {

		Connection conn = null;
		Statement s = null;
		ResultSet rs = null;

		List<Departamento> listDep = new ArrayList<>();

		try {
			conn = connectDB();

			// Se utiliza un objeto Statement dado que no hay parámetros en la consulta.
			s = conn.createStatement();

			rs = s.executeQuery("SELECT * FROM departamento"); //Obtenemos las celdas que queremos modificar
			while (rs.next()) { //de mientras haya celdas
				Departamento dep = new Departamento();
				int idx = 1;
				dep.setId(rs.getInt(idx++));
				dep.setNombre(rs.getString(idx++));
				dep.setPresupuesto(rs.getDouble(idx++));
				dep.setGastos(rs.getDouble(idx++));
				listDep.add(dep);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			closeDb(conn, s, rs);
		}
		return listDep;

	}

	/**
	 * Devuelve Optional de departamento con el ID dado.
	 */
	@Override
	public Optional<Departamento> find(int id) {

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = connectDB();

			ps = conn.prepareStatement("SELECT * FROM departamento WHERE id = ?");

			int idx = 1;
			ps.setInt(idx, id);

			rs = ps.executeQuery();

			if (rs.next()) {
				Departamento dep = new Departamento();
				idx = 1;
				dep.setId(rs.getInt(idx++));
				dep.setNombre(rs.getString(idx++));
				dep.setPresupuesto(rs.getDouble(idx++));
				dep.setGastos(rs.getDouble(idx));

				return Optional.of(dep); //Para devolverlo en modo Optional
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			closeDb(conn, ps, rs);
		}

		return Optional.empty(); //Devuelve un optional vacío

	}

	/**
	 * Actualiza departamento con campos del bean departamento según ID del mismo.
	 */
	@Override
	public void update(Departamento departamento) {

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = connectDB();

			ps = conn.prepareStatement("UPDATE departamento SET nombre = ? WHERE id = ?");
			
			int idx = 1;
			ps.setString(idx++, departamento.getNombre());
			ps.setInt(idx, departamento.getId());

			int rows = ps.executeUpdate();

			if (rows == 0)
				System.out.println("Update de departamento con 0 registros actualizados.");

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			closeDb(conn, ps, rs);
		}

	}

	/**
	 * Borra departamento con ID proporcionado.
	 */
	@Override
	public void delete(int id) {

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = connectDB();

			ps = conn.prepareStatement("DELETE FROM departamento WHERE id = ?");
			int idx = 1;
			ps.setInt(idx, id);

			int rows = ps.executeUpdate();

			if (rows == 0)
				System.out.println("Delete de departamento con 0 registros eliminados.");

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			closeDb(conn, ps, rs);
		}

	}

	/**
	 * Devuelve Optional de Integer con el ID dado.
	 */
	@Override
	public Optional<Integer> getCountEmpleados(int id) {

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = connectDB();

			ps = conn.prepareStatement("SELECT COUNT(*) FROM empleado WHERE id_departamento = ?");

			int idx = 1;
			ps.setInt(idx, id);

			rs = ps.executeQuery();

			if (rs.next()) {
				Integer num;
				idx = 1;
				num = rs.getInt(idx);

				return Optional.of(num);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			closeDb(conn, ps, rs);
		}

		return Optional.empty();

	}

	/**
	 * Devuelve una lista con todos los DTO e Integer con el ID dado.
	 */
	@Override
	public List<DepartamentoDTO> getAllDTOPlusCountEmpleados() {

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		List<DepartamentoDTO> listaDep = new ArrayList<>();

		try {
			conn = connectDB();

			ps = conn.prepareStatement(
					"select d.*, count(e.id) as numEmpl from departamento d left outer join empleado e on d.id = e.id_departamento group by d.id;");

			rs = ps.executeQuery();

			while (rs.next()) {
				DepartamentoDTO dep = new DepartamentoDTO();
				dep.setId(rs.getInt("id"));
				dep.setNombre(rs.getString("nombre"));
				dep.setPresupuesto(rs.getDouble("presupuesto"));
				dep.setGastos(rs.getDouble("gastos"));
				dep.setNumEmpleados(rs.getInt("numEmpl"));

				listaDep.add(dep);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			closeDb(conn, ps, rs);
		}

		return listaDep;

	}

	/**
	 * Devuelve una lista con todos los DTO e Integer con el ID dado.
	 */
	@Override
	public List<DepartamentoDTO> getAllRango(Double desde, Double hasta) {

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		List<DepartamentoDTO> listaDep = new ArrayList<>();

		try {
			conn = connectDB();
			
			ps = conn.prepareStatement("""
						SELECT
							d.*, COUNT(d.id) AS numEmpl
						FROM
							departamento d
								LEFT OUTER JOIN empleado e ON d.id = e.id_departamento
						where (d.presupuesto - d.gastos) >= ? AND (d.presupuesto - d.gastos) <= ?
                        GROUP BY d.id;
					"""); //También se puede poner con BETWEEN

			int idx = 1;
			ps.setDouble(idx++, desde);
			ps.setDouble(idx, hasta);

			rs = ps.executeQuery();

			while (rs.next()) {
				DepartamentoDTO dep = new DepartamentoDTO();
				dep.setId(rs.getInt("id"));
				dep.setNombre(rs.getString("nombre"));
				dep.setPresupuesto(rs.getDouble("presupuesto"));
				dep.setGastos(rs.getDouble("gastos"));
				dep.setNumEmpleados(rs.getInt("numEmpl"));

				listaDep.add(dep);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			closeDb(conn, ps, rs);
		}

		return listaDep;

	}

}