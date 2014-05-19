package br.uniararas.mobile.baladanights.view;

import java.util.List;

import android.content.Context;
import br.uniararas.mobile.baladanights.models.Balada;
import br.uniararas.mobile.baladanights.models.Usuario;

public interface BaladaView {
	
	public void definirBaladaViewHandler(BaladaViewHandler viewHandler);
	
	interface BaladaViewHandler{
		
		/**
		 * Salva uma balda no web service
		 * @param Balada Balada a ser salva
		 * @param usuario Usuario solicita a operação
		 * @param back Retorno resposata do servidor
		 */
		void salvarBalada(Balada balada, Usuario usuario, ComunicacaoSalvar back);
		
		
		/**
		 * Retorna um lista de baladas para exibição
		 * @param usuario
		 * @return
		 */
		List<Balada> ExibirBaldas(Usuario usuario);
		
		/**
		 * Confirma usuario na balada e adicona no banco local
		 * @param balada
		 * @param usuario
		 */
		void EuVouBalada(Balada balada, Usuario usuario, Context context, ComunicaoEuVou back);
		
		
		/**
		 * Lista presença nas baladas do banco feito aplicar os conceitos da aula
		 * @param usuario
		 * @param context
		 * @return
		 */
		List<Balada> ListaPresencaBaladaBancoDados(Usuario usuario, Context context);
		
	}
	
	interface ComunicacaoSalvar {
		
		String SALVA_OK = "0";
		String SALVA_FALHA = "1";
		String SALVA_BALADA_JA_EXISTENTE = "2";
		
		
		void finalizaBaladaSalvar(String status);
		
	}
	
	interface ComunicaoEuVou{
		
		String EU_VOU_OK = "0";
		String EU_VOU_FALHA = "1";
		
		void finalizaEuVouParaBalada(String status);
	}

}
