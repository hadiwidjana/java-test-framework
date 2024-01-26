package JavaDemo.Integrations.Listener;

import JavaDemo.Integrations.Logger.Log;
import JavaDemo.Integrations.SlackNotif.SlackNotif;
import JavaDemo.FE.MobileBaseMethod;
import JavaDemo.Integrations.AllureReport.AllureAttachments;
import JavaDemo.Integrations.AllureReport.AllureGenerate;
import JavaDemo.Integrations.Netlify.NetlifyAPI;
import JavaDemo.Integrations.TestRail.TestRailAPI;
import JavaDemo.Integrations.Utils.ScreenRecord;
import io.qameta.allure.listener.TestLifecycleListener;
import io.qameta.allure.model.TestResult;
import org.testng.*;

import java.net.UnknownHostException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static org.zeroturnaround.zip.ZipUtil.pack;

public class CustomListener implements ITestListener, ISuiteListener, IInvokedMethodListener, IExecutionListener, TestLifecycleListener {

    public static List<ITestNGMethod> passedTests = new ArrayList<>();
    public static List<ITestNGMethod> failedTests = new ArrayList<>();
    public static List<ITestNGMethod> skippedTests = new ArrayList<>();
    private static long startTime;
    private static long endTime;


    /*This belongs to ISuiteListener and will execute before the Suite Starts*/
    @Override
    public void onStart(ISuite arg0) {
        Reporter.log("Execution of the Suite '" + arg0.getName() + "' Started!", false);
    }


    /*This belongs to ISuiteListener and will execute after the Suite Ends*/
    @Override
    public void onFinish(ISuite arg0) {
        Reporter.log("Execution of the Suite '" + arg0.getName() + "' Completed!", false);
    }


    /*This belongs to ITestListener, It will execute at the time of Test Execution */
    @Override
    public void onStart(ITestContext arg0) {
//        testRailAPI.clientInit();
        Reporter.log("About to begin executing Test " + arg0.getName(), false);
    }


    /*This belongs to ITestListener, It will execute at the End of Test*/
    @Override
    public void onFinish(ITestContext arg0) {
        Reporter.log("Completed executing test " + arg0.getName(), false);
    }




    /*This belongs to ITestListener, It will Execute only when the Test is PASSED*/
    @Override
    public void onTestSuccess(ITestResult result) {
        TestRailAPI.postTestRail(result);
        passedTests.add(result.getMethod());
        Log.infoGreen("SUCCESSFULLY EXECUTED TEST: " + result.getTestClass().getName() + "." + result.getMethod().getMethodName());
    }


    /*This belongs to ITestListener, It will Execute only when the Test is FAILED*/
    @Override
    public void onTestFailure(ITestResult result) {
        TestRailAPI.postTestRail(result);
        failedTests.add(result.getMethod());
        Log.infoRed("FAILED TEST: " + result.getTestClass().getName() + "." + result.getMethod().getMethodName());
    }


    /*This belongs to ITestListener, It and will execute before the Main Test Starts (@Test)*/
    @Override
    public void onTestStart(ITestResult result) {
        try {
            Log.infoBlue("STARTED TESTING: " + result.getTestClass().getName() + "." + result.getMethod().getMethodName());
            if(AllureAttachments.recordOnPassed | AllureAttachments.recordOnFailure) {
                if (AllureAttachments.deviceName.contains("android") || AllureAttachments.deviceName.contains("ios")) MobileBaseMethod.startRecordingMobile();
                else ScreenRecord.startRecording(returnMethodName(result.getMethod()));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /*This belongs to ITestListener, It will execute only if any of the Main Test(@Test) Gets Skipped*/
    @Override
    public void onTestSkipped(ITestResult result) {
        TestRailAPI.postTestRail(result);
        skippedTests.add(result.getMethod());
        Log.infoYellow("SKIPPED TEST: " + result.getTestClass().getName() + "." + result.getMethod().getMethodName() + "\n");

    }

    @Override
    public void beforeTestStop(TestResult result) {
        if(AllureAttachments.recordOnPassed | AllureAttachments.recordOnFailure) {
            try {
                switch (result.getStatus()) {
                    case PASSED -> {
                        if (AllureAttachments.deviceName.contains("android") || AllureAttachments.deviceName.contains("ios"))
                            MobileBaseMethod.stopRecordingMobile(AllureAttachments.recordOnPassed);
                        else ScreenRecord.stopRecording(AllureAttachments.recordOnPassed, true, true);
                    }
                    default -> {
                        if (AllureAttachments.deviceName.contains("android") || AllureAttachments.deviceName.contains("ios"))
                            MobileBaseMethod.stopRecordingMobile(AllureAttachments.recordOnFailure);
                        else ScreenRecord.stopRecording(AllureAttachments.recordOnFailure, true, true);
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }



    /*This belongs to IInvokedMethodListener, It will execute before every method Including @Before @After @Test*/
    public void beforeInvocation(IInvokedMethod arg0, ITestResult arg1) {
        String textMsg = "About to begin executing following method : " + returnMethodName(arg0.getTestMethod());
        Reporter.log(textMsg, false);
    }


    /*This belongs to IInvokedMethodListener and will execute after every method Including @Before @After @Test*/
    public void afterInvocation(IInvokedMethod arg0, ITestResult arg1) {
        String textMsg = "Completed executing following method : " + returnMethodName(arg0.getTestMethod());
        Reporter.log(textMsg, false);
    }


    /*This will return method names to the calling function*/
    private String returnMethodName(ITestNGMethod method) {
        return method.getRealClass().getSimpleName() + "." + method.getMethodName();
    }


    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
    }

    @Override
    public void onExecutionStart() {
        startTime = System.currentTimeMillis();
    }

    @Override
    public void onExecutionFinish() {

        endTime = System.currentTimeMillis();


        //deploy allure report
        if(NetlifyAPI.DEPLOY_ALLURE.contains("on")){
            try {
                NetlifyAPI.deployAllure();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }


        //slack notification
        if (SlackNotif.SLACK_EN.toLowerCase().equals("on")) {
            try {
                SlackNotif.sendSlackNotification(SlackNotif.WEB_HOOK, getTestSummary());
            } catch (UnknownHostException e) {
                throw new RuntimeException(e);
            }
        }

        if(NetlifyAPI.DELETE_ALLURE_RESULT.toLowerCase().contains("on")) {
            AllureGenerate.delAllureResult();
        }
    }

    public static String getTestSummary() throws UnknownHostException {
        int passed = passedTests.size();
        int failed = failedTests.size();
        int skipped = skippedTests.size();
        int total = passed + failed + skipped;
        String duration = getExecutionTime(endTime - startTime);

        return SlackNotif.composeMessage(passed, failed, skipped, total, duration, getFailedTestsList());
    }


    private static String getExecutionTime(long millisecond) {
        long min = Duration.ofMillis(millisecond).toMinutes();
        long sec = Duration.ofMillis(millisecond).toSeconds()-(min*60);
        return min + " minute(s) " + sec + " seconds";
    }

    public static String getFailedTestsList() {
        StringBuilder builder = new StringBuilder();
        if (failedTests.size() > 0) {
            for (ITestNGMethod test : failedTests) {
                builder.append("\\n\\u2022 " + test.getMethodName());
            }
        } else {
            builder.append("\\n\\u2022 None");
        }
        return builder.toString();
    }

}
