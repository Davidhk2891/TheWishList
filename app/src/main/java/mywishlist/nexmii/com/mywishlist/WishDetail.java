package mywishlist.nexmii.com.mywishlist;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

import data.DatabaseHandler;

public class WishDetail extends AppCompatActivity {

    private TextView title, date, content;
    private ImageButton deleteButton, deleteBtnTwo;
    private AlertDialog.Builder theDialog;

    final String[] goodWork = {

            "Keep it up!",
            "Don\'t stop here, keep working to a achieve another goal",
            "Keep up the good work",
            "Good job",
            "Great. Stay focused",
            "Keep on working",
            "Keep Going"

    };

    final String[] waysToKillWish = {

            "Your Resolution has been killed with a shotgun",
            "Your Resolution committed suicide",
            "Your Resolution was eaten by a hammer shark",
            "Your Resolution was obliterated by a hydrogen bomb",
            "Your Resolution removed his astronaut helmet in outer space",
            "Your Resolution was beaten up by bullies and died",
            "Your Resolution died in a sword fight",
            "Your Resolution was shot by its mother-in-law",
            "Your Resolution fell from the 67th floor of the Empire State building",
            "Your Resolution was ran over by a bus"

    };

    final String[] motivationQuotes = {

            "If you want to achieve greatness stop asking for permission.",
            "Things work out best for those who make the best of how things work out.",
            "To live a creative life, we must lose our fear of being wrong.",
            "If you are not willing to risk the usual you will have to settle for the ordinary.",
            "Trust because you are willing to accept the risk, not because it’s safe or certain.",
            "Take up one idea. Make that one idea your life - think of it, dream of it, live on that idea.",
            "Let the brain, muscles, nerves, every part of your body, be full of that idea, and just leave every other idea alone. This is the way to success.",
            "All our dreams can come true if we have the courage to pursue them.",
            "Good things come to people who wait, but better things come to those who go out and get them.",
            "If you do what you always did, you will get what you always got.",
            "Success is walking from failure to failure with no loss of enthusiasm.",
            "Just when the caterpillar thought the world was ending, he turned into a butterfly.",
            "Successful entrepreneurs are givers and not takers of positive energy.",
            "Whenever you see a successful person you only see the public glories, never the private sacrifices to reach them.",
            "Opportunities don’t happen, you create them.",
            "Try not to become a person of success, but rather try to become a person of value.",
            "Great minds discuss ideas; average minds discuss events; small minds discuss people.",
            "I have not failed. I’ve just found 10,000 ways that won’t work.",
            "If you don’t value your time, neither will others. Stop giving away your time and talents- start charging for it.",
            "A successful man is one who can lay a firm foundation with the bricks others have thrown at him.",
            "No one can make you feel inferior without your consent.",
            "The whole secret of a successful life is to find out what is one’s destiny to do, and then do it.",
            "If you’re going through hell keep going.",
            "The ones who are crazy enough to think they can change the world, are the ones that do.",
            "Don’t raise your voice, improve your argument.",
            "What seems to us as bitter trials are often blessings in disguise.",
            "The meaning of life is to find your gift. The purpose of life is to give it away.",
            "The distance between insanity and genius is measured only by success.",
            "When you stop chasing the wrong things you give the right things a chance to catch you.",
            "Don’t be afraid to give up the good to go for the great.",
            "No masterpiece was ever created by a lazy artist."

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wish_detail);

        title = (TextView) findViewById(R.id.textTitle);
        date = (TextView) findViewById(R.id.record);
        content = (TextView) findViewById(R.id.textWish);
        deleteButton = (ImageButton) findViewById(R.id.deleteButton);
        deleteBtnTwo = (ImageButton) findViewById(R.id.resCompleted);

        Bundle myExtras = getIntent().getExtras();

        if (myExtras != null){

            title.setText(myExtras.getString("title"));

            date.setText("created: " + myExtras.getString("date"));

            content.setText(" \" " + myExtras.getString("content") + " \" ");

            final int id = myExtras.getInt("id");

            deleteButton.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {

                    Random mrRandom = new Random();

                    int happyRandomNumber = mrRandom.nextInt(motivationQuotes.length);

                    theDialog = new AlertDialog.Builder(WishDetail. this);
                    //set Title
                    theDialog.setTitle(getResources().getString(R.string.dialog_title));
                    //set message
                    theDialog.setMessage(motivationQuotes[happyRandomNumber]);
                    //set cancelable
                    theDialog.setNegativeButton(getResources().getString(R.string.no),
                                new DialogInterface.OnClickListener(){

                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    //cancel dialog Box
                                    theDialog.setCancelable(true);

                                }
                            });
                    //set acceptable executable
                    theDialog.setPositiveButton(getResources().getString(R.string.yes),
                            new DialogInterface.OnClickListener(){

                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    DatabaseHandler dbhDeletion = new DatabaseHandler(getApplicationContext());

                                    dbhDeletion.deleteWish(id);

                                    Random mrRandom = new Random();

                                    int funnyRandomNumber = mrRandom.nextInt(waysToKillWish.length);

                                    Toast.makeText(getApplicationContext(), waysToKillWish[funnyRandomNumber],
                                            Toast.LENGTH_LONG).show();

                                    startActivity(new Intent(WishDetail.this, ListViewActivity.class));

                                }
                            });

                    //Create Dialog
                    AlertDialog alertD = theDialog.create();

                    //Show Dialog
                    alertD.show();

                }
            });

        }



        final int id = myExtras.getInt("id");

        deleteBtnTwo.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                theDialog = new AlertDialog.Builder(WishDetail. this);

                theDialog.setTitle(getResources().getString(R.string.resolutionAchieved));

                theDialog.setMessage(getResources().getString(R.string.didYouFinish));

                theDialog.setNegativeButton(getResources().getString(R.string.no),
                        new DialogInterface.OnClickListener(){

                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                theDialog.setCancelable(true);

                            }
                        });

                theDialog.setPositiveButton(getResources().getString(R.string.yes),
                        new DialogInterface.OnClickListener(){

                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                DatabaseHandler dbhDeletion = new DatabaseHandler(getApplicationContext());

                                dbhDeletion.deleteWish(id);

                                Random mrRandom = new Random();

                                int goodRandomNumber = mrRandom.nextInt(goodWork.length);

                                Toast.makeText(getApplicationContext(), goodWork[goodRandomNumber],
                                        Toast.LENGTH_LONG).show();

                                startActivity(new Intent(WishDetail.this, ListViewActivity.class));

                            }
                        });

                //Create Dialog
                AlertDialog alertY = theDialog.create();

                //Show Dialog
                alertY.show();

            }
        });

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if(keyCode == KeyEvent.KEYCODE_BACK) {

            Intent backIntent = new Intent(WishDetail. this, ListViewActivity. class);

            startActivity(backIntent);

        }

        return false;
    }

}
