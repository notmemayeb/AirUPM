import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;
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
    private Avion[] listaAviones;

    public ListaAviones(int capacidad){
        this.capacidad = capacidad;
        this.listaAviones = new Avion[capacidad];
        for (int i = 0; i < capacidad; i++){
            listaAviones[i] = null;
        }
    };
    public int getOcupacion(){
        int ocupacion = 0;
        for (Avion avion: listaAviones
        ) {
            if (avion != null) ocupacion++;
        }
        return ocupacion;
    };
    public boolean estaLlena(){
        return this.getOcupacion() == capacidad;
        };
    public Avion getAvion(int posicion){
        return listaAviones[posicion];
    };
    public boolean insertarAvion(Avion avion){
        if (!this.estaLlena()){
            this.listaAviones[this.getOcupacion()] = avion;
            return true;
        }
        return false;
    };
    public Avion buscarAvion(String matricula){
        Avion avionBuscado = null;
        for (Avion avion: listaAviones
        ) {
            if (avion != null){
                if (avion.getMatricula().equals(matricula)){
                    avionBuscado = avion;
                }
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
        Scanner entrada = null;
        ListaAviones lista = new ListaAviones(capacidad);
        int lineas = 0;
        if (Utilidades.contarLineasFichero(fichero) != -1){
            lineas = Utilidades.contarLineasFichero(fichero);
        }
        try {
            entrada = new Scanner(new FileReader(fichero));
            for (int i = 0; i < Math.min(lineas, capacidad); i++){
                String[] linea = entrada.nextLine().split(";");
                String marca = linea[0];
                String modelo = linea[1];
                String matricula = linea[2];
                int columnas = Integer.parseInt(linea[3]);
                int filas = Integer.parseInt(linea[4]);
                double alcance = Double.parseDouble(linea[5]);

                lista.listaAviones[i] = new Avion(marca,modelo,matricula,columnas,filas,alcance);

            }
        } catch (IOException exc){
            System.out.println(exc.getMessage());
        }
        return lista;
    };
}
