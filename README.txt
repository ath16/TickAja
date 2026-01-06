===========================================================================
                PANDUAN INSTALASI & MENJALANKAN APLIKASI
                           TICKAJA (KELOMPOK 4)
===========================================================================

Aplikasi ini adalah Sistem Pemesanan Tiket Bioskop berbasis Desktop (JavaFX)
yang terintegrasi dengan database MySQL.

---------------------------------------------------------------------------
1. PERSYARATAN SISTEM (PREREQUISITES)
---------------------------------------------------------------------------
Pastikan komputer Anda sudah terinstall software berikut:
1. Java Development Kit (JDK) versi 21 atau lebih baru.
2. Apache Maven (untuk build tool).
3. MySQL Server (XAMPP / MySQL Workbench).
4. Koneksi Internet (untuk mengunduh dependency Maven pertama kali).

---------------------------------------------------------------------------
2. KONFIGURASI DATABASE
---------------------------------------------------------------------------
Sebelum menjalankan aplikasi, Anda WAJIB menyiapkan database terlebih dahulu.

1. Buka aplikasi manajemen database Anda (phpMyAdmin / MySQL Workbench).
2. Buat database baru (opsional, script di bawah otomatis membuatnya).
3. Import file SQL yang telah disediakan:
   - Lokasi File: database/db_tickaja.sql
   - Cara Import:
     a. Buka file 'db_tickaja.sql' di teks editor.
     b. Copy seluruh isinya.
     c. Paste ke SQL Editor di phpMyAdmin/Workbench dan jalankan (Execute).

4. Konfigurasi Koneksi Java:
   - Buka file: src/main/java/pbo/kelompok4/util/KoneksiDatabase.java
   - Pastikan username dan password sesuai dengan MySQL lokal Anda.
     Contoh:
     private static final String USER = "root";
     private static final String PASSWORD = ""; // Kosongkan jika pakai XAMPP default

---------------------------------------------------------------------------
3. CARA MENJALANKAN APLIKASI
---------------------------------------------------------------------------
Ada dua cara untuk menjalankan aplikasi ini:

CARA A: MENGGUNAKAN TERMINAL / COMMAND PROMPT (Disarankan)
1. Buka Terminal atau Command Prompt.
2. Arahkan ke folder root proyek ini (folder tempat file pom.xml berada).
3. Ketik perintah berikut untuk membersihkan build lama dan menjalankan baru:
   
   mvn clean javafx:run

4. Tunggu hingga proses build selesai dan aplikasi muncul.

CARA B: MENGGUNAKAN VISUAL STUDIO CODE
1. Buka folder proyek ini di VS Code.
2. Pastikan Extension Pack for Java sudah terinstall.
3. Buka file 'src/main/java/pbo/kelompok4/App.java'.
4. Klik tombol "Run" atau "Play" yang ada di atas kode main.

---------------------------------------------------------------------------
4. AKUN DEMO (LOGIN)
---------------------------------------------------------------------------
Gunakan akun berikut untuk menguji fitur aplikasi:

A. AKUN ADMIN (Full Akses: Kelola Film, Jadwal, User, Laporan)
   - Username : admin
   - Password : admin123

B. AKUN USER (Akses: Lihat Film, Booking Tiket, Riwayat)
   - Username : user
   - Password : user123

   *Anda juga bisa mendaftar akun baru melalui menu Register di aplikasi.

---------------------------------------------------------------------------
5. TROUBLESHOOTING (JIKA ADA ERROR)
---------------------------------------------------------------------------
1. "Connection Refused / Communications Link Failure":
   - Pastikan MySQL (XAMPP) sudah dinyalakan.
   - Cek kembali username/password di file KoneksiDatabase.java.

2. Gambar Poster Tidak Muncul:
   - Pastikan folder 'images' ada di dalam:
     src/main/resources/pbo/kelompok4/images/
   - Lakukan "Clean Build" dengan perintah: mvn clean javafx:run

3. Error "Release version 21 not supported":
   - Pastikan JDK yang Anda gunakan minimal versi 21. Cek dengan perintah: java -version

===========================================================================
Terima kasih telah mencoba aplikasi TickAja!
Kelompok 4 - Pemrograman Berorientasi Objek
===========================================================================