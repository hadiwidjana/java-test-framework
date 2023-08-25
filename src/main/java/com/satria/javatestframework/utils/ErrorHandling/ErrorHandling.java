package com.satria.javatestframework.utils.ErrorHandling;

import org.openqa.selenium.*;

public class ErrorHandling {


    public static void elementErrorHandling(Exception ErrorType) {

        var documentation = "https://gokomodo.atlassian.net/wiki/spaces/QA/pages/2076966992/Automation+Framework";
        var dictionary = "https://gokomodo.atlassian.net/wiki/spaces/QA/pages/2076672075/Dictionary+Method";
        var error = "https://gokomodo.atlassian.net/wiki/spaces/QA/pages/2078998549/Dictionary+Error+Log";
        var xpath = "https://gokomodo.atlassian.net/wiki/spaces/QA/pages/2079391798/Dictionary+Xpath";

        var errorInfo = "";

        switch (ErrorType.getClass().getSimpleName()) {
            case "NoSuchElementException" -> {
//                JOptionPane.showMessageDialog(null, " Check Error log di bagian (CHECK ERROR NYA DI BAWAH INI)", "Running Automationya GAGAL!!!: ", JOptionPane.INFORMATION_MESSAGE);
                errorInfo =
                        "\n=============================== CHECK ERROR NYA DI SINI =====================================\n" +
                                "#############################################################################################\n\n" +
                                "             - XPATH / ID TIDAK SESUAI, CHECK LAGI XPATH ATAU ID NYA! -\n\n" +
                                "Reference         :" + "  " + documentation + "\n" +
                                "Dictionary Method :" + "    " + dictionary + "\n" +
                                "Dictionary Error  :" + "    " + error + "\n" +
                                "Dictionary Xpath  :" + "    " + xpath + "\n" +
                                "-----                                                                                     -----\n" +
                                "===============================================================================================\n";
                throw new NoSuchElementException(errorInfo);
            }
            case "NoSuchWindowException" -> {
                errorInfo =
                        "\n=============================== CHECK ERROR NYA DI SINI =====================================\n" +
                                "#############################################################################################\n\n" +
                                "                   - GAGAL PINDAH HALAMAN, CHECK KEMBALI CODE NYA! -\n\n" +
                                "Reference         :" + "  " + documentation + "\n" +
                                "Dictionary Method :" + "    " + dictionary + "\n" +
                                "Dictionary Error  :" + "    " + error + "\n" +
                                "Dictionary Xpath  :" + "    " + xpath + "\n" +
                                "-----                                                                                     -----\n" +
                                "===============================================================================================\n";
                throw new NoSuchWindowException(errorInfo);
            }
            case "TimeoutException" -> {
                errorInfo =
                        "\n=============================== CHECK ERROR NYA DI SINI =====================================\n" +
                                "#############################################################################################\n\n" +
                                "           - KONEKSI TERGANGGU, CHECK KEMBALI KONEKSI ANDA DAN RUNNING LAGI! -\n" +
                                "Reference         :" + "  " + documentation + "\n" +
                                "Dictionary Method :" + "    " + dictionary + "\n" +
                                "Dictionary Error  :" + "    " + error + "\n" +
                                "Dictionary Xpath  :" + "    " + xpath + "\n" +
                                "-----                                                                                     -----\n" +
                                "===============================================================================================\n";
                throw new TimeoutException(errorInfo);
            }
            case "ElementNotInteractableException" -> {
                errorInfo =
                        "\n=============================== CHECK ERROR NYA DI SINI =====================================\n" +
                                "#############################################################################################\n\n" +
                                "            - ELEMENT TIDAK DAPAT BERINTERAKSI DENGAN ELEMENT YANG ANDA BUAT -\n" +
                                " Reference:" + "  " + documentation + " -\n" +
                                "Dictionary Method :" + "    " + dictionary + "\n" +
                                "Dictionary Error  :" + "    " + error + "\n" +
                                "Dictionary Xpath  :" + "    " + xpath + "\n" +
                                "-----                                                                                     -----\n" +
                                "===============================================================================================\n";
                throw new ElementNotInteractableException(errorInfo);
            }
            case "NoSuchFrameException" -> {
                errorInfo =
                        "\n=============================== CHECK ERROR NYA DI SINI =====================================\n" +
                                "#############################################################################################\n\n" +
                                "               - FRAME TIDAK DAPAT TERBUKA! CEK ULANG CODINGNYA -\n" +
                                "Reference:" + "  " + documentation + " -\n" +
                                "Dictionary Method :" + "    " + dictionary + "\n" +
                                "Dictionary Error  :" + "    " + error + "\n" +
                                "Dictionary Xpath  :" + "    " + xpath + "\n" +
                                "-----                                                                                     -----\n" +
                                "===============================================================================================\n";
                throw new NoSuchFrameException(errorInfo);
            }
            case "ElementClickInterceptedException" -> {
                errorInfo =
                        "\n=============================== CHECK ERROR NYA DI SINI =====================================\n" +
                                "#############################################################################################\n\n" +
                                "               - ELEMENT TERTUTUP OLEH ELEMENT LAIN -\n" +
                                "Reference:" + "  " + documentation + " -\n" +
                                "Dictionary Method :" + "    " + dictionary + "\n" +
                                "Dictionary Error  :" + "    " + error + "\n" +
                                "Dictionary Xpath  :" + "    " + xpath + "\n" +
                                "-----                                                                                     -----\n" +
                                "===============================================================================================\n";
                throw new ElementClickInterceptedException(errorInfo);
            }
            default -> ErrorType.printStackTrace();
        }
    }
}
