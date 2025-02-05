    package com.example.nuvizeapp

    import android.os.Bundle
    import androidx.activity.ComponentActivity
    import androidx.activity.compose.setContent
    import androidx.compose.foundation.Image
    import androidx.compose.foundation.clickable
    import androidx.compose.foundation.layout.*
    import androidx.compose.foundation.lazy.LazyColumn
    import androidx.compose.foundation.lazy.items
    import androidx.compose.material3.*
    import androidx.compose.runtime.*
    import androidx.compose.ui.Alignment
    import androidx.compose.ui.Modifier
    import androidx.compose.ui.res.painterResource
    import androidx.compose.ui.text.input.PasswordVisualTransformation
    import androidx.compose.ui.unit.dp
    import androidx.compose.ui.unit.sp
    import com.example.nuvizeapp.R

    class MainActivity : ComponentActivity() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContent {
                App()
            }
        }
    }

    @Composable
    fun App() {
        var showLogin by remember { mutableStateOf(true) }

        if (showLogin) {
            LoginScreen(onLoginSuccess = { showLogin = false })
        } else {
            NewslettersScreen(onLogout = { showLogin = true })
        }
    }

    @Composable
    fun LoginScreen(onLoginSuccess: () -> Unit) {
        var username by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var showError by remember { mutableStateOf(false) }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Nuvize Logo",
                modifier = Modifier
                    .size(80.dp)
                    .padding(bottom = 32.dp)
            )


            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Username") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(5.dp))


            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )


            if (showError) {
                Text(
                    text = "Incorrect username/password",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(10.dp))


            Button(
                onClick = {
                    if (username == "user" && password == "password") {
                        onLoginSuccess()
                    } else {
                        showError = true
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                Text("Login", fontSize = 20.sp)
            }
        }
    }


    data class Newsletter(
        val id: Int,
        val title: String,
        val description: String,
        val date: String,
        val publisher: String,
        val content: String
    )


    val dummyNewsletters = listOf(
        Newsletter(
            id = 1,
            title = "Jetpack Compose Basics",
            description = "Learn the basics of Jetpack Compose for Android development.",
            date = "Oct 10, 2023",
            publisher = "Android Developers",
            content = "Jetpack Compose is a modern toolkit for building native Android UI."
        ),
        Newsletter(
            id = 2,
            title = "Kotlin Coroutines Deep Dive",
            description = "A deep dive into Kotlin Coroutines for asynchronous programming.",
            date = "Oct 5, 2023",
            publisher = "Kotlin Blog",
            content = "Kotlin Coroutines provide a way to write asynchronous code in a sequential style."
        ),
        Newsletter(
            id = 3,
            title = "UI/UX Design Tips",
            description = "Improve your app's user experience with these design tips.",
            date = "Oct 1, 2023",
            publisher = "Design Weekly",
            content = "Good UI/UX design is essential for creating engaging and user-friendly apps."
        ),
        Newsletter(
            id = 4,
            title = "Android Architecture Patterns",
            description = "Explore MVVM, MVI, and other architecture patterns for Android.",
            date = "Sep 25, 2023",
            publisher = "Android Weekly",
            content = "Choosing the right architecture pattern can make your app more maintainable and scalable."
        ),
        Newsletter(
            id = 5,
            title = "Flutter vs Jetpack Compose",
            description = "A comparison of Flutter and Jetpack Compose for cross-platform and native development.",
            date = "Sep 20, 2023",
            publisher = "Mobile Dev Digest",
            content = "Both Flutter and Jetpack Compose have their strengths. Learn which one suits your needs."
        ),
        Newsletter(
            id = 6,
            title = "Building REST APIs with Ktor",
            description = "Learn how to build REST APIs using Ktor, a lightweight Kotlin framework.",
            date = "Sep 15, 2023",
            publisher = "Kotlin Weekly",
            content = "Ktor is a great choice for building asynchronous and scalable APIs."
        ),
        Newsletter(
            id = 7,
            title = "State Management in Jetpack Compose",
            description = "Understand how to manage state effectively in Jetpack Compose.",
            date = "Sep 10, 2023",
            publisher = "Android Developers",
            content = "State management is crucial for building reactive and efficient UIs in Compose."
        ),
        Newsletter(
            id = 8,
            title = "Introduction to Room Database",
            description = "Learn how to use Room for local data storage in Android apps.",
            date = "Sep 5, 2023",
            publisher = "Android Weekly",
            content = "Room simplifies database interactions and integrates seamlessly with LiveData and Coroutines."
        )
    )

    @Composable
    fun NewslettersScreen(onLogout: () -> Unit) {
        var selectedNewsletter by remember { mutableStateOf<Newsletter?>(null) }

        if (selectedNewsletter != null) {
            NewsletterDetailScreen(newsletter = selectedNewsletter!!, onBack = { selectedNewsletter = null })
        } else {
            Column {

                Button(
                    onClick = { onLogout() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error,
                        contentColor = MaterialTheme.colorScheme.onError
                    )
                ) {
                    Text("Logout", fontSize = 16.sp)
                }


                NewsletterListScreen(onNewsletterClick = { newsletter -> selectedNewsletter = newsletter })
            }
        }
    }

    @Composable
    fun NewsletterListScreen(onNewsletterClick: (Newsletter) -> Unit) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            items(dummyNewsletters) { newsletter ->
                NewsletterItem(newsletter = newsletter, onClick = { onNewsletterClick(newsletter) })
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }

    @Composable
    fun NewsletterItem(newsletter: Newsletter, onClick: () -> Unit) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onClick() }
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(text = newsletter.title, style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.primary)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = newsletter.description, style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Published on ${newsletter.date} by ${newsletter.publisher}",
                    style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.secondary)
            }
        }
    }

    @Composable
    fun NewsletterDetailScreen(newsletter: Newsletter, onBack: () -> Unit) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {

            Text(
                text = "← Back",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .clickable { onBack() }
                    .padding(bottom = 16.dp)
            )


            Text(text = newsletter.title, style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Published on ${newsletter.date} by ${newsletter.publisher}",
                style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.secondary)
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = newsletter.content, style = MaterialTheme.typography.bodyLarge)
        }
    }
