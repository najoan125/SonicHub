package com.hyfata.sonichub;

import com.hyfata.file.utils.FileUtil;
import com.hyfata.json.JsonReader;
import com.hyfata.json.exceptions.JsonEmptyException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Objects;

public class BotConfig {
    private final String defaultConfig = FileUtil.readFromInputStream(Objects.requireNonNull(SonicHub.class.getResourceAsStream("/reference.json")));
    private JSONObject config;
    private boolean loadSuccess = true;

    private String token, owner;

    private void loadJson(File file) throws JsonEmptyException, IOException {
        config = JsonReader.readFromFile(file.toString());
        token = config.getString("token");
        owner = config.getString("owner");
    }

    private void checkData() {
        JSONObject defaultJson = new JSONObject(defaultConfig);
        for (String key : config.keySet()) {
            if (config.getString(key).isEmpty() || config.getString(key).equals(defaultJson.getString(key))) {
                loadSuccess = false;
            }
        }
    }

    public void load() throws IOException, JsonEmptyException {
        File file = new File(System.getProperty("user.dir"), "config.json");
        if (!file.exists()) {
            Files.write(file.toPath(), defaultConfig.getBytes());
            loadSuccess = false;
        } else {
            loadJson(file);
            checkData();
        }
    }

    public boolean isLoadSuccess() {
        return loadSuccess;
    }

    public String getToken() {
        return token;
    }

    public String getOwner() {
        return owner;
    }
}
