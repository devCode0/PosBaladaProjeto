package br.uniararas.mobile.baladanights.models.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

	private static final int VERSAO_DB = 1;

	private static final String DB_NAME = "uniararasdb";
	private static final String TABELA_PRESENCA = "presenca";		
	
	private final String CRIA_TABELA_PRESENCA =
			"create table " + TABELA_PRESENCA + "(username text, nome text, descricao "
					+ "text, local text, datahora text, open text)";

	
	public DatabaseHelper(Context context) {
		super(context, DB_NAME, null, VERSAO_DB);
	}
	
	@Override
	public void onCreate(SQLiteDatabase arg0) {
		arg0.execSQL(CRIA_TABELA_PRESENCA);

	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// Drop older books table if existed
        arg0.execSQL("DROP TABLE IF EXISTS"+TABELA_PRESENCA);
		
		onCreate(arg0);

	}

}
