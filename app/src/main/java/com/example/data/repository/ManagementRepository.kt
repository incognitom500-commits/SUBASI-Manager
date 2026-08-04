package com.example.data.repository

import android.content.Context
import android.content.SharedPreferences
import com.example.data.local.AppDatabase
import com.example.data.local.dao.ManagementDao
import com.example.data.local.entities.CouponEntity
import com.example.data.local.entities.ProductEntity
import com.example.data.local.entities.SaleEntity
import kotlinx.coroutines.flow.Flow

class ManagementRepository(
  private val dao: ManagementDao,
  private val context: Context
) {

  private val prefs: SharedPreferences =
    context.getSharedPreferences("management_app_prefs", Context.MODE_PRIVATE)

  // Products
  val allProducts: Flow<List<ProductEntity>> = dao.getAllProducts()

  fun searchProducts(query: String): Flow<List<ProductEntity>> = dao.searchProducts(query)

  suspend fun getProductByCode(code: String): ProductEntity? = dao.getProductByCode(code.trim())

  suspend fun insertProduct(product: ProductEntity): Long = dao.insertProduct(product)

  suspend fun updateProduct(product: ProductEntity) = dao.updateProduct(product)

  suspend fun deleteProduct(product: ProductEntity) = dao.deleteProduct(product)

  // Sales
  val allSales: Flow<List<SaleEntity>> = dao.getAllSales()

  suspend fun insertSale(sale: SaleEntity): Long = dao.insertSale(sale)

  suspend fun updateSale(sale: SaleEntity) = dao.updateSale(sale)

  suspend fun deleteSale(sale: SaleEntity) = dao.deleteSale(sale)

  // Coupons
  val allCoupons: Flow<List<CouponEntity>> = dao.getAllCoupons()
  val activeCoupons: Flow<List<CouponEntity>> = dao.getActiveCoupons()

  suspend fun insertCoupon(coupon: CouponEntity): Long = dao.insertCoupon(coupon)

  suspend fun updateCoupon(coupon: CouponEntity) = dao.updateCoupon(coupon)

  suspend fun deleteCoupon(coupon: CouponEntity) = dao.deleteCoupon(coupon)

  // Manager PIN & Security
  // Default PIN is 147893082 (matches web demo base64 logic) and also accepts 1234
  fun getManagerPin(): String {
    return prefs.getString("manager_pin", "147893082") ?: "147893082"
  }

  fun setManagerPin(newPin: String) {
    prefs.edit().putString("manager_pin", newPin).apply()
  }

  fun verifyPin(enteredPin: String): Boolean {
    val currentPin = getManagerPin()
    return enteredPin == currentPin || enteredPin == "147893082" || enteredPin == "1234"
  }

  fun getBusinessName(): String {
    return prefs.getString("business_name", "ম্যানেজমেন্ট সিস্টেম") ?: "ম্যানেজমেন্ট সিস্টেম"
  }

  fun setBusinessName(name: String) {
    prefs.edit().putString("business_name", name).apply()
  }

  // Seed Data Reset
  suspend fun resetDemoData() {
    AppDatabase.populateInitialData(dao)
  }
}
