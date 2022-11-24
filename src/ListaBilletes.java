import java.io.FileReader;
import java.util.Scanner;

/**
 * Description of the class
 *
 * @author  Isaac Lopez
 * @author  Fedor Kunin
 * @version     1.0
 */
public class ListaBilletes {
    /**
     * Constructor of the class
     *
     * @param capacidad
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
        return lista.length;
        };
    public boolean estaLlena(){
        return this.getOcupacion() == capacidad;
        };
    public Billete getBillete(int i){
        return lista[i];
    };
    public boolean insertarBillete (Billete billete){
        if (lista.length != capacidad){
            if (!billete.getVuelo().vueloLleno() && !billete.getPasajero().maxBilletesAlcanzado()){
                lista[lista.length-1] = billete;
                return true;
            }
        }
        return false;
    };
    public Billete buscarBillete (String localizador){
        Billete billeteBuscado = null;
        for (Billete billete: lista
             ) {
            if (billete.getLocalizador() == localizador) billeteBuscado = billete;
        }
        return billeteBuscado;
    };
    public Billete buscarBillete (String idVuelo, int fila, int columna){
        Billete billeteBuscado = null;
        for (Billete billete: lista) {
            if (billete.getVuelo().getID() == idVuelo && billete.getColumna() == columna && billete.getFila() == fila) billeteBuscado = billete;
        }
        return billeteBuscado;
    };
    public boolean eliminarBillete (String localizador){
        boolean resultado = false;
        for (int i = 0; i < capacidad; i++){
            if (lista[i].getLocalizador() == localizador){
                lista[i] = null;
                resultado = true;
            }
        }
        return resultado;
    };
    // Muestra por pantalla los billetes de la lista
    public void listarBilletes(){

    };
    // Permite seleccionar un billete existente a partir de su localizador, usando el mensaje pasado como argumento para la solicitud
    // y siguiendo el orden y los textos mostrados en el enunciado
    // La función solicita repetidamente hasta que se introduzca un localizador correcto
    public Billete seleccionarBillete(Scanner teclado, String mensaje){
        return null;
    };
    // Añade los billetes al final de un fichero CSV, sin sobreescribirlo
    public boolean aniadirBilletesCsv(String fichero){
        return true;
    };

    // Métodos estáticos
    
    // Lee los billetes del fichero CSV y los añade a las listas de sus respectivos Vuelos y Pasajeros
    public static void leerBilletesCsv(String ficheroBilletes, ListaVuelos vuelos, ListaPasajeros pasajeros){
        Scanner sc = null;
        try{
            sc = new Scanner(new FileReader(ficheroBilletes));
            while (sc.hasNextLine()) {
                String nextLine = sc.nextLine();
                String[] linea = nextLine.split(";");
                String codigo = linea[1];
                String dni = linea[2];
                Billete.TIPO tipo = Billete.TIPO.TURISTA;
                switch (linea[3]){
                    case "PREFERENTE":
                        tipo = Billete.TIPO.PREFERENTE;
                        break;
                    case "PRIMERA":
                        tipo = Billete.TIPO.PRIMERA;
                        break;
                }
                Billete billete = new Billete(
                        linea[0],
                        vuelos.buscarVuelo(codigo),
                        pasajeros.buscarPasajeroDNI(dni),
                        tipo,
                        Integer.parseInt(linea[4]),
                        Integer.parseInt(linea[5]),
                        Double.parseDouble(linea[6])
                );


//              billete.getVuelo().ocuparAsiento(billete);
//                billete.getPasajero().aniadirBillete(billete);

            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            if (sc != null) {
                sc.close();
            }
        }
    };
}
