import java.io.File;
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
public class ListaAviones {

    /**
     * Constructor of the class
     *
     * @param capacidad
     * @param listaAviones
     */

    private int capacidad;
    private Avion[] listaAviones = new Avion[capacidad];

    public ListaAviones(int capacidad){
        this.capacidad = capacidad;
    };
    public int getOcupacion(){
        return listaAviones.length;
        };
    public boolean estaLlena(){
        return this.getOcupacion() == capacidad;
        };
    public Avion getAvion(int posicion){
        return listaAviones[posicion];
    };
    public boolean insertarAvion(Avion avion){
        return true;
    };
    public Avion buscarAvion(String matricula){
        Avion avionBuscado = null;
        for (Avion avion: listaAviones
        ) {
            if (avion.getMatricula() == matricula){
                avionBuscado = avion;
            }
        }
        return avionBuscado;
    };
    // Permite seleccionar un avión existente a partir de su matrícula, y comprueba si dispone de un alcance mayor o igual que el pasado como argumento,
    // usando el mensaje pasado como argumento para la solicitud y siguiendo el orden y los textos mostrados en el enunciado
    // La función solicita repetidamente la matrícula del avión hasta que se introduzca uno con alcance suficiente
    public Avion seleccionarAvion(Scanner teclado, String mensaje, double alcance){
        return null;
    };
    // Genera un fichero CSV con la lista de aviones, sobreescribiendolo
    public boolean escribirAvionesCsv(String nombre){
        return true;
    };

    //Métodos estáticos
    // Genera una lista de aviones a partir del fichero CSV, usando el argumento como   
    // capacidad máxima de la lista
    public static ListaAviones leerAvionesCsv(String fichero, int capacidad){
        Scanner entry = null;
        ListaAviones lista = new ListaAviones(capacidad);
        try {
            entry = new Scanner(new FileReader(fichero));
            for (int i = 0; i < capacidad; i++){
                if (entry.hasNext()) {
                    String[] nextLine = entry.nextLine().split(";");
                    lista.listaAviones[i] = new Avion(
                            nextLine[0],
                            nextLine[1],
                            nextLine[2],
                            Integer.parseInt(nextLine[3]),
                            Integer.parseInt(nextLine[4]),
                            Double.parseDouble(nextLine[5])
                    );
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
