package com.example.ui.screens

import android.content.Intent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.LockReset
import androidx.compose.material.icons.filled.Paid
import androidx.compose.material.icons.filled.RestartAlt
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
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
fun ManagerReportsTab(
  viewModel: ManagementViewModel,
  modifier: Modifier = Modifier
) {
  val salesSummary by viewModel.salesSummary.collectAsStateWithLifecycle()
  val sales by viewModel.salesList.collectAsStateWithLifecycle()
  val products by viewModel.productsList.collectAsStateWithLifecycle()
  val coupons by viewModel.couponsList.collectAsStateWithLifecycle()
  val context = LocalContext.current

  var showChangePinDialog by remember { mutableStateOf(false) }
  var showResetConfirmDialog by remember { mutableStateOf(false) }

  var oldPin by remember { mutableStateOf("") }
  var newPin by remember { mutableStateOf("") }
  var pinChangeError by remember { mutableStateOf<String?>(null) }

  Column(
    modifier = modifier.fillMaxWidth(),
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {

    // 1. Comprehensive Financial Analytics Card
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
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.SpaceBetween,
          modifier = Modifier.fillMaxWidth()
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
              modifier = Modifier
                .size(44.dp)
                .clip(CircleShape)
                .background(EmeraldGreenLight.copy(alpha = 0.8f)),
              contentAlignment = Alignment.Center
            ) {
              Icon(
                imageVector = Icons.Default.Assessment,
                contentDescription = null,
                tint = EmeraldGreenDark,
                modifier = Modifier.size(24.dp)
              )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
              Text(
                text = "ব্যবসায়িক সার্বিক রিপোর্ট",
                style = MaterialTheme.typography.titleMedium.copy(
                  fontWeight = FontWeight.Bold,
                  color = FrostedSapphireDark,
                  fontSize = 17.sp,
                  letterSpacing = (-0.2).sp
                )
              )
              Text(
                text = "মোট আয়-ব্যয় এবং লাভ পর্যালোচনা",
                fontSize = 12.sp,
                color = FrostedTextSecondary
              )
            }
          }

          FrostedPillBadge(
            text = "${sales.size} টি বিক্রি",
            backgroundColor = FrostedIce.copy(alpha = 0.8f),
            textColor = FrostedSapphireDark,
            borderColor = Color.White
          )
        }

        HorizontalDivider(color = FrostedIceDark.copy(alpha = 0.5f))

        // Stat Grid Rows
        ReportStatItem(
          label = "সর্বমোট কেনা খরচ (Total Buy):",
          value = formatTakaClean(salesSummary.totalBuy),
          color = FrostedSapphireDark
        )

        ReportStatItem(
          label = "সর্বমোট বিক্রয় আয় (Total Sell):",
          value = formatTakaClean(salesSummary.totalSell),
          color = FrostedSapphireDark
        )

        ReportStatItem(
          label = "মোট খাঁটি লাভ (Net Profit):",
          value = formatTakaClean(salesSummary.totalProfit),
          color = EmeraldGreenDark,
          isBold = true
        )

        ReportStatItem(
          label = "গড় লাভ মার্জিন (Profit Margin):",
          value = "${String.format(java.util.Locale.US, "%.1f", salesSummary.profitMargin)}%",
          color = AmberGold,
          isBold = true
        )

        ReportStatItem(
          label = "মোট ক্যাটালগ প্রোডাক্ট:",
          value = "${products.size} টি আইটেম",
          color = FrostedSapphireDark
        )

        ReportStatItem(
          label = "সক্রিয় কুপন অফার:",
          value = "${coupons.count { it.isActive }} টি অফার",
          color = FrostedSapphireDark
        )

        Spacer(modifier = Modifier.height(6.dp))

        // Share Report Button
        Button(
          onClick = {
            val reportText = """
              📊 *ম্যানেজমেন্ট সিস্টেম - ব্যবসায়িক রিপোর্ট*
              -------------------------------------
              • সর্বমোট কেনা: ${formatTakaClean(salesSummary.totalBuy)}
              • সর্বমোট বিক্রি: ${formatTakaClean(salesSummary.totalSell)}
              • মোট লাভ: ${formatTakaClean(salesSummary.totalProfit)}
              • লাভ মার্জিন: ${String.format(java.util.Locale.US, "%.1f", salesSummary.profitMargin)}%
              • মোট বিক্রয় লেনদেন: ${sales.size} টি
              • মোট ক্যাটালগ পণ্য: ${products.size} টি
              -------------------------------------
              রিপোর্ট প্রস্তুতকারক: ম্যানেজমেন্ট অ্যাপ
            """.trimIndent()

            val shareIntent = Intent(Intent.ACTION_SEND).apply {
              type = "text/plain"
              putExtra(Intent.EXTRA_SUBJECT, "ব্যবসায়িক রিপোর্ট")
              putExtra(Intent.EXTRA_TEXT, reportText)
            }
            context.startActivity(Intent.createChooser(shareIntent, "রিপোর্ট শেয়ার করুন"))
          },
          colors = ButtonDefaults.buttonColors(
            containerColor = FrostedSapphire,
            contentColor = PureWhite
          ),
          shape = CircleShape,
          modifier = Modifier
            .fillMaxWidth()
            .testTag("btn_share_report")
        ) {
          Icon(
            imageVector = Icons.Default.Share,
            contentDescription = null,
            modifier = Modifier.size(18.dp)
          )
          Spacer(modifier = Modifier.width(8.dp))
          Text("রিপোর্ট শেয়ার করুন (WhatsApp / SMS)", fontWeight = FontWeight.Bold)
        }
      }
    }

    // 2. Settings & PIN Management Card
    FrostedGlassCard(
      shape = RoundedCornerShape(20.dp),
      backgroundColor = Color.White.copy(alpha = 0.74f),
      borderColor = Color.White.copy(alpha = 0.88f),
      elevation = 1.dp,
      modifier = Modifier.fillMaxWidth()
    ) {
      Column(
        modifier = Modifier.padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Box(
            modifier = Modifier
              .size(44.dp)
              .clip(CircleShape)
              .background(AmberGold.copy(alpha = 0.15f)),
            contentAlignment = Alignment.Center
          ) {
            Icon(
              imageVector = Icons.Default.Security,
              contentDescription = null,
              tint = AmberGold,
              modifier = Modifier.size(22.dp)
            )
          }
          Spacer(modifier = Modifier.width(12.dp))
          Column {
            Text(
              text = "সিকিউরিটি ও ডাটা সেটিংস",
              style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                color = FrostedSapphireDark,
                fontSize = 17.sp,
                letterSpacing = (-0.2).sp
              )
            )
            Text(
              text = "ম্যানেজার পিন ও ব্যাকআপ নিয়ন্ত্রণ",
              fontSize = 12.sp,
              color = FrostedTextSecondary
            )
          }
        }

        HorizontalDivider(color = FrostedIceDark.copy(alpha = 0.5f))

        // Change PIN Button
        OutlinedButton(
          onClick = {
            oldPin = ""
            newPin = ""
            pinChangeError = null
            showChangePinDialog = true
          },
          shape = CircleShape,
          border = BorderStroke(1.dp, FrostedSapphire.copy(alpha = 0.3f)),
          colors = ButtonDefaults.outlinedButtonColors(
            containerColor = Color.White.copy(alpha = 0.6f)
          ),
          modifier = Modifier
            .fillMaxWidth()
            .testTag("btn_change_pin")
        ) {
          Icon(
            imageVector = Icons.Default.Key,
            contentDescription = null,
            tint = FrostedSapphire,
            modifier = Modifier.size(18.dp)
          )
          Spacer(modifier = Modifier.width(8.dp))
          Text("ম্যানেজার পিন কোড পরিবর্তন করুন", color = FrostedSapphireDark, fontWeight = FontWeight.SemiBold)
        }

        // Reset Demo Data Button
        OutlinedButton(
          onClick = { showResetConfirmDialog = true },
          shape = CircleShape,
          colors = ButtonDefaults.outlinedButtonColors(
            containerColor = AmberGold.copy(alpha = 0.1f),
            contentColor = AmberGold
          ),
          border = BorderStroke(1.dp, AmberGold.copy(alpha = 0.5f)),
          modifier = Modifier
            .fillMaxWidth()
            .testTag("btn_reset_demo")
        ) {
          Icon(
            imageVector = Icons.Default.RestartAlt,
            contentDescription = null,
            tint = AmberGold,
            modifier = Modifier.size(18.dp)
          )
          Spacer(modifier = Modifier.width(8.dp))
          Text("ডিফল্ট ডেমো ডাটা রিস্টোর করুন", color = AmberGold, fontWeight = FontWeight.SemiBold)
        }
      }
    }

  }

  // Change PIN Dialog
  if (showChangePinDialog) {
    AlertDialog(
      onDismissRequest = { showChangePinDialog = false },
      title = { Text("পিন পরিবর্তন করুন", fontWeight = FontWeight.Bold, color = FrostedSapphireDark) },
      text = {
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
          Text("আপনার বর্তমান পিন ও নতুন পিন কোড দিন:", fontSize = 13.sp, color = FrostedTextSecondary)

          OutlinedTextField(
            value = oldPin,
            onValueChange = { oldPin = it },
            label = { Text("বর্তমান পিন") },
            placeholder = { Text("ডিফল্ট: 147893082") },
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth()
          )

          OutlinedTextField(
            value = newPin,
            onValueChange = { newPin = it },
            label = { Text("নতুন পিন") },
            placeholder = { Text("যেমন: 1234") },
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth()
          )

          if (pinChangeError != null) {
            Text(pinChangeError!!, color = Color(0xFFE11D48), fontSize = 12.sp)
          }
        }
      },
      confirmButton = {
        Button(
          onClick = {
            if (oldPin.isBlank() || newPin.isBlank()) {
              pinChangeError = "সব তথ্য পূরণ করুন।"
              return@Button
            }
            val success = viewModel.changeManagerPin(oldPin, newPin)
            if (success) {
              showChangePinDialog = false
            } else {
              pinChangeError = "বর্তমান পিন ভুল অথবা নতুন পিন অকার্যকর।"
            }
          },
          colors = ButtonDefaults.buttonColors(containerColor = FrostedSapphire),
          shape = CircleShape
        ) {
          Text("সেভ করুন", color = PureWhite)
        }
      },
      dismissButton = {
        TextButton(onClick = { showChangePinDialog = false }) {
          Text("বাতিল", color = FrostedSapphireDark)
        }
      }
    )
  }

  // Reset Confirmation Dialog
  if (showResetConfirmDialog) {
    AlertDialog(
      onDismissRequest = { showResetConfirmDialog = false },
      title = { Text("ডেমো ডাটা রিস্টোর করবেন?", fontWeight = FontWeight.Bold, color = FrostedSapphireDark) },
      text = {
        Text(
          "এটি ডিফল্ট প্রোডাক্ট (P01, P02...), ডেমো বিক্রির হিসাব এবং কুপন কোডগুলো পুনরায় লোড করবে।",
          color = FrostedTextSecondary
        )
      },
      confirmButton = {
        Button(
          onClick = {
            viewModel.resetAllDemoData()
            showResetConfirmDialog = false
          },
          colors = ButtonDefaults.buttonColors(containerColor = AmberGold),
          shape = CircleShape
        ) {
          Text("রিস্টোর করুন", color = PureWhite)
        }
      },
      dismissButton = {
        TextButton(onClick = { showResetConfirmDialog = false }) {
          Text("বাতিল", color = FrostedSapphireDark)
        }
      }
    )
  }
}

@Composable
private fun ReportStatItem(
  label: String,
  value: String,
  color: Color,
  isBold: Boolean = false
) {
  Row(
    modifier = Modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.CenterVertically
  ) {
    Text(
      text = label,
      fontSize = 13.sp,
      color = FrostedTextSecondary,
      fontWeight = if (isBold) FontWeight.Bold else FontWeight.Normal
    )
    Text(
      text = value,
      fontSize = if (isBold) 15.sp else 13.sp,
      color = color,
      fontWeight = if (isBold) FontWeight.ExtraBold else FontWeight.Bold
    )
  }
}

