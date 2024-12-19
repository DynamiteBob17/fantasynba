package io.parser;

import data.Player;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PlayerParser {

    private PlayerParser() {}

    public static Player[] getPlayersFromJson(
            JSONObject json,
            boolean withAdditional,
            String[] playerValueKeys
    ) {
        try {
            JSONArray playersJson = json.getJSONArray("players");
            Player[] players = new Player[playersJson.length()];

            for (int i = 0; i < playersJson.length(); ++i) {
                JSONObject playerJson = playersJson.getJSONObject(i);
                Player.Builder builder = new Player.Builder();

                builder.name(playerJson.getString(playerValueKeys[0]))
                        .team(playerJson.getString(playerValueKeys[1]))
                        .salary(playerJson.getDouble(playerValueKeys[2]))
                        .form(playerJson.getDouble(playerValueKeys[3]))
                        .tp(playerJson.getDouble(playerValueKeys[4]));

                if (withAdditional) {
                    builder.withAdditional(playerValueKeys[5], playerJson.getDouble(playerValueKeys[5]));
                }

                players[i] = builder.build();
            }

            return players;
        } catch (JSONException e) {
            System.err.println(e.getLocalizedMessage());
            return new Player[0];
        }
    }

}
