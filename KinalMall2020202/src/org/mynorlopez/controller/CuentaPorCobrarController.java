package org.mynorlopez.controller;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
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
import javax.swing.JOptionPane;
import org.mynorlopez.bean.Administracion;
import org.mynorlopez.bean.Cliente;
import org.mynorlopez.bean.CuentaPorCobrar;
import org.mynorlopez.bean.Locales;
import org.mynorlopez.db.Conexion;
import org.mynorlopez.system.Principal;

public class CuentaPorCobrarController implements Initializable{
    private Principal escenarioPrincipal;
    private enum operaciones {NUEVO, GUARDAR, ELIMINAR, ACTUALIZAR, CANCELAR, NINGUNO};
    private operaciones tipoDeOperacion = operaciones.NINGUNO;
    private ObservableList<CuentaPorCobrar> listaCuentaPorCobrar;
    private ObservableList<Administracion> listaAdministracion;
    private ObservableList<Cliente> listaCliente;
    private ObservableList<Locales> listaLocales;
    
    @FXML private Button btnNuevo;
    @FXML private Button btnEliminar;
    @FXML private Button btnEditar;
    @FXML private Button btnReporte;
    @FXML private TextField txtCodigoCuentaPorCobrar;
    @FXML private TextField txtNumeroFactura;
    @FXML private TextField txtAnio;
    @FXML private TextField txtMes;
    @FXML private TextField txtValorNetoPago;
    @FXML private TextField txtEstadoPago;
    @FXML private ComboBox cmbCodigoLocal;
    @FXML private ComboBox cmbCodigoAdministracion;
    @FXML private ComboBox cmbCodigoCliente;
    @FXML private TableView tblCuentaPorCobrar;
    @FXML private TableColumn colCodigoCuentaPorCobrar;
    @FXML private TableColumn colNumeroFactura;
    @FXML private TableColumn colAnio;
    @FXML private TableColumn colMes;
    @FXML private TableColumn colValorNetoPago;
    @FXML private TableColumn colEstadoPago;
    @FXML private TableColumn colCodigoLocal;
    @FXML private TableColumn colCodigoAdministracion;
    @FXML private TableColumn colCodigoCliente;
    @FXML private ImageView imgNuevo;
    @FXML private ImageView imgEliminar;
    @FXML private ImageView imgEditar;
    @FXML private ImageView imgReporte;
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
    }
    
    public void cargarDatos(){
        tblCuentaPorCobrar.setItems(getCuentaPorCobrar());
        colCodigoCuentaPorCobrar.setCellValueFactory(new PropertyValueFactory<Cliente,Integer>("codigoCuentaPorCobrar"));
        colNumeroFactura.setCellValueFactory(new PropertyValueFactory<Cliente,String>("numeroFactura"));
        colAnio.setCellValueFactory(new PropertyValueFactory<Cliente,String>("anio"));
        colMes.setCellValueFactory(new PropertyValueFactory<Cliente,String>("mes"));
        colValorNetoPago.setCellValueFactory(new PropertyValueFactory<Cliente,Double>("valorNetoPago"));
        colEstadoPago.setCellValueFactory(new PropertyValueFactory<Cliente,String>("estadoPago"));
        colCodigoAdministracion.setCellValueFactory(new PropertyValueFactory<Administracion,String>("codigoAdministracion"));
        colCodigoCliente.setCellValueFactory(new PropertyValueFactory<Cliente,String>("codigoCliente"));
        colCodigoLocal.setCellValueFactory(new PropertyValueFactory<Locales,String>("codigoLocal"));
        cmbCodigoAdministracion.setItems(getAdministracion());
        cmbCodigoCliente.setItems(getCliente());
        cmbCodigoLocal.setItems(getLocal());
    }
    
    public void seleccionarElemento(){
    if(tblCuentaPorCobrar.getSelectionModel().getSelectedItem()== null){
        limpiarControles();
    }else{
        txtCodigoCuentaPorCobrar.setText(String.valueOf(((CuentaPorCobrar)tblCuentaPorCobrar.getSelectionModel().getSelectedItem()).getCodigoCuentaPorCobrar()));
        txtNumeroFactura.setText(((CuentaPorCobrar)tblCuentaPorCobrar.getSelectionModel().getSelectedItem()).getNumeroFactura());
        txtAnio.setText(((CuentaPorCobrar)tblCuentaPorCobrar.getSelectionModel().getSelectedItem()).getAnio());
        txtMes.setText(((CuentaPorCobrar)tblCuentaPorCobrar.getSelectionModel().getSelectedItem()).getMes());
        txtValorNetoPago.setText(String.valueOf(((CuentaPorCobrar)tblCuentaPorCobrar.getSelectionModel().getSelectedItem()).getValorNetoPago()));
        txtEstadoPago.setText(((CuentaPorCobrar)tblCuentaPorCobrar.getSelectionModel().getSelectedItem()).getEstadoPago());
        cmbCodigoAdministracion.getSelectionModel().select(buscarAdministracion(((CuentaPorCobrar)tblCuentaPorCobrar.getSelectionModel().getSelectedItem()).getCodigoAdministracion()));
        cmbCodigoCliente.getSelectionModel().select(buscarCliente(((CuentaPorCobrar)tblCuentaPorCobrar.getSelectionModel().getSelectedItem()).getCodigoCliente()));
        cmbCodigoLocal.getSelectionModel().select(buscarLocal(((CuentaPorCobrar)tblCuentaPorCobrar.getSelectionModel().getSelectedItem()).getCodigoLocal()));
        }
    }
    
    public ObservableList<CuentaPorCobrar> getCuentaPorCobrar(){
        ArrayList<CuentaPorCobrar> lista = new ArrayList<CuentaPorCobrar>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarCuentasPorCobrar()}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new CuentaPorCobrar(resultado.getInt("codigoCuentaPorCobrar"),
                                            resultado.getString("numeroFactura"),
                                            resultado.getString("anio"),
                                            resultado.getString("mes"),
                                            resultado.getDouble("valorNetoPago"),
                                            resultado.getString("estadoPago"),
                                            resultado.getInt("codigoAdministracion"),
                                            resultado.getInt("codigoCliente"),
                                            resultado.getInt("codigoLocal")));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return listaCuentaPorCobrar = FXCollections.observableArrayList(lista);
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
    
    public ObservableList<Cliente> getCliente(){
        ArrayList<Cliente> lista = new ArrayList<Cliente>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarClientes()}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new Cliente(resultado.getInt("codigoCliente"),
                                            resultado.getString("nombreCliente"),
                                            resultado.getString("apellidoCliente"),
                                            resultado.getString("telefonoCliente"),
                                            resultado.getString("direccionCliente"),
                                            resultado.getString("email"),
                                            resultado.getInt("codigoLocal"),
                                            resultado.getInt("codigoAdministracion"),
                                            resultado.getInt("codigoTipoCliente")));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return listaCliente = FXCollections.observableArrayList(lista);
    }
    
    public Cliente buscarCliente(int codigoCliente){
        Cliente resultado = null;
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarCliente(?)}");
            procedimiento.setInt(1, codigoCliente);
            ResultSet registro = procedimiento.executeQuery();
            while(registro.next()){
                resultado = new Cliente(registro.getInt("codigoCliente"),
                                            registro.getString("nombreCliente"),
                                            registro.getString("apellidoCliente"),
                                            registro.getString("telefonoCliente"),
                                            registro.getString("direccionCliente"),
                                            registro.getString("email"),
                                            registro.getInt("codigoLocal"),
                                            registro.getInt("codigoAdministracion"),
                                            registro.getInt("codigoTipoCliente"));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return resultado;
    }
    
    public ObservableList<Locales> getLocal(){
        ArrayList<Locales> lista = new ArrayList<Locales>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarLocales()}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new Locales(resultado.getInt("codigoLocal"),
                                                resultado.getDouble("saldoFavor"),
                                                resultado.getDouble("saldoContra"),
                                                resultado.getInt("mesesPendientes"),
                                                resultado.getBoolean("disponibilidad"),
                                                resultado.getDouble("valorLocal"),
                                                resultado.getDouble("valorAdministracion")));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return listaLocales = FXCollections.observableArrayList(lista);
    }
    
    public Locales buscarLocal(int codigoLocal){
        Locales resultado = null;
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarLocal(?)}");
            procedimiento.setInt(1, codigoLocal);
            ResultSet registro = procedimiento.executeQuery();
            while(registro.next()){
                resultado = new Locales(registro.getInt("codigoLocal"),
                                            registro.getDouble("saldoFavor"),
                                            registro.getDouble("saldoContra"),
                                            registro.getInt("mesesPendientes"),
                                            registro.getBoolean("disponibilidad"),
                                            registro.getDouble("valorLocal"),
                                            registro.getDouble("valorAdministracion"));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return resultado;
    }
    
    public void nuevo(){
        switch(tipoDeOperacion){
            case NINGUNO:
                activarControles();
                limpiarControles();
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
        if (txtNumeroFactura.getText().isEmpty() & txtAnio.getText().isEmpty() & txtMes.getText().isEmpty() 
                   & txtValorNetoPago.getText().isEmpty() & txtEstadoPago.getText().isEmpty() //& cmbCodigoLocal.getItems().isEmpty()
                       //& cmbCodigoAdministracion.getItems().isEmpty() & cmbCodigoTipoCliente.getItems().isEmpty()
                )
            JOptionPane.showMessageDialog(null, "Debe ingresar datos");
        else{ 
        CuentaPorCobrar registro = new CuentaPorCobrar();
        registro.setNumeroFactura(txtNumeroFactura.getText());
        registro.setAnio(txtAnio.getText());
        registro.setMes(txtMes.getText());
        registro.setValorNetoPago(Double.parseDouble(txtValorNetoPago.getText()));
        registro.setEstadoPago(txtEstadoPago.getText());
        registro.setCodigoAdministracion(((Administracion)cmbCodigoAdministracion.getSelectionModel().getSelectedItem()).getCodigoAdministracion());
        registro.setCodigoCliente(((Cliente)cmbCodigoCliente.getSelectionModel().getSelectedItem()).getCodigoCliente());
        registro.setCodigoLocal(((Locales)cmbCodigoLocal.getSelectionModel().getSelectedItem()).getCodigoLocal());
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarCuentaPorCobrar(?,?,?,?,?,?,?,?)}");
            procedimiento.setString(1, registro.getNumeroFactura());
            procedimiento.setString(2, registro.getAnio());
            procedimiento.setString(3, registro.getMes());
            procedimiento.setDouble(4, registro.getValorNetoPago());
            procedimiento.setString(5, registro.getEstadoPago());
            procedimiento.setInt(6, registro.getCodigoAdministracion());
            procedimiento.setInt(7, registro.getCodigoCliente());
            procedimiento.setInt(8, registro.getCodigoLocal());
            procedimiento.execute();
            listaCuentaPorCobrar.add(registro);
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
                if(tblCuentaPorCobrar.getSelectionModel().getSelectedItem()!= null){
                    int respuesta = JOptionPane.showConfirmDialog(null, "¿Está seguro de eliminar el registro?","Eliminar CuentaPorCobrar",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (respuesta == JOptionPane.YES_OPTION){
                        try{
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarCuentaPorCobrar(?)}");
                            procedimiento.setInt(1, ((CuentaPorCobrar)tblCuentaPorCobrar.getSelectionModel().getSelectedItem()).getCodigoCuentaPorCobrar());
                            procedimiento.execute();
                            listaCuentaPorCobrar.remove(tblCuentaPorCobrar.getSelectionModel().getSelectedIndex());
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
                if(tblCuentaPorCobrar.getSelectionModel().getSelectedItem() != null){
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
        if (txtNumeroFactura.getText().isEmpty() & txtAnio.getText().isEmpty() & txtMes.getText().isEmpty() 
                   & txtValorNetoPago.getText().isEmpty() & txtEstadoPago.getText().isEmpty() //& cmbCodigoLocal.getItems().isEmpty()
                       //& cmbCodigoAdministracion.getItems().isEmpty() & cmbCodigoTipoCliente.getItems().isEmpty()
                )
            JOptionPane.showMessageDialog(null, "Debe ingresar datos");
        else{  
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EditarCuentaPorCobrar(?,?,?,?,?,?)}");
            CuentaPorCobrar registro = (CuentaPorCobrar)tblCuentaPorCobrar.getSelectionModel().getSelectedItem();
            registro.setNumeroFactura(txtNumeroFactura.getText());
            registro.setAnio(txtAnio.getText());
            registro.setMes(txtMes.getText());
            registro.setValorNetoPago(Double.parseDouble(txtValorNetoPago.getText()));
            registro.setEstadoPago(txtEstadoPago.getText());
            registro.setCodigoAdministracion(((Administracion)cmbCodigoAdministracion.getSelectionModel().getSelectedItem()).getCodigoAdministracion());
            registro.setCodigoCliente(((Cliente)cmbCodigoCliente.getSelectionModel().getSelectedItem()).getCodigoCliente());
            registro.setCodigoLocal(((Locales)cmbCodigoLocal.getSelectionModel().getSelectedItem()).getCodigoLocal());
            procedimiento.setInt(1, registro.getCodigoCuentaPorCobrar());
            procedimiento.setString(2, registro.getNumeroFactura());
            procedimiento.setString(3, registro.getAnio());
            procedimiento.setString(4, registro.getMes());
            procedimiento.setDouble(5, registro.getValorNetoPago());
            procedimiento.setString(6, registro.getEstadoPago());
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
                imgEditar.setImage(new Image("/org/mynorlopez/images/editar2.png"));
                imgReporte.setImage(new Image("/org/mynorlopez/images/reporte2.png"));
                btnNuevo.setDisable(false);
                btnEliminar.setDisable(false);
                tipoDeOperacion = operaciones.NINGUNO;
                break;
        }
        
    }
    
    public void desactivarControles(){
        txtCodigoCuentaPorCobrar.setEditable(false);
        txtNumeroFactura.setEditable(false);
        txtAnio.setEditable(false);
        txtMes.setEditable(false);
        txtValorNetoPago.setEditable(false);
        txtEstadoPago.setEditable(false);
        cmbCodigoLocal.setDisable(true);
        cmbCodigoAdministracion.setDisable(true);
        cmbCodigoCliente.setDisable(true);
        
    }
    public void activarControles(){
        txtCodigoCuentaPorCobrar.setEditable(false);
        txtNumeroFactura.setEditable(true);
        txtAnio.setEditable(true);
        txtMes.setEditable(true);
        txtValorNetoPago.setEditable(true);
        txtEstadoPago.setEditable(true);
        cmbCodigoLocal.setDisable(false);
        cmbCodigoAdministracion.setDisable(false);
        cmbCodigoCliente.setDisable(false);
    }
    public void limpiarControles(){
        txtCodigoCuentaPorCobrar.clear();
        txtNumeroFactura.clear();
        txtAnio.clear();
        txtMes.clear();
        txtValorNetoPago.clear();
        txtEstadoPago.clear();
        cmbCodigoLocal.getSelectionModel().clearSelection();
        cmbCodigoAdministracion.getSelectionModel().clearSelection();
        cmbCodigoCliente.getSelectionModel().clearSelection();
    }
    
    public void desactivarComboBox(){
        cmbCodigoLocal.setDisable(true);
        cmbCodigoAdministracion.setDisable(true);
        cmbCodigoCliente.setDisable(true);
    }
    
    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    public void menuPrincipal(){
        escenarioPrincipal.ventanaLocales();
    }
}
