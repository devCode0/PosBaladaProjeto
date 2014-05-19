package br.uniararas.mobile.baladanights.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface.OnClickListener;
import android.widget.Toast;

public class MensagemUtil {
	
	
	/**
	 * Método Cria mensagem toast
	 * @param activity
	 * @param msg
	 */
	public static void mostraMensagem(Activity activity, String msg){
		Toast.makeText(activity, msg, Toast.LENGTH_LONG).show();
	}
	
	
	/**
	 * Metodo adicionar mensagem sem ação no click
	 * @param activity
	 * @param titulo
	 * @param msg
	 * @param icone
	 */
	public static void mostrarMensagemNeutra(Activity activity, String titulo, String msg, int icone){
		AlertDialog.Builder builderDialog = new AlertDialog.Builder(activity);
		builderDialog.setTitle(titulo);
		builderDialog.setMessage(msg);
		builderDialog.setNeutralButton("OK", null);
		builderDialog.setIcon(icone);
		builderDialog.show();
	}
	
	/**
	 * Metrodo com entrada da ação do Click no OK
	 * @param activity
	 * @param titulo
	 * @param msg
	 * @param icone
	 * @param listener 
	 */
	public static void mostrarMensagemConfirmacao(Activity activity, String titulo, String msg, 
			int icone, OnClickListener listener){
		AlertDialog.Builder builderDialog = new AlertDialog.Builder(activity);
		builderDialog.setTitle(titulo);
		builderDialog.setMessage(msg);
		builderDialog.setPositiveButton("OK", listener);
		builderDialog.setIcon(icone);
		builderDialog.show();
	}
	
	/**
	 * Metodo para implemntar o click sim e não
	 * @param activity
	 * @param titulo
	 * @param msg
	 * @param icone
	 * @param listener
	 */
	public static void mostrarMensagemConfirmaNega(Activity activity, String titulo, String msg, 
			int icone, OnClickListener listener){
		AlertDialog.Builder builderDialog = new AlertDialog.Builder(activity);
		builderDialog.setTitle(titulo);
		builderDialog.setMessage(msg);
		builderDialog.setPositiveButton("Sim", listener);
		builderDialog.setNegativeButton("Não", null);
		builderDialog.setIcon(icone);
		builderDialog.show();
	}

}
