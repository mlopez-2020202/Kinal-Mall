/*
Programador: Mynor Roberto López Díaz 
Carné: 2020202
Código Técnico: IN5AV
 */
package org.mynorlopez.system;

import java.io.IOException;
import java.io.InputStream;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.mynorlopez.controller.AdministracionController;
import org.mynorlopez.controller.CargoController;
import org.mynorlopez.controller.ClienteController;
import org.mynorlopez.controller.CuentaPorCobrarController;
import org.mynorlopez.controller.CuentaPorPagarController;
import org.mynorlopez.controller.DepartamentoController;
import org.mynorlopez.controller.EmpleadoController;
import org.mynorlopez.controller.HorarioController;
import org.mynorlopez.controller.LocalesController;
import org.mynorlopez.controller.LoginController;
import org.mynorlopez.controller.MenuPrincipalController;
import org.mynorlopez.controller.ProgramadorController;
import org.mynorlopez.controller.ProveedorController;
import org.mynorlopez.controller.TipoClienteController;
import org.mynorlopez.controller.UsuarioController;

/**
 *
 * @author mynor
 */
public class Principal extends Application {
    private final String PAQUETE_VISTA = "/org/mynorlopez/view/";
    private Stage escenarioPrincipal;
    private Scene escena;
    @Override
    public void start(Stage escenarioPrincipal) throws IOException {
        this.escenarioPrincipal = escenarioPrincipal;
        this.escenarioPrincipal.setTitle("Kinal Mall 2020202");
//        Parent root = FXMLLoader.load(getClass().getResource("/org/mynorlopez/view/ProgramadorView.fxml"));
//        Scene escena = new Scene (root);
//        escenarioPrincipal.setScene(escena);
        ventanaLogin();
        escenarioPrincipal.show();
    }
    
    public void menuPrincipal(){
        try{
            MenuPrincipalController menuPrincipal =  (MenuPrincipalController) cambiarEscena("MenuPrincipalView.fxml",369,400);
            menuPrincipal.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void ventanaProgramador(){
        try{
            ProgramadorController programador =  (ProgramadorController) cambiarEscena("ProgramadorView.fxml",542,400);
            programador.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void ventanaAdministracion(){
        try{
            AdministracionController admin =  (AdministracionController) cambiarEscena("AdministracionView.fxml",808,434);
            admin.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void ventanaTipoCliente(){
        try{
            TipoClienteController tipoCliente =  (TipoClienteController) cambiarEscena("TipoClienteView.fxml",748,417);
            tipoCliente.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void ventanaLocales(){
        try{
            LocalesController locales =  (LocalesController) cambiarEscena("LocalesView.fxml",841,448);
            locales.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void ventanaCliente(){
        try{
            ClienteController cliente =  (ClienteController) cambiarEscena("ClienteView.fxml",1200,423);
            cliente.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void ventanaDepartamento(){
        try{
            DepartamentoController departamento =  (DepartamentoController) cambiarEscena("DepartamentosView.fxml",734,416);
            departamento.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void ventanaProveedor(){
        try{
            ProveedorController proveedor =  (ProveedorController) cambiarEscena("ProveedorView.fxml",1200,423);
            proveedor.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void ventanaCuentasPorPagar(){
        try{
            CuentaPorPagarController cuentasPagar =  (CuentaPorPagarController) cambiarEscena("CuentaPorPagarView.fxml",1200,425);
            cuentasPagar.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void ventanaHorario(){
        try{
            HorarioController horario =  (HorarioController) cambiarEscena("HorarioView.fxml",1000,425);
            horario.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void ventanaUsuarios(){
        try{
            UsuarioController usuario =  (UsuarioController) cambiarEscena("UsuarioView.fxml",600,425);
            usuario.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void ventanaLogin(){
        try{
            LoginController login =  (LoginController) cambiarEscena("LoginView.fxml",600,425);
            login.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void ventanaCargo(){
        try{
            CargoController cargo =  (CargoController) cambiarEscena("CargosView.fxml",748,417);
            cargo.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void ventanaEmpleado(){
        try{
            EmpleadoController empleado =  (EmpleadoController) cambiarEscena("EmpleadoView.fxml",1284,484);
            empleado.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void ventanaCuentaPorCobrar(){
        try{
            CuentaPorCobrarController cuentaCobrar =  (CuentaPorCobrarController) cambiarEscena("CuentasPorCobrarView.fxml",1257,423);
            cuentaCobrar.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    
    public Initializable cambiarEscena (String fxml,int ancho,int alto) throws IOException{
        Initializable resultado = null;
        FXMLLoader cargadorFXML = new FXMLLoader();
        InputStream archivo = Principal.class.getResourceAsStream(PAQUETE_VISTA+fxml);
        cargadorFXML.setBuilderFactory(new JavaFXBuilderFactory());
        cargadorFXML.setLocation(Principal.class.getResource(PAQUETE_VISTA+fxml));
        escena = new Scene((AnchorPane)cargadorFXML.load(archivo),ancho,alto);
        escenarioPrincipal.setScene(escena);
        escenarioPrincipal.sizeToScene();
        resultado = (Initializable)cargadorFXML.getController();
        return resultado;
    }
    
}
