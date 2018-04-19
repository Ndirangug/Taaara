package ke.co.taara.taara;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ke.co.taara.taara.Adapters.CartItemsAdapter;
import ke.co.taara.taara.Fragments.AddItemFragment;
import ke.co.taara.taara.Fragments.ConfirmPayment;

public class Cart extends AppCompatActivity  implements AddItemFragment.AddItemListener{

    android.support.v7.widget.Toolbar mToolbar;
    AddItemFragment addItemDialog;
    CartItemsAdapter cartItemsAdapter;
    String  quantity;
    TextView textTotal;
    String totalStr;
   public static String addedItem;
    RecyclerView r;
    List<String> titlesList;
    List<String> pricesList;
    List<String> vatList;
    String[] itemTitles;
    String[] itemPrices;
    String[] itemVat;
    String[] titles;
    String[] prices;
    String[] vats;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        mToolbar = findViewById(R.id.CartToolbar);
        textTotal = findViewById(R.id.txtTotalCart);
        totalStr = "0";
        textTotal.setText(totalStr);
        itemTitles = getResources().getStringArray(R.array.titles);
        itemPrices = getResources().getStringArray(R.array.prices);
        itemVat = getResources().getStringArray(R.array.vat);
        titles = new String[]{};
        prices = new String[]{};
        vats = new String[]{};
        titlesList = new ArrayList<>();
        pricesList = new ArrayList<>();
        vatList = new ArrayList<>();
        addItemDialog = new AddItemFragment();
        cartItemsAdapter = new CartItemsAdapter(titlesList.toArray(titles), pricesList.toArray(prices), vatList.toArray(vats));
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
    public void onDialogPositiveClick(DialogFragment dialog) {
        addedItem = addItemDialog.getItemId();
        Log.i("POSITIVE", "String gotten" + addedItem);
        int index = Integer.parseInt(addedItem);
        if (index < itemTitles.length) {
            titlesList.add(itemTitles[index]);
            pricesList.add(itemPrices[index]);
            vatList.add(itemVat[index]);
            cartItemsAdapter = new CartItemsAdapter(titlesList.toArray(titles), pricesList.toArray(prices), vatList.toArray(vats));
            r.setAdapter(cartItemsAdapter);
           int currentAmount = Integer.parseInt(textTotal.getText().toString());
           currentAmount += Integer.parseInt(itemPrices[index]);
           textTotal.setText(String.valueOf(currentAmount));

        }else{
            Toast.makeText(getApplicationContext(), "No item with entered id.Try id between 0 and " + String.valueOf(((itemTitles.length) - 1)), Toast.LENGTH_LONG ).show();
        }




    }

    public void removeItem(View view){
        view.requestFocus();
        titlesList.remove(r.getChildAdapterPosition(r.getFocusedChild()));
        pricesList.remove(r.getChildAdapterPosition(r.getFocusedChild()));
        vatList.remove(r.getChildAdapterPosition(r.getFocusedChild()));
        int currentAmount = Integer.parseInt(textTotal.getText().toString());
        TextView tosubtract = r.getFocusedChild().findViewById(R.id.txtUnitPrice);
        currentAmount -= Integer.parseInt(tosubtract.getText().toString());
        textTotal.setText(String.valueOf(currentAmount));
        cartItemsAdapter = new CartItemsAdapter(titlesList.toArray(titles), pricesList.toArray(prices), vatList.toArray(vats));
        r.setAdapter(cartItemsAdapter);
    }

    public void checkOut(View view){
        double amountToCheckout = Integer.parseInt(textTotal.getText().toString());
       ConfirmPayment confirmPaymentDialog = new ConfirmPayment();
       Bundle paymentAmount = new Bundle();
       paymentAmount.putDouble("PAYMENT", amountToCheckout);
       confirmPaymentDialog.setArguments(paymentAmount);
       confirmPaymentDialog.show(getSupportFragmentManager(), "CONFIRM_PAYMENT");


    }


}
