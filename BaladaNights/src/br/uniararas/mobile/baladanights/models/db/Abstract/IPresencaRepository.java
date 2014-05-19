package br.uniararas.mobile.baladanights.models.db.Abstract;

import br.uniararas.mobile.baladanights.models.Balada;
import br.uniararas.mobile.baladanights.models.Usuario;
import java.util.List;

public interface IPresencaRepository {

	boolean salvar(Usuario usuario, Balada balada);
	
	List<Balada> listar(Usuario usuario);
}
