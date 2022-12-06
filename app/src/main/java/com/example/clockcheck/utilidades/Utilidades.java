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
}
