package src.com.techlab.pedidos;

import java.util.ArrayList;

public class Pedido {
    private static int contador = 1;

    private int id;
    private ArrayList<LineaPedido> lineas;

    public Pedido() {
        this.id = contador++;
        this.lineas = new ArrayList<>();
    }

    public void agregarLinea(LineaPedido linea) {
        lineas.add(linea);
    }

    public double calcularTotal() {
        double total = 0;
        for (LineaPedido l : lineas) {
            total += l.calcularSubtotal();
        }
        return total;
    }

    public void mostrarPedido() {
        System.out.println("Pedido #" + id);
        for (LineaPedido l : lineas) {
            System.out.println(l.getProducto().getNombre() +
                    " x" + l.getCantidad());
        }
        System.out.println("Total: $" + calcularTotal());
    }
}