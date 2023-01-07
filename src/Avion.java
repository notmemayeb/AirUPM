/**
 * Avion es una clase que enclausura toda la
 * informacion necesaria para la identificacion
 * y distinccion de un avion
 *
 * @author  Fedor Kunin
 * @author  Isaac Lopez
 * @version     1.0
 */
public class Avion {

    /**
     * Constructor of the class
     *
     * @param marca la marca del avion
     * @param modelo el modelo del avion
     * @param matricula la matricula del avion
     * @param columnas numero de columnas en el avion
     * @param filas numero de filas del avion
     * @param alcance alcance en Km del avion
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

    /**
     * Crea un String con los datos de un avión con el siguiente formato:
     * Boeing 737(EC-LKE): 180 asientos, hasta 5700.0 km
     * @return string con formato
     */
    public String toString(){
        return String.format("%s %s(%s): %d asientos, hasta %.1fKm",
                this.marca,
                this.modelo,
                this.matricula,
                this.getColumnas()*this.getFilas(),
                this.alcance

        );
    };

    /**
     * Crea un String con los datos de un avión con el siguiente formato:
     * Boeing 737(EC-LKE)
     * @return  string con formato
     */
    public String toStringSimple(){
        return String.format("%s %s(%s)",
                this.marca,
                this.modelo,
                this.matricula
        );
    };
}
