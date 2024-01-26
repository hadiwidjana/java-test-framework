# [GATE] AUTOMATION GOKOMODO FRAMEWORK
_ _ _
## Table of Content
- - -
<!-- TOC -->
  * [1.1 Schema Framework](#11-schema-framework)
  * [1.2 Support libraries:](#12-support-libraries)
    * [1.1.0 Installation:](#110installation)
  * [1.3 How To Use:](#13-how-to-use)
      * [1.3.1 Pull Project From GitHub](#131-pull-project-from-github)
      * [1.3.2 Create Branch](#132-create-branch)
      * [1.3.3 Open Project](#133-open-project-)
      * [1.3.4 Clean Project before Build](#134-clean-project-before-build)
      * [1.3.5 Build Project](#135-build-project-)
      * [1.3.6 Run some test script](#136-run-some-test-script)
      * [1.3.7 Create Report](#137-create-report)
<!-- TOC -->

- - -
## 1.1 Schema Framework
- - -
<img src="https://github.com/gokomodo/GATE/blob/Development/src/test/resources/asset/FrameWork%20Automation%20Update.jpg">

## 1.2 Support libraries:
- - -
Ini adalah framework Automation yang sudah terintegrasi dengan beberapa fitur
seperti:

| Feature Name       | Function                                                                                       | Support                                                                            |
|--------------------|:-----------------------------------------------------------------------------------------------|------------------------------------------------------------------------------------|
| Selenium           | Untuk Automation Web                                                                           | Firefox, Chrome, Opera, Safari, Chrome_Headless, firefox_headless, Chrome Devtools |
| Appium             | Untuk Mobile automation dan Web Mobile Automation                                              | Mobile : Android Ios. <br>Web: Firefox, Safari, Chrome</br>                        |
| RestAssured        | Untuk BE Test                                                                                  | REST, GRPC, GraphQL                                                                |
| Apache POI         | Untuk membuat, mengedit dan menghapus file excel                                               | -                                                                                  |
| Log4j              | Untuk menangkap log di local                                                                   | -                                                                                  |
| TestNG             | Untuk mengcompile test script ke test suite                                                    | -                                                                                  |
| Web Driver Manager | Sebagai penyedia browser manager agar tidak perlu mengkondisikan versi browser yang ada        | -                                                                                  |
| Allure Report      | Sebagai report automation yang di gunakan setelah selesai running test script                  | -                                                                                  |
| Joda               | Digunakan untuk men-generate date                                                              | -                                                                                  |
| Video Record       | Digunakan untuk me-record test step yang sedang berlangsung                                    | -                                                                                  |
| Assertion          | Digunakan untuk meng-compare yang value nya berupa string                                      | -                                                                                  |
| Faker              | Digunakan untuk mem-generake nama, alamat, email dll secara acak                               | -                                                                                  |
| JDBC               | Digunakan untuk mem-validasi sebuah data yang ada di database ke UI                            | -                                                                                  |
| Spring Boot        | Digunakan sebagai tailoring test script yang sudah ada dengan test script yang akan di develop | -                                                                                  |
| TestRail           | Untuk management test cases yang di integrasikan dengan script automation                      | -                                                                                  |
| AWS                | Diintegrasikan untuk kebutuhan menyimpan enviroment framework                                  | -                                                                                  |
| GITHUB             | Untuk mengcompile pekerjaan tim automation                                                     | -                                                                                  |
| JENKINS            | Untuk menjalankan crownjob dan CI                                                              | -                                                                                  |
| SLACK              | Untuk menampung notifikasi jika status automation pass atau tidak                              | -                                                                                  |
| JMETER             | Untuk kebutuhan Load test                                                                      | -                                                                                  |
| Google             | Digunakan untuk menggunakan, menyimpan dan menghapus beberapa data ke source google            | Spreadsheet, Word, Gmail.                                                          |
| Mailhog            | Untuk mengambil data email masuk                                                               |                                                                                    |


### 1.1.0 Installation:
- - -
https://gokomodo.atlassian.net/wiki/spaces/GA/pages/2024112365/Readme+Please+for+New+Joiner+Automation+Member


## 1.3 How To Use
- - -

Pada bagian ini pastikan sudah melakukan registrasi dan sudah masuk dalam project ini. jika belum terdaftar silahkan melakukan registrasi pada [link ini.](https://github.com/signup?ref_cta=Sign+up&ref_loc=header+logged+out&ref_page=%2F&source=header-home)
 - - -
+ #### 1.3.1 Pull Project From GitHub
- - -

1. Copy link project pada tombol bagian "Code", 
   lalu pilih "local" dan pilih icon "Copy". <br><img height="300" src="https://github.com/gokomodo/GATE/blob/Development/src/test/resources/asset/Asset1.png" width="300">
2. Setelah berhasil mengambil source lalu buka sourcetree untuk clone project, langkah -langkahnya sebagai berikut:
   - Pertama, buka Sourcetree.
   - Kedua, pilih "New".
   - Ketiga, pilih Clone From URL
<br><img height="300" src="https://github.com/gokomodo/GATE/blob/Development/src/test/resources/asset/Asset2.png" width="300">
   - Keempat, paste url tadi ke dalam Source URL, lalu akan secara otomatis akan ter-generate juga untuk source foldernya by defaultnya "GATE". Jika akan mengubah destination foldernya, bisa dengan tekan tombol "..." yang terdapat pada baris Destination Path.
<br><img height="300" src="https://github.com/gokomodo/GATE/blob/Development/src/test/resources/asset/Asset3.png" width="650">
   - Kelima, tekan tombol "Clone".
 - - -
   
+ #### 1.3.2 Create Branch
- - -
  Pada bagian ini ada 2 cara untuk membuat branch. 
  Pada cara pertama bisa mengakses pada [link ini](https://github.com/gokomodo/GATE), Lalu cara kedua bisa dengan menggunakan tools sourcetree.  <ul>1. Github: 
     <li> Buka main Repository "_Gate_".
     <li> Pilih menu "_Branch_". 
     <br> <img height="300" src="https://github.com/gokomodo/GATE/blob/Development/src/test/resources/asset/Asset6.png" width="700">
     <li>Pilih menu "_New Branch_".
     <br> <img height="300" src="https://github.com/gokomodo/GATE/blob/Development/src/test/resources/asset/Asset4.png" width="700">
     <li> Masukan nama branch. _Dengan ketentuan (nama_tribe)._
     <br> <img height="300" src="https://github.com/gokomodo/GATE/blob/Development/src/test/resources/asset/Asset5.png" width="700">
     <li> Lalu pilih menu "_Create Branch_".
     <br> <img height="300" src="https://github.com/gokomodo/GATE/blob/Development/src/test/resources/asset/Asset5.png" width="700">
- - -

+ #### 1.3.3 Open Project 
- - -
   Untuk open project silahkan buka project yang sudah di _clone_ dari github tadi ke dalam IDE IntellI, tunggu beberapa saat biasanya kalau yang baru melakukan _"clone project"_ akan sedikit menunggu lama di karenakan IDE sedang mengunduh beberapa _"library"_  yang di butuhkan, dan pastikan koneksi jaringan internet dalam keadaan stabil. 

- - -
+ #### 1.3.4 Clean Project before Build
- - -

| No. | Mac                                                                                                                                                                                                                                                                 | Windows                                                                                                                                                                                                                                                     |
|-----|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| 1   | Pada OS Mac bisa di lakukan dengan menekan tombol "Control" sebanyak 2x.                                                                                                                                                                                            | Pada OS Windows bisa di lakukan dengan menekan tombol Ctrl 2x.                                                                                                                                                                                              |
| 2   | Lalu ketikan `mvn clean`                                                                                                                                                                                                                                            | Lalu ketikan `mvn clean`                                                                                                                                                                                                                                    |
- - -
+ #### 1.3.5 Build Project 
- - - 

| Mac                                                                                                                                                                                                                                                                  | Windows                                                                                                                                                                                                                                                              |
|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Jika proses pada 1.3.4 sudah selesai, tekan tombol<img height="20" src="https://github.com/gokomodo/GATE/blob/Development/src/test/resources/asset/Asset7.png" width="20"/> Tunggu hingga proses build selesai. Jika terdapat error build dapat menghubungi SDET core Automation. | Jika proses pada 1.3.4 sudah selesai, tekan tombol<img height="20" src="https://github.com/gokomodo/GATE/blob/Development/src/test/resources/asset/Asset7.png" width="20"> Tunggu hingga proses build selesai. Jika terdapat error build dapat menghubungi SDET core Automation. |         |
- - -
+ #### 1.3.6 Run test script
- - -
Pada bagian ini dapat dilakukan dengan beberapa cara diantaranya:
 1. Menjalankan single test script;
    <br>a. Buka pada folder "Test" > "Java" > "Gokomodo" > "FE" > "WEB" > "test" > "Eproc" > "Forget Password". 
    <br>b. Lalu pilih salah satu dari test script Forget Password <br>(contoh: <img height="100" src="https://github.com/gokomodo/GATE/blob/Development/src/test/resources/asset/Asset8.png" width="500"/>  )
    <br>c. Dan tekan tombol  <img height="10" src="https://github.com/gokomodo/GATE/blob/Development/src/test/resources/asset/Asset9.jpeg" width="10"/> 
 2. Menjalankan di Test Suite (TestNG);
    <br> a. Buka folder "TestRunner" > "Eproc" > "Eproc.xml"
    <br> b. Click kanan, lalu pilih <img height="16" src="https://github.com/gokomodo/GATE/blob/Development/src/test/resources/asset/Asset10.jpeg" width="300"/>
 3. Menggunakan Command tools dengan spesifik test suite;
    <br> a. Tekan logo <img height="17" src="https://github.com/gokomodo/GATE/blob/Development/src/test/resources/asset/Asset11.jpeg" width=""/>
    <br> b. lalu ketik "mvn clean test -Dsurefire.suiteXmlFiles=TestRunner/Integration/Integration.xml". Dan tekan Enter.
    <br> <img src="https://github.com/gokomodo/GATE/blob/Development/src/test/resources/asset/Asset12.jpeg"/>
 4. Menjalankan keseluruhan test script;
    <br> a. Tekan logo <img height="17" src="https://github.com/gokomodo/GATE/blob/Development/src/test/resources/asset/Asset11.jpeg" width=""/>
    <br> b. Lalu ketik `mvn clean test` dan tekan "Enter"
- - -

+ #### 1.3.7 Create Report
- - -
Untuk membuat Allure Report, pastikan pada 1.3.6 sudah berjalan dengan baik walaupun status testnya  pass ataupun failed, untuk command yang di gunakan membuat report ketik `allure serve` pada terminal Intellij.
- - -






