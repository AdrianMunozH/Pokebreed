package com.example.pokebreed;

import android.app.Activity;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pokebreed.Pokemon;

import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {
    private List<Pokemon> dataList;
    private Activity context;
    private ImageView btnkreuz;


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
                btnkreuz = dialog.findViewById(R.id.button_kreuz);

                dialog.getWindow().setLayout(width,height);
                dialog.show();

                TextView name = dialog.findViewById(R.id.savedPokemon);
                //Mutter
                TextView mName = dialog.findViewById(R.id.name_mutter);
                TextView mItem = dialog.findViewById(R.id.item_mutter);
                TextView mWesen = dialog.findViewById(R.id.nature_mutter);
                TextView mAtk = dialog.findViewById(R.id.attacke_mutter);
                TextView mAbl = dialog.findViewById(R.id.mutter_ability);
                //Vater

                TextView vName = dialog.findViewById(R.id.name_vater);
                TextView vItem = dialog.findViewById(R.id.item_vater);
                TextView vWesen = dialog.findViewById(R.id.nature_vater);
                TextView vAtk = dialog.findViewById(R.id.attacke_vater);
                TextView vAbl = dialog.findViewById(R.id.vater_ability);

                //DV

                TextView kp = dialog.findViewById(R.id.hp_value);
                TextView atk = dialog.findViewById(R.id.attack_value);
                TextView def = dialog.findViewById(R.id.defense_value);
                TextView spatk = dialog.findViewById(R.id.spattack_value);
                TextView spdef = dialog.findViewById(R.id.spdefense_value);
                TextView spe = dialog.findViewById(R.id.speed_value);

                name.setText(curP.getName());

                //Mutter init
                mName.setText(curP.getMother().getName());
                mItem.setText(curP.getMother().getItem());
                mWesen.setText(curP.getMother().getNature());
                mAtk.setText(curP.getMother().getMoves());
                mAbl.setText(curP.getMother().getAbility());
                //Father init
                vName.setText(curP.getFather().getName());
                vItem.setText(curP.getFather().getItem());
                vWesen.setText(curP.getFather().getNature());
                vAtk.setText(curP.getFather().getMoves());
                vAbl.setText(curP.getFather().getAbility());

                // DV init
                kp.setText(curP.getKp());
                atk.setText(curP.getAttack());
                def.setText(curP.getDefense());
                spatk.setText(curP.getSpecialAttack());
                spdef.setText(curP.getSpecialDefense());
                spe.setText(curP.getSpeed());


                btnkreuz.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
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
