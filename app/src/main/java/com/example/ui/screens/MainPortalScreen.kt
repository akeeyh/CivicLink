package com.example.ui.screens

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Color as AndroidColor
import android.graphics.RectF
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.data.model.Booking
import com.example.data.model.OrgBulkMaintenanceSchedule
import com.example.data.model.ServiceProvider
import com.example.data.model.Complaint
import com.example.ui.viewmodel.CivicViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

// --- Programmatic Bitmap Generators for Multimodal Fault Analysis ---
fun generateBurstPipeBitmap(): Bitmap {
    val bitmap = Bitmap.createBitmap(320, 320, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    val paint = Paint().apply { isAntiAlias = true }

    // Draw wall background
    paint.color = AndroidColor.rgb(180, 180, 180)
    canvas.drawRect(0f, 0f, 320f, 320f, paint)

    // Draw brick patterns
    paint.color = AndroidColor.rgb(150, 150, 150)
    paint.strokeWidth = 4f
    canvas.drawLine(0f, 80f, 320f, 80f, paint)
    canvas.drawLine(0f, 160f, 320f, 160f, paint)
    canvas.drawLine(0f, 240f, 320f, 240f, paint)
    canvas.drawLine(80f, 0f, 80f, 80f, paint)
    canvas.drawLine(240f, 0f, 240f, 80f, paint)
    canvas.drawLine(160f, 80f, 160f, 160f, paint)
    canvas.drawLine(80f, 160f, 80f, 240f, paint)
    canvas.drawLine(240f, 160f, 240f, 240f, paint)

    // Draw iron pipe
    paint.style = Paint.Style.FILL
    paint.color = AndroidColor.rgb(110, 120, 130)
    canvas.drawRect(40f, 130f, 280f, 190f, paint)

    // Pipe connector rings
    paint.color = AndroidColor.rgb(80, 90, 100)
    canvas.drawRect(140f, 120f, 180f, 200f, paint)

    // Burst leak crack
    paint.color = AndroidColor.BLACK
    paint.strokeWidth = 6f
    canvas.drawLine(155f, 160f, 175f, 165f, paint)

    // Leaking spraying water (dynamic neon blue arcs)
    paint.color = AndroidColor.rgb(33, 150, 243)
    paint.style = Paint.Style.STROKE
    paint.strokeWidth = 5f
    canvas.drawArc(RectF(80f, 40f, 165f, 160f), 180f, 90f, false, paint)
    canvas.drawArc(RectF(100f, 60f, 165f, 160f), 190f, 80f, false, paint)
    canvas.drawArc(RectF(155f, 40f, 240f, 160f), 270f, 90f, false, paint)
    canvas.drawArc(RectF(155f, 70f, 210f, 160f), 280f, 80f, false, paint)

    // Droplets
    paint.style = Paint.Style.FILL
    canvas.drawCircle(110f, 50f, 6f, paint)
    canvas.drawCircle(220f, 60f, 6f, paint)
    canvas.drawCircle(150f, 90f, 8f, paint)

    return bitmap
}

fun generateBurntOutletBitmap(): Bitmap {
    val bitmap = Bitmap.createBitmap(320, 320, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    val paint = Paint().apply { isAntiAlias = true }

    // White socket plate wall background
    paint.color = AndroidColor.rgb(230, 225, 215)
    canvas.drawRect(0f, 0f, 320f, 320f, paint)

    // Plastic cover faceplate outline
    paint.color = AndroidColor.rgb(190, 185, 175)
    paint.style = Paint.Style.STROKE
    paint.strokeWidth = 8f
    canvas.drawRoundRect(RectF(60f, 60f, 260f, 260f), 16f, 16f, paint)

    // Socket plugs
    paint.style = Paint.Style.FILL
    paint.color = AndroidColor.rgb(215, 210, 200)
    canvas.drawCircle(160f, 115f, 30f, paint)
    canvas.drawCircle(160f, 205f, 30f, paint)

    // Socket slots
    paint.color = AndroidColor.DKGRAY
    canvas.drawRect(145f, 105f, 152f, 125f, paint)
    canvas.drawRect(168f, 105f, 175f, 125f, paint)
    canvas.drawRect(156f, 125f, 164f, 135f, paint) // ground pin

    canvas.drawRect(145f, 195f, 152f, 215f, paint)
    canvas.drawRect(168f, 195f, 175f, 215f, paint)
    canvas.drawRect(156f, 215f, 164f, 225f, paint)

    // Burn mark (dark charcoal/smoke soot over the top socket)
    paint.color = AndroidColor.rgb(45, 45, 45)
    canvas.drawCircle(160f, 110f, 40f, paint)
    paint.color = AndroidColor.rgb(90, 75, 65)
    canvas.drawCircle(160f, 110f, 55f, paint) // outer singe

    // Splotches of soot
    paint.style = Paint.Style.FILL
    paint.color = AndroidColor.rgb(30, 30, 30)
    canvas.drawCircle(130f, 90f, 18f, paint)
    canvas.drawCircle(195f, 125f, 15f, paint)

    // Melted plastic crack
    paint.color = AndroidColor.rgb(20, 20, 20)
    paint.strokeWidth = 4f
    canvas.drawLine(145f, 110f, 120f, 75f, paint)
    canvas.drawLine(175f, 120f, 210f, 130f, paint)

    return bitmap
}

fun generateBrokenCabinetBitmap(): Bitmap {
    val bitmap = Bitmap.createBitmap(320, 320, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    val paint = Paint().apply { isAntiAlias = true }

    // Wood background
    paint.color = AndroidColor.rgb(139, 90, 43) // brown
    canvas.drawRect(0f, 0f, 320f, 320f, paint)

    // Wood grain lines
    paint.color = AndroidColor.rgb(110, 70, 30)
    paint.strokeWidth = 3f
    canvas.drawLine(20f, 0f, 40f, 320f, paint)
    canvas.drawLine(100f, 0f, 90f, 320f, paint)
    canvas.drawLine(210f, 0f, 220f, 320f, paint)
    canvas.drawLine(300f, 0f, 280f, 320f, paint)

    // Drawer framing (cutout)
    paint.color = AndroidColor.rgb(50, 30, 10)
    paint.style = Paint.Style.FILL
    canvas.drawRect(50f, 50f, 270f, 270f, paint)

    // Slanted, broken falling door panel
    paint.color = AndroidColor.rgb(160, 110, 60)
    canvas.save()
    canvas.rotate(-15f, 70f, 70f)
    canvas.drawRect(60f, 60f, 250f, 250f, paint)

    // Metal handle
    paint.color = AndroidColor.rgb(192, 192, 192)
    canvas.drawRect(210f, 120f, 225f, 180f, paint)

    // Broken dangling metal hinge
    paint.color = AndroidColor.rgb(100, 100, 100)
    canvas.drawRect(50f, 75f, 75f, 95f, paint)
    canvas.restore()

    // Bottom hinge broken clean with screw holes
    paint.color = AndroidColor.BLACK
    canvas.drawCircle(60f, 230f, 4f, paint)
    canvas.drawCircle(65f, 240f, 4f, paint)

    return bitmap
}

fun generateCloggedFilterBitmap(): Bitmap {
    val bitmap = Bitmap.createBitmap(320, 320, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    val paint = Paint().apply { isAntiAlias = true }

    // Plastic white frame
    paint.color = AndroidColor.rgb(240, 240, 240)
    canvas.drawRect(0f, 0f, 320f, 320f, paint)

    // Inner mesh area
    paint.color = AndroidColor.rgb(220, 220, 220)
    canvas.drawRect(30f, 30f, 290f, 290f, paint)

    // Grid mesh lines
    paint.color = AndroidColor.rgb(180, 180, 180)
    paint.strokeWidth = 2f
    for (i in 40..280 step 15) {
        canvas.drawLine(i.toFloat(), 30f, i.toFloat(), 290f, paint)
        canvas.drawLine(30f, i.toFloat(), 290f, i.toFloat(), paint)
    }

    // Heavy gray-brown dust accumulation blocks (clogged center)
    paint.style = Paint.Style.FILL
    paint.color = AndroidColor.rgb(110, 105, 95)
    canvas.drawCircle(160f, 160f, 65f, paint)
    canvas.drawCircle(120f, 140f, 45f, paint)
    canvas.drawCircle(190f, 170f, 50f, paint)

    // Dusty splatter
    paint.color = AndroidColor.rgb(140, 135, 125)
    canvas.drawCircle(90f, 100f, 30f, paint)
    canvas.drawCircle(220f, 220f, 35f, paint)
    canvas.drawCircle(150f, 230f, 28f, paint)

    // Frame border lines
    paint.color = AndroidColor.rgb(200, 200, 200)
    paint.strokeWidth = 6f
    paint.style = Paint.Style.STROKE
    canvas.drawRect(30f, 30f, 290f, 290f, paint)

    return bitmap
}

// --- Composable UI Screens ---

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainPortalScreen(viewModel: CivicViewModel) {
    val activeTab by viewModel.currentPortalTab.collectAsState()
    val bookings by viewModel.allBookings.collectAsState()
    val providers by viewModel.allProviders.collectAsState()
    val schedules by viewModel.allSchedules.collectAsState()
    val complaints by viewModel.allComplaints.collectAsState()

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier.width(300.dp),
                drawerContainerColor = MaterialTheme.colorScheme.surfaceVariant
            ) {
                Spacer(modifier = Modifier.height(24.dp))
                Row(
                    modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Build,
                        contentDescription = "Logo",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(36.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "CivicLink AI",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                Divider(modifier = Modifier.padding(horizontal = 16.dp))
                Spacer(modifier = Modifier.height(16.dp))

                NavigationDrawerItem(
                    label = { Text("Citizen Portal") },
                    selected = activeTab == 0,
                    onClick = {
                        viewModel.setPortalTab(0)
                        scope.launch { drawerState.close() }
                    },
                    icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp).testTag("drawer_citizen_tab")
                )

                NavigationDrawerItem(
                    label = { Text("Service Provider Portal") },
                    selected = activeTab == 1,
                    onClick = {
                        viewModel.setPortalTab(1)
                        scope.launch { drawerState.close() }
                    },
                    icon = { Icon(Icons.Default.DirectionsCar, contentDescription = "Provider") },
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp).testTag("drawer_provider_tab")
                )

                NavigationDrawerItem(
                    label = { Text("Organization Dashboard") },
                    selected = activeTab == 2,
                    onClick = {
                        viewModel.setPortalTab(2)
                        scope.launch { drawerState.close() }
                    },
                    icon = { Icon(Icons.Default.Business, contentDescription = "Org") },
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp).testTag("drawer_org_tab")
                )

                NavigationDrawerItem(
                    label = { Text("Admin Dashboard") },
                    selected = activeTab == 3,
                    onClick = {
                        viewModel.setPortalTab(3)
                        scope.launch { drawerState.close() }
                    },
                    icon = { Icon(Icons.Default.Shield, contentDescription = "Admin") },
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp).testTag("drawer_admin_tab")
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "v1.0.0 Prototype",
                    modifier = Modifier.padding(16.dp),
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                )
            }
        }
    ) {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = when (activeTab) {
                                0 -> "Citizen Portal"
                                1 -> "Service Provider Hub"
                                2 -> "Organization Console"
                                else -> "Admin & Command Center"
                            },
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(Icons.Default.Menu, contentDescription = "Menu")
                        }
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                )
            },
            bottomBar = {
                NavigationBar {
                    NavigationBarItem(
                        selected = activeTab == 0,
                        onClick = { viewModel.setPortalTab(0) },
                        icon = { Icon(Icons.Default.Home, contentDescription = "Citizen") },
                        label = { Text("Citizen") }
                    )
                    NavigationBarItem(
                        selected = activeTab == 1,
                        onClick = { viewModel.setPortalTab(1) },
                        icon = { Icon(Icons.Default.Build, contentDescription = "Provider") },
                        label = { Text("Provider") }
                    )
                    NavigationBarItem(
                        selected = activeTab == 2,
                        onClick = { viewModel.setPortalTab(2) },
                        icon = { Icon(Icons.Default.Business, contentDescription = "Organization") },
                        label = { Text("Organization") }
                    )
                    NavigationBarItem(
                        selected = activeTab == 3,
                        onClick = { viewModel.setPortalTab(3) },
                        icon = { Icon(Icons.Default.Analytics, contentDescription = "Admin") },
                        label = { Text("Admin") }
                    )
                }
            }
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(MaterialTheme.colorScheme.background)
            ) {
                AnimatedContent(
                    targetState = activeTab,
                    transitionSpec = {
                        fadeIn(animationSpec = tween(250)) togetherWith fadeOut(animationSpec = tween(200))
                    }
                ) { targetState ->
                    when (targetState) {
                        0 -> CitizenPortalScreen(viewModel, bookings, providers)
                        1 -> ProviderPortalScreen(viewModel, bookings, providers)
                        2 -> OrganizationDashboardScreen(viewModel, bookings, schedules)
                        else -> AdminDashboardScreen(viewModel, bookings, providers, complaints)
                    }
                }
            }
        }
    }
}

// --- CITIZEN PORTAL ---
@Composable
fun CitizenPortalScreen(
    viewModel: CivicViewModel,
    bookings: List<Booking>,
    providers: List<ServiceProvider>
) {
    val aiResponse by viewModel.aiAnalysisResult.collectAsState()
    val isAnalyzing by viewModel.isAnalyzing.collectAsState()

    val imageResponse by viewModel.imageDiagnosticResult.collectAsState()
    val isImageAnalyzing by viewModel.isImageAnalyzing.collectAsState()

    val selectedBooking by viewModel.selectedBooking.collectAsState()

    var descInput by remember { mutableStateOf("") }
    var userPromptText by remember { mutableStateOf("") }
    var selectedImageIndex by remember { mutableStateOf(-1) }
    var showBookDialog by remember { mutableStateOf(false) }

    // Form inputs for booking dialog
    var bookCategory by remember { mutableStateOf("Plumbing") }
    var bookDesc by remember { mutableStateOf("") }
    var bookAddress by remember { mutableStateOf("") }
    var bookName by remember { mutableStateOf("") }
    var bookEmergency by remember { mutableStateOf(false) }
    var bookCost by remember { mutableStateOf(65.0) }
    var bookTimeEst by remember { mutableStateOf("1-2 Hours") }

    val categories = listOf("Plumbing", "Electrical", "Appliance Repair", "Deep Cleaning", "Pest Control", "Carpentry")

    val context = LocalContext.current

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // 1. Service Location & User Avatar Header
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "SERVICE LOCATION",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        letterSpacing = 1.2.sp
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.primary)
                        )
                        Text(
                            text = "Downtown Metropolitan",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                }
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primaryContainer)
                        .border(1.5.dp, MaterialTheme.colorScheme.primary, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "JD",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }

        // Active Booking live tracking status
        val activeTrackingBooking = bookings.firstOrNull { it.status in listOf("Assigned", "In Progress") && !it.isOrganizationBulk }
        if (activeTrackingBooking != null) {
            item {
                ActiveTrackingCard(booking = activeTrackingBooking, viewModel = viewModel)
            }
        }

        // 2. High-Priority Emergency Action Row
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Report Hazard
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color(0xFFFFDAD6))
                        .border(1.dp, Color(0xFFFFB4AB), RoundedCornerShape(16.dp))
                        .clickable {
                            bookCategory = "Electrical"
                            bookDesc = "Urgently reporting hazardous civic damage or residential emergency."
                            bookEmergency = true
                            bookCost = 120.0
                            bookTimeEst = "30-45 Mins"
                            showBookDialog = true
                        }
                        .padding(horizontal = 16.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text("⚠️", fontSize = 16.sp)
                    Text(
                        text = "REPORT HAZARD",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF410002),
                        letterSpacing = 0.5.sp
                    )
                }

                // Verify Provider
                var showVerifyDialog by remember { mutableStateOf(false) }
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color(0xFFE1E2EC))
                        .border(1.dp, Color(0xFFC4C6D0), RoundedCornerShape(16.dp))
                        .clickable {
                            showVerifyDialog = true
                        }
                        .padding(horizontal = 16.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text("📝", fontSize = 16.sp)
                    Text(
                        text = "VERIFY PROVIDER",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF191A2C),
                        letterSpacing = 0.5.sp
                    )
                }

                if (showVerifyDialog) {
                    Dialog(onDismissRequest = { showVerifyDialog = false }) {
                        Card(
                            shape = RoundedCornerShape(24.dp),
                            modifier = Modifier.fillMaxWidth().padding(16.dp)
                        ) {
                            Column(
                                modifier = Modifier.padding(20.dp),
                                verticalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                Text(
                                    "Verified CivicLink Specialists",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 18.sp,
                                    color = MaterialTheme.colorScheme.primary
                                )
                                Text(
                                    "All registered technicians undergo biometric screening, criminal background checks, and state-license verification.",
                                    fontSize = 12.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Divider()
                                LazyColumn(
                                    modifier = Modifier.height(180.dp),
                                    verticalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    items(providers) { provider ->
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f), RoundedCornerShape(12.dp))
                                                .padding(10.dp),
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            Column {
                                                Text(provider.name, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                                Text(provider.category, fontSize = 11.sp, color = Color.Gray)
                                            }
                                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                                Icon(Icons.Default.Verified, contentDescription = "Verified", tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(16.dp))
                                                Text("Active", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                                            }
                                        }
                                    }
                                }
                                Button(
                                    onClick = { showVerifyDialog = false },
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text("Done")
                                }
                            }
                        }
                    }
                }
            }
        }

        // 3. Main Service Modules Grid (2x2 Grid)
        item {
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = "Explore Essential Service Modules",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    // Repairs Card
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(24.dp))
                            .background(Color(0xFFF3EFFF))
                            .border(1.dp, Color(0xFFD7E2FF), RoundedCornerShape(24.dp))
                            .clickable {
                                bookCategory = "Plumbing"
                                bookDesc = "General plumbing & residential repair services request."
                                bookEmergency = false
                                bookCost = 75.0
                                bookTimeEst = "1-2 Hours"
                                showBookDialog = true
                            }
                            .padding(16.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(Color.White),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("🛠️", fontSize = 20.sp)
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                        Text("Repairs", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = Color(0xFF1D1B20))
                        Text("Plumbing, AC, Electrical & Tech support", fontSize = 11.sp, color = Color(0xFF49454F), lineHeight = 14.sp)
                    }

                    // Cleaning Card
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(24.dp))
                            .background(Color(0xFFE8F5E9))
                            .border(1.dp, Color(0xFFC8E6C9), RoundedCornerShape(24.dp))
                            .clickable {
                                bookCategory = "Deep Cleaning"
                                bookDesc = "Full house deep cleaning & sanitization service."
                                bookEmergency = false
                                bookCost = 150.0
                                bookTimeEst = "3-4 Hours"
                                showBookDialog = true
                            }
                            .padding(16.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(Color.White),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("🧹", fontSize = 20.sp)
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                        Text("Cleaning", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = Color(0xFF1D1B20))
                        Text("Deep clean, Sofa, Tanks & Waste processing", fontSize = 11.sp, color = Color(0xFF49454F), lineHeight = 14.sp)
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    // Logistics Card
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(24.dp))
                            .background(Color(0xFFFFF4E1))
                            .border(1.dp, Color(0xFFFFE0B2), RoundedCornerShape(24.dp))
                            .clickable {
                                bookCategory = "Carpentry"
                                bookDesc = "Furniture assembly, shifting & logistics support."
                                bookEmergency = false
                                bookCost = 90.0
                                bookTimeEst = "2-3 Hours"
                                showBookDialog = true
                            }
                            .padding(16.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(Color.White),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("📦", fontSize = 20.sp)
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                        Text("Logistics", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = Color(0xFF1D1B20))
                        Text("Delivery, Towing & heavy furniture shifting", fontSize = 11.sp, color = Color(0xFF49454F), lineHeight = 14.sp)
                    }

                    // Care Card
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(24.dp))
                            .background(Color(0xFFE1F5FE))
                            .border(1.dp, Color(0xFFB3E5FC), RoundedCornerShape(24.dp))
                            .clickable {
                                bookCategory = "Pest Control"
                                bookDesc = "Residential pest control & home disinfection support."
                                bookEmergency = false
                                bookCost = 80.0
                                bookTimeEst = "2 Hours"
                                showBookDialog = true
                            }
                            .padding(16.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(Color.White),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("⚕️", fontSize = 20.sp)
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                        Text("Care Support", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = Color(0xFF1D1B20))
                        Text("Elderly, Patient & Pet service safety", fontSize = 11.sp, color = Color(0xFF49454F), lineHeight = 14.sp)
                    }
                }
            }
        }

        // AI Assistant natural language input
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(28.dp))
                    .background(MaterialTheme.colorScheme.surface)
                    .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(28.dp))
                    .testTag("ai_assistant_card")
            ) {
                // Top-right background decorative circle
                Canvas(modifier = Modifier.size(96.dp).align(Alignment.TopEnd)) {
                    drawCircle(
                        color = Color(0xFFD7E2FF),
                        radius = size.minDimension / 1f,
                        center = Offset(size.width, 0f),
                        alpha = 0.2f
                    )
                }

                Column(modifier = Modifier.padding(20.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(MaterialTheme.colorScheme.primary),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("✨", color = Color.White, fontSize = 14.sp)
                        }
                        Text(
                            text = "CivicLink AI Assistant",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    OutlinedTextField(
                        value = descInput,
                        onValueChange = { descInput = it },
                        modifier = Modifier.fillMaxWidth().height(100.dp).testTag("ai_troubleshoot_input"),
                        placeholder = { Text("e.g., My kitchen sink is clogged and hot water is overflowing onto the floor...") },
                        label = { Text("Describe what's wrong in plain language") },
                        colors = TextFieldDefaults.colors(focusedContainerColor = Color.Transparent, unfocusedContainerColor = Color.Transparent)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = {
                                if (descInput.isNotBlank()) {
                                    viewModel.analyzeServiceNeed(descInput)
                                }
                            },
                            modifier = Modifier.weight(1f).testTag("ai_analyze_button"),
                            enabled = !isAnalyzing && descInput.isNotBlank()
                        ) {
                            if (isAnalyzing) {
                                CircularProgressIndicator(modifier = Modifier.size(18.dp), color = Color.White, strokeWidth = 2.dp)
                            } else {
                                Icon(Icons.Default.AutoAwesome, contentDescription = "AI")
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Diagnose & Book")
                            }
                        }
                        if (aiResponse != null) {
                            OutlinedButton(
                                onClick = {
                                    viewModel.clearAIState()
                                    descInput = ""
                                }
                            ) {
                                Text("Reset")
                            }
                        }
                    }

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.padding(top = 12.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(100.dp))
                                .background(Color(0xFFF1F0F4))
                                .padding(horizontal = 12.dp, vertical = 4.dp)
                        ) {
                            Text("Voice Analysis active", fontSize = 11.sp, fontWeight = FontWeight.Medium, color = Color(0xFF44474E))
                        }
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(100.dp))
                                .background(Color(0xFFE1E2EC))
                                .padding(horizontal = 12.dp, vertical = 4.dp)
                        ) {
                            Text("Photo identified fault", fontSize = 11.sp, fontWeight = FontWeight.Medium, color = Color(0xFF191A2C))
                        }
                    }
                }
            }
        }

        // Render AI response
        if (aiResponse != null) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f), RoundedCornerShape(8.dp))
                        .padding(12.dp)
                ) {
                    Column {
                        Text(
                            text = "CivicLink AI Diagnostic Proposal:",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = aiResponse ?: "",
                            fontSize = 13.sp,
                            lineHeight = 18.sp
                        )
                        Spacer(modifier = Modifier.height(12.dp))

                        val lowerRes = aiResponse!!.lowercase()
                        val categoryGuess = when {
                            "plumbing" in lowerRes -> "Plumbing"
                            "electric" in lowerRes -> "Electrical"
                            "appliance" in lowerRes -> "Appliance Repair"
                            "clean" in lowerRes -> "Deep Cleaning"
                            "pest" in lowerRes -> "Pest Control"
                            "carpent" in lowerRes -> "Carpentry"
                            else -> "Plumbing"
                        }
                        val emergencyGuess = "emergency" in lowerRes || "critical" in lowerRes || "hazard" in lowerRes || "burst" in lowerRes

                        Button(
                            onClick = {
                                bookCategory = categoryGuess
                                bookDesc = descInput
                                bookEmergency = emergencyGuess
                                bookCost = if (emergencyGuess) 110.0 else 75.0
                                bookTimeEst = if (emergencyGuess) "30-45 Mins" else "1-2 Hours"
                                showBookDialog = true
                            },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
                        ) {
                            Icon(Icons.Default.Check, contentDescription = "Confirm")
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Accept AI Plan & Request Match")
                        }
                    }
                }
            }
        }

        // Multimodal Visual Diagnostics
        item {
            Card(
                modifier = Modifier.fillMaxWidth().testTag("visual_diagnostics_card"),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "2. Computer Vision Fault Diagnosis",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Simulate uploading damage photos. Choose a visual scenario below to send real Bitmap buffers to Gemini:",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f)
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        items(listOf("Water Leak", "Burnt Outlet", "Broken Hinge", "Dirty Filter")) { item ->
                            val index = when(item) {
                                "Water Leak" -> 0
                                "Burnt Outlet" -> 1
                                "Broken Hinge" -> 2
                                else -> 3
                            }
                            val bmap = when(index) {
                                0 -> generateBurstPipeBitmap()
                                1 -> generateBurntOutletBitmap()
                                2 -> generateBrokenCabinetBitmap()
                                else -> generateCloggedFilterBitmap()
                            }
                            Column(
                                modifier = Modifier
                                    .width(100.dp)
                                    .clickable { selectedImageIndex = index }
                                    .border(
                                        width = if (selectedImageIndex == index) 3.dp else 1.dp,
                                        color = if (selectedImageIndex == index) MaterialTheme.colorScheme.primary else Color.LightGray,
                                        shape = RoundedCornerShape(8.dp)
                                    )
                                    .padding(4.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Image(
                                    bitmap = bmap.asImageBitmap(),
                                    contentDescription = item,
                                    modifier = Modifier.size(80.dp).clip(RoundedCornerShape(6.dp))
                                )
                                Text(item, fontSize = 11.sp, fontWeight = FontWeight.Medium, modifier = Modifier.padding(top = 4.dp))
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))
                    OutlinedTextField(
                        value = userPromptText,
                        onValueChange = { userPromptText = it },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text("Ask Gemini specific questions about this damage picture (optional)...") },
                        label = { Text("Analysis focus prompt") }
                    )

                    Spacer(modifier = Modifier.height(8.dp))
                    Button(
                        onClick = {
                            if (selectedImageIndex != -1) {
                                val selectedBmap = when(selectedImageIndex) {
                                    0 -> generateBurstPipeBitmap()
                                    1 -> generateBurntOutletBitmap()
                                    2 -> generateBrokenCabinetBitmap()
                                    else -> generateCloggedFilterBitmap()
                                }
                                viewModel.analyzeDamagePhoto(selectedBmap, userPromptText)
                            }
                        },
                        modifier = Modifier.fillMaxWidth().testTag("visual_analyze_button"),
                        enabled = selectedImageIndex != -1 && !isImageAnalyzing
                    ) {
                        if (isImageAnalyzing) {
                            CircularProgressIndicator(modifier = Modifier.size(18.dp), color = Color.White, strokeWidth = 2.dp)
                        } else {
                            Icon(Icons.Default.PhotoCamera, contentDescription = "Camera")
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Run Computer Vision Diagnosis")
                        }
                    }

                    if (imageResponse != null) {
                        Spacer(modifier = Modifier.height(16.dp))
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.5f), RoundedCornerShape(8.dp))
                                .padding(12.dp)
                        ) {
                            Column {
                                Text(
                                    text = "Computer Vision Diagnostic Report:",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp,
                                    color = MaterialTheme.colorScheme.secondary
                                )
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(text = imageResponse ?: "", fontSize = 13.sp, lineHeight = 18.sp)
                                Spacer(modifier = Modifier.height(12.dp))

                                val categoryHint = when(selectedImageIndex) {
                                    0 -> "Plumbing"
                                    1 -> "Electrical"
                                    2 -> "Carpentry"
                                    else -> "Appliance Repair"
                                }

                                Button(
                                    onClick = {
                                        bookCategory = categoryHint
                                        bookDesc = "Identified via Computer Vision Diagnostics: ${imageResponse?.take(80)}..."
                                        bookEmergency = selectedImageIndex in listOf(0, 1)
                                        bookCost = if (selectedImageIndex in listOf(0, 1)) 125.0 else 65.0
                                        bookTimeEst = if (selectedImageIndex in listOf(0, 1)) "45 Mins" else "2 Hours"
                                        showBookDialog = true
                                    },
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Icon(Icons.Default.Send, contentDescription = "Book")
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text("Book Diagnostic Repair Plan")
                                }
                            }
                        }
                    }
                }
            }
        }

        // Direct Manual Book Service Card
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "3. Quick Standard Booking",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Prefer bypassing AI matching? Open our direct service request form instantly.",
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Button(
                        onClick = {
                            bookCategory = "Plumbing"
                            bookDesc = ""
                            bookEmergency = false
                            bookCost = 60.0
                            bookTimeEst = "1-2 Hours"
                            showBookDialog = true
                        },
                        modifier = Modifier.fillMaxWidth().testTag("open_manual_booking")
                    ) {
                        Icon(Icons.Default.Add, contentDescription = "Book")
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Fill Service Request Form")
                    }
                }
            }
        }

        // Historical Bookings Header
        item {
            Text(
                text = "Your Service History",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(vertical = 4.dp)
            )
        }

        // Bookings List
        val citizenBookings = bookings.filter { !it.isOrganizationBulk }
        if (citizenBookings.isEmpty()) {
            item {
                Box(
                    modifier = Modifier.fillMaxWidth().padding(24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No bookings registered. Submit a service request to start!", color = Color.Gray, fontSize = 14.sp)
                }
            }
        } else {
            items(citizenBookings) { booking ->
                BookingHistoryRow(booking = booking, viewModel = viewModel)
            }
        }
    }

    // Interactive booking Form Dialog
    if (showBookDialog) {
        Dialog(onDismissRequest = { showBookDialog = false }) {
            Card(
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth().padding(8.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp).verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Text("Book Essential Service", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)

                    // Category dropdown compile-safe loop
                    var expandedCat by remember { mutableStateOf(false) }
                    Box(modifier = Modifier.fillMaxWidth()) {
                        OutlinedButton(
                            onClick = { expandedCat = true },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Category: $bookCategory")
                        }
                        DropdownMenu(expanded = expandedCat, onDismissRequest = { expandedCat = false }) {
                            for (cat in categories) {
                                DropdownMenuItem(
                                    text = { Text(cat) },
                                    onClick = {
                                        bookCategory = cat
                                        expandedCat = false
                                    }
                                )
                            }
                        }
                    }

                    OutlinedTextField(
                        value = bookDesc,
                        onValueChange = { bookDesc = it },
                        label = { Text("Detailed Problem Description") },
                        modifier = Modifier.fillMaxWidth().height(90.dp).testTag("dialog_problem_desc_input")
                    )

                    OutlinedTextField(
                        value = bookAddress,
                        onValueChange = { bookAddress = it },
                        label = { Text("Service Address") },
                        modifier = Modifier.fillMaxWidth().testTag("dialog_address_input")
                    )

                    OutlinedTextField(
                        value = bookName,
                        onValueChange = { bookName = it },
                        label = { Text("Citizen Name") },
                        modifier = Modifier.fillMaxWidth().testTag("dialog_name_input")
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(checked = bookEmergency, onCheckedChange = { bookEmergency = it })
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("This is an Emergency Hazard (Surcharged)", fontSize = 13.sp, fontWeight = FontWeight.Medium)
                    }

                    Divider()

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text("Predicted Cost", fontSize = 11.sp, color = Color.Gray)
                            Text("$${bookCost}", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                        }
                        Column {
                            Text("Matched Speed", fontSize = 11.sp, color = Color.Gray)
                            Text(bookTimeEst, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedButton(onClick = { showBookDialog = false }, modifier = Modifier.weight(1f)) {
                            Text("Cancel")
                        }
                        Button(
                            onClick = {
                                if (bookAddress.isNotBlank() && bookName.isNotBlank() && bookDesc.isNotBlank()) {
                                    viewModel.bookNewService(
                                        category = bookCategory,
                                        problemDescription = bookDesc,
                                        address = bookAddress,
                                        citizenName = bookName,
                                        isEmergency = bookEmergency,
                                        estimatedCost = bookCost,
                                        completionTime = bookTimeEst
                                    )
                                    showBookDialog = false
                                }
                            },
                            modifier = Modifier.weight(1f).testTag("dialog_confirm_booking_button"),
                            enabled = bookAddress.isNotBlank() && bookName.isNotBlank() && bookDesc.isNotBlank()
                        ) {
                            Text("Submit Job")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BookingHistoryRow(booking: Booking, viewModel: CivicViewModel) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f))
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    val icon = when(booking.category.lowercase()) {
                        "plumbing" -> Icons.Default.WaterDamage
                        "electrical" -> Icons.Default.FlashOn
                        "appliance repair" -> Icons.Default.Tv
                        "deep cleaning" -> Icons.Default.CleaningServices
                        "pest control" -> Icons.Default.BugReport
                        else -> Icons.Default.Build
                    }
                    Icon(icon, contentDescription = booking.category, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(24.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(booking.category, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                }

                val statusColor = when (booking.status) {
                    "Pending" -> Color(0xFFE65100)
                    "Assigned" -> Color(0xFF0D47A1)
                    "In Progress" -> Color(0xFF004D40)
                    else -> Color(0xFF33691E)
                }
                Box(
                    modifier = Modifier
                        .background(statusColor.copy(alpha = 0.15f), RoundedCornerShape(12.dp))
                        .padding(horizontal = 8.dp, vertical = 2.dp)
                ) {
                    Text(booking.status, color = statusColor, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }
            }

            Spacer(modifier = Modifier.height(4.dp))
            Text(booking.problemDescription, fontSize = 13.sp, maxLines = 2, color = MaterialTheme.colorScheme.onSurface)
            Spacer(modifier = Modifier.height(6.dp))
            Text("📍 ${booking.address}", fontSize = 11.sp, color = Color.Gray)

            if (booking.assignedProviderName != null) {
                Spacer(modifier = Modifier.height(6.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Technician: ${booking.assignedProviderName}", fontSize = 12.sp, fontWeight = FontWeight.Medium)
                    Text("Total: $${booking.estimatedCost}", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                }
            }

            if (booking.status == "Completed") {
                Spacer(modifier = Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    repeat(5) { starIndex ->
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Rating Star",
                            tint = if (starIndex < booking.rating) Color(0xFFFFB300) else Color.LightGray,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                    booking.comment?.let {
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("\"$it\"", fontSize = 11.sp, color = Color.Gray, maxLines = 1)
                    }
                }
            }
        }
    }
}

// Live Canvas-based GPS Transit Animation Map
@Composable
fun ActiveTrackingCard(booking: Booking, viewModel: CivicViewModel) {
    var showReviewDialog by remember { mutableStateOf(false) }
    var ratingVal by remember { mutableStateOf(5) }
    var reviewComment by remember { mutableStateOf("") }

    // High-Fidelity Secure Payment States (Real Product Design)
    var paymentMethod by remember { mutableStateOf("Card") } // "Card" or "GPay"
    var cardHolder by remember { mutableStateOf("John Doe") }
    var cardNumber by remember { mutableStateOf("") }
    var cardExpiry by remember { mutableStateOf("") }
    var cardCvv by remember { mutableStateOf("") }
    var cardZip by remember { mutableStateOf("") }
    var isErrorMsg by remember { mutableStateOf<String?>(null) }
    var paymentStep by remember { mutableStateOf(0) } // 0 = Input, 1 = Processing, 2 = Success Receipt

    val referenceId = remember(booking.id) { "CV-" + (100000..999999).random().toString() }

    // Floating animation ticker for the GPS moving car
    val infiniteTransition = rememberInfiniteTransition()
    val progress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(12000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    Card(
        modifier = Modifier.fillMaxWidth().testTag("active_tracking_card"),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1A1C1E)),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        border = BorderStroke(1.dp, Color(0xFF005AC1)),
        shape = RoundedCornerShape(24.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "ACTIVE REQUEST TRACKER",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.LightGray,
                        letterSpacing = 1.2.sp
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "Technician: ${booking.assignedProviderName ?: "Searching nearby..."}",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
                Box(
                    modifier = Modifier
                        .background(Color(0xFF005AC1).copy(alpha = 0.3f), RoundedCornerShape(12.dp))
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = booking.status,
                        color = Color.White,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Canvas Map Drawer
            Canvas(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(0xFFE8F5E9))
            ) {
                val width = size.width
                val height = size.height

                // Draw street paths (light gray lines)
                drawLine(Color.White, Offset(0f, height * 0.3f), Offset(width, height * 0.3f), strokeWidth = 30f)
                drawLine(Color.White, Offset(width * 0.4f, 0f), Offset(width * 0.4f, height), strokeWidth = 30f)
                drawLine(Color.White, Offset(0f, height * 0.8f), Offset(width, height * 0.8f), strokeWidth = 30f)

                // Grid detail line separators
                drawLine(Color.LightGray, Offset(0f, height * 0.3f), Offset(width, height * 0.3f), strokeWidth = 2f)
                drawLine(Color.LightGray, Offset(width * 0.4f, 0f), Offset(width * 0.4f, height), strokeWidth = 2f)

                val pStart = Offset(width * 0.1f, height * 0.3f)
                val pTurn = Offset(width * 0.4f, height * 0.3f)
                val pEnd = Offset(width * 0.4f, height * 0.8f)

                // Draw route line
                val routePath = Path().apply {
                    moveTo(pStart.x, pStart.y)
                    lineTo(pTurn.x, pTurn.y)
                    lineTo(pEnd.x, pEnd.y)
                }
                drawPath(
                    path = routePath,
                    color = Color.Red,
                    style = Stroke(
                        width = 5f,
                        pathEffect = androidx.compose.ui.graphics.PathEffect.dashPathEffect(floatArrayOf(15f, 15f), 0f)
                    )
                )

                // Draw Customer Home Marker (Green)
                drawCircle(color = Color(0xFF2E7D32), radius = 16f, center = pEnd)
                drawCircle(color = Color.White, radius = 6f, center = pEnd)

                // Calculate animated driver offset
                val driverPos = if (progress < 0.5f) {
                    val t = progress / 0.5f
                    Offset(pStart.x + (pTurn.x - pStart.x) * t, pStart.y)
                } else {
                    val t = (progress - 0.5f) / 0.5f
                    Offset(pTurn.x, pTurn.y + (pEnd.y - pTurn.y) * t)
                }

                // Draw Driver pulsing circle
                drawCircle(color = Color(0x331976D2), radius = 30f + (progress * 15f), center = driverPos)
                drawCircle(color = Color(0xFF1976D2), radius = 14f, center = driverPos)
                drawCircle(color = Color.White, radius = 5f, center = driverPos)
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = when(booking.status) {
                        "Assigned" -> "Technician is packing tools & heading out."
                        else -> "Technician is actively working on-site."
                    },
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.LightGray
                )

                Button(
                    onClick = { showReviewDialog = true },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF005AC1))
                ) {
                    Text(
                        text = if (booking.status == "Assigned") "Chat / Instructions" else "Pay & Complete",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }

    if (showReviewDialog) {
        Dialog(onDismissRequest = { showReviewDialog = false }) {
            Card(
                shape = RoundedCornerShape(24.dp),
                modifier = Modifier.fillMaxWidth().padding(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp).verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(14.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (paymentStep == 0) {
                        // --- STEP 0: CARD/GPAY DETAILS INPUT & RATING ---
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(Icons.Default.Lock, contentDescription = "Secure lock", tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(20.dp))
                            Text("Secure Portal Checkout", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                        }

                        Text(
                            text = "Complete the service with instant digital transfer to ${booking.assignedProviderName ?: "your specialist"}.",
                            fontSize = 12.sp,
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        // Surcharge details card
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f)),
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            Column(modifier = Modifier.padding(14.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text("Category:", fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                    Text(booking.category, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                                }
                                Spacer(modifier = Modifier.height(4.dp))
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text("Total Surcharge:", fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                    Text("$${booking.estimatedCost}", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                                }
                            }
                        }

                        // Rating input
                        Text("Rate your Specialist Experience", fontSize = 13.sp, fontWeight = FontWeight.Bold)
                        Row {
                            repeat(5) { index ->
                                IconButton(onClick = { ratingVal = index + 1 }) {
                                    Icon(
                                        imageVector = Icons.Default.Star,
                                        contentDescription = "Star",
                                        tint = if (index < ratingVal) Color(0xFFFFB300) else Color.LightGray,
                                        modifier = Modifier.size(32.dp)
                                    )
                                }
                            }
                        }

                        OutlinedTextField(
                            value = reviewComment,
                            onValueChange = { reviewComment = it },
                            label = { Text("Write service feedback or review (optional)...") },
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Select Payment Method", fontSize = 13.sp, fontWeight = FontWeight.Bold)

                        // Segmented buttons for payment method selection
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Button(
                                onClick = { paymentMethod = "Card" },
                                modifier = Modifier.weight(1f),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (paymentMethod == "Card") MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant,
                                    contentColor = if (paymentMethod == "Card") MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant
                                ),
                                shape = RoundedCornerShape(100.dp)
                            ) {
                                Icon(Icons.Default.CreditCard, contentDescription = "Card", modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("Card", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            }

                            Button(
                                onClick = { paymentMethod = "GPay" },
                                modifier = Modifier.weight(1f),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (paymentMethod == "GPay") MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant,
                                    contentColor = if (paymentMethod == "GPay") MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant
                                ),
                                shape = RoundedCornerShape(100.dp)
                            ) {
                                Text("Google / Apple Pay", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            }
                        }

                        if (paymentMethod == "Card") {
                            // Credit Card Fields
                            OutlinedTextField(
                                value = cardHolder,
                                onValueChange = { cardHolder = it },
                                label = { Text("Cardholder Name") },
                                singleLine = true,
                                modifier = Modifier.fillMaxWidth()
                            )

                            OutlinedTextField(
                                value = cardNumber,
                                onValueChange = { input ->
                                    val clean = input.filter { it.isDigit() }.take(16)
                                    cardNumber = clean.chunked(4).joinToString(" ")
                                },
                                label = { Text("Card Number") },
                                singleLine = true,
                                leadingIcon = { Icon(Icons.Default.CreditCard, contentDescription = null) },
                                keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(keyboardType = androidx.compose.ui.text.input.KeyboardType.Number),
                                modifier = Modifier.fillMaxWidth()
                            )

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                OutlinedTextField(
                                    value = cardExpiry,
                                    onValueChange = { input ->
                                        val clean = input.filter { it.isDigit() }
                                        cardExpiry = if (clean.length >= 2) {
                                            "${clean.substring(0, 2)}/${clean.substring(2, minOf(clean.length, 4))}"
                                        } else {
                                            clean
                                        }
                                    },
                                    label = { Text("MM/YY") },
                                    singleLine = true,
                                    modifier = Modifier.weight(1f),
                                    keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(keyboardType = androidx.compose.ui.text.input.KeyboardType.Number)
                                )

                                OutlinedTextField(
                                    value = cardCvv,
                                    onValueChange = { cardCvv = it.filter { c -> c.isDigit() }.take(4) },
                                    label = { Text("CVV") },
                                    singleLine = true,
                                    visualTransformation = androidx.compose.ui.text.input.PasswordVisualTransformation(),
                                    modifier = Modifier.weight(1f),
                                    keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(keyboardType = androidx.compose.ui.text.input.KeyboardType.Number)
                                )
                            }

                            OutlinedTextField(
                                value = cardZip,
                                onValueChange = { cardZip = it.take(8) },
                                label = { Text("Billing Postal Code (optional)") },
                                singleLine = true,
                                modifier = Modifier.fillMaxWidth()
                            )
                        } else {
                            // Apple / Google Pay Express Checkout
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 12.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(48.dp)
                                        .clip(RoundedCornerShape(24.dp))
                                        .background(Color.Black)
                                        .clickable {
                                            isErrorMsg = null
                                            paymentStep = 1 // biometric success straight to loading
                                        },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                                    ) {
                                        Text(" Pay", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                                        Text("| Pay Express", color = Color.Gray, fontSize = 12.sp)
                                    }
                                }

                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(48.dp)
                                        .clip(RoundedCornerShape(24.dp))
                                        .background(Color(0xFFE3F2FD))
                                        .border(1.dp, Color(0xFF90CAF9), RoundedCornerShape(24.dp))
                                        .clickable {
                                            isErrorMsg = null
                                            paymentStep = 1
                                        },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                                    ) {
                                        Text("Google Pay", color = Color(0xFF1976D2), fontWeight = FontWeight.Bold, fontSize = 15.sp)
                                        Icon(Icons.Default.CheckCircle, contentDescription = "Verified GPay", tint = Color(0xFF1976D2), modifier = Modifier.size(16.dp))
                                    }
                                }
                            }
                        }

                        if (isErrorMsg != null) {
                            Text(
                                text = isErrorMsg ?: "",
                                color = MaterialTheme.colorScheme.error,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(top = 4.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            OutlinedButton(onClick = { showReviewDialog = false }, modifier = Modifier.weight(1f)) {
                                Text("Cancel")
                            }
                            Button(
                                onClick = {
                                    if (paymentMethod == "Card") {
                                        val hasError = when {
                                            cardHolder.trim().isEmpty() -> {
                                                isErrorMsg = "Please enter Cardholder Name"
                                                true
                                            }
                                            cardNumber.replace(" ", "").length < 15 -> {
                                                isErrorMsg = "Please enter a valid 15- or 16-digit Card Number"
                                                true
                                            }
                                            !cardExpiry.matches(Regex("^(0[1-9]|1[0-2])/[0-9]{2}$")) -> {
                                                isErrorMsg = "Expiry must be MM/YY"
                                                true
                                            }
                                            cardCvv.length < 3 -> {
                                                isErrorMsg = "Please enter a valid CVV"
                                                true
                                            }
                                            else -> false
                                        }
                                        if (!hasError) {
                                            isErrorMsg = null
                                            paymentStep = 1
                                        }
                                    } else {
                                        paymentStep = 1
                                    }
                                },
                                modifier = Modifier.weight(1.5f).testTag("payment_confirm_button"),
                                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                            ) {
                                Text("Authorize $${booking.estimatedCost}")
                            }
                        }
                    } else if (paymentStep == 1) {
                        // --- STEP 1: PROCESSING SECURE GATEWAY ROUTING ---
                        LaunchedEffect(Unit) {
                            kotlinx.coroutines.delay(2000)
                            paymentStep = 2
                        }

                        Column(
                            modifier = Modifier.padding(vertical = 32.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            CircularProgressIndicator(
                                strokeWidth = 5.dp,
                                color = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(64.dp)
                            )
                            Text("Securing Secure Gateway Connection...", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                            Text("Routing transaction via 256-bit SSL pipeline. Please do not close or reload application...", fontSize = 11.sp, textAlign = TextAlign.Center, color = Color.Gray)
                        }
                    } else {
                        // --- STEP 2: PAYMENT SUCCESSFUL DIGITAL RECEIPT ---
                        Column(
                            modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(72.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFFE8F5E9)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = "Success",
                                    tint = Color(0xFF2E7D32),
                                    modifier = Modifier.size(40.dp)
                                )
                            }

                            Text("Transaction Approved", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color(0xFF2E7D32))

                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                            ) {
                                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                        Text("Ref Number:", fontSize = 12.sp, color = Color.Gray)
                                        Text(referenceId, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                    }
                                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                        Text("Payment Surcharge:", fontSize = 12.sp, color = Color.Gray)
                                        Text("$${booking.estimatedCost}", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                                    }
                                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                        Text("Assigned Specialist:", fontSize = 12.sp, color = Color.Gray)
                                        Text(booking.assignedProviderName ?: "Civic Specialist", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                    }
                                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                        Text("Payment Method:", fontSize = 12.sp, color = Color.Gray)
                                        Text(if (paymentMethod == "Card") "Credit Card (Visa)" else "Express Digital Wallet", fontSize = 12.sp, fontWeight = FontWeight.Medium)
                                    }
                                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                        Text("Status:", fontSize = 12.sp, color = Color.Gray)
                                        Text("DEPOSITED", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFF2E7D32))
                                    }
                                }
                            }

                            Button(
                                onClick = {
                                    viewModel.completeBookingAndPay(booking.id, ratingVal, reviewComment)
                                    paymentStep = 0
                                    showReviewDialog = false
                                },
                                modifier = Modifier.fillMaxWidth(),
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D32))
                            ) {
                                Text("Finalize & Return to Home", color = Color.White, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }
        }
    }
}


// --- SERVICE PROVIDER PORTAL ---
@Composable
fun ProviderPortalScreen(
    viewModel: CivicViewModel,
    bookings: List<Booking>,
    providers: List<ServiceProvider>
) {
    val matchingProviderId = 1 // Simulating Marcus Vance logged in
    val activeProvider = providers.firstOrNull { it.id == matchingProviderId }
    val earnings = activeProvider?.earnings ?: 1540.0
    val rating = activeProvider?.rating ?: 4.8
    val status = activeProvider?.status ?: "Available"

    val activeJobs = bookings.filter { it.assignedProviderId == matchingProviderId && it.status != "Completed" }
    val completedJobs = bookings.filter { it.assignedProviderId == matchingProviderId && it.status == "Completed" }

    var selectedJobForDetails by remember { mutableStateOf<Booking?>(null) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Provider Profile Banner
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Marcus Vance (Plumber)", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = MaterialTheme.colorScheme.onSecondaryContainer)
                        Text("Status: $status | Rating: ⭐ ${String.format(Locale.US, "%.1f", rating)}", fontSize = 13.sp, color = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.8f))
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("Total Earnings: $${String.format(Locale.US, "%.2f", earnings)}", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                    }
                    Icon(Icons.Default.Engineering, contentDescription = "Builder", modifier = Modifier.size(56.dp), tint = MaterialTheme.colorScheme.secondary)
                }
            }
        }

        // Optimized Route Canvas
        item {
            Card(
                modifier = Modifier.fillMaxWidth().testTag("route_optimization_card")
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("AI Optimized Route Recommendation", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = MaterialTheme.colorScheme.secondary)
                    Spacer(modifier = Modifier.height(6.dp))
                    Text("Your stops are sequenced to minimize fuel emissions and response delays:", fontSize = 12.sp)

                    Spacer(modifier = Modifier.height(12.dp))

                    Canvas(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color(0xFFECEFF1))
                    ) {
                        val w = size.width
                        val h = size.height

                        val n1 = Offset(w * 0.15f, h * 0.5f)
                        val n2 = Offset(w * 0.5f, h * 0.5f)
                        val n3 = Offset(w * 0.85f, h * 0.5f)

                        drawLine(Color.LightGray, n1, n3, strokeWidth = 10f)
                        drawLine(Color(0xFF2E7D32), n1, n2, strokeWidth = 10f)

                        drawCircle(Color(0xFF2E7D32), radius = 16f, center = n1)
                        drawCircle(Color(0xFF1976D2), radius = 16f, center = n2)
                        drawCircle(Color.Gray, radius = 16f, center = n3)

                        drawCircle(Color.White, radius = 6f, center = n1)
                        drawCircle(Color.White, radius = 6f, center = n2)
                        drawCircle(Color.White, radius = 6f, center = n3)
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("1. Dispatch Base", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color.Gray)
                        Text("2. Active Job (5 mi)", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                        Text("3. Next scheduled stop", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color.Gray)
                    }
                }
            }
        }

        // Active Tasks Feed
        item {
            Text("Your Active Dispatch Tasks", fontWeight = FontWeight.Bold, fontSize = 16.sp)
        }

        if (activeJobs.isEmpty()) {
            item {
                Box(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text("You have no pending assignments! Rest or check Admin list.", color = Color.Gray, fontSize = 14.sp)
                }
            }
        } else {
            items(activeJobs) { job ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { selectedJobForDetails = job },
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(job.citizenName, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                            Text(job.problemDescription, fontSize = 12.sp, maxLines = 1, color = Color.Gray)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text("📍 ${job.address}", fontSize = 11.sp, color = MaterialTheme.colorScheme.primary)
                        }
                        Button(onClick = { selectedJobForDetails = job }) {
                            Text("Manage")
                        }
                    }
                }
            }
        }

        // Completed Jobs Statistics
        item {
            Text("Completed Jobs History", fontWeight = FontWeight.Bold, fontSize = 16.sp)
        }

        if (completedJobs.isEmpty()) {
            item {
                Text("No historically completed jobs listed.", color = Color.Gray, fontSize = 13.sp)
            }
        } else {
            items(completedJobs) { historyJob ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f))
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(historyJob.citizenName, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                            Text("Revenue generated: $${historyJob.estimatedCost}", fontSize = 12.sp, color = Color.Green, fontWeight = FontWeight.Bold)
                        }
                        Row {
                            repeat(historyJob.rating) {
                                Icon(Icons.Default.Star, contentDescription = "*", tint = Color(0xFFFFB300), modifier = Modifier.size(16.dp))
                            }
                        }
                    }
                }
            }
        }
    }

    // Capture delegated smart cast by mapping to safe immutable local val
    val activeJob = selectedJobForDetails
    if (activeJob != null) {
        Dialog(onDismissRequest = { selectedJobForDetails = null }) {
            Card(
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth().padding(12.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text("Active Dispatch Details", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.secondary)

                    Divider()

                    Text("Customer Name: ${activeJob.citizenName}", fontSize = 14.sp, fontWeight = FontWeight.Bold)
                    Text("Address: ${activeJob.address}", fontSize = 13.sp)
                    Text("Description: ${activeJob.problemDescription}", fontSize = 13.sp, color = Color.Gray)

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                    ) {
                        Column(modifier = Modifier.padding(10.dp)) {
                            Text("Estimated Reward: $${activeJob.estimatedCost}", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                            Text("Matched SLA: ${activeJob.completionTimeEstimate}", fontSize = 12.sp)
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = {
                                viewModel.completeBookingAndPay(activeJob.id, 5, "Job completed by on-duty plumber.")
                                selectedJobForDetails = null
                            },
                            modifier = Modifier.weight(1.5f).testTag("provider_job_complete_btn")
                        ) {
                            Text("Mark Completed")
                        }
                        OutlinedButton(onClick = { selectedJobForDetails = null }, modifier = Modifier.weight(1f)) {
                            Text("Close")
                        }
                    }
                }
            }
        }
    }
}


// --- ORGANIZATION DASHBOARD ---
@Composable
fun OrganizationDashboardScreen(
    viewModel: CivicViewModel,
    bookings: List<Booking>,
    schedules: List<OrgBulkMaintenanceSchedule>
) {
    val orgName = "Hillcrest Retirement Home"
    var showAddSchedule by remember { mutableStateOf(false) }

    var sType by remember { mutableStateOf("Plumbing") }
    var sLoc by remember { mutableStateOf("Building B East Wing") }
    var sRecurrence by remember { mutableStateOf("Monthly") }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(orgName, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("Corporate Client Code: ORG-HLCRST", fontSize = 12.sp, color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f))
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = {
                                viewModel.createBulkOrgBooking(
                                    orgName = orgName,
                                    category = "Plumbing",
                                    desc = "Emergency backflow leak identified in main cafeteria boiler.",
                                    address = "1900 Hillcrest Rd, Tower A"
                                )
                            },
                            modifier = Modifier.weight(1f).testTag("org_bulk_request_btn")
                        ) {
                            Icon(Icons.Default.Add, contentDescription = "Bulk")
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("File Bulk Ticket")
                        }
                        OutlinedButton(
                            onClick = { showAddSchedule = true },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Schedule Routine")
                        }
                    }
                }
            }
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Routine Maintenance Calendar", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                IconButton(onClick = { showAddSchedule = true }) {
                    Icon(Icons.Default.Add, contentDescription = "Add")
                }
            }
        }

        if (schedules.isEmpty()) {
            item {
                Text("No routine recurring tasks currently scheduled.", color = Color.Gray, fontSize = 13.sp)
            }
        } else {
            items(schedules) { schedule ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(schedule.serviceType, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                            Box(
                                modifier = Modifier
                                    .background(MaterialTheme.colorScheme.secondary.copy(alpha = 0.15f), RoundedCornerShape(12.dp))
                                    .padding(horizontal = 8.dp, vertical = 2.dp)
                            ) {
                                Text(schedule.recurrence, color = MaterialTheme.colorScheme.secondary, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("📍 ${schedule.location}", fontSize = 11.sp, color = Color.Gray)
                        Spacer(modifier = Modifier.height(4.dp))
                        val format = SimpleDateFormat("MMM dd, yyyy", Locale.US)
                        Text("Next Due Date: ${format.format(Date(schedule.nextScheduledDate))}", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                    }
                }
            }
        }

        item {
            Text("Active Corporate Bookings", fontWeight = FontWeight.Bold, fontSize = 16.sp)
        }

        val orgBookings = bookings.filter { it.isOrganizationBulk }
        if (orgBookings.isEmpty()) {
            item {
                Text("No active corporative bookings running currently.", color = Color.Gray, fontSize = 13.sp)
            }
        } else {
            items(orgBookings) { booking ->
                BookingHistoryRow(booking = booking, viewModel = viewModel)
            }
        }
    }

    if (showAddSchedule) {
        Dialog(onDismissRequest = { showAddSchedule = false }) {
            Card(
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth().padding(12.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text("Schedule Recurring Maintenance", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)

                    OutlinedTextField(
                        value = sType,
                        onValueChange = { sType = it },
                        label = { Text("Service (e.g. Electrical Inspections)") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = sLoc,
                        onValueChange = { sLoc = it },
                        label = { Text("Target Location / Building Wing") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    // Recurrence Selector with safe standard loop
                    var recurExpanded by remember { mutableStateOf(false) }
                    val recurrences = listOf("Weekly", "Monthly", "Quarterly", "Annually")
                    Box(modifier = Modifier.fillMaxWidth()) {
                        OutlinedButton(onClick = { recurExpanded = true }, modifier = Modifier.fillMaxWidth()) {
                            Text("Recurrence: $sRecurrence")
                        }
                        DropdownMenu(expanded = recurExpanded, onDismissRequest = { recurExpanded = false }) {
                            for (rec in recurrences) {
                                DropdownMenuItem(
                                    text = { Text(rec) },
                                    onClick = {
                                        sRecurrence = rec
                                        recurExpanded = false
                                    }
                                )
                            }
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = {
                                viewModel.scheduleMaintenance(
                                    orgName = orgName,
                                    serviceType = sType,
                                    location = sLoc,
                                    recurrence = sRecurrence
                                )
                                showAddSchedule = false
                            },
                            modifier = Modifier.weight(1.5f).testTag("org_confirm_schedule_btn")
                        ) {
                            Text("Confirm Schedule")
                        }
                        OutlinedButton(onClick = { showAddSchedule = false }, modifier = Modifier.weight(1f)) {
                            Text("Cancel")
                        }
                    }
                }
            }
        }
    }
}


// --- ADMIN DASHBOARD ---
@Composable
fun AdminDashboardScreen(
    viewModel: CivicViewModel,
    bookings: List<Booking>,
    providers: List<ServiceProvider>,
    complaints: List<Complaint>
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Card(modifier = Modifier.weight(1f)) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text("Active Providers", fontSize = 11.sp, color = Color.Gray)
                        Text("${providers.size}", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                    }
                }
                Card(modifier = Modifier.weight(1f)) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text("Total Orders", fontSize = 11.sp, color = Color.Gray)
                        Text("${bookings.size}", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                    }
                }
                Card(modifier = Modifier.weight(1f)) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text("Complaints", fontSize = 11.sp, color = Color.Gray)
                        Text("${complaints.size}", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.Red)
                    }
                }
            }
        }

        // Canvas Analytics Section (Weekly performance graphs)
        item {
            Card(
                modifier = Modifier.fillMaxWidth().testTag("admin_analytics_card")
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Weekly Platform Booking Volume", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = MaterialTheme.colorScheme.primary)
                    Spacer(modifier = Modifier.height(12.dp))

                    Canvas(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp)
                            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f))
                    ) {
                        val w = size.width
                        val h = size.height

                        drawLine(Color.Gray, Offset(40f, h - 20f), Offset(w - 20f, h - 20f), strokeWidth = 2f)
                        drawLine(Color.Gray, Offset(40f, 10f), Offset(40f, h - 20f), strokeWidth = 2f)

                        val points = listOf(
                            Offset(40f + (w - 60f) * 0f / 6, h - 20f - (h - 40f) * 0.2f),
                            Offset(40f + (w - 60f) * 1f / 6, h - 20f - (h - 40f) * 0.45f),
                            Offset(40f + (w - 60f) * 2f / 6, h - 20f - (h - 40f) * 0.35f),
                            Offset(40f + (w - 60f) * 3f / 6, h - 20f - (h - 40f) * 0.75f),
                            Offset(40f + (w - 60f) * 4f / 6, h - 20f - (h - 40f) * 0.55f),
                            Offset(40f + (w - 60f) * 5f / 6, h - 20f - (h - 40f) * 0.9f),
                            Offset(40f + (w - 60f) * 6f / 6, h - 20f - (h - 40f) * 0.8f)
                        )

                        val linePath = Path().apply {
                            moveTo(points[0].x, points[0].y)
                            for (i in 1 until points.size) {
                                lineTo(points[i].x, points[i].y)
                            }
                        }
                        drawPath(linePath, color = Color(0xFF6200EE), style = Stroke(width = 6f))

                        for (p in points) {
                            drawCircle(Color(0xFF03DAC6), radius = 8f, center = p)
                            drawCircle(Color.White, radius = 3f, center = p)
                        }
                    }

                    Spacer(modifier = Modifier.height(6.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun").forEach { day ->
                            Text(day, fontSize = 11.sp, color = Color.Gray, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }

        item {
            Text("Verified Service Provider Roster", fontWeight = FontWeight.Bold, fontSize = 16.sp)
        }

        items(providers) { provider ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(provider.name, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        Text("${provider.category} | ${provider.experienceYears} Years Exp", fontSize = 12.sp, color = Color.Gray)
                        Spacer(modifier = Modifier.height(4.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = if (provider.isVerified) Icons.Default.Verified else Icons.Default.Warning,
                                contentDescription = "Verified status",
                                tint = if (provider.isVerified) Color(0xFF2E7D32) else Color(0xFFFF9100),
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(if (provider.isVerified) "Verified Professional" else "Needs Admin Validation", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                    }

                    if (!provider.isVerified) {
                        Button(
                            onClick = { viewModel.approveProvider(provider.id) },
                            modifier = Modifier.testTag("verify_provider_${provider.id}")
                        ) {
                            Text("Verify")
                        }
                    }
                }
            }
        }

        item {
            Text("Citizen Complaints & Resolution Center", fontWeight = FontWeight.Bold, fontSize = 16.sp)
        }

        if (complaints.isEmpty()) {
            item {
                Text("Zero citizen complaints filed today. Platform integrity running high!", color = Color.Gray, fontSize = 13.sp)
            }
        } else {
            items(complaints) { complaint ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(complaint.title, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = MaterialTheme.colorScheme.onErrorContainer)
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(complaint.details, fontSize = 12.sp, color = MaterialTheme.colorScheme.onErrorContainer)
                    }
                }
            }
        }
    }
}
