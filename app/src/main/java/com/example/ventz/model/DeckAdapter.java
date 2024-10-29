package com.example.ventz.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.ventz.R;

import java.util.List;

public class DeckAdapter extends ArrayAdapter<Deck> {
    private final List<Deck> decks;
    private final Context context;

    public DeckAdapter(Context context, List<Deck> decks) {
        super(context, 0, decks);
        this.decks = decks;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.item_deck, parent, false); // Use o ID do layout diretamente aqui
        }

        Deck deck = getItem(position);

        if (deck != null) {
            TextView nome = view.findViewById(R.id.nome);
            TextView idUsuarioCriador = view.findViewById(R.id.id_usuario_fk_id_usuario);

            nome.setText(deck.getNome());
            idUsuarioCriador.setText("Usuário criador: " + String.valueOf(deck.getId_usuario_fk_id_usuario()));
        }

        return view;
    }
}