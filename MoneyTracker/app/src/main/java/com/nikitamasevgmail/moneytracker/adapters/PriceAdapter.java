package com.nikitamasevgmail.moneytracker.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;

import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nikitamasevgmail.moneytracker.listeners.PriceAdapterListener;
import com.nikitamasevgmail.moneytracker.R;
import com.nikitamasevgmail.moneytracker.data.Price;

import java.util.ArrayList;
import java.util.List;

public class PriceAdapter extends RecyclerView.Adapter<PriceAdapter.PriceViewHolder> {

    private static final String TAG = "PriceAdapter";

    private Context context;
    private List<Price> priceList;
    private PriceAdapterListener priceAdapterListener;

    private SparseBooleanArray selections;

    public PriceAdapter(Context context) {
        this.context = context;
        this.priceList = new ArrayList<>();
        this.selections = new SparseBooleanArray();
    }

    public void setData(List<Price> priceList) {
        this.priceList = priceList;
        notifyDataSetChanged();
    }

    public void setPriceAdapterListener(PriceAdapterListener priceAdapterListener) {
        this.priceAdapterListener = priceAdapterListener;
    }

    public void toggleSelection(int pos) {
        if (selections.get(pos, false)) {
            selections.delete(pos);
        } else {
            selections.put(pos,true);
        }
        notifyItemChanged(pos);
    }

    public void clearSelections() {
        selections.clear();
    }

    public int getSelectedPriceSize() {
        return selections.size();
    }

    public List<Integer> getSelectedPrices() {
        List<Integer> prices = new ArrayList<>();

        for (int i = 0; i < selections.size(); i++) {
            prices.add(selections.keyAt(i));
        }
        return prices;
    }

    public Price remove(int pos) {
        Price price = priceList.remove(pos);
        notifyItemRemoved(pos);
        notifyDataSetChanged();
        return price;
    }

    @NonNull
    @Override
    public PriceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PriceViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_activity_item_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PriceViewHolder holder, int position) {
        Price price = priceList.get(position);
        holder.bind(price, position, priceAdapterListener, selections.get(position,false));
    }

    @Override
    public int getItemCount() {
        return priceList.size();
    }

    public void addPrice(Price price) {
        priceList.add(price);
        notifyItemChanged(priceList.size());
    }

    class PriceViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvTitle;
        private final TextView tvPrice;

        private PriceViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.title_item_rv);
            tvPrice = itemView.findViewById(R.id.price_item_rv);
        }

        private String getSpannableFormattedString(Price price) {
            String strFormatted = String.format(context.getResources().getString(R.string.pattern_format_tvPrice), price.getPrice(), context.getResources().getString(R.string.rub_text_symbol));
            SpannableString finalSpannableStr = new SpannableString(strFormatted);
            finalSpannableStr.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.colorTabInactive)), String.valueOf(price.getPrice()).length() + 1, String.valueOf(price.getPrice()).length() + 2, 0);
            return finalSpannableStr.toString();
        }

        private void bind(final Price price, final int position, final PriceAdapterListener priceAdapterListener, boolean selected) {
            tvTitle.setText(price.getName());

            tvPrice.setText(getSpannableFormattedString(price));

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (priceAdapterListener != null) {
                        priceAdapterListener.onPriceClick(price,position);
                    }
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if (priceAdapterListener != null) {
                        priceAdapterListener.onPriceLongClick(price,position);
                    }
                    return true;
                }
            });

            itemView.setActivated(selected);
        }
    }
}
