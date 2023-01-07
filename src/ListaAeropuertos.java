import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Scanner;

/**
 * ListaAeropuertos es una clase que contiene una la
 * lista de aeropuertos y los metodos necesarios para menejar
 * estos
 *
 * @author  Isaac Lopez
 * @author  Fedor Kunin
 * @version     1.0
 */
public class ListaAeropuertos {


    /**
     * Constructor of the class
     *
     * @param capacidad el numero de aeropuertos maximos en la lista
     */

    private int capacidad;
    private Aeropuerto[] listaAeropuertos;

    public ListaAeropuertos(int capacidad){
        this.capacidad = capacidad;
        this.listaAeropuertos = new Aeropuerto[capacidad];
        for (int i = 0; i < capacidad; i++){
            listaAeropuertos[i] = null;
        }
    };
    public int getOcupacion(){
        int ocupacion = 0;
        for (Aeropuerto aeropuerto: listaAeropuertos
        ) {
            if (aeropuerto != null) ocupacion++;
        }
        return ocupacion;
    };
    public boolean estaLlena(){
        return this.getOcupacion() == capacidad;
    };
    public Aeropuerto getAeropuerto(int i){
        return listaAeropuertos[i];
    };

    /**
     * Inserta un aeropuerto a la lista comprobando antes
     * que esta no esta llena
     * @param aeropuerto que aeropuerto introducir
     * @return boolean indicando si se ha podido introducir o no
     */
    public boolean insertarAeropuerto(Aeropuerto aeropuerto){
        if (!this.estaLlena()){
            this.listaAeropuertos[this.getOcupacion()] = aeropuerto;
            return true;
        }
        return false;
    };

    /**
     * Busca un aeropuerto en la lista usando su codigo
     * @param codigo del aeropuerto
     * @return aeropuerto tipo Aeropuerto
     */
    public Aeropuerto buscarAeropuerto(String codigo){
        Aeropuerto aeropuertoBuscado = null;
        for (Aeropuerto aeropuerto: listaAeropuertos
             ) {
            if (aeropuerto != null) if (aeropuerto.getCodigo().equals(codigo)) aeropuertoBuscado = aeropuerto;
        }
        return aeropuertoBuscado;
    };

    /**
     * Permite seleccionar un aeropuerto existente a partir de su código, usando el mensaje pasado como argumento para la solicitud
     * y siguiendo el orden y los textos mostrados en el enunciado
     * La función solicita repetidamente el código hasta que se introduzca uno correcto
     * @param teclado Scanner
     * @param mensaje usado para solicitar la informacion
     * @return aeropuerto tipo Aeropuerto
     */
    public Aeropuerto seleccionarAeropuerto(Scanner teclado, String mensaje){
        String codigoOrigen;
        do {
            System.out.print(mensaje);
            codigoOrigen = teclado.next();
            teclado.nextLine();
            if (this.buscarAeropuerto(codigoOrigen) == null) System.out.println("Código de aeropuerto no encontrado.");
        } while (this.buscarAeropuerto(codigoOrigen) == null);
        return buscarAeropuerto(codigoOrigen);
    };

    /**
     * Genera un fichero CSV con la lista de aeropuertos, sobreescribiendolo
     * @param nombre del fichero donde escribir la informacion
     * @return boolean indicando si se ha podido escribir o no
     */
    public boolean escribirAeropuertosCsv(String nombre){
        PrintWriter salida = null;
        boolean resultado = false;
        try {
            salida = new PrintWriter(nombre);
            String linea = "";
            for (int i = 0; i < getOcupacion(); i++) {

                linea = String.valueOf(listaAeropuertos[i].getNombre()) + ";" + String.valueOf(listaAeropuertos[i].getCodigo()) + ";" + String.valueOf(listaAeropuertos[i].getLatitud()) + ";" + String.valueOf(listaAeropuertos[i].getLongitud()) + ";" + String.valueOf(listaAeropuertos[i].getTerminales());

                salida.println(linea);
            }
            resultado = true;
        } catch (FileNotFoundException _exc){
            System.out.println("Fichero Aeropuertos no encontrado.");
        } catch (Exception _exc){
            System.out.println("Error de escritura de fichero Aeropuertos.");

        } finally {
            if (salida != null){
                try {
                    salida.close();
                } catch (Exception _exc){
                    System.out.println("Error de cierre de fichero Aeropuertos.");
                }
            }
        }
        return resultado;
    };

    //Métodos estáticos

    /**
     * Genera una lista de aeropuertos a partir del fichero CSV, usando el argumento como
     * capacidad máxima de la lista
     * @param fichero donde se va a escribir la informacion
     * @param capacidad de aeropuertos maximos en la lista
     * @return boolean indicando si se ha podido leer o no
     */
    public static ListaAeropuertos leerAeropuertosCsv(String fichero, int capacidad){
        Scanner entrada = null;
        ListaAeropuertos lista = new ListaAeropuertos(capacidad);
        int lineas = 0;
        if (Utilidades.contarLineasFichero(fichero, "Aeropuertos") != -1){
            lineas = Utilidades.contarLineasFichero(fichero, "Aeropuertos");
        }
        try {
            entrada = new Scanner(new FileReader(fichero));
            for (int i = 0; i < Math.min(lineas, capacidad); i++){
                String[] linea = entrada.nextLine().split(";");
                String nombre = linea[0];
                String codigo = linea[1];
                double latitud = Double.parseDouble(linea[2]);
                double longitud = Double.parseDouble(linea[3]);
                int terminals = Integer.parseInt(linea[4]);

                lista.listaAeropuertos[i] = new Aeropuerto(nombre,codigo,latitud,longitud,terminals);
            }
        } catch (FileNotFoundException _exc){

            System.out.println("Fichero Aeropuertos no encontrado.");

        } catch (Exception _exc){

            System.out.println("Error de lectura de fichero Aeropuertos.");

        } finally {
            if (entrada != null){
                try {
                    entrada.close();
                } catch (Exception _exc){
                    System.out.println("Error de cierre de fichero Aeropuertos.");
                }
            }
        }
        return lista;
    };
}
