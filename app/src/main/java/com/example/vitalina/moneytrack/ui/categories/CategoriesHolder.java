package com.example.vitalina.moneytrack.ui.categories;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vitalina.moneytrack.R;
import com.example.vitalina.moneytrack.model.entities.Categorie;
import com.example.vitalina.moneytrack.ui.categories.add.AddCategoryActivity;
import com.example.vitalina.moneytrack.ui.categories.detail.CategorieDetailActivity;
import com.example.vitalina.moneytrack.ui.utils.WindowUtils;
import com.example.vitalina.moneytrack.ui.view.BaseHolder;
import com.squareup.picasso.Picasso;

public class CategoriesHolder extends BaseHolder<Categorie> {

    private ImageView vCategoryIcon;
    private TextView vCategoryTitle, vCategoryFreemoney;
    private FloatingActionButton vAdd;
    private int type = -1;

    public CategoriesHolder(View itemView, int type) {
        super(itemView);
        this.type = type;
        GridLayoutManager.LayoutParams params = (GridLayoutManager.LayoutParams) itemView.getLayoutParams();
        params.width = WindowUtils.getScreenDimension(itemView.getContext()).x / 3;
        itemView.setLayoutParams(params);
        if (type == CategoriesAdapter.ITEM) {
            vCategoryFreemoney = itemView.findViewById(R.id.vCategoryFreeMoney);
            vCategoryTitle = itemView.findViewById(R.id.vCategorieTitle);
            vCategoryIcon = itemView.findViewById(R.id.vCategorieIcon);
        } else {
            vAdd = itemView.findViewById(R.id.vAdd);
        }
    }

    @Override
    public void bind(Categorie categorie) {
        if (type == CategoriesAdapter.ITEM) {
            if (categorie.getIcon() != null && !categorie.getIcon().isEmpty()) {
                Picasso.get().load(categorie.getIcon()).into(vCategoryIcon);
            }
            vCategoryTitle.setText(categorie.getName());
            vCategoryFreemoney.setText(categorie.getFreeMoney() + "");
            if (categorie.getFreeMoney() < 0) {
                vCategoryFreemoney.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.colorError));
            } else {
                vCategoryFreemoney.setTextColor(ContextCompat.getColor(itemView.getContext(),
                        android.R.color.primary_text_dark));
            }
            itemView.setOnClickListener(v -> {
                Intent intent = new Intent(itemView.getContext(), CategorieDetailActivity.class);
                intent.putExtra("categorie", categorie);
                itemView.getContext().startActivity(intent);
            });
        } else {
            vAdd.setOnClickListener((v)->{
                Intent intent = new Intent(itemView.getContext(), AddCategoryActivity.class);
                itemView.getContext().startActivity(intent);
            });
        }


    }
}
