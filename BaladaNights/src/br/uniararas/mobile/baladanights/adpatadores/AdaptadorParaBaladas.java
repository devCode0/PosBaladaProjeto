package br.uniararas.mobile.baladanights.adpatadores;

import java.util.List;

import br.uniararas.mobile.baladanights.R;
import br.uniararas.mobile.baladanights.R.id;
import br.uniararas.mobile.baladanights.R.layout;
import br.uniararas.mobile.baladanights.models.Balada;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class AdaptadorParaBaladas extends BaseAdapter{

	private Context context;
    private List<Balada> baladas;

    public AdaptadorParaBaladas(Context context, List<Balada> baladas) {
        this.context = context;
        this.baladas = baladas;
    }
	
	@Override
	public int getCount() {
		return baladas.size();
	}

	@Override
	public Object getItem(int arg0) {
		return baladas.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {

		//Pare recuperar o layoute, usaremos os servicos da plataforma
        //Para recuperar qualquer servico da plataforma e preciso que tenhamos acesso a algum contexto (Context)

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        
        View layout = inflater.inflate(R.layout.linha_balada, null);
        
        TextView nome = (TextView) layout.findViewById(R.id.linha_balada_nome);
        TextView descricao = (TextView) layout.findViewById(R.id.linha_balada_local);
        TextView inicio = (TextView) layout.findViewById(R.id.linha_balada_inicio);
        TextView fim = (TextView) layout.findViewById(R.id.linha_balada_fim);
        
        Balada baladaAtual = baladas.get(arg0);
        
        
      //escrevendo os valores em cada item da linha emq questão

        
        nome.setText(baladaAtual.getNome());
        descricao.setText(baladaAtual.getDescricao());
        inicio.setText(baladaAtual.getData());
        fim.setText(baladaAtual.getHora());
        
        return layout;

	}
	
	

}
