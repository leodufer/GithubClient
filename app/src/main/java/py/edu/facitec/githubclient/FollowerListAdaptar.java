package py.edu.facitec.githubclient;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by virux on 26/04/17.
 */

public class FollowerListAdaptar extends BaseAdapter {

    private Context context;
    private List<User> users;

    public FollowerListAdaptar(Context context, List<User> users) {
        this.context = context;
        this.users = users;
    }

    @Override
    public int getCount() {
        return users.size();
    }

    @Override
    public Object getItem(int position) {
        return users.get(position);
    }

    @Override
    public long getItemId(int position) {
        return users.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if(v == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.item_followrs,null);
        }

        User u = users.get(position);

        TextView loginTextView = (TextView)v.findViewById(R.id.textViewLogin);
        ImageView avatarImageView = (ImageView)v.findViewById(R.id.imageViewAvatar);

        loginTextView.setText(u.getLogin());

        //Transformacion
        Picasso.with(context).load(u.getAvatar_url()).into(avatarImageView);




        return v;
    }
}
