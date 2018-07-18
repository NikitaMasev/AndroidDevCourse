package com.nikitamasevgmail.moneytracker.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nikitamasevgmail.moneytracker.R;
import com.nikitamasevgmail.moneytracker.data.Price;

import java.util.ArrayList;
import java.util.List;

public class PriceAdapter extends RecyclerView.Adapter<PriceAdapter.PriceViewHolder> {

    private static final String TAG = "PriceAdapter";

    private Context context;
    private List<Price> priceList;

    public PriceAdapter(Context context) {
        this.context = context;
        this.priceList = new ArrayList<>();
    }

    public void setData(List<Price> priceList) {
         this.priceList = priceList;
         notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PriceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder");
        return new PriceViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_activity_item_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PriceViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder");
        Price price = priceList.get(position);
        holder.applyData(price);
    }

    @Override
    public int getItemCount() {
        return priceList.size();
    }

    class PriceViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvTitle;
        private final TextView tvPrice;

        private PriceViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.title_item_rv);
            tvPrice = itemView.findViewById(R.id.price_item_rv);
        }

        private void applyData(Price price) {
            tvTitle.setText(price.getName());

            String strFormatted = String.format(context.getResources().getString(R.string.pattern_format_tvPrice), price.getPrice(), context.getResources().getString(R.string.rub_text_symbol));
            SpannableString finalSpannableStr = new SpannableString(strFormatted);
            finalSpannableStr.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.colorTabInactive)), String.valueOf(price.getPrice()).length() + 1,
                    String.valueOf(price.getPrice()).length() + 2, 0);

            tvPrice.setText(finalSpannableStr);
        }
    }
}
