package com.parentapp.mobile.ui.main

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Phone
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

@Composable
fun PopupMenu() {
    val context = LocalContext.current
    var isEmailFormVisible by remember { mutableStateOf(false) }
    val number = "1234567890" // replace with your phone number
    val callIntent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$number"))

    Column(
        modifier = Modifier.padding(bottom = 60.dp, end = 30.dp).fillMaxSize(),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.End
    ) {
        IconButton(
            onClick = { context.startActivity(callIntent) },
            modifier = Modifier.padding(4.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Phone,
                contentDescription = "Call",
                tint = Color.White

            )
        }
        Box(modifier = Modifier.padding(4.dp)) {
            IconButton(onClick = { isEmailFormVisible = true }) {
                Icon(
                    imageVector = Icons.Default.Email,
                    contentDescription = "Email",
                    tint = Color.White
                )
            }
            if (isEmailFormVisible) {
                Surface(
                    modifier = Modifier
                        .width(300.dp)
                        .padding(16.dp),
                    shape = RoundedCornerShape(8.dp),
                    elevation = 4.dp
                ) {
                    ContactUsScreen(
                        onSubmit = { to, subject, message ->
                            // handle submission of email data
                        },
                        onClose = {
                            // handle closing of the form
                        }
                    )
                }
            }
        }
    }
}
