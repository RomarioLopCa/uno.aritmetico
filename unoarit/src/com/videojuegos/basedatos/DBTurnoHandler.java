package com.videojuegos.basedatos;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBTurnoHandler extends SQLiteOpenHelper {

	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 2;

	// Database Name
	private static final String DATABASE_NAME = "gestionPartidas";

	// Contacts table name
	private static final String TABLE_PARTIDA = "partida";

	// Contacts Table Columns names
	private static final String KEY_ID = "id";
	private static final String KEY_ID_PARTIDA = "num_partida";
	private static final String KEY_TURNO = "turno";
	private static final String KEY_JUGADOR = "jugador";
	private static final String KEY_COLOR = "color";
	private static final String KEY_OPERACION_MAZO = "operacion_mazo";
	private static final String KEY_OPERACION_JUGADA = "operacion_jugada";
	private static final String KEY_VALOR = "valor";

	public DBTurnoHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	/**
	 * Método para crear la tabla
	 */

	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_PARTIDA + "("
				+ KEY_ID + " INTEGER PRIMARY KEY," + KEY_ID_PARTIDA + " INTEGER,"
				+ KEY_TURNO + " INTEGER," + KEY_JUGADOR + " TEXT,"
				+ KEY_COLOR + " TEXT," + KEY_OPERACION_MAZO + " TEXT,"
				+ KEY_OPERACION_JUGADA + " TEXT," + KEY_VALOR + " TEXT" + ")";
		db.execSQL(CREATE_CONTACTS_TABLE);
	}

	/**
	 * Método para actualizar la tabla
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_PARTIDA);
		onCreate(db);
	}

	/**
	 * Método para agregar un nuevo turno en la tabla
	 */
	public void agregarTurno(DBTurno turno) {
		String insertQuery = "INSERT INTO " + TABLE_PARTIDA + "(" +
				KEY_ID_PARTIDA + "," + KEY_TURNO + "," + KEY_JUGADOR + "," + KEY_COLOR + ","
				+ KEY_OPERACION_MAZO + "," + KEY_OPERACION_JUGADA + "," + KEY_VALOR + ") VALUES " + "("
				+ turno.getIdPartidaDB() + "," + turno.getTurnoDB() + ",'" + turno.getJugadorDB() + "','" + turno.getColorDB()
				+ "','" + turno.getOperacionMazoDB() + "','" + turno.getOperacionJugadaDB() + "','" + turno.getValorDB() + "')";
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL(insertQuery);
	}

	/**
	 * Método para obtener todos los turnos de la tabla
	 */
	public List<DBTurno> obtenerTodosTurnos() {
		List<DBTurno> turnoList = new ArrayList<DBTurno>();
		String selectQuery = "SELECT  * FROM " + TABLE_PARTIDA;
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			do {
				DBTurno turno = new DBTurno();
				turno.setID(Integer.parseInt(cursor.getString(0)));
				turno.setIdPartidaDB(Integer.parseInt(cursor.getString(1)));
				turno.setTurnoDB(Integer.parseInt(cursor.getString(2)));
				turno.setJugadorDB(cursor.getString(3));
				turno.setColorDB(cursor.getString(4));
				turno.setOperacionMazoDB(cursor.getString(5));
				turno.setOperacionJugadaDB(cursor.getString(6));
				turno.setValorDB(cursor.getString(7));

				turnoList.add(turno);
			} while (cursor.moveToNext());
		}

		return turnoList;
	}

	/**
	 * Método para borrar todos los turnos de la tabla
	 */
	public void borrarTodosTurnos() {
		SQLiteDatabase db= this.getWritableDatabase();
		db.delete(TABLE_PARTIDA, null, null);
		System.out.println("Base de datos borrada.");
	}

	/**
	 * Método para asignar el ultimo id de partida de la tabla
	 */
	public int asignarUltimoIdPartida() {
		int id_partidaActual = 1;
		int ultimoIdDb;
		String selectQuery = "SELECT  * FROM " + TABLE_PARTIDA;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		if(cursor!=null && cursor.getCount()!=0){
			cursor.moveToLast();
			ultimoIdDb = cursor.getInt(cursor.getColumnIndex(KEY_ID_PARTIDA));
			while(id_partidaActual <= ultimoIdDb) {
				id_partidaActual++;
			}
		}

		return id_partidaActual;
	}
}
