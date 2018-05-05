package com.example.mpxv2.giuaky;

import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    DatabaseReference ref;
    ArrayList<Account> listdata = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ref = FirebaseDatabase.getInstance().getReference("Account");
    }

    @Override
    protected void onStart() {
        super.onStart();
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listdata.clear();
                for(DataSnapshot acc_snapshot : dataSnapshot.getChildren())
                {
                    Account ac = acc_snapshot.getValue(Account.class);
                    listdata.add(ac);
                }
                ListView list = (ListView) findViewById(R.id.listview);
                list.setAdapter(new CustomListAdapter(MainActivity.this,listdata));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void add (View v)
    {
        EditText name = (EditText) findViewById(R.id.editText_name);
        EditText pass = (EditText) findViewById(R.id.editText_pass);
        String _name = name.getText().toString();
        String _pass = pass.getText().toString();

        Account ac = new Account();
        ac.setName(_name);
        ac.setPass(_pass);
        String key = ref.push().getKey();
        ac.setKey(key);
        ref.child(key).setValue(ac);
        listdata.add(ac);
        name.setText(null);
        pass.setText(null);
    }
    public void delete (View v)
    {
        Integer index = (Integer)v.getTag();
        ArrayList<Account> data = listdata;
        Account ac = data.get(index);
        ref.child(ac.getKey()).removeValue();
        listdata.remove(ac);
    }
    public void show_detail(final String id)
    {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.edit_layout, null);
        dialogBuilder.setView(dialogView);
        final TextView name = (TextView) dialogView.findViewById(R.id.editText_name_edit);
        final TextView pass = (TextView) dialogView.findViewById(R.id.editText_pass_edit);
        Button Back = (Button) dialogView.findViewById(R.id.button_back);
        Button Update = (Button) dialogView.findViewById(R.id.button_update);

        final AlertDialog dialog = dialogBuilder.create();
        dialog.show();
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String _name = name.getText().toString();
                String _pass = pass.getText().toString();
                Account ac = new Account();
                ac.setKey(id);
                ac.setName(_name);
                ac.setPass(_pass);
                ref.child(id).setValue(ac);
                Toast.makeText(MainActivity.this,"Cập nhật thành công",Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
    }
    public void edit (View v)
    {
        Integer index = (Integer)v.getTag();
        ArrayList<Account> data = listdata;
        Account ac = data.get(index);
        show_detail(ac.getKey());
    }

}
