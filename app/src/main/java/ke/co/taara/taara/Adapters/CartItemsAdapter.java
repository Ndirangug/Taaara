package ke.co.taara.taara.Adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import ke.co.taara.taara.R;

public class CartItemsAdapter extends RecyclerView.Adapter<CartItemsAdapter.ViewHolder>  {



    static String[] fakeData = new String[] {
            "One",
            "Two"
    };

    int currentPosition;

   public CartItemsAdapter(String[] items){
       fakeData = items;
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
        holder.titleView.setText(fakeData[position]);
        holder.mRemoveButton.setFocusable(true);
        holder.mRemoveButton.setFocusableInTouchMode(true);


    }

    @Override
    public int getItemCount() {
        return fakeData.length;
    }



    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView titleView;
        Button mRemoveButton;
        int pos;
        public ViewHolder(CardView card) {
            super(card);
            cardView = card;
            titleView = (TextView)card.findViewById(R.id.item_title);
            mRemoveButton = card.findViewById(R.id.removeItem);
            pos = getAdapterPosition();


        }
    }


}
