import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * ListaBilletes es una clase que contiene una la
 * lista de billetes y los metodos necesarios para menejar
 * estos
 *
 * @author  Isaac Lopez
 * @author  Fedor Kunin
 * @version     1.0
 */
public class ListaBilletes {
    /**
     * Constructor of the class
     *
     * @param capacidad el numero de billetes maximos en la lista
     */

    private int capacidad;
    private Billete[] lista;

    public ListaBilletes(int capacidad){
        this.capacidad = capacidad;
        this.lista =new Billete[capacidad];
        for (int i = 0; i < capacidad; i++){
            lista[i] = null;
        }
    };
    public int getOcupacion(){
        int ocupacion = 0;
        for (Billete billete: lista
        ) {
            if (billete != null) ocupacion++;
        }
        return ocupacion;
    };
    public boolean estaLlena(){
        return this.getOcupacion() == capacidad;
        };
    public Billete getBillete(int i){
        return lista[i];
    };

    /**
     * Introduce un billete en la lista comprobando que no este
     * llena antes
     * @param billete que se quiere introducir en la lista
     * @return boolean indicando si se ha podido introducir o no
     */
    public boolean insertarBillete (Billete billete){
        if (!this.estaLlena()){
            lista[this.getOcupacion()] = billete;
            return true;
        }
        return false;
    };

    /**
     * Busca un billete en la lista usando su localizador
     * @param localizador del billete a buscar
     * @return billete tipo Billete
     */
    public Billete buscarBillete (String localizador){
        Billete billeteBuscado = null;
        for (Billete billete: lista) {
            if (billete != null){
                if (billete.getLocalizador().equals(localizador)) billeteBuscado = billete;
            }
        }
        return billeteBuscado;
    };

    /**
     * Busca un billete en la lisyta usando su id de vuelo
     * y su numero de buatca asociado
     * @param idVuelo vuelo al que pertenece el billete
     * @param fila de la butaca
     * @param columna de la butaca
     * @return billete tipo Billete
     */
    public Billete buscarBillete(String idVuelo, int fila, int columna){
        Billete billeteBuscado = null;
        for (Billete billete: lista) {
            if (billete != null){
                if (billete.getVuelo().getID().equals(idVuelo)  && billete.getColumna() == columna && billete.getFila() == fila) billeteBuscado = billete;
            }
        }
        return billeteBuscado;
    };

    /**
     * Elimina un billete de la lista usando su localizador
     * @param localizador del billete a eliminar
     * @return boolean indicando si se ha podido eliminar o no
     */
    public boolean eliminarBillete (String localizador){
        boolean resultado = false;
        for (int i = 0; i < capacidad; i++){
            if (lista[i] != null){
                if (lista[i].getLocalizador().equals(localizador)){
                    lista[i] = null;
                    resultado = true;
                }
            }

        }
        return resultado;
    };

    /**
     * Muestra por pantalla los billetes de la lista
     */
    public void listarBilletes(){
        for (int i = 0; i <= getOcupacion(); i++) {
            if (lista[i] != null) System.out.println(lista[i].toString());
        }
    };

    /**
     * Permite seleccionar un billete existente a partir de su localizador, usando el mensaje pasado como argumento para la solicitud
     * y siguiendo el orden y los textos mostrados en el enunciado
     * La funci??n solicita repetidamente hasta que se introduzca un localizador correcto
     * @param teclado Scanner
     * @param mensaje usado para pedir la informacion
     * @return billete seleccionado
     */
    public Billete seleccionarBillete(Scanner teclado, String mensaje){
        String localizador;
        do {
            System.out.print(mensaje);
            localizador = teclado.next();
            if (buscarBillete(localizador) == null) System.out.println("Localizador no encontrado.");
        }while(buscarBillete(localizador) == null);
        return buscarBillete(localizador);
    };

    /**
     * A??ade los billetes al final de un fichero CSV, sin sobreescribirlo
     * @param fichero donde se desea scribir
     * @return boolean indicando si se ha podido escribir o no
     */
    public boolean aniadirBilletesCsv(String fichero){
        PrintWriter salida = null;
        Scanner sc = null;
        boolean resultado = false;
        try {
            salida = new PrintWriter(fichero);
            sc = new Scanner(new FileReader(fichero));
            String linea = "";
            while (sc.hasNextLine()) {sc.nextLine();}
            for (int i = 0; i < getOcupacion(); i++) {
//                salida.printf("%s;%s;%s;%s;%d;%d;%f\n",
//                        lista[i].getLocalizador(),
//                        lista[i].getVuelo().getID(),
//                        lista[i].getPasajero().getDNI(),
//                        lista[i].getTipo().toString(),
//                        lista[i].getFila(),
//                        lista[i].getColumna(),
//                        lista[i].getPrecio());
                linea = lista[i].getLocalizador() + ";" + lista[i].getVuelo().getID() + ";" + lista[i].getPasajero().getDNI() + ";" + lista[i].getTipo().toString() + ";" + lista[i].getFila() + ";" + lista[i].getColumna() + ";" + lista[i].getPrecio();
                salida.println(linea);
            }
            resultado = true;
        } catch (FileNotFoundException _exc){
            System.out.println("Fichero Billetes no encontrado.");

        } catch (Exception _exc){
            System.out.println("Error de escritura de fichero Billetes.");

        } finally {
            if (salida != null){
                try {
                    salida.close();
                } catch (Exception _exc){
                    System.out.println("Error de cierre de fichero Billetes.");
                }
            }
        }
        return resultado;
    };

    // M??todos est??ticos

    /**
     * Lee los billetes del fichero CSV y los a??ade a las listas de sus respectivos Vuelos y Pasajeros
     * @param ficheroBilletes donde leer
     * @param vuelos presentes en el programa
     * @param pasajeros presentes en el programa
     */
    public static void leerBilletesCsv(String ficheroBilletes, ListaVuelos vuelos, ListaPasajeros pasajeros){
        Scanner entrada = null;
        int lineas = 0;
        if (Utilidades.contarLineasFichero(ficheroBilletes, "Billetes") != -1){
            lineas = Utilidades.contarLineasFichero(ficheroBilletes, "Billetes");
        }
        try {
            entrada = new Scanner(new FileReader(ficheroBilletes));
            for (int i = 0; i < lineas; i++){
                String[] linea = entrada.nextLine().split(";");
                String localizador = linea[0];
                Vuelo vuelo = vuelos.buscarVuelo(linea[1]);
                Pasajero pasajero = pasajeros.buscarPasajeroDNI(linea[2]);
                Billete.TIPO tipo = Billete.TIPO.valueOf(linea[3]);
                int fila = Integer.parseInt(linea[4]);
                int columna = Integer.parseInt(linea[5]);
                double precio = Double.parseDouble(linea[6]);

                Billete billete = new Billete(localizador, vuelo, pasajero, tipo, fila, columna, precio);

                pasajero.aniadirBillete(billete);
                vuelo.ocuparAsiento(billete);


            }
        } catch (FileNotFoundException _exc){

            System.out.println("Fichero Billetes no encontrado.");

        } catch (Exception _exc){

            System.out.println("Error de lectura de fichero Billetes.");

        } finally {
            if (entrada != null){
                try {
                    entrada.close();
                } catch (Exception _exc){
                    System.out.println("Error de cierre de fichero Billetes.");
                }
            }
        }
    };
}
