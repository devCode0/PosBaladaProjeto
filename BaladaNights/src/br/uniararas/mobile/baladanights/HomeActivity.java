package br.uniararas.mobile.baladanights;


import br.uniararas.mobile.baladanights.fragments.FragmentCadastroBalada;
import br.uniararas.mobile.baladanights.fragments.FragmentEuVou;
import br.uniararas.mobile.baladanights.fragments.FragmentHome;
import br.uniararas.mobile.baladanights.models.Usuario;
import br.uniararas.mobile.baladanights.util.MensagemUtil;
import android.app.Activity;
import android.app.ActionBar;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;

public class HomeActivity extends FragmentActivity {

	private String[] drawerListViewItems;
    private DrawerLayout drawerLayout;
    private ListView drawerListView;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    
    private Usuario usuario;
    
    final String[] fragments ={
            "br.uniararas.mobile.baladanights.FragmentHome",
            "com.example.navigationdrawer.FragmentTwo",
            "com.example.navigationdrawer.FragmentThree"};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

//		if (savedInstanceState == null) {
//			getFragmentManager().beginTransaction()
//					.add(R.id.container, new PlaceholderFragment()).commit();
//		}
		
		usuario = (Usuario) getIntent().getExtras().getSerializable("dados");
		
		FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
        //tx.replace(R.id.content_frame,Fragment.instantiate(HomeActivity.this, fragments[0]));
		
		tx.replace(R.id.content_frame, new FragmentHome(HomeActivity.this,0,usuario));
        tx.commit();
        
        MenuEsquerdoDraw();
	}
	
	public void MenuEsquerdoDraw(){
		// Pega lista de item do xml
        drawerListViewItems = getResources().getStringArray(R.array.items);
        // pega o lista view
        drawerListView = (ListView) findViewById(R.id.left_drawer);
 
        // Seta o adpdator para list view
        drawerListView.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_listview_item, drawerListViewItems));
 
        // Pega o Icon do Aplicativo
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
 
        // Cria ActionBarDrawerToggle
        actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* Activity */
                drawerLayout,         /* DrawerLayout Obejto*/
                R.drawable.ic_drawer,  /* icon*/
                R.string.drawer_open,  /* Abre*/
                R.string.drawer_close  /* Fecha*/
                );
 
        // Seta a action bar no drawer
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
 
        
        getActionBar().setDisplayHomeAsUpEnabled(true);
 
        
        drawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
 
        drawerListView.setOnItemClickListener(new DrawerItemClickListener());
		
	}
	
	@Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
         actionBarDrawerToggle.syncState();
    }
 
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }
    
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            Toast.makeText(HomeActivity.this, ((TextView)view).getText(), Toast.LENGTH_LONG).show();
            drawerLayout.closeDrawer(drawerListView);
            
            FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
            
            switch (position) {
            case 0:

            	tx.replace(R.id.content_frame, new FragmentHome(HomeActivity.this,0,usuario));
                tx.commit();
            	break;
            case 1:
            	
    			tx.replace(R.id.content_frame, new FragmentCadastroBalada(usuario));
    	        tx.commit();
            	break;
            case 2:
            	MensagemUtil.mostrarMensagemConfirmaNega(HomeActivity.this, "Logout", "Deseja Sair?", 
        				R.drawable.info, new DialogInterface.OnClickListener() {
        					
        					@Override
        					public void onClick(DialogInterface dialog, int which) {
        						finish();	
        					}
        				});
            	break;
            default:
            	break;
            }

        }
    }
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.home, menu);
		
		//Carrega o arquivo de menu.
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menubuscar, menu);

		//Pega o Componente. 
		SearchView mSearchView = (SearchView) menu.findItem(R.id.search)
				.getActionView();
		//Define um texto de ajuda:
		mSearchView.setQueryHint(getApplicationContext().getString(R.string.procurar_barra));

		// exemplos de utilização:
		mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
			
			@Override
			public boolean onQueryTextSubmit(String query) {
				FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
		        //tx.replace(R.id.content_frame,Fragment.instantiate(HomeActivity.this, fragments[0]));
				
				tx.replace(R.id.content_frame, new FragmentHome(HomeActivity.this,1,usuario));
		        tx.commit();
				return false;
			}
			
			@Override
			public boolean onQueryTextChange(String newText) {
				// TODO Auto-generated method stub
				return false;
			}
		});
		
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
//		int id = item.getItemId();
//		if (id == R.id.action_settings) {
//			return true;
//		}
//		return super.onOptionsItemSelected(item);
		
		if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onBackPressed() {

		moveTaskToBack(true);		
		//super.onBackPressed();
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
			View rootView = inflater.inflate(R.layout.fragment_home, container,
					false);
			return rootView;
		}
	}

}
