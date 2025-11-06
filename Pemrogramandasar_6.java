import java.util.Scanner;
public class Pemrogramandasar_6 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String nama;
        int tanggal, menukasir;

        System.out.print("Masukkan nama Anda: ");
        nama = scanner.nextLine();
        System.out.print("Masukkan tanggal hari ini: ");
        tanggal = scanner.nextInt();
        
        System.out.println("Menu Kasir");
        System.out.println("1. Layani Belanja:");
        System.out.println("2. Daftar stok barang");

        System.out.print("Pilih menu kasir: ");
        menukasir = scanner.nextInt();

        if (menukasir == 1) {
            System.out.println("Anda memilih Layani Belanja.");
        } else if (menukasir == 2) {
            System.out.println("Anda memilih Daftar stok barang.");
        } else {
            System.out.println("Menu tidak valid.");
        }
    }
}
