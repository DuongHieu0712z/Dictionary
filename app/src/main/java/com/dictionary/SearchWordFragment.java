package com.dictionary;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dictionary.Adapter.WordAdapter;
import com.dictionary.Model.Word;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SearchWordFragment extends Fragment {
    private Context mContext;
    private SearchView searchView;
    private WordAdapter adapter;
    private ArrayList<Word> datas = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_search_words, container, false);
        initViews(rootView);
        return rootView;
    }

    private void initViews(View v) {
        RecyclerView rcv = v.findViewById(R.id.rcv_display_word);
        fakeData();
        adapter = new WordAdapter(datas,mContext);
        rcv.setAdapter(adapter);
        rcv.setLayoutManager(new LinearLayoutManager(mContext));

//        TextView tv_sub_title = v.findViewById(R.id.tv_sub_title);
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference("appname");
//        myRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                // This method is called once with the initial value and again
//                // whenever data at this location is updated.
//                String value = dataSnapshot.getValue(String.class);
//                tv_sub_title.setText(value);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError error) {
//
//            }
//        });

        searchView = v.findViewById(R.id.sv_search);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                adapter.getFilter().filter(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.getFilter().filter(s);
                return false;
            }
        });
    }

    private void fakeData() {
        //ArrayList<Word> list = new ArrayList<>();
        /*list.add(new Word("Cloud storage solutions", "Lưu trữ đám mây là một mô hình của lưu trữ dữ liệu máy tính, trong đó các dữ liệu kỹ thuật số được lưu trữ trong các hồ chứa logic", "Cloud technology"));
        list.add(new Word("Hybrid cloud", "Lưu trữ đám mây là một mô hình của lưu trữ dữ liệu máy tính, trong đó các dữ liệu kỹ thuật số được lưu trữ trong các hồ chứa logic","Cloud technology"));
        list.add(new Word("Cloud storage providers", "Lưu trữ đám mây là một mô hình của lưu trữ dữ liệu máy tính, trong đó các dữ liệu kỹ thuật số được lưu trữ trong các hồ chứa logic","Cloud technology"));
        list.add(new Word("Service cloud", "Lưu trữ đám mây là một mô hình của lưu trữ dữ liệu máy tính, trong đó các dữ liệu kỹ thuật số được lưu trữ trong các hồ chứa logic","Cloud technology"));
        list.add(new Word("Cloud architecture", "Lưu trữ đám mây là một mô hình của lưu trữ dữ liệu máy tính, trong đó các dữ liệu kỹ thuật số được lưu trữ trong các hồ chứa logic","Cloud technology"));
        list.add(new Word("Cloud server", "Cloud server (hay còn gọi là máy chủ đám mây) là cơ sở hạ tầng ảo được xây dựng để thực hiện việc lưu trữ xử lý thông tin và ứng dụng","Cloud technology"));
        list.add(new Word("Cloud hosting", "Cloud server (hay còn gọi là máy chủ đám mây) là cơ sở hạ tầng ảo được xây dựng để thực hiện việc lưu trữ xử lý thông tin và ứng dụng","Cloud technology"));
        list.add(new Word("Cloud computing", "Là mô hình điện toán sử dụng công nghệ máy tính và phát triển dựa vào mạng Internet","Cloud technology"));

        list.add(new Word("Network security", "Cloud server (hay còn gọi là máy chủ đám mây) là cơ sở hạ tầng ảo được xây dựng để thực hiện việc lưu trữ xử lý thông tin và ứng dụng","Cybersecurity"));
        list.add(new Word("IT security", "Cloud server (hay còn gọi là máy chủ đám mây) là cơ sở hạ tầng ảo được xây dựng để thực hiện việc lưu trữ xử lý thông tin và ứng dụng","Cybersecurity"));
        list.add(new Word("Cybersecurity attack", "Là mô hình điện toán sử dụng công nghệ máy tính và phát triển dựa vào mạng Internet","Cybersecurity"));
*/
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Datas");
        // My top posts by number of stars
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                datas.clear();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    Word word = postSnapshot.getValue(Word.class);
                    datas.add(word);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message

            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }
}
