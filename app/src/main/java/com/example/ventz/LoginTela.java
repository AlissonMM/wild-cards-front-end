package com.example.ventz;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ventz.model.Dados;

public class LoginTela extends AppCompatActivity {

    private RequestQueue requestQueue;
    private String url;
    private int userId; // Variável para armazenar o ID do usuário

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_login_tela);

        requestQueue = Volley.newRequestQueue(this);



        EditText txtEmail = findViewById(R.id.txtEmail);
        EditText txtSenha = findViewById(R.id.txtSenha);
        Button btnCadastro = findViewById(R.id.btnTelaCadastro);
        Button btnLogin = findViewById(R.id.btnLogin);

        EditText txtUrl = findViewById(R.id.txtUrl);


        Dados.getInstance().setUrl("http://ec2-18-228-151-32.sa-east-1.compute.amazonaws.com:8080");

        // Botão para ir para a tela de cadastro
        btnCadastro.setOnClickListener(v -> {
            Intent intent = new Intent(this, CadastroTela.class);
            startActivity(intent);
            finish();
        });

        // Botão de login
        btnLogin.setOnClickListener(v -> {
            String email = txtEmail.getText().toString();
            String senha = txtSenha.getText().toString();
            String urlTexto = txtUrl.getText().toString();

            if (email.isEmpty() || senha.isEmpty() || urlTexto.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
                return;
            }

//             Dados.getInstance().setUrl(txtUrl.getText().toString());
            url = Dados.getInstance().getUrl() + "/usuarios/buscarPorEmailESenha";

            // Adiciona as credenciais como parâmetros na URL
            String loginUrl = url + "?email=" + email + "&senha=" + senha;

            // Faz a requisição GET usando StringRequest para capturar o ID de resposta
            StringRequest request = new StringRequest(
                    Request.Method.GET,
                    loginUrl,
                    response -> {
                        // Extrai o ID do usuário da string de resposta
                        String idUsuarioStr = response.substring(response.indexOf("idUsuario=") + 10, response.indexOf(",", response.indexOf("idUsuario=")));
                        try {
                            userId = Integer.parseInt(idUsuarioStr);
                            Toast.makeText(LoginTela.this, "Login realizado com sucesso! ID: " + userId, Toast.LENGTH_SHORT).show();

                            // Salva o ID do usuário em uma instância global ou em SharedPreferences
                            Dados.getInstance().setIdUsuarioLogado(userId);

                            Intent intent = new Intent(LoginTela.this, MainActivity.class); // Substitua por sua próxima tela
                            startActivity(intent);
                            finish();

                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                            Toast.makeText(LoginTela.this, "Erro ao processar ID do usuário", Toast.LENGTH_SHORT).show();
                        }
                    },
                    error -> {
                        // Caso de erro (credenciais inválidas ou outra falha)
                        if (error.networkResponse != null && error.networkResponse.statusCode == 400) {
                            Toast.makeText(LoginTela.this, "Credenciais inválidas", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(LoginTela.this, "Erro de conexão", Toast.LENGTH_SHORT).show();
                        }
                    }
            );

            // Adiciona a requisição à fila
            requestQueue.add(request);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;
    }
}
