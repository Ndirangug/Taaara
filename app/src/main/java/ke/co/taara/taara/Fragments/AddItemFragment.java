package ke.co.taara.taara.Fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import ke.co.taara.taara.R;

public class AddItemFragment extends AppCompatDialogFragment {

    // Use this instance of the interface to deliver action events
    AddItemListener mListener;
    EditText editText;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){



        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_add_item, null);
         editText = view.findViewById(R.id.editItemId);

        Log.i("POSITIVE", "initialized ");
        builder.setTitle(R.string.add_item)
                .setView(view)
                .setPositiveButton(R.string.add_to_cart, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mListener.onDialogPositiveClick(AddItemFragment.this);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mListener.onDialogNegativeClick(AddItemFragment.this);
                    }
                });



        return builder.create();
    }

    public String getItemId(){
        Log.i("POSITIVE", "ATTEMPTING");
        String result = editText.getText().toString();
       Log.i("POSITIVE", "got sting");
       return result;
    }



    public interface AddItemListener {
        public void onDialogPositiveClick(DialogFragment dialog);
        public void onDialogNegativeClick(DialogFragment dialog);


    }






    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the AddItemListener so we can send events to the host
            mListener = (AddItemListener) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(context.toString()
                    + " must implement AddItemListener");
        }
    }
}
