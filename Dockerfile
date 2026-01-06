# --- Stage 1: Build (Pakai Java 21 SDK) ---
# Gunakan image JDK lengkap untuk proses build
FROM eclipse-temurin:21-jdk-jammy AS build

WORKDIR /app

# Copy semua file project ke dalam container
COPY . .

# Berikan izin eksekusi ke file gradlew (PENTING: kadang permission hilang saat copy)
RUN chmod +x gradlew

# Build aplikasi jadi file .jar
# Gunakan 'bootJar' untuk memastikan kita dapat executable jar (bukan plain jar)
# '-x test' untuk skip testing biar deploy lebih cepat
RUN ./gradlew clean bootJar -x test

# --- Stage 2: Run (Pakai Java 21 JRE) ---
# Gunakan image JRE yang lebih kecil untuk running (Alpine Linux)
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Ambil hasil build dari folder build/libs di stage 1
# Note: Spring Boot Gradle biasanya taruh jar di build/libs/
COPY --from=build /app/build/libs/*.jar app.jar

# Expose port 8181 sesuai request kamu
EXPOSE 8181

# Jalankan aplikasi
ENTRYPOINT ["java", "-jar", "app.jar"]