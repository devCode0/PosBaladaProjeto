package br.uniararas.mobile.baladanights.presenter;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;
import br.uniararas.mobile.baladanights.models.Usuario;
import br.uniararas.mobile.baladanights.util.Constantes;
import br.uniararas.mobile.baladanights.util.Funcoes;
import br.uniararas.mobile.baladanights.view.LoginView;
import br.uniararas.mobile.baladanights.view.LoginView.LoginComunicacao;

public class LoginPresenter implements LoginView.LoginViewHandler {

	private LoginView view;
	
	public LoginPresenter(LoginView view){
		this.view = view;
	}
	
	@Override
	public void realizarLogin(Usuario usuario, LoginComunicacao back) {
		
		HttpClient clienteHttp = new DefaultHttpClient();

        HttpGet get = new HttpGet(Constantes.URL_REALIZA_LOGIN_GET+"username=" + usuario.getUserName() 
        		+"&senha=" + usuario.getSenha());
        
        try {
        	HttpResponse resposta =  clienteHttp.execute(get);
        	
        	InputStream dadosResposta = resposta.getEntity().getContent();
            String respostaJSON = Funcoes.lerResposta(dadosResposta);
            usuario = converteParaUsuario(respostaJSON);
        	
        }catch (IOException e) {
            e.printStackTrace();
        }catch (Exception e){
            Log.e("Erro",e.getMessage());
            e.printStackTrace();
        }
        
        if(usuario.getUserName() == "" || usuario.getUserName() == null)
        	back.operacaoFinalizada(usuario, LoginView.LoginComunicacao.USUARIO_NOK);
        else	
        	back.operacaoFinalizada(usuario, LoginView.LoginComunicacao.USUARIO_OK);
		
	}
	
	private Usuario converteParaUsuario(String respostaJson)
    {
    	Usuario usuario = null;

        //Leitura do Json Conversão para objetos
        try {
            JSONObject objetoJsonAtual = new JSONObject(respostaJson);           

                usuario = new Usuario();
                usuario.setIdSessao(Integer.parseInt(objetoJsonAtual.getString("idSessao"))); 
                usuario.setUserName(objetoJsonAtual.getString("userName"));
                usuario.setNome(objetoJsonAtual.getString("nome"));

                

        } catch (JSONException e) {
            e.printStackTrace();
        }
        
        return usuario;
    
    }

}
