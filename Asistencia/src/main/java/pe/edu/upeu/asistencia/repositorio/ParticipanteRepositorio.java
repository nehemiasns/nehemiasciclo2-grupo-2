package pe.edu.upeu.asistencia.repositorio;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import pe.edu.upeu.asistencia.enums.CARRERA;
import pe.edu.upeu.asistencia.enums.TITPO_PARTICIPANTE;
import pe.edu.upeu.asistencia.modelo.Participante;

import java.util.ArrayList;
import java.util.List;

public abstract class ParticipanteRepositorio {
   public List<Participante> listaParticipantes = new ArrayList<>();

   public List<Participante> findAllEntidades() {

      listaParticipantes.add(
              new Participante(
                      new SimpleStringProperty("73944395"),
                      new SimpleStringProperty("Pepe"),
                      new SimpleStringProperty("ramos"),
                      new SimpleBooleanProperty(true), CARRERA.Sistemas,
                      TITPO_PARTICIPANTE.Asistente
              )
      );
      listaParticipantes.add(
              new Participante(
                      new SimpleStringProperty("73944395"),
                      new SimpleStringProperty("nehemias"),
                      new SimpleStringProperty("quispe"),
                      new SimpleBooleanProperty(true),CARRERA.Sistemas,
                      TITPO_PARTICIPANTE.Asistente
              )
      );


      return listaParticipantes;
   }
}
