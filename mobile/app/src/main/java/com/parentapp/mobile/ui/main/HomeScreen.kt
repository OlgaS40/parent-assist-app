package com.parentapp.mobile.ui.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.parentapp.mobile.R
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.home_bg),
            contentDescription = "Home",
            modifier = Modifier
                .fillMaxSize()
                .blur(5.dp),
            contentScale = ContentScale.Crop
        )
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(40.dp)
            .alpha(0.5f)
            .clip(
                CutCornerShape(topStart = 8.dp, topEnd = 16.dp, bottomStart = 16.dp, bottomEnd = 8.dp)
            )
            .background(androidx.compose.material3.MaterialTheme.colorScheme.background)
    )
    Column(
        modifier = Modifier.padding(50.dp).fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Welcome to",
            color = Color.Blue,
            fontSize = 36.sp,
            fontWeight = FontWeight.ExtraBold,
            fontFamily = FontFamily(
                Font(R.font.elegant)
            )
        )
        Spacer(modifier = Modifier.height(16.dp))
        Icon(
            painter = painterResource(R.drawable.logo),
            null,
            Modifier.size(200.dp),
            tint = Color.Blue
        )
        Button(onClick = { /* TODO */ }, modifier = Modifier.fillMaxWidth()) {
            Text("About us")
        }
        Button(onClick = { /* TODO */ }, modifier = Modifier.fillMaxWidth()) {
            Text("Login")
        }
        Button(onClick = { /* TODO */ }, modifier = Modifier.fillMaxWidth()) {
            Text("SignUp")
        }
    }
}