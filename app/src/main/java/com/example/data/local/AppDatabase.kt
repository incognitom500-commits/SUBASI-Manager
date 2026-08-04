package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.data.local.dao.ManagementDao
import com.example.data.local.entities.CouponEntity
import com.example.data.local.entities.ProductEntity
import com.example.data.local.entities.SaleEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
  entities = [
    ProductEntity::class,
    SaleEntity::class,
    CouponEntity::class
  ],
  version = 1,
  exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

  abstract fun managementDao(): ManagementDao

  companion object {
    @Volatile
    private var INSTANCE: AppDatabase? = null

    fun getDatabase(context: Context, scope: CoroutineScope): AppDatabase {
      return INSTANCE ?: synchronized(this) {
        val instance = Room.databaseBuilder(
          context.applicationContext,
          AppDatabase::class.java,
          "management_system_database"
        )
          .addCallback(DatabaseCallback(scope))
          .fallbackToDestructiveMigration()
          .build()
        INSTANCE = instance
        instance
      }
    }

    private class DatabaseCallback(
      private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {
      override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        INSTANCE?.let { database ->
          scope.launch(Dispatchers.IO) {
            populateInitialData(database.managementDao())
          }
        }
      }
    }

    suspend fun populateInitialData(dao: ManagementDao) {
      // 1. Initial Products
      val initialProducts = listOf(
        ProductEntity(
          code = "P01",
          name = "প্রিমিয়াম ফরমাল শার্ট",
          price = 1250.0,
          size = "M, L, XL, XXL",
          color = "রয়্যাল ব্লু, সাদা, কালো",
          stockQuantity = 25,
          category = "পোশাক"
        ),
        ProductEntity(
          code = "P02",
          name = "সেমি-ফিটেড কটন পাঞ্জাবি",
          price = 1850.0,
          size = "40, 42, 44",
          color = "মেরুন, অফ-হোয়াইট",
          stockQuantity = 18,
          category = "পাঞ্জাবি"
        ),
        ProductEntity(
          code = "P03",
          name = "স্ট্রেচ ডেনিম জিন্স প্যান্ট",
          price = 1450.0,
          size = "30, 32, 34, 36",
          color = "ডিপ ব্লু, অ্যাস",
          stockQuantity = 30,
          category = "প্যান্ট"
        ),
        ProductEntity(
          code = "P04",
          name = "ক্লাসিক পোলো টি-শার্ট",
          price = 650.0,
          size = "M, L, XL",
          color = "নেভি ব্লু, বটল গ্রিন, কালো",
          stockQuantity = 40,
          category = "টি-শার্ট"
        ),
        ProductEntity(
          code = "P05",
          name = "লেদার মানিব্যাগ ও বেল্ট কম্বো",
          price = 950.0,
          size = "স্ট্যান্ডার্ড",
          color = "ব্রাউন, ব্ল্যাক",
          stockQuantity = 15,
          category = "অ্যাক্সেসরিজ"
        )
      )
      dao.insertProducts(initialProducts)

      // 2. Initial Sales Records
      val initialSales = listOf(
        SaleEntity(
          productCode = "P01",
          productName = "প্রিমিয়াম ফরমাল শার্ট",
          date = "2026-08-01",
          buyPrice = 850.0,
          sellPrice = 1250.0,
          quantity = 2,
          profit = 400.0,
          notes = "নগদ বিক্রয়"
        ),
        SaleEntity(
          productCode = "P02",
          productName = "সেমি-ফিটেড কটন পাঞ্জাবি",
          date = "2026-08-02",
          buyPrice = 1200.0,
          sellPrice = 1850.0,
          quantity = 1,
          profit = 650.0,
          notes = "ঈদ কালেকশন"
        ),
        SaleEntity(
          productCode = "P03",
          productName = "স্ট্রেচ ডেনিম জিন্স প্যান্ট",
          date = "2026-08-03",
          buyPrice = 950.0,
          sellPrice = 1450.0,
          quantity = 1,
          profit = 500.0,
          notes = "কাস্টমার ক্যাশ মেমো #104"
        ),
        SaleEntity(
          productCode = "P04",
          productName = "ক্লাসিক পোলো টি-শার্ট",
          date = "2026-08-04",
          buyPrice = 400.0,
          sellPrice = 650.0,
          quantity = 3,
          profit = 250.0,
          notes = "অনলাইন অর্ডার"
        )
      )
      dao.insertSales(initialSales)

      // 3. Initial Coupons
      val initialCoupons = listOf(
        CouponEntity(
          title = "ঈদ স্পেশাল অফার",
          code = "EID20",
          discount = "২০% ছাড়",
          description = "সর্বনিম্ন ১০০০ টাকার কেনাকাটায় ২০% ছাড় উপভোগ করুন।",
          isActive = true,
          expiryDate = "2026-09-15"
        ),
        CouponEntity(
          title = "বৈশাখী ধামাকা",
          code = "BOISHAKHI15",
          discount = "১৫% ছাড়",
          description = "সকল নতুন কালেকশনের পোশাকে ১৫% ইনস্ট্যান্ট ডিসকাউন্ট।",
          isActive = true,
          expiryDate = "2026-08-31"
        ),
        CouponEntity(
          title = "নিউ কাস্টমার ওয়েলকাম",
          code = "WELCOME10",
          discount = "১০% ছাড়",
          description = "প্রথমবারের অর্ডারে নিশ্চিত ১০% ক্যাশব্যাক/ছাড়।",
          isActive = true,
          expiryDate = "2026-12-31"
        ),
        CouponEntity(
          title = "ফ্ল্যাশ সুপার সেভ",
          code = "FLASHSAVE",
          discount = "৳১৫০ ফ্ল্যাট",
          description = "১৫০০ টাকার উপরে অর্ডারে সরাসরি ১৫০ টাকা মাইনাস।",
          isActive = true,
          expiryDate = "2026-08-20"
        )
      )
      dao.insertCoupons(initialCoupons)
    }
  }
}
