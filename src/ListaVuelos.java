import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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
    //Devuelve true si puede insertar el vuelo
    public boolean insertarVuelo (Vuelo vuelo){
        if (!this.estaLlena()) {
            listaVuelos[getOcupacion()] = vuelo;
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
           if (listaVuelos[i] != null){
               if (listaVuelos[i].getSalida().coincide(fecha)) {
                   if (listaVuelos[i].getOrigen().getCodigo().equals(codigoOrigen)) {
                       if (listaVuelos[i].getDestino().getCodigo().equals(codigoDestino)) {
                           lista.listaVuelos[n]= listaVuelos[i];
                           n++;
                       }
                   }
               }
           }
        }
        return lista;
    };
    //Muestra por pantalla los vuelos siguiendo el formato de los ejemplos del enunciado
    public void listarVuelos(){
        for (Vuelo vuelo: listaVuelos
             ) {
            if (vuelo != null){
                System.out.println(vuelo);
            }
        }
    };
    //Permite seleccionar un vuelo existente a partir de su ID, usando el mensaje pasado como argumento para la solicitud
    //y siguiendo el orden y los textos mostrados en el enunciado, y usando la cadena cancelar para salir devolviendo null
    //La función solicita repetidamente hasta que se introduzca un ID correcto
    public Vuelo seleccionarVuelo(Scanner teclado, String mensaje, String cancelar){return null;};
    //Ha de escribir la lista de vuelos en la ruta y nombre del fichero pasado como parámetro.
    //Si existe el fichero, se sobreescribe, si no existe se crea.
    public boolean escribirVuelosCsv(String fichero){
        PrintWriter salida = null;
        try {
            salida = new PrintWriter(fichero);
            for (int i = 0; i < getOcupacion()-1; i++) {
                salida.printf("%s;%s;%s;%d;%s;%s;%d;%s;%f",
                        listaVuelos[i].getID(),
                        listaVuelos[i].getAvion().getMatricula(),
                        listaVuelos[i].getOrigen().getCodigo(),
                        listaVuelos[i].getOrigen().getTerminales(),
                        listaVuelos[i].getSalida().toString(),
                        listaVuelos[i].getDestino().getCodigo(),
                        listaVuelos[i].getDestino().getTerminales(),
                        listaVuelos[i].getLlegada().toString(),
                        listaVuelos[i].getPrecio());
                salida.println();
            }
        }catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        } finally {
            if (salida != null) {
                salida.close();
            }
        }
        return true;
    };

    //Métodos estáticos
    //Genera una lista de vuelos a partir del fichero CSV, usando los límites especificados como argumentos para la capacidad
    //de la lista
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
