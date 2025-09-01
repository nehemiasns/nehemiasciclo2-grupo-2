package pe.edu.upeu.asistencia.enums;

public enum CARRERA {
    Sistemas (FACULTAD.FIA),
    Civil(FACULTAD.FIA),
    Administracion(FACULTAD.FCE),
    Nutricion(FACULTAD.FCE),
    Educacion(FACULTAD.FCS),


General(FACULTAD.GENERAL);
private FACULTAD facultad;

private CARRERA(FACULTAD facultad){
    this.facultad=facultad;
}
public FACULTAD getFacultad() {
    return facultad;
}
}
