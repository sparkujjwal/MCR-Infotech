package com.codinghelper.mscii;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 */
public class Explore extends Fragment {
    private EditText SearchBar;
    private Button Askbtn,flyGame;
    private DatabaseReference Root,userQuestion;
    private FirebaseAuth auth;
    private String currentUserID;
    String[] selectTopic = {"--Select Topic--","Computer","MAths","physics"};
    String selectedTopic;
    ImageButton Computer;
    long value;
    Integer j;






    public Explore() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_explore, container, false);
        SearchBar=(EditText)v.findViewById(R.id.search);
        Computer=(ImageButton)v.findViewById(R.id.ComputerBtn);
        Askbtn=(Button)v.findViewById(R.id.buttonAsk);
        flyGame=(Button)v.findViewById(R.id.game);
        auth=FirebaseAuth.getInstance();
        currentUserID=auth.getCurrentUser().getUid();
        userQuestion=FirebaseDatabase.getInstance().getReference().child("Questions");
        Root=FirebaseDatabase.getInstance().getReference().child("studentDetail");
        Spinner spinTopic = (Spinner)v.findViewById(R.id.selectTopic);
        ArrayAdapter<String> courseAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, selectTopic);
        courseAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinTopic.setAdapter(courseAdapter);
        flyGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), splashGameScreen.class));
            }
        });

       // Root.child(currentUserID).child("countA").setValue(j);
        spinTopic.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedTopic = parent.getItemAtPosition(position).toString();
                if (selectedTopic!="--Select Topic--") {
                    Toast.makeText(parent.getContext(), selectedTopic, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        Computer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ComputerActivity.class));

            }
        });

        //  questionRoot.child("Question_Asked").setValue("");



        Askbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String question = SearchBar.getText().toString();
                final String Topic = selectedTopic;
                if (TextUtils.isEmpty(question)){
                    SearchBar.setError("Ask your question!!");
                    SearchBar.setFocusable(true);
                }else if (Topic.equals("--Select Topic--")){
                    Toast.makeText(getActivity(), "select Topic related to your question!!", Toast.LENGTH_LONG).show();
                }else{
                     SearchBar.setText("");
                   //  userQuestion.push().child("QuestionAsked").setValue(question)




                   /* */




                    //unconfirmed change but working fine addvalueeentlistner
                       Root.child(currentUserID).addListenerForSingleValueEvent(new ValueEventListener() {
                           @Override
                           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                               if (dataSnapshot.exists()) {
                                   String key=userQuestion.push().getKey();
                                   String Simg =String.valueOf(dataSnapshot.child("imageUrl").getValue());
                                   String Sname =String.valueOf(dataSnapshot.child("username").getValue());
                                   value= (long)dataSnapshot.child("countQ").getValue();
                                   value=value+1;
                                   Root.child(currentUserID).child("countQ").setValue(value);
                                   HashMap rec=new HashMap();
                                   rec.put("QuestionAsked",question);
                                   rec.put("askerUID",currentUserID);
                                   rec.put("Answer","Not answered yet!!");
                                   rec.put("AskerImage",Simg);
                                   rec.put("Topic",Topic);
                                   rec.put("position","answer");
                                   rec.put("AnswererImage","https://firebasestorage.googleapis.com/v0/b/mscii-8cb88.appspot.com/o/skull%20(1).png?alt=media&token=22a5e53b-4270-40b9-bbf2-41109c135557");
                                   rec.put("AnswererId","Not yet");
                                   rec.put("FinalAnswererId","Not yet");
                                   rec.put("AskerName",Sname);
                                   rec.put("AnswererName","unknown");
                                   userQuestion.child(key).updateChildren(rec).addOnCompleteListener(new OnCompleteListener() {
                                       @Override
                                       public void onComplete(@NonNull Task task) {
                                           if(task.isSuccessful()){
                                              /* Root.child(currentUserID).addValueEventListener(new ValueEventListener() {
                                                   @Override
                                                   public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                       String Sqcount = String.valueOf(dataSnapshot.child("countQ").getValue());
                                                       int a=Integer.parseInt(Sqcount);
                                                       a++;
                                                       String Strq=Integer.toString(a);
                                                       Toast.makeText(getActivity(), Strq, Toast.LENGTH_LONG).show();

                                                   }

                                                   @Override
                                                   public void onCancelled(@NonNull DatabaseError databaseError) {

                                                   }
                                               });*/
                                              // Root.child(currentUserID).child("countQ").setValue(Strq);
                                               Toast.makeText(getActivity(), "your Question is available on Activities page", Toast.LENGTH_LONG).show();
                                           }
                                       }
                                   });


                               }
                           }

                           @Override
                           public void onCancelled(@NonNull DatabaseError databaseError) {

                           }
                       });

                }
            }
        });

      /*  SearchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(final Editable editable) {
            if(!editable.toString().isEmpty()){
                search(editable.toString());
            }else {
                if(editable.toString().isEmpty()) {
                    Askbtn.setVisibility(View.VISIBLE);
                    Askbtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String question = editable.toString();
                            questionRoot.child("Question Asked").setValue(question);
                        }
                    });
                    Askbtn.setVisibility(View.INVISIBLE);
                }
         //   }
            }
        });*/
        return v;
    }

   /* private void search(String editable) {
        Query query=questionRoot.orderByChild("Question Asked").startAt(editable).endAt(editable + "\uf8ff");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChildren()){

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }*/


}
