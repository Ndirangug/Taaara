package ke.co.taara.taara.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import ke.co.taara.taara.R;

public class ConfirmPayment extends AppCompatDialogFragment {

    String paymentAmount;
    TextView shillings;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Intent intent = getActivity().getIntent();
        paymentAmount = String.valueOf(getArguments().getDouble("PAYMENT"));
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.confirm_payment, null);
        builder.setTitle("CONFIRM PAYMENT")
                .setView(v)
                .setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getContext(), "WAIT TO ENTER MPESA PIN", Toast.LENGTH_LONG).show();
                    }
                })
                .setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getContext(), "TRANSACTION CANCELLED", Toast.LENGTH_LONG).show();
                    }
                });
        shillings = v.findViewById(R.id.txtShillings);
        shillings.setText(paymentAmount);


        return builder.create();
    }
}
