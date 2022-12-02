<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="org.iesvegademijas.model.Departamento"%>
<%@page import="java.util.Optional"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Detalle Departamento</title>
<style>
.clearfix::after {
	content: "";
	display: block;
	clear: both;
}
</style>

</head>
<body>

	<main>

		<div id="contenedora"
			style="float: none; margin: 0 auto; width: 900px;">
			<form action="/web_empleados/departamentos/editar/" method="post">
				<input type="hidden" name="__method__" value="put" />
				<div class="clearfix">
					<div style="float: left; width: 50%">
						<h1>Editar Departamento</h1>
					</div>
					<div
						style="float: none; width: auto; overflow: hidden; min-height: 80px; position: relative;">

						<div style="position: absolute; left: 39%; top: 39%;">
							<input type="submit" value="Guardar" />
						</div>

					</div>
				</div>

				<div class="clearfix">
					<hr />
				</div>

				<%
				Optional<Departamento> optDep = (Optional<Departamento>) request.getAttribute("departamento");
				if (optDep.isPresent()) {
				%>

				<div style="margin-top: 6px;" class="clearfix">
					<div style="float: left; width: 50%">
						<label>Código</label>
					</div>
					<div style="float: none; width: auto; overflow: hidden;">
						<input style="border: none;" name="codigo"
							value="<%=optDep.get().getId()%>" readonly="readonly" />
					</div>
				</div>
				<div style="margin-top: 6px;" class="clearfix">
					<div style="float: left; width: 50%">
						<label>Nombre</label>
					</div>
					<div style="float: none; width: auto; overflow: hidden;">
						<input name="nombre" value="<%=optDep.get().getNombre()%>" />
					</div>
				</div>

				<%
				} else {
				%>

				request.sendRedirect("departamentos/");

				<%
				}
				%>
			</form>
		</div>

	</main>

</body>
</html>