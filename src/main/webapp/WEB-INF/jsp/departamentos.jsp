<%@page import="org.iesvegademijas.dao.DepartamentoDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="org.iesvegademijas.model.Departamento"%>
<%@page import="java.util.List"%>

<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="UTF-8">
<title>Departamentos</title>
<style>
.clearfix::after {
	content: "";
	display: block;
	clear: both;
}
</style>

</head>

<main>
	<div id="contenedora"
		style="float: none; margin: 0 auto; width: 900px;">
		<div class="clearfix">
			<div style="float: left; width: 50%">
				<a href="http://localhost:8080/web_empleados/departamentos">
					<h1>Departamentos</h1>
				</a>
			</div>
			<div
				style="float: none; width: auto; overflow: hidden; min-height: 80px; position: relative;">

				<div style="position: absolute; top: 40%;">
					<form method="GET" action="/web_empleados/departamentos">
						<span>Rango de presupuesto: </span> 
						<input type="number" name="desde">
						<input type="number" name="hasta">
							<input type="submit" value="Filtrar"
							style="background-color: lightblue; border-radius: 5px;">
					</form>
				</div>

				<div style="position: absolute; left: 80%; top: 40%;">

					<form action="/web_empleados/departamentos/crear">
						<input type="submit" value="Crear"
							style="background-color: lightgreen; border-radius: 5px;">
					</form>
				</div>

			</div>
		</div>
		<div class="clearfix">
			<hr />
		</div>
		<div class="clearfix">
			<div style="float: left; width: 5%">Código</div>
			<div style="float: left; width: 25%">Nombre</div>
			<div style="float: left; width: 10%">Presupuesto</div>
			<div style="float: left; width: 10%">Gastos</div>
			<div style="float: left; width: 10%">Nº empleados</div>
			<div style="float: none; width: auto; overflow: hidden;">Acción</div>
		</div>
		<div class="clearfix">
			<hr />
		</div>
		<%
		if (request.getAttribute("listaDepartamentos") != null) {
			List<DepartamentoDTO> listaDepartamento = (List<DepartamentoDTO>) request.getAttribute("listaDepartamentos");

			for (DepartamentoDTO departamento : listaDepartamento) {
		%>

		<div style="margin-top: 6px;" class="clearfix">
			<div style="float: left; width: 5%"><%=departamento.getId()%></div>
			<div style="float: left; width: 25%"><%=departamento.getNombre()%></div>
			<div style="float: left; width: 10%"><%=departamento.getPresupuesto()%></div>
			<div style="float: left; width: 10%"><%=departamento.getGastos()%></div>
			<div style="float: left; width: 10%"><%=departamento.getNumEmpleados()%></div>
			<div style="float: none; width: auto; overflow: hidden;">
				<form
					action="/web_empleados/departamentos/<%=departamento.getId()%>"
					style="display: inline;">
					<input type="submit" value="Ver Detalle" />
				</form>

				<form
					action="/web_empleados/departamentos/editar/<%=departamento.getId()%>"
					style="display: inline;">
					<input type="submit" value="Editar" />
				</form>
				<form action="/web_empleados/departamentos/borrar/" method="post"
					style="display: inline;">
					<input type="hidden" name="__method__" value="delete" /> <input
						type="hidden" name="codigo" value="<%=departamento.getId()%>" />
					<input type="submit" value="Eliminar" />
				</form>
				
			</div>
		</div>
		<%
		}
		} else {
		%>
		No hay registros de departamento
		<%
		}
		%>
	</div>
</main>
</body>
</html>