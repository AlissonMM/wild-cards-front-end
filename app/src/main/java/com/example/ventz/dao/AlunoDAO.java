//package com.example.ventz.dao;
//
//import android.content.ContentValues;
//import android.content.Context;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//import android.util.Log;
//import android.widget.Toast;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import br.edu.fatec.bancodedados.TelaNovo;
//import br.edu.fatec.bancodedados.model.Aluno;
//import br.edu.fatec.bancodedados.util.ConnectionFactory;
//
//public class AlunoDAO {
//    private ConnectionFactory conexao;
//    private SQLiteDatabase banco;
//    public AlunoDAO(Context context){
//        //ConnectionFactory com o banco de dados
//        conexao = new ConnectionFactory(context, "banco.db", null,1);
//        banco = conexao.getWritableDatabase();
//
//
//        conexao = new ConnectionFactory(context, "banco.db", null, 1);
//        banco = conexao.getWritableDatabase();
//        if (banco.isOpen()) {
//            Log.d("AlunoDAO", "Database is connected");
//        } else {
//            Log.e("AlunoDAO", "Database is not connected");
//        }
//    }
//
//    // método inserir
//    public long insert(Aluno aluno){
//        // Check if CPF already exists
//        if (cpfExists(aluno.getCpf())) {
//            Log.d("AlunoDAO", "CPF já cadastrado. Tente novamente.");
//            return -1; // Indicating that insertion failed due to duplicate CPF
//        }
//
//        ContentValues values = new ContentValues();
//        values.put("nome", aluno.getNome());
//        values.put("cpf", aluno.getCpf());
//        values.put("telefone", aluno.getTelefone());
//        return(banco.insert("aluno", null, values));
//    }
//
//    // Method to check if CPF already exists
//    public boolean cpfExists(String cpf) {
//        String[] columns = {"cpf"};
//        String selection = "cpf = ?";
//        String[] selectionArgs = {cpf};
//
//        Cursor cursor = banco.query("aluno", columns, selection, selectionArgs, null, null, null);
//        boolean exists = cursor.getCount() > 0;
//        cursor.close();
//        return exists;
//    }
//
//
//    public Aluno read(Integer id) {
//        String args[] = {String.valueOf(id)};
//        Cursor cursor = banco.query("aluno", new String[]{"id", "nome", "cpf", "telefone"},
//                "id=?", args, null, null, null);
//
//        cursor.moveToFirst();
//        Aluno aluno = new Aluno();
//
//       if (cursor.getCount() > 0) {
//           aluno.setId(cursor.getInt(0));
//           aluno.setNome((cursor.getString(1)));
//           aluno.setCpf((cursor.getString(2)));
//           aluno.setTelefone((cursor.getString(3)));
//       }
//
//       return aluno;
//    }
//
//    public List<Aluno> obterTodos() {
//        List<Aluno> alunos = new ArrayList<>();
//        Cursor cursor = banco.query("aluno", new String[]{"id", "nome", "cpf", "telefone"},
//               null, null, null, null, null);
//
//        while (cursor.moveToNext()) {
//            Aluno a = new Aluno();
//            a.setId(cursor.getInt(0));
//            a.setNome((cursor.getString(1)));
//            a.setCpf((cursor.getString(2)));
//            a.setTelefone((cursor.getString(3)));
//            alunos.add(a);
//        }
//
//        while (cursor.moveToNext()) {
//            Aluno a = new Aluno();
//            a.setId(cursor.getInt(0));
//            a.setNome((cursor.getString(1)));
//            a.setCpf((cursor.getString(2)));
//            a.setTelefone((cursor.getString(3)));
//            alunos.add(a);
//            Log.d("AlunoDAO", "Aluno fetched: " + a.getNome());
//        }
//        cursor.close();
//
//        return alunos;
//    }
//
//    public void delete(int id) {
//        banco.delete("aluno", "id = ?", new String[]{String.valueOf(id)});
//        Log.d("AlunoDAO", "Aluno with id " + id + " deleted");
//    }
//
//    public void update(Aluno aluno) {
//        ContentValues values = new ContentValues();
//        values.put("nome", aluno.getNome());
//        values.put("cpf", aluno.getCpf());
//        values.put("telefone", aluno.getTelefone());
//
//        // Update the database record
//        int rowsAffected = banco.update("aluno", values, "id = ?", new String[]{String.valueOf(aluno.getId())});
//
//        if (rowsAffected > 0) {
//            Log.d("AlunoDAO", "Aluno com id " + aluno.getId() + " atualizado com sucesso!");
//        } else {
//            Log.e("AlunoDAO", "Falha ao atualizar aluno com id  " + aluno.getId());
//        }
//    }
//}