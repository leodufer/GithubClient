package py.edu.facitec.githubclient;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class MainActivity extends AppCompatActivity {

    EditText loginEditText;
    TextView nameTextView;
    TextView emailTextView;
    RelativeLayout resultadoRelativeLayout;
    ImageView avatarImageView;
    ProgressBar progressBar;
    ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginEditText = (EditText)findViewById(R.id.editTextLogin);
        nameTextView = (TextView)findViewById(R.id.textViewName);
        emailTextView = (TextView)findViewById(R.id.textViewEmail);
        resultadoRelativeLayout = (RelativeLayout)findViewById(R.id.layoutResultado);
        avatarImageView = (ImageView)findViewById(R.id.imageViewAvatar);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        listView = (ListView)findViewById(R.id.listViewFollowers);
    }

    public void buscar(View view){
        progressBar.setVisibility(View.VISIBLE);
        final String login = loginEditText.getText().toString();

        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint("https://api.github.com").build();
        final UserService userService = restAdapter.create(UserService.class);

        userService.getUsuario(login, new Callback<User>() {
            @Override
            public void success(final User user, Response response) {
                progressBar.setVisibility(View.GONE);
                Log.i("USUARIO", user.toString());
                nameTextView.setText(user.getName());
                emailTextView.setText(user.getEmail());

                Transformation transformation = new RoundedTransformationBuilder()
                        .oval(true)
                        .build();

                Picasso.with(getApplicationContext())
                        .load(user.getAvatar_url())
                        .transform(transformation)
                        .resize(180,180)
                        .into(avatarImageView);
                resultadoRelativeLayout.setVisibility(android.view.View.VISIBLE);

                userService.getFollowers(login, new Callback<List<User>>() {
                    @Override
                    public void success(List<User> users, Response response) {
                        Log.i("Followers", users.toString());
                        FollowerListAdaptar adapter = new FollowerListAdaptar(MainActivity.this,users);
                        listView.setAdapter(adapter);

                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.e("Error followers", error.getLocalizedMessage());
                    }
                });

            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("ERROR DE PETICION", error.getLocalizedMessage());
                progressBar.setVisibility(View.GONE);
            }
        });
    }

}
