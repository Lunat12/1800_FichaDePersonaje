package institute.immune.playersheet;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import android.app.DialogFragment;

public class EraseCharacterDialogFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(Constants.SURE)
                .setPositiveButton(Constants.YES, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ((MainActivity) getActivity()).eraseCharacter();
                        Toast.makeText(getContext(), Constants.DELETED, Toast.LENGTH_SHORT).show();
                        EasyAccess.setIsErasing();
                    }
                })
                .setNegativeButton(Constants.NO, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        EasyAccess.setIsErasing();
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}
