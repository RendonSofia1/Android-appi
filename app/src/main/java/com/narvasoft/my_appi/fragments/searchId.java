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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.narvasoft.my_appi.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class searchId extends Fragment {

    private EditText idInput, userIdInput, titleInput, bodyInput;
    private Button searchBtn, updateBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_id, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        idInput = view.findViewById(R.id.idInput);
        searchBtn = view.findViewById(R.id.searchBtn);
        userIdInput = view.findViewById(R.id.userIdInput);
        titleInput = view.findViewById(R.id.titleInput);
        bodyInput = view.findViewById(R.id.bodyInput);
        updateBtn = view.findViewById(R.id.updateBtn);

        searchBtn.setOnClickListener(v -> getById());

        updateBtn.setOnClickListener(v -> {
            String id = idInput.getText().toString().trim();
            String title = titleInput.getText().toString().trim();
            String body = bodyInput.getText().toString().trim();
            String userId = userIdInput.getText().toString().trim();

            if (id.isEmpty() || title.isEmpty() || body.isEmpty() || userId.isEmpty()) {
                Toast.makeText(requireContext(), "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
                return;
            }

            sendUpdateRequest(title, body, userId, id);
        });
    }

    private void getById() {
        // Obtener el valor del campo de texto 'idInput' y eliminar espacios en blanco al principio y al final
        String id = idInput.getText().toString().trim();

        // Comprobar si el campo de ID está vacío
        if (id.isEmpty()) {
            Log.e("Error", "El campo ID está vacío");
            return;
        }

        // Construir la URL para la solicitud HTTP GET utilizando el ID proporcionado
        String url = "https://jsonplaceholder.typicode.com/posts/" + id;

        // Crear una cola de solicitudes utilizando Volley
        RequestQueue requestQueue = Volley.newRequestQueue(requireContext());

        // Crear una solicitud de tipo GET
        StringRequest postRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    try {
                        // Si la solicitud es exitosa, procesar la respuesta
                        JSONObject jsonObject = new JSONObject(response);  // Convertir la respuesta en un objeto JSON

                        // Establecer los valores de los campos de texto 'userIdInput', 'titleInput' y 'bodyInput' con los datos obtenidos
                        userIdInput.setText(jsonObject.getString("userId"));
                        titleInput.setText(jsonObject.getString("title"));
                        bodyInput.setText(jsonObject.getString("body"));
                    } catch (JSONException e) {
                        // Manejo de error en caso de que la respuesta no sea un JSON válido
                        e.printStackTrace();
                        Log.e("Error", "JSON mal formado: " + e.getMessage());  // Registrar el error en el log
                    }
                },
                error -> Log.e("Error", "Solicitud fallida: " + error.getMessage())  // Registrar un error en caso de que la solicitud de red falle
        );

        // Añade la solicitud a la cola de Volley para que sea procesada
        requestQueue.add(postRequest);
    }


    private void sendUpdateRequest(final String title, final String body, final String userId, final String id) {
        // Construir la URL para la solicitud HTTP PUT utilizando el ID proporcionado
        String url = "https://jsonplaceholder.typicode.com/posts/" + id;

        // Crear una cola de solicitudes utilizando Volley para enviar la solicitud de red
        RequestQueue requestQueue = Volley.newRequestQueue(requireContext());

        // Crear una solicitud de tipo PUT para actualizar los datos en el servidor
        StringRequest putRequest = new StringRequest(Request.Method.PUT, url,
                response -> {
                    // Si la solicitud es exitosa, mostrar un mensaje de éxito al usuario
                    Toast.makeText(requireContext(), "Se actualizó correctamente", Toast.LENGTH_LONG).show();
                    // Registrar la respuesta del servidor en los logs para su depuración
                    Log.d("Respuesta", response);
                },
                error -> Log.e("Error", "Error al actualizar: " + error.getMessage())
        ) {
            // Sobrescribir el método getParams para enviar los parámetros en el cuerpo de la solicitud PUT
            @Override
            protected Map<String, String> getParams() {
                // Crear un mapa de parámetros que serán enviados en la solicitud PUT
                Map<String, String> params = new HashMap<>();
                // Agregar los parámetros necesarios (id, title, body, userId)
                params.put("id", id);
                params.put("title", title);
                params.put("body", body);
                params.put("userId", userId);
                return params;  // Devolver el mapa con los parámetros
            }
        };

        // Añade la solicitud a la cola de Volley para que sea procesada
        requestQueue.add(putRequest);
    }

}