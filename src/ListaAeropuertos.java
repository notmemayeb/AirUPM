import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

/**
 * Description of the class
 *
 * @author  Isaac Lopez
 * @author  Fedor Kunin
 * @version     1.0
 */
public class ListaAeropuertos {


    /**
     * Constructor of the class
     *
     * @param capacidad
     */

    private int capacidad;
    private Aeropuerto[] listaAeropuertos = new Aeropuerto[capacidad];

    public ListaAeropuertos(int capacidad){
        this.capacidad = capacidad;
    };
    public int getOcupacion(){
        return listaAeropuertos.length;
    };
    public boolean estaLlena(){
        return this.getOcupacion() == capacidad;
    };
    public Aeropuerto getAeropuerto(int i){
        return listaAeropuertos[i];
    };
    public boolean insertarAeropuerto(Aeropuerto aeropuerto){
        if (!this.estaLlena()){
            this.listaAeropuertos[this.listaAeropuertos.length-1] = aeropuerto;
            return true;
        }
        return false;
    };
    public Aeropuerto buscarAeropuerto(String codigo){
        Aeropuerto aeropuertoBuscado = null;
        for (Aeropuerto aeropuerto: listaAeropuertos
             ) {
            if (aeropuerto.getCodigo() == codigo) aeropuertoBuscado = aeropuerto;
        }
        return aeropuertoBuscado;
    };
    // Permite seleccionar un aeropuerto existente a partir de su código, usando el mensaje pasado como argumento para la solicitud
    // y siguiendo el orden y los textos mostrados en el enunciado
    // La función solicita repetidamente el código hasta que se introduzca uno correcto
    public Aeropuerto seleccionarAeropuerto(Scanner teclado, String mensaje){
        return null;
    };
    // Genera un fichero CSV con la lista de aeropuertos, sobreescribiendolo
    public boolean escribirAeropuertosCsv(String nombre){
        return true;
    };

    //Métodos estáticos
    //Genera una lista de aeropuertos a partir del fichero CSV, usando el argumento como   
    //capacidad máxima de la lista
    public static ListaAeropuertos leerAeropuertosCsv(String fichero, int capacidad){
        Scanner entry = null;
        ListaAeropuertos lista = new ListaAeropuertos(capacidad);
        try {
            entry = new Scanner(new FileReader(fichero));
            for (int i = 0; i < capacidad; i++){
                if (entry.hasNext()){
                    String[] nextLine = entry.nextLine().split(";");
                    lista.listaAeropuertos[i] = new Aeropuerto(
                            nextLine[0],
                            nextLine[1],
                            Double.parseDouble(nextLine[2]),
                            Double.parseDouble(nextLine[3]),
                            Integer.parseInt(nextLine[4]));
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            return null;
        } finally {
            if (entry != null) entry.close();
        }
        return lista;
    };
}
