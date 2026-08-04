package com.example.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.local.entities.CouponEntity
import com.example.data.local.entities.ProductEntity
import com.example.data.local.entities.SaleEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ManagementDao {

  // Products
  @Query("SELECT * FROM products ORDER BY id DESC")
  fun getAllProducts(): Flow<List<ProductEntity>>

  @Query("SELECT * FROM products WHERE code = :code LIMIT 1")
  suspend fun getProductByCode(code: String): ProductEntity?

  @Query("SELECT * FROM products WHERE code LIKE '%' || :query || '%' OR name LIKE '%' || :query || '%'")
  fun searchProducts(query: String): Flow<List<ProductEntity>>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertProduct(product: ProductEntity): Long

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertProducts(products: List<ProductEntity>)

  @Update
  suspend fun updateProduct(product: ProductEntity)

  @Delete
  suspend fun deleteProduct(product: ProductEntity)

  @Query("DELETE FROM products WHERE id = :id")
  suspend fun deleteProductById(id: Long)

  // Sales
  @Query("SELECT * FROM sales ORDER BY id DESC")
  fun getAllSales(): Flow<List<SaleEntity>>

  @Query("SELECT * FROM sales WHERE date = :date ORDER BY id DESC")
  fun getSalesByDate(date: String): Flow<List<SaleEntity>>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertSale(sale: SaleEntity): Long

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertSales(sales: List<SaleEntity>)

  @Update
  suspend fun updateSale(sale: SaleEntity)

  @Delete
  suspend fun deleteSale(sale: SaleEntity)

  @Query("DELETE FROM sales WHERE id = :id")
  suspend fun deleteSaleById(id: Long)

  // Coupons
  @Query("SELECT * FROM coupons ORDER BY id DESC")
  fun getAllCoupons(): Flow<List<CouponEntity>>

  @Query("SELECT * FROM coupons WHERE isActive = 1 ORDER BY id DESC")
  fun getActiveCoupons(): Flow<List<CouponEntity>>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertCoupon(coupon: CouponEntity): Long

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertCoupons(coupons: List<CouponEntity>)

  @Update
  suspend fun updateCoupon(coupon: CouponEntity)

  @Delete
  suspend fun deleteCoupon(coupon: CouponEntity)

  @Query("DELETE FROM coupons WHERE id = :id")
  suspend fun deleteCouponById(id: Long)

  // Aggregates & Counts
  @Query("SELECT COUNT(*) FROM products")
  suspend fun getProductCount(): Int

  @Query("SELECT COUNT(*) FROM sales")
  suspend fun getSalesCount(): Int

  @Query("SELECT COUNT(*) FROM coupons")
  suspend fun getCouponCount(): Int
}
