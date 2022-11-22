/**
 * Description of the class
 *
 * @author  Fedor Kunin
 * @author  Isaac Lopez
 * @version     1.0
 */
public class Avion {

    /**
     * Constructor of the class
     *
     * @param marca
     * @param modelo
     * @param matricula
     * @param columnas
     * @param filas
     * @param alcance
     */

    private String marca, modelo, matricula;
    private int columnas, filas;
    private double alcance;

    public Avion(String marca, String modelo, String matricula, int columnas, int filas, double alcance){
        this.marca = marca;
        this.modelo = modelo;
        this.matricula = matricula;
        this.columnas = columnas;
        this.filas = filas;
        this.alcance = alcance;
    };
    public String getMarca(){ return marca; };
    public String getModelo(){ return modelo; };
    public String getMatricula(){ return matricula; };
    public int getColumnas(){ return columnas; };
    public int getFilas(){ return filas; };
    public double getAlcance(){ return alcance; };
    // Crea un String con los datos de un avión con el siguiente formato:
    // Boeing 737(EC-LKE): 180 asientos, hasta 5700.0 km
    public String toString(){
        return "";
    };
    // Crea un String con los datos de un avión con el siguiente formato:
    // Boeing 737(EC-LKE)
    public String toStringSimple(){
        return "";
    };
}
