package org.mynorlopez.controller;

import eu.schudt.javafx.controls.calendar.DatePicker;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javax.swing.JOptionPane;
import org.mynorlopez.bean.Administracion;
import org.mynorlopez.bean.Cargo;
import org.mynorlopez.bean.Departamento;
import org.mynorlopez.bean.Empleado;
import org.mynorlopez.bean.Horario;
import org.mynorlopez.db.Conexion;
import org.mynorlopez.report.GenerarReporte;
import org.mynorlopez.system.Principal;

public class EmpleadoController implements Initializable{
    private Principal escenarioPrincipal;
    private enum operaciones {NUEVO, GUARDAR, ELIMINAR, ACTUALIZAR, CANCELAR, NINGUNO};
    private operaciones tipoDeOperacion = operaciones.NINGUNO;
    private ObservableList<Empleado> listaEmpleado;
    private ObservableList<Departamento> listaDepartamento;
    private ObservableList<Cargo> listaCargo;
    private ObservableList<Horario> listaHorario;
    private ObservableList<Administracion> listaAdministracion;
    private DatePicker fechaContratacion;
    
    @FXML private Button btnNuevo;
    @FXML private Button btnEliminar;
    @FXML private Button btnEditar;
    @FXML private Button btnReporte;
    @FXML private TextField txtCodigoEmpleado;
    @FXML private TextField txtNombreEmpleado;
    @FXML private TextField txtApellidoEmpleado;
    @FXML private TextField txtCorreoElectronico;
    @FXML private TextField txtTelefonoEmpleado;
    @FXML private TextField txtFechaContratacion;
    @FXML private TextField txtSueldo;
    @FXML private ComboBox cmbCodigoDepartamento;
    @FXML private ComboBox cmbCodigoCargo;
    @FXML private ComboBox cmbCodigoHorario;
    @FXML private ComboBox cmbCodigoAdministracion;
    @FXML private TableView tblEmpleado;
    @FXML private TableColumn colCodigoEmpleado;
    @FXML private TableColumn colNombreEmpleado;
    @FXML private TableColumn colApellidoEmpleado;
    @FXML private TableColumn colCorreoElectronico;
    @FXML private TableColumn colFechaContratacion;
    @FXML private TableColumn colTelefonoEmpleado;
    @FXML private TableColumn colSueldo;
    @FXML private TableColumn colCodigoDepartamento;
    @FXML private TableColumn colCodigoCargo;
    @FXML private TableColumn colCodigoHorario;
    @FXML private TableColumn colCodigoAdministracion;
    @FXML private GridPane grpFechaContratacion;
    @FXML private ImageView imgNuevo;
    @FXML private ImageView imgEliminar;
    @FXML private ImageView imgEditar;
    @FXML private ImageView imgReporte;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fechaContratacion = new DatePicker(Locale.ENGLISH);
        fechaContratacion.setDateFormat(new SimpleDateFormat("YYYY-MM-DD"));
        fechaContratacion.getCalendarView().todayButtonTextProperty().set("Today");
        fechaContratacion.getCalendarView().setShowWeeks(false);
        grpFechaContratacion.add(fechaContratacion, 3, 1);
        fechaContratacion.getStylesheets().add("/org/mynorlopez/resource/DatePicker.css");
        cargarDatos();
    }
    
    public void cargarDatos(){
            tblEmpleado.setItems(getEmpleado());
            colCodigoEmpleado.setCellValueFactory(new PropertyValueFactory<Empleado,Integer>("codigoEmpleado"));
            colNombreEmpleado.setCellValueFactory(new PropertyValueFactory<Empleado,String>("nombreEmpleado"));
            colApellidoEmpleado.setCellValueFactory(new PropertyValueFactory<Empleado,String>("apellidoEmpleado"));
            colCorreoElectronico.setCellValueFactory(new PropertyValueFactory<Empleado,String>("correoElectronico"));
            colTelefonoEmpleado.setCellValueFactory(new PropertyValueFactory<Empleado,String>("telefonoEmpleado"));
            colFechaContratacion.setCellValueFactory(new PropertyValueFactory<Empleado,Date>("fechaContratacion"));
            colSueldo.setCellValueFactory(new PropertyValueFactory<Empleado,Double>("sueldo"));
            colCodigoDepartamento.setCellValueFactory(new PropertyValueFactory<Departamento,Integer>("codigoDepartamento"));
            colCodigoCargo.setCellValueFactory(new PropertyValueFactory<Cargo,Integer>("codigoCargo"));
            colCodigoHorario.setCellValueFactory(new PropertyValueFactory<Horario,Integer>("codigoHorario"));
            colCodigoAdministracion.setCellValueFactory(new PropertyValueFactory<Administracion,Integer>("codigoAdministracion"));
            cmbCodigoDepartamento.setItems(getDepartamento());
            cmbCodigoCargo.setItems(getCargo());
            cmbCodigoHorario.setItems(getHorario());
            cmbCodigoAdministracion.setItems(getAdministracion());

    }
    
    public void seleccionarElemento(){
        if(tblEmpleado.getSelectionModel().getSelectedItem()== null){
        limpiarControles();
    }else{
        txtCodigoEmpleado.setText(String.valueOf(((Empleado)tblEmpleado.getSelectionModel().getSelectedItem()).getCodigoEmpleado()));
        txtNombreEmpleado.setText(((Empleado)tblEmpleado.getSelectionModel().getSelectedItem()).getNombreEmpleado());
        txtApellidoEmpleado.setText(((Empleado)tblEmpleado.getSelectionModel().getSelectedItem()).getApellidoEmpleado());
        txtCorreoElectronico.setText(((Empleado)tblEmpleado.getSelectionModel().getSelectedItem()).getCorreoElectronico());
        txtTelefonoEmpleado.setText(((Empleado)tblEmpleado.getSelectionModel().getSelectedItem()).getTelefonoEmpleado());
        fechaContratacion.selectedDateProperty().set(((Empleado)tblEmpleado.getSelectionModel().getSelectedItem()).getFechaContratacion());
        txtSueldo.setText(String.valueOf(((Empleado)tblEmpleado.getSelectionModel().getSelectedItem()).getSueldo()));
        cmbCodigoDepartamento.getSelectionModel().select(buscarDepartamento(((Empleado)tblEmpleado.getSelectionModel().getSelectedItem()).getCodigoDepartamento()));
        cmbCodigoCargo.getSelectionModel().select(buscarCargo(((Empleado)tblEmpleado.getSelectionModel().getSelectedItem()).getCodigoCargo()));
        cmbCodigoHorario.getSelectionModel().select(buscarHorario(((Empleado)tblEmpleado.getSelectionModel().getSelectedItem()).getCodigoHorario()));
        cmbCodigoAdministracion.getSelectionModel().select(buscarAdministracion(((Empleado)tblEmpleado.getSelectionModel().getSelectedItem()).getCodigoAdministracion()));
        }
    }
    
    public ObservableList <Empleado> getEmpleado(){
        ArrayList<Empleado> lista = new ArrayList<Empleado>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarEmpleados()}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new Empleado(resultado.getInt("codigoEmpleado"),
                                            resultado.getString("nombreEmpleado"),
                                            resultado.getString("apellidoEmpleado"),
                                            resultado.getString("correoElectronico"),
                                            resultado.getString("telefonoEmpleado"),    
                                            resultado.getDate("fechaContratacion"),
                                            resultado.getDouble("sueldo"),
                                            resultado.getInt("codigoDepartamento"),
                                            resultado.getInt("codigoCargo"),
                                            resultado.getInt("codigoHorario"),
                                            resultado.getInt("codigoAdministracion")));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return listaEmpleado = FXCollections.observableArrayList(lista);
    } 
    
    public ObservableList<Departamento> getDepartamento(){
        ArrayList<Departamento> lista = new ArrayList<Departamento>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarDepartamentos()}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new Departamento(resultado.getInt("codigoDepartamento"),
                                                resultado.getString("nombreDepartamento")));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return listaDepartamento = FXCollections.observableArrayList(lista);
    }
    
     public Departamento buscarDepartamento(int codigoDepartamento){
        Departamento resultado = null;
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarDepartamento(?)}");
            procedimiento.setInt(1, codigoDepartamento);
            ResultSet registro = procedimiento.executeQuery();
            while(registro.next()){
                resultado = new Departamento(registro.getInt("codigoDepartamento"),
                                                registro.getString("nombreDepartamento"));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return resultado;
    }
     
    public ObservableList<Cargo> getCargo(){
        ArrayList<Cargo> lista = new ArrayList<Cargo>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarCargos()}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new Cargo(resultado.getInt("codigoCargo"),
                                                resultado.getString("nombreCargo")));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return listaCargo = FXCollections.observableArrayList(lista);
    }
    
    public Cargo buscarCargo(int codigoCargo){
        Cargo resultado = null;
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarCargo(?)}");
            procedimiento.setInt(1, codigoCargo);
            ResultSet registro = procedimiento.executeQuery();
            while(registro.next()){
                resultado = new Cargo(registro.getInt("codigoCargo"),
                                                registro.getString("nombreCargo"));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return resultado;
    }
    
    public ObservableList<Horario> getHorario(){
        ArrayList<Horario> lista = new ArrayList<Horario>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarHorarios()}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new Horario(resultado.getInt("codigoHorario"),
                                            resultado.getString("horaEntrada"),
                                            resultado.getString("horaSalida"),
                                            resultado.getBoolean("lunes"),
                                            resultado.getBoolean("martes"),
                                            resultado.getBoolean("miercoles"),
                                            resultado.getBoolean("jueves"),
                                            resultado.getBoolean("viernes")));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return listaHorario = FXCollections.observableArrayList(lista);
    }
    
    public Horario buscarHorario(int codigoHorario){
        Horario resultado = null;
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarHorario(?)}");
            procedimiento.setInt(1, codigoHorario);
            ResultSet registro = procedimiento.executeQuery();
            while(registro.next()){
                resultado = new Horario(registro.getInt("codigoHorario"),
                                                registro.getString("horaEntrada"),
                                                registro.getString("horaSalida"),
                                                registro.getBoolean("lunes"),
                                                registro.getBoolean("martes"),
                                                registro.getBoolean("miercoles"),
                                                registro.getBoolean("jueves"),
                                                registro.getBoolean("viernes"));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return resultado;
    }
    
     
    public ObservableList<Administracion> getAdministracion(){
        ArrayList<Administracion> lista = new ArrayList<Administracion>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarAdministracion()}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new Administracion(resultado.getInt("codigoAdministracion"),
                                                resultado.getString("direccion"),
                                                resultado.getString("telefono")));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return listaAdministracion = FXCollections.observableArrayList(lista);
    }
    
     public Administracion buscarAdministracion(int codigoAdministracion){
        Administracion resultado = null;
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarAdministracion(?)}");
            procedimiento.setInt(1, codigoAdministracion);
            ResultSet registro = procedimiento.executeQuery();
            while(registro.next()){
                resultado = new Administracion(registro.getInt("codigoAdministracion"),
                                                registro.getString("direccion"),
                                                registro.getString("telefono"));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return resultado;
    } 
     
    public void nuevo(){
        switch(tipoDeOperacion){
            case NINGUNO:
                limpiarControles();
                activarControles();
                btnNuevo.setText("Guardar");
                btnEliminar.setText("Cancelar");
                btnEditar.setDisable(true);
                btnReporte.setDisable(true);
                imgNuevo.setImage(new Image("/org/mynorlopez/images/guardar.png"));
                imgEliminar.setImage(new Image("/org/mynorlopez/images/cancelar.png"));
                tipoDeOperacion = operaciones.GUARDAR;
                break;
                
            case GUARDAR:
                guardar();
                desactivarControles();
                limpiarControles();
                btnNuevo.setText("Nuevo");
                btnEliminar.setText("Eliminar");
                btnEditar.setDisable(false);
                btnReporte.setDisable(false);
                imgNuevo.setImage(new Image("/org/mynorlopez/images/nuevo.png"));
                imgEliminar.setImage(new Image("/org/mynorlopez/images/eliminar.png"));
                tipoDeOperacion = operaciones.NINGUNO;
                cargarDatos();
                break;
        }
    }
    
    public void guardar(){
        if (txtNombreEmpleado.getText().isEmpty() & txtApellidoEmpleado.getText().isEmpty()  & txtCorreoElectronico.getText().isEmpty()
                 & txtTelefonoEmpleado.getText().isEmpty()  & txtSueldo.getText().isEmpty())
            JOptionPane.showMessageDialog(null, "Debe ingresar datos");
        else{ 
        Empleado registro = new Empleado();
        registro.setNombreEmpleado(txtNombreEmpleado.getText());
        registro.setApellidoEmpleado(txtApellidoEmpleado.getText());
        registro.setCorreoElectronico(txtCorreoElectronico.getText());
        registro.setTelefonoEmpleado(txtTelefonoEmpleado.getText());
        registro.setFechaContratacion(fechaContratacion.getSelectedDate());
        registro.setSueldo(Double.parseDouble(txtSueldo.getText()));
        registro.setCodigoDepartamento(((Departamento)cmbCodigoDepartamento.getSelectionModel().getSelectedItem()).getCodigoDepartamento());
        registro.setCodigoCargo(((Cargo)cmbCodigoCargo.getSelectionModel().getSelectedItem()).getCodigoCargo());
        registro.setCodigoHorario(((Horario)cmbCodigoHorario.getSelectionModel().getSelectedItem()).getCodigoHorario());
        registro.setCodigoAdministracion(((Administracion)cmbCodigoAdministracion.getSelectionModel().getSelectedItem()).getCodigoAdministracion());       
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarEmpleado(?,?,?,?,?,?,?,?,?,?)}");
            procedimiento.setString(1, registro.getNombreEmpleado());
            procedimiento.setString(2, registro.getApellidoEmpleado());
            procedimiento.setString(3, registro.getCorreoElectronico());
            procedimiento.setString(4, registro.getTelefonoEmpleado());
            procedimiento.setDate(5, new java.sql.Date(registro.getFechaContratacion().getTime()));
            procedimiento.setDouble(6, registro.getSueldo());
            procedimiento.setInt(7, registro.getCodigoDepartamento());
            procedimiento.setInt(8, registro.getCodigoCargo());
            procedimiento.setInt(9, registro.getCodigoHorario());
            procedimiento.setInt(10, registro.getCodigoAdministracion());
            procedimiento.execute();
            listaEmpleado.add(registro);
        }catch(Exception e){
            e.printStackTrace();
           }
        }
    }
    
    public void eliminar(){
        switch(tipoDeOperacion){
            case GUARDAR:
                desactivarControles();
                limpiarControles();
                btnNuevo.setText("Nuevo");
                btnEliminar.setText("Eliminar");
                imgNuevo.setImage(new Image("/org/mynorlopez/images/nuevo.png"));
                imgEliminar.setImage(new Image("/org/mynorlopez/images/eliminar.png"));
                btnEditar.setDisable(false);
                btnReporte.setDisable(false);
                tipoDeOperacion = operaciones.NINGUNO;
                break;
            default:
                if(tblEmpleado.getSelectionModel().getSelectedItem()!= null){
                    int respuesta = JOptionPane.showConfirmDialog(null, "¿Está seguro de eliminar el registro?","Eliminar Empleado",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (respuesta == JOptionPane.YES_OPTION){
                        try{
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarEmpleado(?)}");
                            procedimiento.setInt(1, ((Empleado)tblEmpleado.getSelectionModel().getSelectedItem()).getCodigoEmpleado());
                            procedimiento.execute();
                            listaEmpleado.remove(tblEmpleado.getSelectionModel().getSelectedIndex());
                            limpiarControles();
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "Debe seleccionar un elemento");
                }
        }
        
    }
    
    public void editar(){
        switch(tipoDeOperacion){
            case NINGUNO:
                if(tblEmpleado.getSelectionModel().getSelectedItem() != null){
                    btnEditar.setText("Actualizar");
                    btnReporte.setText("Cancelar");
                    btnNuevo.setDisable(true);
                    btnEliminar.setDisable(true);
                    imgEditar.setImage(new Image("/org/mynorlopez/images/actualizar.png"));
                    imgReporte.setImage(new Image("/org/mynorlopez/images/cancelar.png"));
                    activarControles();
                    desactivarComboBox();
                    tipoDeOperacion = operaciones.ACTUALIZAR;
                }else{
                    JOptionPane.showMessageDialog(null, "Debe seleccionar un elemento");
                }
            break;
            case ACTUALIZAR:
                actualizar();
                btnNuevo.setDisable(false);
                btnEliminar.setDisable(false);
                btnEditar.setText("Editar");
                btnReporte.setText("Reporte");
                imgEditar.setImage(new Image("/org/mynorlopez/images/editar.png"));
                imgReporte.setImage(new Image("/org/mynorlopez/images/reporte.png"));
                limpiarControles();
                desactivarControles();
                cargarDatos();
                tipoDeOperacion = operaciones.NINGUNO;
                break;
        }
    }
    
    public void actualizar(){
        if (txtNombreEmpleado.getText().isEmpty() & txtApellidoEmpleado.getText().isEmpty()  & txtCorreoElectronico.getText().isEmpty()
                 & txtTelefonoEmpleado.getText().isEmpty()  & txtSueldo.getText().isEmpty())
            JOptionPane.showMessageDialog(null, "Debe ingresar datos");
        else{ 
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EditarEmpleado(?,?,?,?,?,?,?)}");
            Empleado registro = (Empleado)tblEmpleado.getSelectionModel().getSelectedItem();
            registro.setNombreEmpleado(txtNombreEmpleado.getText());
            registro.setApellidoEmpleado(txtApellidoEmpleado.getText());
            registro.setCorreoElectronico(txtCorreoElectronico.getText());
            registro.setTelefonoEmpleado(txtTelefonoEmpleado.getText());
            registro.setFechaContratacion(fechaContratacion.getSelectedDate());
            registro.setSueldo(Double.parseDouble(txtSueldo.getText()));
            registro.setCodigoDepartamento(((Departamento)cmbCodigoDepartamento.getSelectionModel().getSelectedItem()).getCodigoDepartamento());
            registro.setCodigoCargo(((Cargo)cmbCodigoCargo.getSelectionModel().getSelectedItem()).getCodigoCargo());
            registro.setCodigoHorario(((Horario)cmbCodigoHorario.getSelectionModel().getSelectedItem()).getCodigoHorario());
            registro.setCodigoAdministracion(((Administracion)cmbCodigoAdministracion.getSelectionModel().getSelectedItem()).getCodigoAdministracion());
            procedimiento.setInt(1, registro.getCodigoEmpleado()); 
            procedimiento.setString(2, registro.getNombreEmpleado());
            procedimiento.setString(3, registro.getApellidoEmpleado());
            procedimiento.setString(4, registro.getCorreoElectronico());
            procedimiento.setString(5, registro.getTelefonoEmpleado());
            procedimiento.setDate(6, new java.sql.Date(registro.getFechaContratacion().getTime()));
            procedimiento.setDouble(7, registro.getSueldo());      
            procedimiento.execute();
        }catch(Exception e){
            e.printStackTrace();
            }
        }
    }
    
    public void reporte(){
        switch(tipoDeOperacion){
            case ACTUALIZAR:
                desactivarControles();
                limpiarControles();
                btnEditar.setText("Editar");
                btnReporte.setText("Reporte");
                imgEditar.setImage(new Image("/org/mynorlopez/images/editar.png"));
                imgReporte.setImage(new Image("/org/mynorlopez/images/reporte.png"));
                btnNuevo.setDisable(false);
                btnEliminar.setDisable(false);
                tipoDeOperacion = operaciones.NINGUNO;
                break;
            case NINGUNO:
                imprimirReporte();
                break;
        }
        
    }
    
    public void imprimirReporte(){
        Map parametros = new HashMap();
        int codEmpleado = ((Empleado)tblEmpleado.getSelectionModel().getSelectedItem()).getCodigoEmpleado();
        parametros.put("codEmpleado", codEmpleado);
        GenerarReporte.mostrarReporte("ReporteEmpleados.jasper", "Reporte Empleado", parametros);
    }
    
    public void desactivarControles(){
        txtCodigoEmpleado.setEditable(false);
        txtNombreEmpleado.setEditable(false);
        txtApellidoEmpleado.setEditable(false);
        txtCorreoElectronico.setEditable(false);
        txtTelefonoEmpleado.setEditable(false);
        txtSueldo.setEditable(false);
        cmbCodigoDepartamento.setDisable(true);
        cmbCodigoCargo.setDisable(true);
        cmbCodigoHorario.setDisable(true);
        cmbCodigoAdministracion.setDisable(true);
        fechaContratacion.setDisable(true);
        
    }
    public void activarControles(){
        txtCodigoEmpleado.setEditable(false);
        txtNombreEmpleado.setEditable(true);
        txtApellidoEmpleado.setEditable(true);
        txtCorreoElectronico.setEditable(true);
        txtTelefonoEmpleado.setEditable(true);
        txtSueldo.setEditable(true);
        cmbCodigoDepartamento.setDisable(false);
        cmbCodigoCargo.setDisable(false);
        cmbCodigoHorario.setDisable(false);
        cmbCodigoAdministracion.setDisable(false);
        fechaContratacion.setDisable(false);
    }
    public void limpiarControles(){
        txtCodigoEmpleado.clear();
        txtNombreEmpleado.clear();
        txtApellidoEmpleado.clear();
        txtCorreoElectronico.clear();
        txtTelefonoEmpleado.clear();
        txtSueldo.clear();
        cmbCodigoDepartamento.setValue(null);
        cmbCodigoCargo.setValue(null);
        cmbCodigoHorario.setValue(null);
        cmbCodigoAdministracion.setValue(null);
        fechaContratacion.setPromptText("");

    }
    
    public void desactivarComboBox(){
        cmbCodigoAdministracion.setDisable(true);
        cmbCodigoDepartamento.setDisable(true);
        cmbCodigoCargo.setDisable(true);
        cmbCodigoHorario.setDisable(true);
    }
    
    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    public void menuPrincipal(){
        escenarioPrincipal.ventanaCargo();
    }
    
}
