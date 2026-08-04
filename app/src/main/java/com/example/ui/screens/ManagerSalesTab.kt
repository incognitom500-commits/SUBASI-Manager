package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material.icons.filled.TrendingUp
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
import com.example.data.local.entities.SaleEntity
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
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun ManagerSalesTab(
  viewModel: ManagementViewModel,
  modifier: Modifier = Modifier
) {
  val sales by viewModel.salesList.collectAsStateWithLifecycle()
  val products by viewModel.productsList.collectAsStateWithLifecycle()
  val editingSale by viewModel.editingSale.collectAsStateWithLifecycle()
  val salesSummary by viewModel.salesSummary.collectAsStateWithLifecycle()

  var productCode by remember { mutableStateOf("") }
  var date by remember { mutableStateOf("") }
  var buyPriceText by remember { mutableStateOf("") }
  var sellPriceText by remember { mutableStateOf("") }
  var formError by remember { mutableStateOf<String?>(null) }
  var showReportBox by remember { mutableStateOf(true) }
  var saleToDelete by remember { mutableStateOf<SaleEntity?>(null) }

  // Set default date to today
  val todayStr = remember {
    SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
  }

  // Populate form if editing
  LaunchedEffect(editingSale) {
    if (editingSale != null) {
      productCode = editingSale!!.productCode
      date = editingSale!!.date
      buyPriceText = editingSale!!.buyPrice.toString()
      sellPriceText = editingSale!!.sellPrice.toString()
      formError = null
    } else {
      productCode = ""
      date = todayStr
      buyPriceText = ""
      sellPriceText = ""
      formError = null
    }
  }

  val buyVal = buyPriceText.toDoubleOrNull() ?: 0.0
  val sellVal = sellPriceText.toDoubleOrNull() ?: 0.0
  val calculatedProfit = sellVal - buyVal

  Column(
    modifier = modifier.fillMaxWidth(),
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {

    // 1. Sales Entry Form Card
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
            text = if (editingSale != null) "বিক্রির হিসাব আপডেট করুন" else "বিক্রির হিসাব এন্ট্রি করুন",
            style = MaterialTheme.typography.titleMedium.copy(
              fontWeight = FontWeight.Bold,
              color = FrostedSapphireDark,
              fontSize = 17.sp,
              letterSpacing = (-0.2).sp
            )
          )

          if (editingSale != null) {
            FrostedPillBadge(
              text = "এডিট মোড",
              backgroundColor = AmberGold.copy(alpha = 0.2f),
              textColor = AmberGold,
              borderColor = AmberGold.copy(alpha = 0.4f)
            )
          }
        }

        HorizontalDivider(color = FrostedIceDark.copy(alpha = 0.5f))

        // Product Code input + quick select
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
          Text(
            text = "প্রোডাক্ট কোড:",
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            color = FrostedSapphireDark
          )
          OutlinedTextField(
            value = productCode,
            onValueChange = {
              productCode = it.uppercase()
              // Auto-fill price from product catalog if matched
              val matched = products.find { p -> p.code.equals(it.trim(), ignoreCase = true) }
              if (matched != null && sellPriceText.isBlank()) {
                sellPriceText = matched.price.toString()
              }
            },
            placeholder = { Text("যেমন: P01, P02", color = FrostedTextTertiary) },
            singleLine = true,
            shape = RoundedCornerShape(14.dp),
            leadingIcon = {
              Icon(imageVector = Icons.Default.QrCode, contentDescription = null, tint = FrostedSapphire)
            },
            modifier = Modifier
              .fillMaxWidth()
              .testTag("sale_input_code"),
            colors = OutlinedTextFieldDefaults.colors(
              focusedBorderColor = FrostedSapphire,
              unfocusedBorderColor = Color.White.copy(alpha = 0.8f),
              focusedContainerColor = Color.White.copy(alpha = 0.85f),
              unfocusedContainerColor = Color.White.copy(alpha = 0.6f)
            )
          )

          // Quick Code Pickers
          if (products.isNotEmpty()) {
            LazyRow(
              horizontalArrangement = Arrangement.spacedBy(8.dp),
              modifier = Modifier.padding(top = 4.dp)
            ) {
              items(products) { p ->
                val isSelected = productCode == p.code
                Surface(
                  shape = CircleShape,
                  color = if (isSelected) FrostedSapphire else FrostedIce.copy(alpha = 0.8f),
                  contentColor = if (isSelected) PureWhite else FrostedSapphireDark,
                  border = BorderStroke(1.dp, if (isSelected) FrostedSapphire else Color.White),
                  modifier = Modifier
                    .clip(CircleShape)
                    .clickable {
                      productCode = p.code
                      if (sellPriceText.isBlank() || sellVal == 0.0) {
                        sellPriceText = p.price.toString()
                      }
                    }
                ) {
                  Text(
                    text = p.code,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 5.dp)
                  )
                }
              }
            }
          }
        }

        // Date input
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
          Text(
            text = "তারিখ:",
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            color = FrostedSapphireDark
          )
          OutlinedTextField(
            value = date.ifBlank { todayStr },
            onValueChange = { date = it },
            placeholder = { Text("YYYY-MM-DD", color = FrostedTextTertiary) },
            singleLine = true,
            shape = RoundedCornerShape(14.dp),
            leadingIcon = {
              Icon(imageVector = Icons.Default.CalendarMonth, contentDescription = null, tint = FrostedSapphire)
            },
            modifier = Modifier
              .fillMaxWidth()
              .testTag("sale_input_date"),
            colors = OutlinedTextFieldDefaults.colors(
              focusedBorderColor = FrostedSapphire,
              unfocusedBorderColor = Color.White.copy(alpha = 0.8f),
              focusedContainerColor = Color.White.copy(alpha = 0.85f),
              unfocusedContainerColor = Color.White.copy(alpha = 0.6f)
            )
          )
        }

        // Buying & Selling Price in a Row
        Row(
          horizontalArrangement = Arrangement.spacedBy(10.dp),
          modifier = Modifier.fillMaxWidth()
        ) {
          Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(4.dp)
          ) {
            Text(
              text = "কেনার দাম (৳):",
              fontSize = 13.sp,
              fontWeight = FontWeight.Bold,
              color = FrostedSapphireDark
            )
            OutlinedTextField(
              value = buyPriceText,
              onValueChange = { buyPriceText = it },
              placeholder = { Text("যেমন: 850", color = FrostedTextTertiary) },
              singleLine = true,
              shape = RoundedCornerShape(14.dp),
              keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
              modifier = Modifier
                .fillMaxWidth()
                .testTag("sale_input_buy_price"),
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
            Text(
              text = "বিক্রির দাম (৳):",
              fontSize = 13.sp,
              fontWeight = FontWeight.Bold,
              color = FrostedSapphireDark
            )
            OutlinedTextField(
              value = sellPriceText,
              onValueChange = { sellPriceText = it },
              placeholder = { Text("যেমন: 1250", color = FrostedTextTertiary) },
              singleLine = true,
              shape = RoundedCornerShape(14.dp),
              keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
              modifier = Modifier
                .fillMaxWidth()
                .testTag("sale_input_sell_price"),
              colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = FrostedSapphire,
                unfocusedBorderColor = Color.White.copy(alpha = 0.8f),
                focusedContainerColor = Color.White.copy(alpha = 0.85f),
                unfocusedContainerColor = Color.White.copy(alpha = 0.6f)
              )
            )
          }
        }

        // Live Profit Indicator Preview
        if (buyPriceText.isNotBlank() || sellPriceText.isNotBlank()) {
          Surface(
            shape = RoundedCornerShape(12.dp),
            color = if (calculatedProfit >= 0) EmeraldGreenLight.copy(alpha = 0.7f) else FrostedRose.copy(alpha = 0.7f),
            border = BorderStroke(1.dp, if (calculatedProfit >= 0) EmeraldGreen.copy(alpha = 0.3f) else Color(0xFFFDA4AF)),
            modifier = Modifier.fillMaxWidth()
          ) {
            Row(
              modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Text(
                text = "আনুমানিক লাভ / লোকসান:",
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                color = FrostedTextSecondary
              )
              Text(
                text = formatTakaClean(calculatedProfit),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = if (calculatedProfit >= 0) EmeraldGreenDark else Color(0xFF9F1239)
              )
            }
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

        // Action Buttons
        Row(
          horizontalArrangement = Arrangement.spacedBy(10.dp),
          modifier = Modifier.fillMaxWidth()
        ) {
          Button(
            onClick = {
              if (productCode.isBlank() || buyVal <= 0 || sellVal <= 0) {
                formError = "দয়া করে কোড, কেনার দাম এবং বিক্রির দাম সঠিকভাবে দিন।"
                return@Button
              }
              formError = null
              viewModel.saveSale(
                productCode = productCode,
                date = date.ifBlank { todayStr },
                buyPrice = buyVal,
                sellPrice = sellVal
              )
              productCode = ""
              buyPriceText = ""
              sellPriceText = ""
              date = todayStr
            },
            colors = ButtonDefaults.buttonColors(
              containerColor = FrostedSapphire,
              contentColor = PureWhite
            ),
            shape = CircleShape,
            modifier = Modifier
              .weight(1f)
              .testTag("btn_save_sales")
          ) {
            Icon(
              imageVector = if (editingSale != null) Icons.Default.Check else Icons.Default.Add,
              contentDescription = null,
              modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = if (editingSale != null) "আপডেট করুন" else "হিসাব সেভ করুন",
              fontWeight = FontWeight.Bold
            )
          }

          if (editingSale != null) {
            OutlinedButton(
              onClick = {
                viewModel.cancelEditSale()
                formError = null
              },
              shape = CircleShape,
              border = BorderStroke(1.dp, Color(0xFFFDA4AF)),
              colors = ButtonDefaults.outlinedButtonColors(
                containerColor = FrostedRose.copy(alpha = 0.6f)
              ),
              modifier = Modifier.testTag("btn_cancel_sales")
            ) {
              Text("বাতিল করুন", color = Color(0xFF9F1239), fontWeight = FontWeight.Bold)
            }
          }
        }
      }
    }

    // 2. Sales Records List / Table
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
            text = "বিক্রির হিসাব তালিকা (${sales.size})",
            style = MaterialTheme.typography.titleMedium.copy(
              fontWeight = FontWeight.Bold,
              color = FrostedSapphireDark,
              letterSpacing = (-0.2).sp
            )
          )
          FrostedPillBadge(
            text = "মোট লাভ: ${formatTakaClean(salesSummary.totalProfit)}",
            backgroundColor = FrostedIce.copy(alpha = 0.8f),
            textColor = FrostedSapphireDark,
            borderColor = Color.White
          )
        }

        if (sales.isEmpty()) {
          Box(
            modifier = Modifier
              .fillMaxWidth()
              .padding(24.dp),
            contentAlignment = Alignment.Center
          ) {
            Text(
              text = "বর্তমানে কোনো বিক্রির এন্ট্রি নেই। উপরে ফর্ম পূরণ করে সেভ করুন।",
              color = FrostedTextSecondary,
              fontSize = 13.sp
            )
          }
        } else {
          sales.forEach { item ->
            FrostedGlassCard(
              shape = RoundedCornerShape(16.dp),
              backgroundColor = Color.White.copy(alpha = 0.65f),
              borderColor = Color.White.copy(alpha = 0.85f),
              modifier = Modifier
                .fillMaxWidth()
                .testTag("sale_item_${item.id}")
            ) {
              Column(
                modifier = Modifier.padding(14.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
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
                        text = item.productCode,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                      )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                      text = item.date,
                      fontSize = 12.sp,
                      color = FrostedTextSecondary
                    )
                  }

                  // Action Buttons (Edit & Delete)
                  Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    IconButton(
                      onClick = { viewModel.prepareEditSale(item) },
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
                      onClick = { saleToDelete = item },
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
                      text = "কেনা: ${formatTakaClean(item.buyPrice)}",
                      fontSize = 12.sp,
                      color = FrostedTextSecondary
                    )
                    Text(
                      text = "বিক্রি: ${formatTakaClean(item.sellPrice)}",
                      fontSize = 12.sp,
                      fontWeight = FontWeight.SemiBold,
                      color = FrostedSapphireDark
                    )
                  }

                  FrostedPillBadge(
                    text = "লাভ: ${formatTakaClean(item.profit)}",
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

    // 3. Monthly / Total Report Summary Section
    FrostedGlassCard(
      shape = RoundedCornerShape(20.dp),
      backgroundColor = Color.White.copy(alpha = 0.74f),
      borderColor = Color.White.copy(alpha = 0.88f),
      elevation = 1.dp,
      modifier = Modifier.fillMaxWidth()
    ) {
      Column(
        modifier = Modifier.padding(18.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
      ) {
        Button(
          onClick = { showReportBox = !showReportBox },
          colors = ButtonDefaults.buttonColors(
            containerColor = FrostedIce.copy(alpha = 0.8f),
            contentColor = FrostedSapphireDark
          ),
          border = BorderStroke(1.dp, Color.White),
          shape = CircleShape,
          modifier = Modifier.fillMaxWidth()
        ) {
          Icon(
            imageVector = Icons.Default.TrendingUp,
            contentDescription = null,
            tint = FrostedSapphire,
            modifier = Modifier.size(18.dp)
          )
          Spacer(modifier = Modifier.width(8.dp))
          Text(
            text = if (showReportBox) "টোটাল রিপোর্ট লুকান" else "মাসিক/টোটাল রিপোর্ট তৈরি করুন",
            fontWeight = FontWeight.Bold
          )
        }

        AnimatedVisibility(visible = showReportBox) {
          FrostedGlassCard(
            shape = RoundedCornerShape(16.dp),
            backgroundColor = Color.White.copy(alpha = 0.82f),
            borderColor = FrostedSapphire.copy(alpha = 0.3f),
            modifier = Modifier.fillMaxWidth()
          ) {
            Column(
              modifier = Modifier.padding(18.dp),
              verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
              Text(
                text = "সার্বিক বিক্রয় ও লাভ রিপোর্ট",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = FrostedSapphireDark,
                letterSpacing = (-0.2).sp
              )

              HorizontalDivider(color = FrostedIceDark.copy(alpha = 0.5f))

              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
              ) {
                Text("সর্বমোট কেনা:", fontWeight = FontWeight.SemiBold, color = FrostedTextSecondary)
                Text(
                  text = formatTakaClean(salesSummary.totalBuy),
                  fontWeight = FontWeight.Bold,
                  color = FrostedSapphireDark
                )
              }

              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
              ) {
                Text("সর্বমোট বিক্রি:", fontWeight = FontWeight.SemiBold, color = FrostedTextSecondary)
                Text(
                  text = formatTakaClean(salesSummary.totalSell),
                  fontWeight = FontWeight.Bold,
                  color = FrostedSapphireDark
                )
              }

              HorizontalDivider(color = FrostedIceDark.copy(alpha = 0.5f))

              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
              ) {
                Text(
                  text = "সর্বমোট লাভ:",
                  fontWeight = FontWeight.Bold,
                  fontSize = 16.sp,
                  color = EmeraldGreenDark
                )
                Text(
                  text = formatTakaClean(salesSummary.totalProfit),
                  fontWeight = FontWeight.ExtraBold,
                  fontSize = 18.sp,
                  color = EmeraldGreenDark
                )
              }
            }
          }
        }
      }
    }

  }

  // Delete Confirmation Dialog
  if (saleToDelete != null) {
    AlertDialog(
      onDismissRequest = { saleToDelete = null },
      title = { Text("হিসাব ডিলিট করবেন?", fontWeight = FontWeight.Bold) },
      text = {
        Text("কোড: ${saleToDelete!!.productCode} (${saleToDelete!!.date}) এর বিক্রির হিসাবটি ডিলিট করতে চান?")
      },
      confirmButton = {
        Button(
          onClick = {
            viewModel.deleteSale(saleToDelete!!)
            saleToDelete = null
          },
          colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE11D48)),
          shape = CircleShape
        ) {
          Text("ডিলিট করুন", color = PureWhite)
        }
      },
      dismissButton = {
        TextButton(onClick = { saleToDelete = null }) {
          Text("বাতিল", color = FrostedSapphireDark)
        }
      }
    )
  }
}

