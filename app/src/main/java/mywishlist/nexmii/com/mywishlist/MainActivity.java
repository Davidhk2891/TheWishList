package mywishlist.nexmii.com.mywishlist;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import data.DatabaseHandler;
import model.MyWish;

public class MainActivity extends AppCompatActivity {

    private EditText title;
    private EditText content;

    private ImageView saveButton;
    private DatabaseHandler dbh;
    private AlertDialog.Builder inDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbh = new DatabaseHandler(MainActivity.this);
        title = (EditText) findViewById(R.id.titleEditText);
        content = (EditText) findViewById(R.id.wishEditText);
        saveButton = (ImageView) findViewById(R.id.saveBtn);

        saveButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                String editTitle = title.getText().toString();
                String editContent = content.getText().toString();

                if (editTitle.isEmpty() || editContent.isEmpty()){

                    //In case no value is placed, handle error with alert dialog
                    inDialog = new AlertDialog.Builder(MainActivity. this);
                    //set Title
                    inDialog.setTitle(getResources().getString(R.string.dTitle));
                    //set message
                    inDialog.setMessage(getResources().getString(R.string.dMessage));
                    //set cancelable
                    inDialog.setNegativeButton(getResources().getString(R.string.cancel),
                            new DialogInterface.OnClickListener(){

                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    //cancel dialog Box
                                    inDialog.setCancelable(true);

                                }
                            });

                    //Create Dialog
                    AlertDialog alertD = inDialog.create();

                    //Show Dialog
                    alertD.show();

                } else {

                    saveToDB();

                }
            }
        });
    }

    //Exit the App
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if(keyCode == KeyEvent.KEYCODE_BACK) {

            Intent i = new Intent(MainActivity. this, ListViewActivity.class);

            startActivity(i);

        }

        return false;
    }

    private void saveToDB(){

        MyWish wish = new MyWish();
        wish.setTitle(title.getText().toString().trim());
        wish.setContent(content.getText().toString().trim());

        dbh.addWishes(wish);
        dbh.close();

        //clear the editText(s)
        title.setText("");
        content.setText("");

        //go to details Activity upon adding new entry and clearing out the textEdit(s)
        Intent i = new Intent(MainActivity.this, ListViewActivity.class);
        startActivity(i);

    }

}
