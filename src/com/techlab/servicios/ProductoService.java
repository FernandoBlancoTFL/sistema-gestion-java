package src.com.techlab.servicios;

import src.com.techlab.productos.Producto;
import java.util.ArrayList;

public class ProductoService {
    private ArrayList<Producto> productos = new ArrayList<>();

    public ProductoService() {
        cargarProductosIniciales();
    }

    public void agregarProducto(String nombre, double precio, int stock) {
        productos.add(new Producto(nombre, precio, stock));
    }

    public void listarProductos() {
        for (Producto p : productos) {
            System.out.println(p);
        }
    }

    public Producto buscarPorId(int id) {
        for (Producto p : productos) {
            if (p.getId() == id) return p;
        }
        return null;
    }

    public void eliminarProducto(int id) {
        productos.removeIf(p -> p.getId() == id);
    }

    public ArrayList<Producto> getProductos() {
        return productos;
    }

    private void cargarProductosIniciales() {
        productos.add(new Producto("Café Premium", 1500.50, 10));
        productos.add(new Producto("Té Verde", 800.00, 20));
        productos.add(new Producto("Galletitas", 500.75, 15));
        productos.add(new Producto("Leche", 600.00, 12));
    }
}