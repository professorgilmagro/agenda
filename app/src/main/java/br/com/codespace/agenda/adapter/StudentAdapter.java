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
    public View getView(int i, View view, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(this.context);
        View viewLayout = inflater.inflate(R.layout.list_item, parent, false);
        TextView txtName = (TextView) viewLayout.findViewById(R.id.item_nome);
        TextView txtPhone = (TextView) viewLayout.findViewById(R.id.item_phone);
        ImageView imgPhoto = (ImageView) viewLayout.findViewById(R.id.item_photo);
        Student student = this.students.get(i);

        txtName.setText(student.getFullName());
        txtPhone.setText(student.getPhoneNumber());
        if (student.getPhotoPath() != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(student.getPhotoPath());
            Bitmap thumbPhoto = Bitmap.createScaledBitmap(bitmap, 48, 48, true);
            imgPhoto.setImageBitmap(thumbPhoto);
            imgPhoto.setTag(student.getPhotoPath());
        }

        return viewLayout;
    }
}
