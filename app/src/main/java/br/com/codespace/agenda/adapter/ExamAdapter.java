package br.com.codespace.agenda.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import br.com.codespace.agenda.R;
import br.com.codespace.agenda.model.Exam;

/**
 * Created by gilma on 01/04/2017.
 */

public class ExamAdapter extends BaseAdapter {
    private Context context;
    private List<Exam> exams;

    public ExamAdapter(Context context, List<Exam> exams) {
        this.context = context;
        this.exams = exams;
    }

    @Override
    public int getCount() {
        return this.exams.size();
    }

    @Override
    public Object getItem(int position) {
        return this.exams.get(position);
    }

    @Override
    public long getItemId(int position) {
        return this.exams.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.list_materia_item, parent, false);
        }

        TextView txtMatter = (TextView) view.findViewById(R.id.item_materia);
        TextView txtNumTopics = (TextView) view.findViewById(R.id.item_num_topicos);

        Exam exam = this.exams.get(position);
        txtMatter.setText(exam.getMatter());
        txtNumTopics.setText(Integer.toString(exam.getTopics().size()));

        return view;
    }
}
