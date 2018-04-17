package ke.co.taara.taara;

import android.net.Uri;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ke.co.taara.taara.Adapters.CartItemsAdapter;
import ke.co.taara.taara.Fragments.AddItemFragment;
import ke.co.taara.taara.Fragments.CartItemsFragment;

public class Cart extends AppCompatActivity  implements AddItemFragment.AddItemListener, CartItemsFragment.OnFragmentInteractionListener{

    android.support.v7.widget.Toolbar mToolbar;
    AddItemFragment addItemDialog;
    CartItemsAdapter cartItemsAdapter;



   public static String addedItem;
    RecyclerView r;
    String[] items = new String[]{};
    List<String> itemsList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        mToolbar = findViewById(R.id.CartToolbar);
        itemsList = new ArrayList<>();
        addItemDialog = new AddItemFragment();
        cartItemsAdapter = new CartItemsAdapter(itemsList.toArray(items));
        r = findViewById(R.id.recycler);
        r.setAdapter(cartItemsAdapter);
        r.setHasFixedSize(true);
        r.setLayoutManager(
                new LinearLayoutManager(getApplicationContext()));

        Toast.makeText(getApplicationContext(), "TO REMOVE AN ITEM, DOUBLE CLICK THE REMOVE BUTTON", Toast.LENGTH_LONG).show();
    }


    public void addItem(View view){
        addItemDialog.show(getSupportFragmentManager(), "ADD_ITEM");

    }

    public interface AddItemListener{
        void addItemClicked(String itemId);
    }



    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }



    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        addedItem = addItemDialog.getItemId();
        Log.i("POSITIVE", "String gotten" + addedItem);
        itemsList.add(addedItem);
        cartItemsAdapter = new CartItemsAdapter(itemsList.toArray(items));
        r.setAdapter(cartItemsAdapter);


    }

    public void removeItem(View view){
        view.requestFocus();
        itemsList.remove(r.getChildAdapterPosition(r.getFocusedChild()));
        cartItemsAdapter = new CartItemsAdapter(itemsList.toArray(items));
        r.setAdapter(cartItemsAdapter);
    }


}
