Drop database if exists DBKinalMall2020202;
create database DBKinalMall2020202;
Use DBKinalMall2020202;

-- ******ENTIDADES******
create table Departamentos(
	codigoDepartamento int not null auto_increment,
    nombreDepartamento VARCHAR(45) not null,
    primary key PK_codigoDepartamentos(codigoDepartamento)
);
create table Cargos(
	codigoCargo int not null auto_increment,
    nombreCargo VARCHAR(45) not null,
    primary key PK_codigoCargo(codigoCargo)
);
create table Horarios(	
	codigoHorario int not null auto_increment,
    horaEntrada VARCHAR (5) not null,
    horaSalida VARCHAR (5) not null,
    lunes boolean,
    martes boolean,
    miercoles boolean,
    jueves boolean,
    viernes boolean,
    primary key PK_codigoHorario(codigoHorario)
 );
create table TipoCliente(
	codigoTipoCliente int not null auto_increment,
    descripcion VARCHAR(45) not null,
    primary key PK_codigoTipoCliente(codigoTipoCliente)

);
create table Locales(
	codigoLocal int not null auto_increment,
    saldoFavor double(11,2) default 0.0,
    saldoContra double(11,2) default 0.0,
    mesesPendientes int default 0,
    disponibilidad boolean not null,
    valorLocal double(11,2) not null,
    valorAdministracion double(11,2) not null,
    primary key PK_codigoLocal(codigoLocal)
);
create table Administracion(
	codigoAdministracion int not null auto_increment,
    direccion VARCHAR(45) not null,
    telefono VARCHAR(8) not null,
    primary key PK_codigoAdministracion(codigoAdministracion)
);
create table Empleados(
	codigoEmpleado int not null auto_increment,
    nombreEmpleado VARCHAR(45) not null,
    apellidoEmpleado VARCHAR(45) not null,
    correoElectronico VARCHAR(45) not null,
    telefonoEmpleado VARCHAR(8) not null,
    fechaContratacion Date not null,
    sueldo double not null,
    codigoDepartamento int not null,
    codigoCargo int not null,
    codigoHorario int not null,
    codigoAdministracion int not null,
    primary key PK_codigoEmpleado(codigoEmpleado),
    Constraint FK_Empleados_Departamentos foreign key (codigoDepartamento)
		references Departamentos(codigoDepartamento),
	constraint FK_Empleados_Cargos foreign key(codigoCargo)
		references Cargos(codigoCargo),
    constraint FK_Empleados_Horarios foreign key(codigoHorario)
		references Horarios(codigoHorario),
    constraint FK_Empleados_Administracion foreign key(codigoAdministracion)
		references Administracion(codigoAdministracion)
);
create table Proveedores(
	codigoProveedor int not null auto_increment,
    NITProveedor VARCHAR(45) not null,
    servicioPrestado VARCHAR(45) not null,
    telefonoProveedor VARCHAR(45) not null,
    direccionProveedor VARCHAR(60) not null,
    sueldoFavor double(11,2) not null,
    sueldoContra double(11,2) not null,
    codigoAdministracion int not null,
    primary key PK_codigoProveedor(codigoProveedor),
    constraint FK_Proveedores_Administracion foreign key(codigoAdministracion)
		references Administracion(codigoAdministracion)
);
create table CuentasPorPagar(
	codigoCuentaPorPagar int not null auto_increment,
    numeroFactura VARCHAR(45) not null,
    fechaLimitePago date not null,
    estadoPago VARCHAR(45) not null,
    valorNetoPago double(10,2) not null,
    codigoAdministracion int not null,
    codigoProveedor int not null,
    primary key PK_codigoCuentaPorPagar(codigoCuentaPorPagar),
    constraint FK_CuentasPorPagar_Administracion foreign key(codigoAdministracion)
		references Administracion(codigoAdministracion),
	constraint FK_CuentasPorPagar_Proveedores foreign key(codigoProveedor)
		references Proveedores(codigoProveedor)
);
create table Clientes(
	codigoCliente int not null auto_increment,
	nombreCliente VARCHAR(45) not null,
    apellidoCliente VARCHAR(45) not null,
    telefonoCliente VARCHAR(8) not null,
    direccionCliente VARCHAR(60) not null,
    email varchar(45) not null,
    codigoLocal int not null,
    codigoAdministracion int not null,
    codigoTipoCliente int not null,
    primary key PK_codigoCliente(codigoCliente),
    constraint FK_Clientes_Locales foreign key(codigoLocal)
		references Locales(codigoLocal),
	constraint FK_Clientes_Administracion foreign key(codigoAdministracion)
		references Administracion(codigoAdministracion),
	constraint FK_Clientes_TipoCliente foreign key(codigoTipoCliente)
		references TipoCliente(codigoTipoCliente)
);
create table CuentasPorCobrar(
	codigoCuentaPorCobrar int not null auto_increment,
    numeroFactura VARCHAR(45) not null,
    anio VARCHAR(4) not null,
    mes VARCHAR(2) not null,
    valorNetoPago double(11,2) not null,
    estadoPago VARCHAR(45) not null,
    codigoAdministracion int not null,
    codigoCliente int not null,	
	codigoLocal int not null,
    primary key PK_codigoCuenataPorCobrar(codigoCuentaPorCobrar),
    constraint FK_CuentasPorCobrar_Administracion foreign key (codigoAdministracion)
		references Administracion(codigoAdministracion),
	constraint FK_CuentasPorCobrar_Clientes foreign key (codigoCliente)
		references Clientes(codigoCliente),
	constraint FK_CuentasPorCobrar_Local foreign key(codigoLocal)
		references Locales(codigoLocal)
);
-- *****PROCEDIMIENTOS ALMACENADOS*****
-- //DEPARTAMENTOS//
Delimiter $$
	Create procedure sp_AgregarDepartamento(in nombreDepartamento varchar(45))
    Begin
		insert into Departamentos(nombreDepartamento) values (nombreDepartamento);
End$$
Delimiter ;
Delimiter $$
	Create procedure sp_ListarDepartamentos()
	Begin
		select 
			D.codigoDepartamento, 
            D.nombreDepartamento
		from Departamentos D;
	End$$
Delimiter ;
Delimiter $$
	create procedure sp_EditarDepartamento(in codigoDepartamento2 int, in nombreDepartamento2 varchar(45) )
    Begin
		update Departamentos
			set
				nombreDepartamento=nombreDepartamento2
                where
					codigoDepartamento=codigoDepartamento2;
	End$$
Delimiter;
Delimiter $$
	Create procedure sp_BuscarDepartamento(in codigoDepartamento2 int)
    Begin
		select 
			D.codigoDepartamento,
            D.nombreDepartamento
		from Departamentos D
        where codigoDepartamento2=codigoDepartamento;
    End$$
Delimiter ;
Delimiter $$
	create procedure sp_EliminarDepartamento(in codigoDepartamento2 int)
    Begin
		delete from Departamentos where codigoDepartamento = codigoDepartamento2;
	End$$
Delimiter ;
-- //CARGOS//
Delimiter $$
	Create procedure sp_AgregarCargo(in nombreCargo varchar(45))
    Begin
		insert into Cargos(nombreCargo) values (nombreCargo);
End$$
Delimiter ;
Delimiter $$
	Create procedure sp_ListarCargos()
	Begin
		select 
			C.codigoCargo,
            C.nombreCargo
		from Cargos C;
	End$$
Delimiter ;
Delimiter $$
	create procedure sp_EditarCargo(in codigoCargo2 int, in nombreCargo2 varchar(45) )
    Begin
		update Cargos
			set
				nombreCargo=nombreCargo2
                where
					codigoCargo=codigoCargo2;
	End$$
Delimiter;
Delimiter $$
	Create procedure sp_BuscarCargo(in codigoCargo2 int)
    Begin
		select 
			C.codigoCargo,
            C.nombreCargo
		from Cargos C
        where codigoCargo2 =codigoCargo;
    End$$
Delimiter ;
Delimiter $$
	create procedure sp_EliminarCargo(in codigoCargo2 int)
    Begin
		delete from Cargos where codigoCargo = codigoCargo2;
	End$$
Delimiter ;
-- //HORARIOS//
Delimiter $$
	Create procedure sp_AgregarHorario(in horaEntrada varchar(5), in horaSalida varchar(5), in lunes boolean, 
		in martes boolean, in miercoles boolean, in jueves boolean, in viernes boolean)
    Begin
		insert into Horarios(horaEntrada, horaSalida, lunes, martes, miercoles, jueves, viernes)
			values (horaEntrada, horaSalida, lunes, martes, miercoles, jueves, viernes);
End$$
Delimiter ;
Delimiter $$
	Create procedure sp_ListarHorarios()
	Begin
		select 
			H.codigoHorario, 
            H.horaEntrada, 
            H.horaSalida, 
            H.lunes, 
            H.martes, 
            H.miercoles, 
            H.jueves,
            H.viernes
		from Horarios H;
	End$$
Delimiter ;
Delimiter $$
	create procedure sp_EditarHorario(in codigoHorario2 int, in horaEntrada2 varchar(5), in horaSalida2 varchar(5), in lunes2 boolean, 
		in martes2 boolean, in miercoles2 boolean, in jueves2 boolean, in viernes2 boolean)
    Begin
		update Horarios
			set
               horaEntrada=horaEntrada2,
               horaSalida=horaSalida2,
               lunes=lunes2, 
               martes=martes2, 
               miercoles=miercoles2,
               jueves=jueves2,
               viernes=viernes2
			where 
				codigoHorario2=codigoHorario;
	End$$
Delimiter;
Delimiter $$
	Create procedure sp_BuscarHorario(in codigoHorario2 int)
    Begin
		select 
			C.codigoHorario,
            C.horaEntrada, 
            C.horaSalida, 
            C.lunes, 
            C.martes, 
            C.miercoles, 
            C.jueves, 
            C.viernes
		from Horarios C
        where codigoHorario2 =codigoHorario;
    End$$
Delimiter ;
Delimiter $$
	create procedure sp_EliminarHorario(in codigoHorario2 int)
    Begin
		delete from Horarios where codigoHorario = codigoHorario2;
	End$$
Delimiter ;
-- //TIPOCLIENTE//
Delimiter $$
	Create procedure sp_AgregarTipoCliente(in descripcion varchar(45))
    Begin
		insert into TipoCliente(descripcion) values (descripcion);
End$$
Delimiter ;
Delimiter $$
	Create procedure sp_ListarTipoClientes()
	Begin
		select 
			TC.codigoTipoCliente,
            TC.descripcion
		from TipoCliente TC;
	End$$
Delimiter ;
Delimiter $$
	create procedure sp_EditarTipoCliente(in codigoTipoCliente2 int, in descripcion2 varchar(45) )
    Begin
		update TipoCliente
			set
				descripcion=descripcion2
                where
					codigoTipoCliente=codigoTipoCliente2;
	End$$
Delimiter;
Delimiter $$
	Create procedure sp_BuscarTipoCliente(in codigoTipoCliente2 int)
    Begin
		select 
			TC.codigoTipoCliente,
			TC.descripcion
		from TipoCliente TC
        where codigoTipoCliente2 = codigoTipoCliente;
    End$$
Delimiter ;
Delimiter $$
	create procedure sp_EliminarTipoCliente(in codigoTipoCliente2 int)
    Begin
		delete from TipoCliente where codigoTipoCliente = codigoTipoCliente2;
	End$$
Delimiter ;
-- //LOCALES//
Delimiter $$
	Create procedure sp_AgregarLocal(in disponibilidad boolean, in valorLocal double,in valorAdministracion double)
    Begin
		insert into Locales(disponibilidad, valorLocal, valorAdministracion) 
			values (disponibilidad, valorLocal, valorAdministracion);
End$$
Delimiter ;
-- drop procedure sp_AgregarLocales;
Delimiter $$
	Create procedure sp_ListarLocales()
	Begin
		select 
			L.codigoLocal,
			L.saldoFavor, 
            L.saldoContra, 
            L.mesesPendientes, 
            L.disponibilidad, 
            L.valorLocal, 
            L.valorAdministracion
		from Locales L;
	End$$
Delimiter ;
-- drop procedure sp_ListarLocal;
Delimiter $$
	create procedure sp_EditarLocal(in codigoLocal2 int, in disponibilidad2 boolean, in valorLocal2 double,in valorAdministracion2 double)
    Begin
		update Locales
			set
				/*saldoFavor=saldoFavor2 , 
                saldoContra=saldoContra2, 
                mesesPendientes=mesesPendientes2,*/ 
                disponibilidad=disponibilidad2, 
                valorLocal=valorLocal2, 
                valorAdministracion=valorAdministracion2
                where
					codigoLocal=codigoLocal2;
	End$$
Delimiter;
-- drop procedure sp_EditarLocal;
Delimiter $$
	Create procedure sp_BuscarLocal(in codigoLocal2 int)
    Begin
		select 
			L.codigoLocal, 
            L.saldoFavor, 
            L.saldoContra, 
            L.mesesPendientes, 
            L.disponibilidad, 
            L.valorLocal, 
            L.valorAdministracion
		from Locales L
        where codigoLocal2 = codigoLocal;
    End$$
Delimiter ;
Delimiter $$
	create procedure sp_EliminarLocal(in codigoLocal2 int)
    Begin
		delete from Locales where codigoLocal = codigoLocal2;
	End$$
Delimiter ;
-- //ADMINISTRACION//
Delimiter $$
	Create procedure sp_AgregarAdministracion(in direccion VARCHAR(45), in telefono VARCHAR(8))
    BEGIN
		insert into Administracion(direccion, telefono) values (direccion, telefono);
	END$$
Delimiter ;
Delimiter $$
	Create procedure sp_ListarAdministracion()
	BEGIN
		select 
			A.codigoAdministracion,
            A.direccion, 
            A.telefono
		from Administracion A;
	END$$
Delimiter ;
Delimiter $$
	create procedure sp_EditarAdministracion(in codigoAdministracion2 int, in direccion2 VARCHAR(45),in telefono2 VARCHAR(8))
    BEGIN
		update Administracion
			set
				direccion=direccion2,
                telefono=telefono2
                where
					codigoAdministracion=codigoAdministracion2;
	END$$
Delimiter;
Delimiter $$
	Create procedure sp_BuscarAdministracion(in codigoAdministracion2 int)
    BEGIN
		select 
			A.codigoAdministracion,
			A.direccion,
            A.telefono
		from Administracion A
        where codigoAdministracion2 = codigoAdministracion;
    END$$
Delimiter ;
Delimiter $$
	create procedure sp_EliminarAdministracion(in codigoAdministracion2 int)
    BEGIN
		delete from Administracion where codigoAdministracion = codigoAdministracion2;
	END$$
Delimiter ;
-- //EMPLEADOS//
Delimiter $$
	Create procedure sp_AgregarEmpleado(in nombreEmpleado varchar(45), in apellidoEmpleado varchar(45), in correoElectronico varchar(45), 	
		in telefonoEmpleado varchar(8),in fechaContratacion date, in sueldo double,in codigoDepartamento int,
			in codigoCargo int,in codigoHorario int,in codigoAdministracion int)
    Begin
		insert into Empleados(nombreEmpleado, apellidoEmpleado, correoElectronico, telefonoEmpleado, fechaContratacion, sueldo,
			codigoDepartamento, codigoCargo, codigoHorario, codigoAdministracion) 
				values (nombreEmpleado, apellidoEmpleado, correoElectronico, telefonoEmpleado, fechaContratacion, sueldo, 
					codigoDepartamento, codigoCargo, codigoHorario, codigoAdministracion);
End$$
Delimiter ;
Delimiter $$
	Create procedure sp_ListarEmpleados()
	Begin
		select 
			E.codigoEmpleado,
			E.nombreEmpleado, 
            E.apellidoEmpleado, 
            E.correoElectronico, 
            E.telefonoEmpleado, 
            E.fechaContratacion, 
            E.sueldo, 
            E.codigoDepartamento, 
            E.codigoCargo, 
            E.codigoHorario, 
            E.codigoAdministracion
		from Empleados E;
	End$$
Delimiter ;
-- drop procedure sp_ListarEmpleados;
Delimiter $$
	create procedure sp_EditarEmpleado(in codigoEmpleado2 int, in nombreEmpleado2 varchar(45), in apellidoEmpleado2 varchar(45), 
		in correoElectronico2 varchar(45), in telefonoEmpleado2 varchar(8),in fechaContratacion2 date, in sueldo2 double)
    Begin
		update Empleados
			set
				nombreEmpleado=nombreEmpleado2, 
                apellidoEmpleado=apellidoEmpleado2, 
                correoElectronico=correoElectronico2, 
                telefonoEmpleado=telefonoEmpleado2, 
                fechaContratacion=fechaContratacion2, 
                sueldo=sueldo2 
                where
					codigoEmpleado=codigoEmpleado2;
	End$$
Delimiter;
Delimiter $$
	Create procedure sp_BuscarEmpleado(in codigoEmpleado2 int)
    Begin
		select 
			E.nombreEmpleado, 
            E.apellidoEmpleado, 
            E.correoElectronico, 
            E.telefonoEmpleado, 
            E.fechaContratacion, 
            E.sueldo, 
            E.codigoDepartamento, 
            E.codigoCargo, 
            E.codigoHorario, 
            E.codigoAdministracion
		from Empleados E
        where codigoEmpleado2=codigoEmpleado;
    End$$
Delimiter ;
Delimiter $$
	create procedure sp_EliminarEmpleado(in codigoEmpleado2 int)
    Begin
		delete from Empleados where codigoEmpleado=codigoEmpleado2;
	End$$
Delimiter ;
-- //PROVEEDORES//
Delimiter $$
	Create procedure sp_AgregarProveedor(in NITProveedor varchar(45),in servicioPrestado varchar(45),in telefonoProveedor varchar(45),
		in direccionProveedor varchar(60),in sueldoFavor double,in sueldoContra double,in codigoAdministracion int)
    Begin
		insert into Proveedores(NITProveedor, servicioPrestado, telefonoProveedor, direccionProveedor, sueldoFavor, 
			sueldoContra, codigoAdministracion) 
				values (NITProveedor, servicioPrestado, telefonoProveedor, direccionProveedor, sueldoFavor, sueldoContra,
					codigoAdministracion);
End$$
Delimiter ;
Delimiter $$
	Create procedure sp_ListarProveedores()
	Begin
		select 
			p.codigoProveedor,
			P.NITProveedor, 
            P.servicioPrestado, 
            P.telefonoProveedor, 
            P.direccionProveedor, 
            P.sueldoFavor, 
            P.sueldoContra, 
            P.codigoAdministracion
		from Proveedores P;
	End$$
Delimiter ;
-- drop procedure sp_ListarProveedores;
Delimiter $$
	create procedure sp_EditarProveedor(in codigoProveedor2 int, in NITProveedor2 varchar(45),in servicioPrestado2 varchar(45),
		in telefonoProveedor2 varchar(45),in direccionProveedor2 varchar(60),in sueldoFavor2 double,in sueldoContra2 double)
    Begin
		update Proveedores
			set
				NITProveedor=NITProveedor2, 
                servicioPrestado=servicioPrestado2, 
                telefonoProveedor=telefonoProveedor2, 
                direccionProveedor=direccionProveedor2, 
                sueldoFavor=sueldoFavor2, 
                sueldoContra=sueldoContra2 
                where
					codigoProveedor=codigoProveedor2;
	End$$
Delimiter;
Delimiter $$
	Create procedure sp_BuscarProveedor(in codigoProveedor2 int)
    Begin
		select 
			P.codigoProveedor,
			P.NITProveedor, 
            P.servicioPrestado, 
            P.telefonoProveedor, 
            P.direccionProveedor, 
            P.sueldoFavor, 
            P.sueldoContra, 
            P.codigoAdministracion
		from Proveedores P
        where codigoProveedor2=codigoProveedor;
    End$$
Delimiter ;
-- drop procedure sp_BuscarProveedor;
Delimiter $$
	create procedure sp_EliminarProveedor(in codigoProveedor2 int)
    Begin
		delete from Proveedores where codigoProveedor=codigoProveedor2;
	End$$
Delimiter ;
-- //CUENTASPORPAGAR//
Delimiter $$
	Create procedure sp_AgregarCuentaPorPagar(in numeroFactura varchar(45),in fechaLimitePago date,in estadoPago varchar(45),
		in valorNetoPago double,in codigoAdministracion int , in codigoProveedor int)
    Begin
		insert into CuentasPorPagar(numeroFactura, fechaLimitePago, estadoPago, valorNetoPago, codigoAdministracion,
			codigoProveedor) 
				values (numeroFactura, fechaLimitePago, estadoPago, valorNetoPago, codigoAdministracion,
					codigoProveedor);
End$$
Delimiter ;
Delimiter $$
	Create procedure sp_ListarCuentasPorPagar()
	Begin
		select 
			CP.codigoCuentaPorPagar,
			CP.numeroFactura, 
            CP.fechaLimitePago, 
            CP.estadoPago, 
            CP.valorNetoPago, 
            CP.codigoAdministracion, 
            CP.codigoProveedor
		from CuentasPorPagar CP;
	End$$
Delimiter ;
-- drop procedure sp_ListarCuentasPorPagar;
Delimiter $$
	create procedure sp_EditarCuentaPorPagar(in codigoCuentasPorPagar2 int, in numeroFactura2 varchar(45),in fechaLimitePago2 date,
		in estadoPago2 varchar(45),in valorNetoPago2 double)
    Begin
		update CuentasPorPagar
			set
				numeroFactura=numeroFactura2, 
                fechaLimitePago=fechaLimitePago2, 
                estadoPago=estadoPago2, 
                valorNetoPago=valorNetoPago2 
                where
					codigoCuentaPorPagar=codigoCuentasPorPagar2;
	End$$
Delimiter;
-- drop procedure sp_EditarCuentasPorPagar;
Delimiter $$
	Create procedure sp_BuscarCuentaPorPagar(in codigoCuentasPorPagar2 int)
    Begin
		select 
			CP.codigoCuentaPorPagar,
			CP.numeroFactura, 
            CP.fechaLimitePago, 
            CP.estadoPago, 
            CP.valorNetoPago, 
            CP.codigoAdministracion, 
            CP.codigoProveedor
		from CuentasPorPagar CP
        where codigoCuentasPorPagar2=codigoCuentaPorPagar;
    End$$
Delimiter ;
-- drop procedure sp_BuscarCuentasPorPagar;
Delimiter $$
	create procedure sp_EliminarCuentaPorPagar(in codigoCuentasPorPagar2 int)
    Begin
		delete from CuentasPorPagar where codigoCuentaPorPagar=codigoCuentasPorPagar2;
	End$$
Delimiter ;
-- //CLIENTES//
Delimiter $$
	Create procedure sp_AgregarCliente(in nombreCliente varchar(45),in apellidoCliente varchar(45),in telefonoCliente varchar(8),	
		in direcciónCliente varchar(60),in email varchar(45),in codigoLocal int,in codigoAdministracion int,
			in codigoTipoCliente int)
    Begin
		insert into Clientes(nombreCliente, apellidoCliente, telefonoCliente, direccionCliente, email, codigoLocal, 
			codigoAdministracion, codigoTipoCliente) 
				values (nombreCliente, apellidoCliente, telefonoCliente, direccionCliente, email, codigoLocal, 
					codigoAdministracion, codigoTipoCliente);
End$$
Delimiter ;
-- drop procedure sp_EliminarCliente;
Delimiter $$
	Create procedure sp_ListarClientes()
	Begin
		select 
			c.codigoCliente,
			C.nombreCliente, 
            C.apellidoCliente, 
            C.telefonoCliente, 
            C.direccionCliente, 
            C.email, 
            C.codigoLocal, 
            C.codigoAdministracion, 
            C.codigoTipoCliente
		from Clientes C;
	End$$
Delimiter ;
-- drop procedure sp_ListarClientes;
Delimiter $$
	create procedure sp_EditarCliente(in codigoCliente2 int,in nombreCliente2 varchar(45),in apellidoCliente2 varchar(45),
		in telefonoCliente2 varchar(8),in direccionCliente2 varchar(60),in email2 varchar(45))
    Begin
		update Clientes
			set
				nombreCliente=nombreCliente2,
                apellidoCliente=apellidoCliente2,
                telefonoCliente=telefonoCliente2,
                direccionCliente=direccionCliente2,
                email=email2
                where
					codigoCliente=codigoCliente2;
	End$$
Delimiter;
Delimiter $$
	Create procedure sp_BuscarCliente(in codigoCliente2 int)
    Begin
		select 
			C.codigoCliente,
			C.nombreCliente, 
            C.apellidoCliente, 
            C.telefonoCliente, 
            C.direccionCliente, 
            C.email, 
            C.codigoLocal, 
            C.codigoAdministracion, 
            C.codigoTipoCliente
		from Clientes C
        where codigoCliente2=codigoCliente;
    End$$
Delimiter ;
-- drop procedure sp_BuscarCliente;
Delimiter $$
	create procedure sp_EliminarCliente(in codigoCliente2 int)
    Begin
		delete from Clientes where codigoCliente=codigoCliente2;
	End$$
Delimiter ;
-- //CUENTASPORCOBRAR//
Delimiter $$
	Create procedure sp_AgregarCuentaPorCobrar(in numeroFactura varchar(45),in anio varchar(4),in mes varchar(2),in valorNetoPago double,
		in estadoPago varchar(45),in codigoAdministracion int,in codigoCliente int,in codigoLocal int)
    Begin
		insert into CuentasPorCobrar(numeroFactura, anio, mes, valorNetoPago, estadoPago, codigoAdministracion,
			codigoCliente, codigoLocal) 
				values (numeroFactura, anio, mes, valorNetoPago, estadoPago, codigoAdministracion, 
					codigoCliente, codigoLocal);
End$$
Delimiter ;
-- drop procedure sp_ListarCuentasPorCobrar;
Delimiter $$
	Create procedure sp_ListarCuentasPorCobrar()
	Begin
		select 
			CC.codigoCuentaPorCobrar,
			CC.numeroFactura, 
            CC.anio, 
            CC.mes, 
            CC.valorNetoPago, 
            CC.estadoPago, 
            CC.codigoAdministracion, 
            CC.codigoCliente, 
            CC.codigoLocal
		from CuentasPorCobrar CC;
	End$$
Delimiter ;
-- drop procedure sp_ListarCuentasPorCobrar:
Delimiter $$
	create procedure sp_EditarCuentaPorCobrar(in codigoCuentaPorCobrar2 int, in numeroFactura2 varchar(45),in anio2 varchar(4),in mes2 varchar(2),
		in valorNetoPago2 double,in estadoPago2 varchar(45))    
	Begin
		update CuentasPorCobrar
			set
				numeroFactura=numeroFactura2, 
                anio=anio2, 
                mes=mes2, 
                valorNetoPago=valorNetoPago2, 
                estadoPago=estadoPago2 
                where
					codigoCuentaPorCobrar=codigoCuentaPorCobrar2;
	End$$
Delimiter;
Delimiter $$
	Create procedure sp_BuscarCuentaPorCobrar(in codigoCuentaPorCobrar2 int)
    Begin
		select 
			CC.numeroFactura, 
            CC.anio, 
            CC.mes, 
            CC.valorNetoPago, 
            CC.estadoPago, 
            CC.codigoAdministracion, 
            CC.codigoCliente, 
            CC.codigoLocal
		from CuentasPorCobrar CC
        where codigoCuentaPorCobrar2=codigoCuentaPorCobrar;
    End$$
Delimiter ;
Delimiter $$
	create procedure sp_EliminarCuentaPorCobrar(in codigoCuentaPorCobrar2 int)
    Begin
		delete from CuentasPorCobrar where codigoCuentaPorCobrar=codigoCuentaPorCobrar2;
	End$$
Delimiter ;
-- ******LLAMANDO PROCEDIMIENTOS ALMACENADOS
-- //DEPARTAMENTOS//
call sp_AgregarDepartamento("Contabilidad");
call sp_AgregarDepartamento("Marketing");
call sp_ListarDepartamentos();
call sp_EditarDepartamento(2,"Limpieza");
call sp_BuscarDepartamento(1);
call sp_EliminarDepartamento(2);
-- //CARGOS//
call sp_AgregarCargo("Jefe");
call sp_AgregarCargo("SubJefe");
call sp_ListarCargos();
call sp_EditarCargo(1,"Gerente");
call sp_BuscarCargo(1);
call sp_EliminarCargo(2);
-- //HORARIOS//
call sp_AgregarHorario("10:00", "10:00", true, false, true, true, false);
call sp_AgregarHorario("7:00", "8:00", true, true, true, true, false);
call sp_ListarHorarios();
call sp_EditarHorario(1, "8:00", "7:00", true, false, true, true, false);
call sp_BuscarHorario(2);
call sp_EliminarHorario(2);
-- //TIPOCLIENTE//
call sp_AgregarTipoCliente("Puntual");
call sp_AgregarTipoCliente("Impuntual");
call sp_ListarTipoClientes();
call sp_EditarTipoCliente(1,"Importante");
call sp_BuscarTipoCliente(2);
call sp_EliminarTipoCliente(2);
-- //LOCALES//
call sp_AgregarLocal(false,5000,500);
call sp_AgregarLocal(false,4500,550);
call sp_ListarLocales();
call sp_EditarLocal(1,false,7000,600);
call sp_BuscarLocal(1);
call sp_EliminarLocal(2);
-- //ADMINISTRACION//
call sp_AgregarAdministracion("Guatemala zona 7 Landivar","12345678");
call sp_AgregarAdministracion("Guatemala  zona 19 la Florida","12345678");
call sp_ListarAdministracion();
call sp_EditarAdministracion(1, "Guatemala Zona 7 Landivar", "87654321");
call sp_BuscarAdministracion(1);
call sp_EliminarAdministracion(2);
-- //EMPLEADOS//
call sp_AgregarEmpleado("Mynor","Lopez","mlopez2020202@gmail.com","12345678",'2020-5-5',5000.00,1,1,1,1);
call sp_AgregarEmpleado("Pedro","Armas","PedroArmas@gmail.com","87654321",'2020-5-6',6000.00,1,1,1,1);
call sp_ListarEmpleados();
call sp_EditarEmpleado(2,"Jorge","Perez","JorgePerez@gmail.com","54321540",'2020-5-12',5500.00);
call sp_BuscarEmpleado(1);
call sp_EliminarEmpleado(2);
-- //PROVEEDORES//
call sp_AgregarProveedor("21341234-1", "Pan", "12345678", "Zona 1 1ra calle ", 4583.42, 2456.10,1);
call sp_AgregarProveedor("54341234-1", "Luz", "67543223", "Zona 1 2da calle", 8283.42, 4456.10,1);
call sp_ListarProveedores();
call sp_EditarProveedor(1, "65245332-5", "Limpieza", "65139841", "La florida Guatemala", 9976.12, 4985.14);
call sp_BuscarProveedor(1);
call sp_EliminarProveedor(2);
-- //CUENTASPORPAGAR//
call sp_AgregarCuentaPorPagar("78951224",'2020-10-4', "Pagada", 5000,1,1);
call sp_AgregarCuentaPorPagar("45562157",'2020-9-10', "Atrasada", 7000,1,1);
call sp_ListarCuentasPorPagar();
call sp_EditarCuentaPorPagar(1,"94641234",'2020-5-1', "Al dia", 9000);
call sp_BuscarCuentaPorPagar(2);
call sp_EliminarCuentaPorPagar(2);
-- //CLIENTES//
call sp_AgregarCliente("Carlos","López","12345678", "cuidad de Guatemala","carloslopez20@gmail.com", 1,1,1);
call sp_AgregarCliente("Josue","Luna","98765432", "Villa nueva","Josueluna17@gmail.com", 1,1,1);
call sp_ListarClientes();
call sp_EditarCliente(1,"Rodrigo","Lara","54587321", "Ciudad quetzal","RodriLara@gmail.com");
call sp_BuscarCliente(1);
call sp_EliminarCliente(2);
-- //CUENTASPORCOBRAR//
call sp_AgregarCuentaPorCobrar("45632189",2020,10,5000, "al dia", 1,1,1);
call sp_AgregarCuentaPorCobrar("85296347",2019,9,4000, "al dia", 1,1,1);
call sp_ListarCuentasPorCobrar();
call sp_EditarCuentaPorCobrar(1, "54826917",2021,5,8000, "pendiente");
call sp_BuscarCuentaPorCobrar(2);
call sp_EliminarCuentaPorCobrar(2);



/*call sp_AgregarProveedores(); -- MynorLópez 2020202 10 registros
call sp_AgregarProveedores('21341234-1', 'Pan', '12345678', 'Zona 1 1ra calle ', 4583.42, 2456.10,1);
call sp_AgregarProveedores('54341234-1', 'Luz', '67543223', 'Zona 1 2da calle', 8283.42, 4456.10,1);
call sp_AgregarProveedores('21341234-1', 'Agua', '56895425', 'Zona 2 3ra calle ', 523.42, 2056.10,1);
call sp_AgregarProveedores('54341234-1', 'Electricidad', '14235789', 'Zona 3 4ta calle', 7283.42, 3456.10,1);
call sp_AgregarProveedores('21341234-1', 'Seguridad', '52368741', 'Zona 4 1ra calle ', 6583.42, 4000.10,1);
call sp_AgregarProveedores('54341234-1', 'Ventas', '54963214', 'Zona 5 5da calle', 10183.42, 5006.10,1);
call sp_AgregarProveedores('21341234-1', 'Plasticos', '47896321', 'Zona 6 6ta calle ', 11000.42, 6456.10,1);
call sp_AgregarProveedores('54341234-1', 'Vidrios', '54258789', 'Zona 7 2da calle', 2583.42, 1056.10,1);
call sp_AgregarProveedores('21341234-1', 'Metales', '12312345', 'Zona 8 8va calle ', 5583.42, 256.10,1);
call sp_AgregarProveedores('54341234-1', 'Porductor', '67543223', 'Zona 9 2da avenida', 8883.42, 4666.10,1);
call sp_ListarProveedores();

						-- Mynor López 2020202

Delimiter $$
	Create Procedure sp_CalcularLiquido(in codProveedor int)
		Begin
			Select
            *, P.sueldoFavor-P.sueldoContra as Liquido
            from Proveedores P 
				where codProveedor = codigoProveedor;
        End$$
    
Delimiter ;

call sp_CalcularLiquido(1);
call sp_CalcularLiquido(3);
call sp_CalcularLiquido(4);
call sp_CalcularLiquido(5);
call sp_CalcularLiquido(6);
call sp_CalcularLiquido(7);
call sp_CalcularLiquido(8);
call sp_CalcularLiquido(9);
call sp_CalcularLiquido(10);
call sp_CalcularLiquido(11);



			-- Mynor López 2020202
Delimiter $$
	Create Procedure sp_LlamarProveedores()
		Begin
			Select
            *, P.sueldoFavor-P.sueldoContra as Liquido
            from Proveedores P;
        End$$
    
Delimiter  

call sp_LlamarProveedores();
*/

/*call sp_AgregarProveedores('52525252-1', 'Seguridad', '12345678', 'Guatemala ', 5000.00, 3000.50,1);
call sp_AgregarProveedores('41414141-2', 'Contabilidad', '87654321', 'Escuintla', 8000.00, 4500.00,1);
call sp_AgregarProveedores('22222222-3', 'Limpieza', '12345678', 'Chiquimula ', 5000.42, 3000.50,1);
call sp_AgregarProveedores('30303030-4', 'Asesoria', '14141414', 'Esquipulas', 8000.00, 4500.00,1);
call sp_AgregarProveedores('65656565-5', 'Accesorios', '25252525', 'Peten ', 5000.00, 4000.10,1);
call sp_AgregarProveedores('86868868-6', 'Liquidos', '60606060', 'Jalapa', 8000.00, 4500.00,1);
call sp_AgregarProveedores('97979797-7', 'Lacteos', '55555555', 'Ciudad Quetzal', 5000.00, 3000.50,1);
call sp_AgregarProveedores('28282828-8', 'Pan', '47474747', 'La florida', 8000.00, 4500.00,1);
call sp_AgregarProveedores('19191919-9', 'Carne', '12345678', 'Zona 1 ', 5000.00, 3000.50,1);
call sp_AgregarProveedores('55252525-1', 'Papel', '12332112', 'Zona 2', 8000.00, 4500.00,1);
call sp_ListarProveedores();
*/
		-- Mynor López 2020202
/*call sp_AgregarLocales(false,5000,500);
call sp_AgregarLocales(true,4500,450);
call sp_AgregarLocales(true,3000,200);
call sp_AgregarLocales(false,2500,200);
call sp_AgregarLocales(true,6000,600);
call sp_AgregarLocales(false,8000,450);
call sp_AgregarLocales(true,5500,300);
call sp_AgregarLocales(false,7000,450);
call sp_AgregarLocales(false,10000,800);
call sp_AgregarLocales(true,4000,350);
call sp_ListarLocal();

			-- Mynor López 2020202
Delimiter $$
	Create Procedure sp_CalcularMesesPendientes(in codLocal int)
		Begin
			Select
            L.valorLocal*L.mesesPendientes as MesesPendientes
            from Locales L 
				where codLocal = codigoLocal;
        End$$
    
Delimiter ;
call sp_CalcularMesesPendientes(1);

			-- Mynor López 2020202
Delimiter $$
	Create Procedure sp_CalcularLiquidoLocal(in codLocal int)
		Begin
			Select
            L.saldoFavor-L.saldoContra as Liquido
            from Locales L 
				where codLocal = codigoLocal;
        End$$
    
Delimiter ;

call sp_CalcularLiquido(1);

call sp_ListarLocal();


				-- Mynor López 2020202
Delimiter $$
	Create Procedure sp_CalcularTotalLocal(in codLocal int)
		Begin
			Select
            (L.valorLocal*L.mesesPendientes)-(L.saldoFavor-L.saldoContra)  as Total
            from Locales L 
				where codLocal = codigoLocal;
        End$$
    
Delimiter ;
call sp_CalcularTotalLocal(1);


Delimiter $$
	Create Procedure sp_CalcularTotalLocales()
		Begin
			Select
            (L.valorLocal*L.mesesPendientes)-(L.saldoFavor-L.saldoContra)  as TotalLocales
            from Locales L;
        End$$
    
Delimiter ;
-- Mynor López 2020202
call sp_CalcularTotalLocales();*/

-- call sp_ListarLocal();

				-- Mynor López 2020202
/*Delimiter $$
	Create Procedure sp_CalcularDisponibilidad()
		Begin
			Select  count(L.disponibilidad) as Disponibles from Locales L where disponibilidad = true;
            Select  count(L.disponibilidad) as NoDisponibles from Locales L where disponibilidad = false;
			Select  count(L.disponibilidad) as LocalesEnExistencia from Locales L;
        End$$
Delimiter ;
call sp_CalcularDisponibilidad(); */

		-- Mynor López 2020202
call sp_AgregarLocal(false,5000,500);
call sp_AgregarLocal(true,4500,450);
call sp_AgregarLocal(true,3000,200);
call sp_AgregarLocal(false,2500,200);
call sp_AgregarLocal(true,6000,600);
call sp_AgregarLocal(false,8000,450);
call sp_AgregarLocal(true,5500,300);
call sp_AgregarLocal(false,7000,450);
call sp_AgregarLocal(false,10000,800);
call sp_AgregarLocal(true,4000,350);
call sp_listarLocales();

ALTER USER 'root'@'localhost' IDENTIFIED WITH mysql_native_password By 'admin';


Create table Usuarios(
	codigoUsuario int not null auto_increment,
    nombreUsuario VARCHAR(100) not null,
    apellidoUsuario VARCHAR(100) not null,
    usuarioLogin VARCHAR(50) not null,
    contrasena VARCHAR(50) not null,
    primary key PK_codigoUsuario(codigoUsuario)
);
-- drop table Usuarios;

delimiter $$
	Create procedure sp_AgregarUsuario(in nombreUsuario varchar(100), in apellidoUsuario varchar(100),
		in usuarioLogin varchar(50), in contrasena varchar(50))
    Begin
		insert into Usuarios(nombreUsuario,apellidoUsuario,usuarioLogin,contrasena) 
			values (nombreUsuario,apellidoUsuario,usuarioLogin,contrasena);
	End$$
delimiter ;
-- drop procedure sp_AgregarUsuario;
-- drop procedure sp_ListarUsuarios;

Delimiter $$
	Create procedure sp_ListarUsuarios()
	Begin
		select 
			U.codigoUsuario, 
            U.nombreUsuario,
            U.apellidoUsuario,
            U.usuarioLogin,
            U.contrasena
		from Usuarios U;
	End$$
Delimiter ;
call sp_AgregarUsuario('Mynor','López','mlopez','2020202'); 
call sp_ListarUsuarios();
                    
Create table Login(
	usuarioMaster VARCHAR(50) not null,
    passwordLogin VARCHAR(50) not null,
    primary key PK_usuarioMaster(usuarioMaster)
);



select * from departamentos;
select * from cargos;
select * from empleados;

select * from Departamentos D inner join Empleados E
	on D.codigoDepartamento = E.codigoDepartamento where E.codigoEmpleado = 1;
    
select * from Empleados E inner join Cargos C inner join Departamentos D inner join Horarios H 
	on E.codigoCargo = C.codigoCargo and E.codigoDepartamento =  D.codigoDepartamento 
		and E.codigoHorario = H.codigoHorario where E.codigoEmpleado = 1;
	
Select * from Clientes C inner join TipoCliente TC inner join Locales L inner join CuentasPorCobrar CC
	on C.codigoTipoCliente = TC.codigoTipoCliente and C.codigoLocal = L.codigoLocal 
		and C.codigoCliente = CC.codigoCliente where C.codigoCliente = 1;


