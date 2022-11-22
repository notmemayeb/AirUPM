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

    private static String nombre;
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
        return 1;
    };
    // Crea un String con los datos de un aeropuerto con el siguiente formato:
    // Adolfo Suarez Madrid - Barajas(MAD), en (40.4927751 -3.5778), con 4 terminales
    public String toString(){
        return "";
    };
    // Crea un String con los datos de un aeropuerto con el siguiente formato:
    // Adolfo Suarez Madrid - Barajas(MAD)
    public String toStringSimple(){
        return "";
    };
}
