package org.mynorlopez.bean;

import java.util.Date;

public class CuentaPorPagar {
    //Atributos 
    private int codigoCuentaPorPagar;
    private String numeroFactura;
    private Date fechaLimitePago;
    private String estadoPago;
    private Double valorNetoPago;
    private int codigoAdministracion;
    private int codigoProveedor;
    //Constructores
    public CuentaPorPagar(){
        
    }

    public CuentaPorPagar(int codigoCuentaPorPagar, String numeroFactura, Date fechaLimitePago, String estadoPago, Double valorNetoPago, int codigoAdministracion, int codigoProveedor) {
        this.codigoCuentaPorPagar = codigoCuentaPorPagar;
        this.numeroFactura = numeroFactura;
        this.fechaLimitePago = fechaLimitePago;
        this.estadoPago = estadoPago;
        this.valorNetoPago = valorNetoPago;
        this.codigoAdministracion = codigoAdministracion;
        this.codigoProveedor = codigoProveedor;
    }
    
   
    //Metodos de acceso

    public int getCodigoCuentaPorPagar() {
        return codigoCuentaPorPagar;
    }

    public void setCodigoCuentaPorPagar(int codigoCuentaPorPagar) {
        this.codigoCuentaPorPagar = codigoCuentaPorPagar;
    }

    public String getNumeroFactura() {
        return numeroFactura;
    }

    public void setNumeroFactura(String numeroFactura) {
        this.numeroFactura = numeroFactura;
    }

    public Date getFechaLimitePago() {
        return fechaLimitePago;
    }

    public void setFechaLimitePago(Date fechaLimitePago) {
        this.fechaLimitePago = fechaLimitePago;
    }

    public String getEstadoPago() {
        return estadoPago;
    }

    public void setEstadoPago(String estadoPago) {
        this.estadoPago = estadoPago;
    }

    public Double getValorNetoPago() {
        return valorNetoPago;
    }

    public void setValorNetoPago(Double valorNetoPago) {
        this.valorNetoPago = valorNetoPago;
    }

    public int getCodigoAdministracion() {
        return codigoAdministracion;
    }

    public void setCodigoAdministracion(int codigoAdministracion) {
        this.codigoAdministracion = codigoAdministracion;
    }

    public int getCodigoProveedor() {
        return codigoProveedor;
    }

    public void setCodigoProveedor(int codigoProveedor) {
        this.codigoProveedor = codigoProveedor;
    }
   
}
