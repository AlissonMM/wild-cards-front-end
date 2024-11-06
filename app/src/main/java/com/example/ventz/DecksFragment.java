package com.example.ventz;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ventz.model.Dados;
import com.example.ventz.model.Deck;
import com.example.ventz.model.DeckAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DecksFragment extends Fragment {

    private String nomeDeckString = "";
    private String url;
    private RequestQueue requestQueue;
    private int idDeckPorNome = 0;

    public DecksFragment() {
        // Required empty public constructor
    }

    public static DecksFragment newInstance(String param1, String param2) {
        DecksFragment fragment = new DecksFragment();
        Bundle args = new Bundle();
        args.putString("param1", param1);
        args.putString("param2", param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_decks, container, false);
        ListView listView = view.findViewById(R.id.listView);
        ImageButton btnCriarDeck = view.findViewById(R.id.btnCriarDeck);

        requestQueue = Volley.newRequestQueue(getContext()); // Inicializa a RequestQueue

        // Initialize the Dialog
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.deck_dialog_box);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);

        Button btnCancelar = dialog.findViewById(R.id.btnCancelar);
        Button btnCriar = dialog.findViewById(R.id.btnCriar);
        EditText nomeDeck = dialog.findViewById(R.id.textViewNomeDeck);

        // Adapter for ListView of Decks
        List<Deck> decks = new ArrayList<>();
        DeckAdapter adapter = new DeckAdapter(getContext(), decks);
        listView.setAdapter(adapter);

        // List with Deck IDs
        List<Integer> listaIdDecks = new ArrayList<>();

        // URL to search for the user by ID
        String urlBuscarUsuarioPorId = Dados.getInstance().getUrl() + "/usuarios/buscarPorId/" + Dados.getInstance().getIdUsuarioLogado();

        StringRequest requestBuscarUsuario = new StringRequest(
                Request.Method.GET,
                urlBuscarUsuarioPorId,
                response -> {
                    try {
                        // Clean response
                        response = response.replaceFirst("Usuario\\{", "").replaceAll("\\}$", "");
                        response = response.replaceAll("DeckUsuario\\{", "{").replaceAll("usuario=\\d+,\\s?", "");
                        response = response.replaceAll("=", ":").replaceAll("'", "\"");
                        response = "{" + response + "}"; // Wrap the response to create a valid JSON

                        JSONObject jsonResponse = new JSONObject(response);
                        JSONArray deckUsuariosArray = jsonResponse.getJSONArray("deckUsuarios");

                        // Extract deck IDs from the response
                        for (int i = 0; i < deckUsuariosArray.length(); i++) {
                            JSONObject deckUsuario = deckUsuariosArray.getJSONObject(i);
                            int deckId = deckUsuario.getInt("deck");
                            listaIdDecks.add(deckId);
                        }

                        // Display the deck IDs (Optional)
                        if (getContext() != null) {
                            Toast.makeText(getContext(), "Decks encontrados: " + listaIdDecks.toString(), Toast.LENGTH_SHORT).show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getContext(), "Erro ao processar a resposta.", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    Toast.makeText(getContext(), "Erro de rede desconhecido.", Toast.LENGTH_SHORT).show();
                }
        );

        // Add the request to the RequestQueue
        requestQueue.add(requestBuscarUsuario);

        // Iterate over deck IDs to fetch deck names and users
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            for (int deckId : listaIdDecks) {
                String urlBuscarDecksPorId = Dados.getInstance().getUrl() + "/decks/buscarPorId/" + deckId;

                JsonObjectRequest requestBuscarDeckNomeEId = new JsonObjectRequest(
                        Request.Method.GET,
                        urlBuscarDecksPorId,
                        null,
                        response -> {
                            try {
                                String nomeDeckRe = response.getString("nome");
                                JSONObject usuarioJson = response.getJSONObject("idUsuarioFk");
                                int idUsuario = usuarioJson.getInt("idUsuario");

                                Deck deck = new Deck(nomeDeckRe, idUsuario);
                                decks.add(deck);

                                Toast.makeText(getContext(), "Carregando decks...", Toast.LENGTH_SHORT).show();
                                adapter.notifyDataSetChanged();
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(getContext(), "Erro ao processar a resposta.", Toast.LENGTH_SHORT).show();
                            }
                        },
                        error -> {
                            if (error.networkResponse != null) {
                                int statusCode = error.networkResponse.statusCode;
                                Toast.makeText(getContext(), "Erro ao buscar o deck. Código: " + statusCode, Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getContext(), "Erro", Toast.LENGTH_SHORT).show();
                            }
                        }
                );
                requestQueue.add(requestBuscarDeckNomeEId);
            }
        }, 3000); // 3000 milliseconds = 3 seconds

        url = Dados.getInstance().getUrl() + "/decks/inserirDeck";

        btnCriarDeck.setOnClickListener(v -> dialog.show());
        btnCancelar.setOnClickListener(v -> dialog.dismiss());

        btnCriar.setOnClickListener(v -> {
            nomeDeckString = nomeDeck.getText().toString().trim();
            if (nomeDeckString.isEmpty()) {
                Toast.makeText(getContext(), "O nome do deck não pode estar vazio.", Toast.LENGTH_SHORT).show();
                return;
            }

            JSONObject jsonBody = new JSONObject();
            try {
                jsonBody.put("idUsuarioFk", Dados.getInstance().getIdUsuarioLogado());
                jsonBody.put("nome", nomeDeckString);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            JsonObjectRequest request = new JsonObjectRequest(
                    Request.Method.POST,
                    url,
                    jsonBody,
                    response -> {},
                    error -> {
                        if (error.networkResponse != null) {
                            int statusCode = error.networkResponse.statusCode;
                            if (statusCode == 409) {
                                Toast.makeText(getContext(), "Já existe um deck com esse nome.", Toast.LENGTH_SHORT).show();
                            } else if (statusCode == 404) {
                                Toast.makeText(getContext(), "Usuário não encontrado.", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getContext(), "Erro ao criar o deck.", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getContext(), "Deck criado com sucesso!", Toast.LENGTH_SHORT).show();
                            decks.add(new Deck(nomeDeckString, Dados.getInstance().getIdUsuarioLogado()));
                            adapter.notifyDataSetChanged();
                            nomeDeck.setText("");
                            dialog.dismiss();
                        }
                    }
            );
            requestQueue.add(request);

            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                String urlBuscarDeckPorNome = Dados.getInstance().getUrl() + "/decks/buscarPorNome/" + nomeDeckString;

                JsonObjectRequest requestBuscarDeckPorNome = new JsonObjectRequest(
                        Request.Method.GET,
                        urlBuscarDeckPorNome,
                        null,
                        response -> {
                            try {
                                idDeckPorNome = response.getInt("idDeck");
                                Toast.makeText(getContext(), "id Deck encontrado: " + idDeckPorNome, Toast.LENGTH_SHORT).show();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        },
                        error -> {
                            if (error.networkResponse != null) {
                                int statusCode = error.networkResponse.statusCode;
                                if (statusCode == 404) {
                                    Toast.makeText(getContext(), "Deck não encontrado.", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getContext(), "Erro ao buscar o deck. Código: " + statusCode, Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(getContext(), "Erro desconhecido.", Toast.LENGTH_SHORT).show();
                            }
                        }
                );
                requestQueue.add(requestBuscarDeckPorNome);

            }, 6000);

            // Create JSON to associate the deck with the user
            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                JSONObject jsonAssociarDeck = new JSONObject();
                try {
                    jsonAssociarDeck.put("idUsuarioFk", Dados.getInstance().getIdUsuarioLogado());
                    jsonAssociarDeck.put("idDeckFk", idDeckPorNome);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                JsonObjectRequest requestAssociarDeck = new JsonObjectRequest(
                        Request.Method.POST,
                        Dados.getInstance().getUrl() + "/deckUsuarios/inserir",
                        jsonAssociarDeck,
                        response -> Log.d("AssociarDeck", "Associado com sucesso!"),
                        error -> {
                            if (error.networkResponse != null) {
                                int statusCode = error.networkResponse.statusCode;
                                Toast.makeText(getContext(), "Erro ao associar o deck ao usuário. Código: " + statusCode, Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getContext(), "Deck associado ao Usuario", Toast.LENGTH_SHORT).show();
                            }
                        }
                );
                requestQueue.add(requestAssociarDeck);
            }, 10000);
        });

        listView.setOnItemClickListener((parent, view1, position, id) -> {
            Intent intent = new Intent(getActivity(), DeckTela.class);
            intent.putExtra("idDeck", decks.get(position).getId_usuario_fk_id_usuario());
            startActivity(intent);
        });

        return view;
    }
}