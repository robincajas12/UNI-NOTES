/* select nombre,apell1,apell2,dni,direccion,sexo,sueldo,nrp as xd from empleado
 where (dep=4 and sueldo > 25000) or (dep=5 and sueldo > 30000)
 order by dep desc*/
select dni, apell1, apell2, sueldo, (sueldo * 0.945) iess from empleado;
select dni, apell1, apell2, sueldo, (sueldo * 0.945) as iess from empleado;
select dni, apell1, apell2, sueldo, (sueldo * @iessImp) as iess, sueldo - (sueldo * @iessImp) as sueldo_recibir from empleado
select nombre,apell1,apell2,dni,direccion,sexo,sueldo,nrp as xd from empleado
 where (dep=4 and sueldo > 25000)
select * from producto;
select * from fabricante ;
select producto.nombre as p_nombre, producto.precio, fabricante.nombre as f_nombre from producto cross join fabricante on producto.id_fabricante = fabricante.id;

-- ddl para objetos, dml para datos
