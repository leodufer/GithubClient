package py.edu.facitec.githubclient;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static py.edu.facitec.githubclient.R.styleable.View;

public class MainActivity extends AppCompatActivity {

    EditText loginEditText;
    TextView nameTextView;
    TextView emailTextView;
    RelativeLayout resultadoRelativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginEditText = (EditText)findViewById(R.id.editTextLogin);
        nameTextView = (TextView)findViewById(R.id.textViewName);
        emailTextView = (TextView)findViewById(R.id.textViewEmail);
        resultadoRelativeLayout = (RelativeLayout)findViewById(R.id.layoutResultado);
    }

    public void buscar(View view){

        String login = loginEditText.getText().toString();

        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint("https://api.github.com").build();
        UserService userService = restAdapter.create(UserService.class);

        userService.getUsuario(login, new Callback<User>() {
            @Override
            public void success(User user, Response response) {
                Log.i("USUARIO", user.toString());
                nameTextView.setText(user.getName());
                emailTextView.setText(user.getEmail());
                resultadoRelativeLayout.setVisibility(android.view.View.VISIBLE);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("ERROR DE PETICION", error.getLocalizedMessage());
            }
        });
    }

}
