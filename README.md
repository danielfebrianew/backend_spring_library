# 📚 Spring Boot Library API (Back-Office)

Ini adalah proyek REST API untuk sistem manajemen perpustakaan (back-office), yang dibangun sepenuhnya menggunakan **Spring Boot**.

Proyek ini sengaja dirancang untuk mendemonstrasikan fondasi Spring Boot tanpa menggunakan *Object-Relational Mapping* (ORM) seperti JPA/Hibernate. Sebagai gantinya, seluruh akses data dikelola secara manual menggunakan **`JdbcTemplate`** untuk kontrol penuh atas *raw query* SQL.

## ✨ Fitur Utama

* **Autentikasi & Otorisasi:** Sistem login/register penuh menggunakan **Spring Security** dan **JWT (JSON Web Tokens)**.
* **Sistem Berbasis Peran (Roles):** Perbedaan hak akses yang jelas antara `ROLE_ADMIN` dan `ROLE_USER` menggunakan `@PreAuthorize`.
* **Manajemen User (CRUD):** *Endpoint* untuk mengelola data pengguna.
* **Manajemen Buku (CRUD):** *Endpoint* admin untuk menambah, mengedit, menghapus, dan mencari buku (termasuk pencarian via `ISBN` atau `keyword`).
* **Siklus Peminjaman Lengkap:**
    * `User` bisa membuat request peminjaman (`PENDING`).
    * `Admin` bisa melihat daftar request dan menyetujuinya (`APPROVED`).
    * `Admin` bisa memproses pengembalian buku (`RETURNED`).
* **Manajemen Stok Otomatis:** Stok buku (`available_copies`) otomatis berkurang saat *Approve* dan bertambah saat *Return* menggunakan `@Transactional`.
* **Migrasi Database:** Skema database (PostgreSQL) dikelola secara profesional menggunakan **Flyway**.
* **Respons API Standar:** Semua respons API dibungkus dalam *wrapper* `ApiResponse` yang konsisten, lengkap dengan `status`, `message`, `data`, dan `timestamp`.

## 🛠️ Tumpukan Teknologi (Tech Stack)

* **Framework:** Spring Boot 3
* **Keamanan:** Spring Security (dengan `CustomUserDetailsService`)
* **Token:** JWT (JSON Web Tokens)
* **Database:** PostgreSQL
* **Akses Data:** **Spring `JdbcTemplate`** (Bukan JPA/Hibernate)
* **Migrasi:** Flyway
* **Build Tool:** Gradle
* **Lain-lain:** Lombok, `record` DTO