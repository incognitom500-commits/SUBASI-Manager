package com.example.ui.screens

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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CardGiftcard
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.LocalOffer
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.local.entities.CouponEntity
import com.example.ui.ManagementViewModel
import com.example.ui.components.FrostedGlassCard
import com.example.ui.components.FrostedPillBadge
import com.example.ui.theme.AmberGold
import com.example.ui.theme.EmeraldGreen
import com.example.ui.theme.EmeraldGreenDark
import com.example.ui.theme.EmeraldGreenLight
import com.example.ui.theme.FrostedIce
import com.example.ui.theme.FrostedLavender
import com.example.ui.theme.FrostedRose
import com.example.ui.theme.FrostedSapphire
import com.example.ui.theme.FrostedSapphireDark
import com.example.ui.theme.FrostedTextPrimary
import com.example.ui.theme.FrostedTextSecondary
import com.example.ui.theme.FrostedTextTertiary
import com.example.ui.theme.PureWhite

@Composable
fun CouponScreen(
  viewModel: ManagementViewModel,
  modifier: Modifier = Modifier
) {
  val coupons by viewModel.couponsList.collectAsStateWithLifecycle()
  val activeCoupons = coupons.filter { it.isActive }
  val clipboardManager = LocalClipboardManager.current
  var copiedCode by remember { mutableStateOf<String?>(null) }

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .background(Color.Transparent),
    contentPadding = PaddingValues(16.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {

    // Header Intro
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
          verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
              modifier = Modifier
                .size(42.dp)
                .clip(CircleShape)
                .background(FrostedLavender.copy(alpha = 0.8f)),
              contentAlignment = Alignment.Center
            ) {
              Icon(
                imageVector = Icons.Default.LocalOffer,
                contentDescription = null,
                tint = Color(0xFF6B21A8),
                modifier = Modifier.size(22.dp)
              )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
              Text(
                text = "অ্যাক্টিভ কুপন কোডসমূহ",
                style = MaterialTheme.typography.titleLarge.copy(
                  fontWeight = FontWeight.Bold,
                  color = FrostedSapphireDark,
                  fontSize = 19.sp,
                  letterSpacing = (-0.2).sp
                )
              )
              Text(
                text = "নিচের কুপন কোডগুলো ব্যবহার করে বিশেষ ডিসকাউন্ট গ্রহণ করুন।",
                style = MaterialTheme.typography.bodySmall.copy(
                  color = FrostedTextSecondary,
                  fontSize = 12.sp
                )
              )
            }
          }
        }
      }
    }

    if (activeCoupons.isEmpty()) {
      item {
        FrostedGlassCard(
          shape = RoundedCornerShape(20.dp),
          backgroundColor = Color.White.copy(alpha = 0.7f),
          borderColor = Color.White.copy(alpha = 0.85f),
          modifier = Modifier.fillMaxWidth()
        ) {
          Column(
            modifier = Modifier
              .fillMaxWidth()
              .padding(40.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
          ) {
            Icon(
              imageVector = Icons.Default.CardGiftcard,
              contentDescription = null,
              tint = FrostedTextTertiary,
              modifier = Modifier.size(48.dp)
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
              text = "বর্তমানে কোনো কুপন অফার সক্রিয় নেই।",
              fontWeight = FontWeight.SemiBold,
              color = FrostedSapphireDark,
              fontSize = 15.sp,
              textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
              text = "ম্যানেজার প্যানেল থেকে নতুন কুপন কোড যুক্ত করতে পারেন।",
              color = FrostedTextSecondary,
              fontSize = 12.sp,
              textAlign = TextAlign.Center
            )
          }
        }
      }
    } else {
      items(activeCoupons, key = { it.id }) { coupon ->
        CouponCardItem(
          coupon = coupon,
          isCopied = copiedCode == coupon.code,
          onCopyCode = {
            clipboardManager.setText(AnnotatedString(coupon.code))
            copiedCode = coupon.code
            viewModel.showMessage("কুপন কোড '${coupon.code}' কপি করা হয়েছে!")
          }
        )
      }
    }
  }
}

@Composable
fun CouponCardItem(
  coupon: CouponEntity,
  isCopied: Boolean,
  onCopyCode: () -> Unit
) {
  FrostedGlassCard(
    shape = RoundedCornerShape(24.dp),
    backgroundColor = Color.White.copy(alpha = 0.74f),
    borderColor = Color.White.copy(alpha = 0.90f),
    elevation = 2.dp,
    modifier = Modifier
      .fillMaxWidth()
      .testTag("coupon_card_${coupon.code}")
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(18.dp)
    ) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
      ) {
        Column(modifier = Modifier.weight(1f)) {
          Text(
            text = coupon.title,
            style = MaterialTheme.typography.titleMedium.copy(
              fontWeight = FontWeight.Bold,
              color = FrostedSapphireDark,
              fontSize = 17.sp
            )
          )
          if (coupon.description.isNotBlank()) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
              text = coupon.description,
              style = MaterialTheme.typography.bodySmall.copy(
                color = FrostedTextSecondary,
                fontSize = 12.sp
              )
            )
          }
        }

        FrostedPillBadge(
          text = coupon.discount,
          backgroundColor = FrostedRose.copy(alpha = 0.8f),
          textColor = Color(0xFF9F1239),
          borderColor = Color(0xFFFDA4AF)
        )
      }

      Spacer(modifier = Modifier.height(14.dp))

      // Frosted code box with copy action
      Surface(
        shape = RoundedCornerShape(14.dp),
        color = FrostedIce.copy(alpha = 0.45f),
        border = BorderStroke(1.dp, Color.White.copy(alpha = 0.85f)),
        modifier = Modifier.fillMaxWidth()
      ) {
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 14.dp, vertical = 10.dp),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Column {
            Text(
              text = "প্রোমো কোড:",
              fontSize = 10.sp,
              color = FrostedTextSecondary,
              fontWeight = FontWeight.SemiBold
            )
            Text(
              text = coupon.code,
              fontSize = 20.sp,
              fontWeight = FontWeight.ExtraBold,
              letterSpacing = 2.sp,
              color = FrostedSapphireDark
            )
          }

          Button(
            onClick = onCopyCode,
            colors = ButtonDefaults.buttonColors(
              containerColor = if (isCopied) EmeraldGreenDark else FrostedSapphire,
              contentColor = PureWhite
            ),
            shape = CircleShape,
            contentPadding = PaddingValues(horizontal = 14.dp, vertical = 6.dp),
            modifier = Modifier.testTag("copy_coupon_${coupon.code}")
          ) {
            Icon(
              imageVector = if (isCopied) Icons.Default.Check else Icons.Default.ContentCopy,
              contentDescription = "কপি করুন",
              modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = if (isCopied) "কপি হয়েছে!" else "কপি করুন",
              fontSize = 12.sp,
              fontWeight = FontWeight.Bold
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(10.dp))

      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Icon(
            imageVector = Icons.Default.Schedule,
            contentDescription = null,
            tint = FrostedTextTertiary,
            modifier = Modifier.size(14.dp)
          )
          Spacer(modifier = Modifier.width(4.dp))
          Text(
            text = "মেয়াদ: ${coupon.expiryDate}",
            fontSize = 11.sp,
            color = FrostedTextTertiary
          )
        }

        FrostedPillBadge(
          text = "বিশেষ ছাড়!",
          backgroundColor = EmeraldGreenLight.copy(alpha = 0.6f),
          textColor = EmeraldGreenDark,
          borderColor = EmeraldGreen.copy(alpha = 0.3f)
        )
      }
    }
  }
}

