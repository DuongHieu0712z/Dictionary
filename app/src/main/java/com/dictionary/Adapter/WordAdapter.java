package com.dictionary.Adapter;

import static android.graphics.text.LineBreaker.JUSTIFICATION_MODE_INTER_WORD;

import static com.dictionary.R.drawable.abc_ratingbar_indicator_material;
import static com.dictionary.R.drawable.harddrive;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.text.LineBreaker;
import android.net.Uri;
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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.dictionary.Database.SQLiteHelper;
import com.dictionary.MainActivity;
import com.dictionary.Model.Word;
import com.dictionary.R;

import java.io.File;
import java.util.ArrayList;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class WordAdapter extends RecyclerView.Adapter<WordAdapter.StoryHolder> implements Filterable {
    private ArrayList<Word> listWord;
    private ArrayList<Word> listWordOld; //on database
    private ArrayList<Word> listWordLocal; //have been search
    private final Context mContext;
    private TextToSpeech mTTS;



    public WordAdapter(ArrayList<Word> listWord, Context mContext) {

        this.mContext = mContext;
        this.listWordOld=listWord;

        SQLiteHelper helper = new SQLiteHelper(mContext);
        helper.openDB();

        this.listWordLocal = helper.getAll();
        this.listWord = listWordLocal;
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
                String strEngKey = strName.split("/")[0].toString();
                String strVieKey = strName.split("/")[1].toString();
                String strVieMean ="";
                String strEngMean = "";
                String strDes ="";
                String strTopic="";
                for(Word w: listWordOld){
                    if(w.getEngKey().compareTo(strEngKey)==0&&w.getVieKey().compareTo(strVieKey)==0)
                    {
                        strEngMean=w.getEngMean().toString();
                        strVieMean=w.getVieMean().toString();
                        strTopic=w.getTopic().toString();
                    }
                }

                openDialogDetail(strEngKey, strEngMean, strVieKey, strVieMean, strTopic);
                SQLiteHelper helper = new SQLiteHelper(mContext);
                helper.openDB();
                Word w = new Word(strEngKey, strEngMean, strVieKey, strVieMean, strTopic);
                if(helper.IsExist(w)== true){
                    helper.update(w);
                }
                else{
                    helper.insert(w);
                }
                listWordLocal = helper.getAll();
            }
        });
        return new StoryHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(WordAdapter.StoryHolder holder, int position) {
        Word item = listWord.get(position);
        holder.tv_Name.setText(item.getEngKey().toString()+"/"+item.getVieKey());
        try{
            holder.tv_Description.setText((item.getEngMean().substring(0,50)).toString()+"..." +"\n\n"+
                    item.getVieMean().substring(0,50).toString()+"...");
        }
        catch (Exception ex){
            holder.tv_Description.setText(item.getEngMean().toString()+"..." +"\n\n"+
                    item.getVieMean().toString()+"...");
        }

        holder.tv_Description.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
    }

    @Override
    public int getItemCount() {
        if(listWord!=null)
        return listWord.size();
        return 0;
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
                    listWord=listWordLocal;
                    //listWord=listWordOld;
                else{
                    ArrayList<Word> list = new ArrayList<>();
                    for(Word w:listWordOld){
                        if(w.getEngKey().toLowerCase().contains(strSearch.toLowerCase())||
                                w.getVieKey().toLowerCase().contains(strSearch.toLowerCase())){
                            list.add(w);
                        }
                    }
                    listWord=list;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = listWord;
                return filterResults;
            }

            @SuppressLint("NotifyDataSetChanged")
            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                listWord=(ArrayList<Word>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void openDialogDetail(String EngKey, String EngMean, String VieKey, String VieMean, String Topic){
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

        tvName.setText(EngKey+"/"+VieKey);
        tvDes.setText(EngMean+"\n\n"+VieMean);
        tvSubName.setText(Topic);

        tvDes.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
        tvName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView tv_name = view.findViewById(R.id.tv_detail_name);
                String str= tv_name.getText().toString().split("/")[0];
                mTTS.speak(str,TextToSpeech.QUEUE_ADD, null);
            }
        });
        tvSubName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<Word> list = new ArrayList<>();
                for(Word w: listWordOld){
                    if(w.getTopic().toLowerCase().compareTo(Topic.toLowerCase())==0)
                        list.add(w);
                }
                listWord=list;
                notifyDataSetChanged();
                dialog.dismiss();
            }
        });
        int id = dialog.getContext().getResources().getIdentifier(EngKey.replaceAll("\\s+","").toLowerCase(Locale.ROOT), "drawable", dialog.getContext().getPackageName() );
        File file = new File(getURLForResource(id));
        if(id>0){
            CircleImageView img_des = dialog.findViewById(R.id.circle_image);
            img_des.setImageResource(id);
            img_des.getLayoutParams().width = 300;
            img_des.getLayoutParams().height = 300;
        }
        dialog.show();
    }
    public String getURLForResource (int resourceId) {
        return Uri.parse("android.resource://"+R.class.getPackage().getName()+"/" +resourceId).toString();
    }

}