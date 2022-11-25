import java.util.*;
/**
 * Description of the class
 *
 * @author  Fedor Kunin
 * @author  Isaac Lopez
 * @version     1.0
 */
public class Aeropuerto {

    /**
     * Constructor of the class
     *
     * @param nombre
     * @param codigo
     * @param latitud
     * @param longitud
     * @param terminales
     */

    private String nombre;
    private String codigo;
    private double latitud, longitud;
    private int terminales;


    public Aeropuerto(String nombre, String codigo, double latitud, double longitud, int terminales){

        this.nombre = nombre;
        this.codigo = codigo;
        this.latitud = latitud;
        this.longitud = longitud;
        this.terminales = terminales;

    };
    public String getNombre(){ return nombre; };
    public String getCodigo(){ return codigo; };
    public double getLatitud(){ return latitud; };
    public double getLongitud(){ return longitud; };
    public int getTerminales(){ return terminales; };

    // Calcula la distancia entre el aeropuerto que recibe el mensaje y el aeropuerto "destino" siguiendo la fórmula del enunciado
    public double distancia(Aeropuerto destino){
        double latitudA = Math.toRadians(this.latitud);
        double latitudB = Math.toRadians(destino.latitud);
        double longitudA = Math.toRadians(this.longitud);
        double longitudB = Math.toRadians(destino.longitud);
        double radioTierra = 6378;
        return Math.acos(Math.sin(latitudA)*Math.sin(latitudB)+Math.cos(latitudA)*Math.cos(latitudB)*Math.cos(longitudA-longitudB))*radioTierra;
    };
    // Crea un String con los datos de un aeropuerto con el siguiente formato:
    // Adolfo Suarez Madrid - Barajas(MAD), en (40.4927751 -3.5778), con 4 terminales
    public String toString(){
        String terminalString = String.format("%d terminales", terminales);
        if (terminales == 1) terminalString = String.format("%d terminal", terminales);
        return String.format("%s(%s), en (%f %f), con", nombre, codigo, latitud, longitud) + terminalString;
    };
    // Crea un String con los datos de un aeropuerto con el siguiente formato:
    // Adolfo Suarez Madrid - Barajas(MAD)
    public String toStringSimple(){
        return String.format("%s(%s)", nombre, codigo);
    };
}
