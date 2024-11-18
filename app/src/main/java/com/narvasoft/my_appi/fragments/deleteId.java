package com.narvasoft.my_appi.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.narvasoft.my_appi.R;


public class deleteId extends Fragment {

    private EditText inputId;
    private Button deleteButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_delete_id, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        inputId = view.findViewById(R.id.deleteId);
        deleteButton = view.findViewById(R.id.deleteBtn);

        deleteButton.setOnClickListener(v -> {
            String id = inputId.getText().toString().trim();

            if (!id.isEmpty()) {
                sendDeleteRequest(id);
            } else {
                Toast.makeText(getContext(), "Por favor, ingresa un ID válido.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sendDeleteRequest(String id) {
        // Construye la URL concatenando la base con el ID proporcionado por el usuario
        String url = "https://jsonplaceholder.typicode.com/posts/" + id;

        // Crea una instancia de RequestQueue, que gestiona las solicitudes HTTP en la app.
        // Es necesario para enviar la petición al servidor.
        RequestQueue queue = Volley.newRequestQueue(requireContext());

        // Configura la solicitud DELETE utilizando StringRequest
        StringRequest deleteRequest = new StringRequest(
                Request.Method.DELETE,  // Especifica el método HTTP DELETE
                url,                    // La URL del recurso a eliminar
                response -> {
                    // Imprime la respuesta del servidor en el Logcat)
                    Log.d("DELETE_RESPONSE", response);
                    // Muestra un mensaje de éxito al usuario
                    Toast.makeText(getContext(), "Elemento eliminado correctamente", Toast.LENGTH_SHORT).show();
                },
                error -> {
                    Log.e("DELETE_ERROR", error.toString());
                    Toast.makeText(getContext(), "Error al eliminar el elemento", Toast.LENGTH_SHORT).show();
                }
        );

        // Añade la solicitud a la cola de Volley para que sea procesada
        queue.add(deleteRequest);
    }
}