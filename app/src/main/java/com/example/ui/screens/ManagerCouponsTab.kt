package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CardGiftcard
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.LocalOffer
import androidx.compose.material.icons.filled.Percent
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
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.local.entities.CouponEntity
import com.example.ui.ManagementViewModel
import com.example.ui.components.FrostedGlassCard
import com.example.ui.components.FrostedPillBadge
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
fun ManagerCouponsTab(
  viewModel: ManagementViewModel,
  modifier: Modifier = Modifier
) {
  val coupons by viewModel.couponsList.collectAsStateWithLifecycle()
  val editingCoupon by viewModel.editingCoupon.collectAsStateWithLifecycle()

  var title by remember { mutableStateOf("") }
  var code by remember { mutableStateOf("") }
  var discount by remember { mutableStateOf("") }
  var description by remember { mutableStateOf("") }
  var formError by remember { mutableStateOf<String?>(null) }
  var couponToDelete by remember { mutableStateOf<CouponEntity?>(null) }

  LaunchedEffect(editingCoupon) {
    if (editingCoupon != null) {
      title = editingCoupon!!.title
      code = editingCoupon!!.code
      discount = editingCoupon!!.discount
      description = editingCoupon!!.description
      formError = null
    } else {
      title = ""
      code = ""
      discount = ""
      description = ""
      formError = null
    }
  }

  Column(
    modifier = modifier.fillMaxWidth(),
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {

    // 1. Coupon Form Card
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
            text = if (editingCoupon != null) "কুপন আপডেট করুন" else "কুপন তৈরি করুন",
            style = MaterialTheme.typography.titleMedium.copy(
              fontWeight = FontWeight.Bold,
              color = FrostedSapphireDark,
              fontSize = 17.sp,
              letterSpacing = (-0.2).sp
            )
          )

          if (editingCoupon != null) {
            FrostedPillBadge(
              text = "এডিট মোড",
              backgroundColor = AmberGold.copy(alpha = 0.2f),
              textColor = AmberGold,
              borderColor = AmberGold.copy(alpha = 0.4f)
            )
          }
        }

        HorizontalDivider(color = FrostedIceDark.copy(alpha = 0.5f))

        // Title input
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
          Text(
            text = "কুপনের টাইটেল (যেমন: ঈদ অফার):",
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            color = FrostedSapphireDark
          )
          OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            placeholder = { Text("যেমন: ঈদ স্পেশাল অফার", color = FrostedTextTertiary) },
            singleLine = true,
            shape = RoundedCornerShape(14.dp),
            leadingIcon = {
              Icon(imageVector = Icons.Default.CardGiftcard, contentDescription = null, tint = FrostedSapphire)
            },
            modifier = Modifier
              .fillMaxWidth()
              .testTag("coupon_input_title"),
            colors = OutlinedTextFieldDefaults.colors(
              focusedBorderColor = FrostedSapphire,
              unfocusedBorderColor = Color.White.copy(alpha = 0.8f),
              focusedContainerColor = Color.White.copy(alpha = 0.85f),
              unfocusedContainerColor = Color.White.copy(alpha = 0.6f)
            )
          )
        }

        // Code & Discount Row
        Row(
          horizontalArrangement = Arrangement.spacedBy(10.dp),
          modifier = Modifier.fillMaxWidth()
        ) {
          Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(4.dp)
          ) {
            Text(
              text = "কুপন কোড (যেমন: EID10):",
              fontSize = 13.sp,
              fontWeight = FontWeight.Bold,
              color = FrostedSapphireDark
            )
            OutlinedTextField(
              value = code,
              onValueChange = { code = it.uppercase() },
              placeholder = { Text("EID20", color = FrostedTextTertiary) },
              singleLine = true,
              shape = RoundedCornerShape(14.dp),
              leadingIcon = {
                Icon(imageVector = Icons.Default.LocalOffer, contentDescription = null, tint = FrostedSapphire)
              },
              modifier = Modifier
                .fillMaxWidth()
                .testTag("coupon_input_code"),
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
              text = "ডিসকাউন্ট (%):",
              fontSize = 13.sp,
              fontWeight = FontWeight.Bold,
              color = FrostedSapphireDark
            )
            OutlinedTextField(
              value = discount,
              onValueChange = { discount = it },
              placeholder = { Text("যেমন: ১০% বা ২০%", color = FrostedTextTertiary) },
              singleLine = true,
              shape = RoundedCornerShape(14.dp),
              leadingIcon = {
                Icon(imageVector = Icons.Default.Percent, contentDescription = null, tint = FrostedSapphire)
              },
              modifier = Modifier
                .fillMaxWidth()
                .testTag("coupon_input_discount"),
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

        // Action buttons
        Row(
          horizontalArrangement = Arrangement.spacedBy(10.dp),
          modifier = Modifier.fillMaxWidth()
        ) {
          Button(
            onClick = {
              if (title.isBlank() || code.isBlank() || discount.isBlank()) {
                formError = "দয়া করে টাইটেল, কোড এবং ডিসকাউন্ট এর তথ্য পূরণ করুন।"
                return@Button
              }
              formError = null
              val cleanDiscount = if (!discount.contains("%") && !discount.contains("৳") && !discount.contains("ছাড়")) {
                "$discount% ছাড়"
              } else {
                discount
              }
              viewModel.saveCoupon(
                title = title,
                code = code,
                discount = cleanDiscount,
                description = description.ifBlank { "বিশেষ ডিসকাউন্ট অফার" }
              )
              title = ""
              code = ""
              discount = ""
              description = ""
            },
            colors = ButtonDefaults.buttonColors(
              containerColor = FrostedSapphire,
              contentColor = PureWhite
            ),
            shape = CircleShape,
            modifier = Modifier
              .weight(1f)
              .testTag("btn_save_coupon")
          ) {
            Icon(
              imageVector = if (editingCoupon != null) Icons.Default.Check else Icons.Default.Add,
              contentDescription = null,
              modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = if (editingCoupon != null) "আপডেট করুন" else "কুপন সেভ করুন",
              fontWeight = FontWeight.Bold
            )
          }

          if (editingCoupon != null) {
            OutlinedButton(
              onClick = {
                viewModel.cancelEditCoupon()
                formError = null
              },
              shape = CircleShape,
              border = BorderStroke(1.dp, Color(0xFFFDA4AF)),
              colors = ButtonDefaults.outlinedButtonColors(
                containerColor = FrostedRose.copy(alpha = 0.6f)
              ),
              modifier = Modifier.testTag("btn_cancel_coupon")
            ) {
              Text("বাতিল করুন", color = Color(0xFF9F1239), fontWeight = FontWeight.Bold)
            }
          }
        }
      }
    }

    // 2. Coupons List / Table
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
            text = "কুপন তালিকা (${coupons.size})",
            style = MaterialTheme.typography.titleMedium.copy(
              fontWeight = FontWeight.Bold,
              color = FrostedSapphireDark,
              letterSpacing = (-0.2).sp
            )
          )
        }

        if (coupons.isEmpty()) {
          Box(
            modifier = Modifier
              .fillMaxWidth()
              .padding(24.dp),
            contentAlignment = Alignment.Center
          ) {
            Text(
              text = "কোনো কুপন তৈরি করা হয়নি। উপরে ফর্ম পূরণ করে সেভ করুন।",
              color = FrostedTextSecondary,
              fontSize = 13.sp
            )
          }
        } else {
          coupons.forEach { item ->
            FrostedGlassCard(
              shape = RoundedCornerShape(16.dp),
              backgroundColor = if (item.isActive) Color.White.copy(alpha = 0.72f) else Color.White.copy(alpha = 0.45f),
              borderColor = if (item.isActive) FrostedSapphire.copy(alpha = 0.4f) else Color.White.copy(alpha = 0.6f),
              modifier = Modifier
                .fillMaxWidth()
                .testTag("coupon_item_${item.code}")
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
                        text = item.code,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 13.sp,
                        letterSpacing = 1.sp,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                      )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                      text = item.title,
                      fontWeight = FontWeight.Bold,
                      fontSize = 14.sp,
                      color = FrostedSapphireDark
                    )
                  }

                  // Edit & Delete
                  Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    IconButton(
                      onClick = { viewModel.prepareEditCoupon(item) },
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
                      onClick = { couponToDelete = item },
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
                  Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                      text = "স্ট্যাটাস:",
                      fontSize = 12.sp,
                      color = FrostedTextSecondary
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Switch(
                      checked = item.isActive,
                      onCheckedChange = { viewModel.toggleCouponStatus(item) },
                      colors = SwitchDefaults.colors(
                        checkedThumbColor = PureWhite,
                        checkedTrackColor = EmeraldGreenDark,
                        uncheckedTrackColor = FrostedIceDark
                      ),
                      modifier = Modifier.size(36.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                      text = if (item.isActive) "সক্রিয়" else "বন্ধ",
                      fontSize = 11.sp,
                      fontWeight = FontWeight.SemiBold,
                      color = if (item.isActive) EmeraldGreenDark else FrostedTextSecondary
                    )
                  }

                  FrostedPillBadge(
                    text = item.discount,
                    backgroundColor = if (item.isActive) EmeraldGreenLight.copy(alpha = 0.8f) else FrostedIce,
                    textColor = if (item.isActive) EmeraldGreenDark else FrostedTextSecondary,
                    borderColor = if (item.isActive) EmeraldGreen.copy(alpha = 0.4f) else Color.White
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
  if (couponToDelete != null) {
    AlertDialog(
      onDismissRequest = { couponToDelete = null },
      title = { Text("কুপন ডিলিট করবেন?", fontWeight = FontWeight.Bold) },
      text = {
        Text("কোড: ${couponToDelete!!.code} (${couponToDelete!!.title}) কুপনটি ডিলিট করতে চান?")
      },
      confirmButton = {
        Button(
          onClick = {
            viewModel.deleteCoupon(couponToDelete!!)
            couponToDelete = null
          },
          colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE11D48)),
          shape = CircleShape
        ) {
          Text("ডিলিট করুন", color = PureWhite)
        }
      },
      dismissButton = {
        TextButton(onClick = { couponToDelete = null }) {
          Text("বাতিল", color = FrostedSapphireDark)
        }
      }
    )
  }
}

