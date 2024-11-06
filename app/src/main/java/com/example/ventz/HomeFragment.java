package com.example.ventz;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ventz.model.Dados;
import com.example.ventz.model.Deck;
import com.example.ventz.model.DeckAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RequestQueue requestQueue;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Initialize ListView using the inflated view
        ListView listView = view.findViewById(R.id.listView);


        // Url buscar todos os decks
        String urlBuscarTodosDecks = Dados.getInstance().getUrl() + "/decks/buscarTodos";

        requestQueue = Volley.newRequestQueue(getContext()); // Inicializa a RequestQueue

        List<Deck> decksList = new ArrayList<>();
        // Set up the adapter with mock data
        DeckAdapter adapter = new DeckAdapter(getContext(), decksList);
        listView.setAdapter(adapter);



        // Make the API request using Volley
        StringRequest stringRequest = new StringRequest(Request.Method.GET, urlBuscarTodosDecks,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            // Log the response for debugging
                            Log.d("API Response", response);

                            // If the API response is in the format you mentioned (e.g., deck objects inside a string)
                            // We need to parse the string response that looks like [Deck{idDeck=46, ...}, ...]

                            // Regex to extract each Deck object as a string
                            Pattern pattern = Pattern.compile("Deck\\{[^}]*\\}");
                            Matcher matcher = pattern.matcher(response);
                            while (matcher.find()) {
                                String deckString = matcher.group();

                                // Extract the 'nome' and 'idUsuarioFk' from the string
                                String nomeDeck = extractSecondNome(deckString);
                                int idUsuarioFk = extractIdUsuario(deckString);

                                // Log the extracted information for debugging
                                Log.d("Deck Info", "Nome do Deck: " + nomeDeck + ", Usuario Criador: " + idUsuarioFk);

                                // Add the extracted deck to the list
                                decksList.add(new Deck(nomeDeck, idUsuarioFk));
                            }

                            // Notify the adapter that the data has changed
                            adapter.notifyDataSetChanged();

                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.e("Error", "Erro ao processar a resposta da API", e);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("API Error", "Erro na requisição", error);
            }
        });

        requestQueue.add(stringRequest);

        return view;
    }

    // Helper method to extract the 'nome' from the deck string


private String extractSecondNome(String response) {
    String nomeDeck = "";
    // Padrão para capturar o primeiro 'nome=' após 'Deck{' até o fechamento da chave
    Pattern nomePattern = Pattern.compile("Deck\\{[^}]*?nome='([^']*)'");
    Matcher nomeMatcher = nomePattern.matcher(response);

    // Verifica se há um 'nome' no primeiro Deck
    if (nomeMatcher.find()) {
        nomeDeck = nomeMatcher.group(1); // Captura o primeiro 'nome'
    }

    return "Deck criado por: " + nomeDeck;
}



    // Helper method to extract the 'idUsuario' from the deck string
    private int extractIdUsuario(String deckString) {
        int idUsuario = -1;
        // Pattern to match idUsuario inside the 'Usuario{idUsuario=...' part of the string.
        Pattern idUsuarioPattern = Pattern.compile("idUsuario=(\\d+)");
        Matcher idUsuarioMatcher = idUsuarioPattern.matcher(deckString);
        if (idUsuarioMatcher.find()) {
            idUsuario = Integer.parseInt(idUsuarioMatcher.group(1)); // Extract the 'idUsuario' value
        }
        return idUsuario;
    }
}