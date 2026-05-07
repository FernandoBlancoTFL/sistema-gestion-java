package src.com.techlab.servicios;

import src.com.techlab.pedidos.*;
import src.com.techlab.productos.Producto;
import src.com.techlab.excepciones.StockInsuficienteException;

import java.util.ArrayList;

public class PedidoService {
    private ArrayList<Pedido> pedidos = new ArrayList<>();

    public Pedido crearPedido(ArrayList<LineaPedido> lineas)
            throws StockInsuficienteException {

        // validar stock
        for (LineaPedido l : lineas) {
            if (l.getCantidad() > l.getProducto().getStock()) {
                throw new StockInsuficienteException(
                        "Error: Stock insuficiente para " + l.getProducto().getNombre());
            }
        }

        // descontar stock
        for (LineaPedido l : lineas) {
            Producto p = l.getProducto();
            p.setStock(p.getStock() - l.getCantidad());
        }

        Pedido pedido = new Pedido();
        for (LineaPedido l : lineas) {
            pedido.agregarLinea(l);
        }

        pedidos.add(pedido);
        return pedido;
    }

    public void listarPedidos() {
        for (Pedido p : pedidos) {
            p.mostrarPedido();
            System.out.println("------------------");
        }
    }
}