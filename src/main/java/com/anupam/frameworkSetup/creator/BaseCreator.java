package com.anupam.frameworkSetup.creator;

import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;

import java.io.*;
import java.net.URLEncoder;
import java.util.*;

import org.testng.Reporter;

import com.anupam.frameworkSetup.exception.FrameworkException;
import com.jayway.jsonpath.JsonPath;

public class BaseCreator {

    public String url = "";

    /**
     * This method creates JSONObject for give .json file.
     *
     * @param jsonFile
     *            File to be converted in JSONObject.
     * @return json JSONObject created from given file.
     */
    public JSONObject getJSONObject(final File jsonFile) {
        JSONObject json = null;
        try {

            final JSONParser parser = new JSONParser(JSONParser.MODE_JSON_SIMPLE);
            final FileReader fileReader = new FileReader(jsonFile);
            json = (JSONObject) parser.parse(fileReader);
            fileReader.close();
        } catch (IOException | ParseException e) {
            throw new FrameworkException(
                    "Exception while converting Json file to Json object" + jsonFile.toPath()
                            .toAbsolutePath());
        }

        return json;

    }

    /**
     * Get json object from String
     *
     * @param json
     *            in string form
     * @return json object
     */
    public JSONObject getJSONObject(final String json) {
        JSONObject jsonObject = null;
        try {
            final JSONParser parser = new JSONParser(JSONParser.MODE_JSON_SIMPLE);
            jsonObject = (JSONObject) parser.parse(json);

        } catch (final ParseException e) {
            Reporter.log(e.getMessage());
        }
        return jsonObject;
    }

    /**
     * This method creates the JSONObject from the given file and updates the
     * created object with provided properties created here if we want to take data from properties file,
     * Skipping this since its test
     *
     */
    public JSONObject createJSON(final File jsonFile, final Set<Map.Entry<Object, Object>> entries) {
        JSONObject json = null;
        json = getJSONObject(jsonFile);
        return updateJSONFromPropertyFile(json, entries);
    }

    /**
     * read json from Templatefile and create objects
     *
     */
    public JSONObject createJSON(final File jsonFile) {
        JSONObject json = null;
        json = getJSONObject(jsonFile);
        return json;
    }

    /**
     * This method updates proved JSONObject with the give set of properties.It
     * will iterate over each property from set and update the related property
     * in JSONObject.
     *
     * @param jsonObject
     *            JSONObject to be updated.
     * @param entries
     *            Set of properties that needs to be updated in JSONObject.
     * @return jsonObject Updated JSONObject.
     */
    public JSONObject updateJSONFromPropertyFile(final JSONObject jsonObject,
                                                 final Set<Map.Entry<Object, Object>> entries) {

        String json = jsonObject.toString();
        for (final Map.Entry entry : entries) {
            json = json.replaceAll("\\#" + entry.getKey().toString(), entry.getValue().toString());
        }
        return getJSONObject(json);
    }

    /**
     * Method to create queryString
     *
     * @param name
     *            Query parameter
     * @param value
     *            Query parameter value
     * @return class
     */
    public BaseCreator queryString(final String name, final Object value) {
        final StringBuilder queryString = new StringBuilder();
        if (this.url.contains("?")) {
            if (!this.url.endsWith("?")) {
                queryString.append("&");
            }
        } else {
            queryString.append("?");
        }
        try {
            queryString.append(name).append("=")
                    .append(URLEncoder.encode((value == null) ? "" : value.toString(), "UTF-8"));
        } catch (final UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        this.url += queryString.toString();
        return this;
    }

    /**
     * Method for adding query string e.g delete
     *
     * @param value
     *            of string to append
     * @return class
     */
    public BaseCreator queryString(final String value) {
        final StringBuilder queryString = new StringBuilder();
        try {
            if (!this.url.toString().endsWith("/")) {
                queryString.append("/").append(URLEncoder.encode((value == null) ? "" : value.toString(), "UTF-8"));
            } else {
                queryString.append(URLEncoder.encode((value == null) ? "" : value.toString(), "UTF-8"));
            }
        } catch (final UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        this.url += queryString.toString();
        return this;
    }

    /**
     * query string creator the set of key value pairs of params
     *
     * @param parameters
     *            map
     * @return query string
     */
    public String queryString(final Map<String, Object> parameters) {
        if (parameters != null) {
            for (final Map.Entry<String, Object> param : parameters.entrySet()) {
                if (param.getValue() instanceof String || param.getValue() instanceof Number || param
                        .getValue() instanceof Boolean) {
                    if (!param.getValue().equals("missing")) {
                        queryString(param.getKey(), param.getValue());
                    }

                } else if (param.getValue() instanceof ArrayList<?>) {
                    for (final String value : (ArrayList<String>) param.getValue()) {
                        queryString(param.getKey(), value);
                    }
                } else {
                    throw new RuntimeException("Parameter \"" + param
                            .getKey() + "\" can't be sent with a GET request because of type: " + param
                            .getValue().getClass().getName());
                }
            }
        }
        return "";
    }

    /**
     * query string creator the set of key value pairs of params
     *
     * @param parameters
     *            map
     * @param duplicateKey to include duplicate parameters.
     * @return query string
     */
    public String queryString(final Map<String, Object> parameters, final String duplicateKey) {
        if (parameters != null) {
            for (final Map.Entry<String, Object> param : parameters.entrySet()) {
                if (param.getValue() instanceof String || param.getValue() instanceof Number
                        || param.getValue() instanceof Boolean) {
                    if (!param.getValue().equals("missing")) {
                        queryString(duplicateKey, param.getValue());
                    }

                } else if (param.getValue() instanceof ArrayList<?>) {
                    for (final String value : (ArrayList<String>) param.getValue()) {
                        queryString(duplicateKey, value);
                    }
                } else {
                    throw new RuntimeException(
                            "Parameter \"" + param.getKey() + "\" can't be sent with a GET request because of type: "
                                    + param.getValue().getClass().getName());
                }
            }
        }
        return "";
    }

    /**
     * delete merchant json data with json path
     * @param path json path for finding json data to delete
     * @param json json object
     * @return Modified json object
     */

    public JSONObject deleteJsonByPath(final String path, final JSONObject json) {
        return JsonPath.parse(json).delete(path).json();
    }

    /**
     * delete multiple merchant json objects with json path
     * @param paths list of jsonpaths to be deleted
     * @param json json object
     * @return Modified json object
     */

    public JSONObject deleteJsonByPath(final ArrayList<String> paths, final JSONObject json) {

        for (final String path : paths) {
            JsonPath.parse(json).delete(path).json();
        }

        return json;
    }

    /**
     *
     * @param dataMap
     *          of jsonpath and value
     * @param json
     *          json in which values need to be changed
     * @return
     *          modified json
     */
    public JSONObject updateJsonByPath(final HashMap<String, String> dataMap, final JSONObject json) {

        final Iterator<Map.Entry<String, String>> it = dataMap.entrySet().iterator();
        while (it.hasNext()) {
            final Map.Entry<String, String> pair = it.next();
            JsonPath.parse(json).set(pair.getKey(), pair.getValue()).json();
            it.remove(); // avoids a ConcurrentModificationException

        }
        return json;
    }
    /**
     * Method for adding query string e.g delete
     *
     * @param parameters value of string to append
     * @return class
     */
    public BaseCreator queryStringWithoutEncoding(final Map<String, Object> parameters) {

        if (parameters != null) {
            for (final Map.Entry<String, Object> param : parameters.entrySet()) {
                final StringBuilder queryString = new StringBuilder();
                if (this.url.toString().contains("?")) {
                    queryString.append("&");
                } else {
                    queryString.append("?");
                }
                queryString.append(param.getKey() + "=").append(param.getValue() == null ? "" : param.getValue());
                this.url += queryString.toString();
            }
        }
        return this;
    }

    /**
     * Method for adding query string e.g delete
     *
     * @param name
     *            Query parameter
     * @param value
     *            Query parameter value
     * @return class
     */
    public BaseCreator queryStringWithoutEncoding(final String name, final Object value) {

        final StringBuilder queryString = new StringBuilder();
        if (this.url.toString().contains("?")) {
            queryString.append("&");
        } else {
            queryString.append("?");
        }
        queryString.append(name + "=").append(value == null ? "" : value);
        this.url += queryString.toString();

        return this;
    }
}
