package com.narvasoft.my_appi.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.narvasoft.my_appi.R;

import java.util.HashMap;
import java.util.Map;


public class create extends Fragment {

    private EditText userIdInput, titleInput, bodyInput;
    private Button createBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Inicializar los elementos de la vista
        userIdInput = view.findViewById(R.id.userIdInput);
        titleInput = view.findViewById(R.id.titleInput);
        bodyInput = view.findViewById(R.id.bodyInput);
        createBtn = view.findViewById(R.id.createBtn);

        // Configurar el botón para enviar los datos
        createBtn.setOnClickListener(v -> {
            // Obtener los valores de los campos
            String userId = userIdInput.getText().toString().trim();
            String title = titleInput.getText().toString().trim();
            String body = bodyInput.getText().toString().trim();

            // Verificar si los campos están vacíos
            if (userId.isEmpty() || title.isEmpty() || body.isEmpty()) {
                Toast.makeText(requireContext(), "Por favor, completa todos los campos.", Toast.LENGTH_SHORT).show();
            } else {
                sendCreateRequest(title, body, userId);
            }
        });
    }

    // Método para enviar los datos al servidor
    private void sendCreateRequest(final String title, final String body, final String userId) {
        String url = "https://jsonplaceholder.typicode.com/posts";

        // Crear la solicitud POST
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                response -> {
                    // Mostrar respuesta del servidor
                    Toast.makeText(requireContext(), "Se añadio correctamente", Toast.LENGTH_LONG).show();
                    // Registrar la respuesta del servidor en los logs para su depuración
                    Log.d("Respuesta", response);
                },
                error -> {
                    // Manejar el error en caso de que ocurra
                    Log.e("Error", "Error al enviar los datos: " + error.getMessage());
                }) {
            @Override
            protected Map<String, String> getParams() {
                // Crear el mapa de parámetros a enviar
                Map<String, String> params = new HashMap<>();
                params.put("title", title);
                params.put("body", body);
                params.put("userId", userId);
                return params;
            }
        };

        // Añade la solicitud a la cola de Volley para que sea procesada
        Volley.newRequestQueue(requireContext()).add(postRequest);
    }
}