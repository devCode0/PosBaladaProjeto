package br.uniararas.mobile.baladanights;


import br.uniararas.mobile.baladanights.models.Usuario;
import br.uniararas.mobile.baladanights.presenter.LoginPresenter;
import br.uniararas.mobile.baladanights.util.HttpUtil;
import br.uniararas.mobile.baladanights.util.MensagemUtil;
import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.os.Build;
import br.uniararas.mobile.baladanights.view.LoginView.LoginViewHandler;
import br.uniararas.mobile.baladanights.view.LoginView;

public class LoginActivity extends Activity implements LoginView {

	public static final String PREF_USUARIO = "Pref";
	
	private LoginViewHandler viewHandler;
	
	private Usuario usuario;
	private EditText edtUsuario, edtSenha;
	private Button btnEntrar, btnLimpar;
	private RelativeLayout mainLayout;
	private CheckBox chkSalvarPrefUsuario;
	
	private ProgressBar bar;
	
	private String resposta="";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//Burlando Politicas de Acesso a Internet
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
		
		setContentView(R.layout.activity_login);

//		if (savedInstanceState == null) {
//			getFragmentManager().beginTransaction()
//					.add(R.id.container, new PlaceholderFragment()).commit();
//		}
		
		usuario = new Usuario();
		mainLayout = (RelativeLayout) findViewById(R.id.container);
		
		
		
		ComponenteAcoesTela();		
        	
		verificaPreferencias();
		
		//Injetando
		definirLoginViewHandler(new LoginPresenter(this));
		
		
	}
	
	@Override
    public void definirLoginViewHandler(LoginViewHandler viewHandler) {
        this.viewHandler = viewHandler;
    }

    public LoginView.LoginViewHandler getViewHandler() {
        return viewHandler;
    }
    
    @Override
    protected void onResume() {
    	verificaPreferencias();
    	edtSenha.setText("");
    	edtUsuario.setVisibility(View.VISIBLE);
		edtSenha.setVisibility(View.VISIBLE);
		btnEntrar.setVisibility(View.VISIBLE);
		btnLimpar.setVisibility(View.VISIBLE);
		chkSalvarPrefUsuario.setVisibility(View.VISIBLE);
    	super.onResume();
    }
    
    private void verificaPreferencias(){
    	//Restaura as preferencias gravadas
        SharedPreferences settings = getSharedPreferences(PREF_USUARIO, 0);
        String setTextUsuario = settings.getString("PrefUsuario", "");
        if(setTextUsuario != ""){
        	edtUsuario.setText(setTextUsuario);
        	chkSalvarPrefUsuario.setChecked(true);
        }else
        	edtUsuario.setText("");
        
    }
	
	private void ComponenteAcoesTela(){
		edtUsuario = (EditText) findViewById(R.id.login_edtUsuario);
		edtSenha = (EditText) findViewById(R.id.login_edtSenha);
		btnEntrar = (Button) findViewById(R.id.login_btnEntrar);
		btnLimpar = (Button) findViewById(R.id.login_btnLimpar);
		bar = (ProgressBar) this.findViewById(R.id.progressBar);
		chkSalvarPrefUsuario = (CheckBox) findViewById(R.id.login_chkSalvarPrefUsuario);
		
		
		btnLimpar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	edtUsuario.setText("");
            	edtSenha.setText("");
            }
        });
		
		
		btnEntrar.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {

				//Esconder o Teclado quando clica no botao Entrar
				InputMethodManager imm = (InputMethodManager)getSystemService(LoginActivity.this.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(mainLayout.getWindowToken(), 0);               			
				
				//Validação
				if(edtUsuario.getText().toString().length() == 0){
					edtUsuario.setError("Usuário em branco!");
				}else
					edtUsuario.setError(null);
				
				if(edtSenha.getText().toString().length() == 0){
					edtSenha.setError("Senha em Branco!");
				}else
					edtSenha.setError(null);
				
				if(edtUsuario.getText().toString().length() > 0 && edtSenha.getText().toString().length() > 0){
					
					//Ocultando Componentes
					edtUsuario.setVisibility(View.GONE);
					edtSenha.setVisibility(View.GONE);
					btnEntrar.setVisibility(View.GONE);
					btnLimpar.setVisibility(View.GONE);
					chkSalvarPrefUsuario.setVisibility(View.GONE);
					
					new LoadingAsync().execute();
				}

				//Temporario Fazer Login
//				usuario.setIdSessao(22);
//				usuario.setUserName("Carlos");
//				usuario.setNome("Carlos");
//				Intent homeTela = new Intent(LoginActivity.this, HomeActivity.class);
//				homeTela.putExtra("dados", usuario);
//				startActivity(homeTela);

			}
		});
	}
	
	private class LoadingAsync extends AsyncTask<Void, Void, Void>{

    	
    	@Override
    	protected void onPreExecute() {

    		bar.setVisibility(View.VISIBLE);
    	}
    	
		@Override
		protected Void doInBackground(Void... params) {
			try {				
				usuario.setUserName(edtUsuario.getText().toString());
				usuario.setSenha(edtSenha.getText().toString());
				
				getViewHandler().realizarLogin(usuario, new LoginComunicacao() {
					
					@Override
					public void operacaoFinalizada(Usuario usuario, String status) {
						if(status == LoginComunicacao.USUARIO_OK){
							
							Intent homeTela = new Intent(LoginActivity.this, HomeActivity.class);
							homeTela.putExtra("dados", usuario);
							startActivity(homeTela);
							resposta = "0";
							
						}else{
							 resposta = "1";
						}
					}
				});

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {		
			
			//Insere a preferÊncia de salvar o user
			if(chkSalvarPrefUsuario.isChecked()){
				SharedPreferences settings = getSharedPreferences(PREF_USUARIO, 0);
				SharedPreferences.Editor editor = settings.edit();
				editor.putString("PrefUsuario", edtUsuario.getText().toString());

				//Confirma a gravação dos dados
				editor.commit();
			}else{
				SharedPreferences settings = getSharedPreferences(PREF_USUARIO, 0);
				SharedPreferences.Editor editor = settings.edit();
				editor.putString("PrefUsuario", "");

				editor.commit();					
			}
			
			if(resposta == "1"){
				edtUsuario.setVisibility(View.VISIBLE);
				edtSenha.setVisibility(View.VISIBLE);
				btnEntrar.setVisibility(View.VISIBLE);
				btnLimpar.setVisibility(View.VISIBLE);
				chkSalvarPrefUsuario.setVisibility(View.VISIBLE);
				MensagemUtil.mostraMensagem(LoginActivity.this, "Usuário ou senha inválido!");
			}		
			
			bar.setVisibility(View.GONE);	
    
		}
	}   	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_login,
					container, false);
			return rootView;
		}
	}

}
