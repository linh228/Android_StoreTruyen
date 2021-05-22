package com.example.ql_store_truyen.CSDL;

public class NhanVien {
    public int Id;
    public String HoTen;
    public String NgaySinh;
    public String SDT;
    public String DiaChi;
    public String TaiKhoan;
    public String MatKhau;
    public String ChucVu;
    public byte[] AnhNV;

    public NhanVien(int id, String hoTen, String ngaySinh, String SDT, String diaChi, String taiKhoan, String matKhau, String chucVu, byte[] anhNV) {
        Id = id;
        HoTen = hoTen;
        NgaySinh = ngaySinh;
        this.SDT = SDT;
        DiaChi = diaChi;
        TaiKhoan = taiKhoan;
        MatKhau = matKhau;
        ChucVu = chucVu;
        AnhNV = anhNV;
    }
    public void clear(){
        Id = 0;
        HoTen = "";
        NgaySinh = "";
        this.SDT = "";
        DiaChi = "";
        TaiKhoan = "";
        MatKhau = "";
        ChucVu = "";
        AnhNV = null;
    }
    public NhanVien(){}
    public static NhanVien NguoiDung;
}
