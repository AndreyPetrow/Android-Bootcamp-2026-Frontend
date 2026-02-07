package ru.sicampus.bootcamp2026.ui.screens.signup

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import ru.sicampus.bootcamp2026.R
import ru.sicampus.bootcamp2026.ui.screens.navlogin.LoginItemsNav


@Composable
fun SignUpScreen(modifier: Modifier = Modifier,
                 navHostController: NavHostController) {
            var emailText by remember { mutableStateOf("") }
            var passText by remember { mutableStateOf("") }
            var pass2Text by remember { mutableStateOf("") }
            var name by remember { mutableStateOf("") }
            var surname by remember { mutableStateOf("") }
            val emailHasErrors by remember {
                derivedStateOf {
                    if (emailText.isNotBlank() && emailText.length <= 255 ) {
                        !android.util.Patterns.EMAIL_ADDRESS.matcher(emailText).matches()
                    } else if (emailText.isBlank()) {
                        false
                    }else{
                        true
                    }
                }
            }
            val passwordHasErrors by remember {
                derivedStateOf {
                    if((passText.length !in 8..65) && passText.isNotEmpty() || (passText.isBlank() && passText.isNotEmpty())){
                        true
                    }else{
                        false
                    }
                }
            }
                Box(Modifier.fillMaxSize()) {
                    Card(
                        modifier = Modifier
                            .shadow(
                                elevation = 10.dp,
                                shape = RoundedCornerShape(30.dp)
                            )
                            .width(360.dp)
                            .align(Alignment.Center),

                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        Column(Modifier.align(Alignment.CenterHorizontally), horizontalAlignment =
                            Alignment.CenterHorizontally) {
                            Spacer(Modifier.size(25.dp))
                            Icon(painterResource(R.drawable.app_icon), "", modifier = Modifier.size(90.dp),
                                tint = Color(0xff155DFC))
                            Text(stringResource(R.string.app_name_label), fontSize = 45.sp, fontWeight = FontWeight.ExtraBold)
                            Text(stringResource(R.string.app_name_desk), fontSize = 20.sp, fontWeight = FontWeight.Normal, modifier = Modifier.alpha(0.7f))
                            Spacer(Modifier.size(45.dp))
                            CustomTextField1(value = name, onValueChange = {name = it}, placeholder = stringResource(R.string.person_name))
                            Spacer(Modifier.size(20.dp))
                            CustomTextField1(value = surname, onValueChange = {surname = it}, placeholder = stringResource(R.string.person_surname))
                            Spacer(Modifier.size(20.dp))
                            CustomEmailTextField1(value = emailText, onValueChange = {emailText = it}, placeholder = stringResource(R.string.email),
                                validatorEmailHasErrors = emailHasErrors)
                            Spacer(Modifier.size(20.dp))
                            CustomPasswordTextField(value = passText, onValueChange = {passText = it}, placeholder = stringResource(R.string.password),
                                validatorPasswordHasErrors = passwordHasErrors)
                            Spacer(Modifier.size(20.dp))
                            CustomPasswordTextField(value = pass2Text, onValueChange = {pass2Text = it}, placeholder = stringResource(R.string.repeat_password),
                                validatorPasswordHasErrors = passwordHasErrors)
                            Spacer(Modifier.size(20.dp))
                            Button(onClick = {}, modifier = Modifier
                                .height(53.dp)
                                .fillMaxWidth(0.9f), colors = ButtonDefaults.buttonColors(containerColor = Color(0xff155DFC))) {
                                Text(stringResource(R.string.signup), fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
                            }
                            Spacer(Modifier.size(10.dp))
                            Text("Войти", Modifier.clickable(onClick = {
                                navHostController.navigate(LoginItemsNav.NavItems[0].route)
                            }))
                            Spacer(Modifier.size(25.dp))
                        }
                    }
                }
            }
    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    fun CustomTextField1(
        value: String,
        onValueChange: (String) -> Unit,
        placeholder: String,
        modifier: Modifier = Modifier,
        enabled: Boolean = true,
        keyboardType: KeyboardType = KeyboardType.Text
    ) {
        var isFocused by remember { mutableStateOf(false) }
        val focusRequester = remember { FocusRequester() }
        val elevation by animateDpAsState(
            targetValue = if (isFocused) 8.dp else 2.dp,
            animationSpec = tween(durationMillis = 200)
        )

        Surface(
            modifier = modifier
                .shadow(
                    elevation = elevation,
                    shape = RoundedCornerShape(20.dp)
                )
                .border(
                    width = 1.dp,
                    color = if (isFocused) Color(0xff155DFC).copy(alpha = 0.6f)
                    else Color.LightGray.copy(alpha = 0.3f),
                    shape = RoundedCornerShape(20.dp)
                )
                .clip(RoundedCornerShape(20.dp))
                .background(
                    color = if (enabled) Color.White else Color.LightGray
                )
                .clickable(
                    enabled = enabled,
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) { focusRequester.requestFocus() },
            color = Color.Transparent
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .padding(horizontal = 16.dp, vertical = 4.dp)
            ) {
                Spacer(modifier = Modifier.width(12.dp))

                BasicTextField(
                    value = value,
                    onValueChange = onValueChange,
                    modifier = Modifier
                        .weight(1f)
                        .focusRequester(focusRequester)
                        .onFocusChanged { focusState ->
                            isFocused = focusState.isFocused
                        }
                        .height(46.dp)
                        .fillMaxWidth(0.9f),
                    enabled = enabled,
                    keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
                    textStyle = TextStyle(
                        color = if (enabled) Color.Black else Color.Gray,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal
                    ),
                    decorationBox = { innerTextField ->
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            if (value.isEmpty()) {
                                Text(
                                    text = placeholder,
                                    color = Color.Gray.copy(alpha = 0.6f),
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Normal
                                )
                            }
                            innerTextField()
                        }
                    }
                )
            }
        }

    }
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CustomEmailTextField1(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    keyboardType: KeyboardType = KeyboardType.Text,
    validatorEmailHasErrors: Boolean
) {
    var isFocused by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }
    val elevation by animateDpAsState(
        targetValue = if (isFocused) 8.dp else 2.dp,
        animationSpec = tween(durationMillis = 200)
    )

    Column(
        modifier = modifier
            .fillMaxWidth(0.9f)
    ) {
        Surface(
            modifier = Modifier
                .shadow(
                    elevation = elevation,
                    shape = RoundedCornerShape(20.dp)
                )
                .border(
                    width = 1.dp,
                    color = if (validatorEmailHasErrors) Color.Red.copy(alpha = 0.6f)
                    else if (isFocused) Color(0xff155DFC).copy(alpha = 0.6f)
                    else Color.LightGray.copy(alpha = 0.3f),
                    shape = RoundedCornerShape(20.dp)
                )
                .clip(RoundedCornerShape(20.dp))
                .background(
                    color = if (enabled) Color.White else Color.LightGray
                )
                .clickable(
                    enabled = enabled,
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) { focusRequester.requestFocus() },
            color = Color.Transparent
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp)
                    .padding(horizontal = 16.dp)
            ) {
                Spacer(modifier = Modifier.width(12.dp))

                BasicTextField(
                    value = value,
                    onValueChange = onValueChange,
                    modifier = Modifier
                        .weight(1f)
                        .focusRequester(focusRequester)
                        .onFocusChanged { focusState ->
                            isFocused = focusState.isFocused
                        },
                    enabled = enabled,
                    keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
                    textStyle = TextStyle(
                        color = if (enabled) Color.Black else Color.Gray,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal
                    ),
                    decorationBox = { innerTextField ->
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            if (value.isEmpty()) {
                                Text(
                                    text = placeholder,
                                    color = Color.Gray.copy(alpha = 0.6f),
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Normal
                                )
                            }
                            innerTextField()
                        }
                    }
                )
            }
        }

        if (validatorEmailHasErrors) {
            Text(
                text = "Email введен некорректно!",
                color = Color.Red,
                fontSize = 14.sp,
                modifier = Modifier
                    .padding(start = 16.dp, top = 4.dp)
                    .fillMaxWidth()
            )
        }
    }
}
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CustomPasswordTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,validatorPasswordHasErrors: Boolean,
    keyboardType: KeyboardType = KeyboardType.Text,
    isPassword: Boolean = true
) {
    var isFocused by remember { mutableStateOf(false) }
    var passwordVisible by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }
    val elevation by animateDpAsState(
        targetValue = if (isFocused) 8.dp else 2.dp,
        animationSpec = tween(durationMillis = 200)
    )

    val visualTransformation = if (isPassword) {
        if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation()
    } else {
        VisualTransformation.None
    }
    Surface(
        modifier = modifier
            .shadow(
                elevation = elevation,
                shape = RoundedCornerShape(20.dp)
            )
            .border(
                width = 1.dp,
                color = if (isFocused) Color(0xff155DFC).copy(alpha = 0.6f)
                else Color.LightGray.copy(alpha = 0.3f),
                shape = RoundedCornerShape(20.dp)
            )
            .clip(RoundedCornerShape(20.dp))
            .background(
                color = if (enabled) Color.White else Color.LightGray
            )
            .clickable(
                enabled = enabled,
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { focusRequester.requestFocus() },
        color = Color.Transparent
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .height(52.dp)
                .padding(horizontal = 16.dp, vertical = 4.dp)
        ) {
            Spacer(modifier = Modifier.width(12.dp))

            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                modifier = Modifier
                    .weight(1f)
                    .focusRequester(focusRequester)
                    .onFocusChanged { focusState ->
                        isFocused = focusState.isFocused
                    },
                enabled = enabled,
                keyboardOptions = KeyboardOptions(
                    keyboardType = if (isPassword) KeyboardType.Password else keyboardType
                ),
                textStyle = TextStyle(
                    color = if (enabled) Color.Black else Color.Gray,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal
                ),
                visualTransformation = visualTransformation,
                decorationBox = { innerTextField ->
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        if (value.isEmpty()) {
                            Text(
                                text = placeholder,
                                color = Color.Gray.copy(alpha = 0.6f),
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Normal
                            )
                        }
                        innerTextField()
                    }
                }
            )
            if (isPassword) {
                val image = if (passwordVisible)
                    R.drawable.visibility_icon
                else R.drawable.visibility_off_icon
                IconButton(
                    onClick = { passwordVisible = !passwordVisible },
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        painterResource(image),
                        contentDescription = "",
                        tint = Color.Black.copy(alpha = 0.6f)
                    )
                }
            }
        }

    }
    if (validatorPasswordHasErrors) {
        Text(
            text = if (value.isBlank()) "Поле пустое"
                else "Длина пароля должна быть от 8 до 64",
            color = Color.Red,
            fontSize = 14.sp,
            modifier = Modifier
                .padding(start = 16.dp, top = 4.dp)
                .fillMaxWidth()
        )
    }
}

