package app.models;

import java.io.Serializable;
import java.util.Objects;

public class Producto implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String nombre;
    private String categoria;
    private double precio;
    private int stock;
    private String imagen; // nueva propiedad

    public Producto() {}

    // Constructor completo con imagen
    public Producto(int id, String nombre, String categoria, double precio, int stock, String imagen) {
        this.id = id;
        this.nombre = nombre;
        this.categoria = categoria;
        this.precio = precio;
        this.stock = stock;
        this.imagen = imagen;
    }

    // Constructor "rápido" útil para crear el menú estático
    public Producto(String nombre, double precio, String categoria, String imagen) {
        this(0, nombre, categoria, precio, 0, imagen);
    }

    // Constructor sin imagen (compatibilidad)
    public Producto(int id, String nombre, String categoria, double precio, int stock) {
        this(id, nombre, categoria, precio, stock, null);
    }

    // getters & setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }

    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }

    public String getImagen() { return imagen; }
    public void setImagen(String imagen) { this.imagen = imagen; }

    @Override
    public String toString() {
        return "Producto{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", categoria='" + categoria + '\'' +
                ", precio=" + precio +
                ", imagen='" + imagen + '\'' +
                ", stock=" + stock +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Producto)) return false;
        Producto producto = (Producto) o;
        if (this.id > 0 && producto.id > 0) return this.id == producto.id;
        return this.nombre != null && this.nombre.equalsIgnoreCase(producto.nombre);
    }

    @Override
    public int hashCode() {
        if (id > 0) return Objects.hash(id);
        return nombre == null ? 0 : nombre.toLowerCase().hashCode();
    }
}
