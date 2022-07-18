package org.mynorlopez.controller;

import eu.schudt.javafx.controls.calendar.DatePicker;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.Locale;
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
import org.mynorlopez.bean.CuentaPorPagar;
import org.mynorlopez.bean.Proveedor;
import org.mynorlopez.db.Conexion;
import org.mynorlopez.system.Principal;

public class CuentaPorPagarController implements Initializable {
    private enum operaciones {NUEVO, GUARDAR, ELIMINAR, ACTUALIZAR, CANCELAR, NINGUNO};
    private operaciones tipoDeOperacion = operaciones.NINGUNO;
    private Principal escenarioPrincipal;
    private ObservableList<CuentaPorPagar> listaCuentaPorPagar;
    private ObservableList<Administracion> listaAdministracion;
    private ObservableList<Proveedor> listaProveedor;
    private DatePicker fechaLimite;
    @FXML private Button btnNuevo;
    @FXML private Button btnEliminar;
    @FXML private Button btnEditar;
    @FXML private Button btnReporte;
    @FXML private TextField txtCodigoCuentaPorPagar;
    @FXML private TextField txtNumeroFactura;
    @FXML private TextField txtFechaLimitePago;
    @FXML private TextField txtEstadoDePago;
    @FXML private TextField txtValorNetoPago;
    @FXML private ComboBox cmbCodigoAdministracion;
    @FXML private ComboBox cmbCodigoProveedor;
    @FXML private TableView tblCuentasPorPagar;
    @FXML private TableColumn colCodigoCuentaPorPagar;
    @FXML private TableColumn colNumeroFactura;
    @FXML private TableColumn colFechaLimitePago;
    @FXML private TableColumn colEstadoDePago;
    @FXML private TableColumn colValorNetoPago;
    @FXML private TableColumn colCodigoAdministracion;
    @FXML private TableColumn colCodigoProveedor;
    @FXML private GridPane grpFechaLimite;
    @FXML private ImageView imgNuevo;
    @FXML private ImageView imgEliminar;
    @FXML private ImageView imgEditar;
    @FXML private ImageView imgReporte;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fechaLimite = new DatePicker(Locale.ENGLISH);
        fechaLimite.setDateFormat(new SimpleDateFormat("YYYY-MM-DD"));
        fechaLimite.getCalendarView().todayButtonTextProperty().set("Today");
        fechaLimite.getCalendarView().setShowWeeks(false);
        grpFechaLimite.add(fechaLimite, 5, 0);
        fechaLimite.getStylesheets().add("/org/mynorlopez/resource/DatePicker.css");
        cargarDatos();
    }
    
    public void cargarDatos(){
            tblCuentasPorPagar.setItems(getCuentaPorPagar());
            colCodigoCuentaPorPagar.setCellValueFactory(new PropertyValueFactory<CuentaPorPagar,Integer>("codigoCuentaPorPagar"));
            colNumeroFactura.setCellValueFactory(new PropertyValueFactory<CuentaPorPagar,String>("numeroFactura"));
            colFechaLimitePago.setCellValueFactory(new PropertyValueFactory<CuentaPorPagar,Date>("fechaLimitePago"));
            colEstadoDePago.setCellValueFactory(new PropertyValueFactory<CuentaPorPagar,String>("estadoPago"));
            colValorNetoPago.setCellValueFactory(new PropertyValueFactory<CuentaPorPagar,Double>("valorNetoPago"));
            colCodigoAdministracion.setCellValueFactory(new PropertyValueFactory<Administracion,Integer>("codigoAdministracion"));
            colCodigoProveedor.setCellValueFactory(new PropertyValueFactory<Proveedor,Integer>("codigoProveedor"));
            cmbCodigoAdministracion.setItems(getAdministracion());
            cmbCodigoProveedor.setItems(getProveedor());

    }
    
    public void seleccionarElemento(){
        if(tblCuentasPorPagar.getSelectionModel().getSelectedItem()== null){
        limpiarControles();
    }else{
        txtCodigoCuentaPorPagar.setText(String.valueOf(((CuentaPorPagar)tblCuentasPorPagar.getSelectionModel().getSelectedItem()).getCodigoCuentaPorPagar()));
        txtNumeroFactura.setText(((CuentaPorPagar)tblCuentasPorPagar.getSelectionModel().getSelectedItem()).getNumeroFactura());
        fechaLimite.selectedDateProperty().set(((CuentaPorPagar)tblCuentasPorPagar.getSelectionModel().getSelectedItem()).getFechaLimitePago());
//        txtFechaLimitePago.setText(((CuentaPorPagar)tblCuentasPorPagar.getSelectionModel().getSelectedItem()).getServicioPrestado());
        txtEstadoDePago.setText(((CuentaPorPagar)tblCuentasPorPagar.getSelectionModel().getSelectedItem()).getEstadoPago());
        txtValorNetoPago.setText(String.valueOf(((CuentaPorPagar)tblCuentasPorPagar.getSelectionModel().getSelectedItem()).getValorNetoPago()));
        cmbCodigoAdministracion.getSelectionModel().select(buscarAdministracion(((CuentaPorPagar)tblCuentasPorPagar.getSelectionModel().getSelectedItem()).getCodigoAdministracion()));
        cmbCodigoProveedor.getSelectionModel().select(buscarProveedor(((CuentaPorPagar)tblCuentasPorPagar.getSelectionModel().getSelectedItem()).getCodigoProveedor()));
        }
    }
    
    public ObservableList <CuentaPorPagar> getCuentaPorPagar(){
        ArrayList<CuentaPorPagar> lista = new ArrayList<CuentaPorPagar>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarCuentasPorPagar()}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new CuentaPorPagar(resultado.getInt("codigoCuentaPorPagar"),
                                            resultado.getString("numeroFactura"),
                                            resultado.getDate("fechaLimitePago"),
                                            resultado.getString("estadoPago"),
                                            resultado.getDouble("valorNetoPago"),
                                            resultado.getInt("codigoAdministracion"),
                                            resultado.getInt("codigoProveedor")));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return listaCuentaPorPagar = FXCollections.observableArrayList(lista);
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
     
     
    public ObservableList <Proveedor> getProveedor(){
        ArrayList<Proveedor> lista = new ArrayList<Proveedor>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarProveedores()}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new Proveedor(resultado.getInt("codigoProveedor"),
                                            resultado.getString("NITProveedor"),
                                            resultado.getString("servicioPrestado"),
                                            resultado.getString("telefonoProveedor"),
                                            resultado.getString("direccionProveedor"),
                                            resultado.getDouble("sueldoFavor"),
                                            resultado.getDouble("sueldoContra"),
                                            resultado.getInt("codigoAdministracion")));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        
    
        
        return listaProveedor = FXCollections.observableArrayList(lista);
    }   
    
    public Proveedor buscarProveedor(int codigoProveedor){
        Proveedor resultado = null;
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarProveedor(?)}");
            procedimiento.setInt(1, codigoProveedor);
            ResultSet registro = procedimiento.executeQuery();
            while(registro.next()){
                resultado = new Proveedor(registro.getInt("codigoProveedor"),
                                            registro.getString("NITProveedor"),
                                            registro.getString("servicioPrestado"),
                                            registro.getString("telefonoProveedor"),
                                            registro.getString("direccionProveedor"),
                                            registro.getDouble("sueldoFavor"),
                                            registro.getDouble("sueldoContra"),
                                            registro.getInt("codigoAdministracion"));
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
                imgNuevo.setImage(new Image("/org/mynorlopez/images/nuevo2.png"));
                imgEliminar.setImage(new Image("/org/mynorlopez/images/eliminar2.png"));
                tipoDeOperacion = operaciones.NINGUNO;
                cargarDatos();
                break;
        }
    }
    
    public void guardar(){
        if (txtNumeroFactura.getText().isEmpty() //& fechaLimite.getSelectedDate(). 
                & txtEstadoDePago.getText().isEmpty() 
                   & txtValorNetoPago.getText().isEmpty() // cmbCodigoAdministracion.getItems().isEmpty() & cmbCodigoProveedor.getItems().isEmpty()
                )
            JOptionPane.showMessageDialog(null, "Debe ingresar datos");
        else{ 
        CuentaPorPagar registro = new CuentaPorPagar();
        registro.setNumeroFactura(txtNumeroFactura.getText());
        registro.setFechaLimitePago(fechaLimite.getSelectedDate());
        registro.setEstadoPago(txtEstadoDePago.getText());
        registro.setValorNetoPago(Double.parseDouble(txtValorNetoPago.getText()));
        registro.setCodigoAdministracion(((Administracion)cmbCodigoAdministracion.getSelectionModel().getSelectedItem()).getCodigoAdministracion());
        registro.setCodigoProveedor(((Proveedor)cmbCodigoProveedor.getSelectionModel().getSelectedItem()).getCodigoProveedor());
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarCuentaPorPagar(?,?,?,?,?,?)}");
            procedimiento.setString(1, registro.getNumeroFactura());
            procedimiento.setDate(2, new java.sql.Date(registro.getFechaLimitePago().getTime()));
            procedimiento.setString(3, registro.getEstadoPago());
            procedimiento.setDouble(4, registro.getValorNetoPago());
            procedimiento.setInt(5, registro.getCodigoAdministracion());
            procedimiento.setInt(6, registro.getCodigoProveedor());
            procedimiento.execute();
            listaCuentaPorPagar.add(registro);
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
                imgNuevo.setImage(new Image("/org/mynorlopez/images/nuevo2.png"));
                imgEliminar.setImage(new Image("/org/mynorlopez/images/eliminar2.png"));
                btnEditar.setDisable(false);
                btnReporte.setDisable(false);
                tipoDeOperacion = operaciones.NINGUNO;
                break;
            default:
                if(tblCuentasPorPagar.getSelectionModel().getSelectedItem()!= null){
                    int respuesta = JOptionPane.showConfirmDialog(null, "¿Está seguro de eliminar el registro?","Eliminar CuentaPorPagar",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (respuesta == JOptionPane.YES_OPTION){
                        try{
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarCuentaPorPagar(?)}");
                            procedimiento.setInt(1, ((CuentaPorPagar)tblCuentasPorPagar.getSelectionModel().getSelectedItem()).getCodigoCuentaPorPagar());
                            procedimiento.execute();
                            listaCuentaPorPagar.remove(tblCuentasPorPagar.getSelectionModel().getSelectedIndex());
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
                if(tblCuentasPorPagar.getSelectionModel().getSelectedItem() != null){
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
                imgEditar.setImage(new Image("/org/mynorlopez/images/editar2.png"));
                imgReporte.setImage(new Image("/org/mynorlopez/images/reporte2.png"));
                limpiarControles();
                desactivarControles();
                cargarDatos();
                tipoDeOperacion = operaciones.NINGUNO;
                break;
        }
    }
    
    public void actualizar(){
        if (txtNumeroFactura.getText().isEmpty() //& fechaLimite.getSelectedDate(). 
                & txtEstadoDePago.getText().isEmpty() 
                   & txtValorNetoPago.getText().isEmpty() // cmbCodigoAdministracion.getItems().isEmpty() & cmbCodigoProveedor.getItems().isEmpty()
                )
            JOptionPane.showMessageDialog(null, "Debe ingresar datos");
        else{ 
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EditarCuentaPorPagar(?,?,?,?,?)}");
            CuentaPorPagar registro = (CuentaPorPagar)tblCuentasPorPagar.getSelectionModel().getSelectedItem();
            registro.setNumeroFactura(txtNumeroFactura.getText());
            registro.setFechaLimitePago(fechaLimite.getSelectedDate());
            registro.setEstadoPago(txtEstadoDePago.getText());
            registro.setValorNetoPago(Double.parseDouble(txtValorNetoPago.getText()));
            registro.setCodigoAdministracion(((Administracion)cmbCodigoAdministracion.getSelectionModel().getSelectedItem()).getCodigoAdministracion());
            registro.setCodigoProveedor(((Proveedor)cmbCodigoProveedor.getSelectionModel().getSelectedItem()).getCodigoProveedor());
            procedimiento.setInt(1, registro.getCodigoCuentaPorPagar()); 
            procedimiento.setString(2, registro.getNumeroFactura());
            procedimiento.setDate(3, new java.sql.Date(registro.getFechaLimitePago().getTime()));
            procedimiento.setString(4, registro.getEstadoPago());
            procedimiento.setDouble(5, registro.getValorNetoPago());      
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
        }
        
    }


     public void desactivarControles(){
        txtCodigoCuentaPorPagar.setEditable(false);
        txtNumeroFactura.setEditable(false);
//        txtFechaLimitePago.setEditable(false);
        txtEstadoDePago.setEditable(false);
        txtValorNetoPago.setEditable(false);
        cmbCodigoAdministracion.setDisable(true);
        cmbCodigoProveedor.setDisable(true);
        fechaLimite.setDisable(true);
        
    }
    public void activarControles(){
        txtCodigoCuentaPorPagar.setEditable(false);
        txtNumeroFactura.setEditable(true);
//        txtFechaLimitePago.setEditable(true);
        txtEstadoDePago.setEditable(true);
        txtValorNetoPago.setEditable(true);
        cmbCodigoAdministracion.setDisable(false);
        cmbCodigoProveedor.setDisable(false);
        fechaLimite.setDisable(false);
    }
    public void limpiarControles(){
        txtCodigoCuentaPorPagar.clear();
        txtNumeroFactura.clear();
//        txtFechaLimitePago.clear();
        txtEstadoDePago.clear();
        txtValorNetoPago.clear();
        cmbCodigoAdministracion.setValue(null);
        cmbCodigoProveedor.setValue(null);
        fechaLimite.setPromptText("");

    }
    
    public void desactivarComboBox(){
        cmbCodigoAdministracion.setDisable(true);
        cmbCodigoProveedor.setDisable(true);
    }

    
    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    public void menuPrincipal(){
        escenarioPrincipal.ventanaProveedor();
    }
    
}
