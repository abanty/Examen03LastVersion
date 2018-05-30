package examenreyna03.android.examen03lastversion.Presentators;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import examenreyna03.android.examen03lastversion.ActivitiesViews.AgendaActivity;
import examenreyna03.android.examen03lastversion.ActivitiesViews.TabContactos;
import examenreyna03.android.examen03lastversion.Models.Contacto;
import examenreyna03.android.examen03lastversion.R;

public class AdaptadorListView extends BaseAdapter {
    final private int REQUEST_CALL = 1;
    private Context context;
    private int layout;
    private ArrayList<Contacto> ContactList;


    public AdaptadorListView(Context context, int layout, ArrayList<Contacto> contactList) {
        this.context = context;
        this.layout = layout;
        ContactList = contactList;
    }


    @Override
    public int getCount() {
        return ContactList.size();
    }

    @Override
    public Object getItem(int position) {
        return ContactList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    private class ViewHolder {
        ImageView imgfot;
        TextView txtnom, txtcel;
        ImageView imgcall;


    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View viewcontact = convertView;
        ViewHolder holder = new ViewHolder();


        if (viewcontact == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            viewcontact = inflater.inflate(R.layout.item_contacto, parent, false);
            holder.txtnom = viewcontact.findViewById(R.id.txtnombre);
            holder.txtcel = viewcontact.findViewById(R.id.txtcelular);
            holder.imgfot = viewcontact.findViewById(R.id.imgfoto);
            holder.imgcall = viewcontact.findViewById(R.id.imgllamar);


            final ViewHolder getdatocelular = holder;
            holder.imgcall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String celular = getdatocelular.txtcel.getText().toString();
                    Uri uri = Uri.parse("tel:" + celular);
                    Intent i = new Intent(Intent.ACTION_CALL, uri);
                    if (ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    context.startActivity(i);
                }
            });

            viewcontact.setTag(holder);
        }
        else {
            holder = (ViewHolder)viewcontact.getTag();
        }

        Contacto modelocontacto = ContactList.get(position) ;

        holder.txtnom.setText(modelocontacto.getNombre());
        holder.txtcel.setText(modelocontacto.getTelefono());


        byte[] recordImage = modelocontacto.getFoto();
        Bitmap bitmap = BitmapFactory.decodeByteArray(recordImage, 0, recordImage.length);

        holder.imgfot.setImageBitmap(bitmap);

        return viewcontact;
    }




}
