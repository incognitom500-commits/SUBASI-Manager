package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Inventory2
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material.icons.filled.Straighten
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.local.entities.ProductEntity
import com.example.ui.ManagementViewModel
import com.example.ui.components.FrostedGlassCard
import com.example.ui.components.FrostedPillBadge
import com.example.ui.components.formatTakaClean
import com.example.ui.theme.AmberGold
import com.example.ui.theme.CrimsonRed
import com.example.ui.theme.EmeraldGreen
import com.example.ui.theme.EmeraldGreenDark
import com.example.ui.theme.EmeraldGreenLight
import com.example.ui.theme.FrostedIce
import com.example.ui.theme.FrostedIceDark
import com.example.ui.theme.FrostedLavender
import com.example.ui.theme.FrostedRose
import com.example.ui.theme.FrostedSapphire
import com.example.ui.theme.FrostedSapphireDark
import com.example.ui.theme.FrostedTextPrimary
import com.example.ui.theme.FrostedTextSecondary
import com.example.ui.theme.FrostedTextTertiary
import com.example.ui.theme.PureWhite

@Composable
fun ManagerProductsTab(
  viewModel: ManagementViewModel,
  modifier: Modifier = Modifier
) {
  val products by viewModel.productsList.collectAsStateWithLifecycle()
  val editingProduct by viewModel.editingProduct.collectAsStateWithLifecycle()

  var name by remember { mutableStateOf("") }
  var code by remember { mutableStateOf("") }
  var priceText by remember { mutableStateOf("") }
  var size by remember { mutableStateOf("") }
  var color by remember { mutableStateOf("") }
  var stockText by remember { mutableStateOf("10") }
  var formError by remember { mutableStateOf<String?>(null) }
  var productToDelete by remember { mutableStateOf<ProductEntity?>(null) }

  LaunchedEffect(editingProduct) {
    if (editingProduct != null) {
      name = editingProduct!!.name
      code = editingProduct!!.code
      priceText = editingProduct!!.price.toString()
      size = editingProduct!!.size
      color = editingProduct!!.color
      stockText = editingProduct!!.stockQuantity.toString()
      formError = null
    } else {
      name = ""
      code = ""
      priceText = ""
      size = ""
      color = ""
      stockText = "10"
      formError = null
    }
  }

  val priceVal = priceText.toDoubleOrNull() ?: 0.0
  val stockVal = stockText.toIntOrNull() ?: 10

  Column(
    modifier = modifier.fillMaxWidth(),
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {

    // 1. Product Form Card
    FrostedGlassCard(
      shape = RoundedCornerShape(24.dp),
      backgroundColor = Color.White.copy(alpha = 0.76f),
      borderColor = Color.White.copy(alpha = 0.90f),
      elevation = 2.dp,
      modifier = Modifier.fillMaxWidth()
    ) {
      Column(
        modifier = Modifier.padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
      ) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = if (editingProduct != null) "পণ্যের বিবরণ আপডেট করুন" else "পণ্যের বিবরণ সেভ করুন",
            style = MaterialTheme.typography.titleMedium.copy(
              fontWeight = FontWeight.Bold,
              color = FrostedSapphireDark,
              fontSize = 17.sp,
              letterSpacing = (-0.2).sp
            )
          )

          if (editingProduct != null) {
            FrostedPillBadge(
              text = "এডিট মোড",
              backgroundColor = AmberGold.copy(alpha = 0.2f),
              textColor = AmberGold,
              borderColor = AmberGold.copy(alpha = 0.4f)
            )
          }
        }

        HorizontalDivider(color = FrostedIceDark.copy(alpha = 0.5f))

        // Name input
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
          Text("নাম:", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = FrostedSapphireDark)
          OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            placeholder = { Text("যেমন: প্রিমিয়াম ফরমাল শার্ট", color = FrostedTextTertiary) },
            singleLine = true,
            shape = RoundedCornerShape(14.dp),
            leadingIcon = {
              Icon(imageVector = Icons.Default.Inventory2, contentDescription = null, tint = FrostedSapphire)
            },
            modifier = Modifier
              .fillMaxWidth()
              .testTag("product_input_name"),
            colors = OutlinedTextFieldDefaults.colors(
              focusedBorderColor = FrostedSapphire,
              unfocusedBorderColor = Color.White.copy(alpha = 0.8f),
              focusedContainerColor = Color.White.copy(alpha = 0.85f),
              unfocusedContainerColor = Color.White.copy(alpha = 0.6f)
            )
          )
        }

        // Code & Price Row
        Row(
          horizontalArrangement = Arrangement.spacedBy(10.dp),
          modifier = Modifier.fillMaxWidth()
        ) {
          Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(4.dp)
          ) {
            Text("কোড:", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = FrostedSapphireDark)
            OutlinedTextField(
              value = code,
              onValueChange = { code = it.uppercase() },
              placeholder = { Text("যেমন: P01", color = FrostedTextTertiary) },
              singleLine = true,
              shape = RoundedCornerShape(14.dp),
              leadingIcon = {
                Icon(imageVector = Icons.Default.QrCode, contentDescription = null, tint = FrostedSapphire)
              },
              modifier = Modifier
                .fillMaxWidth()
                .testTag("product_input_code"),
              colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = FrostedSapphire,
                unfocusedBorderColor = Color.White.copy(alpha = 0.8f),
                focusedContainerColor = Color.White.copy(alpha = 0.85f),
                unfocusedContainerColor = Color.White.copy(alpha = 0.6f)
              )
            )
          }

          Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(4.dp)
          ) {
            Text("দাম (৳):", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = FrostedSapphireDark)
            OutlinedTextField(
              value = priceText,
              onValueChange = { priceText = it },
              placeholder = { Text("যেমন: 1250", color = FrostedTextTertiary) },
              singleLine = true,
              shape = RoundedCornerShape(14.dp),
              keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
              leadingIcon = {
                Icon(imageVector = Icons.Default.Payments, contentDescription = null, tint = FrostedSapphire)
              },
              modifier = Modifier
                .fillMaxWidth()
                .testTag("product_input_price"),
              colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = FrostedSapphire,
                unfocusedBorderColor = Color.White.copy(alpha = 0.8f),
                focusedContainerColor = Color.White.copy(alpha = 0.85f),
                unfocusedContainerColor = Color.White.copy(alpha = 0.6f)
              )
            )
          }
        }

        // Size & Color Row
        Row(
          horizontalArrangement = Arrangement.spacedBy(10.dp),
          modifier = Modifier.fillMaxWidth()
        ) {
          Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(4.dp)
          ) {
            Text("সাইজ:", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = FrostedSapphireDark)
            OutlinedTextField(
              value = size,
              onValueChange = { size = it },
              placeholder = { Text("M, L, XL", color = FrostedTextTertiary) },
              singleLine = true,
              shape = RoundedCornerShape(14.dp),
              leadingIcon = {
                Icon(imageVector = Icons.Default.Straighten, contentDescription = null, tint = FrostedSapphire)
              },
              modifier = Modifier
                .fillMaxWidth()
                .testTag("product_input_size"),
              colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = FrostedSapphire,
                unfocusedBorderColor = Color.White.copy(alpha = 0.8f),
                focusedContainerColor = Color.White.copy(alpha = 0.85f),
                unfocusedContainerColor = Color.White.copy(alpha = 0.6f)
              )
            )
          }

          Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(4.dp)
          ) {
            Text("কালার:", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = FrostedSapphireDark)
            OutlinedTextField(
              value = color,
              onValueChange = { color = it },
              placeholder = { Text("নীল, সাদা", color = FrostedTextTertiary) },
              singleLine = true,
              shape = RoundedCornerShape(14.dp),
              leadingIcon = {
                Icon(imageVector = Icons.Default.Palette, contentDescription = null, tint = FrostedSapphire)
              },
              modifier = Modifier
                .fillMaxWidth()
                .testTag("product_input_color"),
              colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = FrostedSapphire,
                unfocusedBorderColor = Color.White.copy(alpha = 0.8f),
                focusedContainerColor = Color.White.copy(alpha = 0.85f),
                unfocusedContainerColor = Color.White.copy(alpha = 0.6f)
              )
            )
          }
        }

        if (formError != null) {
          Text(
            text = formError!!,
            color = Color(0xFFE11D48),
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium
          )
        }

        // Save & Cancel Buttons
        Row(
          horizontalArrangement = Arrangement.spacedBy(10.dp),
          modifier = Modifier.fillMaxWidth()
        ) {
          Button(
            onClick = {
              if (name.isBlank() || code.isBlank() || priceVal <= 0) {
                formError = "দয়া করে নাম, কোড এবং দাম প্রদান করুন।"
                return@Button
              }
              formError = null
              viewModel.saveProduct(
                name = name,
                code = code,
                price = priceVal,
                size = size,
                color = color,
                stock = stockVal
              )
              name = ""
              code = ""
              priceText = ""
              size = ""
              color = ""
            },
            colors = ButtonDefaults.buttonColors(
              containerColor = FrostedSapphire,
              contentColor = PureWhite
            ),
            shape = CircleShape,
            modifier = Modifier
              .weight(1f)
              .testTag("btn_save_product")
          ) {
            Icon(
              imageVector = if (editingProduct != null) Icons.Default.Check else Icons.Default.Add,
              contentDescription = null,
              modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = if (editingProduct != null) "আপডেট করুন" else "বিবরণ সেভ করুন",
              fontWeight = FontWeight.Bold
            )
          }

          if (editingProduct != null) {
            OutlinedButton(
              onClick = {
                viewModel.cancelEditProduct()
                formError = null
              },
              shape = CircleShape,
              border = BorderStroke(1.dp, Color(0xFFFDA4AF)),
              colors = ButtonDefaults.outlinedButtonColors(
                containerColor = FrostedRose.copy(alpha = 0.6f)
              ),
              modifier = Modifier.testTag("btn_cancel_product")
            ) {
              Text("বাতিল করুন", color = Color(0xFF9F1239), fontWeight = FontWeight.Bold)
            }
          }
        }
      }
    }

    // 2. Products List / Table
    FrostedGlassCard(
      shape = RoundedCornerShape(20.dp),
      backgroundColor = Color.White.copy(alpha = 0.74f),
      borderColor = Color.White.copy(alpha = 0.88f),
      elevation = 1.dp,
      modifier = Modifier.fillMaxWidth()
    ) {
      Column(
        modifier = Modifier.padding(18.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
      ) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = "পণ্যের তালিকা (${products.size})",
            style = MaterialTheme.typography.titleMedium.copy(
              fontWeight = FontWeight.Bold,
              color = FrostedSapphireDark,
              letterSpacing = (-0.2).sp
            )
          )
        }

        if (products.isEmpty()) {
          Box(
            modifier = Modifier
              .fillMaxWidth()
              .padding(24.dp),
            contentAlignment = Alignment.Center
          ) {
            Text(
              text = "কোনো পণ্য যুক্ত করা হয়নি। উপরে ফর্ম পূরণ করে সেভ করুন।",
              color = FrostedTextSecondary,
              fontSize = 13.sp
            )
          }
        } else {
          products.forEach { item ->
            FrostedGlassCard(
              shape = RoundedCornerShape(16.dp),
              backgroundColor = Color.White.copy(alpha = 0.65f),
              borderColor = Color.White.copy(alpha = 0.85f),
              modifier = Modifier
                .fillMaxWidth()
                .testTag("product_item_${item.code}")
            ) {
              Column(
                modifier = Modifier.padding(14.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
              ) {
                Row(
                  modifier = Modifier.fillMaxWidth(),
                  horizontalArrangement = Arrangement.SpaceBetween,
                  verticalAlignment = Alignment.CenterVertically
                ) {
                  Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                      shape = RoundedCornerShape(8.dp),
                      color = FrostedSapphire,
                      contentColor = PureWhite
                    ) {
                      Text(
                        text = item.code,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                      )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                      text = item.name,
                      fontWeight = FontWeight.Bold,
                      fontSize = 14.sp,
                      color = FrostedSapphireDark
                    )
                  }

                  // Edit & Delete
                  Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    IconButton(
                      onClick = { viewModel.prepareEditProduct(item) },
                      modifier = Modifier.size(32.dp)
                    ) {
                      Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "এডিট",
                        tint = FrostedSapphire,
                        modifier = Modifier.size(18.dp)
                      )
                    }
                    IconButton(
                      onClick = { productToDelete = item },
                      modifier = Modifier.size(32.dp)
                    ) {
                      Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "ডিলিট",
                        tint = Color(0xFFE11D48),
                        modifier = Modifier.size(18.dp)
                      )
                    }
                  }
                }

                HorizontalDivider(color = FrostedIceDark.copy(alpha = 0.4f))

                Row(
                  modifier = Modifier.fillMaxWidth(),
                  horizontalArrangement = Arrangement.SpaceBetween,
                  verticalAlignment = Alignment.CenterVertically
                ) {
                  Column {
                    Text(
                      text = "সাইজ: ${item.size.ifBlank { "স্ট্যান্ডার্ড" }} • কালার: ${item.color.ifBlank { "-" }}",
                      fontSize = 12.sp,
                      color = FrostedTextSecondary
                    )
                  }

                  FrostedPillBadge(
                    text = formatTakaClean(item.price),
                    backgroundColor = EmeraldGreenLight.copy(alpha = 0.7f),
                    textColor = EmeraldGreenDark,
                    borderColor = EmeraldGreen.copy(alpha = 0.4f)
                  )
                }
              }
            }
          }
        }
      }
    }

  }

  // Delete Confirmation Dialog
  if (productToDelete != null) {
    AlertDialog(
      onDismissRequest = { productToDelete = null },
      title = { Text("পণ্য ডিলিট করবেন?", fontWeight = FontWeight.Bold) },
      text = {
        Text("কোড: ${productToDelete!!.code} (${productToDelete!!.name}) পণ্যটি ক্যাটালগ থেকে মুছে ফেলতে চান?")
      },
      confirmButton = {
        Button(
          onClick = {
            viewModel.deleteProduct(productToDelete!!)
            productToDelete = null
          },
          colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE11D48)),
          shape = CircleShape
        ) {
          Text("ডিলিট করুন", color = PureWhite)
        }
      },
      dismissButton = {
        TextButton(onClick = { productToDelete = null }) {
          Text("বাতিল", color = FrostedSapphireDark)
        }
      }
    )
  }
}

