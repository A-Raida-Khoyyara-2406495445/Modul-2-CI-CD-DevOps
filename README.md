# Tutorial 1: Coding Standards

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

# Tutorial 2: CI/CD &DevOps 

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


## Kesimpulan
Dari modul 2 ini, saya tidak hanya memperbaiki kualitas kode dan memastikan konsistensi standar pengembangan, tetapi juga mendapat pengalaman dalam mengelola pipeline CI/CD dan mengintegrasikan GitHub Actions dan SonarCloud.
Implementasi ini menunjukkan bahwa otomatisasi pengujian, analisis kualitas, dan deployment dapat meningkatkan reliability serta efisiensi dalam pengembangan perangkat lunak.