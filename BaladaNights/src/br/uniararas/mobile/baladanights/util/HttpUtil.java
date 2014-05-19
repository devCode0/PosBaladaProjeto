
package br.uniararas.mobile.baladanights.util;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;
import br.uniararas.mobile.baladanights.models.Balada;
import br.uniararas.mobile.baladanights.models.Usuario;

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
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by PC on 26/04/2014.
 */
public class HttpUtil {

//    private static final String URL_Lista_Baladas = "http://localhost:8080/ListaBaladas";
//    //private static final String URL_Lista_Baladas = "http://poswebservice.azurewebsites.net/Service1.asmx/ListarBaladas?=idsessao";
//
//    //private static final String URL_Salva_Balada = "http://poswebservice.azurewebsites.net/Service1.asmx/SalvarBalada";
//    private static final String URL_Salva_Balada ="http://localhost:8080/ListaBaladas";
//
//    public Usuario realizarLogin(String userName, String senha){
//    	
//    	HttpClient clienteHttp = new DefaultHttpClient();
//
//        HttpGet get = new HttpGet("http://poswebservice.azurewebsites.net/Service1.asmx/RealizarLogin?username=" + userName 
//        		+"&senha=" + senha);
//
//        Usuario usuario = new Usuario();
//        
//        try {
//        	HttpResponse resposta =  clienteHttp.execute(get);
//        	
//        	InputStream dadosResposta = resposta.getEntity().getContent();
//            String respostaJSON = lerResposta(dadosResposta);
//            usuario = converteParaUsuario(respostaJSON);
//        	
//        }catch (IOException e) {
//            e.printStackTrace();
//        }catch (Exception e){
//            Log.e("Erro",e.getMessage());
//            e.printStackTrace();
//        }
//    	
//    	return usuario;
//    }
//    
//    private Usuario converteParaUsuario(String respostaJson)
//    {
//    	Usuario usuario = null;
//
//        //Leitura do Json Conversão para objetos
//        try {
//            JSONObject colecao = new JSONObject(respostaJson);
//            
//            JSONObject objetoJsonAtual;
//
//
//
//                objetoJsonAtual = colecao;
//
//                usuario = new Usuario();
//                usuario.setIdSessao(Integer.parseInt(objetoJsonAtual.getString("idSessao"))); 
//                usuario.setUserName(objetoJsonAtual.getString("userName"));
//                usuario.setNome(objetoJsonAtual.getString("nome"));
//
//                
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        
//        return usuario;
//    
//    }
//    
//    
//    public List<Balada> pesquisarBaladas(String filtro){
//
//        //Criar cleinte http para poder se comunicar com o servido
//        HttpClient clienteHttp = new DefaultHttpClient();
//
//        HttpGet get = new HttpGet("http://poswebservice.azurewebsites.net/Service1.asmx/ListarBaladas?idsessao=22");
//
//        List<Balada> baladas = new ArrayList<Balada>();
//
//        try {
//            //Mandar o dados no servidor apra recuperar baladas
//            HttpResponse resposta =  clienteHttp.execute(get);
//            //resposta.getEntity().getContent();
//
//            //Guarda uma referência para o conteudo de resposta do HTTP enviada pelo servidor.
//            InputStream dadosResposta = resposta.getEntity().getContent();
//            String respostaJSON = lerResposta(dadosResposta);
//            baladas = converteParaBalada(respostaJSON);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }catch (Exception e){
//            Log.e("Erro","Erro");
//            e.printStackTrace();
//        }
//
//        return baladas;
//    }
//
//    private List<Balada> converteParaBalada(String respostaJson){
//
//        List<Balada> retorna = new ArrayList<Balada>();
//
//        //Leitura do Json Conversão para objetos
//        try {
//            JSONArray colecao = new JSONArray(respostaJson);
//            Balada balada = null;
//            JSONObject objetoJsonAtual;
//
//            //Processando baladas uma a uma
//            for (int pos = 0; pos < colecao.length(); pos++) {
//
//                objetoJsonAtual = colecao.getJSONObject(pos);
//
//                balada = new Balada();
//                balada.baladaId = objetoJsonAtual.getString("baladaId");
//                balada.nome =  objetoJsonAtual.getString("nome");
//                balada.descricao =  objetoJsonAtual.getString("descricao");
//                
//               // balada.setLocal(objetoJsonAtual.getString("local"));
//                //balada.setInicio(objetoJsonAtual.getString("data"));
//                //balada.setFim(objetoJsonAtual.getString("inicio"));
//
//                retorna.add(balada);
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return retorna;
//    }
//
//    private String lerResposta(InputStream stream) throws IOException{
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        byte[] buffer = new byte[1024];
//        int tamanhoido = 0;
//
//        while ((tamanhoido = stream.read(buffer) ) > 0) {
//            //Passar o conteudo lido para o outiput stream
//            outputStream.write(buffer, 0, tamanhoido);
//        }
//
//        String valorTextual = new String(outputStream.toByteArray());
//        return  valorTextual;
//
//    }
//
//    public String salvarBalada(Balada balada) {
//    	
//    	String situacao ="";
//
//        HttpClient cliente = new DefaultHttpClient();
//        HttpPost post = new HttpPost("http://poswebservice.azurewebsites.net/Service1.asmx/SalvarBalada");
//
//        List<NameValuePair> paremetrosForm = new ArrayList<NameValuePair>();
//        paremetrosForm.add(new BasicNameValuePair("idsessao", "22"));
//        paremetrosForm.add(new BasicNameValuePair("nome", balada.nome));
//        paremetrosForm.add(new BasicNameValuePair("descricao", balada.descricao));
//        paremetrosForm.add(new BasicNameValuePair("local", balada.local));
//        paremetrosForm.add(new BasicNameValuePair("data", balada.data));
//        paremetrosForm.add(new BasicNameValuePair("inicio", balada.hora));
//        paremetrosForm.add(new BasicNameValuePair("open", balada.open));
//        
//
//        try{
//            post.setEntity(new UrlEncodedFormEntity(paremetrosForm,"utf-8"));
//            HttpResponse resposta = cliente.execute(post);
//
//            InputStream dadosResposta = resposta.getEntity().getContent();
//            String respostaJSON = lerResposta(dadosResposta);;
//			situacao = converteParaSalvar(respostaJSON);
//            
//            
//            StatusLine status = resposta.getStatusLine();
//
//            if(status.getStatusCode() == HttpStatus.SC_OK){
//                //return balada;
//            }
//            
//            
//
//        }catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }catch (ClientProtocolException e){
//            e.printStackTrace();
//        }catch (Exception e){
//            e.printStackTrace();;
//        }
//        return situacao;
//    }
//    
//    private String converteParaSalvar(String respostaJson)
//    {
//    	String situacao ="";
//
//        //Leitura do Json Conversão para objetos
//        try {
//            JSONObject colecao = new JSONObject(respostaJson);
//            
//            JSONObject objetoJsonAtual;
//
//                objetoJsonAtual = colecao;
//                situacao = objetoJsonAtual.getString("retorno");
//                
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        
//        return situacao;
//    
//    }
//    
//    public String EuVou(Balada balada, String idsessao, String username){
//
//    	String situacao ="";
//
//    	HttpClient cliente = new DefaultHttpClient();
//    	HttpPost post = new HttpPost("http://poswebservice.azurewebsites.net/Service1.asmx/EuVou");
//
//    	List<NameValuePair> paremetrosForm = new ArrayList<NameValuePair>();
//    	paremetrosForm.add(new BasicNameValuePair("idsessao", "22"));
//    	paremetrosForm.add(new BasicNameValuePair("username", balada.nome));
//    	paremetrosForm.add(new BasicNameValuePair("baladaId", balada.baladaId));
//
//    	try{
//    		post.setEntity(new UrlEncodedFormEntity(paremetrosForm,"utf-8"));
//    		HttpResponse resposta = cliente.execute(post);
//
//    		InputStream dadosResposta = resposta.getEntity().getContent();
//    		String respostaJSON = lerResposta(dadosResposta);;
//    		situacao = converteParaSalvar(respostaJSON);
//
//    	}catch (UnsupportedEncodingException e) {
//    		e.printStackTrace();
//    	}catch (ClientProtocolException e){
//    		e.printStackTrace();
//    	}catch (Exception e){
//    		e.printStackTrace();;
//    	}
//    	return situacao;
//
//    }
}

