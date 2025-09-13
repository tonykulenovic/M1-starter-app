package com.cpen321.usermanagement.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.cpen321.usermanagement.R
import com.cpen321.usermanagement.data.remote.dto.Book
import com.cpen321.usermanagement.ui.components.MessageSnackbar
import com.cpen321.usermanagement.ui.components.MessageSnackbarState
import com.cpen321.usermanagement.ui.viewmodels.MainUiState
import com.cpen321.usermanagement.ui.viewmodels.MainViewModel
import com.cpen321.usermanagement.ui.viewmodels.ProfileViewModel
import com.cpen321.usermanagement.ui.theme.LocalSpacing
import androidx.compose.material.icons.filled.AccountCircle

@Composable
fun MainScreen(
    mainViewModel: MainViewModel,
    profileViewModel: ProfileViewModel,
    onProfileClick: () -> Unit
) {
    val uiState by mainViewModel.uiState.collectAsState()
    val profileState by profileViewModel.uiState.collectAsState()
    val snackBarHostState = remember { SnackbarHostState() }

    LaunchedEffect(profileState.user) {
        profileState.user?.let { user ->
            mainViewModel.loadBooksForUser(user)
        }
    }

    MainContent(
        uiState = uiState,
        snackBarHostState = snackBarHostState,
        onProfileClick = onProfileClick,
        onSuccessMessageShown = mainViewModel::clearSuccessMessage,
        onErrorMessageShown = mainViewModel::clearError
    )
}

@Composable
private fun MainContent(
    uiState: MainUiState,
    snackBarHostState: SnackbarHostState,
    onProfileClick: () -> Unit,
    onSuccessMessageShown: () -> Unit,
    onErrorMessageShown: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            MainTopBar(onProfileClick = onProfileClick)
        },
        snackbarHost = {
            MainSnackbarHost(
                hostState = snackBarHostState,
                successMessage = uiState.successMessage,
                errorMessage = uiState.errorMessage,
                onSuccessMessageShown = onSuccessMessageShown,
                onErrorMessageShown = onErrorMessageShown
            )
        }
    ) { paddingValues ->
        MainBody(
            uiState = uiState,
            paddingValues = paddingValues
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MainTopBar(
    onProfileClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        modifier = modifier,
        title = {
            AppTitle()
        },
        actions = {
            ProfileActionButton(onClick = onProfileClick)
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface,
            titleContentColor = MaterialTheme.colorScheme.onSurface
        )
    )
}

@Composable
private fun AppTitle(
    modifier: Modifier = Modifier
) {
    Text(
        text = stringResource(R.string.app_name),
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.Medium,
        modifier = modifier
    )
}

@Composable
private fun ProfileActionButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val spacing = LocalSpacing.current

    IconButton(
        onClick = onClick,
        modifier = modifier.size(spacing.extraLarge2)
    ) {
        ProfileIcon()
    }
}

@Composable
private fun ProfileIcon() {
    androidx.compose.material3.Icon(
        imageVector = androidx.compose.material.icons.Icons.Filled.AccountCircle,
        contentDescription = "Profile"
    )
}

@Composable
private fun MainSnackbarHost(
    hostState: SnackbarHostState,
    successMessage: String?,
    errorMessage: String?,
    onSuccessMessageShown: () -> Unit,
    onErrorMessageShown: () -> Unit,
    modifier: Modifier = Modifier
) {
    MessageSnackbar(
        hostState = hostState,
        messageState = MessageSnackbarState(
            successMessage = successMessage,
            errorMessage = errorMessage,
            onSuccessMessageShown = onSuccessMessageShown,
            onErrorMessageShown = onErrorMessageShown
        ),
        modifier = modifier
    )
}

@Composable
private fun MainBody(
    uiState: MainUiState,
    paddingValues: PaddingValues,
    modifier: Modifier = Modifier
) {
    val spacing = LocalSpacing.current

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(spacing.medium),
        verticalArrangement = Arrangement.spacedBy(spacing.large)
    ) {
        item {
            WelcomeSection(uiState = uiState)
        }

        if (uiState.isLoadingBooks) {
            item {
                LoadingIndicator()
            }
        } else if (uiState.books.isNotEmpty()) {
            item {
                BookRecommendationsSection(books = uiState.books)
            }
        } else if (uiState.userHobbies.isNotEmpty()) {
            item {
                NoBooksFoundMessage()
            }
        }
    }
}

@Composable
private fun WelcomeSection(
    uiState: MainUiState,
    modifier: Modifier = Modifier
) {
    val spacing = LocalSpacing.current

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = if (uiState.userHobbies.isNotEmpty()) {
                "Books for your hobbies: ${uiState.userHobbies.joinToString(", ")}"
            } else {
                stringResource(R.string.welcome)
            },
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center
        )

        if (uiState.userHobbies.isNotEmpty()) {
            Spacer(modifier = Modifier.height(spacing.small))
            Text(
                text = "Discover books related to your interests",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun BookRecommendationsSection(
    books: List<Book>,
    modifier: Modifier = Modifier
) {
    val spacing = LocalSpacing.current

    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = "Recommended Books",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(spacing.medium))

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(spacing.medium)
        ) {
            items(books) { book ->
                BookCard(book = book)
            }
        }
    }
}

@Composable
private fun BookCard(
    book: Book,
    modifier: Modifier = Modifier
) {
    val spacing = LocalSpacing.current

    Card(
        modifier = modifier
            .width(160.dp)
            .height(280.dp),
        shape = RoundedCornerShape(spacing.small),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(spacing.small)
        ) {
            // Book cover
            AsyncImage(
                model = book.coverUrl,
                contentDescription = "Book cover for ${book.title}",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(spacing.small))

            // Book title
            Text(
                text = book.title,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Medium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(spacing.extraSmall))

            // Author
            Text(
                text = book.authorNames,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(spacing.extraSmall))

            // Publish year
            book.publishYear?.let { year ->
                Text(
                    text = year.toString(),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
private fun LoadingIndicator(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun NoBooksFoundMessage(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "No books found for your hobbies. Try adding more hobbies to your profile!",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )
    }
}