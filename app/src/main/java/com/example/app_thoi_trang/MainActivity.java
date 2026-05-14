package com.example.app_thoi_trang;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.app_thoi_trang.fragment.ChangePassFragment;
import com.example.app_thoi_trang.fragment.DoanhThuFragment;
import com.example.app_thoi_trang.fragment.GiayFragment;
import com.example.app_thoi_trang.fragment.HoaDonFragment;
import com.example.app_thoi_trang.fragment.KhachHangFragment;
import com.example.app_thoi_trang.fragment.LoaiGiayFragment;
import com.example.app_thoi_trang.fragment.NhanVienFragment;
import com.example.app_thoi_trang.fragment.TopFragment;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawer;
    private MaterialToolbar toolbar;
    private View mHeaderView;
    private TextView edUser;
    private NavigationView nv;
    private BottomNavigationView bm;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Ép ứng dụng luôn ở chế độ sáng (Fix lỗi nền đen do Dark Mode)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        // Thiết lập Full Screen trước khi gọi super.onCreate để tránh lỗi trên một số máy
        try {
            supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } catch (Exception e) {
            e.printStackTrace();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Ánh xạ View
        drawer = findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.toolbar);
        nv = findViewById(R.id.nvView);
        bm = findViewById(R.id.btt_nav);

        // Thiết lập Toolbar
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setHomeAsUpIndicator(R.drawable.menu_tab_bar);
            ab.setDisplayHomeAsUpEnabled(true);
        }

        // Mặc định mở fragment đầu tiên (Hóa Đơn)
        if (savedInstanceState == null) {
            replaceFragment(new HoaDonFragment(), "Quản Lý Hóa Đơn");
        }

        // Thiết lập Navigation View (Menu Drawer)
        if (nv != null) {
            nv.setCheckedItem(R.id.nav_HoaDon);
            if (nv.getHeaderCount() > 0) {
                mHeaderView = nv.getHeaderView(0);
                edUser = mHeaderView.findViewById(R.id.tvUser);
            }

            Intent i = getIntent();
            String user = i.getStringExtra("user");
            if (edUser != null) {
                edUser.setText("Welcome " + (user != null ? user : "") + "!");
            }

            if (user != null && user.equalsIgnoreCase("admin")) {
                MenuItem itemNV = nv.getMenu().findItem(R.id.nav_sub_NhanVien);
                if (itemNV != null) itemNV.setVisible(true);
            }

            nv.setNavigationItemSelectedListener(item -> {
                int id = item.getItemId();
                if (id == R.id.nav_HoaDon) {
                    replaceFragment(new HoaDonFragment(), "Quản Lý Hóa Đơn");
                    updateBottomNav(R.id.navb_HoaDon);
                } else if (id == R.id.nav_LoaiGiay) {
                    replaceFragment(new LoaiGiayFragment(), "Quản Lý Loại");
                    updateBottomNav(R.id.navb_LoaiGiay);
                } else if (id == R.id.nav_Giay) {
                    replaceFragment(new GiayFragment(), "Quản Lý Sản Phẩm");
                    updateBottomNav(R.id.navb_Giay);
                } else if (id == R.id.nav_KhachHang) {
                    replaceFragment(new KhachHangFragment(), "Quản Lý Khách Hàng");
                } else if (id == R.id.nav_sub_Top) {
                    replaceFragment(new TopFragment(), "Top 10 Bán Chạy");
                    updateBottomNav(R.id.navb_Top);
                } else if (id == R.id.nav_sub_DoanhThu) {
                    replaceFragment(new DoanhThuFragment(), "Thống Kê Doanh Thu");
                } else if (id == R.id.nav_sub_NhanVien) {
                    replaceFragment(new NhanVienFragment(), "Quản Lý Nhân Viên");
                } else if (id == R.id.nav_sub_Pass) {
                    replaceFragment(new ChangePassFragment(), "Đổi Mật Khẩu");
                } else if (id == R.id.nav_sub_Logout) {
                    showLogoutDialog();
                }
                drawer.closeDrawers();
                return true;
            });
        }

        // Thiết lập Bottom Navigation
        if (bm != null) {
            if (savedInstanceState == null) {
                bm.setSelectedItemId(R.id.navb_HoaDon);
            }
            bm.setOnItemSelectedListener(item -> {
                int id = item.getItemId();
                if (id == R.id.navb_Giay) {
                    replaceFragment(new GiayFragment(), "Quản Lý Sản Phẩm");
                    if (nv != null) nv.setCheckedItem(R.id.nav_Giay);
                } else if (id == R.id.navb_LoaiGiay) {
                    replaceFragment(new LoaiGiayFragment(), "Quản Lý Loại");
                    if (nv != null) nv.setCheckedItem(R.id.nav_LoaiGiay);
                } else if (id == R.id.navb_HoaDon) {
                    replaceFragment(new HoaDonFragment(), "Quản Lý Hóa Đơn");
                    if (nv != null) nv.setCheckedItem(R.id.nav_HoaDon);
                } else if (id == R.id.navb_Top) {
                    replaceFragment(new TopFragment(), "Top 10 Bán Chạy");
                    if (nv != null) nv.setCheckedItem(R.id.nav_sub_Top);
                }
                return true;
            });
        }
    }

    private void updateBottomNav(int id) {
        if (bm != null && bm.getMenu().findItem(id) != null) {
            bm.getMenu().findItem(id).setChecked(true);
        }
    }

    private void showLogoutDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Đăng xuất")
                .setMessage("Bạn có muốn đăng xuất khỏi ứng dụng?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    finish();
                })
                .setNegativeButton("No", null)
                .show();
    }

    private void replaceFragment(Fragment fragment, String title) {
        setTitle(title);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.flContent, fragment)
                .commit();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if (drawer != null) drawer.openDrawer(GravityCompat.START);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean doubleBackToExitPressedOnce = false;
    @Override
    public void onBackPressed() {
        if (drawer != null && drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
            return;
        }

        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Nhấn lần nữa để thoát", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(() -> doubleBackToExitPressedOnce = false, 2000);
    }
}
