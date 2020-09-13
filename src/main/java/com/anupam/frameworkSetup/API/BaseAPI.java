package com.anupam.frameworkSetup.API;

import net.minidev.json.JSONObject;

import java.io.*;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.PathNotFoundException;

public class BaseAPI {
    static Properties properties;
    static Map<String, File> files;

    /*
      Load property from given property file
     */
    public static void loadPropertiesFile(final String proprtyFileName) throws IOException {

        // Change this
        final String propertyFileLocation =
                System.getProperty("user.dir") + File.separator + "src/main/resources" + File.separator + "properties";

        final FileReader reader = new FileReader(propertyFileLocation + File.separator + proprtyFileName);
        properties = new Properties();
        properties.load(reader);
    }

    /*
      Load property from given property file
     */
    public Properties loadPropertiesFile(final String proprtyFileName, Properties properties) throws IOException {

        // TO DO :Need to change this code
        final String propertyFileLocation = System.getProperty("user.dir") + File.separator + "src/main/resources"
                + File.separator + "properties";

        final FileReader reader = new FileReader(propertyFileLocation + File.separator + proprtyFileName);
        properties = new Properties();
        properties.load(reader);
        return properties;
    }

    public Object getProperty(final String property) {
        return properties.get(property);
    }

    public Set<Map.Entry<Object, Object>> getPropertySet() {
        return properties.entrySet();
    }

    public Set<Map.Entry<Object, Object>> getPropertySet(final Properties properties) {
        return properties.entrySet();
    }

    public JSONObject insertValues(JSONObject jsonObject, final String fieldJsonPath, final Object value) {
        try {
            if (!(value == null) && value.equals("missing")) {
                jsonObject = JsonPath.parse(jsonObject).delete(fieldJsonPath).json();
                return jsonObject;
            }
            JsonPath.parse(jsonObject).set(fieldJsonPath, value).json();
        } catch (final PathNotFoundException e) {
            if (value == null || !value.equals("missing")) {
                String jsonpath = fieldJsonPath;
                final String key = jsonpath.substring(jsonpath.lastIndexOf(".") + 1);
                jsonpath = jsonpath.substring(0, jsonpath.lastIndexOf("."));
                JsonPath.parse(jsonObject).put(jsonpath, key, value).json();
            }
        }
        return jsonObject;
    }

    public JSONObject insertValues(final JSONObject jsonObject, final Map<String, Object> map) {
        if (map != null) {
            for (final Map.Entry<String, Object> param : map.entrySet()) {
                if (param.getValue() instanceof String || param.getValue() instanceof Number
                        || param.getValue() instanceof Boolean || param.getValue() == null
                        || param.getValue() instanceof Object) {
                    insertValues(jsonObject, param.getKey(), param.getValue());
                }
            }
        }
        return jsonObject;
    }


}
