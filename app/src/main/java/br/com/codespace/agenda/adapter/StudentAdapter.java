package br.com.codespace.agenda.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import br.com.codespace.agenda.R;
import br.com.codespace.agenda.converter.ImageConverter;
import br.com.codespace.agenda.model.Student;

import java.util.List;

/**
 * Created by gilmar on 28/03/17.
 */

public class StudentAdapter extends BaseAdapter {
    private final List<Student> students;
    private final Context context;

    public StudentAdapter(Context context, List<Student> students) {
        this.students = students;
        this.context = context;
    }

    @Override
    public int getCount() {
        return this.students.size();
    }

    @Override
    public Object getItem(int i) {
        return this.students.get(i);
    }

    @Override
    public long getItemId(int i) {
        return this.students.get(i).getId();
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        View view = convertView;

        // se a view já foi instanciada, logo não precisamos instanciá-la novamente
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(this.context);
            view = inflater.inflate(R.layout.list_item, parent, false);
        }

        TextView txtName = (TextView) view.findViewById(R.id.item_nome);
        TextView txtPhone = (TextView) view.findViewById(R.id.item_phone);
        ImageView imgPhoto = (ImageView) view.findViewById(R.id.item_photo);
        Student student = this.students.get(i);

        txtName.setText(student.getFullName());
        txtPhone.setText(student.getPhoneNumberFormatted());

        TextView txtEmail = (TextView) view.findViewById(R.id.item_email);
        if (txtEmail != null) {
            txtEmail.setText(student.getEmail());
        }

        TextView txtEndereco = (TextView) view.findViewById(R.id.item_endereco);
        if (txtEndereco != null) {
            txtEndereco.setText(student.getAddress());
        }

        if (student.getPhotoPath() != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(student.getPhotoPath());
            Bitmap thumbPhoto = ImageConverter.resizeBitmap(bitmap, imgPhoto.getHeight());
            imgPhoto.setImageBitmap(thumbPhoto);
            imgPhoto.setTag(student.getPhotoPath());
        }

        return view;
    }
}
