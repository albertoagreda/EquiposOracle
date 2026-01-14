import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        ConexionOracle cx = new ConexionOracle();
        Scanner sc = new Scanner(System.in);

        try {
            cx.conectar();
            cx.crearTipoJugador();

            while (true) {
                System.out.println("\n--- MENU ---");
                System.out.println("1. Recorrer directorio y crear tablas");
                System.out.println("2. Rellenar equipo desde XML (elige equipo)");
                System.out.println("3. Mostrar equipo");
                System.out.println("4. Eliminar todas las tablas");
                System.out.println("5. Salir");
                System.out.print("Opcion: ");

                int op = Integer.parseInt(sc.nextLine());

                switch (op) {
                    case 1 -> {
                        System.out.print("Ruta carpeta XML: ");
                        String carpeta = sc.nextLine();
                        cx.crearTablasDesdeDirectorio(carpeta);
                    }
                    case 2 -> {
                        System.out.print("Ruta carpeta XML: ");
                        String carpeta = sc.nextLine();
                        System.out.print("Equipo (ej Barcelona): ");
                        String equipo = sc.nextLine();
                        cx.cargarEquipoDesdeCarpeta(carpeta, equipo);
                    }
                    case 3 -> {
                        System.out.print("Equipo: ");
                        String equipo = sc.nextLine();
                        cx.mostrarEquipo(equipo);
                    }
                    case 4 -> cx.eliminarTodasLasTablas();
                    case 5 -> {
                        cx.desconectar();
                        System.out.println("Bye 👋");
                        return;
                    }
                    default -> System.out.println("Opcion invalida");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
