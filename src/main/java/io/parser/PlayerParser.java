package io.parser;

import data.Player;
import io.file.FileHandler;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

public class PlayerParser {

    private PlayerParser() {
    }

    public static JSONObject getPlayersJsonFromFile(
            String filePath,
            String[] keys
    ) {
        try {
            List<String> lines = FileHandler.readLines(filePath);
            JSONObject json = new JSONObject();
            JSONArray players = new JSONArray();
            JSONObject player = new JSONObject();

            int nonNumericalKeysAmount = 2;
            String[] numericalKeys = new String[keys.length - nonNumericalKeysAmount];
            System.arraycopy(keys, nonNumericalKeysAmount, numericalKeys, 0, numericalKeys.length);

            int dataIndex = 0;
            for (String line : lines) {
                line = line.trim();

                if (line.isBlank() ||
                        line.matches("[i!]") ||
                        line.startsWith("Player")) {
                    continue;
                }

                if (dataIndex > 2) {
                    players.put(player);
                    player.clear();
                    dataIndex = 0;
                }

                switch (dataIndex) {
                    case 0 -> player.put(keys[0], line);
                    case 1 -> player.put(keys[1], line);
                    case 2 -> {
                        String[] parts = line.split("[ \t]");

                        player.put(numericalKeys[0], Double.parseDouble(parts[0]));
                        // ignore the 4th column as we don't care about Sel. %
                        try {
                            for (int i = 2; i < parts.length; ++i) {
                                player.put(numericalKeys[i - 1], Double.parseDouble(parts[i]));
                            }
                        } catch (Exception e) {
                            System.err.println(e.getLocalizedMessage());
                            return null;
                        }
                    }
                    default -> {
                        System.err.println("Something went wrong.");
                        return null;
                    }
                }

                ++dataIndex;
            }

            json.put("players", players);
            return json;
        } catch (IOException e) {
            System.err.println(e.getLocalizedMessage());
            return null;
        }
    }

    public static Player[] getPlayersFromJson(
            JSONObject json,
            boolean withAdditional,
            String[] keys
    ) {
        try {
            JSONArray playersJson = json.getJSONArray("players");
            Player[] players = new Player[playersJson.length()];

            for (int i = 0; i < playersJson.length(); ++i) {
                JSONObject playerJson = playersJson.getJSONObject(i);
                Player.Builder builder = new Player.Builder();

                builder.name(playerJson.getString(keys[0]))
                        .team(playerJson.getString(keys[1]))
                        .salary(playerJson.getDouble(keys[2]))
                        .form(playerJson.getDouble(keys[3]))
                        .tp(playerJson.getDouble(keys[4]));

                if (withAdditional) {
                    builder.withAdditional(keys[5], playerJson.getDouble(keys[5]));
                }

                players[i] = builder.build();
            }

            return players;
        } catch (Exception e) {
            System.err.println(e.getLocalizedMessage());
            return null;
        }
    }

}
