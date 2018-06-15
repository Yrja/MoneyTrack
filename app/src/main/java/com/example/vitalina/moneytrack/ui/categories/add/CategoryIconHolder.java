package com.example.vitalina.moneytrack.ui.categories.add;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.widget.ImageView;

import com.example.vitalina.moneytrack.R;
import com.example.vitalina.moneytrack.ui.utils.WindowUtils;
import com.example.vitalina.moneytrack.ui.view.BaseHolder;
import com.squareup.picasso.Picasso;

public class CategoryIconHolder extends BaseHolder<SelectableItem<String>> {
    private ImageView vIcon;
    private ImageView vChecked;
    private OnItemClick click;
    public CategoryIconHolder(View itemView, OnItemClick onItemClick) {
        super(itemView);
        vIcon = itemView.findViewById(R.id.vIcon);
        vChecked = itemView.findViewById(R.id.vChecked);
        click = onItemClick;
    }

    @Override
    public void bind(SelectableItem<String> stringSelectableItem) {

        GridLayoutManager.LayoutParams params = (GridLayoutManager.LayoutParams) itemView.getLayoutParams();
        params.width = WindowUtils.getScreenDimension(itemView.getContext()).x/3;
        itemView.setLayoutParams(params);
        Picasso.get().load(stringSelectableItem.item).into(vIcon);
        vChecked.setVisibility(stringSelectableItem.isSelected? View.VISIBLE :View.INVISIBLE);
        itemView.setOnClickListener(v->{
            if (!stringSelectableItem.isSelected){
                vChecked.setVisibility(View.VISIBLE);
            } else {
               // vChecked.setVisibility(View.INVISIBLE);
            }
            stringSelectableItem.isSelected = true;
            click.onItemClick(getAdapterPosition());
        });
    }
}
