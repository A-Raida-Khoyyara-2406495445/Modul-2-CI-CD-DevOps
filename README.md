```
Nama : Raida Khoyyara
NPM : 2406495445 
Kelas : Pemrograman Lanjut-A
```
# Notes
SonarCloud main branch dikonfigurasi pada branch `ci-cd` (bukan `main`)
karena keterbatasan SonarCloud Free Plan yang tidak memperbolehkan
pergantian main branch. Quality Gate analysis dapat dilihat di branch `ci-cd`
pada SonarCloud dashboard.

<details>
<summary>Tutorial 1: Coding Standards</summary>

## Reflection 1
### Clean Code Principles
- Meaningful Names: menamai variabel dan fungsi dengan jelas. Contoh: findById, delete, productData. Orang lain baca langsung paham.
- Single Responsibility Principle (SRP): memisahkan tugas dengan rapi.
    - Controller: Cuma urus web/permintaan user.
    - Service: Urus logika bisnis.
    - Repository: Urus data (simpan/hapus).
    - Penjelasan: tidak menumpuk semua kodingan di satu file.
- Logika delete yang sangat simpel (removeIf), tidak berbelit-belit.

### Secure Coding Practices
- UUID for ID: menggunakan UUID.randomUUID() untuk membuat ID produk (contoh: a1b2-c3d4...).
  Aman, karena ID-nya acak dan panjang, jadi orang iseng ga gampang menebak ID produk lain (misal ganti URL /delete/1 jadi /delete/2).
- Method HTTP yang Benar: pakai GET hanya untuk melihat data. Pakai POST untuk mengubah data (Create, Edit, Delete).
  Ini mencegah perubahan data yang tidak disengaja kalau link-nya cuma diklik biasa.

### Code Fixes
Masalah pas ngerjain tutorial ini salahsatunya pas awal menggunakan Field Injection (@Autowired langsung di variabel).
Itu membuat kode susah dites (unit testing) dan melanggar prinsip dependency injection.
Lalu untuk perbaikan aku ubah menjadi Constructor Injection (pakai public ProductServiceImpl(...)).
Ini lebih aman dan direkomendasikan oleh Spring Boot.
</details>

<details>
<summary>Tutorial 2: CI/CD &DevOps</summary>
## Reflection 2

### Code Quality Issues yang Diperbaiki
1. Linter Issues (Indentation Mismatch)
   **Deskripsi:**  
   Terdapat peringatan dari linter terkait indentasi pada beberapa file Java. Standar yang digunakan adalah 4 spasi, namun default indentasi di intelj belum sesuai.
   **Strategi Perbaikan:**  
   Saya melakukan konfigurasi ulang pada pengaturan Code Style di IntelliJ IDEA untuk memastikan penggunaan 4 spasi sebagai standar indentasi. Selanjutnya, saya merapikan seluruh file agar konsisten sebelum melakukan push ke repositori.

2. Dashboard Access "Not Allowed" (Configuration Issue)
   **Deskripsi:**  
   Terjadi ketidaksinkronan antara default branch di GitHub dan konfigurasi di SonarCloud, sehingga laporan kualitas kode tidak dapat diakses dengan benar.
   **Strategi Perbaikan:**  
   Saya mengatur ulang default branch di GitHub menjadi `main`(sebelumnya ci-cd), menghapus konfigurasi proyek lama di SonarCloud, dan melakukan re-import repository agar struktur branch terbaca dengan benar sejak awal.

### Refleksi CI/CD
Menurut saya, implementasi saat ini telah memenuhi definisi Continuous Integration (CI) dan Continuous Deployment (CD).
Dari sisi Continuous Integration, setiap perubahan kode yang di-push ke repository secara otomatis memicu GitHub Actions untuk menjalankan unit test dan analisis kualitas kode. Hal ini memastikan bahwa perubahan baru tidak merusak fitur yang sudah ada dan tetap menjaga standar kualitas kode.
Dari sisi Continuous Deployment, setelah seluruh tahapan CI berhasil dilewati, aplikasi secara otomatis di-deploy ke platform PaaS (Koyeb) tanpa intervensi manual. Dengan demikian, setiap perubahan yang valid dapat langsung tersedia di lingkungan produksi secara cepat dan konsisten.

### Kesimpulan
Dari modul 2 ini, saya tidak hanya memperbaiki kualitas kode dan memastikan konsistensi standar pengembangan, tetapi juga mendapat pengalaman dalam mengelola pipeline CI/CD dan mengintegrasikan GitHub Actions dan SonarCloud.
Implementasi ini menunjukkan bahwa otomatisasi pengujian, analisis kualitas, dan deployment dapat meningkatkan reliability serta efisiensi dalam pengembangan perangkat lunak.
</details>

<details>
<summary>Tutorial 3: OO Principles & Software Maintainability</summary>

# Reflection 3
# Penerapan SOLID Principle dan Clean Code
## 1. Single Responsibility Principle (SRP)
### Masalah
- CarController digabung atau melakukan extends ProductController, sehingga satu file menangani dua entitas berbeda.
- CarRepository memiliki banyak tanggung jawab:
    - Mengelola penyimpanan data
    - Membuat UUID
    - Mengatur update atribut satu per satu

### Perbaikan
- Memisahkan CarController ke file sendiri.
- Menghapus extends ProductController.
- Memindahkan logika pembuatan UUID ke model atau saat instansiasi objek.
- Repository hanya fokus pada penyimpanan dan pengambilan data.

## 2. Open-Closed Principle (OCP)
### Masalah
Method `update()` pada repository mengatur atribut satu per satu seperti:
- `setCarName()`, `setCarColor()`, dll
Jika ada atribut baru pada model `Car`, repository harus dimodifikasi.

### Perbaikan
- Method `update()` diubah agar menerima objek `Car` yang sudah diperbarui secara utuh.
- Repository tidak lagi mengatur atribut satu per satu.

## 3. Liskov Substitution Principle (LSP)
### Masalah
`CarController` melakukan `extends ProductController`, padahal secara konsep berbeda entitas dan routing.

### Perbaikan
- Menghapus pewarisan (`extends ProductController`).
- `CarController` berdiri secara independen.

## 4. Interface Segregation Principle (ISP)
### Penerapan
- Menggunakan `CarService` sebagai interface.
- `CarController` hanya bergantung pada kontrak method yang tersedia di interface.
- Implementasi detail berada di `CarServiceImpl`.

## 5. Dependency Inversion Principle (DIP)
### Masalah
- Menggunakan `@Autowired` langsung pada field (Field Injection).
- Ketergantungan langsung pada kelas konkret (`CarServiceImpl`, `CarRepository`,dll).

### Perbaikan
- Mengganti Field Injection menjadi Constructor Injection.
- Menggunakan keyword `final` pada dependency.
- Mengubah tipe variabel menjadi interface.


## Optimalisasi Repository
### Generalisasi Method Update
Repository menerima objek `Car` yang sudah diperbarui sepenuhnya tanpa mengatur atribut satu per satu.
### Relokasi Logika ID
Logika UUID dipindahkan dari repository ke model atau saat instansiasi objek.

## Peningkatan Clean Code
### Penghapusan Code Smell
- Menghapus unused import.
- Menghapus dead code.
### Keamanan Log
- Menghapus `System.out.println()` untuk menghindari Security Hotspot pada SonarCloud.

# Advantages of Applying SOLID Principles
- Maintainability: Kode lebih mudah dirawat. Jika saya ingin menambah fitur pada Mobil, saya tidak akan tidak sengaja merusak fitur Produk.
- Testability: Dengan Constructor Injection, saya bisa melakukan unit testing dengan mudah menggunakan @MockitoBean untuk menggunakan mock dependencies tanpa harus menjalankan seluruh konteks Spring.
- Flexibility: Dengan adanya interface pada Repository, aplikasi ini siap jika suatu saat ingin beralih dari penyimpanan In-Memory List ke Database SQL/JPA tanpa harus mengubah logika di level Service atau Controller.

# Disadvantages of Not Applying SOLID Principles
Tanpa prinsip SOLID, proyek ini akan mengalami beberapa masalah:
- Rigidity (Kekakuan): Kode menjadi sulit diubah karena satu perubahan kecil akan memaksa perubahan di banyak tempat lain (seperti masalah OCP pada repository).
- Fragility (Kerapuhan): Menjalankan aplikasi menjadi berisiko. Memperbaiki bug di satu tempat berpotensi memicu bug baru di tempat yang tidak berhubungan (seperti masalah LSP pada controller).
- Difficulty in Testing: Field injection mempersulit proses isolasi kelas saat pembuatan unit test, yang mengakibatkan code coverage sulit dicapai secara maksimal.
</details>

