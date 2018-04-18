package ke.co.taara.taara.Fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import ke.co.taara.taara.AccountInfo;
import ke.co.taara.taara.Cart;
import ke.co.taara.taara.R;

public class CheckInFragment extends AppCompatDialogFragment  {

    double mlongitude;
    double mlatitude;
    TextView mTextView;
    String storename;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        sharedPreferences = getActivity().getSharedPreferences("STORE", 0);
        editor = sharedPreferences.edit();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.check_in, null);
        builder.setTitle(R.string.check_in)
                .setView(v)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent proceed = new Intent(getActivity(), Cart.class);
                        startActivity(proceed);


                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        Bundle bundle = getArguments();
        mlatitude = bundle.getDouble("LATITUDE");
        mlongitude = bundle.getDouble("LONGITUDE");


        if (mlongitude == 36.8068689 && mlatitude == -1.268375){
            storename = "Esgray Hostels";
        }

        else if(mlongitude == 0 && mlatitude == 0){
            storename = "Some Location";
        }

        else{
            storename = "Sorry..it seems like you're not in a recognized supermarket";
            builder.setPositiveButton(R.string.go_on, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent proceed = new Intent(getActivity(), Cart.class);
                    startActivity(proceed);
                }
            });
        }
        mTextView = v.findViewById(R.id.storeName);
        if (mTextView != null){
        mTextView.setText(storename);
        editor.putString("STORENAME", storename);
        }
        else{
            Log.i("STORE", "null textview");
        }
        return builder.create();
    }


}
