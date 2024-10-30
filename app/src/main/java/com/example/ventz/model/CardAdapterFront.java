package com.example.ventz.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.ventz.R;

import java.util.List;

public class CardAdapterFront extends ArrayAdapter<Card> {
    private final List<Card> cards;
    private final Context context;

    public CardAdapterFront(Context context, List<Card> cards) {
        super(context, 0, cards);
        this.cards = cards;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.item_card_front, parent, false); // Replace with the correct layout for the Card item
        }

        Card card = getItem(position);

        if (card != null) {
            TextView perguntaTextView = view.findViewById(R.id.pergunta); // Ensure these IDs match your layout
            perguntaTextView.setText(card.getPergunta());
        }

        return view;
    }
}
