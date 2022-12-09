package edu.msu.murraniy.project3;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import edu.msu.murraniy.project3.Cloud.Cloud;

public class CommentDialog extends DialogFragment {

    private AlertDialog dlg;
    private int locationID;
    private int userID;
    private EditText comment;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(R.string.comment_title);

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.activity_create_comment, null);
        builder.setView(view);

        comment = (EditText) view.findViewById(R.id.commentText);

        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                // Cancel just closes the dialog box
            }
        });

        builder.setPositiveButton(R.string.comment_post, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        Cloud cloud = new Cloud();
                        final boolean ok;
                        try{
                            ok = cloud.createComment(userID, locationID, comment.getText().toString());

                            if(!ok){
                                view.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(view.getContext(), R.string.create_comment_fail, Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }else{
                                dlg.dismiss();
                            }

                        }catch (Exception e){
                            Log.e("CreateComment", "Something went wrong when creating the comment", e);
                            view.post(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(view.getContext(), R.string.create_comment_fail, Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

                    }
                }).start();
            }
        });

        dlg = builder.create();
        return dlg;
    }

    public void setLocationID(int locationID) {
        this.locationID = locationID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

}
