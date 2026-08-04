package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Inventory2
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Straighten
import androidx.compose.material.icons.filled.Warning
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ui.ManagementViewModel
import com.example.ui.components.FrostedGlassCard
import com.example.ui.components.FrostedPillBadge
import com.example.ui.components.formatTakaClean
import com.example.ui.theme.AmberGold
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
fun UserSearchScreen(
  viewModel: ManagementViewModel,
  onNavigateBack: () -> Unit,
  modifier: Modifier = Modifier
) {
  val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
  val searchedProduct by viewModel.searchedProduct.collectAsStateWithLifecycle()
  val hasSearched by viewModel.hasSearched.collectAsStateWithLifecycle()
  val allProducts by viewModel.productsList.collectAsStateWithLifecycle()
  val clipboardManager = LocalClipboardManager.current

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .background(Color.Transparent),
    contentPadding = PaddingValues(16.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {

    // Header Card
    item {
      FrostedGlassCard(
        shape = RoundedCornerShape(20.dp),
        backgroundColor = Color.White.copy(alpha = 0.75f),
        borderColor = Color.White.copy(alpha = 0.9f),
        elevation = 1.dp,
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(
          modifier = Modifier.padding(18.dp),
          verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
          ) {
            Row(
              verticalAlignment = Alignment.CenterVertically,
              modifier = Modifier.weight(1f)
            ) {
              Box(
                modifier = Modifier
                  .size(42.dp)
                  .clip(CircleShape)
                  .background(FrostedIce.copy(alpha = 0.8f)),
                contentAlignment = Alignment.Center
              ) {
                Icon(
                  imageVector = Icons.Default.Search,
                  contentDescription = null,
                  tint = FrostedSapphire,
                  modifier = Modifier.size(22.dp)
                )
              }
              Spacer(modifier = Modifier.width(12.dp))
              Column {
                Text(
                  text = "ইউজার প্যানেল (প্রোডাক্ট খুঁজুন)",
                  style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = FrostedSapphireDark,
                    fontSize = 18.sp,
                    letterSpacing = (-0.2).sp
                  )
                )
                Text(
                  text = "এখানে প্রোডাক্ট কোড দিয়ে বিস্তারিত দেখুন",
                  fontSize = 12.sp,
                  color = FrostedTextSecondary
                )
              }
            }

            OutlinedButton(
              onClick = onNavigateBack,
              shape = CircleShape,
              border = BorderStroke(1.dp, FrostedSapphire.copy(alpha = 0.3f)),
              colors = ButtonDefaults.outlinedButtonColors(
                containerColor = FrostedIce.copy(alpha = 0.5f)
              ),
              contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp)
            ) {
              Text("মেনুতে ফিরুন", fontSize = 11.sp, color = FrostedSapphireDark, fontWeight = FontWeight.Bold)
            }
          }
        }
      }
    }

    // Search Input Section
    item {
      FrostedGlassCard(
        shape = RoundedCornerShape(24.dp),
        backgroundColor = Color.White.copy(alpha = 0.74f),
        borderColor = Color.White.copy(alpha = 0.88f),
        elevation = 2.dp,
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(
          modifier = Modifier.padding(20.dp),
          verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
          Text(
            text = "এখানেই আপনার প্রোডাক্ট এর কোড দিন:",
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            color = FrostedSapphireDark
          )

          OutlinedTextField(
            value = searchQuery,
            onValueChange = { viewModel.updateSearchQuery(it) },
            placeholder = { Text("যেমন: P01, P02 ইত্যাদি...", color = FrostedTextTertiary) },
            singleLine = true,
            leadingIcon = {
              Icon(
                imageVector = Icons.Default.QrCode,
                contentDescription = null,
                tint = FrostedSapphire
              )
            },
            trailingIcon = {
              if (searchQuery.isNotEmpty()) {
                IconButton(onClick = { viewModel.clearSearch() }) {
                  Icon(
                    imageVector = Icons.Default.Clear,
                    contentDescription = "মুছুন",
                    tint = FrostedTextSecondary
                  )
                }
              }
            },
            shape = RoundedCornerShape(14.dp),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = { viewModel.searchProduct() }),
            modifier = Modifier
              .fillMaxWidth()
              .testTag("product_search_input"),
            colors = OutlinedTextFieldDefaults.colors(
              focusedBorderColor = FrostedSapphire,
              unfocusedBorderColor = Color.White.copy(alpha = 0.8f),
              focusedContainerColor = Color.White.copy(alpha = 0.85f),
              unfocusedContainerColor = Color.White.copy(alpha = 0.6f)
            )
          )

          // Quick Code Suggestions / Chips
          if (allProducts.isNotEmpty()) {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
              Text(
                text = "দ্রুত সিলেক্ট করুন:",
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                color = FrostedTextSecondary
              )
              LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(allProducts.take(8)) { product ->
                  val isSelected = searchQuery == product.code
                  Surface(
                    shape = CircleShape,
                    color = if (isSelected) FrostedSapphire else FrostedIce.copy(alpha = 0.8f),
                    contentColor = if (isSelected) PureWhite else FrostedSapphireDark,
                    border = BorderStroke(1.dp, if (isSelected) FrostedSapphire else Color.White),
                    modifier = Modifier
                      .clip(CircleShape)
                      .clickable {
                        viewModel.updateSearchQuery(product.code)
                        viewModel.searchProduct(product.code)
                      }
                  ) {
                    Text(
                      text = "${product.code} (${product.name.take(6)}..)",
                      fontSize = 12.sp,
                      fontWeight = FontWeight.Bold,
                      modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                    )
                  }
                }
              }
            }
          }

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
          ) {
            Button(
              onClick = { viewModel.searchProduct() },
              colors = ButtonDefaults.buttonColors(
                containerColor = FrostedSapphire,
                contentColor = PureWhite
              ),
              shape = CircleShape,
              modifier = Modifier
                .weight(1f)
                .testTag("product_search_button")
            ) {
              Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
                modifier = Modifier.size(18.dp)
              )
              Spacer(modifier = Modifier.width(6.dp))
              Text("খুঁজুন (Search)", fontWeight = FontWeight.Bold)
            }

            if (hasSearched) {
              OutlinedButton(
                onClick = { viewModel.clearSearch() },
                shape = CircleShape,
                border = BorderStroke(1.dp, FrostedSapphire.copy(alpha = 0.3f)),
                colors = ButtonDefaults.outlinedButtonColors(
                  containerColor = FrostedIce.copy(alpha = 0.5f)
                )
              ) {
                Text("রিসেট", color = FrostedSapphireDark, fontWeight = FontWeight.Bold)
              }
            }
          }
        }
      }
    }

    // Search Result Section
    if (hasSearched) {
      item {
        AnimatedVisibility(
          visible = true,
          enter = fadeIn(),
          exit = fadeOut()
        ) {
          if (searchedProduct != null) {
            val product = searchedProduct!!
            FrostedGlassCard(
              shape = RoundedCornerShape(24.dp),
              backgroundColor = Color.White.copy(alpha = 0.82f),
              borderColor = FrostedSapphire.copy(alpha = 0.4f),
              elevation = 3.dp,
              modifier = Modifier
                .fillMaxWidth()
                .testTag("search_result_box")
            ) {
              Column(
                modifier = Modifier
                  .fillMaxWidth()
                  .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
              ) {
                Row(
                  modifier = Modifier.fillMaxWidth(),
                  horizontalArrangement = Arrangement.SpaceBetween,
                  verticalAlignment = Alignment.CenterVertically
                ) {
                  Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                      shape = RoundedCornerShape(10.dp),
                      color = FrostedSapphire,
                      contentColor = PureWhite
                    ) {
                      Text(
                        text = product.code,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 5.dp)
                      )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                      text = "পণ্যের বিবরণ",
                      style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = FrostedSapphireDark,
                        fontSize = 18.sp
                      )
                    )
                  }

                  FrostedPillBadge(
                    text = "স্টকে আছে: ${product.stockQuantity}",
                    backgroundColor = EmeraldGreenLight.copy(alpha = 0.7f),
                    textColor = EmeraldGreenDark,
                    borderColor = EmeraldGreen.copy(alpha = 0.4f)
                  )
                }

                HorizontalDivider(color = FrostedIceDark.copy(alpha = 0.5f))

                // Details Rows
                DetailItemRow(
                  icon = Icons.Default.Inventory2,
                  label = "নাম:",
                  value = product.name
                )

                DetailItemRow(
                  icon = Icons.Default.QrCode,
                  label = "কোড:",
                  value = product.code
                )

                DetailItemRow(
                  icon = Icons.Default.Payments,
                  label = "দাম:",
                  value = formatTakaClean(product.price),
                  highlight = true
                )

                DetailItemRow(
                  icon = Icons.Default.Straighten,
                  label = "সাইজ:",
                  value = product.size.ifBlank { "স্ট্যান্ডার্ড" }
                )

                DetailItemRow(
                  icon = Icons.Default.Palette,
                  label = "কালার:",
                  value = product.color.ifBlank { "নির্দিষ্ট নয়" }
                )

                Spacer(modifier = Modifier.height(4.dp))

                Button(
                  onClick = {
                    val info = "প্রোডাক্ট: ${product.name}\nকোড: ${product.code}\nদাম: ${formatTakaClean(product.price)}\nসাইজ: ${product.size}\nকালার: ${product.color}"
                    clipboardManager.setText(AnnotatedString(info))
                    viewModel.showMessage("প্রোডাক্টের বিবরণ কপি করা হয়েছে!")
                  },
                  colors = ButtonDefaults.buttonColors(
                    containerColor = FrostedSapphire,
                    contentColor = PureWhite
                  ),
                  shape = CircleShape,
                  modifier = Modifier.fillMaxWidth()
                ) {
                  Icon(
                    imageVector = Icons.Default.ContentCopy,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp)
                  )
                  Spacer(modifier = Modifier.width(6.dp))
                  Text("বিবরণ কপি করুন", fontWeight = FontWeight.Bold)
                }
              }
            }
          } else {
            // Not Found Box
            FrostedGlassCard(
              shape = RoundedCornerShape(20.dp),
              backgroundColor = FrostedRose.copy(alpha = 0.7f),
              borderColor = Color(0xFFFDA4AF),
              modifier = Modifier
                .fillMaxWidth()
                .testTag("search_not_found_box")
            ) {
              Column(
                modifier = Modifier
                  .fillMaxWidth()
                  .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
              ) {
                Icon(
                  imageVector = Icons.Default.Warning,
                  contentDescription = null,
                  tint = Color(0xFFE11D48),
                  modifier = Modifier.size(40.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                  text = "কোনো প্রোডাক্ট পাওয়া যায়নি।",
                  fontWeight = FontWeight.Bold,
                  fontSize = 16.sp,
                  color = Color(0xFF9F1239)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                  text = "দয়া করে সঠিক প্রোডাক্ট কোড দিন অথবা ম্যানেজার প্যানেলে চেক করুন।",
                  fontSize = 12.sp,
                  color = FrostedTextSecondary
                )
              }
            }
          }
        }
      }
    }

    // Available Products Catalog Overview
    item {
      Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        Text(
          text = "ক্যাটালগ পণ্যসমূহ (${allProducts.size})",
          style = MaterialTheme.typography.titleMedium.copy(
            fontWeight = FontWeight.Bold,
            color = FrostedSapphireDark,
            letterSpacing = (-0.2).sp
          )
        )

        allProducts.forEach { item ->
          FrostedGlassCard(
            shape = RoundedCornerShape(16.dp),
            backgroundColor = Color.White.copy(alpha = 0.70f),
            borderColor = Color.White.copy(alpha = 0.85f),
            modifier = Modifier
              .fillMaxWidth()
              .clip(RoundedCornerShape(16.dp))
              .clickable {
                viewModel.updateSearchQuery(item.code)
                viewModel.searchProduct(item.code)
              }
          ) {
            Row(
              modifier = Modifier.padding(14.dp),
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.SpaceBetween
            ) {
              Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
              ) {
                Surface(
                  shape = RoundedCornerShape(10.dp),
                  color = FrostedIce.copy(alpha = 0.8f),
                  border = BorderStroke(1.dp, Color.White),
                  modifier = Modifier.size(38.dp)
                ) {
                  Box(contentAlignment = Alignment.Center) {
                    Text(
                      text = item.code,
                      fontWeight = FontWeight.Bold,
                      fontSize = 11.sp,
                      color = FrostedSapphireDark
                    )
                  }
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                  Text(
                    text = item.name,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp,
                    color = FrostedSapphireDark
                  )
                  Spacer(modifier = Modifier.height(2.dp))
                  Text(
                    text = "সাইজ: ${item.size} • কালার: ${item.color}",
                    fontSize = 11.sp,
                    color = FrostedTextSecondary
                  )
                }
              }

              FrostedPillBadge(
                text = formatTakaClean(item.price),
                backgroundColor = FrostedIce.copy(alpha = 0.7f),
                textColor = FrostedSapphireDark,
                borderColor = Color.White
              )
            }
          }
        }
      }
    }

  }
}

@Composable
private fun DetailItemRow(
  icon: androidx.compose.ui.graphics.vector.ImageVector,
  label: String,
  value: String,
  highlight: Boolean = false
) {
  Row(
    modifier = Modifier.fillMaxWidth(),
    verticalAlignment = Alignment.CenterVertically
  ) {
    Icon(
      imageVector = icon,
      contentDescription = null,
      tint = if (highlight) AmberGold else FrostedSapphire,
      modifier = Modifier.size(18.dp)
    )
    Spacer(modifier = Modifier.width(10.dp))
    Text(
      text = label,
      fontWeight = FontWeight.Bold,
      fontSize = 14.sp,
      color = FrostedTextSecondary,
      modifier = Modifier.width(70.dp)
    )
    Text(
      text = value,
      fontWeight = if (highlight) FontWeight.Bold else FontWeight.Medium,
      fontSize = if (highlight) 16.sp else 14.sp,
      color = if (highlight) EmeraldGreenDark else FrostedSapphireDark
    )
  }
}

