package src.com.techlab.main;

import src.com.techlab.productos.Producto;
import src.com.techlab.servicios.*;
import src.com.techlab.pedidos.*;
import src.com.techlab.excepciones.ProductoNoEncontradoException;
import src.com.techlab.excepciones.StockInsuficienteException;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        ProductoService productoService = new ProductoService();
        PedidoService pedidoService = new PedidoService();

        int opcion;

        do {
            System.out.println("\n--- SISTEMA ---");
            System.out.println("1) Agregar producto");
            System.out.println("2) Listar productos");
            System.out.println("3) Buscar / Actualizar producto");
            System.out.println("4) Eliminar producto");
            System.out.println("5) Crear pedido");
            System.out.println("6) Listar pedidos");
            System.out.println("7) Salir");

            opcion = sc.nextInt();
            if (opcion < 1 || opcion > 7) {
                System.out.println("Opción inválida");
            }
            try {
                switch (opcion) {
                    
                    case 1:
                        sc.nextLine();
                        System.out.print("Nombre: ");
                        String nombre = sc.nextLine();

                        System.out.print("Precio: ");
                        double precio = sc.nextDouble();

                        System.out.print("Stock: ");
                        int stock = sc.nextInt();

                        productoService.agregarProducto(nombre, precio, stock);
                        break;

                    case 2:
                        productoService.listarProductos();
                        break;

                    case 3:
                        System.out.print("Ingrese ID del producto: ");
                        int idBuscar = sc.nextInt();

                        Producto prod = productoService.buscarPorId(idBuscar);

                        if (prod == null) {
                            throw new ProductoNoEncontradoException("No existe el producto con ID " + idBuscar);
                        }

                        // Mostrar producto
                        System.out.println("Producto encontrado:");
                        System.out.println(prod);

                        // Preguntar si quiere modificar
                        System.out.print("¿Desea modificarlo? (s/n): ");
                        String opcionModificar = sc.next();

                        if (opcionModificar.equalsIgnoreCase("s")) {

                            sc.nextLine(); // limpiar buffer

                            System.out.print("Nuevo nombre (enter para no cambiar): ");
                            String nuevoNombre = sc.nextLine();

                            System.out.print("Nuevo precio (-1 para no cambiar): ");
                            double nuevoPrecio = sc.nextDouble();

                            System.out.print("Nuevo stock (-1 para no cambiar): ");
                            int nuevoStock = sc.nextInt();

                            try {
                                if (!nuevoNombre.trim().isEmpty()) {
                                    prod.setNombre(nuevoNombre);
                                }
                                if (nuevoPrecio != -1) {
                                    prod.setPrecio(nuevoPrecio);
                                }
                                if (nuevoStock != -1) {
                                    prod.setStock(nuevoStock);
                                }
                                System.out.println("Producto actualizado correctamente");

                            } catch (IllegalArgumentException e) {
                                System.out.println(e.getMessage());
                            }
                        }
                        break;

                    case 4:
                        System.out.print("ID a eliminar: ");
                        int id = sc.nextInt();
                        productoService.eliminarProducto(id);
                        break;

                    case 5:
                        ArrayList<LineaPedido> lineas = new ArrayList<>();

                        System.out.print("Ingrese la cantidad de productos a comprar: ");
                        int cant = sc.nextInt();

                        for (int i = 0; i < cant; i++) {
                            System.out.printf("ID producto %s: ", i+1);
                            int idProd = sc.nextInt();

                            Producto p = productoService.buscarPorId(idProd);

                            if (p == null) {
                                System.out.println("No existe");
                                i--;
                                continue;
                            }

                            System.out.print("Cantidad: ");
                            int cantidad = sc.nextInt();

                            lineas.add(new LineaPedido(p, cantidad));
                        }

                        Pedido pedido = pedidoService.crearPedido(lineas);
                        pedido.mostrarPedido();
                        break;

                    case 6:
                        pedidoService.listarPedidos();
                        break;

                }
            } catch (ProductoNoEncontradoException e) {
                System.out.println(e.getMessage());
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            } catch (StockInsuficienteException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                System.out.println("Error en datos ingresados");
                sc.nextLine();
            }

        } while (opcion != 7);

        System.out.println("Fin del programa");
    }
}