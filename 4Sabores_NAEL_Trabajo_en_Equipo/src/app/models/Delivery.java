package app.models;

import java.io.Serializable;

public class Delivery implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private int ventaId;
    private String direccion;
    private String estado; // e.g., Pendiente, Enviado, Entregado

    public Delivery() {}

    public Delivery(int id, int ventaId, String direccion, String estado) {
        this.id = id; this.ventaId = ventaId; this.direccion = direccion; this.estado = estado;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getVentaId() { return ventaId; }
    public void setVentaId(int ventaId) { this.ventaId = ventaId; }
    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
