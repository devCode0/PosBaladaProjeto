package br.uniararas.mobile.baladanights.models.db.Concrete;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import br.uniararas.mobile.baladanights.models.Balada;
import br.uniararas.mobile.baladanights.models.Usuario;
import br.uniararas.mobile.baladanights.models.db.DatabaseHelper;
import br.uniararas.mobile.baladanights.models.db.Abstract.IPresencaRepository;

public class PresencaRepositoryConcrete implements IPresencaRepository {

	private Context context;
	private DatabaseHelper dbHelper;
	
	public PresencaRepositoryConcrete(Context context){
		this.context = context;
	}
	
	@Override
	public boolean salvar(Usuario usuario, Balada balada) {
		
		if (dbHelper == null) {
			dbHelper = new DatabaseHelper(context);
		}
		
		SQLiteDatabase bd = dbHelper.getWritableDatabase();
		
		ContentValues valoresParaInserir = new ContentValues();
		valoresParaInserir.put("username", usuario.getUserName());
		valoresParaInserir.put("nome", balada.getNome());
		valoresParaInserir.put("descricao", balada.getDescricao());
		valoresParaInserir.put("local", balada.getLocal());
		valoresParaInserir.put("datahora", balada.getHora());
		valoresParaInserir.put("open", balada.getOpen());
		
		long result = bd.insert("presenca", null, valoresParaInserir);
		
		return (result > 0);
	}

	@Override
	public List<Balada> listar(Usuario usuario) {
		
		Cursor cursor = null;
		List<Balada> baladas = new ArrayList<>();

		if (dbHelper == null) {
			dbHelper = new DatabaseHelper(context);
		}
	
		//Selecionando somente as baladas do usuario logado
		cursor = dbHelper.getReadableDatabase().query("presenca", null, 
				"username=?", new String[]{usuario.getUserName()}, null, null, null);
		

		
		// Gerando lista de baladas com presença pelo usuário
		while (cursor.moveToNext()) {
			
			Balada balada = new Balada();
			
			balada.setNome(cursor.getString(cursor.getColumnIndex("nome")));
			balada.setDescricao(cursor.getString(cursor.getColumnIndex("descricao")));
			balada.setLocal(cursor.getString(cursor.getColumnIndex("local")));
			balada.setData(cursor.getString(cursor.getColumnIndex("datahora")));
			balada.setHora(cursor.getString(cursor.getColumnIndex("datahora")));
			balada.setOpen(cursor.getString(cursor.getColumnIndex("open")));
			
			baladas.add(balada);
		}

		return baladas;
		
		
		

	}

}
