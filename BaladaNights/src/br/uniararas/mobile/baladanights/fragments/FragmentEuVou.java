package br.uniararas.mobile.baladanights.fragments;

import br.uniararas.mobile.baladanights.R;
import br.uniararas.mobile.baladanights.models.Balada;
import br.uniararas.mobile.baladanights.models.Usuario;
import br.uniararas.mobile.baladanights.models.db.Concrete.PresencaRepositoryConcrete;
import br.uniararas.mobile.baladanights.presenter.BaladaPresenter;
import br.uniararas.mobile.baladanights.util.HttpUtil;
import br.uniararas.mobile.baladanights.util.MensagemUtil;
import br.uniararas.mobile.baladanights.view.BaladaView;
import br.uniararas.mobile.baladanights.view.BaladaView.BaladaViewHandler;
import br.uniararas.mobile.baladanights.view.BaladaView.ComunicacaoSalvar;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;


@SuppressLint("ValidFragment")
public class FragmentEuVou extends Fragment implements BaladaView {

	private BaladaViewHandler viewHandler;
	
	private Balada balada;
	private Button btnEuVou, btnCancelar;
	
	private TextView txvNomeResultado, txvDescricaoResultado, txvLocalResultado,
		txvDataInicioResultado;
	private Switch swtOpen;
	
	private View root;

	private Usuario usuario;
	
	private String resposta="", resp="";
	
	public FragmentEuVou(Balada balada, Usuario usuario){
		this.balada = balada;
		this.usuario = usuario;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

		View root = (View) inflater.inflate(R.layout.fragment_eu_vou, null);
		this.root = root;
		getActivity().setTitle("Eu Vou- Balada Nights");
		
		ComponentesAcoesTela();
		
		
		//Injetando
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
	
	private void ComponentesAcoesTela(){
		
		txvNomeResultado = (TextView) root.findViewById(R.id.eu_vou_txvNomeResultado);
		txvDescricaoResultado = (TextView) root.findViewById(R.id.eu_vou_txvDescricaoResultado);
		txvLocalResultado = (TextView) root.findViewById(R.id.eu_vou_txvLocalResultado);
		txvDataInicioResultado = (TextView) root.findViewById(R.id.eu_vou_txvDataInicioResultado);
		swtOpen = (Switch) root.findViewById(R.id.eu_vou_swtOpen);
		
		
		txvNomeResultado.setText(balada.getNome());
		txvDescricaoResultado.setText(balada.getDescricao());
		txvLocalResultado.setText(balada.getLocal());
		txvDataInicioResultado.setText(balada.getData());
		
		if(balada.getOpen().toUpperCase().equals(String.valueOf("true").toUpperCase()))
			swtOpen.setChecked(true);
		else
			swtOpen.setChecked(false);
		
		
		btnEuVou = (Button) root.findViewById(R.id.eu_vou_btnEuVou);
		
		btnEuVou.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				new LoadingAsync().execute();
				
			}
		});
		
		
		btnCancelar = (Button) root.findViewById(R.id.eu_vou_btnCancelar);
		btnCancelar.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				FragmentTransaction tx = getFragmentManager().beginTransaction();
				tx.replace(R.id.content_frame, new FragmentHome(getActivity(),0,usuario));
				tx.commit();
				
				MensagemUtil.mostraMensagem(getActivity(), "A menininha filhinha do papai, criada com a "
						+ "vovó não vai?");
			}
		});
	}
	
	private class LoadingAsync extends AsyncTask<Void, Void, Void>{

		ProgressDialog progress = new ProgressDialog(getActivity());
		
		@Override
		protected void onPreExecute() {
			progress.setTitle("Presença");
			progress.setIcon(R.drawable.info);
			progress.setMessage("Confirmando Presença na  Balada: "+balada.getNome()+" ....");
			progress.show();
		}
		
		@Override
		protected Void doInBackground(Void... params) {
			getViewHandler().EuVouBalada(balada, usuario, getActivity(), new ComunicaoEuVou() {
				
				@Override
				public void finalizaEuVouParaBalada(String status) {
					resposta = status;
				}
			});
			
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			progress.dismiss();
					
			if(resposta == ComunicaoEuVou.EU_VOU_OK){
				AlertaSucesso();
			}
			else
				MensagemUtil.mostrarMensagemNeutra(getActivity(), "Falha", "Não foi possivel confimar a presença", 
						R.drawable.falha);
		}
		
	}
	
	private void AlertaSucesso(){
		
		MensagemUtil.mostrarMensagemConfirmacao(getActivity(), "Sucesso", "Presença Confirma separa sua Roupa e Curta!", 
				R.drawable.sucesso, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						FragmentTransaction tx = getFragmentManager().beginTransaction();
		            	tx.replace(R.id.content_frame, new FragmentHome(getActivity(),0,usuario));
		                tx.commit();
						
					}
				});
	}
}
