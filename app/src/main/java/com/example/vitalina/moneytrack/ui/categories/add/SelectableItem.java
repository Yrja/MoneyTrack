package com.example.vitalina.moneytrack.ui.categories.add;

public class SelectableItem<T> {
    protected T item;
    protected boolean isSelected;

    public SelectableItem(T item, boolean isSelected) {
        this.item = item;
        this.isSelected = isSelected;
    }

    public T getItem() {
        return item;
    }

    public void setItem(T item) {
        this.item = item;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
