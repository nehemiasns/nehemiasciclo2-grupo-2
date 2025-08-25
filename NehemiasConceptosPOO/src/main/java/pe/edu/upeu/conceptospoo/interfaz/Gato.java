package pe.edu.upeu.conceptospoo.interfaz;

public class Gato implements Animal {

    @Override
    public void emitirSonido() {
        System.out.println("Miauuu miauuuu");
    }

    @Override
    public void dormir() {
        System.out.println("El gato está durmiendo Zzzzz...");
    }

    // Método extra no definido en la interfaz
    public String juega() {
        return "El gato está jugando con una lana!";
    }
}
