package br.uniararas.mobile.baladanights.fragments;

import java.util.ArrayList;
import java.util.List;

import br.uniararas.mobile.baladanights.R;
import br.uniararas.mobile.baladanights.R.id;
import br.uniararas.mobile.baladanights.R.layout;
import br.uniararas.mobile.baladanights.adpatadores.AdaptadorParaBaladas;
import br.uniararas.mobile.baladanights.models.Balada;
import br.uniararas.mobile.baladanights.models.Usuario;
import br.uniararas.mobile.baladanights.presenter.BaladaPresenter;
import br.uniararas.mobile.baladanights.util.HttpUtil;
import br.uniararas.mobile.baladanights.util.MensagemUtil;
import br.uniararas.mobile.baladanights.view.BaladaView;
import br.uniararas.mobile.baladanights.view.BaladaView.BaladaViewHandler;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

@SuppressLint("ValidFragment")
public class FragmentHome extends Fragment implements BaladaView {

	private BaladaViewHandler viewHandler;
	
	private ListView lista;
	private List<Balada> baladas;
	private AdaptadorParaBaladas adaptador;
	
	private Context contexto;
	private View root;
	
	private ImageView imagen;
	private Button btnLista, btnCadatraBalada, btnPresenca;
	
	private int busca;
	
	private Usuario usuario;
	
	
	public FragmentHome(Context context, int busca, Usuario usuario){
		this.contexto = context;
		this.busca = busca;
		this.usuario = usuario;
	}	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		//ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_home, null);

		View root = (View) inflater.inflate(R.layout.fragment_home, null);
		this.root = root;
		getActivity().setTitle("Home - Balada Nights");

		baladas = new ArrayList<Balada>();

		CompenentesAcoesTela();

		if(busca == 1){
			new LoadingAsync().execute();
		}

		definirBaladaViewHandler(new BaladaPresenter(this));

		return root;
	}
    
    public BaladaViewHandler getViewHandler(){
		return viewHandler;
	}
	
	@Override
	public void definirBaladaViewHandler(BaladaViewHandler viewHandler) {
		this.viewHandler = viewHandler;	
	}
    
    public void carregaLista(){
    	lista = (ListView) getActivity().findViewById(R.id.main_lista);
    }
    
    public void CompenentesAcoesTela(){
    	 btnLista = (Button) root.findViewById(R.id.home_btn_lista_balada);
         btnCadatraBalada = (Button) root.findViewById(R.id.home_btn_cadastra_balada);
         btnPresenca = (Button) root.findViewById(R.id.home_btn_presenca_balada);
         
         imagen = (ImageView) root.findViewById(R.id.home_imagen);
         
         
         btnPresenca.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				FragmentTransaction tx = getFragmentManager().beginTransaction();
 				tx.replace(R.id.content_frame, new FragmentPresenca(usuario));
     	        tx.commit();
				
			}
		});
         
         
         btnLista.setOnClickListener(new View.OnClickListener() {
 			
 			@Override
 			public void onClick(View v) {
 				new LoadingAsync().execute();

 			}
 		});
         
         
         
         btnCadatraBalada.setOnClickListener(new View.OnClickListener() {
 			
 			@Override
 			public void onClick(View arg0) {
 				FragmentTransaction tx = getFragmentManager().beginTransaction();
 				tx.replace(R.id.content_frame, new FragmentCadastroBalada(usuario));
     	        tx.commit();
 				
 			}
 		});
         
         
         lista = (ListView) root.findViewById(R.id.main_lista);
         lista.setOnItemClickListener(new OnItemClickListener() {

 			@Override
 			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
 					long arg3) {
 				
 				Balada bal = (Balada) adaptador.getItem(arg2);
 				
 				Toast.makeText(getActivity(), bal.getNome(), Toast.LENGTH_LONG).show();
 				
 				FragmentTransaction tx = getFragmentManager().beginTransaction();
 				tx.replace(R.id.content_frame, new FragmentEuVou(bal,usuario));
     	        tx.commit();
 				
 			}
 		});
    }
    
    private class LoadingAsync extends AsyncTask<Void, Void, Void>{

    	ProgressDialog progressDialog = new ProgressDialog(getActivity());
    	
    	@Override
    	protected void onPreExecute() {
    		progressDialog.setMessage("Carregando Baladas...");
    		progressDialog.show();
    	}
    	
		@Override
		protected Void doInBackground(Void... arg0) {
			
			try {
				
				baladas = getViewHandler().ExibirBaldas(usuario);

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			
			adaptador = new AdaptadorParaBaladas(getActivity(), baladas);
			lista.setAdapter(adaptador);	
			imagen.setVisibility(View.GONE);
			progressDialog.dismiss();
			
			MensagemUtil.mostraMensagem(getActivity(), "Clique em uma balada da lista para exibir os detalhes!");
		}
    	
    }
    
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	super.onCreate(savedInstanceState);
    	
    	
    }
    
}
