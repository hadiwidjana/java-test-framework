package com.satria.javatestframework.utils.Integration.TestRail;

import org.testng.ITestContext;
import org.testng.ITestResult;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TestRailAPI
{
    protected static APIClient client = null;

    protected static String PROJECT_ID = "3";

    public static void clientInit(ITestContext ctx) throws APIException, IOException {
        // TODO make this read from settings/config file
        client = new APIClient("https://qagokomodo.testrail.io");
        client.setUser("gunar.kasimir@gokomodo.com");
        client.setPassword("Gokomodo2022!");
    }

    public static void postTestRail(ITestResult result, ITestContext ctx) throws APIException, IOException {
        Map data = new HashMap();
        if(result.isSuccess()) {
            data.put("status_id",1);
            data.put("custom_tested_by",12);
        }
        else {
            data.put("status_id", 5);
            data.put("comment", result.getThrowable().toString());
            data.put("custom_tested_by",12);
        }

        String caseId = (String)ctx.getAttribute("caseId");
        String runId = (String)ctx.getAttribute("runId");
        client.sendPost("add_result_for_case/"+runId+"/"+caseId,data);
    }

}
