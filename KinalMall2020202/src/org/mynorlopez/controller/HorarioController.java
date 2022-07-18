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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.swing.JOptionPane;
import org.mynorlopez.bean.Horario;
import org.mynorlopez.db.Conexion;
import org.mynorlopez.system.Principal;

public class HorarioController implements Initializable{
    private enum operaciones {NUEVO, GUARDAR, ELIMINAR, ACTUALIZAR, CANCELAR, NINGUNO};
    private operaciones tipoDeOperacion = operaciones.NINGUNO;
    private Principal escenarioPrincipal;
    private ObservableList<Horario> listaHorario;
    
    
    @FXML private Button btnNuevo;
    @FXML private Button btnEliminar;
    @FXML private Button btnEditar;
    @FXML private Button btnReporte;
    @FXML private TextField txtCodigoHorario;
    @FXML private TextField txtHoraEntrada;
    @FXML private TextField txtHoraSalida;
    @FXML private TextField txtLunes;
    @FXML private TextField txtMartes;
    @FXML private TextField txtMiercoles;
    @FXML private TextField txtJueves;
    @FXML private TextField txtViernes;
    @FXML private TableView tblHorario;
    @FXML private TableColumn colCodigoHorario;
    @FXML private TableColumn colHoraEntrada;
    @FXML private TableColumn colHoraSalida;
    @FXML private TableColumn colLunes;
    @FXML private TableColumn colMartes;
    @FXML private TableColumn colMiercoles;
    @FXML private TableColumn colJueves;
    @FXML private TableColumn colViernes;
    @FXML private ImageView imgNuevo;
    @FXML private ImageView imgEliminar;
    @FXML private ImageView imgEditar;
    @FXML private ImageView imgReporte;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
    }
 
    public void cargarDatos(){
        tblHorario.setItems(getHorario());
        colCodigoHorario.setCellValueFactory(new PropertyValueFactory<Horario,Integer>("codigoHorario"));
        colHoraEntrada.setCellValueFactory(new PropertyValueFactory<Horario,String>("horaEntrada"));
        colHoraSalida.setCellValueFactory(new PropertyValueFactory<Horario,String>("horaSalida"));
        colLunes.setCellValueFactory(new PropertyValueFactory<Horario,Boolean>("lunes"));
        colMartes.setCellValueFactory(new PropertyValueFactory<Horario,Boolean>("martes"));
        colMiercoles.setCellValueFactory(new PropertyValueFactory<Horario,Boolean>("miercoles"));
        colJueves.setCellValueFactory(new PropertyValueFactory<Horario,Boolean>("jueves"));
        colViernes.setCellValueFactory(new PropertyValueFactory<Horario,Boolean>("viernes"));


    }
    
    public void seleccionarElemento(){
        if(tblHorario.getSelectionModel().getSelectedItem()== null){
        limpiarControles();
        }else{
        txtCodigoHorario.setText(String.valueOf(((Horario)tblHorario.getSelectionModel().getSelectedItem()).getCodigoHorario()));
        txtHoraEntrada.setText(((Horario)tblHorario.getSelectionModel().getSelectedItem()).getHoraEntrada());
        txtHoraSalida.setText(((Horario)tblHorario.getSelectionModel().getSelectedItem()).getHoraSalida());
        txtLunes.setText(String.valueOf(((Horario)tblHorario.getSelectionModel().getSelectedItem()).isLunes()));
        txtMartes.setText(String.valueOf(((Horario)tblHorario.getSelectionModel().getSelectedItem()).isMartes()));
        txtMiercoles.setText(String.valueOf(((Horario)tblHorario.getSelectionModel().getSelectedItem()).isMiercoles()));
        txtJueves.setText(String.valueOf(((Horario)tblHorario.getSelectionModel().getSelectedItem()).isJueves()));
        txtViernes.setText(String.valueOf(((Horario)tblHorario.getSelectionModel().getSelectedItem()).isViernes()));
        }
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
        if (txtHoraEntrada.getText().isEmpty() & txtHoraSalida.getText().isEmpty())
            JOptionPane.showMessageDialog(null, "Debe ingresar datos");
        else{ 
        Horario registro = new Horario();
        registro.setHoraEntrada(txtHoraEntrada.getText());
        registro.setHoraSalida(txtHoraSalida.getText());
        registro.setLunes(Boolean.parseBoolean(txtLunes.getText()));
        registro.setMartes(Boolean.parseBoolean(txtMartes.getText()));
        registro.setMiercoles(Boolean.parseBoolean(txtMiercoles.getText()));
        registro.setJueves(Boolean.parseBoolean(txtJueves.getText()));
        registro.setViernes(Boolean.parseBoolean(txtViernes.getText()));
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarHorario(?,?,?,?,?,?,?)}");
            procedimiento.setString(1, registro.getHoraEntrada());
            procedimiento.setString(2, registro.getHoraSalida());
            procedimiento.setBoolean(3, registro.isLunes());
            procedimiento.setBoolean(4, registro.isMartes());
            procedimiento.setBoolean(5, registro.isMiercoles());
            procedimiento.setBoolean(6, registro.isJueves());
            procedimiento.setBoolean(7, registro.isViernes());
            procedimiento.execute();
            listaHorario.add(registro);
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
                if(tblHorario.getSelectionModel().getSelectedItem()!= null){
                    int respuesta = JOptionPane.showConfirmDialog(null, "¿Está seguro de eliminar el registro?","Eliminar Horario",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (respuesta == JOptionPane.YES_OPTION){
                        try{
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarHorario(?)}");
                            procedimiento.setInt(1, ((Horario)tblHorario.getSelectionModel().getSelectedItem()).getCodigoHorario());
                            procedimiento.execute();
                            listaHorario.remove(tblHorario.getSelectionModel().getSelectedIndex());
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
                if(tblHorario.getSelectionModel().getSelectedItem() != null){
                    btnEditar.setText("Actualizar");
                    btnReporte.setText("Cancelar");
                    btnNuevo.setDisable(true);
                    btnEliminar.setDisable(true);
                    imgEditar.setImage(new Image("/org/mynorlopez/images/actualizar.png"));
                    imgReporte.setImage(new Image("/org/mynorlopez/images/cancelar.png"));
                    activarControles();
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
        if (txtHoraEntrada.getText().isEmpty() & txtHoraSalida.getText().isEmpty())
            JOptionPane.showMessageDialog(null, "Debe ingresar datos");
        else{ 
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EditarHorario(?,?,?,?,?,?,?,?)}");
            Horario registro = (Horario)tblHorario.getSelectionModel().getSelectedItem();
            registro.setHoraEntrada(txtHoraEntrada.getText());
            registro.setHoraSalida(txtHoraSalida.getText());
            registro.setLunes(Boolean.parseBoolean(txtLunes.getText()));
            registro.setMartes(Boolean.parseBoolean(txtMartes.getText()));
            registro.setMiercoles(Boolean.parseBoolean(txtMiercoles.getText()));
            registro.setJueves(Boolean.parseBoolean(txtJueves.getText()));
            registro.setViernes(Boolean.parseBoolean(txtViernes.getText()));
            procedimiento.setInt(1, registro.getCodigoHorario());
            procedimiento.setString(2, registro.getHoraEntrada());
            procedimiento.setString(3, registro.getHoraSalida());
            procedimiento.setBoolean(4, registro.isLunes());
            procedimiento.setBoolean(5, registro.isMartes());
            procedimiento.setBoolean(6, registro.isMiercoles());
            procedimiento.setBoolean(7, registro.isJueves());
            procedimiento.setBoolean(8, registro.isViernes());
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
        txtCodigoHorario.setEditable(false);
        txtHoraEntrada.setEditable(false);
        txtHoraSalida.setEditable(false);
        txtLunes.setEditable(false);
        txtMartes.setEditable(false);
        txtMiercoles.setEditable(false);
        txtJueves.setEditable(false);
        txtViernes.setEditable(false);
        
    }
    public void activarControles(){
        txtCodigoHorario.setEditable(false);
        txtHoraEntrada.setEditable(true);
        txtHoraSalida.setEditable(true);
        txtLunes.setEditable(true);
        txtMartes.setEditable(true);
        txtMiercoles.setEditable(true);
        txtJueves.setEditable(true);
        txtViernes.setEditable(true);
    }
    public void limpiarControles(){
        txtCodigoHorario.clear();
        txtHoraEntrada.clear();
        txtHoraSalida.clear();
        txtLunes.clear();
        txtMartes.clear();
        txtMiercoles.clear();
        txtJueves.clear();
        txtViernes.clear();
    }

    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    public void menuPrincipal(){
        escenarioPrincipal.menuPrincipal();
    }
    
}
