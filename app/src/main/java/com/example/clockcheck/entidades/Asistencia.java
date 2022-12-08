package com.example.clockcheck.entidades;

public class Asistencia {
    private String nombres;
    private String apellidos;

    private String folio;
    private String id;
    private String horaLlegada;
    private String horaSalida;
    private String calidadAsistencia;

    /*
    public Asistencia(String nombres, String apellidos, String folio, String id, String horaLlegada, String horaSalida, String calidadAsistencia) {
        this.nombres = nombres;
        this.apellidos = apellidos;

        this.folio = folio;
        this.id = id;
        this.horaLlegada = horaLlegada;
        this.horaSalida = horaSalida;
        this.calidadAsistencia = calidadAsistencia;
    }
     */

    public Asistencia(){

    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHoraLlegada() {
        return horaLlegada;
    }

    public void setHoraLlegada(String horaLlegada) {
        this.horaLlegada = horaLlegada;
    }

    public String getHoraSalida() {
        return horaSalida;
    }

    public void setHoraSalida(String horaSalida) {
        this.horaSalida = horaSalida;
    }

    public String getCalidadAsistencia() {
        return calidadAsistencia;
    }

    public void setCalidadAsistencia(String calidadAsistencia) {
        this.calidadAsistencia = calidadAsistencia;
    }
}
