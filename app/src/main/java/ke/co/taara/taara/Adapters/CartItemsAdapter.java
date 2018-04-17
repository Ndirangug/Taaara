package ke.co.taara.taara.Adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import ke.co.taara.taara.R;

public class CartItemsAdapter extends RecyclerView.Adapter<CartItemsAdapter.ViewHolder>  {



    static String[] itemTitles = new String[] {
            "Dummy",
            "Data"
    };
    static String[] itemPrices = new String[] {
            "Dummy",
            "Data"
    };

    static String[] itemVats = new String[] {
            "Dummy",
            "Data"
    };

    int currentPosition;

   public CartItemsAdapter(String[] itemTitles, String[] itemPrices, String[] itemVat){
       CartItemsAdapter.itemTitles = itemTitles;
       CartItemsAdapter.itemPrices = itemPrices;
       CartItemsAdapter.itemVats = itemVat;
   }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView v = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cart_item_card, parent, false);
        // wrap it in a ViewHolder
        return new ViewHolder(v);




    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.titleView.setText(itemTitles[position]);
        holder.priceView.setText( itemPrices[position]);
        holder.vatView.setText(itemVats[position]);
        holder.mRemoveButton.setFocusable(true);
        holder.mRemoveButton.setFocusableInTouchMode(true);


    }

    @Override
    public int getItemCount() {
        return itemTitles.length;
    }



    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView titleView, priceView, vatView;
        Button mRemoveButton;
        int pos;
        public ViewHolder(CardView card) {
            super(card);
            cardView = card;
            titleView = (TextView)card.findViewById(R.id.item_title);
            priceView = card.findViewById(R.id.txtUnitPrice);
            vatView = card.findViewById(R.id.txtVat);
            mRemoveButton = card.findViewById(R.id.removeItem);
            pos = getAdapterPosition();


        }
    }


}
