package br.uniararas.mobile.baladanights.fragments;

import java.util.ArrayList;
import java.util.List;

import br.uniararas.mobile.baladanights.HomeActivity;
import br.uniararas.mobile.baladanights.R;
import br.uniararas.mobile.baladanights.adpatadores.AdaptadorParaBaladas;
import br.uniararas.mobile.baladanights.models.Balada;
import br.uniararas.mobile.baladanights.models.Usuario;
import br.uniararas.mobile.baladanights.models.db.Concrete.PresencaRepositoryConcrete;
import br.uniararas.mobile.baladanights.presenter.BaladaPresenter;
import br.uniararas.mobile.baladanights.view.BaladaView;
import br.uniararas.mobile.baladanights.view.BaladaView.BaladaViewHandler;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

@SuppressLint("ValidFragment")
public class FragmentPresenca extends Fragment implements BaladaView {
	
	private BaladaViewHandler viewHandler;
	
	private View root;
	
	private List<Balada> baladas;
	private AdaptadorParaBaladas adaptador;
	
	private Button btnHome;
	private ListView ltvPresenca;
	
	private Usuario usuario;
	
	public FragmentPresenca(Usuario usuario){
		this.usuario = usuario;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

		View root = (View) inflater.inflate(R.layout.fragment_presenca, null);
		this.root = root;
		getActivity().setTitle("Presença - Balada Nights");
		
		baladas = new ArrayList<Balada>();
		
		ComponentesAcoesTela();		
		
		//Injetando
        definirBaladaViewHandler(new BaladaPresenter(this));
        
        baladas = getViewHandler().ListaPresencaBaladaBancoDados(usuario, getActivity());
        adaptador = new AdaptadorParaBaladas(getActivity(), baladas);
		ltvPresenca.setAdapter(adaptador);
		
		return root;
	}
	
	public BaladaViewHandler getViewHandler(){
		return viewHandler;
	}
	
	@Override
	public void definirBaladaViewHandler(BaladaViewHandler viewHandler) {
		this.viewHandler = viewHandler;	
	}
	
	public void ComponentesAcoesTela(){
		btnHome = (Button) root.findViewById(R.id.presenca_btnHome);
		ltvPresenca = (ListView) root.findViewById(R.id.presenca_lista);
		
		btnHome.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				FragmentTransaction tx = getFragmentManager().beginTransaction();
				tx.replace(R.id.content_frame, new FragmentHome(getActivity(),0,usuario));
                tx.commit();
				
			}
		});
	
	}

}
