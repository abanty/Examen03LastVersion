package examenreyna03.android.examen03lastversion.ActivitiesViews;

/*EXAMEN 03 APLICACIONES MOVILES - APP GESTION DE CONTACTOS*/


import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import examenreyna03.android.examen03lastversion.Models.Contacto;
import examenreyna03.android.examen03lastversion.Presentators.AdaptadorListView;
import examenreyna03.android.examen03lastversion.Presentators.DBHelper;
import examenreyna03.android.examen03lastversion.R;

import static android.app.Activity.RESULT_OK;


public class TabContactos extends Fragment {


    private TextView call;

    private ListView Milista;
    private ArrayList<Contacto> ContactList;
    AdaptadorListView MiAdaptador = null;
    ImageView imagenfoto;
    public DBHelper SQLiteDB;
    protected View mView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootcontactview = inflater.inflate(R.layout.tab2_contactos, container, false);

        Milista = (ListView) rootcontactview.findViewById(R.id.tablistacontac);
        call =(TextView) rootcontactview.findViewById(R.id.getdatacell);
        ContactList = new ArrayList<>();
        MiAdaptador = new AdaptadorListView(getActivity(),R.layout.item_contacto, ContactList);
        Milista.setAdapter(MiAdaptador);

        call.setText("991611444");


        SQLiteDB = new DBHelper(getActivity(), "DBCONTACTO.sqlite", null, 1);
        SQLiteDB.queryData("CREATE TABLE IF NOT EXISTS CONTACTO(id INTEGER PRIMARY KEY AUTOINCREMENT, nombre VARCHAR, telefono VARCHAR, foto BLOB)");

        Cursor cursor = SQLiteDB.getData("SELECT * FROM CONTACTO");
        ContactList.clear();
        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String nombre = cursor.getString(1);
            String telefono = cursor.getString(2);
            byte[] foto  = cursor.getBlob(3);
            ContactList.add(new Contacto(id,nombre,telefono,foto));
        }

        MiAdaptador.notifyDataSetChanged();
        if (ContactList.size()==0){
            //if there is no record in table of database which means listview is empty
            Toast.makeText(getActivity(), "No hay Contactos", Toast.LENGTH_SHORT).show();
        }

        Milista.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int position, long l) {

                final CharSequence[] items = {"Hacer Llamada","Modificar","Eliminar","Favoritos"};

                AlertDialog.Builder dialog = new AlertDialog.Builder(view.getContext());

                dialog.setTitle("Elige una Opcion");
                dialog.setIcon(R.drawable.ic_dialog);

                dialog.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (i == 0){
                            String cel = call.getText().toString();
//                            Toast.makeText(getActivity(), "El numero es" + cel, Toast.LENGTH_SHORT).show();
                            Uri uri = Uri.parse("tel:" + cel);
                            Intent icall = new Intent(Intent.ACTION_CALL, uri);
                            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                return;
                            }
                            startActivity(icall);

                        }

                        if (i == 1){
                            Cursor c = InsertarDatosActivity.SQLiteDB.getData("SELECT id FROM CONTACTO");
                            ArrayList<Integer> arrID = new ArrayList<Integer>();
                            while (c.moveToNext()){
                                arrID.add(c.getInt(0));
                            }
                            showDialogUpdate(getActivity(),arrID.get(position));


                        }
                        if (i==2){
                            Cursor c = InsertarDatosActivity.SQLiteDB.getData("SELECT id FROM CONTACTO");
                            ArrayList<Integer> arrID = new ArrayList<Integer>();
                            while (c.moveToNext()){
                                arrID.add(c.getInt(0));
                            }
                            showDialogDelete(arrID.get(position));
                        }
                        if (i == 3){
                            Toast.makeText(getActivity(), "Enviando a Favoritos", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                dialog.show();
                return true;
            }
        });



        return rootcontactview;




 }





    private void showDialogDelete(final int idRecord) {
        AlertDialog.Builder dialogDelete = new AlertDialog.Builder(getActivity());
        dialogDelete.setTitle("Atencion!");
        dialogDelete.setIcon(R.drawable.ic_deletecontact);
        dialogDelete.setMessage("Estas seguro de eliminar?");
        dialogDelete.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                try {
                    InsertarDatosActivity.SQLiteDB.eliminarcontacto(idRecord);
                    Toast.makeText(getActivity(), "Contacto Eliminado", Toast.LENGTH_SHORT).show();
                }
                catch (Exception e){
                    Log.e("error", e.getMessage());
                }
                updateRecordList();
            }
        });
        dialogDelete.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        dialogDelete.show();
    }


    private void showDialogUpdate(Activity activity, final int position){
        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.contacto_dialog);
        dialog.setTitle("Modificar");

        imagenfoto = dialog.findViewById(R.id.imageViewRecord);
        final EditText edtName = dialog.findViewById(R.id.edtName);
        final EditText edtPhone = dialog.findViewById(R.id.edtPhone);
        Button btnUpdate = dialog.findViewById(R.id.btnUpdate);

        Toast.makeText(activity, ""+position, Toast.LENGTH_SHORT).show();

        int iddd = position;
        Cursor cursor = SQLiteDB.getData("SELECT * FROM CONTACTO WHERE id="+iddd);
        ContactList.clear();
        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String nombre = cursor.getString(1);
            edtName.setText(nombre);
            String telefono = cursor.getString(2);
            edtPhone.setText(telefono);
            byte[] foto  = cursor.getBlob(3);
            imagenfoto.setImageBitmap(BitmapFactory.decodeByteArray(foto, 0, foto.length));
            //add to list
            ContactList.add(new Contacto(id, nombre, telefono, foto));
        }

        //set width of dialog
        int width = (int)(activity.getResources().getDisplayMetrics().widthPixels*0.95);
        //set hieght of dialog
        int height = (int)(activity.getResources().getDisplayMetrics().heightPixels*0.7);
        dialog.getWindow().setLayout(width,height);
        dialog.show();

        //in update dialog click image view to update image
        imagenfoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //check external storage permission
                requestPermissions(
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        888
                );
            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    SQLiteDB.editarcontacto(
                            edtName.getText().toString().trim(),
                            edtPhone.getText().toString().trim(),
                            imageViewToByte(imagenfoto), position);
                    dialog.dismiss();
                    Toast.makeText(getActivity(), "Datos Modificados", Toast.LENGTH_SHORT).show();
                }
                catch (Exception error){
                    Log.e("Error al modificar", error.getMessage());
                }
                updateRecordList();
            }
        });

    }

    private void updateRecordList() {
        //get all data from sqlite
        Cursor cursor = SQLiteDB.getData("SELECT * FROM CONTACTO");
        ContactList.clear();
        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String nombre = cursor.getString(1);
            String telefono = cursor.getString(2);
            byte[] foto = cursor.getBlob(3);

            ContactList.add(new Contacto(id,nombre,telefono,foto));
        }
        MiAdaptador.notifyDataSetChanged();
    }


    public static byte[] imageViewToByte(ImageView image) {
        Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
//            hacerllamada();
//        }else{
//            Toast.makeText(this, "Permisos Denegados", Toast.LENGTH_SHORT).show();
//        }

        if (requestCode == 888){
            if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                //gallery intent
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, 888);
            }
            else {
                Toast.makeText(getActivity(), "No tienes permisos suficientes para el archivo", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 888 && resultCode == RESULT_OK){
            Uri imageUri = data.getData();
            CropImage.activity(imageUri)
                    .setGuidelines(CropImageView.Guidelines.ON) //enable image guidlines
                    .setAspectRatio(7,7)// image will be square
                    .start(getActivity());




        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result =CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK){
                Uri resultUri = result.getUri();
                //set image choosed from gallery to image view
                imagenfoto.setImageURI(resultUri);
            }
            else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){
                Exception error = result.getError();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

}
