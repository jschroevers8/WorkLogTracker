package worklogtracker.frontend.presentation.worklog

import android.Manifest
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import com.google.android.gms.location.LocationServices
import org.koin.androidx.compose.koinViewModel
import worklogtracker.frontend.presentation.framework.BottomNavigationBar
import java.io.ByteArrayOutputStream

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkLogScreen(
    backStack: NavBackStack<NavKey>,
    taskId: String? = null,
    viewModel: WorkLogViewModel = koinViewModel()
) {
    val uiState = viewModel.uiState
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    LaunchedEffect(taskId) {
        taskId?.toLongOrNull()?.let {
            viewModel.onTaskAssignmentSelected(it.toInt())
        }
    }

    // Camera Launcher
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        if (bitmap != null) {
            val base64 = bitmapToBase64(bitmap)
            viewModel.onPhotoCaptured(base64)
        }
    }

    // Permission Launchers
    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            cameraLauncher.launch()
        }
    }

    val locationPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        if (permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
            permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true
        ) {
            captureLocation(context, viewModel)
        }
    }

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                backStack = backStack,
                onItemSelected = { screen -> backStack.add(screen) }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(scrollState)
        ) {
            Text(
                text = "Uren Registreren",
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Task Selection
            Text("Selecteer Taak", style = MaterialTheme.typography.labelLarge)
            var expanded by remember { mutableStateOf(false) }
            val selectedAssignment = uiState.tasks.find { it.id == uiState.selectedTaskAssignmentId }
            
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                TextField(
                    value = selectedAssignment?.title ?: "Selecteer een taak",
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    modifier = Modifier.fillMaxWidth().menuAnchor(),
                    colors = ExposedDropdownMenuDefaults.textFieldColors()
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    uiState.tasks.forEach { task ->
                        DropdownMenuItem(
                            text = { Text(task.title) },
                            onClick = {
                                viewModel.onTaskAssignmentSelected(task.id)
                                expanded = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Hours
            TextField(
                value = uiState.hours,
                onValueChange = { viewModel.onHoursChanged(it) },
                label = { Text("Aantal uren") },
                placeholder = { Text("Bijv. 2.5") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(
                    keyboardType = androidx.compose.ui.text.input.KeyboardType.Number
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Description
            TextField(
                value = uiState.description,
                onValueChange = { viewModel.onDescriptionChanged(it) },
                label = { Text("Omschrijving") },
                placeholder = { Text("Wat heb je gedaan?") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Location
            Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
                Button(onClick = {
                    locationPermissionLauncher.launch(
                        arrayOf(
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                        )
                    )
                }) {
                    Text(if (uiState.latitude != null) "Locatie OK ✓" else "Haal GPS Locatie op")
                }
                if (uiState.latitude != null) {
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        "Lat: ${uiState.latitude?.toString()?.take(8)}, Lon: ${uiState.longitude?.toString()?.take(8)}",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Photo
            if (uiState.photoBase64 != null) {
                val bitmap = base64ToBitmap(uiState.photoBase64)
                bitmap?.let {
                    Image(
                        bitmap = it.asImageBitmap(),
                        contentDescription = "Captured photo",
                        modifier = Modifier.fillMaxWidth().height(200.dp)
                    )
                }
                Button(
                    onClick = { viewModel.onPhotoCaptured("") },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                ) {
                    Text("Opnieuw")
                }
            } else {
                Button(onClick = {
                    cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
                }) {
                    Text("Foto maken met Camera")
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Submit
            Button(
                onClick = { viewModel.submitWorkLog() },
                modifier = Modifier.fillMaxWidth(),
                enabled = uiState.selectedTaskAssignmentId != null && uiState.hours.isNotEmpty() && !uiState.loading
            ) {
                if (uiState.loading) {
                    CircularProgressIndicator(modifier = Modifier.size(24.dp), color = MaterialTheme.colorScheme.onPrimary)
                } else {
                    Text("Registreer Uren")
                }
            }

            if (uiState.error != null) {
                Text(text = uiState.error, color = MaterialTheme.colorScheme.error)
            }

            if (uiState.success) {
                AlertDialog(
                    onDismissRequest = { viewModel.resetSuccess() },
                    title = { Text("Succes") },
                    text = { Text("Uren succesvol geregistreerd!") },
                    confirmButton = {
                        TextButton(onClick = { viewModel.resetSuccess() }) {
                            Text("OK")
                        }
                    }
                )
            }
        }
    }
}

private fun captureLocation(context: Context, viewModel: WorkLogViewModel) {
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    try {
        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
                viewModel.onLocationCaptured(location.latitude, location.longitude)
            }
        }
    } catch (e: SecurityException) {
        // Handle exception
    }
}

private fun bitmapToBase64(bitmap: Bitmap): String {
    // Scale down if necessary to reduce Base64 length
    val maxWidth = 1024
    val scaledBitmap = if (bitmap.width > maxWidth) {
        val scale = maxWidth.toDouble() / bitmap.width
        Bitmap.createScaledBitmap(bitmap, maxWidth, (bitmap.height * scale).toInt(), true)
    } else {
        bitmap
    }
    
    val byteArrayOutputStream = ByteArrayOutputStream()
    scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 70, byteArrayOutputStream)
    val byteArray = byteArrayOutputStream.toByteArray()
    return "data:image/jpeg;base64," + Base64.encodeToString(byteArray, Base64.DEFAULT)
}

private fun base64ToBitmap(base64Str: String): Bitmap? {
    return try {
        val pureBase64 = if (base64Str.contains(",")) base64Str.split(",")[1] else base64Str
        val decodedBytes = Base64.decode(pureBase64, Base64.DEFAULT)
        BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
    } catch (e: Exception) {
        null
    }
}
