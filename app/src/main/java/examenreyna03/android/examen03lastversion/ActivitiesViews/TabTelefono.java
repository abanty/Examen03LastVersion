package examenreyna03.android.examen03lastversion.ActivitiesViews;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import examenreyna03.android.examen03lastversion.Models.Contacto;
import examenreyna03.android.examen03lastversion.Presentators.AdaptadorListView;
import examenreyna03.android.examen03lastversion.Presentators.DBHelper;
import examenreyna03.android.examen03lastversion.R;

public class TabTelefono  extends Fragment {

    private ListView Milista;
    private ArrayList<Contacto> ContactList;
    AdaptadorListView MiAdaptador = null;
    ImageView imagenfoto;
    public DBHelper SQLiteDB;

    @Override
    @SuppressWarnings("unchecked")
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab1_telefono, container, false);

        Milista = (ListView) rootView.findViewById(R.id.tablistacontac);
        ContactList = new ArrayList<>();
        MiAdaptador = new AdaptadorListView(getActivity(),R.layout.item_contacto, ContactList);
        Milista.setAdapter(MiAdaptador);

        SQLiteDB = new DBHelper(getActivity().getApplicationContext(), "DBCONTACTO.sqlite", null, 1);
        SQLiteDB.queryData("CREATE TABLE IF NOT EXISTS CONTACTO(id INTEGER PRIMARY KEY AUTOINCREMENT, nombre VARCHAR, telefono VARCHAR, foto BLOB)");

        Cursor cursor = SQLiteDB.getData("SELECT * FROM CONTACTO");
        ContactList.clear();
        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
//            String nombre = cursor.getString(1);
            String telefono = cursor.getString(2);
            byte[] foto  = cursor.getBlob(3);
            ContactList.add(new Contacto(id,telefono,foto));
        }

        MiAdaptador.notifyDataSetChanged();
        if (ContactList.size()==0){
            //if there is no record in table of database which means listview is empty
            Toast.makeText(getActivity(), "No hay Contactos", Toast.LENGTH_SHORT).show();
        }

        return rootView;
    }
}
