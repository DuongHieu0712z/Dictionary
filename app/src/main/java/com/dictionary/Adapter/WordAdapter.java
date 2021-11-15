package com.dictionary.Adapter;

import static android.graphics.text.LineBreaker.JUSTIFICATION_MODE_INTER_WORD;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.text.LineBreaker;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.dictionary.Model.Word;
import com.dictionary.R;

import java.util.ArrayList;
import java.util.Locale;

public class WordAdapter extends RecyclerView.Adapter<WordAdapter.StoryHolder> implements Filterable {
    private ArrayList<Word> listWord;
    private ArrayList<Word> listWordOld;
    private final Context mContext;
    private TextToSpeech mTTS;


    public WordAdapter(ArrayList<Word> listWord, Context mContext) {
        this.listWord = listWord;
        this.mContext = mContext;
        this.listWordOld=listWord;
        mTTS = new TextToSpeech(mContext, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if(i==TextToSpeech.SUCCESS){
                    int result = mTTS.setLanguage(Locale.US);
                    if(result==TextToSpeech.LANG_MISSING_DATA||
                    result==TextToSpeech.LANG_NOT_SUPPORTED){
                        Log.e("TTS", "Language not support");
                    }
                }else{
                    Log.e("TTS", "Fails");
                }
            }
        });
    }


    @Override
    public StoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item__be_searched_word, parent, false);
        view.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                /*TextView tv_name = view.findViewById(R.id.tv_be_search_word_name);
                String str= tv_name.getText().toString();
                mTTS.speak(str,TextToSpeech.QUEUE_ADD, null);*/
                TextView tvName = view.findViewById(R.id.tv_be_search_word_name);
                String strName=tvName.getText().toString();
                String strDes ="";
                String strTopic="";
                for(Word w: listWordOld){
                    if(w.English == strName)
                    {
                        strDes=w.VietNamese;
                        strTopic=w.Topic;
                    }
                }

                openDialogDetail(strName, strDes, strTopic);
            }
        });
        return new StoryHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(WordAdapter.StoryHolder holder, int position) {
        Word item = listWord.get(position);
        holder.tv_Name.setText(item.English);
        if(item.VietNamese.length() >100)
            holder.tv_Description.setText(item.VietNamese.substring(0,100)+"...");
        else holder.tv_Description.setText(item.VietNamese);

        holder.tv_Description.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
        //holder.tvName.setText(item.getName());
    }

    @Override
    public int getItemCount() {
        return listWord.size();
    }

    public class StoryHolder extends RecyclerView.ViewHolder {
        TextView tv_Name;
        TextView tv_Description;

        public StoryHolder(View itemView) {
            super(itemView);
            tv_Name = itemView.findViewById(R.id.tv_be_search_word_name);
            tv_Description=itemView.findViewById(R.id.tv_be_search_word_description);
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String strSearch = charSequence.toString();
                if(strSearch.isEmpty())
                    listWord=listWordOld;
                else{
                    ArrayList<Word> list = new ArrayList<>();
                    for(Word w:listWordOld){
                        if(w.English.toLowerCase().contains(strSearch.toLowerCase())){
                            list.add(w);
                        }
                    }
                    listWord=list;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = listWord;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                listWord=(ArrayList<Word>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void openDialogDetail(String strName, String strDes, String strTopic){
        final Dialog dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.floating_dialog);
        Window window = dialog.getWindow();
        if(window==null)return;
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windowAttribute = window.getAttributes();
        windowAttribute.gravity= Gravity.CENTER;
        window.setAttributes(windowAttribute);
        dialog.setCancelable(true);

        TextView tvSubName = dialog.findViewById(R.id.tv_detail_sub_name);
        TextView tvName = dialog.findViewById(R.id.tv_detail_name);
        TextView tvDes = dialog.findViewById(R.id.tv_detail_description);

        tvName.setText(strName);
        tvDes.setText(strDes);
        tvSubName.setText(strTopic);

        tvDes.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
        tvName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView tv_name = view.findViewById(R.id.tv_detail_name);
                String str= tv_name.getText().toString();
                mTTS.speak(str,TextToSpeech.QUEUE_ADD, null);
            }
        });
        tvSubName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<Word> list = new ArrayList<>();
                for(Word w: listWordOld){
                    if(w.Topic == strTopic)
                        list.add(w);
                }
                listWord=list;
                notifyDataSetChanged();
                dialog.dismiss();
            }
        });

        dialog.show();
    }

}