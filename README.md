# Wahid Nur Hakim | 2509116016 | Sistem Informasi A'25

# 🕌 Rumah Quran Al-Hafizh - Sistem Manajemen Data

## 📋 Deskripsi Singkat Program

<p align="justify">
Program ini adalah sistem manajemen berbasis konsol untuk mengelola data Rumah Quran Al-Hafizh. Sistem ini dirancang untuk memudahkan administrator dalam mengelola tiga entitas utama: santri, kelas mengaji, dan pengajar. 
</p>

<p align="justify">
Dengan antarmuka menu yang interaktif, pengguna dapat melakukan operasi CRUD (Create, Read, Update, Delete) seperti menambah data baru, melihat daftar data, mengubah data yang sudah ada, hingga menghapus data. Program ini juga dilengkapi fitur pencarian global yang memungkinkan pengguna mencari data santri atau pengajar berdasarkan nama, nomor telepon, atau kelas.
</p>

<p align="justify">
Dibangun dengan menerapkan prinsip-prinsip Object-Oriented Programming (OOP) seperti encapsulation, inheritance, polymorphism, dan abstraction, program ini memiliki struktur kode yang terorganisir, mudah dipelihara, dan dapat dikembangkan lebih lanjut.
</p>

---

## 📁 Struktur Package

<p align="justify">
Program menggunakan pembagian package berdasarkan tanggung jawab untuk memisahkan model data, tampilan, dan alur pengelolaan data. Integrasi database JDBC ditempatkan pada package config dan dao, sedangkan implementasi tambahan Hibernate ORM ditempatkan pada package orm. Pembagian ini menjaga struktur OOP yang sudah dibuat dan memudahkan pemeliharaan program.
</p>

| Package | File | Fungsi |
|---------|------|--------|
| `main` | `MiniProject4.java` | Titik masuk aplikasi utama, melakukan pengecekan koneksi database, dan menjalankan menu program |
| `controller` | `CrudUmmi.java` | Mengatur alur CRUD, validasi input, pencarian, dan pemanggilan DAO |
| `model` | `RumahQuran.java` | Abstract class yang menyediakan atribut serta method umum bagi Santri dan Pengajar |
| `model` | `Santri.java` | Model data santri yang memiliki umur dan relasi ke kelas mengaji |
| `model` | `Pengajar.java` | Model data pengajar beserta informasi kelas mengajar |
| `model` | `KelasMengaji.java` | Model data kelas yang menyimpan kode, nama kelas, pengajar, dan kapasitas |
| `model` | `CariData.java` | Interface yang mendefinisikan kontrak pencocokan data melalui method cocokDengan() |
| `view` | `Menu.java` | Menampilkan menu, membaca input pengguna, dan menampilkan pesan |
| `config` | `Koneksi.java` | Menyediakan koneksi JDBC ke database MySQL |
| `config` | `TestKoneksi.java` | Class pengujian terpisah untuk memeriksa koneksi database |
| `model.dao` | `SantriDAO.java` | Menjalankan query JDBC untuk tambah, baca, update kelas, hapus, dan pencarian data santri |
| `model.dao` | `PengajarDAO.java` | Menjalankan query JDBC untuk tambah, baca, update kelas mengajar, hapus, dan pencarian data pengajar |
| `model.dao` | `KelasMengajiDAO.java` | Menjalankan query JDBC untuk CRUD kelas serta menghitung jumlah santri dalam kelas |
| `orm` | `PengajarEntity.java` | Memetakan objek pengajar ke tabel pengajar menggunakan annotation Hibernate |
| `orm` | `TestORM.java` | Mengatur konfigurasi Hibernate dan menyediakan menu pengujian CRUD ORM pada data pengajar |

---

## 🔄 Alur Program

<p align="justify">
Program dimulai dengan menampilkan menu utama yang berisi opsi untuk mengelola data santri, kelas mengaji, pengajar, mencari data, atau keluar.
</p>

<p align="justify">
Ketika pengguna memilih menu pengelolaan data, program masuk ke submenu dengan fitur CRUD (Create, Read, Update, Delete). Setiap input divalidasi sebelum diproses jika tidak valid, pengguna diminta mengisi ulang. Data yang valid langsung diproses dan hasilnya ditampilkan dalam bentuk pesan atau tabel.
</p>

<p align="justify">
Setelah setiap operasi selesai, program kembali ke submenu hingga pengguna memilih kembali ke menu utama. Program berakhir saat pengguna memilih opsi keluar dengan menampilkan ucapan penutup.
</p>

---

## 🔒 Penerapan Encapsulation dan Inheritance

### **Encapsulation**

<p align="justify">
Encapsulation diterapkan dengan membuat semua atribut di setiap kelas menjadi private, sehingga tidak dapat diakses langsung dari luar kelas. Akses terhadap atribut tersebut hanya dapat dilakukan melalui method getter dan setter yang disediakan.
</p>

<p align="justify">
Setiap method setter dilengkapi dengan validasi untuk memastikan data yang masuk sesuai dengan aturan yang ditetapkan. Misalnya, umur santri harus antara 5 sampai 17 tahun, nomor telepon harus berupa angka sebanyak 10 sampai 15 digit, dan kapasitas kelas harus antara 1 sampai 30. Jika data tidak valid, program akan melempar exception atau meminta pengguna untuk mengisi ulang.
</p>

<p align="center">
<img width="848" alt="Encapsulation" src="https://github.com/user-attachments/assets/fd19d18e-c2c3-4a4e-abe9-de23cb1a5ced" />
<br>
  <em>Contoh encapsulation pada kelas Santri dengan atribut private dan getter/setter</em>
</p>

**Manfaat Encapsulation:**
- ✅ Data terlindungi dari akses langsung
- ✅ Validasi data dapat dilakukan di setter
- ✅ Kode lebih mudah dipelihara

---

### **Inheritance**
  
               RumahQuran
          - nama, nomorTelepon
           /                 \
      Santri               Pengajar
     - umur                - mengajar
     - kelasMengaji

<p align="justify">
Inheritance (pewarisan) diterapkan pada paket model, yaitu class RumahQuran sebagai superclass yang diturunkan ke dua subclass, Santri dan Pengajar.
</p>

<p align="center">
<img width="547" height="165" alt="Screenshot 2026-09-24 215816" src="https://github.com/user-attachments/assets/c121ce40-ba42-4d42-86f4-d58bf9ce8596" />
<br>
  <em>Class RumahQuran</em>
</p>

<p align="justify">
Class RumahQuran menyimpan atribut yang sama-sama dimiliki santri dan pengajar, yaitu nama dan nomorTelepon, lengkap dengan getter dan setter-nya, sehingga kode tidak perlu ditulis ulang di setiap subclass.
</p>

<p align="center">
<img width="768" height="204" alt="image" src="https://github.com/user-attachments/assets/44f5c5a3-cc48-4608-983b-205b11a87a02" />
<br>
  <em>Class Santri</em>
</p>

<p align="center">
<img width="634" height="168" alt="image" src="https://github.com/user-attachments/assets/732c4077-7cb0-4038-8151-cf670a3fa85c" />
<br>
  <em>Class Pengajar</em>
</p>

<p align="justify">
Class Santri dan Pengajar memakai kata kunci extends RumahQuran dan memanggil constructor superclass melalui super(nama, nomorTelepon), lalu menambahkan atribut khusus masing-masing: Santri memiliki umur dan kelasMengaji, sedangkan Pengajar memiliki mengajar.
</p>

**Manfaat Inheritance:**
- ✅ **Reusability**: Atribut `nama` dan `nomorTelepon` tidak perlu ditulis ulang
- ✅ **Konsistensi**: Semua entitas punya struktur dasar yang sama
- ✅ **Polymorphism**: Bisa perlakukan `Santri` dan `Pengajar` sebagai `RumahQuran`

---

## 🎭 Penerapan Polymorphism dan Abstraction

### **Polymorphism**

#### 1. **Method Overloading**

<p align="justify">
Method Overloading dapat dilihat pada kelas KelasMengaji di mana method setPengajar() didefinisikan dalam dua versi dengan parameter yang berbeda. Versi pertama menerima parameter bertipe String untuk mengatur pengajar berdasarkan nama saja, sedangkan versi kedua menerima parameter bertipe objek Pengajar untuk relasi yang lebih kuat antar objek. Overloading ini memberikan fleksibilitas kepada developer untuk memilih cara yang paling sesuai dengan konteks penggunaan.
</p>

<p align="center">
<img width="722" height="292" alt="image" src="https://github.com/user-attachments/assets/139571eb-3521-4739-a148-08d8333abb8a" />
<br>
  <em>Penerapan Method Overloading pada Class Pengajar</em>
</p>

---

#### 2. **Method Overriding**

<p align="justify">
Method Overriding terjadi ketika kelas Santri dan Pengajar meng-override abstract method getJenisPengguna() dari parent class RumahQuran. Meskipun nama method sama, implementasinya berbeda di setiap kelas—kelas Santri mengembalikan string "Santri", sedangkan kelas Pengajar mengembalikan string "Pengajar". Hal ini memungkinkan objek dari kedua kelas tersebut dapat diperlakukan secara polymorphic sebagai objek bertipe RumahQuran.
</p>

<p align="center">
<img width="313" height="87" alt="image" src="https://github.com/user-attachments/assets/48509bdb-745d-45da-9c6e-0da2dfdecfd7" />
<br>
  <em>Penerapan Method Overriding</em>
</p>

---

### **Abstraction**

<p align="center">
<img width="555" height="209" alt="image" src="https://github.com/user-attachments/assets/6f663379-141b-41f3-9e27-990c2801b72e" />
<br>
  <em>Abstract class RumahQuran dengan abstract method getJenisPengguna()</em>
</p>

<p align="justify">
Abstract class RumahQuran mendefinisikan struktur dasar yang harus dimiliki oleh setiap pengguna sistem, yaitu atribut nama dan nomorTelepon, serta abstract method getJenisPengguna() yang tidak memiliki implementasi di parent class. Abstract method ini memaksa setiap child class untuk mengimplementasikan method tersebut sesuai dengan karakteristik masing-masing. Hal ini menyembunyikan kompleksitas implementasi dari user dan hanya menampilkan apa yang perlu diketahui.
</p>

---

### **Interface**

<p align="center">
<img width="389" height="60" alt="image" src="https://github.com/user-attachments/assets/8069ee17-6efe-434a-9162-1372841ddbe9" />
<br>
  <em>Interface CariData/setter</em>
</p>

<p align="center">
<img width="562" height="34" alt="image" src="https://github.com/user-attachments/assets/ff68f6ca-a39f-49a7-818a-31fd4910937d" />
<br>
  <em>Implementasi interface CariData</em>
</p>

<p align="center">
<img width="527" height="191" alt="image" src="https://github.com/user-attachments/assets/014e8bec-523e-4cb1-be09-b93b6bcb548c" />
<br>
  <em>Implementasi method cocokDengan() pada interface CariData</em>
</p>

<p align="justify">
Interface CariData mendefinisikan kontrak yang harus dipenuhi oleh kelas yang mengimplementasikannya, yaitu method cocokDengan(String kataKunci). Interface ini diimplementasikan oleh kelas Santri dan Pengajar untuk memungkinkan fitur pencarian global. Dengan interface, program mencapai loose coupling di mana kelas yang menggunakan interface tidak perlu tahu detail implementasi dari kelas yang mengimplementasikannya.
</p>

---

## 🗄️ Penerapan JDBC dan Database

Program Rumah Quran Al-Hafizh telah diintegrasikan dengan database **MySQL** menggunakan **JDBC (Java Database Connectivity)**. JDBC digunakan sebagai penghubung antara aplikasi Java dan database, sehingga data pengajar, kelas mengaji, dan santri tidak lagi tersimpan sementara di dalam `ArrayList`, tetapi tersimpan secara permanen di MySQL.

Dengan penerapan JDBC, data yang ditambahkan, diubah, atau dihapus melalui aplikasi akan langsung diproses pada database. Data tersebut tetap tersedia meskipun aplikasi ditutup dan dijalankan kembali. Integrasi ini juga menggunakan pola **DAO (Data Access Object)** untuk memisahkan query database dari class controller dan menu, sehingga struktur program tetap rapi serta penerapan OOP tetap terjaga.

### 🔗 Konfigurasi Koneksi Database

Koneksi database dikelola oleh class `Koneksi` pada package `config`. Class ini menyimpan konfigurasi URL database, username, dan password, kemudian menyediakan method `getConnection()` untuk membuat koneksi ke MySQL menggunakan `DriverManager`.

<p align="center">
<img width="759" height="403" alt="image" src="https://github.com/user-attachments/assets/3b0c20fe-15d6-435a-8b3e-ef6c53d90ac3" />
</p>

<p align="center">
  <em>Class <code>Koneksi</code> yang mengatur koneksi antara program Java dan database MySQL.</em>
</p>

Program juga melakukan pengecekan koneksi di awal melalui class utama `MiniProject4`. Jika koneksi berhasil, program menampilkan status bahwa database siap digunakan dan menu utama dijalankan. Jika koneksi gagal, program menampilkan penyebab kegagalan dan menghentikan proses agar operasi CRUD tidak berjalan dalam kondisi database tidak tersedia.

<p align="center">
<img width="549" height="382" alt="image" src="https://github.com/user-attachments/assets/8380a19e-c8c4-43bb-871f-abe0f308fd8f" />
</p>

<p align="center">
  <em>Pengecekan koneksi database pada awal program sebelum menu utama ditampilkan.</em>
</p>

---

### 📦 Dependency MySQL Connector/J

Program menggunakan dependency **MySQL Connector/J** melalui Maven. Dependency ini berfungsi sebagai driver JDBC agar aplikasi Java dapat berkomunikasi dengan MySQL. Konfigurasi dependency ditempatkan pada file `pom.xml`, sehingga Maven dapat mengunduh dan mengelola library database secara otomatis.

<p align="center">
<img width="420" height="120" alt="image" src="https://github.com/user-attachments/assets/52bffab8-8b12-44fb-b1cf-a29ce3548568" />
</p>

<p align="center">
  <em>Dependency MySQL Connector/J pada file <code>pom.xml</code> untuk menghubungkan Java dengan MySQL.</em>
</p>

---

### 🧩 Penerapan DAO

Program menerapkan pola **DAO (Data Access Object)** dengan membuat class `SantriDAO`, `PengajarDAO`, dan `KelasMengajiDAO` pada package `dao`. Setiap class DAO bertanggung jawab untuk menjalankan query database sesuai entitasnya.

Pemisahan ini membuat class `CrudUmmi` tetap berfokus pada alur program, validasi input, dan komunikasi dengan menu, sedangkan proses SQL seperti tambah, tampil, ubah, hapus, dan pencarian diletakkan di DAO. Dengan demikian, query database tidak ditulis di class `Menu` ataupun class model.

<p align="center">
<img width="229" height="84" alt="image" src="https://github.com/user-attachments/assets/b9d2cdbd-1034-4eef-bf38-c5db2ab693ad" />
</p>

<p align="center">
  <em>Package <code>dao</code> yang memisahkan operasi database berdasarkan entitas Santri, Pengajar, dan Kelas Mengaji.</em>
</p>

---

### 🔐 Penggunaan PreparedStatement

Seluruh query yang menerima input pengguna menggunakan `PreparedStatement`. Query SQL menggunakan placeholder `?`, kemudian nilai input dimasukkan melalui method seperti `setString()` dan `setInt()` sebelum query dijalankan.

Penggunaan `PreparedStatement` membuat query lebih aman dan terstruktur karena nilai input diproses sebagai parameter, bukan digabungkan langsung ke dalam teks SQL. Hal ini juga memudahkan penggunaan query yang sama dengan nilai berbeda untuk operasi tambah, ubah, hapus, dan pencarian.

<p align="center">
<img width="671" height="505" alt="image" src="https://github.com/user-attachments/assets/af7594e6-278d-4dbc-ab77-bfce9261701c" />
</p>

<p align="center">
  <em>Contoh penggunaan <code>PreparedStatement</code> pada DAO untuk menjalankan query dengan parameter input.</em>
</p>

---

### 🔄 Operasi CRUD dan Pencarian

Fitur CRUD pada program telah dihubungkan ke database MySQL melalui DAO:

| Operasi | DAO yang digunakan | Fungsi |
|---|---|---|
| Create | `insert()` | Menambahkan data santri, pengajar, atau kelas ke MySQL |
| Read | `getAll()` dan `getById()` | Mengambil dan menampilkan data dari MySQL |
| Update | `updateKelas()`, `updateMengajar()`, atau `update()` | Memperbarui data yang dipilih |
| Delete | `delete()` | Menghapus data berdasarkan ID atau kode kelas |
| Search | `search()` | Mencari data berdasarkan nama, nomor telepon, atau kelas |

Sebagai contoh, fitur tambah santri terlebih dahulu memvalidasi nama, umur, nomor telepon wali, dan kelas yang dipilih. Setelah input valid, program membuat objek `Santri`, lalu memanggil method `insert()` pada `SantriDAO`. DAO kemudian menyimpan data tersebut ke tabel `santri` di MySQL.

<p align="center">
<img width="550" height="168" alt="image" src="https://github.com/user-attachments/assets/8f2ef7c4-0544-4478-b138-7db3bb50d3c4" />
</p>

<p align="center">
  <em>Class <code>CrudUmmi</code> memanggil <code>SantriDAO</code> untuk menyimpan data santri ke database.</em>
</p>

---

### 🛡️ Penanganan Error dan Resource

Setiap operasi database dibungkus dengan `try-catch` untuk menangani `SQLException`. Jika terjadi kegagalan koneksi, tabel tidak ditemukan, query salah, atau data masih memiliki relasi dengan tabel lain, program menampilkan pesan error yang jelas dan tidak menampilkan pesan keberhasilan.

Selain itu, program menggunakan `try-with-resources` untuk menutup objek `Connection`, `PreparedStatement`, dan `ResultSet` secara otomatis setelah query selesai. Cara ini membantu mencegah resource database tetap terbuka atau koneksi bocor ketika program mengalami error.

<p align="center">
<img width="615" height="171" alt="image" src="https://github.com/user-attachments/assets/daf7441b-dfbf-41dd-a82c-f658ac6a6d62" />
</p>

<p align="center">
  <em>Penggunaan <code>try-with-resources</code> untuk menutup resource serta menangani error database secara aman.</em>
</p>

<p align="center">
<img width="608" height="65" alt="image" src="https://github.com/user-attachments/assets/e1dbacca-a76e-4623-8155-48141d39265c" />
</p>

<p align="center">
  <em>Penanganan SQLException pada controller untuk menampilkan pesan yang jelas ketika operasi database gagal.</em>
</p>

---

### 🧱 Struktur Database dan Relasi

Database menggunakan tiga tabel utama: `pengajar`, `kelas_mengaji`, dan `santri`. Tabel `kelas_mengaji` memiliki foreign key `pengajar_id` yang merujuk ke tabel `pengajar`, sedangkan tabel `santri` memiliki foreign key `kode_kelas` yang merujuk ke tabel `kelas_mengaji`.

Relasi ini sesuai dengan model OOP pada program: satu kelas mengaji memiliki satu pengajar, dan satu santri berada pada satu kelas mengaji. Foreign key juga membantu menjaga konsistensi data, misalnya kelas tidak dapat dihapus apabila masih digunakan oleh data santri.

<p align="center">
<img width="267" height="183" alt="image" src="https://github.com/user-attachments/assets/c21a0e08-a0a2-4923-85c5-fb1b4f922c80" />
</p>

<p align="center">
  <em>Gambar 8: Struktur tabel database MySQL beserta relasi antara pengajar, kelas mengaji, dan santri.</em>
</p>

---

### ✅ Persistensi Data

Setelah integrasi database, data utama tidak lagi dibuat ulang dari constructor `CrudUmmi`. Data dummy awal dimasukkan melalui file SQL, kemudian seluruh data dibaca dari MySQL saat fitur tampil data atau pencarian dijalankan.

Dengan pendekatan ini, data tetap tersedia setelah aplikasi ditutup dan dijalankan ulang. Hal tersebut membuktikan bahwa aplikasi telah menerapkan persistensi data menggunakan MySQL.

---

## ⭐ Penjelasan Letak Penerapan Nilai Tambah

## ⚙️ Penerapan ORM dengan Hibernate

Program menggunakan Hibernate ORM sebagai fitur tambahan untuk mengelola data pengajar melalui objek Java. Implementasi ORM dijalankan melalui `TestORM`, sedangkan aplikasi utama `MiniProject4` tetap menggunakan JDBC dan DAO. Keduanya mengakses database `db_rq_alhafizh` yang sama.

---

### 📦 Dependency dan Konfigurasi

Dependency `hibernate-core` ditambahkan pada `pom.xml`. Konfigurasi koneksi dan pendaftaran entity ditempatkan pada `TestORM`, kemudian digunakan untuk membuat `SessionFactory`. Properti `hibernate.hbm2ddl.auto=validate` memeriksa kesesuaian mapping tanpa membuat ulang tabel, sedangkan `hibernate.show_sql=false` menyembunyikan query SQL dari console.

<p align="center">
<img width="434" height="128" alt="image" src="https://github.com/user-attachments/assets/8793a837-8cce-4ea2-a9ea-668d3d9cc8bf" />
</p>

<p align="center">
  <em>Dependency Hibernate ORM pada file pom.xml.</em>
</p>

<p align="center">
<img width="475" height="763" alt="image" src="https://github.com/user-attachments/assets/07a0af0b-1ee4-41ab-9519-3050a249c82f" />
</p>

<p align="center">
  <em>Konfigurasi Hibernate untuk mengakses MySQL dan mendaftarkan PengajarEntity.</em>
</p>

---

### 🧩 Pemetaan Entity

Class `PengajarEntity` dipetakan ke tabel `pengajar` menggunakan annotation `@Entity`, `@Table`, dan `@Column`. Primary key ditentukan melalui `@Id`, sedangkan `@GeneratedValue` dengan strategi `IDENTITY` mengikuti pembentukan ID melalui `AUTO_INCREMENT` pada MySQL. Entity ini dibuat terpisah agar model OOP dan DAO JDBC yang sudah ada tetap dipertahankan.

<p align="center">
<img width="600" height="358" alt="image" src="https://github.com/user-attachments/assets/086d9b9e-fc03-4c24-85e0-cd2ca231b4f6" />
</p>

<p align="center">
  <em>Pemetaan atribut PengajarEntity ke tabel pengajar.</em>
</p>

---

### 🔄 CRUD dan Penanganan Error

Penambahan data menggunakan `persist()`, pembacaan menggunakan `find()` atau HQL, dan penghapusan menggunakan `remove()`. Update dilakukan dengan mengubah entity yang sedang dikelola session, lalu menyimpan perubahan melalui `commit()`.

Operasi dijalankan dalam transaksi dan menggunakan `rollback()` ketika gagal. Program juga menangani input tidak valid, ID yang tidak ditemukan, serta penghapusan pengajar yang masih digunakan oleh kelas. Pesan berhasil hanya ditampilkan setelah commit selesai.

<p align="center">
<img width="661" height="317" alt="image" src="https://github.com/user-attachments/assets/3b419e40-097b-45e7-806a-9a09f3bb363d" />
</p>

<p align="center">
  <em>Contoh penyimpanan pengajar melalui persist(), commit, dan penanganan kegagalan transaksi.</em>
</p>

---

### ✅ Pengujian dan Integrasi JDBC

Pengujian dilakukan melalui menu `TestORM` dan diverifikasi langsung di MySQL. Pengujian mencakup tambah, baca, update, hapus, validasi, dan persistensi setelah program dijalankan ulang.

Karena menggunakan tabel yang sama, data yang disimpan melalui ORM dapat dibaca melalui menu pengajar pada aplikasi JDBC. Hasil setiap pengujian dicatat sesuai kondisi aktual.

<p align="center">
<img width="365" height="130" alt="image" src="https://github.com/user-attachments/assets/578c9606-5a88-48a1-aaf8-7f17b6c245e4" />
</p>

<p align="center">
  <em>Output penambahan pengajar melalui Hibernate ORM.</em>
</p>

<p align="center">
<img width="361" height="113" alt="image" src="https://github.com/user-attachments/assets/bc66f75e-3f3c-495e-8a42-3f3d42acf090" />
</p>

<p align="center">
  <em>Verifikasi data pengajar yang disimpan melalui ORM pada MySQL.</em>
</p>

---

### ⚖️ Perbedaan JDBC dan ORM pada Program

| Aspek | JDBC + DAO | Hibernate ORM |
|---|---|---|
| Peran | Implementasi utama aplikasi | Pengembangan tambahan |
| Class pengujian/utama | `MiniProject4` | `TestORM` |
| Cakupan data | Santri, kelas mengaji, dan pengajar | Pengajar |
| Akses database | SQL melalui `PreparedStatement` | Entity, HQL, dan operasi `Session` |
| Mapping data | Manual melalui `ResultSet` | Annotation pada `PengajarEntity` |
| Database | `db_rq_alhafizh` | Database yang sama |
