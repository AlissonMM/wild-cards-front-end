package com.example.ventz.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.ventz.R;

import java.util.List;

public class CardAdapterBack extends ArrayAdapter<Card> {
    private final List<Card> cards;
    private final Context context;

    public CardAdapterBack(Context context, List<Card> cards) {
        super(context, 0, cards);
        this.cards = cards;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.item_card_front, parent, false); // Substitua pelo layout correto do item do Card
        }

        Card card = getItem(position);

        if (card != null) {

            TextView respostaTextView = view.findViewById(R.id.resposta); // Certifique-se de que esses IDs correspondam ao layout

            respostaTextView.setText(card.getResposta());

        }

        return view;
    }
}

