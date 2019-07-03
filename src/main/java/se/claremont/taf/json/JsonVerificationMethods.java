package se.claremont.taf.json;

import org.json.JSONObject;
import se.claremont.taf.core.logging.LogLevel;
import se.claremont.taf.core.testcase.TestCase;

public class JsonVerificationMethods {

    private Json json;
    private TestCase testCase;

    public JsonVerificationMethods(TestCase testCase, Json json){
        if(testCase == null) testCase = new TestCase();
        this.testCase = testCase;
        this.json = json;
    }

    @SuppressWarnings("unused")
    public JsonVerificationMethods mandatoryFieldIsNotEmpty(String mandatoryParameterName){
        if(json == null || json.json == null) testCase.log(LogLevel.VERIFICATION_FAILED, "Content was null when trying to verify that mandatory parameter '" + mandatoryParameterName + "' was not empty.");
        if(mandatoryParameterName == null) testCase.log(LogLevel.VERIFICATION_PROBLEM, "mandatoryParameterName was null when trying to verify that mandatory parameter was not empty.");
        JSONObject object;
        boolean parameterNameExist = true;
        String parameterValue = null;
        try {
            object = new JSONObject(json.json);
            if(!object.has(mandatoryParameterName)){
                parameterNameExist = false;
            } else {
                parameterValue = object.get(mandatoryParameterName).toString();
            }
        }catch (Exception e){
            testCase.log(LogLevel.FRAMEWORK_ERROR, "Could not create JSONObject from content '" + json.json + "'.");
        }
        if(parameterValue != null && parameterValue.length() > 0){
            testCase.log(LogLevel.VERIFICATION_PASSED, "Mandatory parameter '" + mandatoryParameterName + "' was populated with content '" + parameterValue + "'.");
        }else {
            if(parameterNameExist){
                testCase.log(LogLevel.VERIFICATION_FAILED, "Mandatory parameter '" + mandatoryParameterName + "' had no content. Mandatory parameters cannot be empty.");
            } else {
                testCase.log(LogLevel.VERIFICATION_FAILED, "Mandatory parameter '" + mandatoryParameterName + "' is missing. Mandatory fields should be populated.");
            }
            testCase.log(LogLevel.DEVIATION_EXTRA_INFO, "JSON content for where the parameter '" + mandatoryParameterName + "' was looked for: '" + json.json + "'.");
        }
        return this;
    }

}
