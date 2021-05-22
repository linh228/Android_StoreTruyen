package com.example.ql_store_truyen.CSDL;

public class Truyen {
    public int Id;
    public String TenT;
    public String TacGia;
    public String NXB;
    public String NoiDung;
    public String TheLoai;
    public int Gia;
    public int SoLuong;
    public byte[] AnhBia;

    public Truyen(int id, String tenT, String tacGia, String NXB, String noiDung, String theLoai, int gia, int soLuong, byte[] anhBia) {
        Id = id;
        TenT = tenT;
        TacGia = tacGia;
        this.NXB = NXB;
        NoiDung = noiDung;
        TheLoai = theLoai;
        Gia = gia;
        SoLuong = soLuong;
        AnhBia = anhBia;
    }

    public Truyen() {
    }
}
