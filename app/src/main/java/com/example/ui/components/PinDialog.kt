package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.AmberGold
import com.example.ui.theme.CrimsonRed
import com.example.ui.theme.EmeraldGreen
import com.example.ui.theme.FrostedIce
import com.example.ui.theme.FrostedIceDark
import com.example.ui.theme.FrostedSapphire
import com.example.ui.theme.FrostedSapphireDark
import com.example.ui.theme.FrostedTextPrimary
import com.example.ui.theme.FrostedTextSecondary
import com.example.ui.theme.FrostedTextTertiary
import com.example.ui.theme.GlassCardSurfaceHigh
import com.example.ui.theme.PureWhite

@Composable
fun PinDialog(
  isOpen: Boolean,
  errorMessage: String?,
  onDismiss: () -> Unit,
  onSubmitPin: (String) -> Unit
) {
  if (!isOpen) return

  var enteredPin by remember { mutableStateOf("") }
  var isPasswordVisible by remember { mutableStateOf(false) }
  var showHint by remember { mutableStateOf(false) }

  AlertDialog(
    onDismissRequest = onDismiss,
    containerColor = GlassCardSurfaceHigh,
    shape = RoundedCornerShape(24.dp),
    icon = {
      Box(
        modifier = Modifier
          .size(52.dp)
          .clip(CircleShape)
          .background(FrostedIce.copy(alpha = 0.8f)),
        contentAlignment = Alignment.Center
      ) {
        Icon(
          imageVector = Icons.Default.Lock,
          contentDescription = null,
          tint = FrostedSapphire,
          modifier = Modifier.size(26.dp)
        )
      }
    },
    title = {
      Text(
        text = "ম্যানেজার পিন দিন",
        style = MaterialTheme.typography.titleLarge.copy(
          fontWeight = FontWeight.Bold,
          color = FrostedSapphireDark,
          letterSpacing = (-0.2).sp
        )
      )
    },
    text = {
      Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        Text(
          text = "ম্যানেজার প্যানেলে প্রবেশ করতে আপনার সিকিউরিটি পিন কোড প্রদান করুন:",
          style = MaterialTheme.typography.bodyMedium,
          color = FrostedTextSecondary
        )

        OutlinedTextField(
          value = enteredPin,
          onValueChange = { enteredPin = it },
          label = { Text("পিন কোড (PIN)") },
          placeholder = { Text("যেমন: 147893082 বা 1234", color = FrostedTextTertiary) },
          singleLine = true,
          shape = RoundedCornerShape(14.dp),
          keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.NumberPassword,
            imeAction = ImeAction.Done
          ),
          keyboardActions = KeyboardActions(
            onDone = {
              if (enteredPin.isNotBlank()) onSubmitPin(enteredPin)
            }
          ),
          visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
          trailingIcon = {
            IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
              Icon(
                imageVector = if (isPasswordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                contentDescription = if (isPasswordVisible) "পিন লুকান" else "পিন দেখুন",
                tint = FrostedTextSecondary
              )
            }
          },
          isError = errorMessage != null,
          modifier = Modifier
            .fillMaxWidth()
            .testTag("pin_input_field"),
          colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = FrostedSapphire,
            unfocusedBorderColor = Color.White.copy(alpha = 0.85f),
            focusedContainerColor = Color.White.copy(alpha = 0.85f),
            unfocusedContainerColor = Color.White.copy(alpha = 0.6f)
          )
        )

        if (errorMessage != null) {
          Text(
            text = errorMessage,
            color = Color(0xFFE11D48),
            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium)
          )
        }

        // Quick helper / hint button
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          TextButton(
            onClick = { showHint = !showHint },
            modifier = Modifier.testTag("pin_hint_button")
          ) {
            Icon(
              imageVector = Icons.Default.Info,
              contentDescription = null,
              modifier = Modifier.size(16.dp),
              tint = AmberGold
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
              text = if (showHint) "সহায়তা বন্ধ করুন" else "পিন মনে নেই?",
              fontSize = 12.sp,
              color = AmberGold
            )
          }

          TextButton(
            onClick = {
              enteredPin = "147893082"
              onSubmitPin("147893082")
            }
          ) {
            Text(
              text = "ডিফল্ট পিন অটো-পূরণ",
              fontSize = 12.sp,
              fontWeight = FontWeight.SemiBold,
              color = EmeraldGreen
            )
          }
        }

        AnimatedVisibility(visible = showHint) {
          Surface(
            shape = RoundedCornerShape(12.dp),
            color = FrostedIce.copy(alpha = 0.5f),
            border = BorderStroke(1.dp, Color.White.copy(alpha = 0.8f)),
            modifier = Modifier.fillMaxWidth()
          ) {
            Column(modifier = Modifier.padding(12.dp)) {
              Text(
                text = "ডিফল্ট ম্যানেজার পিন:",
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
                color = FrostedSapphireDark
              )
              Text(
                text = "147893082 অথবা 1234",
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp,
                color = FrostedSapphire
              )
            }
          }
        }
      }
    },
    confirmButton = {
      Button(
        onClick = { onSubmitPin(enteredPin) },
        enabled = enteredPin.isNotBlank(),
        colors = ButtonDefaults.buttonColors(
          containerColor = FrostedSapphire,
          contentColor = PureWhite
        ),
        shape = CircleShape,
        modifier = Modifier.testTag("pin_submit_button")
      ) {
        Text("প্রবেশ করুন", fontWeight = FontWeight.Bold)
      }
    },
    dismissButton = {
      TextButton(onClick = onDismiss) {
        Text("বাতিল", color = FrostedSapphireDark)
      }
    }
  )
}

