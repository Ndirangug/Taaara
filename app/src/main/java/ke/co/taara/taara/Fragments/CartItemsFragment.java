package ke.co.taara.taara.Fragments;


import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.security.PublicKey;

import ke.co.taara.taara.R;
import ke.co.taara.taara.Adapters.CartItemsAdapter;


public class CartItemsFragment extends Fragment {

    CartItemsAdapter cartItemsAdapter;

    RecyclerView recyclerView;
    String textToAdd;
    String[] strings ={"hello", "world"};
    public CartItemsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       cartItemsAdapter = new CartItemsAdapter(strings);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v = inflater.inflate(R.layout.fragment_cart_items,
                container, false);
        recyclerView = v.findViewById(R.id.cartRecycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(cartItemsAdapter);
        recyclerView.setLayoutManager(
                new LinearLayoutManager(getActivity()));


        return v;
    }

    public void setTextToAdd(String string){
        this.textToAdd = string;
    }

    public void addCard(){
        CardView cardView;
        LayoutInflater inflater = getLayoutInflater();
        cardView = (CardView) inflater.inflate(R.layout.cart_item_card, null);
        Log.i("POSITIVE", "FOUND CARDVIEW");
        TextView textView = cardView.findViewById(R.id.item_title);
        Log.i("POSITIVE", "FOUND TEXTVIEW");
        textView.setText(textToAdd);
        Log.i("POSITIVE", textToAdd.toUpperCase() + "text added to text view");
        cardView.removeView(textView);
        recyclerView.addView(textView);
        Log.i("POSITIVE", "bOUND TO RECYCLER " + recyclerView.getChildCount() + ".");
    }
    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    OnFragmentInteractionListener onFragmentInteractionListener;

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);


    }

    public RecyclerView  getRecyclerView() {
        Log.i("POSITIVE", "ATTEMPTED");
        return recyclerView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the AddItemListener so we can send events to the host
            onFragmentInteractionListener = (OnFragmentInteractionListener) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(context.toString()
                    + " must implement OnFragmentInterractionListener");
        }
    }


}
