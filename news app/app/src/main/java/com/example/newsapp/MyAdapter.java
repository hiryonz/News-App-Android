package com.example.newsapp;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class MyAdapter  extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {


    List<DataArticle> item;

    public MyAdapter(List<DataArticle> item) {
        this.item = item;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return  new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.news_cardview,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        DataArticle article = item.get(position);
        holder.titulo.setText(article.getTitle());
        holder.resumen.setText(article.getSource_id());
        if(article.getImageURL() != null) {
            Picasso.get().load(article.getImageURL())
                    .into(holder.imagen);
        }else{
            holder.imagen.setImageResource(R.drawable.no_image_icon);
        }


        holder.itemView.setOnClickListener(x -> {
            if(article.getLink() == null) {return;}
            Intent intent = new Intent(x.getContext(), Pagina_News.class);
            intent.putExtra("url", article.getLink());
            x.getContext().startActivity(intent);
        });
    }

    void UpdateData(List<DataArticle> data) {
        item.clear();
        item.addAll(data);
    }

    @Override
    public int getItemCount() {
        return item.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imagen;
        TextView titulo, resumen;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.imagen = itemView.findViewById(R.id.imageNews);
            this.titulo = itemView.findViewById(R.id.tituloNoticias);
            this.resumen = itemView.findViewById(R.id.resumenNoticias);

        }
    }

}

