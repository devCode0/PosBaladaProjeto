package br.uniararas.mobile.baladanights.view;

import br.uniararas.mobile.baladanights.models.Usuario;

public interface LoginView {
	
	public void definirLoginViewHandler(LoginViewHandler viewHandler);
	
	interface LoginViewHandler{
		
		void realizarLogin(Usuario usuario, LoginComunicacao back);
	}
	
	interface LoginComunicacao{
		 
		
		String USUARIO_OK = "0";
		
		String USUARIO_NOK = "1";
		
		void operacaoFinalizada(Usuario usuario, String status);
	}

}
