package com.example.vitalina.moneytrack.ui.categories.add;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.view.View;

import com.example.vitalina.moneytrack.R;
import com.example.vitalina.moneytrack.ui.view.BaseHolder;

public class CategoryIconHolder extends BaseHolder<SelectableItem<String>> {
    public CategoryIconHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void bind(SelectableItem<String> stringSelectableItem) {
        itemView.setOnClickListener(v->{
            if (stringSelectableItem.isSelected){
                ((CardView)itemView).setCardBackgroundColor(ContextCompat.getColor(itemView.getContext(),
                        R.color.colorSelected));
            } else {
                ((CardView)itemView).setCardBackgroundColor(ContextCompat.getColor(itemView.getContext(),
                                R.color.cardview_dark_background));
            }
            stringSelectableItem.isSelected = !stringSelectableItem.isSelected;
        });
    }
}
