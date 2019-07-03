package se.claremont.taf.json;

import org.json.JSONArray;
import org.json.JSONObject;
import se.claremont.taf.core.logging.LogLevel;
import se.claremont.taf.core.testcase.TestCase;

import java.util.ArrayList;
import java.util.List;

public class Json {

    private TestCase testCase;
    public String json;

    public Json(TestCase testCase, String json){
        this.json = json;

        if(testCase == null)
            testCase = new TestCase();
        this.testCase = testCase;
    }

    public JsonVerificationMethods verify(){
        return new JsonVerificationMethods(testCase, this);
    }

    public String get(String parameterName){
        JSONObject object;
        if(!isJson()) return null;
        String returnValue = null;
        try {
            object = new JSONObject(json);
        }catch (Exception e){
            testCase.log(LogLevel.DEBUG, "Could not get value for parameter '" + parameterName + "' from JSON string '" + json + "'. " + e.toString());
            return null;
        }
        try {
            returnValue = object.getString(parameterName);
        }catch (Exception e){
            testCase.log(LogLevel.DEBUG, "Could not get value for parameter '" + parameterName + "' from JSON string '" + json + "'. " + e.toString());
        }
        testCase.log(LogLevel.DEBUG, "Read value '" + returnValue + "' for JSON parameter '" + parameterName + "'.");
        return returnValue;
    }

    @SuppressWarnings("unused")
    public boolean isJson(){
        if(json == null)return false;
        try {
            @SuppressWarnings("unused")
            JSONObject object = new JSONObject(json);
            return true;
        }catch (Exception e){
            return  false;
        }
    }

    public Integer getInt(String parameterName){
        if(!isJson()) return null;

        if(json == null || parameterName == null) return null;
        JSONObject object;
        try {
            object = new JSONObject(json);
        }catch (Exception e){
            testCase.log(LogLevel.DEBUG, "Could not get value for parameter '" + parameterName + "' from JSON string '" + json + "'. " + e.toString());
            return null;
        }
        Integer returnValue = null;
        try{
            returnValue = object.getInt(parameterName);
        }catch (Exception e){
            testCase.log(LogLevel.DEBUG, "Could not get value for parameter '" + parameterName + "' from JSON string '" + json + "'. " + e.toString());
        }
        testCase.log(LogLevel.DEBUG, "Read value '" + returnValue + "' for JSON parameter '" + parameterName + "'.");
        return returnValue;
    }


    @SuppressWarnings("unused")
    public List<String> childObjects(String parentParameter){
        ArrayList<String> returnString = new ArrayList<>();
        JSONObject object = new JSONObject(json);
        JSONArray children = object.getJSONArray(parentParameter);
        for(Object child : children){
            returnString.add(child.toString());
        }
        return returnString;
    }

    @SuppressWarnings("unused")
    public String ToHtml(){
        StringBuilder html = new StringBuilder();
        String tempjson = json.replace(System.lineSeparator(), "");
        String[] parts = tempjson.split("}");
        for(String part : parts){
            String[] values = part.split(",");
            for(String value : values){
                html.append(value).append(",<br>").append(System.lineSeparator());
            }
            html.append("}<br>").append(System.lineSeparator());
        }
        return html.toString();
    }

}
