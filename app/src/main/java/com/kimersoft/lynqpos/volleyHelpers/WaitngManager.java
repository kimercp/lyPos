package com.kimersoft.lynqpos.volleyHelpers;

import android.content.Context;

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

public class WaitngManager {


    private Context mContext;

    public WaitngManager(Context context) {
        this.mContext = context;
    }

    public void getWaitingAssets(String wallet, String code, final VolleyCallback volleyCallback) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("wallet", wallet);
            jsonObject.put("code", code);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, Urls.scanQr, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //return a json object
                volleyCallback.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                volleyCallback.onError(error);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                if (User.getCurrentUser(mContext).getToken() != null)
                    params.put("Authorization", "Bearer " + User.getCurrentUser(mContext).getToken());
                return params;
            }
        };

        ConnectionSingleton.getInstance(mContext).addToRequestque(req);
    }
}