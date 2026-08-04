package com.example.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.AppDatabase
import com.example.data.local.entities.CouponEntity
import com.example.data.local.entities.ProductEntity
import com.example.data.local.entities.SaleEntity
import com.example.data.repository.ManagementRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

enum class NavDestination {
  HOME,
  COUPONS,
  TOOLS,
  USER_SEARCH,
  MANAGER
}

enum class ManagerTab(val title: String) {
  SALES("হিসাব নিকাশ"),
  PRODUCTS("প্রোডাক্ট এর বিবরণ"),
  COUPONS("কুপন কোড"),
  REPORTS("রিপোর্ট ও সেটিংস")
}

data class SalesSummary(
  val totalBuy: Double = 0.0,
  val totalSell: Double = 0.0,
  val totalProfit: Double = 0.0,
  val totalTransactions: Int = 0,
  val profitMargin: Double = 0.0
)

class ManagementViewModel(application: Application) : AndroidViewModel(application) {

  private val repository: ManagementRepository

  init {
    val db = AppDatabase.getDatabase(application, viewModelScope)
    repository = ManagementRepository(db.managementDao(), application)
  }

  // Navigation State
  private val _currentNav = MutableStateFlow(NavDestination.HOME)
  val currentNav: StateFlow<NavDestination> = _currentNav.asStateFlow()

  private val _activeManagerTab = MutableStateFlow(ManagerTab.SALES)
  val activeManagerTab: StateFlow<ManagerTab> = _activeManagerTab.asStateFlow()

  // Authentication / Manager PIN
  private val _isManagerUnlocked = MutableStateFlow(false)
  val isManagerUnlocked: StateFlow<Boolean> = _isManagerUnlocked.asStateFlow()

  private val _isPinDialogOpen = MutableStateFlow(false)
  val isPinDialogOpen: StateFlow<Boolean> = _isPinDialogOpen.asStateFlow()

  private val _pinErrorMessage = MutableStateFlow<String?>(null)
  val pinErrorMessage: StateFlow<String?> = _pinErrorMessage.asStateFlow()

  // Toast / Feedback
  private val _userMessage = MutableStateFlow<String?>(null)
  val userMessage: StateFlow<String?> = _userMessage.asStateFlow()

  // Data Flows
  val productsList: StateFlow<List<ProductEntity>> = repository.allProducts
    .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

  val salesList: StateFlow<List<SaleEntity>> = repository.allSales
    .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

  val couponsList: StateFlow<List<CouponEntity>> = repository.allCoupons
    .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

  val activeCouponsList: StateFlow<List<CouponEntity>> = repository.activeCoupons
    .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

  // Sales Summary Calculation
  val salesSummary: StateFlow<SalesSummary> = salesList.combine(productsList) { sales, _ ->
    var buy = 0.0
    var sell = 0.0
    var profit = 0.0
    for (s in sales) {
      buy += s.buyPrice * s.quantity
      sell += s.sellPrice * s.quantity
      profit += s.profit * s.quantity
    }
    val margin = if (sell > 0) (profit / sell) * 100 else 0.0
    SalesSummary(
      totalBuy = buy,
      totalSell = sell,
      totalProfit = profit,
      totalTransactions = sales.size,
      profitMargin = margin
    )
  }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), SalesSummary())

  // User Product Search State
  private val _searchQuery = MutableStateFlow("")
  val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

  private val _searchedProduct = MutableStateFlow<ProductEntity?>(null)
  val searchedProduct: StateFlow<ProductEntity?> = _searchedProduct.asStateFlow()

  private val _hasSearched = MutableStateFlow(false)
  val hasSearched: StateFlow<Boolean> = _hasSearched.asStateFlow()

  // Edit States for Manager Form
  private val _editingSale = MutableStateFlow<SaleEntity?>(null)
  val editingSale: StateFlow<SaleEntity?> = _editingSale.asStateFlow()

  private val _editingProduct = MutableStateFlow<ProductEntity?>(null)
  val editingProduct: StateFlow<ProductEntity?> = _editingProduct.asStateFlow()

  private val _editingCoupon = MutableStateFlow<CouponEntity?>(null)
  val editingCoupon: StateFlow<CouponEntity?> = _editingCoupon.asStateFlow()

  // Navigation handlers
  fun navigateTo(nav: NavDestination) {
    if (nav == NavDestination.MANAGER && !_isManagerUnlocked.value) {
      _pinErrorMessage.value = null
      _isPinDialogOpen.value = true
      return
    }
    _currentNav.value = nav
  }

  fun setManagerTab(tab: ManagerTab) {
    _activeManagerTab.value = tab
  }

  // Manager Security PIN
  fun openPinDialog() {
    _pinErrorMessage.value = null
    _isPinDialogOpen.value = true
  }

  fun closePinDialog() {
    _isPinDialogOpen.value = false
    _pinErrorMessage.value = null
  }

  fun unlockManagerWithPin(pin: String): Boolean {
    val isValid = repository.verifyPin(pin.trim())
    if (isValid) {
      _isManagerUnlocked.value = true
      _isPinDialogOpen.value = false
      _pinErrorMessage.value = null
      _currentNav.value = NavDestination.MANAGER
      _userMessage.value = "ম্যানেজার প্যানেলে স্বাগতম!"
      return true
    } else {
      _pinErrorMessage.value = "ভুল পিন কোড! পুনরায় চেষ্টা করুন।"
      return false
    }
  }

  fun lockManager() {
    _isManagerUnlocked.value = false
    if (_currentNav.value == NavDestination.MANAGER) {
      _currentNav.value = NavDestination.TOOLS
    }
    _userMessage.value = "ম্যানেজার মোড লক করা হয়েছে।"
  }

  fun changeManagerPin(oldPin: String, newPin: String): Boolean {
    if (!repository.verifyPin(oldPin.trim())) {
      _userMessage.value = "বর্তমান পিনটি সঠিক নয়।"
      return false
    }
    if (newPin.trim().length < 4) {
      _userMessage.value = "নতুন পিন কমপক্ষে ৪ সংখ্যার হতে হবে।"
      return false
    }
    repository.setManagerPin(newPin.trim())
    _userMessage.value = "ম্যানেজার পিন সফলভাবে পরিবর্তন হয়েছে।"
    return true
  }

  fun getCurrentManagerPinForHint(): String {
    return repository.getManagerPin()
  }

  // User Product Search
  fun updateSearchQuery(query: String) {
    _searchQuery.value = query
  }

  fun searchProduct(codeOrName: String? = null) {
    val target = (codeOrName ?: _searchQuery.value).trim()
    if (target.isEmpty()) {
      _searchedProduct.value = null
      _hasSearched.value = false
      return
    }
    _hasSearched.value = true
    viewModelScope.launch {
      // First exact match by code (case-insensitive)
      val directMatch = productsList.value.find {
        it.code.equals(target, ignoreCase = true)
      }
      if (directMatch != null) {
        _searchedProduct.value = directMatch
      } else {
        // Match by partial name or code
        val partialMatch = productsList.value.find {
          it.code.contains(target, ignoreCase = true) || it.name.contains(target, ignoreCase = true)
        }
        _searchedProduct.value = partialMatch
      }
    }
  }

  fun clearSearch() {
    _searchQuery.value = ""
    _searchedProduct.value = null
    _hasSearched.value = false
  }

  // Sales Management
  fun saveSale(
    productCode: String,
    date: String,
    buyPrice: Double,
    sellPrice: Double,
    quantity: Int = 1,
    notes: String = ""
  ) {
    val code = productCode.trim()
    val matchedProduct = productsList.value.find { it.code.equals(code, ignoreCase = true) }
    val prodName = matchedProduct?.name ?: "প্রোডাক্ট ($code)"
    val actualDate = if (date.isBlank()) {
      SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
    } else {
      date.trim()
    }
    val profit = sellPrice - buyPrice

    val currentEditing = _editingSale.value
    viewModelScope.launch {
      if (currentEditing != null) {
        val updated = currentEditing.copy(
          productCode = code,
          productName = prodName,
          date = actualDate,
          buyPrice = buyPrice,
          sellPrice = sellPrice,
          quantity = quantity,
          profit = profit,
          notes = notes
        )
        repository.updateSale(updated)
        _editingSale.value = null
        _userMessage.value = "বিক্রির হিসাব আপডেট হয়েছে!"
      } else {
        val newSale = SaleEntity(
          productCode = code,
          productName = prodName,
          date = actualDate,
          buyPrice = buyPrice,
          sellPrice = sellPrice,
          quantity = quantity,
          profit = profit,
          notes = notes
        )
        repository.insertSale(newSale)
        _userMessage.value = "নতুন বিক্রির হিসাব সংরক্ষিত হয়েছে!"
      }
    }
  }

  fun prepareEditSale(sale: SaleEntity) {
    _editingSale.value = sale
  }

  fun cancelEditSale() {
    _editingSale.value = null
  }

  fun deleteSale(sale: SaleEntity) {
    viewModelScope.launch {
      repository.deleteSale(sale)
      _userMessage.value = "হিসাব ডিলিট করা হয়েছে。"
      if (_editingSale.value?.id == sale.id) {
        _editingSale.value = null
      }
    }
  }

  // Product Catalog Management
  fun saveProduct(
    name: String,
    code: String,
    price: Double,
    size: String,
    color: String,
    stock: Int = 10,
    category: String = "সাধারণ"
  ) {
    val cleanName = name.trim()
    val cleanCode = code.trim().uppercase()
    val currentEditing = _editingProduct.value

    viewModelScope.launch {
      if (currentEditing != null) {
        val updated = currentEditing.copy(
          name = cleanName,
          code = cleanCode,
          price = price,
          size = size.trim(),
          color = color.trim(),
          stockQuantity = stock,
          category = category.trim()
        )
        repository.updateProduct(updated)
        _editingProduct.value = null
        _userMessage.value = "প্রোডাক্ট বিবরণ আপডেট হয়েছে!"
      } else {
        val newProduct = ProductEntity(
          name = cleanName,
          code = cleanCode,
          price = price,
          size = size.trim(),
          color = color.trim(),
          stockQuantity = stock,
          category = category.trim()
        )
        repository.insertProduct(newProduct)
        _userMessage.value = "নতুন প্রোডাক্ট সফলভাবে যোগ হয়েছে!"
      }
    }
  }

  fun prepareEditProduct(product: ProductEntity) {
    _editingProduct.value = product
  }

  fun cancelEditProduct() {
    _editingProduct.value = null
  }

  fun deleteProduct(product: ProductEntity) {
    viewModelScope.launch {
      repository.deleteProduct(product)
      _userMessage.value = "প্রোডাক্ট ডিলিট করা হয়েছে。"
      if (_editingProduct.value?.id == product.id) {
        _editingProduct.value = null
      }
    }
  }

  // Coupons Management
  fun saveCoupon(
    title: String,
    code: String,
    discount: String,
    description: String = "",
    isActive: Boolean = true,
    expiryDate: String = "2026-12-31"
  ) {
    val cleanTitle = title.trim()
    val cleanCode = code.trim().uppercase()
    val currentEditing = _editingCoupon.value

    viewModelScope.launch {
      if (currentEditing != null) {
        val updated = currentEditing.copy(
          title = cleanTitle,
          code = cleanCode,
          discount = discount.trim(),
          description = description.trim(),
          isActive = isActive,
          expiryDate = expiryDate.trim()
        )
        repository.updateCoupon(updated)
        _editingCoupon.value = null
        _userMessage.value = "কুপন কোড আপডেট হয়েছে!"
      } else {
        val newCoupon = CouponEntity(
          title = cleanTitle,
          code = cleanCode,
          discount = discount.trim(),
          description = description.trim(),
          isActive = isActive,
          expiryDate = expiryDate.trim()
        )
        repository.insertCoupon(newCoupon)
        _userMessage.value = "নতুন কুপন কোড সফলভাবে যোগ হয়েছে!"
      }
    }
  }

  fun prepareEditCoupon(coupon: CouponEntity) {
    _editingCoupon.value = coupon
  }

  fun cancelEditCoupon() {
    _editingCoupon.value = null
  }

  fun deleteCoupon(coupon: CouponEntity) {
    viewModelScope.launch {
      repository.deleteCoupon(coupon)
      _userMessage.value = "কুপন ডিলিট করা হয়েছে।"
      if (_editingCoupon.value?.id == coupon.id) {
        _editingCoupon.value = null
      }
    }
  }

  fun toggleCouponStatus(coupon: CouponEntity) {
    viewModelScope.launch {
      val updated = coupon.copy(isActive = !coupon.isActive)
      repository.updateCoupon(updated)
      _userMessage.value = if (updated.isActive) "কুপন সক্রিয় করা হয়েছে!" else "কুপন নিষ্ক্রিয় করা হয়েছে।"
    }
  }

  // Reset Demo Data
  fun resetAllDemoData() {
    viewModelScope.launch {
      repository.resetDemoData()
      _userMessage.value = "ডিফল্ট ডেমো ডাটা রিস্টোর করা হয়েছে!"
    }
  }

  fun showMessage(msg: String) {
    _userMessage.value = msg
  }

  fun clearUserMessage() {
    _userMessage.value = null
  }
}
