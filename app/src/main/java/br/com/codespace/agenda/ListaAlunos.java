package br.com.codespace.agenda;

import android.app.ListActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Browser;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import br.com.codespace.agenda.dao.StudentDAO;
import br.com.codespace.agenda.model.Student;

public class ListaAlunos extends AppCompatActivity {
    final static String EXTRA_ACTION_NEW = "new";
    final static String EXTRA_ACTION_EDIT = "edit";
    private ListView listaAlunos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_alunos);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listaAlunos = (ListView) findViewById(R.id.lista_alunos);

        this.addCreateEvent();
        this.addEditEvent();

        registerForContextMenu(listaAlunos);
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.fillListView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_lista_alunos, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Preenche a lista de alunos
     */
    private void fillListView() {
        StudentDAO dao = new StudentDAO(this);
        List<Student> students = dao.getAll();
        ArrayAdapter adapter = new ArrayAdapter<Student>(this, android.R.layout.simple_list_item_1, students);
        listaAlunos.setAdapter(adapter);
        dao.close();
    }

    /**
     * Adiciona o envento ao botão para adicionar novos itens
     */
    private void addCreateEvent()
    {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(ListaAlunos.this, FormularioActivity.class);
                it.putExtra("EXTRA_ACTION", EXTRA_ACTION_NEW);
                startActivity(it);
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, final ContextMenu.ContextMenuInfo menuInfo) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        final Student student = (Student) listaAlunos.getItemAtPosition(info.position);


        String website = student.getWebsite();
        if (!website.isEmpty()) {
            MenuItem menuSite = menu.add("Visitar site");
            Intent itSite = new Intent(Intent.ACTION_VIEW);
            if (!website.startsWith("http")) {
                website = "http://" + website;
            }
            itSite.setData(Uri.parse(website));
            menuSite.setIntent(itSite);
        }

        if (!(student.getPhoneNumber().isEmpty())) {
            MenuItem menuSMS = menu.add("Enviar SMS");
            Intent itSMS = new Intent(Intent.ACTION_VIEW);
            itSMS.setData(Uri.parse(String.format("sms::%s", student.getPhoneNumber())));
            menuSMS.setIntent(itSMS);
        }

        MenuItem menuMapa = menu.add("Ver no mapa");
        Intent itMap = new Intent(Intent.ACTION_VIEW);
        itMap.setData(Uri.parse(String.format("geo:0,0?q=%s",student.getAddress())));
        menuMapa.setIntent(itMap);

        MenuItem menuDel = menu.add("Excluir");
        menuDel.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                StudentDAO dao = new StudentDAO(ListaAlunos.this);
                dao.delete(student);
                dao.close();
                fillListView();
                Toast.makeText(ListaAlunos.this, "Aluno removido com sucesso", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }

    /**
     * Adiciona o evento para permitir a edição do aluno
     */
    private void addEditEvent()
    {
        listaAlunos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> listView, View item, int position, long id) {
                Intent it = new Intent(ListaAlunos.this, FormularioActivity.class);
                it.putExtra("EXTRA_ACTION", EXTRA_ACTION_EDIT);
                Student student = (Student) listView.getItemAtPosition(position);
                it.putExtra("EXTRA_ITEM", student);
                startActivity(it);
            }
        });
    }
}
