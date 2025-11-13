// Kita butuh 2 library ini
import java.util.Scanner;
import java.util.ArrayList;

public class KasirJava {

    // --- VARIABEL GLOBAL (STATIC) ---
    // 'static' berarti variabel ini bisa diakses dari semua method static
    // Ini adalah pengganti database kita
    static ArrayList<String> listKode = new ArrayList<>();
    static ArrayList<String> listNama = new ArrayList<>();
    static ArrayList<Double> listHarga = new ArrayList<>();
    static ArrayList<Integer> listStok = new ArrayList<>();

    // Variabel untuk kasir dan tanggal
    static String namaKasir;
    static String tanggalLengkap;
    static int tanggalHari; // Hanya angka (untuk ganjil/genap)

    // Variabel untuk melacak penjualan
    static double totalPendapatanHarian = 0;
    static int nomorPelanggan = 0;

    // Scanner global agar bisa dipakai di semua method
    static Scanner scanner = new Scanner(System.in);


    /**
     * Ini adalah method utama, program dimulai dari sini.
     */
    public static void main(String[] args) {
        setupAwal();
        isiDataBarangAwal(); // Otomatis mengisi 10 barang
        menuUtama();
    }

    /**
     * 1. Halaman awal: Meminta tanggal dan nama kasir
     */
    static void setupAwal() {
        System.out.println("--- Selamat Datang di Program Kasir ---");
        System.out.print("Masukkan Nama Kasir: ");
        namaKasir = scanner.nextLine();
        
        System.out.print("Masukkan Tanggal Hari Ini (misal: 7 November 2025): ");
        tanggalLengkap = scanner.nextLine();

        // Loop validasi untuk meminta angka tanggal
        while (true) {
            try {
                System.out.print("Masukkan Tanggal (HANYA ANGKA, 1-31): ");
                tanggalHari = Integer.parseInt(scanner.nextLine());
                if (tanggalHari >= 1 && tanggalHari <= 31) {
                    break; // Angka valid, keluar loop
                } else {
                    System.out.println("Tanggal tidak valid. Harap masukkan antara 1 dan 31.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Input tidak valid. Harap masukkan angka saja.");
            }
        }
        System.out.println("\nSetup Selesai. Selamat bekerja, " + namaKasir + "!");
    }

    /**
     * Method untuk mengisi data 10 barang awal
     */
    static void isiDataBarangAwal() {
        listKode.add("001"); listNama.add("Buku Tulis"); listHarga.add(5000.0); listStok.add(100);
        listKode.add("002"); listNama.add("Pulpen Pilot"); listHarga.add(3000.0); listStok.add(150);
        listKode.add("003"); listNama.add("Pensil 2B"); listHarga.add(2500.0); listStok.add(200);
        listKode.add("004"); listNama.add("Penghapus"); listHarga.add(1500.0); listStok.add(300);
        listKode.add("005"); listNama.add("Penggaris 30cm"); listHarga.add(4000.0); listStok.add(80);
        listKode.add("006"); listNama.add("Tipe-X"); listHarga.add(5000.0); listStok.add(50);
        listKode.add("007"); listNama.add("Spidol Hitam"); listHarga.add(8000.0); listStok.add(75);
        listKode.add("008"); listNama.add("Sticky Notes"); listHarga.add(6000.0); listStok.add(120);
        listKode.add("009"); listNama.add("Stapler"); listHarga.add(15000.0); listStok.add(40);
        listKode.add("010"); listNama.add("Isi Stapler"); listHarga.add(3500.0); listStok.add(100);
    }

    /**
     * 2. Pilihan Menu Utama
     */
    static void menuUtama() {
        boolean selesaiBekerja = false;
        while (!selesaiBekerja) {
            System.out.println("\n--- Menu Utama ---");
            System.out.println("Kasir: " + namaKasir + " | Tanggal: " + tanggalLengkap);
            System.out.println("1. Melayani Pembeli");
            System.out.println("2. Lihat & Kelola Daftar Barang");
            System.out.println("3. Selesai Bekerja");
            System.out.print("Pilih opsi (1-3): ");

            try {
                int pilihan = Integer.parseInt(scanner.nextLine());
                switch (pilihan) {
                    case 1:
                        // Poin 3 & 5
                        melayaniPelanggan();
                        break;
                    case 2:
                        // Poin 4
                        manajemenBarang();
                        break;
                    case 3:
                        // Poin 5 (Selesai)
                        selesaiBekerja = true;
                        break;
                    default:
                        System.out.println("Pilihan tidak valid. Silakan coba lagi.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Input tidak valid. Harap masukkan angka.");
            }
        }
        rekapHarian(); // Panggil rekap setelah selesai bekerja
    }

    /**
     * 4. Logika untuk Manajemen Barang
     */
    static void manajemenBarang() {
        while (true) {
            System.out.println("\n--- Manajemen Daftar Barang ---");
            System.out.println("1. Lihat Daftar Barang");
            System.out.println("2. Tambah Barang Baru");
            System.out.println("3. Tambah Stok Barang");
            System.out.println("4. Kembali ke Menu Utama");
            System.out.print("Pilih opsi (1-4): ");

            try {
                int pilihan = Integer.parseInt(scanner.nextLine());
                if (pilihan == 1) {
                    tampilkanDaftarBarang();
                } else if (pilihan == 2) {
                    tambahBarangBaru();
                } else if (pilihan == 3) {
                    tambahStokBarang();
                } else if (pilihan == 4) {
                    break; // Keluar dari loop manajemen barang, kembali ke menu utama
                } else {
                    System.out.println("Pilihan tidak valid.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Input tidak valid. Harap masukkan angka.");
            }
        }
    }

    /**
     * 4. (Bagian dari) Menampilkan daftar barang
     */
    static void tampilkanDaftarBarang() {
        System.out.println("\n--- Daftar Barang ---");
        System.out.println("==========================================================");
        System.out.printf("| %-5s | %-20s | %-15s | %-5s |\n", "Kode", "Nama Barang", "Harga Satuan", "Stok");
        System.out.println("==========================================================");
        
        if (listKode.isEmpty()) {
            System.out.println("|                      Data Kosong                      |");
        } else {
            // Loop sebanyak isi dari list
            for (int i = 0; i < listKode.size(); i++) {
                System.out.printf("| %-5s | %-20s | Rp %-12.0f | %-5d |\n", 
                    listKode.get(i), 
                    listNama.get(i), 
                    listHarga.get(i), 
                    listStok.get(i));
            }
        }
        System.out.println("==========================================================");
    }
    
    /**
     * Method bantu untuk mencari indeks barang berdasarkan kodenya.
     * Mengembalikan -1 jika tidak ditemukan.
     */
    static int cariBarang(String kode) {
        for (int i = 0; i < listKode.size(); i++) {
            if (listKode.get(i).equals(kode)) {
                return i; // Mengembalikan indeks (posisi) barang
            }
        }
        return -1; // Kode tidak ditemukan
    }


    /**
     * 4. (Bagian dari) Tambah Barang Baru
     */
    static void tambahBarangBaru() {
        System.out.println("\n--- Tambah Barang Baru ---");
        String kode;
        while (true) {
            System.out.print("Masukkan Kode Barang (3 digit, misal: 011): ");
            kode = scanner.nextLine();
            if (kode.length() != 3) {
                System.out.println("Kode harus 3 digit.");
            } else if (cariBarang(kode) != -1) { // -1 artinya tidak ketemu
                System.out.println("Kode barang sudah ada. Gunakan kode lain.");
            } else {
                break; // Kode valid dan unik
            }
        }

        System.out.print("Masukkan Nama Barang: ");
        String nama = scanner.nextLine();

        double harga = 0;
        while (harga <= 0) {
            try {
                System.out.print("Masukkan Harga Satuan: ");
                harga = Double.parseDouble(scanner.nextLine());
                if (harga <= 0) System.out.println("Harga harus positif.");
            } catch (NumberFormatException e) { System.out.println("Input harga tidak valid."); }
        }

        int stok = -1;
        while (stok < 0) {
            try {
                System.out.print("Masukkan Stok Awal: ");
                stok = Integer.parseInt(scanner.nextLine());
                if (stok < 0) System.out.println("Stok tidak boleh negatif.");
            } catch (NumberFormatException e) { System.out.println("Input stok tidak valid."); }
        }

        // Tambahkan data baru ke semua list
        listKode.add(kode);
        listNama.add(nama);
        listHarga.add(harga);
        listStok.add(stok);
        System.out.println("Barang baru '" + nama + "' berhasil ditambahkan!");
    }
    
    /**
     * 4. (Bagian dari) Tambah Stok Barang
     */
    static void tambahStokBarang() {
        System.out.println("\n--- Tambah Stok Barang ---");
        System.out.print("Masukkan Kode Barang: ");
        String kode = scanner.nextLine();

        int indeks = cariBarang(kode);
        if (indeks == -1) {
            System.out.println("Barang dengan kode " + kode + " tidak ditemukan.");
            return; // Kembali ke menu manajemen
        }

        int jumlahTambah = 0;
        while (jumlahTambah <= 0) {
            try {
                System.out.print("Masukkan Jumlah Stok Tambahan: ");
                jumlahTambah = Integer.parseInt(scanner.nextLine());
                if (jumlahTambah <= 0) System.out.println("Jumlah tambahan harus positif.");
            } catch (NumberFormatException e) { System.out.println("Input jumlah tidak valid."); }
        }
        
        // Update stok di list
        int stokLama = listStok.get(indeks);
        listStok.set(indeks, stokLama + jumlahTambah); // set() untuk update nilai
        
        System.out.println("Stok " + listNama.get(indeks) + " berhasil ditambah.");
        System.out.println("Stok lama: " + stokLama + ", Stok baru: " + listStok.get(indeks));
    }


    /**
     * 3. Logika untuk Melayani Pelanggan
     */
    static void melayaniPelanggan() {
        nomorPelanggan++; // Sesuai poin 5
        System.out.println("\n--- Melayani Pelanggan ke-" + nomorPelanggan + " ---");

        // Ini adalah "Keranjang Belanja" khusus untuk pelanggan ini
        // Akan di-reset setiap ada pelanggan baru
        ArrayList<String> keranjangKode = new ArrayList<>();
        ArrayList<String> keranjangNama = new ArrayList<>();
        ArrayList<Integer> keranjangJumlah = new ArrayList<>();
        ArrayList<Double> keranjangHargaSatuan = new ArrayList<>();
        ArrayList<Double> keranjangSubtotal = new ArrayList<>();
        
        while (true) {
            System.out.print("Masukkan Kode Barang (atau ketik 'selesai' untuk checkout): ");
            String kode = scanner.nextLine();

            if (kode.equalsIgnoreCase("selesai")) {
                if (keranjangKode.isEmpty()) {
                    System.out.println("Pelanggan tidak membeli apapun.");
                    nomorPelanggan--; // Batalkan penomoran
                    return; // Kembali ke menu utama
                }
                break; // Lanjut ke checkout
            }

            int indeks = cariBarang(kode);
            if (indeks == -1) {
                System.out.println("Kode barang tidak ditemukan. Coba lagi.");
                continue; // Ulangi loop
            }

            // Ambil data barang dari list global
            String nama = listNama.get(indeks);
            double harga = listHarga.get(indeks);
            int stok = listStok.get(indeks);

            // Input jumlah dan validasi stok
            int jumlah = 0;
            while (true) {
                try {
                    System.out.print("Masukkan Jumlah: ");
                    jumlah = Integer.parseInt(scanner.nextLine());
                    if (jumlah <= 0) {
                        System.out.println("Jumlah harus lebih dari 0.");
                    } else if (jumlah > stok) {
                        System.out.println("Stok tidak mencukupi. Stok tersisa: " + stok);
                    } else {
                        break; // Jumlah valid
                    }
                } catch (NumberFormatException e) { System.out.println("Input jumlah tidak valid."); }
            }

            // Masukkan ke keranjang belanja
            keranjangKode.add(kode);
            keranjangNama.add(nama);
            keranjangJumlah.add(jumlah);
            keranjangHargaSatuan.add(harga);
            keranjangSubtotal.add(harga * jumlah);
            
            // Kurangi stok di list global
            listStok.set(indeks, stok - jumlah);

            System.out.printf("Ditambahkan: %s (%d) - Subtotal: Rp %.0f\n", 
                nama, jumlah, (harga * jumlah));
        }

        // Lanjut ke proses checkout, bawa data keranjang
        prosesCheckout(keranjangKode, keranjangNama, keranjangJumlah, keranjangHargaSatuan, keranjangSubtotal);
    }
    
    /**
     * 3b. Proses Checkout (Total, Diskon, Bayar, Struk)
     * Method ini menerima data keranjang dari 'melayaniPelanggan'
     */
    static void prosesCheckout(
        ArrayList<String> kKode, ArrayList<String> kNama, 
        ArrayList<Integer> kJumlah, ArrayList<Double> kHarga, 
        ArrayList<Double> kSubtotal
    ) {
        System.out.println("\n--- Proses Checkout ---");
        
        // Hitung total belanja
        double totalBelanja = 0;
        for (double sub : kSubtotal) {
            totalBelanja += sub;
        }
        System.out.printf("Total Belanja: Rp %.0f\n", totalBelanja);

        // Terapkan diskon ganjil/genap (poin 3)
        double persentaseDiskon = (tanggalHari % 2 == 0) ? 0.10 : 0.05; // Genap 10%, Ganjil 5%
        double nilaiDiskon = totalBelanja * persentaseDiskon;
        double totalSetelahDiskon = totalBelanja - nilaiDiskon;

        System.out.printf("Diskon Tanggal (%d%%): -Rp %.0f\n", (int)(persentaseDiskon * 100), nilaiDiskon);
        System.out.printf("Total Setelah Diskon: Rp %.0f\n", totalSetelahDiskon);

        // Metode Pembayaran (poin 3)
        double biayaAdmin = 0;
        double kembalian = 0;
        double totalFinal = totalSetelahDiskon;
        String metodeBayarStr = "";

        while (true) {
            System.out.print("Metode Pembayaran (1. Tunai / 2. Non-Tunai): ");
            try {
                int metode = Integer.parseInt(scanner.nextLine());
                
                if (metode == 1) { // Tunai
                    metodeBayarStr = "Tunai";
                    System.out.print("Masukkan Uang Tunai: Rp ");
                    double uangBayar = Double.parseDouble(scanner.nextLine());
                    
                    if (uangBayar < totalSetelahDiskon) {
                        System.out.println("Uang tunai kurang. Transaksi dibatalkan.");
                        // Kembalikan stok
                        kembalikanStok(kKode, kJumlah);
                        return; // Kembali ke menu utama
                    }
                    kembalian = uangBayar - totalSetelahDiskon;
                    totalPendapatanHarian += totalSetelahDiskon; // Tambah ke rekap
                    System.out.printf("Kembalian: Rp %.0f\n", kembalian);
                    break;

                } else if (metode == 2) { // Non-Tunai
                    metodeBayarStr = "Non-Tunai";
                    biayaAdmin = 1000;
                    totalFinal = totalSetelahDiskon + biayaAdmin;
                    System.out.printf("Biaya Admin: Rp %.0f\n", biayaAdmin);
                    System.out.printf("Total Final: Rp %.0f\n", totalFinal);
                    System.out.println("Pembayaran Non-Tunai diterima.");
                    totalPendapatanHarian += totalFinal; // Tambah ke rekap
                    break;
                    
                } else {
                    System.out.println("Pilihan tidak valid.");
                }
            } catch (NumberFormatException e) { System.out.println("Input tidak valid."); }
        }

        // Cetak Struk (poin 3)
        cetakStruk(kKode, kNama, kJumlah, kHarga, kSubtotal, 
                   totalBelanja, nilaiDiskon, totalSetelahDiskon, 
                   biayaAdmin, kembalian, metodeBayarStr);
    }
    
    /**
     * 3c. Cetak Struk
     */
    static void cetakStruk(
        ArrayList<String> kKode, ArrayList<String> kNama, ArrayList<Integer> kJumlah,
        ArrayList<Double> kHarga, ArrayList<Double> kSubtotal, double total, 
        double diskon, double totalSetelahDiskon, double admin, double kembalian, String metodeBayar
    ) {
        System.out.println("\n==========================================");
        System.out.println("             STRUK PEMBELIAN");
        System.out.println("==========================================");
        System.out.println("Kasir    : " + namaKasir);
        System.out.println("Tanggal  : " + tanggalLengkap);
        System.out.println("Pelanggan: " + nomorPelanggan);
        System.out.println("------------------------------------------");

        // Loop isi keranjang
        for (int i = 0; i < kKode.size(); i++) {
            System.out.printf("%s (%d x %.0f)\n", 
                kNama.get(i), 
                kJumlah.get(i), 
                kHarga.get(i));
            System.out.printf("  [%-30s] Rp %.0f\n", 
                kKode.get(i), 
                kSubtotal.get(i));
        }

        System.out.println("------------------------------------------");
        System.out.printf("%-20s: Rp %.0f\n", "Subtotal", total);
        System.out.printf("%-20s: -Rp %.0f\n", "Diskon", diskon);
        System.out.printf("%-20s: Rp %.0f\n", "Total", totalSetelahDiskon);
        
        if (admin > 0) {
            System.out.printf("%-20s: Rp %.0f\n", "Biaya Admin", admin);
            System.out.printf("%-20s: Rp %.0f\n", "GRAND TOTAL", totalSetelahDiskon + admin);
        }
        
        System.out.println("------------------------------------------");
        System.out.printf("%-20s: %s\n", "Metode Bayar", metodeBayar);
        
        if (kembalian > 0) {
            System.out.printf("%-20s: Rp %.0f\n", "Kembalian", kembalian);
        }
        
        System.out.println("==========================================");
        System.out.println("        Terima Kasih Telah Berbelanja!");
        System.out.println("==========================================");
    }

    /**
     * 3d. Mengembalikan stok jika transaksi tunai dibatalkan
     */
    static void kembalikanStok(ArrayList<String> kKode, ArrayList<Integer> kJumlah) {
        System.out.println("Mengembalikan stok barang ke sistem...");
        for (int i = 0; i < kKode.size(); i++) {
            String kode = kKode.get(i);
            int jumlah = kJumlah.get(i);
            int indeks = cariBarang(kode);
            
            if (indeks != -1) {
                int stokLama = listStok.get(indeks);
                listStok.set(indeks, stokLama + jumlah); // Tambah stok kembali
            }
        }
    }


    /**
     * 5. Selesai Bekerja dan Rekap Harian
     */
    static void rekapHarian() {
        System.out.println("\n--- Selesai Bekerja ---");
        System.out.println("Sesi untuk kasir " + namaKasir + " telah berakhir.");
        System.out.println("Total pelanggan dilayani: " + nomorPelanggan);
        System.out.printf("Total Pendapatan Harian: Rp %.0f\n", totalPendapatanHarian);
        System.out.println("Terima kasih dan sampai jumpa!");
        scanner.close(); // Tutup scanner
    }
}