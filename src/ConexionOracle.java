import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.io.File;

import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class ConexionOracle {

    protected Connection conn;

    // =========================
    // CONECTAR / DESCONECTAR
    // =========================
    public void conectar() throws SQLException {
        String url = "jdbc:oracle:thin:@localhost:1521:xe";
        String usuario = "system";
        String password = "admin"; //

        conn = DriverManager.getConnection(url, usuario, password);
        System.out.println("Conectado a Oracle correctamente");
    }

    public void desconectar() throws SQLException {
        if (conn != null && !conn.isClosed()) {
            conn.close();
            System.out.println("Desconectado de Oracle");
        }
    }

    // =========================
    // TYPE JUGADOR
    // =========================
    public void crearTipoJugador() {
        String sql =
                "CREATE TYPE JUGADOR_T AS OBJECT (" +
                        " nombre VARCHAR2(100)," +
                        " dorsal NUMBER," +
                        " demarcacion VARCHAR2(50)," +
                        " nacimiento VARCHAR2(20)" +
                        ")";

        try (Statement st = conn.createStatement()) {
            st.executeUpdate(sql);
            System.out.println("TYPE JUGADOR_T creado");
        } catch (SQLException e) {
            String msg = e.getMessage();
            if (msg != null && (msg.contains("ORA-00955") || msg.contains("ORA-02303"))) {
                System.out.println("TYPE JUGADOR_T ya existe. Continuamos.");
            } else {
                System.out.println("ERROR creando TYPE JUGADOR_T:");
                e.printStackTrace();
            }
        }
    }

    // =========================
    // CREAR TABLA EQUIPO
    // =========================
    public void crearTablaEquipo(String nombreArchivo) {
        String tabla = normalizarNombreTabla(nombreArchivo);
        String sql = "CREATE TABLE " + tabla + " OF JUGADOR_T";

        try (Statement st = conn.createStatement()) {
            st.executeUpdate(sql);
            System.out.println("Tabla " + tabla + " creada");
        } catch (SQLException e) {
            String msg = e.getMessage();
            if (msg != null && msg.contains("ORA-00955")) {
                System.out.println("Tabla " + tabla + " ya existe. Continuamos.");
            } else {
                System.out.println("ERROR creando tabla " + tabla + ": " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    // =========================
    // RECORRER DIRECTORIO Y CREAR TABLAS
    // =========================
    public void crearTablasDesdeDirectorio(String rutaCarpeta) throws Exception {
        Path dir = Path.of(rutaCarpeta);

        if (!Files.exists(dir) || !Files.isDirectory(dir)) {
            System.out.println("La carpeta no existe o no es carpeta: " + dir.toAbsolutePath());
            return;
        }

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir, "*.xml")) {
            int count = 0;
            for (Path p : stream) {
                crearTablaEquipo(p.getFileName().toString());
                count++;
            }
            System.out.println("Tablas creadas/revisadas: " + count);
        }
    }

    // =========================
    // INSERTAR JUGADOR
    // =========================
    public void insertarJugador(String equipo,
                                String nombre,
                                int dorsal,
                                String demarcacion,
                                String nacimiento) throws SQLException {

        String tabla = normalizarNombreTabla(equipo);
        String sql = "INSERT INTO " + tabla + " VALUES (JUGADOR_T(?, ?, ?, ?))";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nombre);
            ps.setInt(2, dorsal);
            ps.setString(3, demarcacion);
            ps.setString(4, nacimiento);
            ps.executeUpdate();
        }
    }

    // =========================
    // CARGAR EQUIPO DESDE XML (ruta completa)
    // =========================
    public void cargarEquipoDesdeXML(String rutaArchivo) throws Exception {

        rutaArchivo = rutaArchivo.trim();
        if (rutaArchivo.startsWith("\"") && rutaArchivo.endsWith("\"")) {
            rutaArchivo = rutaArchivo.substring(1, rutaArchivo.length() - 1);
        }

        File xmlFile = new File(rutaArchivo);
        if (!xmlFile.exists()) {
            System.out.println("NO EXISTE el archivo: " + xmlFile.getAbsolutePath());
            return;
        }

        String equipoTabla = xmlFile.getName(); // Barcelona.xml
        crearTablaEquipo(equipoTabla);

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(xmlFile);
        doc.getDocumentElement().normalize();

        NodeList jugadores = doc.getElementsByTagName("jugador");

        for (int i = 0; i < jugadores.getLength(); i++) {
            Element jugador = (Element) jugadores.item(i);

            String nombre = getTag(jugador, "nombre");
            int dorsal = Integer.parseInt(getTag(jugador, "dorsal"));
            String demarcacion = getTag(jugador, "demarcacion");
            String nacimiento = getTag(jugador, "nacimiento");

            insertarJugador(equipoTabla, nombre, dorsal, demarcacion, nacimiento);
        }

        System.out.println("Equipo cargado desde XML: " + xmlFile.getName());
    }

    // =========================
    // CARGAR EQUIPO DESDE CARPETA (carpeta + nombre equipo)
    // =========================
    public void cargarEquipoDesdeCarpeta(String rutaCarpeta, String equipo) throws Exception {
        String nombreArchivo = equipo.endsWith(".xml") ? equipo : (equipo + ".xml");
        String ruta = Path.of(rutaCarpeta, nombreArchivo).toString();
        cargarEquipoDesdeXML(ruta);
    }

    // =========================
    // MOSTRAR EQUIPO
    // =========================
    public void mostrarEquipo(String equipo) throws SQLException {
        String tabla = normalizarNombreTabla(equipo);
        String sql = "SELECT nombre, dorsal, demarcacion, nacimiento FROM " + tabla + " ORDER BY dorsal";

        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                System.out.println(
                        rs.getString("nombre") + " | " +
                                rs.getInt("dorsal") + " | " +
                                rs.getString("demarcacion") + " | " +
                                rs.getString("nacimiento")
                );
            }
        }
    }

    // =========================
    // BORRAR TODAS LAS TABLAS
    // =========================
    public void eliminarTodasLasTablas() throws SQLException {
        String sql = "SELECT table_name FROM user_tables";

        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                String tabla = rs.getString(1);
                try (Statement drop = conn.createStatement()) {
                    drop.executeUpdate("DROP TABLE " + tabla + " PURGE");
                }
            }
        }
        System.out.println("Todas las tablas eliminadas");
    }

    // =========================
    // HELPERS
    // =========================
    private String getTag(Element e, String tag) {
        NodeList nl = e.getElementsByTagName(tag);
        if (nl.getLength() == 0) return "";
        return nl.item(0).getTextContent().trim();
    }

    private String normalizarNombreTabla(String nombre) {
        String n = nombre.toUpperCase();
        n = n.replace(".XML", "");
        n = n.replaceAll("[^A-Z0-9_]", "_");
        return n;
    }
}
