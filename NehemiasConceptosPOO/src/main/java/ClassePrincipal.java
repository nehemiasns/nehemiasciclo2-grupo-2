import pe.edu.upeu.conceptospoo.interfaz.Animal;
import pe.edu.upeu.conceptospoo.interfaz.Loro;
import pe.edu.upeu.conceptospoo.interfaz.Gato;

public class ClassePrincipal {
    public static void main(String[] args) {
        Animal animal = new Loro();
        animal.emitirSonido();
        animal.dormir();

        animal = new Gato();
        animal.emitirSonido();
        animal.dormir();
    }
}
