package com.android.traveltrek;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Locale;

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.ViewHolder> {

    private List<String> DataNames;
    private List<String> DataTypes;
    private List<String> DataTimes;
    private List<String> DataLocations;
    private List<String> DataURLs;
    private List<String> DataDesc;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private Context context;

    // data is passed into the constructor
    EventsAdapter(Context context, List<String> dataNames, List<String> dataDesc, List<String> dataLocations, List<String> dataTimes, List<String> dataTypes, List<String> dataURLs) {
        this.mInflater = LayoutInflater.from(context);
        this.DataNames = dataNames;
        this.DataDesc = dataDesc;
        this.DataLocations = dataLocations;
        this.DataTimes = dataTimes;
        this.DataTypes = dataTypes;
        this.DataURLs = dataURLs;
        this.context = context;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_single_event, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.EventName.setText(DataNames.get(position));
        holder.EventType.setText(DataTypes.get(position));
        holder.EventTime.setText(DataTimes.get(position));
        holder.EventDesc.setText(DataDesc.get(position));
        holder.EventLocation.setText(DataLocations.get(position));
        Picasso.get().load(DataURLs.get(position)).into(holder.EventURL);

        holder.textToSpeech = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status == TextToSpeech.SUCCESS){
                    int lang = holder.textToSpeech.setLanguage(Locale.ENGLISH);
                }
            }
        });

        holder.Audioimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = DataDesc.get(position);
                int speech = holder.textToSpeech.speak(s,TextToSpeech.QUEUE_FLUSH,null);
            }
        });
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return DataNames.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView EventName;
        TextView EventType;
        TextView EventDesc;
        TextView EventLocation;
        TextView EventTime;
        ImageView EventURL;
        ImageView Audioimg;
        TextToSpeech textToSpeech;

        ViewHolder(View itemView) {
            super(itemView);
            EventName = itemView.findViewById(R.id.event_name);
            EventType = itemView.findViewById(R.id.event_type);
            EventDesc = itemView.findViewById(R.id.desc);
            EventTime = itemView.findViewById(R.id.time);
            EventLocation = itemView.findViewById(R.id.loc);
            EventURL = itemView.findViewById(R.id.img);
            Audioimg = itemView.findViewById(R.id.audio);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    String getItem(int id) {
        return DataNames.get(id);
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}