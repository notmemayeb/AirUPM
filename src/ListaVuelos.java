import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Vector;

/**
 * ListaVuelos es una clase que contiene una la
 * lista de vuelos y los metodos necesarios para menejar
 * estos
 *
 * @author  Isaac Lopez
 * @author  Fedor Kunin
 * @version     1.0
 */
public class ListaVuelos {
    /**
     * Constructor of the class
     *
     * @param capacidad el numero de vuelos maximos en la lista
     * @param listaVuelos lista de los vuelos
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
        int ocupacion = 0;
        for (Vuelo vuelo: listaVuelos
        ) {
            if (vuelo != null) ocupacion++;
        }
        return ocupacion;
    };
    public boolean estaLlena(){
        return this.getOcupacion() == capacidad;
        };
    //Devuelve el objeto vuelo que está en la posición i del array
    public Vuelo getVuelo(int i){
        return listaVuelos[i];
    };

    /**
     * Inserta un vuelo en la lista comprobando
     * antes que esta no este llena
     * @param vuelo que se desea insertar
     * @return boolean indicando si se ha podido insertar o no
     */
    public boolean insertarVuelo (Vuelo vuelo){
        if (!this.estaLlena()) {
            listaVuelos[getOcupacion()] = vuelo;
        }
        return true;
    };

    /**
     * Devuelve el objeto vuelo que tenga el identificador igual al parámetro id
     * Si no lo encuentra, devolverá null
     * @param id del vuelo a buscar
     * @return vuelo de tipo Vuelo
     */
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

    /**
     * Devuelve un nuevo objeto ListaVuelos conteniendo los vuelos que vayan de un aeropuerto a otro en una determinada fecha
     * @param codigoOrigen codigo del aeropuerto de origen
     * @param codigoDestino codigo del aeropuerto del destino
     * @param fecha fecha de despege del vuelo
     * @return devuelve los vuelos en una lista
     */
    public ListaVuelos buscarVuelos(String codigoOrigen, String codigoDestino, Fecha fecha){
        ListaVuelos lista = new ListaVuelos(listaVuelos.length);
        int n = 0;
        for (int i = 0 ; i < listaVuelos.length; i++) {
           if (listaVuelos[i] != null){
               if (this.listaVuelos[i].coincide(codigoOrigen, codigoDestino, fecha)) {
                   lista.listaVuelos[n] = listaVuelos[i];
                   n++;
               }
           }
        }
        return lista;
    };

    /**
     * Muestra por pantalla los vuelos siguiendo el formato de los ejemplos del enunciado
     */
    public void listarVuelos(){
        for (Vuelo vuelo: listaVuelos
             ) {
            if (vuelo != null){
                System.out.println(vuelo);
            }
        }
    };

    /**
     * Permite seleccionar un vuelo existente a partir de su ID, usando el mensaje pasado como argumento para la solicitud
     * y siguiendo el orden y los textos mostrados en el enunciado, y usando la cadena cancelar para salir devolviendo null
     * La función solicita repetidamente hasta que se introduzca un ID correcto
     * @param teclado Scanner
     * @param mensaje usado para pedir la informacion
     * @param cancelar string usado para detener el proceso
     * @return vuelo seleccionado de tipo Vuelo
     */
    public Vuelo seleccionarVuelo(Scanner teclado, String mensaje, String cancelar){
        Vuelo vueloSeleccionado = null;
        String respuesta;
        try {
            do {
                System.out.print(mensaje);
                respuesta = teclado.nextLine();
                vueloSeleccionado = buscarVuelo(respuesta);
                if (vueloSeleccionado == null) {
                    System.out.println("ID de vuelo no encontrado.");
                }
            } while (vueloSeleccionado == null && !respuesta.equals(cancelar));
            if (respuesta.equals(cancelar)) vueloSeleccionado = null;
            return vueloSeleccionado;
        } catch (InputMismatchException _exc){
            System.out.println("Formato de entrada incorrecto.");
            teclado.nextLine();
            return null;
        }
    };

    /**
     * Ha de escribir la lista de vuelos en la ruta y nombre del fichero pasado como parámetro.
     * Si existe el fichero, se sobreescribe, si no existe se crea.
     * @param fichero donde se quiere escribir
     * @return boolean indicando si se ha podido escribir o no
     */
    public boolean escribirVuelosCsv(String fichero){
        PrintWriter salida = null;
        boolean resultado = false;
        try {
            salida = new PrintWriter(fichero);
            for (int i = 0; i < getOcupacion(); i++) {
                salida.printf("%s;%s;%s;%d;%s;%s;%d;%s;%f\n",
                        listaVuelos[i].getID(),
                        listaVuelos[i].getAvion().getMatricula(),
                        listaVuelos[i].getOrigen().getCodigo(),
                        listaVuelos[i].getOrigen().getTerminales(),
                        listaVuelos[i].getSalida().toString(),
                        listaVuelos[i].getDestino().getCodigo(),
                        listaVuelos[i].getDestino().getTerminales(),
                        listaVuelos[i].getLlegada().toString(),
                        listaVuelos[i].getPrecio());
            }
            resultado = true;
        } catch (FileNotFoundException _exc){
            System.out.println("Fichero Vuelos no encontrado.");

        } catch (Exception _exc){
            System.out.println("Error de escritura de fichero Vuelos.");

        } finally {
            if (salida != null){
                try {
                    salida.close();
                } catch (Exception _exc){
                    System.out.println("Error de cierre de fichero Vuelos.");
                }
            }
        }
        return resultado;
    };

    //Métodos estáticos

    /**
     * Genera una lista de vuelos a partir del fichero CSV, usando los límites especificados como argumentos para la capacidad
     * de la lista
     * @param fichero de donde se quiere leer
     * @param capacidad maxima de vuelos a leer
     * @param aeropuertos conocidos por el programa
     * @param aviones conocidos por el programa
     * @return boolean indicando si se ha podido leer o no
     */
    public static ListaVuelos leerVuelosCsv(String fichero, int capacidad, ListaAeropuertos aeropuertos, ListaAviones aviones){
        Scanner entrada = null;
        ListaVuelos lista = new ListaVuelos(capacidad);
        int lineas = 0;
        if (Utilidades.contarLineasFichero(fichero, "Vuelos") != -1){
            lineas = Utilidades.contarLineasFichero(fichero, "Vuelos");
        }
        try {
            entrada = new Scanner(new FileReader(fichero));
            for (int i = 0; i < Math.min(lineas, capacidad); i++){
                String[] linea = entrada.nextLine().split(";");
                String id = linea[0];
                Avion avion = aviones.buscarAvion(linea[1]);
                Aeropuerto origen = aeropuertos.buscarAeropuerto(linea[2]);
                int terminalOrigen = Integer.parseInt(linea[3]);
                Fecha salida = Fecha.fromString(linea[4]);
                Aeropuerto destino = aeropuertos.buscarAeropuerto(linea[5]);
                int terminalDestino = Integer.parseInt(linea[6]);
                Fecha llegada = Fecha.fromString(linea[7]);
                double precio = Double.parseDouble(linea[8]);

                lista.listaVuelos[i] = new Vuelo(id, avion, origen, terminalOrigen, salida, destino, terminalDestino, llegada, precio);
            }
        } catch (FileNotFoundException _exc){

            System.out.println("Fichero Vuelos no encontrado.");

        } catch (Exception _exc){

            System.out.println("Error de lectura de fichero Vuelos.");

        } finally {
            if (entrada != null){
                try {
                    entrada.close();
                } catch (Exception _exc){
                    System.out.println("Error de cierre de fichero Vuelos.");
                }
            }
        }
        return lista;
    };
}
