package com.kimersoft.lynqpos.volleyHelpers;

import android.app.Activity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.kimersoft.lynqpos.models.User;
import com.kimersoft.lynqpos.util.Urls;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Gadour on 05/12/2017.
 */

public class VolleyRequestsHandler {


    private Activity mActivity;

    public VolleyRequestsHandler(Activity activity){
        this.mActivity = activity;
    }

    public void userSignIn(JSONObject object, final VolleyCallback volleyCallback){

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, Urls.userSignIn, object, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String token = response.getString("token");
                    volleyCallback.onSuccess(token);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                volleyCallback.onError(error);
            }
        });

        ConnectionSingleton.getInstance(mActivity).addToRequestque(req);
    }

    // retrive user  information
    public void getUserInfo(String token, final VolleyCallback volleyCallback) {
        final JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, Urls.getUserInfo, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    User u=new User();
                    u.setEmail(response.getString("email"));
                    u.setWalletKey(response.getString("address"));
                    u.setName(response.getString("name"));
                    u.setLastname(response.getString("lastname"));
                    User.setCurrentUserAfterInfo(u,mActivity);

                    volleyCallback.onSuccess(u);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                volleyCallback.onError(error);

            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                if(User.getCurrentUser(mActivity).getToken()!=null)
                    params.put("Authorization", "Bearer "+User.getCurrentUser(mActivity).getToken());

                return params;
            }
        };

        ConnectionSingleton.getInstance(mActivity).addToRequestque(req);
    }



}
