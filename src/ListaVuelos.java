import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.Vector;

/**
 * Description of the class
 *
 * @author  Isaac Lopez
 * @author  Fedor Kunin
 * @version     1.0
 */
public class ListaVuelos {
    /**
     * Constructor of the class
     *
     * @param capacidad
     * @param listaVuelos
     */

    private int capacidad;
    private Vuelo[] listaVuelos;
    public ListaVuelos(int capacidad){
        this.capacidad = capacidad;
        this.listaVuelos  = new Vuelo[capacidad];
        for (int i = 0; i < capacidad; i++){
            listaVuelos[i] = null;
        }
    };
    public int getOcupacion(){
        return listaVuelos.length;
        };
    public boolean estaLlena(){
        if (getOcupacion()>=capacidad) {
            return true;
        }
        return false;
        };
    //Devuelve el objeto vuelo que está en la posición i del array
    public Vuelo getVuelo(int i){
        return listaVuelos[i];
    };
    //Devuelve true si puede insertar el vuelo
    public boolean insertarVuelo (Vuelo vuelo){
        if (!estaLlena()) {
            listaVuelos[getOcupacion()-1] = vuelo;
        }
        return true;
    };
    //Devuelve el objeto vuelo que tenga el identificador igual al parámetro id
    //Si no lo encuentra, devolverá null
    public Vuelo buscarVuelo (String id){
        Vuelo vueloBuscado = null;
        for (int i = 0;i<=getOcupacion()-1;i++) {
            if (listaVuelos[i] != null){
                if (listaVuelos[i].getID().equals(id)) {
                    vueloBuscado = listaVuelos[i];
                }
            }
        }
        return vueloBuscado;
    };
    //Devuelve un nuevo objeto ListaVuelos conteniendo los vuelos que vayan de un aeropuerto a otro en una determinada fecha
    public ListaVuelos buscarVuelos(String codigoOrigen, String codigoDestino, Fecha fecha){
        ListaVuelos lista = new ListaVuelos(listaVuelos.length);
        int n = 0;
        for (int i = 0 ; i < listaVuelos.length; i++) {
            if (listaVuelos[i].getSalida() == fecha) {
                if (listaVuelos[i].getOrigen().getCodigo().equals(codigoOrigen)) {
                    if (listaVuelos[i].getDestino().getCodigo().equals(codigoDestino)) {
                        lista.listaVuelos[n]= listaVuelos[i];
                        n++;
                    }
                }
            }
        }
        return null;
    };
    //Muestra por pantalla los vuelos siguiendo el formato de los ejemplos del enunciado
    public void listarVuelos(){
        for (int i = 0; i < listaVuelos.length-1; i++) {
            System.out.printf("Vuelo %S de %s(%s) T%d (%s) a %s(%s) T%d (%s) en %s %s(%s) por %f€\n",
                    listaVuelos[i].getID(),
                    listaVuelos[i].getOrigen().getNombre(),
                    listaVuelos[i].getOrigen().getCodigo(),
                    listaVuelos[i].getOrigen().getTerminales(),
                    listaVuelos[i].getSalida().toString(),
                    listaVuelos[i].getDestino().getNombre(),
                    listaVuelos[i].getDestino().getCodigo(),
                    listaVuelos[i].getDestino().getTerminales(),
                    listaVuelos[i].getLlegada().toString(),
                    listaVuelos[i].getAvion().getMarca(),
                    listaVuelos[i].getAvion().getModelo(),
                    listaVuelos[i].getAvion().getMatricula(),
                    listaVuelos[i].getPrecio());
        }
    };
    //Permite seleccionar un vuelo existente a partir de su ID, usando el mensaje pasado como argumento para la solicitud
    //y siguiendo el orden y los textos mostrados en el enunciado, y usando la cadena cancelar para salir devolviendo null
    //La función solicita repetidamente hasta que se introduzca un ID correcto
    public Vuelo seleccionarVuelo(Scanner teclado, String mensaje, String cancelar){return null;};
    //Ha de escribir la lista de vuelos en la ruta y nombre del fichero pasado como parámetro.
    //Si existe el fichero, se sobreescribe, si no existe se crea.
    public boolean escribirVuelosCsv(String fichero){
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(fichero);
            for (int i = 0; i < getOcupacion()-1; i++) {
                pw.printf("%s;%s;%s;%d;%s;%s;%d;%s;%f",
                        listaVuelos[i].getID(),
                        listaVuelos[i].getAvion().getMatricula(),
                        listaVuelos[i].getOrigen().getCodigo(),
                        listaVuelos[i].getOrigen().getTerminales(),
                        listaVuelos[i].getSalida().toString(),
                        listaVuelos[i].getDestino().getCodigo(),
                        listaVuelos[i].getDestino().getTerminales(),
                        listaVuelos[i].getLlegada().toString(),
                        listaVuelos[i].getPrecio());
                //PM1111;EC-LKF;MAD;4;24/12/2022 12:35:00;BCN;1;24/12/2022 14:05:30;100.0
            }
        }catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        } finally {
            if (pw != null) {
                pw.close();
            }
        }
        return true;
    };

    //Métodos estáticos
    //Genera una lista de vuelos a partir del fichero CSV, usando los límites especificados como argumentos para la capacidad
    //de la lista
    public static ListaVuelos leerVuelosCsv(String fichero, int capacidad, ListaAeropuertos aeropuertos, ListaAviones aviones){
        Scanner sc = null;
        ListaVuelos lista = new ListaVuelos(capacidad);
        try {
            sc = new Scanner(new FileReader(fichero));
            int i = 0;
            do {
                String[] nextLine = sc.nextLine().split(";");
                lista.listaVuelos[i] = new Vuelo(nextLine[0],
                        aviones.buscarAvion(nextLine[1]),
                        aeropuertos.buscarAeropuerto(nextLine[2]),
                        Integer.parseInt(nextLine[3]),
                        Fecha.fromString(nextLine[4]),
                        aeropuertos.buscarAeropuerto(nextLine[5]),
                        Integer.parseInt(nextLine[6]),
                        Fecha.fromString(nextLine[7]),
                        Double.parseDouble(nextLine[8]));
                i++;
            } while (sc.hasNextLine());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        } finally {
            if (sc != null) {
                sc.close();
            }
        }
        return lista;
    };
}
