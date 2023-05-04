package com.parentapp.mobile.ui.main

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.parentapp.mobile.models.EmailFormData

@Composable
fun ContactUsScreen(onSubmit: (String, String, String) -> Unit, onClose: () -> Unit) {
    val context = LocalContext.current
    var formData by remember { mutableStateOf(EmailFormData()) }
    var isEmailSent by remember { mutableStateOf(false) }
    val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
        data = Uri.parse("mailto:support@gmail.com") // replace with your email address
        putExtra(Intent.EXTRA_SUBJECT, "Bug/Question Report")
    }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Contact Us",
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        TextField(
            value = formData.name,
            onValueChange = { formData = formData.copy(name = it) },
            label = { Text("Name") },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )
        TextField(
            value = formData.subject,
            onValueChange = { formData = formData.copy(subject = it) },
            label = { Text("Subject") },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )
        TextField(
            value = formData.message,
            onValueChange = { formData = formData.copy(message = it) },
            label = { Text("Message") },
            maxLines = 8,
            modifier = Modifier
                .height(250.dp)
                .fillMaxSize()
                .padding(bottom = 16.dp),
        )
        Button(
            onClick = {
                context.startActivity(emailIntent)
                onSubmit(formData.subject, formData.subject, formData.message)
                onClose()
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(text = "Send")
        }
    }
}






