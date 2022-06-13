package com.example.clockcheck.utilidades;

public class Utilidades {

    //constantes campos tabla usuario
    public static final String TABLA_USUARIO = "usuario";

    //public static final String CAMPO_ID = "id";

    public static final String CAMPO_NOMBRE = "nombre";
    public static final String CAMPO_APELLIDOP = "apellidoP";
    public static final String CAMPO_APELLIDOM = "apellidoM";
    public static final String CAMPO_FECHAN = "fechaN";
    public static final String CAMPO_NUMTEL = "numTel";
    public static final String CAMPO_DIRECCION = "direccion";
    public static final String CAMPO_CORREO = "correo";


    public static final String CREAR_TABLA_USUARIO= "CREATE TABLE "+TABLA_USUARIO+" (id INTEGER PRIMARY KEY AUTOINCREMENT,"
            +CAMPO_NOMBRE+" TEXT, "+CAMPO_APELLIDOP+" TEXT,"+CAMPO_APELLIDOM+","+CAMPO_FECHAN+" TEXT, "+CAMPO_NUMTEL+" TEXT, "
            +CAMPO_DIRECCION+" TEXT, "+CAMPO_CORREO+" TEXT)";
}
