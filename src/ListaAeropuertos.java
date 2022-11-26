import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
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
    public boolean insertarAeropuerto(Aeropuerto aeropuerto){
        if (!this.estaLlena()){
            this.listaAeropuertos[this.getOcupacion()] = aeropuerto;
            return true;
        }
        return false;
    };
    public Aeropuerto buscarAeropuerto(String codigo){
        Aeropuerto aeropuertoBuscado = null;
        for (Aeropuerto aeropuerto: listaAeropuertos
             ) {
            if (aeropuerto != null) if (aeropuerto.getCodigo().equals(codigo)) aeropuertoBuscado = aeropuerto;
        }
        return aeropuertoBuscado;
    };
    // Permite seleccionar un aeropuerto existente a partir de su código, usando el mensaje pasado como argumento para la solicitud
    // y siguiendo el orden y los textos mostrados en el enunciado
    // La función solicita repetidamente el código hasta que se introduzca uno correcto
    public Aeropuerto seleccionarAeropuerto(Scanner teclado, String mensaje){
        String codigoOrigen;
        do {
            System.out.print(mensaje);
            codigoOrigen = teclado.next();
            if (this.buscarAeropuerto(codigoOrigen) == null) System.out.println("Código de aeropuerto no encontrado.");
        } while (this.buscarAeropuerto(codigoOrigen) == null);
        return buscarAeropuerto(codigoOrigen);
    };
    // Genera un fichero CSV con la lista de aeropuertos, sobreescribiendolo
    public boolean escribirAeropuertosCsv(String nombre){
        return true;
    };

    //Métodos estáticos
    //Genera una lista de aeropuertos a partir del fichero CSV, usando el argumento como   
    //capacidad máxima de la lista
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
