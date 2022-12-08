package com.example.clockcheck.utilidades;

public class Utilidades {

    //Constantes campos tabla usuarios
    public static final String TABLA_USUARIOS = "usuarios";
    public static final String CAMPO_ID = "id";
    public static final String CAMPO_NOMBRES = "nombres";
    public static final String CAMPO_APELLIDOS = "apellidos";
    public static final String CAMPO_FECHANACIMIENTO = "fechaNacimiento";
    public static final String CAMPO_NUMTELEFONO = "numTelefono";
    public static final String CAMPO_CORREOELECTRONICO = "correoElectronico";
    public static final String CAMPO_FECHAREGISTRO = "fechaRegistro";
    public static final String CAMPO_CONTRASENA = "contraseña";

    public static final String CREAR_TABLA_USUARIOS = "CREATE TABLE "+TABLA_USUARIOS+" (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "nombres TEXT NOT NULL, " +
            "apellidos TEXT NOT NULL, " +
            "fechaNacimiento TEXT, " +
            "numTelefono TEXT, " +
            "correoElectronico TEXT, " +
            "fechaRegistro TEXT, " +
            "contraseña TEXT)";

    public static final String TABLA_ASISTENCIAS = "asistencias";
    public static final String CAMPO_FOLIO = "folio";
    public static final String CAMPO_ID_fk = "id";
    public static final String CAMPO_HORALLEGADA = "horaLlegada";
    public static final String CAMPO_HORASALIDA = "horaSalida";
    public static final String CAMPO_ASISTENCIA = "asistencia";
    public static final String CAMPO_CALIDADASISTENCIA = "calidadAsistencia";


    public static final String CREAR_TABLA_ASISTENCIAS = "CREATE TABLE "+TABLA_ASISTENCIAS+" (folio INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "id INTEGER, " +
            "horaLlegada TEXT, "+
            "horaSalida TEXT, " +
            "asistencia INTEGER, " +
            "calidadAsistencia TEXT, " +
            "CONSTRAINT FK_usuarios FOREIGN KEY (id) REFERENCES Usuarios(id))";



    public static final String SELECT_USUARIOS_ASISTENCIAS =
            "SELECT usuarios.nombres, usuarios.apellidos, asistencias.horaLlegada, asistencias.horaSalida FROM usuarios INNER JOIN asistencias ON usuarios.id = asistencias.id";
}
