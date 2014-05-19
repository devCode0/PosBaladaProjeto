package br.uniararas.mobile.baladanights.presenter;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;
import br.uniararas.mobile.baladanights.models.Balada;
import br.uniararas.mobile.baladanights.models.Usuario;
import br.uniararas.mobile.baladanights.models.db.Concrete.PresencaRepositoryConcrete;
import br.uniararas.mobile.baladanights.util.Constantes;
import br.uniararas.mobile.baladanights.util.Funcoes;
import br.uniararas.mobile.baladanights.view.BaladaView;
import br.uniararas.mobile.baladanights.view.BaladaView.ComunicacaoSalvar;
import br.uniararas.mobile.baladanights.view.BaladaView.ComunicaoEuVou;



public class BaladaPresenter implements BaladaView.BaladaViewHandler{
	
	private BaladaView view;
	
	public BaladaPresenter(BaladaView view){
		this.view = view;
	}

	@Override
	public void salvarBalada(Balada balada, Usuario usuario,
			ComunicacaoSalvar back) {

		String situacao ="";

        HttpClient cliente = new DefaultHttpClient();
        HttpPost post = new HttpPost(Constantes.URL_SALVA_BALADA_POST);

        List<NameValuePair> paremetrosForm = new ArrayList<NameValuePair>();
        paremetrosForm.add(new BasicNameValuePair("idsessao", "22"));
        paremetrosForm.add(new BasicNameValuePair("nome", balada.getNome()));
        paremetrosForm.add(new BasicNameValuePair("descricao", balada.getDescricao()));
        paremetrosForm.add(new BasicNameValuePair("local", balada.getLocal()));
        paremetrosForm.add(new BasicNameValuePair("data", balada.getData()));
        paremetrosForm.add(new BasicNameValuePair("inicio", balada.getHora()));
        paremetrosForm.add(new BasicNameValuePair("open", balada.getOpen()));        

        try{
        	post.setEntity(new UrlEncodedFormEntity(paremetrosForm,"utf-8"));
        	HttpResponse resposta = cliente.execute(post);

        	InputStream dadosResposta = resposta.getEntity().getContent();
        	String respostaJSON =  Funcoes.lerResposta(dadosResposta);;
        	situacao = converteParaSalvar(respostaJSON);

        	//            StatusLine status = resposta.getStatusLine();
        	//
        	//            if(status.getStatusCode() == HttpStatus.SC_OK){
        	//                //return balada;
        	//            }
        }catch (UnsupportedEncodingException e) {
        	e.printStackTrace();
        }catch (ClientProtocolException e){
        	e.printStackTrace();
        }catch (Exception e){
        	e.printStackTrace();
        }

        if(situacao.equals("0")){
        	back.finalizaBaladaSalvar(BaladaView.ComunicacaoSalvar.SALVA_OK);  
        }

        else if(situacao.equals("1")){
        	back.finalizaBaladaSalvar(BaladaView.ComunicacaoSalvar.SALVA_FALHA);
        }

        else if(situacao.equals("2")){
        	back.finalizaBaladaSalvar(BaladaView.ComunicacaoSalvar.SALVA_BALADA_JA_EXISTENTE);
        }
    }
    
    private String converteParaSalvar(String respostaJson)
    {
    	String situacao ="";

        //Leitura do Json Conversão para objetos
        try {
            JSONObject colecao = new JSONObject(respostaJson);
            
            JSONObject objetoJsonAtual;

                objetoJsonAtual = colecao;
                situacao = objetoJsonAtual.getString("retorno");
                
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
        return situacao;
    
    }

	@Override
	public List<Balada> ExibirBaldas(Usuario usuario) {
		//Criar cleinte http para poder se comunicar com o servido
        HttpClient clienteHttp = new DefaultHttpClient();

        HttpGet get = new HttpGet(Constantes.URL_LISTA_BALADAS_GET+usuario.getIdSessao());

        List<Balada> baladas = new ArrayList<Balada>();

        try {
            //Mandar o dados no servidor apra recuperar baladas
            HttpResponse resposta =  clienteHttp.execute(get);
            //resposta.getEntity().getContent();

            //Guarda uma referência para o conteudo de resposta do HTTP enviada pelo servidor.
            InputStream dadosResposta = resposta.getEntity().getContent();
            String respostaJSON = Funcoes.lerResposta(dadosResposta);
            baladas = converteParaBalada(respostaJSON);

        } catch (IOException e) {
            e.printStackTrace();
        }catch (Exception e){
            Log.e("Erro","Erro");
            e.printStackTrace();
        }

        return baladas;
	}
	
    private List<Balada> converteParaBalada(String respostaJson){

        List<Balada> retorna = new ArrayList<Balada>();

        //Leitura do Json Conversão para objetos
        try {
            JSONArray colecao = new JSONArray(respostaJson);
            Balada balada = null;
            JSONObject objetoJsonAtual;

            //Processando baladas uma a uma
            for (int pos = 0; pos < colecao.length(); pos++) {

                objetoJsonAtual = colecao.getJSONObject(pos);

                balada = new Balada();
                balada.setBaladaId(objetoJsonAtual.getString("baladaId"));
                balada.setNome(objetoJsonAtual.getString("nome"));
                balada.setDescricao(objetoJsonAtual.getString("descricao"));
                balada.setLocal(objetoJsonAtual.getString("local"));
                balada.setData(objetoJsonAtual.getString("data"));
                balada.setHora(objetoJsonAtual.getString("inicio"));
                balada.setOpen(objetoJsonAtual.getString("open"));
                
                retorna.add(balada);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return retorna;
    }

	@Override
	public void EuVouBalada(Balada balada, Usuario usuario,  Context context, ComunicaoEuVou back) {
		String situacao ="";

    	HttpClient cliente = new DefaultHttpClient();
    	HttpPost post = new HttpPost("http://poswebservice.azurewebsites.net/Service1.asmx/EuVou");

    	List<NameValuePair> paremetrosForm = new ArrayList<NameValuePair>();
    	paremetrosForm.add(new BasicNameValuePair("idsessao", String.valueOf(usuario.getIdSessao())));
    	paremetrosForm.add(new BasicNameValuePair("username", usuario.getUserName()));
    	paremetrosForm.add(new BasicNameValuePair("baladaId", balada.getBaladaId()));

    	try{
    		post.setEntity(new UrlEncodedFormEntity(paremetrosForm,"utf-8"));
    		HttpResponse resposta = cliente.execute(post);

    		InputStream dadosResposta = resposta.getEntity().getContent();
    		String respostaJSON = Funcoes.lerResposta(dadosResposta);;
    		situacao = converteParaSalvar(respostaJSON);

    	}catch (UnsupportedEncodingException e) {
    		e.printStackTrace();
    	}catch (ClientProtocolException e){
    		e.printStackTrace();
    	}catch (Exception e){
    		e.printStackTrace();;
    	}
    	
    	if(situacao.equals("0")){
        	back.finalizaEuVouParaBalada(BaladaView.ComunicaoEuVou.EU_VOU_OK); 
        	PresencaRepositoryConcrete repository = new PresencaRepositoryConcrete(context);
			if(repository.salvar(usuario, balada)){
				//MensagemUtil.mostraMensagem(getActivity(), "Gravou no Banco");
			}	
        }

        else if(situacao.equals("1")){
        	back.finalizaEuVouParaBalada(BaladaView.ComunicaoEuVou.EU_VOU_FALHA);
        }
	
	}

	@Override
	public List<Balada> ListaPresencaBaladaBancoDados(Usuario usuario,
			Context context) {

		List<Balada> baladas = new ArrayList<>();
		
		PresencaRepositoryConcrete repository = new PresencaRepositoryConcrete(context);
		baladas = repository.listar(usuario);
		
		return baladas;
	}
		
}
