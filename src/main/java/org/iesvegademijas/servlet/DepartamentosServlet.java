package org.iesvegademijas.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.iesvegademijas.dao.DepartamentoDAO;
import org.iesvegademijas.dao.DepartamentoDAOImpl;
import org.iesvegademijas.model.Departamento;

/**
 * Servlet implementation class DepartamentosServlet
 */
@WebServlet("/departamentos/*")
public class DepartamentosServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public DepartamentosServlet() {
        super();
    }

    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher;
		
		String pathInfo = request.getPathInfo();
		
		if (pathInfo == null || "/".equals(pathInfo)) {
			
			DepartamentoDAO depDAO = new DepartamentoDAOImpl();
			
			// GET
			// /departamentos/
			// /departamentos
			
			var listaDepDTO = depDAO.getAllDTOPlusCountEmpleados();
			
			String desdeString = request.getParameter("desde");
			String hastaString = request.getParameter("hasta");
			
			Double desde = null;
			Double hasta = null;
			
			if (desdeString != null && hastaString != null) {
				desde = Double.parseDouble(desdeString);
				hasta = Double.parseDouble(hastaString);
			}
			
			if (desde != null && hasta != null) {
				var listaRango = depDAO.getAllRango(desde, hasta);
				request.setAttribute("listaDepartamentos", listaRango);
				dispatcher = request.getRequestDispatcher("WEB-INF/jsp/departamentos.jsp");
			} else {
				request.setAttribute("listaDepartamentos", listaDepDTO);
				dispatcher = request.getRequestDispatcher("WEB-INF/jsp/departamentos.jsp");
			}
			
		} else {
			
			pathInfo = pathInfo.replaceAll("/$", "");
			String[] pathParts = pathInfo.split("/");
			
			if (pathParts.length == 2 && "crear".equalsIgnoreCase(pathParts[1])) {
				dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/crear-departamento.jsp");
			} else if (pathParts.length == 3 && "editar".equalsIgnoreCase(pathParts[1])) {
				DepartamentoDAO depDAO = new DepartamentoDAOImpl();
				
				try {
					request.setAttribute("departamento", depDAO.find(Integer.parseInt(pathParts[2])));
					dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/editar-departamento.jsp");
				} catch (Exception ex) {
					ex.printStackTrace();
					dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/departamentos.jsp");
				}
				
			} else {
				System.out.println("Operación POST no soportada.");
				dispatcher = request.getRequestDispatcher("WEB-INF/jsp/departamentos.jsp");
			}
			
		}
		
		dispatcher.forward(request, response);
		
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		RequestDispatcher dispatcher;
		String __method__ = request.getParameter("__method__");

		if (__method__ == null) {
			// Crear uno nuevo
			DepartamentoDAO depDAO = new DepartamentoDAOImpl();

			String nombre = request.getParameter("nombre");
			Departamento nuevoDep = new Departamento();
			nuevoDep.setNombre(nombre);
			depDAO.create(nuevoDep);

		} else if (__method__ != null && "put".equalsIgnoreCase(__method__)) {
			// Actualizar uno existente
			// Dado que los forms de html sólo soportan method GET y POST utilizo parámetro
			// oculto para indicar la operación de actulización PUT.
			doPut(request, response);

		} else if (__method__ != null && "delete".equalsIgnoreCase(__method__)) {
			// Actualizar uno existente
			// Dado que los forms de html sólo soportan method GET y POST utilizo parámetro
			// oculto para indicar la operación de actulización DELETE.
			doDelete(request, response);

		} else {

			System.out.println("Opción POST no soportada.");

		}

		response.sendRedirect("/web_empleados/departamentos"); // Sirve para redireccionar una vez lo creamos

	}

	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		DepartamentoDAO depDAO = new DepartamentoDAOImpl();
		String id = request.getParameter("id"); // Coge el codigo del jsp
		String nombre = request.getParameter("nombre"); // Coge el nombre del jsp
		String presupuestoStr = request.getParameter("presupuesto"); // Coge el nombre del jsp
		String gastosStr = request.getParameter("gastos"); // Coge el nombre del jsp
		Departamento dep = new Departamento(); // Crea al nuevo fabricante

		try {

			int codigo = Integer.parseInt(id);
			Double presupuesto = Double.parseDouble(presupuestoStr);
			Double gastos = Double.parseDouble(gastosStr);
			
			dep.setId(codigo);
			dep.setNombre(nombre);
			dep.setPresupuesto(presupuesto);
			dep.setGastos(gastos);
			depDAO.update(dep); // Añade al fabricante

		} catch (NumberFormatException nfe) {
			nfe.printStackTrace();
		}

	}

	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
