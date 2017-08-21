package com.example.administrator.demo.recycleView;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.administrator.demo.R;

import java.util.List;

/**
 * 作者：LSH on 2016/12/2 0002 16:19
 */
public class RvAdapter extends RecyclerView.Adapter {

    private LayoutInflater layoutInflater;
    private Context context;
    private List list;
    private ItemClickListener itemClickListener;

    public RvAdapter(Context context, List list) {
        this.list = list;
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
    }

    public interface ItemClickListener {
        void onItemClick(int Pos);
        void OnItemLongClick(int Pos);
    }


    public void setOnclicListener(ItemClickListener onclicListener) {
        this.itemClickListener = onclicListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        MyViewHolder myViewHolder = new MyViewHolder(layoutInflater.inflate(R.layout.item_rv, parent, false));
        return myViewHolder;
    }

    //点击增加item
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        ((MyViewHolder) holder).tv.setText(list.get(position).toString());

        ((MyViewHolder) holder).tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClickListener != null) {
                    itemClickListener.onItemClick(position);
                }
//                addData(holder.getAdapterPosition());
            }
        });

        //长按删除item
        ((MyViewHolder) holder).tv.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
//                removeData(holder.getAdapterPosition());
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addData(int position) {
        list.add(position, "Insert");
        notifyItemInserted(position);
    }

    public void removeData(int position) {
        list.remove(position);
        notifyItemRemoved(position);
    }

    //创建一个自定义的viewholder
    static class MyViewHolder extends RecyclerView.ViewHolder {
        Button tv;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv = (Button) itemView.findViewById(R.id.tv);
        }
    }
}
