package com.example.pokebreed;

import android.app.Activity;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pokebreed.Pokemon;

import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {
    private List<Pokemon> dataList;
    private Activity context;

    public MainAdapter(Activity context,List<Pokemon> dataList) {
        this.context = context;
        this.dataList = dataList;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public MainAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_main,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MainAdapter.ViewHolder holder, int position) {
        final String data = dataList.get(position).getName();
        holder.textView.setText(data);
        holder.btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                dataList.remove(position);
                notifyItemRemoved(position);
                notifyItemChanged(position,dataList.size());
            }
        });
        holder.btEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // init main data
                String sText = dataList.get(holder.getAdapterPosition()).getName();
                Pokemon curP = dataList.get(holder.getAdapterPosition());
                // create dialogue
                final Dialog dialog= new Dialog(context);
                dialog.setContentView(R.layout.dialog_update);
                int width = WindowManager.LayoutParams.MATCH_PARENT;
                int height = WindowManager.LayoutParams.WRAP_CONTENT;

                dialog.getWindow().setLayout(width,height);
                dialog.show();

                //init and assing var
                //final TextView textView = dialog.findViewById(R.id.savedPokemon);
                //final TextView textView2 = dialog.findViewById(R.id.detailedHistory2);

                // set text on edit text
                //textView.setText(curP.getName());

                //textView2.setText("");
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        ImageView btEdit,btDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.text_view);
            btEdit = itemView.findViewById(R.id.bt_edit);
            btDelete = itemView.findViewById(R.id.bt_delete);

        }
    }
}
