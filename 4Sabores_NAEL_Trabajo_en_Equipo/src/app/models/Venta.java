package app.models;

import java.io.Serializable;
import java.util.Date;

public class Venta implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private String cliente;
    private String items; // serialized items description
    private double total;
    private Date fecha;

    public Venta() {}

    public Venta(int id, String cliente, String items, double total, Date fecha) {
        this.id = id; this.cliente = cliente; this.items = items; this.total = total; this.fecha = fecha;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getCliente() { return cliente; }
    public void setCliente(String cliente) { this.cliente = cliente; }
    public String getItems() { return items; }
    public void setItems(String items) { this.items = items; }
    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }
    public Date getFecha() { return fecha; }
    public void setFecha(Date fecha) { this.fecha = fecha; }
}
