package com.kimersoft.lynqpos.volleyHelpers;

import com.kimersoft.lynqpos.models.WalletItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;

/**
 * Created by Gadour on 05/12/2017.
 */

public class JsonToObjectParser {

    private WalletItem parseWalletItem(JSONObject walletJson) {
        WalletItem n = new WalletItem();
        try {

            n.setAsset(walletJson.getString("asset"));
            n.setBalance(walletJson.getInt("balance"));
            try {
                n.setCategory(walletJson.getString("category"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                n.setCode(walletJson.getString("code"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                n.setDescription(walletJson.getString("description"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            n.setIconUrl(walletJson.getString("iconUrl"));

            try {
                n.setIssuer(walletJson.getString("issuer"));
            } catch (JSONException e) {
                n.setRating("0");
                e.printStackTrace();
            }
            n.setName(walletJson.getString("name"));

            try {
                n.setNameShort(walletJson.getString("nameShort"));

            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                n.setPath(walletJson.getString("path"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            n.setPrice(walletJson.getString("price"));

            try {
                n.setRating(walletJson.getString("rating"));
            } catch (JSONException e) {
                n.setRating("0");
                e.printStackTrace();
            }
            try {
                n.setActualBalance(walletJson.getInt("quantity"));
            } catch (JSONException e) {
                e.printStackTrace();
            }


            try {

                LinkedHashMap<String, String> modifiersMap = new LinkedHashMap<>();

                JSONArray jModifires = walletJson.getJSONArray("modifiers");
                for (int j = 0; j < jModifires.length(); j++) {
                    JSONObject jHeader = jModifires.getJSONObject(j);//{"single":"1"}
                    Iterator<?> keys = jHeader.keys();

                    while (keys.hasNext()) {
                        String key = (String) keys.next();
                        String jModifiersDetail = jHeader.getString(key);
                        modifiersMap.put(key, jModifiersDetail);
                    }
                }
                n.setWalletModifiers(modifiersMap);


            } catch (JSONException e) {
                e.printStackTrace();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return n;

    }


    public ArrayList<WalletItem> parseWalletData(JSONObject walletJson) {

        ArrayList<WalletItem> walletItems = new ArrayList<>();
        String costumer = "";
        JSONArray array = new JSONArray();
        try {
            costumer = walletJson.getString("customer");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            array = walletJson.getJSONArray("data");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < array.length(); i++) {
            try {
                WalletItem walletItem = parseWalletItem(array.getJSONObject(i));
                walletItem.setCustumer(costumer);
                walletItems.add(walletItem);
            } catch (JSONException e) {
                e.printStackTrace();
                System.out.println("maher unexpected error");
            }
        }

        return walletItems;

    }

}
