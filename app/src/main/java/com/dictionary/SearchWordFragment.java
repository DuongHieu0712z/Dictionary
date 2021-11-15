package com.dictionary;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dictionary.Adapter.WordAdapter;
import com.dictionary.Model.Word;

import java.util.ArrayList;

public class SearchWordFragment extends Fragment {
    private Context mContext;
    private SearchView searchView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_search_words, container, false);
        initViews(rootView);
        return rootView;
    }

    private void initViews(View v) {
        RecyclerView rcv = v.findViewById(R.id.rcv_display_word);
        ArrayList<Word> listStory = fakeData();
        WordAdapter adapter = new WordAdapter(listStory,mContext);
        rcv.setAdapter(adapter);
        rcv.setLayoutManager(new LinearLayoutManager(mContext));

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

    private ArrayList<Word> fakeData() {
        ArrayList<Word> list = new ArrayList<>();
        list.add(new Word("Cloud storage solutions", "Lưu trữ đám mây là một mô hình của lưu trữ dữ liệu máy tính, trong đó các dữ liệu kỹ thuật số được lưu trữ trong các hồ chứa logic", "Cloud technology"));
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

        return list;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }
}
