package br.uniararas.mobile.baladanights.fragments;

import br.uniararas.mobile.baladanights.R;
import br.uniararas.mobile.baladanights.R.drawable;
import br.uniararas.mobile.baladanights.R.id;
import br.uniararas.mobile.baladanights.R.layout;
import br.uniararas.mobile.baladanights.models.Balada;
import br.uniararas.mobile.baladanights.models.Usuario;
import br.uniararas.mobile.baladanights.presenter.BaladaPresenter;
import br.uniararas.mobile.baladanights.util.HttpUtil;
import br.uniararas.mobile.baladanights.util.MensagemUtil;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;
import br.uniararas.mobile.baladanights.view.BaladaView;
import br.uniararas.mobile.baladanights.view.BaladaView.BaladaViewHandler;;

@SuppressLint("ValidFragment")
public class FragmentCadastroBalada extends Fragment implements BaladaView {
	
	
	private BaladaViewHandler viewHandler;
	
	private Button btnSalvar, btnCancelar;
	private EditText edtNome, edtDescricao, edtLocal, edtData, edtHora;
	private Switch swtOpen;
	
	private Balada balada;
	
	private AlertDialog alerta;
	
	private Usuario usuario;
	private View root;
	
	private String resposta="";
	
	public FragmentCadastroBalada(Usuario usuario){
		this.usuario = usuario;
	}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        //ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_home, null);
  
    	View root = (View) inflater.inflate(R.layout.fragment_cadastro_balada, null);
    	this.root = root;
    	getActivity().setTitle("Cadastra - Balada Nights");
    	
    	balada = new Balada();    	   	
        
        ComponentesAcoesTela();
        
        //Injetando
        definirBaladaViewHandler(new BaladaPresenter(this));
        
    	return root;
	}	
	
	private class LoadingAsync extends AsyncTask<Void, Void, Void>{

		ProgressDialog progress = new ProgressDialog(getActivity());
		
		@Override
		protected void onPreExecute() {
			progress.setTitle("Salvar");
			progress.setIcon(R.drawable.info);
			progress.setMessage("Salvando Balada: "+balada.getNome()+" ....");
			progress.show();
		}
		
		@Override
		protected Void doInBackground(Void... params) {
			getViewHandler().salvarBalada(balada, usuario, new ComunicacaoSalvar() {
				
				@Override
				public void finalizaBaladaSalvar(String status) {
						resposta = status;
				}
			});
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			progress.dismiss();
			
			if(resposta == ComunicacaoSalvar.SALVA_OK){
				AlertaSucesso();
			}else if(resposta == ComunicacaoSalvar.SALVA_FALHA){
				MensagemUtil.mostraMensagem(getActivity(), "Falha");
			}else if(resposta == ComunicacaoSalvar.SALVA_BALADA_JA_EXISTENTE){
				MensagemUtil.mostrarMensagemNeutra(getActivity(), 
						"Falha!", "Não foi possivel cadastrar, a Balada como nome: "+balada.getNome()+" já existe!", R.drawable.info);
			}
		}
		
	}
	
	public BaladaViewHandler getViewHandler(){
		return viewHandler;
	}
	
	@Override
	public void definirBaladaViewHandler(BaladaViewHandler viewHandler) {
		this.viewHandler = viewHandler;	
	}
	
	public void ComponentesAcoesTela(){
		btnCancelar = (Button) root.findViewById(R.id.cadastro_balada_btnCancelar);
		btnSalvar = (Button) root.findViewById(R.id.cadastro_balada_btnSalvar);


		edtNome = (EditText) root.findViewById(R.id.cadastro_balada_editNome);
		edtDescricao = (EditText) root.findViewById(R.id.cadastro_balada_editDescricao);
		edtLocal = (EditText) root.findViewById(R.id.cadastro_balada_editLocal);
		edtData = (EditText) root.findViewById(R.id.cadastro_balada_editData);
		edtHora = (EditText) root.findViewById(R.id.cadastro_balada_editHora);
		swtOpen = (Switch) root.findViewById(R.id.cadastro_balada_swtOpen);


		if (swtOpen != null) {
			swtOpen.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

					if(isChecked) {
						balada.setOpen("true");
					} else {
						balada.setOpen("true");
					}

				}
			});
		}

		btnSalvar.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				balada.setNome(edtNome.getText().toString());
				balada.setDescricao(edtDescricao.getText().toString());
				balada.setLocal(edtLocal.getText().toString()); 
				balada.setData(edtData.getText().toString());
				balada.setHora(edtHora.getText().toString()); 
				if(balada.getOpen() == null || balada.getOpen() == "")
					balada.setOpen("false");
				
				if(Validacao())
					new LoadingAsync().execute();


			}
		});

		edtHora.setOnFocusChangeListener(new View.OnFocusChangeListener() {

			@Override
			public void onFocusChange(View arg0, boolean arg1) {
				if(edtHora.isFocused()){
					DialogFragment horaFragment = new TimePickerFragment(edtHora);
					horaFragment.show(getFragmentManager(), "timePicker");
				}	
			}
		});


		edtData.setOnFocusChangeListener(new View.OnFocusChangeListener() {

			@Override
			public void onFocusChange(View arg0, boolean arg1) {
				if(edtData.isFocused()){
					DialogFragment dataFragment = new DatePickerFragment(edtData);
					dataFragment.show(getFragmentManager(), "datePicker");
				}	
			}
		});
		
		btnCancelar.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				MensagemUtil.mostrarMensagemConfirmaNega(getActivity(), "Cancelamento", "Deseja cancelar o cadastramento e retornar para Home?", 
						R.drawable.info, new OnClickListener() {

					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						FragmentTransaction tx = getFragmentManager().beginTransaction();
						tx.replace(R.id.content_frame, new FragmentHome(getActivity(),0,usuario));
						tx.commit();

					}
				});		
			}
		});

	}
	
	private void AlertaSucesso(){
		
		MensagemUtil.mostrarMensagemConfirmacao(getActivity(), "Sucesso!", "Cadastro Efetuado Com Sucesso!", 
				R.drawable.sucesso, new OnClickListener() {
					
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						FragmentTransaction tx = getFragmentManager().beginTransaction();
		            	tx.replace(R.id.content_frame, new FragmentHome(getActivity(),0,usuario));
		                tx.commit();
						
					}
				});	
	}
	
	/**
	 * Validção dos campos da view
	 * @return
	 */
	private boolean Validacao(){
		boolean dadosValidos = true;
		
		if(edtNome.getText().toString().equals(null) || edtNome.getText().toString().equals("")){
			dadosValidos = false;
			MensagemUtil.mostraMensagem(getActivity(), "Nome da Balada é Obrigatório!");
		}else if(edtDescricao.getText().toString().equals(null)  || edtDescricao.getText().toString().equals("")){
			dadosValidos = false;
			MensagemUtil.mostraMensagem(getActivity(), "Descrição da Balada é Obrigatório!");			
		}else if(edtLocal.getText().toString().equals(null)  || edtLocal.getText().toString().equals("")){
			dadosValidos = false;
			MensagemUtil.mostraMensagem(getActivity(), "Endereço da Balada é Obrigatório!");			
		}else if(edtData.getText().toString().equals(null)  || edtData.getText().toString().equals("")){
			dadosValidos = false;
			MensagemUtil.mostraMensagem(getActivity(), "Data da Balada é Obrigatório!");		
		}else if(edtHora.getText().toString().equals(null)  || edtHora.getText().toString().equals("")){
			dadosValidos = false;
			MensagemUtil.mostraMensagem(getActivity(), "Hora de Inicio da Balada é Obrigatório!");
		}
		
		return dadosValidos;
	}

	
}
