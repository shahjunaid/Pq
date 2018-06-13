package art.kashmir.com.pq;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;


/**

 */
public class quotes extends Fragment implements View.OnClickListener {
    ImageView copy;
    TextView quote,author;
    LinearLayout tx,pro;
    private String url="http://api.forismatic.com/api/1.0/?method=getQuote&lang=en&format=json&key=100";
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
   @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_quotes, container, false);
        copy=v.findViewById(R.id.copy);
        quote=v.findViewById(R.id.quote);
        author=v.findViewById(R.id.writer);
        copy.setOnClickListener(this);
        tx=v.findViewById(R.id.tx);
        pro=v.findViewById(R.id.pro);
        tx.setVisibility(View.GONE);
        result();
        return v;
    }
public void result(){
    StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            try {
                JSONObject jsonObject=new JSONObject(response);
                tx.setVisibility(View.VISIBLE);
                pro.setVisibility(View.GONE);
                String quot=jsonObject.get("quoteText").toString();
                quote.setText(quot);
                String auth=jsonObject.get("quoteAuthor").toString();
                author.setText(auth);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            if(error instanceof NetworkError)
            {
                Toast.makeText(getActivity(), "No internet connection", Toast.LENGTH_SHORT).show();
            }
            else if(error instanceof ServerError)
            {
                Toast.makeText(getActivity(), "Bare with us for a moment", Toast.LENGTH_SHORT).show();
            }
        }
    });
    singletone.getInstance(getActivity()).addToRequestQueue(stringRequest);
}

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.copy:
                Intent intent=new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT,quote.getText().toString());
                intent.setType("text/plain");
                startActivity(intent);
        }
    }
}
