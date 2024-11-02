package com.example.ventz;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ventz.model.Deck;
import com.example.ventz.model.DeckAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DecksFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DecksFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String nomeDeckString = "";

    public DecksFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SettingsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DecksFragment newInstance(String param1, String param2) {
        DecksFragment fragment = new DecksFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_decks, container, false);
        ListView listView = view.findViewById(R.id.listView);
        ImageButton btnCriarDeck = view.findViewById(R.id.btnCriarDeck);

        // Initialize the Dialog
        Dialog dialog = new Dialog(DecksFragment.this.getContext());
        dialog.setContentView(R.layout.deck_dialog_box);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);

        // Access the buttons from the dialog's content view
        Button btnCancelar = dialog.findViewById(R.id.btnCancelar);
        Button btnCriar = dialog.findViewById(R.id.btnCriar);
        EditText nomeDeck = dialog.findViewById(R.id.textViewNomeDeck);

        // Mock data for testing
        List<Deck> mockDeck = new ArrayList<>();
        mockDeck.add(new Deck(1, "Meu Pequeno Deck", 1));
        mockDeck.add(new Deck(2, "Meu Grande Deck", 3));

        // Set up the adapter with mock data
        DeckAdapter adapter = new DeckAdapter(getContext(), mockDeck);
        listView.setAdapter(adapter);


        btnCriarDeck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnCriar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //add a toast with the text `Deck criado com sucesso`
                nomeDeckString = nomeDeck.getText().toString();
                Toast.makeText(getContext(), "Deck " + nomeDeckString + " criado com sucesso!", Toast.LENGTH_SHORT).show();

                mockDeck.add(new Deck(69, nomeDeckString, 3));

                adapter.notifyDataSetChanged();

                nomeDeck.setText("");
                dialog.dismiss();


            }
        });




        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), DeckTela.class);
                startActivity(intent);
            }
        });

        return view;
    }
}
