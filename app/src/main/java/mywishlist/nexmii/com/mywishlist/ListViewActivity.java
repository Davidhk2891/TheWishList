package mywishlist.nexmii.com.mywishlist;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import data.DatabaseHandler;
import model.MyWish;

//--------------------------------------------------------------------------------------------------
public class ListViewActivity extends AppCompatActivity {

    private DatabaseHandler dbh;//<--
    private Button addyButton;

    private ArrayList<MyWish> dbWishes = new ArrayList<>();//<--
    private WishAdapter wishyAdapter;//<--
    private ListView myListView;//<--


    @Override
    public void onContentChanged() {
        super.onContentChanged();                       //<---THIS AT THE VERY END

        View empty = findViewById(R.id.noWishes);
        myListView = (ListView) findViewById(R.id.list);
        myListView.setEmptyView(empty);
        myListView.setAdapter(new ArrayAdapter(this, R.layout.activity_list_view, new ArrayList()));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        myListView = (ListView) findViewById(R.id.list);
        addyButton = (Button) findViewById(R.id.addButton);

        addyButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                Intent i = new Intent(ListViewActivity. this, MainActivity. class);
                startActivity(i);

            }
        });

        refreshData();

    }

    //Go back to Main Activity
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if(keyCode == KeyEvent.KEYCODE_BACK) {

            Intent homeIntent = new Intent(Intent.ACTION_MAIN);

            homeIntent.addCategory( Intent.CATEGORY_HOME );

            homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            startActivity(homeIntent);

        }

        return false;
    }

    private void refreshData() {

        dbWishes.clear();

        dbh = new DatabaseHandler(getApplicationContext());

        ArrayList<MyWish> wishesFromDB = dbh.getWishes();

        for (int i = 0; i < wishesFromDB.size(); i++){

            String title = wishesFromDB.get(i).getTitle();

            String dateText = wishesFromDB.get(i).getRecordDate();

            String content = wishesFromDB.get(i).getContent();

            int mid = wishesFromDB.get(i).getItemId();

            MyWish myWishy = new MyWish();

            myWishy.setTitle(title);

            myWishy.setContent(content);

            myWishy.setRecordDate(dateText);

            myWishy.setItemId(mid);

            dbWishes.add(myWishy);

        }

        dbh.close();

       wishyAdapter = new WishAdapter(ListViewActivity.this, R.layout.wish_row, dbWishes);

        myListView.setAdapter(wishyAdapter);

        wishyAdapter.notifyDataSetChanged();

    }

    public class WishAdapter extends ArrayAdapter<MyWish>{

        Activity activity;
        int layoutResource;
        MyWish wish;
        ArrayList<MyWish> mData = new ArrayList<>();

        public WishAdapter(Activity act, int resource, ArrayList<MyWish> data) {
            super(act, resource, data);

            activity = act;
            layoutResource = resource;
            mData = data;

            notifyDataSetChanged();

        }

        public Activity getActivity() {
            return activity;
        }

        public void setActivity(Activity activity) {
            this.activity = activity;
        }

        public int getLayoutResource() {
            return layoutResource;
        }

        public void setLayoutResource(int layoutResource) {
            this.layoutResource = layoutResource;
        }

        public MyWish getWish() {
            return wish;
        }

        public void setWish(MyWish wish) {
            this.wish = wish;
        }

        public ArrayList<MyWish> getmData() {
            return mData;
        }

        public void setmData(ArrayList<MyWish> mData) {
            this.mData = mData;
        }

        @Override
        public int getCount() {
            return mData.size();
        }

        @Nullable
        @Override
        public MyWish getItem(int position) {
            return mData.get(position);
        }

        @Override
        public int getPosition(MyWish item) {
            return super.getPosition(item);
        }

        @Override
        public long getItemId(int position) {
            return super.getItemId(position);
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View row = convertView;
            ViewHolder holder = null;

            if (row == null || (row.getTag()) == null){

                LayoutInflater theInflator = LayoutInflater.from(activity);

                row = theInflator.inflate(layoutResource, null);

                holder = new ViewHolder();

                holder.mTitle = (TextView) row.findViewById(R.id.name);

                holder.mDate = (TextView) row.findViewById(R.id.dateText);

                row.setTag(holder);

            } else {

                holder = (ViewHolder) row.getTag();

            }

            holder.theWish = getItem(position);

            holder.mTitle.setText(holder.theWish.getTitle());

            holder.mDate.setText(holder.theWish.getRecordDate());

            final ViewHolder finalHolder = holder;
            row.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {

                    String text = finalHolder.theWish.getContent().toString();

                    String dateText = finalHolder.theWish.getRecordDate().toString();

                    String title = finalHolder.theWish.getTitle().toString();

                    int mid = finalHolder.theWish.getItemId();

                    Intent i = new Intent(ListViewActivity. this, WishDetail.class);
                    i.putExtra("title", title);
                    i.putExtra("content", text);
                    i.putExtra("date", dateText);
                    i.putExtra("id", mid);
                    startActivity(i);

                }
            });

            return row;
        }

        class ViewHolder{

            MyWish theWish;

            TextView mTitle;
            int mId;
            TextView mContent;
            TextView mDate;

        }

    }

}
