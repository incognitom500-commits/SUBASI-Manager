package com.example.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sales")
data class SaleEntity(
  @PrimaryKey(autoGenerate = true)
  val id: Long = 0,
  val productCode: String,
  val productName: String = "",
  val date: String, // e.g., "2026-08-04"
  val buyPrice: Double,
  val sellPrice: Double,
  val quantity: Int = 1,
  val profit: Double = sellPrice - buyPrice,
  val notes: String = "",
  val createdAt: Long = System.currentTimeMillis()
)
