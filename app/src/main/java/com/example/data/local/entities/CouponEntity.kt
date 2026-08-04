package com.example.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "coupons")
data class CouponEntity(
  @PrimaryKey(autoGenerate = true)
  val id: Long = 0,
  val title: String,
  val code: String,
  val discount: String, // e.g. "10%" or "20%" or "৳150"
  val description: String = "বিশেষ ছাড়ের অফার",
  val isActive: Boolean = true,
  val expiryDate: String = "2026-12-31",
  val createdAt: Long = System.currentTimeMillis()
)
