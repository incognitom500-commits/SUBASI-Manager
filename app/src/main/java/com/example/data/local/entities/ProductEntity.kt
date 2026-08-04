package com.example.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class ProductEntity(
  @PrimaryKey(autoGenerate = true)
  val id: Long = 0,
  val code: String,
  val name: String,
  val price: Double,
  val size: String,
  val color: String,
  val stockQuantity: Int = 10,
  val category: String = "সাধারণ",
  val createdAt: Long = System.currentTimeMillis()
)
