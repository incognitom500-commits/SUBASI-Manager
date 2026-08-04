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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.LockOpen
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ui.ManagementViewModel
import com.example.ui.NavDestination
import com.example.ui.components.FrostedGlassCard
import com.example.ui.components.FrostedPillBadge
import com.example.ui.theme.AmberGold
import com.example.ui.theme.AmberGoldLight
import com.example.ui.theme.EmeraldGreen
import com.example.ui.theme.EmeraldGreenDark
import com.example.ui.theme.EmeraldGreenLight
import com.example.ui.theme.FrostedIce
import com.example.ui.theme.FrostedIceDark
import com.example.ui.theme.FrostedLavender
import com.example.ui.theme.FrostedSapphire
import com.example.ui.theme.FrostedSapphireDark
import com.example.ui.theme.FrostedTextPrimary
import com.example.ui.theme.FrostedTextSecondary
import com.example.ui.theme.FrostedTextTertiary
import com.example.ui.theme.PureWhite
import com.example.ui.theme.Slate700

@Composable
fun ToolsScreen(
  viewModel: ManagementViewModel,
  onNavigate: (NavDestination) -> Unit,
  modifier: Modifier = Modifier
) {
  val isManagerUnlocked by viewModel.isManagerUnlocked.collectAsStateWithLifecycle()

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .background(Color.Transparent),
    contentPadding = PaddingValues(16.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {

    // Top Header Banner
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
                .background(FrostedIce.copy(alpha = 0.8f)),
              contentAlignment = Alignment.Center
            ) {
              Icon(
                imageVector = Icons.Default.Build,
                contentDescription = null,
                tint = FrostedSapphire,
                modifier = Modifier.size(22.dp)
              )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
              Text(
                text = "টুলস মেনু (Tools Menu)",
                style = MaterialTheme.typography.titleLarge.copy(
                  fontWeight = FontWeight.Bold,
                  color = FrostedSapphireDark,
                  fontSize = 19.sp,
                  letterSpacing = (-0.2).sp
                )
              )
              Text(
                text = "নিচের অপশনগুলো থেকে প্রয়োজনীয় প্যানেল বেছে নিন।",
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

    // 1. User Panel Choice Card
    item {
      FrostedGlassCard(
        shape = RoundedCornerShape(24.dp),
        backgroundColor = Color.White.copy(alpha = 0.72f),
        borderColor = Color.White.copy(alpha = 0.88f),
        elevation = 2.dp,
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(24.dp))
          .clickable { onNavigate(NavDestination.USER_SEARCH) }
          .testTag("tools_user_panel_btn")
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
                  .size(48.dp)
                  .clip(CircleShape)
                  .background(FrostedIce.copy(alpha = 0.8f)),
                contentAlignment = Alignment.Center
              ) {
                Icon(
                  imageVector = Icons.Default.Person,
                  contentDescription = null,
                  tint = FrostedSapphire,
                  modifier = Modifier.size(26.dp)
                )
              }
              Spacer(modifier = Modifier.width(14.dp))
              Column {
                Text(
                  text = "ইউজার (User)",
                  style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = FrostedSapphireDark,
                    fontSize = 18.sp
                  )
                )
                Text(
                  text = "প্রোডাক্ট খুঁজুন ও বিবরণ দেখুন",
                  fontSize = 12.sp,
                  color = FrostedTextSecondary
                )
              }
            }

            Button(
              onClick = { onNavigate(NavDestination.USER_SEARCH) },
              colors = ButtonDefaults.buttonColors(
                containerColor = FrostedSapphire,
                contentColor = PureWhite
              ),
              shape = CircleShape
            ) {
              Text("প্রবেশ করুন", fontWeight = FontWeight.Bold, fontSize = 12.sp)
            }
          }

          Surface(
            shape = RoundedCornerShape(14.dp),
            color = FrostedIce.copy(alpha = 0.35f),
            border = BorderStroke(1.dp, Color.White.copy(alpha = 0.6f)),
            modifier = Modifier.fillMaxWidth()
          ) {
            Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
              FeatureRow(text = "প্রোডাক্ট কোড দিয়ে তাৎক্ষণিক অনুসন্ধান")
              FeatureRow(text = "পণ্যের নাম, বিক্রয় মূল্য, সাইজ ও কালার দেখা")
              FeatureRow(text = "সহজ ও দ্রুত কাস্টমার অনুসন্ধান প্যানেল")
            }
          }
        }
      }
    }

    // 2. Manager Panel Choice Card
    item {
      FrostedGlassCard(
        shape = RoundedCornerShape(24.dp),
        backgroundColor = Color.White.copy(alpha = 0.72f),
        borderColor = Color.White.copy(alpha = 0.88f),
        elevation = 2.dp,
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(24.dp))
          .clickable { onNavigate(NavDestination.MANAGER) }
          .testTag("tools_manager_panel_btn")
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
                  .size(48.dp)
                  .clip(CircleShape)
                  .background(FrostedLavender.copy(alpha = 0.8f)),
                contentAlignment = Alignment.Center
              ) {
                Icon(
                  imageVector = Icons.Default.AdminPanelSettings,
                  contentDescription = null,
                  tint = Color(0xFF6B21A8),
                  modifier = Modifier.size(26.dp)
                )
              }
              Spacer(modifier = Modifier.width(14.dp))
              Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                  Text(
                    text = "ম্যানেজার (Manager)",
                    style = MaterialTheme.typography.titleLarge.copy(
                      fontWeight = FontWeight.Bold,
                      color = FrostedSapphireDark,
                      fontSize = 18.sp
                    )
                  )
                  Spacer(modifier = Modifier.width(6.dp))
                  Icon(
                    imageVector = if (isManagerUnlocked) Icons.Default.LockOpen else Icons.Default.Lock,
                    contentDescription = null,
                    tint = if (isManagerUnlocked) EmeraldGreenDark else AmberGold,
                    modifier = Modifier.size(16.dp)
                  )
                }
                Text(
                  text = if (isManagerUnlocked) "আনলক করা আছে" else "পিন কোড দ্বারা সুরক্ষিত",
                  fontSize = 12.sp,
                  color = if (isManagerUnlocked) EmeraldGreenDark else FrostedTextSecondary
                )
              }
            }

            Button(
              onClick = { onNavigate(NavDestination.MANAGER) },
              colors = ButtonDefaults.buttonColors(
                containerColor = if (isManagerUnlocked) FrostedSapphire else AmberGold,
                contentColor = PureWhite
              ),
              shape = CircleShape
            ) {
              Text(
                text = if (isManagerUnlocked) "ড্যাশবোর্ড" else "লগইন",
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp
              )
            }
          }

          Surface(
            shape = RoundedCornerShape(14.dp),
            color = FrostedLavender.copy(alpha = 0.30f),
            border = BorderStroke(1.dp, Color.White.copy(alpha = 0.6f)),
            modifier = Modifier.fillMaxWidth()
          ) {
            Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
              FeatureRow(text = "১. হিসাব নিকাশ (কেনার দাম, বিক্রির দাম, লাভ ও রিপোর্ট)")
              FeatureRow(text = "২. প্রোডাক্ট এর বিবরণ (নতুন পণ্য যোগ ও এডিট)")
              FeatureRow(text = "৩. কুপন কোড তৈরি ও ডিসকাউন্ট পরিচালনা")
            }
          }
        }
      }
    }

  }
}

@Composable
private fun FeatureRow(text: String) {
  Row(verticalAlignment = Alignment.CenterVertically) {
    Icon(
      imageVector = Icons.Default.CheckCircle,
      contentDescription = null,
      tint = EmeraldGreenDark,
      modifier = Modifier.size(14.dp)
    )
    Spacer(modifier = Modifier.width(8.dp))
    Text(
      text = text,
      fontSize = 12.sp,
      color = FrostedTextSecondary
    )
  }
}

