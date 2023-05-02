package com.parentapp.mobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.insets.navigationBarsWithImePadding
import com.parentapp.mobile.ui.theme.MobileTheme
import com.parentapp.mobile.ui.theme.Shapes

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MobileTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    Login()
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun Login() {
    val passwordFocusRequester = FocusRequester()
    val focusManager: FocusManager = LocalFocusManager.current

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.login_bg),
            contentDescription = "Login",
            modifier = Modifier
                .fillMaxSize()
                .blur(5.dp),
            contentScale = ContentScale.Crop
        )
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(35.dp)
            .alpha(0.5f)
            .clip(
                CutCornerShape(topStart = 8.dp, topEnd = 16.dp, bottomStart = 16.dp, bottomEnd = 8.dp)
            )
            .background(MaterialTheme.colorScheme.background)
    )
    Column(
        Modifier
            .navigationBarsWithImePadding()
            .padding(50.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp, alignment = Alignment.Bottom),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LoginHeader()

        LoginForm(focusManager, passwordFocusRequester)

        LoginFooter()
    }
}

@Composable
fun LoginHeader() {
    Text(
        text = "Welcome to",
        color = Color.Blue,
        fontSize = 36.sp,
        fontWeight = FontWeight.ExtraBold,
        fontFamily = FontFamily(Font(R.font.elegant)
        )
    )
    Icon(
        painter = painterResource(id = R.drawable.logo),
        null,
        Modifier.size(200.dp),
        tint = Color.Blue
    )
    Text(
        text = "Log in to continue:",
        fontSize = 18.sp
    )
}

@Composable
fun LoginForm(focusManager: FocusManager, passwordFocusRequester: FocusRequester) {
    LoginField(
        InputType.Name, keyboardActions = KeyboardActions(onNext = { passwordFocusRequester.requestFocus() })
    )
    LoginField(
        InputType.Password,
        keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
        focusRequest = passwordFocusRequester
    )
}

@Composable
fun LoginField(
    inputType: InputType, focusRequest: FocusRequester? = null, keyboardActions: KeyboardActions
) {
    var value: String by remember { mutableStateOf("") }
    TextField(
        value = value,
        onValueChange = { value = it },
        modifier = Modifier.fillMaxWidth().focusRequester(FocusRequester()),
        leadingIcon = { Icon(imageVector = inputType.icon, null) },
        label = { Text(text = inputType.label) },
        shape = Shapes.small,
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
        singleLine = true,
        keyboardOptions = inputType.keyboardOptions,
        visualTransformation = inputType.visualTransformation,
        keyboardActions = keyboardActions
    )
}

@Composable
fun LoginFooter() {
    Button(
        onClick = {/* Handle Google sign-in */ }, modifier = Modifier.fillMaxWidth()
    ) {
        Text("Log in", Modifier.padding(vertical = 8.dp))
    }
    TextButton(onClick = {/* Forgot password */ }, Modifier.padding(0.dp)){
        Text(text = "Forgot password?")
    }
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text("You can also log in with:", color = Color.Black)
    }
    Row(verticalAlignment = Alignment.CenterVertically) {
        IconButton(onClick = { /* Handle Google sign-in */ }, modifier = Modifier.size(62.dp), content = {
            Image(
                painter = painterResource(R.drawable.ic_google), contentDescription = "Google logo"
            )
        })
        Spacer(modifier = Modifier.width(30.dp))
        Text("or")
        Spacer(modifier = Modifier.width(30.dp))
        IconButton(onClick = { /* Handle Facebook sign-in */ }, modifier = Modifier.size(50.dp), content = {
            Image(
                painter = painterResource(R.drawable.ic_facebook), contentDescription = "Facebook logo"
            )
        })
    }
    Divider(
        color = Color.White.copy(alpha = 0.3f),
        thickness = 1.dp,
    )
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text("Don't have an account?", color = Color.Black)
        TextButton(onClick = {}) {
            Text("Sign up")
        }
    }
}

sealed class InputType(
    val label: String,
    val icon: ImageVector,
    val keyboardOptions: KeyboardOptions,
    val visualTransformation: VisualTransformation
) {
    object Name : InputType(
        label = "Username",
        icon = Icons.Default.Person,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        visualTransformation = VisualTransformation.None
    )

    object Password : InputType(
        label = "Password", icon = Icons.Default.Lock, keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done, keyboardType = KeyboardType.Password
        ), visualTransformation = PasswordVisualTransformation()
    )
}