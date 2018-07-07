package com.nikitamasevgmail.moneytracker.decorators;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.nikitamasevgmail.moneytracker.R;

public class PriceDividerItemDecorator extends DividerItemDecoration {

    public PriceDividerItemDecorator(Context context, int orientation) {
        super(context,orientation);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view);

        if (position == parent.getAdapter().getItemCount() - 1) {
            outRect.setEmpty();
        } else  {
            super.getItemOffsets(outRect,view,parent,state);
        }
    }
}
